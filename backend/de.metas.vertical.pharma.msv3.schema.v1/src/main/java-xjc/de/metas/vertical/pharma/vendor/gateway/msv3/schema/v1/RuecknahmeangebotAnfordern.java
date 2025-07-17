
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ruecknahmeangebotAnfordern complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ruecknahmeangebotAnfordern">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{urn:msv3:v1}ClientSoftwareKennung"/>
 *         <element name="ruecknahmeangebot" type="{urn:msv3:v1}Ruecknahmeangebot"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ruecknahmeangebotAnfordern", propOrder = {
    "clientSoftwareKennung",
    "ruecknahmeangebot"
})
public class RuecknahmeangebotAnfordern {

    @XmlElement(namespace = "", required = true)
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "", required = true)
    protected Ruecknahmeangebot ruecknahmeangebot;

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
     * Gets the value of the ruecknahmeangebot property.
     * 
     * @return
     *     possible object is
     *     {@link Ruecknahmeangebot }
     *     
     */
    public Ruecknahmeangebot getRuecknahmeangebot() {
        return ruecknahmeangebot;
    }

    /**
     * Sets the value of the ruecknahmeangebot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ruecknahmeangebot }
     *     
     */
    public void setRuecknahmeangebot(Ruecknahmeangebot value) {
        this.ruecknahmeangebot = value;
    }

}
