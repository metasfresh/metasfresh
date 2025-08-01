
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for higherInsurance complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="higherInsurance">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="amount">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *               <maxInclusive value="9999999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="currency">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="3"/>
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
@XmlType(name = "higherInsurance", propOrder = {
    "amount",
    "currency"
})
public class HigherInsurance {

    /**
     * Increased insurance value with 2 decimal point positions without separators.
     * 
     */
    protected long amount;
    /**
     * Currency code for increased insurance in format ISO 4217 alpha 3.
     * 
     */
    @XmlElement(required = true)
    protected String currency;

    /**
     * Increased insurance value with 2 decimal point positions without separators.
     * 
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(long value) {
        this.amount = value;
    }

    /**
     * Currency code for increased insurance in format ISO 4217 alpha 3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCurrency()
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

}
