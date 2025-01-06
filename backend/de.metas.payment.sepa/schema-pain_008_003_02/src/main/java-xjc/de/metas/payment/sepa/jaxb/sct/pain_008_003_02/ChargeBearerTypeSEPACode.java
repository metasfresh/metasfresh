
package de.metas.payment.sepa.jaxb.sct.pain_008_003_02;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChargeBearerTypeSEPACode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ChargeBearerTypeSEPACode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SLEV"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ChargeBearerTypeSEPACode")
@XmlEnum
public enum ChargeBearerTypeSEPACode {

    SLEV;

    public String value() {
        return name();
    }

    public static ChargeBearerTypeSEPACode fromValue(String v) {
        return valueOf(v);
    }

}
