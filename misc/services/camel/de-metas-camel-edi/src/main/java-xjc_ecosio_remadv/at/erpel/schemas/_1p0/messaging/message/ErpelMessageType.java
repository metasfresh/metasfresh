
package at.erpel.schemas._1p0.messaging.message;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.erpel.schemas._1p0.documents.DocumentType;
import at.erpel.schemas._1p0.messaging.header.ErpelBusinessDocumentHeaderType;


/**
 * <p>Java class for ErpelMessageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErpelMessageType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/messaging/header}ErpelBusinessDocumentHeader" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Document" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErpelMessageType", propOrder = {
    "erpelBusinessDocumentHeader",
    "document"
})
public class ErpelMessageType {

    @XmlElement(name = "ErpelBusinessDocumentHeader", namespace = "http://erpel.at/schemas/1p0/messaging/header")
    protected ErpelBusinessDocumentHeaderType erpelBusinessDocumentHeader;
    @XmlElement(name = "Document", namespace = "http://erpel.at/schemas/1p0/documents", required = true)
    protected List<DocumentType> document;

    /**
     * Gets the value of the erpelBusinessDocumentHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ErpelBusinessDocumentHeaderType }
     *     
     */
    public ErpelBusinessDocumentHeaderType getErpelBusinessDocumentHeader() {
        return erpelBusinessDocumentHeader;
    }

    /**
     * Sets the value of the erpelBusinessDocumentHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErpelBusinessDocumentHeaderType }
     *     
     */
    public void setErpelBusinessDocumentHeader(ErpelBusinessDocumentHeaderType value) {
        this.erpelBusinessDocumentHeader = value;
    }

    /**
     * Gets the value of the document property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the document property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentType }
     * 
     * 
     */
    public List<DocumentType> getDocument() {
        if (document == null) {
            document = new ArrayList<DocumentType>();
        }
        return this.document;
    }

}
