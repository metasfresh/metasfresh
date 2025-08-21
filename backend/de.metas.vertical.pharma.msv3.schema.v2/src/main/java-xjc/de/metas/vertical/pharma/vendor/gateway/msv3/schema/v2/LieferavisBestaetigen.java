
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lieferavisBestaetigen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="lieferavisBestaetigen">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="lieferavisBestaetigenType" type="{urn:msv3:v2}LieferavisBestaetigenType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lieferavisBestaetigen", propOrder = {
    "clientSoftwareKennung",
    "lieferavisBestaetigenType"
})
public class LieferavisBestaetigen {

    @XmlElement(namespace = "", required = true)
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "", required = true)
    protected LieferavisBestaetigenType lieferavisBestaetigenType;

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
     * Gets the value of the lieferavisBestaetigenType property.
     * 
     * @return
     *     possible object is
     *     {@link LieferavisBestaetigenType }
     *     
     */
    public LieferavisBestaetigenType getLieferavisBestaetigenType() {
        return lieferavisBestaetigenType;
    }

    /**
     * Sets the value of the lieferavisBestaetigenType property.
     * 
     * @param value
     *     allowed object is
     *     {@link LieferavisBestaetigenType }
     *     
     */
    public void setLieferavisBestaetigenType(LieferavisBestaetigenType value) {
        this.lieferavisBestaetigenType = value;
    }

}
