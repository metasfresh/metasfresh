
package at.erpel.schemas._1p0.documents.extensions.edifact;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessEntityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessEntityType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}BusinessEntityTypeQualifier"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}VATIdentificationNumber" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}FurtherIdentification" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}DocumentReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Phone" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Email" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}GLN" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Contact" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/extensions/edifact}Address" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessEntityType", propOrder = {
    "businessEntityTypeQualifier",
    "vatIdentificationNumber",
    "furtherIdentification",
    "documentReference",
    "phone",
    "email",
    "gln",
    "contact",
    "address"
})
public class BusinessEntityType {

    @XmlElement(name = "BusinessEntityTypeQualifier", required = true)
    protected String businessEntityTypeQualifier;
    @XmlElement(name = "VATIdentificationNumber")
    protected String vatIdentificationNumber;
    @XmlElement(name = "FurtherIdentification")
    protected List<FurtherIdentificationType> furtherIdentification;
    @XmlElement(name = "DocumentReference")
    protected List<DocumentReferenceType> documentReference;
    @XmlElement(name = "Phone")
    protected String phone;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "GLN")
    protected String gln;
    @XmlElement(name = "Contact")
    protected ContactType contact;
    @XmlElement(name = "Address")
    protected AddressType address;

    /**
     * A two-digit qualifier, identifying the type of business entity, e.g., UC for ultimate consignee. Codes according to UN/CEFACT 3035 (party function code qualifer) shall be used. Cf. http://www.unece.org/trade/untdid/d01b/tred/tred3035.htm 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessEntityTypeQualifier() {
        return businessEntityTypeQualifier;
    }

    /**
     * Sets the value of the businessEntityTypeQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessEntityTypeQualifier(String value) {
        this.businessEntityTypeQualifier = value;
    }

    /**
     * The VAT identification number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVATIdentificationNumber() {
        return vatIdentificationNumber;
    }

    /**
     * Sets the value of the vatIdentificationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVATIdentificationNumber(String value) {
        this.vatIdentificationNumber = value;
    }

    /**
     * Additional identification used to uniquely identify the party.Gets the value of the furtherIdentification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the furtherIdentification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFurtherIdentification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FurtherIdentificationType }
     * 
     * 
     */
    public List<FurtherIdentificationType> getFurtherIdentification() {
        if (furtherIdentification == null) {
            furtherIdentification = new ArrayList<FurtherIdentificationType>();
        }
        return this.furtherIdentification;
    }

    /**
     * References to documents from the given party.Gets the value of the documentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getDocumentReference() {
        if (documentReference == null) {
            documentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.documentReference;
    }

    /**
     * Deprecated. Please supply the phone number in Contact/Phone
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Deprecated. Please supply the email address in Contact/Email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Global Location Number of the party.
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

    /**
     * Further contact details.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setContact(ContactType value) {
        this.contact = value;
    }

    /**
     * Address details of the party.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

}
