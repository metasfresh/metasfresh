
package at.erpel.schemas._1p0.documents;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for UnitPriceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitPriceType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://erpel.at/schemas/1p0/documents&gt;Decimal4Type"&gt;
 *       &lt;attribute ref="{http://erpel.at/schemas/1p0/documents}BaseQuantity"/&gt;
 *       &lt;attribute ref="{http://erpel.at/schemas/1p0/documents}PriceType"/&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitPriceType", propOrder = {
    "value"
})
public class UnitPriceType {

    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "BaseQuantity", namespace = "http://erpel.at/schemas/1p0/documents")
    protected BigDecimal baseQuantity;
    @XmlAttribute(name = "PriceType", namespace = "http://erpel.at/schemas/1p0/documents")
    protected PriceTypeType priceType;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Base quantity to which the unit price is related, i.e. 1, 100, etc.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseQuantity() {
        return baseQuantity;
    }

    /**
     * Sets the value of the baseQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseQuantity(BigDecimal value) {
        this.baseQuantity = value;
    }

    /**
     * Indicates whether the price is the calculation net (including allowances/charges, excluding taxes) or the calculation gross price (excluding allowances/charges/taxes) . If this attribute is not specified the UnitPrice is considered as calculation gross price.
     * 
     * @return
     *     possible object is
     *     {@link PriceTypeType }
     *     
     */
    public PriceTypeType getPriceType() {
        return priceType;
    }

    /**
     * Sets the value of the priceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceTypeType }
     *     
     */
    public void setPriceType(PriceTypeType value) {
        this.priceType = value;
    }

}
