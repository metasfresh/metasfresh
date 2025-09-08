// Generated Model - DO NOT CHANGE
package de.metas.picking.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PickingProfile_Filter
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PickingProfile_Filter extends org.compiere.model.PO implements I_PickingProfile_Filter, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2008379447L;

    /** Standard Constructor */
    public X_PickingProfile_Filter (final Properties ctx, final int PickingProfile_Filter_ID, @Nullable final String trxName)
    {
      super (ctx, PickingProfile_Filter_ID, trxName);
    }

    /** Load Constructor */
    public X_PickingProfile_Filter (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * FilterType AD_Reference_ID=541849
	 * Reference name: PickingFilter_Options
	 */
	public static final int FILTERTYPE_AD_Reference_ID=541849;
	/** Customer = Customer */
	public static final String FILTERTYPE_Customer = "Customer";
	/** DeliveryDate = DeliveryDate */
	public static final String FILTERTYPE_DeliveryDate = "DeliveryDate";
	/** HandoverLocation = HandoverLocation */
	public static final String FILTERTYPE_HandoverLocation = "HandoverLocation";
	@Override
	public void setFilterType (final java.lang.String FilterType)
	{
		set_Value (COLUMNNAME_FilterType, FilterType);
	}

	@Override
	public java.lang.String getFilterType() 
	{
		return get_ValueAsString(COLUMNNAME_FilterType);
	}

	@Override
	public org.compiere.model.I_MobileUI_UserProfile_Picking getMobileUI_UserProfile_Picking()
	{
		return get_ValueAsPO(COLUMNNAME_MobileUI_UserProfile_Picking_ID, org.compiere.model.I_MobileUI_UserProfile_Picking.class);
	}

	@Override
	public void setMobileUI_UserProfile_Picking(final org.compiere.model.I_MobileUI_UserProfile_Picking MobileUI_UserProfile_Picking)
	{
		set_ValueFromPO(COLUMNNAME_MobileUI_UserProfile_Picking_ID, org.compiere.model.I_MobileUI_UserProfile_Picking.class, MobileUI_UserProfile_Picking);
	}

	@Override
	public void setMobileUI_UserProfile_Picking_ID (final int MobileUI_UserProfile_Picking_ID)
	{
		if (MobileUI_UserProfile_Picking_ID < 1) 
			set_Value (COLUMNNAME_MobileUI_UserProfile_Picking_ID, null);
		else 
			set_Value (COLUMNNAME_MobileUI_UserProfile_Picking_ID, MobileUI_UserProfile_Picking_ID);
	}

	@Override
	public int getMobileUI_UserProfile_Picking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_Picking_ID);
	}

	@Override
	public void setPickingProfile_Filter_ID (final int PickingProfile_Filter_ID)
	{
		if (PickingProfile_Filter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PickingProfile_Filter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickingProfile_Filter_ID, PickingProfile_Filter_ID);
	}

	@Override
	public int getPickingProfile_Filter_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickingProfile_Filter_ID);
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