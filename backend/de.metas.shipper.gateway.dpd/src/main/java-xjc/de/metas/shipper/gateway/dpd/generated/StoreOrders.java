
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for storeOrders complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="storeOrders">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="printOptions" type="{http://dpd.com/common/service/types/ShipmentService/3.2}printOptions" minOccurs="0"/>
 *         <element name="order" type="{http://dpd.com/common/service/types/ShipmentService/3.2}shipmentServiceData" maxOccurs="30"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "storeOrders", propOrder = {
    "printOptions",
    "order"
})
public class StoreOrders {

    /**
     * The Options which should be used for parcel printing.
     * 
     */
    protected PrintOptions printOptions;
    /**
     * Contains the whole data for the shipments.
     * 
     */
    @XmlElement(required = true)
    protected List<ShipmentServiceData> order;

    /**
     * The Options which should be used for parcel printing.
     * 
     * @return
     *     possible object is
     *     {@link PrintOptions }
     *     
     */
    public PrintOptions getPrintOptions() {
        return printOptions;
    }

    /**
     * Sets the value of the printOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrintOptions }
     *     
     * @see #getPrintOptions()
     */
    public void setPrintOptions(PrintOptions value) {
        this.printOptions = value;
    }

    /**
     * Contains the whole data for the shipments.
     * 
     * Gets the value of the order property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the order property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getOrder().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentServiceData }
     * </p>
     * 
     * 
     * @return
     *     The value of the order property.
     */
    public List<ShipmentServiceData> getOrder() {
        if (order == null) {
            order = new ArrayList<>();
        }
        return this.order;
    }

}
