
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 *         Die Struktur (Artikel) bezüglich Mengen muss zwischen Anfrage und Antwort identisch sein
 *       
 * 
 * <p>Java class for VerfuegbarkeitsanfrageEinzelneAntwort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VerfuegbarkeitsanfrageEinzelneAntwort">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Artikel" type="{urn:msv3:v1}VerfuegbarkeitsantwortArtikel" maxOccurs="50"/>
 *       </sequence>
 *       <attribute name="Id" use="required" type="{urn:msv3:v1}uuid" />
 *       <attribute name="RTyp" use="required" type="{urn:msv3:v1}VerfuegbarkeitTyp" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerfuegbarkeitsanfrageEinzelneAntwort", propOrder = {
    "artikel"
})
public class VerfuegbarkeitsanfrageEinzelneAntwort {

    @XmlElement(name = "Artikel", required = true)
    protected List<VerfuegbarkeitsantwortArtikel> artikel;
    @XmlAttribute(name = "Id", required = true)
    protected String id;
    @XmlAttribute(name = "RTyp", required = true)
    protected VerfuegbarkeitTyp rTyp;

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
     * {@link VerfuegbarkeitsantwortArtikel }
     * 
     * 
     * @return
     *     The value of the artikel property.
     */
    public List<VerfuegbarkeitsantwortArtikel> getArtikel() {
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

    /**
     * Gets the value of the rTyp property.
     * 
     * @return
     *     possible object is
     *     {@link VerfuegbarkeitTyp }
     *     
     */
    public VerfuegbarkeitTyp getRTyp() {
        return rTyp;
    }

    /**
     * Sets the value of the rTyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerfuegbarkeitTyp }
     *     
     */
    public void setRTyp(VerfuegbarkeitTyp value) {
        this.rTyp = value;
    }

}
