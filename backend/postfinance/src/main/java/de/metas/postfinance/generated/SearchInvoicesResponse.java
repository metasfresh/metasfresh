
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
 *         <element name="SearchInvoicesResult" type="{http://swisspost_ch.ebs.ebill.b2bservice}SearchInvoicesResponse" minOccurs="0"/>
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
    "searchInvoicesResult"
})
@XmlRootElement(name = "SearchInvoicesResponse")
public class SearchInvoicesResponse {

    @XmlElementRef(name = "SearchInvoicesResult", namespace = "http://ch.swisspost.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<SearchInvoicesResponse2> searchInvoicesResult;

    /**
     * Gets the value of the searchInvoicesResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SearchInvoicesResponse2 }{@code >}
     *     
     */
    public JAXBElement<SearchInvoicesResponse2> getSearchInvoicesResult() {
        return searchInvoicesResult;
    }

    /**
     * Sets the value of the searchInvoicesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SearchInvoicesResponse2 }{@code >}
     *     
     */
    public void setSearchInvoicesResult(JAXBElement<SearchInvoicesResponse2> value) {
        this.searchInvoicesResult = value;
    }

}
