
package iso.std.iso._20022.tech.xsd.pain_008_001;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Scope
 * The CustomerDirectDebitInitiation message is sent by the initiating party to the forwarding agent or creditor agent. It is used to request single or bulk collection(s) of funds from one or various debtor's account(s) for a creditor.
 * Usage
 * The CustomerDirectDebitInitiation message can contain one or more direct debit instructions.
 * The message can be used in a direct or a relay scenario:
 * - In a direct scenario, the message is sent directly to the creditor agent. The creditor agent is the account servicer of the creditor.
 * - In a relay scenario, the message is sent to a forwarding agent. The forwarding agent acts as a concentrating financial institution. It will forward the CustomerDirectDebitInitiation message to the creditor agent.
 * The message can also be used by an initiating party that has authority to send the message on behalf of the creditor. This caters for example for the scenario of a payments factory initiating all payments on behalf of a large corporate.
 * The CustomerDirectDebitInitiation message can be used in domestic and cross-border scenarios.
 * The CustomerDirectDebitInitiation may or may not contain mandate related information, i.e. extracts from a mandate, such as MandateIdentification or DateOfSignature. The CustomerDirectDebitInitiation message must not be considered as a mandate.
 * The CustomerDirectDebitInitiation message must not be used by the creditor agent to execute the direct debit instruction(s). The FIToFICustomerDirectDebit message must be used instead.
 *       
 * 
 * <p>Java class for CustomerDirectDebitInitiationV08 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerDirectDebitInitiationV08"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}GroupHeader83"/&gt;
 *         &lt;element name="PmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentInstruction29" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerDirectDebitInitiationV08", propOrder = {
    "grpHdr",
    "pmtInf"
})
@XmlSeeAlso({
    CustomerDirectDebitInitiationV08SDD.class
})
public class CustomerDirectDebitInitiationV08 {

    @XmlElement(name = "GrpHdr", required = true)
    protected GroupHeader83 grpHdr;
    @XmlElement(name = "PmtInf", required = true)
    protected List<PaymentInstruction29> pmtInf;

    /**
     * Gets the value of the grpHdr property.
     * 
     * @return
     *     possible object is
     *     {@link GroupHeader83 }
     *     
     */
    public GroupHeader83 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupHeader83 }
     *     
     */
    public void setGrpHdr(GroupHeader83 value) {
        this.grpHdr = value;
    }

    /**
     * Gets the value of the pmtInf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pmtInf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPmtInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentInstruction29 }
     * 
     * 
     */
    public List<PaymentInstruction29> getPmtInf() {
        if (pmtInf == null) {
            pmtInf = new ArrayList<PaymentInstruction29>();
        }
        return this.pmtInf;
    }

}
