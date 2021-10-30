// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_User_Role
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_User_Role extends org.compiere.model.PO implements I_C_User_Role, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1464548893L;

    /** Standard Constructor */
    public X_C_User_Role (final Properties ctx, final int C_User_Role_ID, @Nullable final String trxName)
    {
      super (ctx, C_User_Role_ID, trxName);
    }

    /** Load Constructor */
    public X_C_User_Role (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_User_Role_ID (final int C_User_Role_ID)
	{
		if (C_User_Role_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_User_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_User_Role_ID, C_User_Role_ID);
	}

	@Override
	public int getC_User_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_User_Role_ID);
	}

	@Override
	public void setIsUniqueForBPartner (final boolean IsUniqueForBPartner)
	{
		set_Value (COLUMNNAME_IsUniqueForBPartner, IsUniqueForBPartner);
	}

	@Override
	public boolean isUniqueForBPartner() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUniqueForBPartner);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}