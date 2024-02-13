
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
 *         <element name="GetEBillRecipientSubscriptionStatusResult" type="{http://swisspost_ch.ebs.ebill.b2bservice}ArrayOfEBillRecipientSubscriptionStatus" minOccurs="0"/>
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
    "getEBillRecipientSubscriptionStatusResult"
})
@XmlRootElement(name = "GetEBillRecipientSubscriptionStatusResponse")
public class GetEBillRecipientSubscriptionStatusResponse {

    @XmlElementRef(name = "GetEBillRecipientSubscriptionStatusResult", namespace = "http://ch.swisspost.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfEBillRecipientSubscriptionStatus> getEBillRecipientSubscriptionStatusResult;

    /**
     * Gets the value of the getEBillRecipientSubscriptionStatusResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfEBillRecipientSubscriptionStatus }{@code >}
     *     
     */
    public JAXBElement<ArrayOfEBillRecipientSubscriptionStatus> getGetEBillRecipientSubscriptionStatusResult() {
        return getEBillRecipientSubscriptionStatusResult;
    }

    /**
     * Sets the value of the getEBillRecipientSubscriptionStatusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfEBillRecipientSubscriptionStatus }{@code >}
     *     
     */
    public void setGetEBillRecipientSubscriptionStatusResult(JAXBElement<ArrayOfEBillRecipientSubscriptionStatus> value) {
        this.getEBillRecipientSubscriptionStatusResult = value;
    }

}
