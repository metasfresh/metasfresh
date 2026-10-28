
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RemittanceInformation16_SDD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemittanceInformation16_SDD"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}RemittanceInformation16"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Ustrd" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max140Text" minOccurs="0"/&gt;
 *         &lt;element name="Strd" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}StructuredRemittanceInformation16_SDD" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemittanceInformation16_SDD")
public class RemittanceInformation16SDD
    extends RemittanceInformation16
{


}
