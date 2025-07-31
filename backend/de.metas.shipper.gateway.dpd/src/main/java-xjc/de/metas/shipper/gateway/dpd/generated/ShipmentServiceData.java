
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * Bundles shipment service data.
 * 
 * <p>Java class for shipmentServiceData complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="shipmentServiceData">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="generalShipmentData" type="{http://dpd.com/common/service/types/ShipmentService/3.2}generalShipmentData"/>
 *         <element name="parcels" type="{http://dpd.com/common/service/types/ShipmentService/3.2}parcel" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="productAndServiceData" type="{http://dpd.com/common/service/types/ShipmentService/3.2}productAndServiceData"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shipmentServiceData", propOrder = {
    "generalShipmentData",
    "parcels",
    "productAndServiceData"
})
public class ShipmentServiceData {

    /**
     * Contains general data for a shipment.
     * 
     */
    @XmlElement(required = true)
    protected GeneralShipmentData generalShipmentData;
    /**
     * Contains information for the parcels.
     * 
     */
    protected List<Parcel> parcels;
    /**
     * Contains special data for a shipment.
     * 
     */
    @XmlElement(required = true)
    protected ProductAndServiceData productAndServiceData;

    /**
     * Contains general data for a shipment.
     * 
     * @return
     *     possible object is
     *     {@link GeneralShipmentData }
     *     
     */
    public GeneralShipmentData getGeneralShipmentData() {
        return generalShipmentData;
    }

    /**
     * Sets the value of the generalShipmentData property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneralShipmentData }
     *     
     * @see #getGeneralShipmentData()
     */
    public void setGeneralShipmentData(GeneralShipmentData value) {
        this.generalShipmentData = value;
    }

    /**
     * Contains information for the parcels.
     * 
     * Gets the value of the parcels property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parcels property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getParcels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Parcel }
     * </p>
     * 
     * 
     * @return
     *     The value of the parcels property.
     */
    public List<Parcel> getParcels() {
        if (parcels == null) {
            parcels = new ArrayList<>();
        }
        return this.parcels;
    }

    /**
     * Contains special data for a shipment.
     * 
     * @return
     *     possible object is
     *     {@link ProductAndServiceData }
     *     
     */
    public ProductAndServiceData getProductAndServiceData() {
        return productAndServiceData;
    }

    /**
     * Sets the value of the productAndServiceData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAndServiceData }
     *     
     * @see #getProductAndServiceData()
     */
    public void setProductAndServiceData(ProductAndServiceData value) {
        this.productAndServiceData = value;
    }

}
