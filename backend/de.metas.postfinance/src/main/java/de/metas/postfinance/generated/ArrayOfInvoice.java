
package de.metas.postfinance.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfInvoice">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Invoice" type="{http://swisspost_ch.ebs.ebill.b2bservice}Invoice" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInvoice", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "invoice"
})
public class ArrayOfInvoice {

    @XmlElement(name = "Invoice", nillable = true)
    protected List<Invoice> invoice;

    /**
     * Gets the value of the invoice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the invoice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Invoice }
     * 
     * 
     * @return
     *     The value of the invoice property.
     */
    public List<Invoice> getInvoice() {
        if (invoice == null) {
            invoice = new ArrayList<>();
        }
        return this.invoice;
    }

}
