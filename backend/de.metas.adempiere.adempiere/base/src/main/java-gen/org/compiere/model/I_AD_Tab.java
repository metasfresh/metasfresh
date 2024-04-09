package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Tab
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Tab 
{

	String Table_Name = "AD_Tab";

//	/** AD_Table_ID=106 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	@Nullable org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(@Nullable org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_AD_Tab.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Order Column.
	 * Column determining the order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_ColumnSortOrder_ID (int AD_ColumnSortOrder_ID);

	/**
	 * Get Order Column.
	 * Column determining the order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_ColumnSortOrder_ID();

	@Nullable org.compiere.model.I_AD_Column getAD_ColumnSortOrder();

	void setAD_ColumnSortOrder(@Nullable org.compiere.model.I_AD_Column AD_ColumnSortOrder);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column> COLUMN_AD_ColumnSortOrder_ID = new ModelColumn<>(I_AD_Tab.class, "AD_ColumnSortOrder_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_ColumnSortOrder_ID = "AD_ColumnSortOrder_ID";

	/**
	 * Set Included Column.
	 * Column determining if a Table Column is included in Ordering
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_ColumnSortYesNo_ID (int AD_ColumnSortYesNo_ID);

	/**
	 * Get Included Column.
	 * Column determining if a Table Column is included in Ordering
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_ColumnSortYesNo_ID();

	@Nullable org.compiere.model.I_AD_Column getAD_ColumnSortYesNo();

	void setAD_ColumnSortYesNo(@Nullable org.compiere.model.I_AD_Column AD_ColumnSortYesNo);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column> COLUMN_AD_ColumnSortYesNo_ID = new ModelColumn<>(I_AD_Tab.class, "AD_ColumnSortYesNo_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_ColumnSortYesNo_ID = "AD_ColumnSortYesNo_ID";

	/**
	 * Set System Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Element_ID();

	org.compiere.model.I_AD_Element getAD_Element();

	void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new ModelColumn<>(I_AD_Tab.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
	String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

	/**
	 * Set Image.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Image_ID (int AD_Image_ID);

	/**
	 * Get Image.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Image_ID();

	@Nullable org.compiere.model.I_AD_Image getAD_Image();

	void setAD_Image(@Nullable org.compiere.model.I_AD_Image AD_Image);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Image> COLUMN_AD_Image_ID = new ModelColumn<>(I_AD_Tab.class, "AD_Image_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_AD_Image_ID = "AD_Image_ID";

	/**
	 * Set Message.
	 * System Message
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Message_ID (int AD_Message_ID);

	/**
	 * Get Message.
	 * System Message
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Message_ID();

	String COLUMNNAME_AD_Message_ID = "AD_Message_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Process.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Process.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	@Nullable org.compiere.model.I_AD_Process getAD_Process();

	void setAD_Process(@Nullable org.compiere.model.I_AD_Process AD_Process);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_AD_Tab.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Tab.
	 * Tab within a Window
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Tab.
	 * Tab within a Window
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Tab_ID();

	ModelColumn<I_AD_Tab, Object> COLUMN_AD_Tab_ID = new ModelColumn<>(I_AD_Tab.class, "AD_Tab_ID", null);
	String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Window.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Window.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Window_ID();

	org.compiere.model.I_AD_Window getAD_Window();

	void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new ModelColumn<>(I_AD_Tab.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Allow Quick Input.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllowQuickInput (boolean AllowQuickInput);

	/**
	 * Get Allow Quick Input.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowQuickInput();

	ModelColumn<I_AD_Tab, Object> COLUMN_AllowQuickInput = new ModelColumn<>(I_AD_Tab.class, "AllowQuickInput", null);
	String COLUMNNAME_AllowQuickInput = "AllowQuickInput";

	/**
	 * Set Commit Warning.
	 * Warning displayed when saving
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCommitWarning (@Nullable java.lang.String CommitWarning);

	/**
	 * Get Commit Warning.
	 * Warning displayed when saving
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCommitWarning();

	ModelColumn<I_AD_Tab, Object> COLUMN_CommitWarning = new ModelColumn<>(I_AD_Tab.class, "CommitWarning", null);
	String COLUMNNAME_CommitWarning = "CommitWarning";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Tab, Object> COLUMN_Created = new ModelColumn<>(I_AD_Tab.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Default Selected Record.
	 * default SQL WHERE clause for selecting a default line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDefaultWhereClause (@Nullable java.lang.String DefaultWhereClause);

	/**
	 * Get Default Selected Record.
	 * default SQL WHERE clause for selecting a default line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDefaultWhereClause();

	ModelColumn<I_AD_Tab, Object> COLUMN_DefaultWhereClause = new ModelColumn<>(I_AD_Tab.class, "DefaultWhereClause", null);
	String COLUMNNAME_DefaultWhereClause = "DefaultWhereClause";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Tab, Object> COLUMN_Description = new ModelColumn<>(I_AD_Tab.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Display Logic.
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDisplayLogic (@Nullable java.lang.String DisplayLogic);

	/**
	 * Get Display Logic.
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDisplayLogic();

	ModelColumn<I_AD_Tab, Object> COLUMN_DisplayLogic = new ModelColumn<>(I_AD_Tab.class, "DisplayLogic", null);
	String COLUMNNAME_DisplayLogic = "DisplayLogic";

	/**
	 * Set Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_AD_Tab, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Tab.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Has Tree.
	 * Window has Tree Graph
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHasTree (boolean HasTree);

	/**
	 * Get Has Tree.
	 * Window has Tree Graph
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHasTree();

	ModelColumn<I_AD_Tab, Object> COLUMN_HasTree = new ModelColumn<>(I_AD_Tab.class, "HasTree", null);
	String COLUMNNAME_HasTree = "HasTree";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_AD_Tab, Object> COLUMN_Help = new ModelColumn<>(I_AD_Tab.class, "Help", null);
	String COLUMNNAME_Help = "Help";

	/**
	 * Set Create Fields.
	 * Create Field from Table Column, which do not exist in the Tab yet
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportFields (@Nullable java.lang.String ImportFields);

	/**
	 * Get Create Fields.
	 * Create Field from Table Column, which do not exist in the Tab yet
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImportFields();

	ModelColumn<I_AD_Tab, Object> COLUMN_ImportFields = new ModelColumn<>(I_AD_Tab.class, "ImportFields", null);
	String COLUMNNAME_ImportFields = "ImportFields";

	/**
	 * Set Included Tab.
	 * Included Tab in this Tab (Master Detail)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncluded_Tab_ID (int Included_Tab_ID);

	/**
	 * Get Included Tab.
	 * Included Tab in this Tab (Master Detail)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getIncluded_Tab_ID();

	@Nullable org.compiere.model.I_AD_Tab getIncluded_Tab();

	void setIncluded_Tab(@Nullable org.compiere.model.I_AD_Tab Included_Tab);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Tab> COLUMN_Included_Tab_ID = new ModelColumn<>(I_AD_Tab.class, "Included_Tab_ID", org.compiere.model.I_AD_Tab.class);
	String COLUMNNAME_Included_Tab_ID = "Included_Tab_ID";

	/**
	 * Set New Record Input Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIncludedTabNewRecordInputMode (java.lang.String IncludedTabNewRecordInputMode);

	/**
	 * Get New Record Input Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getIncludedTabNewRecordInputMode();

	ModelColumn<I_AD_Tab, Object> COLUMN_IncludedTabNewRecordInputMode = new ModelColumn<>(I_AD_Tab.class, "IncludedTabNewRecordInputMode", null);
	String COLUMNNAME_IncludedTabNewRecordInputMode = "IncludedTabNewRecordInputMode";

	/**
	 * Set Include filters.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncludeFiltersStrategy (@Nullable java.lang.String IncludeFiltersStrategy);

	/**
	 * Get Include filters.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncludeFiltersStrategy();

	ModelColumn<I_AD_Tab, Object> COLUMN_IncludeFiltersStrategy = new ModelColumn<>(I_AD_Tab.class, "IncludeFiltersStrategy", null);
	String COLUMNNAME_IncludeFiltersStrategy = "IncludeFiltersStrategy";

	/**
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInternalName (@Nullable java.lang.String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInternalName();

	ModelColumn<I_AD_Tab, Object> COLUMN_InternalName = new ModelColumn<>(I_AD_Tab.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Tab.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Advanced Tab.
	 * This Tab contains advanced Functionality
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAdvancedTab (boolean IsAdvancedTab);

	/**
	 * Get Advanced Tab.
	 * This Tab contains advanced Functionality
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAdvancedTab();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsAdvancedTab = new ModelColumn<>(I_AD_Tab.class, "IsAdvancedTab", null);
	String COLUMNNAME_IsAdvancedTab = "IsAdvancedTab";

	/**
	 * Set Autodetect Default Date Filter .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutodetectDefaultDateFilter (boolean IsAutodetectDefaultDateFilter);

	/**
	 * Get Autodetect Default Date Filter .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutodetectDefaultDateFilter();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsAutodetectDefaultDateFilter = new ModelColumn<>(I_AD_Tab.class, "IsAutodetectDefaultDateFilter", null);
	String COLUMNNAME_IsAutodetectDefaultDateFilter = "IsAutodetectDefaultDateFilter";

	/**
	 * Set Check Parents Changed.
	 * Before saving a record in this tab shall we check if the parent tabs were changed?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCheckParentsChanged (boolean IsCheckParentsChanged);

	/**
	 * Get Check Parents Changed.
	 * Before saving a record in this tab shall we check if the parent tabs were changed?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCheckParentsChanged();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsCheckParentsChanged = new ModelColumn<>(I_AD_Tab.class, "IsCheckParentsChanged", null);
	String COLUMNNAME_IsCheckParentsChanged = "IsCheckParentsChanged";

	/**
	 * Set Grid Mode Only.
	 * Allow grid mode only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsGridModeOnly (boolean IsGridModeOnly);

	/**
	 * Get Grid Mode Only.
	 * Allow grid mode only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isGridModeOnly();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsGridModeOnly = new ModelColumn<>(I_AD_Tab.class, "IsGridModeOnly", null);
	String COLUMNNAME_IsGridModeOnly = "IsGridModeOnly";

	/**
	 * Set Accounting Tab.
	 * This Tab contains accounting information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsInfoTab (boolean IsInfoTab);

	/**
	 * Get Accounting Tab.
	 * This Tab contains accounting information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isInfoTab();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsInfoTab = new ModelColumn<>(I_AD_Tab.class, "IsInfoTab", null);
	String COLUMNNAME_IsInfoTab = "IsInfoTab";

	/**
	 * Set Insert Record.
	 * The user can insert a new Record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInsertRecord (boolean IsInsertRecord);

	/**
	 * Get Insert Record.
	 * The user can insert a new Record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInsertRecord();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsInsertRecord = new ModelColumn<>(I_AD_Tab.class, "IsInsertRecord", null);
	String COLUMNNAME_IsInsertRecord = "IsInsertRecord";

	/**
	 * Set Allow querying when not filtered.
	 * Allow view querying even if user didn't apply any filter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsQueryIfNoFilters (boolean IsQueryIfNoFilters);

	/**
	 * Get Allow querying when not filtered.
	 * Allow view querying even if user didn't apply any filter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isQueryIfNoFilters();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsQueryIfNoFilters = new ModelColumn<>(I_AD_Tab.class, "IsQueryIfNoFilters", null);
	String COLUMNNAME_IsQueryIfNoFilters = "IsQueryIfNoFilters";

	/**
	 * Set Query data on load.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsQueryOnLoad (boolean IsQueryOnLoad);

	/**
	 * Get Query data on load.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isQueryOnLoad();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsQueryOnLoad = new ModelColumn<>(I_AD_Tab.class, "IsQueryOnLoad", null);
	String COLUMNNAME_IsQueryOnLoad = "IsQueryOnLoad";

	/**
	 * Set readonly.
	 * Feld / Eintrag / Berecih ist schreibgeschützt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReadOnly (boolean IsReadOnly);

	/**
	 * Get readonly.
	 * Feld / Eintrag / Berecih ist schreibgeschützt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReadOnly();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsReadOnly = new ModelColumn<>(I_AD_Tab.class, "IsReadOnly", null);
	String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/**
	 * Set Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRefreshAllOnActivate (boolean IsRefreshAllOnActivate);

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRefreshAllOnActivate();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsRefreshAllOnActivate = new ModelColumn<>(I_AD_Tab.class, "IsRefreshAllOnActivate", null);
	String COLUMNNAME_IsRefreshAllOnActivate = "IsRefreshAllOnActivate";

	/**
	 * Set Refresh view on change events.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRefreshViewOnChangeEvents (boolean IsRefreshViewOnChangeEvents);

	/**
	 * Get Refresh view on change events.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRefreshViewOnChangeEvents();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsRefreshViewOnChangeEvents = new ModelColumn<>(I_AD_Tab.class, "IsRefreshViewOnChangeEvents", null);
	String COLUMNNAME_IsRefreshViewOnChangeEvents = "IsRefreshViewOnChangeEvents";

	/**
	 * Set Search Active.
	 * This mark activates the search button from toolbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSearchActive (boolean IsSearchActive);

	/**
	 * Get Search Active.
	 * This mark activates the search button from toolbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSearchActive();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsSearchActive = new ModelColumn<>(I_AD_Tab.class, "IsSearchActive", null);
	String COLUMNNAME_IsSearchActive = "IsSearchActive";

	/**
	 * Set Collapse Search Panel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSearchCollapsed (boolean IsSearchCollapsed);

	/**
	 * Get Collapse Search Panel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSearchCollapsed();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsSearchCollapsed = new ModelColumn<>(I_AD_Tab.class, "IsSearchCollapsed", null);
	String COLUMNNAME_IsSearchCollapsed = "IsSearchCollapsed";

	/**
	 * Set Single Row Layout.
	 * Default for toggle between Single- and Multi-Row (Grid) Layout
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSingleRow (boolean IsSingleRow);

	/**
	 * Get Single Row Layout.
	 * Default for toggle between Single- and Multi-Row (Grid) Layout
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSingleRow();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsSingleRow = new ModelColumn<>(I_AD_Tab.class, "IsSingleRow", null);
	String COLUMNNAME_IsSingleRow = "IsSingleRow";

	/**
	 * Set Order Tab.
	 * The Tab determines the Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSortTab (boolean IsSortTab);

	/**
	 * Get Order Tab.
	 * The Tab determines the Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSortTab();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsSortTab = new ModelColumn<>(I_AD_Tab.class, "IsSortTab", null);
	String COLUMNNAME_IsSortTab = "IsSortTab";

	/**
	 * Set TranslationTab.
	 * This Tab contains translation information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTranslationTab (boolean IsTranslationTab);

	/**
	 * Get TranslationTab.
	 * This Tab contains translation information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTranslationTab();

	ModelColumn<I_AD_Tab, Object> COLUMN_IsTranslationTab = new ModelColumn<>(I_AD_Tab.class, "IsTranslationTab", null);
	String COLUMNNAME_IsTranslationTab = "IsTranslationTab";

	/**
	 * Set Max Query Records.
	 * If defined, you cannot query more records as defined - the query criteria needs to be changed to query less records
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMaxQueryRecords (int MaxQueryRecords);

	/**
	 * Get Max Query Records.
	 * If defined, you cannot query more records as defined - the query criteria needs to be changed to query less records
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMaxQueryRecords();

	ModelColumn<I_AD_Tab, Object> COLUMN_MaxQueryRecords = new ModelColumn<>(I_AD_Tab.class, "MaxQueryRecords", null);
	String COLUMNNAME_MaxQueryRecords = "MaxQueryRecords";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_AD_Tab, Object> COLUMN_Name = new ModelColumn<>(I_AD_Tab.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Not Found Message.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNotFound_Message (@Nullable java.lang.String NotFound_Message);

	/**
	 * Get Not Found Message.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNotFound_Message();

	ModelColumn<I_AD_Tab, Object> COLUMN_NotFound_Message = new ModelColumn<>(I_AD_Tab.class, "NotFound_Message", null);
	String COLUMNNAME_NotFound_Message = "NotFound_Message";

	/**
	 * Set Not Found Message Detail.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNotFound_MessageDetail (@Nullable java.lang.String NotFound_MessageDetail);

	/**
	 * Get Not Found Message Detail.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNotFound_MessageDetail();

	ModelColumn<I_AD_Tab, Object> COLUMN_NotFound_MessageDetail = new ModelColumn<>(I_AD_Tab.class, "NotFound_MessageDetail", null);
	String COLUMNNAME_NotFound_MessageDetail = "NotFound_MessageDetail";

	/**
	 * Set SQL ORDER BY.
	 * Fully qualified ORDER BY clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderByClause (@Nullable java.lang.String OrderByClause);

	/**
	 * Get SQL ORDER BY.
	 * Fully qualified ORDER BY clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderByClause();

	ModelColumn<I_AD_Tab, Object> COLUMN_OrderByClause = new ModelColumn<>(I_AD_Tab.class, "OrderByClause", null);
	String COLUMNNAME_OrderByClause = "OrderByClause";

	/**
	 * Set Parent Column.
	 * The link column on the parent tab.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParent_Column_ID (int Parent_Column_ID);

	/**
	 * Get Parent Column.
	 * The link column on the parent tab.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParent_Column_ID();

	@Nullable org.compiere.model.I_AD_Column getParent_Column();

	void setParent_Column(@Nullable org.compiere.model.I_AD_Column Parent_Column);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column> COLUMN_Parent_Column_ID = new ModelColumn<>(I_AD_Tab.class, "Parent_Column_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_Parent_Column_ID = "Parent_Column_ID";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_AD_Tab, Object> COLUMN_Processing = new ModelColumn<>(I_AD_Tab.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Quick Input Close Button Caption.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQuickInput_CloseButton_Caption (@Nullable java.lang.String QuickInput_CloseButton_Caption);

	/**
	 * Get Quick Input Close Button Caption.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQuickInput_CloseButton_Caption();

	ModelColumn<I_AD_Tab, Object> COLUMN_QuickInput_CloseButton_Caption = new ModelColumn<>(I_AD_Tab.class, "QuickInput_CloseButton_Caption", null);
	String COLUMNNAME_QuickInput_CloseButton_Caption = "QuickInput_CloseButton_Caption";

	/**
	 * Set Quick Input Layout.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQuickInputLayout (@Nullable java.lang.String QuickInputLayout);

	/**
	 * Get Quick Input Layout.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQuickInputLayout();

	ModelColumn<I_AD_Tab, Object> COLUMN_QuickInputLayout = new ModelColumn<>(I_AD_Tab.class, "QuickInputLayout", null);
	String COLUMNNAME_QuickInputLayout = "QuickInputLayout";

	/**
	 * Set Quick Input Open Button Caption.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQuickInput_OpenButton_Caption (@Nullable java.lang.String QuickInput_OpenButton_Caption);

	/**
	 * Get Quick Input Open Button Caption.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQuickInput_OpenButton_Caption();

	ModelColumn<I_AD_Tab, Object> COLUMN_QuickInput_OpenButton_Caption = new ModelColumn<>(I_AD_Tab.class, "QuickInput_OpenButton_Caption", null);
	String COLUMNNAME_QuickInput_OpenButton_Caption = "QuickInput_OpenButton_Caption";

	/**
	 * Set Read Only Logic.
	 * Logic to determine if field is read only (applies only when field is read-write)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReadOnlyLogic (@Nullable java.lang.String ReadOnlyLogic);

	/**
	 * Get Read Only Logic.
	 * Logic to determine if field is read only (applies only when field is read-write)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReadOnlyLogic();

	ModelColumn<I_AD_Tab, Object> COLUMN_ReadOnlyLogic = new ModelColumn<>(I_AD_Tab.class, "ReadOnlyLogic", null);
	String COLUMNNAME_ReadOnlyLogic = "ReadOnlyLogic";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_AD_Tab, Object> COLUMN_SeqNo = new ModelColumn<>(I_AD_Tab.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Tab Level.
	 * Hierarchical Tab Level (0 = top)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTabLevel (int TabLevel);

	/**
	 * Get Tab Level.
	 * Hierarchical Tab Level (0 = top)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getTabLevel();

	ModelColumn<I_AD_Tab, Object> COLUMN_TabLevel = new ModelColumn<>(I_AD_Tab.class, "TabLevel", null);
	String COLUMNNAME_TabLevel = "TabLevel";

	/**
	 * Set Template Tab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTemplate_Tab_ID (int Template_Tab_ID);

	/**
	 * Get Template Tab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getTemplate_Tab_ID();

	@Nullable org.compiere.model.I_AD_Tab getTemplate_Tab();

	void setTemplate_Tab(@Nullable org.compiere.model.I_AD_Tab Template_Tab);

	ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Tab> COLUMN_Template_Tab_ID = new ModelColumn<>(I_AD_Tab.class, "Template_Tab_ID", org.compiere.model.I_AD_Tab.class);
	String COLUMNNAME_Template_Tab_ID = "Template_Tab_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Tab, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Tab.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set SQL WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWhereClause (@Nullable java.lang.String WhereClause);

	/**
	 * Get SQL WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWhereClause();

	ModelColumn<I_AD_Tab, Object> COLUMN_WhereClause = new ModelColumn<>(I_AD_Tab.class, "WhereClause", null);
	String COLUMNNAME_WhereClause = "WhereClause";
}
