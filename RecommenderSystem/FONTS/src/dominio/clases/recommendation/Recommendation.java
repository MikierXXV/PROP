package dominio.clases.recommendation;

import dominio.clases.rating.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author Manel Piera Garrigosa
 * @author Marc Delgado Sánchez
 * @brief  This class represents a recommendation, a result of applying either of the algorithms.
 */
public class Recommendation {
    /**
     * @brief   List of recommended items with their expected ratings.
     */
    private List<Rating> conjunt;
    /**
     * @brief   ID of the user who has asked for this recommendation.
     */
    private int ID_perfil;
    /**
     * @brief   Algorithm used to calculate this recommendation. (0: Collaborative, 1: Content, 2: Hybrid)
     */
    private int alg;

    //Constructoras

    /**
     * @brief Standard builder, without parameters.
     */
    public Recommendation() {
        ID_perfil = 0;
        conjunt = new ArrayList<Rating>();
    }

    /**
     * @brief Standard Builder, with parameters.
     * @param userID    ID of the user.
     * @param alg       Algorithm used.
     */
    public Recommendation(int userID, int alg) {
        ID_perfil = userID;
        this.alg = alg;
        conjunt = new ArrayList<Rating>();
    }

    //Consultoras

    /**
     * @brief   Getter of the ID of the user.
     * @return  Returns the ID of the user.
     */
    public int getID_perfil() {
        return ID_perfil;
    }

    /**
     * @brief   Getter of the Algorithm used.
     * @return  Returns the Algorithm used.
     */
    public int getAlg(){ return alg;}

    /**
     * @brief Getter of the items and their expected ratings.
     * @return Returns the list of items in the recommendation and their expected ratings.
     */
    public List<Rating> getConjunt() {
        return conjunt;
    }

    /**
     * @brief   Gets the ID's of the items in the recommendation.
     * @return  Returns a List of Integers, where each Integer is the ID of an item in the recommendation.
     */
    public List<Integer> getItemIDs(){
        List<Integer> result = new ArrayList<>();
        for(Rating r: conjunt) {
            result.add(r.getId());
        }
        return result;
    }

    /**
     * @brief   Gets the expected ratings of the items in the recommendation.
     * @return  Returns a List of Floats, where each Float is the expected rating of an item in the recommendation.
     */
    public List<Float> getItemRates(){
        List<Float> result = new ArrayList<>();
        for(Rating r: conjunt) {
            result.add(r.getValor());
        }
        return result;
    }

    //Modificadoras

    /**
     * @brief Setter of the ID of the user.
     * @param id    ID of the user.
     * \pre <em>True</em>
     * \post The ID of the user is set to <em>id</em>.
     */
    public void setID_perfil(int id) {
        this.ID_perfil = id;
    }

    /**
     * @brief Setter of the list of items and expected ratings.
     * @param conj  New items and expected ratings
     * \pre <em>True</em>
     * \post The conjunt of the user is set to <em>conj</em>.
     */
    public void setConjunt(List<Rating> conj) {
        this.conjunt = conj;
    }

    /**
     * @brief Setter of the algorithm used.
     * @param algoritme  New algorithm.
     * \pre <em>True</em>
     * \post The alg of the user is set to <em>algoritme</em>.
     */
    public void setAlg(int algoritme) {
        this.alg = algoritme;
    }

    /**
     * @brief Adds a new item and its expected rating to the recommendation.
     * @param new_rating  New item with its expected rating.
     * \pre <em>True</em>
     * \post <em>conjunt</em> now contains <em>new_rating</em>.
     */
    public void addRating(Rating new_rating) {
        this.conjunt.add(new_rating);
    }
    /**
     * @brief Getter of the expected rating of a given item.
     * @param id_item  ID of the item.
     * \pre <em>True</em>
     * \post Returns the expected rating of <em>id_item</em> or -1 if it's not in the recommendation.
     */
    public float getValue(int id_item) {
        for (Rating r : conjunt) {
            if (r.getId() == id_item) return r.getValor();
        }
        return -1;
    }
    /**
     * @brief Removes a given item from the recommendation.
     * @param id  ID of the item.
     * \pre <em>True</em>
     * \post Removes the given item from the recommendation.
     */
    public void removeRating(int id) {
        for (Rating r : conjunt) {
            if (r.getId() == id) {
                conjunt.remove(r);
                break;
            }
        }
    }
    /**
     * @brief Sorts the recommendation by the items' expected ratings.
     * \pre <em>True</em>
     * \post <em>conjunt</em> is now sorted decreasingly by expected rating.
     */
    public void sortR(){
        Collections.sort(this.conjunt, new Rating.SortByVal());
    }
}