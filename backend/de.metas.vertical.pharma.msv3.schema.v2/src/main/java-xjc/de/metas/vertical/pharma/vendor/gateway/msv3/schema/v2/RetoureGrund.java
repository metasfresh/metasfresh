
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetoureGrund.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="RetoureGrund">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Bestellfehler"/>
 *     <enumeration value="BerechnetNichtGeliefert"/>
 *     <enumeration value="Beschaedigt"/>
 *     <enumeration value="VerfallZuKurz"/>
 *     <enumeration value="ZuvielGeliefert"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "RetoureGrund")
@XmlEnum
public enum RetoureGrund {

    @XmlEnumValue("Bestellfehler")
    BESTELLFEHLER("Bestellfehler"),
    @XmlEnumValue("BerechnetNichtGeliefert")
    BERECHNET_NICHT_GELIEFERT("BerechnetNichtGeliefert"),
    @XmlEnumValue("Beschaedigt")
    BESCHAEDIGT("Beschaedigt"),
    @XmlEnumValue("VerfallZuKurz")
    VERFALL_ZU_KURZ("VerfallZuKurz"),
    @XmlEnumValue("ZuvielGeliefert")
    ZUVIEL_GELIEFERT("ZuvielGeliefert");
    private final String value;

    RetoureGrund(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RetoureGrund fromValue(String v) {
        for (RetoureGrund c: RetoureGrund.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
