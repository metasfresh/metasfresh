
package de.metas.einvoice.cii.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TradeSettlementPaymentMeansType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeSettlementPaymentMeansType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TypeCode" type="{urn:un:unece:uncefact:data:standard:QualifiedDataType:100}PaymentMeansCodeType"/&gt;
 *         &lt;element name="Information" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}TextType" minOccurs="0"/&gt;
 *         &lt;element name="ApplicableTradeSettlementFinancialCard" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradeSettlementFinancialCardType" minOccurs="0"/&gt;
 *         &lt;element name="PayerPartyDebtorFinancialAccount" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}DebtorFinancialAccountType" minOccurs="0"/&gt;
 *         &lt;element name="PayeePartyCreditorFinancialAccount" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}CreditorFinancialAccountType" minOccurs="0"/&gt;
 *         &lt;element name="PayeeSpecifiedCreditorFinancialInstitution" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}CreditorFinancialInstitutionType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeSettlementPaymentMeansType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "typeCode",
    "information",
    "applicableTradeSettlementFinancialCard",
    "payerPartyDebtorFinancialAccount",
    "payeePartyCreditorFinancialAccount",
    "payeeSpecifiedCreditorFinancialInstitution"
})
public class TradeSettlementPaymentMeansType {

    @XmlElement(name = "TypeCode", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected PaymentMeansCodeType typeCode;
    @XmlElement(name = "Information", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TextType information;
    @XmlElement(name = "ApplicableTradeSettlementFinancialCard", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TradeSettlementFinancialCardType applicableTradeSettlementFinancialCard;
    @XmlElement(name = "PayerPartyDebtorFinancialAccount", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected DebtorFinancialAccountType payerPartyDebtorFinancialAccount;
    @XmlElement(name = "PayeePartyCreditorFinancialAccount", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected CreditorFinancialAccountType payeePartyCreditorFinancialAccount;
    @XmlElement(name = "PayeeSpecifiedCreditorFinancialInstitution", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected CreditorFinancialInstitutionType payeeSpecifiedCreditorFinancialInstitution;

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentMeansCodeType }
     *     
     */
    public PaymentMeansCodeType getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentMeansCodeType }
     *     
     */
    public void setTypeCode(PaymentMeansCodeType value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the information property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getInformation() {
        return information;
    }

    /**
     * Sets the value of the information property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setInformation(TextType value) {
        this.information = value;
    }

    /**
     * Gets the value of the applicableTradeSettlementFinancialCard property.
     * 
     * @return
     *     possible object is
     *     {@link TradeSettlementFinancialCardType }
     *     
     */
    public TradeSettlementFinancialCardType getApplicableTradeSettlementFinancialCard() {
        return applicableTradeSettlementFinancialCard;
    }

    /**
     * Sets the value of the applicableTradeSettlementFinancialCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeSettlementFinancialCardType }
     *     
     */
    public void setApplicableTradeSettlementFinancialCard(TradeSettlementFinancialCardType value) {
        this.applicableTradeSettlementFinancialCard = value;
    }

    /**
     * Gets the value of the payerPartyDebtorFinancialAccount property.
     * 
     * @return
     *     possible object is
     *     {@link DebtorFinancialAccountType }
     *     
     */
    public DebtorFinancialAccountType getPayerPartyDebtorFinancialAccount() {
        return payerPartyDebtorFinancialAccount;
    }

    /**
     * Sets the value of the payerPartyDebtorFinancialAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebtorFinancialAccountType }
     *     
     */
    public void setPayerPartyDebtorFinancialAccount(DebtorFinancialAccountType value) {
        this.payerPartyDebtorFinancialAccount = value;
    }

    /**
     * Gets the value of the payeePartyCreditorFinancialAccount property.
     * 
     * @return
     *     possible object is
     *     {@link CreditorFinancialAccountType }
     *     
     */
    public CreditorFinancialAccountType getPayeePartyCreditorFinancialAccount() {
        return payeePartyCreditorFinancialAccount;
    }

    /**
     * Sets the value of the payeePartyCreditorFinancialAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditorFinancialAccountType }
     *     
     */
    public void setPayeePartyCreditorFinancialAccount(CreditorFinancialAccountType value) {
        this.payeePartyCreditorFinancialAccount = value;
    }

    /**
     * Gets the value of the payeeSpecifiedCreditorFinancialInstitution property.
     * 
     * @return
     *     possible object is
     *     {@link CreditorFinancialInstitutionType }
     *     
     */
    public CreditorFinancialInstitutionType getPayeeSpecifiedCreditorFinancialInstitution() {
        return payeeSpecifiedCreditorFinancialInstitution;
    }

    /**
     * Sets the value of the payeeSpecifiedCreditorFinancialInstitution property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditorFinancialInstitutionType }
     *     
     */
    public void setPayeeSpecifiedCreditorFinancialInstitution(CreditorFinancialInstitutionType value) {
        this.payeeSpecifiedCreditorFinancialInstitution = value;
    }

}
