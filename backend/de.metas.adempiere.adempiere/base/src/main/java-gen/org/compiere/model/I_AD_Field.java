package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for AD_Field
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Field
{

	String Table_Name = "AD_Field";

	//	/** AD_Table_ID=107 */
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_AD_Field.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Field.
	 * Field on a database table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Field_ID (int AD_Field_ID);

	/**
	 * Get Field.
	 * Field on a database table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Field_ID();

	ModelColumn<I_AD_Field, Object> COLUMN_AD_Field_ID = new ModelColumn<>(I_AD_Field.class, "AD_Field_ID", null);
	String COLUMNNAME_AD_Field_ID = "AD_Field_ID";

	/**
	 * Set Field Group.
	 * Logical grouping of fields
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_FieldGroup_ID (int AD_FieldGroup_ID);

	/**
	 * Get Field Group.
	 * Logical grouping of fields
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_FieldGroup_ID();

	@Nullable org.compiere.model.I_AD_FieldGroup getAD_FieldGroup();

	void setAD_FieldGroup(@Nullable org.compiere.model.I_AD_FieldGroup AD_FieldGroup);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_FieldGroup> COLUMN_AD_FieldGroup_ID = new ModelColumn<>(I_AD_Field.class, "AD_FieldGroup_ID", org.compiere.model.I_AD_FieldGroup.class);
	String COLUMNNAME_AD_FieldGroup_ID = "AD_FieldGroup_ID";

	/**
	 * Set AD_Name_ID.
	 * This is an AD_Element_ID that can does not need to have a ColumnName and can be used to name things
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Name_ID (int AD_Name_ID);

	/**
	 * Get AD_Name_ID.
	 * This is an AD_Element_ID that can does not need to have a ColumnName and can be used to name things
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Name_ID();

	@Nullable org.compiere.model.I_AD_Element getAD_Name();

	void setAD_Name(@Nullable org.compiere.model.I_AD_Element AD_Name);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Element> COLUMN_AD_Name_ID = new ModelColumn<>(I_AD_Field.class, "AD_Name_ID", org.compiere.model.I_AD_Element.class);
	String COLUMNNAME_AD_Name_ID = "AD_Name_ID";

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
	 * Set Reference Overwrite.
	 * System Reference - optional Overwrite
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Reference Overwrite.
	 * System Reference - optional Overwrite
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Reference_ID();

	@Nullable org.compiere.model.I_AD_Reference getAD_Reference();

	void setAD_Reference(@Nullable org.compiere.model.I_AD_Reference AD_Reference);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new ModelColumn<>(I_AD_Field.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set Reference Key.
	 * Required to specify, if data type is Table or List
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Reference_Value_ID (int AD_Reference_Value_ID);

	/**
	 * Get Reference Key.
	 * Required to specify, if data type is Table or List
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Reference_Value_ID();

	@Nullable org.compiere.model.I_AD_Reference getAD_Reference_Value();

	void setAD_Reference_Value(@Nullable org.compiere.model.I_AD_Reference AD_Reference_Value);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new ModelColumn<>(I_AD_Field.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

	/**
	 * Set Sequence.
	 * Document Sequence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Sequence_ID (int AD_Sequence_ID);

	/**
	 * Get Sequence.
	 * Document Sequence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Sequence_ID();

	@Nullable org.compiere.model.I_AD_Sequence getAD_Sequence();

	void setAD_Sequence(@Nullable org.compiere.model.I_AD_Sequence AD_Sequence);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Sequence> COLUMN_AD_Sequence_ID = new ModelColumn<>(I_AD_Field.class, "AD_Sequence_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_AD_Sequence_ID = "AD_Sequence_ID";

	/**
	 * Set Tab.
	 * Tab within a Window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Tab.
	 * Tab within a Window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Tab_ID();

	org.compiere.model.I_AD_Tab getAD_Tab();

	void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Tab> COLUMN_AD_Tab_ID = new ModelColumn<>(I_AD_Field.class, "AD_Tab_ID", org.compiere.model.I_AD_Tab.class);
	String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

	/**
	 * Set Validation Rule.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Val_Rule_ID (int AD_Val_Rule_ID);

	/**
	 * Get Validation Rule.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Val_Rule_ID();

	@Nullable org.compiere.model.I_AD_Val_Rule getAD_Val_Rule();

	void setAD_Val_Rule(@Nullable org.compiere.model.I_AD_Val_Rule AD_Val_Rule);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new ModelColumn<>(I_AD_Field.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Color Logic.
	 * Color used for background
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setColorLogic (@Nullable java.lang.String ColorLogic);

	/**
	 * Get Color Logic.
	 * Color used for background
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getColorLogic();

	ModelColumn<I_AD_Field, Object> COLUMN_ColorLogic = new ModelColumn<>(I_AD_Field.class, "ColorLogic", null);
	String COLUMNNAME_ColorLogic = "ColorLogic";

	/**
	 * Set Column Display Length.
	 * Column display length for grid mode
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setColumnDisplayLength (int ColumnDisplayLength);

	/**
	 * Get Column Display Length.
	 * Column display length for grid mode
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getColumnDisplayLength();

	ModelColumn<I_AD_Field, Object> COLUMN_ColumnDisplayLength = new ModelColumn<>(I_AD_Field.class, "ColumnDisplayLength", null);
	String COLUMNNAME_ColumnDisplayLength = "ColumnDisplayLength";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Field, Object> COLUMN_Created = new ModelColumn<>(I_AD_Field.class, "Created", null);
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
	 * Set Default Logic.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDefaultValue (@Nullable java.lang.String DefaultValue);

	/**
	 * Get Default Logic.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDefaultValue();

	ModelColumn<I_AD_Field, Object> COLUMN_DefaultValue = new ModelColumn<>(I_AD_Field.class, "DefaultValue", null);
	String COLUMNNAME_DefaultValue = "DefaultValue";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Field, Object> COLUMN_Description = new ModelColumn<>(I_AD_Field.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Display Length.
	 * Length of the display in characters
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDisplayLength (int DisplayLength);

	/**
	 * Get Display Length.
	 * Length of the display in characters
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDisplayLength();

	ModelColumn<I_AD_Field, Object> COLUMN_DisplayLength = new ModelColumn<>(I_AD_Field.class, "DisplayLength", null);
	String COLUMNNAME_DisplayLength = "DisplayLength";

	/**
	 * Set Display Logic.
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDisplayLogic (@Nullable java.lang.String DisplayLogic);

	/**
	 * Get Display Logic.
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDisplayLogic();

	ModelColumn<I_AD_Field, Object> COLUMN_DisplayLogic = new ModelColumn<>(I_AD_Field.class, "DisplayLogic", null);
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

	ModelColumn<I_AD_Field, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Field.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Filter Validation Rule.
	 * Validation Rule used for filtering
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFilter_Val_Rule_ID (int Filter_Val_Rule_ID);

	/**
	 * Get Filter Validation Rule.
	 * Validation Rule used for filtering
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFilter_Val_Rule_ID();

	@Nullable org.compiere.model.I_AD_Val_Rule getFilter_Val_Rule();

	void setFilter_Val_Rule(@Nullable org.compiere.model.I_AD_Val_Rule Filter_Val_Rule);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Val_Rule> COLUMN_Filter_Val_Rule_ID = new ModelColumn<>(I_AD_Field.class, "Filter_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_Filter_Val_Rule_ID = "Filter_Val_Rule_ID";

	/**
	 * Set IsForbidNewRecordCreation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsForbidNewRecordCreation (boolean IsForbidNewRecordCreation);

	/**
	 * Get IsForbidNewRecordCreation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isForbidNewRecordCreation();

	ModelColumn<I_AD_Field, Object> COLUMN_IsForbidNewRecordCreation = new ModelColumn<>(I_AD_Field.class, "IsForbidNewRecordCreation", null);
	String COLUMNNAME_IsForbidNewRecordCreation = "IsForbidNewRecordCreation";

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

	ModelColumn<I_AD_Field, Object> COLUMN_Help = new ModelColumn<>(I_AD_Field.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Tab> COLUMN_Included_Tab_ID = new ModelColumn<>(I_AD_Field.class, "Included_Tab_ID", org.compiere.model.I_AD_Tab.class);
	String COLUMNNAME_Included_Tab_ID = "Included_Tab_ID";

	/**
	 * Set Included Tab Height.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncludedTabHeight (int IncludedTabHeight);

	/**
	 * Get Included Tab Height.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getIncludedTabHeight();

	ModelColumn<I_AD_Field, Object> COLUMN_IncludedTabHeight = new ModelColumn<>(I_AD_Field.class, "IncludedTabHeight", null);
	String COLUMNNAME_IncludedTabHeight = "IncludedTabHeight";

	/**
	 * Set Info Factory Class.
	 * Fully qualified class name that implements the InfoFactory interface
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInfoFactoryClass (@Nullable java.lang.String InfoFactoryClass);

	/**
	 * Get Info Factory Class.
	 * Fully qualified class name that implements the InfoFactory interface
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInfoFactoryClass();

	ModelColumn<I_AD_Field, Object> COLUMN_InfoFactoryClass = new ModelColumn<>(I_AD_Field.class, "InfoFactoryClass", null);
	String COLUMNNAME_InfoFactoryClass = "InfoFactoryClass";

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

	ModelColumn<I_AD_Field, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Field.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisplayed();

	ModelColumn<I_AD_Field, Object> COLUMN_IsDisplayed = new ModelColumn<>(I_AD_Field.class, "IsDisplayed", null);
	String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisplayedGrid (boolean IsDisplayedGrid);

	/**
	 * Get Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisplayedGrid();

	ModelColumn<I_AD_Field, Object> COLUMN_IsDisplayedGrid = new ModelColumn<>(I_AD_Field.class, "IsDisplayedGrid", null);
	String COLUMNNAME_IsDisplayedGrid = "IsDisplayedGrid";

	/**
	 * Set Column Encryption.
	 * Test and enable Column Encryption
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEncrypted (boolean IsEncrypted);

	/**
	 * Get Column Encryption.
	 * Test and enable Column Encryption
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEncrypted();

	ModelColumn<I_AD_Field, Object> COLUMN_IsEncrypted = new ModelColumn<>(I_AD_Field.class, "IsEncrypted", null);
	String COLUMNNAME_IsEncrypted = "IsEncrypted";

	/**
	 * Set Exclude from Zoom Targets.
	 * Exclude from zoom targets / related documents
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsExcludeFromZoomTargets (@Nullable java.lang.String IsExcludeFromZoomTargets);

	/**
	 * Get Exclude from Zoom Targets.
	 * Exclude from zoom targets / related documents
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsExcludeFromZoomTargets();

	ModelColumn<I_AD_Field, Object> COLUMN_IsExcludeFromZoomTargets = new ModelColumn<>(I_AD_Field.class, "IsExcludeFromZoomTargets", null);
	String COLUMNNAME_IsExcludeFromZoomTargets = "IsExcludeFromZoomTargets";

	/**
	 * Set Field Only.
	 * Label is not displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFieldOnly (boolean IsFieldOnly);

	/**
	 * Get Field Only.
	 * Label is not displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFieldOnly();

	ModelColumn<I_AD_Field, Object> COLUMN_IsFieldOnly = new ModelColumn<>(I_AD_Field.class, "IsFieldOnly", null);
	String COLUMNNAME_IsFieldOnly = "IsFieldOnly";

	/**
	 * Set Filter.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsFilterField (@Nullable java.lang.String IsFilterField);

	/**
	 * Get Filter.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsFilterField();

	ModelColumn<I_AD_Field, Object> COLUMN_IsFilterField = new ModelColumn<>(I_AD_Field.class, "IsFilterField", null);
	String COLUMNNAME_IsFilterField = "IsFilterField";

	/**
	 * Set Heading only.
	 * Field without Column - Only label is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsHeading (boolean IsHeading);

	/**
	 * Get Heading only.
	 * Field without Column - Only label is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHeading();

	ModelColumn<I_AD_Field, Object> COLUMN_IsHeading = new ModelColumn<>(I_AD_Field.class, "IsHeading", null);
	String COLUMNNAME_IsHeading = "IsHeading";

	/**
	 * Set mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsMandatory (@Nullable java.lang.String IsMandatory);

	/**
	 * Get mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsMandatory();

	ModelColumn<I_AD_Field, Object> COLUMN_IsMandatory = new ModelColumn<>(I_AD_Field.class, "IsMandatory", null);
	String COLUMNNAME_IsMandatory = "IsMandatory";

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

	ModelColumn<I_AD_Field, Object> COLUMN_IsReadOnly = new ModelColumn<>(I_AD_Field.class, "IsReadOnly", null);
	String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/**
	 * Set Same Line.
	 * Displayed on same line as previous field
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSameLine (boolean IsSameLine);

	/**
	 * Get Same Line.
	 * Displayed on same line as previous field
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSameLine();

	ModelColumn<I_AD_Field, Object> COLUMN_IsSameLine = new ModelColumn<>(I_AD_Field.class, "IsSameLine", null);
	String COLUMNNAME_IsSameLine = "IsSameLine";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_AD_Field, Object> COLUMN_Name = new ModelColumn<>(I_AD_Field.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Obscure.
	 * Type of obscuring the data (limiting the display)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setObscureType (@Nullable java.lang.String ObscureType);

	/**
	 * Get Obscure.
	 * Type of obscuring the data (limiting the display)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getObscureType();

	ModelColumn<I_AD_Field, Object> COLUMN_ObscureType = new ModelColumn<>(I_AD_Field.class, "ObscureType", null);
	String COLUMNNAME_ObscureType = "ObscureType";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
	 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
	 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_AD_Field, Object> COLUMN_SeqNo = new ModelColumn<>(I_AD_Field.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
	 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNoGrid (int SeqNoGrid);

	/**
	 * Get Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
	 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNoGrid();

	ModelColumn<I_AD_Field, Object> COLUMN_SeqNoGrid = new ModelColumn<>(I_AD_Field.class, "SeqNoGrid", null);
	String COLUMNNAME_SeqNoGrid = "SeqNoGrid";

	/**
	 * Set Record Sort No.
	 * Determines in what order the records are displayed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSortNo (@Nullable BigDecimal SortNo);

	/**
	 * Get Record Sort No.
	 * Determines in what order the records are displayed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSortNo();

	ModelColumn<I_AD_Field, Object> COLUMN_SortNo = new ModelColumn<>(I_AD_Field.class, "SortNo", null);
	String COLUMNNAME_SortNo = "SortNo";

	/**
	 * Set Column span.
	 * Number of columns spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSpanX (int SpanX);

	/**
	 * Get Column span.
	 * Number of columns spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSpanX();

	ModelColumn<I_AD_Field, Object> COLUMN_SpanX = new ModelColumn<>(I_AD_Field.class, "SpanX", null);
	String COLUMNNAME_SpanX = "SpanX";

	/**
	 * Set Row Span.
	 * Number of rows spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSpanY (int SpanY);

	/**
	 * Get Row Span.
	 * Number of rows spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSpanY();

	ModelColumn<I_AD_Field, Object> COLUMN_SpanY = new ModelColumn<>(I_AD_Field.class, "SpanY", null);
	String COLUMNNAME_SpanY = "SpanY";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Field, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Field.class, "Updated", null);
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
}
