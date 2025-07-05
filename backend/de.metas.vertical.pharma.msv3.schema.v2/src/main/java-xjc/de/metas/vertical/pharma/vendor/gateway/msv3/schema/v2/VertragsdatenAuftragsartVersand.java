
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VertragsdatenAuftragsartVersand complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VertragsdatenAuftragsartVersand">
 *   <complexContent>
 *     <extension base="{urn:msv3:v2}VertragsdatenAuftragsart">
 *       <attribute name="NurKompletteGebinde" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VertragsdatenAuftragsartVersand")
public class VertragsdatenAuftragsartVersand
    extends VertragsdatenAuftragsart
{

    @XmlAttribute(name = "NurKompletteGebinde", required = true)
    protected boolean nurKompletteGebinde;

    /**
     * Gets the value of the nurKompletteGebinde property.
     * 
     */
    public boolean isNurKompletteGebinde() {
        return nurKompletteGebinde;
    }

    /**
     * Sets the value of the nurKompletteGebinde property.
     * 
     */
    public void setNurKompletteGebinde(boolean value) {
        this.nurKompletteGebinde = value;
    }

}
