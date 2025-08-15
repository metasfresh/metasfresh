
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for parcel complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="parcel">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="parcelLabelNumber" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="11"/>
 *               <maxLength value="14"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customerReferenceNumber1" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customerReferenceNumber2" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customerReferenceNumber3" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customerReferenceNumber4" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="swap" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="volume" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="999999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="weight" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="99999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="hazardousLimitedQuantities" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="higherInsurance" type="{http://dpd.com/common/service/types/ShipmentService/3.2}higherInsurance" minOccurs="0"/>
 *         <element name="content" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="addService" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <enumeration value="1"/>
 *               <enumeration value="2"/>
 *               <enumeration value="3"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="messageNumber" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="99999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="function" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="LOCKDZB"/>
 *               <enumeration value="LOCKASG"/>
 *               <enumeration value="LOCKEVM"/>
 *               <enumeration value="LOCKSHOP"/>
 *               <enumeration value="LOCKTV"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="parameter" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="300"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="cod" type="{http://dpd.com/common/service/types/ShipmentService/3.2}cod" minOccurs="0"/>
 *         <element name="international" type="{http://dpd.com/common/service/types/ShipmentService/3.2}international" minOccurs="0"/>
 *         <element name="hazardous" type="{http://dpd.com/common/service/types/ShipmentService/3.2}hazardous" maxOccurs="4" minOccurs="0"/>
 *         <element name="printInfo1OnParcelLabel" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="info1" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="29"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="info2" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="30"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="returns" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parcel", propOrder = {
    "parcelLabelNumber",
    "customerReferenceNumber1",
    "customerReferenceNumber2",
    "customerReferenceNumber3",
    "customerReferenceNumber4",
    "swap",
    "volume",
    "weight",
    "hazardousLimitedQuantities",
    "higherInsurance",
    "content",
    "addService",
    "messageNumber",
    "function",
    "parameter",
    "cod",
    "international",
    "hazardous",
    "printInfo1OnParcelLabel",
    "info1",
    "info2",
    "returns"
})
public class Parcel {

    /**
     * Parcel label number. Number range and validity are checked. Length can be 11 or 14.
     * 
     */
    protected String parcelLabelNumber;
    /**
     * Parcel customer reference number 1.
     * 
     */
    protected String customerReferenceNumber1;
    /**
     * Parcel customer reference number 2.
     * 
     */
    protected String customerReferenceNumber2;
    /**
     * Parcel customer reference number 3.
     * 
     */
    protected String customerReferenceNumber3;
    /**
     * Parcel customer reference number 4.
     * 
     */
    protected String customerReferenceNumber4;
    /**
     * Defines if the parcel is a consignment swap parcel. Default value is false.
     * 
     */
    protected Boolean swap;
    /**
     * Volume of the single parcel (length/width/height in format LLLWWWHHH) in cm without separators.
     * 
     */
    protected Integer volume;
    /**
     * Parcel weight in grams rounded in 10 gram units without decimal delimiter (e.g. 300 equals 3kg). Consignement allows 8-digit values, collection requests 7-digit and dangerous goods 6-digit.
     * 
     */
    protected Integer weight;
    /**
     * Defines if the parcel is a limited quantities hazardous goods parcel. Default value is false.
     * 
     */
    protected Boolean hazardousLimitedQuantities;
    /**
     * Defines if the parcel has increased insurance value. Default value is false.
     * 
     */
    protected HigherInsurance higherInsurance;
    /**
     * Content of the parcel. Mandatory for increased insurance. For collection requests maximum length is 50, for consignment it is 35.
     * 
     */
    protected String content;
    /**
     * Additional service.
     * Possible values are:
     *  1 = delivery information
     *  2 = documents return
     *  3 = written permission to deposit goods by sender
     * 
     */
    protected Integer addService;
    /**
     * Message number for consignment shipper information. Default value is 1.
     * 
     */
    protected Integer messageNumber;
    /**
     * Blockable functions, possible values are:
     * LOCKDZB = delivery to a third party with notification of delivery
     * LOCKASG = written permission to deposit goods
     * LOCKEVM = delivery with non-recurring authority
     * LOCKSHOP = parcel shop
     * LOCKTV = appointment
     * 
     */
    protected String function;
    /**
     * Free text for blockable functions.
     * 
     */
    protected String parameter;
    /**
     * Contains COD data for COD consignments.
     * 
     */
    protected Cod cod;
    /**
     * Contains data for consignments across customs frontiers.
     * 
     */
    protected International international;
    /**
     * Contains packing and substance data for dangerous goods.
     * 
     */
    protected List<Hazardous> hazardous;
    /**
     * Determines if content of parameter info1 will be added on the label of a collection request parcel. Default value is false.
     * 
     */
    protected Boolean printInfo1OnParcelLabel;
    /**
     * Information text 1. Will be printed on the parcel label if the flag printInfo1OnParcellabel is set to true.
     * 
     */
    protected String info1;
    /**
     * Information text 2. Will not be printed on the parcel label.
     * 
     */
    protected String info2;
    /**
     * Defines if parcel is a return parcel. The return parcel must always be next in order to the corresponding outbound parcel. Default value is false.
     * 
     */
    protected Boolean returns;

    /**
     * Parcel label number. Number range and validity are checked. Length can be 11 or 14.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParcelLabelNumber() {
        return parcelLabelNumber;
    }

    /**
     * Sets the value of the parcelLabelNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getParcelLabelNumber()
     */
    public void setParcelLabelNumber(String value) {
        this.parcelLabelNumber = value;
    }

    /**
     * Parcel customer reference number 1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerReferenceNumber1() {
        return customerReferenceNumber1;
    }

    /**
     * Sets the value of the customerReferenceNumber1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomerReferenceNumber1()
     */
    public void setCustomerReferenceNumber1(String value) {
        this.customerReferenceNumber1 = value;
    }

    /**
     * Parcel customer reference number 2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerReferenceNumber2() {
        return customerReferenceNumber2;
    }

    /**
     * Sets the value of the customerReferenceNumber2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomerReferenceNumber2()
     */
    public void setCustomerReferenceNumber2(String value) {
        this.customerReferenceNumber2 = value;
    }

    /**
     * Parcel customer reference number 3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerReferenceNumber3() {
        return customerReferenceNumber3;
    }

    /**
     * Sets the value of the customerReferenceNumber3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomerReferenceNumber3()
     */
    public void setCustomerReferenceNumber3(String value) {
        this.customerReferenceNumber3 = value;
    }

    /**
     * Parcel customer reference number 4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerReferenceNumber4() {
        return customerReferenceNumber4;
    }

    /**
     * Sets the value of the customerReferenceNumber4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomerReferenceNumber4()
     */
    public void setCustomerReferenceNumber4(String value) {
        this.customerReferenceNumber4 = value;
    }

    /**
     * Defines if the parcel is a consignment swap parcel. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSwap() {
        return swap;
    }

    /**
     * Sets the value of the swap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isSwap()
     */
    public void setSwap(Boolean value) {
        this.swap = value;
    }

    /**
     * Volume of the single parcel (length/width/height in format LLLWWWHHH) in cm without separators.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getVolume()
     */
    public void setVolume(Integer value) {
        this.volume = value;
    }

    /**
     * Parcel weight in grams rounded in 10 gram units without decimal delimiter (e.g. 300 equals 3kg). Consignement allows 8-digit values, collection requests 7-digit and dangerous goods 6-digit.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getWeight()
     */
    public void setWeight(Integer value) {
        this.weight = value;
    }

    /**
     * Defines if the parcel is a limited quantities hazardous goods parcel. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazardousLimitedQuantities() {
        return hazardousLimitedQuantities;
    }

    /**
     * Sets the value of the hazardousLimitedQuantities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isHazardousLimitedQuantities()
     */
    public void setHazardousLimitedQuantities(Boolean value) {
        this.hazardousLimitedQuantities = value;
    }

    /**
     * Defines if the parcel has increased insurance value. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link HigherInsurance }
     *     
     */
    public HigherInsurance getHigherInsurance() {
        return higherInsurance;
    }

    /**
     * Sets the value of the higherInsurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link HigherInsurance }
     *     
     * @see #getHigherInsurance()
     */
    public void setHigherInsurance(HigherInsurance value) {
        this.higherInsurance = value;
    }

    /**
     * Content of the parcel. Mandatory for increased insurance. For collection requests maximum length is 50, for consignment it is 35.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getContent()
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Additional service.
     * Possible values are:
     *  1 = delivery information
     *  2 = documents return
     *  3 = written permission to deposit goods by sender
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAddService() {
        return addService;
    }

    /**
     * Sets the value of the addService property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getAddService()
     */
    public void setAddService(Integer value) {
        this.addService = value;
    }

    /**
     * Message number for consignment shipper information. Default value is 1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMessageNumber() {
        return messageNumber;
    }

    /**
     * Sets the value of the messageNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     * @see #getMessageNumber()
     */
    public void setMessageNumber(Integer value) {
        this.messageNumber = value;
    }

    /**
     * Blockable functions, possible values are:
     * LOCKDZB = delivery to a third party with notification of delivery
     * LOCKASG = written permission to deposit goods
     * LOCKEVM = delivery with non-recurring authority
     * LOCKSHOP = parcel shop
     * LOCKTV = appointment
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets the value of the function property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getFunction()
     */
    public void setFunction(String value) {
        this.function = value;
    }

    /**
     * Free text for blockable functions.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getParameter()
     */
    public void setParameter(String value) {
        this.parameter = value;
    }

    /**
     * Contains COD data for COD consignments.
     * 
     * @return
     *     possible object is
     *     {@link Cod }
     *     
     */
    public Cod getCod() {
        return cod;
    }

    /**
     * Sets the value of the cod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cod }
     *     
     * @see #getCod()
     */
    public void setCod(Cod value) {
        this.cod = value;
    }

    /**
     * Contains data for consignments across customs frontiers.
     * 
     * @return
     *     possible object is
     *     {@link International }
     *     
     */
    public International getInternational() {
        return international;
    }

    /**
     * Sets the value of the international property.
     * 
     * @param value
     *     allowed object is
     *     {@link International }
     *     
     * @see #getInternational()
     */
    public void setInternational(International value) {
        this.international = value;
    }

    /**
     * Contains packing and substance data for dangerous goods.
     * 
     * Gets the value of the hazardous property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hazardous property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getHazardous().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Hazardous }
     * </p>
     * 
     * 
     * @return
     *     The value of the hazardous property.
     */
    public List<Hazardous> getHazardous() {
        if (hazardous == null) {
            hazardous = new ArrayList<>();
        }
        return this.hazardous;
    }

    /**
     * Determines if content of parameter info1 will be added on the label of a collection request parcel. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPrintInfo1OnParcelLabel() {
        return printInfo1OnParcelLabel;
    }

    /**
     * Sets the value of the printInfo1OnParcelLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isPrintInfo1OnParcelLabel()
     */
    public void setPrintInfo1OnParcelLabel(Boolean value) {
        this.printInfo1OnParcelLabel = value;
    }

    /**
     * Information text 1. Will be printed on the parcel label if the flag printInfo1OnParcellabel is set to true.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo1() {
        return info1;
    }

    /**
     * Sets the value of the info1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getInfo1()
     */
    public void setInfo1(String value) {
        this.info1 = value;
    }

    /**
     * Information text 2. Will not be printed on the parcel label.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo2() {
        return info2;
    }

    /**
     * Sets the value of the info2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getInfo2()
     */
    public void setInfo2(String value) {
        this.info2 = value;
    }

    /**
     * Defines if parcel is a return parcel. The return parcel must always be next in order to the corresponding outbound parcel. Default value is false.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReturns() {
        return returns;
    }

    /**
     * Sets the value of the returns property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isReturns()
     */
    public void setReturns(Boolean value) {
        this.returns = value;
    }

}
