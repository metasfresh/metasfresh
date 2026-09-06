
package de.metas.payment.sepa.jaxb.sct.pain_008_003_02;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for ActiveOrHistoricCurrencyAndAmountSEPA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ActiveOrHistoricCurrencyAndAmountSEPA"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;urn:iso:std:iso:20022:tech:xsd:pain.008.003.02&gt;ActiveOrHistoricCurrencyAndAmount_SimpleTypeSEPA"&gt;
 *       &lt;attribute name="Ccy" use="required" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.003.02}ActiveOrHistoricCurrencyCodeEUR" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActiveOrHistoricCurrencyAndAmountSEPA", propOrder = {
    "value"
})
public class ActiveOrHistoricCurrencyAndAmountSEPA {

    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "Ccy", required = true)
    protected ActiveOrHistoricCurrencyCodeEUR ccy;

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
     * Gets the value of the ccy property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyCodeEUR }
     *     
     */
    public ActiveOrHistoricCurrencyCodeEUR getCcy() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyCodeEUR }
     *     
     */
    public void setCcy(ActiveOrHistoricCurrencyCodeEUR value) {
        this.ccy = value;
    }

}
