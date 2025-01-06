// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Picking_Job
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Job extends org.compiere.model.PO implements I_M_Picking_Job, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1061545997L;

    /** Standard Constructor */
    public X_M_Picking_Job (final Properties ctx, final int M_Picking_Job_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Job_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Job (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setDeliveryDate (final java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
	}

	@Override
	public void setDeliveryToAddress (final java.lang.String DeliveryToAddress)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryToAddress, DeliveryToAddress);
	}

	@Override
	public java.lang.String getDeliveryToAddress() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryToAddress);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setHandOver_Location_ID (final int HandOver_Location_ID)
	{
		if (HandOver_Location_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_ID, HandOver_Location_ID);
	}

	@Override
	public int getHandOver_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Location_ID);
	}

	@Override
	public void setHandOver_Partner_ID (final int HandOver_Partner_ID)
	{
		if (HandOver_Partner_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Partner_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Partner_ID, HandOver_Partner_ID);
	}

	@Override
	public int getHandOver_Partner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Partner_ID);
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
	public void setIsApproved (final boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, IsApproved);
	}

	@Override
	public boolean isApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApproved);
	}

	@Override
	public void setIsPickingReviewRequired (final boolean IsPickingReviewRequired)
	{
		set_Value (COLUMNNAME_IsPickingReviewRequired, IsPickingReviewRequired);
	}

	@Override
	public boolean isPickingReviewRequired() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickingReviewRequired);
	}

	@Override
	public void setIsReadyToReview (final boolean IsReadyToReview)
	{
		set_Value (COLUMNNAME_IsReadyToReview, IsReadyToReview);
	}

	@Override
	public boolean isReadyToReview() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReadyToReview);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_LU_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_LU_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_LU_HU(final de.metas.handlingunits.model.I_M_HU M_LU_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_LU_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_LU_HU);
	}

	@Override
	public void setM_LU_HU_ID (final int M_LU_HU_ID)
	{
		if (M_LU_HU_ID < 1) 
			set_Value (COLUMNNAME_M_LU_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_LU_HU_ID, M_LU_HU_ID);
	}

	@Override
	public int getM_LU_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_LU_HU_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI getM_LU_HU_PI()
	{
		return get_ValueAsPO(COLUMNNAME_M_LU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setM_LU_HU_PI(final de.metas.handlingunits.model.I_M_HU_PI M_LU_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_M_LU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, M_LU_HU_PI);
	}

	@Override
	public void setM_LU_HU_PI_ID (final int M_LU_HU_PI_ID)
	{
		if (M_LU_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_M_LU_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_M_LU_HU_PI_ID, M_LU_HU_PI_ID);
	}

	@Override
	public int getM_LU_HU_PI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_LU_HU_PI_ID);
	}

	@Override
	public void setM_Picking_Job_ID (final int M_Picking_Job_ID)
	{
		if (M_Picking_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_ID, M_Picking_Job_ID);
	}

	@Override
	public int getM_Picking_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_ID);
	}

	@Override
	public void setM_PickingSlot_ID (final int M_PickingSlot_ID)
	{
		if (M_PickingSlot_ID < 1) 
			set_Value (COLUMNNAME_M_PickingSlot_ID, null);
		else 
			set_Value (COLUMNNAME_M_PickingSlot_ID, M_PickingSlot_ID);
	}

	@Override
	public int getM_PickingSlot_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PickingSlot_ID);
	}

	@Override
	public void setPicking_User_ID (final int Picking_User_ID)
	{
		if (Picking_User_ID < 1) 
			set_Value (COLUMNNAME_Picking_User_ID, null);
		else 
			set_Value (COLUMNNAME_Picking_User_ID, Picking_User_ID);
	}

	@Override
	public int getPicking_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Picking_User_ID);
	}

	@Override
	public void setPreparationDate (final java.sql.Timestamp PreparationDate)
	{
		set_Value (COLUMNNAME_PreparationDate, PreparationDate);
	}

	@Override
	public java.sql.Timestamp getPreparationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationDate);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI getM_TU_HU_PI()
	{
		return get_ValueAsPO(COLUMNNAME_M_TU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setM_TU_HU_PI(final de.metas.handlingunits.model.I_M_HU_PI M_TU_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_M_TU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, M_TU_HU_PI);
	}

	@Override
	public void setM_TU_HU_PI_ID (final int M_TU_HU_PI_ID)
	{
		if (M_TU_HU_PI_ID < 1)
			set_Value (COLUMNNAME_M_TU_HU_PI_ID, null);
		else
			set_Value (COLUMNNAME_M_TU_HU_PI_ID, M_TU_HU_PI_ID);
	}

	@Override
	public int getM_TU_HU_PI_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_TU_HU_PI_ID);
	}
}