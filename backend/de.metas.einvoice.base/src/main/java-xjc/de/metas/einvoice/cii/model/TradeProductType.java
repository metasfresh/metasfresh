
package de.metas.einvoice.cii.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TradeProductType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeProductType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GlobalID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}IDType" minOccurs="0"/&gt;
 *         &lt;element name="SellerAssignedID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}IDType" minOccurs="0"/&gt;
 *         &lt;element name="BuyerAssignedID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}IDType" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}TextType"/&gt;
 *         &lt;element name="Description" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100}TextType" minOccurs="0"/&gt;
 *         &lt;element name="ApplicableProductCharacteristic" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ProductCharacteristicType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="DesignatedProductClassification" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}ProductClassificationType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="OriginTradeCountry" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100}TradeCountryType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeProductType", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", propOrder = {
    "globalID",
    "sellerAssignedID",
    "buyerAssignedID",
    "name",
    "description",
    "applicableProductCharacteristic",
    "designatedProductClassification",
    "originTradeCountry"
})
public class TradeProductType {

    @XmlElement(name = "GlobalID", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected IDType globalID;
    @XmlElement(name = "SellerAssignedID", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected IDType sellerAssignedID;
    @XmlElement(name = "BuyerAssignedID", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected IDType buyerAssignedID;
    @XmlElement(name = "Name", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100", required = true)
    protected TextType name;
    @XmlElement(name = "Description", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TextType description;
    @XmlElement(name = "ApplicableProductCharacteristic", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected List<ProductCharacteristicType> applicableProductCharacteristic;
    @XmlElement(name = "DesignatedProductClassification", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected List<ProductClassificationType> designatedProductClassification;
    @XmlElement(name = "OriginTradeCountry", namespace = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100")
    protected TradeCountryType originTradeCountry;

    /**
     * Gets the value of the globalID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getGlobalID() {
        return globalID;
    }

    /**
     * Sets the value of the globalID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setGlobalID(IDType value) {
        this.globalID = value;
    }

    /**
     * Gets the value of the sellerAssignedID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getSellerAssignedID() {
        return sellerAssignedID;
    }

    /**
     * Sets the value of the sellerAssignedID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setSellerAssignedID(IDType value) {
        this.sellerAssignedID = value;
    }

    /**
     * Gets the value of the buyerAssignedID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getBuyerAssignedID() {
        return buyerAssignedID;
    }

    /**
     * Sets the value of the buyerAssignedID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setBuyerAssignedID(IDType value) {
        this.buyerAssignedID = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setName(TextType value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDescription(TextType value) {
        this.description = value;
    }

    /**
     * Gets the value of the applicableProductCharacteristic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicableProductCharacteristic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicableProductCharacteristic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductCharacteristicType }
     * 
     * 
     */
    public List<ProductCharacteristicType> getApplicableProductCharacteristic() {
        if (applicableProductCharacteristic == null) {
            applicableProductCharacteristic = new ArrayList<ProductCharacteristicType>();
        }
        return this.applicableProductCharacteristic;
    }

    /**
     * Gets the value of the designatedProductClassification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the designatedProductClassification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDesignatedProductClassification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductClassificationType }
     * 
     * 
     */
    public List<ProductClassificationType> getDesignatedProductClassification() {
        if (designatedProductClassification == null) {
            designatedProductClassification = new ArrayList<ProductClassificationType>();
        }
        return this.designatedProductClassification;
    }

    /**
     * Gets the value of the originTradeCountry property.
     * 
     * @return
     *     possible object is
     *     {@link TradeCountryType }
     *     
     */
    public TradeCountryType getOriginTradeCountry() {
        return originTradeCountry;
    }

    /**
     * Sets the value of the originTradeCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeCountryType }
     *     
     */
    public void setOriginTradeCountry(TradeCountryType value) {
        this.originTradeCountry = value;
    }

}
