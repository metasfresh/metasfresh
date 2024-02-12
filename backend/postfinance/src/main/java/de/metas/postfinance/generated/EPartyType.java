
package de.metas.postfinance.generated;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EPartyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="EPartyType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="PRIVATE"/>
 *     <enumeration value="COMPANY"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "EPartyType", namespace = "http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic")
@XmlEnum
public enum EPartyType {

    PRIVATE,
    COMPANY;

    public String value() {
        return name();
    }

    public static EPartyType fromValue(String v) {
        return valueOf(v);
    }

}
