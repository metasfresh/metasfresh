
package de.metas.payment.sepa.jaxb.sct.pain_008_003_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OthrIdentification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OthrIdentification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.003.02}OthrIdentificationCode"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OthrIdentification", propOrder = {
    "id"
})
public class OthrIdentification {

    @XmlElement(name = "Id", required = true)
    @XmlSchemaType(name = "string")
    protected OthrIdentificationCode id;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link OthrIdentificationCode }
     *     
     */
    public OthrIdentificationCode getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link OthrIdentificationCode }
     *     
     */
    public void setId(OthrIdentificationCode value) {
        this.id = value;
    }

}
