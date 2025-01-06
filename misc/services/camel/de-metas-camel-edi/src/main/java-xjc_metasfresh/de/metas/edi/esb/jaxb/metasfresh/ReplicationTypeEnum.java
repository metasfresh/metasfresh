
package de.metas.edi.esb.jaxb.metasfresh;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReplicationTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ReplicationTypeEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="L"/&gt;
 *     &lt;enumeration value="M"/&gt;
 *     &lt;enumeration value="R"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReplicationTypeEnum")
@XmlEnum
public enum ReplicationTypeEnum {

    @XmlEnumValue("L")
    Local("L"),
    @XmlEnumValue("M")
    Merge("M"),
    @XmlEnumValue("R")
    Reference("R");
    private final String value;

    ReplicationTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReplicationTypeEnum fromValue(String v) {
        for (ReplicationTypeEnum c: ReplicationTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
