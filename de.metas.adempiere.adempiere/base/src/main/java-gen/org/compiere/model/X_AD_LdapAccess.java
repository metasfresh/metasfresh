/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_LdapAccess
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_LdapAccess extends org.compiere.model.PO implements I_AD_LdapAccess, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -602068863L;

    /** Standard Constructor */
    public X_AD_LdapAccess (Properties ctx, int AD_LdapAccess_ID, String trxName)
    {
      super (ctx, AD_LdapAccess_ID, trxName);
      /** if (AD_LdapAccess_ID == 0)
        {
			setAD_LdapAccess_ID (0);
			setAD_LdapProcessor_ID (0);
			setIsError (false);
        } */
    }

    /** Load Constructor */
    public X_AD_LdapAccess (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Ldap Access.
		@param AD_LdapAccess_ID 
		Ldap Access Log
	  */
	@Override
	public void setAD_LdapAccess_ID (int AD_LdapAccess_ID)
	{
		if (AD_LdapAccess_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_LdapAccess_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_LdapAccess_ID, Integer.valueOf(AD_LdapAccess_ID));
	}

	/** Get Ldap Access.
		@return Ldap Access Log
	  */
	@Override
	public int getAD_LdapAccess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_LdapAccess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_LdapProcessor getAD_LdapProcessor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_LdapProcessor_ID, org.compiere.model.I_AD_LdapProcessor.class);
	}

	@Override
	public void setAD_LdapProcessor(org.compiere.model.I_AD_LdapProcessor AD_LdapProcessor)
	{
		set_ValueFromPO(COLUMNNAME_AD_LdapProcessor_ID, org.compiere.model.I_AD_LdapProcessor.class, AD_LdapProcessor);
	}

	/** Set LDAP-Server.
		@param AD_LdapProcessor_ID 
		LDAP Server to authenticate and authorize external systems based on Adempiere
	  */
	@Override
	public void setAD_LdapProcessor_ID (int AD_LdapProcessor_ID)
	{
		if (AD_LdapProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_LdapProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_LdapProcessor_ID, Integer.valueOf(AD_LdapProcessor_ID));
	}

	/** Get LDAP-Server.
		@return LDAP Server to authenticate and authorize external systems based on Adempiere
	  */
	@Override
	public int getAD_LdapProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_LdapProcessor_ID);
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

	/** Set Fehler.
		@param IsError 
		An Error occured in the execution
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_ValueNoCheck (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return An Error occured in the execution
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
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
			set_ValueNoCheck (COLUMNNAME_R_InterestArea_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_InterestArea_ID, Integer.valueOf(R_InterestArea_ID));
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

	/** Set Zusammenfassung.
		@param Summary 
		Textual summary of this request
	  */
	@Override
	public void setSummary (java.lang.String Summary)
	{
		set_ValueNoCheck (COLUMNNAME_Summary, Summary);
	}

	/** Get Zusammenfassung.
		@return Textual summary of this request
	  */
	@Override
	public java.lang.String getSummary () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Summary);
	}
}