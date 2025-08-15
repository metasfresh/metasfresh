
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Bundles delivery data.
 * 
 * <p>Java class for delivery complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="delivery">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="day" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="20"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="dateFrom" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="99999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="dateTo" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="99999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="timeFrom" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="2400"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="timeTo" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="2400"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delivery", propOrder = {
    "day",
    "dateFrom",
    "dateTo",
    "timeFrom",
    "timeTo"
})
public class Delivery {

    /**
     * Allowed delivery days in the week (0 = Sunday, 1 = Monday etc.).
     * Comma separated list of possible delivery days (e.g. "2,3,4,5").
     * 
     */
    protected String day;
    /**
     * Fixed delivery from date - format YYYYMMDD, e.g. 20080213.
     * 
     */
    protected Integer dateFrom;
    /**
     * Fixed delivery to date - format YYYYMMDD, e.g. 20080213.
     * 
     */
    protected Integer dateTo;
    /**
     * Time from which the consignee is available - format hhmm (local time receipient country), e.g. 1400 or 0830.
     * 
     */
    protected Integer timeFrom;
    /**
     * Time until the consignee is available - format hhmm (local time receipient country), e.g. 1600 or 0930.
     * 
     */
    protected Integer timeTo;

    /**
     * Allowed delivery days in the week (0 = Sunday, 1 = Monday etc.).
     * Comma separated list of possible delivery days (e.g. "2,3,4,5").
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDay() {
        return day;
    }

    /**
     * Sets the value of the day property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDay()
     */
    public void setDay(String value) {
        this.day = value;
    }

    /**
     * Fixed delivery from date - format YYYYMMDD, e.g. 20080213.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getDateFrom()
     */
    public void setDateFrom(Integer value) {
        this.dateFrom = value;
    }

    /**
     * Fixed delivery to date - format YYYYMMDD, e.g. 20080213.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDateTo() {
        return dateTo;
    }

    /**
     * Sets the value of the dateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getDateTo()
     */
    public void setDateTo(Integer value) {
        this.dateTo = value;
    }

    /**
     * Time from which the consignee is available - format hhmm (local time receipient country), e.g. 1400 or 0830.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeFrom() {
        return timeFrom;
    }

    /**
     * Sets the value of the timeFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getTimeFrom()
     */
    public void setTimeFrom(Integer value) {
        this.timeFrom = value;
    }

    /**
     * Time until the consignee is available - format hhmm (local time receipient country), e.g. 1600 or 0930.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeTo() {
        return timeTo;
    }

    /**
     * Sets the value of the timeTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getTimeTo()
     */
    public void setTimeTo(Integer value) {
        this.timeTo = value;
    }

}
