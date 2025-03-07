
package at.erpel.schemas._1p0.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentMethodType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentMethodType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Comment" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element ref="{http://erpel.at/schemas/1p0/documents}UniversalBankTransaction"/&gt;
 *           &lt;element ref="{http://erpel.at/schemas/1p0/documents}NoPayment"/&gt;
 *           &lt;element ref="{http://erpel.at/schemas/1p0/documents}DirectDebit"/&gt;
 *           &lt;element ref="{http://erpel.at/schemas/1p0/documents}SEPADirectDebit"/&gt;
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
@XmlType(name = "PaymentMethodType", propOrder = {
    "comment",
    "universalBankTransaction",
    "noPayment",
    "directDebit",
    "sepaDirectDebit"
})
public class PaymentMethodType {

    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "UniversalBankTransaction")
    protected UniversalBankTransactionType universalBankTransaction;
    @XmlElement(name = "NoPayment")
    protected NoPaymentType noPayment;
    @XmlElement(name = "DirectDebit")
    protected DirectDebitType directDebit;
    @XmlElement(name = "SEPADirectDebit")
    protected SEPADirectDebitType sepaDirectDebit;

    /**
     * Free-text comment which may be specified for a payment method.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Used to denote details about a bank transaction, via which this invoice has to be paid.
     * 
     * @return
     *     possible object is
     *     {@link UniversalBankTransactionType }
     *     
     */
    public UniversalBankTransactionType getUniversalBankTransaction() {
        return universalBankTransaction;
    }

    /**
     * Sets the value of the universalBankTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniversalBankTransactionType }
     *     
     */
    public void setUniversalBankTransaction(UniversalBankTransactionType value) {
        this.universalBankTransaction = value;
    }

    /**
     * Indicates that no payment takes place as a result of this invoice.
     * 
     * @return
     *     possible object is
     *     {@link NoPaymentType }
     *     
     */
    public NoPaymentType getNoPayment() {
        return noPayment;
    }

    /**
     * Sets the value of the noPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoPaymentType }
     *     
     */
    public void setNoPayment(NoPaymentType value) {
        this.noPayment = value;
    }

    /**
     * Indicates that a direct debit will take place as a result of this invoice.
     * 
     * @return
     *     possible object is
     *     {@link DirectDebitType }
     *     
     */
    public DirectDebitType getDirectDebit() {
        return directDebit;
    }

    /**
     * Sets the value of the directDebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link DirectDebitType }
     *     
     */
    public void setDirectDebit(DirectDebitType value) {
        this.directDebit = value;
    }

    /**
     * Used to denote details about a SEPA direct debit, via which this invoice has to be paid.
     * 
     * @return
     *     possible object is
     *     {@link SEPADirectDebitType }
     *     
     */
    public SEPADirectDebitType getSEPADirectDebit() {
        return sepaDirectDebit;
    }

    /**
     * Sets the value of the sepaDirectDebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link SEPADirectDebitType }
     *     
     */
    public void setSEPADirectDebit(SEPADirectDebitType value) {
        this.sepaDirectDebit = value;
    }

}
