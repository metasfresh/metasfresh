
package de.metas.payment.sepa.jaxb.sct.pain_008_001_08;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FinancialInstitutionIdentification18_SDD_2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FinancialInstitutionIdentification18_SDD_2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}FinancialInstitutionIdentification18"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="BICFI" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}BICFIDec2014Identifier"/&gt;
 *         &lt;element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}GenericFinancialIdentification1_SDD_2"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FinancialInstitutionIdentification18_SDD_2")
public class FinancialInstitutionIdentification18SDD2
    extends FinancialInstitutionIdentification18
{


}
