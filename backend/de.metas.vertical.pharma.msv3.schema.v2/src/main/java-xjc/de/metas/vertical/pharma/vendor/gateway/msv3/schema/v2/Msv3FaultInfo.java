
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Msv3FaultInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Msv3FaultInfo">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="ErrorCode">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="5"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="EndanwenderFehlertext">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="1024"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="TechnischerFehlertext">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="1024"/>
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
@XmlType(name = "Msv3FaultInfo", propOrder = {
    "errorCode",
    "endanwenderFehlertext",
    "technischerFehlertext"
})
@XmlSeeAlso({
    AuthorizationFaultInfo.class,
    ServerFaultInfo.class,
    ValidationFaultInfo.class,
    DenialOfServiceFault.class,
    LieferscheinOderBarcodeUnbekanntFaultInfo.class
})
public class Msv3FaultInfo {

    @XmlElement(name = "ErrorCode", required = true)
    protected String errorCode;
    @XmlElement(name = "EndanwenderFehlertext", required = true)
    protected String endanwenderFehlertext;
    @XmlElement(name = "TechnischerFehlertext", required = true)
    protected String technischerFehlertext;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the endanwenderFehlertext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndanwenderFehlertext() {
        return endanwenderFehlertext;
    }

    /**
     * Sets the value of the endanwenderFehlertext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndanwenderFehlertext(String value) {
        this.endanwenderFehlertext = value;
    }

    /**
     * Gets the value of the technischerFehlertext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechnischerFehlertext() {
        return technischerFehlertext;
    }

    /**
     * Sets the value of the technischerFehlertext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechnischerFehlertext(String value) {
        this.technischerFehlertext = value;
    }

}
