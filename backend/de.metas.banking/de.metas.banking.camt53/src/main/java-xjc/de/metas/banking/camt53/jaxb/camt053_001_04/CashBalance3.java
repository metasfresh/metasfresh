
package de.metas.banking.camt53.jaxb.camt053_001_04;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CashBalance3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CashBalance3"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}BalanceType12"/&gt;
 *         &lt;element name="CdtLine" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CreditLine2" minOccurs="0"/&gt;
 *         &lt;element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ActiveOrHistoricCurrencyAndAmount"/&gt;
 *         &lt;element name="CdtDbtInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CreditDebitCode"/&gt;
 *         &lt;element name="Dt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}DateAndDateTimeChoice"/&gt;
 *         &lt;element name="Avlbty" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CashBalanceAvailability2" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashBalance3", propOrder = {
    "tp",
    "cdtLine",
    "amt",
    "cdtDbtInd",
    "dt",
    "avlbty"
})
public class CashBalance3 {

    @XmlElement(name = "Tp", required = true)
    protected BalanceType12 tp;
    @XmlElement(name = "CdtLine")
    protected CreditLine2 cdtLine;
    @XmlElement(name = "Amt", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    @XmlElement(name = "CdtDbtInd", required = true)
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    @XmlElement(name = "Dt", required = true)
    protected DateAndDateTimeChoice dt;
    @XmlElement(name = "Avlbty")
    protected List<CashBalanceAvailability2> avlbty;

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link BalanceType12 }
     *     
     */
    public BalanceType12 getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BalanceType12 }
     *     
     */
    public void setTp(BalanceType12 value) {
        this.tp = value;
    }

    /**
     * Gets the value of the cdtLine property.
     * 
     * @return
     *     possible object is
     *     {@link CreditLine2 }
     *     
     */
    public CreditLine2 getCdtLine() {
        return cdtLine;
    }

    /**
     * Sets the value of the cdtLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditLine2 }
     *     
     */
    public void setCdtLine(CreditLine2 value) {
        this.cdtLine = value;
    }

    /**
     * Gets the value of the amt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.amt = value;
    }

    /**
     * Gets the value of the cdtDbtInd property.
     * 
     * @return
     *     possible object is
     *     {@link CreditDebitCode }
     *     
     */
    public CreditDebitCode getCdtDbtInd() {
        return cdtDbtInd;
    }

    /**
     * Sets the value of the cdtDbtInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditDebitCode }
     *     
     */
    public void setCdtDbtInd(CreditDebitCode value) {
        this.cdtDbtInd = value;
    }

    /**
     * Gets the value of the dt property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public DateAndDateTimeChoice getDt() {
        return dt;
    }

    /**
     * Sets the value of the dt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTimeChoice }
     *     
     */
    public void setDt(DateAndDateTimeChoice value) {
        this.dt = value;
    }

    /**
     * Gets the value of the avlbty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the avlbty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvlbty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CashBalanceAvailability2 }
     * 
     * 
     */
    public List<CashBalanceAvailability2> getAvlbty() {
        if (avlbty == null) {
            avlbty = new ArrayList<CashBalanceAvailability2>();
        }
        return this.avlbty;
    }

}
