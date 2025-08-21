
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for verfuegbarkeitAnfragenBulkResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="verfuegbarkeitAnfragenBulkResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="return" type="{urn:msv3:v1}VerfuegbarkeitsanfrageBulkAntwort"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verfuegbarkeitAnfragenBulkResponse", propOrder = {
    "_return"
})
public class VerfuegbarkeitAnfragenBulkResponse {

    @XmlElement(name = "return", namespace = "", required = true)
    protected VerfuegbarkeitsanfrageBulkAntwort _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link VerfuegbarkeitsanfrageBulkAntwort }
     *     
     */
    public VerfuegbarkeitsanfrageBulkAntwort getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerfuegbarkeitsanfrageBulkAntwort }
     *     
     */
    public void setReturn(VerfuegbarkeitsanfrageBulkAntwort value) {
        this._return = value;
    }

}
