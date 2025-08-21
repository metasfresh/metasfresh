
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerfuegbarkeitSubstitution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VerfuegbarkeitSubstitution">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Substitutionsgrund" type="{urn:msv3:v1}Substitutionsgrund"/>
 *         <element name="Grund" type="{urn:msv3:v1}VerfuegbarkeitDefektgrund"/>
 *         <element name="LieferPzn" type="{urn:msv3:v1}pzn"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerfuegbarkeitSubstitution", propOrder = {
    "substitutionsgrund",
    "grund",
    "lieferPzn"
})
public class VerfuegbarkeitSubstitution {

    @XmlElement(name = "Substitutionsgrund", required = true)
    @XmlSchemaType(name = "string")
    protected Substitutionsgrund substitutionsgrund;
    @XmlElement(name = "Grund", required = true)
    @XmlSchemaType(name = "string")
    protected VerfuegbarkeitDefektgrund grund;
    @XmlElement(name = "LieferPzn")
    protected long lieferPzn;

    /**
     * Gets the value of the substitutionsgrund property.
     * 
     * @return
     *     possible object is
     *     {@link Substitutionsgrund }
     *     
     */
    public Substitutionsgrund getSubstitutionsgrund() {
        return substitutionsgrund;
    }

    /**
     * Sets the value of the substitutionsgrund property.
     * 
     * @param value
     *     allowed object is
     *     {@link Substitutionsgrund }
     *     
     */
    public void setSubstitutionsgrund(Substitutionsgrund value) {
        this.substitutionsgrund = value;
    }

    /**
     * Gets the value of the grund property.
     * 
     * @return
     *     possible object is
     *     {@link VerfuegbarkeitDefektgrund }
     *     
     */
    public VerfuegbarkeitDefektgrund getGrund() {
        return grund;
    }

    /**
     * Sets the value of the grund property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerfuegbarkeitDefektgrund }
     *     
     */
    public void setGrund(VerfuegbarkeitDefektgrund value) {
        this.grund = value;
    }

    /**
     * Gets the value of the lieferPzn property.
     * 
     */
    public long getLieferPzn() {
        return lieferPzn;
    }

    /**
     * Sets the value of the lieferPzn property.
     * 
     */
    public void setLieferPzn(long value) {
        this.lieferPzn = value;
    }

}
