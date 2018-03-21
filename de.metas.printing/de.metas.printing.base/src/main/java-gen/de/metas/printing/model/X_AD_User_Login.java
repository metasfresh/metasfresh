/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User_Login
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_User_Login extends org.compiere.model.PO implements I_AD_User_Login, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1415769564L;

    /** Standard Constructor */
    public X_AD_User_Login (Properties ctx, int AD_User_Login_ID, String trxName)
    {
      super (ctx, AD_User_Login_ID, trxName);
      /** if (AD_User_Login_ID == 0)
        {
			setAD_User_Login_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_User_Login (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Session getAD_Session() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Session_ID, org.compiere.model.I_AD_Session.class);
	}

	@Override
	public void setAD_Session(org.compiere.model.I_AD_Session AD_Session)
	{
		set_ValueFromPO(COLUMNNAME_AD_Session_ID, org.compiere.model.I_AD_Session.class, AD_Session);
	}

	/** Set Nutzersitzung.
		@param AD_Session_ID 
		User Session Online or Web
	  */
	@Override
	public void setAD_Session_ID (int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_Value (COLUMNNAME_AD_Session_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Session_ID, Integer.valueOf(AD_Session_ID));
	}

	/** Get Nutzersitzung.
		@return User Session Online or Web
	  */
	@Override
	public int getAD_Session_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Session_ID);
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

	/** Set User Login Request.
		@param AD_User_Login_ID User Login Request	  */
	@Override
	public void setAD_User_Login_ID (int AD_User_Login_ID)
	{
		if (AD_User_Login_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_Login_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_Login_ID, Integer.valueOf(AD_User_Login_ID));
	}

	/** Get User Login Request.
		@return User Login Request	  */
	@Override
	public int getAD_User_Login_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Login_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Host key.
		@param HostKey 
		Unique identifier of a host
	  */
	@Override
	public void setHostKey (java.lang.String HostKey)
	{
		set_Value (COLUMNNAME_HostKey, HostKey);
	}

	/** Get Host key.
		@return Unique identifier of a host
	  */
	@Override
	public java.lang.String getHostKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HostKey);
	}

	/** Set Kennwort.
		@param Password Kennwort	  */
	@Override
	public void setPassword (java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Kennwort.
		@return Kennwort	  */
	@Override
	public java.lang.String getPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Password);
	}

	/** Set Registered EMail.
		@param UserName 
		Email of the responsible for the System
	  */
	@Override
	public void setUserName (java.lang.String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	/** Get Registered EMail.
		@return Email of the responsible for the System
	  */
	@Override
	public java.lang.String getUserName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserName);
	}
}