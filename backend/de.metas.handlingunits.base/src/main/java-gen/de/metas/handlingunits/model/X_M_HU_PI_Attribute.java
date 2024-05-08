// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_PI_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_PI_Attribute extends org.compiere.model.PO implements I_M_HU_PI_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -87011726L;

    /** Standard Constructor */
    public X_M_HU_PI_Attribute (final Properties ctx, final int M_HU_PI_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_PI_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_PI_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAggregationStrategy_JavaClass_ID (final int AggregationStrategy_JavaClass_ID)
	{
		if (AggregationStrategy_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_AggregationStrategy_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_AggregationStrategy_JavaClass_ID, AggregationStrategy_JavaClass_ID);
	}

	@Override
	public int getAggregationStrategy_JavaClass_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AggregationStrategy_JavaClass_ID);
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
	public void setHU_TansferStrategy_JavaClass_ID (final int HU_TansferStrategy_JavaClass_ID)
	{
		if (HU_TansferStrategy_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_HU_TansferStrategy_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_HU_TansferStrategy_JavaClass_ID, HU_TansferStrategy_JavaClass_ID);
	}

	@Override
	public int getHU_TansferStrategy_JavaClass_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HU_TansferStrategy_JavaClass_ID);
	}

	@Override
	public void setIsDisplayed (final boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, IsDisplayed);
	}

	@Override
	public boolean isDisplayed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayed);
	}

	@Override
	public void setIsInstanceAttribute (final boolean IsInstanceAttribute)
	{
		set_Value (COLUMNNAME_IsInstanceAttribute, IsInstanceAttribute);
	}

	@Override
	public boolean isInstanceAttribute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInstanceAttribute);
	}

	@Override
	public void setIsMandatory (final boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	@Override
	public boolean isMandatory() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMandatory);
	}

	@Override
	public void setIsOnlyIfInProductAttributeSet (final boolean IsOnlyIfInProductAttributeSet)
	{
		set_Value (COLUMNNAME_IsOnlyIfInProductAttributeSet, IsOnlyIfInProductAttributeSet);
	}

	@Override
	public boolean isOnlyIfInProductAttributeSet() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOnlyIfInProductAttributeSet);
	}

	@Override
	public void setIsReadOnly (final boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, IsReadOnly);
	}

	@Override
	public boolean isReadOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReadOnly);
	}

	@Override
	public void setM_Attribute_ID (final int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, M_Attribute_ID);
	}

	@Override
	public int getM_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Attribute_ID);
	}

	@Override
	public void setM_HU_PI_Attribute_ID (final int M_HU_PI_Attribute_ID)
	{
		if (M_HU_PI_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Attribute_ID, M_HU_PI_Attribute_ID);
	}

	@Override
	public int getM_HU_PI_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Attribute_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class);
	}

	@Override
	public void setM_HU_PI_Version(final de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class, M_HU_PI_Version);
	}

	@Override
	public void setM_HU_PI_Version_ID (final int M_HU_PI_Version_ID)
	{
		if (M_HU_PI_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Version_ID, M_HU_PI_Version_ID);
	}

	@Override
	public int getM_HU_PI_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Version_ID);
	}

	/** 
	 * PropagationType AD_Reference_ID=540404
	 * Reference name: M_HU_PI_Attribute_PropagationType
	 */
	public static final int PROPAGATIONTYPE_AD_Reference_ID=540404;
	/** TopDown = TOPD */
	public static final String PROPAGATIONTYPE_TopDown = "TOPD";
	/** BottomUp = BOTU */
	public static final String PROPAGATIONTYPE_BottomUp = "BOTU";
	/** NoPropagation = NONE */
	public static final String PROPAGATIONTYPE_NoPropagation = "NONE";
	@Override
	public void setPropagationType (final java.lang.String PropagationType)
	{
		set_Value (COLUMNNAME_PropagationType, PropagationType);
	}

	@Override
	public java.lang.String getPropagationType() 
	{
		return get_ValueAsString(COLUMNNAME_PropagationType);
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
	public void setSplitterStrategy_JavaClass_ID (final int SplitterStrategy_JavaClass_ID)
	{
		if (SplitterStrategy_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_SplitterStrategy_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_SplitterStrategy_JavaClass_ID, SplitterStrategy_JavaClass_ID);
	}

	@Override
	public int getSplitterStrategy_JavaClass_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SplitterStrategy_JavaClass_ID);
	}

	@Override
	public void setUseInASI (final boolean UseInASI)
	{
		set_Value (COLUMNNAME_UseInASI, UseInASI);
	}

	@Override
	public boolean isUseInASI() 
	{
		return get_ValueAsBoolean(COLUMNNAME_UseInASI);
	}
}