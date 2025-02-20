
package at.erpel.schemas._1p0.documents.extensions.edifact;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A monetary amout equivalent to a MOA segment.
 * 
 * <p>Java class for MonetaryAmountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MonetaryAmountType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AmountTypeCode" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumType" minOccurs="0"/&gt;
 *         &lt;element name="Amount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Decimal2Type"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonetaryAmountType", propOrder = {
    "amountTypeCode",
    "amount"
})
public class MonetaryAmountType {

    @XmlElement(name = "AmountTypeCode")
    protected String amountTypeCode;
    @XmlElement(name = "Amount", required = true)
    protected BigDecimal amount;

    /**
     * Gets the value of the amountTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountTypeCode() {
        return amountTypeCode;
    }

    /**
     * Sets the value of the amountTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountTypeCode(String value) {
        this.amountTypeCode = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

}
