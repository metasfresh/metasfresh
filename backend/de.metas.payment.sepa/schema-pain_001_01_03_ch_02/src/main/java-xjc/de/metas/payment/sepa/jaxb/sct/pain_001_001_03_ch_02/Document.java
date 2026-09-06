
package de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CstmrCdtTrfInitn" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}CustomerCreditTransferInitiationV03-CH"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "cstmrCdtTrfInitn"
})
public class Document {

    @XmlElement(name = "CstmrCdtTrfInitn", required = true)
    protected CustomerCreditTransferInitiationV03CH cstmrCdtTrfInitn;

    /**
     * Gets the value of the cstmrCdtTrfInitn property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerCreditTransferInitiationV03CH }
     *     
     */
    public CustomerCreditTransferInitiationV03CH getCstmrCdtTrfInitn() {
        return cstmrCdtTrfInitn;
    }

    /**
     * Sets the value of the cstmrCdtTrfInitn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerCreditTransferInitiationV03CH }
     *     
     */
    public void setCstmrCdtTrfInitn(CustomerCreditTransferInitiationV03CH value) {
        this.cstmrCdtTrfInitn = value;
    }

}
