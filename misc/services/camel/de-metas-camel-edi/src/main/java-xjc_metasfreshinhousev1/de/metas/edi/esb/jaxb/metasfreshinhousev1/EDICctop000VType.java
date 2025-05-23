//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.03.25 at 03:18:04 PM EET 
//


package de.metas.edi.esb.jaxb.metasfreshinhousev1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;


/**
 * <p>Java class for EDI_cctop_000_vType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EDI_cctop_000_vType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="C_BPartner_Location_ID" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="EDI_cctop_000_v_ID" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="GLN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EDI_cctop_000_vType", propOrder = {
    "cbPartnerLocationID",
    "ediCctop000VID",
    "gln"
})
public class EDICctop000VType {

    @XmlElement(name = "C_BPartner_Location_ID")
    protected BigInteger cbPartnerLocationID;
    @XmlElement(name = "EDI_cctop_000_v_ID")
    protected BigInteger ediCctop000VID;
    @XmlElement(name = "GLN")
    protected String gln;

    /**
     * Gets the value of the cbPartnerLocationID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCBPartnerLocationID() {
        return cbPartnerLocationID;
    }

    /**
     * Sets the value of the cbPartnerLocationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCBPartnerLocationID(BigInteger value) {
        this.cbPartnerLocationID = value;
    }

    /**
     * Gets the value of the ediCctop000VID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEDICctop000VID() {
        return ediCctop000VID;
    }

    /**
     * Sets the value of the ediCctop000VID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEDICctop000VID(BigInteger value) {
        this.ediCctop000VID = value;
    }

    /**
     * Gets the value of the gln property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGLN() {
        return gln;
    }

    /**
     * Sets the value of the gln property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGLN(String value) {
        this.gln = value;
    }

}
