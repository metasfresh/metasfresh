// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_CostDetail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_CostDetail extends org.compiere.model.PO implements I_M_CostDetail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 264607107L;

    /** Standard Constructor */
    public X_M_CostDetail (final Properties ctx, final int M_CostDetail_ID, @Nullable final String trxName)
    {
      super (ctx, M_CostDetail_ID, trxName);
    }

    /** Load Constructor */
    public X_M_CostDetail (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmt (final BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	@Override
	public BigDecimal getAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(final org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	@Override
	public void setC_AcctSchema_ID (final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class);
	}

	@Override
	public void setC_InvoiceLine(final org.compiere.model.I_C_InvoiceLine C_InvoiceLine)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class, C_InvoiceLine);
	}

	@Override
	public void setC_InvoiceLine_ID (final int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, C_InvoiceLine_ID);
	}

	@Override
	public int getC_InvoiceLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceLine_ID);
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
			set_Value (COLUMNNAME_C_ProjectIssue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectIssue_ID, C_ProjectIssue_ID);
	}

	@Override
	public int getC_ProjectIssue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectIssue_ID);
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
	public void setDateAcct (final java.sql.Timestamp DateAcct)
	{
		set_ValueNoCheck (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDeltaAmt (final @Nullable BigDecimal DeltaAmt)
	{
		set_Value (COLUMNNAME_DeltaAmt, DeltaAmt);
	}

	@Override
	public BigDecimal getDeltaAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DeltaAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDeltaQty (final @Nullable BigDecimal DeltaQty)
	{
		set_Value (COLUMNNAME_DeltaQty, DeltaQty);
	}

	@Override
	public BigDecimal getDeltaQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DeltaQty);
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
	public void setIsChangingCosts (final boolean IsChangingCosts)
	{
		set_Value (COLUMNNAME_IsChangingCosts, IsChangingCosts);
	}

	@Override
	public boolean isChangingCosts() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsChangingCosts);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public void setM_CostDetail_ID (final int M_CostDetail_ID)
	{
		if (M_CostDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostDetail_ID, M_CostDetail_ID);
	}

	@Override
	public int getM_CostDetail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostDetail_ID);
	}

	/** 
	 * M_CostDetail_Type AD_Reference_ID=541722
	 * Reference name: M_CostDetail_Type
	 */
	public static final int M_COSTDETAIL_TYPE_AD_Reference_ID=541722;
	/** Main = M */
	public static final String M_COSTDETAIL_TYPE_Main = "M";
	/** Cost Adjustment = A */
	public static final String M_COSTDETAIL_TYPE_CostAdjustment = "A";
	/** Already Shipped = S */
	public static final String M_COSTDETAIL_TYPE_AlreadyShipped = "S";
	@Override
	public void setM_CostDetail_Type (final java.lang.String M_CostDetail_Type)
	{
		set_Value (COLUMNNAME_M_CostDetail_Type, M_CostDetail_Type);
	}

	@Override
	public java.lang.String getM_CostDetail_Type() 
	{
		return get_ValueAsString(COLUMNNAME_M_CostDetail_Type);
	}

	@Override
	public org.compiere.model.I_M_CostElement getM_CostElement()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class);
	}

	@Override
	public void setM_CostElement(final org.compiere.model.I_M_CostElement M_CostElement)
	{
		set_ValueFromPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class, M_CostElement);
	}

	@Override
	public void setM_CostElement_ID (final int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, M_CostElement_ID);
	}

	@Override
	public int getM_CostElement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostElement_ID);
	}

	@Override
	public org.compiere.model.I_M_CostRevaluation getM_CostRevaluation()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostRevaluation_ID, org.compiere.model.I_M_CostRevaluation.class);
	}

	@Override
	public void setM_CostRevaluation(final org.compiere.model.I_M_CostRevaluation M_CostRevaluation)
	{
		set_ValueFromPO(COLUMNNAME_M_CostRevaluation_ID, org.compiere.model.I_M_CostRevaluation.class, M_CostRevaluation);
	}

	@Override
	public void setM_CostRevaluation_ID (final int M_CostRevaluation_ID)
	{
		if (M_CostRevaluation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluation_ID, M_CostRevaluation_ID);
	}

	@Override
	public int getM_CostRevaluation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostRevaluation_ID);
	}

	@Override
	public org.compiere.model.I_M_CostRevaluationLine getM_CostRevaluationLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostRevaluationLine_ID, org.compiere.model.I_M_CostRevaluationLine.class);
	}

	@Override
	public void setM_CostRevaluationLine(final org.compiere.model.I_M_CostRevaluationLine M_CostRevaluationLine)
	{
		set_ValueFromPO(COLUMNNAME_M_CostRevaluationLine_ID, org.compiere.model.I_M_CostRevaluationLine.class, M_CostRevaluationLine);
	}

	@Override
	public void setM_CostRevaluationLine_ID (final int M_CostRevaluationLine_ID)
	{
		if (M_CostRevaluationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluationLine_ID, M_CostRevaluationLine_ID);
	}

	@Override
	public int getM_CostRevaluationLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostRevaluationLine_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
	public org.compiere.model.I_M_InventoryLine getM_InventoryLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class);
	}

	@Override
	public void setM_InventoryLine(final org.compiere.model.I_M_InventoryLine M_InventoryLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class, M_InventoryLine);
	}

	@Override
	public void setM_InventoryLine_ID (final int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_Value (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InventoryLine_ID, M_InventoryLine_ID);
	}

	@Override
	public int getM_InventoryLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InventoryLine_ID);
	}

	@Override
	public org.compiere.model.I_M_MatchInv getM_MatchInv()
	{
		return get_ValueAsPO(COLUMNNAME_M_MatchInv_ID, org.compiere.model.I_M_MatchInv.class);
	}

	@Override
	public void setM_MatchInv(final org.compiere.model.I_M_MatchInv M_MatchInv)
	{
		set_ValueFromPO(COLUMNNAME_M_MatchInv_ID, org.compiere.model.I_M_MatchInv.class, M_MatchInv);
	}

	@Override
	public void setM_MatchInv_ID (final int M_MatchInv_ID)
	{
		if (M_MatchInv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MatchInv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MatchInv_ID, M_MatchInv_ID);
	}

	@Override
	public int getM_MatchInv_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_MatchInv_ID);
	}

	@Override
	public org.compiere.model.I_M_MatchPO getM_MatchPO()
	{
		return get_ValueAsPO(COLUMNNAME_M_MatchPO_ID, org.compiere.model.I_M_MatchPO.class);
	}

	@Override
	public void setM_MatchPO(final org.compiere.model.I_M_MatchPO M_MatchPO)
	{
		set_ValueFromPO(COLUMNNAME_M_MatchPO_ID, org.compiere.model.I_M_MatchPO.class, M_MatchPO);
	}

	@Override
	public void setM_MatchPO_ID (final int M_MatchPO_ID)
	{
		if (M_MatchPO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MatchPO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MatchPO_ID, M_MatchPO_ID);
	}

	@Override
	public int getM_MatchPO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_MatchPO_ID);
	}

	@Override
	public org.compiere.model.I_M_MovementLine getM_MovementLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_MovementLine_ID, org.compiere.model.I_M_MovementLine.class);
	}

	@Override
	public void setM_MovementLine(final org.compiere.model.I_M_MovementLine M_MovementLine)
	{
		set_ValueFromPO(COLUMNNAME_M_MovementLine_ID, org.compiere.model.I_M_MovementLine.class, M_MovementLine);
	}

	@Override
	public void setM_MovementLine_ID (final int M_MovementLine_ID)
	{
		if (M_MovementLine_ID < 1) 
			set_Value (COLUMNNAME_M_MovementLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_MovementLine_ID, M_MovementLine_ID);
	}

	@Override
	public int getM_MovementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_MovementLine_ID);
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
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_ID, PP_Cost_Collector_ID);
	}

	@Override
	public int getPP_Cost_Collector_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Cost_Collector_ID);
	}

	@Override
	public void setPrev_CumulatedAmt (final BigDecimal Prev_CumulatedAmt)
	{
		set_Value (COLUMNNAME_Prev_CumulatedAmt, Prev_CumulatedAmt);
	}

	@Override
	public BigDecimal getPrev_CumulatedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Prev_CumulatedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPrev_CumulatedQty (final BigDecimal Prev_CumulatedQty)
	{
		set_Value (COLUMNNAME_Prev_CumulatedQty, Prev_CumulatedQty);
	}

	@Override
	public BigDecimal getPrev_CumulatedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Prev_CumulatedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPrev_CurrentCostPrice (final BigDecimal Prev_CurrentCostPrice)
	{
		set_Value (COLUMNNAME_Prev_CurrentCostPrice, Prev_CurrentCostPrice);
	}

	@Override
	public BigDecimal getPrev_CurrentCostPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Prev_CurrentCostPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPrev_CurrentCostPriceLL (final BigDecimal Prev_CurrentCostPriceLL)
	{
		set_Value (COLUMNNAME_Prev_CurrentCostPriceLL, Prev_CurrentCostPriceLL);
	}

	@Override
	public BigDecimal getPrev_CurrentCostPriceLL() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Prev_CurrentCostPriceLL);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPrev_CurrentQty (final BigDecimal Prev_CurrentQty)
	{
		set_Value (COLUMNNAME_Prev_CurrentQty, Prev_CurrentQty);
	}

	@Override
	public BigDecimal getPrev_CurrentQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Prev_CurrentQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPrice (final @Nullable BigDecimal Price)
	{
		throw new IllegalArgumentException ("Price is virtual column");	}

	@Override
	public BigDecimal getPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Price);
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

	@Override
	public void setSource_Currency_ID (final int Source_Currency_ID)
	{
		if (Source_Currency_ID < 1) 
			set_Value (COLUMNNAME_Source_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_Source_Currency_ID, Source_Currency_ID);
	}

	@Override
	public int getSource_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_Currency_ID);
	}

	@Override
	public void setSourceAmt (final @Nullable BigDecimal SourceAmt)
	{
		set_Value (COLUMNNAME_SourceAmt, SourceAmt);
	}

	@Override
	public BigDecimal getSourceAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SourceAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}