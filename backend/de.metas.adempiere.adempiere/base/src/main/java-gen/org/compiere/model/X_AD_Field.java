// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Field
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Field extends org.compiere.model.PO implements I_AD_Field, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1789221161L;

    /** Standard Constructor */
    public X_AD_Field (final Properties ctx, final int AD_Field_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Field_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Field (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(final org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	@Override
	public void setAD_Column_ID (final int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
	}

	@Override
	public void setAD_Field_ID (final int AD_Field_ID)
	{
		if (AD_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Field_ID, AD_Field_ID);
	}

	@Override
	public int getAD_Field_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Field_ID);
	}

	@Override
	public org.compiere.model.I_AD_FieldGroup getAD_FieldGroup()
	{
		return get_ValueAsPO(COLUMNNAME_AD_FieldGroup_ID, org.compiere.model.I_AD_FieldGroup.class);
	}

	@Override
	public void setAD_FieldGroup(final org.compiere.model.I_AD_FieldGroup AD_FieldGroup)
	{
		set_ValueFromPO(COLUMNNAME_AD_FieldGroup_ID, org.compiere.model.I_AD_FieldGroup.class, AD_FieldGroup);
	}

	@Override
	public void setAD_FieldGroup_ID (final int AD_FieldGroup_ID)
	{
		if (AD_FieldGroup_ID < 1) 
			set_Value (COLUMNNAME_AD_FieldGroup_ID, null);
		else 
			set_Value (COLUMNNAME_AD_FieldGroup_ID, AD_FieldGroup_ID);
	}

	@Override
	public int getAD_FieldGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_FieldGroup_ID);
	}

	@Override
	public org.compiere.model.I_AD_Element getAD_Name()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Name_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Name(final org.compiere.model.I_AD_Element AD_Name)
	{
		set_ValueFromPO(COLUMNNAME_AD_Name_ID, org.compiere.model.I_AD_Element.class, AD_Name);
	}

	@Override
	public void setAD_Name_ID (final int AD_Name_ID)
	{
		if (AD_Name_ID < 1) 
			set_Value (COLUMNNAME_AD_Name_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Name_ID, AD_Name_ID);
	}

	@Override
	public int getAD_Name_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Name_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference(final org.compiere.model.I_AD_Reference AD_Reference)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class, AD_Reference);
	}

	@Override
	public void setAD_Reference_ID (final int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, AD_Reference_ID);
	}

	@Override
	public int getAD_Reference_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Value()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Value(final org.compiere.model.I_AD_Reference AD_Reference_Value)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Value);
	}

	@Override
	public void setAD_Reference_Value_ID (final int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, AD_Reference_Value_ID);
	}

	@Override
	public int getAD_Reference_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_Value_ID);
	}

	@Override
	public org.compiere.model.I_AD_Sequence getAD_Sequence()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setAD_Sequence(final org.compiere.model.I_AD_Sequence AD_Sequence)
	{
		set_ValueFromPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class, AD_Sequence);
	}

	@Override
	public void setAD_Sequence_ID (final int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_Value (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Sequence_ID, AD_Sequence_ID);
	}

	@Override
	public int getAD_Sequence_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Sequence_ID);
	}

	@Override
	public org.compiere.model.I_AD_Tab getAD_Tab()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setAD_Tab(final org.compiere.model.I_AD_Tab AD_Tab)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class, AD_Tab);
	}

	@Override
	public void setAD_Tab_ID (final int AD_Tab_ID)
	{
		if (AD_Tab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, AD_Tab_ID);
	}

	@Override
	public int getAD_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Tab_ID);
	}

	@Override
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setAD_Val_Rule(final org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, AD_Val_Rule);
	}

	@Override
	public void setAD_Val_Rule_ID (final int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, AD_Val_Rule_ID);
	}

	@Override
	public int getAD_Val_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Val_Rule_ID);
	}

	@Override
	public void setColorLogic (final @Nullable java.lang.String ColorLogic)
	{
		set_Value (COLUMNNAME_ColorLogic, ColorLogic);
	}

	@Override
	public java.lang.String getColorLogic() 
	{
		return get_ValueAsString(COLUMNNAME_ColorLogic);
	}

	@Override
	public void setColumnDisplayLength (final int ColumnDisplayLength)
	{
		set_Value (COLUMNNAME_ColumnDisplayLength, ColumnDisplayLength);
	}

	@Override
	public int getColumnDisplayLength() 
	{
		return get_ValueAsInt(COLUMNNAME_ColumnDisplayLength);
	}

	@Override
	public void setDefaultValue (final @Nullable java.lang.String DefaultValue)
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

	@Override
	public java.lang.String getDefaultValue() 
	{
		return get_ValueAsString(COLUMNNAME_DefaultValue);
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
	public void setDisplayLength (final int DisplayLength)
	{
		set_Value (COLUMNNAME_DisplayLength, DisplayLength);
	}

	@Override
	public int getDisplayLength() 
	{
		return get_ValueAsInt(COLUMNNAME_DisplayLength);
	}

	@Override
	public void setDisplayLogic (final @Nullable java.lang.String DisplayLogic)
	{
		set_Value (COLUMNNAME_DisplayLogic, DisplayLogic);
	}

	@Override
	public java.lang.String getDisplayLogic() 
	{
		return get_ValueAsString(COLUMNNAME_DisplayLogic);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public org.compiere.model.I_AD_Val_Rule getFilter_Val_Rule()
	{
		return get_ValueAsPO(COLUMNNAME_Filter_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setFilter_Val_Rule(final org.compiere.model.I_AD_Val_Rule Filter_Val_Rule)
	{
		set_ValueFromPO(COLUMNNAME_Filter_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, Filter_Val_Rule);
	}

	@Override
	public void setFilter_Val_Rule_ID (final int Filter_Val_Rule_ID)
	{
		if (Filter_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_Filter_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_Filter_Val_Rule_ID, Filter_Val_Rule_ID);
	}

	@Override
	public int getFilter_Val_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Filter_Val_Rule_ID);
	}

	@Override
	public void setIsForbidNewRecordCreation (final boolean IsForbidNewRecordCreation)
	{
		set_Value (COLUMNNAME_IsForbidNewRecordCreation, IsForbidNewRecordCreation);
	}

	@Override
	public boolean isForbidNewRecordCreation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsForbidNewRecordCreation);
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
	public org.compiere.model.I_AD_Tab getIncluded_Tab()
	{
		return get_ValueAsPO(COLUMNNAME_Included_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setIncluded_Tab(final org.compiere.model.I_AD_Tab Included_Tab)
	{
		set_ValueFromPO(COLUMNNAME_Included_Tab_ID, org.compiere.model.I_AD_Tab.class, Included_Tab);
	}

	@Override
	public void setIncluded_Tab_ID (final int Included_Tab_ID)
	{
		if (Included_Tab_ID < 1) 
			set_Value (COLUMNNAME_Included_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_Included_Tab_ID, Included_Tab_ID);
	}

	@Override
	public int getIncluded_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Included_Tab_ID);
	}

	@Override
	public void setIncludedTabHeight (final int IncludedTabHeight)
	{
		set_Value (COLUMNNAME_IncludedTabHeight, IncludedTabHeight);
	}

	@Override
	public int getIncludedTabHeight() 
	{
		return get_ValueAsInt(COLUMNNAME_IncludedTabHeight);
	}

	@Override
	public void setInfoFactoryClass (final @Nullable java.lang.String InfoFactoryClass)
	{
		set_Value (COLUMNNAME_InfoFactoryClass, InfoFactoryClass);
	}

	@Override
	public java.lang.String getInfoFactoryClass() 
	{
		return get_ValueAsString(COLUMNNAME_InfoFactoryClass);
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
	public void setIsDisplayedGrid (final boolean IsDisplayedGrid)
	{
		set_Value (COLUMNNAME_IsDisplayedGrid, IsDisplayedGrid);
	}

	@Override
	public boolean isDisplayedGrid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayedGrid);
	}

	@Override
	public void setIsEncrypted (final boolean IsEncrypted)
	{
		set_Value (COLUMNNAME_IsEncrypted, IsEncrypted);
	}

	@Override
	public boolean isEncrypted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEncrypted);
	}

	/** 
	 * IsExcludeFromZoomTargets AD_Reference_ID=540528
	 * Reference name: Yes_No
	 */
	public static final int ISEXCLUDEFROMZOOMTARGETS_AD_Reference_ID=540528;
	/** Yes = Y */
	public static final String ISEXCLUDEFROMZOOMTARGETS_Yes = "Y";
	/** No = N */
	public static final String ISEXCLUDEFROMZOOMTARGETS_No = "N";
	@Override
	public void setIsExcludeFromZoomTargets (final @Nullable java.lang.String IsExcludeFromZoomTargets)
	{
		set_Value (COLUMNNAME_IsExcludeFromZoomTargets, IsExcludeFromZoomTargets);
	}

	@Override
	public java.lang.String getIsExcludeFromZoomTargets() 
	{
		return get_ValueAsString(COLUMNNAME_IsExcludeFromZoomTargets);
	}

	@Override
	public void setIsFieldOnly (final boolean IsFieldOnly)
	{
		set_Value (COLUMNNAME_IsFieldOnly, IsFieldOnly);
	}

	@Override
	public boolean isFieldOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFieldOnly);
	}

	/** 
	 * IsFilterField AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISFILTERFIELD_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISFILTERFIELD_Yes = "Y";
	/** No = N */
	public static final String ISFILTERFIELD_No = "N";
	@Override
	public void setIsFilterField (final @Nullable java.lang.String IsFilterField)
	{
		set_Value (COLUMNNAME_IsFilterField, IsFilterField);
	}

	@Override
	public java.lang.String getIsFilterField() 
	{
		return get_ValueAsString(COLUMNNAME_IsFilterField);
	}

	@Override
	public void setIsHeading (final boolean IsHeading)
	{
		set_Value (COLUMNNAME_IsHeading, IsHeading);
	}

	@Override
	public boolean isHeading() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsHeading);
	}

	/** 
	 * IsMandatory AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISMANDATORY_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISMANDATORY_Yes = "Y";
	/** No = N */
	public static final String ISMANDATORY_No = "N";
	@Override
	public void setIsMandatory (final @Nullable java.lang.String IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	@Override
	public java.lang.String getIsMandatory() 
	{
		return get_ValueAsString(COLUMNNAME_IsMandatory);
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
	public void setIsSameLine (final boolean IsSameLine)
	{
		set_Value (COLUMNNAME_IsSameLine, IsSameLine);
	}

	@Override
	public boolean isSameLine() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSameLine);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	/** 
	 * ObscureType AD_Reference_ID=291
	 * Reference name: AD_Field ObscureType
	 */
	public static final int OBSCURETYPE_AD_Reference_ID=291;
	/** Obscure Digits but last 4 = 904 */
	public static final String OBSCURETYPE_ObscureDigitsButLast4 = "904";
	/** Obscure Digits but first/last 4 = 944 */
	public static final String OBSCURETYPE_ObscureDigitsButFirstLast4 = "944";
	/** Obscure AlphaNumeric but first/last 4 = A44 */
	public static final String OBSCURETYPE_ObscureAlphaNumericButFirstLast4 = "A44";
	/** Obscure AlphaNumeric but last 4 = A04 */
	public static final String OBSCURETYPE_ObscureAlphaNumericButLast4 = "A04";
	@Override
	public void setObscureType (final @Nullable java.lang.String ObscureType)
	{
		set_Value (COLUMNNAME_ObscureType, ObscureType);
	}

	@Override
	public java.lang.String getObscureType() 
	{
		return get_ValueAsString(COLUMNNAME_ObscureType);
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
	public void setSeqNoGrid (final int SeqNoGrid)
	{
		set_Value (COLUMNNAME_SeqNoGrid, SeqNoGrid);
	}

	@Override
	public int getSeqNoGrid() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNoGrid);
	}

	@Override
	public void setSortNo (final @Nullable BigDecimal SortNo)
	{
		set_Value (COLUMNNAME_SortNo, SortNo);
	}

	@Override
	public BigDecimal getSortNo() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SortNo);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSpanX (final int SpanX)
	{
		set_Value (COLUMNNAME_SpanX, SpanX);
	}

	@Override
	public int getSpanX() 
	{
		return get_ValueAsInt(COLUMNNAME_SpanX);
	}

	@Override
	public void setSpanY (final int SpanY)
	{
		set_Value (COLUMNNAME_SpanY, SpanY);
	}

	@Override
	public int getSpanY() 
	{
		return get_ValueAsInt(COLUMNNAME_SpanY);
	}
}