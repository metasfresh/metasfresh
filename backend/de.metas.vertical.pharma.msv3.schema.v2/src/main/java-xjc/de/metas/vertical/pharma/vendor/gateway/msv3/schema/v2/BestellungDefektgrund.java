
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BestellungDefektgrund.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="BestellungDefektgrund">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="KeineAngabe"/>
 *     <enumeration value="FehltZurzeit"/>
 *     <enumeration value="HerstellerNichtLieferbar"/>
 *     <enumeration value="NurDirekt"/>
 *     <enumeration value="NichtGefuehrt"/>
 *     <enumeration value="ArtikelNrUnbekannt"/>
 *     <enumeration value="AusserHandel"/>
 *     <enumeration value="KeinBezug"/>
 *     <enumeration value="Transportausschluss"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "BestellungDefektgrund")
@XmlEnum
public enum BestellungDefektgrund {

    @XmlEnumValue("KeineAngabe")
    KEINE_ANGABE("KeineAngabe"),
    @XmlEnumValue("FehltZurzeit")
    FEHLT_ZURZEIT("FehltZurzeit"),
    @XmlEnumValue("HerstellerNichtLieferbar")
    HERSTELLER_NICHT_LIEFERBAR("HerstellerNichtLieferbar"),
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
    TRANSPORTAUSSCHLUSS("Transportausschluss");
    private final String value;

    BestellungDefektgrund(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BestellungDefektgrund fromValue(String v) {
        for (BestellungDefektgrund c: BestellungDefektgrund.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
