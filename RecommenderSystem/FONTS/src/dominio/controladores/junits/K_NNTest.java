package dominio.controladores.junits;


import dominio.clases.algorithm.contentbasedflitering.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class K_NNTest {
    /*private static BufferedReader buffer;

    public static Map<Integer, Map<Integer, Float>> read_map_rate() throws IOException {
        //Number of users
        String line = buffer.readLine();
        int num_users = Integer.parseInt(line);

        Map<Integer, Map<Integer, Float>> opinions = new TreeMap<>();

        for (int i = 0; i < num_users; ++i) {
            //User ID
            line = buffer.readLine();
            int userID = Integer.parseInt(line);
            opinions.put(userID, new TreeMap<Integer, Float>());
            //Number of items rated by that user
            line = buffer.readLine();
            int numItems = Integer.parseInt(line);
            for (int j = 0; j < numItems; ++j) {
                //Item ID
                String line2 = buffer.readLine();
                int itemID = Integer.parseInt(line2);
                //Rating that user gave to that item
                line2 = buffer.readLine();
                float itemVal = Float.parseFloat(line2);
                opinions.get(userID).put(itemID, itemVal);
            }
        }
        return opinions;
    }

    @org.junit.Test
    public void TestFindDistances() throws IOException {
        Map<Integer, List<Content>> map = new TreeMap<>();
        buffer = new BufferedReader(new FileReader("dominio/controladores/junits/JocDeProves_KNN.txt"));
        String line;
        //Number of items declared
        line = buffer.readLine();
        int num_items = Integer.parseInt(line);
        //Number of tags per item
        line = buffer.readLine();
        int num_tags = Integer.parseInt(line);
        //Read all items and all tags
        for (int i = 0; i < num_items; ++i) {
            List<Content> lista = new ArrayList<>();
            for (int j = 0; j < num_tags; ++j) {
                line = buffer.readLine();
                String[] values = line.split(" ");
                int mida = values.length;

                String tag = values[0];
                int integer = Integer.parseInt(values[1]);
                double decimal = Double.parseDouble(values[2]);
                List<String> sublist = null;
                if (mida > 3) sublist = new ArrayList<>(Arrays.asList(values).subList(3, mida));

                Content content = new Content(tag, integer, decimal, sublist);
                lista.add(content);
            }
            map.put(i, lista);
        }
        Map<Integer, Map<Integer, Float>> map_rate = read_map_rate();
        //List with real id's for the items (in this example, real id's are equal to id's used inside program)
        List<Integer> id_reals = new ArrayList<>();
        for (int i = 0; i < num_items; ++i) id_reals.add(i);
        //Initialize distances between all items
        K_NN taula = new K_NN(map_rate,map_rate,id_reals);
        taula.initSimilarityTable(map);

        double similarity_0_1 = taula.getSimilarity(0,1);
        double similarity_1_2 = taula.getSimilarity(1,2);
        double similarity_0_4 = taula.getSimilarity(0,4);
        double similarity_2_3 = taula.getSimilarity(2,3);

        double expected_0_1 = 0.33826;
        double expected_1_2 = 0.5736;
        double expected_0_4 = 0.3878;
        double expected_2_3 = 0.22144;

        double delta = 0.001;
        assertEquals(expected_0_1,similarity_0_1,delta);
        assertEquals(expected_1_2,similarity_1_2,delta);
        assertEquals(expected_0_4,similarity_0_4,delta);
        assertEquals(expected_2_3,similarity_2_3,delta);
    }*/
}