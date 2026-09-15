
package de.metas.einvoice.cii.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExchangedDocumentContextType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExchangedDocumentContextType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BusinessProcessSpecifiedDocumentContextParameter" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}DocumentContextParameterType" minOccurs="0"/&gt;
 *         &lt;element name="GuidelineSpecifiedDocumentContextParameter" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}DocumentContextParameterType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExchangedDocumentContextType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "businessProcessSpecifiedDocumentContextParameter",
    "guidelineSpecifiedDocumentContextParameter"
})
public class ExchangedDocumentContextType {

    @XmlElement(name = "BusinessProcessSpecifiedDocumentContextParameter", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected DocumentContextParameterType businessProcessSpecifiedDocumentContextParameter;
    @XmlElement(name = "GuidelineSpecifiedDocumentContextParameter", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected DocumentContextParameterType guidelineSpecifiedDocumentContextParameter;

    /**
     * Gets the value of the businessProcessSpecifiedDocumentContextParameter property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentContextParameterType }
     *     
     */
    public DocumentContextParameterType getBusinessProcessSpecifiedDocumentContextParameter() {
        return businessProcessSpecifiedDocumentContextParameter;
    }

    /**
     * Sets the value of the businessProcessSpecifiedDocumentContextParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentContextParameterType }
     *     
     */
    public void setBusinessProcessSpecifiedDocumentContextParameter(DocumentContextParameterType value) {
        this.businessProcessSpecifiedDocumentContextParameter = value;
    }

    /**
     * Gets the value of the guidelineSpecifiedDocumentContextParameter property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentContextParameterType }
     *     
     */
    public DocumentContextParameterType getGuidelineSpecifiedDocumentContextParameter() {
        return guidelineSpecifiedDocumentContextParameter;
    }

    /**
     * Sets the value of the guidelineSpecifiedDocumentContextParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentContextParameterType }
     *     
     */
    public void setGuidelineSpecifiedDocumentContextParameter(DocumentContextParameterType value) {
        this.guidelineSpecifiedDocumentContextParameter = value;
    }

}
