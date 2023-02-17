// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Cost
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Cost extends org.compiere.model.PO implements I_M_Cost, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1485331934L;

    /** Standard Constructor */
    public X_M_Cost (final Properties ctx, final int M_Cost_ID, @Nullable final String trxName)
    {
      super (ctx, M_Cost_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Cost (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	/** 
	 * CostingMethod AD_Reference_ID=122
	 * Reference name: C_AcctSchema Costing Method
	 */
	public static final int COSTINGMETHOD_AD_Reference_ID=122;
	/** StandardCosting = S */
	public static final String COSTINGMETHOD_StandardCosting = "S";
	/** AveragePO = A */
	public static final String COSTINGMETHOD_AveragePO = "A";
	/** Lifo = L */
	public static final String COSTINGMETHOD_Lifo = "L";
	/** Fifo = F */
	public static final String COSTINGMETHOD_Fifo = "F";
	/** LastPOPrice = p */
	public static final String COSTINGMETHOD_LastPOPrice = "p";
	/** AverageInvoice = I */
	public static final String COSTINGMETHOD_AverageInvoice = "I";
	/** LastInvoice = i */
	public static final String COSTINGMETHOD_LastInvoice = "i";
	/** UserDefined = U */
	public static final String COSTINGMETHOD_UserDefined = "U";
	/** _ = x */
	public static final String COSTINGMETHOD__ = "x";
	/** MovingAverageInvoice = M */
	public static final String COSTINGMETHOD_MovingAverageInvoice = "M";
	@Override
	public void setCostingMethod (final @Nullable java.lang.String CostingMethod)
	{
		throw new IllegalArgumentException ("CostingMethod is virtual column");	}

	@Override
	public java.lang.String getCostingMethod() 
	{
		return get_ValueAsString(COLUMNNAME_CostingMethod);
	}

	@Override
	public void setCumulatedAmt (final @Nullable BigDecimal CumulatedAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CumulatedAmt, CumulatedAmt);
	}

	@Override
	public BigDecimal getCumulatedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CumulatedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCumulatedQty (final @Nullable BigDecimal CumulatedQty)
	{
		set_ValueNoCheck (COLUMNNAME_CumulatedQty, CumulatedQty);
	}

	@Override
	public BigDecimal getCumulatedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CumulatedQty);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setCurrentCostPrice (final BigDecimal CurrentCostPrice)
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
	public void setCurrentCostPriceLL (final BigDecimal CurrentCostPriceLL)
	{
		set_Value (COLUMNNAME_CurrentCostPriceLL, CurrentCostPriceLL);
	}

	@Override
	public BigDecimal getCurrentCostPriceLL() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrentCostPriceLL);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCurrentQty (final BigDecimal CurrentQty)
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
	public void setFutureCostPrice (final BigDecimal FutureCostPrice)
	{
		set_Value (COLUMNNAME_FutureCostPrice, FutureCostPrice);
	}

	@Override
	public BigDecimal getFutureCostPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FutureCostPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFutureCostPriceLL (final @Nullable BigDecimal FutureCostPriceLL)
	{
		set_Value (COLUMNNAME_FutureCostPriceLL, FutureCostPriceLL);
	}

	@Override
	public BigDecimal getFutureCostPriceLL() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FutureCostPriceLL);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsCostFrozen (final boolean IsCostFrozen)
	{
		set_Value (COLUMNNAME_IsCostFrozen, IsCostFrozen);
	}

	@Override
	public boolean isCostFrozen() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCostFrozen);
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
	public void setM_Cost_ID (final int M_Cost_ID)
	{
		if (M_Cost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Cost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Cost_ID, M_Cost_ID);
	}

	@Override
	public int getM_Cost_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Cost_ID);
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
	public void setPercent (final int Percent)
	{
		set_Value (COLUMNNAME_Percent, Percent);
	}

	@Override
	public int getPercent() 
	{
		return get_ValueAsInt(COLUMNNAME_Percent);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		throw new IllegalArgumentException ("Processed is virtual column");	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}