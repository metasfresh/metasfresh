// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DD_Order_MoveSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_Order_MoveSchedule extends org.compiere.model.PO implements I_DD_Order_MoveSchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1701427561L;

    /** Standard Constructor */
    public X_DD_Order_MoveSchedule (final Properties ctx, final int DD_Order_MoveSchedule_ID, @Nullable final String trxName)
    {
      super (ctx, DD_Order_MoveSchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_Order_MoveSchedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public org.eevolution.model.I_DD_Order getDD_Order()
	{
		return get_ValueAsPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class);
	}

	@Override
	public void setDD_Order(final org.eevolution.model.I_DD_Order DD_Order)
	{
		set_ValueFromPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class, DD_Order);
	}

	@Override
	public void setDD_Order_ID (final int DD_Order_ID)
	{
		if (DD_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, DD_Order_ID);
	}

	@Override
	public int getDD_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_Order_ID);
	}

	@Override
	public void setDD_Order_MoveSchedule_ID (final int DD_Order_MoveSchedule_ID)
	{
		if (DD_Order_MoveSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_Order_MoveSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_Order_MoveSchedule_ID, DD_Order_MoveSchedule_ID);
	}

	@Override
	public int getDD_Order_MoveSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_Order_MoveSchedule_ID);
	}

	@Override
	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class);
	}

	@Override
	public void setDD_OrderLine(final org.eevolution.model.I_DD_OrderLine DD_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class, DD_OrderLine);
	}

	@Override
	public void setDD_OrderLine_ID (final int DD_OrderLine_ID)
	{
		if (DD_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, DD_OrderLine_ID);
	}

	@Override
	public int getDD_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_ID);
	}

	@Override
	public void setDropTo_Locator_ID (final int DropTo_Locator_ID)
	{
		if (DropTo_Locator_ID < 1) 
			set_Value (COLUMNNAME_DropTo_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_DropTo_Locator_ID, DropTo_Locator_ID);
	}

	@Override
	public int getDropTo_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropTo_Locator_ID);
	}

	@Override
	public void setDropTo_Warehouse_ID (final int DropTo_Warehouse_ID)
	{
		if (DropTo_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_DropTo_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_DropTo_Warehouse_ID, DropTo_Warehouse_ID);
	}

	@Override
	public int getDropTo_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropTo_Warehouse_ID);
	}

	@Override
	public void setIsPickWholeHU (final boolean IsPickWholeHU)
	{
		set_Value (COLUMNNAME_IsPickWholeHU, IsPickWholeHU);
	}

	@Override
	public boolean isPickWholeHU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickWholeHU);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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
			set_Value (COLUMNNAME_PickFrom_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_Locator_ID, PickFrom_Locator_ID);
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
			set_Value (COLUMNNAME_PickFrom_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_Warehouse_ID, PickFrom_Warehouse_ID);
	}

	@Override
	public int getPickFrom_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_Warehouse_ID);
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

	/** 
	 * Status AD_Reference_ID=541435
	 * Reference name: DD_OrderLine_Schedule_Status
	 */
	public static final int STATUS_AD_Reference_ID=541435;
	/** NotStarted = NS */
	public static final String STATUS_NotStarted = "NS";
	/** InProgress = IP */
	public static final String STATUS_InProgress = "IP";
	/** Completed = CO */
	public static final String STATUS_Completed = "CO";
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