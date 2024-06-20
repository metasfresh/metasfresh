// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Picking_Job_Step
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Job_Step extends org.compiere.model.PO implements I_M_Picking_Job_Step, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1243400153L;

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
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
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
	public void setIsDynamic (final boolean IsDynamic)
	{
		set_Value (COLUMNNAME_IsDynamic, IsDynamic);
	}

	@Override
	public boolean isDynamic() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDynamic);
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
	public void setPackTo_HU_PI_Item_Product_ID (final int PackTo_HU_PI_Item_Product_ID)
	{
		if (PackTo_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_PackTo_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_PackTo_HU_PI_Item_Product_ID, PackTo_HU_PI_Item_Product_ID);
	}

	@Override
	public int getPackTo_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PackTo_HU_PI_Item_Product_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getPicked_HU()
	{
		return get_ValueAsPO(COLUMNNAME_Picked_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setPicked_HU(final de.metas.handlingunits.model.I_M_HU Picked_HU)
	{
		set_ValueFromPO(COLUMNNAME_Picked_HU_ID, de.metas.handlingunits.model.I_M_HU.class, Picked_HU);
	}

	@Override
	public void setPicked_HU_ID (final int Picked_HU_ID)
	{
		if (Picked_HU_ID < 1) 
			set_Value (COLUMNNAME_Picked_HU_ID, null);
		else 
			set_Value (COLUMNNAME_Picked_HU_ID, Picked_HU_ID);
	}

	@Override
	public int getPicked_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Picked_HU_ID);
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
			set_ValueNoCheck (COLUMNNAME_PickFrom_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickFrom_HU_ID, PickFrom_HU_ID);
	}

	@Override
	public int getPickFrom_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_HU_ID);
	}

	@Override
	public void setPickFrom_Locator_ID (final int PickFrom_Locator_ID)
	{
		if (PickFrom_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PickFrom_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickFrom_Locator_ID, PickFrom_Locator_ID);
	}

	@Override
	public int getPickFrom_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_Locator_ID);
	}

	@Override
	public void setPickFrom_Warehouse_ID (final int PickFrom_Warehouse_ID)
	{
		if (PickFrom_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PickFrom_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickFrom_Warehouse_ID, PickFrom_Warehouse_ID);
	}

	@Override
	public int getPickFrom_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_Warehouse_ID);
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
	 * Reference name: QtyNotPicked RejectReason
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