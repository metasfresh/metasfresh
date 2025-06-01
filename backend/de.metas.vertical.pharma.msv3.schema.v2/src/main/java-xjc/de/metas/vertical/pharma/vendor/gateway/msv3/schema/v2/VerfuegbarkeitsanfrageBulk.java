
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for VerfuegbarkeitsanfrageBulk complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VerfuegbarkeitsanfrageBulk">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="Pzn" type="{urn:msv3:v2}pzn" maxOccurs="10000"/>
 *         <element name="PznBlock" type="{urn:msv3:v2}pzn"/>
 *       </choice>
 *       <attribute name="Id" use="required" type="{urn:msv3:v2}uuid" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerfuegbarkeitsanfrageBulk", propOrder = {
    "pzn",
    "pznBlock"
})
public class VerfuegbarkeitsanfrageBulk {

    @XmlElement(name = "Pzn", type = Long.class)
    protected List<Long> pzn;
    @XmlElement(name = "PznBlock")
    protected Long pznBlock;
    @XmlAttribute(name = "Id", required = true)
    protected String id;

    /**
     * Gets the value of the pzn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the pzn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPzn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     * @return
     *     The value of the pzn property.
     */
    public List<Long> getPzn() {
        if (pzn == null) {
            pzn = new ArrayList<>();
        }
        return this.pzn;
    }

    /**
     * Gets the value of the pznBlock property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPznBlock() {
        return pznBlock;
    }

    /**
     * Sets the value of the pznBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPznBlock(Long value) {
        this.pznBlock = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
