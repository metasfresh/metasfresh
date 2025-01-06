
package de.metas.banking.camt53.jaxb.camt053_001_02;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InterestType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="InterestType1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="INDY"/&gt;
 *     &lt;enumeration value="OVRN"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "InterestType1Code")
@XmlEnum
public enum InterestType1Code {

    INDY,
    OVRN;

    public String value() {
        return name();
    }

    public static InterestType1Code fromValue(String v) {
        return valueOf(v);
    }

}
