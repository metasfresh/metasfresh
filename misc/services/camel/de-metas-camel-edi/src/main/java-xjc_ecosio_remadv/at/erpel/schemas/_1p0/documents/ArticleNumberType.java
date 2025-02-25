
package at.erpel.schemas._1p0.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for ArticleNumberType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArticleNumberType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute ref="{http://erpel.at/schemas/1p0/documents}ArticleNumberType"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArticleNumberType", propOrder = {
    "content"
})
public class ArticleNumberType {

    @XmlValue
    protected String content;
    @XmlAttribute(name = "ArticleNumberType", namespace = "http://erpel.at/schemas/1p0/documents")
    protected ArticleNumberTypeType articleNumberType;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Attribute defining the type of article number.
     * 
     * @return
     *     possible object is
     *     {@link ArticleNumberTypeType }
     *     
     */
    public ArticleNumberTypeType getArticleNumberType() {
        return articleNumberType;
    }

    /**
     * Sets the value of the articleNumberType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArticleNumberTypeType }
     *     
     */
    public void setArticleNumberType(ArticleNumberTypeType value) {
        this.articleNumberType = value;
    }

}
