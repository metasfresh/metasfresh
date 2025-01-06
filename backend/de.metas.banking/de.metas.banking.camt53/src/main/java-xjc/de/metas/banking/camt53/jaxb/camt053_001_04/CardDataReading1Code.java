
package de.metas.banking.camt53.jaxb.camt053_001_04;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardDataReading1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="CardDataReading1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="TAGC"/&gt;
 *     &lt;enumeration value="PHYS"/&gt;
 *     &lt;enumeration value="BRCD"/&gt;
 *     &lt;enumeration value="MGST"/&gt;
 *     &lt;enumeration value="CICC"/&gt;
 *     &lt;enumeration value="DFLE"/&gt;
 *     &lt;enumeration value="CTLS"/&gt;
 *     &lt;enumeration value="ECTL"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CardDataReading1Code")
@XmlEnum
public enum CardDataReading1Code {

    TAGC,
    PHYS,
    BRCD,
    MGST,
    CICC,
    DFLE,
    CTLS,
    ECTL;

    public String value() {
        return name();
    }

    public static CardDataReading1Code fromValue(String v) {
        return valueOf(v);
    }

}
