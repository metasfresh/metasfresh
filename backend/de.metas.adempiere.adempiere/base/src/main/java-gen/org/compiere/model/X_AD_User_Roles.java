/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User_Roles
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_User_Roles extends org.compiere.model.PO implements I_AD_User_Roles, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 215842772L;

    /** Standard Constructor */
    public X_AD_User_Roles (Properties ctx, int AD_User_Roles_ID, String trxName)
    {
      super (ctx, AD_User_Roles_ID, trxName);
      /** if (AD_User_Roles_ID == 0)
        {
			setAD_Role_ID (0);
			setAD_User_ID (0);
			setAD_User_Roles_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_User_Roles (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
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

	/** Set AD_User_Roles.
		@param AD_User_Roles_ID AD_User_Roles	  */
	@Override
	public void setAD_User_Roles_ID (int AD_User_Roles_ID)
	{
		if (AD_User_Roles_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_Roles_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_Roles_ID, Integer.valueOf(AD_User_Roles_ID));
	}

	/** Get AD_User_Roles.
		@return AD_User_Roles	  */
	@Override
	public int getAD_User_Roles_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Roles_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}