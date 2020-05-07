/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Role_OrgAccess
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Role_OrgAccess extends org.compiere.model.PO implements I_AD_Role_OrgAccess, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 936006794L;

    /** Standard Constructor */
    public X_AD_Role_OrgAccess (Properties ctx, int AD_Role_OrgAccess_ID, String trxName)
    {
      super (ctx, AD_Role_OrgAccess_ID, trxName);
      /** if (AD_Role_OrgAccess_ID == 0)
        {
			setAD_Role_ID (0);
			setAD_Role_OrgAccess_ID (0);
			setIsReadOnly (false);
        } */
    }

    /** Load Constructor */
    public X_AD_Role_OrgAccess (Properties ctx, ResultSet rs, String trxName)
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

	/** Set AD_Role_OrgAccess.
		@param AD_Role_OrgAccess_ID AD_Role_OrgAccess	  */
	@Override
	public void setAD_Role_OrgAccess_ID (int AD_Role_OrgAccess_ID)
	{
		if (AD_Role_OrgAccess_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_OrgAccess_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_OrgAccess_ID, Integer.valueOf(AD_Role_OrgAccess_ID));
	}

	/** Get AD_Role_OrgAccess.
		@return AD_Role_OrgAccess	  */
	@Override
	public int getAD_Role_OrgAccess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_OrgAccess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Schreibgeschützt.
		@param IsReadOnly 
		Field is read only
	  */
	@Override
	public void setIsReadOnly (boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, Boolean.valueOf(IsReadOnly));
	}

	/** Get Schreibgeschützt.
		@return Field is read only
	  */
	@Override
	public boolean isReadOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}