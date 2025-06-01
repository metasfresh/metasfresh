
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
 * Abfrage von Neuen oder Historischen Lieveravis; zu einem Lieferschein GENAU ein Avis
 * 
 * <p>Java class for LieferavisAbfragenAntwort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="LieferavisAbfragenAntwort">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Lieferavis" maxOccurs="999" minOccurs="0">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Zeile" maxOccurs="9999">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="Anteil" maxOccurs="99" minOccurs="0">
 *                               <complexType>
 *                                 <complexContent>
 *                                   <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     <attribute name="AuftragsID" type="{urn:msv3:v2}uuid" />
 *                                     <attribute name="MengeGeliefert" use="required">
 *                                       <simpleType>
 *                                         <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                           <minInclusive value="1"/>
 *                                         </restriction>
 *                                       </simpleType>
 *                                     </attribute>
 *                                     <attribute name="Packstuecknummer">
 *                                       <simpleType>
 *                                         <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           <maxLength value="20"/>
 *                                           <minLength value="1"/>
 *                                         </restriction>
 *                                       </simpleType>
 *                                     </attribute>
 *                                     <attribute name="Charge">
 *                                       <simpleType>
 *                                         <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           <minLength value="0"/>
 *                                           <maxLength value="50"/>
 *                                         </restriction>
 *                                       </simpleType>
 *                                     </attribute>
 *                                     <attribute name="Verfalldatum" type="{http://www.w3.org/2001/XMLSchema}date" />
 *                                   </restriction>
 *                                 </complexContent>
 *                               </complexType>
 *                             </element>
 *                             <element name="Fehlmenge" maxOccurs="99" minOccurs="0">
 *                               <complexType>
 *                                 <complexContent>
 *                                   <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     <attribute name="AuftragsID" type="{urn:msv3:v2}uuid" />
 *                                     <attribute name="Menge" use="required">
 *                                       <simpleType>
 *                                         <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                           <minInclusive value="1"/>
 *                                         </restriction>
 *                                       </simpleType>
 *                                     </attribute>
 *                                   </restriction>
 *                                 </complexContent>
 *                               </complexType>
 *                             </element>
 *                           </sequence>
 *                           <attribute name="PZN" use="required" type="{urn:msv3:v2}pzn" />
 *                           <attribute name="Artikelbezeichnung">
 *                             <simpleType>
 *                               <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 <minLength value="1"/>
 *                                 <maxLength value="50"/>
 *                               </restriction>
 *                             </simpleType>
 *                           </attribute>
 *                           <attribute name="GebindeId" type="{urn:msv3:v2}DruckbareKennung" />
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                   <element name="DokumentId" type="{urn:msv3:v2}uuid" minOccurs="0"/>
 *                 </sequence>
 *                 <attribute name="Lieferscheinnummer" use="required" type="{urn:msv3:v2}LieferscheinnummerType" />
 *                 <attribute name="BarcodeReferenz" use="required" type="{urn:msv3:v2}BarcodeReferenzType" />
 *                 <attribute name="Auftragsart" use="required" type="{urn:msv3:v2}Auftragsart" />
 *                 <attribute name="Auftragskennung" use="required" type="{urn:msv3:v2}DruckbareKennung" />
 *                 <attribute name="Belegdatum" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *                 <attribute name="Lieferzeitpunkt" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                 <attribute name="Tour">
 *                   <simpleType>
 *                     <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       <minLength value="1"/>
 *                       <maxLength value="80"/>
 *                     </restriction>
 *                   </simpleType>
 *                 </attribute>
 *                 <attribute name="FehlmengenEnthalten" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
@XmlType(name = "LieferavisAbfragenAntwort", propOrder = {
    "lieferavis"
})
public class LieferavisAbfragenAntwort {

    @XmlElement(name = "Lieferavis")
    protected List<LieferavisAbfragenAntwort.Lieferavis> lieferavis;

    /**
     * Gets the value of the lieferavis property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the lieferavis property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLieferavis().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LieferavisAbfragenAntwort.Lieferavis }
     * 
     * 
     * @return
     *     The value of the lieferavis property.
     */
    public List<LieferavisAbfragenAntwort.Lieferavis> getLieferavis() {
        if (lieferavis == null) {
            lieferavis = new ArrayList<>();
        }
        return this.lieferavis;
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
     *         <element name="Zeile" maxOccurs="9999">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="Anteil" maxOccurs="99" minOccurs="0">
     *                     <complexType>
     *                       <complexContent>
     *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           <attribute name="AuftragsID" type="{urn:msv3:v2}uuid" />
     *                           <attribute name="MengeGeliefert" use="required">
     *                             <simpleType>
     *                               <restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                 <minInclusive value="1"/>
     *                               </restriction>
     *                             </simpleType>
     *                           </attribute>
     *                           <attribute name="Packstuecknummer">
     *                             <simpleType>
     *                               <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 <maxLength value="20"/>
     *                                 <minLength value="1"/>
     *                               </restriction>
     *                             </simpleType>
     *                           </attribute>
     *                           <attribute name="Charge">
     *                             <simpleType>
     *                               <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 <minLength value="0"/>
     *                                 <maxLength value="50"/>
     *                               </restriction>
     *                             </simpleType>
     *                           </attribute>
     *                           <attribute name="Verfalldatum" type="{http://www.w3.org/2001/XMLSchema}date" />
     *                         </restriction>
     *                       </complexContent>
     *                     </complexType>
     *                   </element>
     *                   <element name="Fehlmenge" maxOccurs="99" minOccurs="0">
     *                     <complexType>
     *                       <complexContent>
     *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           <attribute name="AuftragsID" type="{urn:msv3:v2}uuid" />
     *                           <attribute name="Menge" use="required">
     *                             <simpleType>
     *                               <restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                 <minInclusive value="1"/>
     *                               </restriction>
     *                             </simpleType>
     *                           </attribute>
     *                         </restriction>
     *                       </complexContent>
     *                     </complexType>
     *                   </element>
     *                 </sequence>
     *                 <attribute name="PZN" use="required" type="{urn:msv3:v2}pzn" />
     *                 <attribute name="Artikelbezeichnung">
     *                   <simpleType>
     *                     <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       <minLength value="1"/>
     *                       <maxLength value="50"/>
     *                     </restriction>
     *                   </simpleType>
     *                 </attribute>
     *                 <attribute name="GebindeId" type="{urn:msv3:v2}DruckbareKennung" />
     *               </restriction>
     *             </complexContent>
     *           </complexType>
     *         </element>
     *         <element name="DokumentId" type="{urn:msv3:v2}uuid" minOccurs="0"/>
     *       </sequence>
     *       <attribute name="Lieferscheinnummer" use="required" type="{urn:msv3:v2}LieferscheinnummerType" />
     *       <attribute name="BarcodeReferenz" use="required" type="{urn:msv3:v2}BarcodeReferenzType" />
     *       <attribute name="Auftragsart" use="required" type="{urn:msv3:v2}Auftragsart" />
     *       <attribute name="Auftragskennung" use="required" type="{urn:msv3:v2}DruckbareKennung" />
     *       <attribute name="Belegdatum" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
     *       <attribute name="Lieferzeitpunkt" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *       <attribute name="Tour">
     *         <simpleType>
     *           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             <minLength value="1"/>
     *             <maxLength value="80"/>
     *           </restriction>
     *         </simpleType>
     *       </attribute>
     *       <attribute name="FehlmengenEnthalten" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     </restriction>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "zeile",
        "dokumentId"
    })
    public static class Lieferavis {

        @XmlElement(name = "Zeile", required = true)
        protected List<LieferavisAbfragenAntwort.Lieferavis.Zeile> zeile;
        @XmlElement(name = "DokumentId")
        protected String dokumentId;
        @XmlAttribute(name = "Lieferscheinnummer", required = true)
        protected String lieferscheinnummer;
        @XmlAttribute(name = "BarcodeReferenz", required = true)
        protected String barcodeReferenz;
        @XmlAttribute(name = "Auftragsart", required = true)
        protected Auftragsart auftragsart;
        @XmlAttribute(name = "Auftragskennung", required = true)
        protected String auftragskennung;
        @XmlAttribute(name = "Belegdatum", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar belegdatum;
        @XmlAttribute(name = "Lieferzeitpunkt")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar lieferzeitpunkt;
        @XmlAttribute(name = "Tour")
        protected String tour;
        @XmlAttribute(name = "FehlmengenEnthalten", required = true)
        protected boolean fehlmengenEnthalten;

        /**
         * Gets the value of the zeile property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a {@code set} method for the zeile property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getZeile().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LieferavisAbfragenAntwort.Lieferavis.Zeile }
         * 
         * 
         * @return
         *     The value of the zeile property.
         */
        public List<LieferavisAbfragenAntwort.Lieferavis.Zeile> getZeile() {
            if (zeile == null) {
                zeile = new ArrayList<>();
            }
            return this.zeile;
        }

        /**
         * Gets the value of the dokumentId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDokumentId() {
            return dokumentId;
        }

        /**
         * Sets the value of the dokumentId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDokumentId(String value) {
            this.dokumentId = value;
        }

        /**
         * Gets the value of the lieferscheinnummer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLieferscheinnummer() {
            return lieferscheinnummer;
        }

        /**
         * Sets the value of the lieferscheinnummer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLieferscheinnummer(String value) {
            this.lieferscheinnummer = value;
        }

        /**
         * Gets the value of the barcodeReferenz property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBarcodeReferenz() {
            return barcodeReferenz;
        }

        /**
         * Sets the value of the barcodeReferenz property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBarcodeReferenz(String value) {
            this.barcodeReferenz = value;
        }

        /**
         * Gets the value of the auftragsart property.
         * 
         * @return
         *     possible object is
         *     {@link Auftragsart }
         *     
         */
        public Auftragsart getAuftragsart() {
            return auftragsart;
        }

        /**
         * Sets the value of the auftragsart property.
         * 
         * @param value
         *     allowed object is
         *     {@link Auftragsart }
         *     
         */
        public void setAuftragsart(Auftragsart value) {
            this.auftragsart = value;
        }

        /**
         * Gets the value of the auftragskennung property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAuftragskennung() {
            return auftragskennung;
        }

        /**
         * Sets the value of the auftragskennung property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAuftragskennung(String value) {
            this.auftragskennung = value;
        }

        /**
         * Gets the value of the belegdatum property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getBelegdatum() {
            return belegdatum;
        }

        /**
         * Sets the value of the belegdatum property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setBelegdatum(XMLGregorianCalendar value) {
            this.belegdatum = value;
        }

        /**
         * Gets the value of the lieferzeitpunkt property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getLieferzeitpunkt() {
            return lieferzeitpunkt;
        }

        /**
         * Sets the value of the lieferzeitpunkt property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setLieferzeitpunkt(XMLGregorianCalendar value) {
            this.lieferzeitpunkt = value;
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
         * Gets the value of the fehlmengenEnthalten property.
         * 
         */
        public boolean isFehlmengenEnthalten() {
            return fehlmengenEnthalten;
        }

        /**
         * Sets the value of the fehlmengenEnthalten property.
         * 
         */
        public void setFehlmengenEnthalten(boolean value) {
            this.fehlmengenEnthalten = value;
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
         *         <element name="Anteil" maxOccurs="99" minOccurs="0">
         *           <complexType>
         *             <complexContent>
         *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 <attribute name="AuftragsID" type="{urn:msv3:v2}uuid" />
         *                 <attribute name="MengeGeliefert" use="required">
         *                   <simpleType>
         *                     <restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                       <minInclusive value="1"/>
         *                     </restriction>
         *                   </simpleType>
         *                 </attribute>
         *                 <attribute name="Packstuecknummer">
         *                   <simpleType>
         *                     <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       <maxLength value="20"/>
         *                       <minLength value="1"/>
         *                     </restriction>
         *                   </simpleType>
         *                 </attribute>
         *                 <attribute name="Charge">
         *                   <simpleType>
         *                     <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       <minLength value="0"/>
         *                       <maxLength value="50"/>
         *                     </restriction>
         *                   </simpleType>
         *                 </attribute>
         *                 <attribute name="Verfalldatum" type="{http://www.w3.org/2001/XMLSchema}date" />
         *               </restriction>
         *             </complexContent>
         *           </complexType>
         *         </element>
         *         <element name="Fehlmenge" maxOccurs="99" minOccurs="0">
         *           <complexType>
         *             <complexContent>
         *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 <attribute name="AuftragsID" type="{urn:msv3:v2}uuid" />
         *                 <attribute name="Menge" use="required">
         *                   <simpleType>
         *                     <restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                       <minInclusive value="1"/>
         *                     </restriction>
         *                   </simpleType>
         *                 </attribute>
         *               </restriction>
         *             </complexContent>
         *           </complexType>
         *         </element>
         *       </sequence>
         *       <attribute name="PZN" use="required" type="{urn:msv3:v2}pzn" />
         *       <attribute name="Artikelbezeichnung">
         *         <simpleType>
         *           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             <minLength value="1"/>
         *             <maxLength value="50"/>
         *           </restriction>
         *         </simpleType>
         *       </attribute>
         *       <attribute name="GebindeId" type="{urn:msv3:v2}DruckbareKennung" />
         *     </restriction>
         *   </complexContent>
         * </complexType>
         * }</pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "anteil",
            "fehlmenge"
        })
        public static class Zeile {

            @XmlElement(name = "Anteil")
            protected List<LieferavisAbfragenAntwort.Lieferavis.Zeile.Anteil> anteil;
            @XmlElement(name = "Fehlmenge")
            protected List<LieferavisAbfragenAntwort.Lieferavis.Zeile.Fehlmenge> fehlmenge;
            @XmlAttribute(name = "PZN", required = true)
            protected long pzn;
            @XmlAttribute(name = "Artikelbezeichnung")
            protected String artikelbezeichnung;
            @XmlAttribute(name = "GebindeId")
            protected String gebindeId;

            /**
             * Gets the value of the anteil property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the Jakarta XML Binding object.
             * This is why there is not a {@code set} method for the anteil property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAnteil().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link LieferavisAbfragenAntwort.Lieferavis.Zeile.Anteil }
             * 
             * 
             * @return
             *     The value of the anteil property.
             */
            public List<LieferavisAbfragenAntwort.Lieferavis.Zeile.Anteil> getAnteil() {
                if (anteil == null) {
                    anteil = new ArrayList<>();
                }
                return this.anteil;
            }

            /**
             * Gets the value of the fehlmenge property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the Jakarta XML Binding object.
             * This is why there is not a {@code set} method for the fehlmenge property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getFehlmenge().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link LieferavisAbfragenAntwort.Lieferavis.Zeile.Fehlmenge }
             * 
             * 
             * @return
             *     The value of the fehlmenge property.
             */
            public List<LieferavisAbfragenAntwort.Lieferavis.Zeile.Fehlmenge> getFehlmenge() {
                if (fehlmenge == null) {
                    fehlmenge = new ArrayList<>();
                }
                return this.fehlmenge;
            }

            /**
             * Gets the value of the pzn property.
             * 
             */
            public long getPZN() {
                return pzn;
            }

            /**
             * Sets the value of the pzn property.
             * 
             */
            public void setPZN(long value) {
                this.pzn = value;
            }

            /**
             * Gets the value of the artikelbezeichnung property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getArtikelbezeichnung() {
                return artikelbezeichnung;
            }

            /**
             * Sets the value of the artikelbezeichnung property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setArtikelbezeichnung(String value) {
                this.artikelbezeichnung = value;
            }

            /**
             * Gets the value of the gebindeId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getGebindeId() {
                return gebindeId;
            }

            /**
             * Sets the value of the gebindeId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setGebindeId(String value) {
                this.gebindeId = value;
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
             *       <attribute name="AuftragsID" type="{urn:msv3:v2}uuid" />
             *       <attribute name="MengeGeliefert" use="required">
             *         <simpleType>
             *           <restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *             <minInclusive value="1"/>
             *           </restriction>
             *         </simpleType>
             *       </attribute>
             *       <attribute name="Packstuecknummer">
             *         <simpleType>
             *           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             <maxLength value="20"/>
             *             <minLength value="1"/>
             *           </restriction>
             *         </simpleType>
             *       </attribute>
             *       <attribute name="Charge">
             *         <simpleType>
             *           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             <minLength value="0"/>
             *             <maxLength value="50"/>
             *           </restriction>
             *         </simpleType>
             *       </attribute>
             *       <attribute name="Verfalldatum" type="{http://www.w3.org/2001/XMLSchema}date" />
             *     </restriction>
             *   </complexContent>
             * </complexType>
             * }</pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Anteil {

                @XmlAttribute(name = "AuftragsID")
                protected String auftragsID;
                @XmlAttribute(name = "MengeGeliefert", required = true)
                protected int mengeGeliefert;
                @XmlAttribute(name = "Packstuecknummer")
                protected String packstuecknummer;
                @XmlAttribute(name = "Charge")
                protected String charge;
                @XmlAttribute(name = "Verfalldatum")
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar verfalldatum;

                /**
                 * Gets the value of the auftragsID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAuftragsID() {
                    return auftragsID;
                }

                /**
                 * Sets the value of the auftragsID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAuftragsID(String value) {
                    this.auftragsID = value;
                }

                /**
                 * Gets the value of the mengeGeliefert property.
                 * 
                 */
                public int getMengeGeliefert() {
                    return mengeGeliefert;
                }

                /**
                 * Sets the value of the mengeGeliefert property.
                 * 
                 */
                public void setMengeGeliefert(int value) {
                    this.mengeGeliefert = value;
                }

                /**
                 * Gets the value of the packstuecknummer property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPackstuecknummer() {
                    return packstuecknummer;
                }

                /**
                 * Sets the value of the packstuecknummer property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPackstuecknummer(String value) {
                    this.packstuecknummer = value;
                }

                /**
                 * Gets the value of the charge property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCharge() {
                    return charge;
                }

                /**
                 * Sets the value of the charge property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCharge(String value) {
                    this.charge = value;
                }

                /**
                 * Gets the value of the verfalldatum property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getVerfalldatum() {
                    return verfalldatum;
                }

                /**
                 * Sets the value of the verfalldatum property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setVerfalldatum(XMLGregorianCalendar value) {
                    this.verfalldatum = value;
                }

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
             *       <attribute name="AuftragsID" type="{urn:msv3:v2}uuid" />
             *       <attribute name="Menge" use="required">
             *         <simpleType>
             *           <restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *             <minInclusive value="1"/>
             *           </restriction>
             *         </simpleType>
             *       </attribute>
             *     </restriction>
             *   </complexContent>
             * </complexType>
             * }</pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Fehlmenge {

                @XmlAttribute(name = "AuftragsID")
                protected String auftragsID;
                @XmlAttribute(name = "Menge", required = true)
                protected int menge;

                /**
                 * Gets the value of the auftragsID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAuftragsID() {
                    return auftragsID;
                }

                /**
                 * Sets the value of the auftragsID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAuftragsID(String value) {
                    this.auftragsID = value;
                }

                /**
                 * Gets the value of the menge property.
                 * 
                 */
                public int getMenge() {
                    return menge;
                }

                /**
                 * Sets the value of the menge property.
                 * 
                 */
                public void setMenge(int value) {
                    this.menge = value;
                }

            }

        }

    }

}
