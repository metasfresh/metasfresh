
package de.metas.postfinance.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfEBillRecipientSubscriptionStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfEBillRecipientSubscriptionStatus">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="EBillRecipientSubscriptionStatus" type="{http://swisspost_ch.ebs.ebill.b2bservice}EBillRecipientSubscriptionStatus" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfEBillRecipientSubscriptionStatus", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "eBillRecipientSubscriptionStatus"
})
public class ArrayOfEBillRecipientSubscriptionStatus {

    @XmlElement(name = "EBillRecipientSubscriptionStatus", nillable = true)
    protected List<EBillRecipientSubscriptionStatus> eBillRecipientSubscriptionStatus;

    /**
     * Gets the value of the eBillRecipientSubscriptionStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the eBillRecipientSubscriptionStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEBillRecipientSubscriptionStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EBillRecipientSubscriptionStatus }
     * 
     * 
     * @return
     *     The value of the eBillRecipientSubscriptionStatus property.
     */
    public List<EBillRecipientSubscriptionStatus> getEBillRecipientSubscriptionStatus() {
        if (eBillRecipientSubscriptionStatus == null) {
            eBillRecipientSubscriptionStatus = new ArrayList<>();
        }
        return this.eBillRecipientSubscriptionStatus;
    }

}
