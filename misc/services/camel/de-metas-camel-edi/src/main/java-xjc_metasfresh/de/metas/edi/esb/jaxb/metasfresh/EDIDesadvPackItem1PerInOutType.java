
package de.metas.edi.esb.jaxb.metasfresh;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EDI_Desadv_Pack_Item_1PerInOutType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EDI_Desadv_Pack_Item_1PerInOutType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BestBeforeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="GTIN_TU_PackingMaterial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LotNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="M_HU_PackagingCode_TU_Text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EDI_DesadvLine_ID" type="{}EXP_M_InOut_DesadvLine_VType" minOccurs="0"/&gt;
 *         &lt;element name="QtyCUsPerLU" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="QtyCUsPerLU_InInvoiceUOM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="QtyCUsPerTU" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="QtyCUsPerTU_InInvoiceUOM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="QtyTU" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EDI_Desadv_Pack_Item_1PerInOutType", propOrder = {
    "bestBeforeDate",
    "gtintuPackingMaterial",
    "lotNumber",
    "mhuPackagingCodeTUText",
    "ediDesadvLineID",
    "qtyCUsPerLU",
    "qtyCUsPerLUInInvoiceUOM",
    "qtyCUsPerTU",
    "qtyCUsPerTUInInvoiceUOM",
    "qtyTU"
})
public class EDIDesadvPackItem1PerInOutType {

    @XmlElement(name = "BestBeforeDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar bestBeforeDate;
    @XmlElement(name = "GTIN_TU_PackingMaterial")
    protected String gtintuPackingMaterial;
    @XmlElement(name = "LotNumber")
    protected String lotNumber;
    @XmlElement(name = "M_HU_PackagingCode_TU_Text")
    protected String mhuPackagingCodeTUText;
    @XmlElement(name = "EDI_DesadvLine_ID")
    protected EXPMInOutDesadvLineVType ediDesadvLineID;
    @XmlElement(name = "QtyCUsPerLU")
    protected BigDecimal qtyCUsPerLU;
    @XmlElement(name = "QtyCUsPerLU_InInvoiceUOM")
    protected BigDecimal qtyCUsPerLUInInvoiceUOM;
    @XmlElement(name = "QtyCUsPerTU")
    protected BigDecimal qtyCUsPerTU;
    @XmlElement(name = "QtyCUsPerTU_InInvoiceUOM")
    protected BigDecimal qtyCUsPerTUInInvoiceUOM;
    @XmlElement(name = "QtyTU")
    protected BigInteger qtyTU;

    /**
     * Gets the value of the bestBeforeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBestBeforeDate() {
        return bestBeforeDate;
    }

    /**
     * Sets the value of the bestBeforeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBestBeforeDate(XMLGregorianCalendar value) {
        this.bestBeforeDate = value;
    }

    /**
     * Gets the value of the gtintuPackingMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGTINTUPackingMaterial() {
        return gtintuPackingMaterial;
    }

    /**
     * Sets the value of the gtintuPackingMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGTINTUPackingMaterial(String value) {
        this.gtintuPackingMaterial = value;
    }

    /**
     * Gets the value of the lotNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotNumber() {
        return lotNumber;
    }

    /**
     * Sets the value of the lotNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotNumber(String value) {
        this.lotNumber = value;
    }

    /**
     * Gets the value of the mhuPackagingCodeTUText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMHUPackagingCodeTUText() {
        return mhuPackagingCodeTUText;
    }

    /**
     * Sets the value of the mhuPackagingCodeTUText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMHUPackagingCodeTUText(String value) {
        this.mhuPackagingCodeTUText = value;
    }

    /**
     * Gets the value of the ediDesadvLineID property.
     * 
     * @return
     *     possible object is
     *     {@link EXPMInOutDesadvLineVType }
     *     
     */
    public EXPMInOutDesadvLineVType getEDIDesadvLineID() {
        return ediDesadvLineID;
    }

    /**
     * Sets the value of the ediDesadvLineID property.
     * 
     * @param value
     *     allowed object is
     *     {@link EXPMInOutDesadvLineVType }
     *     
     */
    public void setEDIDesadvLineID(EXPMInOutDesadvLineVType value) {
        this.ediDesadvLineID = value;
    }

    /**
     * Gets the value of the qtyCUsPerLU property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQtyCUsPerLU() {
        return qtyCUsPerLU;
    }

    /**
     * Sets the value of the qtyCUsPerLU property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQtyCUsPerLU(BigDecimal value) {
        this.qtyCUsPerLU = value;
    }

    /**
     * Gets the value of the qtyCUsPerLUInInvoiceUOM property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQtyCUsPerLUInInvoiceUOM() {
        return qtyCUsPerLUInInvoiceUOM;
    }

    /**
     * Sets the value of the qtyCUsPerLUInInvoiceUOM property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQtyCUsPerLUInInvoiceUOM(BigDecimal value) {
        this.qtyCUsPerLUInInvoiceUOM = value;
    }

    /**
     * Gets the value of the qtyCUsPerTU property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQtyCUsPerTU() {
        return qtyCUsPerTU;
    }

    /**
     * Sets the value of the qtyCUsPerTU property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQtyCUsPerTU(BigDecimal value) {
        this.qtyCUsPerTU = value;
    }

    /**
     * Gets the value of the qtyCUsPerTUInInvoiceUOM property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQtyCUsPerTUInInvoiceUOM() {
        return qtyCUsPerTUInInvoiceUOM;
    }

    /**
     * Sets the value of the qtyCUsPerTUInInvoiceUOM property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQtyCUsPerTUInInvoiceUOM(BigDecimal value) {
        this.qtyCUsPerTUInInvoiceUOM = value;
    }

    /**
     * Gets the value of the qtyTU property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQtyTU() {
        return qtyTU;
    }

    /**
     * Sets the value of the qtyTU property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQtyTU(BigInteger value) {
        this.qtyTU = value;
    }

}
