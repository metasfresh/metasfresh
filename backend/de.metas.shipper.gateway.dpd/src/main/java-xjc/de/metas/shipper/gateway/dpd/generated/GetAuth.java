
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getAuth complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="getAuth">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="delisId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="messageLanguage">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="5"/>
 *               <maxLength value="5"/>
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
@XmlType(name = "getAuth", namespace = "http://dpd.com/common/service/types/LoginService/2.0", propOrder = {
    "delisId",
    "password",
    "messageLanguage"
})
public class GetAuth {

    /**
     * The DELIS-Id of the user.
     * 
     */
    @XmlElement(required = true)
    protected String delisId;
    /**
     * The password of the user.
     * 
     */
    @XmlElement(required = true)
    protected String password;
    /**
     * The language (Java format) for messages.
     * "de_DE" for german messages.
     * "en_US" for english messages.
     * 
     */
    @XmlElement(required = true)
    protected String messageLanguage;

    /**
     * The DELIS-Id of the user.
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
     * The password of the user.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPassword()
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * The language (Java format) for messages.
     * "de_DE" for german messages.
     * "en_US" for english messages.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageLanguage() {
        return messageLanguage;
    }

    /**
     * Sets the value of the messageLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMessageLanguage()
     */
    public void setMessageLanguage(String value) {
        this.messageLanguage = value;
    }

}
