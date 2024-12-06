
package de.metas.banking.camt53.jaxb.camt053_001_04;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartyType4Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="PartyType4Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MERC"/&gt;
 *     &lt;enumeration value="ACCP"/&gt;
 *     &lt;enumeration value="ITAG"/&gt;
 *     &lt;enumeration value="ACQR"/&gt;
 *     &lt;enumeration value="CISS"/&gt;
 *     &lt;enumeration value="TAXH"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PartyType4Code")
@XmlEnum
public enum PartyType4Code {

    MERC,
    ACCP,
    ITAG,
    ACQR,
    CISS,
    TAXH;

    public String value() {
        return name();
    }

    public static PartyType4Code fromValue(String v) {
        return valueOf(v);
    }

}
