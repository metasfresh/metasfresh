
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lieferavisBestaetigen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lieferavisBestaetigen"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="clientSoftwareKennung" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lieferavisBestaetigenType" type="{urn:msv3:v2}LieferavisBestaetigenType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
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
