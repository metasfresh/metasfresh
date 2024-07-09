// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Tab
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Tab extends org.compiere.model.PO implements I_AD_Tab, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1494329261L;

    /** Standard Constructor */
    public X_AD_Tab (final Properties ctx, final int AD_Tab_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Tab_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Tab (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Column getAD_ColumnSortOrder()
	{
		return get_ValueAsPO(COLUMNNAME_AD_ColumnSortOrder_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_ColumnSortOrder(final org.compiere.model.I_AD_Column AD_ColumnSortOrder)
	{
		set_ValueFromPO(COLUMNNAME_AD_ColumnSortOrder_ID, org.compiere.model.I_AD_Column.class, AD_ColumnSortOrder);
	}

	@Override
	public void setAD_ColumnSortOrder_ID (final int AD_ColumnSortOrder_ID)
	{
		if (AD_ColumnSortOrder_ID < 1) 
			set_Value (COLUMNNAME_AD_ColumnSortOrder_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ColumnSortOrder_ID, AD_ColumnSortOrder_ID);
	}

	@Override
	public int getAD_ColumnSortOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ColumnSortOrder_ID);
	}

	@Override
	public org.compiere.model.I_AD_Column getAD_ColumnSortYesNo()
	{
		return get_ValueAsPO(COLUMNNAME_AD_ColumnSortYesNo_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_ColumnSortYesNo(final org.compiere.model.I_AD_Column AD_ColumnSortYesNo)
	{
		set_ValueFromPO(COLUMNNAME_AD_ColumnSortYesNo_ID, org.compiere.model.I_AD_Column.class, AD_ColumnSortYesNo);
	}

	@Override
	public void setAD_ColumnSortYesNo_ID (final int AD_ColumnSortYesNo_ID)
	{
		if (AD_ColumnSortYesNo_ID < 1) 
			set_Value (COLUMNNAME_AD_ColumnSortYesNo_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ColumnSortYesNo_ID, AD_ColumnSortYesNo_ID);
	}

	@Override
	public int getAD_ColumnSortYesNo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ColumnSortYesNo_ID);
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
	public org.compiere.model.I_AD_Image getAD_Image()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setAD_Image(final org.compiere.model.I_AD_Image AD_Image)
	{
		set_ValueFromPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class, AD_Image);
	}

	@Override
	public void setAD_Image_ID (final int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, AD_Image_ID);
	}

	@Override
	public int getAD_Image_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Image_ID);
	}

	@Override
	public void setAD_Message_ID (final int AD_Message_ID)
	{
		if (AD_Message_ID < 1) 
			set_Value (COLUMNNAME_AD_Message_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Message_ID, AD_Message_ID);
	}

	@Override
	public int getAD_Message_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Message_ID);
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public org.compiere.model.I_AD_Window getAD_Window()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setAD_Window(final org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	@Override
	public void setAD_Window_ID (final int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, AD_Window_ID);
	}

	@Override
	public int getAD_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Window_ID);
	}

	@Override
	public void setAllowQuickInput (final boolean AllowQuickInput)
	{
		set_Value (COLUMNNAME_AllowQuickInput, AllowQuickInput);
	}

	@Override
	public boolean isAllowQuickInput() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AllowQuickInput);
	}

	@Override
	public void setCommitWarning (final @Nullable java.lang.String CommitWarning)
	{
		set_Value (COLUMNNAME_CommitWarning, CommitWarning);
	}

	@Override
	public java.lang.String getCommitWarning() 
	{
		return get_ValueAsString(COLUMNNAME_CommitWarning);
	}

	@Override
	public void setDefaultWhereClause (final @Nullable java.lang.String DefaultWhereClause)
	{
		set_Value (COLUMNNAME_DefaultWhereClause, DefaultWhereClause);
	}

	@Override
	public java.lang.String getDefaultWhereClause() 
	{
		return get_ValueAsString(COLUMNNAME_DefaultWhereClause);
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
	public void setHasTree (final boolean HasTree)
	{
		set_Value (COLUMNNAME_HasTree, HasTree);
	}

	@Override
	public boolean isHasTree() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasTree);
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
	public void setImportFields (final @Nullable java.lang.String ImportFields)
	{
		set_Value (COLUMNNAME_ImportFields, ImportFields);
	}

	@Override
	public java.lang.String getImportFields() 
	{
		return get_ValueAsString(COLUMNNAME_ImportFields);
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

	/** 
	 * IncludedTabNewRecordInputMode AD_Reference_ID=541421
	 * Reference name: IncludedTabNewRecordInputMode
	 */
	public static final int INCLUDEDTABNEWRECORDINPUTMODE_AD_Reference_ID=541421;
	/** ALL_AVAILABLE_METHODS = A */
	public static final String INCLUDEDTABNEWRECORDINPUTMODE_ALL_AVAILABLE_METHODS = "A";
	/** Quick Input Only = Q */
	public static final String INCLUDEDTABNEWRECORDINPUTMODE_QuickInputOnly = "Q";
	@Override
	public void setIncludedTabNewRecordInputMode (final java.lang.String IncludedTabNewRecordInputMode)
	{
		set_Value (COLUMNNAME_IncludedTabNewRecordInputMode, IncludedTabNewRecordInputMode);
	}

	@Override
	public java.lang.String getIncludedTabNewRecordInputMode() 
	{
		return get_ValueAsString(COLUMNNAME_IncludedTabNewRecordInputMode);
	}

	/** 
	 * IncludeFiltersStrategy AD_Reference_ID=541862
	 * Reference name: IncludeFiltersStrategy
	 */
	public static final int INCLUDEFILTERSSTRATEGY_AD_Reference_ID=541862;
	/** None = N */
	public static final String INCLUDEFILTERSSTRATEGY_None = "N";
	/** Explicit = E */
	public static final String INCLUDEFILTERSSTRATEGY_Explicit = "E";
	/** Auto = A */
	public static final String INCLUDEFILTERSSTRATEGY_Auto = "A";
	@Override
	public void setIncludeFiltersStrategy (final @Nullable java.lang.String IncludeFiltersStrategy)
	{
		set_Value (COLUMNNAME_IncludeFiltersStrategy, IncludeFiltersStrategy);
	}

	@Override
	public java.lang.String getIncludeFiltersStrategy() 
	{
		return get_ValueAsString(COLUMNNAME_IncludeFiltersStrategy);
	}

	@Override
	public void setInternalName (final @Nullable java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setIsAdvancedTab (final boolean IsAdvancedTab)
	{
		set_Value (COLUMNNAME_IsAdvancedTab, IsAdvancedTab);
	}

	@Override
	public boolean isAdvancedTab() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAdvancedTab);
	}

	@Override
	public void setIsAutodetectDefaultDateFilter (final boolean IsAutodetectDefaultDateFilter)
	{
		set_Value (COLUMNNAME_IsAutodetectDefaultDateFilter, IsAutodetectDefaultDateFilter);
	}

	@Override
	public boolean isAutodetectDefaultDateFilter() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutodetectDefaultDateFilter);
	}

	@Override
	public void setIsCheckParentsChanged (final boolean IsCheckParentsChanged)
	{
		set_Value (COLUMNNAME_IsCheckParentsChanged, IsCheckParentsChanged);
	}

	@Override
	public boolean isCheckParentsChanged() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCheckParentsChanged);
	}

	@Override
	public void setIsGridModeOnly (final boolean IsGridModeOnly)
	{
		set_Value (COLUMNNAME_IsGridModeOnly, IsGridModeOnly);
	}

	@Override
	public boolean isGridModeOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsGridModeOnly);
	}

	@Override
	public void setIsInfoTab (final boolean IsInfoTab)
	{
		set_Value (COLUMNNAME_IsInfoTab, IsInfoTab);
	}

	@Override
	public boolean isInfoTab() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInfoTab);
	}

	@Override
	public void setIsInsertRecord (final boolean IsInsertRecord)
	{
		set_Value (COLUMNNAME_IsInsertRecord, IsInsertRecord);
	}

	@Override
	public boolean isInsertRecord() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInsertRecord);
	}

	@Override
	public void setIsQueryIfNoFilters (final boolean IsQueryIfNoFilters)
	{
		set_Value (COLUMNNAME_IsQueryIfNoFilters, IsQueryIfNoFilters);
	}

	@Override
	public boolean isQueryIfNoFilters() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQueryIfNoFilters);
	}

	@Override
	public void setIsQueryOnLoad (final boolean IsQueryOnLoad)
	{
		set_Value (COLUMNNAME_IsQueryOnLoad, IsQueryOnLoad);
	}

	@Override
	public boolean isQueryOnLoad() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQueryOnLoad);
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
	public void setIsRefreshAllOnActivate (final boolean IsRefreshAllOnActivate)
	{
		set_Value (COLUMNNAME_IsRefreshAllOnActivate, IsRefreshAllOnActivate);
	}

	@Override
	public boolean isRefreshAllOnActivate() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRefreshAllOnActivate);
	}

	@Override
	public void setIsRefreshViewOnChangeEvents (final boolean IsRefreshViewOnChangeEvents)
	{
		set_Value (COLUMNNAME_IsRefreshViewOnChangeEvents, IsRefreshViewOnChangeEvents);
	}

	@Override
	public boolean isRefreshViewOnChangeEvents() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRefreshViewOnChangeEvents);
	}

	@Override
	public void setIsSearchActive (final boolean IsSearchActive)
	{
		set_Value (COLUMNNAME_IsSearchActive, IsSearchActive);
	}

	@Override
	public boolean isSearchActive() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSearchActive);
	}

	@Override
	public void setIsSearchCollapsed (final boolean IsSearchCollapsed)
	{
		set_Value (COLUMNNAME_IsSearchCollapsed, IsSearchCollapsed);
	}

	@Override
	public boolean isSearchCollapsed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSearchCollapsed);
	}

	@Override
	public void setIsSingleRow (final boolean IsSingleRow)
	{
		set_Value (COLUMNNAME_IsSingleRow, IsSingleRow);
	}

	@Override
	public boolean isSingleRow() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSingleRow);
	}

	@Override
	public void setIsSortTab (final boolean IsSortTab)
	{
		set_Value (COLUMNNAME_IsSortTab, IsSortTab);
	}

	@Override
	public boolean isSortTab() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSortTab);
	}

	@Override
	public void setIsTranslationTab (final boolean IsTranslationTab)
	{
		set_Value (COLUMNNAME_IsTranslationTab, IsTranslationTab);
	}

	@Override
	public boolean isTranslationTab() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTranslationTab);
	}

	@Override
	public void setMaxQueryRecords (final int MaxQueryRecords)
	{
		set_Value (COLUMNNAME_MaxQueryRecords, MaxQueryRecords);
	}

	@Override
	public int getMaxQueryRecords() 
	{
		return get_ValueAsInt(COLUMNNAME_MaxQueryRecords);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setNotFound_Message (final @Nullable java.lang.String NotFound_Message)
	{
		set_Value (COLUMNNAME_NotFound_Message, NotFound_Message);
	}

	@Override
	public java.lang.String getNotFound_Message() 
	{
		return get_ValueAsString(COLUMNNAME_NotFound_Message);
	}

	@Override
	public void setNotFound_MessageDetail (final @Nullable java.lang.String NotFound_MessageDetail)
	{
		set_Value (COLUMNNAME_NotFound_MessageDetail, NotFound_MessageDetail);
	}

	@Override
	public java.lang.String getNotFound_MessageDetail() 
	{
		return get_ValueAsString(COLUMNNAME_NotFound_MessageDetail);
	}

	@Override
	public void setOrderByClause (final @Nullable java.lang.String OrderByClause)
	{
		set_Value (COLUMNNAME_OrderByClause, OrderByClause);
	}

	@Override
	public java.lang.String getOrderByClause() 
	{
		return get_ValueAsString(COLUMNNAME_OrderByClause);
	}

	@Override
	public org.compiere.model.I_AD_Column getParent_Column()
	{
		return get_ValueAsPO(COLUMNNAME_Parent_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setParent_Column(final org.compiere.model.I_AD_Column Parent_Column)
	{
		set_ValueFromPO(COLUMNNAME_Parent_Column_ID, org.compiere.model.I_AD_Column.class, Parent_Column);
	}

	@Override
	public void setParent_Column_ID (final int Parent_Column_ID)
	{
		if (Parent_Column_ID < 1) 
			set_Value (COLUMNNAME_Parent_Column_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_Column_ID, Parent_Column_ID);
	}

	@Override
	public int getParent_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Parent_Column_ID);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setQuickInput_CloseButton_Caption (final @Nullable java.lang.String QuickInput_CloseButton_Caption)
	{
		set_Value (COLUMNNAME_QuickInput_CloseButton_Caption, QuickInput_CloseButton_Caption);
	}

	@Override
	public java.lang.String getQuickInput_CloseButton_Caption() 
	{
		return get_ValueAsString(COLUMNNAME_QuickInput_CloseButton_Caption);
	}

	@Override
	public void setQuickInputLayout (final @Nullable java.lang.String QuickInputLayout)
	{
		set_Value (COLUMNNAME_QuickInputLayout, QuickInputLayout);
	}

	@Override
	public java.lang.String getQuickInputLayout() 
	{
		return get_ValueAsString(COLUMNNAME_QuickInputLayout);
	}

	@Override
	public void setQuickInput_OpenButton_Caption (final @Nullable java.lang.String QuickInput_OpenButton_Caption)
	{
		set_Value (COLUMNNAME_QuickInput_OpenButton_Caption, QuickInput_OpenButton_Caption);
	}

	@Override
	public java.lang.String getQuickInput_OpenButton_Caption() 
	{
		return get_ValueAsString(COLUMNNAME_QuickInput_OpenButton_Caption);
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
	public void setTabLevel (final int TabLevel)
	{
		set_Value (COLUMNNAME_TabLevel, TabLevel);
	}

	@Override
	public int getTabLevel() 
	{
		return get_ValueAsInt(COLUMNNAME_TabLevel);
	}

	@Override
	public org.compiere.model.I_AD_Tab getTemplate_Tab()
	{
		return get_ValueAsPO(COLUMNNAME_Template_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setTemplate_Tab(final org.compiere.model.I_AD_Tab Template_Tab)
	{
		set_ValueFromPO(COLUMNNAME_Template_Tab_ID, org.compiere.model.I_AD_Tab.class, Template_Tab);
	}

	@Override
	public void setTemplate_Tab_ID (final int Template_Tab_ID)
	{
		if (Template_Tab_ID < 1) 
			set_Value (COLUMNNAME_Template_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_Template_Tab_ID, Template_Tab_ID);
	}

	@Override
	public int getTemplate_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Template_Tab_ID);
	}

	@Override
	public void setWhereClause (final @Nullable java.lang.String WhereClause)
	{
		set_Value (COLUMNNAME_WhereClause, WhereClause);
	}

	@Override
	public java.lang.String getWhereClause() 
	{
		return get_ValueAsString(COLUMNNAME_WhereClause);
	}
}