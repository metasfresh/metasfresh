// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_UserProfile_Picking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_Picking extends org.compiere.model.PO implements I_MobileUI_UserProfile_Picking, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1082695111L;

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
	public void setIsAllowAnyCustomer (final boolean IsAllowAnyCustomer)
	{
		set_Value (COLUMNNAME_IsAllowAnyCustomer, IsAllowAnyCustomer);
	}

	@Override
	public boolean isAllowAnyCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowAnyCustomer);
	}

	@Override
	public void setIsAllowNewTU (final boolean IsAllowNewTU)
	{
		set_Value (COLUMNNAME_IsAllowNewTU, IsAllowNewTU);
	}

	@Override
	public boolean isAllowNewTU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowNewTU);
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
	public void setIsAllowSkippingRejectedReason (final boolean IsAllowSkippingRejectedReason)
	{
		set_Value (COLUMNNAME_IsAllowSkippingRejectedReason, IsAllowSkippingRejectedReason);
	}

	@Override
	public boolean isAllowSkippingRejectedReason() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowSkippingRejectedReason);
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
	public void setIsCatchWeightTUPickingEnabled (final boolean IsCatchWeightTUPickingEnabled)
	{
		set_Value (COLUMNNAME_IsCatchWeightTUPickingEnabled, IsCatchWeightTUPickingEnabled);
	}

	@Override
	public boolean isCatchWeightTUPickingEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCatchWeightTUPickingEnabled);
	}

	@Override
	public void setIsConsiderSalesOrderCapacity (final boolean IsConsiderSalesOrderCapacity)
	{
		set_Value (COLUMNNAME_IsConsiderSalesOrderCapacity, IsConsiderSalesOrderCapacity);
	}

	@Override
	public boolean isConsiderSalesOrderCapacity() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsConsiderSalesOrderCapacity);
	}

	@Override
	public void setIsPickingWithNewLU (final boolean IsPickingWithNewLU)
	{
		set_Value (COLUMNNAME_IsPickingWithNewLU, IsPickingWithNewLU);
	}

	@Override
	public boolean isPickingWithNewLU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickingWithNewLU);
	}

	@Override
	public void setIsShowConfirmationPromptWhenOverPick (final boolean IsShowConfirmationPromptWhenOverPick)
	{
		set_Value (COLUMNNAME_IsShowConfirmationPromptWhenOverPick, IsShowConfirmationPromptWhenOverPick);
	}

	@Override
	public boolean isShowConfirmationPromptWhenOverPick() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShowConfirmationPromptWhenOverPick);
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

	/** 
	 * PickingLineGroupBy AD_Reference_ID=541899
	 * Reference name: PickingLineGroupByValues
	 */
	public static final int PICKINGLINEGROUPBY_AD_Reference_ID=541899;
	/** Product_Category = product_category */
	public static final String PICKINGLINEGROUPBY_Product_Category = "product_category";
	@Override
	public void setPickingLineGroupBy (final @Nullable java.lang.String PickingLineGroupBy)
	{
		set_Value (COLUMNNAME_PickingLineGroupBy, PickingLineGroupBy);
	}

	@Override
	public java.lang.String getPickingLineGroupBy() 
	{
		return get_ValueAsString(COLUMNNAME_PickingLineGroupBy);
	}

	/** 
	 * PickingLineSortBy AD_Reference_ID=541900
	 * Reference name: PickingLineSortByValues
	 */
	public static final int PICKINGLINESORTBY_AD_Reference_ID=541900;
	/** ORDER_LINE_SEQ_NO = ORDER_LINE_SEQ_NO */
	public static final String PICKINGLINESORTBY_ORDER_LINE_SEQ_NO = "ORDER_LINE_SEQ_NO";
	/** QTY_TO_PICK_ASC = QTY_TO_PICK_ASC */
	public static final String PICKINGLINESORTBY_QTY_TO_PICK_ASC = "QTY_TO_PICK_ASC";
	/** QTY_TO_PICK_DESC = QTY_TO_PICK_DESC */
	public static final String PICKINGLINESORTBY_QTY_TO_PICK_DESC = "QTY_TO_PICK_DESC";
	@Override
	public void setPickingLineSortBy (final @Nullable java.lang.String PickingLineSortBy)
	{
		set_Value (COLUMNNAME_PickingLineSortBy, PickingLineSortBy);
	}

	@Override
	public java.lang.String getPickingLineSortBy() 
	{
		return get_ValueAsString(COLUMNNAME_PickingLineSortBy);
	}
}