
package at.erpel.schemas._1p0.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReductionAndSurchargeListLineItemDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReductionAndSurchargeListLineItemDetailsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element ref="{http://erpel.at/schemas/1p0/documents}ReductionListLineItem" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element ref="{http://erpel.at/schemas/1p0/documents}SurchargeListLineItem" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}TotalReductionAndSurchargeListLineItem" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReductionAndSurchargeListLineItemDetailsType", propOrder = {
    "reductionListLineItemOrSurchargeListLineItem",
    "totalReductionAndSurchargeListLineItem"
})
public class ReductionAndSurchargeListLineItemDetailsType {

    @XmlElementRefs({
        @XmlElementRef(name = "ReductionListLineItem", namespace = "http://erpel.at/schemas/1p0/documents", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "SurchargeListLineItem", namespace = "http://erpel.at/schemas/1p0/documents", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<ReductionAndSurchargeBaseType>> reductionListLineItemOrSurchargeListLineItem;
    @XmlElement(name = "TotalReductionAndSurchargeListLineItem")
    protected BigDecimal totalReductionAndSurchargeListLineItem;

    /**
     * Gets the value of the reductionListLineItemOrSurchargeListLineItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reductionListLineItemOrSurchargeListLineItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReductionListLineItemOrSurchargeListLineItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link ReductionAndSurchargeBaseType }{@code >}
     * {@link JAXBElement }{@code <}{@link ReductionAndSurchargeBaseType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<ReductionAndSurchargeBaseType>> getReductionListLineItemOrSurchargeListLineItem() {
        if (reductionListLineItemOrSurchargeListLineItem == null) {
            reductionListLineItemOrSurchargeListLineItem = new ArrayList<JAXBElement<ReductionAndSurchargeBaseType>>();
        }
        return this.reductionListLineItemOrSurchargeListLineItem;
    }

    /**
     * Gets the value of the totalReductionAndSurchargeListLineItem property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalReductionAndSurchargeListLineItem() {
        return totalReductionAndSurchargeListLineItem;
    }

    /**
     * Sets the value of the totalReductionAndSurchargeListLineItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalReductionAndSurchargeListLineItem(BigDecimal value) {
        this.totalReductionAndSurchargeListLineItem = value;
    }

}
