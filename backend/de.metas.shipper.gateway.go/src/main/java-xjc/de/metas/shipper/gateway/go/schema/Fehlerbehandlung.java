
package de.metas.shipper.gateway.go.schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Fehlerbehandlung complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Fehlerbehandlung">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Fehlermeldungen">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Fehler" maxOccurs="unbounded">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="FehlerNr">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <minLength value="1"/>
 *                                   <maxLength value="10"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="Feld" minOccurs="0">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <minLength value="1"/>
 *                                   <maxLength value="255"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="StackTrace" minOccurs="0">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <minLength value="1"/>
 *                                   <maxLength value="255"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="Beschreibung">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <minLength value="1"/>
 *                                   <maxLength value="255"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                           </sequence>
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
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
@XmlType(name = "Fehlerbehandlung", propOrder = {
    "fehlermeldungen"
})
public class Fehlerbehandlung {

    @XmlElement(name = "Fehlermeldungen", required = true)
    protected Fehlerbehandlung.Fehlermeldungen fehlermeldungen;

    /**
     * Gets the value of the fehlermeldungen property.
     * 
     * @return
     *     possible object is
     *     {@link Fehlerbehandlung.Fehlermeldungen }
     *     
     */
    public Fehlerbehandlung.Fehlermeldungen getFehlermeldungen() {
        return fehlermeldungen;
    }

    /**
     * Sets the value of the fehlermeldungen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fehlerbehandlung.Fehlermeldungen }
     *     
     */
    public void setFehlermeldungen(Fehlerbehandlung.Fehlermeldungen value) {
        this.fehlermeldungen = value;
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
     *         <element name="Fehler" maxOccurs="unbounded">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="FehlerNr">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <minLength value="1"/>
     *                         <maxLength value="10"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="Feld" minOccurs="0">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <minLength value="1"/>
     *                         <maxLength value="255"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="StackTrace" minOccurs="0">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <minLength value="1"/>
     *                         <maxLength value="255"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="Beschreibung">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <minLength value="1"/>
     *                         <maxLength value="255"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                 </sequence>
     *               </restriction>
     *             </complexContent>
     *           </complexType>
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
        "fehler"
    })
    public static class Fehlermeldungen {

        @XmlElement(name = "Fehler", required = true)
        protected List<Fehlerbehandlung.Fehlermeldungen.Fehler> fehler;

        /**
         * Gets the value of the fehler property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a {@code set} method for the fehler property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFehler().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Fehlerbehandlung.Fehlermeldungen.Fehler }
         * 
         * 
         * @return
         *     The value of the fehler property.
         */
        public List<Fehlerbehandlung.Fehlermeldungen.Fehler> getFehler() {
            if (fehler == null) {
                fehler = new ArrayList<>();
            }
            return this.fehler;
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
         *         <element name="FehlerNr">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <minLength value="1"/>
         *               <maxLength value="10"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="Feld" minOccurs="0">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <minLength value="1"/>
         *               <maxLength value="255"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="StackTrace" minOccurs="0">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <minLength value="1"/>
         *               <maxLength value="255"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="Beschreibung">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <minLength value="1"/>
         *               <maxLength value="255"/>
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
            "fehlerNr",
            "feld",
            "stackTrace",
            "beschreibung"
        })
        public static class Fehler {

            @XmlElement(name = "FehlerNr", required = true)
            protected String fehlerNr;
            @XmlElement(name = "Feld")
            protected String feld;
            @XmlElement(name = "StackTrace")
            protected String stackTrace;
            @XmlElement(name = "Beschreibung", required = true)
            protected String beschreibung;

            /**
             * Gets the value of the fehlerNr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFehlerNr() {
                return fehlerNr;
            }

            /**
             * Sets the value of the fehlerNr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFehlerNr(String value) {
                this.fehlerNr = value;
            }

            /**
             * Gets the value of the feld property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFeld() {
                return feld;
            }

            /**
             * Sets the value of the feld property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFeld(String value) {
                this.feld = value;
            }

            /**
             * Gets the value of the stackTrace property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStackTrace() {
                return stackTrace;
            }

            /**
             * Sets the value of the stackTrace property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStackTrace(String value) {
                this.stackTrace = value;
            }

            /**
             * Gets the value of the beschreibung property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBeschreibung() {
                return beschreibung;
            }

            /**
             * Sets the value of the beschreibung property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBeschreibung(String value) {
                this.beschreibung = value;
            }

        }

    }

}
