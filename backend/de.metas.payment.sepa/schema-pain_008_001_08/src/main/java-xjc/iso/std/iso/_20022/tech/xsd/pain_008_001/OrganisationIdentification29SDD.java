
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrganisationIdentification29_SDD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrganisationIdentification29_SDD"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}OrganisationIdentification29"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AnyBIC" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}AnyBICDec2014Identifier" minOccurs="0"/&gt;
 *         &lt;element name="LEI" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}LEIIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}GenericOrganisationIdentification1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganisationIdentification29_SDD")
public class OrganisationIdentification29SDD
    extends OrganisationIdentification29
{


}
