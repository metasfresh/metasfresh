
package de.metas.postfinance.generated;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
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
 *         <element name="GetInvoiceListPayerResult" type="{http://swisspost_ch.ebs.ebill.b2bservice}ArrayOfInvoiceReport" minOccurs="0"/>
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
    "getInvoiceListPayerResult"
})
@XmlRootElement(name = "GetInvoiceListPayerResponse")
public class GetInvoiceListPayerResponse {

    @XmlElementRef(name = "GetInvoiceListPayerResult", namespace = "http://ch.swisspost.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfInvoiceReport> getInvoiceListPayerResult;

    /**
     * Gets the value of the getInvoiceListPayerResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfInvoiceReport }{@code >}
     *     
     */
    public JAXBElement<ArrayOfInvoiceReport> getGetInvoiceListPayerResult() {
        return getInvoiceListPayerResult;
    }

    /**
     * Sets the value of the getInvoiceListPayerResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfInvoiceReport }{@code >}
     *     
     */
    public void setGetInvoiceListPayerResult(JAXBElement<ArrayOfInvoiceReport> value) {
        this.getInvoiceListPayerResult = value;
    }

}
