
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Bundles pickup data.
 * 
 * <p>Java class for pickup complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="pickup">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="tour" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="quantity">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="99999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="date">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="99999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="day">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="6"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="fromTime1" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="2400"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="toTime1" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="2400"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="fromTime2" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="2400"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="toTime2" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="2400"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="extraPickup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="collectionRequestAddress" type="{http://dpd.com/common/service/types/ShipmentService/3.2}address" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pickup", propOrder = {
    "tour",
    "quantity",
    "date",
    "day",
    "fromTime1",
    "toTime1",
    "fromTime2",
    "toTime2",
    "extraPickup",
    "collectionRequestAddress"
})
public class Pickup {

    /**
     * Tour number (between 000 and 999).
     * 
     */
    protected Integer tour;
    /**
     * Quantity of pickup parcels. Mandatory for consignment.
     * 
     */
    protected int quantity;
    /**
     * Pickup date in consignments and collection requests. The collection day for advice customers. The from date for pickup information. Format is YYYYMMDD.
     * It can also be used as pickup date for collection requests, then format is YYMMDD. Mandatory for consignment.
     * 
     */
    protected int date;
    /**
     * Pickup day of week for consignments and pickup information. Allowed values are 0..6 (0=Sunday, 1=Monday, etc.). Mandatory for consignment.
     * 
     */
    protected int day;
    /**
     * From time 1 for consignments and pickup information. Format is hhmm. Mandatory for consignment.
     * 
     */
    protected Integer fromTime1;
    /**
     * Until time 1 for consignments and pickup information. Format is hhmm. Mandatory for consignment.
     * 
     */
    protected Integer toTime1;
    /**
     * From time 2 for consignments and pickup information. Format is hhmm.
     * 
     */
    protected Integer fromTime2;
    /**
     * Until time 2 for consignments and pickup information. Format is hhmm.
     * 
     */
    protected Integer toTime2;
    /**
     * True if extra pickup is requested.
     * 
     */
    protected Boolean extraPickup;
    /**
     * Contains pickup address information for consignments and collection requests. Mandatory for consignment.
     * 
     */
    protected Address collectionRequestAddress;

    /**
     * Tour number (between 000 and 999).
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTour() {
        return tour;
    }

    /**
     * Sets the value of the tour property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getTour()
     */
    public void setTour(Integer value) {
        this.tour = value;
    }

    /**
     * Quantity of pickup parcels. Mandatory for consignment.
     * 
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(int value) {
        this.quantity = value;
    }

    /**
     * Pickup date in consignments and collection requests. The collection day for advice customers. The from date for pickup information. Format is YYYYMMDD.
     * It can also be used as pickup date for collection requests, then format is YYMMDD. Mandatory for consignment.
     * 
     */
    public int getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     */
    public void setDate(int value) {
        this.date = value;
    }

    /**
     * Pickup day of week for consignments and pickup information. Allowed values are 0..6 (0=Sunday, 1=Monday, etc.). Mandatory for consignment.
     * 
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the value of the day property.
     * 
     */
    public void setDay(int value) {
        this.day = value;
    }

    /**
     * From time 1 for consignments and pickup information. Format is hhmm. Mandatory for consignment.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFromTime1() {
        return fromTime1;
    }

    /**
     * Sets the value of the fromTime1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getFromTime1()
     */
    public void setFromTime1(Integer value) {
        this.fromTime1 = value;
    }

    /**
     * Until time 1 for consignments and pickup information. Format is hhmm. Mandatory for consignment.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getToTime1() {
        return toTime1;
    }

    /**
     * Sets the value of the toTime1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getToTime1()
     */
    public void setToTime1(Integer value) {
        this.toTime1 = value;
    }

    /**
     * From time 2 for consignments and pickup information. Format is hhmm.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFromTime2() {
        return fromTime2;
    }

    /**
     * Sets the value of the fromTime2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getFromTime2()
     */
    public void setFromTime2(Integer value) {
        this.fromTime2 = value;
    }

    /**
     * Until time 2 for consignments and pickup information. Format is hhmm.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getToTime2() {
        return toTime2;
    }

    /**
     * Sets the value of the toTime2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getToTime2()
     */
    public void setToTime2(Integer value) {
        this.toTime2 = value;
    }

    /**
     * True if extra pickup is requested.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExtraPickup() {
        return extraPickup;
    }

    /**
     * Sets the value of the extraPickup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isExtraPickup()
     */
    public void setExtraPickup(Boolean value) {
        this.extraPickup = value;
    }

    /**
     * Contains pickup address information for consignments and collection requests. Mandatory for consignment.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getCollectionRequestAddress() {
        return collectionRequestAddress;
    }

    /**
     * Sets the value of the collectionRequestAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     * @see #getCollectionRequestAddress()
     */
    public void setCollectionRequestAddress(Address value) {
        this.collectionRequestAddress = value;
    }

}
