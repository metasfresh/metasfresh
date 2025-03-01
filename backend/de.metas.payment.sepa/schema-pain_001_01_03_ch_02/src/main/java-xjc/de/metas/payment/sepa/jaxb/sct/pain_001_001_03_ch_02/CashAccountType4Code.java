
package de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CashAccountType4Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="CashAccountType4Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CACC"/&gt;
 *     &lt;enumeration value="CASH"/&gt;
 *     &lt;enumeration value="CHAR"/&gt;
 *     &lt;enumeration value="CISH"/&gt;
 *     &lt;enumeration value="COMM"/&gt;
 *     &lt;enumeration value="LOAN"/&gt;
 *     &lt;enumeration value="MGLD"/&gt;
 *     &lt;enumeration value="MOMA"/&gt;
 *     &lt;enumeration value="NREX"/&gt;
 *     &lt;enumeration value="ODFT"/&gt;
 *     &lt;enumeration value="ONDP"/&gt;
 *     &lt;enumeration value="SACC"/&gt;
 *     &lt;enumeration value="SLRY"/&gt;
 *     &lt;enumeration value="SVGS"/&gt;
 *     &lt;enumeration value="TAXE"/&gt;
 *     &lt;enumeration value="TRAS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CashAccountType4Code")
@XmlEnum
public enum CashAccountType4Code {

    CACC,
    CASH,
    CHAR,
    CISH,
    COMM,
    LOAN,
    MGLD,
    MOMA,
    NREX,
    ODFT,
    ONDP,
    SACC,
    SLRY,
    SVGS,
    TAXE,
    TRAS;

    public String value() {
        return name();
    }

    public static CashAccountType4Code fromValue(String v) {
        return valueOf(v);
    }

}
