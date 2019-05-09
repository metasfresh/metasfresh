/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Role_Record_Access_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Role_Record_Access_Config extends org.compiere.model.PO implements I_AD_Role_Record_Access_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1227147749L;

    /** Standard Constructor */
    public X_AD_Role_Record_Access_Config (Properties ctx, int AD_Role_Record_Access_Config_ID, String trxName)
    {
      super (ctx, AD_Role_Record_Access_Config_ID, trxName);
      /** if (AD_Role_Record_Access_Config_ID == 0)
        {
			setAD_Role_ID (0);
			setAD_Role_Record_Access_Config_ID (0);
			setType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Role_Record_Access_Config (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Role Record Access Config.
		@param AD_Role_Record_Access_Config_ID Role Record Access Config	  */
	@Override
	public void setAD_Role_Record_Access_Config_ID (int AD_Role_Record_Access_Config_ID)
	{
		if (AD_Role_Record_Access_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_Record_Access_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_Record_Access_Config_ID, Integer.valueOf(AD_Role_Record_Access_Config_ID));
	}

	/** Get Role Record Access Config.
		@return Role Record Access Config	  */
	@Override
	public int getAD_Role_Record_Access_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_Record_Access_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
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

	/** 
	 * Type AD_Reference_ID=540987
	 * Reference name: AD_Role_Record_Access_Config_Type
	 */
	public static final int TYPE_AD_Reference_ID=540987;
	/** Table = T */
	public static final String TYPE_Table = "T";
	/** Business Partner Hierarchy = BPH */
	public static final String TYPE_BusinessPartnerHierarchy = "BPH";
	/** Set Art.
		@param Type Art	  */
	@Override
	public void setType (java.lang.String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Art	  */
	@Override
	public java.lang.String getType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}
}