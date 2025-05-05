
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
 *         &lt;element name="SubscriptionInitiationEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "subscriptionInitiationEmailAddress"
})
@XmlRootElement(name = "InitiateEBillRecipientSubscription")
public class InitiateEBillRecipientSubscription {

    @XmlElement(name = "BillerID", required = true, nillable = true)
    protected String billerID;
    @XmlElement(name = "SubscriptionInitiationEmailAddress", required = true, nillable = true)
    protected String subscriptionInitiationEmailAddress;

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
     * Gets the value of the subscriptionInitiationEmailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriptionInitiationEmailAddress() {
        return subscriptionInitiationEmailAddress;
    }

    /**
     * Sets the value of the subscriptionInitiationEmailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriptionInitiationEmailAddress(String value) {
        this.subscriptionInitiationEmailAddress = value;
    }

}
