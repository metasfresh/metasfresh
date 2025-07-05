
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for VertragsdatenHauptbestellzeit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VertragsdatenHauptbestellzeit">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tag" type="{urn:msv3:v2}VertragsdatenTag"/>
 *         <element name="Zeitpunkt" type="{urn:msv3:v2}VertragsdatenZeit"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VertragsdatenHauptbestellzeit", propOrder = {
    "tag",
    "zeitpunkt"
})
public class VertragsdatenHauptbestellzeit {

    @XmlElement(name = "Tag", required = true)
    @XmlSchemaType(name = "string")
    protected VertragsdatenTag tag;
    @XmlElement(name = "Zeitpunkt", required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar zeitpunkt;

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
     * Gets the value of the zeitpunkt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getZeitpunkt() {
        return zeitpunkt;
    }

    /**
     * Sets the value of the zeitpunkt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setZeitpunkt(XMLGregorianCalendar value) {
        this.zeitpunkt = value;
    }

}
