
package de.metas.payment.camt054_001_06;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticationEntity1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="AuthenticationEntity1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ICCD"/&gt;
 *     &lt;enumeration value="AGNT"/&gt;
 *     &lt;enumeration value="MERC"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "AuthenticationEntity1Code")
@XmlEnum
public enum AuthenticationEntity1Code {

    ICCD,
    AGNT,
    MERC;

    public String value() {
        return name();
    }

    public static AuthenticationEntity1Code fromValue(String v) {
        return valueOf(v);
    }

}
