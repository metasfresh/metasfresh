
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for BestellungAntwortPosition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="BestellungAntwortPosition">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="BestellPzn" type="{urn:msv3:v2}pzn"/>
 *         <element name="BestellMenge" type="{urn:msv3:v2}MengeType"/>
 *         <element name="BestellLiefervorgabe" type="{urn:msv3:v2}Liefervorgabe"/>
 *         <element name="Substitution" type="{urn:msv3:v2}BestellungSubstitution" minOccurs="0"/>
 *         <element name="Anteile" type="{urn:msv3:v2}BestellungAnteil" maxOccurs="9"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BestellungAntwortPosition", propOrder = {
    "bestellPzn",
    "bestellMenge",
    "bestellLiefervorgabe",
    "substitution",
    "anteile"
})
public class BestellungAntwortPosition {

    @XmlElement(name = "BestellPzn")
    protected long bestellPzn;
    @XmlElement(name = "BestellMenge")
    protected int bestellMenge;
    @XmlElement(name = "BestellLiefervorgabe", required = true)
    @XmlSchemaType(name = "string")
    protected Liefervorgabe bestellLiefervorgabe;
    @XmlElement(name = "Substitution")
    protected BestellungSubstitution substitution;
    @XmlElement(name = "Anteile", required = true)
    protected List<BestellungAnteil> anteile;

    /**
     * Gets the value of the bestellPzn property.
     * 
     */
    public long getBestellPzn() {
        return bestellPzn;
    }

    /**
     * Sets the value of the bestellPzn property.
     * 
     */
    public void setBestellPzn(long value) {
        this.bestellPzn = value;
    }

    /**
     * Gets the value of the bestellMenge property.
     * 
     */
    public int getBestellMenge() {
        return bestellMenge;
    }

    /**
     * Sets the value of the bestellMenge property.
     * 
     */
    public void setBestellMenge(int value) {
        this.bestellMenge = value;
    }

    /**
     * Gets the value of the bestellLiefervorgabe property.
     * 
     * @return
     *     possible object is
     *     {@link Liefervorgabe }
     *     
     */
    public Liefervorgabe getBestellLiefervorgabe() {
        return bestellLiefervorgabe;
    }

    /**
     * Sets the value of the bestellLiefervorgabe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Liefervorgabe }
     *     
     */
    public void setBestellLiefervorgabe(Liefervorgabe value) {
        this.bestellLiefervorgabe = value;
    }

    /**
     * Gets the value of the substitution property.
     * 
     * @return
     *     possible object is
     *     {@link BestellungSubstitution }
     *     
     */
    public BestellungSubstitution getSubstitution() {
        return substitution;
    }

    /**
     * Sets the value of the substitution property.
     * 
     * @param value
     *     allowed object is
     *     {@link BestellungSubstitution }
     *     
     */
    public void setSubstitution(BestellungSubstitution value) {
        this.substitution = value;
    }

    /**
     * Gets the value of the anteile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the anteile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnteile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BestellungAnteil }
     * 
     * 
     * @return
     *     The value of the anteile property.
     */
    public List<BestellungAnteil> getAnteile() {
        if (anteile == null) {
            anteile = new ArrayList<>();
        }
        return this.anteile;
    }

}
