
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for productAndServiceData complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="productAndServiceData">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="orderType">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="consignment"/>
 *               <enumeration value="collection request order"/>
 *               <enumeration value="pickup information"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="saturdayDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="exWorksDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="guarantee" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="tyres" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="personalDelivery" type="{http://dpd.com/common/service/types/ShipmentService/3.2}personalDelivery" minOccurs="0"/>
 *         <element name="pickup" type="{http://dpd.com/common/service/types/ShipmentService/3.2}pickup" minOccurs="0"/>
 *         <element name="parcelShopDelivery" type="{http://dpd.com/common/service/types/ShipmentService/3.2}parcelShopDelivery" minOccurs="0"/>
 *         <element name="predict" type="{http://dpd.com/common/service/types/ShipmentService/3.2}notification" minOccurs="0"/>
 *         <element name="personalDeliveryNotification" type="{http://dpd.com/common/service/types/ShipmentService/3.2}notification" minOccurs="0"/>
 *         <element name="proactiveNotification" type="{http://dpd.com/common/service/types/ShipmentService/3.2}proactiveNotification" maxOccurs="5" minOccurs="0"/>
 *         <element name="delivery" type="{http://dpd.com/common/service/types/ShipmentService/3.2}delivery" minOccurs="0"/>
 *         <element name="invoiceAddress" type="{http://dpd.com/common/service/types/ShipmentService/3.2}address" minOccurs="0"/>
 *         <element name="countrySpecificService" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productAndServiceData", propOrder = {
    "orderType",
    "saturdayDelivery",
    "exWorksDelivery",
    "guarantee",
    "tyres",
    "personalDelivery",
    "pickup",
    "parcelShopDelivery",
    "predict",
    "personalDeliveryNotification",
    "proactiveNotification",
    "delivery",
    "invoiceAddress",
    "countrySpecificService"
})
public class ProductAndServiceData {

    /**
     * Defines the shipment type.
     * Possible values are:
     * consignment
     * collection request order
     * pickup information
     * 
     */
    @XmlElement(required = true)
    protected String orderType;
    /**
     * Defines if saturday delivery is demanded. Only selectable for product "E12". Default value is false.
     * 
     */
    protected Boolean saturdayDelivery;
    /**
     * Defines if the recipient has to pay the consignment. Default value is false.
     * 
     */
    protected Boolean exWorksDelivery;
    /**
     * Set to true if an international shipment shall use Guarantee, only for products NP and E18 in international parcels
     * 
     */
    protected Boolean guarantee;
    /**
     * Set to true if this consignment contains bulk tyres, only for product NP.
     * 
     */
    protected Boolean tyres;
    /**
     * Contains information for personal delivery.
     * 
     */
    protected PersonalDelivery personalDelivery;
    /**
     * Contains information for pickup consignments, pickup address for collection requests or details for pickup information.
     * 
     */
    protected Pickup pickup;
    /**
     * Contains the necessary information, if a parcel shop delivery is intended.
     * 
     */
    protected ParcelShopDelivery parcelShopDelivery;
    /**
     * Contains data for interactive notification for consignments. Only channel e-mail and SMS is allowed.
     * 
     */
    protected Notification predict;
    /**
     * Contains data for personal delivery notification for consignments.
     * 
     */
    protected Notification personalDeliveryNotification;
    /**
     * Contains information of proactive notification for consignments.
     * 
     */
    protected List<ProactiveNotification> proactiveNotification;
    /**
     * Contains special delivery data for consignments.
     * 
     */
    protected Delivery delivery;
    /**
     * Contains data about invoice recipient if it differs for consignment.
     * 
     */
    protected Address invoiceAddress;
    /**
     * In some relations a specific service code can be set which overwrites the feature selection.
     * 
     */
    protected String countrySpecificService;

    /**
     * Defines the shipment type.
     * Possible values are:
     * consignment
     * collection request order
     * pickup information
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Sets the value of the orderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getOrderType()
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    /**
     * Defines if saturday delivery is demanded. Only selectable for product "E12". Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSaturdayDelivery() {
        return saturdayDelivery;
    }

    /**
     * Sets the value of the saturdayDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isSaturdayDelivery()
     */
    public void setSaturdayDelivery(Boolean value) {
        this.saturdayDelivery = value;
    }

    /**
     * Defines if the recipient has to pay the consignment. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExWorksDelivery() {
        return exWorksDelivery;
    }

    /**
     * Sets the value of the exWorksDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isExWorksDelivery()
     */
    public void setExWorksDelivery(Boolean value) {
        this.exWorksDelivery = value;
    }

    /**
     * Set to true if an international shipment shall use Guarantee, only for products NP and E18 in international parcels
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGuarantee() {
        return guarantee;
    }

    /**
     * Sets the value of the guarantee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isGuarantee()
     */
    public void setGuarantee(Boolean value) {
        this.guarantee = value;
    }

    /**
     * Set to true if this consignment contains bulk tyres, only for product NP.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTyres() {
        return tyres;
    }

    /**
     * Sets the value of the tyres property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isTyres()
     */
    public void setTyres(Boolean value) {
        this.tyres = value;
    }

    /**
     * Contains information for personal delivery.
     * 
     * @return
     *     possible object is
     *     {@link PersonalDelivery }
     *     
     */
    public PersonalDelivery getPersonalDelivery() {
        return personalDelivery;
    }

    /**
     * Sets the value of the personalDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonalDelivery }
     *     
     * @see #getPersonalDelivery()
     */
    public void setPersonalDelivery(PersonalDelivery value) {
        this.personalDelivery = value;
    }

    /**
     * Contains information for pickup consignments, pickup address for collection requests or details for pickup information.
     * 
     * @return
     *     possible object is
     *     {@link Pickup }
     *     
     */
    public Pickup getPickup() {
        return pickup;
    }

    /**
     * Sets the value of the pickup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pickup }
     *     
     * @see #getPickup()
     */
    public void setPickup(Pickup value) {
        this.pickup = value;
    }

    /**
     * Contains the necessary information, if a parcel shop delivery is intended.
     * 
     * @return
     *     possible object is
     *     {@link ParcelShopDelivery }
     *     
     */
    public ParcelShopDelivery getParcelShopDelivery() {
        return parcelShopDelivery;
    }

    /**
     * Sets the value of the parcelShopDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParcelShopDelivery }
     *     
     * @see #getParcelShopDelivery()
     */
    public void setParcelShopDelivery(ParcelShopDelivery value) {
        this.parcelShopDelivery = value;
    }

    /**
     * Contains data for interactive notification for consignments. Only channel e-mail and SMS is allowed.
     * 
     * @return
     *     possible object is
     *     {@link Notification }
     *     
     */
    public Notification getPredict() {
        return predict;
    }

    /**
     * Sets the value of the predict property.
     * 
     * @param value
     *     allowed object is
     *     {@link Notification }
     *     
     * @see #getPredict()
     */
    public void setPredict(Notification value) {
        this.predict = value;
    }

    /**
     * Contains data for personal delivery notification for consignments.
     * 
     * @return
     *     possible object is
     *     {@link Notification }
     *     
     */
    public Notification getPersonalDeliveryNotification() {
        return personalDeliveryNotification;
    }

    /**
     * Sets the value of the personalDeliveryNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Notification }
     *     
     * @see #getPersonalDeliveryNotification()
     */
    public void setPersonalDeliveryNotification(Notification value) {
        this.personalDeliveryNotification = value;
    }

    /**
     * Contains information of proactive notification for consignments.
     * 
     * Gets the value of the proactiveNotification property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the proactiveNotification property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getProactiveNotification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProactiveNotification }
     * </p>
     * 
     * 
     * @return
     *     The value of the proactiveNotification property.
     */
    public List<ProactiveNotification> getProactiveNotification() {
        if (proactiveNotification == null) {
            proactiveNotification = new ArrayList<>();
        }
        return this.proactiveNotification;
    }

    /**
     * Contains special delivery data for consignments.
     * 
     * @return
     *     possible object is
     *     {@link Delivery }
     *     
     */
    public Delivery getDelivery() {
        return delivery;
    }

    /**
     * Sets the value of the delivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Delivery }
     *     
     * @see #getDelivery()
     */
    public void setDelivery(Delivery value) {
        this.delivery = value;
    }

    /**
     * Contains data about invoice recipient if it differs for consignment.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getInvoiceAddress() {
        return invoiceAddress;
    }

    /**
     * Sets the value of the invoiceAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     * @see #getInvoiceAddress()
     */
    public void setInvoiceAddress(Address value) {
        this.invoiceAddress = value;
    }

    /**
     * In some relations a specific service code can be set which overwrites the feature selection.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountrySpecificService() {
        return countrySpecificService;
    }

    /**
     * Sets the value of the countrySpecificService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCountrySpecificService()
     */
    public void setCountrySpecificService(String value) {
        this.countrySpecificService = value;
    }

}
