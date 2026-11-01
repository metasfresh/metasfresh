// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Table_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Table_Access extends org.compiere.model.PO implements I_AD_Table_Access, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1131862601L;

    /** Standard Constructor */
    public X_AD_Table_Access (final Properties ctx, final int AD_Table_Access_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Table_Access_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Table_Access (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setAD_Table_Access_ID (final int AD_Table_Access_ID)
	{
		if (AD_Table_Access_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_Access_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_Access_ID, AD_Table_Access_ID);
	}

	@Override
	public int getAD_Table_Access_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_Access_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setIsCanCreateNewRecords (final boolean IsCanCreateNewRecords)
	{
		set_Value (COLUMNNAME_IsCanCreateNewRecords, IsCanCreateNewRecords);
	}

	@Override
	public boolean isCanCreateNewRecords() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCanCreateNewRecords);
	}

	@Override
	public void setIsCanExport (final boolean IsCanExport)
	{
		set_Value (COLUMNNAME_IsCanExport, IsCanExport);
	}

	@Override
	public boolean isCanExport() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCanExport);
	}

	@Override
	public void setIsCanReport (final boolean IsCanReport)
	{
		set_Value (COLUMNNAME_IsCanReport, IsCanReport);
	}

	@Override
	public boolean isCanReport() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCanReport);
	}

	@Override
	public void setIsReadOnly (final boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, IsReadOnly);
	}

	@Override
	public boolean isReadOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReadOnly);
	}
}