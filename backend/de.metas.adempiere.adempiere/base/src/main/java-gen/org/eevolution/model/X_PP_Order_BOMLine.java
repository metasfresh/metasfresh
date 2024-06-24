// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order_BOMLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_BOMLine extends org.compiere.model.PO implements I_PP_Order_BOMLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1884950035L;

    /** Standard Constructor */
    public X_PP_Order_BOMLine (final Properties ctx, final int PP_Order_BOMLine_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_BOMLine_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_BOMLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAssay (final @Nullable BigDecimal Assay)
	{
		set_ValueNoCheck (COLUMNNAME_Assay, Assay);
	}

	@Override
	public BigDecimal getAssay() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Assay);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBackflushGroup (final @Nullable java.lang.String BackflushGroup)
	{
		set_ValueNoCheck (COLUMNNAME_BackflushGroup, BackflushGroup);
	}

	@Override
	public java.lang.String getBackflushGroup() 
	{
		return get_ValueAsString(COLUMNNAME_BackflushGroup);
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
	public void setCULabelQuanitity (final @Nullable java.lang.String CULabelQuanitity)
	{
		set_Value (COLUMNNAME_CULabelQuanitity, CULabelQuanitity);
	}

	@Override
	public java.lang.String getCULabelQuanitity() 
	{
		return get_ValueAsString(COLUMNNAME_CULabelQuanitity);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setCurrent_Receiving_LU_HU_ID (final int Current_Receiving_LU_HU_ID)
	{
		if (Current_Receiving_LU_HU_ID < 1) 
			set_Value (COLUMNNAME_Current_Receiving_LU_HU_ID, null);
		else 
			set_Value (COLUMNNAME_Current_Receiving_LU_HU_ID, Current_Receiving_LU_HU_ID);
	}

	@Override
	public int getCurrent_Receiving_LU_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Current_Receiving_LU_HU_ID);
	}

	@Override
	public void setCurrent_Receiving_TU_PI_Item_Product_ID (final int Current_Receiving_TU_PI_Item_Product_ID)
	{
		if (Current_Receiving_TU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_Current_Receiving_TU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Current_Receiving_TU_PI_Item_Product_ID, Current_Receiving_TU_PI_Item_Product_ID);
	}

	@Override
	public int getCurrent_Receiving_TU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Current_Receiving_TU_PI_Item_Product_ID);
	}

	@Override
	public void setDateDelivered (final @Nullable java.sql.Timestamp DateDelivered)
	{
		set_Value (COLUMNNAME_DateDelivered, DateDelivered);
	}

	@Override
	public java.sql.Timestamp getDateDelivered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDelivered);
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
	public void setFeature (final @Nullable java.lang.String Feature)
	{
		set_Value (COLUMNNAME_Feature, Feature);
	}

	@Override
	public java.lang.String getFeature() 
	{
		return get_ValueAsString(COLUMNNAME_Feature);
	}

	@Override
	public void setForecast (final @Nullable BigDecimal Forecast)
	{
		set_ValueNoCheck (COLUMNNAME_Forecast, Forecast);
	}

	@Override
	public BigDecimal getForecast() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Forecast);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setIsAllowIssuingAnyProduct (final boolean IsAllowIssuingAnyProduct)
	{
		set_ValueNoCheck (COLUMNNAME_IsAllowIssuingAnyProduct, IsAllowIssuingAnyProduct);
	}

	@Override
	public boolean isAllowIssuingAnyProduct() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowIssuingAnyProduct);
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
	public void setIsEnforceIssuingTolerance (final boolean IsEnforceIssuingTolerance)
	{
		set_Value (COLUMNNAME_IsEnforceIssuingTolerance, IsEnforceIssuingTolerance);
	}

	@Override
	public boolean isEnforceIssuingTolerance() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEnforceIssuingTolerance);
	}

	@Override
	public void setIsManualQtyInput (final boolean IsManualQtyInput)
	{
		set_Value (COLUMNNAME_IsManualQtyInput, IsManualQtyInput);
	}

	@Override
	public boolean isManualQtyInput() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManualQtyInput);
	}

	@Override
	public void setIsQtyPercentage (final boolean IsQtyPercentage)
	{
		set_ValueNoCheck (COLUMNNAME_IsQtyPercentage, IsQtyPercentage);
	}

	@Override
	public boolean isQtyPercentage() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQtyPercentage);
	}

	/** 
	 * IssueMethod AD_Reference_ID=53226
	 * Reference name: PP_Product_BOM IssueMethod
	 */
	public static final int ISSUEMETHOD_AD_Reference_ID=53226;
	/** Issue = 0 */
	public static final String ISSUEMETHOD_Issue = "0";
	/** Backflush = 1 */
	public static final String ISSUEMETHOD_Backflush = "1";
	/** Floor Stock = 2 */
	public static final String ISSUEMETHOD_FloorStock = "2";
	/** IssueOnlyForReceived = 9 */
	public static final String ISSUEMETHOD_IssueOnlyForReceived = "9";
	@Override
	public void setIssueMethod (final @Nullable java.lang.String IssueMethod)
	{
		set_Value (COLUMNNAME_IssueMethod, IssueMethod);
	}

	@Override
	public java.lang.String getIssueMethod() 
	{
		return get_ValueAsString(COLUMNNAME_IssueMethod);
	}

	@Override
	public void setIssuingTolerance_Perc (final @Nullable BigDecimal IssuingTolerance_Perc)
	{
		set_Value (COLUMNNAME_IssuingTolerance_Perc, IssuingTolerance_Perc);
	}

	@Override
	public BigDecimal getIssuingTolerance_Perc() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_IssuingTolerance_Perc);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIssuingTolerance_Qty (final @Nullable BigDecimal IssuingTolerance_Qty)
	{
		set_Value (COLUMNNAME_IssuingTolerance_Qty, IssuingTolerance_Qty);
	}

	@Override
	public BigDecimal getIssuingTolerance_Qty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_IssuingTolerance_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIssuingTolerance_UOM_ID (final int IssuingTolerance_UOM_ID)
	{
		if (IssuingTolerance_UOM_ID < 1) 
			set_Value (COLUMNNAME_IssuingTolerance_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_IssuingTolerance_UOM_ID, IssuingTolerance_UOM_ID);
	}

	@Override
	public int getIssuingTolerance_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_IssuingTolerance_UOM_ID);
	}

	/** 
	 * IssuingTolerance_ValueType AD_Reference_ID=541693
	 * Reference name: IssuingTolerance_ValueType
	 */
	public static final int ISSUINGTOLERANCE_VALUETYPE_AD_Reference_ID=541693;
	/** Percentage = P */
	public static final String ISSUINGTOLERANCE_VALUETYPE_Percentage = "P";
	/** Quantity = Q */
	public static final String ISSUINGTOLERANCE_VALUETYPE_Quantity = "Q";
	@Override
	public void setIssuingTolerance_ValueType (final @Nullable java.lang.String IssuingTolerance_ValueType)
	{
		set_Value (COLUMNNAME_IssuingTolerance_ValueType, IssuingTolerance_ValueType);
	}

	@Override
	public java.lang.String getIssuingTolerance_ValueType() 
	{
		return get_ValueAsString(COLUMNNAME_IssuingTolerance_ValueType);
	}

	@Override
	public void setLeadTimeOffset (final int LeadTimeOffset)
	{
		set_Value (COLUMNNAME_LeadTimeOffset, LeadTimeOffset);
	}

	@Override
	public int getLeadTimeOffset() 
	{
		return get_ValueAsInt(COLUMNNAME_LeadTimeOffset);
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
	public org.compiere.model.I_M_ChangeNotice getM_ChangeNotice()
	{
		return get_ValueAsPO(COLUMNNAME_M_ChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class);
	}

	@Override
	public void setM_ChangeNotice(final org.compiere.model.I_M_ChangeNotice M_ChangeNotice)
	{
		set_ValueFromPO(COLUMNNAME_M_ChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class, M_ChangeNotice);
	}

	@Override
	public void setM_ChangeNotice_ID (final int M_ChangeNotice_ID)
	{
		if (M_ChangeNotice_ID < 1) 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, null);
		else 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, M_ChangeNotice_ID);
	}

	@Override
	public int getM_ChangeNotice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ChangeNotice_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
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
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_BOM getPP_Order_BOM()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_BOM_ID, org.eevolution.model.I_PP_Order_BOM.class);
	}

	@Override
	public void setPP_Order_BOM(final org.eevolution.model.I_PP_Order_BOM PP_Order_BOM)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_BOM_ID, org.eevolution.model.I_PP_Order_BOM.class, PP_Order_BOM);
	}

	@Override
	public void setPP_Order_BOM_ID (final int PP_Order_BOM_ID)
	{
		if (PP_Order_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOM_ID, PP_Order_BOM_ID);
	}

	@Override
	public int getPP_Order_BOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_BOM_ID);
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
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMLine_ID, PP_Product_BOMLine_ID);
	}

	@Override
	public int getPP_Product_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOMLine_ID);
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
	public void setQtyBatch (final BigDecimal QtyBatch)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBatch, QtyBatch);
	}

	@Override
	public BigDecimal getQtyBatch() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBatch);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyBeforeClose (final BigDecimal QtyBeforeClose)
	{
		set_Value (COLUMNNAME_QtyBeforeClose, QtyBeforeClose);
	}

	@Override
	public BigDecimal getQtyBeforeClose() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBeforeClose);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyBOM (final BigDecimal QtyBOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBOM, QtyBOM);
	}

	@Override
	public BigDecimal getQtyBOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDelivered (final BigDecimal QtyDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public BigDecimal getQtyDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredActual (final BigDecimal QtyDeliveredActual)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDeliveredActual, QtyDeliveredActual);
	}

	@Override
	public BigDecimal getQtyDeliveredActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEntered (final @Nullable BigDecimal QtyEntered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPost (final BigDecimal QtyPost)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPost, QtyPost);
	}

	@Override
	public BigDecimal getQtyPost() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPost);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReject (final BigDecimal QtyReject)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReject, QtyReject);
	}

	@Override
	public BigDecimal getQtyReject() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReject);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyRequiered (final BigDecimal QtyRequiered)
	{
		set_Value (COLUMNNAME_QtyRequiered, QtyRequiered);
	}

	@Override
	public BigDecimal getQtyRequiered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyRequiered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final BigDecimal QtyReserved)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyScrap (final BigDecimal QtyScrap)
	{
		set_ValueNoCheck (COLUMNNAME_QtyScrap, QtyScrap);
	}

	@Override
	public BigDecimal getQtyScrap() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyScrap);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyUsageVariance (final BigDecimal QtyUsageVariance)
	{
		set_Value (COLUMNNAME_QtyUsageVariance, QtyUsageVariance);
	}

	@Override
	public BigDecimal getQtyUsageVariance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyUsageVariance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setScrap (final @Nullable BigDecimal Scrap)
	{
		set_ValueNoCheck (COLUMNNAME_Scrap, Scrap);
	}

	@Override
	public BigDecimal getScrap() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Scrap);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setShowSubBOMIngredients (final boolean ShowSubBOMIngredients)
	{
		set_Value (COLUMNNAME_ShowSubBOMIngredients, ShowSubBOMIngredients);
	}

	@Override
	public boolean isShowSubBOMIngredients() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ShowSubBOMIngredients);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
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

	/** 
	 * VariantGroup AD_Reference_ID=540490
	 * Reference name: VariantGroup
	 */
	public static final int VARIANTGROUP_AD_Reference_ID=540490;
	/** 01 = 01 */
	public static final String VARIANTGROUP_01 = "01";
	/** 02 = 02 */
	public static final String VARIANTGROUP_02 = "02";
	/** 03 = 03 */
	public static final String VARIANTGROUP_03 = "03";
	/** 04 = 04 */
	public static final String VARIANTGROUP_04 = "04";
	/** 05 = 05 */
	public static final String VARIANTGROUP_05 = "05";
	/** 06 = 06 */
	public static final String VARIANTGROUP_06 = "06";
	/** 07 = 07 */
	public static final String VARIANTGROUP_07 = "07";
	/** 08 = 08 */
	public static final String VARIANTGROUP_08 = "08";
	/** 09 = 09 */
	public static final String VARIANTGROUP_09 = "09";
	@Override
	public void setVariantGroup (final @Nullable java.lang.String VariantGroup)
	{
		set_Value (COLUMNNAME_VariantGroup, VariantGroup);
	}

	@Override
	public java.lang.String getVariantGroup() 
	{
		return get_ValueAsString(COLUMNNAME_VariantGroup);
	}
}