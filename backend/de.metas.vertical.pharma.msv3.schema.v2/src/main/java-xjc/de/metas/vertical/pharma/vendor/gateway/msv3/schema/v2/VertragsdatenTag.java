
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VertragsdatenTag.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="VertragsdatenTag">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Mo"/>
 *     <enumeration value="Di"/>
 *     <enumeration value="Mi"/>
 *     <enumeration value="Do"/>
 *     <enumeration value="Fr"/>
 *     <enumeration value="Sa"/>
 *     <enumeration value="So"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "VertragsdatenTag")
@XmlEnum
public enum VertragsdatenTag {

    @XmlEnumValue("Mo")
    MO("Mo"),
    @XmlEnumValue("Di")
    DI("Di"),
    @XmlEnumValue("Mi")
    MI("Mi"),
    @XmlEnumValue("Do")
    DO("Do"),
    @XmlEnumValue("Fr")
    FR("Fr"),
    @XmlEnumValue("Sa")
    SA("Sa"),
    @XmlEnumValue("So")
    SO("So");
    private final String value;

    VertragsdatenTag(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VertragsdatenTag fromValue(String v) {
        for (VertragsdatenTag c: VertragsdatenTag.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
