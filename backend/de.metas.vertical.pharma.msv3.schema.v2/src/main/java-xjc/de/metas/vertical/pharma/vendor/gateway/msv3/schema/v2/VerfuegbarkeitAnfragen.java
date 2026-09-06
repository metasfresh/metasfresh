
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for verfuegbarkeitAnfragen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="verfuegbarkeitAnfragen"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="clientSoftwareKennung" type="{urn:msv3:v2}ClientSoftwareKennung"/&gt;
 *         &lt;element name="verfuegbarkeitsanfrage" type="{urn:msv3:v2}VerfuegbarkeitsanfrageEinzelne"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
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
