
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for notification complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="notification">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="channel">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <enumeration value="1"/>
 *               <enumeration value="2"/>
 *               <enumeration value="3"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="value">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="language" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="2"/>
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
@XmlType(name = "notification", propOrder = {
    "channel",
    "value",
    "language"
})
public class Notification {

    /**
     * Defines channel of notification.
     * Possible values are:
     *  1 = email
     *  2 = telephone
     *  3 = SMS
     * 
     */
    protected int channel;
    /**
     * Value for the chosen channel, i.e. the phone
     * 						number or the e-mail address. No required data
     * 						format for SMS and phone.
     * 
     */
    @XmlElement(required = true)
    protected String value;
    /**
     * Language of the notification in ISO 3166-1 alpha-2 format (e.g. 'DE').
     * 
     */
    protected String language;

    /**
     * Defines channel of notification.
     * Possible values are:
     *  1 = email
     *  2 = telephone
     *  3 = SMS
     * 
     */
    public int getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     */
    public void setChannel(int value) {
        this.channel = value;
    }

    /**
     * Value for the chosen channel, i.e. the phone
     * 						number or the e-mail address. No required data
     * 						format for SMS and phone.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getValue()
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Language of the notification in ISO 3166-1 alpha-2 format (e.g. 'DE').
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getLanguage()
     */
    public void setLanguage(String value) {
        this.language = value;
    }

}
