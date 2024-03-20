
package de.metas.postfinance.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProcessedInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProcessedInvoice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProcessedInvoice" type="{http://swisspost_ch.ebs.ebill.b2bservice}ProcessedInvoice" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProcessedInvoice", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "processedInvoice"
})
public class ArrayOfProcessedInvoice {

    @XmlElement(name = "ProcessedInvoice", nillable = true)
    protected List<ProcessedInvoice> processedInvoice;

    /**
     * Gets the value of the processedInvoice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the processedInvoice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcessedInvoice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProcessedInvoice }
     * 
     * 
     */
    public List<ProcessedInvoice> getProcessedInvoice() {
        if (processedInvoice == null) {
            processedInvoice = new ArrayList<ProcessedInvoice>();
        }
        return this.processedInvoice;
    }

}
