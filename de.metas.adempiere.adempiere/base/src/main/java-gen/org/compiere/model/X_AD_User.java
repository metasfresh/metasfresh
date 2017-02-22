/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_User extends org.compiere.model.PO implements I_AD_User, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 936190244L;

    /** Standard Constructor */
    public X_AD_User (Properties ctx, int AD_User_ID, String trxName)
    {
      super (ctx, AD_User_ID, trxName);
      /** if (AD_User_ID == 0)
        {
			setAD_User_ID (0);
			setIsDefaultContact (false);
// N
			setIsFullBPAccess (true);
// Y
			setIsInPayroll (false);
// N
			setIsMFProcurementUser (false);
// N
			setIsPurchaseContact_Default (false);
// N
			setIsSalesContact_Default (false);
// N
			setName (null);
			setNotificationType (null);
// N
        } */
    }

    /** Load Constructor */
    public X_AD_User (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class, AD_OrgTrx);
	}

	/** Set Buchende Organisation.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	@Override
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Buchende Organisation.
		@return Performing or initiating organization
	  */
	@Override
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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

	@Override
	public org.compiere.model.I_AD_User getAD_User_InCharge() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_InCharge_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_InCharge(org.compiere.model.I_AD_User AD_User_InCharge)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_InCharge_ID, org.compiere.model.I_AD_User.class, AD_User_InCharge);
	}

	/** Set Betreuer.
		@param AD_User_InCharge_ID 
		Person, die bei einem fachlichen Problem vom System informiert wird.
	  */
	@Override
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, Integer.valueOf(AD_User_InCharge_ID));
	}

	/** Get Betreuer.
		@return Person, die bei einem fachlichen Problem vom System informiert wird.
	  */
	@Override
	public int getAD_User_InCharge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_InCharge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public org.compiere.model.I_C_Job getC_Job() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class);
	}

	@Override
	public void setC_Job(org.compiere.model.I_C_Job C_Job)
	{
		set_ValueFromPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class, C_Job);
	}

	/** Set Position.
		@param C_Job_ID 
		Job Position
	  */
	@Override
	public void setC_Job_ID (int C_Job_ID)
	{
		if (C_Job_ID < 1) 
			set_Value (COLUMNNAME_C_Job_ID, null);
		else 
			set_Value (COLUMNNAME_C_Job_ID, Integer.valueOf(C_Job_ID));
	}

	/** Get Position.
		@return Job Position
	  */
	@Override
	public int getC_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** 
	 * ConnectionProfile AD_Reference_ID=364
	 * Reference name: AD_User ConnectionProfile
	 */
	public static final int CONNECTIONPROFILE_AD_Reference_ID=364;
	/** LAN = L */
	public static final String CONNECTIONPROFILE_LAN = "L";
	/** TerminalServer = T */
	public static final String CONNECTIONPROFILE_TerminalServer = "T";
	/** VPN = V */
	public static final String CONNECTIONPROFILE_VPN = "V";
	/** WAN = W */
	public static final String CONNECTIONPROFILE_WAN = "W";
	/** Set Verbindungsart.
		@param ConnectionProfile 
		How a Java Client connects to the server(s)
	  */
	@Override
	public void setConnectionProfile (java.lang.String ConnectionProfile)
	{

		set_Value (COLUMNNAME_ConnectionProfile, ConnectionProfile);
	}

	/** Get Verbindungsart.
		@return How a Java Client connects to the server(s)
	  */
	@Override
	public java.lang.String getConnectionProfile () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConnectionProfile);
	}

	/** 
	 * ContactLimitation AD_Reference_ID=540089
	 * Reference name: ContactLimitation
	 */
	public static final int CONTACTLIMITATION_AD_Reference_ID=540089;
	/** keine Anrufe = B */
	public static final String CONTACTLIMITATION_KeineAnrufe = "B";
	/** keine Anrufe und eMails = C */
	public static final String CONTACTLIMITATION_KeineAnrufeUndEMails = "C";
	/** keine Anrufe, eMails und Post = D */
	public static final String CONTACTLIMITATION_KeineAnrufeEMailsUndPost = "D";
	/** keine eMail und Post = E */
	public static final String CONTACTLIMITATION_KeineEMailUndPost = "E";
	/** Set Kontakt Einschränkung.
		@param ContactLimitation Kontakt Einschränkung	  */
	@Override
	public void setContactLimitation (java.lang.String ContactLimitation)
	{

		set_Value (COLUMNNAME_ContactLimitation, ContactLimitation);
	}

	/** Get Kontakt Einschränkung.
		@return Kontakt Einschränkung	  */
	@Override
	public java.lang.String getContactLimitation () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContactLimitation);
	}

	/** Set Begründung.
		@param ContactLimitationReason Begründung	  */
	@Override
	public void setContactLimitationReason (java.lang.String ContactLimitationReason)
	{
		set_Value (COLUMNNAME_ContactLimitationReason, ContactLimitationReason);
	}

	/** Get Begründung.
		@return Begründung	  */
	@Override
	public java.lang.String getContactLimitationReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContactLimitationReason);
	}

	/** Set Löschdatum.
		@param DelDate Löschdatum	  */
	@Override
	public void setDelDate (java.sql.Timestamp DelDate)
	{
		set_Value (COLUMNNAME_DelDate, DelDate);
	}

	/** Get Löschdatum.
		@return Löschdatum	  */
	@Override
	public java.sql.Timestamp getDelDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DelDate);
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

	/** Set EMail.
		@param EMail 
		Electronic Mail Address
	  */
	@Override
	public void setEMail (java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail.
		@return Electronic Mail Address
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** Set EMail Nutzer-ID.
		@param EMailUser 
		User Name (ID) in the Mail System
	  */
	@Override
	public void setEMailUser (java.lang.String EMailUser)
	{
		set_Value (COLUMNNAME_EMailUser, EMailUser);
	}

	/** Get EMail Nutzer-ID.
		@return User Name (ID) in the Mail System
	  */
	@Override
	public java.lang.String getEMailUser () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMailUser);
	}

	/** Set Passwort EMail-Nutzer.
		@param EMailUserPW 
		Passwort Ihrer EMail Nutzer-ID
	  */
	@Override
	public void setEMailUserPW (java.lang.String EMailUserPW)
	{
		set_Value (COLUMNNAME_EMailUserPW, EMailUserPW);
	}

	/** Get Passwort EMail-Nutzer.
		@return Passwort Ihrer EMail Nutzer-ID
	  */
	@Override
	public java.lang.String getEMailUserPW () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMailUserPW);
	}

	/** Set Überprüfung EMail.
		@param EMailVerify 
		Verification information of EMail Address
	  */
	@Override
	public void setEMailVerify (java.lang.String EMailVerify)
	{
		set_ValueNoCheck (COLUMNNAME_EMailVerify, EMailVerify);
	}

	/** Get Überprüfung EMail.
		@return Verification information of EMail Address
	  */
	@Override
	public java.lang.String getEMailVerify () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMailVerify);
	}

	/** Set EMail überprüft.
		@param EMailVerifyDate 
		Date Email was verified
	  */
	@Override
	public void setEMailVerifyDate (java.sql.Timestamp EMailVerifyDate)
	{
		set_ValueNoCheck (COLUMNNAME_EMailVerifyDate, EMailVerifyDate);
	}

	/** Get EMail überprüft.
		@return Date Email was verified
	  */
	@Override
	public java.sql.Timestamp getEMailVerifyDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EMailVerifyDate);
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

	/** 
	 * Fresh_xmas_Gift AD_Reference_ID=540504
	 * Reference name: fresh_xmas_list
	 */
	public static final int FRESH_XMAS_GIFT_AD_Reference_ID=540504;
	/** Karte = K */
	public static final String FRESH_XMAS_GIFT_Karte = "K";
	/** Geschenk = G */
	public static final String FRESH_XMAS_GIFT_Geschenk = "G";
	/** Set Weihnachtsgeschenk.
		@param Fresh_xmas_Gift Weihnachtsgeschenk	  */
	@Override
	public void setFresh_xmas_Gift (java.lang.String Fresh_xmas_Gift)
	{

		set_Value (COLUMNNAME_Fresh_xmas_Gift, Fresh_xmas_Gift);
	}

	/** Get Weihnachtsgeschenk.
		@return Weihnachtsgeschenk	  */
	@Override
	public java.lang.String getFresh_xmas_Gift () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Fresh_xmas_Gift);
	}

	/** Set Included Tab.
		@param Included_Tab_ID 
		Included Tab in this Tab (Master Dateail)
	  */
	@Override
	public void setIncluded_Tab_ID (java.lang.String Included_Tab_ID)
	{
		set_Value (COLUMNNAME_Included_Tab_ID, Included_Tab_ID);
	}

	/** Get Included Tab.
		@return Included Tab in this Tab (Master Dateail)
	  */
	@Override
	public java.lang.String getIncluded_Tab_ID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Included_Tab_ID);
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

	/** Set Full BP Access.
		@param IsFullBPAccess 
		The user/contact has full access to Business Partner information and resources
	  */
	@Override
	public void setIsFullBPAccess (boolean IsFullBPAccess)
	{
		set_Value (COLUMNNAME_IsFullBPAccess, Boolean.valueOf(IsFullBPAccess));
	}

	/** Get Full BP Access.
		@return The user/contact has full access to Business Partner information and resources
	  */
	@Override
	public boolean isFullBPAccess () 
	{
		Object oo = get_Value(COLUMNNAME_IsFullBPAccess);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is In Payroll.
		@param IsInPayroll 
		Defined if any User Contact will be used for Calculate Payroll
	  */
	@Override
	public void setIsInPayroll (boolean IsInPayroll)
	{
		set_Value (COLUMNNAME_IsInPayroll, Boolean.valueOf(IsInPayroll));
	}

	/** Get Is In Payroll.
		@return Defined if any User Contact will be used for Calculate Payroll
	  */
	@Override
	public boolean isInPayroll () 
	{
		Object oo = get_Value(COLUMNNAME_IsInPayroll);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mengenmeldung-WebUI.
		@param IsMFProcurementUser 
		Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann
	  */
	@Override
	public void setIsMFProcurementUser (boolean IsMFProcurementUser)
	{
		set_Value (COLUMNNAME_IsMFProcurementUser, Boolean.valueOf(IsMFProcurementUser));
	}

	/** Get Mengenmeldung-WebUI.
		@return Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann
	  */
	@Override
	public boolean isMFProcurementUser () 
	{
		Object oo = get_Value(COLUMNNAME_IsMFProcurementUser);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Einkaufskontakt.
		@param IsPurchaseContact Einkaufskontakt	  */
	@Override
	public void setIsPurchaseContact (boolean IsPurchaseContact)
	{
		set_Value (COLUMNNAME_IsPurchaseContact, Boolean.valueOf(IsPurchaseContact));
	}

	/** Get Einkaufskontakt.
		@return Einkaufskontakt	  */
	@Override
	public boolean isPurchaseContact () 
	{
		Object oo = get_Value(COLUMNNAME_IsPurchaseContact);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPurchaseContact_Default.
		@param IsPurchaseContact_Default IsPurchaseContact_Default	  */
	@Override
	public void setIsPurchaseContact_Default (boolean IsPurchaseContact_Default)
	{
		set_Value (COLUMNNAME_IsPurchaseContact_Default, Boolean.valueOf(IsPurchaseContact_Default));
	}

	/** Get IsPurchaseContact_Default.
		@return IsPurchaseContact_Default	  */
	@Override
	public boolean isPurchaseContact_Default () 
	{
		Object oo = get_Value(COLUMNNAME_IsPurchaseContact_Default);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSalesContact.
		@param IsSalesContact IsSalesContact	  */
	@Override
	public void setIsSalesContact (boolean IsSalesContact)
	{
		set_Value (COLUMNNAME_IsSalesContact, Boolean.valueOf(IsSalesContact));
	}

	/** Get IsSalesContact.
		@return IsSalesContact	  */
	@Override
	public boolean isSalesContact () 
	{
		Object oo = get_Value(COLUMNNAME_IsSalesContact);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSalesContact_Default.
		@param IsSalesContact_Default IsSalesContact_Default	  */
	@Override
	public void setIsSalesContact_Default (boolean IsSalesContact_Default)
	{
		set_Value (COLUMNNAME_IsSalesContact_Default, Boolean.valueOf(IsSalesContact_Default));
	}

	/** Get IsSalesContact_Default.
		@return IsSalesContact_Default	  */
	@Override
	public boolean isSalesContact_Default () 
	{
		Object oo = get_Value(COLUMNNAME_IsSalesContact_Default);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSubjectMatterContact.
		@param IsSubjectMatterContact IsSubjectMatterContact	  */
	@Override
	public void setIsSubjectMatterContact (boolean IsSubjectMatterContact)
	{
		set_Value (COLUMNNAME_IsSubjectMatterContact, Boolean.valueOf(IsSubjectMatterContact));
	}

	/** Get IsSubjectMatterContact.
		@return IsSubjectMatterContact	  */
	@Override
	public boolean isSubjectMatterContact () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubjectMatterContact);
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

	/** Set Berechtigen über LDAP.
		@param LDAPUser 
		User Name used for authorization via LDAP (directory) services
	  */
	@Override
	public void setLDAPUser (java.lang.String LDAPUser)
	{
		set_Value (COLUMNNAME_LDAPUser, LDAPUser);
	}

	/** Get Berechtigen über LDAP.
		@return User Name used for authorization via LDAP (directory) services
	  */
	@Override
	public java.lang.String getLDAPUser () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LDAPUser);
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

	/** 
	 * NotificationType AD_Reference_ID=344
	 * Reference name: AD_User NotificationType
	 */
	public static final int NOTIFICATIONTYPE_AD_Reference_ID=344;
	/** EMail = E */
	public static final String NOTIFICATIONTYPE_EMail = "E";
	/** Notice = N */
	public static final String NOTIFICATIONTYPE_Notice = "N";
	/** None = X */
	public static final String NOTIFICATIONTYPE_None = "X";
	/** EMailPlusNotice = B */
	public static final String NOTIFICATIONTYPE_EMailPlusNotice = "B";
	/** NotifyUserInCharge = O */
	public static final String NOTIFICATIONTYPE_NotifyUserInCharge = "O";
	/** Set Benachrichtigungs-Art.
		@param NotificationType 
		Type of Notifications
	  */
	@Override
	public void setNotificationType (java.lang.String NotificationType)
	{

		set_Value (COLUMNNAME_NotificationType, NotificationType);
	}

	/** Get Benachrichtigungs-Art.
		@return Type of Notifications
	  */
	@Override
	public java.lang.String getNotificationType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NotificationType);
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

	/** Set Portalpasswort.
		@param passwordportal Portalpasswort	  */
	@Override
	public void setpasswordportal (java.lang.String passwordportal)
	{
		set_Value (COLUMNNAME_passwordportal, passwordportal);
	}

	/** Get Portalpasswort.
		@return Portalpasswort	  */
	@Override
	public java.lang.String getpasswordportal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_passwordportal);
	}

	/** Set Code für Passwort-Änderung.
		@param PasswordResetCode Code für Passwort-Änderung	  */
	@Override
	public void setPasswordResetCode (java.lang.String PasswordResetCode)
	{
		set_Value (COLUMNNAME_PasswordResetCode, PasswordResetCode);
	}

	/** Get Code für Passwort-Änderung.
		@return Code für Passwort-Änderung	  */
	@Override
	public java.lang.String getPasswordResetCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PasswordResetCode);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	@Override
	public void setPhone (java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	@Override
	public java.lang.String getPhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone);
	}

	/** Set Telefon (alternativ).
		@param Phone2 
		Identifies an alternate telephone number.
	  */
	@Override
	public void setPhone2 (java.lang.String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	/** Get Telefon (alternativ).
		@return Identifies an alternate telephone number.
	  */
	@Override
	public java.lang.String getPhone2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone2);
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

	/** Set Handelsregister.
		@param Registry 
		Handelsregister
	  */
	@Override
	public void setRegistry (java.lang.String Registry)
	{
		set_Value (COLUMNNAME_Registry, Registry);
	}

	/** Get Handelsregister.
		@return Handelsregister
	  */
	@Override
	public java.lang.String getRegistry () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Registry);
	}

	@Override
	public org.compiere.model.I_AD_User getSupervisor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Supervisor_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSupervisor(org.compiere.model.I_AD_User Supervisor)
	{
		set_ValueFromPO(COLUMNNAME_Supervisor_ID, org.compiere.model.I_AD_User.class, Supervisor);
	}

	/** Set Vorgesetzter.
		@param Supervisor_ID 
		Supervisor for this user/organization - used for escalation and approval
	  */
	@Override
	public void setSupervisor_ID (int Supervisor_ID)
	{
		if (Supervisor_ID < 1) 
			set_Value (COLUMNNAME_Supervisor_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor_ID, Integer.valueOf(Supervisor_ID));
	}

	/** Get Vorgesetzter.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	@Override
	public int getSupervisor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UserPIN.
		@param UserPIN UserPIN	  */
	@Override
	public void setUserPIN (java.lang.String UserPIN)
	{
		set_Value (COLUMNNAME_UserPIN, UserPIN);
	}

	/** Get UserPIN.
		@return UserPIN	  */
	@Override
	public java.lang.String getUserPIN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserPIN);
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