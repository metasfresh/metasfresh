
package at.erpel.schemas._1p0.documents.ext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}CustomerExtension"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}ErpelCustomerExtension"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerExtensionType", propOrder = {
    "customerExtension",
    "erpelCustomerExtension"
})
public class CustomerExtensionType {

    @XmlElement(name = "CustomerExtension", namespace = "http://erpel.at/schemas/1p0/documents/extensions/edifact")
    protected at.erpel.schemas._1p0.documents.extensions.edifact.CustomerExtensionType customerExtension;
    @XmlElement(name = "ErpelCustomerExtension")
    protected CustomType erpelCustomerExtension;

    /**
     * Gets the value of the customerExtension property.
     * 
     * @return
     *     possible object is
     *     {@link at.erpel.schemas._1p0.documents.extensions.edifact.CustomerExtensionType }
     *     
     */
    public at.erpel.schemas._1p0.documents.extensions.edifact.CustomerExtensionType getCustomerExtension() {
        return customerExtension;
    }

    /**
     * Sets the value of the customerExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link at.erpel.schemas._1p0.documents.extensions.edifact.CustomerExtensionType }
     *     
     */
    public void setCustomerExtension(at.erpel.schemas._1p0.documents.extensions.edifact.CustomerExtensionType value) {
        this.customerExtension = value;
    }

    /**
     * Gets the value of the erpelCustomerExtension property.
     * 
     * @return
     *     possible object is
     *     {@link CustomType }
     *     
     */
    public CustomType getErpelCustomerExtension() {
        return erpelCustomerExtension;
    }

    /**
     * Sets the value of the erpelCustomerExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomType }
     *     
     */
    public void setErpelCustomerExtension(CustomType value) {
        this.erpelCustomerExtension = value;
    }

}
