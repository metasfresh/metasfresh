
package de.metas.einvoice.cii.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LineTradeAgreementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineTradeAgreementType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BuyerOrderReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ReferencedDocumentType" minOccurs="0"/&gt;
 *         &lt;element name="GrossPriceProductTradePrice" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradePriceType" minOccurs="0"/&gt;
 *         &lt;element name="NetPriceProductTradePrice" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradePriceType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeAgreementType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "buyerOrderReferencedDocument",
    "grossPriceProductTradePrice",
    "netPriceProductTradePrice"
})
public class LineTradeAgreementType {

    @XmlElement(name = "BuyerOrderReferencedDocument", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected ReferencedDocumentType buyerOrderReferencedDocument;
    @XmlElement(name = "GrossPriceProductTradePrice", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TradePriceType grossPriceProductTradePrice;
    @XmlElement(name = "NetPriceProductTradePrice", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected TradePriceType netPriceProductTradePrice;

    /**
     * Gets the value of the buyerOrderReferencedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public ReferencedDocumentType getBuyerOrderReferencedDocument() {
        return buyerOrderReferencedDocument;
    }

    /**
     * Sets the value of the buyerOrderReferencedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public void setBuyerOrderReferencedDocument(ReferencedDocumentType value) {
        this.buyerOrderReferencedDocument = value;
    }

    /**
     * Gets the value of the grossPriceProductTradePrice property.
     * 
     * @return
     *     possible object is
     *     {@link TradePriceType }
     *     
     */
    public TradePriceType getGrossPriceProductTradePrice() {
        return grossPriceProductTradePrice;
    }

    /**
     * Sets the value of the grossPriceProductTradePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePriceType }
     *     
     */
    public void setGrossPriceProductTradePrice(TradePriceType value) {
        this.grossPriceProductTradePrice = value;
    }

    /**
     * Gets the value of the netPriceProductTradePrice property.
     * 
     * @return
     *     possible object is
     *     {@link TradePriceType }
     *     
     */
    public TradePriceType getNetPriceProductTradePrice() {
        return netPriceProductTradePrice;
    }

    /**
     * Sets the value of the netPriceProductTradePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePriceType }
     *     
     */
    public void setNetPriceProductTradePrice(TradePriceType value) {
        this.netPriceProductTradePrice = value;
    }

}
