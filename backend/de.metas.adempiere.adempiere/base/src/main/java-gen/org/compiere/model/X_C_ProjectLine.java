// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_ProjectLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ProjectLine extends org.compiere.model.PO implements I_C_ProjectLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1421375660L;

    /** Standard Constructor */
    public X_C_ProjectLine (final Properties ctx, final int C_ProjectLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_ProjectLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ProjectLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_OrderPO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderPO(final org.compiere.model.I_C_Order C_OrderPO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class, C_OrderPO);
	}

	@Override
	public void setC_OrderPO_ID (final int C_OrderPO_ID)
	{
		if (C_OrderPO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderPO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderPO_ID, C_OrderPO_ID);
	}

	@Override
	public int getC_OrderPO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderPO_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public org.compiere.model.I_C_ProjectIssue getC_ProjectIssue()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectIssue_ID, org.compiere.model.I_C_ProjectIssue.class);
	}

	@Override
	public void setC_ProjectIssue(final org.compiere.model.I_C_ProjectIssue C_ProjectIssue)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectIssue_ID, org.compiere.model.I_C_ProjectIssue.class, C_ProjectIssue);
	}

	@Override
	public void setC_ProjectIssue_ID (final int C_ProjectIssue_ID)
	{
		if (C_ProjectIssue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectIssue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectIssue_ID, C_ProjectIssue_ID);
	}

	@Override
	public int getC_ProjectIssue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectIssue_ID);
	}

	@Override
	public void setC_ProjectLine_ID (final int C_ProjectLine_ID)
	{
		if (C_ProjectLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectLine_ID, C_ProjectLine_ID);
	}

	@Override
	public int getC_ProjectLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectLine_ID);
	}

	@Override
	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectPhase_ID, org.compiere.model.I_C_ProjectPhase.class);
	}

	@Override
	public void setC_ProjectPhase(final org.compiere.model.I_C_ProjectPhase C_ProjectPhase)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectPhase_ID, org.compiere.model.I_C_ProjectPhase.class, C_ProjectPhase);
	}

	@Override
	public void setC_ProjectPhase_ID (final int C_ProjectPhase_ID)
	{
		if (C_ProjectPhase_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectPhase_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectPhase_ID, C_ProjectPhase_ID);
	}

	@Override
	public int getC_ProjectPhase_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectPhase_ID);
	}

	@Override
	public org.compiere.model.I_C_ProjectTask getC_ProjectTask()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectTask_ID, org.compiere.model.I_C_ProjectTask.class);
	}

	@Override
	public void setC_ProjectTask(final org.compiere.model.I_C_ProjectTask C_ProjectTask)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectTask_ID, org.compiere.model.I_C_ProjectTask.class, C_ProjectTask);
	}

	@Override
	public void setC_ProjectTask_ID (final int C_ProjectTask_ID)
	{
		if (C_ProjectTask_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectTask_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectTask_ID, C_ProjectTask_ID);
	}

	@Override
	public int getC_ProjectTask_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectTask_ID);
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
	public void setCommittedAmt (final @Nullable BigDecimal CommittedAmt)
	{
		set_Value (COLUMNNAME_CommittedAmt, CommittedAmt);
	}

	@Override
	public BigDecimal getCommittedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CommittedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCommittedQty (final @Nullable BigDecimal CommittedQty)
	{
		set_Value (COLUMNNAME_CommittedQty, CommittedQty);
	}

	@Override
	public BigDecimal getCommittedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CommittedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDoPricing (final @Nullable java.lang.String DoPricing)
	{
		set_Value (COLUMNNAME_DoPricing, DoPricing);
	}

	@Override
	public java.lang.String getDoPricing() 
	{
		return get_ValueAsString(COLUMNNAME_DoPricing);
	}

	@Override
	public void setInvoicedAmt (final BigDecimal InvoicedAmt)
	{
		set_Value (COLUMNNAME_InvoicedAmt, InvoicedAmt);
	}

	@Override
	public BigDecimal getInvoicedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoicedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoicedQty (final BigDecimal InvoicedQty)
	{
		set_Value (COLUMNNAME_InvoicedQty, InvoicedQty);
	}

	@Override
	public BigDecimal getInvoicedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoicedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsPrinted (final boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, IsPrinted);
	}

	@Override
	public boolean isPrinted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrinted);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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
	public void setPlannedAmt (final BigDecimal PlannedAmt)
	{
		set_Value (COLUMNNAME_PlannedAmt, PlannedAmt);
	}

	@Override
	public BigDecimal getPlannedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedMarginAmt (final BigDecimal PlannedMarginAmt)
	{
		set_Value (COLUMNNAME_PlannedMarginAmt, PlannedMarginAmt);
	}

	@Override
	public BigDecimal getPlannedMarginAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedMarginAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedPrice (final BigDecimal PlannedPrice)
	{
		set_Value (COLUMNNAME_PlannedPrice, PlannedPrice);
	}

	@Override
	public BigDecimal getPlannedPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedQty (final BigDecimal PlannedQty)
	{
		set_Value (COLUMNNAME_PlannedQty, PlannedQty);
	}

	@Override
	public BigDecimal getPlannedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedQty);
		return bd != null ? bd : BigDecimal.ZERO;
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
}