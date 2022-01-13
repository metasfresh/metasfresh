// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_AttachmentEntry_ReferencedRecord_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_AttachmentEntry_ReferencedRecord_v extends org.compiere.model.PO implements I_M_Product_AttachmentEntry_ReferencedRecord_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1709507054L;

    /** Standard Constructor */
    public X_M_Product_AttachmentEntry_ReferencedRecord_v (final Properties ctx, final int M_Product_AttachmentEntry_ReferencedRecord_v_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_AttachmentEntry_ReferencedRecord_v_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_AttachmentEntry_ReferencedRecord_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_AttachmentEntry getAD_AttachmentEntry()
	{
		return get_ValueAsPO(COLUMNNAME_AD_AttachmentEntry_ID, org.compiere.model.I_AD_AttachmentEntry.class);
	}

	@Override
	public void setAD_AttachmentEntry(final org.compiere.model.I_AD_AttachmentEntry AD_AttachmentEntry)
	{
		set_ValueFromPO(COLUMNNAME_AD_AttachmentEntry_ID, org.compiere.model.I_AD_AttachmentEntry.class, AD_AttachmentEntry);
	}

	@Override
	public void setAD_AttachmentEntry_ID (final int AD_AttachmentEntry_ID)
	{
		if (AD_AttachmentEntry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_AttachmentEntry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_AttachmentEntry_ID, AD_AttachmentEntry_ID);
	}

	@Override
	public int getAD_AttachmentEntry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_AttachmentEntry_ID);
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
	public void setBinaryData (final @Nullable java.lang.String BinaryData)
	{
		set_ValueNoCheck (COLUMNNAME_BinaryData, BinaryData);
	}

	@Override
	public java.lang.String getBinaryData() 
	{
		return get_ValueAsString(COLUMNNAME_BinaryData);
	}

	@Override
	public void setContentType (final @Nullable java.lang.String ContentType)
	{
		set_ValueNoCheck (COLUMNNAME_ContentType, ContentType);
	}

	@Override
	public java.lang.String getContentType() 
	{
		return get_ValueAsString(COLUMNNAME_ContentType);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setFileName (final @Nullable java.lang.String FileName)
	{
		set_ValueNoCheck (COLUMNNAME_FileName, FileName);
	}

	@Override
	public java.lang.String getFileName() 
	{
		return get_ValueAsString(COLUMNNAME_FileName);
	}

	@Override
	public void setM_Product_AttachmentEntry_ReferencedRecord_v_ID (final int M_Product_AttachmentEntry_ReferencedRecord_v_ID)
	{
		if (M_Product_AttachmentEntry_ReferencedRecord_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_AttachmentEntry_ReferencedRecord_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_AttachmentEntry_ReferencedRecord_v_ID, M_Product_AttachmentEntry_ReferencedRecord_v_ID);
	}

	@Override
	public int getM_Product_AttachmentEntry_ReferencedRecord_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_AttachmentEntry_ReferencedRecord_v_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setType (final boolean Type)
	{
		set_ValueNoCheck (COLUMNNAME_Type, Type);
	}

	@Override
	public boolean isType() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Type);
	}

	@Override
	public void setURL (final @Nullable java.lang.String URL)
	{
		set_ValueNoCheck (COLUMNNAME_URL, URL);
	}

	@Override
	public java.lang.String getURL() 
	{
		return get_ValueAsString(COLUMNNAME_URL);
	}
}