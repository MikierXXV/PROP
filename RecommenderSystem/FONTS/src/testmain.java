/*import java.util.*;

import dominio.clases.algorithm.collaborativefiltering.*;
import dominio.clases.algorithm.contentbasedflitering.*;
import dominio.clases.algorithm.hybrid.Hybrid;
import dominio.clases.evaluation.*;
import persistencia.preprocessat.*;
import dominio.clases.rating.Rating;
import dominio.clases.recommendation.Recommendation;

/**
 * @brief Clase ejecutable del proyecto con la que probar sus funcionalidades.
 **/

/*import static java.lang.Math.max;
import static java.lang.Math.min;

public class testmain {
    private static Scanner sc;
    private static String path_item;
    private static String path_known;
    private static String path_unknown;
    private static boolean serie = false;

    public static void computeK(Map<Integer, Map<Integer, Float>> mapRateKnown, Map<Integer, Map<Integer, Float>> mapRateUnknown, int maxItems, int userID){
        CollaborativeFiltering CFAux;
        int maxK = mapRateKnown.size()/3;
        float maxDCG = 0.0f;
        Evaluation E = new Evaluation(mapRateUnknown);
        for(int i = mapRateKnown.size()/3; i > mapRateKnown.size()/50; --i){
            float DCG = 0.0f;
            CFAux = new CollaborativeFiltering(mapRateKnown, mapRateUnknown, i);
            for(Map.Entry<Integer, Map<Integer, Float>> entry: mapRateUnknown.entrySet()){
                DCG += E.DCG(CFAux.recommend(entry.getKey(), maxItems, true));
            }
            if(DCG > maxDCG){
                maxK = i;
                maxDCG = DCG;
            }
        }

        CollaborativeFiltering CF1 = new CollaborativeFiltering(mapRateKnown, mapRateUnknown, maxK);
        System.out.println("La mejor k clusters de para valoraciones de 10 items es: " + maxK + " con DCG " + E.DCG(CF1.recommend(userID, 10, true)));
    }

    public static void makerecommendation(){
        System.out.println("Vas a querer una valoración de la recomendación?");
        System.out.println("0: NO, 1: SI");
        int valoration = sc.nextInt();
        boolean val = (valoration == 1);

        CSVparserItem CSVItem = new CSVparserItem(path_item);
        List<Integer> id_reals = CSVItem.getId_Items();
        CSVItem.readLoadItem();
        CSVItem.MapItemData(CSVItem.getContent());

        CSVparserRate CSVRate_known = new CSVparserRate(path_known);
        CSVRate_known.readLoadRate();
        CSVRate_known.LoadRate(CSVRate_known.getContent());
        Map<Integer, Map<Integer, Float>> map_rate_known = CSVRate_known.getMapRate();

        CSVparserRate CSVRate_unknown = new CSVparserRate(path_unknown);
        CSVRate_unknown.readLoadRate();
        CSVRate_unknown.LoadRate(CSVRate_unknown.getContent());
        Map<Integer, Map<Integer, Float>> map_rate_unknown = CSVRate_unknown.getMapRate();

        CollaborativeFiltering CF = new CollaborativeFiltering(map_rate_known, map_rate_unknown, max(1, map_rate_known.size() / 3));
        System.out.println("La k es " + max(1, map_rate_known.size() / 3) + " y hay " + map_rate_known.size() + " usuarios.");

        Map<Integer, List<Content>> map_rate_item = CSVItem.getMapRatedata();
        K_NN taula = new K_NN(map_rate_known, map_rate_unknown, map_rate_item, id_reals);
        taula.initSimilarityTable();

        Hybrid hybrid = new Hybrid();

        System.out.println("Introduzca el ID del user que quiere la recomendacion, por lo contrario si desea volver atras escribe -1 :");
        int userID = sc.nextInt();
        boolean existinguser = map_rate_known.containsKey(userID);

        while(!existinguser && userID != -1){
            System.out.println("Este user no existe! Introduzca ID del user que quiere la recomendacion  por lo contrario si desea volver atras escribe -1 :");
            userID = sc.nextInt();

            existinguser = map_rate_known.containsKey(userID);
        }

        if(existinguser) {
            System.out.println("Qué algoritmo de recomendación desea usar?");
            System.out.println("\t 1) Collaborative filtering (k-means + slope-one)");
            System.out.println("\t 2) Content based filtering (k-nn)");
            System.out.println("\t 3) Hybrid algorithm");
            System.out.println("\t 0) Volver atras");

            Recommendation recommendation = new Recommendation();
            int choice = sc.nextInt();

            System.out.println("Cuántos ítems quieres que te recomienden?");
            int k = sc.nextInt();
            switch (choice) {
                case 1:
                    try {
                        computeK(map_rate_known, map_rate_unknown, k, userID);
                        recommendation = CF.recommend(userID, k, val);
                        for (Rating r : recommendation.getConjunt()) {
                                System.out.println("ID item: " + r.getId() + " with expected rating " + min(10,r.getValor()));
                        }
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 2:
                    try {
                        recommendation = taula.recommend(userID,k,val);
                        for (Rating r : recommendation.getConjunt()) {
                            System.out.println("ID item: " + r.getId() + " with similarity " + r.getValor());
                        }
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 3:
                    try {
                        Recommendation r1 = taula.recommend(userID,k,val);
                        Recommendation r2 = CF.recommend(userID, k, val);
                        recommendation = hybrid.recommend(r1,r2,k);
                        for (Rating r : recommendation.getConjunt()) {
                            System.out.println("ID item: " + r.getId() + " with similarity " + r.getValor());
                        }
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println(choice);
                    break;
            }

            if (choice != 1 && choice != 2 && choice != 0 && choice != 3) {
                System.out.println("No has elegido una opcion valida.");
            } else if(val) {
                Evaluation eval = new Evaluation(map_rate_unknown);
                System.out.println("DCG de la recomendacion: " + eval.DCG(recommendation) +"\n");
            }
        }

    }

    public static void main(String[] args) {
        boolean salir = false;
        sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        System.out.println("!!IMPORTANTE!!");
        System.out.println("Antes de realizar una recomendacion debe insertar el path de los csv EXCEPTO en los casos movies y series");

        while (!salir) {
            System.out.println("Escoja una opción:");
            System.out.println("\t 0) Salir");
            System.out.println("\t 1) Introduzca el path del los csv");
            System.out.println("\t 2) movies 250");
            System.out.println("\t 3) movies 750");
            System.out.println("\t 4) series 250");
            System.out.println("\t 5) series 750");
            System.out.println("\t 6) series 2250");
            System.out.println("\t 7) Hacer recomendacion (solo en el caso que haya insertado manualmente los paths)");
            int option = sc.nextInt();
            serie = false;
            switch (option) {
                case 0:
                    salir = true;
                    break;
                case 1:
                    System.out.println("\t 1) Introduzca el path del parser tipo items.csv");
                    path_item = sc1.next();
                    System.out.println("\t 2) Introduzca el path del parser tipo ratings.known.csv");
                    path_known = sc1.next();
                    System.out.println("\t 3) Introduzca el path del parser tipo ratings.unknown.csv");
                    path_unknown = sc1.next();
                    break;
                case 2:
                    path_item = "DATA/movies250/items.csv";
                    path_known = "DATA/movies250/ratings.test.known.csv";
                    path_unknown = "DATA/movies250/ratings.test.unknown.csv";
                    try {
                        makerecommendation();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 3:
                    path_item = "DATA/movies750/items.csv";
                    path_known = "DATA/movies750/ratings.test.known.csv";
                    path_unknown = "DATA/movies750/ratings.test.unknown.csv";
                    try {
                        makerecommendation();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 4:
                    path_item = "DATA/series/250/items.csv";
                    path_known = "DATA/series/250/ratings.test.known.csv";
                    path_unknown = "DATA/series/250/ratings.test.unknown.csv";
                    try {
                        makerecommendation();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 5:
                    path_item = "DATA/series/750/items.csv";
                    path_known = "DATA/series/750/ratings.test.known.csv";
                    path_unknown = "DATA/series/750/ratings.test.unknown.csv";
                    try {
                        makerecommendation();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 6:
                    path_item = "DATA/series2250/items.csv";
                    path_known = "DATA/series2250/ratings.test.known.csv";
                    path_unknown = "DATA/series2250/ratings.test.unknown.csv";
                    serie = true;
                    try {
                        makerecommendation();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 7:
                    try {
                        makerecommendation();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                default:
                    System.out.println(option);
                    break;
            }
        }
    }
}
*/