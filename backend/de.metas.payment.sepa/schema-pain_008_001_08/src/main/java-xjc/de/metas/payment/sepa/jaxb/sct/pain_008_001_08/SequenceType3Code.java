
package de.metas.payment.sepa.jaxb.sct.pain_008_001_08;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SequenceType3Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SequenceType3Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="FNAL"/&gt;
 *     &lt;enumeration value="FRST"/&gt;
 *     &lt;enumeration value="OOFF"/&gt;
 *     &lt;enumeration value="RCUR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SequenceType3Code")
@XmlEnum
public enum SequenceType3Code {


    /**
     * Final collection of a series of direct debit instructions.
     * 
     */
    FNAL,

    /**
     * First collection of a series of direct debit instructions.
     * 
     */
    FRST,

    /**
     * Direct debit instruction where the debtor's authorisation is used to initiate one single direct debit transaction.
     * 
     */
    OOFF,

    /**
     * Direct debit instruction where the debtor's authorisation is used for regular direct debit transactions initiated by the creditor.
     * 
     */
    RCUR;

    public String value() {
        return name();
    }

    public static SequenceType3Code fromValue(String v) {
        return valueOf(v);
    }

}
