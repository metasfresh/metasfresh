
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lieferavisAbfrageNeuResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="lieferavisAbfrageNeuResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="return" type="{urn:msv3:v2}LieferavisAbfragenAntwort"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lieferavisAbfrageNeuResponse", propOrder = {
    "_return"
})
public class LieferavisAbfrageNeuResponse {

    @XmlElement(name = "return", namespace = "", required = true)
    protected LieferavisAbfragenAntwort _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link LieferavisAbfragenAntwort }
     *     
     */
    public LieferavisAbfragenAntwort getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link LieferavisAbfragenAntwort }
     *     
     */
    public void setReturn(LieferavisAbfragenAntwort value) {
        this._return = value;
    }

}
