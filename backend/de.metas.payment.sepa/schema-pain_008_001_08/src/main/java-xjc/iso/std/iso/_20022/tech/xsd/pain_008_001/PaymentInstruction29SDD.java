
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentInstruction29_SDD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentInstruction29_SDD"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentInstruction29"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PmtInfId"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text"&gt;
 *               &lt;pattern value="([A-Za-z0-9]|[\+|\?|/|\-|:|\(|\)|\.|,|'| ]){1,35}"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PmtMtd" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentMethod2Code"/&gt;
 *         &lt;element name="BtchBookg" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}BatchBookingIndicator" minOccurs="0"/&gt;
 *         &lt;element name="NbOfTxs" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max15NumericText"/&gt;
 *         &lt;element name="CtrlSum" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}DecimalNumber_SDD"/&gt;
 *         &lt;element name="PmtTpInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentTypeInformation29_SDD" minOccurs="0"/&gt;
 *         &lt;element name="ReqdColltnDt" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ISODate"/&gt;
 *         &lt;element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135_SDD_2"/&gt;
 *         &lt;element name="CdtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}CashAccount38_SDD"/&gt;
 *         &lt;element name="CdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}BranchAndFinancialInstitutionIdentification6_SDD"/&gt;
 *         &lt;element name="UltmtCdtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135_SDD_3" minOccurs="0"/&gt;
 *         &lt;element name="ChrgBr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ChargeBearerType1Code" minOccurs="0"/&gt;
 *         &lt;element name="CdtrSchmeId" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135_SDD_4" minOccurs="0"/&gt;
 *         &lt;element name="DrctDbtTxInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}DirectDebitTransactionInformation23_SDD" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentInstruction29_SDD")
public class PaymentInstruction29SDD
    extends PaymentInstruction29
{


}
