// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for RV_PP_Cost_BOMLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RV_PP_Cost_BOMLine extends org.compiere.model.PO implements I_RV_PP_Cost_BOMLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1995649221L;

    /** Standard Constructor */
    public X_RV_PP_Cost_BOMLine (final Properties ctx, final int RV_PP_Cost_BOMLine_ID, @Nullable final String trxName)
    {
      super (ctx, RV_PP_Cost_BOMLine_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_PP_Cost_BOMLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * ComponentType AD_Reference_ID=53225
	 * Reference name: PP_ComponentType
	 */
	public static final int COMPONENTTYPE_AD_Reference_ID=53225;
	/** By-Product = BY */
	public static final String COMPONENTTYPE_By_Product = "BY";
	/** Component = CO */
	public static final String COMPONENTTYPE_Component = "CO";
	/** Phantom = PH */
	public static final String COMPONENTTYPE_Phantom = "PH";
	/** Packing = PK */
	public static final String COMPONENTTYPE_Packing = "PK";
	/** Planning = PL */
	public static final String COMPONENTTYPE_Planning = "PL";
	/** Tools = TL */
	public static final String COMPONENTTYPE_Tools = "TL";
	/** Option = OP */
	public static final String COMPONENTTYPE_Option = "OP";
	/** Variant = VA */
	public static final String COMPONENTTYPE_Variant = "VA";
	/** Co-Product = CP */
	public static final String COMPONENTTYPE_Co_Product = "CP";
	/** Scrap = SC */
	public static final String COMPONENTTYPE_Scrap = "SC";
	/** Product = PR */
	public static final String COMPONENTTYPE_Product = "PR";
	@Override
	public void setComponentType (final @Nullable java.lang.String ComponentType)
	{
		set_Value (COLUMNNAME_ComponentType, ComponentType);
	}

	@Override
	public java.lang.String getComponentType() 
	{
		return get_ValueAsString(COLUMNNAME_ComponentType);
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
	public void setIsCritical (final boolean IsCritical)
	{
		set_Value (COLUMNNAME_IsCritical, IsCritical);
	}

	@Override
	public boolean isCritical() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCritical);
	}

	@Override
	public void setIsQtyPercentage (final @Nullable BigDecimal IsQtyPercentage)
	{
		set_Value (COLUMNNAME_IsQtyPercentage, IsQtyPercentage);
	}

	@Override
	public BigDecimal getIsQtyPercentage() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_IsQtyPercentage);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIssueMethod (final boolean IssueMethod)
	{
		set_Value (COLUMNNAME_IssueMethod, IssueMethod);
	}

	@Override
	public boolean issueMethod() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IssueMethod);
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
	public void setScrap (final @Nullable BigDecimal Scrap)
	{
		set_Value (COLUMNNAME_Scrap, Scrap);
	}

	@Override
	public BigDecimal getScrap() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Scrap);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setTM_Product_ID (final int TM_Product_ID)
	{
		if (TM_Product_ID < 1) 
			set_Value (COLUMNNAME_TM_Product_ID, null);
		else 
			set_Value (COLUMNNAME_TM_Product_ID, TM_Product_ID);
	}

	@Override
	public int getTM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_TM_Product_ID);
	}

	@Override
	public void setValidFrom (final @Nullable java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}
}