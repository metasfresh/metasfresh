
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

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
 * <p>Java class for Ruecknahmeangebot complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Ruecknahmeangebot">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Artikel" type="{urn:msv3:v2}ArtikelMenge" maxOccurs="1000"/>
 *       </sequence>
 *       <attribute name="Id" use="required" type="{urn:msv3:v2}uuid" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ruecknahmeangebot", propOrder = {
    "artikel"
})
public class Ruecknahmeangebot {

    @XmlElement(name = "Artikel", required = true)
    protected List<ArtikelMenge> artikel;
    @XmlAttribute(name = "Id", required = true)
    protected String id;

    /**
     * Gets the value of the artikel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the artikel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArtikel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArtikelMenge }
     * 
     * 
     * @return
     *     The value of the artikel property.
     */
    public List<ArtikelMenge> getArtikel() {
        if (artikel == null) {
            artikel = new ArrayList<>();
        }
        return this.artikel;
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

}
