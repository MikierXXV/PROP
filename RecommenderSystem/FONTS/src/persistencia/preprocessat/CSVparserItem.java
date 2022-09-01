package persistencia.preprocessat;



import java.io.*;
import java.util.*;

import static java.lang.System.out;


/**
 * @class CSVparserItem
 * @brief Implements the lecture and the proces of data of the csv
 * @author Miguel
 */

public class CSVparserItem {

    private Integer numCols, numRows;
    private String path;
    private List<List<String>> content;
    private List<String> header;
    private Map<Integer, List<Content>> mapRatedata;
    private List<Integer> id_Items;
    private List<String> tipus;

    /**
     * @brief Default builder.
     * @param path where is located the csv document
     * \pre the path of the file needs to be existent and the document csv needs to be from type items.csv
     *
     * \post It creates a <em>CSVparserItem</em> object with its attribute <em>path</em> with the values:
     * <em>numCols</em>  initialized as 0, <em>numRows</em>  initialized as 0, <em>content</em> empty, <em>header</em> empty,
     *  <em>mapRatedata</em> empty, <em>id_Items</em> and <em>tipus</em> empty.
     */
    public CSVparserItem(String path){
        this.path = path;
        this.numCols = 0;
        this.numRows = 0;
        this.content = new ArrayList<>();
        this.mapRatedata = new TreeMap<>();
        this.header = new ArrayList<>();
        this.id_Items = new ArrayList<>();
        this.tipus = new ArrayList<>();
    }

    /**
     * @brief Getter of the num rows
     * \pre the csv document needs to be created and read.
     * \post obtain the number of the rows form the class
     * @return the number of rows of the csv
     */
    public Integer getNumRows() {
        return numRows;
    }

    /**
     * @brief Getter of the num columns
     * \pre the csv document needs to be created and read.
     * \post obtain the number of the columns form the class
     * @return the number of columns of the csv
     */
    public Integer getNumCols() {
        return numCols;
    }

    /**
     * @brief Getter of the header
     * \pre the csv document needs to be created and read.
     * \post obtain the list of the header in the csv.
     * @return the header of the csv as a List
     */
    public List<String> getHeader() {
        return header;
    }

    /**
     * @brief Getter of the path of the location of the csv
     * \pre needs to be a existent path.
     * \post obtain the path where is located the csv.
     * @return the path as String
     */
    public String getPath() {
        return path;
    }

    /**
     * @brief Getter of the class, gets the content
     * \pre the document needs to be read
     * \post obtain the lecture of the csv ready to manipulate
     * @return the lecture of the document csv
     */
    public List<List<String>> getContent() {
        return content;
    }

    /**
     * @brief Getter of the class, gets the mapRatedata
     * \pre the document needs to be read
     * \post obtain the data set manipulated to make easy the operation in algorithms
     * @return set of data structured
     */
    public Map<Integer, List<Content>> getMapRatedata() {
        return mapRatedata;
    }

    /**
     * @brief Getter of the class, gets the id_items
     * \pre the document needs to be read
     * \post obtain the list ids items
     * @return obtain the list ids items
     */
    public List<Integer> getId_Items() {
        return id_Items;
    }

    /**
     * @brief Getter of the class, gets the set of the type pf the header
     * \pre needs to have a mapRatedata to obtain the set of types
     * \post obtain the set of types of the header from the csv
     * @return the array list of ths different types that each column is composed of the csv
     */
    public List<String> getTipus() {
        return tipus;
    }

    /**
     * @brief Setter of the class, modified the number of rows
     * \pre true
     * \post modify the attribute numRows of the class
     * @param numRows, new size of the number of rows
     */
    public void setNumRows(Integer numRows) {
        this.numRows = numRows;
    }

    /**
     * @brief Setter of the class, modified the number of columns
     * \pre true
     * \post modify the attribute numCols of the class
     * @param numCols, new size of the number of columns
     */
    public void setNumCols(Integer numCols) {
        this.numCols = numCols;
    }

    /**
     * @brief Setter of the class, modified the elements of the header
     * \pre true
     * \post modify the attribute header of the class
     * @param header, header of the csv as a list
     */
    public void setHeader(List<String> header) {
        this.header = header;
    }

    /**
     * @brief Setter of the class, modified the path where is located ths csv
     * \pre the path needs to be corresponded with the location file
     * \post modify the attribute path, changing to a new document.
     * @param path, where the document is located, as string
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @brief Setter of the class, gets the dominio.controladores.clases.atribut.content
     * \pre true
     * \post modify the attribute form the class, changing the lecture.
     * @param content, lecture of the csv document to attribute
     */
    public void setContent(List<List<String>> content) {
        this.content = content;
    }

    /**
     * @brief Setter of the class, gets the mapRatedata
     * \pre true
     * \post modify the attribute mapRatedata form the class.
     * @param mapRatedata, set of data to redefine a previous one
     */
    public void setMapRatedata(Map<Integer, List<Content>> mapRatedata) {
        this.mapRatedata = mapRatedata;
    }

    /**
     * @brief Setter of the class, set the id of the items
     * \pre true
     * \post modify the attribute id_Items
     * @param id_Items, set of data to redefine a previous one
     */
    public void setId_Items(List<Integer> id_Items) {
        this.id_Items = id_Items;
    }

    /**
     * @brief Setter of the class, set the types of the header
     * \pre true
     * \post modify the attribute tipus form the class.
     * @param tipus, set of data to redefine a previous one
     */
    public void setTipus(List<String> tipus) {
        this.tipus = tipus;
    }

    /**
     * @brief Get the header of the csv.
     * \pre needs a previous lecture of the csv
     * \post obtain the set of ids of the document.
     * @param path where the document csv is located
     */
    public void obtainHeader(String path){
        FileInputStream fis;
        try {
            fis = new FileInputStream(this.path);
            Scanner sc = new Scanner(fis);
            String h = sc.nextLine();
            header = Arrays.asList(h.split(","));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief show is exists a dtereminate item in the csv
     * @param idItem to look for in the csv
     * \pre needs a csv with a list of the items to read.
     * \post obtain the position of the id dominio.controladores.clases.atribut.item in a list.
     * @return the position where are located the id in the header, -1 otherwise.
     */
    public boolean exsistitemID(int idItem){
        return id_Items.contains(idItem);
    }

    /**
     * @brief obtains the position where is located the id column in csv
     * @param header of the csv
     * \pre needs a header to read.
     * \post obtain the position of the id in the header list.
     * @return the position where are located the id in the header, -1 otherwise.
     */
    public Integer obten_id_header(List<String> header){
        if (header.contains("id")){
            return header.indexOf("id");
        }
        return -1;
    }

    /**
     * @brief Read the content of the csv into memory.
     * \pre needs a document csv to read
     * \post obtains the lecture of the document manipulated into the content.
     */
    public void readLoadItem(){
        FileInputStream fis;
        try {
            fis = new FileInputStream(this.path);
            Scanner sc = new Scanner(fis);
            obtainHeader(this.path);
            sc.nextLine(); //without header
            //For each line
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                List<String> splitContent = new ArrayList<>(Arrays.asList(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)));
                if (splitContent.size() < header.size()){
                    String res = splitContent.get(0);
                    for (int i = 1; i < splitContent.size(); ++i){
                        String s = splitContent.get(i);
                        res += s;
                    }
                    line = sc.nextLine();
                    res += line;
                    splitContent = new ArrayList<>(Arrays.asList(res.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)));
                }
                content.add(splitContent);
                //Update cols&rows
                this.numRows += 1;
                this.numCols = Math.max(splitContent.size(), numCols);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readLoadItem1(){
        FileInputStream fis;
        try {
            fis = new FileInputStream(this.path);
            Scanner sc = new Scanner(fis);
            obtainHeader(this.path);
            sc.nextLine(); //without header
            //For each line
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                List<String> splitContent = new ArrayList<>(Arrays.asList(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)));
                content.add(splitContent);
                //Update cols&rows
                this.numRows += 1;
                this.numCols = Math.max(splitContent.size(), numCols);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Change a String value to an Integer one
     * \pre true
     * \post change the value of the string to a integer.
     * @param s String to obtain the corresponding value
     * @return string s converted to integer
     */
    public  Integer String_to_Int(String s){
        return Integer.parseInt(s);
    }


    /**
     * @brief Change a String value to a Double one
     * \pre true
     * \post change the value of the string to a float.
     * @param s String to obtain the corresponding value
     * @return string s converted to double
     */
    public Double String_to_Double(String s){
        return Double.parseDouble(s);
    }


    /**
     * @brief Lecture of the List<List<String>> rows
     * \pre needs to have a dominio.controladores.clases.atribut.content of the lecture
     * \post obtain a row of the dominio.controladores.clases.atribut.content read.
     * @param i number of the row to obtain
     */
    public String getRow(int i) {
        return String.valueOf(this.content.get(i));
    }



    /**
     * @brief obtains the list of the types form each item in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the types form each column of the differents items in the csv
     * @return list of list of strings corresponding to the type of each element of the item.
     */
    public List<List<String>> obtentipus(){
        List<List<String>>  res = new ArrayList<>();
        for (Map.Entry<Integer, List<Content>> entry : mapRatedata.entrySet()) {
            List<Content> l = entry.getValue();
            List<String> aux = new ArrayList<>();
            for (Content c : l){
                String s = c.getTag();
                aux.add(s);
            }
            res.add(aux);
        }
        return res;
    }


    /**
     * @brief obtains the list of the integers form each item in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the integers form each column of the differents items in the csv
     * @return list of list of integers corresponding to each element of the item is an integer and it value.
     */
    public List<List<Integer>> obtenints(){
        List<List<Integer>>  res = new ArrayList<>();
        for (Map.Entry<Integer, List<Content>> entry : mapRatedata.entrySet()) {
            List<Content> l = entry.getValue();
            List<Integer> aux = new ArrayList<>();
            for (Content c : l){
                Integer i = c.getTag_numi();
                aux.add(i);
            }
            res.add(aux);
        }
        return res;
    }

    /**
     * @brief obtains the list of the doubles form each item in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the doubles form each column of the differents items in the csv
     * @return list of list of doubles corresponding to each element of the item is a double and it value.
     */
    public List<List<Double>> obtendoubles(){
        List<List<Double>>  res = new ArrayList<>();
        for (Map.Entry<Integer, List<Content>> entry : mapRatedata.entrySet()) {
            List<Content> l = entry.getValue();
            List<Double> aux = new ArrayList<>();
            for (Content c : l){
                Double d = c.getTag_numd();
                aux.add(d);
            }
            res.add(aux);
        }
        return res;
    }

    /**
     * @brief obtains the list of the categorics form each element for each item in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the categorics form each column of the differents items in the csv
     * @return list of list of list of strings corresponding to each element of the item is a categoric and it value(s).
     */
    public List<List<List<String>>> obtencategorics(){
        List<List<List<String>>> res = new ArrayList<>();
        for (Map.Entry<Integer, List<Content>> entry : mapRatedata.entrySet()) {
            List<Content> l = entry.getValue();
            List<List<String>> aux = new ArrayList<>();
            for (Content c : l){
                List<String> cat = c.getCategorics();
                aux.add(cat);
            }
            res.add(aux);
        }
        return res;
    }

    /**
     * @brief Obtains the appropriate preprocess of data set for the kk-neight
     * \pre needs to have a content of the lecture
     * \post change the content to a estructure where the data is processed
     * @param rate_content data set read from the csv
     */
    public void MapItemData(List<List<String>> rate_content){
        int index = 0;
        int id_pos = obten_id_header(this.header);
        for (List<String> aux : rate_content) {
            int pos = 0;
            List<Content> newtagform = new ArrayList<>();
            for (String s : aux) {
                if (pos == id_pos){
                    id_Items.add(String_to_Int(s));
                }
                boolean act = true;
                Content t = new Content();
                if (s.equals("False")) {
                    t.setTag("b");
                    t.setTag_numi(0);
                    t.setTag_numd(-1.0);
                    t.setCategorics(null);
                    act = false;
                }
                if (s.equals("True")) {
                    t.setTag("b");
                    t.setTag_numi(1);
                    t.setTag_numd(-1.0);
                    t.setCategorics(null);
                    act = false;
                }
                Double valD = -1.0;
                boolean bd = true;
                boolean bi = true;
                try {
                    valD = String_to_Double(s);
                } catch (NumberFormatException e) {
                    bd = false;
                    bi = false;
                }
                Integer valI = -1;
                try {
                    valI = String_to_Int(s);
                } catch (NumberFormatException e) {
                    bi = false;
                }
                if (bd) {
                    t.setTag("d");
                    t.setTag_numi(valI);
                    t.setTag_numd(valD);
                    t.setCategorics(null);
                    act = false;
                }
                if (bi) {
                    t.setTag("i");
                    t.setTag_numi(valI);
                    t.setTag_numd(valD);
                    t.setCategorics(null);
                    act = false;
                }
                if (s.contains(";")){
                    if(!s.contains("\"")) {
                        t.setTag("c");
                        t.setTag_numi(valI);
                        t.setTag_numd(valD);
                        List<String> orderlist = Arrays.asList(s.split(";"));
                        Collections.sort(orderlist);
                        t.setCategorics(orderlist);
                        act = false;
                    }
                }
                if (act) t.setTag(s);
                newtagform.add(t);
                ++pos;
            }
            mapRatedata.put(index, newtagform);
            ++index;
        }
    }

    /**
     * @brief obtains the list of types of the elements in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the types form each column of the differents items in the csv and define the attribute tipus
     */
    public void listatipos(){
        if (mapRatedata.size() > 2) {
            List<Integer> res = new ArrayList<>();
            List<Content> first = mapRatedata.get(0);
            List<Integer> res1 = new ArrayList<>();
            List<Content> second = mapRatedata.get(1);
            for (Content c : first){
                String s = c.getTag();
                if (s.equals("b")) res.add(0);
                else if(s.equals("i")) res.add(1);
                else if(s.equals("d")) res.add(2);
                else if(s.equals("c")) res.add(3);
                else res.add(4);
            }
            for (Content c : second){
                String s = c.getTag();
                if (s.equals("b")) res1.add(0);
                else if(s.equals("i")) res1.add(1);
                else if(s.equals("d")) res1.add(2);
                else if(s.equals("c")) res1.add(3);
                else res1.add(4);
            }

            for (int i = 0; i < res.size(); ++i){
                int i1 = res.get(i);
                int i2 = res1.get(i);
                if (i1 != i2){
                    if ((i1 == 3 || i1 == 4) && (i2 == 3 || i2 == 4)) res.set(i, 3);
                }
            }
            List<String> s = new ArrayList<>();
            for (int i = 0; i < res.size(); ++i){
                if (res.get(i) == 0) s.add("b");
                else if (res.get(i) == 1) s.add("i");
                else if (res.get(i) == 2) s.add("d");
                else if (res.get(i) == 3) s.add("c");
                else s.add("n");
            }
            tipus = s;
        }

    }

    /**
     * @brief obtains the list of the data preprocessed form certain row in the csv
     * \pre needs a mapRatedata to read.
     * \post obtain the list of data preprocessed a certain row of the csv
     * @param IDitem item to look for preprocess data
     * @param tags list of elements to preprocess
     * @return list of content orresponding to the preprocessed data of the row.
     */
    public List<Content> linia_procesado(int IDitem, List<String> tags){
        List<Content> res = new ArrayList<>();
        for (String s : tags) {
            boolean act = true;

            Content t = new Content();
            if (s.equals("False")) {
                t.setTag("b");
                t.setTag_numi(0);
                t.setTag_numd(-1.0);
                t.setCategorics(null);
                act = false;
            }
            if (s.equals("True")) {
                t.setTag("b");
                t.setTag_numi(1);
                t.setTag_numd(-1.0);
                t.setCategorics(null);
                act = false;
            }
            Double valD = -1.0;
            boolean bd = true;
            boolean bi = true;
            try {
                valD = String_to_Double(s);
            } catch (NumberFormatException e) {
                bd = false;
                bi = false;
            }
            Integer valI = -1;
            try {
                valI = String_to_Int(s);
            } catch (NumberFormatException e) {
                bi = false;
            }
            if (bd) {
                t.setTag("d");
                t.setTag_numi(valI);
                t.setTag_numd(valD);
                t.setCategorics(null);
                act = false;
            }
            if (bi) {
                t.setTag("i");
                t.setTag_numi(valI);
                t.setTag_numd(valD);
                t.setCategorics(null);
                act = false;
            }
            if (s.contains(";")) {
                if (!s.contains("\"")) {
                    t.setTag("c");
                    t.setTag_numi(valI);
                    t.setTag_numd(valD);
                    List<String> orderlist = Arrays.asList(s.split(";"));
                    Collections.sort(orderlist);
                    t.setCategorics(orderlist);
                    act = false;
                }
            }
            if (act) t.setTag(s);
            res.add(t);
        }
        return res;
    }


    /**
     * @brief save the content in a specific directory
     * \pre needs a directory defined
     * \post create a document .csv in the specific directory
     * @param name directory where save the document
     */
    public void guardar_datos(String name) {

        //Scanner sc = new Scanner(System.in);
        //String nuevoFichero = sc.nextLine();
        //PENDIENTE NOMBRE FICHERO
        File archivo = new File("DATA/" + name + "/" + "items" + ".csv");

        try {
            FileWriter doc = new FileWriter(archivo);
            PrintWriter out = new PrintWriter(doc);
            int size_header = header.size();
            for (int i = 0; i < size_header; ++i) {
                if (i == 0) out.write(header.get(0));
                else out.write("," + header.get(i));
            }
            out.write("\n");
            for (int i = 0; i < content.size(); ++i) {
                List<String> l = content.get(i);
                boolean p = true;
                for (String s : l) {
                    if (p) {
                        out.write(s);
                        p = false;
                    } else out.write("," + s);
                }
                out.write("\n");
            }
            out.write("\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * @brief save the preprocessed data in a specific directory
     * \pre needs a directory defined
     * \post create a document .csv in the specific directory
     * @param name directory where save the document
     */
    public void guardar_datos_prepros(String name) {

        //Scanner sc = new Scanner(System.in);
        //String nuevoFichero = sc.nextLine();
        //PENDIENTE NOMBRE FICHERO
        File archivo = new File("DATA/"+ name + "/" + "items.prepro" + ".csv");

        try {
            FileWriter doc = new FileWriter(archivo);
            PrintWriter out = new PrintWriter(doc);
            int size_header = header.size();
            for (int i = 0; i < size_header; ++i) {
                if (i == 0) out.write(header.get(0));
                else out.write("," + header.get(i));
            }
            out.write("\n");
            for (Map.Entry<Integer, List<Content>> entry : mapRatedata.entrySet()) {
                Integer first = entry.getKey();
                List<Content> l = entry.getValue();
                for (int i = 0; i < l.size(); ++i){
                    Content c = l.get(i);
                    boolean primer = true;
                    out.write( c.getTag() + "," + String.valueOf(c.getTag_numi()) + "," +String.valueOf(c.getTag_numd()) + ',');
                    if (c.getCategorics() != null){
                        List<String> li = c.getCategorics();
                        for (int j = 0; j < li.size(); ++j){
                            if (j == 0)  out.write(li.get(j));
                            else out.write(";" + li.get(j));
                        }
                    }
                    else if (c.getCategorics() == null) out.write( "null");
                    if (primer) primer = false;
                    if (!primer) out.write(",");
                }
                out.write( "\n");
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * @brief reload the preprocessed data from a specific directory
     * \pre needs a directory defined
     * \post reload a document .csv from a specific directory
     * @param path directory where reload the document
     */
    public void reload_map_preporcess(String path){
        FileInputStream fis;
        try {
            fis = new FileInputStream(path);
            Scanner sc = new Scanner(fis);
            obtainHeader(path);
            sc.nextLine();  // without header
            Map<Integer, List<Content>> m = new TreeMap<>();
            //For each line
            int filas = 0;
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                List<String> splitContent = Arrays.asList(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1));
                List<Content> aux = new ArrayList<>();
                int size = splitContent.size();
                int i0 = 0;
                int i1 = 1;
                int i2 = 2;
                int i3 = 3;
                while(i3 < size){
                    String tag = splitContent.get(i0);
                    String tagi = splitContent.get(i1);
                    String tagd = splitContent.get(i2);
                    String categorics = splitContent.get(i3);
                    Content c = new Content();
                    c.setTag(tag);
                    if (!tagi.equals("null")) c.setTag_numi(String_to_Int(tagi));
                    if (!tagd.equals("null")) c.setTag_numd(String_to_Double(tagd));
                    /*float f = Float.parseFloat(tagd);
                    double tagdd = (double) f;*/
                    if (categorics.contains(";")){
                        List<String> cat = Arrays.asList(categorics.split(";"));
                        c.setCategorics(cat);
                    }
                    aux.add(c);
                    i0 += 4;
                    i1 += 4;
                    i2 += 4;
                    i3 += 4;
                }
                m.put(filas, aux);
                ++filas;
            }
            this.mapRatedata = m;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Add a new item into the csv
     * \pre needs current csv of items
     * \post added a new item to the csv
     * @param ID of the item to add
     * @param tags list of tags that compose the items
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public boolean addItemCSV(int ID, List<String> tags){
        ///ATENCION FORMATO CONTENT !!!!!
        if (id_Items.contains(ID)) return false;
        else {
            //incremento fila
            //int rows = numRows;
            numRows += 1;
            //numRows = rows;

            //Añadir los item y respectivos tags
            //List<List<String>> l = content;
            //trato datos para crear lista a inserir
            List<String> res = new ArrayList<>();
            //List<String> head = header;
            int posid = header.indexOf("id");
            for (int i = 0; i < tags.size(); ++i){
                if (i == posid){
                    res.add(String.valueOf(ID));
                    String s = tags.get(i);
                    res.add(s);
                }
                else res.add(tags.get(i));
            }
            content.add(res);

            //actualizo el nuevo
            //content = l;

            //PREPROCESADO
            //Map<Integer, List<Content>> map = mapRatedata;
            List<Content> cont = linia_procesado(ID, res);
            mapRatedata.put(numRows-1, cont);
            //mapRatedata = map;

            //ADDItem a la lista de items
            id_Items.add(ID);
            //id_Items = iditems;
        }
        return true;
    }

    /**
     * @brief Complete a restructure of a mapRatedata
     * \pre needs a mapRatedata defined.
     * \post restructure the mapRatedate orederd corrctly from a previous remove
     * @param map of the item to add
     * @return a boolean, if its true the action has been complited successfully, otherwise not completed the action
     */
    public Map<Integer, List<Content>> setMapRate(Map<Integer, List<Content>> map){
        int cont = 0;
        Map<Integer, List<Content>> aux = new TreeMap<>();
        for (Map.Entry<Integer, List<Content>> entry : map.entrySet()) {
            List<Content> l = entry.getValue();
            aux.put(cont, l);
            ++cont;
        }
        return aux;
    }

    /**
     * @brief Delete an item from a current csv
     * \pre needs current csv of items
     * \post deleted an item to the csv
     * @param ID of the item to delete
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public boolean delItemCSV(int ID) {
        ///ATENCION FORMATO CONTENT !!!!!
        if (!id_Items.contains(ID)) return false;
        else {
            //Posicion de id del item
            int pos = id_Items.indexOf(ID);

            //realizo el delete
            //List<List<String>> l = content;
            content.remove(pos);
            //content = l;

            mapRatedata.remove(pos);
            Map<Integer, List<Content>> aux = setMapRate(mapRatedata);
            mapRatedata = aux;
            //mapRatedata = aux;

            //actualizo filas
            //int rows = numRows;
            numRows -= 1;
            //numRows = rows;

            //actualizo lista id items
            id_Items.remove(pos);
            //id_Items = iditems;

        }
        return true;
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
    public boolean modTagCSV(int IDitem, String atribute, String newtag){
        //List<Integer> iditems = id_Items;
        //List<String> head = header;
        if (!id_Items.contains(IDitem)) return false;
        else if(!header.contains(atribute)) return false;
        else{
            //obtener posicion del atributo en el header y pos del item en la lista de items
            int poscol = header.indexOf(atribute);
            int posrow = id_Items.indexOf(IDitem);

            //Modifico tag pertinente del content
            //List<List<String>> l = content;
            List<String> aux = content.get(posrow);
            aux.set(poscol, newtag);
            content.set(posrow, aux);
            //content = l;

            //preporcesado datos del nuevo tag
            //Map<Integer, List<Content>> map = mapRatedata;
            List<Content> cont = linia_procesado(IDitem, aux);
            mapRatedata.remove(posrow);
            mapRatedata.put(posrow, cont);
            //mapRatedata = map;
        }
        return true;
    }

    /**
     * @brief Delete the tag from an item from the csv
     * \pre needs current csv of items
     * \post delete the tag from an item of the csv
     * @param IDitem of the item to modify
     * @param atribute column where is located the tag to delete
     * @return a boolean, if its true the action has been completed successfully, otherwise not completed the action
     */
    public boolean delTagCSV(int IDitem, String atribute){
        //List<Integer> iditems = id_Items;
        //List<String> head = header;
        if (!id_Items.contains(IDitem)) return false;
        else if(!header.contains(atribute)) return false;
        else{
            int poscol = header.indexOf(atribute);
            int posrow = id_Items.indexOf(IDitem);

            //List<List<String>> l = content;
            List<String> aux = content.get(posrow);
            aux.set(poscol, "null");
            content.set(posrow, aux);
            //content = l;

            //PREPROCESADO lo ponemos a null a dicho tag
            //Map<Integer, List<Content>> map = mapRatedata;
            List<Content> cont = linia_procesado(IDitem, aux);
            mapRatedata.remove(posrow);
            mapRatedata.put(posrow, cont);
            //mapRatedata = map;
        }
        return true;
    }


    /*public static void main(String[] args) {
        CSVparserItem CSVItem = new CSVparserItem("/home/miguel/PROP/Project/subgrup-prop2-3/DATA/attempt20-01-2022-18:36:06/items.csv");
        CSVItem.readLoadItem1();
        CSVItem.MapItemData(CSVItem.getContent());
        CSVItem.listatipos();
        /*int id = 123;
        List<String> tags = new ArrayList<>();
        tags.add("Cowboy bebop");
        tags.add("Buenissima serie");
        tags.add("Sci-Fi;Action;Adeventure");
        tags.add("2021-12-18");
        tags.add("14");
        tags.add("50000");
        tags.add("100");
        tags.add("23");
        tags.add("9.07");
        tags.add("link1imagen.jpg");
        tags.add("link2web");
        boolean b1 = CSVItem.addItemCSV(id, tags);
        //boolean b2 = CSVItem.delItemCSV(5114);
        out.println("hola");
    }*/

}
