
package at.erpel.schemas._1p0.documents.ext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ForecastListLineItemExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ForecastListLineItemExtensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}ForecastListLineItemExtension"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}ErpelForecastLineItemExtension"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForecastListLineItemExtensionType", propOrder = {
    "forecastListLineItemExtension",
    "erpelForecastLineItemExtension"
})
public class ForecastListLineItemExtensionType {

    @XmlElement(name = "ForecastListLineItemExtension", namespace = "http://erpel.at/schemas/1p0/documents/extensions/edifact")
    protected at.erpel.schemas._1p0.documents.extensions.edifact.ForecastListLineItemExtensionType forecastListLineItemExtension;
    @XmlElement(name = "ErpelForecastLineItemExtension")
    protected CustomType erpelForecastLineItemExtension;

    /**
     * Gets the value of the forecastListLineItemExtension property.
     * 
     * @return
     *     possible object is
     *     {@link at.erpel.schemas._1p0.documents.extensions.edifact.ForecastListLineItemExtensionType }
     *     
     */
    public at.erpel.schemas._1p0.documents.extensions.edifact.ForecastListLineItemExtensionType getForecastListLineItemExtension() {
        return forecastListLineItemExtension;
    }

    /**
     * Sets the value of the forecastListLineItemExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link at.erpel.schemas._1p0.documents.extensions.edifact.ForecastListLineItemExtensionType }
     *     
     */
    public void setForecastListLineItemExtension(at.erpel.schemas._1p0.documents.extensions.edifact.ForecastListLineItemExtensionType value) {
        this.forecastListLineItemExtension = value;
    }

    /**
     * Gets the value of the erpelForecastLineItemExtension property.
     * 
     * @return
     *     possible object is
     *     {@link CustomType }
     *     
     */
    public CustomType getErpelForecastLineItemExtension() {
        return erpelForecastLineItemExtension;
    }

    /**
     * Sets the value of the erpelForecastLineItemExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomType }
     *     
     */
    public void setErpelForecastLineItemExtension(CustomType value) {
        this.erpelForecastLineItemExtension = value;
    }

}
