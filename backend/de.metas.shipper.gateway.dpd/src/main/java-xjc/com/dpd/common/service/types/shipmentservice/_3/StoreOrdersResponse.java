
package com.dpd.common.service.types.shipmentservice._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for storeOrdersResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="storeOrdersResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="orderResult" type="{http://dpd.com/common/service/types/ShipmentService/3.2}storeOrdersResponseType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "storeOrdersResponse", propOrder = {
    "orderResult"
})
public class StoreOrdersResponse {

    @XmlElement(required = true)
    protected StoreOrdersResponseType orderResult;

    /**
     * Gets the value of the orderResult property.
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
     */
    public void setOrderResult(StoreOrdersResponseType value) {
        this.orderResult = value;
    }

}
