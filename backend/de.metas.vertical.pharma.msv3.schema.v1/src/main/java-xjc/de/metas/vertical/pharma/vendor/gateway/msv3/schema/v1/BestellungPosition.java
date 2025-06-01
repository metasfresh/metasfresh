
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Reihenfolge (bez PZN) in der Antwort muss gleich sein!
 *         keine doppelten PZNs innerhalb eines Auftrags erlaubt (in gesamter Bestellung schon).
 *       
 * 
 * <p>Java class for BestellungPosition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="BestellungPosition">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Pzn" type="{urn:msv3:v1}pzn"/>
 *         <element name="Menge">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <minInclusive value="1"/>
 *               <maxInclusive value="9999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Liefervorgabe" type="{urn:msv3:v1}Liefervorgabe"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BestellungPosition", propOrder = {
    "pzn",
    "menge",
    "liefervorgabe"
})
public class BestellungPosition {

    @XmlElement(name = "Pzn")
    protected long pzn;
    @XmlElement(name = "Menge")
    protected int menge;
    @XmlElement(name = "Liefervorgabe", required = true)
    @XmlSchemaType(name = "string")
    protected Liefervorgabe liefervorgabe;

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

    /**
     * Gets the value of the liefervorgabe property.
     * 
     * @return
     *     possible object is
     *     {@link Liefervorgabe }
     *     
     */
    public Liefervorgabe getLiefervorgabe() {
        return liefervorgabe;
    }

    /**
     * Sets the value of the liefervorgabe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Liefervorgabe }
     *     
     */
    public void setLiefervorgabe(Liefervorgabe value) {
        this.liefervorgabe = value;
    }

}
