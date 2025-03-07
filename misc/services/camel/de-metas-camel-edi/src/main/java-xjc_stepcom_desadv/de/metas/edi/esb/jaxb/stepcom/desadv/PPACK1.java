
package de.metas.edi.esb.jaxb.stepcom.desadv;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="PMESU1" type="{}PMESU1" maxOccurs="10" minOccurs="0"/&gt;
 *         &lt;element name="PHAND1" type="{}PHAND1" maxOccurs="10" minOccurs="0"/&gt;
 *         &lt;element name="DETAIL" type="{}DETAIL_Xlief" maxOccurs="999" minOccurs="0"/&gt;
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
    protected List<PMESU1> pmesu1;
    @XmlElement(name = "PHAND1")
    protected List<PHAND1> phand1;
    @XmlElement(name = "DETAIL")
    protected List<DETAILXlief> detail;

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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pmesu1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPMESU1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PMESU1 }
     * 
     * 
     */
    public List<PMESU1> getPMESU1() {
        if (pmesu1 == null) {
            pmesu1 = new ArrayList<PMESU1>();
        }
        return this.pmesu1;
    }

    /**
     * Gets the value of the phand1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phand1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPHAND1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PHAND1 }
     * 
     * 
     */
    public List<PHAND1> getPHAND1() {
        if (phand1 == null) {
            phand1 = new ArrayList<PHAND1>();
        }
        return this.phand1;
    }

    /**
     * Gets the value of the detail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDETAIL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DETAILXlief }
     * 
     * 
     */
    public List<DETAILXlief> getDETAIL() {
        if (detail == null) {
            detail = new ArrayList<DETAILXlief>();
        }
        return this.detail;
    }

}
