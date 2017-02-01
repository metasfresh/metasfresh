/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for I_BPartner
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_I_BPartner extends org.compiere.model.PO implements I_I_BPartner, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 220647501L;

	/** Standard Constructor */
	public X_I_BPartner(Properties ctx, int I_BPartner_ID, String trxName)
	{
		super(ctx, I_BPartner_ID, trxName);
		/**
		 * if (I_BPartner_ID == 0)
		 * {
		 * setI_BPartner_ID (0);
		 * setI_IsImported (false);
		 * // N
		 * }
		 */
	}

	/** Load Constructor */
	public X_I_BPartner(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo(ctx, Table_Name, get_TrxName());
		return poi;
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

	/**
	 * Set Ansprechpartner.
	 * 
	 * @param AD_User_ID
	 *            User within the system - Internal or Business Partner Contact
	 */
	@Override
	public void setAD_User_ID(int AD_User_ID)
	{
		if (AD_User_ID < 0)
			set_Value(COLUMNNAME_AD_User_ID, null);
		else
			set_Value(COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/**
	 * Get Ansprechpartner.
	 * 
	 * @return User within the system - Internal or Business Partner Contact
	 */
	@Override
	public int getAD_User_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Straße und Nr..
	 * 
	 * @param Address1
	 *            Adresszeile 1 für diesen Standort
	 */
	@Override
	public void setAddress1(java.lang.String Address1)
	{
		set_Value(COLUMNNAME_Address1, Address1);
	}

	/**
	 * Get Straße und Nr..
	 * 
	 * @return Adresszeile 1 für diesen Standort
	 */
	@Override
	public java.lang.String getAddress1()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	/**
	 * Set Adresszusatz.
	 * 
	 * @param Address2
	 *            Adresszeile 2 für diesen Standort
	 */
	@Override
	public void setAddress2(java.lang.String Address2)
	{
		set_Value(COLUMNNAME_Address2, Address2);
	}

	/**
	 * Get Adresszusatz.
	 * 
	 * @return Adresszeile 2 für diesen Standort
	 */
	@Override
	public java.lang.String getAddress2()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address2);
	}

	/**
	 * Set Geburtstag.
	 * 
	 * @param Birthday
	 *            Birthday or Anniversary day
	 */
	@Override
	public void setBirthday(java.sql.Timestamp Birthday)
	{
		set_Value(COLUMNNAME_Birthday, Birthday);
	}

	/**
	 * Get Geburtstag.
	 * 
	 * @return Birthday or Anniversary day
	 */
	@Override
	public java.sql.Timestamp getBirthday()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Birthday);
	}

	/**
	 * Set Kontakt-Anrede.
	 * 
	 * @param BPContactGreeting
	 *            Greeting for Business Partner Contact
	 */
	@Override
	public void setBPContactGreeting(java.lang.String BPContactGreeting)
	{
		set_Value(COLUMNNAME_BPContactGreeting, BPContactGreeting);
	}

	/**
	 * Get Kontakt-Anrede.
	 * 
	 * @return Greeting for Business Partner Contact
	 */
	@Override
	public java.lang.String getBPContactGreeting()
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPContactGreeting);
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

	/**
	 * Set Geschäftspartnergruppe.
	 * 
	 * @param C_BP_Group_ID
	 *            Business Partner Group
	 */
	@Override
	public void setC_BP_Group_ID(int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1)
			set_Value(COLUMNNAME_C_BP_Group_ID, null);
		else
			set_Value(COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/**
	 * Get Geschäftspartnergruppe.
	 * 
	 * @return Business Partner Group
	 */
	@Override
	public int getC_BP_Group_ID()
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

	/**
	 * Set Geschäftspartner.
	 * 
	 * @param C_BPartner_ID
	 *            Identifies a Business Partner
	 */
	@Override
	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_Value(COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/**
	 * Get Geschäftspartner.
	 * 
	 * @return Identifies a Business Partner
	 */
	@Override
	public int getC_BPartner_ID()
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

	/**
	 * Set Standort.
	 * 
	 * @param C_BPartner_Location_ID
	 *            Identifies the (ship to) address for this Business Partner
	 */
	@Override
	public void setC_BPartner_Location_ID(int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1)
			set_Value(COLUMNNAME_C_BPartner_Location_ID, null);
		else
			set_Value(COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/**
	 * Get Standort.
	 * 
	 * @return Identifies the (ship to) address for this Business Partner
	 */
	@Override
	public int getC_BPartner_Location_ID()
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

	/**
	 * Set Land.
	 * 
	 * @param C_Country_ID
	 *            Country
	 */
	@Override
	public void setC_Country_ID(int C_Country_ID)
	{
		if (C_Country_ID < 1)
			set_Value(COLUMNNAME_C_Country_ID, null);
		else
			set_Value(COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/**
	 * Get Land.
	 * 
	 * @return Country
	 */
	@Override
	public int getC_Country_ID()
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

	/**
	 * Set Anrede.
	 * 
	 * @param C_Greeting_ID
	 *            Greeting to print on correspondence
	 */
	@Override
	public void setC_Greeting_ID(int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1)
			set_Value(COLUMNNAME_C_Greeting_ID, null);
		else
			set_Value(COLUMNNAME_C_Greeting_ID, Integer.valueOf(C_Greeting_ID));
	}

	/**
	 * Get Anrede.
	 * 
	 * @return Greeting to print on correspondence
	 */
	@Override
	public int getC_Greeting_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Greeting_ID);
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

	/**
	 * Set Region.
	 * 
	 * @param C_Region_ID
	 *            Identifies a geographical Region
	 */
	@Override
	public void setC_Region_ID(int C_Region_ID)
	{
		if (C_Region_ID < 1)
			set_Value(COLUMNNAME_C_Region_ID, null);
		else
			set_Value(COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/**
	 * Get Region.
	 * 
	 * @return Identifies a geographical Region
	 */
	@Override
	public int getC_Region_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Ort.
	 * 
	 * @param City
	 *            Identifies a City
	 */
	@Override
	public void setCity(java.lang.String City)
	{
		set_Value(COLUMNNAME_City, City);
	}

	/**
	 * Get Ort.
	 * 
	 * @return Identifies a City
	 */
	@Override
	public java.lang.String getCity()
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	/**
	 * Set Bemerkungen.
	 * 
	 * @param Comments
	 *            Comments or additional information
	 */
	@Override
	public void setComments(java.lang.String Comments)
	{
		set_Value(COLUMNNAME_Comments, Comments);
	}

	/**
	 * Get Bemerkungen.
	 * 
	 * @return Comments or additional information
	 */
	@Override
	public java.lang.String getComments()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Comments);
	}

	/**
	 * Set Firmenname.
	 * 
	 * @param Companyname Firmenname
	 */
	@Override
	public void setCompanyname(java.lang.String Companyname)
	{
		set_Value(COLUMNNAME_Companyname, Companyname);
	}

	/**
	 * Get Firmenname.
	 * 
	 * @return Firmenname
	 */
	@Override
	public java.lang.String getCompanyname()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Companyname);
	}

	/**
	 * Set Kontakt-Beschreibung.
	 * 
	 * @param ContactDescription
	 *            Description of Contact
	 */
	@Override
	public void setContactDescription(java.lang.String ContactDescription)
	{
		set_Value(COLUMNNAME_ContactDescription, ContactDescription);
	}

	/**
	 * Get Kontakt-Beschreibung.
	 * 
	 * @return Description of Contact
	 */
	@Override
	public java.lang.String getContactDescription()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContactDescription);
	}

	/**
	 * Set Contact Name.
	 * 
	 * @param ContactName
	 *            Business Partner Contact Name
	 */
	public void setContactName(String ContactName)
	{
		set_Value(COLUMNNAME_ContactName, ContactName);
	}

	/**
	 * Get Contact Name.
	 * 
	 * @return Business Partner Contact Name
	 */
	public String getContactName()
	{
		return (String)get_Value(COLUMNNAME_ContactName);
	}

	/**
	 * Set ISO Ländercode.
	 * 
	 * @param CountryCode
	 *            Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 */
	@Override
	public void setCountryCode(java.lang.String CountryCode)
	{
		set_Value(COLUMNNAME_CountryCode, CountryCode);
	}

	/**
	 * Get ISO Ländercode.
	 * 
	 * @return Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 */
	@Override
	public java.lang.String getCountryCode()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
	}

	/**
	 * Set Beschreibung.
	 * 
	 * @param Description Beschreibung
	 */
	@Override
	public void setDescription(java.lang.String Description)
	{
		set_Value(COLUMNNAME_Description, Description);
	}

	/**
	 * Get Beschreibung.
	 * 
	 * @return Beschreibung
	 */
	@Override
	public java.lang.String getDescription()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/**
	 * Set D-U-N-S.
	 * 
	 * @param DUNS
	 *            Dun & Bradstreet Number
	 */
	@Override
	public void setDUNS(java.lang.String DUNS)
	{
		set_Value(COLUMNNAME_DUNS, DUNS);
	}

	/**
	 * Get D-U-N-S.
	 * 
	 * @return Dun & Bradstreet Number
	 */
	@Override
	public java.lang.String getDUNS()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DUNS);
	}

	/**
	 * Set EMail.
	 * 
	 * @param EMail
	 *            Electronic Mail Address
	 */
	@Override
	public void setEMail(java.lang.String EMail)
	{
		set_Value(COLUMNNAME_EMail, EMail);
	}

	/**
	 * Get EMail.
	 * 
	 * @return Electronic Mail Address
	 */
	@Override
	public java.lang.String getEMail()
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/**
	 * Set Fax.
	 * 
	 * @param Fax
	 *            Facsimile number
	 */
	@Override
	public void setFax(java.lang.String Fax)
	{
		set_Value(COLUMNNAME_Fax, Fax);
	}

	/**
	 * Get Fax.
	 * 
	 * @return Facsimile number
	 */
	@Override
	public java.lang.String getFax()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Fax);
	}

	/**
	 * Set Vorname.
	 * 
	 * @param Firstname
	 *            Vorname
	 */
	@Override
	public void setFirstname(java.lang.String Firstname)
	{
		set_Value(COLUMNNAME_Firstname, Firstname);
	}

	/**
	 * Get Vorname.
	 * 
	 * @return Vorname
	 */
	@Override
	public java.lang.String getFirstname()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Firstname);
	}

	/**
	 * Set Gruppen-Schlüssel.
	 * 
	 * @param GroupValue
	 *            Business Partner Group Key
	 */
	@Override
	public void setGroupValue(java.lang.String GroupValue)
	{
		set_Value(COLUMNNAME_GroupValue, GroupValue);
	}

	/**
	 * Get Gruppen-Schlüssel.
	 * 
	 * @return Business Partner Group Key
	 */
	@Override
	public java.lang.String getGroupValue()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GroupValue);
	}

	/**
	 * Set Import - Geschäftspartner.
	 * 
	 * @param I_BPartner_ID Import - Geschäftspartner
	 */
	@Override
	public void setI_BPartner_ID(int I_BPartner_ID)
	{
		if (I_BPartner_ID < 1)
			set_ValueNoCheck(COLUMNNAME_I_BPartner_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_I_BPartner_ID, Integer.valueOf(I_BPartner_ID));
	}

	/**
	 * Get Import - Geschäftspartner.
	 * 
	 * @return Import - Geschäftspartner
	 */
	@Override
	public int getI_BPartner_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_BPartner_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Import-Fehlermeldung.
	 * 
	 * @param I_ErrorMsg
	 *            Messages generated from import process
	 */
	@Override
	public void setI_ErrorMsg(java.lang.String I_ErrorMsg)
	{
		set_Value(COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/**
	 * Get Import-Fehlermeldung.
	 * 
	 * @return Messages generated from import process
	 */
	@Override
	public java.lang.String getI_ErrorMsg()
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/**
	 * Set Importiert.
	 * 
	 * @param I_IsImported
	 *            Has this import been processed
	 */
	@Override
	public void setI_IsImported(boolean I_IsImported)
	{
		set_Value(COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/**
	 * Get Importiert.
	 * 
	 * @return Has this import been processed
	 */
	@Override
	public boolean isI_IsImported()
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

	/**
	 * Set Interessengebiet.
	 * 
	 * @param InterestAreaName
	 *            Name of the Interest Area
	 */
	@Override
	public void setInterestAreaName(java.lang.String InterestAreaName)
	{
		set_Value(COLUMNNAME_InterestAreaName, InterestAreaName);
	}

	/**
	 * Get Interessengebiet.
	 * 
	 * @return Name of the Interest Area
	 */
	@Override
	public java.lang.String getInterestAreaName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_InterestAreaName);
	}

	/**
	 * Set Customer.
	 * 
	 * @param IsCustomer
	 *            Indicates if this Business Partner is a Customer
	 */
	@Override
	public void setIsCustomer(boolean IsCustomer)
	{
		set_Value(COLUMNNAME_IsCustomer, Boolean.valueOf(IsCustomer));
	}

	/**
	 * Get Customer.
	 * 
	 * @return Indicates if this Business Partner is a Customer
	 */
	@Override
	public boolean isCustomer()
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

	/**
	 * Set Employee.
	 * 
	 * @param IsEmployee
	 *            Indicates if this Business Partner is an employee
	 */
	@Override
	public void setIsEmployee(boolean IsEmployee)
	{
		set_Value(COLUMNNAME_IsEmployee, Boolean.valueOf(IsEmployee));
	}

	/**
	 * Get Employee.
	 * 
	 * @return Indicates if this Business Partner is an employee
	 */
	@Override
	public boolean isEmployee()
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

	/**
	 * Set Vendor.
	 * 
	 * @param IsVendor
	 *            Indicates if this Business Partner is a Vendor
	 */
	@Override
	public void setIsVendor(boolean IsVendor)
	{
		set_Value(COLUMNNAME_IsVendor, Boolean.valueOf(IsVendor));
	}

	/**
	 * Get Vendor.
	 * 
	 * @return Indicates if this Business Partner is a Vendor
	 */
	@Override
	public boolean isVendor()
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

	/**
	 * Set Nachname.
	 * 
	 * @param Lastname Nachname
	 */
	@Override
	public void setLastname(java.lang.String Lastname)
	{
		set_Value(COLUMNNAME_Lastname, Lastname);
	}

	/**
	 * Get Nachname.
	 * 
	 * @return Nachname
	 */
	@Override
	public java.lang.String getLastname()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lastname);
	}

	/**
	 * Set NAICS/SIC.
	 * 
	 * @param NAICS
	 *            Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 */
	@Override
	public void setNAICS(java.lang.String NAICS)
	{
		set_Value(COLUMNNAME_NAICS, NAICS);
	}

	/**
	 * Get NAICS/SIC.
	 * 
	 * @return Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 */
	@Override
	public java.lang.String getNAICS()
	{
		return (java.lang.String)get_Value(COLUMNNAME_NAICS);
	}

	/**
	 * Set Name.
	 * 
	 * @param Name
	 *            Alphanumeric identifier of the entity
	 */
	@Override
	public void setName(java.lang.String Name)
	{
		set_Value(COLUMNNAME_Name, Name);
	}

	/**
	 * Get Name.
	 * 
	 * @return Alphanumeric identifier of the entity
	 */
	@Override
	public java.lang.String getName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/**
	 * Set Name 2.
	 * 
	 * @param Name2
	 *            Additional Name
	 */
	@Override
	public void setName2(java.lang.String Name2)
	{
		set_Value(COLUMNNAME_Name2, Name2);
	}

	/**
	 * Get Name 2.
	 * 
	 * @return Additional Name
	 */
	@Override
	public java.lang.String getName2()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	/**
	 * Set Kennwort.
	 * 
	 * @param Password
	 *            Password of any length (case sensitive)
	 */
	@Override
	public void setPassword(java.lang.String Password)
	{
		set_Value(COLUMNNAME_Password, Password);
	}

	/**
	 * Get Kennwort.
	 * 
	 * @return Password of any length (case sensitive)
	 */
	@Override
	public java.lang.String getPassword()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Password);
	}

	/**
	 * Set Phone.
	 * 
	 * @param Phone
	 *            Identifies a telephone number
	 */
	@Override
	public void setPhone(java.lang.String Phone)
	{
		set_Value(COLUMNNAME_Phone, Phone);
	}

	/**
	 * Get Phone.
	 * 
	 * @return Identifies a telephone number
	 */
	@Override
	public java.lang.String getPhone()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone);
	}

	/**
	 * Set Telefon (alternativ).
	 * 
	 * @param Phone2
	 *            Identifies an alternate telephone number.
	 */
	@Override
	public void setPhone2(java.lang.String Phone2)
	{
		set_Value(COLUMNNAME_Phone2, Phone2);
	}

	/**
	 * Get Telefon (alternativ).
	 * 
	 * @return Identifies an alternate telephone number.
	 */
	@Override
	public java.lang.String getPhone2()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone2);
	}

	/**
	 * Set PLZ.
	 * 
	 * @param Postal
	 *            Postal code
	 */
	@Override
	public void setPostal(java.lang.String Postal)
	{
		set_Value(COLUMNNAME_Postal, Postal);
	}

	/**
	 * Get PLZ.
	 * 
	 * @return Postal code
	 */
	@Override
	public java.lang.String getPostal()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}

	/**
	 * Set -.
	 * 
	 * @param Postal_Add
	 *            Additional ZIP or Postal code
	 */
	@Override
	public void setPostal_Add(java.lang.String Postal_Add)
	{
		set_Value(COLUMNNAME_Postal_Add, Postal_Add);
	}

	/**
	 * Get -.
	 * 
	 * @return Additional ZIP or Postal code
	 */
	@Override
	public java.lang.String getPostal_Add()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal_Add);
	}

	/**
	 * Set Verarbeitet.
	 * 
	 * @param Processed
	 *            Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	@Override
	public void setProcessed(boolean Processed)
	{
		set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/**
	 * Get Verarbeitet.
	 * 
	 * @return Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	@Override
	public boolean isProcessed()
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

	/**
	 * Set Verarbeiten.
	 * 
	 * @param Processing Verarbeiten
	 */
	@Override
	public void setProcessing(boolean Processing)
	{
		set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/**
	 * Get Verarbeiten.
	 * 
	 * @return Verarbeiten
	 */
	@Override
	public boolean isProcessing()
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

	/**
	 * Set Interessengebiet.
	 * 
	 * @param R_InterestArea_ID
	 *            Interest Area or Topic
	 */
	@Override
	public void setR_InterestArea_ID(int R_InterestArea_ID)
	{
		if (R_InterestArea_ID < 1)
			set_Value(COLUMNNAME_R_InterestArea_ID, null);
		else
			set_Value(COLUMNNAME_R_InterestArea_ID, Integer.valueOf(R_InterestArea_ID));
	}

	/**
	 * Get Interessengebiet.
	 * 
	 * @return Interest Area or Topic
	 */
	@Override
	public int getR_InterestArea_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_InterestArea_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Region.
	 * 
	 * @param RegionName
	 *            Name of the Region
	 */
	@Override
	public void setRegionName(java.lang.String RegionName)
	{
		set_Value(COLUMNNAME_RegionName, RegionName);
	}

	/**
	 * Get Region.
	 * 
	 * @return Name of the Region
	 */
	@Override
	public java.lang.String getRegionName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_RegionName);
	}

	/**
	 * Set Steuer-ID.
	 * 
	 * @param TaxID
	 *            Tax Identification
	 */
	@Override
	public void setTaxID(java.lang.String TaxID)
	{
		set_Value(COLUMNNAME_TaxID, TaxID);
	}

	/**
	 * Get Steuer-ID.
	 * 
	 * @return Tax Identification
	 */
	@Override
	public java.lang.String getTaxID()
	{
		return (java.lang.String)get_Value(COLUMNNAME_TaxID);
	}

	/**
	 * Set Titel.
	 * 
	 * @param Title
	 *            Name this entity is referred to as
	 */
	@Override
	public void setTitle(java.lang.String Title)
	{
		set_Value(COLUMNNAME_Title, Title);
	}

	/**
	 * Get Titel.
	 * 
	 * @return Name this entity is referred to as
	 */
	@Override
	public java.lang.String getTitle()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Title);
	}

	/**
	 * Set Suchschlüssel.
	 * 
	 * @param Value
	 *            Search key for the record in the format required - must be unique
	 */
	@Override
	public void setValue(java.lang.String Value)
	{
		set_Value(COLUMNNAME_Value, Value);
	}

	/**
	 * Get Suchschlüssel.
	 * 
	 * @return Search key for the record in the format required - must be unique
	 */
	@Override
	public java.lang.String getValue()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/**
	 * Set Lieferstandard.
	 * 
	 * @param IsShipTo
	 *            Liefer-Adresse für den Geschäftspartner
	 */
	@Override
	public void setIsShipTo(boolean IsShipTo)
	{
		set_Value(COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/**
	 * Get Lieferstandard.
	 * 
	 * @return Liefer-Adresse für den Geschäftspartner
	 */
	@Override
	public boolean isShipTo()
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

	/**
	 * Set Vorbelegung Rechnung.
	 * 
	 * @param IsBillTo
	 *            Rechnungs-Adresse für diesen Geschäftspartner
	 */
	@Override
	public void setIsBillTo(boolean IsBillTo)
	{
		set_Value(COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/**
	 * Get Vorbelegung Rechnung.
	 * 
	 * @return Rechnungs-Adresse für diesen Geschäftspartner
	 */
	@Override
	public boolean isBillTo()
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
}
