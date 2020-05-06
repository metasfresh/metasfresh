/** Generated Model - DO NOT CHANGE */
package de.metas.marketing.cleverreach.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_CleverReach_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MKTG_CleverReach_Config extends org.compiere.model.PO implements I_MKTG_CleverReach_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1081836065L;

    /** Standard Constructor */
    public X_MKTG_CleverReach_Config (Properties ctx, int MKTG_CleverReach_Config_ID, String trxName)
    {
      super (ctx, MKTG_CleverReach_Config_ID, trxName);
      /** if (MKTG_CleverReach_Config_ID == 0)
        {
			setCustomerNo (null);
			setMKTG_CleverReach_Config_ID (0);
			setMKTG_Platform_ID (0);
			setPassword (null);
			setUserName (null);
        } */
    }

    /** Load Constructor */
    public X_MKTG_CleverReach_Config (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Kundennummer.
		@param CustomerNo Kundennummer	  */
	@Override
	public void setCustomerNo (java.lang.String CustomerNo)
	{
		set_Value (COLUMNNAME_CustomerNo, CustomerNo);
	}

	/** Get Kundennummer.
		@return Kundennummer	  */
	@Override
	public java.lang.String getCustomerNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CustomerNo);
	}

	/** Set MKTG_CleverReach_Config.
		@param MKTG_CleverReach_Config_ID MKTG_CleverReach_Config	  */
	@Override
	public void setMKTG_CleverReach_Config_ID (int MKTG_CleverReach_Config_ID)
	{
		if (MKTG_CleverReach_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_CleverReach_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_CleverReach_Config_ID, Integer.valueOf(MKTG_CleverReach_Config_ID));
	}

	/** Get MKTG_CleverReach_Config.
		@return MKTG_CleverReach_Config	  */
	@Override
	public int getMKTG_CleverReach_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_CleverReach_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MKTG_Platform.
		@param MKTG_Platform_ID MKTG_Platform	  */
	@Override
	public void setMKTG_Platform_ID (int MKTG_Platform_ID)
	{
		if (MKTG_Platform_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_Platform_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_Platform_ID, Integer.valueOf(MKTG_Platform_ID));
	}

	/** Get MKTG_Platform.
		@return MKTG_Platform	  */
	@Override
	public int getMKTG_Platform_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_Platform_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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