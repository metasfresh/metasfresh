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

	private static final long serialVersionUID = -1901519157L;

    /** Standard Constructor */
    public X_PP_Order_Cost (Properties ctx, int PP_Order_Cost_ID, String trxName)
    {
      super (ctx, PP_Order_Cost_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_Cost (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
	}

	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setCostDistributionPercent (java.math.BigDecimal CostDistributionPercent)
	{
		set_Value (COLUMNNAME_CostDistributionPercent, CostDistributionPercent);
	}

	@Override
	public java.math.BigDecimal getCostDistributionPercent() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostDistributionPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCumulatedAmt (java.math.BigDecimal CumulatedAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CumulatedAmt, CumulatedAmt);
	}

	@Override
	public java.math.BigDecimal getCumulatedAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CumulatedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCumulatedQty (java.math.BigDecimal CumulatedQty)
	{
		set_ValueNoCheck (COLUMNNAME_CumulatedQty, CumulatedQty);
	}

	@Override
	public java.math.BigDecimal getCumulatedQty() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CumulatedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCurrentCostPrice (java.math.BigDecimal CurrentCostPrice)
	{
		set_ValueNoCheck (COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
	}

	@Override
	public java.math.BigDecimal getCurrentCostPrice() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrentCostPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCurrentCostPriceLL (java.math.BigDecimal CurrentCostPriceLL)
	{
		set_ValueNoCheck (COLUMNNAME_CurrentCostPriceLL, CurrentCostPriceLL);
	}

	@Override
	public java.math.BigDecimal getCurrentCostPriceLL() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrentCostPriceLL);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
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
	public void setM_CostElement(org.compiere.model.I_M_CostElement M_CostElement)
	{
		set_ValueFromPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class, M_CostElement);
	}

	@Override
	public void setM_CostElement_ID (int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
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
	public void setM_CostType(org.compiere.model.I_M_CostType M_CostType)
	{
		set_ValueFromPO(COLUMNNAME_M_CostType_ID, org.compiere.model.I_M_CostType.class, M_CostType);
	}

	@Override
	public void setM_CostType_ID (int M_CostType_ID)
	{
		if (M_CostType_ID < 1) 
			set_Value (COLUMNNAME_M_CostType_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostType_ID, Integer.valueOf(M_CostType_ID));
	}

	@Override
	public int getM_CostType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostType_ID);
	}

	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setPostCalculationAmt (java.math.BigDecimal PostCalculationAmt)
	{
		set_Value (COLUMNNAME_PostCalculationAmt, PostCalculationAmt);
	}

	@Override
	public java.math.BigDecimal getPostCalculationAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PostCalculationAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPP_Order_Cost_ID (int PP_Order_Cost_ID)
	{
		if (PP_Order_Cost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Cost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Cost_ID, Integer.valueOf(PP_Order_Cost_ID));
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
	public void setPP_Order_Cost_TrxType (java.lang.String PP_Order_Cost_TrxType)
	{

		set_Value (COLUMNNAME_PP_Order_Cost_TrxType, PP_Order_Cost_TrxType);
	}

	@Override
	public java.lang.String getPP_Order_Cost_TrxType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PP_Order_Cost_TrxType);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}
}