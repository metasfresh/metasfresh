// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Picking_Job_Step
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Job_Step extends org.compiere.model.PO implements I_M_Picking_Job_Step, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1003516851L;

    /** Standard Constructor */
    public X_M_Picking_Job_Step (final Properties ctx, final int M_Picking_Job_Step_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Job_Step_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Job_Step (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_Picking_Candidate getM_Picking_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Candidate_ID, de.metas.handlingunits.model.I_M_Picking_Candidate.class);
	}

	@Override
	public void setM_Picking_Candidate(final de.metas.handlingunits.model.I_M_Picking_Candidate M_Picking_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Candidate_ID, de.metas.handlingunits.model.I_M_Picking_Candidate.class, M_Picking_Candidate);
	}

	@Override
	public void setM_Picking_Candidate_ID (final int M_Picking_Candidate_ID)
	{
		if (M_Picking_Candidate_ID < 1) 
			set_Value (COLUMNNAME_M_Picking_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_M_Picking_Candidate_ID, M_Picking_Candidate_ID);
	}

	@Override
	public int getM_Picking_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Candidate_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_Picking_Job getM_Picking_Job()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Job_ID, de.metas.handlingunits.model.I_M_Picking_Job.class);
	}

	@Override
	public void setM_Picking_Job(final de.metas.handlingunits.model.I_M_Picking_Job M_Picking_Job)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Job_ID, de.metas.handlingunits.model.I_M_Picking_Job.class, M_Picking_Job);
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
	public de.metas.handlingunits.model.I_M_Picking_Job_Line getM_Picking_Job_Line()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Job_Line_ID, de.metas.handlingunits.model.I_M_Picking_Job_Line.class);
	}

	@Override
	public void setM_Picking_Job_Line(final de.metas.handlingunits.model.I_M_Picking_Job_Line M_Picking_Job_Line)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Job_Line_ID, de.metas.handlingunits.model.I_M_Picking_Job_Line.class, M_Picking_Job_Line);
	}

	@Override
	public void setM_Picking_Job_Line_ID (final int M_Picking_Job_Line_ID)
	{
		if (M_Picking_Job_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Line_ID, M_Picking_Job_Line_ID);
	}

	@Override
	public int getM_Picking_Job_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_Line_ID);
	}

	@Override
	public void setM_Picking_Job_Step_ID (final int M_Picking_Job_Step_ID)
	{
		if (M_Picking_Job_Step_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Step_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Step_ID, M_Picking_Job_Step_ID);
	}

	@Override
	public int getM_Picking_Job_Step_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_Step_ID);
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
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
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
	public void setQtyRejectedToPick (final BigDecimal QtyRejectedToPick)
	{
		set_Value (COLUMNNAME_QtyRejectedToPick, QtyRejectedToPick);
	}

	@Override
	public BigDecimal getQtyRejectedToPick() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyRejectedToPick);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToPick (final BigDecimal QtyToPick)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToPick, QtyToPick);
	}

	@Override
	public BigDecimal getQtyToPick() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToPick);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * RejectReason AD_Reference_ID=541422
	 * Reference name: M_Picking_Candidate_RejectReason
	 */
	public static final int REJECTREASON_AD_Reference_ID=541422;
	/** NotFound = N */
	public static final String REJECTREASON_NotFound = "N";
	/** Damaged = D */
	public static final String REJECTREASON_Damaged = "D";
	@Override
	public void setRejectReason (final @Nullable java.lang.String RejectReason)
	{
		set_Value (COLUMNNAME_RejectReason, RejectReason);
	}

	@Override
	public java.lang.String getRejectReason() 
	{
		return get_ValueAsString(COLUMNNAME_RejectReason);
	}
}