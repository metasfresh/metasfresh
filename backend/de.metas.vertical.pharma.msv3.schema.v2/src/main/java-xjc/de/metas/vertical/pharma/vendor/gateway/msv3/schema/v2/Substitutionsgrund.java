
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Substitutionsgrund.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="Substitutionsgrund">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Nachfolgeprodukt"/>
 *     <enumeration value="ReUndParallelImport"/>
 *     <enumeration value="Vorschlag"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "Substitutionsgrund")
@XmlEnum
public enum Substitutionsgrund {

    @XmlEnumValue("Nachfolgeprodukt")
    NACHFOLGEPRODUKT("Nachfolgeprodukt"),
    @XmlEnumValue("ReUndParallelImport")
    RE_UND_PARALLEL_IMPORT("ReUndParallelImport"),
    @XmlEnumValue("Vorschlag")
    VORSCHLAG("Vorschlag");
    private final String value;

    Substitutionsgrund(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Substitutionsgrund fromValue(String v) {
        for (Substitutionsgrund c: Substitutionsgrund.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
