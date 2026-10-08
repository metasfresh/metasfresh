
package de.metas.einvoice.cii.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TradePriceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradePriceType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ChargeAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}AmountType"/&gt;
 *         &lt;element name="BasisQuantity" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}QuantityType" minOccurs="0"/&gt;
 *         &lt;element name="AppliedTradeAllowanceCharge" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradeAllowanceChargeType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePriceType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "chargeAmount",
    "basisQuantity",
    "appliedTradeAllowanceCharge"
})
public class TradePriceType {

    @XmlElement(name = "ChargeAmount", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected AmountType chargeAmount;
    @XmlElement(name = "BasisQuantity", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected QuantityType basisQuantity;
    @XmlElement(name = "AppliedTradeAllowanceCharge", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TradeAllowanceChargeType appliedTradeAllowanceCharge;

    /**
     * Gets the value of the chargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getChargeAmount() {
        return chargeAmount;
    }

    /**
     * Sets the value of the chargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setChargeAmount(AmountType value) {
        this.chargeAmount = value;
    }

    /**
     * Gets the value of the basisQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getBasisQuantity() {
        return basisQuantity;
    }

    /**
     * Sets the value of the basisQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setBasisQuantity(QuantityType value) {
        this.basisQuantity = value;
    }

    /**
     * Gets the value of the appliedTradeAllowanceCharge property.
     * 
     * @return
     *     possible object is
     *     {@link TradeAllowanceChargeType }
     *     
     */
    public TradeAllowanceChargeType getAppliedTradeAllowanceCharge() {
        return appliedTradeAllowanceCharge;
    }

    /**
     * Sets the value of the appliedTradeAllowanceCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeAllowanceChargeType }
     *     
     */
    public void setAppliedTradeAllowanceCharge(TradeAllowanceChargeType value) {
        this.appliedTradeAllowanceCharge = value;
    }

}
