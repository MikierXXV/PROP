/*package dominio.controladores.drivers;

import dominio.clases.algorithm.k_means.*;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

public class DriverK_Means {
    private static Scanner sc;

    public static void testcosineSquaredSimil(){
        K_Means Kmean = new K_Means();
        Map<Integer, Float> user1 = leerUsuario();
        Map<Integer, Float> user2 = leerUsuario();
        System.out.println("Resultado: " + Kmean.cosineSquaredSimil(user1, user2));
    }

    public static void testequalClusters(){//si no estan en el miso orden se consideran dos grupos de clusters diferentes
        System.out.println("Cluster 1:");
        Vector<Vector<Integer>> clusters1 = leerclusters();
        System.out.println("Cluster 2:");
        Vector<Vector<Integer>> clusters2 = leerclusters();

        K_Means Kmean = new K_Means();
        boolean equal = Kmean.equalClusters(clusters1, clusters2);
        if(equal){
            System.out.println("Son iguales.");
        }
        else{
            System.out.println("Son diferentes.");
        }
    }

    public static void testk_means(){
        System.out.println("Escoja una k:");
        int k = sc.nextInt();
        K_Means Kmean = new K_Means(leeropinions());
        Vector<Vector<Integer>> clusters = Kmean.k_means(k);
        System.out.println("Resultado:");
        for(int i = 0; i < clusters.size(); ++i){
            System.out.println("Cluster " + i + ":");
            for(int j = 0; j < clusters.get(i).size(); ++j){
                System.out.println(clusters.get(i).get(j));
            }
        }
    }

    public static Map<Integer, Float> leerUsuario(){
        Map<Integer, Float> user = new TreeMap<Integer, Float>();
        System.out.println("Numero de items valorados por usuario: ");
        int numItems = sc.nextInt();
        for(int j = 0; j < numItems; ++j){
            System.out.println("ID item numero " + (j+1) + ":");
            int itemID = sc.nextInt();
            System.out.println("Valoracion de l'item " + (j+1) + ":");
            float itemVal = sc.nextFloat();
            user.put(itemID, itemVal);
        }
        return user;
    }

    public static Vector<Vector<Integer>> leerclusters(){
        System.out.println("Numero de clusters del grupo:");
        int numcl1 = sc.nextInt();
        Vector<Vector<Integer>> clusters1 = new Vector<Vector<Integer>>();
        for(int i = 0; i < numcl1; ++i){
            clusters1.add(new Vector<Integer>());
            System.out.println("Numero de elementos en cluster " + (i+1) + " del grupo 1:");
            int numel = sc.nextInt();
            for(int j = 0; j < numel; ++j){
                System.out.println("UserID en cluster " + (i+1) + " del grupo 1:");
                int userID = sc.nextInt();
                clusters1.get(i).add(userID);
            }
        }
        return clusters1;
    }

    public static Map<Integer, Map<Integer, Float>> leeropinions(){
        System.out.println("Numero de users: ");
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

    public static void main(String[] args){
        System.out.println("Driver Kmean");
        boolean salir = false;
        sc = new Scanner(System.in);

        while (!salir) {
            System.out.println("Escoja una opción:");
            System.out.println("\t 1) testcosineSquaredSimil()");
            System.out.println("\t 2) testequalClusters()");
            System.out.println("\t 3) testk_means()");
            System.out.println("\t 0) Salir");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    try {
                        testcosineSquaredSimil();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 2:
                    try {
                        testequalClusters();
                    } catch (Exception E) {
                        System.out.println(E.getMessage());
                    }
                    break;
                case 3:
                    try {
                        testk_means();
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
*/