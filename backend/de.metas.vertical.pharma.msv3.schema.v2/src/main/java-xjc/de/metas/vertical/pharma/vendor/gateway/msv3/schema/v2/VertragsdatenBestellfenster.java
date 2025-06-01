
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for VertragsdatenBestellfenster complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VertragsdatenBestellfenster">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tag" type="{urn:msv3:v2}VertragsdatenTag"/>
 *         <element name="Endezeit" type="{urn:msv3:v2}VertragsdatenZeit"/>
 *         <element name="Hauptbestellzeit" type="{urn:msv3:v2}VertragsdatenHauptbestellzeit"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VertragsdatenBestellfenster", propOrder = {
    "tag",
    "endezeit",
    "hauptbestellzeit"
})
public class VertragsdatenBestellfenster {

    @XmlElement(name = "Tag", required = true)
    @XmlSchemaType(name = "string")
    protected VertragsdatenTag tag;
    @XmlElement(name = "Endezeit", required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar endezeit;
    @XmlElement(name = "Hauptbestellzeit", required = true)
    protected VertragsdatenHauptbestellzeit hauptbestellzeit;

    /**
     * Gets the value of the tag property.
     * 
     * @return
     *     possible object is
     *     {@link VertragsdatenTag }
     *     
     */
    public VertragsdatenTag getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     * 
     * @param value
     *     allowed object is
     *     {@link VertragsdatenTag }
     *     
     */
    public void setTag(VertragsdatenTag value) {
        this.tag = value;
    }

    /**
     * Gets the value of the endezeit property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndezeit() {
        return endezeit;
    }

    /**
     * Sets the value of the endezeit property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndezeit(XMLGregorianCalendar value) {
        this.endezeit = value;
    }

    /**
     * Gets the value of the hauptbestellzeit property.
     * 
     * @return
     *     possible object is
     *     {@link VertragsdatenHauptbestellzeit }
     *     
     */
    public VertragsdatenHauptbestellzeit getHauptbestellzeit() {
        return hauptbestellzeit;
    }

    /**
     * Sets the value of the hauptbestellzeit property.
     * 
     * @param value
     *     allowed object is
     *     {@link VertragsdatenHauptbestellzeit }
     *     
     */
    public void setHauptbestellzeit(VertragsdatenHauptbestellzeit value) {
        this.hauptbestellzeit = value;
    }

}
