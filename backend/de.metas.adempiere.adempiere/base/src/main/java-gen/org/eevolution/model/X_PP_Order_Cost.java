/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order_Cost
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Order_Cost extends org.compiere.model.PO implements I_PP_Order_Cost, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -528959898L;

    /** Standard Constructor */
    public X_PP_Order_Cost (final Properties ctx, final int PP_Order_Cost_ID, final String trxName)
    {
      super (ctx, PP_Order_Cost_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_Cost (final Properties ctx, final ResultSet rs, final String trxName)
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
	public void setCostDistributionPercent (final BigDecimal CostDistributionPercent)
	{
		set_Value (COLUMNNAME_CostDistributionPercent, CostDistributionPercent);
	}

	@Override
	public BigDecimal getCostDistributionPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostDistributionPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCumulatedAmt (final BigDecimal CumulatedAmt)
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
	public void setCumulatedQty (final BigDecimal CumulatedQty)
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
	public void setCurrentCostPrice (final BigDecimal CurrentCostPrice)
	{
		set_ValueNoCheck (COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
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
		set_ValueNoCheck (COLUMNNAME_CurrentCostPriceLL, CurrentCostPriceLL);
	}

	@Override
	public BigDecimal getCurrentCostPriceLL() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrentCostPriceLL);
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
	public void setPostCalculationAmt (final BigDecimal PostCalculationAmt)
	{
		set_Value (COLUMNNAME_PostCalculationAmt, PostCalculationAmt);
	}

	@Override
	public BigDecimal getPostCalculationAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PostCalculationAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPP_Order_Cost_ID (final int PP_Order_Cost_ID)
	{
		if (PP_Order_Cost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Cost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Cost_ID, PP_Order_Cost_ID);
	}

	@Override
	public int getPP_Order_Cost_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Cost_ID);
	}

	/** 
	 * PP_Order_Cost_TrxType AD_Reference_ID=540941
	 * Reference name: PP_Order_Cost_TrxType
	 */
	public static final int PP_ORDER_COST_TRXTYPE_AD_Reference_ID=540941;
	/** MainProduct = MR */
	public static final String PP_ORDER_COST_TRXTYPE_MainProduct = "MR";
	/** MaterialIssue = MI */
	public static final String PP_ORDER_COST_TRXTYPE_MaterialIssue = "MI";
	/** ResourceUtilization = RU */
	public static final String PP_ORDER_COST_TRXTYPE_ResourceUtilization = "RU";
	/** ByProduct = BY */
	public static final String PP_ORDER_COST_TRXTYPE_ByProduct = "BY";
	/** CoProduct = CO */
	public static final String PP_ORDER_COST_TRXTYPE_CoProduct = "CO";
	@Override
	public void setPP_Order_Cost_TrxType (final java.lang.String PP_Order_Cost_TrxType)
	{
		set_Value (COLUMNNAME_PP_Order_Cost_TrxType, PP_Order_Cost_TrxType);
	}

	@Override
	public java.lang.String getPP_Order_Cost_TrxType() 
	{
		return get_ValueAsString(COLUMNNAME_PP_Order_Cost_TrxType);
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
}