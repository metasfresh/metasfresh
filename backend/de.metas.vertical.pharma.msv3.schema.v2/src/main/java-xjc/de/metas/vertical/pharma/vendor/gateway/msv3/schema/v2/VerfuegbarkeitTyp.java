
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerfuegbarkeitTyp.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="VerfuegbarkeitTyp">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Spezifisch"/>
 *     <enumeration value="Unspezifisch"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "VerfuegbarkeitTyp")
@XmlEnum
public enum VerfuegbarkeitTyp {

    @XmlEnumValue("Spezifisch")
    SPEZIFISCH("Spezifisch"),
    @XmlEnumValue("Unspezifisch")
    UNSPEZIFISCH("Unspezifisch");
    private final String value;

    VerfuegbarkeitTyp(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VerfuegbarkeitTyp fromValue(String v) {
        for (VerfuegbarkeitTyp c: VerfuegbarkeitTyp.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
