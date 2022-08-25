// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_CostRevaluationLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_CostRevaluationLine extends org.compiere.model.PO implements I_M_CostRevaluationLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1469726466L;

    /** Standard Constructor */
    public X_M_CostRevaluationLine (final Properties ctx, final int M_CostRevaluationLine_ID, @Nullable final String trxName)
    {
      super (ctx, M_CostRevaluationLine_ID, trxName);
    }

    /** Load Constructor */
    public X_M_CostRevaluationLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
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
		set_Value (COLUMNNAME_CostingLevel, CostingLevel);
	}

	@Override
	public java.lang.String getCostingLevel() 
	{
		return get_ValueAsString(COLUMNNAME_CostingLevel);
	}

	@Override
	public void setCurrentCostPrice (final @Nullable BigDecimal CurrentCostPrice)
	{
		set_Value (COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
	}

	@Override
	public BigDecimal getCurrentCostPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrentCostPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCurrentQty (final @Nullable BigDecimal CurrentQty)
	{
		set_Value (COLUMNNAME_CurrentQty, CurrentQty);
	}

	@Override
	public BigDecimal getCurrentQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrentQty);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setIsRevaluated (final boolean IsRevaluated)
	{
		set_Value (COLUMNNAME_IsRevaluated, IsRevaluated);
	}

	@Override
	public boolean isRevaluated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRevaluated);
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
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
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
			set_Value (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostElement_ID, M_CostElement_ID);
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
			set_Value (COLUMNNAME_M_CostType_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostType_ID, M_CostType_ID);
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
}