
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Bestellstatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="Bestellstatus">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="KennungUnbekannt"/>
 *     <enumeration value="BestellantwortNichtVerfuegbar"/>
 *     <enumeration value="BestellantwortVerfuegbar"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "Bestellstatus")
@XmlEnum
public enum Bestellstatus {

    @XmlEnumValue("KennungUnbekannt")
    KENNUNG_UNBEKANNT("KennungUnbekannt"),
    @XmlEnumValue("BestellantwortNichtVerfuegbar")
    BESTELLANTWORT_NICHT_VERFUEGBAR("BestellantwortNichtVerfuegbar"),
    @XmlEnumValue("BestellantwortVerfuegbar")
    BESTELLANTWORT_VERFUEGBAR("BestellantwortVerfuegbar");
    private final String value;

    Bestellstatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Bestellstatus fromValue(String v) {
        for (Bestellstatus c: Bestellstatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
