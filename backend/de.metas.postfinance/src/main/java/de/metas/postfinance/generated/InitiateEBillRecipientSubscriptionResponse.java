
package de.metas.postfinance.generated;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="InitiateEBillRecipientSubscriptionResult" type="{http://swisspost_ch.ebs.ebill.b2bservice}EBillRecipientSubscriptionInitiation" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "initiateEBillRecipientSubscriptionResult"
})
@XmlRootElement(name = "InitiateEBillRecipientSubscriptionResponse")
public class InitiateEBillRecipientSubscriptionResponse {

    @XmlElementRef(name = "InitiateEBillRecipientSubscriptionResult", namespace = "http://ch.swisspost.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<EBillRecipientSubscriptionInitiation> initiateEBillRecipientSubscriptionResult;

    /**
     * Gets the value of the initiateEBillRecipientSubscriptionResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionInitiation }{@code >}
     *     
     */
    public JAXBElement<EBillRecipientSubscriptionInitiation> getInitiateEBillRecipientSubscriptionResult() {
        return initiateEBillRecipientSubscriptionResult;
    }

    /**
     * Sets the value of the initiateEBillRecipientSubscriptionResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionInitiation }{@code >}
     *     
     */
    public void setInitiateEBillRecipientSubscriptionResult(JAXBElement<EBillRecipientSubscriptionInitiation> value) {
        this.initiateEBillRecipientSubscriptionResult = value;
    }

}
