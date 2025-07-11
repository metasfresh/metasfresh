
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for VerfuegbarkeitsanfrageEinzelne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VerfuegbarkeitsanfrageEinzelne">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Artikel" maxOccurs="50">
 *           <complexType>
 *             <complexContent>
 *               <extension base="{urn:msv3:v2}ArtikelMenge">
 *                 <attribute name="Bedarf" use="required">
 *                   <simpleType>
 *                     <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       <enumeration value="direkt"/>
 *                       <enumeration value="einsAusN"/>
 *                       <enumeration value="unspezifisch"/>
 *                     </restriction>
 *                   </simpleType>
 *                 </attribute>
 *               </extension>
 *             </complexContent>
 *           </complexType>
 *         </element>
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
@XmlType(name = "VerfuegbarkeitsanfrageEinzelne", propOrder = {
    "artikel"
})
public class VerfuegbarkeitsanfrageEinzelne {

    @XmlElement(name = "Artikel", required = true)
    protected List<VerfuegbarkeitsanfrageEinzelne.Artikel> artikel;
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
     * {@link VerfuegbarkeitsanfrageEinzelne.Artikel }
     * 
     * 
     * @return
     *     The value of the artikel property.
     */
    public List<VerfuegbarkeitsanfrageEinzelne.Artikel> getArtikel() {
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>{@code
     * <complexType>
     *   <complexContent>
     *     <extension base="{urn:msv3:v2}ArtikelMenge">
     *       <attribute name="Bedarf" use="required">
     *         <simpleType>
     *           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             <enumeration value="direkt"/>
     *             <enumeration value="einsAusN"/>
     *             <enumeration value="unspezifisch"/>
     *           </restriction>
     *         </simpleType>
     *       </attribute>
     *     </extension>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Artikel
        extends ArtikelMenge
    {

        @XmlAttribute(name = "Bedarf", required = true)
        protected String bedarf;

        /**
         * Gets the value of the bedarf property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBedarf() {
            return bedarf;
        }

        /**
         * Sets the value of the bedarf property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBedarf(String value) {
            this.bedarf = value;
        }

    }

}
