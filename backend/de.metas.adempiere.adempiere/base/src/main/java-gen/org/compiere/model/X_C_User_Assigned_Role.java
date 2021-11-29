// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_User_Assigned_Role
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_User_Assigned_Role extends org.compiere.model.PO implements I_C_User_Assigned_Role, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1971162737L;

    /** Standard Constructor */
    public X_C_User_Assigned_Role (final Properties ctx, final int C_User_Assigned_Role_ID, @Nullable final String trxName)
    {
      super (ctx, C_User_Assigned_Role_ID, trxName);
    }

    /** Load Constructor */
    public X_C_User_Assigned_Role (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setC_User_Assigned_Role_ID (final int C_User_Assigned_Role_ID)
	{
		if (C_User_Assigned_Role_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_User_Assigned_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_User_Assigned_Role_ID, C_User_Assigned_Role_ID);
	}

	@Override
	public int getC_User_Assigned_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_User_Assigned_Role_ID);
	}

	@Override
	public org.compiere.model.I_C_User_Role getC_User_Role()
	{
		return get_ValueAsPO(COLUMNNAME_C_User_Role_ID, org.compiere.model.I_C_User_Role.class);
	}

	@Override
	public void setC_User_Role(final org.compiere.model.I_C_User_Role C_User_Role)
	{
		set_ValueFromPO(COLUMNNAME_C_User_Role_ID, org.compiere.model.I_C_User_Role.class, C_User_Role);
	}

	@Override
	public void setC_User_Role_ID (final int C_User_Role_ID)
	{
		if (C_User_Role_ID < 1) 
			set_Value (COLUMNNAME_C_User_Role_ID, null);
		else 
			set_Value (COLUMNNAME_C_User_Role_ID, C_User_Role_ID);
	}

	@Override
	public int getC_User_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_User_Role_ID);
	}
}