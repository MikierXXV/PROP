package dominio.clases.rating;

import java.util.Comparator;

/**
 *
 * @author
 */

/** @class rating
 * @brief Rating of a dominio.controladores.clases.atribut.user of the dominio.controladores.clases.atribut.item with id <em>id</em> and rating <em>valor</em>.
 */
public class Rating {
    int id;
    float valor;
    /** @brief <em>id</em> represents the dominio.controladores.clases.atribut.item id.
     *  <em>valor</em> represents the dominio.controladores.clases.atribut.item rating.
     */

    /** @brief Builder.
     *
     * @param id Item id.
     * @param valor Item rating.
     *
     * \pre <em>true</em>
     * \post Creates a rating.
     */
    public Rating(Integer id, Float valor){
        this.id = id;
        this.valor = valor;
    }

    public int getId() {
        return this.id;
    }

    public float getValor(){
        return this.valor;
    }

    /** @brief We use this class to sort our <em>recommendation</em> list.
     */
    public static class SortByVal implements Comparator<Rating> {
        public int compare(Rating a, Rating b){
            if(a.valor > b.valor){
                return -1;
            }
            else if(a.valor < b.valor){
                return 1;
            }
            else{
                return 0;
            }
        }
    }
}

