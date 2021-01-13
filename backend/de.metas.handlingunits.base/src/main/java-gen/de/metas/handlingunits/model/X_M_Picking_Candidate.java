// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Picking_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Candidate extends org.compiere.model.PO implements I_M_Picking_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1275027448L;

    /** Standard Constructor */
    public X_M_Picking_Candidate (final Properties ctx, final int M_Picking_Candidate_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * ApprovalStatus AD_Reference_ID=540920
	 * Reference name: M_Picking_Candidate_ApprovalStatus
	 */
	public static final int APPROVALSTATUS_AD_Reference_ID=540920;
	/** ToBeApproved = ? */
	public static final String APPROVALSTATUS_ToBeApproved = "?";
	/** Approved = A */
	public static final String APPROVALSTATUS_Approved = "A";
	/** Rejected = R */
	public static final String APPROVALSTATUS_Rejected = "R";
	@Override
	public void setApprovalStatus (final java.lang.String ApprovalStatus)
	{
		set_Value (COLUMNNAME_ApprovalStatus, ApprovalStatus);
	}

	@Override
	public java.lang.String getApprovalStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ApprovalStatus);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_Picking_Candidate_ID (final int M_Picking_Candidate_ID)
	{
		if (M_Picking_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Candidate_ID, M_Picking_Candidate_ID);
	}

	@Override
	public int getM_Picking_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Candidate_ID);
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
	public void setM_ShipmentSchedule_ID (final int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, M_ShipmentSchedule_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI getPackTo_HU_PI()
	{
		return get_ValueAsPO(COLUMNNAME_PackTo_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setPackTo_HU_PI(final de.metas.handlingunits.model.I_M_HU_PI PackTo_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_PackTo_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, PackTo_HU_PI);
	}

	@Override
	public void setPackTo_HU_PI_ID (final int PackTo_HU_PI_ID)
	{
		if (PackTo_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_PackTo_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_PackTo_HU_PI_ID, PackTo_HU_PI_ID);
	}

	@Override
	public int getPackTo_HU_PI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PackTo_HU_PI_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getPickFrom_HU()
	{
		return get_ValueAsPO(COLUMNNAME_PickFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setPickFrom_HU(final de.metas.handlingunits.model.I_M_HU PickFrom_HU)
	{
		set_ValueFromPO(COLUMNNAME_PickFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class, PickFrom_HU);
	}

	@Override
	public void setPickFrom_HU_ID (final int PickFrom_HU_ID)
	{
		if (PickFrom_HU_ID < 1) 
			set_Value (COLUMNNAME_PickFrom_HU_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_HU_ID, PickFrom_HU_ID);
	}

	@Override
	public int getPickFrom_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_HU_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPickFrom_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PickFrom_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPickFrom_Order(final org.eevolution.model.I_PP_Order PickFrom_Order)
	{
		set_ValueFromPO(COLUMNNAME_PickFrom_Order_ID, org.eevolution.model.I_PP_Order.class, PickFrom_Order);
	}

	@Override
	public void setPickFrom_Order_ID (final int PickFrom_Order_ID)
	{
		if (PickFrom_Order_ID < 1) 
			set_Value (COLUMNNAME_PickFrom_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_Order_ID, PickFrom_Order_ID);
	}

	@Override
	public int getPickFrom_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_Order_ID);
	}

	/** 
	 * PickStatus AD_Reference_ID=540919
	 * Reference name: M_Picking_Candidate_PickStatus
	 */
	public static final int PICKSTATUS_AD_Reference_ID=540919;
	/** ToBePicked = ? */
	public static final String PICKSTATUS_ToBePicked = "?";
	/** Picked = P */
	public static final String PICKSTATUS_Picked = "P";
	/** WillNotBePicked = N */
	public static final String PICKSTATUS_WillNotBePicked = "N";
	/** Packed = A */
	public static final String PICKSTATUS_Packed = "A";
	@Override
	public void setPickStatus (final java.lang.String PickStatus)
	{
		set_Value (COLUMNNAME_PickStatus, PickStatus);
	}

	@Override
	public java.lang.String getPickStatus() 
	{
		return get_ValueAsString(COLUMNNAME_PickStatus);
	}

	@Override
	public void setQtyPicked (final BigDecimal QtyPicked)
	{
		set_Value (COLUMNNAME_QtyPicked, QtyPicked);
	}

	@Override
	public BigDecimal getQtyPicked() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPicked);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReview (final @Nullable BigDecimal QtyReview)
	{
		set_Value (COLUMNNAME_QtyReview, QtyReview);
	}

	@Override
	public BigDecimal getQtyReview() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReview);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * Status AD_Reference_ID=540734
	 * Reference name: M_Picking_Candidate_Status
	 */
	public static final int STATUS_AD_Reference_ID=540734;
	/** Closed = CL */
	public static final String STATUS_Closed = "CL";
	/** InProgress = IP */
	public static final String STATUS_InProgress = "IP";
	/** Processed = PR */
	public static final String STATUS_Processed = "PR";
	/** Voided = VO */
	public static final String STATUS_Voided = "VO";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}
}