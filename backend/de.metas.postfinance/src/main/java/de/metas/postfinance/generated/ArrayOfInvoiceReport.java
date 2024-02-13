
package de.metas.postfinance.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfInvoiceReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfInvoiceReport">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="InvoiceReport" type="{http://swisspost_ch.ebs.ebill.b2bservice}InvoiceReport" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInvoiceReport", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "invoiceReport"
})
public class ArrayOfInvoiceReport {

    @XmlElement(name = "InvoiceReport", nillable = true)
    protected List<InvoiceReport> invoiceReport;

    /**
     * Gets the value of the invoiceReport property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the invoiceReport property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoiceReport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvoiceReport }
     * 
     * 
     * @return
     *     The value of the invoiceReport property.
     */
    public List<InvoiceReport> getInvoiceReport() {
        if (invoiceReport == null) {
            invoiceReport = new ArrayList<>();
        }
        return this.invoiceReport;
    }

}
