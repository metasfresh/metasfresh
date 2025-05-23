
package at.erpel.schemas._1p0.documents.extensions.edifact;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for ExtendedDateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExtendedDateType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="DateFunctionCodeQualifier" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *       &lt;attribute name="DateFormatCode" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtendedDateType", propOrder = {
    "value"
})
public class ExtendedDateType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "DateFunctionCodeQualifier", namespace = "http://erpel.at/schemas/1p0/documents/extensions/edifact")
    @XmlSchemaType(name = "anySimpleType")
    protected String dateFunctionCodeQualifier;
    @XmlAttribute(name = "DateFormatCode", namespace = "http://erpel.at/schemas/1p0/documents/extensions/edifact")
    @XmlSchemaType(name = "anySimpleType")
    protected String dateFormatCode;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the dateFunctionCodeQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateFunctionCodeQualifier() {
        return dateFunctionCodeQualifier;
    }

    /**
     * Sets the value of the dateFunctionCodeQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateFunctionCodeQualifier(String value) {
        this.dateFunctionCodeQualifier = value;
    }

    /**
     * Gets the value of the dateFormatCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateFormatCode() {
        return dateFormatCode;
    }

    /**
     * Sets the value of the dateFormatCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateFormatCode(String value) {
        this.dateFormatCode = value;
    }

}
