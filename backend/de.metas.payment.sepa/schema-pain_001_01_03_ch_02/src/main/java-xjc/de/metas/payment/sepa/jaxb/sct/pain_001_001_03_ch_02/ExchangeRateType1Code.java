
package de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExchangeRateType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ExchangeRateType1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="AGRD"/&gt;
 *     &lt;enumeration value="SALE"/&gt;
 *     &lt;enumeration value="SPOT"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ExchangeRateType1Code")
@XmlEnum
public enum ExchangeRateType1Code {

    AGRD,
    SALE,
    SPOT;

    public String value() {
        return name();
    }

    public static ExchangeRateType1Code fromValue(String v) {
        return valueOf(v);
    }

}
