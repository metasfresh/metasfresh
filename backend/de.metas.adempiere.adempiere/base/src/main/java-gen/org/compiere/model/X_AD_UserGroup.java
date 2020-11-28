/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_UserGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_UserGroup extends org.compiere.model.PO implements I_AD_UserGroup, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 981503374L;

    /** Standard Constructor */
    public X_AD_UserGroup (Properties ctx, int AD_UserGroup_ID, String trxName)
    {
      super (ctx, AD_UserGroup_ID, trxName);
      /** if (AD_UserGroup_ID == 0)
        {
			setAD_UserGroup_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_UserGroup (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}