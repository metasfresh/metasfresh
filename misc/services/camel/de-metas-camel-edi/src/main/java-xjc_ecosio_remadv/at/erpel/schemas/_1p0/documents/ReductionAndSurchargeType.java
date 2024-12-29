
package at.erpel.schemas._1p0.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReductionAndSurchargeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReductionAndSurchargeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://erpel.at/schemas/1p0/documents}ReductionAndSurchargeBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}TaxRate" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReductionAndSurchargeType", propOrder = {
    "taxRate"
})
public class ReductionAndSurchargeType
    extends ReductionAndSurchargeBaseType
{

    @XmlElement(name = "TaxRate")
    protected TaxRateType taxRate;

    /**
     * The VAT rate of the underlying products/services.
     * 
     * @return
     *     possible object is
     *     {@link TaxRateType }
     *     
     */
    public TaxRateType getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the value of the taxRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxRateType }
     *     
     */
    public void setTaxRate(TaxRateType value) {
        this.taxRate = value;
    }

}
