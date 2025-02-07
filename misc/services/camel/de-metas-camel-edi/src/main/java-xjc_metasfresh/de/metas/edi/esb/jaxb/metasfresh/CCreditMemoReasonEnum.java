
package de.metas.edi.esb.jaxb.metasfresh;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for C_CreditMemo_ReasonEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="C_CreditMemo_ReasonEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CMF"/&gt;
 *     &lt;enumeration value="CMD"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "C_CreditMemo_ReasonEnum")
@XmlEnum
public enum CCreditMemoReasonEnum {

    @XmlEnumValue("CMF")
    Falschlieferung("CMF"),
    @XmlEnumValue("CMD")
    Doppellieferung("CMD");
    private final String value;

    CCreditMemoReasonEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CCreditMemoReasonEnum fromValue(String v) {
        for (CCreditMemoReasonEnum c: CCreditMemoReasonEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
