
package de.metas.postfinance.jaxb;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SearchInvoiceParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchInvoiceParameter"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BillerID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eBillAccountID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmountFrom" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="AmountTo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="State" type="{http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic}State" minOccurs="0"/&gt;
 *         &lt;element name="DeliveryDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="DeliveryDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="PaymentDueDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="PaymentDueDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchInvoiceParameter", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "billerID",
    "transactionID",
    "eBillAccountID",
    "amountFrom",
    "amountTo",
    "state",
    "deliveryDateFrom",
    "deliveryDateTo",
    "paymentDueDateFrom",
    "paymentDueDateTo"
})
public class SearchInvoiceParameter {

    @XmlElement(name = "BillerID", required = true, nillable = true)
    protected String billerID;
    @XmlElementRef(name = "TransactionID", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<String> transactionID;
    @XmlElementRef(name = "eBillAccountID", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<String> eBillAccountID;
    @XmlElement(name = "AmountFrom")
    protected BigDecimal amountFrom;
    @XmlElement(name = "AmountTo")
    protected BigDecimal amountTo;
    @XmlElementRef(name = "State", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<State> state;
    @XmlElement(name = "DeliveryDateFrom")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deliveryDateFrom;
    @XmlElement(name = "DeliveryDateTo")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deliveryDateTo;
    @XmlElement(name = "PaymentDueDateFrom")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar paymentDueDateFrom;
    @XmlElement(name = "PaymentDueDateTo")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar paymentDueDateTo;

    /**
     * Gets the value of the billerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillerID() {
        return billerID;
    }

    /**
     * Sets the value of the billerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillerID(String value) {
        this.billerID = value;
    }

    /**
     * Gets the value of the transactionID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTransactionID(JAXBElement<String> value) {
        this.transactionID = value;
    }

    /**
     * Gets the value of the eBillAccountID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEBillAccountID() {
        return eBillAccountID;
    }

    /**
     * Sets the value of the eBillAccountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEBillAccountID(JAXBElement<String> value) {
        this.eBillAccountID = value;
    }

    /**
     * Gets the value of the amountFrom property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    /**
     * Sets the value of the amountFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountFrom(BigDecimal value) {
        this.amountFrom = value;
    }

    /**
     * Gets the value of the amountTo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountTo() {
        return amountTo;
    }

    /**
     * Sets the value of the amountTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountTo(BigDecimal value) {
        this.amountTo = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link State }{@code >}
     *     
     */
    public JAXBElement<State> getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link State }{@code >}
     *     
     */
    public void setState(JAXBElement<State> value) {
        this.state = value;
    }

    /**
     * Gets the value of the deliveryDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeliveryDateFrom() {
        return deliveryDateFrom;
    }

    /**
     * Sets the value of the deliveryDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeliveryDateFrom(XMLGregorianCalendar value) {
        this.deliveryDateFrom = value;
    }

    /**
     * Gets the value of the deliveryDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeliveryDateTo() {
        return deliveryDateTo;
    }

    /**
     * Sets the value of the deliveryDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeliveryDateTo(XMLGregorianCalendar value) {
        this.deliveryDateTo = value;
    }

    /**
     * Gets the value of the paymentDueDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPaymentDueDateFrom() {
        return paymentDueDateFrom;
    }

    /**
     * Sets the value of the paymentDueDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPaymentDueDateFrom(XMLGregorianCalendar value) {
        this.paymentDueDateFrom = value;
    }

    /**
     * Gets the value of the paymentDueDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPaymentDueDateTo() {
        return paymentDueDateTo;
    }

    /**
     * Sets the value of the paymentDueDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPaymentDueDateTo(XMLGregorianCalendar value) {
        this.paymentDueDateTo = value;
    }

}
