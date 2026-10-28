
package de.metas.payment.sepa.jaxb.sct.pain_008_001_08;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExternalLocalInstrument1Code_SDD.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExternalLocalInstrument1Code_SDD"&gt;
 *   &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ExternalLocalInstrument1Code"&gt;
 *     &lt;minLength value="1"/&gt;
 *     &lt;maxLength value="35"/&gt;
 *     &lt;enumeration value="CORE"/&gt;
 *     &lt;enumeration value="B2B"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ExternalLocalInstrument1Code_SDD")
@XmlEnum
public enum ExternalLocalInstrument1CodeSDD {


    /**
     * SEPADirectDebitCore
     * 
     */
    CORE("CORE"),

    /**
     * SEPABusinessToBusinessDirectDebit
     * 
     */
    @XmlEnumValue("B2B")
    B_2_B("B2B");
    private final String value;

    ExternalLocalInstrument1CodeSDD(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExternalLocalInstrument1CodeSDD fromValue(String v) {
        for (ExternalLocalInstrument1CodeSDD c: ExternalLocalInstrument1CodeSDD.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
