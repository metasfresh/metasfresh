
package de.metas.payment.camt054_001_06;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttendanceContext1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="AttendanceContext1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ATTD"/&gt;
 *     &lt;enumeration value="SATT"/&gt;
 *     &lt;enumeration value="UATT"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "AttendanceContext1Code")
@XmlEnum
public enum AttendanceContext1Code {

    ATTD,
    SATT,
    UATT;

    public String value() {
        return name();
    }

    public static AttendanceContext1Code fromValue(String v) {
        return valueOf(v);
    }

}
