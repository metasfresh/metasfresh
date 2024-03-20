
package de.metas.postfinance.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchInvoicesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchInvoicesResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InvoiceCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="TotalInvoiceCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="InvoiceList" type="{http://swisspost_ch.ebs.ebill.b2bservice}ArrayOfSearchInvoice" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchInvoicesResponse", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "invoiceCount",
    "totalInvoiceCount",
    "invoiceList"
})
public class SearchInvoicesResponse2 {

    @XmlElement(name = "InvoiceCount")
    protected Integer invoiceCount;
    @XmlElement(name = "TotalInvoiceCount")
    protected Integer totalInvoiceCount;
    @XmlElementRef(name = "InvoiceList", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfSearchInvoice> invoiceList;

    /**
     * Gets the value of the invoiceCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    /**
     * Sets the value of the invoiceCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInvoiceCount(Integer value) {
        this.invoiceCount = value;
    }

    /**
     * Gets the value of the totalInvoiceCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalInvoiceCount() {
        return totalInvoiceCount;
    }

    /**
     * Sets the value of the totalInvoiceCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalInvoiceCount(Integer value) {
        this.totalInvoiceCount = value;
    }

    /**
     * Gets the value of the invoiceList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfSearchInvoice }{@code >}
     *     
     */
    public JAXBElement<ArrayOfSearchInvoice> getInvoiceList() {
        return invoiceList;
    }

    /**
     * Sets the value of the invoiceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfSearchInvoice }{@code >}
     *     
     */
    public void setInvoiceList(JAXBElement<ArrayOfSearchInvoice> value) {
        this.invoiceList = value;
    }

}
