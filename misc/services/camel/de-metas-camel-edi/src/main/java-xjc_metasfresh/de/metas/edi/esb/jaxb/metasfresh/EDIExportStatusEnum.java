
package de.metas.edi.esb.jaxb.metasfresh;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EDI_ExportStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="EDI_ExportStatusEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="P"/&gt;
 *     &lt;enumeration value="S"/&gt;
 *     &lt;enumeration value="D"/&gt;
 *     &lt;enumeration value="E"/&gt;
 *     &lt;enumeration value="U"/&gt;
 *     &lt;enumeration value="I"/&gt;
 *     &lt;enumeration value="N"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EDI_ExportStatusEnum")
@XmlEnum
public enum EDIExportStatusEnum {

    @XmlEnumValue("P")
    Pending("P"),
    @XmlEnumValue("S")
    Sent("S"),
    @XmlEnumValue("D")
    SendingStarted("D"),
    @XmlEnumValue("E")
    Error("E"),
    @XmlEnumValue("U")
    Enqueued("U"),
    @XmlEnumValue("I")
    Invalid("I"),
    @XmlEnumValue("N")
    DontSend("N");
    private final String value;

    EDIExportStatusEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EDIExportStatusEnum fromValue(String v) {
        for (EDIExportStatusEnum c: EDIExportStatusEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
