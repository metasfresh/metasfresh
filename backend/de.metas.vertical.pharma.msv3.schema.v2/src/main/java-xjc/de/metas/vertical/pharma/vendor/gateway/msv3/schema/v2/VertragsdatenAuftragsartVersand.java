
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VertragsdatenAuftragsartVersand complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VertragsdatenAuftragsartVersand"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn:msv3:v2}VertragsdatenAuftragsart"&gt;
 *       &lt;attribute name="NurKompletteGebinde" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
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
