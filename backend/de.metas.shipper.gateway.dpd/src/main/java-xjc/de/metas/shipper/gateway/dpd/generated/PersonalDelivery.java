
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Bundles personal delivery data.
 * 
 * <p>Java class for personalDelivery complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="personalDelivery">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="type">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <minInclusive value="1"/>
 *               <maxInclusive value="5"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="floor" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="30"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="building" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="30"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="department" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="30"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="name" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="phone" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="20"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="personId" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="35"/>
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
@XmlType(name = "personalDelivery", propOrder = {
    "type",
    "floor",
    "building",
    "department",
    "name",
    "phone",
    "personId"
})
public class PersonalDelivery {

    /**
     * Defines type of personal delivery. Possible values are:
     *  1 = department delivery (without personal identification)
     *  2 = personal delivery with personal identification (ID-Check)
     *  3 = personal delivery without personal identification at drop point (e.g. parcel shop)
     *  4 = personal delivery with personal identification at drop point (e.g. parcel shop)
     *  5 = personal delivery with personal identification at drop point plus ID-Check (e.g. parcel shop)
     * 
     * For parcel shop delivery the parcel shop id must be declared in productAndServiceData. It can be obtained from parcel shop finder.
     * 
     */
    protected int type;
    /**
     * Floor where the personal delivery shall take place. This field is only used for department delivery.
     * 
     */
    protected String floor;
    /**
     * Building where the personal delivery shall take place. This field is only used for department delivery (type 1).
     * 
     */
    protected String building;
    /**
     * Department where the personal delivery shall take place. This field is only used for department delivery (type 1).
     * 
     */
    protected String department;
    /**
     * Name of the person authorised to accept the consignment. This field is only used for delivery with ID-Check (types 2 and 4).
     * 
     */
    protected String name;
    /**
     * Telephone number of the person authorised to accept the consignment. This field is only used for delivery with ID-Check (types 2 and 4). No required data format.
     * 
     */
    protected String phone;
    /**
     * Personal identification number of the person authorised to accept the consignment. This field is only used for delivery with ID-Check (types 2 and 4).
     * 
     */
    protected String personId;

    /**
     * Defines type of personal delivery. Possible values are:
     *  1 = department delivery (without personal identification)
     *  2 = personal delivery with personal identification (ID-Check)
     *  3 = personal delivery without personal identification at drop point (e.g. parcel shop)
     *  4 = personal delivery with personal identification at drop point (e.g. parcel shop)
     *  5 = personal delivery with personal identification at drop point plus ID-Check (e.g. parcel shop)
     * 
     * For parcel shop delivery the parcel shop id must be declared in productAndServiceData. It can be obtained from parcel shop finder.
     * 
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     */
    public void setType(int value) {
        this.type = value;
    }

    /**
     * Floor where the personal delivery shall take place. This field is only used for department delivery.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the value of the floor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getFloor()
     */
    public void setFloor(String value) {
        this.floor = value;
    }

    /**
     * Building where the personal delivery shall take place. This field is only used for department delivery (type 1).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Sets the value of the building property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBuilding()
     */
    public void setBuilding(String value) {
        this.building = value;
    }

    /**
     * Department where the personal delivery shall take place. This field is only used for department delivery (type 1).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the value of the department property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDepartment()
     */
    public void setDepartment(String value) {
        this.department = value;
    }

    /**
     * Name of the person authorised to accept the consignment. This field is only used for delivery with ID-Check (types 2 and 4).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getName()
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Telephone number of the person authorised to accept the consignment. This field is only used for delivery with ID-Check (types 2 and 4). No required data format.
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
     * Personal identification number of the person authorised to accept the consignment. This field is only used for delivery with ID-Check (types 2 and 4).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Sets the value of the personId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPersonId()
     */
    public void setPersonId(String value) {
        this.personId = value;
    }

}
