package dominio.clases.algorithm.collaborativefiltering;

import dominio.clases.rating.*;
import dominio.clases.recommendation.*;
import dominio.clases.algorithm.slopeone.*;
import dominio.clases.algorithm.k_means.*;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @author Manel Piera Garrigosa
 */

/** @class collaborativeFiltering
 * @brief Implements recommendation using K-means and Slope One dominio.controladores.clases.atribut.algorithm.
 */
public class CollaborativeFiltering {

    private Map<Integer, Map<Integer, Float>> opinions;
    private Map<Integer, Map<Integer, Float>> unknown;
    private Vector<Vector<Integer>> clusters;
    private int bestK;


    /** @brief <em>opinions</em> represents the ratings, float in the nested Map, that users, the first Integer is their ID, have given about items, their ID is the integer in the nested Map.
     *  <em>clusters</em> represents the k clusters of similar users.
     */

    /** @brief Default builder.
     *
     * \pre <em>true</em>
     * \post Creates a <em>collaborativeFiltering</em> object with <em>opinions</em> ,  <em>unknown</em> and <em>clusters</em> empty and <em>bestK</em> = 0.
     */
    public CollaborativeFiltering(){
        opinions = new TreeMap<>();
        unknown = new TreeMap<>();
        clusters = new Vector<>();
        bestK = 0;
    }
    /** @brief Builder with defined <em>opinions</em>, <em>unknown</em>. It also sets the best <em>k</em> for the clusters.
     *
     * @param opinions Map that represents the ratings that users have given about some items that are known.
     * @param unknown Map that represents the ratings that users have given about some items that are unknown.
     *
     * \pre opinions.size() > 10
     * \post Creates <em>collaborativeFiltering</em> object with <em>opinions</em> set to opinions and computes the k clusters of users.
     */
    public CollaborativeFiltering(Map<Integer, Map<Integer, Float>> opinions, Map<Integer, Map<Integer, Float>> unknown){
        this.unknown = unknown;
        this.opinions = opinions;
        elbowtest(opinions);
    }

    /** @brief Elbowtest method. It computes the best <em>k</em> using the elbow test.
     *
     * @param opinions Map that represents the ratings that users have given about some items that are known. It tests Ks betweeen 2 and 8.
     *
     *  \pre <em>true</em>
     *  \post The clusters are created with the best k.
     */
    public void elbowtest(Map<Integer, Map<Integer, Float>> opinions){
        int maxK = min(8, opinions.size());
        Vector<Float> inertias = new Vector<>();
        K_Means Kmean = new K_Means(opinions);
        for(int k = 0; k < maxK; ++k){
            Kmean.k_means(k+1);
            inertias.add(Kmean.inertia());
        }

        Vector<Float> angles = new Vector<>();
        for(int i = 0; i < maxK-1; ++i){
            float diff = inertias.get(i)-inertias.get(i+1);
            if(diff == 0){
                angles.add(3.14f);
            }
            else{
                angles.add((float) Math.atan(1/diff));
            }
        }

        float maxDiffAng = 0.0f;
        bestK = 1;
        for(int i = 0; i < maxK-2; ++i){
            float diffAng = angles.get(i)-angles.get(i+1);
            if(maxDiffAng < diffAng){
                maxDiffAng = diffAng;
                bestK = i+2;
            }
        }

        System.out.println("The best K is: " + bestK);

        Kmean.k_means(bestK);
        this.clusters = Kmean.getClusters();
    }


    /** @brief Function that makes a recommendation of items to auser.
     *
     * @param userID ID of the user that receives the recommendation.
     * @return It returns a Map of item ID's together with the expected rating of the dominio.controladores.clases.atribut.user.
     *
     * \pre The user must exist.
     * \post Returns a Map of expected ratings with maximum size 10.
     */
     public dominio.clases.recommendation.Recommendation recommend(Integer userID, Integer maxItems, boolean valoration){
        boolean cont = true;
        Integer clusterUser = 0;
        for(int i = 0; i < clusters.size() && cont; ++i){
            for(int j = 0; j < clusters.get(i).size() && cont; ++j){
                if(userID == clusters.get(i).get(j)){
                    clusterUser = i;
                    cont = false;
                }
            }
        }

        Map<Integer, Map<Integer, Float>> valCluster = new TreeMap<>();
        for(int i = 0; i < clusters.get(clusterUser).size(); ++i){
            valCluster.put(clusters.get(clusterUser).get(i), opinions.get(clusters.get(clusterUser).get(i)));
        }
        SlopeOne Slopeone = new SlopeOne();
        Map<Integer, Float> mapRecommendation = Slopeone.slopeone(valCluster, opinions.get(userID));
        Map<Integer, Float> aux = new TreeMap<>();

        if(valoration){
            for(Map.Entry<Integer, Float> entry: mapRecommendation.entrySet()){
                if(unknown.get(userID).containsKey(entry.getKey())){
                    aux.put(entry.getKey(), entry.getValue());
                }
            }
            mapRecommendation = aux;
            aux = new TreeMap<>();
        }

        if (mapRecommendation.size() > maxItems) {
            for (int i = 0; i < maxItems; ++i) {
                Iterator<Integer> it = mapRecommendation.keySet().iterator();
                int maxitemID = it.next();
                for (Map.Entry<Integer, Float> entry : mapRecommendation.entrySet()) {
                    if (entry.getValue() > mapRecommendation.get(maxitemID)) {
                        maxitemID = entry.getKey();
                    }
                }
                aux.put(maxitemID, mapRecommendation.get(maxitemID));
                mapRecommendation.remove(maxitemID);
            }
        }
        else{
            aux = mapRecommendation;
        }

        Recommendation result = new Recommendation(userID, 0);
        for(Map.Entry<Integer, Float> entry: aux.entrySet()){
            result.addRating(new Rating(entry.getKey(), entry.getValue()));
        }

        result.sortR();
        return result;
    }

    /**@brief Deletes user from clusters.
     *
     * @param ID User ID
     */
    public void delUserCluster(int ID){
         boolean cont = true;
         int mida = clusters.size();
        for(int i = 0; cont && i < mida; ++i) {
            if(clusters.get(i).contains(ID)){
                clusters.get(i).removeElement(ID);
                cont = false;
            }
        }
    }

    /** @brief Deletes item and all its consequences.
     *
     * @param ID Item ID.
     */
    public void delItem(int ID) {
         Map<Integer, Map<Integer, Float>> aux = new TreeMap<>();
        for(Map.Entry<Integer, Map<Integer, Float>> entry1: opinions.entrySet()){
            Map<Integer, Float> rowMap = new TreeMap<>();
            for(Map.Entry<Integer, Float> entry2: entry1.getValue().entrySet()){
                rowMap.put(entry2.getKey(), entry2.getValue());
            }
            aux.put(entry1.getKey(), rowMap);
        }

         for(Map.Entry<Integer, Map<Integer, Float>> entry: opinions.entrySet()){
             int userID = entry.getKey();
             if(entry.getValue().containsKey(ID) && entry.getValue().size() == 1){
                 aux.remove(userID);
                 delUserCluster(userID);
             }
             else if(entry.getValue().containsKey(ID)){
                 aux.get(userID).remove(ID);
             }
         }
         opinions = aux;
         K_Means Kmean = new K_Means();
         Kmean.k_means(bestK, clusters, opinions);
        this.clusters = Kmean.getClusters();
    }


    public void delUser(int ID) {
         if(opinions.containsKey(ID)){
             delUserCluster(ID);
             opinions.remove(ID);
         }
        K_Means Kmean = new K_Means();
        Kmean.k_means(bestK, clusters, opinions);
        this.clusters = Kmean.getClusters();
    }

    public void modRating(int IDuser, int IDitem, float valor) {
         if(opinions.containsKey(IDuser)){
             opinions.get(IDuser).put(IDitem, valor);
         }
         else {
             opinions.put(IDuser, new TreeMap<>());
             opinions.get(IDuser).put(IDitem, valor);
         }

        K_Means Kmean = new K_Means();
        Kmean.k_means(bestK, clusters, opinions);
        this.clusters = Kmean.getClusters();
    }

    public void delRating(int IDuser, int IDitem) {
         if(opinions.get(IDuser).size() == 1){
             opinions.remove(IDuser);
             delUserCluster(IDuser);
         }
         else{
             opinions.get(IDuser).remove(IDitem);
         }
        K_Means Kmean = new K_Means();
        Kmean.k_means(bestK, clusters, opinions);
        this.clusters = Kmean.getClusters();
    }
}
