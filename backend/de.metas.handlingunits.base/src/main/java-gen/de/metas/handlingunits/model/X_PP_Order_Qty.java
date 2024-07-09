// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_Order_Qty
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_Qty extends org.compiere.model.PO implements I_PP_Order_Qty, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1982777405L;

    /** Standard Constructor */
    public X_PP_Order_Qty (final Properties ctx, final int PP_Order_Qty_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_Qty_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_Qty (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsReceipt (final boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, IsReceipt);
	}

	@Override
	public boolean isReceipt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReceipt);
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
	public void setMovementDate (final java.sql.Timestamp MovementDate)
	{
		set_ValueNoCheck (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
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
	public de.metas.handlingunits.model.I_M_HU getNew_LU()
	{
		return get_ValueAsPO(COLUMNNAME_New_LU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setNew_LU(final de.metas.handlingunits.model.I_M_HU New_LU)
	{
		set_ValueFromPO(COLUMNNAME_New_LU_ID, de.metas.handlingunits.model.I_M_HU.class, New_LU);
	}

	@Override
	public void setNew_LU_ID (final int New_LU_ID)
	{
		if (New_LU_ID < 1) 
			set_Value (COLUMNNAME_New_LU_ID, null);
		else 
			set_Value (COLUMNNAME_New_LU_ID, New_LU_ID);
	}

	@Override
	public int getNew_LU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_New_LU_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class);
	}

	@Override
	public void setPP_Cost_Collector(final org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector)
	{
		set_ValueFromPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class, PP_Cost_Collector);
	}

	@Override
	public void setPP_Cost_Collector_ID (final int PP_Cost_Collector_ID)
	{
		if (PP_Cost_Collector_ID < 1) 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, PP_Cost_Collector_ID);
	}

	@Override
	public int getPP_Cost_Collector_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Cost_Collector_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class);
	}

	@Override
	public void setPP_Order_BOMLine(final org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class, PP_Order_BOMLine);
	}

	@Override
	public void setPP_Order_BOMLine_ID (final int PP_Order_BOMLine_ID)
	{
		if (PP_Order_BOMLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOMLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOMLine_ID, PP_Order_BOMLine_ID);
	}

	@Override
	public int getPP_Order_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_BOMLine_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(final org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (final int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_PP_Order_IssueSchedule getPP_Order_IssueSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_IssueSchedule_ID, de.metas.handlingunits.model.I_PP_Order_IssueSchedule.class);
	}

	@Override
	public void setPP_Order_IssueSchedule(final de.metas.handlingunits.model.I_PP_Order_IssueSchedule PP_Order_IssueSchedule)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_IssueSchedule_ID, de.metas.handlingunits.model.I_PP_Order_IssueSchedule.class, PP_Order_IssueSchedule);
	}

	@Override
	public void setPP_Order_IssueSchedule_ID (final int PP_Order_IssueSchedule_ID)
	{
		if (PP_Order_IssueSchedule_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_IssueSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_IssueSchedule_ID, PP_Order_IssueSchedule_ID);
	}

	@Override
	public int getPP_Order_IssueSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_IssueSchedule_ID);
	}

	@Override
	public void setPP_Order_Qty_ID (final int PP_Order_Qty_ID)
	{
		if (PP_Order_Qty_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Qty_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Qty_ID, PP_Order_Qty_ID);
	}

	@Override
	public int getPP_Order_Qty_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Qty_ID);
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
	public void setQty (final BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}