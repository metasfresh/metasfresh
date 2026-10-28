
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonIdentificationSchemeName1Choice_SDD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonIdentificationSchemeName1Choice_SDD"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PersonIdentificationSchemeName1Choice"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Prtry"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text"&gt;
 *               &lt;enumeration value="SEPA"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonIdentificationSchemeName1Choice_SDD")
public class PersonIdentificationSchemeName1ChoiceSDD
    extends PersonIdentificationSchemeName1Choice
{


}
