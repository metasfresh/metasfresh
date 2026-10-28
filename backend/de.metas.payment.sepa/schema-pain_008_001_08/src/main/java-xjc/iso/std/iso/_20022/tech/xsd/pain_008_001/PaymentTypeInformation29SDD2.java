
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentTypeInformation29_SDD_2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentTypeInformation29_SDD_2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentTypeInformation29"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SvcLvl" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ServiceLevel8Choice_SDD"/&gt;
 *         &lt;element name="LclInstrm" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}LocalInstrument2Choice_SDD_2"/&gt;
 *         &lt;element name="SeqTp" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}SequenceType3Code"/&gt;
 *         &lt;element name="CtgyPurp" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}CategoryPurpose1Choice_SDD" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTypeInformation29_SDD_2")
public class PaymentTypeInformation29SDD2
    extends PaymentTypeInformation29
{


}
