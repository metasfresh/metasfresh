
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LieferavisAbfrageHistAbfrage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LieferavisAbfrageHistAbfrage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="Lieferscheinnummer" type="{urn:msv3:v2}LieferscheinnummerType" /&gt;
 *       &lt;attribute name="BarcodeReferenz" type="{urn:msv3:v2}BarcodeReferenzType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LieferavisAbfrageHistAbfrage")
public class LieferavisAbfrageHistAbfrage {

    @XmlAttribute(name = "Lieferscheinnummer")
    protected String lieferscheinnummer;
    @XmlAttribute(name = "BarcodeReferenz")
    protected String barcodeReferenz;

    /**
     * Gets the value of the lieferscheinnummer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLieferscheinnummer() {
        return lieferscheinnummer;
    }

    /**
     * Sets the value of the lieferscheinnummer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLieferscheinnummer(String value) {
        this.lieferscheinnummer = value;
    }

    /**
     * Gets the value of the barcodeReferenz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarcodeReferenz() {
        return barcodeReferenz;
    }

    /**
     * Sets the value of the barcodeReferenz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarcodeReferenz(String value) {
        this.barcodeReferenz = value;
    }

}
