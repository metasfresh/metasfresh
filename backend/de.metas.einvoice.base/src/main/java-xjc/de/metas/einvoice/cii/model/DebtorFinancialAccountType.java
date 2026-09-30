
package de.metas.einvoice.cii.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebtorFinancialAccountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebtorFinancialAccountType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IBANID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}IDType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DebtorFinancialAccountType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "ibanid"
})
public class DebtorFinancialAccountType {

    @XmlElement(name = "IBANID", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected IDType ibanid;

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

}
