// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Archive_Storage
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Archive_Storage extends org.compiere.model.PO implements I_AD_Archive_Storage, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 113045874L;

    /** Standard Constructor */
    public X_AD_Archive_Storage (final Properties ctx, final int AD_Archive_Storage_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Archive_Storage_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Archive_Storage (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Archive_Storage_ID (final int AD_Archive_Storage_ID)
	{
		if (AD_Archive_Storage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_Storage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_Storage_ID, AD_Archive_Storage_ID);
	}

	@Override
	public int getAD_Archive_Storage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Archive_Storage_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setPath (final @Nullable java.lang.String Path)
	{
		set_Value (COLUMNNAME_Path, Path);
	}

	@Override
	public java.lang.String getPath() 
	{
		return get_ValueAsString(COLUMNNAME_Path);
	}

	/** 
	 * Type AD_Reference_ID=541905
	 * Reference name: AD_Archive_Storage_Type
	 */
	public static final int TYPE_AD_Reference_ID=541905;
	/** Database = db */
	public static final String TYPE_Database = "db";
	/** File System = filesystem */
	public static final String TYPE_FileSystem = "filesystem";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}