
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DirectDebitTransaction10_SDD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DirectDebitTransaction10_SDD"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}DirectDebitTransaction10"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MndtRltdInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}MandateRelatedInformation14_SDD"/&gt;
 *         &lt;element name="CdtrSchmeId" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135_SDD_6" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirectDebitTransaction10_SDD")
public class DirectDebitTransaction10SDD
    extends DirectDebitTransaction10
{


}
