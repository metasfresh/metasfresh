
package de.metas.payment.camt054_001_06;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChargeBearerType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ChargeBearerType1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="DEBT"/&gt;
 *     &lt;enumeration value="CRED"/&gt;
 *     &lt;enumeration value="SHAR"/&gt;
 *     &lt;enumeration value="SLEV"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ChargeBearerType1Code")
@XmlEnum
public enum ChargeBearerType1Code {

    DEBT,
    CRED,
    SHAR,
    SLEV;

    public String value() {
        return name();
    }

    public static ChargeBearerType1Code fromValue(String v) {
        return valueOf(v);
    }

}
