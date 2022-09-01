package dominio.controladores;

import dominio.clases.algorithm.collaborativefiltering.*;
import dominio.clases.algorithm.contentbasedflitering.*;
import dominio.clases.algorithm.hybrid.*;
import dominio.clases.content.*;
import dominio.clases.evaluation.*;
import dominio.clases.content.Content;
import dominio.clases.recommendation.*;
import persistencia.*;

import java.util.*;

/**
 *
 * @author Marc Delgado Sánchez
 * @author Manel Piera Garrigosa
 * @brief  Class used to implement the Domain Controller.
 */

public class ControladorDominio {
    /**
     * @brief Instance of the Persistance Controller
     */
    private ControladorPersistencia CP;
    private CollaborativeFiltering CFNotEval;
    private CollaborativeFiltering CFEval;
    private K_NN KNNnotEval;
    private K_NN KNNEval;
    private Evaluation E;
    private Recommendation rec;
    private Hybrid H;

    public ControladorDominio(){
        //Cridar a Persistencia a que porti tots els mapes (En format List<String> i transformar-ho a maps)
        CP = new ControladorPersistencia();
    }


    public Map<Integer, Map<Integer, Float>> constructMapRate(List<Integer> mapRateIDusers, List<List<Integer>> mapRateIDitems, List<List<Float>> mapRateVal){
        Map<Integer, Map<Integer, Float>> result = new TreeMap<>();
        for(int i = 0; i < mapRateIDusers.size(); ++i){
            Map<Integer, Float> interiorMap = new TreeMap<>();
            for(int j = 0; j < mapRateIDitems.get(i).size(); ++j){
                interiorMap.put(mapRateIDitems.get(i).get(j), mapRateVal.get(i).get(j));
            }
            result.put(mapRateIDusers.get(i), interiorMap);
        }
        return result;
    }


    public Map<Integer, List<Content>> constructMapItem(List<Integer> IDs, List<List<String>> types, List<List<Integer>> ints, List<List<Double>> doubles, List<List<List<String>>> categorics) {
        Map<Integer, List<Content>> result = new TreeMap<>();
        int how_many_items = IDs.size();
        int how_many_tags = types.get(1).size();
        for (int i = 0; i < how_many_items; ++i) {
            List<Content> new_tags = new ArrayList<>();
            for (int j = 0; j < how_many_tags; ++j) {
                Content tag = new Content(types.get(i).get(j),ints.get(i).get(j),doubles.get(i).get(j), categorics.get(i).get(j));
                new_tags.add(tag);
            }
            result.put(IDs.get(i),new_tags);
        }
        return result;
    }
    public void inicializar(String path){
        CP.inicializar(path);

        List<Integer> mapRateIDusersRatings = CP.getMapRateIDusers(0);
        List<List<Integer>> mapRateIDitemsRatings = CP.getMapRateIDitems(0);
        List<List<Float>> mapRateValRatings = CP.getMapRateVal(0);

        List<Integer> mapRateIDusersKnown = CP.getMapRateIDusers(1);
        List<List<Integer>> mapRateIDitemsKnown = CP.getMapRateIDitems(1);
        List<List<Float>> mapRateValKnown = CP.getMapRateVal(1);

        List<Integer> mapRateIDusersUnknown = CP.getMapRateIDusers(2);
        List<List<Integer>> mapRateIDitemsUnknown = CP.getMapRateIDitems(2);
        List<List<Float>> mapRateValUnknown = CP.getMapRateVal(2);

        List<Integer> mapItemIDs = CP.getMapItemIDs();
        List<List<String>>  mapItemTagsTipus = CP.getMapTipusTags();
        List<List<Integer>> mapItemTagsIntegers = CP.getMapIntsTags();
        List<List<Double>> mapItemTagsDoubles = CP.getMapDoublesTags();
        List<List<List<String>>> mapItemTagsCategorics = CP.getMapCategoricsTags();

        Map<Integer, Map<Integer, Float>> mapRateRatings1 = constructMapRate(mapRateIDusersRatings, mapRateIDitemsRatings, mapRateValRatings);
        Map<Integer, Map<Integer, Float>> mapRateRatings2 = constructMapRate(mapRateIDusersRatings, mapRateIDitemsRatings, mapRateValRatings);
        Map<Integer, Map<Integer, Float>> mapRateKnown1 = constructMapRate(mapRateIDusersKnown, mapRateIDitemsKnown, mapRateValKnown);
        Map<Integer, Map<Integer, Float>> mapRateKnown2 = constructMapRate(mapRateIDusersKnown, mapRateIDitemsKnown, mapRateValKnown);
        Map<Integer, Map<Integer, Float>> mapRateUnknown = constructMapRate(mapRateIDusersUnknown, mapRateIDitemsUnknown, mapRateValUnknown);
        Map<Integer, List<Content>> mapItems1 = constructMapItem(mapItemIDs, mapItemTagsTipus, mapItemTagsIntegers, mapItemTagsDoubles, mapItemTagsCategorics);
        Map<Integer, List<Content>> mapItems2 = constructMapItem(mapItemIDs, mapItemTagsTipus, mapItemTagsIntegers, mapItemTagsDoubles, mapItemTagsCategorics);

        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> item_list = CP.list_item();
        int mida = item_list.size();
        for (int i = 0; i < mida; ++i) {
            l1.add(item_list.get(i));
            l2.add(item_list.get(i));
        }

        CFEval = new CollaborativeFiltering(mapRateKnown1, mapRateUnknown);
        CFNotEval = new CollaborativeFiltering(mapRateRatings1, new TreeMap<>());
        KNNEval = new K_NN(mapRateKnown2, mapRateUnknown, mapItems1, l1);
        KNNnotEval = new K_NN(mapRateRatings2, new TreeMap<>(), mapItems2, l2);
        E = new Evaluation(mapRateUnknown);
        H = new Hybrid();

        KNNEval.initSimilarityTable();
        KNNnotEval.initSimilarityTable();
    }

    List<Content> convertir_tags_content(List<String> tags) {
        List<String> types = CP.list_tipusheader();
        List<Content> new_tags = new ArrayList<>();
        int mida = tags.size();
        Content aux;
        for (int i = 0; i < mida; ++i) {
            switch (types.get(i)) {
                case "b": {
                    aux = new Content("b", Integer.parseInt(tags.get(i)), null, null);
                    new_tags.add(aux);
                    break;
                }
                case "i": {
                    aux = new Content("i", Integer.parseInt(tags.get(i)), null, null);
                    new_tags.add(aux);
                    break;
                }
                case "d": {
                    aux = new Content("d", null, Double.parseDouble(tags.get(i)), null);
                    new_tags.add(aux);
                    break;
                }
                case "c": {
                    List<String> resultat = Arrays.asList(tags.get(i).split(";"));
                    aux = new Content("c", null, null, resultat);
                    new_tags.add(aux);
                    break;
                }
                default: {
                    aux = new Content(tags.get(i), null, null, null);
                    new_tags.add(aux);
                    break;
                }
            }
        }
        return new_tags;
    }

    Content convertir_un_tag_content(String tag, int index) {
        List<String> types = CP.list_tipusheader();
        String tipus = types.get(index);
        Content result;
        switch (tipus) {
            case "b": {
                result = new Content("b", Integer.parseInt(tag), null, null);
                break;
            }
            case "i": {
                result = new Content("i", Integer.parseInt(tag), null, null);
                break;
            }
            case "d": {
                result = new Content("d", null, Double.parseDouble(tag), null);
                break;
            }
            case "c": {
                List<String> resultat = Arrays.asList(tag.split(";"));
                result = new Content("c", null, null, resultat);
                break;
            }
            default: {
                result = new Content(tag, null, null, null);
                break;
            }
        }
        return result;
    }

    public List<Integer> list_user_known(){
        return CP.list_user_known();
        //retorna una llista amb tots els id users
    }

    public List<Integer> list_user_rating() {
        return CP.list_user_rating();
    }

    public List<Integer> list_item(){
        return CP.list_item();
        //retorna una llista amb tots els id items
    }

    public boolean addItem(int ID, List<String> tags){
        List<Content> new_tags = convertir_tags_content(tags);
        KNNEval.Add_Item(ID, new_tags);
        KNNnotEval.Add_Item(ID,new_tags);
        return CP.addItem(ID, tags);
    }
    public boolean exists(int idItem){
        return CP.exists(idItem);
    }
    public boolean delItem(int ID) {
        CFNotEval.delItem(ID);
        CFEval.delItem(ID);
        KNNnotEval.Del_Item(ID);
        KNNEval.Del_Item(ID);
        return CP.delItem(ID);
    }
    public boolean modTag(int IDitem, String atribute, String newtag){
        int index_of_atribute = CP.get_header_items().indexOf(atribute);
        Content tag_convertida = convertir_un_tag_content(newtag,index_of_atribute);
        KNNEval.Mod_Tag(IDitem,index_of_atribute,tag_convertida);
        KNNnotEval.Mod_Tag(IDitem,index_of_atribute,tag_convertida);
        return CP.modTag(IDitem, atribute, newtag);
    }
    public boolean delTag(int IDitem, String atribute){
        int index_of_atribute = CP.get_header_items().indexOf(atribute);
        KNNEval.Del_Tag(IDitem,index_of_atribute);
        KNNnotEval.Del_Tag(IDitem,index_of_atribute);
        return CP.delTag(IDitem, atribute);
    }
    public boolean addUser(int ID) {
        return CP.addUser(ID);
    }

    public boolean delUser(int ID) {
        CFNotEval.delUser(ID);
        KNNnotEval.esborra_user_map_rating(ID);
        return CP.delUser(ID);
    }

    public boolean modRating(int IDuser, int IDitem, float new_rate) {
        CFNotEval.modRating(IDuser, IDitem, new_rate);
        KNNnotEval.modifica_map_rating(IDuser,IDitem,new_rate);
        return CP.modRating(IDuser, IDitem, new_rate);
    }
    public boolean delRating(int IDuser, int IDitem) {
        CFNotEval.delRating(IDuser, IDitem);
        KNNnotEval.esborra_rating_user(IDuser,IDitem);
        return CP.delRating(IDuser, IDitem);
    }

    /*public List<List<String>> getMapRate(int a){ // 0 si es ratings, 1 si es known, 2 si es unknown
        return CP.getMapRate(a);
    }*/

    /*public List<List<String>> getMapItemTags(){
        return CP.getMapItemTags();
    }*/

    public void recommendCF(int k, int userID, boolean eval){
        if(eval){
            rec = CFEval.recommend(userID, k, eval);
        }
        else{
            rec = CFNotEval.recommend(userID, k, eval);
        }
    }

    public void recommendCBF(int k, int userID, boolean eval){
        if (eval){
            rec = KNNEval.recommend(userID, k, eval);
        }
        else{
            rec = KNNnotEval.recommend(userID, k, eval);
        }
    }

    public void recommendH(int k, int userID, boolean eval){
        Recommendation r1, r2;
        if (eval) {
            r1 = CFEval.recommend(userID, k, eval);
            r2 = KNNEval.recommend(userID, k, eval);
        }
        else{
            r1 = CFNotEval.recommend(userID, k, eval);
            r2 = KNNnotEval.recommend(userID, k, eval);
        }

        rec = H.recommend(r1,r2,k);
    }
    public Float new_DCG(){
        return E.DCG(rec);
    }

    public List<String> tag_list() {
        return CP.get_header_items();
    }

    public List<String> types_header() {
        return CP.list_tipusheader();
    }


    public List<Integer> list_itemREC() {
        return rec.getItemIDs();
        //retorna la llista de items de la recomanacio
    }

    public List<Float> list_valREC() {
        return rec.getItemRates();
        //retorna la llista de valors de la recomanacio
    }

    public void saveRec(){
        CP.saveRec(rec.getID_perfil(), rec.getAlg(), rec.getItemIDs(), rec.getItemRates());
    }

    public List<Float> list_valSavedREC(int IDuser, int alg, String Date) {
        return CP.list_valSavedREC(IDuser, alg, Date);
    }

    public List<Integer> list_itemSavedREC(int IDuser, int alg, String Date) {
        return CP.list_itemSavedREC(IDuser, alg, Date);
    }

    public List<Integer> get_IDuser_rec(){
        return CP.get_IDuser_rec();
    }

    public List<Integer> get_alg_rec(){
        return CP.get_alg_rec();
    }

    public List<String> get_dates_rec(){
        return CP.get_dates_rec();
    }

    public void guardarCambios(){
        boolean b = CP.crear_carpeta();
    }
}
