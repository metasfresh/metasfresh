
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * nur die fachliche Anfrage
 * 
 *         Die Struktur (Aufträge und Positionen) bezüglich Mengen und Reihenfolgen muss zwischen Anfrage und Antwort identisch
 *         sein
 *       
 * 
 * <p>Java class for Bestellung complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Bestellung">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Auftraege" type="{urn:msv3:v1}BestellungAuftrag" maxOccurs="1000"/>
 *       </sequence>
 *       <attribute name="Id" use="required" type="{urn:msv3:v1}uuid" />
 *       <attribute name="BestellSupportId" use="required">
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
@XmlType(name = "Bestellung", propOrder = {
    "auftraege"
})
public class Bestellung {

    @XmlElement(name = "Auftraege", required = true)
    protected List<BestellungAuftrag> auftraege;
    @XmlAttribute(name = "Id", required = true)
    protected String id;
    @XmlAttribute(name = "BestellSupportId", required = true)
    protected int bestellSupportId;

    /**
     * Gets the value of the auftraege property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the auftraege property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuftraege().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BestellungAuftrag }
     * 
     * 
     * @return
     *     The value of the auftraege property.
     */
    public List<BestellungAuftrag> getAuftraege() {
        if (auftraege == null) {
            auftraege = new ArrayList<>();
        }
        return this.auftraege;
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
     * Gets the value of the bestellSupportId property.
     * 
     */
    public int getBestellSupportId() {
        return bestellSupportId;
    }

    /**
     * Sets the value of the bestellSupportId property.
     * 
     */
    public void setBestellSupportId(int value) {
        this.bestellSupportId = value;
    }

}
