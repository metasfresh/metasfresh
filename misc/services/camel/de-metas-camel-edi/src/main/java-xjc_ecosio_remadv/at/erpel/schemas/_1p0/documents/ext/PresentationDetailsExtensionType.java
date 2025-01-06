
package at.erpel.schemas._1p0.documents.ext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PresentationDetailsExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PresentationDetailsExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}ErpelPresentationDetailsExtension"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PresentationDetailsExtensionType", propOrder = {
    "erpelPresentationDetailsExtension"
})
public class PresentationDetailsExtensionType {

    @XmlElement(name = "ErpelPresentationDetailsExtension")
    protected CustomType erpelPresentationDetailsExtension;

    /**
     * Gets the value of the erpelPresentationDetailsExtension property.
     * 
     * @return
     *     possible object is
     *     {@link CustomType }
     *     
     */
    public CustomType getErpelPresentationDetailsExtension() {
        return erpelPresentationDetailsExtension;
    }

    /**
     * Sets the value of the erpelPresentationDetailsExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomType }
     *     
     */
    public void setErpelPresentationDetailsExtension(CustomType value) {
        this.erpelPresentationDetailsExtension = value;
    }

}
