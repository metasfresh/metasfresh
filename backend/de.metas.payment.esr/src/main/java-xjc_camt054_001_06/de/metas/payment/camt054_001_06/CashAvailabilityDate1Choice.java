
package de.metas.payment.camt054_001_06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CashAvailabilityDate1Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CashAvailabilityDate1Choice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="NbOfDays" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.06}Max15PlusSignedNumericText"/&gt;
 *         &lt;element name="ActlDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.06}ISODate"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashAvailabilityDate1Choice", propOrder = {
    "nbOfDays",
    "actlDt"
})
public class CashAvailabilityDate1Choice {

    @XmlElement(name = "NbOfDays")
    protected String nbOfDays;
    @XmlElement(name = "ActlDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar actlDt;

    /**
     * Gets the value of the nbOfDays property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNbOfDays() {
        return nbOfDays;
    }

    /**
     * Sets the value of the nbOfDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNbOfDays(String value) {
        this.nbOfDays = value;
    }

    /**
     * Gets the value of the actlDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActlDt() {
        return actlDt;
    }

    /**
     * Sets the value of the actlDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActlDt(XMLGregorianCalendar value) {
        this.actlDt = value;
    }

}
