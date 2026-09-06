
package de.metas.payment.sepa.jaxb.sct.pain_008_003_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RestrictedPersonIdentificationSchemeNameSEPA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictedPersonIdentificationSchemeNameSEPA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Prtry" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.003.02}IdentificationSchemeNameSEPA"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictedPersonIdentificationSchemeNameSEPA", propOrder = {
    "prtry"
})
public class RestrictedPersonIdentificationSchemeNameSEPA {

    @XmlElement(name = "Prtry", required = true)
    @XmlSchemaType(name = "string")
    protected IdentificationSchemeNameSEPA prtry;

    /**
     * Gets the value of the prtry property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificationSchemeNameSEPA }
     *     
     */
    public IdentificationSchemeNameSEPA getPrtry() {
        return prtry;
    }

    /**
     * Sets the value of the prtry property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificationSchemeNameSEPA }
     *     
     */
    public void setPrtry(IdentificationSchemeNameSEPA value) {
        this.prtry = value;
    }

}
