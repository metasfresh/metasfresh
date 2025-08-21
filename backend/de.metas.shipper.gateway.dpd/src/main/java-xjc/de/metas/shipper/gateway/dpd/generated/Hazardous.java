
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.math.BigDecimal;


/**
 * Bundles hazardous materials data.
 * 
 * <p>Java class for hazardous complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="hazardous">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="identificationUnNo">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="4"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="identificationClass">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="6"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="classificationCode" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="5"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="packingGroup" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="5"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="packingCode">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="0A"/>
 *               <enumeration value="0A1"/>
 *               <enumeration value="0A2"/>
 *               <enumeration value="1A"/>
 *               <enumeration value="1A1"/>
 *               <enumeration value="1A2"/>
 *               <enumeration value="1B"/>
 *               <enumeration value="1B1"/>
 *               <enumeration value="1B2"/>
 *               <enumeration value="1H"/>
 *               <enumeration value="1H1"/>
 *               <enumeration value="1H2"/>
 *               <enumeration value="3A"/>
 *               <enumeration value="3A1"/>
 *               <enumeration value="3A2"/>
 *               <enumeration value="3B"/>
 *               <enumeration value="3B1"/>
 *               <enumeration value="3B2"/>
 *               <enumeration value="3H"/>
 *               <enumeration value="3H1"/>
 *               <enumeration value="3H2"/>
 *               <enumeration value="4A"/>
 *               <enumeration value="4B"/>
 *               <enumeration value="4D"/>
 *               <enumeration value="4G"/>
 *               <enumeration value="4H"/>
 *               <enumeration value="4H1"/>
 *               <enumeration value="4H2"/>
 *               <enumeration value="5H"/>
 *               <enumeration value="5M"/>
 *               <enumeration value="6H"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="description">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="160"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="subsidiaryRisk" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="10"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="tunnelRestrictionCode" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="A"/>
 *               <enumeration value="B"/>
 *               <enumeration value="C"/>
 *               <enumeration value="D"/>
 *               <enumeration value="E"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="hazardousWeight">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               <fractionDigits value="2"/>
 *               <totalDigits value="6"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="netWeight" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               <fractionDigits value="2"/>
 *               <totalDigits value="6"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="factor">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="notOtherwiseSpecified" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="150"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hazardous", propOrder = {
    "identificationUnNo",
    "identificationClass",
    "classificationCode",
    "packingGroup",
    "packingCode",
    "description",
    "subsidiaryRisk",
    "tunnelRestrictionCode",
    "hazardousWeight",
    "netWeight",
    "factor",
    "notOtherwiseSpecified"
})
public class Hazardous {

    /**
     * Defines UN number of hazardous substance. UN numbers range between 0004 and 9004.
     * 
     */
    @XmlElement(required = true)
    protected String identificationUnNo;
    /**
     * Defines class of hazardous substance. Possible values range from 1 to 9. Subclasses are specified as position after decimal point
     * (e.g. class 1 is "explosives", class 1.6 is "extremely insensitive explosives").
     * 
     */
    @XmlElement(required = true)
    protected String identificationClass;
    /**
     * Defines classification code of hazardous substance.
     * 
     */
    protected String classificationCode;
    /**
     * Defines packing group of hazardous substance. Common values are "I", "II" or "III".
     * 
     */
    protected String packingGroup;
    /**
     * Defines packing code. Possible values are:
     *  0A = thin sheet packing
     *  0A1 = thin sheet packing with non removable head
     *  0A2 = thin sheet packing with removable head
     *  1A = steel barrel
     *  1A1 = steel barrel with non removable head
     *  1A2 = steel barrel with removable head
     *  1B = aluminium barrel
     *  1B1 = aluminium barrel with non removable head
     *  1B2 = aluminium barrel with removable head
     *  1H = plastics barrel
     *  1H1 = plastics barrel with non removable head
     *  1H2 = plastics barrel with removable head
     *  3A = steel canister
     *  3A1 = steel canister with non removable head
     *  3A2 = steel canister with removable head
     *  3B = aluminium canister,
     *  3B1 = aluminium canister with non removable head
     *  3B2 = aluminium canister with removable head
     *  3H = plastics canister,
     *  3H1 = plastics canister with non removable head
     *  3H2 = plastics canister with removable head
     *  4A = steel crates
     *  4B = aluminium crate
     *  4D = plywood crate
     *  4G = cardboard crate
     *  4H = plastics crate
     *  4H1 = plastics crate plastics expanded
     *  4H2 = plastics crate plastics solid
     *  5H = plastics bags
     *  5M = paper bags
     *  6H = combination packings
     * 
     */
    @XmlElement(required = true)
    protected String packingCode;
    /**
     * Description of hazardous substance (redundant).
     * 
     */
    @XmlElement(required = true)
    protected String description;
    /**
     * Subsidiary risk of hazardous substance (redundant).
     * 
     */
    protected String subsidiaryRisk;
    /**
     * Tunnel restriction code of hazardous substance (redundant). Possible values are "A", "B", "C", "D" or "E".
     * 
     */
    protected String tunnelRestrictionCode;
    /**
     * Weight of hazardous substance with 4 places before decimal point and 2 decimal places (with separator).
     * 
     */
    @XmlElement(required = true)
    protected BigDecimal hazardousWeight;
    /**
     * Net weight of hazardous substance with 4 places before decimal point and 2 decimal places (with separator).
     * 
     */
    protected BigDecimal netWeight;
    /**
     * Factor of hazardous substance (redundant ). 999 means unlimited.
     * 
     */
    protected int factor;
    /**
     * A not otherwise specified text which is mandatory for some substances.
     * 
     */
    protected String notOtherwiseSpecified;

    /**
     * Defines UN number of hazardous substance. UN numbers range between 0004 and 9004.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationUnNo() {
        return identificationUnNo;
    }

    /**
     * Sets the value of the identificationUnNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getIdentificationUnNo()
     */
    public void setIdentificationUnNo(String value) {
        this.identificationUnNo = value;
    }

    /**
     * Defines class of hazardous substance. Possible values range from 1 to 9. Subclasses are specified as position after decimal point
     * (e.g. class 1 is "explosives", class 1.6 is "extremely insensitive explosives").
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationClass() {
        return identificationClass;
    }

    /**
     * Sets the value of the identificationClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getIdentificationClass()
     */
    public void setIdentificationClass(String value) {
        this.identificationClass = value;
    }

    /**
     * Defines classification code of hazardous substance.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassificationCode() {
        return classificationCode;
    }

    /**
     * Sets the value of the classificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getClassificationCode()
     */
    public void setClassificationCode(String value) {
        this.classificationCode = value;
    }

    /**
     * Defines packing group of hazardous substance. Common values are "I", "II" or "III".
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackingGroup() {
        return packingGroup;
    }

    /**
     * Sets the value of the packingGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPackingGroup()
     */
    public void setPackingGroup(String value) {
        this.packingGroup = value;
    }

    /**
     * Defines packing code. Possible values are:
     *  0A = thin sheet packing
     *  0A1 = thin sheet packing with non removable head
     *  0A2 = thin sheet packing with removable head
     *  1A = steel barrel
     *  1A1 = steel barrel with non removable head
     *  1A2 = steel barrel with removable head
     *  1B = aluminium barrel
     *  1B1 = aluminium barrel with non removable head
     *  1B2 = aluminium barrel with removable head
     *  1H = plastics barrel
     *  1H1 = plastics barrel with non removable head
     *  1H2 = plastics barrel with removable head
     *  3A = steel canister
     *  3A1 = steel canister with non removable head
     *  3A2 = steel canister with removable head
     *  3B = aluminium canister,
     *  3B1 = aluminium canister with non removable head
     *  3B2 = aluminium canister with removable head
     *  3H = plastics canister,
     *  3H1 = plastics canister with non removable head
     *  3H2 = plastics canister with removable head
     *  4A = steel crates
     *  4B = aluminium crate
     *  4D = plywood crate
     *  4G = cardboard crate
     *  4H = plastics crate
     *  4H1 = plastics crate plastics expanded
     *  4H2 = plastics crate plastics solid
     *  5H = plastics bags
     *  5M = paper bags
     *  6H = combination packings
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackingCode() {
        return packingCode;
    }

    /**
     * Sets the value of the packingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPackingCode()
     */
    public void setPackingCode(String value) {
        this.packingCode = value;
    }

    /**
     * Description of hazardous substance (redundant).
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
     * @see #getDescription()
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Subsidiary risk of hazardous substance (redundant).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubsidiaryRisk() {
        return subsidiaryRisk;
    }

    /**
     * Sets the value of the subsidiaryRisk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getSubsidiaryRisk()
     */
    public void setSubsidiaryRisk(String value) {
        this.subsidiaryRisk = value;
    }

    /**
     * Tunnel restriction code of hazardous substance (redundant). Possible values are "A", "B", "C", "D" or "E".
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTunnelRestrictionCode() {
        return tunnelRestrictionCode;
    }

    /**
     * Sets the value of the tunnelRestrictionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getTunnelRestrictionCode()
     */
    public void setTunnelRestrictionCode(String value) {
        this.tunnelRestrictionCode = value;
    }

    /**
     * Weight of hazardous substance with 4 places before decimal point and 2 decimal places (with separator).
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHazardousWeight() {
        return hazardousWeight;
    }

    /**
     * Sets the value of the hazardousWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     * @see #getHazardousWeight()
     */
    public void setHazardousWeight(BigDecimal value) {
        this.hazardousWeight = value;
    }

    /**
     * Net weight of hazardous substance with 4 places before decimal point and 2 decimal places (with separator).
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetWeight() {
        return netWeight;
    }

    /**
     * Sets the value of the netWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     * @see #getNetWeight()
     */
    public void setNetWeight(BigDecimal value) {
        this.netWeight = value;
    }

    /**
     * Factor of hazardous substance (redundant ). 999 means unlimited.
     * 
     */
    public int getFactor() {
        return factor;
    }

    /**
     * Sets the value of the factor property.
     * 
     */
    public void setFactor(int value) {
        this.factor = value;
    }

    /**
     * A not otherwise specified text which is mandatory for some substances.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotOtherwiseSpecified() {
        return notOtherwiseSpecified;
    }

    /**
     * Sets the value of the notOtherwiseSpecified property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getNotOtherwiseSpecified()
     */
    public void setNotOtherwiseSpecified(String value) {
        this.notOtherwiseSpecified = value;
    }

}
