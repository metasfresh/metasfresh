
package at.erpel.schemas._1p0.documents.extensions.edifact;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentConditionsExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentConditionsExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DiscountDuration" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PaymentCondition" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PaymentMeans" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentConditionsExtensionType", propOrder = {
    "discountDuration",
    "paymentCondition",
    "paymentMeans"
})
public class PaymentConditionsExtensionType {

    @XmlElement(name = "DiscountDuration")
    protected Object discountDuration;
    @XmlElement(name = "PaymentCondition")
    protected String paymentCondition;
    @XmlElement(name = "PaymentMeans")
    protected String paymentMeans;

    /**
     * Gets the value of the discountDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDiscountDuration() {
        return discountDuration;
    }

    /**
     * Sets the value of the discountDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDiscountDuration(Object value) {
        this.discountDuration = value;
    }

    /**
     * Contitions which apply for the payment. Please use EDIFACT code list values. (PAI 4439)
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentCondition() {
        return paymentCondition;
    }

    /**
     * Sets the value of the paymentCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentCondition(String value) {
        this.paymentCondition = value;
    }

    /**
     * Payment means coded. Please use EDIFACT code list values. (PAI 4461)
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMeans() {
        return paymentMeans;
    }

    /**
     * Sets the value of the paymentMeans property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMeans(String value) {
        this.paymentMeans = value;
    }

}
