/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_UserGroup_User_Assign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_UserGroup_User_Assign extends org.compiere.model.PO implements I_AD_UserGroup_User_Assign, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1949439806L;

    /** Standard Constructor */
    public X_AD_UserGroup_User_Assign (Properties ctx, int AD_UserGroup_User_Assign_ID, String trxName)
    {
      super (ctx, AD_UserGroup_User_Assign_ID, trxName);
      /** if (AD_UserGroup_User_Assign_ID == 0)
        {
			setAD_User_ID (0);
			setAD_UserGroup_ID (0);
			setAD_UserGroup_User_Assign_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_UserGroup_User_Assign (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_UserGroup getAD_UserGroup() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_UserGroup_ID, org.compiere.model.I_AD_UserGroup.class);
	}

	@Override
	public void setAD_UserGroup(org.compiere.model.I_AD_UserGroup AD_UserGroup)
	{
		set_ValueFromPO(COLUMNNAME_AD_UserGroup_ID, org.compiere.model.I_AD_UserGroup.class, AD_UserGroup);
	}

	/** Set Users Group.
		@param AD_UserGroup_ID Users Group	  */
	@Override
	public void setAD_UserGroup_ID (int AD_UserGroup_ID)
	{
		if (AD_UserGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UserGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UserGroup_ID, Integer.valueOf(AD_UserGroup_ID));
	}

	/** Get Users Group.
		@return Users Group	  */
	@Override
	public int getAD_UserGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UserGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Users Group User Assignment.
		@param AD_UserGroup_User_Assign_ID Users Group User Assignment	  */
	@Override
	public void setAD_UserGroup_User_Assign_ID (int AD_UserGroup_User_Assign_ID)
	{
		if (AD_UserGroup_User_Assign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UserGroup_User_Assign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UserGroup_User_Assign_ID, Integer.valueOf(AD_UserGroup_User_Assign_ID));
	}

	/** Get Users Group User Assignment.
		@return Users Group User Assignment	  */
	@Override
	public int getAD_UserGroup_User_Assign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UserGroup_User_Assign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Gültig bis inklusiv (letzter Tag)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Gültig bis inklusiv (letzter Tag)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}