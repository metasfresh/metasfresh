
package de.metas.payment.sepa.jaxb.sct.pain_008_003_02;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OthrIdentificationCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="OthrIdentificationCode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NOTPROVIDED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "OthrIdentificationCode")
@XmlEnum
public enum OthrIdentificationCode {

    NOTPROVIDED;

    public String value() {
        return name();
    }

    public static OthrIdentificationCode fromValue(String v) {
        return valueOf(v);
    }

}
