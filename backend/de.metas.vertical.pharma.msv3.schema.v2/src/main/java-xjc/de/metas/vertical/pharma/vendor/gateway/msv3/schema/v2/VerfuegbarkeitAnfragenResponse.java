
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for verfuegbarkeitAnfragenResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="verfuegbarkeitAnfragenResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="return" type="{urn:msv3:v2}VerfuegbarkeitsanfrageEinzelneAntwort"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verfuegbarkeitAnfragenResponse", propOrder = {
    "_return"
})
public class VerfuegbarkeitAnfragenResponse {

    @XmlElement(name = "return", namespace = "", required = true)
    protected VerfuegbarkeitsanfrageEinzelneAntwort _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link VerfuegbarkeitsanfrageEinzelneAntwort }
     *     
     */
    public VerfuegbarkeitsanfrageEinzelneAntwort getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerfuegbarkeitsanfrageEinzelneAntwort }
     *     
     */
    public void setReturn(VerfuegbarkeitsanfrageEinzelneAntwort value) {
        this._return = value;
    }

}
