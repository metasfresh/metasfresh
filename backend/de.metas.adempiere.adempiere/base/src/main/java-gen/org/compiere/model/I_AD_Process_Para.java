package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Process_Para
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Process_Para 
{

	String Table_Name = "AD_Process_Para";

//	/** AD_Table_ID=285 */
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
	 * Set System Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Element_ID();

	@Nullable org.compiere.model.I_AD_Element getAD_Element();

	void setAD_Element(@Nullable org.compiere.model.I_AD_Element AD_Element);

	ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new ModelColumn<>(I_AD_Process_Para.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Process.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	org.compiere.model.I_AD_Process getAD_Process();

	void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

	ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_AD_Process_Para.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Process Parameter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Process_Para_ID (int AD_Process_Para_ID);

	/**
	 * Get Process Parameter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Process_Para_ID();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_AD_Process_Para_ID = new ModelColumn<>(I_AD_Process_Para.class, "AD_Process_Para_ID", null);
	String COLUMNNAME_AD_Process_Para_ID = "AD_Process_Para_ID";

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

	ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new ModelColumn<>(I_AD_Process_Para.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
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

	ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new ModelColumn<>(I_AD_Process_Para.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

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

	ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new ModelColumn<>(I_AD_Process_Para.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Barcode Scanner Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBarcodeScannerType (@Nullable java.lang.String BarcodeScannerType);

	/**
	 * Get Barcode Scanner Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBarcodeScannerType();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_BarcodeScannerType = new ModelColumn<>(I_AD_Process_Para.class, "BarcodeScannerType", null);
	String COLUMNNAME_BarcodeScannerType = "BarcodeScannerType";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_ColumnName = new ModelColumn<>(I_AD_Process_Para.class, "ColumnName", null);
	String COLUMNNAME_ColumnName = "ColumnName";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_Created = new ModelColumn<>(I_AD_Process_Para.class, "Created", null);
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
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDefaultValue (@Nullable java.lang.String DefaultValue);

	/**
	 * Get Default Logic.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDefaultValue();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_DefaultValue = new ModelColumn<>(I_AD_Process_Para.class, "DefaultValue", null);
	String COLUMNNAME_DefaultValue = "DefaultValue";

	/**
	 * Set Default Logic 2.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDefaultValue2 (@Nullable java.lang.String DefaultValue2);

	/**
	 * Get Default Logic 2.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDefaultValue2();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_DefaultValue2 = new ModelColumn<>(I_AD_Process_Para.class, "DefaultValue2", null);
	String COLUMNNAME_DefaultValue2 = "DefaultValue2";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_Description = new ModelColumn<>(I_AD_Process_Para.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_DisplayLogic = new ModelColumn<>(I_AD_Process_Para.class, "DisplayLogic", null);
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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Process_Para.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Length.
	 * Length of the column in the database
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFieldLength (int FieldLength);

	/**
	 * Get Length.
	 * Length of the column in the database
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFieldLength();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_FieldLength = new ModelColumn<>(I_AD_Process_Para.class, "FieldLength", null);
	String COLUMNNAME_FieldLength = "FieldLength";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_Help = new ModelColumn<>(I_AD_Process_Para.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Process_Para.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAutocomplete (boolean IsAutocomplete);

	/**
	 * Get Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isAutocomplete();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_IsAutocomplete = new ModelColumn<>(I_AD_Process_Para.class, "IsAutocomplete", null);
	String COLUMNNAME_IsAutocomplete = "IsAutocomplete";

	/**
	 * Set Centrally maintained.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCentrallyMaintained (boolean IsCentrallyMaintained);

	/**
	 * Get Centrally maintained.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCentrallyMaintained();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_IsCentrallyMaintained = new ModelColumn<>(I_AD_Process_Para.class, "IsCentrallyMaintained", null);
	String COLUMNNAME_IsCentrallyMaintained = "IsCentrallyMaintained";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_IsEncrypted = new ModelColumn<>(I_AD_Process_Para.class, "IsEncrypted", null);
	String COLUMNNAME_IsEncrypted = "IsEncrypted";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_IsMandatory = new ModelColumn<>(I_AD_Process_Para.class, "IsMandatory", null);
	String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set Range.
	 * The parameter is a range of values
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRange (boolean IsRange);

	/**
	 * Get Range.
	 * The parameter is a range of values
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRange();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_IsRange = new ModelColumn<>(I_AD_Process_Para.class, "IsRange", null);
	String COLUMNNAME_IsRange = "IsRange";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_Name = new ModelColumn<>(I_AD_Process_Para.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_ReadOnlyLogic = new ModelColumn<>(I_AD_Process_Para.class, "ReadOnlyLogic", null);
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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_SeqNo = new ModelColumn<>(I_AD_Process_Para.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Show Inactive Values.
	 * Also show inactive referenced records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShowInactiveValues (boolean ShowInactiveValues);

	/**
	 * Get Show Inactive Values.
	 * Also show inactive referenced records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShowInactiveValues();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_ShowInactiveValues = new ModelColumn<>(I_AD_Process_Para.class, "ShowInactiveValues", null);
	String COLUMNNAME_ShowInactiveValues = "ShowInactiveValues";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Process_Para, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Process_Para.class, "Updated", null);
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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_ValueMax = new ModelColumn<>(I_AD_Process_Para.class, "ValueMax", null);
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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_ValueMin = new ModelColumn<>(I_AD_Process_Para.class, "ValueMin", null);
	String COLUMNNAME_ValueMin = "ValueMin";

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

	ModelColumn<I_AD_Process_Para, Object> COLUMN_VFormat = new ModelColumn<>(I_AD_Process_Para.class, "VFormat", null);
	String COLUMNNAME_VFormat = "VFormat";
}
