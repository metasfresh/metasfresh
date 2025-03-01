
package at.erpel.schemas._1p0.documents.extensions.edifact;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ShipperExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipperExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TypeOfTransportDocument" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TransportDocumentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TransportDocumentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="SCAC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipperExtensionType", propOrder = {
    "typeOfTransportDocument",
    "transportDocumentNumber",
    "transportDocumentDate",
    "scac"
})
public class ShipperExtensionType {

    @XmlElement(name = "TypeOfTransportDocument")
    protected String typeOfTransportDocument;
    @XmlElement(name = "TransportDocumentNumber")
    protected String transportDocumentNumber;
    @XmlElement(name = "TransportDocumentDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transportDocumentDate;
    @XmlElement(name = "SCAC")
    protected String scac;

    /**
     * Gets the value of the typeOfTransportDocument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfTransportDocument() {
        return typeOfTransportDocument;
    }

    /**
     * Sets the value of the typeOfTransportDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfTransportDocument(String value) {
        this.typeOfTransportDocument = value;
    }

    /**
     * Gets the value of the transportDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransportDocumentNumber() {
        return transportDocumentNumber;
    }

    /**
     * Sets the value of the transportDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransportDocumentNumber(String value) {
        this.transportDocumentNumber = value;
    }

    /**
     * Gets the value of the transportDocumentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransportDocumentDate() {
        return transportDocumentDate;
    }

    /**
     * Sets the value of the transportDocumentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransportDocumentDate(XMLGregorianCalendar value) {
        this.transportDocumentDate = value;
    }

    /**
     * Gets the value of the scac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSCAC() {
        return scac;
    }

    /**
     * Sets the value of the scac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSCAC(String value) {
        this.scac = value;
    }

}
