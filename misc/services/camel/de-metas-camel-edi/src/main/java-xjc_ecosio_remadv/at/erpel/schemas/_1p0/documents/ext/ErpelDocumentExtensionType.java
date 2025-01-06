
package at.erpel.schemas._1p0.documents.ext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErpelDocumentExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErpelDocumentExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}Attachments" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErpelDocumentExtensionType", propOrder = {
    "attachments"
})
public class ErpelDocumentExtensionType {

    @XmlElement(name = "Attachments")
    protected AttachmentsType attachments;

    /**
     * Gets the value of the attachments property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentsType }
     *     
     */
    public AttachmentsType getAttachments() {
        return attachments;
    }

    /**
     * Sets the value of the attachments property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentsType }
     *     
     */
    public void setAttachments(AttachmentsType value) {
        this.attachments = value;
    }

}
