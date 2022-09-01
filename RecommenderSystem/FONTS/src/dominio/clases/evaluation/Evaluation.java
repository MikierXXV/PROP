package dominio.clases.evaluation;

import java.util.*;
import dominio.clases.rating.*;
import dominio.clases.recommendation.Recommendation;

/**
 * @author Manel Piera Garrigosa
 */

/** @class dominio.controladores.clases.atribut.evaluation
 * @brief Evaluates the quality of a recommendation.
 */
public class Evaluation {
    /**
     * @brief represents the map of unknown ratings given by the users. Used to evaluate the recommendation.
     */
    private Map<Integer, Map<Integer, Float>> unknown;

    /** @brief <em>recommendation</em> represents the recommendation to a dominio.controladores.clases.atribut.user ordered decreasingly by the expected rating.
     *  <em>known</em> represents the actual ratings from users of some items.
     */


    /** @brief Builder.
     *
     * @param unknown Sets <em>unknown</em> to unknown.
     *
     * \pre <em>true</em>
     * \post Creates an Evaluation.
     */
    public Evaluation(Map<Integer, Map<Integer, Float>> unknown) {
        this.unknown = unknown;
    }

    /** @brief Function that calculates the quality of a recommendation.
     *
     * @return It returns a number. The higher it is the better recommendation we have.
     *
     * \pre <em>true</em>
     * \post Gives DCG value.
     */
    public float DCG(Recommendation recommendation){
        recommendation.sortR();
        Map<Integer, Float> unknown_user = unknown.get(recommendation.getID_perfil());
        int j = 1;
        float result = 0.0f;
        for(int i = 0; i < recommendation.getConjunt().size(); ++i){
            if(i > 0 && recommendation.getConjunt().get(i).getValor() != recommendation.getConjunt().get(i-1).getValor()){
                j = i+1;
            }
            result += (Math.pow(2, unknown_user.get(recommendation.getConjunt().get(i).getId()))-1)/(Math.log(j + 1)/Math.log(2));
        }
        return result;
    }
}