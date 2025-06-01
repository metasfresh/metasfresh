
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BestellungAnteil complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="BestellungAnteil">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Menge" type="{urn:msv3:v2}MengeType"/>
 *         <element name="Typ" type="{urn:msv3:v2}BestellungRueckmeldungTyp"/>
 *         <element name="Lieferzeitpunkt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         <element name="Tour" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="80"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Grund" type="{urn:msv3:v2}BestellungDefektgrund"/>
 *         <element name="TourId" type="{urn:msv3:v2}DruckbareKennung" minOccurs="0"/>
 *         <element name="Tourabweichung" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BestellungAnteil", propOrder = {
    "menge",
    "typ",
    "lieferzeitpunkt",
    "tour",
    "grund",
    "tourId",
    "tourabweichung"
})
public class BestellungAnteil {

    @XmlElement(name = "Menge", required = true, type = Integer.class, nillable = true)
    protected Integer menge;
    @XmlElement(name = "Typ", required = true)
    @XmlSchemaType(name = "string")
    protected BestellungRueckmeldungTyp typ;
    @XmlElement(name = "Lieferzeitpunkt", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lieferzeitpunkt;
    @XmlElement(name = "Tour")
    protected String tour;
    @XmlElement(name = "Grund", required = true)
    @XmlSchemaType(name = "string")
    protected BestellungDefektgrund grund;
    @XmlElement(name = "TourId")
    protected String tourId;
    @XmlElement(name = "Tourabweichung")
    protected boolean tourabweichung;

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
     *     {@link BestellungRueckmeldungTyp }
     *     
     */
    public BestellungRueckmeldungTyp getTyp() {
        return typ;
    }

    /**
     * Sets the value of the typ property.
     * 
     * @param value
     *     allowed object is
     *     {@link BestellungRueckmeldungTyp }
     *     
     */
    public void setTyp(BestellungRueckmeldungTyp value) {
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
     *     {@link BestellungDefektgrund }
     *     
     */
    public BestellungDefektgrund getGrund() {
        return grund;
    }

    /**
     * Sets the value of the grund property.
     * 
     * @param value
     *     allowed object is
     *     {@link BestellungDefektgrund }
     *     
     */
    public void setGrund(BestellungDefektgrund value) {
        this.grund = value;
    }

    /**
     * Gets the value of the tourId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTourId() {
        return tourId;
    }

    /**
     * Sets the value of the tourId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTourId(String value) {
        this.tourId = value;
    }

    /**
     * Gets the value of the tourabweichung property.
     * 
     */
    public boolean isTourabweichung() {
        return tourabweichung;
    }

    /**
     * Sets the value of the tourabweichung property.
     * 
     */
    public void setTourabweichung(boolean value) {
        this.tourabweichung = value;
    }

}
