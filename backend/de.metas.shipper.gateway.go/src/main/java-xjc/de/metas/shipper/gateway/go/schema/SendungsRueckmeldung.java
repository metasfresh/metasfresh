
package de.metas.shipper.gateway.go.schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for SendungsRueckmeldung complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SendungsRueckmeldung">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Sendung">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="SendungsnummerAX4">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="15"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Frachtbriefnummer">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="18"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Abholdatum">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).(19|20)[0-9]{2}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Zustelldatum">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).(19|20)[0-9]{2}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="ZustellUhrzeitVon" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="ZustellUhrzeitBis" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Position" maxOccurs="unbounded" minOccurs="0">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="PositionsNr">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <minLength value="1"/>
 *                                   <maxLength value="10"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="AnzahlPackstuecke">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <minLength value="1"/>
 *                                   <maxLength value="9"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="Barcodes" minOccurs="0">
 *                               <complexType>
 *                                 <complexContent>
 *                                   <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     <sequence>
 *                                       <element name="BarcodeNr" maxOccurs="unbounded" minOccurs="0">
 *                                         <simpleType>
 *                                           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             <minLength value="1"/>
 *                                             <maxLength value="35"/>
 *                                           </restriction>
 *                                         </simpleType>
 *                                       </element>
 *                                     </sequence>
 *                                   </restriction>
 *                                 </complexContent>
 *                               </complexType>
 *                             </element>
 *                           </sequence>
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                   <element name="Hinweis" minOccurs="0">
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
@XmlType(name = "SendungsRueckmeldung", propOrder = {
    "sendung"
})
public class SendungsRueckmeldung {

    @XmlElement(name = "Sendung", required = true)
    protected SendungsRueckmeldung.Sendung sendung;

    /**
     * Gets the value of the sendung property.
     * 
     * @return
     *     possible object is
     *     {@link SendungsRueckmeldung.Sendung }
     *     
     */
    public SendungsRueckmeldung.Sendung getSendung() {
        return sendung;
    }

    /**
     * Sets the value of the sendung property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendungsRueckmeldung.Sendung }
     *     
     */
    public void setSendung(SendungsRueckmeldung.Sendung value) {
        this.sendung = value;
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
     *         <element name="SendungsnummerAX4">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="15"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Frachtbriefnummer">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="18"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Abholdatum">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).(19|20)[0-9]{2}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Zustelldatum">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).(19|20)[0-9]{2}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="ZustellUhrzeitVon" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="ZustellUhrzeitBis" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Position" maxOccurs="unbounded" minOccurs="0">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="PositionsNr">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <minLength value="1"/>
     *                         <maxLength value="10"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="AnzahlPackstuecke">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <minLength value="1"/>
     *                         <maxLength value="9"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="Barcodes" minOccurs="0">
     *                     <complexType>
     *                       <complexContent>
     *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           <sequence>
     *                             <element name="BarcodeNr" maxOccurs="unbounded" minOccurs="0">
     *                               <simpleType>
     *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   <minLength value="1"/>
     *                                   <maxLength value="35"/>
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
     *         <element name="Hinweis" minOccurs="0">
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
        "sendungsnummerAX4",
        "frachtbriefnummer",
        "abholdatum",
        "zustelldatum",
        "zustellUhrzeitVon",
        "zustellUhrzeitBis",
        "position",
        "hinweis"
    })
    public static class Sendung {

        @XmlElement(name = "SendungsnummerAX4", required = true)
        protected String sendungsnummerAX4;
        @XmlElement(name = "Frachtbriefnummer", required = true)
        protected String frachtbriefnummer;
        @XmlElement(name = "Abholdatum", required = true)
        protected String abholdatum;
        @XmlElement(name = "Zustelldatum", required = true)
        protected String zustelldatum;
        @XmlElement(name = "ZustellUhrzeitVon")
        protected String zustellUhrzeitVon;
        @XmlElement(name = "ZustellUhrzeitBis")
        protected String zustellUhrzeitBis;
        @XmlElement(name = "Position")
        protected List<SendungsRueckmeldung.Sendung.Position> position;
        @XmlElement(name = "Hinweis")
        protected String hinweis;

        /**
         * Gets the value of the sendungsnummerAX4 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSendungsnummerAX4() {
            return sendungsnummerAX4;
        }

        /**
         * Sets the value of the sendungsnummerAX4 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSendungsnummerAX4(String value) {
            this.sendungsnummerAX4 = value;
        }

        /**
         * Gets the value of the frachtbriefnummer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFrachtbriefnummer() {
            return frachtbriefnummer;
        }

        /**
         * Sets the value of the frachtbriefnummer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFrachtbriefnummer(String value) {
            this.frachtbriefnummer = value;
        }

        /**
         * Gets the value of the abholdatum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAbholdatum() {
            return abholdatum;
        }

        /**
         * Sets the value of the abholdatum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAbholdatum(String value) {
            this.abholdatum = value;
        }

        /**
         * Gets the value of the zustelldatum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZustelldatum() {
            return zustelldatum;
        }

        /**
         * Sets the value of the zustelldatum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZustelldatum(String value) {
            this.zustelldatum = value;
        }

        /**
         * Gets the value of the zustellUhrzeitVon property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZustellUhrzeitVon() {
            return zustellUhrzeitVon;
        }

        /**
         * Sets the value of the zustellUhrzeitVon property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZustellUhrzeitVon(String value) {
            this.zustellUhrzeitVon = value;
        }

        /**
         * Gets the value of the zustellUhrzeitBis property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZustellUhrzeitBis() {
            return zustellUhrzeitBis;
        }

        /**
         * Sets the value of the zustellUhrzeitBis property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZustellUhrzeitBis(String value) {
            this.zustellUhrzeitBis = value;
        }

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
         * {@link SendungsRueckmeldung.Sendung.Position }
         * 
         * 
         * @return
         *     The value of the position property.
         */
        public List<SendungsRueckmeldung.Sendung.Position> getPosition() {
            if (position == null) {
                position = new ArrayList<>();
            }
            return this.position;
        }

        /**
         * Gets the value of the hinweis property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHinweis() {
            return hinweis;
        }

        /**
         * Sets the value of the hinweis property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHinweis(String value) {
            this.hinweis = value;
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
         *         <element name="PositionsNr">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <minLength value="1"/>
         *               <maxLength value="10"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="AnzahlPackstuecke">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <minLength value="1"/>
         *               <maxLength value="9"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="Barcodes" minOccurs="0">
         *           <complexType>
         *             <complexContent>
         *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 <sequence>
         *                   <element name="BarcodeNr" maxOccurs="unbounded" minOccurs="0">
         *                     <simpleType>
         *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         <minLength value="1"/>
         *                         <maxLength value="35"/>
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
            "positionsNr",
            "anzahlPackstuecke",
            "barcodes"
        })
        public static class Position {

            @XmlElement(name = "PositionsNr", required = true)
            protected String positionsNr;
            @XmlElement(name = "AnzahlPackstuecke", required = true)
            protected String anzahlPackstuecke;
            @XmlElement(name = "Barcodes")
            protected SendungsRueckmeldung.Sendung.Position.Barcodes barcodes;

            /**
             * Gets the value of the positionsNr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPositionsNr() {
                return positionsNr;
            }

            /**
             * Sets the value of the positionsNr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPositionsNr(String value) {
                this.positionsNr = value;
            }

            /**
             * Gets the value of the anzahlPackstuecke property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnzahlPackstuecke() {
                return anzahlPackstuecke;
            }

            /**
             * Sets the value of the anzahlPackstuecke property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnzahlPackstuecke(String value) {
                this.anzahlPackstuecke = value;
            }

            /**
             * Gets the value of the barcodes property.
             * 
             * @return
             *     possible object is
             *     {@link SendungsRueckmeldung.Sendung.Position.Barcodes }
             *     
             */
            public SendungsRueckmeldung.Sendung.Position.Barcodes getBarcodes() {
                return barcodes;
            }

            /**
             * Sets the value of the barcodes property.
             * 
             * @param value
             *     allowed object is
             *     {@link SendungsRueckmeldung.Sendung.Position.Barcodes }
             *     
             */
            public void setBarcodes(SendungsRueckmeldung.Sendung.Position.Barcodes value) {
                this.barcodes = value;
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
             *         <element name="BarcodeNr" maxOccurs="unbounded" minOccurs="0">
             *           <simpleType>
             *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               <minLength value="1"/>
             *               <maxLength value="35"/>
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
                "barcodeNr"
            })
            public static class Barcodes {

                @XmlElement(name = "BarcodeNr")
                protected List<String> barcodeNr;

                /**
                 * Gets the value of the barcodeNr property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the Jakarta XML Binding object.
                 * This is why there is not a {@code set} method for the barcodeNr property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getBarcodeNr().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 * @return
                 *     The value of the barcodeNr property.
                 */
                public List<String> getBarcodeNr() {
                    if (barcodeNr == null) {
                        barcodeNr = new ArrayList<>();
                    }
                    return this.barcodeNr;
                }

            }

        }

    }

}
