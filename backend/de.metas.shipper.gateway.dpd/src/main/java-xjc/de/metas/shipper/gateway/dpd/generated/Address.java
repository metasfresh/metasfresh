
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for address complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="address">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="name1">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="name2" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="street">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="houseNo" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="8"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="state" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="2"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="country">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="2"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="zipCode">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="9"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="city">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="gln" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *               <maxInclusive value="9999999999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="customerNumber" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="17"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="contact" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="phone" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="30"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="fax" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="30"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="email" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="comment" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="70"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="iaccount" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "address", propOrder = {
    "name1",
    "name2",
    "street",
    "houseNo",
    "state",
    "country",
    "zipCode",
    "city",
    "gln",
    "customerNumber",
    "contact",
    "phone",
    "fax",
    "email",
    "comment",
    "iaccount"
})
public class Address {

    /**
     * Name of address owner. For dangerous goods the maximum length is 50, otherwise always 35.
     * 
     */
    @XmlElement(required = true)
    protected String name1;
    /**
     * Name 2 of address owner.
     * 
     */
    protected String name2;
    /**
     * Street of address owner. For dangerous goods the maximum length is 50, otherwise always 35.
     * 
     */
    @XmlElement(required = true)
    protected String street;
    /**
     * House number of address owner. Maximum length is 8.
     * 
     */
    protected String houseNo;
    /**
     * State of address owner in ISO 3166-2 code (e.g. BY = Bayern).
     * 
     */
    protected String state;
    /**
     * Country of address owner in ISO 3166-1 alpha-2 format (e.g. 'DE').
     * 
     */
    @XmlElement(required = true)
    protected String country;
    /**
     * Zip code of address owner.
     * 
     */
    @XmlElement(required = true)
    protected String zipCode;
    /**
     * City/town of address owner. For dangerous goods the maximum length is 50, otherwise always 35.
     * 
     */
    @XmlElement(required = true)
    protected String city;
    /**
     * International location number of address owner.
     * 
     */
    protected Long gln;
    /**
     * Customer number of address owner. Maximum length is 17 for consignment and pickup information, 11 for collection request. Mandatory for sender's address.
     * 
     */
    protected String customerNumber;
    /**
     * Contact person of address owner.
     * 
     */
    protected String contact;
    /**
     * Phone number of address owner. Mandatory if phone is the chosen notification channel. No required data format.
     * 
     */
    protected String phone;
    /**
     * Fax number of address owner. No required data format.
     * 
     */
    protected String fax;
    /**
     * Email address of address owner. For collection requests the maximum length is 40, otherwise always 50.
     * 
     */
    protected String email;
    /**
     * Comment on address owner.
     * 
     */
    protected String comment;
    /**
     * Account allocation or cost center (for VTG) of invoice data for consignments.
     * 
     */
    protected String iaccount;

    /**
     * Name of address owner. For dangerous goods the maximum length is 50, otherwise always 35.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName1() {
        return name1;
    }

    /**
     * Sets the value of the name1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getName1()
     */
    public void setName1(String value) {
        this.name1 = value;
    }

    /**
     * Name 2 of address owner.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName2() {
        return name2;
    }

    /**
     * Sets the value of the name2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getName2()
     */
    public void setName2(String value) {
        this.name2 = value;
    }

    /**
     * Street of address owner. For dangerous goods the maximum length is 50, otherwise always 35.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getStreet()
     */
    public void setStreet(String value) {
        this.street = value;
    }

    /**
     * House number of address owner. Maximum length is 8.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseNo() {
        return houseNo;
    }

    /**
     * Sets the value of the houseNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getHouseNo()
     */
    public void setHouseNo(String value) {
        this.houseNo = value;
    }

    /**
     * State of address owner in ISO 3166-2 code (e.g. BY = Bayern).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getState()
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Country of address owner in ISO 3166-1 alpha-2 format (e.g. 'DE').
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCountry()
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Zip code of address owner.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the value of the zipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getZipCode()
     */
    public void setZipCode(String value) {
        this.zipCode = value;
    }

    /**
     * City/town of address owner. For dangerous goods the maximum length is 50, otherwise always 35.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCity()
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * International location number of address owner.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getGln() {
        return gln;
    }

    /**
     * Sets the value of the gln property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     * @see #getGln()
     */
    public void setGln(Long value) {
        this.gln = value;
    }

    /**
     * Customer number of address owner. Maximum length is 17 for consignment and pickup information, 11 for collection request. Mandatory for sender's address.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     * Sets the value of the customerNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomerNumber()
     */
    public void setCustomerNumber(String value) {
        this.customerNumber = value;
    }

    /**
     * Contact person of address owner.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getContact()
     */
    public void setContact(String value) {
        this.contact = value;
    }

    /**
     * Phone number of address owner. Mandatory if phone is the chosen notification channel. No required data format.
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
     * @see #getPhone()
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Fax number of address owner. No required data format.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getFax()
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Email address of address owner. For collection requests the maximum length is 40, otherwise always 50.
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
     * @see #getEmail()
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Comment on address owner.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getComment()
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Account allocation or cost center (for VTG) of invoice data for consignments.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIaccount() {
        return iaccount;
    }

    /**
     * Sets the value of the iaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getIaccount()
     */
    public void setIaccount(String value) {
        this.iaccount = value;
    }

}
