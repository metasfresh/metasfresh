
package de.metas.shipper.gateway.go.schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Sendung complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Sendung">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="SendungsnummerAX4" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <pattern value="[0-9]{1,15}"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Frachtbriefnummer" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="18"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Versender">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <pattern value="[0-9]{1,30}"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Benutzername">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="100"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Status">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="1"/>
 *               <enumeration value="3"/>
 *               <enumeration value="20"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Kundenreferenz" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="40"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Abholadresse">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Firmenname1">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="60"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Firmenname2" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="60"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Abteilung" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="40"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Strasse1">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="35"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Hausnummer" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="10"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Strasse2" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="25"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Land">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="[A-Za-z]{1,3}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Postleitzahl">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="9"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Stadt">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="30"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="Empfaenger">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Firmenname1">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="60"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Firmenname2" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="60"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Abteilung" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="40"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Strasse1">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="35"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Hausnummer" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="10"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Strasse2" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="35"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Land">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="[A-Za-z]{1,3}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Postleitzahl">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="9"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Stadt">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="30"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Ansprechpartner" minOccurs="0">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="Telefon">
 *                               <complexType>
 *                                 <complexContent>
 *                                   <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     <sequence>
 *                                       <element name="LaenderPrefix">
 *                                         <simpleType>
 *                                           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             <pattern value="[0]{2}[1-9]{1,2}"/>
 *                                           </restriction>
 *                                         </simpleType>
 *                                       </element>
 *                                       <element name="Ortsvorwahl">
 *                                         <simpleType>
 *                                           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             <pattern value="[0-9]{1,7}"/>
 *                                           </restriction>
 *                                         </simpleType>
 *                                       </element>
 *                                       <element name="Telefonnummer">
 *                                         <simpleType>
 *                                           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             <pattern value="[0-9]{1,10}"/>
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
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="Service">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="0"/>
 *               <enumeration value="1"/>
 *               <enumeration value="2"/>
 *               <enumeration value="3"/>
 *               <enumeration value="4"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Abholdatum">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Datum">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).(19|20)[0-9]{2}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="UhrzeitVon" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="UhrzeitBis" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="Zustelldatum" minOccurs="0">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Datum">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).(19|20)[0-9]{2}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="UhrzeitVon" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="UhrzeitBis" minOccurs="0">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="unfrei">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="0"/>
 *               <enumeration value="1"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Selbstanlieferung">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="0"/>
 *               <enumeration value="1"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Selbstabholung">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="0"/>
 *               <enumeration value="1"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Warenwert" minOccurs="0">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Betrag">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="[1-9][0-9]{0,4}\.[0-9]{2}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Waehrung">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="3"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="Sonderversicherung" minOccurs="0">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Betrag">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="[1-9][0-9]{0,4}\.[0-9]{2}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Waehrung">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="3"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="Nachnahme" minOccurs="0">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Betrag">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="[1-9][0-9]{0,4}\.[0-9]{2}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Waehrung">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="3"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Zahlungsart">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <enumeration value="1"/>
 *                         <enumeration value="2"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="Abholhinweise" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="128"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Zustellhinweise" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="128"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="TelefonEmpfangsbestaetigung" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="0"/>
 *               <maxLength value="25"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="SendungsPosition">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="AnzahlPackstuecke">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="[1-9][0-9]?"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Gewicht">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <pattern value="[1-9][0-9]{0,2}\.[0-9]{2}"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Inhalt">
 *                     <simpleType>
 *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         <minLength value="1"/>
 *                         <maxLength value="40"/>
 *                       </restriction>
 *                     </simpleType>
 *                   </element>
 *                   <element name="Abmessungen" minOccurs="0">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="Laenge">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <pattern value="[0-9]{1,5}\.[0-9]{1}"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="Breite">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <pattern value="[0-9]{1,5}\.[0-9]{1}"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="Hoehe">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <pattern value="[0-9]{1,5}\.[0-9]{1}"/>
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
@XmlType(name = "Sendung", propOrder = {
    "sendungsnummerAX4",
    "frachtbriefnummer",
    "versender",
    "benutzername",
    "status",
    "kundenreferenz",
    "abholadresse",
    "empfaenger",
    "service",
    "abholdatum",
    "zustelldatum",
    "unfrei",
    "selbstanlieferung",
    "selbstabholung",
    "warenwert",
    "sonderversicherung",
    "nachnahme",
    "abholhinweise",
    "zustellhinweise",
    "telefonEmpfangsbestaetigung",
    "sendungsPosition"
})
public class Sendung {

    @XmlElement(name = "SendungsnummerAX4")
    protected String sendungsnummerAX4;
    @XmlElement(name = "Frachtbriefnummer")
    protected String frachtbriefnummer;
    @XmlElement(name = "Versender", required = true)
    protected String versender;
    @XmlElement(name = "Benutzername", required = true)
    protected String benutzername;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "Kundenreferenz")
    protected String kundenreferenz;
    @XmlElement(name = "Abholadresse", required = true)
    protected Sendung.Abholadresse abholadresse;
    @XmlElement(name = "Empfaenger", required = true)
    protected Sendung.Empfaenger empfaenger;
    @XmlElement(name = "Service", required = true)
    protected String service;
    @XmlElement(name = "Abholdatum", required = true)
    protected Sendung.Abholdatum abholdatum;
    @XmlElement(name = "Zustelldatum")
    protected Sendung.Zustelldatum zustelldatum;
    @XmlElement(required = true)
    protected String unfrei;
    @XmlElement(name = "Selbstanlieferung", required = true)
    protected String selbstanlieferung;
    @XmlElement(name = "Selbstabholung", required = true)
    protected String selbstabholung;
    @XmlElement(name = "Warenwert")
    protected Sendung.Warenwert warenwert;
    @XmlElement(name = "Sonderversicherung")
    protected Sendung.Sonderversicherung sonderversicherung;
    @XmlElement(name = "Nachnahme")
    protected Sendung.Nachnahme nachnahme;
    @XmlElement(name = "Abholhinweise")
    protected String abholhinweise;
    @XmlElement(name = "Zustellhinweise")
    protected String zustellhinweise;
    @XmlElement(name = "TelefonEmpfangsbestaetigung")
    protected String telefonEmpfangsbestaetigung;
    @XmlElement(name = "SendungsPosition", required = true)
    protected Sendung.SendungsPosition sendungsPosition;

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
     * Gets the value of the versender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersender() {
        return versender;
    }

    /**
     * Sets the value of the versender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersender(String value) {
        this.versender = value;
    }

    /**
     * Gets the value of the benutzername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * Sets the value of the benutzername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenutzername(String value) {
        this.benutzername = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the kundenreferenz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKundenreferenz() {
        return kundenreferenz;
    }

    /**
     * Sets the value of the kundenreferenz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKundenreferenz(String value) {
        this.kundenreferenz = value;
    }

    /**
     * Gets the value of the abholadresse property.
     * 
     * @return
     *     possible object is
     *     {@link Sendung.Abholadresse }
     *     
     */
    public Sendung.Abholadresse getAbholadresse() {
        return abholadresse;
    }

    /**
     * Sets the value of the abholadresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sendung.Abholadresse }
     *     
     */
    public void setAbholadresse(Sendung.Abholadresse value) {
        this.abholadresse = value;
    }

    /**
     * Gets the value of the empfaenger property.
     * 
     * @return
     *     possible object is
     *     {@link Sendung.Empfaenger }
     *     
     */
    public Sendung.Empfaenger getEmpfaenger() {
        return empfaenger;
    }

    /**
     * Sets the value of the empfaenger property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sendung.Empfaenger }
     *     
     */
    public void setEmpfaenger(Sendung.Empfaenger value) {
        this.empfaenger = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setService(String value) {
        this.service = value;
    }

    /**
     * Gets the value of the abholdatum property.
     * 
     * @return
     *     possible object is
     *     {@link Sendung.Abholdatum }
     *     
     */
    public Sendung.Abholdatum getAbholdatum() {
        return abholdatum;
    }

    /**
     * Sets the value of the abholdatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sendung.Abholdatum }
     *     
     */
    public void setAbholdatum(Sendung.Abholdatum value) {
        this.abholdatum = value;
    }

    /**
     * Gets the value of the zustelldatum property.
     * 
     * @return
     *     possible object is
     *     {@link Sendung.Zustelldatum }
     *     
     */
    public Sendung.Zustelldatum getZustelldatum() {
        return zustelldatum;
    }

    /**
     * Sets the value of the zustelldatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sendung.Zustelldatum }
     *     
     */
    public void setZustelldatum(Sendung.Zustelldatum value) {
        this.zustelldatum = value;
    }

    /**
     * Gets the value of the unfrei property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnfrei() {
        return unfrei;
    }

    /**
     * Sets the value of the unfrei property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnfrei(String value) {
        this.unfrei = value;
    }

    /**
     * Gets the value of the selbstanlieferung property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelbstanlieferung() {
        return selbstanlieferung;
    }

    /**
     * Sets the value of the selbstanlieferung property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelbstanlieferung(String value) {
        this.selbstanlieferung = value;
    }

    /**
     * Gets the value of the selbstabholung property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelbstabholung() {
        return selbstabholung;
    }

    /**
     * Sets the value of the selbstabholung property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelbstabholung(String value) {
        this.selbstabholung = value;
    }

    /**
     * Gets the value of the warenwert property.
     * 
     * @return
     *     possible object is
     *     {@link Sendung.Warenwert }
     *     
     */
    public Sendung.Warenwert getWarenwert() {
        return warenwert;
    }

    /**
     * Sets the value of the warenwert property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sendung.Warenwert }
     *     
     */
    public void setWarenwert(Sendung.Warenwert value) {
        this.warenwert = value;
    }

    /**
     * Gets the value of the sonderversicherung property.
     * 
     * @return
     *     possible object is
     *     {@link Sendung.Sonderversicherung }
     *     
     */
    public Sendung.Sonderversicherung getSonderversicherung() {
        return sonderversicherung;
    }

    /**
     * Sets the value of the sonderversicherung property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sendung.Sonderversicherung }
     *     
     */
    public void setSonderversicherung(Sendung.Sonderversicherung value) {
        this.sonderversicherung = value;
    }

    /**
     * Gets the value of the nachnahme property.
     * 
     * @return
     *     possible object is
     *     {@link Sendung.Nachnahme }
     *     
     */
    public Sendung.Nachnahme getNachnahme() {
        return nachnahme;
    }

    /**
     * Sets the value of the nachnahme property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sendung.Nachnahme }
     *     
     */
    public void setNachnahme(Sendung.Nachnahme value) {
        this.nachnahme = value;
    }

    /**
     * Gets the value of the abholhinweise property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbholhinweise() {
        return abholhinweise;
    }

    /**
     * Sets the value of the abholhinweise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbholhinweise(String value) {
        this.abholhinweise = value;
    }

    /**
     * Gets the value of the zustellhinweise property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZustellhinweise() {
        return zustellhinweise;
    }

    /**
     * Sets the value of the zustellhinweise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZustellhinweise(String value) {
        this.zustellhinweise = value;
    }

    /**
     * Gets the value of the telefonEmpfangsbestaetigung property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefonEmpfangsbestaetigung() {
        return telefonEmpfangsbestaetigung;
    }

    /**
     * Sets the value of the telefonEmpfangsbestaetigung property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefonEmpfangsbestaetigung(String value) {
        this.telefonEmpfangsbestaetigung = value;
    }

    /**
     * Gets the value of the sendungsPosition property.
     * 
     * @return
     *     possible object is
     *     {@link Sendung.SendungsPosition }
     *     
     */
    public Sendung.SendungsPosition getSendungsPosition() {
        return sendungsPosition;
    }

    /**
     * Sets the value of the sendungsPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sendung.SendungsPosition }
     *     
     */
    public void setSendungsPosition(Sendung.SendungsPosition value) {
        this.sendungsPosition = value;
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
     *         <element name="Firmenname1">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="60"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Firmenname2" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="60"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Abteilung" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="40"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Strasse1">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="35"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Hausnummer" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="10"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Strasse2" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="25"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Land">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="[A-Za-z]{1,3}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Postleitzahl">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="9"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Stadt">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="30"/>
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
        "firmenname1",
        "firmenname2",
        "abteilung",
        "strasse1",
        "hausnummer",
        "strasse2",
        "land",
        "postleitzahl",
        "stadt"
    })
    public static class Abholadresse {

        @XmlElement(name = "Firmenname1", required = true)
        protected String firmenname1;
        @XmlElement(name = "Firmenname2")
        protected String firmenname2;
        @XmlElement(name = "Abteilung")
        protected String abteilung;
        @XmlElement(name = "Strasse1", required = true)
        protected String strasse1;
        @XmlElement(name = "Hausnummer")
        protected String hausnummer;
        @XmlElement(name = "Strasse2")
        protected String strasse2;
        @XmlElement(name = "Land", required = true)
        protected String land;
        @XmlElement(name = "Postleitzahl", required = true)
        protected String postleitzahl;
        @XmlElement(name = "Stadt", required = true)
        protected String stadt;

        /**
         * Gets the value of the firmenname1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFirmenname1() {
            return firmenname1;
        }

        /**
         * Sets the value of the firmenname1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFirmenname1(String value) {
            this.firmenname1 = value;
        }

        /**
         * Gets the value of the firmenname2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFirmenname2() {
            return firmenname2;
        }

        /**
         * Sets the value of the firmenname2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFirmenname2(String value) {
            this.firmenname2 = value;
        }

        /**
         * Gets the value of the abteilung property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAbteilung() {
            return abteilung;
        }

        /**
         * Sets the value of the abteilung property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAbteilung(String value) {
            this.abteilung = value;
        }

        /**
         * Gets the value of the strasse1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStrasse1() {
            return strasse1;
        }

        /**
         * Sets the value of the strasse1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStrasse1(String value) {
            this.strasse1 = value;
        }

        /**
         * Gets the value of the hausnummer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHausnummer() {
            return hausnummer;
        }

        /**
         * Sets the value of the hausnummer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHausnummer(String value) {
            this.hausnummer = value;
        }

        /**
         * Gets the value of the strasse2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStrasse2() {
            return strasse2;
        }

        /**
         * Sets the value of the strasse2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStrasse2(String value) {
            this.strasse2 = value;
        }

        /**
         * Gets the value of the land property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLand() {
            return land;
        }

        /**
         * Sets the value of the land property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLand(String value) {
            this.land = value;
        }

        /**
         * Gets the value of the postleitzahl property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPostleitzahl() {
            return postleitzahl;
        }

        /**
         * Sets the value of the postleitzahl property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPostleitzahl(String value) {
            this.postleitzahl = value;
        }

        /**
         * Gets the value of the stadt property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStadt() {
            return stadt;
        }

        /**
         * Sets the value of the stadt property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStadt(String value) {
            this.stadt = value;
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
     *       <sequence>
     *         <element name="Datum">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).(19|20)[0-9]{2}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="UhrzeitVon" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="UhrzeitBis" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
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
        "datum",
        "uhrzeitVon",
        "uhrzeitBis"
    })
    public static class Abholdatum {

        @XmlElement(name = "Datum", required = true)
        protected String datum;
        @XmlElement(name = "UhrzeitVon")
        protected String uhrzeitVon;
        @XmlElement(name = "UhrzeitBis")
        protected String uhrzeitBis;

        /**
         * Gets the value of the datum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDatum() {
            return datum;
        }

        /**
         * Sets the value of the datum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDatum(String value) {
            this.datum = value;
        }

        /**
         * Gets the value of the uhrzeitVon property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUhrzeitVon() {
            return uhrzeitVon;
        }

        /**
         * Sets the value of the uhrzeitVon property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUhrzeitVon(String value) {
            this.uhrzeitVon = value;
        }

        /**
         * Gets the value of the uhrzeitBis property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUhrzeitBis() {
            return uhrzeitBis;
        }

        /**
         * Sets the value of the uhrzeitBis property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUhrzeitBis(String value) {
            this.uhrzeitBis = value;
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
     *       <sequence>
     *         <element name="Firmenname1">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="60"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Firmenname2" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="60"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Abteilung" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="40"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Strasse1">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="35"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Hausnummer" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="10"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Strasse2" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="35"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Land">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="[A-Za-z]{1,3}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Postleitzahl">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="9"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Stadt">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="30"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Ansprechpartner" minOccurs="0">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="Telefon">
     *                     <complexType>
     *                       <complexContent>
     *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           <sequence>
     *                             <element name="LaenderPrefix">
     *                               <simpleType>
     *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   <pattern value="[0]{2}[1-9]{1,2}"/>
     *                                 </restriction>
     *                               </simpleType>
     *                             </element>
     *                             <element name="Ortsvorwahl">
     *                               <simpleType>
     *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   <pattern value="[0-9]{1,7}"/>
     *                                 </restriction>
     *                               </simpleType>
     *                             </element>
     *                             <element name="Telefonnummer">
     *                               <simpleType>
     *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   <pattern value="[0-9]{1,10}"/>
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
    @XmlType(name = "", propOrder = {
        "firmenname1",
        "firmenname2",
        "abteilung",
        "strasse1",
        "hausnummer",
        "strasse2",
        "land",
        "postleitzahl",
        "stadt",
        "ansprechpartner"
    })
    public static class Empfaenger {

        @XmlElement(name = "Firmenname1", required = true)
        protected String firmenname1;
        @XmlElement(name = "Firmenname2")
        protected String firmenname2;
        @XmlElement(name = "Abteilung")
        protected String abteilung;
        @XmlElement(name = "Strasse1", required = true)
        protected String strasse1;
        @XmlElement(name = "Hausnummer")
        protected String hausnummer;
        @XmlElement(name = "Strasse2")
        protected String strasse2;
        @XmlElement(name = "Land", required = true)
        protected String land;
        @XmlElement(name = "Postleitzahl", required = true)
        protected String postleitzahl;
        @XmlElement(name = "Stadt", required = true)
        protected String stadt;
        @XmlElement(name = "Ansprechpartner")
        protected Sendung.Empfaenger.Ansprechpartner ansprechpartner;

        /**
         * Gets the value of the firmenname1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFirmenname1() {
            return firmenname1;
        }

        /**
         * Sets the value of the firmenname1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFirmenname1(String value) {
            this.firmenname1 = value;
        }

        /**
         * Gets the value of the firmenname2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFirmenname2() {
            return firmenname2;
        }

        /**
         * Sets the value of the firmenname2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFirmenname2(String value) {
            this.firmenname2 = value;
        }

        /**
         * Gets the value of the abteilung property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAbteilung() {
            return abteilung;
        }

        /**
         * Sets the value of the abteilung property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAbteilung(String value) {
            this.abteilung = value;
        }

        /**
         * Gets the value of the strasse1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStrasse1() {
            return strasse1;
        }

        /**
         * Sets the value of the strasse1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStrasse1(String value) {
            this.strasse1 = value;
        }

        /**
         * Gets the value of the hausnummer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHausnummer() {
            return hausnummer;
        }

        /**
         * Sets the value of the hausnummer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHausnummer(String value) {
            this.hausnummer = value;
        }

        /**
         * Gets the value of the strasse2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStrasse2() {
            return strasse2;
        }

        /**
         * Sets the value of the strasse2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStrasse2(String value) {
            this.strasse2 = value;
        }

        /**
         * Gets the value of the land property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLand() {
            return land;
        }

        /**
         * Sets the value of the land property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLand(String value) {
            this.land = value;
        }

        /**
         * Gets the value of the postleitzahl property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPostleitzahl() {
            return postleitzahl;
        }

        /**
         * Sets the value of the postleitzahl property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPostleitzahl(String value) {
            this.postleitzahl = value;
        }

        /**
         * Gets the value of the stadt property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStadt() {
            return stadt;
        }

        /**
         * Sets the value of the stadt property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStadt(String value) {
            this.stadt = value;
        }

        /**
         * Gets the value of the ansprechpartner property.
         * 
         * @return
         *     possible object is
         *     {@link Sendung.Empfaenger.Ansprechpartner }
         *     
         */
        public Sendung.Empfaenger.Ansprechpartner getAnsprechpartner() {
            return ansprechpartner;
        }

        /**
         * Sets the value of the ansprechpartner property.
         * 
         * @param value
         *     allowed object is
         *     {@link Sendung.Empfaenger.Ansprechpartner }
         *     
         */
        public void setAnsprechpartner(Sendung.Empfaenger.Ansprechpartner value) {
            this.ansprechpartner = value;
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
         *         <element name="Telefon">
         *           <complexType>
         *             <complexContent>
         *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 <sequence>
         *                   <element name="LaenderPrefix">
         *                     <simpleType>
         *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         <pattern value="[0]{2}[1-9]{1,2}"/>
         *                       </restriction>
         *                     </simpleType>
         *                   </element>
         *                   <element name="Ortsvorwahl">
         *                     <simpleType>
         *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         <pattern value="[0-9]{1,7}"/>
         *                       </restriction>
         *                     </simpleType>
         *                   </element>
         *                   <element name="Telefonnummer">
         *                     <simpleType>
         *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         <pattern value="[0-9]{1,10}"/>
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
            "telefon"
        })
        public static class Ansprechpartner {

            @XmlElement(name = "Telefon", required = true)
            protected Sendung.Empfaenger.Ansprechpartner.Telefon telefon;

            /**
             * Gets the value of the telefon property.
             * 
             * @return
             *     possible object is
             *     {@link Sendung.Empfaenger.Ansprechpartner.Telefon }
             *     
             */
            public Sendung.Empfaenger.Ansprechpartner.Telefon getTelefon() {
                return telefon;
            }

            /**
             * Sets the value of the telefon property.
             * 
             * @param value
             *     allowed object is
             *     {@link Sendung.Empfaenger.Ansprechpartner.Telefon }
             *     
             */
            public void setTelefon(Sendung.Empfaenger.Ansprechpartner.Telefon value) {
                this.telefon = value;
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
             *         <element name="LaenderPrefix">
             *           <simpleType>
             *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               <pattern value="[0]{2}[1-9]{1,2}"/>
             *             </restriction>
             *           </simpleType>
             *         </element>
             *         <element name="Ortsvorwahl">
             *           <simpleType>
             *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               <pattern value="[0-9]{1,7}"/>
             *             </restriction>
             *           </simpleType>
             *         </element>
             *         <element name="Telefonnummer">
             *           <simpleType>
             *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               <pattern value="[0-9]{1,10}"/>
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
                "laenderPrefix",
                "ortsvorwahl",
                "telefonnummer"
            })
            public static class Telefon {

                @XmlElement(name = "LaenderPrefix", required = true)
                protected String laenderPrefix;
                @XmlElement(name = "Ortsvorwahl", required = true)
                protected String ortsvorwahl;
                @XmlElement(name = "Telefonnummer", required = true)
                protected String telefonnummer;

                /**
                 * Gets the value of the laenderPrefix property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLaenderPrefix() {
                    return laenderPrefix;
                }

                /**
                 * Sets the value of the laenderPrefix property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLaenderPrefix(String value) {
                    this.laenderPrefix = value;
                }

                /**
                 * Gets the value of the ortsvorwahl property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOrtsvorwahl() {
                    return ortsvorwahl;
                }

                /**
                 * Sets the value of the ortsvorwahl property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOrtsvorwahl(String value) {
                    this.ortsvorwahl = value;
                }

                /**
                 * Gets the value of the telefonnummer property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTelefonnummer() {
                    return telefonnummer;
                }

                /**
                 * Sets the value of the telefonnummer property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTelefonnummer(String value) {
                    this.telefonnummer = value;
                }

            }

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
     *       <sequence>
     *         <element name="Betrag">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="[1-9][0-9]{0,4}\.[0-9]{2}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Waehrung">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="3"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Zahlungsart">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <enumeration value="1"/>
     *               <enumeration value="2"/>
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
        "betrag",
        "waehrung",
        "zahlungsart"
    })
    public static class Nachnahme {

        @XmlElement(name = "Betrag", required = true)
        protected String betrag;
        @XmlElement(name = "Waehrung", required = true)
        protected String waehrung;
        @XmlElement(name = "Zahlungsart", required = true)
        protected String zahlungsart;

        /**
         * Gets the value of the betrag property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBetrag() {
            return betrag;
        }

        /**
         * Sets the value of the betrag property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBetrag(String value) {
            this.betrag = value;
        }

        /**
         * Gets the value of the waehrung property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWaehrung() {
            return waehrung;
        }

        /**
         * Sets the value of the waehrung property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWaehrung(String value) {
            this.waehrung = value;
        }

        /**
         * Gets the value of the zahlungsart property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZahlungsart() {
            return zahlungsart;
        }

        /**
         * Sets the value of the zahlungsart property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZahlungsart(String value) {
            this.zahlungsart = value;
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
     *       <sequence>
     *         <element name="AnzahlPackstuecke">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="[1-9][0-9]?"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Gewicht">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="[1-9][0-9]{0,2}\.[0-9]{2}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Inhalt">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="40"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Abmessungen" minOccurs="0">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="Laenge">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <pattern value="[0-9]{1,5}\.[0-9]{1}"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="Breite">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <pattern value="[0-9]{1,5}\.[0-9]{1}"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="Hoehe">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <pattern value="[0-9]{1,5}\.[0-9]{1}"/>
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
        "anzahlPackstuecke",
        "gewicht",
        "inhalt",
        "abmessungen"
    })
    public static class SendungsPosition {

        @XmlElement(name = "AnzahlPackstuecke", required = true)
        protected String anzahlPackstuecke;
        @XmlElement(name = "Gewicht", required = true)
        protected String gewicht;
        @XmlElement(name = "Inhalt", required = true)
        protected String inhalt;
        @XmlElement(name = "Abmessungen")
        protected Sendung.SendungsPosition.Abmessungen abmessungen;

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
         * Gets the value of the gewicht property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGewicht() {
            return gewicht;
        }

        /**
         * Sets the value of the gewicht property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGewicht(String value) {
            this.gewicht = value;
        }

        /**
         * Gets the value of the inhalt property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInhalt() {
            return inhalt;
        }

        /**
         * Sets the value of the inhalt property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInhalt(String value) {
            this.inhalt = value;
        }

        /**
         * Gets the value of the abmessungen property.
         * 
         * @return
         *     possible object is
         *     {@link Sendung.SendungsPosition.Abmessungen }
         *     
         */
        public Sendung.SendungsPosition.Abmessungen getAbmessungen() {
            return abmessungen;
        }

        /**
         * Sets the value of the abmessungen property.
         * 
         * @param value
         *     allowed object is
         *     {@link Sendung.SendungsPosition.Abmessungen }
         *     
         */
        public void setAbmessungen(Sendung.SendungsPosition.Abmessungen value) {
            this.abmessungen = value;
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
         *         <element name="Laenge">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <pattern value="[0-9]{1,5}\.[0-9]{1}"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="Breite">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <pattern value="[0-9]{1,5}\.[0-9]{1}"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="Hoehe">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <pattern value="[0-9]{1,5}\.[0-9]{1}"/>
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
            "laenge",
            "breite",
            "hoehe"
        })
        public static class Abmessungen {

            @XmlElement(name = "Laenge", required = true)
            protected String laenge;
            @XmlElement(name = "Breite", required = true)
            protected String breite;
            @XmlElement(name = "Hoehe", required = true)
            protected String hoehe;

            /**
             * Gets the value of the laenge property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLaenge() {
                return laenge;
            }

            /**
             * Sets the value of the laenge property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLaenge(String value) {
                this.laenge = value;
            }

            /**
             * Gets the value of the breite property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBreite() {
                return breite;
            }

            /**
             * Sets the value of the breite property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBreite(String value) {
                this.breite = value;
            }

            /**
             * Gets the value of the hoehe property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHoehe() {
                return hoehe;
            }

            /**
             * Sets the value of the hoehe property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHoehe(String value) {
                this.hoehe = value;
            }

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
     *       <sequence>
     *         <element name="Betrag">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="[1-9][0-9]{0,4}\.[0-9]{2}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Waehrung">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="3"/>
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
        "betrag",
        "waehrung"
    })
    public static class Sonderversicherung {

        @XmlElement(name = "Betrag", required = true)
        protected String betrag;
        @XmlElement(name = "Waehrung", required = true)
        protected String waehrung;

        /**
         * Gets the value of the betrag property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBetrag() {
            return betrag;
        }

        /**
         * Sets the value of the betrag property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBetrag(String value) {
            this.betrag = value;
        }

        /**
         * Gets the value of the waehrung property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWaehrung() {
            return waehrung;
        }

        /**
         * Sets the value of the waehrung property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWaehrung(String value) {
            this.waehrung = value;
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
     *       <sequence>
     *         <element name="Betrag">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="[1-9][0-9]{0,4}\.[0-9]{2}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="Waehrung">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <minLength value="1"/>
     *               <maxLength value="3"/>
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
        "betrag",
        "waehrung"
    })
    public static class Warenwert {

        @XmlElement(name = "Betrag", required = true)
        protected String betrag;
        @XmlElement(name = "Waehrung", required = true)
        protected String waehrung;

        /**
         * Gets the value of the betrag property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBetrag() {
            return betrag;
        }

        /**
         * Sets the value of the betrag property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBetrag(String value) {
            this.betrag = value;
        }

        /**
         * Gets the value of the waehrung property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWaehrung() {
            return waehrung;
        }

        /**
         * Sets the value of the waehrung property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWaehrung(String value) {
            this.waehrung = value;
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
     *       <sequence>
     *         <element name="Datum">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).(19|20)[0-9]{2}"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="UhrzeitVon" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
     *             </restriction>
     *           </simpleType>
     *         </element>
     *         <element name="UhrzeitBis" minOccurs="0">
     *           <simpleType>
     *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               <pattern value="([01][0-9]|2[0-3]):[0-5][0-9]"/>
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
        "datum",
        "uhrzeitVon",
        "uhrzeitBis"
    })
    public static class Zustelldatum {

        @XmlElement(name = "Datum", required = true)
        protected String datum;
        @XmlElement(name = "UhrzeitVon")
        protected String uhrzeitVon;
        @XmlElement(name = "UhrzeitBis")
        protected String uhrzeitBis;

        /**
         * Gets the value of the datum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDatum() {
            return datum;
        }

        /**
         * Sets the value of the datum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDatum(String value) {
            this.datum = value;
        }

        /**
         * Gets the value of the uhrzeitVon property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUhrzeitVon() {
            return uhrzeitVon;
        }

        /**
         * Sets the value of the uhrzeitVon property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUhrzeitVon(String value) {
            this.uhrzeitVon = value;
        }

        /**
         * Gets the value of the uhrzeitBis property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUhrzeitBis() {
            return uhrzeitBis;
        }

        /**
         * Sets the value of the uhrzeitBis property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUhrzeitBis(String value) {
            this.uhrzeitBis = value;
        }

    }

}
