/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User_AuthToken
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_User_AuthToken extends org.compiere.model.PO implements I_AD_User_AuthToken, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 567711089L;

    /** Standard Constructor */
    public X_AD_User_AuthToken (Properties ctx, int AD_User_AuthToken_ID, String trxName)
    {
      super (ctx, AD_User_AuthToken_ID, trxName);
      /** if (AD_User_AuthToken_ID == 0)
        {
			setAD_Role_ID (0);
			setAD_User_AuthToken_ID (0);
			setAD_User_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_User_AuthToken (Properties ctx, ResultSet rs, String trxName)
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

	/** Set User Authentication Token .
		@param AD_User_AuthToken_ID User Authentication Token 	  */
	@Override
	public void setAD_User_AuthToken_ID (int AD_User_AuthToken_ID)
	{
		if (AD_User_AuthToken_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_AuthToken_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_AuthToken_ID, Integer.valueOf(AD_User_AuthToken_ID));
	}

	/** Get User Authentication Token .
		@return User Authentication Token 	  */
	@Override
	public int getAD_User_AuthToken_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_AuthToken_ID);
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

	/** Set Authentication Token.
		@param AuthToken Authentication Token	  */
	@Override
	public void setAuthToken (java.lang.String AuthToken)
	{
		set_Value (COLUMNNAME_AuthToken, AuthToken);
	}

	/** Get Authentication Token.
		@return Authentication Token	  */
	@Override
	public java.lang.String getAuthToken () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AuthToken);
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
}