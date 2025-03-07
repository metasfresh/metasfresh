
package de.metas.edi.esb.jaxb.stepcom.desadv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PHAND1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PHAND1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOCUMENTID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="HANDLINGINSTRCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HANDLINGINSTRDESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HAZARDOUSMATCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HAZARDOUSMATNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PHAND1", propOrder = {
    "documentid",
    "handlinginstrcode",
    "handlinginstrdesc",
    "hazardousmatcode",
    "hazardousmatname"
})
public class PHAND1 {

    @XmlElement(name = "DOCUMENTID", required = true)
    protected String documentid;
    @XmlElement(name = "HANDLINGINSTRCODE")
    protected String handlinginstrcode;
    @XmlElement(name = "HANDLINGINSTRDESC")
    protected String handlinginstrdesc;
    @XmlElement(name = "HAZARDOUSMATCODE")
    protected String hazardousmatcode;
    @XmlElement(name = "HAZARDOUSMATNAME")
    protected String hazardousmatname;

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
     * Gets the value of the handlinginstrcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHANDLINGINSTRCODE() {
        return handlinginstrcode;
    }

    /**
     * Sets the value of the handlinginstrcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHANDLINGINSTRCODE(String value) {
        this.handlinginstrcode = value;
    }

    /**
     * Gets the value of the handlinginstrdesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHANDLINGINSTRDESC() {
        return handlinginstrdesc;
    }

    /**
     * Sets the value of the handlinginstrdesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHANDLINGINSTRDESC(String value) {
        this.handlinginstrdesc = value;
    }

    /**
     * Gets the value of the hazardousmatcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHAZARDOUSMATCODE() {
        return hazardousmatcode;
    }

    /**
     * Sets the value of the hazardousmatcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHAZARDOUSMATCODE(String value) {
        this.hazardousmatcode = value;
    }

    /**
     * Gets the value of the hazardousmatname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHAZARDOUSMATNAME() {
        return hazardousmatname;
    }

    /**
     * Sets the value of the hazardousmatname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHAZARDOUSMATNAME(String value) {
        this.hazardousmatname = value;
    }

}
