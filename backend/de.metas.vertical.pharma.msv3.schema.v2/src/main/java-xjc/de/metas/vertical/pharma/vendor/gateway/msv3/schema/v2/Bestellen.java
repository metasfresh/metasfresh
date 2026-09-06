
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bestellen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bestellen"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="clientSoftwareKennung" type="{urn:msv3:v2}ClientSoftwareKennung"/&gt;
 *         &lt;element name="bestellung" type="{urn:msv3:v2}Bestellung"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bestellen", propOrder = {
    "clientSoftwareKennung",
    "bestellung"
})
public class Bestellen {

    @XmlElement(namespace = "", required = true)
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "", required = true)
    protected Bestellung bestellung;

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
     * Gets the value of the bestellung property.
     * 
     * @return
     *     possible object is
     *     {@link Bestellung }
     *     
     */
    public Bestellung getBestellung() {
        return bestellung;
    }

    /**
     * Sets the value of the bestellung property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bestellung }
     *     
     */
    public void setBestellung(Bestellung value) {
        this.bestellung = value;
    }

}
