
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoginException complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="LoginException">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="additionalData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="additionalInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="errorClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fullMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="shortMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="systemFullMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="systemMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="systemShortMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginException", namespace = "http://dpd.com/common/service/types/LoginService/2.0", propOrder = {
    "additionalData",
    "additionalInfo",
    "errorClass",
    "errorCode",
    "fullMessage",
    "language",
    "message",
    "shortMessage",
    "systemFullMessage",
    "systemMessage",
    "systemShortMessage"
})
public class LoginException {

    /**
     * Additional data for an error.
     * 
     */
    protected String additionalData;
    /**
     * Additional info for an error in user language.
     * 
     */
    protected String additionalInfo;
    /**
     * The class which creates the error.
     * 
     */
    protected String errorClass;
    /**
     * The error code.
     * 
     */
    protected String errorCode;
    /**
     * The complete message text for the error with additional info in user language.
     * 
     */
    protected String fullMessage;
    /**
     * Language for messages.
     * 
     */
    protected String language;
    /**
     * The complete message text for the error in user language.
     * 
     */
    protected String message;
    /**
     * The short info text for the error in user language.
     * 
     */
    protected String shortMessage;
    /**
     * The complete message text for the error with
     * additional info in system language.
     * 
     */
    protected String systemFullMessage;
    /**
     * The complete message text for the error in system language.
     * 
     */
    protected String systemMessage;
    /**
     * The short info text for the error in system language.
     * 
     */
    protected String systemShortMessage;

    /**
     * Additional data for an error.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalData() {
        return additionalData;
    }

    /**
     * Sets the value of the additionalData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getAdditionalData()
     */
    public void setAdditionalData(String value) {
        this.additionalData = value;
    }

    /**
     * Additional info for an error in user language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getAdditionalInfo()
     */
    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

    /**
     * The class which creates the error.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorClass() {
        return errorClass;
    }

    /**
     * Sets the value of the errorClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getErrorClass()
     */
    public void setErrorClass(String value) {
        this.errorClass = value;
    }

    /**
     * The error code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getErrorCode()
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * The complete message text for the error with additional info in user language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullMessage() {
        return fullMessage;
    }

    /**
     * Sets the value of the fullMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getFullMessage()
     */
    public void setFullMessage(String value) {
        this.fullMessage = value;
    }

    /**
     * Language for messages.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getLanguage()
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * The complete message text for the error in user language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMessage()
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * The short info text for the error in user language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortMessage() {
        return shortMessage;
    }

    /**
     * Sets the value of the shortMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getShortMessage()
     */
    public void setShortMessage(String value) {
        this.shortMessage = value;
    }

    /**
     * The complete message text for the error with
     * additional info in system language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemFullMessage() {
        return systemFullMessage;
    }

    /**
     * Sets the value of the systemFullMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getSystemFullMessage()
     */
    public void setSystemFullMessage(String value) {
        this.systemFullMessage = value;
    }

    /**
     * The complete message text for the error in system language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemMessage() {
        return systemMessage;
    }

    /**
     * Sets the value of the systemMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getSystemMessage()
     */
    public void setSystemMessage(String value) {
        this.systemMessage = value;
    }

    /**
     * The short info text for the error in system language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemShortMessage() {
        return systemShortMessage;
    }

    /**
     * Sets the value of the systemShortMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getSystemShortMessage()
     */
    public void setSystemShortMessage(String value) {
        this.systemShortMessage = value;
    }

}
