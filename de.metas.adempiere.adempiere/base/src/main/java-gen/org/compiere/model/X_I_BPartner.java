/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_BPartner
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_BPartner extends org.compiere.model.PO implements I_I_BPartner, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1051207302L;

    /** Standard Constructor */
    public X_I_BPartner (Properties ctx, int I_BPartner_ID, String trxName)
    {
      super (ctx, I_BPartner_ID, trxName);
      /** if (I_BPartner_ID == 0)
        {
			setI_BPartner_ID (0);
			setI_IsImported (false); // N
			setIsBillTo (false); // N
			setIsBillToContact_Default (false); // N
			setIsBillToDefault (false); // N
			setIsDefaultContact (false); // N
			setIsSEPASigned (false); // N
			setIsShipTo (false); // N
			setIsShipToContact_Default (false); // N
			setIsShipToDefault (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_BPartner (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Sprache.
		@param AD_Language 
		Sprache für diesen Eintrag
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Sprache für diesen Eintrag
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Straße und Nr..
		@param Address1 
		Adresszeile 1 für diesen Standort
	  */
	@Override
	public void setAddress1 (java.lang.String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Straße und Nr..
		@return Adresszeile 1 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Adresszusatz.
		@param Address2 
		Adresszeile 2 für diesen Standort
	  */
	@Override
	public void setAddress2 (java.lang.String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Adresszusatz.
		@return Adresszeile 2 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Adresszeile 3.
		@param Address3 
		Adresszeilee 3 für diesen Standort
	  */
	@Override
	public void setAddress3 (java.lang.String Address3)
	{
		set_Value (COLUMNNAME_Address3, Address3);
	}

	/** Get Adresszeile 3.
		@return Adresszeilee 3 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address3);
	}

	/** Set Adresszusatz.
		@param Address4 
		Adresszeile 4 für diesen Standort
	  */
	@Override
	public void setAddress4 (java.lang.String Address4)
	{
		set_Value (COLUMNNAME_Address4, Address4);
	}

	/** Get Adresszusatz.
		@return Adresszeile 4 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress4 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address4);
	}

	/** Set Geburtstag.
		@param Birthday 
		Birthday or Anniversary day
	  */
	@Override
	public void setBirthday (java.sql.Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	/** Get Geburtstag.
		@return Birthday or Anniversary day
	  */
	@Override
	public java.sql.Timestamp getBirthday () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Birthday);
	}

	/** Set Kontakt-Anrede.
		@param BPContactGreeting 
		Greeting for Business Partner Contact
	  */
	@Override
	public void setBPContactGreeting (java.lang.String BPContactGreeting)
	{
		set_Value (COLUMNNAME_BPContactGreeting, BPContactGreeting);
	}

	/** Get Kontakt-Anrede.
		@return Greeting for Business Partner Contact
	  */
	@Override
	public java.lang.String getBPContactGreeting () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPContactGreeting);
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bankverbindung des Geschäftspartners
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	/** Set Geschäftspartnergruppe.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	@Override
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Geschäftspartnergruppe.
		@return Business Partner Group
	  */
	@Override
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID 
		Country 
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Country 
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Greeting getC_Greeting() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Greeting_ID, org.compiere.model.I_C_Greeting.class);
	}

	@Override
	public void setC_Greeting(org.compiere.model.I_C_Greeting C_Greeting)
	{
		set_ValueFromPO(COLUMNNAME_C_Greeting_ID, org.compiere.model.I_C_Greeting.class, C_Greeting);
	}

	/** Set Anrede.
		@param C_Greeting_ID 
		Greeting to print on correspondence
	  */
	@Override
	public void setC_Greeting_ID (int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_Value (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_Value (COLUMNNAME_C_Greeting_ID, Integer.valueOf(C_Greeting_ID));
	}

	/** Get Anrede.
		@return Greeting to print on correspondence
	  */
	@Override
	public int getC_Greeting_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Greeting_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class);
	}

	@Override
	public void setC_InvoiceSchedule(org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class, C_InvoiceSchedule);
	}

	/** Set Terminplan Rechnung.
		@param C_InvoiceSchedule_ID 
		Plan für die Rechnungsstellung
	  */
	@Override
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, Integer.valueOf(C_InvoiceSchedule_ID));
	}

	/** Get Terminplan Rechnung.
		@return Plan für die Rechnungsstellung
	  */
	@Override
	public int getC_InvoiceSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	@Override
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	@Override
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ort.
		@param City 
		Identifies a City
	  */
	@Override
	public void setCity (java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	/** Get Ort.
		@return Identifies a City
	  */
	@Override
	public java.lang.String getCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	/** Set Bemerkungen.
		@param Comments 
		Comments or additional information
	  */
	@Override
	public void setComments (java.lang.String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Bemerkungen.
		@return Comments or additional information
	  */
	@Override
	public java.lang.String getComments () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Firmenname.
		@param Companyname Firmenname	  */
	@Override
	public void setCompanyname (java.lang.String Companyname)
	{
		set_Value (COLUMNNAME_Companyname, Companyname);
	}

	/** Get Firmenname.
		@return Firmenname	  */
	@Override
	public java.lang.String getCompanyname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Companyname);
	}

	/** Set Kontakt-Beschreibung.
		@param ContactDescription 
		Description of Contact
	  */
	@Override
	public void setContactDescription (java.lang.String ContactDescription)
	{
		set_Value (COLUMNNAME_ContactDescription, ContactDescription);
	}

	/** Get Kontakt-Beschreibung.
		@return Description of Contact
	  */
	@Override
	public java.lang.String getContactDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContactDescription);
	}

	/** Set ISO Ländercode.
		@param CountryCode 
		Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public void setCountryCode (java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Ländercode.
		@return Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public java.lang.String getCountryCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set D-U-N-S.
		@param DUNS 
		Dun & Bradstreet Number
	  */
	@Override
	public void setDUNS (java.lang.String DUNS)
	{
		set_Value (COLUMNNAME_DUNS, DUNS);
	}

	/** Get D-U-N-S.
		@return Dun & Bradstreet Number
	  */
	@Override
	public java.lang.String getDUNS () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DUNS);
	}

	/** Set eMail.
		@param EMail 
		EMail-Adresse
	  */
	@Override
	public void setEMail (java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get eMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Fax.
		@param Fax 
		Facsimile number
	  */
	@Override
	public void setFax (java.lang.String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Facsimile number
	  */
	@Override
	public java.lang.String getFax () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Fax);
	}

	/** Set Vorname.
		@param Firstname 
		Vorname
	  */
	@Override
	public void setFirstname (java.lang.String Firstname)
	{
		set_Value (COLUMNNAME_Firstname, Firstname);
	}

	/** Get Vorname.
		@return Vorname
	  */
	@Override
	public java.lang.String getFirstname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Firstname);
	}

	/** Set Erster Verkauf.
		@param FirstSale 
		Datum des Ersten Verkaufs
	  */
	@Override
	public void setFirstSale (java.sql.Timestamp FirstSale)
	{
		set_Value (COLUMNNAME_FirstSale, FirstSale);
	}

	/** Get Erster Verkauf.
		@return Datum des Ersten Verkaufs
	  */
	@Override
	public java.sql.Timestamp getFirstSale () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_FirstSale);
	}

	/** Set Gruppen-Schlüssel.
		@param GroupValue 
		Business Partner Group Key
	  */
	@Override
	public void setGroupValue (java.lang.String GroupValue)
	{
		set_Value (COLUMNNAME_GroupValue, GroupValue);
	}

	/** Get Gruppen-Schlüssel.
		@return Business Partner Group Key
	  */
	@Override
	public java.lang.String getGroupValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GroupValue);
	}

	/** Set Import - Geschäftspartner.
		@param I_BPartner_ID Import - Geschäftspartner	  */
	@Override
	public void setI_BPartner_ID (int I_BPartner_ID)
	{
		if (I_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_ID, Integer.valueOf(I_BPartner_ID));
	}

	/** Get Import - Geschäftspartner.
		@return Import - Geschäftspartner	  */
	@Override
	public int getI_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Messages generated from import process
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Importiert.
		@param I_IsImported 
		Has this import been processed
	  */
	@Override
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Importiert.
		@return Has this import been processed
	  */
	@Override
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IBAN.
		@param IBAN 
		International Bank Account Number
	  */
	@Override
	public void setIBAN (java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	/** Get IBAN.
		@return International Bank Account Number
	  */
	@Override
	public java.lang.String getIBAN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IBAN);
	}

	/** Set Interessengebiet.
		@param InterestAreaName 
		Name of the Interest Area
	  */
	@Override
	public void setInterestAreaName (java.lang.String InterestAreaName)
	{
		set_Value (COLUMNNAME_InterestAreaName, InterestAreaName);
	}

	/** Get Interessengebiet.
		@return Name of the Interest Area
	  */
	@Override
	public java.lang.String getInterestAreaName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InterestAreaName);
	}

	/** Set Status Terminplan.
		@param InvoiceSchedule Status Terminplan	  */
	@Override
	public void setInvoiceSchedule (java.lang.String InvoiceSchedule)
	{
		set_Value (COLUMNNAME_InvoiceSchedule, InvoiceSchedule);
	}

	/** Get Status Terminplan.
		@return Status Terminplan	  */
	@Override
	public java.lang.String getInvoiceSchedule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceSchedule);
	}

	/** Set Vorbelegung Rechnung.
		@param IsBillTo 
		Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public void setIsBillTo (boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/** Get Vorbelegung Rechnung.
		@return Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public boolean isBillTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnungskontakt.
		@param IsBillToContact_Default Rechnungskontakt	  */
	@Override
	public void setIsBillToContact_Default (boolean IsBillToContact_Default)
	{
		set_Value (COLUMNNAME_IsBillToContact_Default, Boolean.valueOf(IsBillToContact_Default));
	}

	/** Get Rechnungskontakt.
		@return Rechnungskontakt	  */
	@Override
	public boolean isBillToContact_Default () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillToContact_Default);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnung Standard Adresse.
		@param IsBillToDefault Rechnung Standard Adresse	  */
	@Override
	public void setIsBillToDefault (boolean IsBillToDefault)
	{
		set_Value (COLUMNNAME_IsBillToDefault, Boolean.valueOf(IsBillToDefault));
	}

	/** Get Rechnung Standard Adresse.
		@return Rechnung Standard Adresse	  */
	@Override
	public boolean isBillToDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillToDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Customer.
		@param IsCustomer 
		Indicates if this Business Partner is a Customer
	  */
	@Override
	public void setIsCustomer (boolean IsCustomer)
	{
		set_Value (COLUMNNAME_IsCustomer, Boolean.valueOf(IsCustomer));
	}

	/** Get Customer.
		@return Indicates if this Business Partner is a Customer
	  */
	@Override
	public boolean isCustomer () 
	{
		Object oo = get_Value(COLUMNNAME_IsCustomer);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard-Ansprechpartner.
		@param IsDefaultContact Standard-Ansprechpartner	  */
	@Override
	public void setIsDefaultContact (boolean IsDefaultContact)
	{
		set_Value (COLUMNNAME_IsDefaultContact, Boolean.valueOf(IsDefaultContact));
	}

	/** Get Standard-Ansprechpartner.
		@return Standard-Ansprechpartner	  */
	@Override
	public boolean isDefaultContact () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefaultContact);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Employee.
		@param IsEmployee 
		Indicates if  this Business Partner is an employee
	  */
	@Override
	public void setIsEmployee (boolean IsEmployee)
	{
		set_Value (COLUMNNAME_IsEmployee, Boolean.valueOf(IsEmployee));
	}

	/** Get Employee.
		@return Indicates if  this Business Partner is an employee
	  */
	@Override
	public boolean isEmployee () 
	{
		Object oo = get_Value(COLUMNNAME_IsEmployee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SEPA Signed.
		@param IsSEPASigned SEPA Signed	  */
	@Override
	public void setIsSEPASigned (boolean IsSEPASigned)
	{
		set_Value (COLUMNNAME_IsSEPASigned, Boolean.valueOf(IsSEPASigned));
	}

	/** Get SEPA Signed.
		@return SEPA Signed	  */
	@Override
	public boolean isSEPASigned () 
	{
		Object oo = get_Value(COLUMNNAME_IsSEPASigned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferstandard.
		@param IsShipTo 
		Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public void setIsShipTo (boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/** Get Lieferstandard.
		@return Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public boolean isShipTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferkontakt.
		@param IsShipToContact_Default Lieferkontakt	  */
	@Override
	public void setIsShipToContact_Default (boolean IsShipToContact_Default)
	{
		set_Value (COLUMNNAME_IsShipToContact_Default, Boolean.valueOf(IsShipToContact_Default));
	}

	/** Get Lieferkontakt.
		@return Lieferkontakt	  */
	@Override
	public boolean isShipToContact_Default () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipToContact_Default);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Liefer Standard Adresse.
		@param IsShipToDefault Liefer Standard Adresse	  */
	@Override
	public void setIsShipToDefault (boolean IsShipToDefault)
	{
		set_Value (COLUMNNAME_IsShipToDefault, Boolean.valueOf(IsShipToDefault));
	}

	/** Get Liefer Standard Adresse.
		@return Liefer Standard Adresse	  */
	@Override
	public boolean isShipToDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipToDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Vendor.
		@param IsVendor 
		Indicates if this Business Partner is a Vendor
	  */
	@Override
	public void setIsVendor (boolean IsVendor)
	{
		set_Value (COLUMNNAME_IsVendor, Boolean.valueOf(IsVendor));
	}

	/** Get Vendor.
		@return Indicates if this Business Partner is a Vendor
	  */
	@Override
	public boolean isVendor () 
	{
		Object oo = get_Value(COLUMNNAME_IsVendor);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Nachname.
		@param Lastname Nachname	  */
	@Override
	public void setLastname (java.lang.String Lastname)
	{
		set_Value (COLUMNNAME_Lastname, Lastname);
	}

	/** Get Nachname.
		@return Nachname	  */
	@Override
	public java.lang.String getLastname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lastname);
	}

	/** Set NAICS/SIC.
		@param NAICS 
		Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	@Override
	public void setNAICS (java.lang.String NAICS)
	{
		set_Value (COLUMNNAME_NAICS, NAICS);
	}

	/** Get NAICS/SIC.
		@return Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	@Override
	public java.lang.String getNAICS () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NAICS);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Name Zusatz.
		@param Name2 
		Zusätzliche Bezeichnung
	  */
	@Override
	public void setName2 (java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name Zusatz.
		@return Zusätzliche Bezeichnung
	  */
	@Override
	public java.lang.String getName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Name3.
		@param Name3 
		Zusätzliche Bezeichnung
	  */
	@Override
	public void setName3 (java.lang.String Name3)
	{
		set_Value (COLUMNNAME_Name3, Name3);
	}

	/** Get Name3.
		@return Zusätzliche Bezeichnung
	  */
	@Override
	public java.lang.String getName3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name3);
	}

	/** Set Organisations-Schlüssel.
		@param OrgValue 
		Suchschlüssel der Organisation
	  */
	@Override
	public void setOrgValue (java.lang.String OrgValue)
	{
		set_Value (COLUMNNAME_OrgValue, OrgValue);
	}

	/** Get Organisations-Schlüssel.
		@return Suchschlüssel der Organisation
	  */
	@Override
	public java.lang.String getOrgValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrgValue);
	}

	/** Set Kennwort.
		@param Password 
		Password of any length (case sensitive)
	  */
	@Override
	public void setPassword (java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Kennwort.
		@return Password of any length (case sensitive)
	  */
	@Override
	public java.lang.String getPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Password);
	}

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** Set Zahlungsweise.
		@param PaymentRule 
		Wie die Rechnung bezahlt wird
	  */
	@Override
	public void setPaymentRule (java.lang.String PaymentRule)
	{

		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	/** Get Zahlungsweise.
		@return Wie die Rechnung bezahlt wird
	  */
	@Override
	public java.lang.String getPaymentRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRule);
	}

	/** 
	 * PaymentRulePO AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULEPO_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULEPO_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULEPO_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULEPO_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULEPO_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULEPO_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULEPO_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULEPO_Mixed = "M";
	/** Set Zahlungsweise.
		@param PaymentRulePO 
		Möglichkeiten der Bezahlung einer Bestellung
	  */
	@Override
	public void setPaymentRulePO (java.lang.String PaymentRulePO)
	{

		set_Value (COLUMNNAME_PaymentRulePO, PaymentRulePO);
	}

	/** Get Zahlungsweise.
		@return Möglichkeiten der Bezahlung einer Bestellung
	  */
	@Override
	public java.lang.String getPaymentRulePO () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRulePO);
	}

	/** Set Zahlungskondition.
		@param PaymentTerm 
		Zahlungskondition
	  */
	@Override
	public void setPaymentTerm (java.lang.String PaymentTerm)
	{
		set_Value (COLUMNNAME_PaymentTerm, PaymentTerm);
	}

	/** Get Zahlungskondition.
		@return Zahlungskondition
	  */
	@Override
	public java.lang.String getPaymentTerm () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentTerm);
	}

	/** Set Telefon.
		@param Phone 
		Beschreibt eine Telefon Nummer
	  */
	@Override
	public void setPhone (java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Telefon.
		@return Beschreibt eine Telefon Nummer
	  */
	@Override
	public java.lang.String getPhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone);
	}

	/** Set Mobil.
		@param Phone2 
		Alternative Mobile Telefonnummer
	  */
	@Override
	public void setPhone2 (java.lang.String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	/** Get Mobil.
		@return Alternative Mobile Telefonnummer
	  */
	@Override
	public java.lang.String getPhone2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone2);
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getPO_PaymentTerm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class);
	}

	@Override
	public void setPO_PaymentTerm(org.compiere.model.I_C_PaymentTerm PO_PaymentTerm)
	{
		set_ValueFromPO(COLUMNNAME_PO_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class, PO_PaymentTerm);
	}

	/** Set Zahlungskondition.
		@param PO_PaymentTerm_ID 
		Zahlungskondition für die Bestellung
	  */
	@Override
	public void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID)
	{
		if (PO_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, Integer.valueOf(PO_PaymentTerm_ID));
	}

	/** Get Zahlungskondition.
		@return Zahlungskondition für die Bestellung
	  */
	@Override
	public int getPO_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PLZ.
		@param Postal 
		Postal code
	  */
	@Override
	public void setPostal (java.lang.String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get PLZ.
		@return Postal code
	  */
	@Override
	public java.lang.String getPostal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}

	/** Set -.
		@param Postal_Add 
		Additional ZIP or Postal code
	  */
	@Override
	public void setPostal_Add (java.lang.String Postal_Add)
	{
		set_Value (COLUMNNAME_Postal_Add, Postal_Add);
	}

	/** Get -.
		@return Additional ZIP or Postal code
	  */
	@Override
	public java.lang.String getPostal_Add () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal_Add);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_R_InterestArea getR_InterestArea() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_InterestArea_ID, org.compiere.model.I_R_InterestArea.class);
	}

	@Override
	public void setR_InterestArea(org.compiere.model.I_R_InterestArea R_InterestArea)
	{
		set_ValueFromPO(COLUMNNAME_R_InterestArea_ID, org.compiere.model.I_R_InterestArea.class, R_InterestArea);
	}

	/** Set Interessengebiet.
		@param R_InterestArea_ID 
		Interest Area or Topic
	  */
	@Override
	public void setR_InterestArea_ID (int R_InterestArea_ID)
	{
		if (R_InterestArea_ID < 1) 
			set_Value (COLUMNNAME_R_InterestArea_ID, null);
		else 
			set_Value (COLUMNNAME_R_InterestArea_ID, Integer.valueOf(R_InterestArea_ID));
	}

	/** Get Interessengebiet.
		@return Interest Area or Topic
	  */
	@Override
	public int getR_InterestArea_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_InterestArea_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Region.
		@param RegionName 
		Name of the Region
	  */
	@Override
	public void setRegionName (java.lang.String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	/** Get Region.
		@return Name of the Region
	  */
	@Override
	public java.lang.String getRegionName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RegionName);
	}

	/** Set Short Description.
		@param ShortDescription Short Description	  */
	@Override
	public void setShortDescription (java.lang.String ShortDescription)
	{
		set_Value (COLUMNNAME_ShortDescription, ShortDescription);
	}

	/** Get Short Description.
		@return Short Description	  */
	@Override
	public java.lang.String getShortDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShortDescription);
	}

	/** Set Swift code.
		@param SwiftCode 
		Swift Code or BIC
	  */
	@Override
	public void setSwiftCode (java.lang.String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	/** Get Swift code.
		@return Swift Code or BIC
	  */
	@Override
	public java.lang.String getSwiftCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SwiftCode);
	}

	/** Set Steuer-ID.
		@param TaxID 
		Tax Identification
	  */
	@Override
	public void setTaxID (java.lang.String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Steuer-ID.
		@return Tax Identification
	  */
	@Override
	public java.lang.String getTaxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TaxID);
	}

	/** Set Titel.
		@param Title 
		Name this entity is referred to as
	  */
	@Override
	public void setTitle (java.lang.String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	/** Get Titel.
		@return Name this entity is referred to as
	  */
	@Override
	public java.lang.String getTitle () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Title);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}