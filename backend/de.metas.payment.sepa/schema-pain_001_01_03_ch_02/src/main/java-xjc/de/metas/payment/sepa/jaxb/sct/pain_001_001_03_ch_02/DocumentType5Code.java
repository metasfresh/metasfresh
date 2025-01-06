
package de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentType5Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="DocumentType5Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="AROI"/&gt;
 *     &lt;enumeration value="BOLD"/&gt;
 *     &lt;enumeration value="CINV"/&gt;
 *     &lt;enumeration value="CMCN"/&gt;
 *     &lt;enumeration value="CNFA"/&gt;
 *     &lt;enumeration value="CREN"/&gt;
 *     &lt;enumeration value="DEBN"/&gt;
 *     &lt;enumeration value="DISP"/&gt;
 *     &lt;enumeration value="DNFA"/&gt;
 *     &lt;enumeration value="HIRI"/&gt;
 *     &lt;enumeration value="MSIN"/&gt;
 *     &lt;enumeration value="SBIN"/&gt;
 *     &lt;enumeration value="SOAC"/&gt;
 *     &lt;enumeration value="TSUT"/&gt;
 *     &lt;enumeration value="VCHR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "DocumentType5Code")
@XmlEnum
public enum DocumentType5Code {

    AROI,
    BOLD,
    CINV,
    CMCN,
    CNFA,
    CREN,
    DEBN,
    DISP,
    DNFA,
    HIRI,
    MSIN,
    SBIN,
    SOAC,
    TSUT,
    VCHR;

    public String value() {
        return name();
    }

    public static DocumentType5Code fromValue(String v) {
        return valueOf(v);
    }

}
