
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Bundles general shipment data.
 * 
 * <p>Java class for generalShipmentData complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="generalShipmentData">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="mpsId" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="25"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="cUser" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="10"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="mpsCustomerReferenceNumber1" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="mpsCustomerReferenceNumber2" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="mpsCustomerReferenceNumber3" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="mpsCustomerReferenceNumber4" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="identificationNumber" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="sendingDepot">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="4"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="product">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="CL"/>
 *               <enumeration value="E830"/>
 *               <enumeration value="E10"/>
 *               <enumeration value="E12"/>
 *               <enumeration value="E18"/>
 *               <enumeration value="IE2"/>
 *               <enumeration value="PL"/>
 *               <enumeration value="PL+"/>
 *               <enumeration value="MAIL"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="mpsCompleteDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="mpsCompleteDeliveryLabel" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="mpsVolume" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *               <maxInclusive value="999999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="mpsWeight" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *               <maxInclusive value="99999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="mpsExpectedSendingDate" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <pattern value="[2][0-9]{3}([0][0-9]|[1][0-2])(0[1-9]|[12][0-9]|3[01])"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="mpsExpectedSendingTime" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <pattern value="(([1][0-9]|[2][0-3]|[0][0-9])([0-5][0-9])([0-5][0-9]))|([0-9]([0-5][0-9])([0-5][0-9]))"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="sender" type="{http://dpd.com/common/service/types/ShipmentService/3.2}address"/>
 *         <element name="recipient" type="{http://dpd.com/common/service/types/ShipmentService/3.2}address"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generalShipmentData", propOrder = {
    "mpsId",
    "cUser",
    "mpsCustomerReferenceNumber1",
    "mpsCustomerReferenceNumber2",
    "mpsCustomerReferenceNumber3",
    "mpsCustomerReferenceNumber4",
    "identificationNumber",
    "sendingDepot",
    "product",
    "mpsCompleteDelivery",
    "mpsCompleteDeliveryLabel",
    "mpsVolume",
    "mpsWeight",
    "mpsExpectedSendingDate",
    "mpsExpectedSendingTime",
    "sender",
    "recipient"
})
public class GeneralShipmentData {

    /**
     * The shipment number for consignment data. The shipment number is only accepted if the parcel label number is allocated by customer.
     * It starts with one of "MPS", "EXP" or "B2C", the last eight ciphers are the date in format yyyyMMdd.
     * 
     */
    protected String mpsId;
    /**
     * User ID of the person who made the entry.
     * 
     */
    protected String cUser;
    /**
     * Consignment customer reference number 1 (maximal length 35), also customer reference number for collection request orders (maximal length 50).
     * 
     */
    protected String mpsCustomerReferenceNumber1;
    /**
     * Consignment customer reference number 2.
     * 
     */
    protected String mpsCustomerReferenceNumber2;
    /**
     * Consignment customer reference number 3.
     * 
     */
    protected String mpsCustomerReferenceNumber3;
    /**
     * Consignment customer reference number 4.
     * 
     */
    protected String mpsCustomerReferenceNumber4;
    /**
     * Serves as unique alphanumeric key of the shipment used by customer. Can obtain up to 999 ciphers.
     * 
     */
    protected String identificationNumber;
    /**
     * Sending depot for consignment, ordering depot for collection request, customer's depot for pickup information or creating/sending depot for dangerous goods. 4 alphanumeric places, including leading zeros, e.g. 0163.
     * 
     */
    @XmlElement(required = true)
    protected String sendingDepot;
    /**
     * Selection of product, exactly one per shipment, mandatory for consignment data. Possible values are:
     * CL = DPD CLASSIC
     * E830 = DPD 8:30
     * E10 = DPD 10:00
     * E12 = DPD 12:00
     * E18 = DPD 18:00
     * IE2 = DPD EXPRESS
     * PL = DPD PARCELLetter
     * PL+ = DPD PARCELLetterPlus
     * MAIL = DPD International Mail
     * 
     */
    @XmlElement(required = true)
    protected String product;
    /**
     * Defines if the shipment should be sent as complete delivery. Mandatory for COD. Default value is false.
     * 
     */
    protected Boolean mpsCompleteDelivery;
    /**
     * Defines if the label for complete delivery is printed for pickup. Default value is false.
     * 
     */
    protected Boolean mpsCompleteDeliveryLabel;
    /**
     * Volume per consignment in cm3 (without decimal places).
     * 
     */
    protected Long mpsVolume;
    /**
     * Shipment weight in grams rounded in 10 gram units without decimal separator (e.g. 300 equals 3kg).
     * 
     */
    protected Long mpsWeight;
    /**
     * Date when the shipment is expected to be transferred to the system. Format is YYYYMMDD.
     * 
     */
    protected String mpsExpectedSendingDate;
    /**
     * Time when the shipment is expected to be transferred to the system. Format is HHMMSS.
     * 
     */
    protected String mpsExpectedSendingTime;
    /**
     * Consignment sender's address, collection request customer's address or pickup information customer's address.
     * 
     */
    @XmlElement(required = true)
    protected Address sender;
    /**
     * Address of the recipient. For parcel shop delivery address of the real recipient.
     * 
     */
    @XmlElement(required = true)
    protected Address recipient;

    /**
     * The shipment number for consignment data. The shipment number is only accepted if the parcel label number is allocated by customer.
     * It starts with one of "MPS", "EXP" or "B2C", the last eight ciphers are the date in format yyyyMMdd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMpsId() {
        return mpsId;
    }

    /**
     * Sets the value of the mpsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMpsId()
     */
    public void setMpsId(String value) {
        this.mpsId = value;
    }

    /**
     * User ID of the person who made the entry.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUser() {
        return cUser;
    }

    /**
     * Sets the value of the cUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCUser()
     */
    public void setCUser(String value) {
        this.cUser = value;
    }

    /**
     * Consignment customer reference number 1 (maximal length 35), also customer reference number for collection request orders (maximal length 50).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMpsCustomerReferenceNumber1() {
        return mpsCustomerReferenceNumber1;
    }

    /**
     * Sets the value of the mpsCustomerReferenceNumber1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMpsCustomerReferenceNumber1()
     */
    public void setMpsCustomerReferenceNumber1(String value) {
        this.mpsCustomerReferenceNumber1 = value;
    }

    /**
     * Consignment customer reference number 2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMpsCustomerReferenceNumber2() {
        return mpsCustomerReferenceNumber2;
    }

    /**
     * Sets the value of the mpsCustomerReferenceNumber2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMpsCustomerReferenceNumber2()
     */
    public void setMpsCustomerReferenceNumber2(String value) {
        this.mpsCustomerReferenceNumber2 = value;
    }

    /**
     * Consignment customer reference number 3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMpsCustomerReferenceNumber3() {
        return mpsCustomerReferenceNumber3;
    }

    /**
     * Sets the value of the mpsCustomerReferenceNumber3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMpsCustomerReferenceNumber3()
     */
    public void setMpsCustomerReferenceNumber3(String value) {
        this.mpsCustomerReferenceNumber3 = value;
    }

    /**
     * Consignment customer reference number 4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMpsCustomerReferenceNumber4() {
        return mpsCustomerReferenceNumber4;
    }

    /**
     * Sets the value of the mpsCustomerReferenceNumber4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMpsCustomerReferenceNumber4()
     */
    public void setMpsCustomerReferenceNumber4(String value) {
        this.mpsCustomerReferenceNumber4 = value;
    }

    /**
     * Serves as unique alphanumeric key of the shipment used by customer. Can obtain up to 999 ciphers.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * Sets the value of the identificationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getIdentificationNumber()
     */
    public void setIdentificationNumber(String value) {
        this.identificationNumber = value;
    }

    /**
     * Sending depot for consignment, ordering depot for collection request, customer's depot for pickup information or creating/sending depot for dangerous goods. 4 alphanumeric places, including leading zeros, e.g. 0163.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendingDepot() {
        return sendingDepot;
    }

    /**
     * Sets the value of the sendingDepot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getSendingDepot()
     */
    public void setSendingDepot(String value) {
        this.sendingDepot = value;
    }

    /**
     * Selection of product, exactly one per shipment, mandatory for consignment data. Possible values are:
     * CL = DPD CLASSIC
     * E830 = DPD 8:30
     * E10 = DPD 10:00
     * E12 = DPD 12:00
     * E18 = DPD 18:00
     * IE2 = DPD EXPRESS
     * PL = DPD PARCELLetter
     * PL+ = DPD PARCELLetterPlus
     * MAIL = DPD International Mail
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getProduct()
     */
    public void setProduct(String value) {
        this.product = value;
    }

    /**
     * Defines if the shipment should be sent as complete delivery. Mandatory for COD. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMpsCompleteDelivery() {
        return mpsCompleteDelivery;
    }

    /**
     * Sets the value of the mpsCompleteDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isMpsCompleteDelivery()
     */
    public void setMpsCompleteDelivery(Boolean value) {
        this.mpsCompleteDelivery = value;
    }

    /**
     * Defines if the label for complete delivery is printed for pickup. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMpsCompleteDeliveryLabel() {
        return mpsCompleteDeliveryLabel;
    }

    /**
     * Sets the value of the mpsCompleteDeliveryLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isMpsCompleteDeliveryLabel()
     */
    public void setMpsCompleteDeliveryLabel(Boolean value) {
        this.mpsCompleteDeliveryLabel = value;
    }

    /**
     * Volume per consignment in cm3 (without decimal places).
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMpsVolume() {
        return mpsVolume;
    }

    /**
     * Sets the value of the mpsVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     * @see #getMpsVolume()
     */
    public void setMpsVolume(Long value) {
        this.mpsVolume = value;
    }

    /**
     * Shipment weight in grams rounded in 10 gram units without decimal separator (e.g. 300 equals 3kg).
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMpsWeight() {
        return mpsWeight;
    }

    /**
     * Sets the value of the mpsWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     * @see #getMpsWeight()
     */
    public void setMpsWeight(Long value) {
        this.mpsWeight = value;
    }

    /**
     * Date when the shipment is expected to be transferred to the system. Format is YYYYMMDD.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMpsExpectedSendingDate() {
        return mpsExpectedSendingDate;
    }

    /**
     * Sets the value of the mpsExpectedSendingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMpsExpectedSendingDate()
     */
    public void setMpsExpectedSendingDate(String value) {
        this.mpsExpectedSendingDate = value;
    }

    /**
     * Time when the shipment is expected to be transferred to the system. Format is HHMMSS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMpsExpectedSendingTime() {
        return mpsExpectedSendingTime;
    }

    /**
     * Sets the value of the mpsExpectedSendingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMpsExpectedSendingTime()
     */
    public void setMpsExpectedSendingTime(String value) {
        this.mpsExpectedSendingTime = value;
    }

    /**
     * Consignment sender's address, collection request customer's address or pickup information customer's address.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     * @see #getSender()
     */
    public void setSender(Address value) {
        this.sender = value;
    }

    /**
     * Address of the recipient. For parcel shop delivery address of the real recipient.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getRecipient() {
        return recipient;
    }

    /**
     * Sets the value of the recipient property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     * @see #getRecipient()
     */
    public void setRecipient(Address value) {
        this.recipient = value;
    }

}
