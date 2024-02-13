
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
 *         <element name="ConfirmEBillRecipientSubscriptionResult" type="{http://swisspost_ch.ebs.ebill.b2bservice}EBillRecipientSubscriptionConfirmation" minOccurs="0"/>
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
    "confirmEBillRecipientSubscriptionResult"
})
@XmlRootElement(name = "ConfirmEBillRecipientSubscriptionResponse")
public class ConfirmEBillRecipientSubscriptionResponse {

    @XmlElementRef(name = "ConfirmEBillRecipientSubscriptionResult", namespace = "http://ch.swisspost.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<EBillRecipientSubscriptionConfirmation> confirmEBillRecipientSubscriptionResult;

    /**
     * Gets the value of the confirmEBillRecipientSubscriptionResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionConfirmation }{@code >}
     *     
     */
    public JAXBElement<EBillRecipientSubscriptionConfirmation> getConfirmEBillRecipientSubscriptionResult() {
        return confirmEBillRecipientSubscriptionResult;
    }

    /**
     * Sets the value of the confirmEBillRecipientSubscriptionResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EBillRecipientSubscriptionConfirmation }{@code >}
     *     
     */
    public void setConfirmEBillRecipientSubscriptionResult(JAXBElement<EBillRecipientSubscriptionConfirmation> value) {
        this.confirmEBillRecipientSubscriptionResult = value;
    }

}
