
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentMethod2Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentMethod2Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="DD"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PaymentMethod2Code")
@XmlEnum
public enum PaymentMethod2Code {


    /**
     * Collection of an amount of money from the debtor's bank account by the creditor. The amount of money and dates of collections may vary.
     * 
     */
    DD;

    public String value() {
        return name();
    }

    public static PaymentMethod2Code fromValue(String v) {
        return valueOf(v);
    }

}
