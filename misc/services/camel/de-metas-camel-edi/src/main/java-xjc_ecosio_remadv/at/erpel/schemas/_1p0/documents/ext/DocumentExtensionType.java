
package at.erpel.schemas._1p0.documents.ext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DocumentExtension"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}ErpelDocumentExtension"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentExtensionType", propOrder = {
    "documentExtension",
    "erpelDocumentExtension"
})
public class DocumentExtensionType {

    @XmlElement(name = "DocumentExtension", namespace = "http://erpel.at/schemas/1p0/documents/extensions/edifact")
    protected at.erpel.schemas._1p0.documents.extensions.edifact.DocumentExtensionType documentExtension;
    @XmlElement(name = "ErpelDocumentExtension")
    protected ErpelDocumentExtensionType erpelDocumentExtension;

    /**
     * Gets the value of the documentExtension property.
     * 
     * @return
     *     possible object is
     *     {@link at.erpel.schemas._1p0.documents.extensions.edifact.DocumentExtensionType }
     *     
     */
    public at.erpel.schemas._1p0.documents.extensions.edifact.DocumentExtensionType getDocumentExtension() {
        return documentExtension;
    }

    /**
     * Sets the value of the documentExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link at.erpel.schemas._1p0.documents.extensions.edifact.DocumentExtensionType }
     *     
     */
    public void setDocumentExtension(at.erpel.schemas._1p0.documents.extensions.edifact.DocumentExtensionType value) {
        this.documentExtension = value;
    }

    /**
     * Gets the value of the erpelDocumentExtension property.
     * 
     * @return
     *     possible object is
     *     {@link ErpelDocumentExtensionType }
     *     
     */
    public ErpelDocumentExtensionType getErpelDocumentExtension() {
        return erpelDocumentExtension;
    }

    /**
     * Sets the value of the erpelDocumentExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErpelDocumentExtensionType }
     *     
     */
    public void setErpelDocumentExtension(ErpelDocumentExtensionType value) {
        this.erpelDocumentExtension = value;
    }

}
