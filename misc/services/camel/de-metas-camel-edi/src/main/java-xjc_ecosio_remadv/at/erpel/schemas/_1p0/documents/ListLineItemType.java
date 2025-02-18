
package at.erpel.schemas._1p0.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.erpel.schemas._1p0.documents.ext.ListLineItemExtensionType;


/**
 * <p>Java class for ListLineItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListLineItemType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}PositionNumber"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ShortDescription"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Description" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Contact" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ArticleNumber" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Quantity"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}UnitPrice" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}TaxRate" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}OtherTax" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}DiscountFlag" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ReductionAndSurchargeListLineItemDetails" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Delivery" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}SuppliersDocumentReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}CustomersDocumentReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}AdditionalInformation" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}SalesValue" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ExtraCharge" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}TaxAmount" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}LineItemAmount" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}ListLineItemExtension" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListLineItemType", propOrder = {
    "positionNumber",
    "shortDescription",
    "description",
    "contact",
    "articleNumber",
    "quantity",
    "unitPrice",
    "taxRate",
    "otherTax",
    "discountFlag",
    "reductionAndSurchargeListLineItemDetails",
    "delivery",
    "suppliersDocumentReference",
    "customersDocumentReference",
    "additionalInformation",
    "salesValue",
    "extraCharge",
    "taxAmount",
    "lineItemAmount",
    "listLineItemExtension"
})
public class ListLineItemType {

    @XmlElement(name = "PositionNumber", required = true)
    protected String positionNumber;
    @XmlElement(name = "ShortDescription", required = true)
    protected String shortDescription;
    @XmlElement(name = "Description")
    protected List<DescriptionType> description;
    @XmlElement(name = "Contact")
    protected ContactType contact;
    @XmlElement(name = "ArticleNumber", required = true)
    protected List<ArticleNumberType> articleNumber;
    @XmlElement(name = "Quantity", required = true)
    protected UnitType quantity;
    @XmlElement(name = "UnitPrice")
    protected List<UnitPriceType> unitPrice;
    @XmlElement(name = "TaxRate")
    protected TaxRateType taxRate;
    @XmlElement(name = "OtherTax")
    protected List<OtherTaxType> otherTax;
    @XmlElement(name = "DiscountFlag")
    protected Boolean discountFlag;
    @XmlElement(name = "ReductionAndSurchargeListLineItemDetails")
    protected ReductionAndSurchargeListLineItemDetailsType reductionAndSurchargeListLineItemDetails;
    @XmlElement(name = "Delivery")
    protected DeliveryType delivery;
    @XmlElement(name = "SuppliersDocumentReference")
    protected List<DocumentReferenceType> suppliersDocumentReference;
    @XmlElement(name = "CustomersDocumentReference")
    protected List<DocumentReferenceType> customersDocumentReference;
    @XmlElement(name = "AdditionalInformation")
    protected AdditionalInformationType additionalInformation;
    @XmlElement(name = "SalesValue")
    protected UnitPriceType salesValue;
    @XmlElement(name = "ExtraCharge")
    protected BigDecimal extraCharge;
    @XmlElement(name = "TaxAmount")
    protected BigDecimal taxAmount;
    @XmlElement(name = "LineItemAmount")
    protected BigDecimal lineItemAmount;
    @XmlElement(name = "ListLineItemExtension", namespace = "http://erpel.at/schemas/1p0/documents/ext")
    protected ListLineItemExtensionType listLineItemExtension;

    /**
     * Position number, identifing a certain entry.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositionNumber() {
        return positionNumber;
    }

    /**
     * Sets the value of the positionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositionNumber(String value) {
        this.positionNumber = value;
    }

    /**
     * A short description of the item in free-text form.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
    }

    /**
     * A detailled description of a certain entry in free-text form.Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * Further contact details.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setContact(ContactType value) {
        this.contact = value;
    }

    /**
     * A number uniquely identifying a certain article in the context of the document. The type of the article number is determined by the accompanying attribute.Gets the value of the articleNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the articleNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArticleNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArticleNumberType }
     * 
     * 
     */
    public List<ArticleNumberType> getArticleNumber() {
        if (articleNumber == null) {
            articleNumber = new ArrayList<ArticleNumberType>();
        }
        return this.articleNumber;
    }

    /**
     * The quantity of the given product.
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setQuantity(UnitType value) {
        this.quantity = value;
    }

    /**
     * The price of a single unit.Gets the value of the unitPrice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unitPrice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnitPrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UnitPriceType }
     * 
     * 
     */
    public List<UnitPriceType> getUnitPrice() {
        if (unitPrice == null) {
            unitPrice = new ArrayList<UnitPriceType>();
        }
        return this.unitPrice;
    }

    /**
     * The VAT tax rate applicable to the given list line item.
     * 
     * @return
     *     possible object is
     *     {@link TaxRateType }
     *     
     */
    public TaxRateType getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the value of the taxRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxRateType }
     *     
     */
    public void setTaxRate(TaxRateType value) {
        this.taxRate = value;
    }

    /**
     * Used to provide information about other tax on line item level.Gets the value of the otherTax property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherTax property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherTax().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OtherTaxType }
     * 
     * 
     */
    public List<OtherTaxType> getOtherTax() {
        if (otherTax == null) {
            otherTax = new ArrayList<OtherTaxType>();
        }
        return this.otherTax;
    }

    /**
     * Indicates, whether a discount may be applied to this line item or not.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDiscountFlag() {
        return discountFlag;
    }

    /**
     * Sets the value of the discountFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDiscountFlag(Boolean value) {
        this.discountFlag = value;
    }

    /**
     * Used to specify reductions and surcharges on the list line item. Note that both, reductions and surcharges, are always provided as positive values.
     * 
     * @return
     *     possible object is
     *     {@link ReductionAndSurchargeListLineItemDetailsType }
     *     
     */
    public ReductionAndSurchargeListLineItemDetailsType getReductionAndSurchargeListLineItemDetails() {
        return reductionAndSurchargeListLineItemDetails;
    }

    /**
     * Sets the value of the reductionAndSurchargeListLineItemDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReductionAndSurchargeListLineItemDetailsType }
     *     
     */
    public void setReductionAndSurchargeListLineItemDetails(ReductionAndSurchargeListLineItemDetailsType value) {
        this.reductionAndSurchargeListLineItemDetails = value;
    }

    /**
     * Used to store information about the delivery itself (such as delivery date, delivery times, etc.) and the delivery party.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryType }
     *     
     */
    public DeliveryType getDelivery() {
        return delivery;
    }

    /**
     * Sets the value of the delivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryType }
     *     
     */
    public void setDelivery(DeliveryType value) {
        this.delivery = value;
    }

    /**
     * Supplier's reference to a previously exchanged document (e.g. an order which was preceding a despatch advice).Gets the value of the suppliersDocumentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the suppliersDocumentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSuppliersDocumentReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getSuppliersDocumentReference() {
        if (suppliersDocumentReference == null) {
            suppliersDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.suppliersDocumentReference;
    }

    /**
     * Customer's reference to a previously exchanged document (e.g. an order which was preceding a despatch advice).Gets the value of the customersDocumentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customersDocumentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomersDocumentReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getCustomersDocumentReference() {
        if (customersDocumentReference == null) {
            customersDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.customersDocumentReference;
    }

    /**
     * Used to provide additional information about the line item.
     * 
     * @return
     *     possible object is
     *     {@link AdditionalInformationType }
     *     
     */
    public AdditionalInformationType getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * Sets the value of the additionalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdditionalInformationType }
     *     
     */
    public void setAdditionalInformation(AdditionalInformationType value) {
        this.additionalInformation = value;
    }

    /**
     * The sales value of the given list line item.
     * 
     * @return
     *     possible object is
     *     {@link UnitPriceType }
     *     
     */
    public UnitPriceType getSalesValue() {
        return salesValue;
    }

    /**
     * Sets the value of the salesValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitPriceType }
     *     
     */
    public void setSalesValue(UnitPriceType value) {
        this.salesValue = value;
    }

    /**
     * Extra charge for the given list line item.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExtraCharge() {
        return extraCharge;
    }

    /**
     * Sets the value of the extraCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExtraCharge(BigDecimal value) {
        this.extraCharge = value;
    }

    /**
     * The tax amount for this line item (gross - net = tax amount)
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTaxAmount(BigDecimal value) {
        this.taxAmount = value;
    }

    /**
     * The overall amount for this line item (net value).
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLineItemAmount() {
        return lineItemAmount;
    }

    /**
     * Sets the value of the lineItemAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLineItemAmount(BigDecimal value) {
        this.lineItemAmount = value;
    }

    /**
     * Gets the value of the listLineItemExtension property.
     * 
     * @return
     *     possible object is
     *     {@link ListLineItemExtensionType }
     *     
     */
    public ListLineItemExtensionType getListLineItemExtension() {
        return listLineItemExtension;
    }

    /**
     * Sets the value of the listLineItemExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListLineItemExtensionType }
     *     
     */
    public void setListLineItemExtension(ListLineItemExtensionType value) {
        this.listLineItemExtension = value;
    }

}
