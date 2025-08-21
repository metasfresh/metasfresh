
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RetourePositionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="RetourePositionType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <attribute name="PosRefID" use="required">
 *         <simpleType>
 *           <restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *             <minInclusive value="1"/>
 *             <maxInclusive value="999"/>
 *           </restriction>
 *         </simpleType>
 *       </attribute>
 *       <attribute name="Lieferscheinnummer" use="required" type="{urn:msv3:v2}LieferscheinnummerType" />
 *       <attribute name="PZN" use="required" type="{urn:msv3:v2}pzn" />
 *       <attribute name="RetourenMenge" use="required">
 *         <simpleType>
 *           <restriction base="{urn:msv3:v2}MengeType">
 *             <maxInclusive value="9999"/>
 *           </restriction>
 *         </simpleType>
 *       </attribute>
 *       <attribute name="Retouregrund" use="required" type="{urn:msv3:v2}RetoureGrund" />
 *       <attribute name="Charge">
 *         <simpleType>
 *           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             <minLength value="0"/>
 *             <maxLength value="50"/>
 *           </restriction>
 *         </simpleType>
 *       </attribute>
 *       <attribute name="Verfalldatum" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetourePositionType")
@XmlSeeAlso({
    de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.RetourenavisAnfrageAntwort.Position.class,
    de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.RetourenavisAnkuendigungAntwort.RetourenAnteil.Position.class
})
public class RetourePositionType {

    @XmlAttribute(name = "PosRefID", required = true)
    protected short posRefID;
    @XmlAttribute(name = "Lieferscheinnummer", required = true)
    protected String lieferscheinnummer;
    @XmlAttribute(name = "PZN", required = true)
    protected long pzn;
    @XmlAttribute(name = "RetourenMenge", required = true)
    protected int retourenMenge;
    @XmlAttribute(name = "Retouregrund", required = true)
    protected RetoureGrund retouregrund;
    @XmlAttribute(name = "Charge")
    protected String charge;
    @XmlAttribute(name = "Verfalldatum")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar verfalldatum;

    /**
     * Gets the value of the posRefID property.
     * 
     */
    public short getPosRefID() {
        return posRefID;
    }

    /**
     * Sets the value of the posRefID property.
     * 
     */
    public void setPosRefID(short value) {
        this.posRefID = value;
    }

    /**
     * Gets the value of the lieferscheinnummer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLieferscheinnummer() {
        return lieferscheinnummer;
    }

    /**
     * Sets the value of the lieferscheinnummer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLieferscheinnummer(String value) {
        this.lieferscheinnummer = value;
    }

    /**
     * Gets the value of the pzn property.
     * 
     */
    public long getPZN() {
        return pzn;
    }

    /**
     * Sets the value of the pzn property.
     * 
     */
    public void setPZN(long value) {
        this.pzn = value;
    }

    /**
     * Gets the value of the retourenMenge property.
     * 
     */
    public int getRetourenMenge() {
        return retourenMenge;
    }

    /**
     * Sets the value of the retourenMenge property.
     * 
     */
    public void setRetourenMenge(int value) {
        this.retourenMenge = value;
    }

    /**
     * Gets the value of the retouregrund property.
     * 
     * @return
     *     possible object is
     *     {@link RetoureGrund }
     *     
     */
    public RetoureGrund getRetouregrund() {
        return retouregrund;
    }

    /**
     * Sets the value of the retouregrund property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetoureGrund }
     *     
     */
    public void setRetouregrund(RetoureGrund value) {
        this.retouregrund = value;
    }

    /**
     * Gets the value of the charge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharge() {
        return charge;
    }

    /**
     * Sets the value of the charge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharge(String value) {
        this.charge = value;
    }

    /**
     * Gets the value of the verfalldatum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVerfalldatum() {
        return verfalldatum;
    }

    /**
     * Sets the value of the verfalldatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVerfalldatum(XMLGregorianCalendar value) {
        this.verfalldatum = value;
    }

}
