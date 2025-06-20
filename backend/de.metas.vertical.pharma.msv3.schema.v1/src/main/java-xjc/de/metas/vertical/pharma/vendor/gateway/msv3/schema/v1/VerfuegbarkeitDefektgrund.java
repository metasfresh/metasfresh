
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerfuegbarkeitDefektgrund.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="VerfuegbarkeitDefektgrund">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="KeineAngabe"/>
 *     <enumeration value="FehltZurzeit"/>
 *     <enumeration value="NichtLieferbar"/>
 *     <enumeration value="NurDirekt"/>
 *     <enumeration value="NichtGefuehrt"/>
 *     <enumeration value="ArtikelNrUnbekannt"/>
 *     <enumeration value="AusserHandel"/>
 *     <enumeration value="KeinBezug"/>
 *     <enumeration value="Transportausschluss"/>
 *     <enumeration value="Teildefekt"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "VerfuegbarkeitDefektgrund")
@XmlEnum
public enum VerfuegbarkeitDefektgrund {

    @XmlEnumValue("KeineAngabe")
    KEINE_ANGABE("KeineAngabe"),
    @XmlEnumValue("FehltZurzeit")
    FEHLT_ZURZEIT("FehltZurzeit"),
    @XmlEnumValue("NichtLieferbar")
    NICHT_LIEFERBAR("NichtLieferbar"),
    @XmlEnumValue("NurDirekt")
    NUR_DIREKT("NurDirekt"),
    @XmlEnumValue("NichtGefuehrt")
    NICHT_GEFUEHRT("NichtGefuehrt"),
    @XmlEnumValue("ArtikelNrUnbekannt")
    ARTIKEL_NR_UNBEKANNT("ArtikelNrUnbekannt"),
    @XmlEnumValue("AusserHandel")
    AUSSER_HANDEL("AusserHandel"),
    @XmlEnumValue("KeinBezug")
    KEIN_BEZUG("KeinBezug"),
    @XmlEnumValue("Transportausschluss")
    TRANSPORTAUSSCHLUSS("Transportausschluss"),
    @XmlEnumValue("Teildefekt")
    TEILDEFEKT("Teildefekt");
    private final String value;

    VerfuegbarkeitDefektgrund(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VerfuegbarkeitDefektgrund fromValue(String v) {
        for (VerfuegbarkeitDefektgrund c: VerfuegbarkeitDefektgrund.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
