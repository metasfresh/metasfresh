// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for RV_PP_Product_BOMLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RV_PP_Product_BOMLine extends org.compiere.model.PO implements I_RV_PP_Product_BOMLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -854433493L;

    /** Standard Constructor */
    public X_RV_PP_Product_BOMLine (final Properties ctx, final int RV_PP_Product_BOMLine_ID, @Nullable final String trxName)
    {
      super (ctx, RV_PP_Product_BOMLine_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_PP_Product_BOMLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsQtyPercentage (final boolean IsQtyPercentage)
	{
		set_Value (COLUMNNAME_IsQtyPercentage, IsQtyPercentage);
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
	public void setQtyBatch (final BigDecimal QtyBatch)
	{
		set_Value (COLUMNNAME_QtyBatch, QtyBatch);
	}

	@Override
	public BigDecimal getQtyBatch() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBatch);
		return bd != null ? bd : BigDecimal.ZERO;
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