/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_User
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_User extends org.compiere.model.PO implements I_I_User, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1094111719L;

    /** Standard Constructor */
    public X_I_User (Properties ctx, int I_User_ID, String trxName)
    {
      super (ctx, I_User_ID, trxName);
      /** if (I_User_ID == 0)
        {
			setI_IsImported (null); // N
			setI_User_ID (0);
			setIsNewsletter (false); // N
			setIsSystemUser (false); // N
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_User (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/** Set Rolle.
		@param AD_Role_ID 
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set BPValue.
		@param BPValue 
		BP Value
	  */
	@Override
	public void setBPValue (java.lang.String BPValue)
	{
		set_Value (COLUMNNAME_BPValue, BPValue);
	}

	/** Get BPValue.
		@return BP Value
	  */
	@Override
	public java.lang.String getBPValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPValue);
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
		Bezeichnet einen Geschäftspartner
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
		@return Bezeichnet einen Geschäftspartner
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
	public org.compiere.model.I_C_DataImport getC_DataImport() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	/** Set Data import.
		@param C_DataImport_ID Data import	  */
	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
	}

	/** Get Data import.
		@return Data import	  */
	@Override
	public int getC_DataImport_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DataImport_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		Faxnummer
	  */
	@Override
	public void setFax (java.lang.String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Faxnummer
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

	/** Set Global ID.
		@param GlobalID Global ID	  */
	@Override
	public void setGlobalID (java.lang.String GlobalID)
	{
		set_Value (COLUMNNAME_GlobalID, GlobalID);
	}

	/** Get Global ID.
		@return Global ID	  */
	@Override
	public java.lang.String getGlobalID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GlobalID);
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	/** Set Importiert.
		@param I_IsImported 
		Ist dieser Import verarbeitet worden?
	  */
	@Override
	public void setI_IsImported (java.lang.String I_IsImported)
	{

		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	/** Get Importiert.
		@return Ist dieser Import verarbeitet worden?
	  */
	@Override
	public java.lang.String getI_IsImported () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_IsImported);
	}

	/** Set Import User.
		@param I_User_ID Import User	  */
	@Override
	public void setI_User_ID (int I_User_ID)
	{
		if (I_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_User_ID, Integer.valueOf(I_User_ID));
	}

	/** Get Import User.
		@return Import User	  */
	@Override
	public int getI_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Newsletter.
		@param IsNewsletter Newsletter	  */
	@Override
	public void setIsNewsletter (boolean IsNewsletter)
	{
		set_Value (COLUMNNAME_IsNewsletter, Boolean.valueOf(IsNewsletter));
	}

	/** Get Newsletter.
		@return Newsletter	  */
	@Override
	public boolean isNewsletter () 
	{
		Object oo = get_Value(COLUMNNAME_IsNewsletter);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Systembenutzer.
		@param IsSystemUser Systembenutzer	  */
	@Override
	public void setIsSystemUser (boolean IsSystemUser)
	{
		set_Value (COLUMNNAME_IsSystemUser, Boolean.valueOf(IsSystemUser));
	}

	/** Get Systembenutzer.
		@return Systembenutzer	  */
	@Override
	public boolean isSystemUser () 
	{
		Object oo = get_Value(COLUMNNAME_IsSystemUser);
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

	/** Set Login.
		@param Login 
		Used for login. See Help.
	  */
	@Override
	public void setLogin (java.lang.String Login)
	{
		set_Value (COLUMNNAME_Login, Login);
	}

	/** Get Login.
		@return Used for login. See Help.
	  */
	@Override
	public java.lang.String getLogin () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Login);
	}

	/** Set Handynummer.
		@param MobilePhone Handynummer	  */
	@Override
	public void setMobilePhone (java.lang.String MobilePhone)
	{
		set_Value (COLUMNNAME_MobilePhone, MobilePhone);
	}

	/** Get Handynummer.
		@return Handynummer	  */
	@Override
	public java.lang.String getMobilePhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MobilePhone);
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

	/** Set Role name.
		@param RoleName Role name	  */
	@Override
	public void setRoleName (java.lang.String RoleName)
	{
		set_Value (COLUMNNAME_RoleName, RoleName);
	}

	/** Get Role name.
		@return Role name	  */
	@Override
	public java.lang.String getRoleName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RoleName);
	}

	/** Set UserValue.
		@param UserValue UserValue	  */
	@Override
	public void setUserValue (java.lang.String UserValue)
	{
		set_Value (COLUMNNAME_UserValue, UserValue);
	}

	/** Get UserValue.
		@return UserValue	  */
	@Override
	public java.lang.String getUserValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserValue);
	}
}