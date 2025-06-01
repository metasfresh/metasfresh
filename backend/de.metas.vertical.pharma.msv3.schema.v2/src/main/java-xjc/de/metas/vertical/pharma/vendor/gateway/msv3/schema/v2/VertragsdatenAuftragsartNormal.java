
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VertragsdatenAuftragsartNormal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VertragsdatenAuftragsartNormal">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <attribute name="MaxAnzahlFreieAuftragskennungen" use="required" type="{urn:msv3:v2}MSV3AnzahlAuftragskennungen" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VertragsdatenAuftragsartNormal")
@XmlSeeAlso({
    VertragsdatenAuftragsart.class
})
public class VertragsdatenAuftragsartNormal {

    @XmlAttribute(name = "MaxAnzahlFreieAuftragskennungen", required = true)
    protected int maxAnzahlFreieAuftragskennungen;

    /**
     * Gets the value of the maxAnzahlFreieAuftragskennungen property.
     * 
     */
    public int getMaxAnzahlFreieAuftragskennungen() {
        return maxAnzahlFreieAuftragskennungen;
    }

    /**
     * Sets the value of the maxAnzahlFreieAuftragskennungen property.
     * 
     */
    public void setMaxAnzahlFreieAuftragskennungen(int value) {
        this.maxAnzahlFreieAuftragskennungen = value;
    }

}
