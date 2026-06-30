
package de.metas.printing.esb.base.jaxb.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReplicationModeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReplicationModeEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="1"/&gt;
 *     &lt;enumeration value="0"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReplicationModeEnum")
@XmlEnum
public enum ReplicationModeEnum {

    @XmlEnumValue("1")
    Document("1"),
    @XmlEnumValue("0")
    Table("0");
    private final String value;

    ReplicationModeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReplicationModeEnum fromValue(String v) {
        for (ReplicationModeEnum c: ReplicationModeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
