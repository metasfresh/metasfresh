
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for BestellungAntwortAuftrag complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="BestellungAntwortAuftrag">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Positionen" type="{urn:msv3:v1}BestellungAntwortPosition" maxOccurs="1000" minOccurs="0"/>
 *         <element name="Auftragsfehler" type="{urn:msv3:v1}Msv3FaultInfo" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="Id" use="required" type="{urn:msv3:v1}uuid" />
 *       <attribute name="Auftragsart" use="required" type="{urn:msv3:v1}Auftragsart" />
 *       <attribute name="Auftragskennung" use="required" type="{urn:msv3:v1}DruckbareKennung" />
 *       <attribute name="GebindeId" type="{urn:msv3:v1}DruckbareKennung" />
 *       <attribute name="AuftragsSupportID" use="required">
 *         <simpleType>
 *           <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             <minInclusive value="1"/>
 *             <maxInclusive value="999999"/>
 *           </restriction>
 *         </simpleType>
 *       </attribute>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BestellungAntwortAuftrag", propOrder = {
    "positionen",
    "auftragsfehler"
})
public class BestellungAntwortAuftrag {

    @XmlElement(name = "Positionen")
    protected List<BestellungAntwortPosition> positionen;
    @XmlElement(name = "Auftragsfehler")
    protected Msv3FaultInfo auftragsfehler;
    @XmlAttribute(name = "Id", required = true)
    protected String id;
    @XmlAttribute(name = "Auftragsart", required = true)
    protected Auftragsart auftragsart;
    @XmlAttribute(name = "Auftragskennung", required = true)
    protected String auftragskennung;
    @XmlAttribute(name = "GebindeId")
    protected String gebindeId;
    @XmlAttribute(name = "AuftragsSupportID", required = true)
    protected int auftragsSupportID;

    /**
     * Gets the value of the positionen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the positionen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPositionen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BestellungAntwortPosition }
     * 
     * 
     * @return
     *     The value of the positionen property.
     */
    public List<BestellungAntwortPosition> getPositionen() {
        if (positionen == null) {
            positionen = new ArrayList<>();
        }
        return this.positionen;
    }

    /**
     * Gets the value of the auftragsfehler property.
     * 
     * @return
     *     possible object is
     *     {@link Msv3FaultInfo }
     *     
     */
    public Msv3FaultInfo getAuftragsfehler() {
        return auftragsfehler;
    }

    /**
     * Sets the value of the auftragsfehler property.
     * 
     * @param value
     *     allowed object is
     *     {@link Msv3FaultInfo }
     *     
     */
    public void setAuftragsfehler(Msv3FaultInfo value) {
        this.auftragsfehler = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the auftragsart property.
     * 
     * @return
     *     possible object is
     *     {@link Auftragsart }
     *     
     */
    public Auftragsart getAuftragsart() {
        return auftragsart;
    }

    /**
     * Sets the value of the auftragsart property.
     * 
     * @param value
     *     allowed object is
     *     {@link Auftragsart }
     *     
     */
    public void setAuftragsart(Auftragsart value) {
        this.auftragsart = value;
    }

    /**
     * Gets the value of the auftragskennung property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuftragskennung() {
        return auftragskennung;
    }

    /**
     * Sets the value of the auftragskennung property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuftragskennung(String value) {
        this.auftragskennung = value;
    }

    /**
     * Gets the value of the gebindeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGebindeId() {
        return gebindeId;
    }

    /**
     * Sets the value of the gebindeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGebindeId(String value) {
        this.gebindeId = value;
    }

    /**
     * Gets the value of the auftragsSupportID property.
     * 
     */
    public int getAuftragsSupportID() {
        return auftragsSupportID;
    }

    /**
     * Sets the value of the auftragsSupportID property.
     * 
     */
    public void setAuftragsSupportID(int value) {
        this.auftragsSupportID = value;
    }

}
