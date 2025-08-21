
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lieferavisAbfrageHist complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="lieferavisAbfrageHist">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="lieferavisAbfrageHistAbfrage" type="{urn:msv3:v2}LieferavisAbfrageHistAbfrage"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lieferavisAbfrageHist", propOrder = {
    "clientSoftwareKennung",
    "lieferavisAbfrageHistAbfrage"
})
public class LieferavisAbfrageHist {

    @XmlElement(namespace = "", required = true)
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "", required = true)
    protected LieferavisAbfrageHistAbfrage lieferavisAbfrageHistAbfrage;

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
     * Gets the value of the lieferavisAbfrageHistAbfrage property.
     * 
     * @return
     *     possible object is
     *     {@link LieferavisAbfrageHistAbfrage }
     *     
     */
    public LieferavisAbfrageHistAbfrage getLieferavisAbfrageHistAbfrage() {
        return lieferavisAbfrageHistAbfrage;
    }

    /**
     * Sets the value of the lieferavisAbfrageHistAbfrage property.
     * 
     * @param value
     *     allowed object is
     *     {@link LieferavisAbfrageHistAbfrage }
     *     
     */
    public void setLieferavisAbfrageHistAbfrage(LieferavisAbfrageHistAbfrage value) {
        this.lieferavisAbfrageHistAbfrage = value;
    }

}
