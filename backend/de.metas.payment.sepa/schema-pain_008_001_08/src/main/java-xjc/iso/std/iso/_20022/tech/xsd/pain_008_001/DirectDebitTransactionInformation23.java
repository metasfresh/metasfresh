
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides further details specific to the individual direct debit transaction(s) included in the message.
 * 
 * <p>Java class for DirectDebitTransactionInformation23 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DirectDebitTransactionInformation23"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PmtId" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentIdentification6"/&gt;
 *         &lt;element name="PmtTpInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentTypeInformation29" minOccurs="0"/&gt;
 *         &lt;element name="InstdAmt" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ActiveOrHistoricCurrencyAndAmount"/&gt;
 *         &lt;element name="ChrgBr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ChargeBearerType1Code" minOccurs="0"/&gt;
 *         &lt;element name="DrctDbtTx" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}DirectDebitTransaction10" minOccurs="0"/&gt;
 *         &lt;element name="UltmtCdtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135" minOccurs="0"/&gt;
 *         &lt;element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}BranchAndFinancialInstitutionIdentification6"/&gt;
 *         &lt;element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135"/&gt;
 *         &lt;element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}CashAccount38"/&gt;
 *         &lt;element name="UltmtDbtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135" minOccurs="0"/&gt;
 *         &lt;element name="Purp" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Purpose2Choice" minOccurs="0"/&gt;
 *         &lt;element name="RmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}RemittanceInformation16" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirectDebitTransactionInformation23", propOrder = {
    "pmtId",
    "pmtTpInf",
    "instdAmt",
    "chrgBr",
    "drctDbtTx",
    "ultmtCdtr",
    "dbtrAgt",
    "dbtr",
    "dbtrAcct",
    "ultmtDbtr",
    "purp",
    "rmtInf"
})
@XmlSeeAlso({
    DirectDebitTransactionInformation23SDD.class
})
public class DirectDebitTransactionInformation23 {

    @XmlElement(name = "PmtId", required = true)
    protected PaymentIdentification6 pmtId;
    @XmlElement(name = "PmtTpInf")
    protected PaymentTypeInformation29 pmtTpInf;
    @XmlElement(name = "InstdAmt", required = true)
    protected ActiveOrHistoricCurrencyAndAmount instdAmt;
    @XmlElement(name = "ChrgBr")
    @XmlSchemaType(name = "string")
    protected ChargeBearerType1Code chrgBr;
    @XmlElement(name = "DrctDbtTx")
    protected DirectDebitTransaction10 drctDbtTx;
    @XmlElement(name = "UltmtCdtr")
    protected PartyIdentification135 ultmtCdtr;
    @XmlElement(name = "DbtrAgt", required = true)
    protected BranchAndFinancialInstitutionIdentification6 dbtrAgt;
    @XmlElement(name = "Dbtr", required = true)
    protected PartyIdentification135 dbtr;
    @XmlElement(name = "DbtrAcct", required = true)
    protected CashAccount38 dbtrAcct;
    @XmlElement(name = "UltmtDbtr")
    protected PartyIdentification135 ultmtDbtr;
    @XmlElement(name = "Purp")
    protected Purpose2Choice purp;
    @XmlElement(name = "RmtInf")
    protected RemittanceInformation16 rmtInf;

    /**
     * Gets the value of the pmtId property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentIdentification6 }
     *     
     */
    public PaymentIdentification6 getPmtId() {
        return pmtId;
    }

    /**
     * Sets the value of the pmtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentIdentification6 }
     *     
     */
    public void setPmtId(PaymentIdentification6 value) {
        this.pmtId = value;
    }

    /**
     * Gets the value of the pmtTpInf property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTypeInformation29 }
     *     
     */
    public PaymentTypeInformation29 getPmtTpInf() {
        return pmtTpInf;
    }

    /**
     * Sets the value of the pmtTpInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTypeInformation29 }
     *     
     */
    public void setPmtTpInf(PaymentTypeInformation29 value) {
        this.pmtTpInf = value;
    }

    /**
     * Gets the value of the instdAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getInstdAmt() {
        return instdAmt;
    }

    /**
     * Sets the value of the instdAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setInstdAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.instdAmt = value;
    }

    /**
     * Gets the value of the chrgBr property.
     * 
     * @return
     *     possible object is
     *     {@link ChargeBearerType1Code }
     *     
     */
    public ChargeBearerType1Code getChrgBr() {
        return chrgBr;
    }

    /**
     * Sets the value of the chrgBr property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargeBearerType1Code }
     *     
     */
    public void setChrgBr(ChargeBearerType1Code value) {
        this.chrgBr = value;
    }

    /**
     * Gets the value of the drctDbtTx property.
     * 
     * @return
     *     possible object is
     *     {@link DirectDebitTransaction10 }
     *     
     */
    public DirectDebitTransaction10 getDrctDbtTx() {
        return drctDbtTx;
    }

    /**
     * Sets the value of the drctDbtTx property.
     * 
     * @param value
     *     allowed object is
     *     {@link DirectDebitTransaction10 }
     *     
     */
    public void setDrctDbtTx(DirectDebitTransaction10 value) {
        this.drctDbtTx = value;
    }

    /**
     * Gets the value of the ultmtCdtr property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification135 }
     *     
     */
    public PartyIdentification135 getUltmtCdtr() {
        return ultmtCdtr;
    }

    /**
     * Sets the value of the ultmtCdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification135 }
     *     
     */
    public void setUltmtCdtr(PartyIdentification135 value) {
        this.ultmtCdtr = value;
    }

    /**
     * Gets the value of the dbtrAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification6 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification6 getDbtrAgt() {
        return dbtrAgt;
    }

    /**
     * Sets the value of the dbtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification6 }
     *     
     */
    public void setDbtrAgt(BranchAndFinancialInstitutionIdentification6 value) {
        this.dbtrAgt = value;
    }

    /**
     * Gets the value of the dbtr property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification135 }
     *     
     */
    public PartyIdentification135 getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification135 }
     *     
     */
    public void setDbtr(PartyIdentification135 value) {
        this.dbtr = value;
    }

    /**
     * Gets the value of the dbtrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount38 }
     *     
     */
    public CashAccount38 getDbtrAcct() {
        return dbtrAcct;
    }

    /**
     * Sets the value of the dbtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount38 }
     *     
     */
    public void setDbtrAcct(CashAccount38 value) {
        this.dbtrAcct = value;
    }

    /**
     * Gets the value of the ultmtDbtr property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification135 }
     *     
     */
    public PartyIdentification135 getUltmtDbtr() {
        return ultmtDbtr;
    }

    /**
     * Sets the value of the ultmtDbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification135 }
     *     
     */
    public void setUltmtDbtr(PartyIdentification135 value) {
        this.ultmtDbtr = value;
    }

    /**
     * Gets the value of the purp property.
     * 
     * @return
     *     possible object is
     *     {@link Purpose2Choice }
     *     
     */
    public Purpose2Choice getPurp() {
        return purp;
    }

    /**
     * Sets the value of the purp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Purpose2Choice }
     *     
     */
    public void setPurp(Purpose2Choice value) {
        this.purp = value;
    }

    /**
     * Gets the value of the rmtInf property.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceInformation16 }
     *     
     */
    public RemittanceInformation16 getRmtInf() {
        return rmtInf;
    }

    /**
     * Sets the value of the rmtInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceInformation16 }
     *     
     */
    public void setRmtInf(RemittanceInformation16 value) {
        this.rmtInf = value;
    }

}
