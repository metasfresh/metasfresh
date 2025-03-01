
package at.erpel.schemas._1p0.documents.extensions.edifact;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for REMADVListLineItemExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="REMADVListLineItemExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BusinessProcessID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocumentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocumentNameDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocumentNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumIDType"/&gt;
 *         &lt;element name="DocumentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Customer" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}BusinessEntityType" minOccurs="0"/&gt;
 *         &lt;element name="DeliveryPoint" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}BusinessEntityType" minOccurs="0"/&gt;
 *         &lt;element name="Payee" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}BusinessEntityType" minOccurs="0"/&gt;
 *         &lt;element name="Supplier" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}BusinessEntityType" minOccurs="0"/&gt;
 *         &lt;element name="RelatedDates" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}BookingDate" minOccurs="0"/&gt;
 *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}InvoiceSettlementDate" minOccurs="0"/&gt;
 *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PaymentDueDate" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="RelatedReferences" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AdditionalReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MonetaryAmounts" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="InvoiceNetAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
 *                   &lt;element name="InvoiceGrossAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
 *                   &lt;element name="CommissionAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
 *                   &lt;element name="PaymentDiscountAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
 *                   &lt;element name="RemittedAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
 *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Adjustment" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "REMADVListLineItemExtensionType", propOrder = {
    "businessProcessID",
    "documentName",
    "documentNameDescription",
    "documentNumber",
    "documentDate",
    "customer",
    "deliveryPoint",
    "payee",
    "supplier",
    "relatedDates",
    "relatedReferences",
    "monetaryAmounts"
})
public class REMADVListLineItemExtensionType {

    @XmlElement(name = "BusinessProcessID")
    protected String businessProcessID;
    @XmlElement(name = "DocumentName")
    protected String documentName;
    @XmlElement(name = "DocumentNameDescription")
    protected String documentNameDescription;
    @XmlElement(name = "DocumentNumber", required = true)
    protected String documentNumber;
    @XmlElement(name = "DocumentDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar documentDate;
    @XmlElement(name = "Customer")
    protected BusinessEntityType customer;
    @XmlElement(name = "DeliveryPoint")
    protected BusinessEntityType deliveryPoint;
    @XmlElement(name = "Payee")
    protected BusinessEntityType payee;
    @XmlElement(name = "Supplier")
    protected BusinessEntityType supplier;
    @XmlElement(name = "RelatedDates")
    protected REMADVListLineItemExtensionType.RelatedDates relatedDates;
    @XmlElement(name = "RelatedReferences")
    protected REMADVListLineItemExtensionType.RelatedReferences relatedReferences;
    @XmlElement(name = "MonetaryAmounts")
    protected REMADVListLineItemExtensionType.MonetaryAmounts monetaryAmounts;

    /**
     * Gets the value of the businessProcessID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessProcessID() {
        return businessProcessID;
    }

    /**
     * Sets the value of the businessProcessID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessProcessID(String value) {
        this.businessProcessID = value;
    }

    /**
     * Gets the value of the documentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the value of the documentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentName(String value) {
        this.documentName = value;
    }

    /**
     * Gets the value of the documentNameDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentNameDescription() {
        return documentNameDescription;
    }

    /**
     * Sets the value of the documentNameDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentNameDescription(String value) {
        this.documentNameDescription = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentNumber(String value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDocumentDate(XMLGregorianCalendar value) {
        this.documentDate = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessEntityType }
     *     
     */
    public BusinessEntityType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessEntityType }
     *     
     */
    public void setCustomer(BusinessEntityType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the deliveryPoint property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessEntityType }
     *     
     */
    public BusinessEntityType getDeliveryPoint() {
        return deliveryPoint;
    }

    /**
     * Sets the value of the deliveryPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessEntityType }
     *     
     */
    public void setDeliveryPoint(BusinessEntityType value) {
        this.deliveryPoint = value;
    }

    /**
     * Gets the value of the payee property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessEntityType }
     *     
     */
    public BusinessEntityType getPayee() {
        return payee;
    }

    /**
     * Sets the value of the payee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessEntityType }
     *     
     */
    public void setPayee(BusinessEntityType value) {
        this.payee = value;
    }

    /**
     * Gets the value of the supplier property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessEntityType }
     *     
     */
    public BusinessEntityType getSupplier() {
        return supplier;
    }

    /**
     * Sets the value of the supplier property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessEntityType }
     *     
     */
    public void setSupplier(BusinessEntityType value) {
        this.supplier = value;
    }

    /**
     * Gets the value of the relatedDates property.
     * 
     * @return
     *     possible object is
     *     {@link REMADVListLineItemExtensionType.RelatedDates }
     *     
     */
    public REMADVListLineItemExtensionType.RelatedDates getRelatedDates() {
        return relatedDates;
    }

    /**
     * Sets the value of the relatedDates property.
     * 
     * @param value
     *     allowed object is
     *     {@link REMADVListLineItemExtensionType.RelatedDates }
     *     
     */
    public void setRelatedDates(REMADVListLineItemExtensionType.RelatedDates value) {
        this.relatedDates = value;
    }

    /**
     * Gets the value of the relatedReferences property.
     * 
     * @return
     *     possible object is
     *     {@link REMADVListLineItemExtensionType.RelatedReferences }
     *     
     */
    public REMADVListLineItemExtensionType.RelatedReferences getRelatedReferences() {
        return relatedReferences;
    }

    /**
     * Sets the value of the relatedReferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link REMADVListLineItemExtensionType.RelatedReferences }
     *     
     */
    public void setRelatedReferences(REMADVListLineItemExtensionType.RelatedReferences value) {
        this.relatedReferences = value;
    }

    /**
     * Gets the value of the monetaryAmounts property.
     * 
     * @return
     *     possible object is
     *     {@link REMADVListLineItemExtensionType.MonetaryAmounts }
     *     
     */
    public REMADVListLineItemExtensionType.MonetaryAmounts getMonetaryAmounts() {
        return monetaryAmounts;
    }

    /**
     * Sets the value of the monetaryAmounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link REMADVListLineItemExtensionType.MonetaryAmounts }
     *     
     */
    public void setMonetaryAmounts(REMADVListLineItemExtensionType.MonetaryAmounts value) {
        this.monetaryAmounts = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="InvoiceNetAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
     *         &lt;element name="InvoiceGrossAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
     *         &lt;element name="CommissionAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
     *         &lt;element name="PaymentDiscountAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
     *         &lt;element name="RemittedAmount" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}MonetaryAmountType" minOccurs="0"/&gt;
     *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Adjustment" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "invoiceNetAmount",
        "invoiceGrossAmount",
        "commissionAmount",
        "paymentDiscountAmount",
        "remittedAmount",
        "adjustment"
    })
    public static class MonetaryAmounts {

        @XmlElement(name = "InvoiceNetAmount")
        protected MonetaryAmountType invoiceNetAmount;
        @XmlElement(name = "InvoiceGrossAmount")
        protected MonetaryAmountType invoiceGrossAmount;
        @XmlElement(name = "CommissionAmount")
        protected MonetaryAmountType commissionAmount;
        @XmlElement(name = "PaymentDiscountAmount")
        protected MonetaryAmountType paymentDiscountAmount;
        @XmlElement(name = "RemittedAmount")
        protected MonetaryAmountType remittedAmount;
        @XmlElement(name = "Adjustment")
        protected List<AdjustmentType> adjustment;

        /**
         * Gets the value of the invoiceNetAmount property.
         * 
         * @return
         *     possible object is
         *     {@link MonetaryAmountType }
         *     
         */
        public MonetaryAmountType getInvoiceNetAmount() {
            return invoiceNetAmount;
        }

        /**
         * Sets the value of the invoiceNetAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link MonetaryAmountType }
         *     
         */
        public void setInvoiceNetAmount(MonetaryAmountType value) {
            this.invoiceNetAmount = value;
        }

        /**
         * Gets the value of the invoiceGrossAmount property.
         * 
         * @return
         *     possible object is
         *     {@link MonetaryAmountType }
         *     
         */
        public MonetaryAmountType getInvoiceGrossAmount() {
            return invoiceGrossAmount;
        }

        /**
         * Sets the value of the invoiceGrossAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link MonetaryAmountType }
         *     
         */
        public void setInvoiceGrossAmount(MonetaryAmountType value) {
            this.invoiceGrossAmount = value;
        }

        /**
         * Gets the value of the commissionAmount property.
         * 
         * @return
         *     possible object is
         *     {@link MonetaryAmountType }
         *     
         */
        public MonetaryAmountType getCommissionAmount() {
            return commissionAmount;
        }

        /**
         * Sets the value of the commissionAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link MonetaryAmountType }
         *     
         */
        public void setCommissionAmount(MonetaryAmountType value) {
            this.commissionAmount = value;
        }

        /**
         * Gets the value of the paymentDiscountAmount property.
         * 
         * @return
         *     possible object is
         *     {@link MonetaryAmountType }
         *     
         */
        public MonetaryAmountType getPaymentDiscountAmount() {
            return paymentDiscountAmount;
        }

        /**
         * Sets the value of the paymentDiscountAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link MonetaryAmountType }
         *     
         */
        public void setPaymentDiscountAmount(MonetaryAmountType value) {
            this.paymentDiscountAmount = value;
        }

        /**
         * Gets the value of the remittedAmount property.
         * 
         * @return
         *     possible object is
         *     {@link MonetaryAmountType }
         *     
         */
        public MonetaryAmountType getRemittedAmount() {
            return remittedAmount;
        }

        /**
         * Sets the value of the remittedAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link MonetaryAmountType }
         *     
         */
        public void setRemittedAmount(MonetaryAmountType value) {
            this.remittedAmount = value;
        }

        /**
         * An adjustment to a monetary amount.Gets the value of the adjustment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the adjustment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdjustment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AdjustmentType }
         * 
         * 
         */
        public List<AdjustmentType> getAdjustment() {
            if (adjustment == null) {
                adjustment = new ArrayList<AdjustmentType>();
            }
            return this.adjustment;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}BookingDate" minOccurs="0"/&gt;
     *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}InvoiceSettlementDate" minOccurs="0"/&gt;
     *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PaymentDueDate" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bookingDate",
        "invoiceSettlementDate",
        "paymentDueDate"
    })
    public static class RelatedDates {

        @XmlElement(name = "BookingDate")
        protected ExtendedDateType bookingDate;
        @XmlElement(name = "InvoiceSettlementDate")
        protected ExtendedDateType invoiceSettlementDate;
        @XmlElement(name = "PaymentDueDate")
        protected ExtendedDateType paymentDueDate;

        /**
         * Date/time at which the invoice was paid.
         * 
         * @return
         *     possible object is
         *     {@link ExtendedDateType }
         *     
         */
        public ExtendedDateType getBookingDate() {
            return bookingDate;
        }

        /**
         * Sets the value of the bookingDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExtendedDateType }
         *     
         */
        public void setBookingDate(ExtendedDateType value) {
            this.bookingDate = value;
        }

        /**
         * Date/time when the invoice was settled.
         * 
         * @return
         *     possible object is
         *     {@link ExtendedDateType }
         *     
         */
        public ExtendedDateType getInvoiceSettlementDate() {
            return invoiceSettlementDate;
        }

        /**
         * Sets the value of the invoiceSettlementDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExtendedDateType }
         *     
         */
        public void setInvoiceSettlementDate(ExtendedDateType value) {
            this.invoiceSettlementDate = value;
        }

        /**
         * Date/time which describes the last allowed payment date of the invoice.
         * 
         * @return
         *     possible object is
         *     {@link ExtendedDateType }
         *     
         */
        public ExtendedDateType getPaymentDueDate() {
            return paymentDueDate;
        }

        /**
         * Sets the value of the paymentDueDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExtendedDateType }
         *     
         */
        public void setPaymentDueDate(ExtendedDateType value) {
            this.paymentDueDate = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AdditionalReference" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "additionalReference"
    })
    public static class RelatedReferences {

        @XmlElement(name = "AdditionalReference")
        protected List<ReferenceType> additionalReference;

        /**
         * Other references if no dedicated field is available.Gets the value of the additionalReference property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the additionalReference property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdditionalReference().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ReferenceType }
         * 
         * 
         */
        public List<ReferenceType> getAdditionalReference() {
            if (additionalReference == null) {
                additionalReference = new ArrayList<ReferenceType>();
            }
            return this.additionalReference;
        }

    }

}
