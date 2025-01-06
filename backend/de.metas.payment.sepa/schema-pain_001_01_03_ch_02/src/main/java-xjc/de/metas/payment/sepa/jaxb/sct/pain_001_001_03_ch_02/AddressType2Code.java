
package de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressType2Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="AddressType2Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ADDR"/&gt;
 *     &lt;enumeration value="BIZZ"/&gt;
 *     &lt;enumeration value="DLVY"/&gt;
 *     &lt;enumeration value="HOME"/&gt;
 *     &lt;enumeration value="MLTO"/&gt;
 *     &lt;enumeration value="PBOX"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "AddressType2Code")
@XmlEnum
public enum AddressType2Code {

    ADDR,
    BIZZ,
    DLVY,
    HOME,
    MLTO,
    PBOX;

    public String value() {
        return name();
    }

    public static AddressType2Code fromValue(String v) {
        return valueOf(v);
    }

}
