// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for T_BOMLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_T_BOMLine extends org.compiere.model.PO implements I_T_BOMLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1163201524L;

    /** Standard Constructor */
    public X_T_BOMLine (final Properties ctx, final int T_BOMLine_ID, @Nullable final String trxName)
    {
      super (ctx, T_BOMLine_ID, trxName);
    }

    /** Load Constructor */
    public X_T_BOMLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
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
	public void setCost (final @Nullable BigDecimal Cost)
	{
		set_Value (COLUMNNAME_Cost, Cost);
	}

	@Override
	public BigDecimal getCost() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Cost);
		return bd != null ? bd : BigDecimal.ZERO;
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
	@Override
	public void setCostingMethod (final @Nullable java.lang.String CostingMethod)
	{
		set_Value (COLUMNNAME_CostingMethod, CostingMethod);
	}

	@Override
	public java.lang.String getCostingMethod() 
	{
		return get_ValueAsString(COLUMNNAME_CostingMethod);
	}

	@Override
	public void setCostStandard (final @Nullable BigDecimal CostStandard)
	{
		set_Value (COLUMNNAME_CostStandard, CostStandard);
	}

	@Override
	public BigDecimal getCostStandard() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostStandard);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setCurrentCostPriceLL (final @Nullable BigDecimal CurrentCostPriceLL)
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
	public void setFutureCostPrice (final @Nullable BigDecimal FutureCostPrice)
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
	public void setImplosion (final boolean Implosion)
	{
		set_Value (COLUMNNAME_Implosion, Implosion);
	}

	@Override
	public boolean isImplosion() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Implosion);
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
	public void setLevelNo (final int LevelNo)
	{
		set_Value (COLUMNNAME_LevelNo, LevelNo);
	}

	@Override
	public int getLevelNo() 
	{
		return get_ValueAsInt(COLUMNNAME_LevelNo);
	}

	@Override
	public void setLevels (final @Nullable java.lang.String Levels)
	{
		set_Value (COLUMNNAME_Levels, Levels);
	}

	@Override
	public java.lang.String getLevels() 
	{
		return get_ValueAsString(COLUMNNAME_Levels);
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
	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class);
	}

	@Override
	public void setPP_Product_BOM(final org.eevolution.model.I_PP_Product_BOM PP_Product_BOM)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class, PP_Product_BOM);
	}

	@Override
	public void setPP_Product_BOM_ID (final int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, PP_Product_BOM_ID);
	}

	@Override
	public int getPP_Product_BOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOM_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class);
	}

	@Override
	public void setPP_Product_BOMLine(final org.eevolution.model.I_PP_Product_BOMLine PP_Product_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class, PP_Product_BOMLine);
	}

	@Override
	public void setPP_Product_BOMLine_ID (final int PP_Product_BOMLine_ID)
	{
		if (PP_Product_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, PP_Product_BOMLine_ID);
	}

	@Override
	public int getPP_Product_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOMLine_ID);
	}

	@Override
	public void setQtyBOM (final @Nullable BigDecimal QtyBOM)
	{
		set_Value (COLUMNNAME_QtyBOM, QtyBOM);
	}

	@Override
	public BigDecimal getQtyBOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSel_Product_ID (final int Sel_Product_ID)
	{
		if (Sel_Product_ID < 1) 
			set_Value (COLUMNNAME_Sel_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Sel_Product_ID, Sel_Product_ID);
	}

	@Override
	public int getSel_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Sel_Product_ID);
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

	@Override
	public void setT_BOMLine_ID (final int T_BOMLine_ID)
	{
		if (T_BOMLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_T_BOMLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_T_BOMLine_ID, T_BOMLine_ID);
	}

	@Override
	public int getT_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_T_BOMLine_ID);
	}
}