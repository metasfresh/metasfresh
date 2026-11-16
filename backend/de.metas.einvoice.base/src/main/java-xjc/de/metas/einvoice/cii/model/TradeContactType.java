
package de.metas.einvoice.cii.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TradeContactType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeContactType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PersonName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}TextType" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}TextType" minOccurs="0"/&gt;
 *         &lt;element name="TelephoneUniversalCommunication" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}UniversalCommunicationType" minOccurs="0"/&gt;
 *         &lt;element name="EmailURIUniversalCommunication" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}UniversalCommunicationType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeContactType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "personName",
    "departmentName",
    "telephoneUniversalCommunication",
    "emailURIUniversalCommunication"
})
public class TradeContactType {

    @XmlElement(name = "PersonName", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TextType personName;
    @XmlElement(name = "DepartmentName", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TextType departmentName;
    @XmlElement(name = "TelephoneUniversalCommunication", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected UniversalCommunicationType telephoneUniversalCommunication;
    @XmlElement(name = "EmailURIUniversalCommunication", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected UniversalCommunicationType emailURIUniversalCommunication;

    /**
     * Gets the value of the personName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getPersonName() {
        return personName;
    }

    /**
     * Sets the value of the personName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setPersonName(TextType value) {
        this.personName = value;
    }

    /**
     * Gets the value of the departmentName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the value of the departmentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDepartmentName(TextType value) {
        this.departmentName = value;
    }

    /**
     * Gets the value of the telephoneUniversalCommunication property.
     * 
     * @return
     *     possible object is
     *     {@link UniversalCommunicationType }
     *     
     */
    public UniversalCommunicationType getTelephoneUniversalCommunication() {
        return telephoneUniversalCommunication;
    }

    /**
     * Sets the value of the telephoneUniversalCommunication property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniversalCommunicationType }
     *     
     */
    public void setTelephoneUniversalCommunication(UniversalCommunicationType value) {
        this.telephoneUniversalCommunication = value;
    }

    /**
     * Gets the value of the emailURIUniversalCommunication property.
     * 
     * @return
     *     possible object is
     *     {@link UniversalCommunicationType }
     *     
     */
    public UniversalCommunicationType getEmailURIUniversalCommunication() {
        return emailURIUniversalCommunication;
    }

    /**
     * Sets the value of the emailURIUniversalCommunication property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniversalCommunicationType }
     *     
     */
    public void setEmailURIUniversalCommunication(UniversalCommunicationType value) {
        this.emailURIUniversalCommunication = value;
    }

}
