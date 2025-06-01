
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Artikel mit Menge
 * 
 * <p>Java class for ArtikelMenge complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ArtikelMenge">
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
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArtikelMenge", propOrder = {
    "pzn",
    "menge"
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
