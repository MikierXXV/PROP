package dominio.controladores.drivers;

import dominio.clases.evaluation.*;
import dominio.clases.rating.Rating;
import dominio.clases.recommendation.Recommendation;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DriverEvaluation {
    private static Scanner sc;

    public static Map<Integer, Map<Integer, Float>> leerunknown(){
        System.out.println("Introduzca las valoraciones de los usuarios no conocidas (unknown). Numero de users: ");
        int Nusers = sc.nextInt();
        Map<Integer, Map<Integer, Float>> opinions = new TreeMap<Integer, Map<Integer, Float>>();
        for(int i = 0; i < Nusers; ++i){
            System.out.println("ID de user num " + (i+1) + ":");
            int userID = sc.nextInt();
            opinions.put(userID, new TreeMap<Integer, Float>());
            System.out.println("Numero de items valorados por usuario " + userID + ":");
            int numItems = sc.nextInt();
            for(int j = 0; j < numItems; ++j){
                System.out.println("ID item numero " + (j+1) + ":");
                int itemID = sc.nextInt();
                System.out.println("Valoracion del item " + (j+1) + ":");
                float itemVal = sc.nextFloat();
                opinions.get(userID).put(itemID, itemVal);
            }
        }
        return opinions;
    }

    public static Recommendation leerrecommendation(){
        Recommendation recommendation = new Recommendation();
        System.out.println("ID del perfil de la recomendacion:");
        int n = sc.nextInt();
        recommendation.setID_perfil(n);
        System.out.println("Numero de items en la recomendacion:");
        n = sc.nextInt();
        for(int i = 0; i < n; ++i){
            System.out.println("ID item " + (i+1) +" :");
            int ID = sc.nextInt();
            System.out.println("Rating item " + (i+1) +" :");
            Float rating = sc.nextFloat();
            recommendation.addRating(new Rating(ID, rating));
        }
        return recommendation;
    }

    public static void testDCG(){
        Evaluation eval = new Evaluation(leerunknown());
        System.out.println("El valor DCG de la recomendacio es: " + eval.DCG(leerrecommendation()));
    }

    public static void main(String[] args){
        System.out.println("Driver collaborativeFiltering");
        boolean salir = false;
        sc = new Scanner(System.in);

        while (!salir) {
            System.out.println("Escoja una opción:");
            System.out.println("\t 1) testDCG()");
            System.out.println("\t 0) Salir");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    try {
                        testDCG();
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
