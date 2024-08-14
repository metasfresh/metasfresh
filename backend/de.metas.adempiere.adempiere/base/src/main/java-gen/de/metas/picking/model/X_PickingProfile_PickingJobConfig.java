// Generated Model - DO NOT CHANGE
package de.metas.picking.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PickingProfile_PickingJobConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PickingProfile_PickingJobConfig extends org.compiere.model.PO implements I_PickingProfile_PickingJobConfig, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -737354942L;

    /** Standard Constructor */
    public X_PickingProfile_PickingJobConfig (final Properties ctx, final int PickingProfile_PickingJobConfig_ID, @Nullable final String trxName)
    {
      super (ctx, PickingProfile_PickingJobConfig_ID, trxName);
    }

    /** Load Constructor */
    public X_PickingProfile_PickingJobConfig (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setFormatPattern (final @Nullable java.lang.String FormatPattern)
	{
		set_Value (COLUMNNAME_FormatPattern, FormatPattern);
	}

	@Override
	public java.lang.String getFormatPattern() 
	{
		return get_ValueAsString(COLUMNNAME_FormatPattern);
	}

	@Override
	public void setIsDisplayInDetailed (final boolean IsDisplayInDetailed)
	{
		set_Value (COLUMNNAME_IsDisplayInDetailed, IsDisplayInDetailed);
	}

	@Override
	public boolean isDisplayInDetailed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayInDetailed);
	}

	@Override
	public void setIsDisplayInSummary (final boolean IsDisplayInSummary)
	{
		set_Value (COLUMNNAME_IsDisplayInSummary, IsDisplayInSummary);
	}

	@Override
	public boolean isDisplayInSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayInSummary);
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

	/** 
	 * PickingJobField AD_Reference_ID=541850
	 * Reference name: PickingJobField_Options
	 */
	public static final int PICKINGJOBFIELD_AD_Reference_ID=541850;
	/** DocumentNo = DocumentNo */
	public static final String PICKINGJOBFIELD_DocumentNo = "DocumentNo";
	/** Customer = Customer */
	public static final String PICKINGJOBFIELD_Customer = "Customer";
	/** Delivery Address = ShipToLocation */
	public static final String PICKINGJOBFIELD_DeliveryAddress = "ShipToLocation";
	/** Date Ready = PreparationDate */
	public static final String PICKINGJOBFIELD_DateReady = "PreparationDate";
	/** Handover Location = HandoverLocation */
	public static final String PICKINGJOBFIELD_HandoverLocation = "HandoverLocation";
	/** Rüstplatz Nr. = Setup_Place_No */
	public static final String PICKINGJOBFIELD_RuestplatzNr = "Setup_Place_No";
	@Override
	public void setPickingJobField (final java.lang.String PickingJobField)
	{
		set_Value (COLUMNNAME_PickingJobField, PickingJobField);
	}

	@Override
	public java.lang.String getPickingJobField() 
	{
		return get_ValueAsString(COLUMNNAME_PickingJobField);
	}

	@Override
	public void setPickingProfile_PickingJobConfig_ID (final int PickingProfile_PickingJobConfig_ID)
	{
		if (PickingProfile_PickingJobConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PickingProfile_PickingJobConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickingProfile_PickingJobConfig_ID, PickingProfile_PickingJobConfig_ID);
	}

	@Override
	public int getPickingProfile_PickingJobConfig_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickingProfile_PickingJobConfig_ID);
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