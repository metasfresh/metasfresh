// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_UserProfile_DD_Sort
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_DD_Sort extends org.compiere.model.PO implements I_MobileUI_UserProfile_DD_Sort, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1425903597L;

    /** Standard Constructor */
    public X_MobileUI_UserProfile_DD_Sort (final Properties ctx, final int MobileUI_UserProfile_DD_Sort_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_UserProfile_DD_Sort_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_UserProfile_DD_Sort (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * FieldName AD_Reference_ID=542026
	 * Reference name: MobileUI_UserProfile_DD_Sort_Field
	 */
	public static final int FIELDNAME_AD_Reference_ID=542026;
	/** Priority = Priority */
	public static final String FIELDNAME_Priority = "Priority";
	/** DatePromised = DatePromised */
	public static final String FIELDNAME_DatePromised = "DatePromised";
	/** SeqNo = SeqNo */
	public static final String FIELDNAME_SeqNo = "SeqNo";
	@Override
	public void setFieldName (final @Nullable java.lang.String FieldName)
	{
		set_Value (COLUMNNAME_FieldName, FieldName);
	}

	@Override
	public java.lang.String getFieldName() 
	{
		return get_ValueAsString(COLUMNNAME_FieldName);
	}

	@Override
	public void setIsAscending (final boolean IsAscending)
	{
		set_Value (COLUMNNAME_IsAscending, IsAscending);
	}

	@Override
	public boolean isAscending() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAscending);
	}

	@Override
	public void setMobileUI_UserProfile_DD_ID (final int MobileUI_UserProfile_DD_ID)
	{
		if (MobileUI_UserProfile_DD_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_DD_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_DD_ID, MobileUI_UserProfile_DD_ID);
	}

	@Override
	public int getMobileUI_UserProfile_DD_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_DD_ID);
	}

	@Override
	public void setMobileUI_UserProfile_DD_Sort_ID (final int MobileUI_UserProfile_DD_Sort_ID)
	{
		if (MobileUI_UserProfile_DD_Sort_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_DD_Sort_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_DD_Sort_ID, MobileUI_UserProfile_DD_Sort_ID);
	}

	@Override
	public int getMobileUI_UserProfile_DD_Sort_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_DD_Sort_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}