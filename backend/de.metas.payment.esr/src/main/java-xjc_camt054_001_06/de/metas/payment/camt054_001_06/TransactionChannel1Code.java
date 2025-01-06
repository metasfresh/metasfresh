
package de.metas.payment.camt054_001_06;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionChannel1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="TransactionChannel1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MAIL"/&gt;
 *     &lt;enumeration value="TLPH"/&gt;
 *     &lt;enumeration value="ECOM"/&gt;
 *     &lt;enumeration value="TVPY"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TransactionChannel1Code")
@XmlEnum
public enum TransactionChannel1Code {

    MAIL,
    TLPH,
    ECOM,
    TVPY;

    public String value() {
        return name();
    }

    public static TransactionChannel1Code fromValue(String v) {
        return valueOf(v);
    }

}
