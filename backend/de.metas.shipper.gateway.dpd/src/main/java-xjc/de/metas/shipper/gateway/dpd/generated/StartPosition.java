
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Start positions
 * 
 * <p>Java class for startPosition</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="startPosition">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="UPPER_LEFT"/>
 *     <enumeration value="UPPER_RIGHT"/>
 *     <enumeration value="LOWER_LEFT"/>
 *     <enumeration value="LOWER_RIGHT"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "startPosition")
@XmlEnum
public enum StartPosition {


    /**
     * Print first label in the upper left corner of the A4 paper.
     * 
     */
    UPPER_LEFT,

    /**
     * Print first label in the upper right corner of the A4 paper.
     * 
     */
    UPPER_RIGHT,

    /**
     * Print first label in the lower left corner of the A4 paper.
     * 
     */
    LOWER_LEFT,

    /**
     * Print first label in the lower right corner of the A4 paper.
     * 
     */
    LOWER_RIGHT;

    public String value() {
        return name();
    }

    public static StartPosition fromValue(String v) {
        return valueOf(v);
    }

}
