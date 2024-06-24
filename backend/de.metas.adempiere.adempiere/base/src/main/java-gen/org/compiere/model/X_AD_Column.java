// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Column
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Column extends org.compiere.model.PO implements I_AD_Column, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1082024214L;

    /** Standard Constructor */
    public X_AD_Column (final Properties ctx, final int AD_Column_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Column_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Column (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Column_ID (final int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
	}

	@Override
	public org.compiere.model.I_AD_Element getAD_Element()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Element(final org.compiere.model.I_AD_Element AD_Element)
	{
		set_ValueFromPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class, AD_Element);
	}

	@Override
	public void setAD_Element_ID (final int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, AD_Element_ID);
	}

	@Override
	public int getAD_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Element_ID);
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(final org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (final int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
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

	/** 
	 * CloningStrategy AD_Reference_ID=541754
	 * Reference name: AD_Column_CloningStrategy
	 */
	public static final int CLONINGSTRATEGY_AD_Reference_ID=541754;
	/** DirectCopy = DC */
	public static final String CLONINGSTRATEGY_DirectCopy = "DC";
	/** UseDefaultValue = DV */
	public static final String CLONINGSTRATEGY_UseDefaultValue = "DV";
	/** MakeUnique = UQ */
	public static final String CLONINGSTRATEGY_MakeUnique = "UQ";
	/** Skip = SK */
	public static final String CLONINGSTRATEGY_Skip = "SK";
	/** Auto = XX */
	public static final String CLONINGSTRATEGY_Auto = "XX";
	@Override
	public void setCloningStrategy (final java.lang.String CloningStrategy)
	{
		set_Value (COLUMNNAME_CloningStrategy, CloningStrategy);
	}

	@Override
	public java.lang.String getCloningStrategy() 
	{
		return get_ValueAsString(COLUMNNAME_CloningStrategy);
	}

	@Override
	public void setColumnName (final java.lang.String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	@Override
	public java.lang.String getColumnName() 
	{
		return get_ValueAsString(COLUMNNAME_ColumnName);
	}

	@Override
	public void setColumnSQL (final @Nullable java.lang.String ColumnSQL)
	{
		set_Value (COLUMNNAME_ColumnSQL, ColumnSQL);
	}

	@Override
	public java.lang.String getColumnSQL() 
	{
		return get_ValueAsString(COLUMNNAME_ColumnSQL);
	}

	@Override
	public void setDDL_NoForeignKey (final boolean DDL_NoForeignKey)
	{
		set_Value (COLUMNNAME_DDL_NoForeignKey, DDL_NoForeignKey);
	}

	@Override
	public boolean isDDL_NoForeignKey() 
	{
		return get_ValueAsBoolean(COLUMNNAME_DDL_NoForeignKey);
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
	public void setFacetFilterSeqNo (final int FacetFilterSeqNo)
	{
		set_Value (COLUMNNAME_FacetFilterSeqNo, FacetFilterSeqNo);
	}

	@Override
	public int getFacetFilterSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_FacetFilterSeqNo);
	}

	@Override
	public void setFieldLength (final int FieldLength)
	{
		set_Value (COLUMNNAME_FieldLength, FieldLength);
	}

	@Override
	public int getFieldLength() 
	{
		return get_ValueAsInt(COLUMNNAME_FieldLength);
	}

	@Override
	public void setFilterDefaultValue (final @Nullable java.lang.String FilterDefaultValue)
	{
		set_Value (COLUMNNAME_FilterDefaultValue, FilterDefaultValue);
	}

	@Override
	public java.lang.String getFilterDefaultValue() 
	{
		return get_ValueAsString(COLUMNNAME_FilterDefaultValue);
	}

	/** 
	 * FilterOperator AD_Reference_ID=541241
	 * Reference name: FilterOperator
	 */
	public static final int FILTEROPERATOR_AD_Reference_ID=541241;
	/** EqualsOrLike = E */
	public static final String FILTEROPERATOR_EqualsOrLike = "E";
	/** Between = B */
	public static final String FILTEROPERATOR_Between = "B";
	/** NotNull = N */
	public static final String FILTEROPERATOR_NotNull = "N";
	@Override
	public void setFilterOperator (final @Nullable java.lang.String FilterOperator)
	{
		set_Value (COLUMNNAME_FilterOperator, FilterOperator);
	}

	@Override
	public java.lang.String getFilterOperator() 
	{
		return get_ValueAsString(COLUMNNAME_FilterOperator);
	}

	@Override
	public void setFormatPattern (final @Nullable java.lang.String FormatPattern)
	{
		set_Value (COLUMNNAME_FormatPattern, FormatPattern);
	}

	@Override
	public java.lang.String getFormatPattern() 
	{
		return get_ValueAsString(COLUMNNAME_FormatPattern);
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
	public void setIsAllowLogging (final boolean IsAllowLogging)
	{
		set_Value (COLUMNNAME_IsAllowLogging, IsAllowLogging);
	}

	@Override
	public boolean isAllowLogging() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowLogging);
	}

	@Override
	public void setIsAlwaysUpdateable (final boolean IsAlwaysUpdateable)
	{
		set_Value (COLUMNNAME_IsAlwaysUpdateable, IsAlwaysUpdateable);
	}

	@Override
	public boolean isAlwaysUpdateable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAlwaysUpdateable);
	}

	@Override
	public void setIsAutoApplyValidationRule (final boolean IsAutoApplyValidationRule)
	{
		set_Value (COLUMNNAME_IsAutoApplyValidationRule, IsAutoApplyValidationRule);
	}

	@Override
	public boolean isAutoApplyValidationRule() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoApplyValidationRule);
	}

	@Override
	public void setIsAutocomplete (final boolean IsAutocomplete)
	{
		set_Value (COLUMNNAME_IsAutocomplete, IsAutocomplete);
	}

	@Override
	public boolean isAutocomplete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutocomplete);
	}

	@Override
	public void setIsCalculated (final boolean IsCalculated)
	{
		set_Value (COLUMNNAME_IsCalculated, IsCalculated);
	}

	@Override
	public boolean isCalculated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCalculated);
	}

	/** 
	 * IsEncrypted AD_Reference_ID=354
	 * Reference name: AD_Column Encrypted
	 */
	public static final int ISENCRYPTED_AD_Reference_ID=354;
	/** Encrypted = Y */
	public static final String ISENCRYPTED_Encrypted = "Y";
	/** Nicht verschlüsselt = N */
	public static final String ISENCRYPTED_NichtVerschluesselt = "N";
	@Override
	public void setIsEncrypted (final java.lang.String IsEncrypted)
	{
		set_Value (COLUMNNAME_IsEncrypted, IsEncrypted);
	}

	@Override
	public java.lang.String getIsEncrypted() 
	{
		return get_ValueAsString(COLUMNNAME_IsEncrypted);
	}

	@Override
	public void setIsExcludeFromZoomTargets (final boolean IsExcludeFromZoomTargets)
	{
		set_Value (COLUMNNAME_IsExcludeFromZoomTargets, IsExcludeFromZoomTargets);
	}

	@Override
	public boolean isExcludeFromZoomTargets() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludeFromZoomTargets);
	}

	@Override
	public void setIsFacetFilter (final boolean IsFacetFilter)
	{
		set_Value (COLUMNNAME_IsFacetFilter, IsFacetFilter);
	}

	@Override
	public boolean isFacetFilter() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFacetFilter);
	}

	@Override
	public void setIsForceIncludeInGeneratedModel (final boolean IsForceIncludeInGeneratedModel)
	{
		set_Value (COLUMNNAME_IsForceIncludeInGeneratedModel, IsForceIncludeInGeneratedModel);
	}

	@Override
	public boolean isForceIncludeInGeneratedModel() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsForceIncludeInGeneratedModel);
	}

	@Override
	public void setIsGenericZoomOrigin (final boolean IsGenericZoomOrigin)
	{
		set_Value (COLUMNNAME_IsGenericZoomOrigin, IsGenericZoomOrigin);
	}

	@Override
	public boolean isGenericZoomOrigin() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsGenericZoomOrigin);
	}

	@Override
	public void setIsIdentifier (final boolean IsIdentifier)
	{
		set_Value (COLUMNNAME_IsIdentifier, IsIdentifier);
	}

	@Override
	public boolean isIdentifier() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsIdentifier);
	}

	@Override
	public void setIsKey (final boolean IsKey)
	{
		set_Value (COLUMNNAME_IsKey, IsKey);
	}

	@Override
	public boolean isKey() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsKey);
	}

	@Override
	public void setIsLazyLoading (final boolean IsLazyLoading)
	{
		set_Value (COLUMNNAME_IsLazyLoading, IsLazyLoading);
	}

	@Override
	public boolean isLazyLoading() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsLazyLoading);
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
	public void setIsParent (final boolean IsParent)
	{
		set_Value (COLUMNNAME_IsParent, IsParent);
	}

	@Override
	public boolean isParent() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsParent);
	}

	@Override
	public void setIsRestAPICustomColumn (final boolean IsRestAPICustomColumn)
	{
		set_Value (COLUMNNAME_IsRestAPICustomColumn, IsRestAPICustomColumn);
	}

	@Override
	public boolean isRestAPICustomColumn() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRestAPICustomColumn);
	}

	@Override
	public void setIsSelectionColumn (final boolean IsSelectionColumn)
	{
		set_Value (COLUMNNAME_IsSelectionColumn, IsSelectionColumn);
	}

	@Override
	public boolean isSelectionColumn() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSelectionColumn);
	}

	@Override
	public void setIsShowFilterIncrementButtons (final boolean IsShowFilterIncrementButtons)
	{
		set_Value (COLUMNNAME_IsShowFilterIncrementButtons, IsShowFilterIncrementButtons);
	}

	@Override
	public boolean isShowFilterIncrementButtons() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShowFilterIncrementButtons);
	}

	@Override
	public void setIsShowFilterInline (final boolean IsShowFilterInline)
	{
		set_Value (COLUMNNAME_IsShowFilterInline, IsShowFilterInline);
	}

	@Override
	public boolean isShowFilterInline() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShowFilterInline);
	}

	@Override
	public void setIsStaleable (final boolean IsStaleable)
	{
		set_Value (COLUMNNAME_IsStaleable, IsStaleable);
	}

	@Override
	public boolean isStaleable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsStaleable);
	}

	@Override
	public void setIsSyncDatabase (final @Nullable java.lang.String IsSyncDatabase)
	{
		set_Value (COLUMNNAME_IsSyncDatabase, IsSyncDatabase);
	}

	@Override
	public java.lang.String getIsSyncDatabase() 
	{
		return get_ValueAsString(COLUMNNAME_IsSyncDatabase);
	}

	@Override
	public void setIsTranslated (final boolean IsTranslated)
	{
		set_Value (COLUMNNAME_IsTranslated, IsTranslated);
	}

	@Override
	public boolean isTranslated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTranslated);
	}

	@Override
	public void setIsUpdateable (final boolean IsUpdateable)
	{
		set_Value (COLUMNNAME_IsUpdateable, IsUpdateable);
	}

	@Override
	public boolean isUpdateable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUpdateable);
	}

	@Override
	public void setIsUseDocSequence (final boolean IsUseDocSequence)
	{
		set_Value (COLUMNNAME_IsUseDocSequence, IsUseDocSequence);
	}

	@Override
	public boolean isUseDocSequence() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseDocSequence);
	}

	@Override
	public void setMandatoryLogic (final @Nullable java.lang.String MandatoryLogic)
	{
		set_Value (COLUMNNAME_MandatoryLogic, MandatoryLogic);
	}

	@Override
	public java.lang.String getMandatoryLogic() 
	{
		return get_ValueAsString(COLUMNNAME_MandatoryLogic);
	}

	@Override
	public void setMaxFacetsToFetch (final int MaxFacetsToFetch)
	{
		set_Value (COLUMNNAME_MaxFacetsToFetch, MaxFacetsToFetch);
	}

	@Override
	public int getMaxFacetsToFetch() 
	{
		return get_ValueAsInt(COLUMNNAME_MaxFacetsToFetch);
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
	 * PersonalDataCategory AD_Reference_ID=540857
	 * Reference name: PersonalDataCategory
	 */
	public static final int PERSONALDATACATEGORY_AD_Reference_ID=540857;
	/** NotPersonal = NP */
	public static final String PERSONALDATACATEGORY_NotPersonal = "NP";
	/** Personal = P */
	public static final String PERSONALDATACATEGORY_Personal = "P";
	/** SensitivePersonal = SP */
	public static final String PERSONALDATACATEGORY_SensitivePersonal = "SP";
	@Override
	public void setPersonalDataCategory (final @Nullable java.lang.String PersonalDataCategory)
	{
		set_Value (COLUMNNAME_PersonalDataCategory, PersonalDataCategory);
	}

	@Override
	public java.lang.String getPersonalDataCategory() 
	{
		return get_ValueAsString(COLUMNNAME_PersonalDataCategory);
	}

	@Override
	public void setReadOnlyLogic (final @Nullable java.lang.String ReadOnlyLogic)
	{
		set_Value (COLUMNNAME_ReadOnlyLogic, ReadOnlyLogic);
	}

	@Override
	public java.lang.String getReadOnlyLogic() 
	{
		return get_ValueAsString(COLUMNNAME_ReadOnlyLogic);
	}

	@Override
	public void setSelectionColumnSeqNo (final int SelectionColumnSeqNo)
	{
		set_Value (COLUMNNAME_SelectionColumnSeqNo, SelectionColumnSeqNo);
	}

	@Override
	public int getSelectionColumnSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SelectionColumnSeqNo);
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
	public void setTechnicalNote (final @Nullable java.lang.String TechnicalNote)
	{
		set_Value (COLUMNNAME_TechnicalNote, TechnicalNote);
	}

	@Override
	public java.lang.String getTechnicalNote() 
	{
		return get_ValueAsString(COLUMNNAME_TechnicalNote);
	}

	@Override
	public void setValueMax (final @Nullable java.lang.String ValueMax)
	{
		set_Value (COLUMNNAME_ValueMax, ValueMax);
	}

	@Override
	public java.lang.String getValueMax() 
	{
		return get_ValueAsString(COLUMNNAME_ValueMax);
	}

	@Override
	public void setValueMin (final @Nullable java.lang.String ValueMin)
	{
		set_Value (COLUMNNAME_ValueMin, ValueMin);
	}

	@Override
	public java.lang.String getValueMin() 
	{
		return get_ValueAsString(COLUMNNAME_ValueMin);
	}

	@Override
	public void setVersion (final BigDecimal Version)
	{
		set_Value (COLUMNNAME_Version, Version);
	}

	@Override
	public BigDecimal getVersion() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Version);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setVFormat (final @Nullable java.lang.String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	@Override
	public java.lang.String getVFormat() 
	{
		return get_ValueAsString(COLUMNNAME_VFormat);
	}
}