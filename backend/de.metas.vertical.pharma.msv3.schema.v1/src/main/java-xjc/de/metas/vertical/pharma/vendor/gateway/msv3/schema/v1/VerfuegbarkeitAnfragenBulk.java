
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for verfuegbarkeitAnfragenBulk complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="verfuegbarkeitAnfragenBulk">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{urn:msv3:v1}ClientSoftwareKennung"/>
 *         <element name="verfuegbarkeitsanfrageBulk" type="{urn:msv3:v1}VerfuegbarkeitsanfrageBulk"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verfuegbarkeitAnfragenBulk", propOrder = {
    "clientSoftwareKennung",
    "verfuegbarkeitsanfrageBulk"
})
public class VerfuegbarkeitAnfragenBulk {

    @XmlElement(namespace = "", required = true)
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "", required = true)
    protected VerfuegbarkeitsanfrageBulk verfuegbarkeitsanfrageBulk;

    /**
     * Gets the value of the clientSoftwareKennung property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSoftwareKennung() {
        return clientSoftwareKennung;
    }

    /**
     * Sets the value of the clientSoftwareKennung property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSoftwareKennung(String value) {
        this.clientSoftwareKennung = value;
    }

    /**
     * Gets the value of the verfuegbarkeitsanfrageBulk property.
     * 
     * @return
     *     possible object is
     *     {@link VerfuegbarkeitsanfrageBulk }
     *     
     */
    public VerfuegbarkeitsanfrageBulk getVerfuegbarkeitsanfrageBulk() {
        return verfuegbarkeitsanfrageBulk;
    }

    /**
     * Sets the value of the verfuegbarkeitsanfrageBulk property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerfuegbarkeitsanfrageBulk }
     *     
     */
    public void setVerfuegbarkeitsanfrageBulk(VerfuegbarkeitsanfrageBulk value) {
        this.verfuegbarkeitsanfrageBulk = value;
    }

}
