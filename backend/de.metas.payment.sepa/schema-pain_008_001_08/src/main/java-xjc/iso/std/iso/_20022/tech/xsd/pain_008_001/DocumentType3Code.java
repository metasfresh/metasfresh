
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentType3Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DocumentType3Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SCOR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "DocumentType3Code")
@XmlEnum
public enum DocumentType3Code {


    /**
     * Document is a structured communication reference provided by the creditor to identify the referred transaction.
     * 
     */
    SCOR;

    public String value() {
        return name();
    }

    public static DocumentType3Code fromValue(String v) {
        return valueOf(v);
    }

}
