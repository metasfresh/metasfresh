
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentIdentification6_SDD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentIdentification6_SDD"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentIdentification6"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InstrId" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text"&gt;
 *               &lt;pattern value="([A-Za-z0-9]|[\+|\?|/|\-|:|\(|\)|\.|,|'| ]){1,35}"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="EndToEndId"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text"&gt;
 *               &lt;pattern value="([A-Za-z0-9]|[\+|\?|/|\-|:|\(|\)|\.|,|'| ]){1,35}"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentIdentification6_SDD")
public class PaymentIdentification6SDD
    extends PaymentIdentification6
{


}
