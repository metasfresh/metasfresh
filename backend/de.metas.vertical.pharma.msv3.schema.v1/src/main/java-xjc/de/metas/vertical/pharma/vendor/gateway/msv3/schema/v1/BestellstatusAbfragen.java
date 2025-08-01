
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bestellstatusAbfragen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="bestellstatusAbfragen">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{urn:msv3:v1}ClientSoftwareKennung"/>
 *         <element name="bestellId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bestellstatusAbfragen", propOrder = {
    "clientSoftwareKennung",
    "bestellId"
})
public class BestellstatusAbfragen {

    @XmlElement(namespace = "", required = true)
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "", required = true)
    protected String bestellId;

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
     * Gets the value of the bestellId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBestellId() {
        return bestellId;
    }

    /**
     * Sets the value of the bestellId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBestellId(String value) {
        this.bestellId = value;
    }

}
