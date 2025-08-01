
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for VerfuegbarkeitsantwortArtikel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VerfuegbarkeitsantwortArtikel">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="AnfrageMenge" type="{urn:msv3:v2}MengeType"/>
 *         <element name="AnfragePzn" type="{urn:msv3:v2}pzn"/>
 *         <element name="Substitution" type="{urn:msv3:v2}VerfuegbarkeitSubstitution" minOccurs="0"/>
 *         <element name="Anteile" type="{urn:msv3:v2}VerfuegbarkeitAnteil" maxOccurs="5"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerfuegbarkeitsantwortArtikel", propOrder = {
    "anfrageMenge",
    "anfragePzn",
    "substitution",
    "anteile"
})
public class VerfuegbarkeitsantwortArtikel {

    @XmlElement(name = "AnfrageMenge")
    protected int anfrageMenge;
    @XmlElement(name = "AnfragePzn")
    protected long anfragePzn;
    @XmlElement(name = "Substitution")
    protected VerfuegbarkeitSubstitution substitution;
    @XmlElement(name = "Anteile", required = true)
    protected List<VerfuegbarkeitAnteil> anteile;

    /**
     * Gets the value of the anfrageMenge property.
     * 
     */
    public int getAnfrageMenge() {
        return anfrageMenge;
    }

    /**
     * Sets the value of the anfrageMenge property.
     * 
     */
    public void setAnfrageMenge(int value) {
        this.anfrageMenge = value;
    }

    /**
     * Gets the value of the anfragePzn property.
     * 
     */
    public long getAnfragePzn() {
        return anfragePzn;
    }

    /**
     * Sets the value of the anfragePzn property.
     * 
     */
    public void setAnfragePzn(long value) {
        this.anfragePzn = value;
    }

    /**
     * Gets the value of the substitution property.
     * 
     * @return
     *     possible object is
     *     {@link VerfuegbarkeitSubstitution }
     *     
     */
    public VerfuegbarkeitSubstitution getSubstitution() {
        return substitution;
    }

    /**
     * Sets the value of the substitution property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerfuegbarkeitSubstitution }
     *     
     */
    public void setSubstitution(VerfuegbarkeitSubstitution value) {
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
     * {@link VerfuegbarkeitAnteil }
     * 
     * 
     * @return
     *     The value of the anteile property.
     */
    public List<VerfuegbarkeitAnteil> getAnteile() {
        if (anteile == null) {
            anteile = new ArrayList<>();
        }
        return this.anteile;
    }

}
