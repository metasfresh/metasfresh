
package de.metas.einvoice.cii.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HeaderTradeAgreementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderTradeAgreementType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BuyerReference" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}TextType" minOccurs="0"/&gt;
 *         &lt;element name="SellerTradeParty" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradePartyType"/&gt;
 *         &lt;element name="BuyerTradeParty" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradePartyType"/&gt;
 *         &lt;element name="SellerTaxRepresentativeTradeParty" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradePartyType" minOccurs="0"/&gt;
 *         &lt;element name="SellerOrderReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ReferencedDocumentType" minOccurs="0"/&gt;
 *         &lt;element name="BuyerOrderReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ReferencedDocumentType" minOccurs="0"/&gt;
 *         &lt;element name="ContractReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ReferencedDocumentType" minOccurs="0"/&gt;
 *         &lt;element name="AdditionalReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ReferencedDocumentType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SpecifiedProcuringProject" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ProcuringProjectType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeAgreementType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "buyerReference",
    "sellerTradeParty",
    "buyerTradeParty",
    "sellerTaxRepresentativeTradeParty",
    "sellerOrderReferencedDocument",
    "buyerOrderReferencedDocument",
    "contractReferencedDocument",
    "additionalReferencedDocument",
    "specifiedProcuringProject"
})
public class HeaderTradeAgreementType {

    @XmlElement(name = "BuyerReference", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TextType buyerReference;
    @XmlElement(name = "SellerTradeParty", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected TradePartyType sellerTradeParty;
    @XmlElement(name = "BuyerTradeParty", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected TradePartyType buyerTradeParty;
    @XmlElement(name = "SellerTaxRepresentativeTradeParty", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TradePartyType sellerTaxRepresentativeTradeParty;
    @XmlElement(name = "SellerOrderReferencedDocument", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected ReferencedDocumentType sellerOrderReferencedDocument;
    @XmlElement(name = "BuyerOrderReferencedDocument", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected ReferencedDocumentType buyerOrderReferencedDocument;
    @XmlElement(name = "ContractReferencedDocument", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected ReferencedDocumentType contractReferencedDocument;
    @XmlElement(name = "AdditionalReferencedDocument", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected List<ReferencedDocumentType> additionalReferencedDocument;
    @XmlElement(name = "SpecifiedProcuringProject", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected ProcuringProjectType specifiedProcuringProject;

    /**
     * Gets the value of the buyerReference property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getBuyerReference() {
        return buyerReference;
    }

    /**
     * Sets the value of the buyerReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setBuyerReference(TextType value) {
        this.buyerReference = value;
    }

    /**
     * Gets the value of the sellerTradeParty property.
     * 
     * @return
     *     possible object is
     *     {@link TradePartyType }
     *     
     */
    public TradePartyType getSellerTradeParty() {
        return sellerTradeParty;
    }

    /**
     * Sets the value of the sellerTradeParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePartyType }
     *     
     */
    public void setSellerTradeParty(TradePartyType value) {
        this.sellerTradeParty = value;
    }

    /**
     * Gets the value of the buyerTradeParty property.
     * 
     * @return
     *     possible object is
     *     {@link TradePartyType }
     *     
     */
    public TradePartyType getBuyerTradeParty() {
        return buyerTradeParty;
    }

    /**
     * Sets the value of the buyerTradeParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePartyType }
     *     
     */
    public void setBuyerTradeParty(TradePartyType value) {
        this.buyerTradeParty = value;
    }

    /**
     * Gets the value of the sellerTaxRepresentativeTradeParty property.
     * 
     * @return
     *     possible object is
     *     {@link TradePartyType }
     *     
     */
    public TradePartyType getSellerTaxRepresentativeTradeParty() {
        return sellerTaxRepresentativeTradeParty;
    }

    /**
     * Sets the value of the sellerTaxRepresentativeTradeParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePartyType }
     *     
     */
    public void setSellerTaxRepresentativeTradeParty(TradePartyType value) {
        this.sellerTaxRepresentativeTradeParty = value;
    }

    /**
     * Gets the value of the sellerOrderReferencedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public ReferencedDocumentType getSellerOrderReferencedDocument() {
        return sellerOrderReferencedDocument;
    }

    /**
     * Sets the value of the sellerOrderReferencedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public void setSellerOrderReferencedDocument(ReferencedDocumentType value) {
        this.sellerOrderReferencedDocument = value;
    }

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
     * Gets the value of the contractReferencedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public ReferencedDocumentType getContractReferencedDocument() {
        return contractReferencedDocument;
    }

    /**
     * Sets the value of the contractReferencedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public void setContractReferencedDocument(ReferencedDocumentType value) {
        this.contractReferencedDocument = value;
    }

    /**
     * Gets the value of the additionalReferencedDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalReferencedDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalReferencedDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferencedDocumentType }
     * 
     * 
     */
    public List<ReferencedDocumentType> getAdditionalReferencedDocument() {
        if (additionalReferencedDocument == null) {
            additionalReferencedDocument = new ArrayList<ReferencedDocumentType>();
        }
        return this.additionalReferencedDocument;
    }

    /**
     * Gets the value of the specifiedProcuringProject property.
     * 
     * @return
     *     possible object is
     *     {@link ProcuringProjectType }
     *     
     */
    public ProcuringProjectType getSpecifiedProcuringProject() {
        return specifiedProcuringProject;
    }

    /**
     * Sets the value of the specifiedProcuringProject property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcuringProjectType }
     *     
     */
    public void setSpecifiedProcuringProject(ProcuringProjectType value) {
        this.specifiedProcuringProject = value;
    }

}
