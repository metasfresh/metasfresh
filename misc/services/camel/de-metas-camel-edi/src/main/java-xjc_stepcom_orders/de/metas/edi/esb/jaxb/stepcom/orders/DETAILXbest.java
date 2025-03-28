
package de.metas.edi.esb.jaxb.stepcom.orders;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DETAIL_Xbest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DETAIL_Xbest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOCUMENTID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LINENUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SUBLINENUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ACTIONREQUEST" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DPRIN1" type="{}DPRIN1" maxOccurs="5"/&gt;
 *         &lt;element name="DPRDE1" type="{}DPRDE1" maxOccurs="3" minOccurs="0"/&gt;
 *         &lt;element name="DMESU1" type="{}DMESU1" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="DQUAN1" type="{}DQUAN1" maxOccurs="3"/&gt;
 *         &lt;element name="DADDI1" type="{}DADDI1" minOccurs="0"/&gt;
 *         &lt;element name="DDATE1" type="{}DDATE1" minOccurs="0"/&gt;
 *         &lt;element name="DAMOU1" type="{}DAMOU1" minOccurs="0"/&gt;
 *         &lt;element name="DTEXT1" type="{}DTEXT1" maxOccurs="2" minOccurs="0"/&gt;
 *         &lt;element name="DPRIC1" type="{}DPRIC1" maxOccurs="4" minOccurs="0"/&gt;
 *         &lt;element name="DREFE1" type="{}DREFE1" maxOccurs="2" minOccurs="0"/&gt;
 *         &lt;element name="DPLAC1" type="{}DPLAC1" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="DTAXI1" type="{}DTAXI1" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="DADRE1" type="{}DADRE1" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="DALCH1" type="{}DALCH1" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="DTRSD1" type="{}DTRSD1" maxOccurs="0" minOccurs="0"/&gt;
 *         &lt;element name="DLPIN1" type="{}DLPIN1" maxOccurs="0" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DETAIL_Xbest", propOrder = {
    "documentid",
    "linenumber",
    "sublinenumber",
    "actionrequest",
    "dprin1",
    "dprde1",
    "dquan1",
    "daddi1",
    "ddate1",
    "damou1",
    "dtext1",
    "dpric1",
    "drefe1"
})
public class DETAILXbest {

    @XmlElement(name = "DOCUMENTID", required = true)
    protected String documentid;
    @XmlElement(name = "LINENUMBER", required = true)
    protected String linenumber;
    @XmlElement(name = "SUBLINENUMBER")
    protected String sublinenumber;
    @XmlElement(name = "ACTIONREQUEST")
    protected String actionrequest;
    @XmlElement(name = "DPRIN1", required = true)
    protected List<DPRIN1> dprin1;
    @XmlElement(name = "DPRDE1")
    protected List<DPRDE1> dprde1;
    @XmlElement(name = "DQUAN1", required = true)
    protected List<DQUAN1> dquan1;
    @XmlElement(name = "DADDI1")
    protected DADDI1 daddi1;
    @XmlElement(name = "DDATE1")
    protected DDATE1 ddate1;
    @XmlElement(name = "DAMOU1")
    protected DAMOU1 damou1;
    @XmlElement(name = "DTEXT1")
    protected List<DTEXT1> dtext1;
    @XmlElement(name = "DPRIC1")
    protected List<DPRIC1> dpric1;
    @XmlElement(name = "DREFE1")
    protected List<DREFE1> drefe1;

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
     * Gets the value of the linenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLINENUMBER() {
        return linenumber;
    }

    /**
     * Sets the value of the linenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLINENUMBER(String value) {
        this.linenumber = value;
    }

    /**
     * Gets the value of the sublinenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUBLINENUMBER() {
        return sublinenumber;
    }

    /**
     * Sets the value of the sublinenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUBLINENUMBER(String value) {
        this.sublinenumber = value;
    }

    /**
     * Gets the value of the actionrequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACTIONREQUEST() {
        return actionrequest;
    }

    /**
     * Sets the value of the actionrequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACTIONREQUEST(String value) {
        this.actionrequest = value;
    }

    /**
     * Gets the value of the dprin1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dprin1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDPRIN1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DPRIN1 }
     * 
     * 
     */
    public List<DPRIN1> getDPRIN1() {
        if (dprin1 == null) {
            dprin1 = new ArrayList<DPRIN1>();
        }
        return this.dprin1;
    }

    /**
     * Gets the value of the dprde1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dprde1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDPRDE1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DPRDE1 }
     * 
     * 
     */
    public List<DPRDE1> getDPRDE1() {
        if (dprde1 == null) {
            dprde1 = new ArrayList<DPRDE1>();
        }
        return this.dprde1;
    }

    /**
     * Gets the value of the dquan1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dquan1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDQUAN1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DQUAN1 }
     * 
     * 
     */
    public List<DQUAN1> getDQUAN1() {
        if (dquan1 == null) {
            dquan1 = new ArrayList<DQUAN1>();
        }
        return this.dquan1;
    }

    /**
     * Gets the value of the daddi1 property.
     * 
     * @return
     *     possible object is
     *     {@link DADDI1 }
     *     
     */
    public DADDI1 getDADDI1() {
        return daddi1;
    }

    /**
     * Sets the value of the daddi1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DADDI1 }
     *     
     */
    public void setDADDI1(DADDI1 value) {
        this.daddi1 = value;
    }

    /**
     * Gets the value of the ddate1 property.
     * 
     * @return
     *     possible object is
     *     {@link DDATE1 }
     *     
     */
    public DDATE1 getDDATE1() {
        return ddate1;
    }

    /**
     * Sets the value of the ddate1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DDATE1 }
     *     
     */
    public void setDDATE1(DDATE1 value) {
        this.ddate1 = value;
    }

    /**
     * Gets the value of the damou1 property.
     * 
     * @return
     *     possible object is
     *     {@link DAMOU1 }
     *     
     */
    public DAMOU1 getDAMOU1() {
        return damou1;
    }

    /**
     * Sets the value of the damou1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DAMOU1 }
     *     
     */
    public void setDAMOU1(DAMOU1 value) {
        this.damou1 = value;
    }

    /**
     * Gets the value of the dtext1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dtext1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDTEXT1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTEXT1 }
     * 
     * 
     */
    public List<DTEXT1> getDTEXT1() {
        if (dtext1 == null) {
            dtext1 = new ArrayList<DTEXT1>();
        }
        return this.dtext1;
    }

    /**
     * Gets the value of the dpric1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dpric1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDPRIC1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DPRIC1 }
     * 
     * 
     */
    public List<DPRIC1> getDPRIC1() {
        if (dpric1 == null) {
            dpric1 = new ArrayList<DPRIC1>();
        }
        return this.dpric1;
    }

    /**
     * Gets the value of the drefe1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drefe1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDREFE1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DREFE1 }
     * 
     * 
     */
    public List<DREFE1> getDREFE1() {
        if (drefe1 == null) {
            drefe1 = new ArrayList<DREFE1>();
        }
        return this.drefe1;
    }

}
