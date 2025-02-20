
package at.erpel.schemas._1p0.documents;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArticleNumberTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ArticleNumberTypeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *     &lt;enumeration value="PZN"/&gt;
 *     &lt;enumeration value="GTIN"/&gt;
 *     &lt;enumeration value="SuppliersArticleNumber"/&gt;
 *     &lt;enumeration value="CustomersArticleNumber"/&gt;
 *     &lt;enumeration value="UltimateCustomersArticleNumber"/&gt;
 *     &lt;enumeration value="ManufacturersArticleNumber"/&gt;
 *     &lt;enumeration value="UPC"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ArticleNumberTypeType")
@XmlEnum
public enum ArticleNumberTypeType {


    /**
     * Pharmazentralnummer - German for "central pharamaceutical number" - a unique identifier for pharmaceutical products
     * 
     */
    PZN("PZN"),

    /**
     * GS1 Global Trade Identification Number
     * 
     */
    GTIN("GTIN"),

    /**
     * Article number used by the supplier
     * 
     */
    @XmlEnumValue("SuppliersArticleNumber")
    SUPPLIERS_ARTICLE_NUMBER("SuppliersArticleNumber"),

    /**
     * Article number used by the customer
     * 
     */
    @XmlEnumValue("CustomersArticleNumber")
    CUSTOMERS_ARTICLE_NUMBER("CustomersArticleNumber"),

    /**
     * The product number of the final (ultimate) customer.
     * 
     */
    @XmlEnumValue("UltimateCustomersArticleNumber")
    ULTIMATE_CUSTOMERS_ARTICLE_NUMBER("UltimateCustomersArticleNumber"),

    /**
     * Article number used by the manufacturer.
     * 
     */
    @XmlEnumValue("ManufacturersArticleNumber")
    MANUFACTURERS_ARTICLE_NUMBER("ManufacturersArticleNumber"),

    /**
     * The Universal Product Code - typcially the coded printed below the bar code.
     * 
     */
    UPC("UPC");
    private final String value;

    ArticleNumberTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ArticleNumberTypeType fromValue(String v) {
        for (ArticleNumberTypeType c: ArticleNumberTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
