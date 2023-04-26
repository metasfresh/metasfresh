// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Role_TableOrg_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Role_TableOrg_Access extends org.compiere.model.PO implements I_AD_Role_TableOrg_Access, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1610042486L;

    /** Standard Constructor */
    public X_AD_Role_TableOrg_Access (final Properties ctx, final int AD_Role_TableOrg_Access_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Role_TableOrg_Access_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Role_TableOrg_Access (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
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
	@Override
	public void setAccess (final java.lang.String Access)
	{
		set_Value (COLUMNNAME_Access, Access);
	}

	@Override
	public java.lang.String getAccess() 
	{
		return get_ValueAsString(COLUMNNAME_Access);
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(final org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	@Override
	public void setAD_Role_ID (final int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setAD_Role_TableOrg_Access_ID (final int AD_Role_TableOrg_Access_ID)
	{
		if (AD_Role_TableOrg_Access_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_TableOrg_Access_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_TableOrg_Access_ID, AD_Role_TableOrg_Access_ID);
	}

	@Override
	public int getAD_Role_TableOrg_Access_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_TableOrg_Access_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}
}