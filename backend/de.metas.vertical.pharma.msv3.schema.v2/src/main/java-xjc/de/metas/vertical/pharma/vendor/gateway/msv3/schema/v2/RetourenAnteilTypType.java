
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetourenAnteilTypType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="RetourenAnteilTypType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="RetoureAkzeptiert"/>
 *     <enumeration value="RetoureAkzeptiertRueckspracheNoetig"/>
 *     <enumeration value="RetoureAkzeptiertKeineRuecksendungNoetig"/>
 *     <enumeration value="LieferscheinnummerUnbekannt"/>
 *     <enumeration value="PznInLieferscheinnummerNichtEnthalten"/>
 *     <enumeration value="KeineRuecknahme"/>
 *     <enumeration value="RueckgabefristUeberschritten"/>
 *     <enumeration value="MengeUeberschritten"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "RetourenAnteilTypType")
@XmlEnum
public enum RetourenAnteilTypType {

    @XmlEnumValue("RetoureAkzeptiert")
    RETOURE_AKZEPTIERT("RetoureAkzeptiert"),
    @XmlEnumValue("RetoureAkzeptiertRueckspracheNoetig")
    RETOURE_AKZEPTIERT_RUECKSPRACHE_NOETIG("RetoureAkzeptiertRueckspracheNoetig"),
    @XmlEnumValue("RetoureAkzeptiertKeineRuecksendungNoetig")
    RETOURE_AKZEPTIERT_KEINE_RUECKSENDUNG_NOETIG("RetoureAkzeptiertKeineRuecksendungNoetig"),
    @XmlEnumValue("LieferscheinnummerUnbekannt")
    LIEFERSCHEINNUMMER_UNBEKANNT("LieferscheinnummerUnbekannt"),
    @XmlEnumValue("PznInLieferscheinnummerNichtEnthalten")
    PZN_IN_LIEFERSCHEINNUMMER_NICHT_ENTHALTEN("PznInLieferscheinnummerNichtEnthalten"),
    @XmlEnumValue("KeineRuecknahme")
    KEINE_RUECKNAHME("KeineRuecknahme"),
    @XmlEnumValue("RueckgabefristUeberschritten")
    RUECKGABEFRIST_UEBERSCHRITTEN("RueckgabefristUeberschritten"),
    @XmlEnumValue("MengeUeberschritten")
    MENGE_UEBERSCHRITTEN("MengeUeberschritten");
    private final String value;

    RetourenAnteilTypType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RetourenAnteilTypType fromValue(String v) {
        for (RetourenAnteilTypType c: RetourenAnteilTypType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
