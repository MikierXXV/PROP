package dominio.controladores.drivers;

import dominio.clases.algorithm.collaborativefiltering.*;
import dominio.clases.algorithm.slopeone.*;

import persistencia.preprocessat.*;
import dominio.clases.rating.Rating;
import dominio.clases.recommendation.Recommendation;


import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import dominio.clases.algorithm.slopeone.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 *
 * @author Oriol Martí Jariod
 */


public class Driverslopeone {
    private static Scanner sc;



    public static Map<Integer, Map<Integer, Float>> leeropinions(){
        System.out.println("Numero de users: ");
        int Nusers = sc.nextInt();
        Map<Integer, Map<Integer, Float>> opinions = new TreeMap<Integer, Map<Integer, Float>>();
        for(int i = 0; i < Nusers; ++i){
            System.out.println("ID de user nuemero " + (i+1) + " : ");
            int userID = sc.nextInt();
            opinions.put(userID, new TreeMap<Integer, Float>());
            System.out.println("Numero de items valorados por usuario " + userID + " : ");
            int numItems = sc.nextInt();
            for(int j = 0; j < numItems; ++j){
                System.out.println("ID item numero " + (j+1) + " : ");
                int itemID = sc.nextInt();
                System.out.println("Valoracion del item " + (j+1) + " : ");
                float itemVal = sc.nextFloat();
                opinions.get(userID).put(itemID, itemVal);
            }
        }
        return opinions;
    }

    public static Map<Integer,Float> leerusuariopredi(){
        System.out.println("Items valorado por el usuario que queremos predecir");
        int Nitem = sc.nextInt();
        Map<Integer,Float> user = new TreeMap<Integer,Float>();
        for(int i = 0; i < Nitem; i++){
            System.out.println("ID item numero " + (i+1) + " : ");
            int itemID = sc.nextInt();
            System.out.println("Valoracion del item " + (i+1) + " : ");
            float itemVal = sc.nextFloat();
            user.put(itemID,itemVal);
        }
        return user;
    }

    public static void testSlopeOne() {
        System.out.println("Test SlopeOne");
        SlopeOne.setMap_data(leeropinions());
        Map<Integer,Float> user = leerusuariopredi();
        System.out.println("esta base de datos");
        SlopeOne.slopeone( SlopeOne.getMap_data(),user);
        System.out.println("\n Resultado:");
        print_map_completo(SlopeOne.getMap_data());
        System.out.println("esta desviacion");
        print_map_completo(SlopeOne.getMap_des());

        System.out.println("\n\t esta prediccion");
        print_pred(SlopeOne.getMap_pred());
        System.out.println("Terminado");

    }

    public static void testSlopeOne_250() {
        System.out.println("Test SlopeOne_250");
        System.out.println("\t1) CSV por defecto");
        System.out.println("\t2) CSV propio, el path al fichero");
        System.out.println("\t0) salir");
        boolean salir = false;
        sc = new Scanner(System.in);
        CSVparserRate csv = new CSVparserRate("DATA/movies250/ratings.test.known.csv");
        csv.readLoadRate();
        csv.LoadRate(csv.getContent());

            int option = sc.nextInt();
            switch (option) {
                case 1:
                    try {
                        SlopeOne.setMap_data(csv.getMapRate());

                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("escriba el path de su csv");
                        String s = sc.next();
                        csv = new CSVparserRate(s);
                        csv.readLoadRate();
                        csv.LoadRate(csv.getContent());
                        SlopeOne.setMap_data(csv.getMapRate());

                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println(option);
                    break;
            }


        System.out.println("Introduzca el ID del user que quiere la recomendacion, por lo contrario si desea volver atras escribe -1 :");
        int userID = sc.nextInt();
        boolean existinguser = SlopeOne.getMap_data().containsKey(userID);

        while(!existinguser && userID != -1){
            System.out.println("Este user no existe! Introduzca ID del user que quiere la recomendacion  por lo contrario si desea volver atras escribe -1 :");
            userID = sc.nextInt();

            existinguser = SlopeOne.getMap_data().containsKey(userID);
        }

        Recommendation recommendation;

        System.out.println("\n Resultado:");
        //CollaborativeFiltering CF = new CollaborativeFiltering(SlopeOne.getMap_data(), new TreeMap<>(), max(1, SlopeOne.getMap_data().size() / 3));
      //  recommendation = CF.recommend(userID, 10, false);
       /* for (Rating r : recommendation.getConjunt()) {
            System.out.println("ID item: " + r.getId() + " con esta predicción " + r.getValor());
        }

        System.out.println("Terminado");
*/
    }

    public static void testSlopeOne_csv() {
        System.out.println("Test SlopeOne_CSV");
        System.out.println("\t1) CSV por defecto");
        System.out.println("\t2) CSV propio, path al fichero");
        System.out.println("\t0) salir");
        boolean salir = false;
        sc = new Scanner(System.in);
        CSVparserRate csv = new CSVparserRate("DATA/movies750/ratings.test.known.csv");
        csv.readLoadRate();
        csv.LoadRate(csv.getContent());

            int option = sc.nextInt();
            switch (option) {
                case 1:
                    try {
                        SlopeOne.setMap_data(csv.getMapRate());

                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("escriba el path de su csv");
                        String s = sc.next();
                        csv = new CSVparserRate(s);
                        csv.readLoadRate();
                        csv.LoadRate(csv.getContent());
                        SlopeOne.setMap_data(csv.getMapRate());

                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println(option);
                    break;

        }

        System.out.println("Introduzca el ID del user que quiere la recomendacion, por lo contrario si desea volver atras escribe -1 :");
        int userID = sc.nextInt();
        boolean existinguser = SlopeOne.getMap_data().containsKey(userID);

        while(!existinguser && userID != -1){
            System.out.println("Este user no existe! Introduzca ID del user que quiere la recomendacion  por lo contrario si desea volver atras escribe -1 :");
            userID = sc.nextInt();

            existinguser = SlopeOne.getMap_data().containsKey(userID);
        }

        Recommendation recommendation;

        System.out.println("\n Resultado:");
        //CollaborativeFiltering CF = new CollaborativeFiltering(SlopeOne.getMap_data(), new TreeMap<>(), max(1, SlopeOne.getMap_data().size() / 3));
        /*recommendation = CF.recommend(userID, 10, false);
        for (Rating r : recommendation.getConjunt()) {
            System.out.println("ID item: " + r.getId() + " con esta predicción " + r.getValor());
        }


        System.out.println("Terminado");*/

    }

    public static void testdesviacio_mitjana() {
        System.out.println("testdesviacio mitjana");
        SlopeOne.setMap_data(leeropinions());
        System.out.println("esta base de datos");
        print_map_completo(SlopeOne.getMap_data());
        SlopeOne.desviacio_mitjana();
        System.out.println("\n Resultado:");
        System.out.println("\n\t esta desviacion");
        print_map_completo(SlopeOne.getMap_des());
        System.out.println("\n\t esta fequencia");
        print_map_freq(SlopeOne.getMap_freq());
        System.out.println("\n test terminado");

    }


    public static void testprediccio() {
        System.out.println("Test preddicio");
        SlopeOne.setMap_data(leeropinions());
        System.out.println("esta desviacion");
        SlopeOne.desviacio_mitjana();
        print_map_completo(SlopeOne.getMap_des());
        System.out.println("\n\t esta fequencia");
        print_map_freq(SlopeOne.getMap_freq());
        Map<Integer,Float> user = leerusuariopredi();
        //prediccio(user);

        System.out.println("\n Resultado:");
        System.out.println("\n\t esta prediccio");
        print_pred(SlopeOne.getMap_pred());
        System.out.println("\n test terminado");
    }

    private static void print_map_freq(Map<Integer, Map<Integer, Integer>> mappa){
        for(int j : mappa.keySet()){
            System.out.println("\t " + j + "-->" + mappa.get(j));

        }
    }

    private static void print_map_completo(Map<Integer, Map<Integer, Float>> mappa){
        for(int j : mappa.keySet()){
            System.out.println("\t " + j + "-->" + mappa.get(j));

        }
    }

    private static void print_pred(Map<Integer, Float> user) {
        for (Integer j : user.keySet()) {
            System.out.println("\t " + j + " --> " + user.get(j));
        }
    }

    public static void main(String[] args){
        System.out.println("Driver SlopeOne");
        boolean salir = false;
        sc = new Scanner(System.in);

        while (!salir) {
            System.out.println("Escoja una opción:");
            System.out.println("\t 1) testdesviacio_mitjana()");
            System.out.println("\t 2) testprediccio()");
            System.out.println("\t 3) testSlopeOne()");
            System.out.println("\t 4) testSlopeOne() n = 250");
            System.out.println("\t 5) testSlopeOne() csv entero");
            System.out.println("\t 0) Salir");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    try {
                        testdesviacio_mitjana();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 2:
                    try {
                        testprediccio();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 3:
                    try {
                        testSlopeOne();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;

                case 4:
                    try {
                        testSlopeOne_250();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 5:
                    try {
                        testSlopeOne_csv();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println(option);
                    break;
            }
        }
    }
}