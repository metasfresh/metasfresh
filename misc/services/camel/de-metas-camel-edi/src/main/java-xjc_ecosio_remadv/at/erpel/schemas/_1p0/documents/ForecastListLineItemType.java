
package at.erpel.schemas._1p0.documents;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.erpel.schemas._1p0.documents.ext.ForecastListLineItemExtensionType;


/**
 * <p>Java class for ForecastListLineItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ForecastListLineItemType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Indicators" minOccurs="0"/&gt;
 *         &lt;element name="LineItemActionRequest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}PositionNumber" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Description" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ArticleNumber" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Delivery" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}AdditionalForecastInformation" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}AdditionalLineItemInformation" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}PackagingDetails" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}PackagingIdentification" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}PlanningQuantity" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}ForecastListLineItemExtension" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForecastListLineItemType", propOrder = {
    "indicators",
    "lineItemActionRequest",
    "positionNumber",
    "description",
    "articleNumber",
    "delivery",
    "additionalForecastInformation",
    "additionalLineItemInformation",
    "packagingDetails",
    "packagingIdentification",
    "planningQuantity",
    "forecastListLineItemExtension"
})
public class ForecastListLineItemType {

    @XmlElement(name = "Indicators")
    protected IndicatorsType indicators;
    @XmlElement(name = "LineItemActionRequest")
    protected String lineItemActionRequest;
    @XmlElement(name = "PositionNumber")
    protected String positionNumber;
    @XmlElement(name = "Description")
    protected List<DescriptionType> description;
    @XmlElement(name = "ArticleNumber", required = true)
    protected List<ArticleNumberType> articleNumber;
    @XmlElement(name = "Delivery")
    protected DeliveryType delivery;
    @XmlElement(name = "AdditionalForecastInformation")
    protected AdditionalForecastInformationType additionalForecastInformation;
    @XmlElement(name = "AdditionalLineItemInformation")
    protected AdditionalLineItemInformationType additionalLineItemInformation;
    @XmlElement(name = "PackagingDetails")
    protected PackagingDetailsType packagingDetails;
    @XmlElement(name = "PackagingIdentification")
    protected PackagingIdentificationType packagingIdentification;
    @XmlElement(name = "PlanningQuantity")
    protected List<PlanningQuantityType> planningQuantity;
    @XmlElement(name = "ForecastListLineItemExtension", namespace = "http://erpel.at/schemas/1p0/documents/ext")
    protected ForecastListLineItemExtensionType forecastListLineItemExtension;

    /**
     * Indicator about the purpose of the forecast message - taken from the GIS segment
     * 
     * @return
     *     possible object is
     *     {@link IndicatorsType }
     *     
     */
    public IndicatorsType getIndicators() {
        return indicators;
    }

    /**
     * Sets the value of the indicators property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndicatorsType }
     *     
     */
    public void setIndicators(IndicatorsType value) {
        this.indicators = value;
    }

    /**
     * Gets the value of the lineItemActionRequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineItemActionRequest() {
        return lineItemActionRequest;
    }

    /**
     * Sets the value of the lineItemActionRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineItemActionRequest(String value) {
        this.lineItemActionRequest = value;
    }

    /**
     * Position number of the entry
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositionNumber() {
        return positionNumber;
    }

    /**
     * Sets the value of the positionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositionNumber(String value) {
        this.positionNumber = value;
    }

    /**
     * A detailled description of a certain entry in free-text form.Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * A number uniquely identifying a certain article in the context of the document. The type of the article number is determined by the accompanying attribute.Gets the value of the articleNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the articleNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArticleNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArticleNumberType }
     * 
     * 
     */
    public List<ArticleNumberType> getArticleNumber() {
        if (articleNumber == null) {
            articleNumber = new ArrayList<ArticleNumberType>();
        }
        return this.articleNumber;
    }

    /**
     * Information about the  delivery location
     * 
     * @return
     *     possible object is
     *     {@link DeliveryType }
     *     
     */
    public DeliveryType getDelivery() {
        return delivery;
    }

    /**
     * Sets the value of the delivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryType }
     *     
     */
    public void setDelivery(DeliveryType value) {
        this.delivery = value;
    }

    /**
     * Referenced documents and date
     * 
     * @return
     *     possible object is
     *     {@link AdditionalForecastInformationType }
     *     
     */
    public AdditionalForecastInformationType getAdditionalForecastInformation() {
        return additionalForecastInformation;
    }

    /**
     * Sets the value of the additionalForecastInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdditionalForecastInformationType }
     *     
     */
    public void setAdditionalForecastInformation(AdditionalForecastInformationType value) {
        this.additionalForecastInformation = value;
    }

    /**
     * Additional information about the forecasted item
     * 
     * @return
     *     possible object is
     *     {@link AdditionalLineItemInformationType }
     *     
     */
    public AdditionalLineItemInformationType getAdditionalLineItemInformation() {
        return additionalLineItemInformation;
    }

    /**
     * Sets the value of the additionalLineItemInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdditionalLineItemInformationType }
     *     
     */
    public void setAdditionalLineItemInformation(AdditionalLineItemInformationType value) {
        this.additionalLineItemInformation = value;
    }

    /**
     * Packaging details, related to the forecasted item
     * 
     * @return
     *     possible object is
     *     {@link PackagingDetailsType }
     *     
     */
    public PackagingDetailsType getPackagingDetails() {
        return packagingDetails;
    }

    /**
     * Sets the value of the packagingDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackagingDetailsType }
     *     
     */
    public void setPackagingDetails(PackagingDetailsType value) {
        this.packagingDetails = value;
    }

    /**
     * Packaging identification information requested by the customer - details about the packaging items
     * 
     * @return
     *     possible object is
     *     {@link PackagingIdentificationType }
     *     
     */
    public PackagingIdentificationType getPackagingIdentification() {
        return packagingIdentification;
    }

    /**
     * Sets the value of the packagingIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackagingIdentificationType }
     *     
     */
    public void setPackagingIdentification(PackagingIdentificationType value) {
        this.packagingIdentification = value;
    }

    /**
     * Planning Quantity Gets the value of the planningQuantity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the planningQuantity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlanningQuantity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlanningQuantityType }
     * 
     * 
     */
    public List<PlanningQuantityType> getPlanningQuantity() {
        if (planningQuantity == null) {
            planningQuantity = new ArrayList<PlanningQuantityType>();
        }
        return this.planningQuantity;
    }

    /**
     * Gets the value of the forecastListLineItemExtension property.
     * 
     * @return
     *     possible object is
     *     {@link ForecastListLineItemExtensionType }
     *     
     */
    public ForecastListLineItemExtensionType getForecastListLineItemExtension() {
        return forecastListLineItemExtension;
    }

    /**
     * Sets the value of the forecastListLineItemExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ForecastListLineItemExtensionType }
     *     
     */
    public void setForecastListLineItemExtension(ForecastListLineItemExtensionType value) {
        this.forecastListLineItemExtension = value;
    }

}
