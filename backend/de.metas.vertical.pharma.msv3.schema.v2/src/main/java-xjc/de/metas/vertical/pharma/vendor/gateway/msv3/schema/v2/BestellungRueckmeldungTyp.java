
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BestellungRueckmeldungTyp.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="BestellungRueckmeldungTyp">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Normal"/>
 *     <enumeration value="Verbund"/>
 *     <enumeration value="Nachlieferung"/>
 *     <enumeration value="Dispo"/>
 *     <enumeration value="KeineLieferungAberNormalMoeglich"/>
 *     <enumeration value="KeineLieferungAberVerbundMoeglich"/>
 *     <enumeration value="KeineLieferungAberNachlieferungMoeglich"/>
 *     <enumeration value="KeineLieferungAberDispoMoeglich"/>
 *     <enumeration value="NichtLieferbar"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "BestellungRueckmeldungTyp")
@XmlEnum
public enum BestellungRueckmeldungTyp {

    @XmlEnumValue("Normal")
    NORMAL("Normal"),
    @XmlEnumValue("Verbund")
    VERBUND("Verbund"),
    @XmlEnumValue("Nachlieferung")
    NACHLIEFERUNG("Nachlieferung"),
    @XmlEnumValue("Dispo")
    DISPO("Dispo"),
    @XmlEnumValue("KeineLieferungAberNormalMoeglich")
    KEINE_LIEFERUNG_ABER_NORMAL_MOEGLICH("KeineLieferungAberNormalMoeglich"),
    @XmlEnumValue("KeineLieferungAberVerbundMoeglich")
    KEINE_LIEFERUNG_ABER_VERBUND_MOEGLICH("KeineLieferungAberVerbundMoeglich"),
    @XmlEnumValue("KeineLieferungAberNachlieferungMoeglich")
    KEINE_LIEFERUNG_ABER_NACHLIEFERUNG_MOEGLICH("KeineLieferungAberNachlieferungMoeglich"),
    @XmlEnumValue("KeineLieferungAberDispoMoeglich")
    KEINE_LIEFERUNG_ABER_DISPO_MOEGLICH("KeineLieferungAberDispoMoeglich"),
    @XmlEnumValue("NichtLieferbar")
    NICHT_LIEFERBAR("NichtLieferbar");
    private final String value;

    BestellungRueckmeldungTyp(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BestellungRueckmeldungTyp fromValue(String v) {
        for (BestellungRueckmeldungTyp c: BestellungRueckmeldungTyp.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
