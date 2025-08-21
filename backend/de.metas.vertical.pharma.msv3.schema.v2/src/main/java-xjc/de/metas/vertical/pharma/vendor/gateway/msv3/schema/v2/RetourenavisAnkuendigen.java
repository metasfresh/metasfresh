
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for retourenavisAnkuendigen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="retourenavisAnkuendigen">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="clientSoftwareKennung" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/>
 *         <element name="retourenavisAnkuendigungType" type="{urn:msv3:v2}RetourenavisAnkuendigungType" minOccurs="0" form="unqualified"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retourenavisAnkuendigen", propOrder = {
    "clientSoftwareKennung",
    "retourenavisAnkuendigungType"
})
public class RetourenavisAnkuendigen {

    @XmlElement(namespace = "")
    protected String clientSoftwareKennung;
    @XmlElement(namespace = "")
    protected RetourenavisAnkuendigungType retourenavisAnkuendigungType;

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
     * Gets the value of the retourenavisAnkuendigungType property.
     * 
     * @return
     *     possible object is
     *     {@link RetourenavisAnkuendigungType }
     *     
     */
    public RetourenavisAnkuendigungType getRetourenavisAnkuendigungType() {
        return retourenavisAnkuendigungType;
    }

    /**
     * Sets the value of the retourenavisAnkuendigungType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetourenavisAnkuendigungType }
     *     
     */
    public void setRetourenavisAnkuendigungType(RetourenavisAnkuendigungType value) {
        this.retourenavisAnkuendigungType = value;
    }

}
