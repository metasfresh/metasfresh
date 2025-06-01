
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for VerfuegbarkeitAnteil complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VerfuegbarkeitAnteil">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Menge">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <minInclusive value="1"/>
 *               <maxInclusive value="9999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Typ" type="{urn:msv3:v1}VerfuegbarkeitRueckmeldungTyp"/>
 *         <element name="Lieferzeitpunkt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         <element name="Tour" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="80"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Grund" type="{urn:msv3:v1}VerfuegbarkeitDefektgrund"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerfuegbarkeitAnteil", propOrder = {
    "menge",
    "typ",
    "lieferzeitpunkt",
    "tour",
    "grund"
})
public class VerfuegbarkeitAnteil {

    @XmlElement(name = "Menge", required = true, type = Integer.class, nillable = true)
    protected Integer menge;
    @XmlElement(name = "Typ", required = true)
    @XmlSchemaType(name = "string")
    protected VerfuegbarkeitRueckmeldungTyp typ;
    @XmlElement(name = "Lieferzeitpunkt", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lieferzeitpunkt;
    @XmlElement(name = "Tour")
    protected String tour;
    @XmlElement(name = "Grund", required = true)
    @XmlSchemaType(name = "string")
    protected VerfuegbarkeitDefektgrund grund;

    /**
     * Gets the value of the menge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMenge() {
        return menge;
    }

    /**
     * Sets the value of the menge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMenge(Integer value) {
        this.menge = value;
    }

    /**
     * Gets the value of the typ property.
     * 
     * @return
     *     possible object is
     *     {@link VerfuegbarkeitRueckmeldungTyp }
     *     
     */
    public VerfuegbarkeitRueckmeldungTyp getTyp() {
        return typ;
    }

    /**
     * Sets the value of the typ property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerfuegbarkeitRueckmeldungTyp }
     *     
     */
    public void setTyp(VerfuegbarkeitRueckmeldungTyp value) {
        this.typ = value;
    }

    /**
     * Gets the value of the lieferzeitpunkt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLieferzeitpunkt() {
        return lieferzeitpunkt;
    }

    /**
     * Sets the value of the lieferzeitpunkt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLieferzeitpunkt(XMLGregorianCalendar value) {
        this.lieferzeitpunkt = value;
    }

    /**
     * Gets the value of the tour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTour() {
        return tour;
    }

    /**
     * Sets the value of the tour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTour(String value) {
        this.tour = value;
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

}
