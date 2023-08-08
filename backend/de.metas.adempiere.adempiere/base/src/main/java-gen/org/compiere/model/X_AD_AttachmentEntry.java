// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_AttachmentEntry
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_AttachmentEntry extends org.compiere.model.PO implements I_AD_AttachmentEntry, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2074495349L;

    /** Standard Constructor */
    public X_AD_AttachmentEntry (final Properties ctx, final int AD_AttachmentEntry_ID, @Nullable final String trxName)
    {
      super (ctx, AD_AttachmentEntry_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_AttachmentEntry (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Attachment getAD_Attachment()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Attachment_ID, org.compiere.model.I_AD_Attachment.class);
	}

	@Override
	public void setAD_Attachment(final org.compiere.model.I_AD_Attachment AD_Attachment)
	{
		set_ValueFromPO(COLUMNNAME_AD_Attachment_ID, org.compiere.model.I_AD_Attachment.class, AD_Attachment);
	}

	@Override
	public void setAD_Attachment_ID (final int AD_Attachment_ID)
	{
		if (AD_Attachment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Attachment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Attachment_ID, AD_Attachment_ID);
	}

	@Override
	public int getAD_Attachment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Attachment_ID);
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
	public void setBinaryData (final @Nullable byte[] BinaryData)
	{
		set_ValueNoCheck (COLUMNNAME_BinaryData, BinaryData);
	}

	@Override
	public byte[] getBinaryData() 
	{
		return (byte[])get_Value(COLUMNNAME_BinaryData);
	}

	@Override
	public void setContentType (final @Nullable java.lang.String ContentType)
	{
		set_Value (COLUMNNAME_ContentType, ContentType);
	}

	@Override
	public java.lang.String getContentType() 
	{
		return get_ValueAsString(COLUMNNAME_ContentType);
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
	public void setFileName (final java.lang.String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	@Override
	public java.lang.String getFileName() 
	{
		return get_ValueAsString(COLUMNNAME_FileName);
	}

	@Override
	public void setTags (final @Nullable java.lang.String Tags)
	{
		set_Value (COLUMNNAME_Tags, Tags);
	}

	@Override
	public java.lang.String getTags() 
	{
		return get_ValueAsString(COLUMNNAME_Tags);
	}

	/** 
	 * Type AD_Reference_ID=540751
	 * Reference name: AD_AttachmentEntry_Type
	 */
	public static final int TYPE_AD_Reference_ID=540751;
	/** Data = D */
	public static final String TYPE_Data = "D";
	/** URL = U */
	public static final String TYPE_URL = "U";
	/** Local File-URL = LU */
	public static final String TYPE_LocalFile_URL = "LU";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_ValueNoCheck (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}

	@Override
	public void setURL (final @Nullable java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	@Override
	public java.lang.String getURL() 
	{
		return get_ValueAsString(COLUMNNAME_URL);
	}
}