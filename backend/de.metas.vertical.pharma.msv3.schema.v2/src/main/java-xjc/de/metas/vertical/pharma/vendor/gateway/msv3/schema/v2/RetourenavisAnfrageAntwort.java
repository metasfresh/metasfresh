
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Antwort(en) zu einer RetourenAnfrage
 * 
 * <p>Java class for RetourenavisAnfrageAntwort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="RetourenavisAnfrageAntwort">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Position" maxOccurs="999">
 *           <complexType>
 *             <complexContent>
 *               <extension base="{urn:msv3:v2}RetourePositionType">
 *                 <attribute name="RetourenAnteilTyp" use="required" type="{urn:msv3:v2}RetourenAnteilTypType" />
 *               </extension>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *       </sequence>
 *       <attribute name="ID" use="required" type="{urn:msv3:v2}uuid" />
 *       <attribute name="RetoureSupportID" use="required" type="{urn:msv3:v2}SupportIDType" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetourenavisAnfrageAntwort", propOrder = {
    "position"
})
public class RetourenavisAnfrageAntwort {

    @XmlElement(name = "Position", required = true)
    protected List<RetourenavisAnfrageAntwort.Position> position;
    @XmlAttribute(name = "ID", required = true)
    protected String id;
    @XmlAttribute(name = "RetoureSupportID", required = true)
    protected int retoureSupportID;

    /**
     * Gets the value of the position property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the position property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RetourenavisAnfrageAntwort.Position }
     * 
     * 
     * @return
     *     The value of the position property.
     */
    public List<RetourenavisAnfrageAntwort.Position> getPosition() {
        if (position == null) {
            position = new ArrayList<>();
        }
        return this.position;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
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
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the retoureSupportID property.
     * 
     */
    public int getRetoureSupportID() {
        return retoureSupportID;
    }

    /**
     * Sets the value of the retoureSupportID property.
     * 
     */
    public void setRetoureSupportID(int value) {
        this.retoureSupportID = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>{@code
     * <complexType>
     *   <complexContent>
     *     <extension base="{urn:msv3:v2}RetourePositionType">
     *       <attribute name="RetourenAnteilTyp" use="required" type="{urn:msv3:v2}RetourenAnteilTypType" />
     *     </extension>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Position
        extends RetourePositionType
    {

        @XmlAttribute(name = "RetourenAnteilTyp", required = true)
        protected RetourenAnteilTypType retourenAnteilTyp;

        /**
         * Gets the value of the retourenAnteilTyp property.
         * 
         * @return
         *     possible object is
         *     {@link RetourenAnteilTypType }
         *     
         */
        public RetourenAnteilTypType getRetourenAnteilTyp() {
            return retourenAnteilTyp;
        }

        /**
         * Sets the value of the retourenAnteilTyp property.
         * 
         * @param value
         *     allowed object is
         *     {@link RetourenAnteilTypType }
         *     
         */
        public void setRetourenAnteilTyp(RetourenAnteilTypType value) {
            this.retourenAnteilTyp = value;
        }

    }

}
