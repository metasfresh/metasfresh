
package de.metas.einvoice.cii.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HeaderTradeDeliveryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderTradeDeliveryType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ShipToTradeParty" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradePartyType" minOccurs="0"/&gt;
 *         &lt;element name="ActualDeliverySupplyChainEvent" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}SupplyChainEventType" minOccurs="0"/&gt;
 *         &lt;element name="DespatchAdviceReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ReferencedDocumentType" minOccurs="0"/&gt;
 *         &lt;element name="ReceivingAdviceReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ReferencedDocumentType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeDeliveryType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "shipToTradeParty",
    "actualDeliverySupplyChainEvent",
    "despatchAdviceReferencedDocument",
    "receivingAdviceReferencedDocument"
})
public class HeaderTradeDeliveryType {

    @XmlElement(name = "ShipToTradeParty", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TradePartyType shipToTradeParty;
    @XmlElement(name = "ActualDeliverySupplyChainEvent", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected SupplyChainEventType actualDeliverySupplyChainEvent;
    @XmlElement(name = "DespatchAdviceReferencedDocument", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected ReferencedDocumentType despatchAdviceReferencedDocument;
    @XmlElement(name = "ReceivingAdviceReferencedDocument", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected ReferencedDocumentType receivingAdviceReferencedDocument;

    /**
     * Gets the value of the shipToTradeParty property.
     * 
     * @return
     *     possible object is
     *     {@link TradePartyType }
     *     
     */
    public TradePartyType getShipToTradeParty() {
        return shipToTradeParty;
    }

    /**
     * Sets the value of the shipToTradeParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePartyType }
     *     
     */
    public void setShipToTradeParty(TradePartyType value) {
        this.shipToTradeParty = value;
    }

    /**
     * Gets the value of the actualDeliverySupplyChainEvent property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyChainEventType }
     *     
     */
    public SupplyChainEventType getActualDeliverySupplyChainEvent() {
        return actualDeliverySupplyChainEvent;
    }

    /**
     * Sets the value of the actualDeliverySupplyChainEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyChainEventType }
     *     
     */
    public void setActualDeliverySupplyChainEvent(SupplyChainEventType value) {
        this.actualDeliverySupplyChainEvent = value;
    }

    /**
     * Gets the value of the despatchAdviceReferencedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public ReferencedDocumentType getDespatchAdviceReferencedDocument() {
        return despatchAdviceReferencedDocument;
    }

    /**
     * Sets the value of the despatchAdviceReferencedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public void setDespatchAdviceReferencedDocument(ReferencedDocumentType value) {
        this.despatchAdviceReferencedDocument = value;
    }

    /**
     * Gets the value of the receivingAdviceReferencedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public ReferencedDocumentType getReceivingAdviceReferencedDocument() {
        return receivingAdviceReferencedDocument;
    }

    /**
     * Sets the value of the receivingAdviceReferencedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public void setReceivingAdviceReferencedDocument(ReferencedDocumentType value) {
        this.receivingAdviceReferencedDocument = value;
    }

}
