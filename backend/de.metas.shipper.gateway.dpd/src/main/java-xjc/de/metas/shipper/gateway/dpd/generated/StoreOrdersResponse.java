
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for storeOrdersResponse complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="storeOrdersResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="orderResult" type="{http://dpd.com/common/service/types/ShipmentService/3.2}storeOrdersResponseType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "storeOrdersResponse", propOrder = {
    "orderResult"
})
public class StoreOrdersResponse {

    /**
     * Contains return object.
     * 
     */
    @XmlElement(required = true)
    protected StoreOrdersResponseType orderResult;

    /**
     * Contains return object.
     * 
     * @return
     *     possible object is
     *     {@link StoreOrdersResponseType }
     *     
     */
    public StoreOrdersResponseType getOrderResult() {
        return orderResult;
    }

    /**
     * Sets the value of the orderResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link StoreOrdersResponseType }
     *     
     * @see #getOrderResult()
     */
    public void setOrderResult(StoreOrdersResponseType value) {
        this.orderResult = value;
    }

}
