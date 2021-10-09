// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Picking_Candidate_IssueToOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Candidate_IssueToOrder extends org.compiere.model.PO implements I_M_Picking_Candidate_IssueToOrder, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2067160600L;

    /** Standard Constructor */
    public X_M_Picking_Candidate_IssueToOrder (final Properties ctx, final int M_Picking_Candidate_IssueToOrder_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Candidate_IssueToOrder_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Candidate_IssueToOrder (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Picking_Candidate_IssueToOrder_ID (final int M_Picking_Candidate_IssueToOrder_ID)
	{
		if (M_Picking_Candidate_IssueToOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Candidate_IssueToOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Candidate_IssueToOrder_ID, M_Picking_Candidate_IssueToOrder_ID);
	}

	@Override
	public int getM_Picking_Candidate_IssueToOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Candidate_IssueToOrder_ID);
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
	public void setQtyToIssue (final BigDecimal QtyToIssue)
	{
		set_Value (COLUMNNAME_QtyToIssue, QtyToIssue);
	}

	@Override
	public BigDecimal getQtyToIssue() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToIssue);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}