
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerfuegbarkeitRueckmeldungTyp.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="VerfuegbarkeitRueckmeldungTyp">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Normal"/>
 *     <enumeration value="Verbund"/>
 *     <enumeration value="Nachlieferung"/>
 *     <enumeration value="Dispo"/>
 *     <enumeration value="NichtLieferbar"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "VerfuegbarkeitRueckmeldungTyp")
@XmlEnum
public enum VerfuegbarkeitRueckmeldungTyp {

    @XmlEnumValue("Normal")
    NORMAL("Normal"),
    @XmlEnumValue("Verbund")
    VERBUND("Verbund"),
    @XmlEnumValue("Nachlieferung")
    NACHLIEFERUNG("Nachlieferung"),
    @XmlEnumValue("Dispo")
    DISPO("Dispo"),
    @XmlEnumValue("NichtLieferbar")
    NICHT_LIEFERBAR("NichtLieferbar");
    private final String value;

    VerfuegbarkeitRueckmeldungTyp(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VerfuegbarkeitRueckmeldungTyp fromValue(String v) {
        for (VerfuegbarkeitRueckmeldungTyp c: VerfuegbarkeitRueckmeldungTyp.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
