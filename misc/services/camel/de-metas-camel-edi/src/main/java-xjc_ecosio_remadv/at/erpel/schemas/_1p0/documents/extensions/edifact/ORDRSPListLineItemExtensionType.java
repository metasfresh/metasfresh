
package at.erpel.schemas._1p0.documents.extensions.edifact;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ORDRSPListLineItemExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ORDRSPListLineItemExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="QuantityVariances" type="{http://erpel.at/schemas/1p0/documents/extensions/edifact}UnitType" minOccurs="0"/&gt;
 *         &lt;element name="VariancesReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PositionAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}FreeText" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ConfirmedQuantity" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://erpel.at/schemas/1p0/documents/extensions/edifact&gt;Decimal4Type"&gt;
 *                 &lt;attribute ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Date"/&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Condition" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ScheduledConditions" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Conditions" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}StatusIndicator" minOccurs="0"/&gt;
 *                                       &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DeliveryRequirementsCoded" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}FreeText" minOccurs="0"/&gt;
 *                             &lt;element name="ScheduledQuantities" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Qualifier" minOccurs="0"/&gt;
 *                                       &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Quantity"/&gt;
 *                                       &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Date"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
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
@XmlType(name = "ORDRSPListLineItemExtensionType", propOrder = {
    "quantityVariances",
    "variancesReasonCode",
    "positionAction",
    "freeText",
    "confirmedQuantity",
    "condition"
})
public class ORDRSPListLineItemExtensionType {

    @XmlElement(name = "QuantityVariances")
    protected UnitType quantityVariances;
    @XmlElement(name = "VariancesReasonCode")
    protected String variancesReasonCode;
    @XmlElement(name = "PositionAction")
    protected String positionAction;
    @XmlElement(name = "FreeText")
    protected List<FreeTextType> freeText;
    @XmlElement(name = "ConfirmedQuantity")
    protected List<ORDRSPListLineItemExtensionType.ConfirmedQuantity> confirmedQuantity;
    @XmlElement(name = "Condition")
    protected ORDRSPListLineItemExtensionType.Condition condition;

    /**
     * Gets the value of the quantityVariances property.
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getQuantityVariances() {
        return quantityVariances;
    }

    /**
     * Sets the value of the quantityVariances property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setQuantityVariances(UnitType value) {
        this.quantityVariances = value;
    }

    /**
     * Gets the value of the variancesReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariancesReasonCode() {
        return variancesReasonCode;
    }

    /**
     * Sets the value of the variancesReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariancesReasonCode(String value) {
        this.variancesReasonCode = value;
    }

    /**
     * Gets the value of the positionAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositionAction() {
        return positionAction;
    }

    /**
     * Sets the value of the positionAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositionAction(String value) {
        this.positionAction = value;
    }

    /**
     * Free text information.Gets the value of the freeText property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeText property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeText().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FreeTextType }
     * 
     * 
     */
    public List<FreeTextType> getFreeText() {
        if (freeText == null) {
            freeText = new ArrayList<FreeTextType>();
        }
        return this.freeText;
    }

    /**
     * Gets the value of the confirmedQuantity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confirmedQuantity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfirmedQuantity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ORDRSPListLineItemExtensionType.ConfirmedQuantity }
     * 
     * 
     */
    public List<ORDRSPListLineItemExtensionType.ConfirmedQuantity> getConfirmedQuantity() {
        if (confirmedQuantity == null) {
            confirmedQuantity = new ArrayList<ORDRSPListLineItemExtensionType.ConfirmedQuantity>();
        }
        return this.confirmedQuantity;
    }

    /**
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link ORDRSPListLineItemExtensionType.Condition }
     *     
     */
    public ORDRSPListLineItemExtensionType.Condition getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link ORDRSPListLineItemExtensionType.Condition }
     *     
     */
    public void setCondition(ORDRSPListLineItemExtensionType.Condition value) {
        this.condition = value;
    }


    /**
     * DEPRECATED. Use Document/Details/ItemList/ListLineItem/ext:ListLineItemExtension/edifact:ListLineItemExtension/ns1:QuantitySchedules instead.
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="ScheduledConditions" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Conditions" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}StatusIndicator" minOccurs="0"/&gt;
     *                             &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DeliveryRequirementsCoded" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}FreeText" minOccurs="0"/&gt;
     *                   &lt;element name="ScheduledQuantities" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Qualifier" minOccurs="0"/&gt;
     *                             &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Quantity"/&gt;
     *                             &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Date"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
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
    @XmlType(name = "", propOrder = {
        "scheduledConditions"
    })
    public static class Condition {

        @XmlElement(name = "ScheduledConditions")
        protected List<ORDRSPListLineItemExtensionType.Condition.ScheduledConditions> scheduledConditions;

        /**
         * Gets the value of the scheduledConditions property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the scheduledConditions property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getScheduledConditions().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ORDRSPListLineItemExtensionType.Condition.ScheduledConditions }
         * 
         * 
         */
        public List<ORDRSPListLineItemExtensionType.Condition.ScheduledConditions> getScheduledConditions() {
            if (scheduledConditions == null) {
                scheduledConditions = new ArrayList<ORDRSPListLineItemExtensionType.Condition.ScheduledConditions>();
            }
            return this.scheduledConditions;
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
         *         &lt;element name="Conditions" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}StatusIndicator" minOccurs="0"/&gt;
         *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DeliveryRequirementsCoded" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}FreeText" minOccurs="0"/&gt;
         *         &lt;element name="ScheduledQuantities" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Qualifier" minOccurs="0"/&gt;
         *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Quantity"/&gt;
         *                   &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Date"/&gt;
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
        @XmlType(name = "", propOrder = {
            "conditions",
            "freeText",
            "scheduledQuantities"
        })
        public static class ScheduledConditions {

            @XmlElement(name = "Conditions")
            protected ORDRSPListLineItemExtensionType.Condition.ScheduledConditions.Conditions conditions;
            @XmlElement(name = "FreeText")
            protected FreeTextType freeText;
            @XmlElement(name = "ScheduledQuantities")
            protected List<ORDRSPListLineItemExtensionType.Condition.ScheduledConditions.ScheduledQuantities> scheduledQuantities;

            /**
             * Gets the value of the conditions property.
             * 
             * @return
             *     possible object is
             *     {@link ORDRSPListLineItemExtensionType.Condition.ScheduledConditions.Conditions }
             *     
             */
            public ORDRSPListLineItemExtensionType.Condition.ScheduledConditions.Conditions getConditions() {
                return conditions;
            }

            /**
             * Sets the value of the conditions property.
             * 
             * @param value
             *     allowed object is
             *     {@link ORDRSPListLineItemExtensionType.Condition.ScheduledConditions.Conditions }
             *     
             */
            public void setConditions(ORDRSPListLineItemExtensionType.Condition.ScheduledConditions.Conditions value) {
                this.conditions = value;
            }

            /**
             * Gets the value of the freeText property.
             * 
             * @return
             *     possible object is
             *     {@link FreeTextType }
             *     
             */
            public FreeTextType getFreeText() {
                return freeText;
            }

            /**
             * Sets the value of the freeText property.
             * 
             * @param value
             *     allowed object is
             *     {@link FreeTextType }
             *     
             */
            public void setFreeText(FreeTextType value) {
                this.freeText = value;
            }

            /**
             * Gets the value of the scheduledQuantities property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the scheduledQuantities property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getScheduledQuantities().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ORDRSPListLineItemExtensionType.Condition.ScheduledConditions.ScheduledQuantities }
             * 
             * 
             */
            public List<ORDRSPListLineItemExtensionType.Condition.ScheduledConditions.ScheduledQuantities> getScheduledQuantities() {
                if (scheduledQuantities == null) {
                    scheduledQuantities = new ArrayList<ORDRSPListLineItemExtensionType.Condition.ScheduledConditions.ScheduledQuantities>();
                }
                return this.scheduledQuantities;
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
             *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}StatusIndicator" minOccurs="0"/&gt;
             *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DeliveryRequirementsCoded" minOccurs="0"/&gt;
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
                "statusIndicator",
                "deliveryRequirementsCoded"
            })
            public static class Conditions {

                @XmlElement(name = "StatusIndicator")
                protected String statusIndicator;
                @XmlElement(name = "DeliveryRequirementsCoded")
                protected String deliveryRequirementsCoded;

                /**
                 * Gets the value of the statusIndicator property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStatusIndicator() {
                    return statusIndicator;
                }

                /**
                 * Sets the value of the statusIndicator property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStatusIndicator(String value) {
                    this.statusIndicator = value;
                }

                /**
                 * Gets the value of the deliveryRequirementsCoded property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDeliveryRequirementsCoded() {
                    return deliveryRequirementsCoded;
                }

                /**
                 * Sets the value of the deliveryRequirementsCoded property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDeliveryRequirementsCoded(String value) {
                    this.deliveryRequirementsCoded = value;
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
             *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Qualifier" minOccurs="0"/&gt;
             *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Quantity"/&gt;
             *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Date"/&gt;
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
                "qualifier",
                "quantity",
                "date"
            })
            public static class ScheduledQuantities {

                @XmlElement(name = "Qualifier")
                protected String qualifier;
                @XmlElement(name = "Quantity", required = true)
                protected UnitType quantity;
                @XmlElement(name = "Date", required = true)
                @XmlSchemaType(name = "dateTime")
                protected XMLGregorianCalendar date;

                /**
                 * Gets the value of the qualifier property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getQualifier() {
                    return qualifier;
                }

                /**
                 * Sets the value of the qualifier property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setQualifier(String value) {
                    this.qualifier = value;
                }

                /**
                 * Gets the value of the quantity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link UnitType }
                 *     
                 */
                public UnitType getQuantity() {
                    return quantity;
                }

                /**
                 * Sets the value of the quantity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link UnitType }
                 *     
                 */
                public void setQuantity(UnitType value) {
                    this.quantity = value;
                }

                /**
                 * Gets the value of the date property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getDate() {
                    return date;
                }

                /**
                 * Sets the value of the date property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setDate(XMLGregorianCalendar value) {
                    this.date = value;
                }

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
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://erpel.at/schemas/1p0/documents/extensions/edifact&gt;Decimal4Type"&gt;
     *       &lt;attribute ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Date"/&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class ConfirmedQuantity {

        @XmlValue
        protected BigDecimal value;
        @XmlAttribute(name = "Date", namespace = "http://erpel.at/schemas/1p0/documents/extensions/edifact")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar date;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValue(BigDecimal value) {
            this.value = value;
        }

        /**
         * Gets the value of the date property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDate() {
            return date;
        }

        /**
         * Sets the value of the date property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDate(XMLGregorianCalendar value) {
            this.date = value;
        }

    }

}
