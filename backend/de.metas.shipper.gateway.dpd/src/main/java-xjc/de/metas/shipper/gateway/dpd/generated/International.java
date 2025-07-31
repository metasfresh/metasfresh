
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for international complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="international">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="parcelType" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <element name="customsAmount">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *               <maxInclusive value="999999999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customsCurrency">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="3"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customsTerms">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="01"/>
 *               <enumeration value="02"/>
 *               <enumeration value="03"/>
 *               <enumeration value="05"/>
 *               <enumeration value="06"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customsContent">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customsTarif" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="8"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customsPaper" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="20"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customsEnclosure" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="customsInvoice" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="20"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customsInvoiceDate" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="99999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customsAmountParcel" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *               <maxInclusive value="999999999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customsOrigin" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="2"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="linehaul" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="AI"/>
 *               <enumeration value="RO"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="shipMrn" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="20"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="collectiveCustomsClearance" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="invoicePosition" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="6"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="comment1" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="70"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="comment2" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="70"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="commercialInvoiceConsigneeVatNumber" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="20"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="commercialInvoiceConsignee" type="{http://dpd.com/common/service/types/ShipmentService/3.2}address"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "international", propOrder = {
    "parcelType",
    "customsAmount",
    "customsCurrency",
    "customsTerms",
    "customsContent",
    "customsTarif",
    "customsPaper",
    "customsEnclosure",
    "customsInvoice",
    "customsInvoiceDate",
    "customsAmountParcel",
    "customsOrigin",
    "linehaul",
    "shipMrn",
    "collectiveCustomsClearance",
    "invoicePosition",
    "comment1",
    "comment2",
    "commercialInvoiceConsigneeVatNumber",
    "commercialInvoiceConsignee"
})
public class International {

    /**
     * Defines if the type of parcel is "documents" (allowed for air based destination only). Default value is false.
     * 
     */
    protected boolean parcelType;
    /**
     * Defines the customs amount in total without decimal separator (e.g. 14.00 = 1400). For documents it is 0.
     * 
     */
    protected long customsAmount;
    /**
     * Currency code in ISO 4217 alpha-3 format.
     * 
     */
    @XmlElement(required = true)
    protected String customsCurrency;
    /**
     * Declares the customs terms. Possible values are:
     *  01 = DAP, cleared
     *  02 = DDP, delivered duty paid (incl. duties and excl. Taxes
     *  03 = DDP, delivered duty paid (incl duties and taxes)
     *  05 = ex works (EXW)
     *  06 = DAP
     * 
     */
    @XmlElement(required = true)
    protected String customsTerms;
    /**
     * Describes the content of the parcel.
     * 
     */
    @XmlElement(required = true)
    protected String customsContent;
    /**
     * Defines the customs tarif number.
     * 
     */
    protected String customsTarif;
    /**
     * Lists the accompanying documents. The values are to be combined without separator (e.g. "ABG"). Following values are defined:
     * A = commercial invoice
     * B = pro forma invoice
     * C = export declaration
     * D = EUR1
     * E = EUR2
     * F = ATR
     * G = delivery note
     * H = third party billing
     * I = T1 document
     * 
     */
    protected String customsPaper;
    /**
     * Defines if the accompanying documents are at the parcel or not. Default value is false.
     * 
     */
    protected Boolean customsEnclosure;
    /**
     * Defines the invoice number.
     * 
     */
    protected String customsInvoice;
    /**
     * Defines the invoice date (format YYYYMMDD).
     * 
     */
    protected Integer customsInvoiceDate;
    /**
     * Parcel value with two decimal places without separator.
     * 
     */
    protected Long customsAmountParcel;
    /**
     * Origin country in ISO 3166-1 alpha-2 format (e.g. 'DE').
     * 
     */
    protected String customsOrigin;
    /**
     * Defines mode of line haul. Possible values are "AI" for air and "RO" for road.
     * 
     */
    protected String linehaul;
    /**
     * Movement reference number of the electronical export declaration.
     * 
     */
    protected String shipMrn;
    /**
     * Flag for determining collective customs clearance. Default value is false.
     * 
     */
    protected Boolean collectiveCustomsClearance;
    /**
     * Defines the invoice position.
     * 
     */
    protected String invoicePosition;
    /**
     * Comment.
     * 
     */
    protected String comment1;
    /**
     * Second comment.
     * 
     */
    protected String comment2;
    /**
     * Defines the commercial invoice consignee VAT number.
     * 
     */
    protected String commercialInvoiceConsigneeVatNumber;
    /**
     * Contains address data of commercial invoice consignee.
     * 
     */
    @XmlElement(required = true)
    protected Address commercialInvoiceConsignee;

    /**
     * Defines if the type of parcel is "documents" (allowed for air based destination only). Default value is false.
     * 
     */
    public boolean isParcelType() {
        return parcelType;
    }

    /**
     * Sets the value of the parcelType property.
     * 
     */
    public void setParcelType(boolean value) {
        this.parcelType = value;
    }

    /**
     * Defines the customs amount in total without decimal separator (e.g. 14.00 = 1400). For documents it is 0.
     * 
     */
    public long getCustomsAmount() {
        return customsAmount;
    }

    /**
     * Sets the value of the customsAmount property.
     * 
     */
    public void setCustomsAmount(long value) {
        this.customsAmount = value;
    }

    /**
     * Currency code in ISO 4217 alpha-3 format.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsCurrency() {
        return customsCurrency;
    }

    /**
     * Sets the value of the customsCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomsCurrency()
     */
    public void setCustomsCurrency(String value) {
        this.customsCurrency = value;
    }

    /**
     * Declares the customs terms. Possible values are:
     *  01 = DAP, cleared
     *  02 = DDP, delivered duty paid (incl. duties and excl. Taxes
     *  03 = DDP, delivered duty paid (incl duties and taxes)
     *  05 = ex works (EXW)
     *  06 = DAP
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsTerms() {
        return customsTerms;
    }

    /**
     * Sets the value of the customsTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomsTerms()
     */
    public void setCustomsTerms(String value) {
        this.customsTerms = value;
    }

    /**
     * Describes the content of the parcel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsContent() {
        return customsContent;
    }

    /**
     * Sets the value of the customsContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomsContent()
     */
    public void setCustomsContent(String value) {
        this.customsContent = value;
    }

    /**
     * Defines the customs tarif number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsTarif() {
        return customsTarif;
    }

    /**
     * Sets the value of the customsTarif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomsTarif()
     */
    public void setCustomsTarif(String value) {
        this.customsTarif = value;
    }

    /**
     * Lists the accompanying documents. The values are to be combined without separator (e.g. "ABG"). Following values are defined:
     * A = commercial invoice
     * B = pro forma invoice
     * C = export declaration
     * D = EUR1
     * E = EUR2
     * F = ATR
     * G = delivery note
     * H = third party billing
     * I = T1 document
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsPaper() {
        return customsPaper;
    }

    /**
     * Sets the value of the customsPaper property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomsPaper()
     */
    public void setCustomsPaper(String value) {
        this.customsPaper = value;
    }

    /**
     * Defines if the accompanying documents are at the parcel or not. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCustomsEnclosure() {
        return customsEnclosure;
    }

    /**
     * Sets the value of the customsEnclosure property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isCustomsEnclosure()
     */
    public void setCustomsEnclosure(Boolean value) {
        this.customsEnclosure = value;
    }

    /**
     * Defines the invoice number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsInvoice() {
        return customsInvoice;
    }

    /**
     * Sets the value of the customsInvoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomsInvoice()
     */
    public void setCustomsInvoice(String value) {
        this.customsInvoice = value;
    }

    /**
     * Defines the invoice date (format YYYYMMDD).
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCustomsInvoiceDate() {
        return customsInvoiceDate;
    }

    /**
     * Sets the value of the customsInvoiceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getCustomsInvoiceDate()
     */
    public void setCustomsInvoiceDate(Integer value) {
        this.customsInvoiceDate = value;
    }

    /**
     * Parcel value with two decimal places without separator.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCustomsAmountParcel() {
        return customsAmountParcel;
    }

    /**
     * Sets the value of the customsAmountParcel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     * @see #getCustomsAmountParcel()
     */
    public void setCustomsAmountParcel(Long value) {
        this.customsAmountParcel = value;
    }

    /**
     * Origin country in ISO 3166-1 alpha-2 format (e.g. 'DE').
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsOrigin() {
        return customsOrigin;
    }

    /**
     * Sets the value of the customsOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomsOrigin()
     */
    public void setCustomsOrigin(String value) {
        this.customsOrigin = value;
    }

    /**
     * Defines mode of line haul. Possible values are "AI" for air and "RO" for road.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinehaul() {
        return linehaul;
    }

    /**
     * Sets the value of the linehaul property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getLinehaul()
     */
    public void setLinehaul(String value) {
        this.linehaul = value;
    }

    /**
     * Movement reference number of the electronical export declaration.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipMrn() {
        return shipMrn;
    }

    /**
     * Sets the value of the shipMrn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getShipMrn()
     */
    public void setShipMrn(String value) {
        this.shipMrn = value;
    }

    /**
     * Flag for determining collective customs clearance. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCollectiveCustomsClearance() {
        return collectiveCustomsClearance;
    }

    /**
     * Sets the value of the collectiveCustomsClearance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isCollectiveCustomsClearance()
     */
    public void setCollectiveCustomsClearance(Boolean value) {
        this.collectiveCustomsClearance = value;
    }

    /**
     * Defines the invoice position.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoicePosition() {
        return invoicePosition;
    }

    /**
     * Sets the value of the invoicePosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getInvoicePosition()
     */
    public void setInvoicePosition(String value) {
        this.invoicePosition = value;
    }

    /**
     * Comment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment1() {
        return comment1;
    }

    /**
     * Sets the value of the comment1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getComment1()
     */
    public void setComment1(String value) {
        this.comment1 = value;
    }

    /**
     * Second comment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment2() {
        return comment2;
    }

    /**
     * Sets the value of the comment2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getComment2()
     */
    public void setComment2(String value) {
        this.comment2 = value;
    }

    /**
     * Defines the commercial invoice consignee VAT number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommercialInvoiceConsigneeVatNumber() {
        return commercialInvoiceConsigneeVatNumber;
    }

    /**
     * Sets the value of the commercialInvoiceConsigneeVatNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCommercialInvoiceConsigneeVatNumber()
     */
    public void setCommercialInvoiceConsigneeVatNumber(String value) {
        this.commercialInvoiceConsigneeVatNumber = value;
    }

    /**
     * Contains address data of commercial invoice consignee.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getCommercialInvoiceConsignee() {
        return commercialInvoiceConsignee;
    }

    /**
     * Sets the value of the commercialInvoiceConsignee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     * @see #getCommercialInvoiceConsignee()
     */
    public void setCommercialInvoiceConsignee(Address value) {
        this.commercialInvoiceConsignee = value;
    }

}
