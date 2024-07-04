
package de.metas.postfinance.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BillerID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SubscriptionInitiationToken" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SubscriptionInitiationActivationCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "billerID",
    "subscriptionInitiationToken",
    "subscriptionInitiationActivationCode"
})
@XmlRootElement(name = "ConfirmEBillRecipientSubscription")
public class ConfirmEBillRecipientSubscription {

    @XmlElement(name = "BillerID", required = true, nillable = true)
    protected String billerID;
    @XmlElement(name = "SubscriptionInitiationToken", required = true, nillable = true)
    protected String subscriptionInitiationToken;
    @XmlElement(name = "SubscriptionInitiationActivationCode", required = true, nillable = true)
    protected String subscriptionInitiationActivationCode;

    /**
     * Gets the value of the billerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillerID() {
        return billerID;
    }

    /**
     * Sets the value of the billerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillerID(String value) {
        this.billerID = value;
    }

    /**
     * Gets the value of the subscriptionInitiationToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriptionInitiationToken() {
        return subscriptionInitiationToken;
    }

    /**
     * Sets the value of the subscriptionInitiationToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriptionInitiationToken(String value) {
        this.subscriptionInitiationToken = value;
    }

    /**
     * Gets the value of the subscriptionInitiationActivationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriptionInitiationActivationCode() {
        return subscriptionInitiationActivationCode;
    }

    /**
     * Sets the value of the subscriptionInitiationActivationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriptionInitiationActivationCode(String value) {
        this.subscriptionInitiationActivationCode = value;
    }

}
