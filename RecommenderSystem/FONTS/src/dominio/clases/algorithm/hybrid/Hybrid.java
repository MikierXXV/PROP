package dominio.clases.algorithm.hybrid;

import dominio.clases.rating.Rating;
import dominio.clases.recommendation.Recommendation;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
/**
 * @brief Class for the implementation of the Hybrid Algorithm for computing recommendations.
 * @author Marc Delgado Sánchez
 */
public class Hybrid {
    /**
     * @brief Basic Constructor, with no parameters.
     */
    public Hybrid() {}

    /**
     * @brief Application of the Hybrid Algorithm.
     * @param r1    Recommendation done by the Collaborative Filtering.
     * @param r2    Recommendation done by the Content-Based Filtering.
     * @param k     Number of items to recommend.
     * @return      Returns a recommendation of k items, using the Hybrid Algorithm.
     * \pre <em>r1</em> and <em>r2</em> are correct recommendations.
     * \post Returns a recommendation of k items.
     */
    public Recommendation recommend(Recommendation r1, Recommendation r2, int k) {
        Recommendation result = new Recommendation(r1.getID_perfil(), 2);
        List<Integer> ids_1 = new ArrayList<>();
        List<Integer> ids_2 = new ArrayList<>();
        List<Rating> comuns = new ArrayList<>();
        int id_1, id_2;

        for (Rating r : r1.getConjunt()) {
            id_1 = r.getId();
            ids_1.add(id_1);
        }

        for (Rating r : r2.getConjunt()) {
            id_2 = r.getId();
            ids_2.add(id_2);
        }

        for(Integer i : ids_1) {
            if (ids_2.contains(i)) {
                Float new_rating = (float)Math.sqrt(r1.getValue(i)*r2.getValue(i));
                comuns.add(new Rating(i,new_rating));
                r1.removeRating(i);
                r2.removeRating(i);
            }
        }
        int i = 0;
        int j = 0;
        while (comuns.size() < k && i < r1.getConjunt().size() && j < r2.getConjunt().size()) {
            Rating rate1 = r1.getConjunt().get(i);
            Rating rate2 = r2.getConjunt().get(j);
            if (rate1.getValor() > rate2.getValor()) {
                comuns.add(new Rating(rate1.getId(), rate1.getValor()));
                ++i;
            }
            else {
                comuns.add(new Rating(rate2.getId(), rate2.getValor()));
                ++j;
            }
        }
        result.setConjunt(comuns);
        result.setAlg(2);
        return result;
    }
}
