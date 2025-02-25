
package at.erpel.schemas._1p0.documents.extensions.edifact;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RECADVExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RECADVExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OrderReference" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="DesadvReference" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="RecadvReference" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="OrdrspReference" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AgreementNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TimeForPayment" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ConsignmentReference" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RECADVExtensionType", propOrder = {
    "orderReference",
    "desadvReference",
    "recadvReference",
    "ordrspReference",
    "contractNumber",
    "agreementNumber",
    "timeForPayment",
    "consignmentReference"
})
public class RECADVExtensionType {

    @XmlElement(name = "OrderReference")
    protected ReferenceType orderReference;
    @XmlElement(name = "DesadvReference")
    protected ReferenceType desadvReference;
    @XmlElement(name = "RecadvReference")
    protected ReferenceType recadvReference;
    @XmlElement(name = "OrdrspReference")
    protected ReferenceType ordrspReference;
    @XmlElement(name = "ContractNumber")
    protected String contractNumber;
    @XmlElement(name = "AgreementNumber")
    protected String agreementNumber;
    @XmlElement(name = "TimeForPayment")
    protected BigInteger timeForPayment;
    @XmlElement(name = "ConsignmentReference")
    protected String consignmentReference;

    /**
     * Gets the value of the orderReference property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getOrderReference() {
        return orderReference;
    }

    /**
     * Sets the value of the orderReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setOrderReference(ReferenceType value) {
        this.orderReference = value;
    }

    /**
     * Gets the value of the desadvReference property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getDesadvReference() {
        return desadvReference;
    }

    /**
     * Sets the value of the desadvReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setDesadvReference(ReferenceType value) {
        this.desadvReference = value;
    }

    /**
     * Gets the value of the recadvReference property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getRecadvReference() {
        return recadvReference;
    }

    /**
     * Sets the value of the recadvReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setRecadvReference(ReferenceType value) {
        this.recadvReference = value;
    }

    /**
     * Gets the value of the ordrspReference property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getOrdrspReference() {
        return ordrspReference;
    }

    /**
     * Sets the value of the ordrspReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setOrdrspReference(ReferenceType value) {
        this.ordrspReference = value;
    }

    /**
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

    /**
     * Gets the value of the agreementNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementNumber() {
        return agreementNumber;
    }

    /**
     * Sets the value of the agreementNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementNumber(String value) {
        this.agreementNumber = value;
    }

    /**
     * Gets the value of the timeForPayment property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTimeForPayment() {
        return timeForPayment;
    }

    /**
     * Sets the value of the timeForPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTimeForPayment(BigInteger value) {
        this.timeForPayment = value;
    }

    /**
     * Consignment reference.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsignmentReference() {
        return consignmentReference;
    }

    /**
     * Sets the value of the consignmentReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsignmentReference(String value) {
        this.consignmentReference = value;
    }

}
