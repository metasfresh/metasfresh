
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for verfuegbarkeitAnfragen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="verfuegbarkeitAnfragen">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{urn:msv3:v2}ClientSoftwareKennung"/>
 *         <element name="verfuegbarkeitsanfrage" type="{urn:msv3:v2}VerfuegbarkeitsanfrageEinzelne"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verfuegbarkeitAnfragen", propOrder = {
    "clientSoftwareKennung",
    "verfuegbarkeitsanfrage"
})
public class VerfuegbarkeitAnfragen {

    @XmlElement(namespace = "", required = true)
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "", required = true)
    protected VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrage;

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
     * Gets the value of the verfuegbarkeitsanfrage property.
     * 
     * @return
     *     possible object is
     *     {@link VerfuegbarkeitsanfrageEinzelne }
     *     
     */
    public VerfuegbarkeitsanfrageEinzelne getVerfuegbarkeitsanfrage() {
        return verfuegbarkeitsanfrage;
    }

    /**
     * Sets the value of the verfuegbarkeitsanfrage property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerfuegbarkeitsanfrageEinzelne }
     *     
     */
    public void setVerfuegbarkeitsanfrage(VerfuegbarkeitsanfrageEinzelne value) {
        this.verfuegbarkeitsanfrage = value;
    }

}
