
package de.metas.banking.camt53.jaxb.camt053_001_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Party6Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Party6Choice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="OrgId" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}OrganisationIdentification4"/&gt;
 *           &lt;element name="PrvtId" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}PersonIdentification5"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party6Choice", propOrder = {
    "orgId",
    "prvtId"
})
public class Party6Choice {

    @XmlElement(name = "OrgId")
    protected OrganisationIdentification4 orgId;
    @XmlElement(name = "PrvtId")
    protected PersonIdentification5 prvtId;

    /**
     * Gets the value of the orgId property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationIdentification4 }
     *     
     */
    public OrganisationIdentification4 getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationIdentification4 }
     *     
     */
    public void setOrgId(OrganisationIdentification4 value) {
        this.orgId = value;
    }

    /**
     * Gets the value of the prvtId property.
     * 
     * @return
     *     possible object is
     *     {@link PersonIdentification5 }
     *     
     */
    public PersonIdentification5 getPrvtId() {
        return prvtId;
    }

    /**
     * Sets the value of the prvtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonIdentification5 }
     *     
     */
    public void setPrvtId(PersonIdentification5 value) {
        this.prvtId = value;
    }

}
