
package at.erpel.schemas._1p0.documents.extensions.edifact;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PRICATListLineItemExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PRICATListLineItemExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProductGroup" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ProductGroupType" minOccurs="0"/&gt;
 *         &lt;element name="ManufacturerSpecification" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ArticleNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ArticleNumberExtType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="Description" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DescriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="ShortDescription" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DescriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="ProductLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ManufacturersName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="CountryOfProduction" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}CountryCodeType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ProductSpecification" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ModelNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Edition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ReleaseDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                   &lt;element name="Measurements" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Weight" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="NetWeight" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
 *                                       &lt;element name="GrossWeight" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Size" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}SizeType" minOccurs="0"/&gt;
 *                             &lt;element name="FillingQuantity" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
 *                             &lt;element name="AlcoholVolumePercentage" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PercentageType" minOccurs="0"/&gt;
 *                             &lt;element name="PlatoVolumePercentage" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PercentageType" minOccurs="0"/&gt;
 *                             &lt;element name="GenderSpecific" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                   &lt;enumeration value="w"/&gt;
 *                                   &lt;enumeration value="m"/&gt;
 *                                 &lt;/restriction&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Appearance" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ColorInformation" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="ColorCode" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumIDType" minOccurs="0"/&gt;
 *                                       &lt;element name="ColorTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Language" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Season" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="SeasonTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="SeasonYear" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                                   &lt;pattern value="[0-9]{2,4}"/&gt;
 *                                 &lt;/restriction&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Indicators" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="LimitedEdition" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                             &lt;element name="TravelExclusive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                             &lt;element name="ContainsPork" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                             &lt;element name="ContainsColorant" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                             &lt;element name="ContainsAlcohol" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                             &lt;element name="ContainsAerosol" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                             &lt;element name="AlcoholTabacoTax" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                             &lt;element name="IsOneShot" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                             &lt;element name="MaintainingColdChain" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="AdditionalArticleNumber" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ArticleNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ArticleNumberExtType"/&gt;
 *                             &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Parties" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}BusinessEntityType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="DeliverySpecification" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="MinimumOrderQuantity" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
 *                   &lt;element name="MinimumOrderValue" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Decimal4Type" minOccurs="0"/&gt;
 *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="DeliveryTimeFromOrder" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/&gt;
 *                   &lt;element name="CustomsTariffNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumIDType" minOccurs="0"/&gt;
 *                   &lt;element name="IntrastratNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumIDType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PriceSpecification" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Purchase" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="PurchasePrice" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PriceSpecificationType" maxOccurs="unbounded"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Sales" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="SalesPrice" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PriceSpecificationType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                             &lt;element name="FirstSalesDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="OtherUnitPrice" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitPriceExtType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="UnitSpecification" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="UnitConversion" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitConversionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="BaseUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="PurchaseUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="SalesUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PRICATListLineItemExtensionType", propOrder = {
    "productGroup",
    "manufacturerSpecification",
    "productSpecification",
    "deliverySpecification",
    "priceSpecification",
    "unitSpecification"
})
public class PRICATListLineItemExtensionType {

    @XmlElement(name = "ProductGroup")
    protected ProductGroupType productGroup;
    @XmlElement(name = "ManufacturerSpecification")
    protected PRICATListLineItemExtensionType.ManufacturerSpecification manufacturerSpecification;
    @XmlElement(name = "ProductSpecification")
    protected PRICATListLineItemExtensionType.ProductSpecification productSpecification;
    @XmlElement(name = "DeliverySpecification")
    protected PRICATListLineItemExtensionType.DeliverySpecification deliverySpecification;
    @XmlElement(name = "PriceSpecification")
    protected PRICATListLineItemExtensionType.PriceSpecification priceSpecification;
    @XmlElement(name = "UnitSpecification")
    protected PRICATListLineItemExtensionType.UnitSpecification unitSpecification;

    /**
     * Gets the value of the productGroup property.
     * 
     * @return
     *     possible object is
     *     {@link ProductGroupType }
     *     
     */
    public ProductGroupType getProductGroup() {
        return productGroup;
    }

    /**
     * Sets the value of the productGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductGroupType }
     *     
     */
    public void setProductGroup(ProductGroupType value) {
        this.productGroup = value;
    }

    /**
     * Gets the value of the manufacturerSpecification property.
     * 
     * @return
     *     possible object is
     *     {@link PRICATListLineItemExtensionType.ManufacturerSpecification }
     *     
     */
    public PRICATListLineItemExtensionType.ManufacturerSpecification getManufacturerSpecification() {
        return manufacturerSpecification;
    }

    /**
     * Sets the value of the manufacturerSpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRICATListLineItemExtensionType.ManufacturerSpecification }
     *     
     */
    public void setManufacturerSpecification(PRICATListLineItemExtensionType.ManufacturerSpecification value) {
        this.manufacturerSpecification = value;
    }

    /**
     * Gets the value of the productSpecification property.
     * 
     * @return
     *     possible object is
     *     {@link PRICATListLineItemExtensionType.ProductSpecification }
     *     
     */
    public PRICATListLineItemExtensionType.ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    /**
     * Sets the value of the productSpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRICATListLineItemExtensionType.ProductSpecification }
     *     
     */
    public void setProductSpecification(PRICATListLineItemExtensionType.ProductSpecification value) {
        this.productSpecification = value;
    }

    /**
     * Gets the value of the deliverySpecification property.
     * 
     * @return
     *     possible object is
     *     {@link PRICATListLineItemExtensionType.DeliverySpecification }
     *     
     */
    public PRICATListLineItemExtensionType.DeliverySpecification getDeliverySpecification() {
        return deliverySpecification;
    }

    /**
     * Sets the value of the deliverySpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRICATListLineItemExtensionType.DeliverySpecification }
     *     
     */
    public void setDeliverySpecification(PRICATListLineItemExtensionType.DeliverySpecification value) {
        this.deliverySpecification = value;
    }

    /**
     * Gets the value of the priceSpecification property.
     * 
     * @return
     *     possible object is
     *     {@link PRICATListLineItemExtensionType.PriceSpecification }
     *     
     */
    public PRICATListLineItemExtensionType.PriceSpecification getPriceSpecification() {
        return priceSpecification;
    }

    /**
     * Sets the value of the priceSpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRICATListLineItemExtensionType.PriceSpecification }
     *     
     */
    public void setPriceSpecification(PRICATListLineItemExtensionType.PriceSpecification value) {
        this.priceSpecification = value;
    }

    /**
     * Gets the value of the unitSpecification property.
     * 
     * @return
     *     possible object is
     *     {@link PRICATListLineItemExtensionType.UnitSpecification }
     *     
     */
    public PRICATListLineItemExtensionType.UnitSpecification getUnitSpecification() {
        return unitSpecification;
    }

    /**
     * Sets the value of the unitSpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRICATListLineItemExtensionType.UnitSpecification }
     *     
     */
    public void setUnitSpecification(PRICATListLineItemExtensionType.UnitSpecification value) {
        this.unitSpecification = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="MinimumOrderQuantity" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
     *         &lt;element name="MinimumOrderValue" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Decimal4Type" minOccurs="0"/&gt;
     *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="DeliveryTimeFromOrder" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/&gt;
     *         &lt;element name="CustomsTariffNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumIDType" minOccurs="0"/&gt;
     *         &lt;element name="IntrastratNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumIDType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "minimumOrderQuantity",
        "minimumOrderValue",
        "status",
        "deliveryTimeFromOrder",
        "customsTariffNumber",
        "intrastratNumber"
    })
    public static class DeliverySpecification {

        @XmlElement(name = "MinimumOrderQuantity")
        protected UnitType minimumOrderQuantity;
        @XmlElement(name = "MinimumOrderValue")
        protected BigDecimal minimumOrderValue;
        @XmlElement(name = "Status")
        protected String status;
        @XmlElement(name = "DeliveryTimeFromOrder")
        protected Duration deliveryTimeFromOrder;
        @XmlElement(name = "CustomsTariffNumber")
        protected String customsTariffNumber;
        @XmlElement(name = "IntrastratNumber")
        protected String intrastratNumber;

        /**
         * Gets the value of the minimumOrderQuantity property.
         * 
         * @return
         *     possible object is
         *     {@link UnitType }
         *     
         */
        public UnitType getMinimumOrderQuantity() {
            return minimumOrderQuantity;
        }

        /**
         * Sets the value of the minimumOrderQuantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link UnitType }
         *     
         */
        public void setMinimumOrderQuantity(UnitType value) {
            this.minimumOrderQuantity = value;
        }

        /**
         * Gets the value of the minimumOrderValue property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getMinimumOrderValue() {
            return minimumOrderValue;
        }

        /**
         * Sets the value of the minimumOrderValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setMinimumOrderValue(BigDecimal value) {
            this.minimumOrderValue = value;
        }

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the deliveryTimeFromOrder property.
         * 
         * @return
         *     possible object is
         *     {@link Duration }
         *     
         */
        public Duration getDeliveryTimeFromOrder() {
            return deliveryTimeFromOrder;
        }

        /**
         * Sets the value of the deliveryTimeFromOrder property.
         * 
         * @param value
         *     allowed object is
         *     {@link Duration }
         *     
         */
        public void setDeliveryTimeFromOrder(Duration value) {
            this.deliveryTimeFromOrder = value;
        }

        /**
         * Gets the value of the customsTariffNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomsTariffNumber() {
            return customsTariffNumber;
        }

        /**
         * Sets the value of the customsTariffNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomsTariffNumber(String value) {
            this.customsTariffNumber = value;
        }

        /**
         * Gets the value of the intrastratNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIntrastratNumber() {
            return intrastratNumber;
        }

        /**
         * Sets the value of the intrastratNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIntrastratNumber(String value) {
            this.intrastratNumber = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="ArticleNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ArticleNumberExtType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="Description" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DescriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="ShortDescription" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DescriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="ProductLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ManufacturersName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="CountryOfProduction" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}CountryCodeType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "articleNumber",
        "description",
        "shortDescription",
        "productLine",
        "manufacturersName",
        "countryOfProduction"
    })
    public static class ManufacturerSpecification {

        @XmlElement(name = "ArticleNumber")
        protected List<ArticleNumberExtType> articleNumber;
        @XmlElement(name = "Description")
        protected List<DescriptionType> description;
        @XmlElement(name = "ShortDescription")
        protected List<DescriptionType> shortDescription;
        @XmlElement(name = "ProductLine")
        protected String productLine;
        @XmlElement(name = "ManufacturersName")
        protected String manufacturersName;
        @XmlElement(name = "CountryOfProduction")
        @XmlSchemaType(name = "token")
        protected CountryCodeType countryOfProduction;

        /**
         * Gets the value of the articleNumber property.
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
         * {@link ArticleNumberExtType }
         * 
         * 
         */
        public List<ArticleNumberExtType> getArticleNumber() {
            if (articleNumber == null) {
                articleNumber = new ArrayList<ArticleNumberExtType>();
            }
            return this.articleNumber;
        }

        /**
         * Gets the value of the description property.
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
         * Gets the value of the shortDescription property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the shortDescription property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getShortDescription().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DescriptionType }
         * 
         * 
         */
        public List<DescriptionType> getShortDescription() {
            if (shortDescription == null) {
                shortDescription = new ArrayList<DescriptionType>();
            }
            return this.shortDescription;
        }

        /**
         * Gets the value of the productLine property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProductLine() {
            return productLine;
        }

        /**
         * Sets the value of the productLine property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProductLine(String value) {
            this.productLine = value;
        }

        /**
         * Gets the value of the manufacturersName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getManufacturersName() {
            return manufacturersName;
        }

        /**
         * Sets the value of the manufacturersName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setManufacturersName(String value) {
            this.manufacturersName = value;
        }

        /**
         * Gets the value of the countryOfProduction property.
         * 
         * @return
         *     possible object is
         *     {@link CountryCodeType }
         *     
         */
        public CountryCodeType getCountryOfProduction() {
            return countryOfProduction;
        }

        /**
         * Sets the value of the countryOfProduction property.
         * 
         * @param value
         *     allowed object is
         *     {@link CountryCodeType }
         *     
         */
        public void setCountryOfProduction(CountryCodeType value) {
            this.countryOfProduction = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Purchase" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="PurchasePrice" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PriceSpecificationType" maxOccurs="unbounded"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Sales" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="SalesPrice" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PriceSpecificationType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                   &lt;element name="FirstSalesDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="OtherUnitPrice" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitPriceExtType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "purchase",
        "sales",
        "otherUnitPrice"
    })
    public static class PriceSpecification {

        @XmlElement(name = "Purchase")
        protected PRICATListLineItemExtensionType.PriceSpecification.Purchase purchase;
        @XmlElement(name = "Sales")
        protected PRICATListLineItemExtensionType.PriceSpecification.Sales sales;
        @XmlElement(name = "OtherUnitPrice")
        protected List<UnitPriceExtType> otherUnitPrice;

        /**
         * Gets the value of the purchase property.
         * 
         * @return
         *     possible object is
         *     {@link PRICATListLineItemExtensionType.PriceSpecification.Purchase }
         *     
         */
        public PRICATListLineItemExtensionType.PriceSpecification.Purchase getPurchase() {
            return purchase;
        }

        /**
         * Sets the value of the purchase property.
         * 
         * @param value
         *     allowed object is
         *     {@link PRICATListLineItemExtensionType.PriceSpecification.Purchase }
         *     
         */
        public void setPurchase(PRICATListLineItemExtensionType.PriceSpecification.Purchase value) {
            this.purchase = value;
        }

        /**
         * Gets the value of the sales property.
         * 
         * @return
         *     possible object is
         *     {@link PRICATListLineItemExtensionType.PriceSpecification.Sales }
         *     
         */
        public PRICATListLineItemExtensionType.PriceSpecification.Sales getSales() {
            return sales;
        }

        /**
         * Sets the value of the sales property.
         * 
         * @param value
         *     allowed object is
         *     {@link PRICATListLineItemExtensionType.PriceSpecification.Sales }
         *     
         */
        public void setSales(PRICATListLineItemExtensionType.PriceSpecification.Sales value) {
            this.sales = value;
        }

        /**
         * Gets the value of the otherUnitPrice property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the otherUnitPrice property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOtherUnitPrice().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link UnitPriceExtType }
         * 
         * 
         */
        public List<UnitPriceExtType> getOtherUnitPrice() {
            if (otherUnitPrice == null) {
                otherUnitPrice = new ArrayList<UnitPriceExtType>();
            }
            return this.otherUnitPrice;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="PurchasePrice" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PriceSpecificationType" maxOccurs="unbounded"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "purchasePrice"
        })
        public static class Purchase {

            @XmlElement(name = "PurchasePrice", required = true)
            protected List<PriceSpecificationType> purchasePrice;

            /**
             * Gets the value of the purchasePrice property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the purchasePrice property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPurchasePrice().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PriceSpecificationType }
             * 
             * 
             */
            public List<PriceSpecificationType> getPurchasePrice() {
                if (purchasePrice == null) {
                    purchasePrice = new ArrayList<PriceSpecificationType>();
                }
                return this.purchasePrice;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="SalesPrice" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PriceSpecificationType" maxOccurs="unbounded" minOccurs="0"/&gt;
         *         &lt;element name="FirstSalesDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "salesPrice",
            "firstSalesDate"
        })
        public static class Sales {

            @XmlElement(name = "SalesPrice")
            protected List<PriceSpecificationType> salesPrice;
            @XmlElement(name = "FirstSalesDate")
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar firstSalesDate;

            /**
             * Gets the value of the salesPrice property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the salesPrice property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSalesPrice().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PriceSpecificationType }
             * 
             * 
             */
            public List<PriceSpecificationType> getSalesPrice() {
                if (salesPrice == null) {
                    salesPrice = new ArrayList<PriceSpecificationType>();
                }
                return this.salesPrice;
            }

            /**
             * Gets the value of the firstSalesDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFirstSalesDate() {
                return firstSalesDate;
            }

            /**
             * Sets the value of the firstSalesDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFirstSalesDate(XMLGregorianCalendar value) {
                this.firstSalesDate = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ModelNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Edition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ReleaseDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *         &lt;element name="Measurements" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Weight" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="NetWeight" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
     *                             &lt;element name="GrossWeight" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Size" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}SizeType" minOccurs="0"/&gt;
     *                   &lt;element name="FillingQuantity" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
     *                   &lt;element name="AlcoholVolumePercentage" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PercentageType" minOccurs="0"/&gt;
     *                   &lt;element name="PlatoVolumePercentage" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PercentageType" minOccurs="0"/&gt;
     *                   &lt;element name="GenderSpecific" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *                         &lt;enumeration value="w"/&gt;
     *                         &lt;enumeration value="m"/&gt;
     *                       &lt;/restriction&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Appearance" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ColorInformation" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="ColorCode" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumIDType" minOccurs="0"/&gt;
     *                             &lt;element name="ColorTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Language" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Season" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="SeasonTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="SeasonYear" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *                         &lt;pattern value="[0-9]{2,4}"/&gt;
     *                       &lt;/restriction&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Indicators" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="LimitedEdition" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                   &lt;element name="TravelExclusive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                   &lt;element name="ContainsPork" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                   &lt;element name="ContainsColorant" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                   &lt;element name="ContainsAlcohol" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                   &lt;element name="ContainsAerosol" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                   &lt;element name="AlcoholTabacoTax" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                   &lt;element name="IsOneShot" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                   &lt;element name="MaintainingColdChain" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="AdditionalArticleNumber" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ArticleNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ArticleNumberExtType"/&gt;
     *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Parties" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}BusinessEntityType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "category",
        "modelNumber",
        "edition",
        "releaseDate",
        "measurements",
        "appearance",
        "season",
        "indicators",
        "additionalArticleNumber",
        "parties"
    })
    public static class ProductSpecification {

        @XmlElement(name = "Category")
        protected String category;
        @XmlElement(name = "ModelNumber")
        protected String modelNumber;
        @XmlElement(name = "Edition")
        protected String edition;
        @XmlElement(name = "ReleaseDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar releaseDate;
        @XmlElement(name = "Measurements")
        protected PRICATListLineItemExtensionType.ProductSpecification.Measurements measurements;
        @XmlElement(name = "Appearance")
        protected PRICATListLineItemExtensionType.ProductSpecification.Appearance appearance;
        @XmlElement(name = "Season")
        protected PRICATListLineItemExtensionType.ProductSpecification.Season season;
        @XmlElement(name = "Indicators")
        protected PRICATListLineItemExtensionType.ProductSpecification.Indicators indicators;
        @XmlElement(name = "AdditionalArticleNumber")
        protected List<PRICATListLineItemExtensionType.ProductSpecification.AdditionalArticleNumber> additionalArticleNumber;
        @XmlElement(name = "Parties")
        protected List<BusinessEntityType> parties;

        /**
         * Gets the value of the category property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCategory() {
            return category;
        }

        /**
         * Sets the value of the category property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCategory(String value) {
            this.category = value;
        }

        /**
         * Gets the value of the modelNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getModelNumber() {
            return modelNumber;
        }

        /**
         * Sets the value of the modelNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setModelNumber(String value) {
            this.modelNumber = value;
        }

        /**
         * Gets the value of the edition property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEdition() {
            return edition;
        }

        /**
         * Sets the value of the edition property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEdition(String value) {
            this.edition = value;
        }

        /**
         * Gets the value of the releaseDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getReleaseDate() {
            return releaseDate;
        }

        /**
         * Sets the value of the releaseDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setReleaseDate(XMLGregorianCalendar value) {
            this.releaseDate = value;
        }

        /**
         * Gets the value of the measurements property.
         * 
         * @return
         *     possible object is
         *     {@link PRICATListLineItemExtensionType.ProductSpecification.Measurements }
         *     
         */
        public PRICATListLineItemExtensionType.ProductSpecification.Measurements getMeasurements() {
            return measurements;
        }

        /**
         * Sets the value of the measurements property.
         * 
         * @param value
         *     allowed object is
         *     {@link PRICATListLineItemExtensionType.ProductSpecification.Measurements }
         *     
         */
        public void setMeasurements(PRICATListLineItemExtensionType.ProductSpecification.Measurements value) {
            this.measurements = value;
        }

        /**
         * Gets the value of the appearance property.
         * 
         * @return
         *     possible object is
         *     {@link PRICATListLineItemExtensionType.ProductSpecification.Appearance }
         *     
         */
        public PRICATListLineItemExtensionType.ProductSpecification.Appearance getAppearance() {
            return appearance;
        }

        /**
         * Sets the value of the appearance property.
         * 
         * @param value
         *     allowed object is
         *     {@link PRICATListLineItemExtensionType.ProductSpecification.Appearance }
         *     
         */
        public void setAppearance(PRICATListLineItemExtensionType.ProductSpecification.Appearance value) {
            this.appearance = value;
        }

        /**
         * Gets the value of the season property.
         * 
         * @return
         *     possible object is
         *     {@link PRICATListLineItemExtensionType.ProductSpecification.Season }
         *     
         */
        public PRICATListLineItemExtensionType.ProductSpecification.Season getSeason() {
            return season;
        }

        /**
         * Sets the value of the season property.
         * 
         * @param value
         *     allowed object is
         *     {@link PRICATListLineItemExtensionType.ProductSpecification.Season }
         *     
         */
        public void setSeason(PRICATListLineItemExtensionType.ProductSpecification.Season value) {
            this.season = value;
        }

        /**
         * Gets the value of the indicators property.
         * 
         * @return
         *     possible object is
         *     {@link PRICATListLineItemExtensionType.ProductSpecification.Indicators }
         *     
         */
        public PRICATListLineItemExtensionType.ProductSpecification.Indicators getIndicators() {
            return indicators;
        }

        /**
         * Sets the value of the indicators property.
         * 
         * @param value
         *     allowed object is
         *     {@link PRICATListLineItemExtensionType.ProductSpecification.Indicators }
         *     
         */
        public void setIndicators(PRICATListLineItemExtensionType.ProductSpecification.Indicators value) {
            this.indicators = value;
        }

        /**
         * Gets the value of the additionalArticleNumber property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the additionalArticleNumber property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdditionalArticleNumber().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PRICATListLineItemExtensionType.ProductSpecification.AdditionalArticleNumber }
         * 
         * 
         */
        public List<PRICATListLineItemExtensionType.ProductSpecification.AdditionalArticleNumber> getAdditionalArticleNumber() {
            if (additionalArticleNumber == null) {
                additionalArticleNumber = new ArrayList<PRICATListLineItemExtensionType.ProductSpecification.AdditionalArticleNumber>();
            }
            return this.additionalArticleNumber;
        }

        /**
         * Gets the value of the parties property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the parties property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParties().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BusinessEntityType }
         * 
         * 
         */
        public List<BusinessEntityType> getParties() {
            if (parties == null) {
                parties = new ArrayList<BusinessEntityType>();
            }
            return this.parties;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="ArticleNumber" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ArticleNumberExtType"/&gt;
         *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "articleNumber",
            "description"
        })
        public static class AdditionalArticleNumber {

            @XmlElement(name = "ArticleNumber", required = true)
            protected ArticleNumberExtType articleNumber;
            @XmlElement(name = "Description")
            protected String description;

            /**
             * Gets the value of the articleNumber property.
             * 
             * @return
             *     possible object is
             *     {@link ArticleNumberExtType }
             *     
             */
            public ArticleNumberExtType getArticleNumber() {
                return articleNumber;
            }

            /**
             * Sets the value of the articleNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link ArticleNumberExtType }
             *     
             */
            public void setArticleNumber(ArticleNumberExtType value) {
                this.articleNumber = value;
            }

            /**
             * Gets the value of the description property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescription() {
                return description;
            }

            /**
             * Sets the value of the description property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescription(String value) {
                this.description = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="ColorInformation" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="ColorCode" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumIDType" minOccurs="0"/&gt;
         *                   &lt;element name="ColorTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Language" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "colorInformation",
            "language"
        })
        public static class Appearance {

            @XmlElement(name = "ColorInformation")
            protected PRICATListLineItemExtensionType.ProductSpecification.Appearance.ColorInformation colorInformation;
            @XmlElement(name = "Language")
            protected String language;

            /**
             * Gets the value of the colorInformation property.
             * 
             * @return
             *     possible object is
             *     {@link PRICATListLineItemExtensionType.ProductSpecification.Appearance.ColorInformation }
             *     
             */
            public PRICATListLineItemExtensionType.ProductSpecification.Appearance.ColorInformation getColorInformation() {
                return colorInformation;
            }

            /**
             * Sets the value of the colorInformation property.
             * 
             * @param value
             *     allowed object is
             *     {@link PRICATListLineItemExtensionType.ProductSpecification.Appearance.ColorInformation }
             *     
             */
            public void setColorInformation(PRICATListLineItemExtensionType.ProductSpecification.Appearance.ColorInformation value) {
                this.colorInformation = value;
            }

            /**
             * Language of the product.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLanguage() {
                return language;
            }

            /**
             * Sets the value of the language property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLanguage(String value) {
                this.language = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="Color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="ColorCode" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}AlphaNumIDType" minOccurs="0"/&gt;
             *         &lt;element name="ColorTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "color",
                "colorCode",
                "colorTerm"
            })
            public static class ColorInformation {

                @XmlElement(name = "Color")
                protected String color;
                @XmlElement(name = "ColorCode")
                protected String colorCode;
                @XmlElement(name = "ColorTerm")
                protected String colorTerm;

                /**
                 * Gets the value of the color property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getColor() {
                    return color;
                }

                /**
                 * Sets the value of the color property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setColor(String value) {
                    this.color = value;
                }

                /**
                 * Gets the value of the colorCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getColorCode() {
                    return colorCode;
                }

                /**
                 * Sets the value of the colorCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setColorCode(String value) {
                    this.colorCode = value;
                }

                /**
                 * Gets the value of the colorTerm property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getColorTerm() {
                    return colorTerm;
                }

                /**
                 * Sets the value of the colorTerm property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setColorTerm(String value) {
                    this.colorTerm = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="LimitedEdition" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *         &lt;element name="TravelExclusive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *         &lt;element name="ContainsPork" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *         &lt;element name="ContainsColorant" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *         &lt;element name="ContainsAlcohol" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *         &lt;element name="ContainsAerosol" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *         &lt;element name="AlcoholTabacoTax" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *         &lt;element name="IsOneShot" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *         &lt;element name="MaintainingColdChain" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "limitedEdition",
            "travelExclusive",
            "containsPork",
            "containsColorant",
            "containsAlcohol",
            "containsAerosol",
            "alcoholTabacoTax",
            "isOneShot",
            "maintainingColdChain"
        })
        public static class Indicators {

            @XmlElement(name = "LimitedEdition")
            protected Boolean limitedEdition;
            @XmlElement(name = "TravelExclusive")
            protected Boolean travelExclusive;
            @XmlElement(name = "ContainsPork")
            protected Boolean containsPork;
            @XmlElement(name = "ContainsColorant")
            protected Boolean containsColorant;
            @XmlElement(name = "ContainsAlcohol")
            protected Boolean containsAlcohol;
            @XmlElement(name = "ContainsAerosol")
            protected Boolean containsAerosol;
            @XmlElement(name = "AlcoholTabacoTax")
            protected Boolean alcoholTabacoTax;
            @XmlElement(name = "IsOneShot")
            protected Boolean isOneShot;
            @XmlElement(name = "MaintainingColdChain")
            protected Boolean maintainingColdChain;

            /**
             * Gets the value of the limitedEdition property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isLimitedEdition() {
                return limitedEdition;
            }

            /**
             * Sets the value of the limitedEdition property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setLimitedEdition(Boolean value) {
                this.limitedEdition = value;
            }

            /**
             * Gets the value of the travelExclusive property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isTravelExclusive() {
                return travelExclusive;
            }

            /**
             * Sets the value of the travelExclusive property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setTravelExclusive(Boolean value) {
                this.travelExclusive = value;
            }

            /**
             * Gets the value of the containsPork property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isContainsPork() {
                return containsPork;
            }

            /**
             * Sets the value of the containsPork property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setContainsPork(Boolean value) {
                this.containsPork = value;
            }

            /**
             * Gets the value of the containsColorant property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isContainsColorant() {
                return containsColorant;
            }

            /**
             * Sets the value of the containsColorant property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setContainsColorant(Boolean value) {
                this.containsColorant = value;
            }

            /**
             * Gets the value of the containsAlcohol property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isContainsAlcohol() {
                return containsAlcohol;
            }

            /**
             * Sets the value of the containsAlcohol property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setContainsAlcohol(Boolean value) {
                this.containsAlcohol = value;
            }

            /**
             * Gets the value of the containsAerosol property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isContainsAerosol() {
                return containsAerosol;
            }

            /**
             * Sets the value of the containsAerosol property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setContainsAerosol(Boolean value) {
                this.containsAerosol = value;
            }

            /**
             * Gets the value of the alcoholTabacoTax property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isAlcoholTabacoTax() {
                return alcoholTabacoTax;
            }

            /**
             * Sets the value of the alcoholTabacoTax property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setAlcoholTabacoTax(Boolean value) {
                this.alcoholTabacoTax = value;
            }

            /**
             * Gets the value of the isOneShot property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIsOneShot() {
                return isOneShot;
            }

            /**
             * Sets the value of the isOneShot property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIsOneShot(Boolean value) {
                this.isOneShot = value;
            }

            /**
             * Gets the value of the maintainingColdChain property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isMaintainingColdChain() {
                return maintainingColdChain;
            }

            /**
             * Sets the value of the maintainingColdChain property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setMaintainingColdChain(Boolean value) {
                this.maintainingColdChain = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Weight" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="NetWeight" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
         *                   &lt;element name="GrossWeight" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Size" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}SizeType" minOccurs="0"/&gt;
         *         &lt;element name="FillingQuantity" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
         *         &lt;element name="AlcoholVolumePercentage" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PercentageType" minOccurs="0"/&gt;
         *         &lt;element name="PlatoVolumePercentage" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}PercentageType" minOccurs="0"/&gt;
         *         &lt;element name="GenderSpecific" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
         *               &lt;enumeration value="w"/&gt;
         *               &lt;enumeration value="m"/&gt;
         *             &lt;/restriction&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "weight",
            "size",
            "fillingQuantity",
            "alcoholVolumePercentage",
            "platoVolumePercentage",
            "genderSpecific"
        })
        public static class Measurements {

            @XmlElement(name = "Weight")
            protected PRICATListLineItemExtensionType.ProductSpecification.Measurements.Weight weight;
            @XmlElement(name = "Size")
            protected SizeType size;
            @XmlElement(name = "FillingQuantity")
            protected UnitType fillingQuantity;
            @XmlElement(name = "AlcoholVolumePercentage")
            protected BigDecimal alcoholVolumePercentage;
            @XmlElement(name = "PlatoVolumePercentage")
            protected BigDecimal platoVolumePercentage;
            @XmlElement(name = "GenderSpecific")
            protected String genderSpecific;

            /**
             * Gets the value of the weight property.
             * 
             * @return
             *     possible object is
             *     {@link PRICATListLineItemExtensionType.ProductSpecification.Measurements.Weight }
             *     
             */
            public PRICATListLineItemExtensionType.ProductSpecification.Measurements.Weight getWeight() {
                return weight;
            }

            /**
             * Sets the value of the weight property.
             * 
             * @param value
             *     allowed object is
             *     {@link PRICATListLineItemExtensionType.ProductSpecification.Measurements.Weight }
             *     
             */
            public void setWeight(PRICATListLineItemExtensionType.ProductSpecification.Measurements.Weight value) {
                this.weight = value;
            }

            /**
             * Gets the value of the size property.
             * 
             * @return
             *     possible object is
             *     {@link SizeType }
             *     
             */
            public SizeType getSize() {
                return size;
            }

            /**
             * Sets the value of the size property.
             * 
             * @param value
             *     allowed object is
             *     {@link SizeType }
             *     
             */
            public void setSize(SizeType value) {
                this.size = value;
            }

            /**
             * Gets the value of the fillingQuantity property.
             * 
             * @return
             *     possible object is
             *     {@link UnitType }
             *     
             */
            public UnitType getFillingQuantity() {
                return fillingQuantity;
            }

            /**
             * Sets the value of the fillingQuantity property.
             * 
             * @param value
             *     allowed object is
             *     {@link UnitType }
             *     
             */
            public void setFillingQuantity(UnitType value) {
                this.fillingQuantity = value;
            }

            /**
             * Gets the value of the alcoholVolumePercentage property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAlcoholVolumePercentage() {
                return alcoholVolumePercentage;
            }

            /**
             * Sets the value of the alcoholVolumePercentage property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAlcoholVolumePercentage(BigDecimal value) {
                this.alcoholVolumePercentage = value;
            }

            /**
             * Gets the value of the platoVolumePercentage property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPlatoVolumePercentage() {
                return platoVolumePercentage;
            }

            /**
             * Sets the value of the platoVolumePercentage property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPlatoVolumePercentage(BigDecimal value) {
                this.platoVolumePercentage = value;
            }

            /**
             * Gets the value of the genderSpecific property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getGenderSpecific() {
                return genderSpecific;
            }

            /**
             * Sets the value of the genderSpecific property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setGenderSpecific(String value) {
                this.genderSpecific = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="NetWeight" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
             *         &lt;element name="GrossWeight" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "netWeight",
                "grossWeight"
            })
            public static class Weight {

                @XmlElement(name = "NetWeight")
                protected UnitType netWeight;
                @XmlElement(name = "GrossWeight")
                protected UnitType grossWeight;

                /**
                 * Gets the value of the netWeight property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link UnitType }
                 *     
                 */
                public UnitType getNetWeight() {
                    return netWeight;
                }

                /**
                 * Sets the value of the netWeight property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link UnitType }
                 *     
                 */
                public void setNetWeight(UnitType value) {
                    this.netWeight = value;
                }

                /**
                 * Gets the value of the grossWeight property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link UnitType }
                 *     
                 */
                public UnitType getGrossWeight() {
                    return grossWeight;
                }

                /**
                 * Sets the value of the grossWeight property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link UnitType }
                 *     
                 */
                public void setGrossWeight(UnitType value) {
                    this.grossWeight = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="SeasonTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="SeasonYear" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
         *               &lt;pattern value="[0-9]{2,4}"/&gt;
         *             &lt;/restriction&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "seasonTime",
            "seasonYear"
        })
        public static class Season {

            @XmlElement(name = "SeasonTime")
            protected String seasonTime;
            @XmlElement(name = "SeasonYear")
            protected BigInteger seasonYear;

            /**
             * Gets the value of the seasonTime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeasonTime() {
                return seasonTime;
            }

            /**
             * Sets the value of the seasonTime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeasonTime(String value) {
                this.seasonTime = value;
            }

            /**
             * Gets the value of the seasonYear property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getSeasonYear() {
                return seasonYear;
            }

            /**
             * Sets the value of the seasonYear property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setSeasonYear(BigInteger value) {
                this.seasonYear = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="UnitConversion" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitConversionType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="BaseUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="PurchaseUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="SalesUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "unitConversion",
        "baseUnit",
        "purchaseUnit",
        "salesUnit"
    })
    public static class UnitSpecification {

        @XmlElement(name = "UnitConversion")
        protected List<UnitConversionType> unitConversion;
        @XmlElement(name = "BaseUnit")
        protected String baseUnit;
        @XmlElement(name = "PurchaseUnit")
        protected String purchaseUnit;
        @XmlElement(name = "SalesUnit")
        protected String salesUnit;

        /**
         * Gets the value of the unitConversion property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the unitConversion property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUnitConversion().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link UnitConversionType }
         * 
         * 
         */
        public List<UnitConversionType> getUnitConversion() {
            if (unitConversion == null) {
                unitConversion = new ArrayList<UnitConversionType>();
            }
            return this.unitConversion;
        }

        /**
         * Gets the value of the baseUnit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBaseUnit() {
            return baseUnit;
        }

        /**
         * Sets the value of the baseUnit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBaseUnit(String value) {
            this.baseUnit = value;
        }

        /**
         * Gets the value of the purchaseUnit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPurchaseUnit() {
            return purchaseUnit;
        }

        /**
         * Sets the value of the purchaseUnit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPurchaseUnit(String value) {
            this.purchaseUnit = value;
        }

        /**
         * Gets the value of the salesUnit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSalesUnit() {
            return salesUnit;
        }

        /**
         * Sets the value of the salesUnit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSalesUnit(String value) {
            this.salesUnit = value;
        }

    }

}
