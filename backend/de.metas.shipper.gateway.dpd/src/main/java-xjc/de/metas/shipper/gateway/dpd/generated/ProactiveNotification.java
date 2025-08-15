
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for proactiveNotification complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="proactiveNotification">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="channel">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <enumeration value="1"/>
 *               <enumeration value="2"/>
 *               <enumeration value="3"/>
 *               <enumeration value="6"/>
 *               <enumeration value="7"/>
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
 *         <element name="rule">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <maxInclusive value="31"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="language">
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
@XmlType(name = "proactiveNotification", propOrder = {
    "channel",
    "value",
    "rule",
    "language"
})
public class ProactiveNotification {

    /**
     * Defines type of proactive notification.
     * Possible values are:
     *  1 = email
     *  2 = telephone
     *  3 = SMS
     *  6 = FAX
     *  7 = postcard
     * 
     */
    protected int channel;
    /**
     * Data for proactive notification, e.g. telephone number, email address, etc.
     * The data format for the proactive message types SMS, phone and FAX is as follows:
     * +international country number#phone number
     * Examples:
     * +49#1725673423
     * +49#01725673423
     * 
     */
    @XmlElement(required = true)
    protected String value;
    /**
     * Rule for which events a notification is sent. Each event has a certain value. By adding the different values it is possible to build combinations of events, e.g. notification for pick-up and delivery is 5.
     * The different values are:
     *  1 = pick-up
     *  2 = non-delivery
     *  4 = delivery
     *  8 = inbound
     *  16 = out for delivery
     * 
     */
    protected int rule;
    /**
     * Language of the proactive notification in ISO-3166-1 alpha-2 format (e.g. 'DE').
     * 
     */
    @XmlElement(required = true)
    protected String language;

    /**
     * Defines type of proactive notification.
     * Possible values are:
     *  1 = email
     *  2 = telephone
     *  3 = SMS
     *  6 = FAX
     *  7 = postcard
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
     * Data for proactive notification, e.g. telephone number, email address, etc.
     * The data format for the proactive message types SMS, phone and FAX is as follows:
     * +international country number#phone number
     * Examples:
     * +49#1725673423
     * +49#01725673423
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
     * Rule for which events a notification is sent. Each event has a certain value. By adding the different values it is possible to build combinations of events, e.g. notification for pick-up and delivery is 5.
     * The different values are:
     *  1 = pick-up
     *  2 = non-delivery
     *  4 = delivery
     *  8 = inbound
     *  16 = out for delivery
     * 
     */
    public int getRule() {
        return rule;
    }

    /**
     * Sets the value of the rule property.
     * 
     */
    public void setRule(int value) {
        this.rule = value;
    }

    /**
     * Language of the proactive notification in ISO-3166-1 alpha-2 format (e.g. 'DE').
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
