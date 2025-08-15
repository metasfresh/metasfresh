
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vertragsdatenAbfragenResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="vertragsdatenAbfragenResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="return" type="{urn:msv3:v1}VertragsdatenAntwort"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vertragsdatenAbfragenResponse", propOrder = {
    "_return"
})
public class VertragsdatenAbfragenResponse {

    @XmlElement(name = "return", namespace = "", required = true)
    protected VertragsdatenAntwort _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link VertragsdatenAntwort }
     *     
     */
    public VertragsdatenAntwort getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link VertragsdatenAntwort }
     *     
     */
    public void setReturn(VertragsdatenAntwort value) {
        this._return = value;
    }

}
