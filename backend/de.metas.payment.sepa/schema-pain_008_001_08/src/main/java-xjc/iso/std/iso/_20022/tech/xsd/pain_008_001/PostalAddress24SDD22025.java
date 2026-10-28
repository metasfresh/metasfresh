
package iso.std.iso._20022.tech.xsd.pain_008_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostalAddress24_SDD_2_2025 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostalAddress24_SDD_2_2025"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}PostalAddress24"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Dept" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max70Text" minOccurs="0"/&gt;
 *         &lt;element name="SubDept" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max70Text" minOccurs="0"/&gt;
 *         &lt;element name="StrtNm" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max70Text" minOccurs="0"/&gt;
 *         &lt;element name="BldgNb" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max16Text" minOccurs="0"/&gt;
 *         &lt;element name="BldgNm" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="Flr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max70Text" minOccurs="0"/&gt;
 *         &lt;element name="PstBx" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max16Text" minOccurs="0"/&gt;
 *         &lt;element name="Room" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max70Text" minOccurs="0"/&gt;
 *         &lt;element name="PstCd" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max16Text" minOccurs="0"/&gt;
 *         &lt;element name="TwnNm" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text"/&gt;
 *         &lt;element name="TwnLctnNm" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="DstrctNm" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="CtrySubDvsn" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="Ctry" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}CountryCode"/&gt;
 *         &lt;element name="AdrLine" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.08}Max70Text" maxOccurs="2" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddress24_SDD_2_2025")
public class PostalAddress24SDD22025
    extends PostalAddress24
{


}
