package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for AD_Column
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Column 
{

	String Table_Name = "AD_Column";

//	/** AD_Table_ID=101 */
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
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	ModelColumn<I_AD_Column, Object> COLUMN_AD_Column_ID = new ModelColumn<>(I_AD_Column.class, "AD_Column_ID", null);
	String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

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

	ModelColumn<I_AD_Column, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new ModelColumn<>(I_AD_Column.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
	String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

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

	ModelColumn<I_AD_Column, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_AD_Column.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Reference Overwrite.
	 * System Reference - optional Overwrite
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Reference Overwrite.
	 * System Reference - optional Overwrite
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Reference_ID();

	org.compiere.model.I_AD_Reference getAD_Reference();

	void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

	ModelColumn<I_AD_Column, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new ModelColumn<>(I_AD_Column.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
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

	ModelColumn<I_AD_Column, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new ModelColumn<>(I_AD_Column.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

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

	ModelColumn<I_AD_Column, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new ModelColumn<>(I_AD_Column.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Cloning Strategy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCloningStrategy (java.lang.String CloningStrategy);

	/**
	 * Get Cloning Strategy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCloningStrategy();

	ModelColumn<I_AD_Column, Object> COLUMN_CloningStrategy = new ModelColumn<>(I_AD_Column.class, "CloningStrategy", null);
	String COLUMNNAME_CloningStrategy = "CloningStrategy";

	/**
	 * Set DB Column Name.
	 * Name of the column in the database
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setColumnName (java.lang.String ColumnName);

	/**
	 * Get DB Column Name.
	 * Name of the column in the database
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getColumnName();

	ModelColumn<I_AD_Column, Object> COLUMN_ColumnName = new ModelColumn<>(I_AD_Column.class, "ColumnName", null);
	String COLUMNNAME_ColumnName = "ColumnName";

	/**
	 * Set Column SQL.
	 * Virtual Column (r/o)
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setColumnSQL (@Nullable java.lang.String ColumnSQL);

	/**
	 * Get Column SQL.
	 * Virtual Column (r/o)
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getColumnSQL();

	ModelColumn<I_AD_Column, Object> COLUMN_ColumnSQL = new ModelColumn<>(I_AD_Column.class, "ColumnSQL", null);
	String COLUMNNAME_ColumnSQL = "ColumnSQL";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Column, Object> COLUMN_Created = new ModelColumn<>(I_AD_Column.class, "Created", null);
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
	 * Set No Foreign Key.
	 * Don't create database foreign key (if applicable) for this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDDL_NoForeignKey (boolean DDL_NoForeignKey);

	/**
	 * Get No Foreign Key.
	 * Don't create database foreign key (if applicable) for this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDDL_NoForeignKey();

	ModelColumn<I_AD_Column, Object> COLUMN_DDL_NoForeignKey = new ModelColumn<>(I_AD_Column.class, "DDL_NoForeignKey", null);
	String COLUMNNAME_DDL_NoForeignKey = "DDL_NoForeignKey";

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

	ModelColumn<I_AD_Column, Object> COLUMN_DefaultValue = new ModelColumn<>(I_AD_Column.class, "DefaultValue", null);
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

	ModelColumn<I_AD_Column, Object> COLUMN_Description = new ModelColumn<>(I_AD_Column.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_AD_Column, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Column.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Facet Filter Sequence No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFacetFilterSeqNo (int FacetFilterSeqNo);

	/**
	 * Get Facet Filter Sequence No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFacetFilterSeqNo();

	ModelColumn<I_AD_Column, Object> COLUMN_FacetFilterSeqNo = new ModelColumn<>(I_AD_Column.class, "FacetFilterSeqNo", null);
	String COLUMNNAME_FacetFilterSeqNo = "FacetFilterSeqNo";

	/**
	 * Set Length.
	 * Length of the column in the database
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFieldLength (int FieldLength);

	/**
	 * Get Length.
	 * Length of the column in the database
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFieldLength();

	ModelColumn<I_AD_Column, Object> COLUMN_FieldLength = new ModelColumn<>(I_AD_Column.class, "FieldLength", null);
	String COLUMNNAME_FieldLength = "FieldLength";

	/**
	 * Set Filter Default Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFilterDefaultValue (@Nullable java.lang.String FilterDefaultValue);

	/**
	 * Get Filter Default Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFilterDefaultValue();

	ModelColumn<I_AD_Column, Object> COLUMN_FilterDefaultValue = new ModelColumn<>(I_AD_Column.class, "FilterDefaultValue", null);
	String COLUMNNAME_FilterDefaultValue = "FilterDefaultValue";

	/**
	 * Set Filter Operator.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFilterOperator (@Nullable java.lang.String FilterOperator);

	/**
	 * Get Filter Operator.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFilterOperator();

	ModelColumn<I_AD_Column, Object> COLUMN_FilterOperator = new ModelColumn<>(I_AD_Column.class, "FilterOperator", null);
	String COLUMNNAME_FilterOperator = "FilterOperator";

	/**
	 * Set Format Pattern.
	 * The pattern used to format a number or date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFormatPattern (@Nullable java.lang.String FormatPattern);

	/**
	 * Get Format Pattern.
	 * The pattern used to format a number or date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFormatPattern();

	ModelColumn<I_AD_Column, Object> COLUMN_FormatPattern = new ModelColumn<>(I_AD_Column.class, "FormatPattern", null);
	String COLUMNNAME_FormatPattern = "FormatPattern";

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

	ModelColumn<I_AD_Column, Object> COLUMN_Help = new ModelColumn<>(I_AD_Column.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_AD_Column, Object> COLUMN_InfoFactoryClass = new ModelColumn<>(I_AD_Column.class, "InfoFactoryClass", null);
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

	ModelColumn<I_AD_Column, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Column.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allow Logging.
	 * Determine if a column must be recorded into the change log
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAllowLogging (boolean IsAllowLogging);

	/**
	 * Get Allow Logging.
	 * Determine if a column must be recorded into the change log
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isAllowLogging();

	ModelColumn<I_AD_Column, Object> COLUMN_IsAllowLogging = new ModelColumn<>(I_AD_Column.class, "IsAllowLogging", null);
	String COLUMNNAME_IsAllowLogging = "IsAllowLogging";

	/**
	 * Set Always Updateable.
	 * The column is always updateable, even if the record is not active or processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAlwaysUpdateable (boolean IsAlwaysUpdateable);

	/**
	 * Get Always Updateable.
	 * The column is always updateable, even if the record is not active or processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAlwaysUpdateable();

	ModelColumn<I_AD_Column, Object> COLUMN_IsAlwaysUpdateable = new ModelColumn<>(I_AD_Column.class, "IsAlwaysUpdateable", null);
	String COLUMNNAME_IsAlwaysUpdateable = "IsAlwaysUpdateable";

	/**
	 * Set Auto-apply validation rule.
	 * If a new record is created where this column or field is empty, then insert the first possible record reference. If set, then AD_Val_Rule_ID is used for filtering and AD_Reference_Value_ID is used for ordering.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoApplyValidationRule (boolean IsAutoApplyValidationRule);

	/**
	 * Get Auto-apply validation rule.
	 * If a new record is created where this column or field is empty, then insert the first possible record reference. If set, then AD_Val_Rule_ID is used for filtering and AD_Reference_Value_ID is used for ordering.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoApplyValidationRule();

	ModelColumn<I_AD_Column, Object> COLUMN_IsAutoApplyValidationRule = new ModelColumn<>(I_AD_Column.class, "IsAutoApplyValidationRule", null);
	String COLUMNNAME_IsAutoApplyValidationRule = "IsAutoApplyValidationRule";

	/**
	 * Set Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutocomplete (boolean IsAutocomplete);

	/**
	 * Get Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutocomplete();

	ModelColumn<I_AD_Column, Object> COLUMN_IsAutocomplete = new ModelColumn<>(I_AD_Column.class, "IsAutocomplete", null);
	String COLUMNNAME_IsAutocomplete = "IsAutocomplete";

	/**
	 * Set Calculated.
	 * The value is calculated by the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCalculated (boolean IsCalculated);

	/**
	 * Get Calculated.
	 * The value is calculated by the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCalculated();

	ModelColumn<I_AD_Column, Object> COLUMN_IsCalculated = new ModelColumn<>(I_AD_Column.class, "IsCalculated", null);
	String COLUMNNAME_IsCalculated = "IsCalculated";

	/**
	 * Set Column Encryption.
	 * Test and enable Column Encryption
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEncrypted (java.lang.String IsEncrypted);

	/**
	 * Get Column Encryption.
	 * Test and enable Column Encryption
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getIsEncrypted();

	ModelColumn<I_AD_Column, Object> COLUMN_IsEncrypted = new ModelColumn<>(I_AD_Column.class, "IsEncrypted", null);
	String COLUMNNAME_IsEncrypted = "IsEncrypted";

	/**
	 * Set Exclude from Zoom Targets.
	 * Exclude from zoom targets / related documents
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExcludeFromZoomTargets (boolean IsExcludeFromZoomTargets);

	/**
	 * Get Exclude from Zoom Targets.
	 * Exclude from zoom targets / related documents
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludeFromZoomTargets();

	ModelColumn<I_AD_Column, Object> COLUMN_IsExcludeFromZoomTargets = new ModelColumn<>(I_AD_Column.class, "IsExcludeFromZoomTargets", null);
	String COLUMNNAME_IsExcludeFromZoomTargets = "IsExcludeFromZoomTargets";

	/**
	 * Set Facet Filter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFacetFilter (boolean IsFacetFilter);

	/**
	 * Get Facet Filter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFacetFilter();

	ModelColumn<I_AD_Column, Object> COLUMN_IsFacetFilter = new ModelColumn<>(I_AD_Column.class, "IsFacetFilter", null);
	String COLUMNNAME_IsFacetFilter = "IsFacetFilter";

	/**
	 * Set Force include in generated model.
	 * Force including this column in java generated interface and class
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsForceIncludeInGeneratedModel (boolean IsForceIncludeInGeneratedModel);

	/**
	 * Get Force include in generated model.
	 * Force including this column in java generated interface and class
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isForceIncludeInGeneratedModel();

	ModelColumn<I_AD_Column, Object> COLUMN_IsForceIncludeInGeneratedModel = new ModelColumn<>(I_AD_Column.class, "IsForceIncludeInGeneratedModel", null);
	String COLUMNNAME_IsForceIncludeInGeneratedModel = "IsForceIncludeInGeneratedModel";

	/**
	 * Set GenericZoom Quellspalte.
	 * Werden beim GenericZoom Referenzen auf diese Spalte beachtet?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsGenericZoomOrigin (boolean IsGenericZoomOrigin);

	/**
	 * Get GenericZoom Quellspalte.
	 * Werden beim GenericZoom Referenzen auf diese Spalte beachtet?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isGenericZoomOrigin();

	ModelColumn<I_AD_Column, Object> COLUMN_IsGenericZoomOrigin = new ModelColumn<>(I_AD_Column.class, "IsGenericZoomOrigin", null);
	String COLUMNNAME_IsGenericZoomOrigin = "IsGenericZoomOrigin";

	/**
	 * Set Identifier.
	 * This column is part of the record identifier
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsIdentifier (boolean IsIdentifier);

	/**
	 * Get Identifier.
	 * This column is part of the record identifier
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isIdentifier();

	ModelColumn<I_AD_Column, Object> COLUMN_IsIdentifier = new ModelColumn<>(I_AD_Column.class, "IsIdentifier", null);
	String COLUMNNAME_IsIdentifier = "IsIdentifier";

	/**
	 * Set Key column.
	 * This column is the key in this table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsKey (boolean IsKey);

	/**
	 * Get Key column.
	 * This column is the key in this table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isKey();

	ModelColumn<I_AD_Column, Object> COLUMN_IsKey = new ModelColumn<>(I_AD_Column.class, "IsKey", null);
	String COLUMNNAME_IsKey = "IsKey";

	/**
	 * Set Lazy loading.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsLazyLoading (boolean IsLazyLoading);

	/**
	 * Get Lazy loading.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isLazyLoading();

	ModelColumn<I_AD_Column, Object> COLUMN_IsLazyLoading = new ModelColumn<>(I_AD_Column.class, "IsLazyLoading", null);
	String COLUMNNAME_IsLazyLoading = "IsLazyLoading";

	/**
	 * Set mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMandatory (boolean IsMandatory);

	/**
	 * Get mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMandatory();

	ModelColumn<I_AD_Column, Object> COLUMN_IsMandatory = new ModelColumn<>(I_AD_Column.class, "IsMandatory", null);
	String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set Parent link column.
	 * This column is a link to the parent table (e.g. header from lines) - incl. Association key columns
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsParent (boolean IsParent);

	/**
	 * Get Parent link column.
	 * This column is a link to the parent table (e.g. header from lines) - incl. Association key columns
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isParent();

	ModelColumn<I_AD_Column, Object> COLUMN_IsParent = new ModelColumn<>(I_AD_Column.class, "IsParent", null);
	String COLUMNNAME_IsParent = "IsParent";

	/**
	 * Set REST-API Custom Column.
	 * If true, it allows users to update the column's value via "extendedProps" field in the corresponding model's REST API payload. (only for REST API endpoints which support the feature)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRestAPICustomColumn (boolean IsRestAPICustomColumn);

	/**
	 * Get REST-API Custom Column.
	 * If true, it allows users to update the column's value via "extendedProps" field in the corresponding model's REST API payload. (only for REST API endpoints which support the feature)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRestAPICustomColumn();

	ModelColumn<I_AD_Column, Object> COLUMN_IsRestAPICustomColumn = new ModelColumn<>(I_AD_Column.class, "IsRestAPICustomColumn", null);
	String COLUMNNAME_IsRestAPICustomColumn = "IsRestAPICustomColumn";

	/**
	 * Set Selection Column.
	 * Is this column used for finding rows in windows
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelectionColumn (boolean IsSelectionColumn);

	/**
	 * Get Selection Column.
	 * Is this column used for finding rows in windows
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSelectionColumn();

	ModelColumn<I_AD_Column, Object> COLUMN_IsSelectionColumn = new ModelColumn<>(I_AD_Column.class, "IsSelectionColumn", null);
	String COLUMNNAME_IsSelectionColumn = "IsSelectionColumn";

	/**
	 * Set Filter +/- buttons.
	 * Show filter increment/decrement buttons
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShowFilterIncrementButtons (boolean IsShowFilterIncrementButtons);

	/**
	 * Get Filter +/- buttons.
	 * Show filter increment/decrement buttons
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShowFilterIncrementButtons();

	ModelColumn<I_AD_Column, Object> COLUMN_IsShowFilterIncrementButtons = new ModelColumn<>(I_AD_Column.class, "IsShowFilterIncrementButtons", null);
	String COLUMNNAME_IsShowFilterIncrementButtons = "IsShowFilterIncrementButtons";

	/**
	 * Set Show filter inline.
	 * Renders the filter as a component (e.g. text box) directly in the filters lane
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShowFilterInline (boolean IsShowFilterInline);

	/**
	 * Get Show filter inline.
	 * Renders the filter as a component (e.g. text box) directly in the filters lane
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShowFilterInline();

	ModelColumn<I_AD_Column, Object> COLUMN_IsShowFilterInline = new ModelColumn<>(I_AD_Column.class, "IsShowFilterInline", null);
	String COLUMNNAME_IsShowFilterInline = "IsShowFilterInline";

	/**
	 * Set Staleable.
	 * If checked then this column is staleable and record needs to be loaded after save.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsStaleable (boolean IsStaleable);

	/**
	 * Get Staleable.
	 * If checked then this column is staleable and record needs to be loaded after save.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isStaleable();

	ModelColumn<I_AD_Column, Object> COLUMN_IsStaleable = new ModelColumn<>(I_AD_Column.class, "IsStaleable", null);
	String COLUMNNAME_IsStaleable = "IsStaleable";

	/**
	 * Set Synchronize Column.
	 * Change database table definition from application dictionary
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSyncDatabase (@Nullable java.lang.String IsSyncDatabase);

	/**
	 * Get Synchronize Column.
	 * Change database table definition from application dictionary
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsSyncDatabase();

	ModelColumn<I_AD_Column, Object> COLUMN_IsSyncDatabase = new ModelColumn<>(I_AD_Column.class, "IsSyncDatabase", null);
	String COLUMNNAME_IsSyncDatabase = "IsSyncDatabase";

	/**
	 * Set Translated.
	 * This column is translated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTranslated (boolean IsTranslated);

	/**
	 * Get Translated.
	 * This column is translated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTranslated();

	ModelColumn<I_AD_Column, Object> COLUMN_IsTranslated = new ModelColumn<>(I_AD_Column.class, "IsTranslated", null);
	String COLUMNNAME_IsTranslated = "IsTranslated";

	/**
	 * Set Updatable.
	 * Determines, if the field can be updated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUpdateable (boolean IsUpdateable);

	/**
	 * Get Updatable.
	 * Determines, if the field can be updated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUpdateable();

	ModelColumn<I_AD_Column, Object> COLUMN_IsUpdateable = new ModelColumn<>(I_AD_Column.class, "IsUpdateable", null);
	String COLUMNNAME_IsUpdateable = "IsUpdateable";

	/**
	 * Set Autogenerate document sequence.
	 * In case the field is empty, use standard document sequence to set the value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseDocSequence (boolean IsUseDocSequence);

	/**
	 * Get Autogenerate document sequence.
	 * In case the field is empty, use standard document sequence to set the value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseDocSequence();

	ModelColumn<I_AD_Column, Object> COLUMN_IsUseDocSequence = new ModelColumn<>(I_AD_Column.class, "IsUseDocSequence", null);
	String COLUMNNAME_IsUseDocSequence = "IsUseDocSequence";

	/**
	 * Set Mandatory Logic.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMandatoryLogic (@Nullable java.lang.String MandatoryLogic);

	/**
	 * Get Mandatory Logic.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMandatoryLogic();

	ModelColumn<I_AD_Column, Object> COLUMN_MandatoryLogic = new ModelColumn<>(I_AD_Column.class, "MandatoryLogic", null);
	String COLUMNNAME_MandatoryLogic = "MandatoryLogic";

	/**
	 * Set Maximum Facets.
	 * Maximum number of facets to fetch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMaxFacetsToFetch (int MaxFacetsToFetch);

	/**
	 * Get Maximum Facets.
	 * Maximum number of facets to fetch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMaxFacetsToFetch();

	ModelColumn<I_AD_Column, Object> COLUMN_MaxFacetsToFetch = new ModelColumn<>(I_AD_Column.class, "MaxFacetsToFetch", null);
	String COLUMNNAME_MaxFacetsToFetch = "MaxFacetsToFetch";

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

	ModelColumn<I_AD_Column, Object> COLUMN_Name = new ModelColumn<>(I_AD_Column.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Data protection category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPersonalDataCategory (@Nullable java.lang.String PersonalDataCategory);

	/**
	 * Get Data protection category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPersonalDataCategory();

	ModelColumn<I_AD_Column, Object> COLUMN_PersonalDataCategory = new ModelColumn<>(I_AD_Column.class, "PersonalDataCategory", null);
	String COLUMNNAME_PersonalDataCategory = "PersonalDataCategory";

	/**
	 * Set Read Only Logic.
	 * Logic to determine if field is read only (applies only when field is read-write)
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReadOnlyLogic (@Nullable java.lang.String ReadOnlyLogic);

	/**
	 * Get Read Only Logic.
	 * Logic to determine if field is read only (applies only when field is read-write)
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReadOnlyLogic();

	ModelColumn<I_AD_Column, Object> COLUMN_ReadOnlyLogic = new ModelColumn<>(I_AD_Column.class, "ReadOnlyLogic", null);
	String COLUMNNAME_ReadOnlyLogic = "ReadOnlyLogic";

	/**
	 * Set Selection column ordering.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSelectionColumnSeqNo (int SelectionColumnSeqNo);

	/**
	 * Get Selection column ordering.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSelectionColumnSeqNo();

	ModelColumn<I_AD_Column, Object> COLUMN_SelectionColumnSeqNo = new ModelColumn<>(I_AD_Column.class, "SelectionColumnSeqNo", null);
	String COLUMNNAME_SelectionColumnSeqNo = "SelectionColumnSeqNo";

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

	ModelColumn<I_AD_Column, Object> COLUMN_SeqNo = new ModelColumn<>(I_AD_Column.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set TechnicalNote.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTechnicalNote (@Nullable java.lang.String TechnicalNote);

	/**
	 * Get TechnicalNote.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTechnicalNote();

	ModelColumn<I_AD_Column, Object> COLUMN_TechnicalNote = new ModelColumn<>(I_AD_Column.class, "TechnicalNote", null);
	String COLUMNNAME_TechnicalNote = "TechnicalNote";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Column, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Column.class, "Updated", null);
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
	 * Set Max. Value.
	 * Maximum Value for a field
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueMax (@Nullable java.lang.String ValueMax);

	/**
	 * Get Max. Value.
	 * Maximum Value for a field
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValueMax();

	ModelColumn<I_AD_Column, Object> COLUMN_ValueMax = new ModelColumn<>(I_AD_Column.class, "ValueMax", null);
	String COLUMNNAME_ValueMax = "ValueMax";

	/**
	 * Set Min. Value.
	 * Minimum Value for a field
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueMin (@Nullable java.lang.String ValueMin);

	/**
	 * Get Min. Value.
	 * Minimum Value for a field
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValueMin();

	ModelColumn<I_AD_Column, Object> COLUMN_ValueMin = new ModelColumn<>(I_AD_Column.class, "ValueMin", null);
	String COLUMNNAME_ValueMin = "ValueMin";

	/**
	 * Set Version.
	 * Version of the table definition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVersion (BigDecimal Version);

	/**
	 * Get Version.
	 * Version of the table definition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getVersion();

	ModelColumn<I_AD_Column, Object> COLUMN_Version = new ModelColumn<>(I_AD_Column.class, "Version", null);
	String COLUMNNAME_Version = "Version";

	/**
	 * Set Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVFormat (@Nullable java.lang.String VFormat);

	/**
	 * Get Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVFormat();

	ModelColumn<I_AD_Column, Object> COLUMN_VFormat = new ModelColumn<>(I_AD_Column.class, "VFormat", null);
	String COLUMNNAME_VFormat = "VFormat";
}
