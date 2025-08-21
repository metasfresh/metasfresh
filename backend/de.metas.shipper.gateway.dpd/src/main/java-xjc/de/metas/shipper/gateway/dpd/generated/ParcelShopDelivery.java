
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parcelShopDelivery complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="parcelShopDelivery">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="parcelShopId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="parcelShopNotification" type="{http://dpd.com/common/service/types/ShipmentService/3.2}notification"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parcelShopDelivery", propOrder = {
    "parcelShopId",
    "parcelShopNotification"
})
public class ParcelShopDelivery {

    /**
     * Parcel shop ID for parcel shop delivery.
     * Can be obtained from parcel shop finder. Assumes that parameter type of
     * personal delivery is filled with a value from 3 to 5,
     * which means parcel shop delivery. Mandatory in this case.
     * 
     */
    protected long parcelShopId;
    /**
     * Contains data for personal notification for parcel shop consignments.
     * 
     */
    @XmlElement(required = true)
    protected Notification parcelShopNotification;

    /**
     * Parcel shop ID for parcel shop delivery.
     * Can be obtained from parcel shop finder. Assumes that parameter type of
     * personal delivery is filled with a value from 3 to 5,
     * which means parcel shop delivery. Mandatory in this case.
     * 
     */
    public long getParcelShopId() {
        return parcelShopId;
    }

    /**
     * Sets the value of the parcelShopId property.
     * 
     */
    public void setParcelShopId(long value) {
        this.parcelShopId = value;
    }

    /**
     * Contains data for personal notification for parcel shop consignments.
     * 
     * @return
     *     possible object is
     *     {@link Notification }
     *     
     */
    public Notification getParcelShopNotification() {
        return parcelShopNotification;
    }

    /**
     * Sets the value of the parcelShopNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Notification }
     *     
     * @see #getParcelShopNotification()
     */
    public void setParcelShopNotification(Notification value) {
        this.parcelShopNotification = value;
    }

}
