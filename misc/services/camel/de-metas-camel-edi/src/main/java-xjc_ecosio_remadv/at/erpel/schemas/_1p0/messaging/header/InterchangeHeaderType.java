
package at.erpel.schemas._1p0.messaging.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InterchangeHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InterchangeHeaderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SyntaxIdentifier" type="{http://erpel.at/schemas/1p0/messaging/header}SyntaxIdentifierType" minOccurs="0"/&gt;
 *         &lt;element name="Sender" type="{http://erpel.at/schemas/1p0/messaging/header}SenderType"/&gt;
 *         &lt;element name="Recipient" type="{http://erpel.at/schemas/1p0/messaging/header}RecipientType"/&gt;
 *         &lt;element name="DateTime" type="{http://erpel.at/schemas/1p0/messaging/header}DateTimeType"/&gt;
 *         &lt;element name="ControlRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RecipientRef" type="{http://erpel.at/schemas/1p0/messaging/header}RecipientRefType" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ProcessingPriorityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AckRequest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AgreementId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TestIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InterchangeHeaderType", propOrder = {
    "syntaxIdentifier",
    "sender",
    "recipient",
    "dateTime",
    "controlRef",
    "recipientRef",
    "applicationRef",
    "processingPriorityCode",
    "ackRequest",
    "agreementId",
    "testIndicator"
})
public class InterchangeHeaderType {

    @XmlElement(name = "SyntaxIdentifier")
    protected SyntaxIdentifierType syntaxIdentifier;
    @XmlElement(name = "Sender", required = true)
    protected SenderType sender;
    @XmlElement(name = "Recipient", required = true)
    protected RecipientType recipient;
    @XmlElement(name = "DateTime", required = true)
    protected DateTimeType dateTime;
    @XmlElement(name = "ControlRef")
    protected String controlRef;
    @XmlElement(name = "RecipientRef")
    protected RecipientRefType recipientRef;
    @XmlElement(name = "ApplicationRef")
    protected String applicationRef;
    @XmlElement(name = "ProcessingPriorityCode")
    protected String processingPriorityCode;
    @XmlElement(name = "AckRequest")
    protected String ackRequest;
    @XmlElement(name = "AgreementId")
    protected String agreementId;
    @XmlElement(name = "TestIndicator")
    protected String testIndicator;

    /**
     * Gets the value of the syntaxIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link SyntaxIdentifierType }
     *     
     */
    public SyntaxIdentifierType getSyntaxIdentifier() {
        return syntaxIdentifier;
    }

    /**
     * Sets the value of the syntaxIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link SyntaxIdentifierType }
     *     
     */
    public void setSyntaxIdentifier(SyntaxIdentifierType value) {
        this.syntaxIdentifier = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link SenderType }
     *     
     */
    public SenderType getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link SenderType }
     *     
     */
    public void setSender(SenderType value) {
        this.sender = value;
    }

    /**
     * Gets the value of the recipient property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientType }
     *     
     */
    public RecipientType getRecipient() {
        return recipient;
    }

    /**
     * Sets the value of the recipient property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientType }
     *     
     */
    public void setRecipient(RecipientType value) {
        this.recipient = value;
    }

    /**
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeType }
     *     
     */
    public DateTimeType getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeType }
     *     
     */
    public void setDateTime(DateTimeType value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the controlRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlRef() {
        return controlRef;
    }

    /**
     * Sets the value of the controlRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlRef(String value) {
        this.controlRef = value;
    }

    /**
     * Gets the value of the recipientRef property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientRefType }
     *     
     */
    public RecipientRefType getRecipientRef() {
        return recipientRef;
    }

    /**
     * Sets the value of the recipientRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientRefType }
     *     
     */
    public void setRecipientRef(RecipientRefType value) {
        this.recipientRef = value;
    }

    /**
     * Gets the value of the applicationRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationRef() {
        return applicationRef;
    }

    /**
     * Sets the value of the applicationRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationRef(String value) {
        this.applicationRef = value;
    }

    /**
     * Gets the value of the processingPriorityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessingPriorityCode() {
        return processingPriorityCode;
    }

    /**
     * Sets the value of the processingPriorityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessingPriorityCode(String value) {
        this.processingPriorityCode = value;
    }

    /**
     * Gets the value of the ackRequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAckRequest() {
        return ackRequest;
    }

    /**
     * Sets the value of the ackRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAckRequest(String value) {
        this.ackRequest = value;
    }

    /**
     * Gets the value of the agreementId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementId() {
        return agreementId;
    }

    /**
     * Sets the value of the agreementId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementId(String value) {
        this.agreementId = value;
    }

    /**
     * Gets the value of the testIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestIndicator() {
        return testIndicator;
    }

    /**
     * Sets the value of the testIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestIndicator(String value) {
        this.testIndicator = value;
    }

}
