
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Artikel mit Menge
 * 
 * <p>Java class for ArtikelMenge complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArtikelMenge"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Pzn" type="{urn:msv3:v2}pzn"/&gt;
 *         &lt;element name="Menge" type="{urn:msv3:v2}MengeType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArtikelMenge", propOrder = {
    "pzn",
    "menge"
})
@XmlSeeAlso({
    de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitsanfrageEinzelne.Artikel.class
})
public class ArtikelMenge {

    @XmlElement(name = "Pzn")
    protected long pzn;
    @XmlElement(name = "Menge")
    protected int menge;

    /**
     * Gets the value of the pzn property.
     * 
     */
    public long getPzn() {
        return pzn;
    }

    /**
     * Sets the value of the pzn property.
     * 
     */
    public void setPzn(long value) {
        this.pzn = value;
    }

    /**
     * Gets the value of the menge property.
     * 
     */
    public int getMenge() {
        return menge;
    }

    /**
     * Sets the value of the menge property.
     * 
     */
    public void setMenge(int value) {
        this.menge = value;
    }

}
