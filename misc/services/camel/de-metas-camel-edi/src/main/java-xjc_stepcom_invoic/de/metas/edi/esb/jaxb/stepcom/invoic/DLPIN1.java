
package de.metas.edi.esb.jaxb.stepcom.invoic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DLPIN1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DLPIN1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOCUMENTID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LINENUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DELIVERYINSTRUCTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FREETEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FREETEXTLANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DPLQU1" type="{}DPLQU1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DLPIN1", propOrder = {
    "documentid",
    "linenumber",
    "deliveryinstruction",
    "freetext",
    "freetextlanguage",
    "dplqu1"
})
public class DLPIN1 {

    @XmlElement(name = "DOCUMENTID", required = true)
    protected String documentid;
    @XmlElement(name = "LINENUMBER", required = true)
    protected String linenumber;
    @XmlElement(name = "DELIVERYINSTRUCTION")
    protected String deliveryinstruction;
    @XmlElement(name = "FREETEXT")
    protected String freetext;
    @XmlElement(name = "FREETEXTLANGUAGE")
    protected String freetextlanguage;
    @XmlElement(name = "DPLQU1")
    protected DPLQU1 dplqu1;

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
     * Gets the value of the deliveryinstruction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDELIVERYINSTRUCTION() {
        return deliveryinstruction;
    }

    /**
     * Sets the value of the deliveryinstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDELIVERYINSTRUCTION(String value) {
        this.deliveryinstruction = value;
    }

    /**
     * Gets the value of the freetext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFREETEXT() {
        return freetext;
    }

    /**
     * Sets the value of the freetext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFREETEXT(String value) {
        this.freetext = value;
    }

    /**
     * Gets the value of the freetextlanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFREETEXTLANGUAGE() {
        return freetextlanguage;
    }

    /**
     * Sets the value of the freetextlanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFREETEXTLANGUAGE(String value) {
        this.freetextlanguage = value;
    }

    /**
     * Gets the value of the dplqu1 property.
     * 
     * @return
     *     possible object is
     *     {@link DPLQU1 }
     *     
     */
    public DPLQU1 getDPLQU1() {
        return dplqu1;
    }

    /**
     * Sets the value of the dplqu1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DPLQU1 }
     *     
     */
    public void setDPLQU1(DPLQU1 value) {
        this.dplqu1 = value;
    }

}
