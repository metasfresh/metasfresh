// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_CostRevaluation_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_CostRevaluation_Detail extends org.compiere.model.PO implements I_M_CostRevaluation_Detail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1696596072L;

    /** Standard Constructor */
    public X_M_CostRevaluation_Detail (final Properties ctx, final int M_CostRevaluation_Detail_ID, @Nullable final String trxName)
    {
      super (ctx, M_CostRevaluation_Detail_ID, trxName);
    }

    /** Load Constructor */
    public X_M_CostRevaluation_Detail (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	/** 
	 * CostingLevel AD_Reference_ID=355
	 * Reference name: C_AcctSchema CostingLevel
	 */
	public static final int COSTINGLEVEL_AD_Reference_ID=355;
	/** Client = C */
	public static final String COSTINGLEVEL_Client = "C";
	/** Organization = O */
	public static final String COSTINGLEVEL_Organization = "O";
	/** BatchLot = B */
	public static final String COSTINGLEVEL_BatchLot = "B";
	@Override
	public void setCostingLevel (final java.lang.String CostingLevel)
	{
		set_ValueNoCheck (COLUMNNAME_CostingLevel, CostingLevel);
	}

	@Override
	public java.lang.String getCostingLevel() 
	{
		return get_ValueAsString(COLUMNNAME_CostingLevel);
	}

	@Override
	public void setDeltaAmt (final BigDecimal DeltaAmt)
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
	public org.compiere.model.I_M_CostDetail getM_CostDetail()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostDetail_ID, org.compiere.model.I_M_CostDetail.class);
	}

	@Override
	public void setM_CostDetail(final org.compiere.model.I_M_CostDetail M_CostDetail)
	{
		set_ValueFromPO(COLUMNNAME_M_CostDetail_ID, org.compiere.model.I_M_CostDetail.class, M_CostDetail);
	}

	@Override
	public void setM_CostDetail_ID (final int M_CostDetail_ID)
	{
		if (M_CostDetail_ID < 1) 
			set_Value (COLUMNNAME_M_CostDetail_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostDetail_ID, M_CostDetail_ID);
	}

	@Override
	public int getM_CostDetail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostDetail_ID);
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
	public void setM_CostRevaluation_Detail_ID (final int M_CostRevaluation_Detail_ID)
	{
		if (M_CostRevaluation_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluation_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluation_Detail_ID, M_CostRevaluation_Detail_ID);
	}

	@Override
	public int getM_CostRevaluation_Detail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostRevaluation_Detail_ID);
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
	public org.compiere.model.I_M_CostType getM_CostType()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostType_ID, org.compiere.model.I_M_CostType.class);
	}

	@Override
	public void setM_CostType(final org.compiere.model.I_M_CostType M_CostType)
	{
		set_ValueFromPO(COLUMNNAME_M_CostType_ID, org.compiere.model.I_M_CostType.class, M_CostType);
	}

	@Override
	public void setM_CostType_ID (final int M_CostType_ID)
	{
		if (M_CostType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostType_ID, M_CostType_ID);
	}

	@Override
	public int getM_CostType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostType_ID);
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
	public void setNewAmt (final BigDecimal NewAmt)
	{
		set_Value (COLUMNNAME_NewAmt, NewAmt);
	}

	@Override
	public BigDecimal getNewAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_NewAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setNewCostPrice (final BigDecimal NewCostPrice)
	{
		set_Value (COLUMNNAME_NewCostPrice, NewCostPrice);
	}

	@Override
	public BigDecimal getNewCostPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_NewCostPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setOldAmt (final BigDecimal OldAmt)
	{
		set_Value (COLUMNNAME_OldAmt, OldAmt);
	}

	@Override
	public BigDecimal getOldAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OldAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setOldCostPrice (final BigDecimal OldCostPrice)
	{
		set_Value (COLUMNNAME_OldCostPrice, OldCostPrice);
	}

	@Override
	public BigDecimal getOldCostPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OldCostPrice);
		return bd != null ? bd : BigDecimal.ZERO;
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

	/** 
	 * RevaluationType AD_Reference_ID=541641
	 * Reference name: M_CostRevaluation_Detail_RevaluationType
	 */
	public static final int REVALUATIONTYPE_AD_Reference_ID=541641;
	/** CurrentCostBeforeRevaluation = CCB */
	public static final String REVALUATIONTYPE_CurrentCostBeforeRevaluation = "CCB";
	/** CurrentCostAfterRevaluation = CCA */
	public static final String REVALUATIONTYPE_CurrentCostAfterRevaluation = "CCA";
	/** CostDetailAdjustment = CDA */
	public static final String REVALUATIONTYPE_CostDetailAdjustment = "CDA";
	@Override
	public void setRevaluationType (final java.lang.String RevaluationType)
	{
		set_Value (COLUMNNAME_RevaluationType, RevaluationType);
	}

	@Override
	public java.lang.String getRevaluationType() 
	{
		return get_ValueAsString(COLUMNNAME_RevaluationType);
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