package persistencia;

import persistencia.preprocessat.CSVparserItem;
import persistencia.preprocessat.CSVparserRate;
import persistencia.preprocessat.RecommendationSave;
import persistencia.preprocessat.UserList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @class ControladorPersistencia
 * @brief Controller of the persistence
 * @author Manel
 * @author Miguel
 */

public class ControladorPersistencia {

    CSVparserItem CSVItem;
    CSVparserRate CSVRate;
    CSVparserRate CSVKnown;
    CSVparserRate CSVUnknown;
    RecommendationSave Recomm;
    UserList UserListKnown;
    UserList UserListRating;
    String directory;

    /**
     * @brief Default builder.
     */
    public ControladorPersistencia(){}


    /**
     * @brief initialize the lecture and preprocess of the documents
     * @param path where are located the csvs documents
     * \pre the path of the file needs to be existent and the document csv needs to be from type items.csv
     *
     * \post It creates a <em>CSVparserItem</em> object with its attribute <em>path</em> with the values:
     * <em>numCols</em>  initialized , <em>numRows</em>  initialized, <em>content</em> initialized, <em>header</em> initialized,
     * <em>mapRatedata</em> initialized, <em>id_Items</em> initialized and <em>tipus</em> initialized.
     *
     * It creates 3  <em>CSVparserRate</em> objects with their attributes <em>path</em> with their values:
     * <em>numCols</em>  initialized , <em>numRows</em>  initialized, <em>content</em> initialized, <em>mapRate</em> initialized and
     * <em>header</em> initialized.
     *
     * It creates a <em>RecommendationSave</em> object with its values:
     * <em>id_user</em>  initialized, <em>idItems</em>  initialized, <em>values</em> initialized, <em>algorithm</em> initialized,
     * <em>dates</em> initialized.
     *
     * It creates a <em>UserList</em> object with the value <em>users</em>  empty
     */
    public void inicializar(String path){
        CSVItem = new CSVparserItem(path + "items.csv");
        CSVItem.readLoadItem();
        CSVItem.MapItemData(CSVItem.getContent());
        CSVItem.listatipos();
        CSVRate = new CSVparserRate(path + "ratings.db.csv");
        CSVRate.readLoadRate();
        CSVRate.LoadRate(CSVRate.getContent());
        CSVKnown = new CSVparserRate(path + "ratings.test.known.csv");
        CSVKnown.readLoadRate();
        CSVKnown.LoadRate(CSVKnown.getContent());
        CSVUnknown = new CSVparserRate(path + "ratings.test.unknown.csv");
        CSVUnknown.readLoadRate();
        CSVUnknown.LoadRate(CSVUnknown.getContent());
        //Crea les classes que necessitis
        UserListKnown = new UserList();
        UserListKnown.initializeUsers(CSVKnown.getMapRate());
        UserListRating = new UserList();
        UserListRating.initializeUsers(CSVRate.getMapRate());
        Recomm = new RecommendationSave();
    }

    /**
     * @brief reload the content of the documents
     * @param dir_name where are located the csvs documents
     * \pre the path of the file needs to be existent and the document csv needs to be from type items.csv
     *
     * \post It creates a <em>CSVparserItem</em> object with its attribute <em>path</em> with the values:
     * <em>numCols</em>  initialized , <em>numRows</em>  initialized, <em>content</em> initialized, <em>header</em> initialized,
     * <em>mapRatedata</em> initialized, <em>id_Items</em> initialized and <em>tipus</em> initialized.
     *
     * It creates 3  <em>CSVparserRate</em> objects with their attributes <em>path</em> with their values:
     * <em>numCols</em>  initialized , <em>numRows</em>  initialized, <em>content</em> initialized, <em>mapRate</em> initialized and
     * <em>header</em> initialized.
     *
     * It creates a <em>RecommendationSave</em> object with its values:
     * <em>id_user</em>  initialized, <em>idItems</em>  initialized, <em>values</em> initialized, <em>algorithm</em> initialized,
     * <em>dates</em> initialized.
     *
     * It creates a <em>UserList</em> object with the value <em>users</em>  empty
     */
    public void iniciar_reload(String dir_name){
        CSVItem = new CSVparserItem("DATA/" + dir_name + "/items.csv");
        CSVItem.readLoadItem1();
        CSVItem.reload_map_preporcess("DATA/" + dir_name + "/items.prepro.csv");
        CSVItem.listatipos();

        CSVRate = new CSVparserRate("DATA/" + dir_name + "ratings.db.csv");
        CSVRate.readLoadRate();
        CSVRate.reload_map_preporcess("DATA/" + dir_name + "ratings.db.prepro.csv");

        CSVKnown = new CSVparserRate("DATA/" + dir_name + "ratings.test.known.csv");
        CSVKnown.readLoadRate();
        CSVKnown.reload_map_preporcess("DATA/" + dir_name + "ratings.test.known.prepro.csv");

        CSVUnknown = new CSVparserRate("DATA/" + dir_name + "ratings.test.unknown.csv");
        CSVUnknown.readLoadRate();
        CSVUnknown.reload_map_preporcess("DATA/" + dir_name + "ratings.test.unknown.prepro.csv");
        //Crea les classes que necessitis
        UserListKnown = new UserList();
        UserListKnown.initializeUsers(CSVKnown.getMapRate());
        UserListRating = new UserList();
        UserListRating.initializeUsers(CSVRate.getMapRate());
        Recomm = new RecommendationSave();
        Recomm.reloadRecommendation("DATA/" + dir_name + "/Recommendation.csv");
    }

    /**
     * @brief Add a new item into the csv
     * \pre needs current csv of items
     * \post added a new item to the csv
     * @param ID of the item to add
     * @param tags list of tags that compose the items
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean addItem(int ID, List<String> tags){
       return CSVItem.addItemCSV(ID, tags);
    }

    /**
     * @brief Delete an item from a current csv and from the others
     * \pre needs current csv of items
     * \post deleted an item to all csv
     * @param ID of the item to delete
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean delItem(int ID) {
        CSVRate.deleteitems(ID);
        CSVKnown.deleteitems(ID);
       return CSVItem.delItemCSV(ID);
    }

    /**
     * @brief Modify the tag from an item from the csv
     * \pre needs current csv of items
     * \post modify the tag from an item of the csv
     * @param IDitem of the item to modify
     * @param atribute column where is located the tag to modify
     * @param newtag to modify from a previous one
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean modTag(int IDitem, String atribute, String newtag){
        return CSVItem.modTagCSV(IDitem, atribute, newtag);
    }

    /**
     * @brief Delete the tag from an item from the csv
     * \pre needs current csv of items
     * \post delete the tag from an item of the csv
     * @param IDitem of the item to modify
     * @param atribute column where is located the tag to delete
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean delTag(int IDitem, String atribute){
        return CSVItem.delTagCSV(IDitem, atribute);
    }

    /**
     * @brief Add a user to the set of users
     * \pre needs Userlist initialized
     * \post the set user is actualized with the new id of the user
     * @param ID of the user to add
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean addUser(int ID) {
        return UserListRating.addUserList(ID);
    }

    /**
     * @brief Delete a user from a current csv
     * \pre needs current csv of items
     * \post deleted a user to the csv
     * @param ID of the user to delete
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean delUser(int ID) {
        return CSVRate.delUserCSV(ID);
    }

    /**
     * @brief Add the rate of an item from a user of the csv
     * \pre needs current csv of items
     * \post add the rate of the item corresponding to a user
     * @param IDuser of the user to realize the action
     * @param IDitem of the item to add the corresponding rate
     * @param valor rate to add
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean addRating(int IDuser, int IDitem, float valor){
        return  CSVRate.addRatingCSV(IDuser, IDitem, valor);
    }

    /**
     * @brief Modify the rate of an item from a user of the csv
     * \pre needs current csv of items
     * \post modify the rate of the item corresponding to a user
     * @param IDuser of the user to realize the action
     * @param IDitem of the item to modify the corresponding rate
     * @param new_rate to modify from a previous one
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean modRating(int IDuser, int IDitem, float new_rate) {
       return CSVRate.modRatingCSV(IDuser, IDitem, new_rate);
    }

    /**
     * @brief Delete the rate of an item from a user of the csv
     * \pre needs current csv of items
     * \post delete the rate of an item from a user of the csv
     * @param IDuser of the user within interact
     * @param IDitem of the item to delete the rate
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean delRating(int IDuser, int IDitem) {
       return CSVRate.delRatingCSV(IDuser, IDitem);
    }


    /**
     * @brief obtains the list of the users form the csv chosen
     * \pre needs csvs initialized
     * \post obtain the user set form the csv
     * @param a number of csv to choose
     * @return list of integers corresponding to the users listed in the corresponding csv .
     */
    public List<Integer> getMapRateIDusers(int a){
        if (a == 0){
            return CSVRate.obtenlistausers();
        }
        else if (a == 1){
            return CSVKnown.obtenlistausers();
        }
        else  return CSVUnknown.obtenlistausers();
    }

    /**
     * @brief obtains the set of items related to a user
     * \pre needs a mapRate to read.
     * \post obtain the items related for a corresponding user
     * @param a number of csv to choose
     * @return list of list of integers corresponding to the items for each user.
     */
    public List<List<Integer>> getMapRateIDitems(int a){
        if (a == 0){
            return CSVRate.obtenlistaitems();
        }
        else if (a == 1){
            return CSVKnown.obtenlistaitems();
        }
        else  return CSVUnknown.obtenlistaitems();
    }

    /**
     * @brief obtains the set of values related to a user given to a item
     * \pre needs a mapRate to read.
     * \post obtain the values of the items related for a corresponding user
     * @param a number of csv to choose
     * @return list of list of floats corresponding to the values of items for each user.
     */
    public List<List<Float>> getMapRateVal(int a){
        if (a == 0){
            return CSVRate.obtenlistavalues();
        }
        else if (a == 1){
            return CSVKnown.obtenlistavalues();
        }
        else  return CSVUnknown.obtenlistavalues();
    }

    /**
     * @brief Getter of the class, gets the set of id items
     * \pre needs to have a mapRatedata to obtain the set of id items
     * \post obtain the set of id items of the csv
     * @return the array list of ths different items of the csv
     */
    public List<Integer> getMapItemIDs(){
        return CSVItem.getId_Items();
    }

    /**
     * @brief obtains the list of the types form each item in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the types form each column of the differents items in the csv
     * @return list of list of strings corresponding to the type of each element of the item.
     */
    public List<List<String>> getMapTipusTags(){
        return CSVItem.obtentipus();
    }

    /**
     * @brief obtains the list of the integers form each item in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the integers form each column of the differents items in the csv
     * @return list of list of integers corresponding to each element of the item is an integer and it value.
     */
    public List<List<Integer>> getMapIntsTags(){
        return CSVItem.obtenints();
    }

    /**
     * @brief obtains the list of the doubles form each item in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the doubles form each column of the differents items in the csv
     * @return list of list of doubles corresponding to each element of the item is a double and it value.
     */
    public List<List<Double>> getMapDoublesTags(){
        return CSVItem.obtendoubles();
    }

    /**
     * @brief obtains the list of the categorics form each element for each item in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the categorics form each column of the differents items in the csv
     * @return list of list of list of strings corresponding to each element of the item is a categoric and it value(s).
     */
    public List<List<List<String>>> getMapCategoricsTags(){
        return CSVItem.obtencategorics();
    }

    /**
     * @brief gets the users rates
     * \pre the document needs to be read
     * \post obtain the set of users ratings
     * @return obtain the list users from the rate
     */
    public List<Integer> list_user_rating(){
        UserListRating.initializeUsers(CSVRate.getMapRate());
        return UserListRating.getUsers();

    }

    /**
     * @brief gets the users known
     * \pre the document needs to be read
     * \post obtain the set of users known
     * @return obtain the list users from the known
     */
    public List<Integer> list_user_known(){
        UserListKnown.initializeUsers(CSVKnown.getMapRate());
        return UserListKnown.getUsers();
    }

    /**
     * @brief  gets the id_items
     * \pre the document needs to be read
     * \post obtain the list ids items
     * @return obtain the list ids items
     */
    public List<Integer> list_item(){
        return CSVItem.getId_Items();
        //retorna una llista amb tots els id items
    }

    /**
     * @brief show is exists a dtereminate item in the csv
     * @param idItem to look for in the csv
     * \pre needs a csv with a list of the items to read.
     * \post obtain the position of the id dominio.controladores.clases.atribut.item in a list.
     * @return the position where are located the id in the header, -1 otherwise.
     */
    public boolean exists(int idItem){
        return CSVItem.exsistitemID(idItem);
    }


    /**
     * @brief Getter of the header
     * \pre the csv document needs to be created and read.
     * \post obtain the list of the header in the csv.
     * @return the header of the csv as a List
     */
    public List<String> get_header_items() {
        return CSVItem.getHeader();
    }

    /**
     * @brief obtain the items for an especific recommendation user
     * \pre needs a RecommendationSave completed
     * \post obtain the set of items for an especific recommendation
     * @param IDuser of the user
     * @param alg integer algorithm type
     * @param date date in string format
     * @return list of floats (rates) of the recommendation specified before
     */
    public List<Float> list_valSavedREC(int IDuser, int alg, String date) {
        return Recomm.valuesrates(IDuser, alg, date);
        //retorna la llista
    }

    /**
     * @brief obtain the items for an especific recommendation user
     * \pre needs a RecommendationSave completed
     * \post obtain the set of items for an especific recommendation
     * @param IDuser of the user
     * @param alg integer algorithm type
     * @param date date in string format
     * @return list of integers (item ids) of the recommendation specified before
     */
    public List<Integer> list_itemSavedREC(int IDuser, int alg, String date) {
        return Recomm.valuesitems(IDuser, alg, date);
    }

    /**
     * @brief Getter of the class, gets the set of id items
     * \pre needs to have a mapRatedata to obtain the set of id items
     * \post obtain the set of id items of the csv
     * @return the array list of ths different items of the csv
     */
    public List<Integer> get_IDuser_rec(){
        return Recomm.getId_user();
    }

    /**
     * @brief Getter of the algorithm used for all users recommended
     * \pre needs a RecommendationSave initialized
     * \post obtain the set of algorithms
     * @return the list of integers (algorithms) of all users recommended
     */
    public List<Integer> get_alg_rec(){
        return Recomm.getAlgorithm();
    }

    /**
     * @brief Getter of the dates registered for all users recommended
     * \pre needs a RecommendationSave initialized
     * \post obtain the set of dates registered
     * @return the list of strings (dates registered) of all users recommended
     */
    public List<String> get_dates_rec(){
        return Recomm.getDates();
    }

    /**
     * @brief Getter of the class, gets the set of the type pf the header
     * \pre needs to have a mapRatedata to obtain the set of types
     * \post obtain the set of types of the header from the csv
     * @return the array list of ths different types that each column is composed of the csv
     */
    public List<String> list_tipusheader(){
        return CSVItem.getTipus();
    }

    /**
     * @brief Create a new folder with csvs documents
     * \pre true
     * \post create a new folder with the attempt and the current data
     * @return create a new folder with the attempt and the current data
     */
    public boolean crear_carpeta(){
        Date data = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        String dateToStr = dateFormat.format(data);
        dateToStr.trim();
        dateToStr.replace(" ","");
        File directorio = new File("DATA/attempt"+ dateToStr );
        String s = "attempt" + dateToStr;
        s.replace(" ","");
        directory = s;
        //directorio.mkdirs();
        if(!directorio.exists()){
            directorio.mkdirs();
            CSVItem.guardar_datos(directory);
            CSVItem.guardar_datos_prepros(directory);
            CSVRate.guardar_datos(directory, "ratings.db.csv");
            CSVRate.guardar_datos_preproces(directory, "ratings.db");
            CSVKnown.guardar_datos(directory, "ratings.test.known.csv");
            CSVKnown.guardar_datos_preproces(directory, "rattings.known.prepro.csv");
            CSVUnknown.guardar_datos(directory, "ratings.test.unknown.csv");
            CSVKnown.guardar_datos_preproces(directory, "ratings.unknown.csv");
            UserListKnown.saveUsers(directory);
            UserListRating.saveUsers(directory);
            Recomm.saveRecommendation(directory);
            return true;
        }
        else return false;
    }


    /**
     * @brief reload the content from docuements csv
     * \pre true
     * \post content reload
     * @return content reload
     */
    public void reload(){
        File direct = new File("DATA/" + directory);
        if(direct.isFile()) {
            iniciar_reload(directory);
            System.out.println("END");
        }
    }

    /**
     * @brief add recommedations to the rceommendSave
     * \pre current recommendation
     * \post obtain the set of users ratings
     * @param IDuser user to add
     * @param alg alghorithem number type to add
     * @param IDitems list of items recommended
     * @param valors list of rates recommended
     * @return add recommedations to the rceommendSave
     */
    public void saveRec(int IDuser, int alg,  List<Integer> IDitems,List<Float> valors){
        Recomm.carregaAtributs(IDuser, alg, IDitems, valors);
    }
}