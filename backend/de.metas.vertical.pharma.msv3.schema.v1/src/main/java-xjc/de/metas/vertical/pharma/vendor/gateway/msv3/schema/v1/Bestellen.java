
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bestellen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="bestellen">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{urn:msv3:v1}ClientSoftwareKennung"/>
 *         <element name="bestellung" type="{urn:msv3:v1}Bestellung"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
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
