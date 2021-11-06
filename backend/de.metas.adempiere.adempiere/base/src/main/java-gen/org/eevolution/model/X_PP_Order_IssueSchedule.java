// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_Order_IssueSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_IssueSchedule extends org.compiere.model.PO implements I_PP_Order_IssueSchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 854193164L;

    /** Standard Constructor */
    public X_PP_Order_IssueSchedule (final Properties ctx, final int PP_Order_IssueSchedule_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_IssueSchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_IssueSchedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIssued_HU_ID (final int Issued_HU_ID)
	{
		if (Issued_HU_ID < 1) 
			set_Value (COLUMNNAME_Issued_HU_ID, null);
		else 
			set_Value (COLUMNNAME_Issued_HU_ID, Issued_HU_ID);
	}

	@Override
	public int getIssued_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Issued_HU_ID);
	}

	@Override
	public void setIssueFrom_HU_ID (final int IssueFrom_HU_ID)
	{
		if (IssueFrom_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_IssueFrom_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_IssueFrom_HU_ID, IssueFrom_HU_ID);
	}

	@Override
	public int getIssueFrom_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_IssueFrom_HU_ID);
	}

	@Override
	public void setIssueFrom_Locator_ID (final int IssueFrom_Locator_ID)
	{
		if (IssueFrom_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_IssueFrom_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_IssueFrom_Locator_ID, IssueFrom_Locator_ID);
	}

	@Override
	public int getIssueFrom_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_IssueFrom_Locator_ID);
	}

	@Override
	public void setIssueFrom_Warehouse_ID (final int IssueFrom_Warehouse_ID)
	{
		if (IssueFrom_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_IssueFrom_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_IssueFrom_Warehouse_ID, IssueFrom_Warehouse_ID);
	}

	@Override
	public int getIssueFrom_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_IssueFrom_Warehouse_ID);
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
	public void setPP_Order_IssueSchedule_ID (final int PP_Order_IssueSchedule_ID)
	{
		if (PP_Order_IssueSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_IssueSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_IssueSchedule_ID, PP_Order_IssueSchedule_ID);
	}

	@Override
	public int getPP_Order_IssueSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_IssueSchedule_ID);
	}

	@Override
	public void setQtyIssued (final BigDecimal QtyIssued)
	{
		set_Value (COLUMNNAME_QtyIssued, QtyIssued);
	}

	@Override
	public BigDecimal getQtyIssued() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyIssued);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToIssue (final BigDecimal QtyToIssue)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToIssue, QtyToIssue);
	}

	@Override
	public BigDecimal getQtyToIssue() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToIssue);
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
		set_ValueNoCheck (COLUMNNAME_RejectReason, RejectReason);
	}

	@Override
	public java.lang.String getRejectReason() 
	{
		return get_ValueAsString(COLUMNNAME_RejectReason);
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