
package at.erpel.schemas._1p0.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.erpel.schemas._1p0.documents.ext.SupplierExtensionType;


/**
 * <p>Java class for SupplierType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplierType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://erpel.at/schemas/1p0/documents}BusinessEntityType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}EBPP_ConsolidatorsBillerID" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}SupplierIDissuedByCustomer" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}SupplierExtension" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplierType", propOrder = {
    "ebppConsolidatorsBillerID",
    "supplierIDissuedByCustomer",
    "supplierExtension"
})
public class SupplierType
    extends BusinessEntityType
{

    @XmlElement(name = "EBPP_ConsolidatorsBillerID")
    protected String ebppConsolidatorsBillerID;
    @XmlElement(name = "SupplierIDissuedByCustomer")
    protected String supplierIDissuedByCustomer;
    @XmlElement(name = "SupplierExtension", namespace = "http://erpel.at/schemas/1p0/documents/ext")
    protected SupplierExtensionType supplierExtension;

    /**
     * ID of the supplier at the consolidator's side.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEBPPConsolidatorsBillerID() {
        return ebppConsolidatorsBillerID;
    }

    /**
     * Sets the value of the ebppConsolidatorsBillerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEBPPConsolidatorsBillerID(String value) {
        this.ebppConsolidatorsBillerID = value;
    }

    /**
     * The ID of the supplier issued by the customer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierIDissuedByCustomer() {
        return supplierIDissuedByCustomer;
    }

    /**
     * Sets the value of the supplierIDissuedByCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierIDissuedByCustomer(String value) {
        this.supplierIDissuedByCustomer = value;
    }

    /**
     * Gets the value of the supplierExtension property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierExtensionType }
     *     
     */
    public SupplierExtensionType getSupplierExtension() {
        return supplierExtension;
    }

    /**
     * Sets the value of the supplierExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierExtensionType }
     *     
     */
    public void setSupplierExtension(SupplierExtensionType value) {
        this.supplierExtension = value;
    }

}
