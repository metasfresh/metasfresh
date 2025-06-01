
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for retourenavisAnfragenResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="retourenavisAnfragenResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="return" type="{urn:msv3:v2}RetourenavisAnfrageAntwort" minOccurs="0" form="unqualified"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retourenavisAnfragenResponse", propOrder = {
    "_return"
})
public class RetourenavisAnfragenResponse {

    @XmlElement(name = "return", namespace = "")
    protected RetourenavisAnfrageAntwort _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RetourenavisAnfrageAntwort }
     *     
     */
    public RetourenavisAnfrageAntwort getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetourenavisAnfrageAntwort }
     *     
     */
    public void setReturn(RetourenavisAnfrageAntwort value) {
        this._return = value;
    }

}
