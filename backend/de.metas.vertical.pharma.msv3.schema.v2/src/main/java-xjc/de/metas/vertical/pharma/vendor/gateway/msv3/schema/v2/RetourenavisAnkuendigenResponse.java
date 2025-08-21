
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for retourenavisAnkuendigenResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="retourenavisAnkuendigenResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="return" type="{urn:msv3:v2}RetourenavisAnkuendigungAntwort" minOccurs="0" form="unqualified"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retourenavisAnkuendigenResponse", propOrder = {
    "_return"
})
public class RetourenavisAnkuendigenResponse {

    @XmlElement(name = "return", namespace = "")
    protected RetourenavisAnkuendigungAntwort _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RetourenavisAnkuendigungAntwort }
     *     
     */
    public RetourenavisAnkuendigungAntwort getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetourenavisAnkuendigungAntwort }
     *     
     */
    public void setReturn(RetourenavisAnkuendigungAntwort value) {
        this._return = value;
    }

}
