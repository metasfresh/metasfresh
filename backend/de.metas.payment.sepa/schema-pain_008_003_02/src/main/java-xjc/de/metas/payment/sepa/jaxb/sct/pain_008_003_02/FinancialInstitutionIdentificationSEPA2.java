
package de.metas.payment.sepa.jaxb.sct.pain_008_003_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FinancialInstitutionIdentificationSEPA2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FinancialInstitutionIdentificationSEPA2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.003.02}RestrictedFinancialIdentificationSEPA"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FinancialInstitutionIdentificationSEPA2", propOrder = {
    "othr"
})
public class FinancialInstitutionIdentificationSEPA2 {

    @XmlElement(name = "Othr", required = true)
    protected RestrictedFinancialIdentificationSEPA othr;

    /**
     * Gets the value of the othr property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictedFinancialIdentificationSEPA }
     *     
     */
    public RestrictedFinancialIdentificationSEPA getOthr() {
        return othr;
    }

    /**
     * Sets the value of the othr property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictedFinancialIdentificationSEPA }
     *     
     */
    public void setOthr(RestrictedFinancialIdentificationSEPA value) {
        this.othr = value;
    }

}
