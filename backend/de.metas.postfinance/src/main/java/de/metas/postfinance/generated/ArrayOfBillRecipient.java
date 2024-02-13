
package de.metas.postfinance.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfBillRecipient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfBillRecipient">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="BillRecipient" type="{http://swisspost_ch.ebs.ebill.b2bservice}BillRecipient" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfBillRecipient", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "billRecipient"
})
public class ArrayOfBillRecipient {

    @XmlElement(name = "BillRecipient", nillable = true)
    protected List<BillRecipient> billRecipient;

    /**
     * Gets the value of the billRecipient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the billRecipient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBillRecipient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BillRecipient }
     * 
     * 
     * @return
     *     The value of the billRecipient property.
     */
    public List<BillRecipient> getBillRecipient() {
        if (billRecipient == null) {
            billRecipient = new ArrayList<>();
        }
        return this.billRecipient;
    }

}
