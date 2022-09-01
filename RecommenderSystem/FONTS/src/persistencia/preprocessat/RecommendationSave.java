package persistencia.preprocessat;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;

/**
 * @class RecommendationSave
 * @brief Implements structures to save the recommendations of the users
 * @author Miguel
 */


public class RecommendationSave {

    private List<Integer> id_user;
    private List<List<Integer>> idItems;
    private List<List<Float>> values;
    private List<Integer> algorithm;
    private List<String> dates;

    /**
     * @brief Default builder.
     * \pre true
     * \post It creates a <em>RecommendationSave</em> object with its values:
     * <em>id_user</em>  empty, <em>idItems</em>  empty, <em>values</em> empty, <em>algorithm</em> empty,
     * <em>dates</em> empty
     */
    public RecommendationSave(){
        this.id_user = new ArrayList<>();
        this.idItems = new ArrayList<>();
        this.values = new ArrayList<>();
        this.algorithm = new ArrayList<>();
        this.dates = new ArrayList<>();
    }

    /**
     * @brief Getter of the ids of the users recommended
     * \pre needs a RecommendationSave initialized
     * \post obtain the set of users
     * @return the list of integers of the id users recommended
     */
    public List<Integer> getId_user() {
        return id_user;
    }

    /**
     * @brief Getter of the items for all user recommended
     * \pre needs a RecommendationSave initialized
     * \post obtain the set of items
     * @return the list of list of integers (items) of all user recommended
     */
    public List<List<Integer>> getIdItems() {
        return idItems;
    }

    /**
     * @brief Getter of the rates for all users recommended
     * \pre needs a RecommendationSave initialized
     * \post obtain the set of rates
     * @return the list of list of float (rates) of all user recommended
     */
    public List<List<Float>> getValues() {
        return values;
    }

    /**
     * @brief Getter of the algorithm used for all users recommended
     * \pre needs a RecommendationSave initialized
     * \post obtain the set of algorithms
     * @return the list of integers (algorithms) of all users recommended
     */
    public List<Integer> getAlgorithm() {
        return algorithm;
    }

    /**
     * @brief Getter of the dates registered for all users recommended
     * \pre needs a RecommendationSave initialized
     * \post obtain the set of dates registered
     * @return the list of strings (dates registered) of all users recommended
     */
    public List<String> getDates() {
        return dates;
    }

    /**
     * @brief Setter of the class, modified the set of ids users
     * \pre true
     * \post modify the attribute id_user of the class
     * @param id_user, new set to introduce
     */
    public void setId_user(List<Integer> id_user) {
        this.id_user = id_user;
    }

    /**
     * @brief Setter of the class, modified the set of ids items
     * \pre true
     * \post modify the attribute idItems of the class
     * @param idItems, new set to introduce
     */
    public void setIdItems(List<List<Integer>> idItems) {
        this.idItems = idItems;
    }

    /**
     * @brief Setter of the class, modified the set of values
     * \pre true
     * \post modify the attribute values of the class
     * @param values, new set to introduce
     */
    public void setValues(List<List<Float>> values) {
        this.values = values;
    }

    /**
     * @brief Setter of the class, modified the set of algorithm
     * \pre true
     * \post modify the attribute id_user of the class
     * @param algorithm, new set to introduce
     */
    public void setAlgorithm(List<Integer> algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * @brief Setter of the class, modified the set of dates
     * \pre true
     * \post modify the attribute dates of the class
     * @param dates, new set to introduce
     */
    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    /*public void addUserL (Integer user){
        this.id_user.add(user);
    }

    public void addlistiditem(List<Integer> iditems){
        this.idItems.add(iditems);
    }

    public void addlistvalues(List<Float> val){
        this.values.add(val);
    }

    public void addalgorithmlist(Integer i){
        this.algorithm.add(i);
    }

    public void adddatalist(String data){
        this.dates.add(data);
    }*/

    /**
     * @brief Set a date type to string
     * \pre needs a correct format date
     * \post obtain the date converted to string
     * @param data, date to convert
     * @return string format of the date
     */
    public String datasystem(Date data){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        String dateToStr = dateFormat.format(data);
        return dateToStr;
    }

    /**
     * @brief Set an integer algorithm to string type
     * \pre needs an algorithm integer (0-2);
     * \post obtain the algorithm integer converted to string
     * @param i algorithm integer type
     * @return string format of the algorithm
     */
    public String selectalgorithm(Integer i){
        if (i.equals(0)) return "KNN";
        else if (i.equals(1)) return "CollaborativeF";
        else return "Hybrid";
    }

    /**
     * @brief Set an algorithm to integer type
     * \pre needs an algorithm name;
     * \post obtain the algorithm integer
     * @param s algorithm
     * @return integer format of the algorithm
     */
    public Integer selectalgorithmS(String s){
        if (s.equals("KNN")) return 0;
        else if (s.equals("CollaborativeF")) return 1;
        else return 2;
    }

    /**
     * @brief obtain the items for an especific recommendation user
     * \pre needs a RecommendationSave completed
     * \post obtain the set of items for an especific recommendation
     * @param ID of the user
     * @param alg integer algorithm type
     * @param data date in string format
     * @return list of integers (item ids) of the recommendation specified before
     */
    public List<Integer> valuesitems(int ID, int alg, String data){
        int pos = 0;
        for (int i = 0; i < id_user.size(); ++i){
            if(id_user.get(i) == ID){
                if(algorithm.get(i) == alg){
                    if (dates.get(i).equals(data)) pos = i;
                }
            }
        }
        return idItems.get(pos);
    }

    /**
     * @brief obtain the items for an especific recommendation user
     * \pre needs a RecommendationSave completed
     * \post obtain the set of items for an especific recommendation
     * @param ID of the user
     * @param alg integer algorithm type
     * @param data date in string format
     * @return list of floats (rates) of the recommendation specified before
     */
    public List<Float> valuesrates(int ID, int alg, String data){
        int pos = 0;
        for (int i = 0; i < id_user.size(); ++i){
            if(id_user.get(i) == ID){
                if(algorithm.get(i) == alg){
                    if (dates.get(i).equals(data)) pos = i;
                }
            }
        }
        return values.get(pos);
    }

    /**
     * @brief Add a recommendation to the Recommendation save
     * \pre true
     * \post obtain the set of the recommendation and add them to the RecommendSave attributes
     * @param IDuser of the user
     * @param alg integer algorithm type
     * @param valors date in string format
     */
    public void carregaAtributs(int IDuser, int alg,  List<Integer> IDitems,List<Float> valors){
        id_user.add(IDuser);
        algorithm.add(alg);
        idItems.add(IDitems);
        values.add(valors);
        Date d = new Date();
        String s = datasystem(d);
        dates.add(s);
    }

    /**
     * @brief save the recommendation in a specific diretory
     * \pre needs a directory defined
     * \post create a document .csv in the specific directory
     * @param name of the directory where save the document
     */
    public  void saveRecommendation(String name){
        File archivo = new File("DATA/" + name + "/" + "Recommedation" + ".csv");
        try {
            FileWriter doc = new FileWriter(archivo);
            PrintWriter out = new PrintWriter(doc);
            Date data = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
            String dateToStr = dateFormat.format(data);
            String dat = datasystem(data);
            for (int i = 0; i < id_user.size(); ++i){
                out.write("USERID:" + String.valueOf(id_user.get(i)) + "," + dateToStr + "," + "ALGORITHM:" + selectalgorithm(algorithm.get(i)) + "\n");
                List<Integer> li = idItems.get(i);
                List<Float> lf = values.get(i);
                out.write("ITEMS:" +  "," + "RATES:" + "\n");
                for(int j = 0; j < li.size() && j < lf.size(); ++j){
                    out.write( String.valueOf(li.get(j)) + "," + String.valueOf(lf.get(j)) + "\n");
                }
                out.write( "/--/" + "\n");
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * @brief Read preprocessed dataset of recommendation.
     * @param path where is located the dataset
     * \pre needs a document csv to read
     * \post obtain preprocessed data into the pertinent map.
     */
    public void reloadRecommendation(String path){
        FileInputStream fis;
        try {
            fis = new FileInputStream(path + "Recommedation" + ".csv");
            Scanner sc = new Scanner(fis);
            //For each line
            List<Integer> idIt = new ArrayList<>();
            List<Float> val = new ArrayList<>();
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                boolean header = line.contains("USERID");
                boolean patro = line.contains("/--/");
                if (patro){
                    idItems.add(idIt);
                    values.add(val);
                    idIt = new ArrayList<>();
                    val = new ArrayList<>();
                }
                if (line.contains("ITEMS:")) line = sc.nextLine();
                List<String> splitContent = Arrays.asList(line.split(","));
                if (header){
                    String s = splitContent.get(0);
                    String n = "";
                    char[] cadena = s.toCharArray();
                    for (int j = 0; j < cadena.length; ++j){
                        if (Character.isDigit(cadena[j])) n += cadena[j];
                    }
                    id_user.add(parseInt(n));

                    String s1 = splitContent.get(1);
                    dates.add(s1);

                    String s2 = splitContent.get(2);
                    if (s2.contains("KNN")) algorithm.add(0);
                    else if (s2.contains("Colla")) algorithm.add(1);
                    else algorithm.add(2);

                    //line = sc.nextLine();
                }
                else if(!header && !patro){
                    idIt.add(parseInt(splitContent.get(0)));
                    val.add(parseFloat(splitContent.get(1)));
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

   /* public static void main(String[] args) {
        RecommendationSave r = new RecommendationSave();
        int user = 234;
        int user1 = 123;
        int user2 = 567;
        r.addUserL(user);
        r.addUserL(user1);
        r.addUserL(user2);

        List<Integer> ints = new ArrayList<>(3);
        List<Integer> ints1 = new ArrayList<>(3);
        List<Integer> ints2 = new ArrayList<>(3);

        ints.add(1);
        ints.add(2);
        ints.add(3);

        ints1.add(7);
        ints1.add(5);
        ints1.add(9);

        ints2.add(8);
        ints2.add(6);
        ints2.add(4);

        r.addlistiditem(ints);
        r.addlistiditem(ints1);
        r.addlistiditem(ints2);

        List<Float> floats = new ArrayList<>(3);
        List<Float> floats1 = new ArrayList<>(3);
        List<Float> floats2 = new ArrayList<>(3);

        floats.add(5.0F);
        floats.add(3.0F);
        floats.add(6.0F);

        floats1.add(9.0F);
        floats1.add(1.0F);
        floats1.add(3.5F);

        floats2.add(2.5F);
        floats2.add(5.5F);
        floats2.add(0.0F);

        r.addlistvalues(floats);
        r.addlistvalues(floats1);
        r.addlistvalues(floats2);

        r.addalgorithmlist(0);
        r.addalgorithmlist(1);
        r.addalgorithmlist(2);

        r.adddatalist("18-12-2021");
        r.adddatalist("19-12-2021");
        r.adddatalist("20-12-2021");

        r.saveRecommendation();
        out.println("hola");
        RecommendationSave s = new RecommendationSave();
        s.reloadRecommendation("DATA/");
        out.println("adios");
    }*/
}
