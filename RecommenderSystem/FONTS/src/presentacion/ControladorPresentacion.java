package presentacion;

import dominio.controladores.*;
import java.util.List;

/**
 *
 * @author Oriol Martí Jariod
 */


public class ControladorPresentacion {
    private ControladorDominio CD;
    private static VistaPrincipal v;

    public void run(){
        crearVista();
        v.hacerVisible();
    }

    public ControladorPresentacion(){
        CD = new ControladorDominio();
    }

    public void crearVista(){
        v = new VistaPrincipal(this);
    }
    public void inicializar(String path){
        CD.inicializar(path);
    }


    public List<Integer> list_user_recommana(boolean e){
        if(e)return CD.list_user_known();
        else return CD.list_user_rating();
        //retorna una llista amb tots els id users
    }

    public List<Integer> list_user_gestionuser(){

        return CD.list_user_rating();
        //retorna una llista amb tots els id users
    }

    public List<Integer> list_item(){
        return CD.list_item();
        //retorna una llista amb tots els id items
    }


    public boolean addUser(int ID){
        return CD.addUser(ID);
    }


    public boolean deleteUser(int ID){
        return CD.delUser(ID);
    }

    public void addItem(int ID, List<String> tags){
        CD.addItem(ID, tags);
    }

    public boolean exists(int idItem){
        return CD.exists(idItem);
    }

     public boolean deleteItem(int ID){
        return CD.delItem(ID);
    }


    public boolean addRating(int ID_User,int IDitem, float valor){
        return CD.modRating(ID_User, IDitem, valor);
    }

    public boolean deleteRating(int ID_actual,int  IDitem){
        return CD.delRating(ID_actual, IDitem);
    }

    public List<String> tag_list() {
        return CD.tag_list();
    }

    public boolean modifyTag(int IDitem,String atribut, String tag){
        return CD.modTag(IDitem, atribut, tag);
    }

    public boolean delTag(int IDitem, String atribut){
        return CD.delTag(IDitem, atribut);
    }


    public void recommendCF(int k, int userID, boolean eval){
        CD.recommendCF(k, userID, eval);
    }

    public void recommendCBF(int k, int userID, boolean eval){
        CD.recommendCBF(k, userID, eval);
    }

    public void recommendH(int k, int userID, boolean eval){
        CD.recommendH(k, userID, eval);
    }


    public List<Integer> list_itemREC() {
        return CD.list_itemREC();
    }

    public List<Float> list_valREC() {
        return CD.list_valREC();
    }

    public Float new_DCG(){
        return CD.new_DCG();
    }

    public List<Float> list_valSavedREC(int IDuser, int alg, String Date) {
        return CD.list_valSavedREC(IDuser, alg, Date);
    }

    public List<Integer> list_itemSavedREC(int IDuser, int alg, String Date) {
        return CD.list_itemSavedREC(IDuser, alg, Date);
    }

    public List<Integer> get_IDuser_rec(){
        return CD.get_IDuser_rec();
    }

    public List<Integer> get_alg_rec(){
        return CD.get_alg_rec();
    }

    public List<String> get_dates_rec(){
        return CD.get_dates_rec();
    }

    public void saveRec() {
        CD.saveRec();
    }

    public List<String> tag_list_header() {
        return CD.tag_list();
        // contingut tags
    }
    public List<String> types_header() {
        return CD.types_header();
        //tipus tag
    }

    public void guardarCambios() {
        CD.guardarCambios();
    }
}
