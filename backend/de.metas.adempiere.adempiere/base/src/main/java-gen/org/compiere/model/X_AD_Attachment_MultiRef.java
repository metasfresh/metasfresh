// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Attachment_MultiRef
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Attachment_MultiRef extends org.compiere.model.PO implements I_AD_Attachment_MultiRef, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -326290838L;

    /** Standard Constructor */
    public X_AD_Attachment_MultiRef (final Properties ctx, final int AD_Attachment_MultiRef_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Attachment_MultiRef_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Attachment_MultiRef (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Attachment_MultiRef_ID (final int AD_Attachment_MultiRef_ID)
	{
		if (AD_Attachment_MultiRef_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Attachment_MultiRef_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Attachment_MultiRef_ID, AD_Attachment_MultiRef_ID);
	}

	@Override
	public int getAD_Attachment_MultiRef_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Attachment_MultiRef_ID);
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
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setFileName_Override (final @Nullable java.lang.String FileName_Override)
	{
		set_Value (COLUMNNAME_FileName_Override, FileName_Override);
	}

	@Override
	public java.lang.String getFileName_Override() 
	{
		return get_ValueAsString(COLUMNNAME_FileName_Override);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}
}