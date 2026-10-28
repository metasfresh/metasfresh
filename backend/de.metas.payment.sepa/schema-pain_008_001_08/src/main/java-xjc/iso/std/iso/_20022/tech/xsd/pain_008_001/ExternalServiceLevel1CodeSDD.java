
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExternalServiceLevel1Code_SDD.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExternalServiceLevel1Code_SDD"&gt;
 *   &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ExternalServiceLevel1Code"&gt;
 *     &lt;minLength value="1"/&gt;
 *     &lt;maxLength value="4"/&gt;
 *     &lt;enumeration value="SEPA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ExternalServiceLevel1Code_SDD")
@XmlEnum
public enum ExternalServiceLevel1CodeSDD {


    /**
     * SingleEuroPaymentsArea
     * 
     */
    SEPA;

    public String value() {
        return name();
    }

    public static ExternalServiceLevel1CodeSDD fromValue(String v) {
        return valueOf(v);
    }

}
