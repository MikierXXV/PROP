package persistencia.preprocessat;

import java.util.List;

/**
 * @class Content
 * @brief  Type nedded to make a efficient proces of data sets for kk-neightbours
 * @author Miguel
 */

public class Content {
    private String tag;
    private Integer tag_numi;
    private Double tag_numd;
    private List<String> categorics;

    /**
     * @brief Default builder
     * \pre the path of the file needs to be existent and the document csv needs to be from type items.csv
     *
     * \post It creates a <em>Content</em> object with values:
     *  <em>dominio.controladores.clases.atribut.tag</em>  initialized as nnull, <em>tag_numi</em>  initialized as null, <em>tag_numd</em> null, <em>categorics</em> empty,
     */
    public Content() {
        this.tag = null;
        this.tag_numi = null;
        this.tag_numd = null;
        this.categorics = null;

    }

    /**
     * @brief Default builder with initializers
     *\pre the path of the file needs to be existent and the document csv needs to be from type items.csv
     *\post It creates a <em>Content</em> object with values:
     *  <em>dominio.controladores.clases.atribut.tag</em>  initialized , <em>tag_numi</em>  initialized, <em>tag_numd</em> initialized, <em>categorics</em> initialized,
     * @param s type of the dominio.controladores.clases.atribut.tag
     * @param i integer number associated
     * @param d double number associated
     * @param categorics list of the categoric elements
     */
    public Content(String s, Integer i, Double d, List<String> categorics) {
        this.tag = s;
        this.tag_numi = i;
        this.tag_numd = d;
        this.categorics = categorics;
    }

    /**
     * @brief Getter of the dominio.controladores.clases.atribut.tag
     * \pre exists a Content
     * \post obtain the dominio.controladores.clases.atribut.tag
     * @return the dominio.controladores.clases.atribut.content dominio.controladores.clases.atribut.tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @brief Getter of the tag_numi
     * \pre exists a Content
     * \post obtain the tag_numi
     * @return the integer associated to the dominio.controladores.clases.atribut.tag
     */
    public Integer getTag_numi() {
        return tag_numi;
    }

    /**
     * @brief Getter of the tag_numd
     * \pre exists a Content
     * \post obtain the tag_numd
     * @return the double associated to the dominio.controladores.clases.atribut.tag
     */
    public Double getTag_numd() {
        return tag_numd;
    }

    /**
     * @brief Getter of categorics
     * \pre exists a Content
     * \post obtain the set of categorics
     * @return list of the categorics elements
     */
    public List<String> getCategorics() {
        return categorics;
    }

    /**
     * @brief Setter of the dominio.controladores.clases.atribut.tag
     * \pre true
     * \post modify the attribute dominio.controladores.clases.atribut.tag
     * @param tag, attributes the new dominio.controladores.clases.atribut.tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @brief Setter of the tag_numi
     * \pre true
     * \post modify the attribute tag_numi
     * @param tag_numi , attributes the integer corresponded
     */
    public void setTag_numi(Integer tag_numi) {
        this.tag_numi = tag_numi;
    }

    /**
     * @brief Setter of the tag_numd
     * \pre true
     * \post modify the attribute tag_numd
     * @param tag_numd , attributes the double corresponded
     */
    public void setTag_numd(Double tag_numd) {
        this.tag_numd = tag_numd;
    }

    /**
     * @brief Setter of categorics
     * \pre true
     * \post modify the attribute categorics
     * @param categorics list of the elements categorics ordered
     */
    public void setCategorics(List<String> categorics) {
        this.categorics = categorics;
    }
}
