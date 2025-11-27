// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_UserProfile_DD_CaptionItem
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_DD_CaptionItem extends org.compiere.model.PO implements I_MobileUI_UserProfile_DD_CaptionItem, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1865878798L;

    /** Standard Constructor */
    public X_MobileUI_UserProfile_DD_CaptionItem (final Properties ctx, final int MobileUI_UserProfile_DD_CaptionItem_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_UserProfile_DD_CaptionItem_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_UserProfile_DD_CaptionItem (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * FieldName AD_Reference_ID=542025
	 * Reference name: MobileUI_UserProfile_DD_CaptionItem_Field
	 */
	public static final int FIELDNAME_AD_Reference_ID=542025;
	/** SourceDoc = SourceDoc */
	public static final String FIELDNAME_SourceDoc = "SourceDoc";
	/** WarehouseFrom = WarehouseFrom */
	public static final String FIELDNAME_WarehouseFrom = "WarehouseFrom";
	/** WarehouseTo = WarehouseTo */
	public static final String FIELDNAME_WarehouseTo = "WarehouseTo";
	/** PickDate = PickDate */
	public static final String FIELDNAME_PickDate = "PickDate";
	/** LocatorFrom = LocatorFrom */
	public static final String FIELDNAME_LocatorFrom = "LocatorFrom";
	/** LocatorTo = LocatorTo */
	public static final String FIELDNAME_LocatorTo = "LocatorTo";
	/** ProductGTIN = ProductGTIN */
	public static final String FIELDNAME_ProductGTIN = "ProductGTIN";
	/** ProductValueAndName = ProductValueAndName */
	public static final String FIELDNAME_ProductValueAndName = "ProductValueAndName";
	/** Plant = Plant */
	public static final String FIELDNAME_Plant = "Plant";
	/** Qty = Qty */
	public static final String FIELDNAME_Qty = "Qty";
	/** Priority = Priority */
	public static final String FIELDNAME_Priority = "Priority";
	@Override
	public void setFieldName (final java.lang.String FieldName)
	{
		set_Value (COLUMNNAME_FieldName, FieldName);
	}

	@Override
	public java.lang.String getFieldName() 
	{
		return get_ValueAsString(COLUMNNAME_FieldName);
	}

	@Override
	public void setMobileUI_UserProfile_DD_CaptionItem_ID (final int MobileUI_UserProfile_DD_CaptionItem_ID)
	{
		if (MobileUI_UserProfile_DD_CaptionItem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_DD_CaptionItem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_DD_CaptionItem_ID, MobileUI_UserProfile_DD_CaptionItem_ID);
	}

	@Override
	public int getMobileUI_UserProfile_DD_CaptionItem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_DD_CaptionItem_ID);
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