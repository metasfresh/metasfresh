
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * The data structure for authentication data.
 * 
 * <p>Java class for anonymous complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="delisId">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="10"/>
 *               <minLength value="8"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="authToken">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="0"/>
 *               <maxLength value="64"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
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
@XmlType(name = "", propOrder = {
    "delisId",
    "authToken",
    "messageLanguage"
})
@XmlRootElement(name = "authentication", namespace = "http://dpd.com/common/service/types/Authentication/2.0")
public class Authentication {

    /**
     * The delis user id for authentication.
     * 
     */
    @XmlElement(required = true)
    protected String delisId;
    /**
     * The token for authentication. Field authToken of Login, as a result of
     * Method "getAuth" of LoginService.
     * 
     */
    @XmlElement(required = true)
    protected String authToken;
    /**
     * The language (Java format) for messages.
     * "de_DE" for german messages.
     * "en_US" for english messages.
     * 
     */
    @XmlElement(required = true)
    protected String messageLanguage;

    /**
     * The delis user id for authentication.
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
     * The token for authentication. Field authToken of Login, as a result of
     * Method "getAuth" of LoginService.
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
