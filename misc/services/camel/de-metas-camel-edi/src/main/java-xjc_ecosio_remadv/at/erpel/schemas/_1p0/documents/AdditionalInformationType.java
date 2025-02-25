
package at.erpel.schemas._1p0.documents;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AdditionalInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdditionalInformationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}SerialNumber" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ChargeNumber" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Classification" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}AlternativeQuantity" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}QuantityCharged" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}QuantityFreeOfCharge" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ChargeNumberExternal" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ChargeExpirationDate" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}BestBeforeDate" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ChargeDateOfManufacture" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}EngineeringChangeNumber" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}DrawingRevisionNumber" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}DeductionQuantity" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}StockLocation" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}StockGround" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}DestinationStockLocation" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}DestinationStockGround" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Size" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Weight" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Boxes" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}Color" minOccurs="0"/&gt;
 *         &lt;element name="CountryOfOrigin" type="{http://erpel.at/schemas/1p0/documents}CountryType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ConfirmedCountryOfOrigin" type="{http://erpel.at/schemas/1p0/documents}CountryType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalInformationType", propOrder = {
    "serialNumber",
    "chargeNumber",
    "classification",
    "alternativeQuantity",
    "quantityCharged",
    "quantityFreeOfCharge",
    "chargeNumberExternal",
    "chargeExpirationDate",
    "bestBeforeDate",
    "chargeDateOfManufacture",
    "engineeringChangeNumber",
    "drawingRevisionNumber",
    "deductionQuantity",
    "stockLocation",
    "stockGround",
    "destinationStockLocation",
    "destinationStockGround",
    "size",
    "weight",
    "boxes",
    "color",
    "countryOfOrigin",
    "confirmedCountryOfOrigin"
})
public class AdditionalInformationType {

    @XmlElement(name = "SerialNumber")
    protected List<String> serialNumber;
    @XmlElement(name = "ChargeNumber")
    protected List<String> chargeNumber;
    @XmlElement(name = "Classification")
    protected List<ClassificationType> classification;
    @XmlElement(name = "AlternativeQuantity")
    protected UnitType alternativeQuantity;
    @XmlElement(name = "QuantityCharged")
    protected UnitType quantityCharged;
    @XmlElement(name = "QuantityFreeOfCharge")
    protected UnitType quantityFreeOfCharge;
    @XmlElement(name = "ChargeNumberExternal")
    protected String chargeNumberExternal;
    @XmlElement(name = "ChargeExpirationDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar chargeExpirationDate;
    @XmlElement(name = "BestBeforeDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar bestBeforeDate;
    @XmlElement(name = "ChargeDateOfManufacture")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar chargeDateOfManufacture;
    @XmlElement(name = "EngineeringChangeNumber")
    protected String engineeringChangeNumber;
    @XmlElement(name = "DrawingRevisionNumber")
    protected String drawingRevisionNumber;
    @XmlElement(name = "DeductionQuantity")
    protected UnitType deductionQuantity;
    @XmlElement(name = "StockLocation")
    protected String stockLocation;
    @XmlElement(name = "StockGround")
    protected String stockGround;
    @XmlElement(name = "DestinationStockLocation")
    protected String destinationStockLocation;
    @XmlElement(name = "DestinationStockGround")
    protected String destinationStockGround;
    @XmlElement(name = "Size")
    protected String size;
    @XmlElement(name = "Weight")
    protected UnitType weight;
    @XmlElement(name = "Boxes")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger boxes;
    @XmlElement(name = "Color")
    protected String color;
    @XmlElement(name = "CountryOfOrigin")
    protected List<CountryType> countryOfOrigin;
    @XmlElement(name = "ConfirmedCountryOfOrigin")
    protected List<CountryType> confirmedCountryOfOrigin;

    /**
     * The serial number of the list line item product.Gets the value of the serialNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serialNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSerialNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSerialNumber() {
        if (serialNumber == null) {
            serialNumber = new ArrayList<String>();
        }
        return this.serialNumber;
    }

    /**
     * The number of the batch/lot.Gets the value of the chargeNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chargeNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChargeNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getChargeNumber() {
        if (chargeNumber == null) {
            chargeNumber = new ArrayList<String>();
        }
        return this.chargeNumber;
    }

    /**
     * The classification of the product/service in free-text form.Gets the value of the classification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClassificationType }
     * 
     * 
     */
    public List<ClassificationType> getClassification() {
        if (classification == null) {
            classification = new ArrayList<ClassificationType>();
        }
        return this.classification;
    }

    /**
     * Used to specify an alternative quantity in addition to ListLineItem/Quantity.
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getAlternativeQuantity() {
        return alternativeQuantity;
    }

    /**
     * Sets the value of the alternativeQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setAlternativeQuantity(UnitType value) {
        this.alternativeQuantity = value;
    }

    /**
     * Used to describe a quantity for which the customer is actually charged. May be provided in case this number deviates from ListLinteItem/Quantity.
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getQuantityCharged() {
        return quantityCharged;
    }

    /**
     * Sets the value of the quantityCharged property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setQuantityCharged(UnitType value) {
        this.quantityCharged = value;
    }

    /**
     * Used to describe a quantity which is provided to the customer free-of-charge. May be provided in case this number deviates from ListLinteItem/Quantity.
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getQuantityFreeOfCharge() {
        return quantityFreeOfCharge;
    }

    /**
     * Sets the value of the quantityFreeOfCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setQuantityFreeOfCharge(UnitType value) {
        this.quantityFreeOfCharge = value;
    }

    /**
     * An external batch/lot number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeNumberExternal() {
        return chargeNumberExternal;
    }

    /**
     * Sets the value of the chargeNumberExternal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeNumberExternal(String value) {
        this.chargeNumberExternal = value;
    }

    /**
     * The expiration date is the last day in which a product is safe to consume.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getChargeExpirationDate() {
        return chargeExpirationDate;
    }

    /**
     * Sets the value of the chargeExpirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setChargeExpirationDate(XMLGregorianCalendar value) {
        this.chargeExpirationDate = value;
    }

    /**
     * The best before date is the last day where the product is still in perfect condition.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBestBeforeDate() {
        return bestBeforeDate;
    }

    /**
     * Sets the value of the bestBeforeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBestBeforeDate(XMLGregorianCalendar value) {
        this.bestBeforeDate = value;
    }

    /**
     * The manufacturing date of a certain batch/lot.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getChargeDateOfManufacture() {
        return chargeDateOfManufacture;
    }

    /**
     * Sets the value of the chargeDateOfManufacture property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setChargeDateOfManufacture(XMLGregorianCalendar value) {
        this.chargeDateOfManufacture = value;
    }

    /**
     * A control number/level indicating the engineering change.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEngineeringChangeNumber() {
        return engineeringChangeNumber;
    }

    /**
     * Sets the value of the engineeringChangeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEngineeringChangeNumber(String value) {
        this.engineeringChangeNumber = value;
    }

    /**
     * Revision number of the underlying drawing of the product.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrawingRevisionNumber() {
        return drawingRevisionNumber;
    }

    /**
     * Sets the value of the drawingRevisionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrawingRevisionNumber(String value) {
        this.drawingRevisionNumber = value;
    }

    /**
     * 
     * 						In case a deduction took place, the deducted quantity is provided here.
     * 					
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getDeductionQuantity() {
        return deductionQuantity;
    }

    /**
     * Sets the value of the deductionQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setDeductionQuantity(UnitType value) {
        this.deductionQuantity = value;
    }

    /**
     * 
     * 						The stock location where the goods item was taken from.
     * 					
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStockLocation() {
        return stockLocation;
    }

    /**
     * Sets the value of the stockLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStockLocation(String value) {
        this.stockLocation = value;
    }

    /**
     * 
     * 						The stock ground the goods item was taken from.
     * 					
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStockGround() {
        return stockGround;
    }

    /**
     * Sets the value of the stockGround property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStockGround(String value) {
        this.stockGround = value;
    }

    /**
     * 
     * 						The destination stock location where the goods item will be shipped to.
     * 					
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationStockLocation() {
        return destinationStockLocation;
    }

    /**
     * Sets the value of the destinationStockLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationStockLocation(String value) {
        this.destinationStockLocation = value;
    }

    /**
     * 
     * 						The destination stock ground where the goods item will be shipped to.
     * 					
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationStockGround() {
        return destinationStockGround;
    }

    /**
     * Sets the value of the destinationStockGround property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationStockGround(String value) {
        this.destinationStockGround = value;
    }

    /**
     * The size of the product.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSize(String value) {
        this.size = value;
    }

    /**
     * The weight of the product.
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setWeight(UnitType value) {
        this.weight = value;
    }

    /**
     * The number of boxes where the products of the given list line item are stored in.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBoxes() {
        return boxes;
    }

    /**
     * Sets the value of the boxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBoxes(BigInteger value) {
        this.boxes = value;
    }

    /**
     * The color of the product of the given list line item.
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
     * Gets the value of the countryOfOrigin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the countryOfOrigin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCountryOfOrigin().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CountryType }
     * 
     * 
     */
    public List<CountryType> getCountryOfOrigin() {
        if (countryOfOrigin == null) {
            countryOfOrigin = new ArrayList<CountryType>();
        }
        return this.countryOfOrigin;
    }

    /**
     * Gets the value of the confirmedCountryOfOrigin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confirmedCountryOfOrigin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfirmedCountryOfOrigin().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CountryType }
     * 
     * 
     */
    public List<CountryType> getConfirmedCountryOfOrigin() {
        if (confirmedCountryOfOrigin == null) {
            confirmedCountryOfOrigin = new ArrayList<CountryType>();
        }
        return this.confirmedCountryOfOrigin;
    }

}
