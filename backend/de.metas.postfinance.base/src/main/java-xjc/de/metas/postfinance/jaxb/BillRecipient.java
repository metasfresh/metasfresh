
package de.metas.postfinance.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillRecipient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BillRecipient"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EbillAccountID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UIDHR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SubmissionStatus" type="{http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic}SubmissionStatus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillRecipient", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "ebillAccountID",
    "emailAddress",
    "uidhr",
    "submissionStatus"
})
public class BillRecipient {

    @XmlElementRef(name = "EbillAccountID", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ebillAccountID;
    @XmlElementRef(name = "EmailAddress", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<String> emailAddress;
    @XmlElementRef(name = "UIDHR", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<String> uidhr;
    @XmlElement(name = "SubmissionStatus", required = true)
    @XmlSchemaType(name = "string")
    protected SubmissionStatus submissionStatus;

    /**
     * Gets the value of the ebillAccountID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEbillAccountID() {
        return ebillAccountID;
    }

    /**
     * Sets the value of the ebillAccountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEbillAccountID(JAXBElement<String> value) {
        this.ebillAccountID = value;
    }

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEmailAddress(JAXBElement<String> value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the uidhr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUIDHR() {
        return uidhr;
    }

    /**
     * Sets the value of the uidhr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUIDHR(JAXBElement<String> value) {
        this.uidhr = value;
    }

    /**
     * Gets the value of the submissionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SubmissionStatus }
     *     
     */
    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    /**
     * Sets the value of the submissionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubmissionStatus }
     *     
     */
    public void setSubmissionStatus(SubmissionStatus value) {
        this.submissionStatus = value;
    }

}
