
package de.metas.postfinance.generated;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubmissionStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="SubmissionStatus">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="NOT_ALLOWED"/>
 *     <enumeration value="ALLOWED"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "SubmissionStatus", namespace = "http://schemas.datacontract.org/2004/07/eBill.B2BServiceLib.Logic")
@XmlEnum
public enum SubmissionStatus {

    NOT_ALLOWED,
    ALLOWED;

    public String value() {
        return name();
    }

    public static SubmissionStatus fromValue(String v) {
        return valueOf(v);
    }

}
