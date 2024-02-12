
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
 *         <element name="Parameter" type="{http://swisspost_ch.ebs.ebill.b2bservice}SearchInvoiceParameter"/>
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
    "parameter"
})
@XmlRootElement(name = "SearchInvoices")
public class SearchInvoices {

    @XmlElement(name = "Parameter", required = true, nillable = true)
    protected SearchInvoiceParameter parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link SearchInvoiceParameter }
     *     
     */
    public SearchInvoiceParameter getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchInvoiceParameter }
     *     
     */
    public void setParameter(SearchInvoiceParameter value) {
        this.parameter = value;
    }

}
