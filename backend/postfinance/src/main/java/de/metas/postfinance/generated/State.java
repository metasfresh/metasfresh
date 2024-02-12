
package de.metas.postfinance.generated;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for State.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="State">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Open"/>
 *     <enumeration value="Paid"/>
 *     <enumeration value="Rejected"/>
 *     <enumeration value="Processing"/>
 *     <enumeration value="Incomplete"/>
 *     <enumeration value="Invalid"/>
 *     <enumeration value="Deleted"/>
 *     <enumeration value="Unsigned"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "State", namespace = "http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic")
@XmlEnum
public enum State {

    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("Paid")
    PAID("Paid"),
    @XmlEnumValue("Rejected")
    REJECTED("Rejected"),
    @XmlEnumValue("Processing")
    PROCESSING("Processing"),
    @XmlEnumValue("Incomplete")
    INCOMPLETE("Incomplete"),
    @XmlEnumValue("Invalid")
    INVALID("Invalid"),
    @XmlEnumValue("Deleted")
    DELETED("Deleted"),
    @XmlEnumValue("Unsigned")
    UNSIGNED("Unsigned");
    private final String value;

    State(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static State fromValue(String v) {
        for (State c: State.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
