/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User_Record_Access
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_User_Record_Access extends org.compiere.model.PO implements I_AD_User_Record_Access, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -333119461L;

    /** Standard Constructor */
    public X_AD_User_Record_Access (Properties ctx, int AD_User_Record_Access_ID, String trxName)
    {
      super (ctx, AD_User_Record_Access_ID, trxName);
      /** if (AD_User_Record_Access_ID == 0)
        {
			setAccess (null);
			setAD_Table_ID (0);
			setAD_User_Record_Access_ID (0);
			setPermissionIssuer (null);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_User_Record_Access (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * Access AD_Reference_ID=540962
	 * Reference name: AD_User_Access
	 */
	public static final int ACCESS_AD_Reference_ID=540962;
	/** Read = R */
	public static final String ACCESS_Read = "R";
	/** Write = W */
	public static final String ACCESS_Write = "W";
	/** Report = P */
	public static final String ACCESS_Report = "P";
	/** Export = E */
	public static final String ACCESS_Export = "E";
	/** Set Access.
		@param Access Access	  */
	@Override
	public void setAccess (java.lang.String Access)
	{

		set_Value (COLUMNNAME_Access, Access);
	}

	/** Get Access.
		@return Access	  */
	@Override
	public java.lang.String getAccess () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Access);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
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

	/** Set User Record Access.
		@param AD_User_Record_Access_ID User Record Access	  */
	@Override
	public void setAD_User_Record_Access_ID (int AD_User_Record_Access_ID)
	{
		if (AD_User_Record_Access_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_Record_Access_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_Record_Access_ID, Integer.valueOf(AD_User_Record_Access_ID));
	}

	/** Get User Record Access.
		@return User Record Access	  */
	@Override
	public int getAD_User_Record_Access_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Record_Access_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_UserGroup getAD_UserGroup()
	{
		return get_ValueAsPO(COLUMNNAME_AD_UserGroup_ID, org.compiere.model.I_AD_UserGroup.class);
	}

	@Override
	public void setAD_UserGroup(org.compiere.model.I_AD_UserGroup AD_UserGroup)
	{
		set_ValueFromPO(COLUMNNAME_AD_UserGroup_ID, org.compiere.model.I_AD_UserGroup.class, AD_UserGroup);
	}

	/** Set Nutzergruppe.
		@param AD_UserGroup_ID Nutzergruppe	  */
	@Override
	public void setAD_UserGroup_ID (int AD_UserGroup_ID)
	{
		if (AD_UserGroup_ID < 1) 
			set_Value (COLUMNNAME_AD_UserGroup_ID, null);
		else 
			set_Value (COLUMNNAME_AD_UserGroup_ID, Integer.valueOf(AD_UserGroup_ID));
	}

	/** Get Nutzergruppe.
		@return Nutzergruppe	  */
	@Override
	public int getAD_UserGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UserGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Permission Issuer.
		@param PermissionIssuer Permission Issuer	  */
	@Override
	public void setPermissionIssuer (java.lang.String PermissionIssuer)
	{
		set_Value (COLUMNNAME_PermissionIssuer, PermissionIssuer);
	}

	/** Get Permission Issuer.
		@return Permission Issuer	  */
	@Override
	public java.lang.String getPermissionIssuer () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PermissionIssuer);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
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