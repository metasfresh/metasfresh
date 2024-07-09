// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MobileUI_UserProfile_Picking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_Picking extends org.compiere.model.PO implements I_MobileUI_UserProfile_Picking, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -393056167L;

    /** Standard Constructor */
    public X_MobileUI_UserProfile_Picking (final Properties ctx, final int MobileUI_UserProfile_Picking_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_UserProfile_Picking_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_UserProfile_Picking (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * CreateShipmentPolicy AD_Reference_ID=541839
	 * Reference name: Create Shipment Policy
	 */
	public static final int CREATESHIPMENTPOLICY_AD_Reference_ID=541839;
	/** DO_NOT_CREATE = NO */
	public static final String CREATESHIPMENTPOLICY_DO_NOT_CREATE = "NO";
	/** CREATE_DRAFT = DR */
	public static final String CREATESHIPMENTPOLICY_CREATE_DRAFT = "DR";
	/** CREATE_AND_COMPLETE = CO */
	public static final String CREATESHIPMENTPOLICY_CREATE_AND_COMPLETE = "CO";
	/** CREATE_COMPLETE_CLOSE = CL */
	public static final String CREATESHIPMENTPOLICY_CREATE_COMPLETE_CLOSE = "CL";
	@Override
	public void setCreateShipmentPolicy (final java.lang.String CreateShipmentPolicy)
	{
		set_Value (COLUMNNAME_CreateShipmentPolicy, CreateShipmentPolicy);
	}

	@Override
	public java.lang.String getCreateShipmentPolicy() 
	{
		return get_ValueAsString(COLUMNNAME_CreateShipmentPolicy);
	}

	@Override
	public void setIsAllowPickingAnyHU (final boolean IsAllowPickingAnyHU)
	{
		set_Value (COLUMNNAME_IsAllowPickingAnyHU, IsAllowPickingAnyHU);
	}

	@Override
	public boolean isAllowPickingAnyHU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowPickingAnyHU);
	}

	@Override
	public void setIsAlwaysSplitHUsEnabled (final boolean IsAlwaysSplitHUsEnabled)
	{
		set_Value (COLUMNNAME_IsAlwaysSplitHUsEnabled, IsAlwaysSplitHUsEnabled);
	}

	@Override
	public boolean isAlwaysSplitHUsEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAlwaysSplitHUsEnabled);
	}

	@Override
	public void setMobileUI_UserProfile_Picking_ID (final int MobileUI_UserProfile_Picking_ID)
	{
		if (MobileUI_UserProfile_Picking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_Picking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_Picking_ID, MobileUI_UserProfile_Picking_ID);
	}

	@Override
	public int getMobileUI_UserProfile_Picking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_Picking_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}