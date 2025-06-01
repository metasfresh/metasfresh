
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Liefervorgabe.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="Liefervorgabe">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Normal"/>
 *     <enumeration value="MaxVerbund"/>
 *     <enumeration value="MaxNachlieferung"/>
 *     <enumeration value="MaxDispo"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "Liefervorgabe")
@XmlEnum
public enum Liefervorgabe {

    @XmlEnumValue("Normal")
    NORMAL("Normal"),
    @XmlEnumValue("MaxVerbund")
    MAX_VERBUND("MaxVerbund"),
    @XmlEnumValue("MaxNachlieferung")
    MAX_NACHLIEFERUNG("MaxNachlieferung"),
    @XmlEnumValue("MaxDispo")
    MAX_DISPO("MaxDispo");
    private final String value;

    Liefervorgabe(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Liefervorgabe fromValue(String v) {
        for (Liefervorgabe c: Liefervorgabe.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
