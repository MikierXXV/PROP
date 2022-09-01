package persistencia.preprocessat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.System.out;

/**
 * @class UserList
 * @brief Implements a list of users of the csv that have or no valoration
 * @author Miguel
 */

public class UserList {
    private List<Integer> users;

    /**
     * @brief Default builder.
     * \pre true
     * \post It creates a <em>UserList</em> object with the value <em>users</em>  empty
     */
    public UserList(){
        this.users = new ArrayList<>();
    }

    /**
     * @brief Getter of the set of users
     * \pre the set users has the corresponding data.
     * \post obtain the set of users
     * @return a set of integers with the ids of the users
     */
    public List<Integer> getUsers() {
        return users;
    }

    /**
     * @brief Setter of the class
     * \pre true
     * \post modify the attribute users of the class
     * @param users, new set of users to introduce
     */
    public void setUsers(List<Integer> users) {
        this.users = users;
    }



    /**
     * @brief take the id from the users of the mapRate and introduce them to the set of users
     * \pre needs a mapRate obtained from a lecture of a csv
     * \post the set user is actualized with the correspondings ids of the users
     * @param mapRate, content of the id of users to treat
     */
    public void initializeUsers( Map<Integer, Map<Integer, Float>> mapRate){
        for (Map.Entry<Integer, Map<Integer, Float>> entry : mapRate.entrySet()) {
            Integer first = entry.getKey();
            users.add(first);
        }
    }

    /**
     * @brief Add a user to the set of users
     * \pre needs Userlist initialized
     * \post the set user is actualized with the new id of the user
     * @param id_user of the user to add
     */
    public void addUser(int id_user){
        if (!users.contains(id_user)) users.add(id_user);
    }

    /**
     * @brief Add a user to the set of users
     * \pre needs Userlist initialized
     * \post the set user is actualized with the new id of the user
     * @param ID of the user to add
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean addUserList (int ID) {
        if (users.contains(ID)) return false;
        else users.add(ID);
        return true;
    }

    /**
     * @brief Delete a user to the set of users
     * \pre needs Userlist initialized
     * \post the set user is actualized without the id_user
     * @param id_user of the user to delete
     */
    public void delUser(int id_user){
        if (users.contains(id_user)) users.remove(id_user);
    }

    /**
     * @brief Delete a user to the set of users
     * \pre needs Userlist initialized
     * \post the set user is actualized without the id_user
     * @param ID of the user to delete
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public  boolean delUserList (int ID) {
        if (!users.contains(ID)) return false;
        else users.remove(ID);
        return true;
    }

    /**
     * @brief save the users in a specific diretory
     * \pre needs a directory defined
     * \post create a document .csv in the specific directory
     * @param name of the directory where save the document
     */
    public void saveUsers(String name){
        File archivo = new File("DATA/" + name + "/" + "UserList" + ".csv");
        try {
            FileWriter doc = new FileWriter(archivo);
            PrintWriter out = new PrintWriter(doc);
            out.write("List of Users" + "\n");
            for (int i = 0; i < users.size(); ++i){
                String salida = String.valueOf(users.get(i));
                out.write(salida + "\n");
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

}
