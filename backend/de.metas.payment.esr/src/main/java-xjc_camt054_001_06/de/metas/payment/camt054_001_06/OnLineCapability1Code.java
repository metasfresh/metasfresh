
package de.metas.payment.camt054_001_06;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OnLineCapability1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="OnLineCapability1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="OFLN"/&gt;
 *     &lt;enumeration value="ONLN"/&gt;
 *     &lt;enumeration value="SMON"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "OnLineCapability1Code")
@XmlEnum
public enum OnLineCapability1Code {

    OFLN,
    ONLN,
    SMON;

    public String value() {
        return name();
    }

    public static OnLineCapability1Code fromValue(String v) {
        return valueOf(v);
    }

}
