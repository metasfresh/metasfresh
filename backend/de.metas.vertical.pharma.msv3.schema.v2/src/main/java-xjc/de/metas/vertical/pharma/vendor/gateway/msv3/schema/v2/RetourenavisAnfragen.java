
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for retourenavisAnfragen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="retourenavisAnfragen">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/>
 *         <element name="retourenavisAnfrageType" type="{urn:msv3:v2}RetourenavisAnfrageType" minOccurs="0" form="unqualified"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retourenavisAnfragen", propOrder = {
    "clientSoftwareKennung",
    "retourenavisAnfrageType"
})
public class RetourenavisAnfragen {

    @XmlElement(namespace = "")
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "")
    protected RetourenavisAnfrageType retourenavisAnfrageType;

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
     * Gets the value of the retourenavisAnfrageType property.
     * 
     * @return
     *     possible object is
     *     {@link RetourenavisAnfrageType }
     *     
     */
    public RetourenavisAnfrageType getRetourenavisAnfrageType() {
        return retourenavisAnfrageType;
    }

    /**
     * Sets the value of the retourenavisAnfrageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetourenavisAnfrageType }
     *     
     */
    public void setRetourenavisAnfrageType(RetourenavisAnfrageType value) {
        this.retourenavisAnfrageType = value;
    }

}
