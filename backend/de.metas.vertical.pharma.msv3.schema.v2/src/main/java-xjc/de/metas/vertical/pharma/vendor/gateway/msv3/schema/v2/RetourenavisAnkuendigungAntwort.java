
package de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * Antwort(en) zu einer RetourenAnkuendigung
 * 
 * <p>Java class for RetourenavisAnkuendigungAntwort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="RetourenavisAnkuendigungAntwort">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="RetourenAnteil" maxOccurs="15">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Position" maxOccurs="999">
 *                     <complexType>
 *                       <complexContent>
 *                         <extension base="{urn:msv3:v2}RetourePositionType">
 *                         </extension>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                 </sequence>
 *                 <attribute name="GHReferenzID">
 *                   <simpleType>
 *                     <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       <minLength value="1"/>
 *                       <maxLength value="30"/>
 *                       <pattern value="[a-zA-Z0-9#+\*<!---->/\-_]*"/>
 *                     </restriction>
 *                   </simpleType>
 *                 </attribute>
 *                 <attribute name="DokumentID" type="{urn:msv3:v2}uuid" />
 *                 <attribute name="Abholzeitpunkt" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                 <attribute name="Tour">
 *                   <simpleType>
 *                     <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       <minLength value="1"/>
 *                       <maxLength value="80"/>
 *                     </restriction>
 *                   </simpleType>
 *                 </attribute>
 *                 <attribute name="RetourenAnteilTyp" use="required" type="{urn:msv3:v2}RetourenAnteilTypType" />
 *               </restriction>
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
@XmlType(name = "RetourenavisAnkuendigungAntwort", propOrder = {
    "retourenAnteil"
})
public class RetourenavisAnkuendigungAntwort {

    @XmlElement(name = "RetourenAnteil", required = true)
    protected List<RetourenavisAnkuendigungAntwort.RetourenAnteil> retourenAnteil;
    @XmlAttribute(name = "ID", required = true)
    protected String id;
    @XmlAttribute(name = "RetoureSupportID", required = true)
    protected int retoureSupportID;

    /**
     * Gets the value of the retourenAnteil property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the retourenAnteil property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRetourenAnteil().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RetourenavisAnkuendigungAntwort.RetourenAnteil }
     * 
     * 
     * @return
     *     The value of the retourenAnteil property.
     */
    public List<RetourenavisAnkuendigungAntwort.RetourenAnteil> getRetourenAnteil() {
        if (retourenAnteil == null) {
            retourenAnteil = new ArrayList<>();
        }
        return this.retourenAnteil;
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
     *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       <sequence>
     *         <element name="Position" maxOccurs="999">
     *           <complexType>
     *             <complexContent>
     *               <extension base="{urn:msv3:v2}RetourePositionType">
     *               </extension>
     *             </complexContent>
     *           </complexType>
     *         </element>
     *       </sequence>
     *       <attribute name="GHReferenzID">
     *         <simpleType>
     *           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             <minLength value="1"/>
     *             <maxLength value="30"/>
     *             <pattern value="[a-zA-Z0-9#+\*<!---->/\-_]*"/>
     *           </restriction>
     *         </simpleType>
     *       </attribute>
     *       <attribute name="DokumentID" type="{urn:msv3:v2}uuid" />
     *       <attribute name="Abholzeitpunkt" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *       <attribute name="Tour">
     *         <simpleType>
     *           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             <minLength value="1"/>
     *             <maxLength value="80"/>
     *           </restriction>
     *         </simpleType>
     *       </attribute>
     *       <attribute name="RetourenAnteilTyp" use="required" type="{urn:msv3:v2}RetourenAnteilTypType" />
     *     </restriction>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "position"
    })
    public static class RetourenAnteil {

        @XmlElement(name = "Position", required = true)
        protected List<RetourenavisAnkuendigungAntwort.RetourenAnteil.Position> position;
        @XmlAttribute(name = "GHReferenzID")
        protected String ghReferenzID;
        @XmlAttribute(name = "DokumentID")
        protected String dokumentID;
        @XmlAttribute(name = "Abholzeitpunkt")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar abholzeitpunkt;
        @XmlAttribute(name = "Tour")
        protected String tour;
        @XmlAttribute(name = "RetourenAnteilTyp", required = true)
        protected RetourenAnteilTypType retourenAnteilTyp;

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
         * {@link RetourenavisAnkuendigungAntwort.RetourenAnteil.Position }
         * 
         * 
         * @return
         *     The value of the position property.
         */
        public List<RetourenavisAnkuendigungAntwort.RetourenAnteil.Position> getPosition() {
            if (position == null) {
                position = new ArrayList<>();
            }
            return this.position;
        }

        /**
         * Gets the value of the ghReferenzID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGHReferenzID() {
            return ghReferenzID;
        }

        /**
         * Sets the value of the ghReferenzID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGHReferenzID(String value) {
            this.ghReferenzID = value;
        }

        /**
         * Gets the value of the dokumentID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDokumentID() {
            return dokumentID;
        }

        /**
         * Sets the value of the dokumentID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDokumentID(String value) {
            this.dokumentID = value;
        }

        /**
         * Gets the value of the abholzeitpunkt property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getAbholzeitpunkt() {
            return abholzeitpunkt;
        }

        /**
         * Sets the value of the abholzeitpunkt property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setAbholzeitpunkt(XMLGregorianCalendar value) {
            this.abholzeitpunkt = value;
        }

        /**
         * Gets the value of the tour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTour() {
            return tour;
        }

        /**
         * Sets the value of the tour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTour(String value) {
            this.tour = value;
        }

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


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>{@code
         * <complexType>
         *   <complexContent>
         *     <extension base="{urn:msv3:v2}RetourePositionType">
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


        }

    }

}
