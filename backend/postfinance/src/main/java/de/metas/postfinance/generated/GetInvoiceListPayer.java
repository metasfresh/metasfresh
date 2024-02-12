
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
 *         <element name="eBillAccountID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="ArchiveData" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "eBillAccountID",
    "archiveData"
})
@XmlRootElement(name = "GetInvoiceListPayer")
public class GetInvoiceListPayer {

    @XmlElement(required = true, nillable = true)
    protected String eBillAccountID;
    @XmlElement(name = "ArchiveData")
    protected boolean archiveData;

    /**
     * Gets the value of the eBillAccountID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEBillAccountID() {
        return eBillAccountID;
    }

    /**
     * Sets the value of the eBillAccountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEBillAccountID(String value) {
        this.eBillAccountID = value;
    }

    /**
     * Gets the value of the archiveData property.
     * 
     */
    public boolean isArchiveData() {
        return archiveData;
    }

    /**
     * Sets the value of the archiveData property.
     * 
     */
    public void setArchiveData(boolean value) {
        this.archiveData = value;
    }

}
