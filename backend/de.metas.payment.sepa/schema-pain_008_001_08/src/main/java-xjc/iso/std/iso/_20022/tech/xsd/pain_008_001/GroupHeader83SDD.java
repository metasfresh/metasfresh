
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroupHeader83_SDD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroupHeader83_SDD"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}GroupHeader83"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MsgId"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text"&gt;
 *               &lt;pattern value="([A-Za-z0-9]|[\+|\?|/|\-|:|\(|\)|\.|,|'| ]){1,35}"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="CreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ISODateTime"/&gt;
 *         &lt;element name="NbOfTxs" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max15NumericText"/&gt;
 *         &lt;element name="CtrlSum" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}DecimalNumber_SDD"/&gt;
 *         &lt;element name="InitgPty" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135_SDD"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupHeader83_SDD")
public class GroupHeader83SDD
    extends GroupHeader83
{


}
