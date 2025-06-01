
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ruecknahmeangebotAnfordernResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ruecknahmeangebotAnfordernResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="return" type="{urn:msv3:v1}RuecknahmeangebotAntwort"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ruecknahmeangebotAnfordernResponse", propOrder = {
    "_return"
})
public class RuecknahmeangebotAnfordernResponse {

    @XmlElement(name = "return", namespace = "", required = true)
    protected RuecknahmeangebotAntwort _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RuecknahmeangebotAntwort }
     *     
     */
    public RuecknahmeangebotAntwort getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuecknahmeangebotAntwort }
     *     
     */
    public void setReturn(RuecknahmeangebotAntwort value) {
        this._return = value;
    }

}
