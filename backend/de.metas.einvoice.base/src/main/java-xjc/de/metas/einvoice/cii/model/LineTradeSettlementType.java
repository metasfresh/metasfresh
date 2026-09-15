
package de.metas.einvoice.cii.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LineTradeSettlementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineTradeSettlementType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicableTradeTax" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradeTaxType"/&gt;
 *         &lt;element name="BillingSpecifiedPeriod" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}SpecifiedPeriodType" minOccurs="0"/&gt;
 *         &lt;element name="SpecifiedTradeAllowanceCharge" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradeAllowanceChargeType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SpecifiedTradeSettlementLineMonetarySummation" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradeSettlementLineMonetarySummationType"/&gt;
 *         &lt;element name="AdditionalReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ReferencedDocumentType" minOccurs="0"/&gt;
 *         &lt;element name="ReceivableSpecifiedTradeAccountingAccount" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradeAccountingAccountType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeSettlementType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "applicableTradeTax",
    "billingSpecifiedPeriod",
    "specifiedTradeAllowanceCharge",
    "specifiedTradeSettlementLineMonetarySummation",
    "additionalReferencedDocument",
    "receivableSpecifiedTradeAccountingAccount"
})
public class LineTradeSettlementType {

    @XmlElement(name = "ApplicableTradeTax", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected TradeTaxType applicableTradeTax;
    @XmlElement(name = "BillingSpecifiedPeriod", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected SpecifiedPeriodType billingSpecifiedPeriod;
    @XmlElement(name = "SpecifiedTradeAllowanceCharge", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge;
    @XmlElement(name = "SpecifiedTradeSettlementLineMonetarySummation", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected TradeSettlementLineMonetarySummationType specifiedTradeSettlementLineMonetarySummation;
    @XmlElement(name = "AdditionalReferencedDocument", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected ReferencedDocumentType additionalReferencedDocument;
    @XmlElement(name = "ReceivableSpecifiedTradeAccountingAccount", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount;

    /**
     * Gets the value of the applicableTradeTax property.
     * 
     * @return
     *     possible object is
     *     {@link TradeTaxType }
     *     
     */
    public TradeTaxType getApplicableTradeTax() {
        return applicableTradeTax;
    }

    /**
     * Sets the value of the applicableTradeTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeTaxType }
     *     
     */
    public void setApplicableTradeTax(TradeTaxType value) {
        this.applicableTradeTax = value;
    }

    /**
     * Gets the value of the billingSpecifiedPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link SpecifiedPeriodType }
     *     
     */
    public SpecifiedPeriodType getBillingSpecifiedPeriod() {
        return billingSpecifiedPeriod;
    }

    /**
     * Sets the value of the billingSpecifiedPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpecifiedPeriodType }
     *     
     */
    public void setBillingSpecifiedPeriod(SpecifiedPeriodType value) {
        this.billingSpecifiedPeriod = value;
    }

    /**
     * Gets the value of the specifiedTradeAllowanceCharge property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specifiedTradeAllowanceCharge property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecifiedTradeAllowanceCharge().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TradeAllowanceChargeType }
     * 
     * 
     */
    public List<TradeAllowanceChargeType> getSpecifiedTradeAllowanceCharge() {
        if (specifiedTradeAllowanceCharge == null) {
            specifiedTradeAllowanceCharge = new ArrayList<TradeAllowanceChargeType>();
        }
        return this.specifiedTradeAllowanceCharge;
    }

    /**
     * Gets the value of the specifiedTradeSettlementLineMonetarySummation property.
     * 
     * @return
     *     possible object is
     *     {@link TradeSettlementLineMonetarySummationType }
     *     
     */
    public TradeSettlementLineMonetarySummationType getSpecifiedTradeSettlementLineMonetarySummation() {
        return specifiedTradeSettlementLineMonetarySummation;
    }

    /**
     * Sets the value of the specifiedTradeSettlementLineMonetarySummation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeSettlementLineMonetarySummationType }
     *     
     */
    public void setSpecifiedTradeSettlementLineMonetarySummation(TradeSettlementLineMonetarySummationType value) {
        this.specifiedTradeSettlementLineMonetarySummation = value;
    }

    /**
     * Gets the value of the additionalReferencedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public ReferencedDocumentType getAdditionalReferencedDocument() {
        return additionalReferencedDocument;
    }

    /**
     * Sets the value of the additionalReferencedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public void setAdditionalReferencedDocument(ReferencedDocumentType value) {
        this.additionalReferencedDocument = value;
    }

    /**
     * Gets the value of the receivableSpecifiedTradeAccountingAccount property.
     * 
     * @return
     *     possible object is
     *     {@link TradeAccountingAccountType }
     *     
     */
    public TradeAccountingAccountType getReceivableSpecifiedTradeAccountingAccount() {
        return receivableSpecifiedTradeAccountingAccount;
    }

    /**
     * Sets the value of the receivableSpecifiedTradeAccountingAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeAccountingAccountType }
     *     
     */
    public void setReceivableSpecifiedTradeAccountingAccount(TradeAccountingAccountType value) {
        this.receivableSpecifiedTradeAccountingAccount = value;
    }

}
