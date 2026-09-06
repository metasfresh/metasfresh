
package de.metas.payment.sepa.jaxb.sct.pain_008_003_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartySEPA2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartySEPA2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PrvtId" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.003.02}PersonIdentificationSEPA2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartySEPA2", propOrder = {
    "prvtId"
})
public class PartySEPA2 {

    @XmlElement(name = "PrvtId", required = true)
    protected PersonIdentificationSEPA2 prvtId;

    /**
     * Gets the value of the prvtId property.
     * 
     * @return
     *     possible object is
     *     {@link PersonIdentificationSEPA2 }
     *     
     */
    public PersonIdentificationSEPA2 getPrvtId() {
        return prvtId;
    }

    /**
     * Sets the value of the prvtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonIdentificationSEPA2 }
     *     
     */
    public void setPrvtId(PersonIdentificationSEPA2 value) {
        this.prvtId = value;
    }

}
