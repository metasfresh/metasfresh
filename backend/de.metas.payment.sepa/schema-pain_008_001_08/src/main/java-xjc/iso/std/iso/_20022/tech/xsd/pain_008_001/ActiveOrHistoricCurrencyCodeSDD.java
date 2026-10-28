
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActiveOrHistoricCurrencyCode_SDD.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActiveOrHistoricCurrencyCode_SDD"&gt;
 *   &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ActiveOrHistoricCurrencyCode"&gt;
 *     &lt;pattern value="[A-Z]{3,3}"/&gt;
 *     &lt;enumeration value="EUR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ActiveOrHistoricCurrencyCode_SDD")
@XmlEnum
public enum ActiveOrHistoricCurrencyCodeSDD {


    /**
     * Euro
     * 
     */
    EUR;

    public String value() {
        return name();
    }

    public static ActiveOrHistoricCurrencyCodeSDD fromValue(String v) {
        return valueOf(v);
    }

}
