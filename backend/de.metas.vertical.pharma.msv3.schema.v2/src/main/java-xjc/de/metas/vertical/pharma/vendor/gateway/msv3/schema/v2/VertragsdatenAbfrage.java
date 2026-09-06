
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Abfrage Vertragsdaten für MSV3 inklusive Sondertage
 * 
 * <p>Java class for VertragsdatenAbfrage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VertragsdatenAbfrage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="AutomatischerAbruf" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VertragsdatenAbfrage")
public class VertragsdatenAbfrage {

    @XmlAttribute(name = "AutomatischerAbruf", required = true)
    protected boolean automatischerAbruf;

    /**
     * Gets the value of the automatischerAbruf property.
     * 
     */
    public boolean isAutomatischerAbruf() {
        return automatischerAbruf;
    }

    /**
     * Sets the value of the automatischerAbruf property.
     * 
     */
    public void setAutomatischerAbruf(boolean value) {
        this.automatischerAbruf = value;
    }

}
