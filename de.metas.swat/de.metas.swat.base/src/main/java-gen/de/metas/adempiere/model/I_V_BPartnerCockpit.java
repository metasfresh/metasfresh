/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package de.metas.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/** Generated Interface for V_BPartnerCockpit
 *  @author Adempiere (generated)
 *  @version Release 3.5.4a#464
 */
@SuppressWarnings("javadoc")
public interface I_V_BPartnerCockpit
{

    /** TableName=V_BPartnerCockpit */
    public static final String Table_Name = "V_BPartnerCockpit";

    /** AD_Table_ID=540060 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

	/** Set Sprache.
	  * Sprache für diesen Eintrag
	  */
	public void setAD_Language (java.lang.String AD_Language);

	/** Get Sprache.
	  * Sprache für diesen Eintrag
	  */
	public java.lang.String getAD_Language();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set Ansprechpartner.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get Ansprechpartner.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Straße und Nr..
	  * Adresszeile 1 für diesen Standort
	  */
	public void setAddress1 (java.lang.String Address1);

	/** Get Straße und Nr..
	  * Adresszeile 1 für diesen Standort
	  */
	public java.lang.String getAddress1();

    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

	/** Set _.
	  * Adresszeile 2 für diesen Standort
	  */
	public void setAddress2 (java.lang.String Address2);

	/** Get _.
	  * Adresszeile 2 für diesen Standort
	  */
	public java.lang.String getAddress2();

    /** Column name Address3 */
    public static final String COLUMNNAME_Address3 = "Address3";

	/** Set Adresszeile 3.
	  * Adresszeilee 3 für diesen Standort
	  */
	public void setAddress3 (java.lang.String Address3);

	/** Get Adresszeile 3.
	  * Adresszeilee 3 für diesen Standort
	  */
	public java.lang.String getAddress3();

    /** Column name Address4 */
    public static final String COLUMNNAME_Address4 = "Address4";

	/** Set Adresszusatz.
	  * Adresszeile 4 für diesen Standort
	  */
	public void setAddress4 (java.lang.String Address4);

	/** Get Adresszusatz.
	  * Adresszeile 4 für diesen Standort
	  */
	public java.lang.String getAddress4();

    /** Column name anschrifttyp */
    public static final String COLUMNNAME_anschrifttyp = "anschrifttyp";

	/** Set Anschrifttyp	  */
	public void setanschrifttyp (java.lang.String anschrifttyp);

	/** Get Anschrifttyp	  */
	public java.lang.String getanschrifttyp();

    /** Column name AnzahlAbos */
    public static final String COLUMNNAME_AnzahlAbos = "AnzahlAbos";

	/** Set Anzahl Abos	  */
	public void setAnzahlAbos (int AnzahlAbos);

	/** Get Anzahl Abos	  */
	public int getAnzahlAbos();

    /** Column name AutoSuche */
    public static final String COLUMNNAME_AutoSuche = "AutoSuche";

	/** Set Auto Suche	  */
	public void setAutoSuche (java.lang.String AutoSuche);

	/** Get Auto Suche	  */
	public java.lang.String getAutoSuche();

    /** Column name BPContactGreeting */
    public static final String COLUMNNAME_BPContactGreeting = "BPContactGreeting";

	/** Set Anrede.
	  * Anrede für den Geschäftspartner-Kontakt
	  */
	public void setBPContactGreeting (int BPContactGreeting);

	/** Get Anrede.
	  * Anrede für den Geschäftspartner-Kontakt
	  */
	public int getBPContactGreeting();

	public org.compiere.model.I_C_Greeting getBPContactGreet() throws RuntimeException;

	public void setBPContactGreet(org.compiere.model.I_C_Greeting BPContactGreet);

    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/** Set Geschäftspartnergruppe.
	  * Geschäftspartnergruppe
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/** Get Geschäftspartnergruppe.
	  * Geschäftspartnergruppe
	  */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException;

	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group);

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/** Set Land.
	  * Land
	  */
	public void setC_Country_ID (int C_Country_ID);

	/** Get Land.
	  * Land
	  */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException;

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column name C_Dunning_ID */
    public static final String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

	/** Set Mahnung.
	  * Dunning Rules for overdue invoices
	  */
	public void setC_Dunning_ID (int C_Dunning_ID);

	/** Get Mahnung.
	  * Dunning Rules for overdue invoices
	  */
	public int getC_Dunning_ID();

	public org.compiere.model.I_C_Dunning getC_Dunning() throws RuntimeException;

	public void setC_Dunning(org.compiere.model.I_C_Dunning C_Dunning);

    /** Column name C_Greeting_ID */
    public static final String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	/** Set Anrede.
	  * Anrede zum Druck auf Korrespondenz
	  */
	public void setC_Greeting_ID (int C_Greeting_ID);

	/** Get Anrede.
	  * Anrede zum Druck auf Korrespondenz
	  */
	public int getC_Greeting_ID();

	public org.compiere.model.I_C_Greeting getC_Greeting() throws RuntimeException;

	public void setC_Greeting(org.compiere.model.I_C_Greeting C_Greeting);

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Zahlungskondition.
	  * Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Zahlungskondition.
	  * Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/** Set Region.
	  * Identifiziert eine geographische Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Region.
	  * Identifiziert eine geographische Region
	  */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException;

	public void setC_Region(org.compiere.model.I_C_Region C_Region);

    /** Column name City */
    public static final String COLUMNNAME_City = "City";

	/** Set Ort.
	  * Name des Ortes
	  */
	public void setCity (java.lang.String City);

	/** Get Ort.
	  * Name des Ortes
	  */
	public java.lang.String getCity();

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Bemerkungen.
	  * Kommentar oder zusätzliche Information
	  */
	public void setComments (java.lang.String Comments);

	/** Get Bemerkungen.
	  * Kommentar oder zusätzliche Information
	  */
	public java.lang.String getComments();

    /** Column name Companyname */
    public static final String COLUMNNAME_Companyname = "Companyname";

	/** Set Firmenname	  */
	public void setCompanyname (java.lang.String Companyname);

	/** Get Firmenname	  */
	public java.lang.String getCompanyname();

    /** Column name ContactDescription */
    public static final String COLUMNNAME_ContactDescription = "ContactDescription";

	/** Set Kontakt-Beschreibung.
	  * Beschreibung des Kontaktes
	  */
	public void setContactDescription (java.lang.String ContactDescription);

	/** Get Kontakt-Beschreibung.
	  * Beschreibung des Kontaktes
	  */
	public java.lang.String getContactDescription();

    /** Column name ContactName */
    public static final String COLUMNNAME_ContactName = "ContactName";

	/** Set Nachname.
	  * Name des Geschäftspartner-Kontaktes
	  */
	public void setContactName (java.lang.String ContactName);

	/** Get Nachname.
	  * Name des Geschäftspartner-Kontaktes
	  */
	public java.lang.String getContactName();

    /** Column name CountryName */
    public static final String COLUMNNAME_CountryName = "CountryName";

	/** Set Land.
	  * Land
	  */
	public void setCountryName (java.lang.String CountryName);

	/** Get Land.
	  * Land
	  */
	public java.lang.String getCountryName();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name CreateSO */
    public static final String COLUMNNAME_CreateSO = "CreateSO";

	/** Set Auftrag anlegen	  */
	public void setCreateSO (java.lang.String CreateSO);

	/** Get Auftrag anlegen	  */
	public java.lang.String getCreateSO();

    /** Column name creditused */
    public static final String COLUMNNAME_creditused = "creditused";

	/** Set creditused	  */
	public void setcreditused (java.math.BigDecimal creditused);

	/** Get creditused	  */
	public java.math.BigDecimal getcreditused();

    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/** Set Lieferung durch.
	  * Wie der Auftrag geliefert wird
	  */
	public void setDeliveryViaRule (boolean DeliveryViaRule);

	/** Get Lieferung durch.
	  * Wie der Auftrag geliefert wird
	  */
	public boolean isDeliveryViaRule();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column name dl_endk */
    public static final String COLUMNNAME_dl_endk = "dl_endk";

	/** Set Downline ENKD	  */
	public void setdl_endk (int dl_endk);

	/** Get Downline ENKD	  */
	public int getdl_endk();

    /** Column name dl_vp */
    public static final String COLUMNNAME_dl_vp = "dl_vp";

	/** Set Downline VP	  */
	public void setdl_vp (int dl_vp);

	/** Get Downline VP	  */
	public int getdl_vp();

    /** Column name ebenenbonus */
    public static final String COLUMNNAME_ebenenbonus = "ebenenbonus";

	/** Set Ebenenbonus Punkte	  */
	public void setebenenbonus (java.math.BigDecimal ebenenbonus);

	/** Get Ebenenbonus Punkte	  */
	public java.math.BigDecimal getebenenbonus();

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail.
	  * EMail-Adresse
	  */
	public void setEMail (java.lang.String EMail);

	/** Get EMail.
	  * EMail-Adresse
	  */
	public java.lang.String getEMail();

    /** Column name EMailUser */
    public static final String COLUMNNAME_EMailUser = "EMailUser";

	/** Set EMail Nutzer-ID.
	  * Nutzer-Name/Konto (ID) im EMail-System
	  */
	public void setEMailUser (java.lang.String EMailUser);

	/** Get EMail Nutzer-ID.
	  * Nutzer-Name/Konto (ID) im EMail-System
	  */
	public java.lang.String getEMailUser();

    /** Column name Fax */
    public static final String COLUMNNAME_Fax = "Fax";

	/** Set Fax.
	  * Faxnummer
	  */
	public void setFax (java.lang.String Fax);

	/** Get Fax.
	  * Faxnummer
	  */
	public java.lang.String getFax();

    /** Column name Firstname */
    public static final String COLUMNNAME_Firstname = "Firstname";

	/** Set Vorname.
	  * Vorname
	  */
	public void setFirstname (java.lang.String Firstname);

	/** Get Vorname.
	  * Vorname
	  */
	public java.lang.String getFirstname();

    /** Column name FirstSale */
    public static final String COLUMNNAME_FirstSale = "FirstSale";

	/** Set Erster Verkauf.
	  * Datum des Ersten Verkaufs
	  */
	public void setFirstSale (java.sql.Timestamp FirstSale);

	/** Get Erster Verkauf.
	  * Datum des Ersten Verkaufs
	  */
	public java.sql.Timestamp getFirstSale();

    /** Column name FreightCostRule */
    public static final String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/** Set Frachtkostenberechnung.
	  * Methode zur Berechnung von Frachtkosten
	  */
	public void setFreightCostRule (boolean FreightCostRule);

	/** Get Frachtkostenberechnung.
	  * Methode zur Berechnung von Frachtkosten
	  */
	public boolean isFreightCostRule();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column name IsBillTo */
    public static final String COLUMNNAME_IsBillTo = "IsBillTo";

	/** Set Rechnungs-Adresse.
	  * Rechnungs-Adresse für diesen Geschäftspartner
	  */
	public void setIsBillTo (boolean IsBillTo);

	/** Get Rechnungs-Adresse.
	  * Rechnungs-Adresse für diesen Geschäftspartner
	  */
	public boolean isBillTo();

    /** Column name IsBillToDefault */
    public static final String COLUMNNAME_IsBillToDefault = "IsBillToDefault";

	/** Set Rechnung Standard Adresse	  */
	public void setIsBillToDefault (boolean IsBillToDefault);

	/** Get Rechnung Standard Adresse	  */
	public boolean isBillToDefault();

    /** Column name IsCompany */
    public static final String COLUMNNAME_IsCompany = "IsCompany";

	/** Set IsCompany	  */
	public void setIsCompany (boolean IsCompany);

	/** Get IsCompany	  */
	public boolean isCompany();

    /** Column name IsCustomer */
    public static final String COLUMNNAME_IsCustomer = "IsCustomer";

	/** Set Kunde.
	  * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	  */
	public void setIsCustomer (boolean IsCustomer);

	/** Get Kunde.
	  * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	  */
	public boolean isCustomer();

    /** Column name IsDefaultContact */
    public static final String COLUMNNAME_IsDefaultContact = "IsDefaultContact";

	/** Set Standard-Ansprechpartner	  */
	public void setIsDefaultContact (boolean IsDefaultContact);

	/** Get Standard-Ansprechpartner	  */
	public boolean isDefaultContact();

    /** Column name IsDefaultLocation */
    public static final String COLUMNNAME_IsDefaultLocation = "IsDefaultLocation";

	/** Set Default Location	  */
	public void setIsDefaultLocation (boolean IsDefaultLocation);

	/** Get Default Location	  */
	public boolean isDefaultLocation();

    /** Column name IsOneTime */
    public static final String COLUMNNAME_IsOneTime = "IsOneTime";

	/** Set One time transaction	  */
	public void setIsOneTime (boolean IsOneTime);

	/** Get One time transaction	  */
	public boolean isOneTime();

    /** Column name IsProspect */
    public static final String COLUMNNAME_IsProspect = "IsProspect";

	/** Set Aktiver Interessent/Kunde.
	  * Kennzeichnet einen Interessenten oder Kunden
	  */
	public void setIsProspect (boolean IsProspect);

	/** Get Aktiver Interessent/Kunde.
	  * Kennzeichnet einen Interessenten oder Kunden
	  */
	public boolean isProspect();

    /** Column name IsSalesRep */
    public static final String COLUMNNAME_IsSalesRep = "IsSalesRep";

	/** Set Vertriebsbeauftragter.
	  * Indicates if  the business partner is a sales representative or company agent
	  */
	public void setIsSalesRep (boolean IsSalesRep);

	/** Get Vertriebsbeauftragter.
	  * Indicates if  the business partner is a sales representative or company agent
	  */
	public boolean isSalesRep();

    /** Column name IsShipTo */
    public static final String COLUMNNAME_IsShipTo = "IsShipTo";

	/** Set Liefer-Adresse.
	  * Liefer-Adresse für den Geschäftspartner
	  */
	public void setIsShipTo (boolean IsShipTo);

	/** Get Liefer-Adresse.
	  * Liefer-Adresse für den Geschäftspartner
	  */
	public boolean isShipTo();

    /** Column name IsShipToDefault */
    public static final String COLUMNNAME_IsShipToDefault = "IsShipToDefault";

	/** Set Liefer Standard Adresse	  */
	public void setIsShipToDefault (boolean IsShipToDefault);

	/** Get Liefer Standard Adresse	  */
	public boolean isShipToDefault();

    /** Column name IsVendor */
    public static final String COLUMNNAME_IsVendor = "IsVendor";

	/** Set Lieferant.
	  * Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	  */
	public void setIsVendor (boolean IsVendor);

	/** Get Lieferant.
	  * Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	  */
	public boolean isVendor();

    /** Column name Lastname */
    public static final String COLUMNNAME_Lastname = "Lastname";

	/** Set Nachname	  */
	public void setLastname (java.lang.String Lastname);

	/** Get Nachname	  */
	public java.lang.String getLastname();

    /** Column name leistungsbonus */
    public static final String COLUMNNAME_leistungsbonus = "leistungsbonus";

	/** Set Leistungsbonus Punkte	  */
	public void setleistungsbonus (java.math.BigDecimal leistungsbonus);

	/** Get Leistungsbonus Punkte	  */
	public java.math.BigDecimal getleistungsbonus();

    /** Column name lifetimevalue */
    public static final String COLUMNNAME_lifetimevalue = "lifetimevalue";

	/** Set lifetimevalue	  */
	public void setlifetimevalue (java.math.BigDecimal lifetimevalue);

	/** Get lifetimevalue	  */
	public java.math.BigDecimal getlifetimevalue();

    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/** Set Rabatt-Schema.
	  * Schema um den prozentualen Rabatt zu berechnen
	  */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/** Get Rabatt-Schema.
	  * Schema um den prozentualen Rabatt zu berechnen
	  */
	public int getM_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException;

	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/** Set Name 2.
	  * Zusätzliche Bezeichnung
	  */
	public void setName2 (java.lang.String Name2);

	/** Get Name 2.
	  * Zusätzliche Bezeichnung
	  */
	public java.lang.String getName2();

    /** Column name NotificationType */
    public static final String COLUMNNAME_NotificationType = "NotificationType";

	/** Set Benachrichtigungs-Art.
	  * Art der Benachrichtigung
	  */
	public void setNotificationType (boolean NotificationType);

	/** Get Benachrichtigungs-Art.
	  * Art der Benachrichtigung
	  */
	public boolean isNotificationType();

    /** Column name OpenAmt */
    public static final String COLUMNNAME_OpenAmt = "OpenAmt";

	/** Set Open Amount.
	  * Open item amount
	  */
	public void setOpenAmt (java.math.BigDecimal OpenAmt);

	/** Get Open Amount.
	  * Open item amount
	  */
	public java.math.BigDecimal getOpenAmt();

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (java.lang.String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public java.lang.String getPhone();

    /** Column name Phone2 */
    public static final String COLUMNNAME_Phone2 = "Phone2";

	/** Set Telefon (alternativ).
	  * Alternative Telefonnummer
	  */
	public void setPhone2 (java.lang.String Phone2);

	/** Get Telefon (alternativ).
	  * Alternative Telefonnummer
	  */
	public java.lang.String getPhone2();

    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/** Set PLZ.
	  * Postleitzahl
	  */
	public void setPostal (java.lang.String Postal);

	/** Get PLZ.
	  * Postleitzahl
	  */
	public java.lang.String getPostal();

    /** Column name prov */
    public static final String COLUMNNAME_prov = "prov";

	/** Set Provision Kennz.	  */
	public void setprov (java.lang.String prov);

	/** Get Provision Kennz.	  */
	public java.lang.String getprov();

    /** Column name revenued30 */
    public static final String COLUMNNAME_revenued30 = "revenued30";

	/** Set revenued30	  */
	public void setrevenued30 (java.math.BigDecimal revenued30);

	/** Get revenued30	  */
	public java.math.BigDecimal getrevenued30();

    /** Column name revenuem0 */
    public static final String COLUMNNAME_revenuem0 = "revenuem0";

	/** Set revenuem0	  */
	public void setrevenuem0 (java.math.BigDecimal revenuem0);

	/** Get revenuem0	  */
	public java.math.BigDecimal getrevenuem0();

    /** Column name revenuem1 */
    public static final String COLUMNNAME_revenuem1 = "revenuem1";

	/** Set revenuem1	  */
	public void setrevenuem1 (java.math.BigDecimal revenuem1);

	/** Get revenuem1	  */
	public java.math.BigDecimal getrevenuem1();

    /** Column name revenuem10 */
    public static final String COLUMNNAME_revenuem10 = "revenuem10";

	/** Set revenuem10	  */
	public void setrevenuem10 (java.math.BigDecimal revenuem10);

	/** Get revenuem10	  */
	public java.math.BigDecimal getrevenuem10();

    /** Column name revenuem11 */
    public static final String COLUMNNAME_revenuem11 = "revenuem11";

	/** Set revenuem11	  */
	public void setrevenuem11 (java.math.BigDecimal revenuem11);

	/** Get revenuem11	  */
	public java.math.BigDecimal getrevenuem11();

    /** Column name revenuem12 */
    public static final String COLUMNNAME_revenuem12 = "revenuem12";

	/** Set revenuem12	  */
	public void setrevenuem12 (java.math.BigDecimal revenuem12);

	/** Get revenuem12	  */
	public java.math.BigDecimal getrevenuem12();

    /** Column name revenuem2 */
    public static final String COLUMNNAME_revenuem2 = "revenuem2";

	/** Set revenuem2	  */
	public void setrevenuem2 (java.math.BigDecimal revenuem2);

	/** Get revenuem2	  */
	public java.math.BigDecimal getrevenuem2();

    /** Column name revenuem3 */
    public static final String COLUMNNAME_revenuem3 = "revenuem3";

	/** Set revenuem3	  */
	public void setrevenuem3 (java.math.BigDecimal revenuem3);

	/** Get revenuem3	  */
	public java.math.BigDecimal getrevenuem3();

    /** Column name revenuem4 */
    public static final String COLUMNNAME_revenuem4 = "revenuem4";

	/** Set revenuem4	  */
	public void setrevenuem4 (java.math.BigDecimal revenuem4);

	/** Get revenuem4	  */
	public java.math.BigDecimal getrevenuem4();

    /** Column name revenuem5 */
    public static final String COLUMNNAME_revenuem5 = "revenuem5";

	/** Set revenuem5	  */
	public void setrevenuem5 (java.math.BigDecimal revenuem5);

	/** Get revenuem5	  */
	public java.math.BigDecimal getrevenuem5();

    /** Column name revenuem6 */
    public static final String COLUMNNAME_revenuem6 = "revenuem6";

	/** Set revenuem6	  */
	public void setrevenuem6 (java.math.BigDecimal revenuem6);

	/** Get revenuem6	  */
	public java.math.BigDecimal getrevenuem6();

    /** Column name revenuem7 */
    public static final String COLUMNNAME_revenuem7 = "revenuem7";

	/** Set revenuem7	  */
	public void setrevenuem7 (java.math.BigDecimal revenuem7);

	/** Get revenuem7	  */
	public java.math.BigDecimal getrevenuem7();

    /** Column name revenuem8 */
    public static final String COLUMNNAME_revenuem8 = "revenuem8";

	/** Set revenuem8	  */
	public void setrevenuem8 (java.math.BigDecimal revenuem8);

	/** Get revenuem8	  */
	public java.math.BigDecimal getrevenuem8();

    /** Column name revenuem9 */
    public static final String COLUMNNAME_revenuem9 = "revenuem9";

	/** Set revenuem9	  */
	public void setrevenuem9 (java.math.BigDecimal revenuem9);

	/** Get revenuem9	  */
	public java.math.BigDecimal getrevenuem9();

    /** Column name RevenueOneYear */
    public static final String COLUMNNAME_RevenueOneYear = "RevenueOneYear";

	/** Set Umsatz 12 Monate	  */
	public void setRevenueOneYear (java.math.BigDecimal RevenueOneYear);

	/** Get Umsatz 12 Monate	  */
	public java.math.BigDecimal getRevenueOneYear();

    /** Column name revenueq0 */
    public static final String COLUMNNAME_revenueq0 = "revenueq0";

	/** Set revenueq0	  */
	public void setrevenueq0 (java.math.BigDecimal revenueq0);

	/** Get revenueq0	  */
	public java.math.BigDecimal getrevenueq0();

    /** Column name revenueq1 */
    public static final String COLUMNNAME_revenueq1 = "revenueq1";

	/** Set revenueq1	  */
	public void setrevenueq1 (java.math.BigDecimal revenueq1);

	/** Get revenueq1	  */
	public java.math.BigDecimal getrevenueq1();

    /** Column name revenueq2 */
    public static final String COLUMNNAME_revenueq2 = "revenueq2";

	/** Set revenueq2	  */
	public void setrevenueq2 (java.math.BigDecimal revenueq2);

	/** Get revenueq2	  */
	public java.math.BigDecimal getrevenueq2();

    /** Column name revenueq3 */
    public static final String COLUMNNAME_revenueq3 = "revenueq3";

	/** Set revenueq3	  */
	public void setrevenueq3 (java.math.BigDecimal revenueq3);

	/** Get revenueq3	  */
	public java.math.BigDecimal getrevenueq3();

    /** Column name revenueq4 */
    public static final String COLUMNNAME_revenueq4 = "revenueq4";

	/** Set revenueq4	  */
	public void setrevenueq4 (java.math.BigDecimal revenueq4);

	/** Get revenueq4	  */
	public java.math.BigDecimal getrevenueq4();

    /** Column name revenuey0 */
    public static final String COLUMNNAME_revenuey0 = "revenuey0";

	/** Set revenuey0	  */
	public void setrevenuey0 (java.math.BigDecimal revenuey0);

	/** Get revenuey0	  */
	public java.math.BigDecimal getrevenuey0();

    /** Column name revenuey1 */
    public static final String COLUMNNAME_revenuey1 = "revenuey1";

	/** Set revenuey1	  */
	public void setrevenuey1 (java.math.BigDecimal revenuey1);

	/** Get revenuey1	  */
	public java.math.BigDecimal getrevenuey1();

    /** Column name revenuey2 */
    public static final String COLUMNNAME_revenuey2 = "revenuey2";

	/** Set revenuey2	  */
	public void setrevenuey2 (java.math.BigDecimal revenuey2);

	/** Get revenuey2	  */
	public java.math.BigDecimal getrevenuey2();

    /** Column name revenuey3 */
    public static final String COLUMNNAME_revenuey3 = "revenuey3";

	/** Set revenuey3	  */
	public void setrevenuey3 (java.math.BigDecimal revenuey3);

	/** Get revenuey3	  */
	public java.math.BigDecimal getrevenuey3();

    /** Column name revenuey4 */
    public static final String COLUMNNAME_revenuey4 = "revenuey4";

	/** Set revenuey4	  */
	public void setrevenuey4 (java.math.BigDecimal revenuey4);

	/** Get revenuey4	  */
	public java.math.BigDecimal getrevenuey4();

    /** Column name revenuey5 */
    public static final String COLUMNNAME_revenuey5 = "revenuey5";

	/** Set revenuey5	  */
	public void setrevenuey5 (java.math.BigDecimal revenuey5);

	/** Get revenuey5	  */
	public java.math.BigDecimal getrevenuey5();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (java.lang.String SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public java.lang.String getSalesRep_ID();

    /** Column name Search */
    public static final String COLUMNNAME_Search = "Search";

	/** Set Suche	  */
	public void setSearch (java.lang.String Search);

	/** Get Suche	  */
	public java.lang.String getSearch();

	/** Column name Search_Location */
	public static final String COLUMNNAME_Search_Location = "Search_Location";

	/** Set Search Location */
	public void setSearch_Location(java.lang.String Search_Location);

	/** Get Search Location */
	public java.lang.String getSearch_Location();

    /** Column name SO_CreditUsed */
    public static final String COLUMNNAME_SO_CreditUsed = "SO_CreditUsed";

	/** Set Kredit gewährt.
	  * Gegenwärtiger Aussenstand
	  */
	public void setSO_CreditUsed (java.math.BigDecimal SO_CreditUsed);

	/** Get Kredit gewährt.
	  * Gegenwärtiger Aussenstand
	  */
	public java.math.BigDecimal getSO_CreditUsed();

    /** Column name SOCreditStatus */
    public static final String COLUMNNAME_SOCreditStatus = "SOCreditStatus";

	/** Set Kreditstatus.
	  * Kreditstatus des Geschäftspartners
	  */
	public void setSOCreditStatus (java.lang.String SOCreditStatus);

	/** Get Kreditstatus.
	  * Kreditstatus des Geschäftspartners
	  */
	public java.lang.String getSOCreditStatus();

    /** Column name Suchname */
    public static final String COLUMNNAME_Suchname = "Suchname";

	/** Set Suchname.
	  * Alphanumeric identifier of the entity
	  */
	public void setSuchname (java.lang.String Suchname);

	/** Get Suchname.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getSuchname();

    /** Column name Title */
    public static final String COLUMNNAME_Title = "Title";

	/** Set Titel.
	  * Bezeichnung für diesen Eintrag
	  */
	public void setTitle (java.lang.String Title);

	/** Get Titel.
	  * Bezeichnung für diesen Eintrag
	  */
	public java.lang.String getTitle();

    /** Column name TotalOpenBalance */
    public static final String COLUMNNAME_TotalOpenBalance = "TotalOpenBalance";

	/** Set Offener Saldo.
	  * Gesamt der offenen Beträge in primärer Buchführungswährung
	  */
	public void setTotalOpenBalance (java.math.BigDecimal TotalOpenBalance);

	/** Get Offener Saldo.
	  * Gesamt der offenen Beträge in primärer Buchführungswährung
	  */
	public java.math.BigDecimal getTotalOpenBalance();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();

    /** Column name V_BPartnerCockpit_ID */
    public static final String COLUMNNAME_V_BPartnerCockpit_ID = "V_BPartnerCockpit_ID";

	/** Set V_BPartnerCockpit_ID	  */
	public void setV_BPartnerCockpit_ID (int V_BPartnerCockpit_ID);

	/** Get V_BPartnerCockpit_ID	  */
	public int getV_BPartnerCockpit_ID();

    /** Column name value */
    public static final String COLUMNNAME_value = "value";

	/** Set Suchschlüssel.
	  * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public void setvalue (java.lang.String value);

	/** Get Suchschlüssel.
	  * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public java.lang.String getvalue();
}