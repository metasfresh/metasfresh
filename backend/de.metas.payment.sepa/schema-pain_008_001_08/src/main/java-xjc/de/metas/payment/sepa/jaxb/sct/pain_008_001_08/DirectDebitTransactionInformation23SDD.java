
package de.metas.payment.sepa.jaxb.sct.pain_008_001_08;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DirectDebitTransactionInformation23_SDD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DirectDebitTransactionInformation23_SDD"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}DirectDebitTransactionInformation23"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PmtId" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentIdentification6_SDD"/&gt;
 *         &lt;element name="PmtTpInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PaymentTypeInformation29_SDD_2" minOccurs="0"/&gt;
 *         &lt;element name="InstdAmt" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ActiveOrHistoricCurrencyAndAmount_SDD"/&gt;
 *         &lt;element name="ChrgBr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}ChargeBearerType1Code" minOccurs="0"/&gt;
 *         &lt;element name="DrctDbtTx" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}DirectDebitTransaction10_SDD"/&gt;
 *         &lt;element name="UltmtCdtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135_SDD_3" minOccurs="0"/&gt;
 *         &lt;element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}BranchAndFinancialInstitutionIdentification6_SDD_2"/&gt;
 *         &lt;element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135_SDD_7"/&gt;
 *         &lt;element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}CashAccount38_SDD_3"/&gt;
 *         &lt;element name="UltmtDbtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PartyIdentification135_SDD_8" minOccurs="0"/&gt;
 *         &lt;element name="Purp" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Purpose2Choice_SDD" minOccurs="0"/&gt;
 *         &lt;element name="RmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}RemittanceInformation16_SDD" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirectDebitTransactionInformation23_SDD")
public class DirectDebitTransactionInformation23SDD
    extends DirectDebitTransactionInformation23
{


}
