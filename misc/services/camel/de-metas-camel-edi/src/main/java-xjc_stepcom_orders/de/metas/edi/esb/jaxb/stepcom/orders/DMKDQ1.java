
package de.metas.edi.esb.jaxb.stepcom.orders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DMKDQ1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DMKDQ1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOCUMENTID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LINENUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="QUANTITYQUAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IDENTIFICATIONQUAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IDENTIFICATIONCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IDENTIFICATIONDATE1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IDENTIFICATIONDATE2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IDENTIFICATIONDATE3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IDENTIFICATIONDATE4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="QUANTITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="QUANTITYMEASUREUNIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DMKDQ1", propOrder = {
    "documentid",
    "linenumber",
    "quantityqual",
    "identificationqual",
    "identificationcode",
    "identificationdate1",
    "identificationdate2",
    "identificationdate3",
    "identificationdate4",
    "quantity",
    "quantitymeasureunit"
})
public class DMKDQ1 {

    @XmlElement(name = "DOCUMENTID", required = true)
    protected String documentid;
    @XmlElement(name = "LINENUMBER", required = true)
    protected String linenumber;
    @XmlElement(name = "QUANTITYQUAL", required = true)
    protected String quantityqual;
    @XmlElement(name = "IDENTIFICATIONQUAL", required = true)
    protected String identificationqual;
    @XmlElement(name = "IDENTIFICATIONCODE")
    protected String identificationcode;
    @XmlElement(name = "IDENTIFICATIONDATE1")
    protected String identificationdate1;
    @XmlElement(name = "IDENTIFICATIONDATE2")
    protected String identificationdate2;
    @XmlElement(name = "IDENTIFICATIONDATE3")
    protected String identificationdate3;
    @XmlElement(name = "IDENTIFICATIONDATE4")
    protected String identificationdate4;
    @XmlElement(name = "QUANTITY")
    protected String quantity;
    @XmlElement(name = "QUANTITYMEASUREUNIT")
    protected String quantitymeasureunit;

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
     * Gets the value of the quantityqual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUANTITYQUAL() {
        return quantityqual;
    }

    /**
     * Sets the value of the quantityqual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUANTITYQUAL(String value) {
        this.quantityqual = value;
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
     * Gets the value of the identificationdate1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDENTIFICATIONDATE1() {
        return identificationdate1;
    }

    /**
     * Sets the value of the identificationdate1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDENTIFICATIONDATE1(String value) {
        this.identificationdate1 = value;
    }

    /**
     * Gets the value of the identificationdate2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDENTIFICATIONDATE2() {
        return identificationdate2;
    }

    /**
     * Sets the value of the identificationdate2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDENTIFICATIONDATE2(String value) {
        this.identificationdate2 = value;
    }

    /**
     * Gets the value of the identificationdate3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDENTIFICATIONDATE3() {
        return identificationdate3;
    }

    /**
     * Sets the value of the identificationdate3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDENTIFICATIONDATE3(String value) {
        this.identificationdate3 = value;
    }

    /**
     * Gets the value of the identificationdate4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDENTIFICATIONDATE4() {
        return identificationdate4;
    }

    /**
     * Sets the value of the identificationdate4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDENTIFICATIONDATE4(String value) {
        this.identificationdate4 = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUANTITY() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUANTITY(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the quantitymeasureunit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUANTITYMEASUREUNIT() {
        return quantitymeasureunit;
    }

    /**
     * Sets the value of the quantitymeasureunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUANTITYMEASUREUNIT(String value) {
        this.quantitymeasureunit = value;
    }

}
