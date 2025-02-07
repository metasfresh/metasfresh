
package at.erpel.schemas._1p0.documents.extensions.edifact;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitConversionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitConversionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BaseUnit" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType"/&gt;
 *         &lt;element name="TargetUnit" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType"/&gt;
 *         &lt;element name="Factor" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Decimal4Type"/&gt;
 *         &lt;element name="BaseUnitArticleNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ArticleNumberExtType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitConversionType", propOrder = {
    "baseUnit",
    "targetUnit",
    "factor",
    "baseUnitArticleNumber"
})
public class UnitConversionType {

    @XmlElement(name = "BaseUnit", required = true)
    protected UnitType baseUnit;
    @XmlElement(name = "TargetUnit", required = true)
    protected UnitType targetUnit;
    @XmlElement(name = "Factor", required = true)
    protected BigDecimal factor;
    @XmlElement(name = "BaseUnitArticleNumber")
    protected List<ArticleNumberExtType> baseUnitArticleNumber;

    /**
     * Gets the value of the baseUnit property.
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getBaseUnit() {
        return baseUnit;
    }

    /**
     * Sets the value of the baseUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setBaseUnit(UnitType value) {
        this.baseUnit = value;
    }

    /**
     * Gets the value of the targetUnit property.
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getTargetUnit() {
        return targetUnit;
    }

    /**
     * Sets the value of the targetUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setTargetUnit(UnitType value) {
        this.targetUnit = value;
    }

    /**
     * Gets the value of the factor property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFactor() {
        return factor;
    }

    /**
     * Sets the value of the factor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFactor(BigDecimal value) {
        this.factor = value;
    }

    /**
     * Gets the value of the baseUnitArticleNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseUnitArticleNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseUnitArticleNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArticleNumberExtType }
     * 
     * 
     */
    public List<ArticleNumberExtType> getBaseUnitArticleNumber() {
        if (baseUnitArticleNumber == null) {
            baseUnitArticleNumber = new ArrayList<ArticleNumberExtType>();
        }
        return this.baseUnitArticleNumber;
    }

}
