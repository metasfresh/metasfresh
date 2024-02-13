
package de.metas.postfinance.generated;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EBillRecipientSubscriptionStatusBulk complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EBillRecipientSubscriptionStatusBulk">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="BillRecipients" type="{http://swisspost_ch.ebs.ebill.b2bservice}ArrayOfBillRecipient" minOccurs="0"/>
 *         <element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EBillRecipientSubscriptionStatusBulk", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "billRecipients",
    "message"
})
public class EBillRecipientSubscriptionStatusBulk {

    @XmlElementRef(name = "BillRecipients", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfBillRecipient> billRecipients;
    @XmlElementRef(name = "Message", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<String> message;

    /**
     * Gets the value of the billRecipients property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfBillRecipient }{@code >}
     *     
     */
    public JAXBElement<ArrayOfBillRecipient> getBillRecipients() {
        return billRecipients;
    }

    /**
     * Sets the value of the billRecipients property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfBillRecipient }{@code >}
     *     
     */
    public void setBillRecipients(JAXBElement<ArrayOfBillRecipient> value) {
        this.billRecipients = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMessage(JAXBElement<String> value) {
        this.message = value;
    }

}
