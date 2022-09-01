package dominio.clases.algorithm.slopeone;


import java.util.Map;
import java.util.TreeMap;

import static java.lang.Math.abs;

/**
 *
 * @author Oriol Martí Jariod
 */

/** @class SlopeOne
 * @brief Implements Slope One dominio.controladores.clases.atribut.algorithm.
 */

public class SlopeOne {


    private static Map<Integer,Map<Integer,Float>> map_data;
    /** @brief matrix data <userid<itemid,rate>>
     * */
    private static Map<Integer, Map<Integer, Float>> map_des;
    /** @brief matrix with differential between two items <itemid_1<itemid_2,diff>>
     * */
    private static Map<Integer, Map<Integer, Integer>> map_freq;
    /** @brief matrix with number of times we’ve computed a differential rating for each pair of items <itemid_1<itemid_2<times>>
     * */
    private static Map<Integer,Float> map_pred;
    /** @brief map with rates' predictions of the items for one dominio.controladores.clases.atribut.user <itemid,predict_rate>
     * */

    //Builders

    /** @brief Default builder.
     *
     * \pre <em>true</em>
     * \post It creates a <em>SlopeOne</em> object with its attribute <em>map_data</em>,
     *       <em>map_des</em>, <em>map_freq</em> and <em>map_pred</em> empty.
     */
    public SlopeOne(){
        SlopeOne.map_data = new TreeMap<>();
        SlopeOne.map_des = new TreeMap<>();
        SlopeOne.map_freq = new TreeMap<>();
        SlopeOne.map_pred = new TreeMap<>();
    }

    /** @brief Builder with defined attributes.
     *
     * @param map_data Matrix that represents the data.
     * @param map_des  Matrix that represents the difference between two items.
     * @param map_freq Matrix that represents times we've computed a diff rating for each pair of items.
     * @param map_pred Map that represents rates' predictions of the items for on dominio.controladores.clases.atribut.user.
     *
     * \pre <em>true</em>
     * \post It creates a <em>SlopeOne</em> object with the parameters map_data, map_des,
     *       map_freq and map_pred as its attribute <em>map_data</em>, <em>map_des</em>, <em>map_freq</em> and <em>map_pred</em>.
     */
    public SlopeOne(Map<Integer,Map<Integer,Float>> map_data, Map<Integer, Map<Integer, Float>> map_des,
                    Map<Integer, Map<Integer, Integer>> map_freq , Map<Integer,Float> map_pred){
        SlopeOne.map_data = map_data;
        SlopeOne.map_des = map_des;
        SlopeOne.map_freq = map_freq;
        SlopeOne.map_pred = map_pred;
    }

    /**
     * @brief Getter of the Map_data
     * @return Return the map_data
     *
     * \pre true
     * \post true
     */
    public static Map<Integer, Map<Integer, Float>> getMap_data() {return map_data;}

    /**
     * @brief Getter of the Map_des
     * @return Return the map_des
     *
     * \pre true
     * \post true
     */
    public static Map<Integer, Map<Integer, Float>> getMap_des() {return map_des;    }

    /**
     * @brief Getter of the Map_pred
     * @return Return the map_pred
     *
     * \pre true
     * \post true
     */
    public static Map<Integer, Float> getMap_pred() {return map_pred;}

    /**
     * @brief Getter of the Map_freq
     * @return Return the map_freq
     *
     * \pre true
     * \post true
     */
    public static Map<Integer, Map<Integer, Integer>> getMap_freq() {return map_freq;}

    /**
     * @brief Setter of the atribute map_data
     * @param map_data Matrix that represents the data.
     *
     * \pre True
     * \post Modify map_data
     */
    public static void setMap_data(Map<Integer, Map<Integer, Float>> map_data) { SlopeOne.map_data = map_data;}


    /** @brief Given the data and a dominio.controladores.clases.atribut.user which it's the one we want to predict his ratings,
     *         calls the pertinents methods to calculate it, and clean the map_pred with the predictions that we want.
     *
     * @param data Matrix with a cluster of users, with his rates for different items.
     * @param user Map with the items from the dominio.controladores.clases.atribut.user we want to predict his rate of the items non-rated.
     *
     * @return It returns the prediction map, which contains the predicted rates form our dominio.controladores.clases.atribut.user.
     *
     * \pre true
     * \post Calculate a prediction for an dominio.controladores.clases.atribut.user
     */
    public static Map<Integer,Float> slopeone(Map<Integer, Map<Integer, Float>> data, Map<Integer,Float> user) {
        map_data = data;
        desviacio_mitjana();

        prediccio(user);
        //for(Map.Entry<Integer,Float> entry : user.entrySet()) { //netejar map_pred
        //    map_pred.remove(entry.getKey());
        //}

        return map_pred;
    }


    /** @brief Calculates the difference between all dominio.controladores.clases.atribut.item-dominio.controladores.clases.atribut.item rating values.
     *
     * \pre Ture
     * \post Fills map_des with the diff between all dominio.controladores.clases.atribut.item-dominio.controladores.clases.atribut.item rating values.
     */
    public static void desviacio_mitjana(){
        map_des = new TreeMap<Integer,Map<Integer,Float>>();
        map_freq = new TreeMap<Integer,Map<Integer,Integer>>();
        for (Map<Integer, Float> users : map_data.values()) { //  per tots els usuaris
            for (Map.Entry<Integer, Float> u_data : users.entrySet()) { // itera a traves de les dades dels usuaris

                if (!map_des.containsKey(u_data.getKey())) { //mirar si item q estem tractan esta al map_des
                    // afegir item map_des i map_freq
                    map_des.put(u_data.getKey(), new TreeMap<Integer, Float>());
                    map_freq.put(u_data.getKey(), new TreeMap<Integer, Integer>());
                }

                for (Map.Entry<Integer, Float> u2_data : users.entrySet()) { //itera a travesd de les dades dels usuaris

                    int cont_ant = 0; //contador anterior, cops que un parell d'items l'hem vist
                    //inicialitzat a 0 per si es el primer cop, si ja s'ha tractat abans, entrarem al if i recuperarem el valor
                    if (map_freq.get(u_data.getKey()).containsKey(u2_data.getKey())) //quan els usuaris son diferents
                        cont_ant =  map_freq.get(u_data.getKey()).get(u2_data.getKey());
                    //acumulem els cops vist el dominio.controladores.clases.atribut.item en dos usuaris diferents


                    float desv_ini = 0.0f; //difrerencia anterior, difrencia de dos items inicialitzat a 0 per si es el primer cop
                    // si ja han estat tractats, ho recuperem i ho sumarem amb la diff actual
                    if (map_des.get(u_data.getKey()).containsKey(u2_data.getKey()))  //if quan els usuaris son diferents
                        desv_ini = map_des.get(u_data.getKey()).get(u2_data.getKey()); // acumulem les desviacions d'usuaris diferents



                    float desv_result = u_data.getValue() - u2_data.getValue(); //diferencia entre els items de u1 i u2
                    map_freq.get(u_data.getKey()).put(u2_data.getKey(), cont_ant +1);
                    //+1, incrementem els cops tractat aquest parell d'dominio.controladores.clases.atribut.item amb els cops anteriors
                    map_des.get(u_data.getKey()).put(u2_data.getKey(), desv_ini + desv_result);
                }
            }
        }

        for (Integer j : map_des.keySet()) {
            for (Integer i : map_des.get(j).keySet()) {
                float rate = (float) map_des.get(j).get(i); //agafem la diferencia del rate
                int cardinalitat = map_freq.get(j).get(i); //agafem la cardianlitat, cops q hem fet la diferencia
                map_des.get(j).put(i, rate / cardinalitat); //calculem la desviació mitjana i l'afegim al map
            }
        }

    }



//prediccio bona no weighted
    /*
    public static void prediccio(Map<Integer, Float> u_data) {
        map_pred = new TreeMap<Integer,Float>();
        Map<Integer,Integer> freq = new TreeMap<>();
        for (int j : map_des.keySet()) { //inicialitzem els maps
            freq.put(j, 0);
            map_pred.put(j, 0.0f);
        }

        float mitjana = 0.0f;
        int k = 0;
        for(Map.Entry<Integer,Float> entry : u_data.entrySet()){ //mitjana de les valoracions de l'usuari a estimar
            ++k;
            mitjana += entry.getValue();
        }
        if(k>1) mitjana /= (float)k; //per més d'un item

        for(Map.Entry<Integer, Map<Integer, Float>> entry: map_des.entrySet()){ //mitjana de la desviacio mitjana
            float desvi = 0.0f;
            for(Map.Entry<Integer, Float> entry2: entry.getValue().entrySet()){
                  desvi += entry2.getValue();
            }
            if(entry.getValue().size() > 1){
                desvi = desvi/(entry.getValue().size()-1);
            }
            float predict = desvi + mitjana; //fem la prediccio



            map_pred.put(entry.getKey(), predict);
        }
    }
//*/

// weighted original

    /**
     * @brief Calculates the prediction for the items non-rated.
     *
     * @param u_data Map with the items from the dominio.controladores.clases.atribut.user we want to predict his rate of the items non-rated.
     * \pre true
     * \post Fill map_pred with the data form u_data and the items not rated by the dominio.controladores.clases.atribut.user but that we predicted.
     */
 public static void prediccio(Map<Integer, Float> u_data) {

        Map<Integer,Float> pred = new TreeMap<Integer,Float>();
        Map<Integer,Integer> freq = new TreeMap<Integer,Integer>();
        for (int j : map_des.keySet()) { //inicialitzem els maps
            freq.put(j, 0);
            pred.put(j, 0.0f);
        }


        for (Map.Entry<Integer, Float> entry : u_data.entrySet()) { //j
            for (Map.Entry<Integer, Map<Integer, Float>> entry2 : map_des.entrySet()){ //k



                float des = 0.0f;
                int times = 0;
                if(map_des.get(entry2.getKey()).get(entry.getKey()) != null) {
                    des = map_des.get(entry2.getKey()).get(entry.getKey());

                }
                float rat = entry.getValue();

                if(map_freq.get(entry2.getKey()).get(entry.getKey()) != null) {
                    times = map_freq.get(entry2.getKey()).get(entry.getKey());

                }
                float newval = abs((des + rat) * times);


                pred.put(entry2.getKey(), pred.get(entry2.getKey())+newval);
                freq.put(entry2.getKey(), freq.get(entry2.getKey())+ times);

            }
        }


        map_pred = new TreeMap<>();
        for (Integer j : pred.keySet()) {
            if (freq.get(j)>0) {
                map_pred.put(j, pred.get(j)/freq.get(j));
            }
        }

        for (Integer j : u_data.keySet()) {
            map_pred.put(j,u_data.get(j));
        }

    }//*/
}

