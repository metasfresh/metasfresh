
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for storeOrdersResponseType complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="storeOrdersResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="parcellabelsPDF" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         <element name="shipmentResponses" type="{http://dpd.com/common/service/types/ShipmentService/3.2}shipmentResponse" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "storeOrdersResponseType", propOrder = {
    "parcellabelsPDF",
    "shipmentResponses"
})
public class StoreOrdersResponseType {

    /**
     * Contains parcel label PDF data in bytes.
     * 
     */
    protected byte[] parcellabelsPDF;
    /**
     * Contains response data for every shipment order.
     * 
     */
    protected List<ShipmentResponse> shipmentResponses;

    /**
     * Contains parcel label PDF data in bytes.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getParcellabelsPDF() {
        return parcellabelsPDF;
    }

    /**
     * Sets the value of the parcellabelsPDF property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     * @see #getParcellabelsPDF()
     */
    public void setParcellabelsPDF(byte[] value) {
        this.parcellabelsPDF = value;
    }

    /**
     * Contains response data for every shipment order.
     * 
     * Gets the value of the shipmentResponses property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipmentResponses property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getShipmentResponses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentResponse }
     * </p>
     * 
     * 
     * @return
     *     The value of the shipmentResponses property.
     */
    public List<ShipmentResponse> getShipmentResponses() {
        if (shipmentResponses == null) {
            shipmentResponses = new ArrayList<>();
        }
        return this.shipmentResponses;
    }

}
