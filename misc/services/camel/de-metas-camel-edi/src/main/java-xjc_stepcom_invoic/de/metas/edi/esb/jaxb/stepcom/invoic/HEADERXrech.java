
package de.metas.edi.esb.jaxb.stepcom.invoic;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HEADER_Xrech complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HEADER_Xrech"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOCUMENTID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PARTNERID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OWNERID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TESTINDICATOR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="APPLICATIONREF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INTERCHANGEREF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MESSAGEREF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DOCUMENTTYP" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DOCUMENTFUNCTION" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DOCUMENTANSWER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HDATE1" type="{}HDATE1" maxOccurs="3" minOccurs="2"/&gt;
 *         &lt;element name="HADDI1" type="{}HADDI1" maxOccurs="3" minOccurs="0"/&gt;
 *         &lt;element name="HTEXT1" type="{}HTEXT1" maxOccurs="6" minOccurs="0"/&gt;
 *         &lt;element name="HREFE1" type="{}HREFE1" maxOccurs="7"/&gt;
 *         &lt;element name="HADRE1" type="{}HADRE1" maxOccurs="8" minOccurs="3"/&gt;
 *         &lt;element name="HCURR1" type="{}HCURR1" maxOccurs="2"/&gt;
 *         &lt;element name="HPAYT1" type="{}HPAYT1" maxOccurs="2" minOccurs="0"/&gt;
 *         &lt;element name="HTRSD1" type="{}HTRSD1" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="HTRSC1" type="{}HTRSC1" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="HALCH1" type="{}HALCH1" maxOccurs="10" minOccurs="0"/&gt;
 *         &lt;element name="DETAIL" type="{}DETAIL_Xrech" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HEADER_Xrech", propOrder = {
    "documentid",
    "partnerid",
    "ownerid",
    "testindicator",
    "applicationref",
    "interchangeref",
    "messageref",
    "documenttyp",
    "documentfunction",
    "documentanswer",
    "hdate1",
    "haddi1",
    "htext1",
    "hrefe1",
    "hadre1",
    "hcurr1",
    "hpayt1",
    "halch1",
    "detail"
})
public class HEADERXrech {

    @XmlElement(name = "DOCUMENTID", required = true)
    protected String documentid;
    @XmlElement(name = "PARTNERID", required = true)
    protected String partnerid;
    @XmlElement(name = "OWNERID", required = true)
    protected String ownerid;
    @XmlElement(name = "TESTINDICATOR")
    protected String testindicator;
    @XmlElement(name = "APPLICATIONREF")
    protected String applicationref;
    @XmlElement(name = "INTERCHANGEREF")
    protected String interchangeref;
    @XmlElement(name = "MESSAGEREF")
    protected String messageref;
    @XmlElement(name = "DOCUMENTTYP", required = true)
    protected String documenttyp;
    @XmlElement(name = "DOCUMENTFUNCTION", required = true)
    protected String documentfunction;
    @XmlElement(name = "DOCUMENTANSWER")
    protected String documentanswer;
    @XmlElement(name = "HDATE1", required = true)
    protected List<HDATE1> hdate1;
    @XmlElement(name = "HADDI1")
    protected List<HADDI1> haddi1;
    @XmlElement(name = "HTEXT1")
    protected List<HTEXT1> htext1;
    @XmlElement(name = "HREFE1", required = true)
    protected List<HREFE1> hrefe1;
    @XmlElement(name = "HADRE1", required = true)
    protected List<HADRE1> hadre1;
    @XmlElement(name = "HCURR1", required = true)
    protected List<HCURR1> hcurr1;
    @XmlElement(name = "HPAYT1")
    protected List<HPAYT1> hpayt1;
    @XmlElement(name = "HALCH1")
    protected List<HALCH1> halch1;
    @XmlElement(name = "DETAIL", required = true)
    protected List<DETAILXrech> detail;

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
     * Gets the value of the partnerid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARTNERID() {
        return partnerid;
    }

    /**
     * Sets the value of the partnerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARTNERID(String value) {
        this.partnerid = value;
    }

    /**
     * Gets the value of the ownerid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOWNERID() {
        return ownerid;
    }

    /**
     * Sets the value of the ownerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOWNERID(String value) {
        this.ownerid = value;
    }

    /**
     * Gets the value of the testindicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTESTINDICATOR() {
        return testindicator;
    }

    /**
     * Sets the value of the testindicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTESTINDICATOR(String value) {
        this.testindicator = value;
    }

    /**
     * Gets the value of the applicationref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPPLICATIONREF() {
        return applicationref;
    }

    /**
     * Sets the value of the applicationref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPPLICATIONREF(String value) {
        this.applicationref = value;
    }

    /**
     * Gets the value of the interchangeref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINTERCHANGEREF() {
        return interchangeref;
    }

    /**
     * Sets the value of the interchangeref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINTERCHANGEREF(String value) {
        this.interchangeref = value;
    }

    /**
     * Gets the value of the messageref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEREF() {
        return messageref;
    }

    /**
     * Sets the value of the messageref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEREF(String value) {
        this.messageref = value;
    }

    /**
     * Gets the value of the documenttyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCUMENTTYP() {
        return documenttyp;
    }

    /**
     * Sets the value of the documenttyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCUMENTTYP(String value) {
        this.documenttyp = value;
    }

    /**
     * Gets the value of the documentfunction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCUMENTFUNCTION() {
        return documentfunction;
    }

    /**
     * Sets the value of the documentfunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCUMENTFUNCTION(String value) {
        this.documentfunction = value;
    }

    /**
     * Gets the value of the documentanswer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCUMENTANSWER() {
        return documentanswer;
    }

    /**
     * Sets the value of the documentanswer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCUMENTANSWER(String value) {
        this.documentanswer = value;
    }

    /**
     * Gets the value of the hdate1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hdate1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHDATE1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HDATE1 }
     * 
     * 
     */
    public List<HDATE1> getHDATE1() {
        if (hdate1 == null) {
            hdate1 = new ArrayList<HDATE1>();
        }
        return this.hdate1;
    }

    /**
     * Gets the value of the haddi1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the haddi1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHADDI1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HADDI1 }
     * 
     * 
     */
    public List<HADDI1> getHADDI1() {
        if (haddi1 == null) {
            haddi1 = new ArrayList<HADDI1>();
        }
        return this.haddi1;
    }

    /**
     * Gets the value of the htext1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the htext1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHTEXT1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HTEXT1 }
     * 
     * 
     */
    public List<HTEXT1> getHTEXT1() {
        if (htext1 == null) {
            htext1 = new ArrayList<HTEXT1>();
        }
        return this.htext1;
    }

    /**
     * Gets the value of the hrefe1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hrefe1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHREFE1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HREFE1 }
     * 
     * 
     */
    public List<HREFE1> getHREFE1() {
        if (hrefe1 == null) {
            hrefe1 = new ArrayList<HREFE1>();
        }
        return this.hrefe1;
    }

    /**
     * Gets the value of the hadre1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hadre1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHADRE1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HADRE1 }
     * 
     * 
     */
    public List<HADRE1> getHADRE1() {
        if (hadre1 == null) {
            hadre1 = new ArrayList<HADRE1>();
        }
        return this.hadre1;
    }

    /**
     * Gets the value of the hcurr1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hcurr1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHCURR1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HCURR1 }
     * 
     * 
     */
    public List<HCURR1> getHCURR1() {
        if (hcurr1 == null) {
            hcurr1 = new ArrayList<HCURR1>();
        }
        return this.hcurr1;
    }

    /**
     * Gets the value of the hpayt1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hpayt1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHPAYT1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HPAYT1 }
     * 
     * 
     */
    public List<HPAYT1> getHPAYT1() {
        if (hpayt1 == null) {
            hpayt1 = new ArrayList<HPAYT1>();
        }
        return this.hpayt1;
    }

    /**
     * Gets the value of the halch1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the halch1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHALCH1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HALCH1 }
     * 
     * 
     */
    public List<HALCH1> getHALCH1() {
        if (halch1 == null) {
            halch1 = new ArrayList<HALCH1>();
        }
        return this.halch1;
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
     * {@link DETAILXrech }
     * 
     * 
     */
    public List<DETAILXrech> getDETAIL() {
        if (detail == null) {
            detail = new ArrayList<DETAILXrech>();
        }
        return this.detail;
    }

}
