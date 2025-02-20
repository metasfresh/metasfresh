
package at.erpel.schemas._1p0.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.erpel.schemas._1p0.documents.ext.ShipperExtensionType;


/**
 * <p>Java class for ShipperType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipperType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://erpel.at/schemas/1p0/documents}BusinessEntityType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}ShipperExtension" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipperType", propOrder = {
    "shipperExtension"
})
public class ShipperType
    extends BusinessEntityType
{

    @XmlElement(name = "ShipperExtension", namespace = "http://erpel.at/schemas/1p0/documents/ext")
    protected ShipperExtensionType shipperExtension;

    /**
     * Gets the value of the shipperExtension property.
     * 
     * @return
     *     possible object is
     *     {@link ShipperExtensionType }
     *     
     */
    public ShipperExtensionType getShipperExtension() {
        return shipperExtension;
    }

    /**
     * Sets the value of the shipperExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipperExtensionType }
     *     
     */
    public void setShipperExtension(ShipperExtensionType value) {
        this.shipperExtension = value;
    }

}
