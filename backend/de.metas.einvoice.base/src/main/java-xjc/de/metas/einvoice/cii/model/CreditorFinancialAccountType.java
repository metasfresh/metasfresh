
package de.metas.einvoice.cii.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditorFinancialAccountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditorFinancialAccountType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IBANID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}IDType" minOccurs="0"/&gt;
 *         &lt;element name="AccountName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}TextType" minOccurs="0"/&gt;
 *         &lt;element name="ProprietaryID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}IDType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditorFinancialAccountType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "ibanid",
    "accountName",
    "proprietaryID"
})
public class CreditorFinancialAccountType {

    @XmlElement(name = "IBANID", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected IDType ibanid;
    @XmlElement(name = "AccountName", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TextType accountName;
    @XmlElement(name = "ProprietaryID", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected IDType proprietaryID;

    /**
     * Gets the value of the ibanid property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getIBANID() {
        return ibanid;
    }

    /**
     * Sets the value of the ibanid property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setIBANID(IDType value) {
        this.ibanid = value;
    }

    /**
     * Gets the value of the accountName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getAccountName() {
        return accountName;
    }

    /**
     * Sets the value of the accountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setAccountName(TextType value) {
        this.accountName = value;
    }

    /**
     * Gets the value of the proprietaryID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getProprietaryID() {
        return proprietaryID;
    }

    /**
     * Sets the value of the proprietaryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setProprietaryID(IDType value) {
        this.proprietaryID = value;
    }

}
