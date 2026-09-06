
package de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BranchAndFinancialInstitutionIdentification4-CH complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BranchAndFinancialInstitutionIdentification4-CH"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FinInstnId" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}FinancialInstitutionIdentification7-CH"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BranchAndFinancialInstitutionIdentification4-CH", propOrder = {
    "finInstnId"
})
public class BranchAndFinancialInstitutionIdentification4CH {

    @XmlElement(name = "FinInstnId", required = true)
    protected FinancialInstitutionIdentification7CH finInstnId;

    /**
     * Gets the value of the finInstnId property.
     * 
     * @return
     *     possible object is
     *     {@link FinancialInstitutionIdentification7CH }
     *     
     */
    public FinancialInstitutionIdentification7CH getFinInstnId() {
        return finInstnId;
    }

    /**
     * Sets the value of the finInstnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinancialInstitutionIdentification7CH }
     *     
     */
    public void setFinInstnId(FinancialInstitutionIdentification7CH value) {
        this.finInstnId = value;
    }

}
