
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VertragsdatenAuftragsart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VertragsdatenAuftragsart">
 *   <complexContent>
 *     <extension base="{urn:msv3:v2}VertragsdatenAuftragsartNormal">
 *       <attribute name="Vereinbart" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VertragsdatenAuftragsart")
@XmlSeeAlso({
    VertragsdatenAuftragsartVersand.class
})
public class VertragsdatenAuftragsart
    extends VertragsdatenAuftragsartNormal
{

    @XmlAttribute(name = "Vereinbart", required = true)
    protected boolean vereinbart;

    /**
     * Gets the value of the vereinbart property.
     * 
     */
    public boolean isVereinbart() {
        return vereinbart;
    }

    /**
     * Sets the value of the vereinbart property.
     * 
     */
    public void setVereinbart(boolean value) {
        this.vereinbart = value;
    }

}
