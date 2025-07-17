
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * Bestätigung des Lieferavis-Empfangs
 * 
 * <p>Java class for LieferavisBestaetigenType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="LieferavisBestaetigenType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Lieferscheinnummer" type="{urn:msv3:v2}LieferscheinnummerType" maxOccurs="999"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LieferavisBestaetigenType", propOrder = {
    "lieferscheinnummer"
})
public class LieferavisBestaetigenType {

    @XmlElement(name = "Lieferscheinnummer", required = true)
    protected List<String> lieferscheinnummer;

    /**
     * Gets the value of the lieferscheinnummer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the lieferscheinnummer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLieferscheinnummer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     * @return
     *     The value of the lieferscheinnummer property.
     */
    public List<String> getLieferscheinnummer() {
        if (lieferscheinnummer == null) {
            lieferscheinnummer = new ArrayList<>();
        }
        return this.lieferscheinnummer;
    }

}
