
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dokumentAbfragen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="dokumentAbfragen">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/>
 *         <element name="dokumentAbfragenType" type="{urn:msv3:v2}DokumentAbfragenType" minOccurs="0" form="unqualified"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dokumentAbfragen", propOrder = {
    "clientSoftwareKennung",
    "dokumentAbfragenType"
})
public class DokumentAbfragen {

    @XmlElement(namespace = "")
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "")
    protected DokumentAbfragenType dokumentAbfragenType;

    /**
     * Gets the value of the clientSoftwareKennung property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSoftwareKennung() {
        return clientSoftwareKennung;
    }

    /**
     * Sets the value of the clientSoftwareKennung property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSoftwareKennung(String value) {
        this.clientSoftwareKennung = value;
    }

    /**
     * Gets the value of the dokumentAbfragenType property.
     * 
     * @return
     *     possible object is
     *     {@link DokumentAbfragenType }
     *     
     */
    public DokumentAbfragenType getDokumentAbfragenType() {
        return dokumentAbfragenType;
    }

    /**
     * Sets the value of the dokumentAbfragenType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DokumentAbfragenType }
     *     
     */
    public void setDokumentAbfragenType(DokumentAbfragenType value) {
        this.dokumentAbfragenType = value;
    }

}
