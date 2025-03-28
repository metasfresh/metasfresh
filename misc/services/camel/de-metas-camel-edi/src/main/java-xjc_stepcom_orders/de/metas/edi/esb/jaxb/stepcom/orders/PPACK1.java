
package de.metas.edi.esb.jaxb.stepcom.orders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PPACK1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PPACK1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOCUMENTID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PACKAGINGDETAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PACKAGINGLEVEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PACKAGINGCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PACKAGINGTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IDENTIFICATIONQUAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IDENTIFICATIONCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HIERARCHICALPARENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PACKAGINGUNIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PACKAGINGUNITCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PMESU1" type="{}PMESU1" minOccurs="0"/&gt;
 *         &lt;element name="PHAND1" type="{}PHAND1" minOccurs="0"/&gt;
 *         &lt;element name="DETAIL" type="{}DETAIL_Xbest" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PPACK1", propOrder = {
    "documentid",
    "packagingdetail",
    "packaginglevel",
    "packagingcode",
    "packagingtype",
    "identificationqual",
    "identificationcode",
    "hierarchicalparent",
    "packagingunit",
    "packagingunitcode",
    "pmesu1",
    "phand1",
    "detail"
})
public class PPACK1 {

    @XmlElement(name = "DOCUMENTID", required = true)
    protected String documentid;
    @XmlElement(name = "PACKAGINGDETAIL")
    protected String packagingdetail;
    @XmlElement(name = "PACKAGINGLEVEL")
    protected String packaginglevel;
    @XmlElement(name = "PACKAGINGCODE")
    protected String packagingcode;
    @XmlElement(name = "PACKAGINGTYPE")
    protected String packagingtype;
    @XmlElement(name = "IDENTIFICATIONQUAL")
    protected String identificationqual;
    @XmlElement(name = "IDENTIFICATIONCODE")
    protected String identificationcode;
    @XmlElement(name = "HIERARCHICALPARENT")
    protected String hierarchicalparent;
    @XmlElement(name = "PACKAGINGUNIT")
    protected String packagingunit;
    @XmlElement(name = "PACKAGINGUNITCODE")
    protected String packagingunitcode;
    @XmlElement(name = "PMESU1")
    protected PMESU1 pmesu1;
    @XmlElement(name = "PHAND1")
    protected PHAND1 phand1;
    @XmlElement(name = "DETAIL")
    protected DETAILXbest detail;

    /**
     * Gets the value of the documentid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCUMENTID() {
        return documentid;
    }

    /**
     * Sets the value of the documentid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCUMENTID(String value) {
        this.documentid = value;
    }

    /**
     * Gets the value of the packagingdetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPACKAGINGDETAIL() {
        return packagingdetail;
    }

    /**
     * Sets the value of the packagingdetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPACKAGINGDETAIL(String value) {
        this.packagingdetail = value;
    }

    /**
     * Gets the value of the packaginglevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPACKAGINGLEVEL() {
        return packaginglevel;
    }

    /**
     * Sets the value of the packaginglevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPACKAGINGLEVEL(String value) {
        this.packaginglevel = value;
    }

    /**
     * Gets the value of the packagingcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPACKAGINGCODE() {
        return packagingcode;
    }

    /**
     * Sets the value of the packagingcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPACKAGINGCODE(String value) {
        this.packagingcode = value;
    }

    /**
     * Gets the value of the packagingtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPACKAGINGTYPE() {
        return packagingtype;
    }

    /**
     * Sets the value of the packagingtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPACKAGINGTYPE(String value) {
        this.packagingtype = value;
    }

    /**
     * Gets the value of the identificationqual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDENTIFICATIONQUAL() {
        return identificationqual;
    }

    /**
     * Sets the value of the identificationqual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDENTIFICATIONQUAL(String value) {
        this.identificationqual = value;
    }

    /**
     * Gets the value of the identificationcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDENTIFICATIONCODE() {
        return identificationcode;
    }

    /**
     * Sets the value of the identificationcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDENTIFICATIONCODE(String value) {
        this.identificationcode = value;
    }

    /**
     * Gets the value of the hierarchicalparent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHIERARCHICALPARENT() {
        return hierarchicalparent;
    }

    /**
     * Sets the value of the hierarchicalparent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHIERARCHICALPARENT(String value) {
        this.hierarchicalparent = value;
    }

    /**
     * Gets the value of the packagingunit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPACKAGINGUNIT() {
        return packagingunit;
    }

    /**
     * Sets the value of the packagingunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPACKAGINGUNIT(String value) {
        this.packagingunit = value;
    }

    /**
     * Gets the value of the packagingunitcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPACKAGINGUNITCODE() {
        return packagingunitcode;
    }

    /**
     * Sets the value of the packagingunitcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPACKAGINGUNITCODE(String value) {
        this.packagingunitcode = value;
    }

    /**
     * Gets the value of the pmesu1 property.
     * 
     * @return
     *     possible object is
     *     {@link PMESU1 }
     *     
     */
    public PMESU1 getPMESU1() {
        return pmesu1;
    }

    /**
     * Sets the value of the pmesu1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PMESU1 }
     *     
     */
    public void setPMESU1(PMESU1 value) {
        this.pmesu1 = value;
    }

    /**
     * Gets the value of the phand1 property.
     * 
     * @return
     *     possible object is
     *     {@link PHAND1 }
     *     
     */
    public PHAND1 getPHAND1() {
        return phand1;
    }

    /**
     * Sets the value of the phand1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PHAND1 }
     *     
     */
    public void setPHAND1(PHAND1 value) {
        this.phand1 = value;
    }

    /**
     * Gets the value of the detail property.
     * 
     * @return
     *     possible object is
     *     {@link DETAILXbest }
     *     
     */
    public DETAILXbest getDETAIL() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     * 
     * @param value
     *     allowed object is
     *     {@link DETAILXbest }
     *     
     */
    public void setDETAIL(DETAILXbest value) {
        this.detail = value;
    }

}
