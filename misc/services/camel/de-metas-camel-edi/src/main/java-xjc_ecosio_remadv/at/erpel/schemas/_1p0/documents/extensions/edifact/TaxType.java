
package at.erpel.schemas._1p0.documents.extensions.edifact;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * To specify relevant duty/tax/fee information.
 * 
 * <p>Java class for TaxType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}VAT" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}OtherTax" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxType", propOrder = {
    "vat",
    "otherTax"
})
public class TaxType {

    @XmlElement(name = "VAT")
    protected VATType vat;
    @XmlElement(name = "OtherTax")
    protected List<OtherTaxType> otherTax;

    /**
     * Used to provide information about the VAT or a potential tax exemptation (if applicable).
     * 
     * @return
     *     possible object is
     *     {@link VATType }
     *     
     */
    public VATType getVAT() {
        return vat;
    }

    /**
     * Sets the value of the vat property.
     * 
     * @param value
     *     allowed object is
     *     {@link VATType }
     *     
     */
    public void setVAT(VATType value) {
        this.vat = value;
    }

    /**
     * Used to provide information about other tax.Gets the value of the otherTax property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherTax property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherTax().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OtherTaxType }
     * 
     * 
     */
    public List<OtherTaxType> getOtherTax() {
        if (otherTax == null) {
            otherTax = new ArrayList<OtherTaxType>();
        }
        return this.otherTax;
    }

}
