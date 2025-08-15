
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Is created when a user logs in and contains its login information.
 * 
 * <p>Java class for Login complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="Login">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="delisId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="customerUid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="authToken" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="depot" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Login", namespace = "http://dpd.com/common/service/types/LoginService/2.0", propOrder = {
    "delisId",
    "customerUid",
    "authToken",
    "depot"
})
public class Login {

    /**
     * The user's DELIS-Id.
     * 
     */
    @XmlElement(required = true)
    protected String delisId;
    /**
     * The user's customer uid. This is needed for subaccounts, usually this is equal to DELIS-Id
     * 
     */
    @XmlElement(required = true)
    protected String customerUid;
    /**
     * The Authtoken, needed for other web service calls.
     * 
     */
    @XmlElement(required = true)
    protected String authToken;
    /**
     * The depot, to which the user is assigned.
     * 
     */
    @XmlElement(required = true)
    protected String depot;

    /**
     * The user's DELIS-Id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelisId() {
        return delisId;
    }

    /**
     * Sets the value of the delisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDelisId()
     */
    public void setDelisId(String value) {
        this.delisId = value;
    }

    /**
     * The user's customer uid. This is needed for subaccounts, usually this is equal to DELIS-Id
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerUid() {
        return customerUid;
    }

    /**
     * Sets the value of the customerUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCustomerUid()
     */
    public void setCustomerUid(String value) {
        this.customerUid = value;
    }

    /**
     * The Authtoken, needed for other web service calls.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the value of the authToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getAuthToken()
     */
    public void setAuthToken(String value) {
        this.authToken = value;
    }

    /**
     * The depot, to which the user is assigned.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepot() {
        return depot;
    }

    /**
     * Sets the value of the depot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDepot()
     */
    public void setDepot(String value) {
        this.depot = value;
    }

}
