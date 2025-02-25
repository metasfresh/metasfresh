
package at.erpel.schemas._1p0.documents.ext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}TaxExtension"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}ErpelTaxExtension"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxExtensionType", propOrder = {
    "taxExtension",
    "erpelTaxExtension"
})
public class TaxExtensionType {

    @XmlElement(name = "TaxExtension", namespace = "http://erpel.at/schemas/1p0/documents/extensions/edifact")
    protected at.erpel.schemas._1p0.documents.extensions.edifact.TaxExtensionType taxExtension;
    @XmlElement(name = "ErpelTaxExtension")
    protected CustomType erpelTaxExtension;

    /**
     * Gets the value of the taxExtension property.
     * 
     * @return
     *     possible object is
     *     {@link at.erpel.schemas._1p0.documents.extensions.edifact.TaxExtensionType }
     *     
     */
    public at.erpel.schemas._1p0.documents.extensions.edifact.TaxExtensionType getTaxExtension() {
        return taxExtension;
    }

    /**
     * Sets the value of the taxExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link at.erpel.schemas._1p0.documents.extensions.edifact.TaxExtensionType }
     *     
     */
    public void setTaxExtension(at.erpel.schemas._1p0.documents.extensions.edifact.TaxExtensionType value) {
        this.taxExtension = value;
    }

    /**
     * Gets the value of the erpelTaxExtension property.
     * 
     * @return
     *     possible object is
     *     {@link CustomType }
     *     
     */
    public CustomType getErpelTaxExtension() {
        return erpelTaxExtension;
    }

    /**
     * Sets the value of the erpelTaxExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomType }
     *     
     */
    public void setErpelTaxExtension(CustomType value) {
        this.erpelTaxExtension = value;
    }

}
