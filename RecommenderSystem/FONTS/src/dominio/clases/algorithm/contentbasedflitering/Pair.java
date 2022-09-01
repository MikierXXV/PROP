package dominio.clases.algorithm.contentbasedflitering;

/**
 *
 * @author Marc Delgado Sánchez
 */

/** @class Pair
 *  @brief Implements the Pair data structure.
 */
public class Pair implements Comparable<Pair> {
    private int id;
    private double similarity;

    /**
     * @brief Default builder, with initializers
     * @param id            id of the item
     * @param similarity    similarity with another item (unknown to this class)
     */
    public Pair(int id, double similarity) {
        this.id = id;
        this.similarity = similarity;
    }

    /**
     * @brief       Getter of the similarity
     * @return      similarity
     * \pre         <em>True</em>
     * \post        Returns the similarity of the pair
     */
    public double getSimilarity() {
        return this.similarity;
    }

    /**
     * @brief       Getter of the ID
     * @return      ID
     * \pre <em>True</em>
     * \post Returns the ID of the pair
     */
    public int getId() {
        return this.id;
    }

    /**
     * @brief Sets a way to compare two different Pair objects.
     * This is done to create a PriorityQueue with Pair objects, which
     * orders depending on the similarity of the Pair objects.
     * @param p     another Pair object
     * @return      returns which Pair object goes first
     */
    @Override
    public int compareTo(Pair p) {
        if (similarity < p.similarity) return -1;
        else if (similarity > p.similarity) return 1;
        else return 0;
    }
}
