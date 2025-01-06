
package de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentMethod3Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="PaymentMethod3Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CHK"/&gt;
 *     &lt;enumeration value="TRA"/&gt;
 *     &lt;enumeration value="TRF"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PaymentMethod3Code")
@XmlEnum
public enum PaymentMethod3Code {

    CHK,
    TRA,
    TRF;

    public String value() {
        return name();
    }

    public static PaymentMethod3Code fromValue(String v) {
        return valueOf(v);
    }

}
