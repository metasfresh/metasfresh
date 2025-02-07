
package at.erpel.schemas._1p0.documents.extensions.edifact;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CONTRLExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CONTRLExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MessageAcknowledgementCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FunctionalGroupAcknowledgementCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONTRLExtensionType", propOrder = {
    "messageAcknowledgementCode",
    "functionalGroupAcknowledgementCode"
})
public class CONTRLExtensionType {

    @XmlElement(name = "MessageAcknowledgementCode")
    protected String messageAcknowledgementCode;
    @XmlElement(name = "FunctionalGroupAcknowledgementCode")
    protected String functionalGroupAcknowledgementCode;

    /**
     * Gets the value of the messageAcknowledgementCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageAcknowledgementCode() {
        return messageAcknowledgementCode;
    }

    /**
     * Sets the value of the messageAcknowledgementCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageAcknowledgementCode(String value) {
        this.messageAcknowledgementCode = value;
    }

    /**
     * Gets the value of the functionalGroupAcknowledgementCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionalGroupAcknowledgementCode() {
        return functionalGroupAcknowledgementCode;
    }

    /**
     * Sets the value of the functionalGroupAcknowledgementCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionalGroupAcknowledgementCode(String value) {
        this.functionalGroupAcknowledgementCode = value;
    }

}
