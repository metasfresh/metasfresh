
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Auftragsart.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="Auftragsart">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="NORMAL"/>
 *     <enumeration value="STAPEL"/>
 *     <enumeration value="SONDER"/>
 *     <enumeration value="VERSAND"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "Auftragsart")
@XmlEnum
public enum Auftragsart {

    NORMAL,
    STAPEL,
    SONDER,
    VERSAND;

    public String value() {
        return name();
    }

    public static Auftragsart fromValue(String v) {
        return valueOf(v);
    }

}
