
package de.metas.shipper.gateway.go.schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Sendungsnummern complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Sendungsnummern">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Seitengroesse">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="A4"/>
 *               <enumeration value="A5"/>
 *               <enumeration value="A6"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="SendungsnummerAX4" maxOccurs="unbounded">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               <minInclusive value="1"/>
 *               <maxInclusive value="15"/>
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
@XmlType(name = "Sendungsnummern", propOrder = {
    "seitengroesse",
    "sendungsnummerAX4"
})
public class Sendungsnummern {

    @XmlElement(name = "Seitengroesse", required = true)
    protected String seitengroesse;
    @XmlElement(name = "SendungsnummerAX4", type = Integer.class)
    protected List<Integer> sendungsnummerAX4;

    /**
     * Gets the value of the seitengroesse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeitengroesse() {
        return seitengroesse;
    }

    /**
     * Sets the value of the seitengroesse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeitengroesse(String value) {
        this.seitengroesse = value;
    }

    /**
     * Gets the value of the sendungsnummerAX4 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the sendungsnummerAX4 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSendungsnummerAX4().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     * @return
     *     The value of the sendungsnummerAX4 property.
     */
    public List<Integer> getSendungsnummerAX4() {
        if (sendungsnummerAX4 == null) {
            sendungsnummerAX4 = new ArrayList<>();
        }
        return this.sendungsnummerAX4;
    }

}
