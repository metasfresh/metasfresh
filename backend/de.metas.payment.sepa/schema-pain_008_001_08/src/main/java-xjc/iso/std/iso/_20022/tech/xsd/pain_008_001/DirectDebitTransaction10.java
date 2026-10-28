
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides further specific details on the direct debit transaction and the related mandate.
 * 
 * <p>Java class for DirectDebitTransaction10 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DirectDebitTransaction10"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MndtRltdInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}MandateRelatedInformation14" minOccurs="0"/&gt;
 *         &lt;element name="CdtrSchmeId" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirectDebitTransaction10", propOrder = {
    "mndtRltdInf",
    "cdtrSchmeId"
})
@XmlSeeAlso({
    DirectDebitTransaction10SDD.class
})
public class DirectDebitTransaction10 {

    @XmlElement(name = "MndtRltdInf")
    protected MandateRelatedInformation14 mndtRltdInf;
    @XmlElement(name = "CdtrSchmeId")
    protected PartyIdentification135 cdtrSchmeId;

    /**
     * Gets the value of the mndtRltdInf property.
     * 
     * @return
     *     possible object is
     *     {@link MandateRelatedInformation14 }
     *     
     */
    public MandateRelatedInformation14 getMndtRltdInf() {
        return mndtRltdInf;
    }

    /**
     * Sets the value of the mndtRltdInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link MandateRelatedInformation14 }
     *     
     */
    public void setMndtRltdInf(MandateRelatedInformation14 value) {
        this.mndtRltdInf = value;
    }

    /**
     * Gets the value of the cdtrSchmeId property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification135 }
     *     
     */
    public PartyIdentification135 getCdtrSchmeId() {
        return cdtrSchmeId;
    }

    /**
     * Sets the value of the cdtrSchmeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification135 }
     *     
     */
    public void setCdtrSchmeId(PartyIdentification135 value) {
        this.cdtrSchmeId = value;
    }

}
