
package de.metas.postfinance.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="invoices" type="{http://swisspost_ch.ebs.ebill.b2bservice}ArrayOfInvoice"/>
 *         <element name="BillerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "invoices",
    "billerID"
})
@XmlRootElement(name = "UploadFilesReport")
public class UploadFilesReport {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfInvoice invoices;
    @XmlElement(name = "BillerID", required = true, nillable = true)
    protected String billerID;

    /**
     * Gets the value of the invoices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInvoice }
     *     
     */
    public ArrayOfInvoice getInvoices() {
        return invoices;
    }

    /**
     * Sets the value of the invoices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInvoice }
     *     
     */
    public void setInvoices(ArrayOfInvoice value) {
        this.invoices = value;
    }

    /**
     * Gets the value of the billerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillerID() {
        return billerID;
    }

    /**
     * Sets the value of the billerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillerID(String value) {
        this.billerID = value;
    }

}
