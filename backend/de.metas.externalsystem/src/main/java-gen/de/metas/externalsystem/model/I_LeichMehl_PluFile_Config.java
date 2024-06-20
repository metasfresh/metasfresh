package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for LeichMehl_PluFile_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_LeichMehl_PluFile_Config 
{

	String Table_Name = "LeichMehl_PluFile_Config";

//	/** AD_Table_ID=542182 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_Created = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "Created", null);
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

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_Description = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PLU File Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLeichMehl_PluFile_ConfigGroup_ID (int LeichMehl_PluFile_ConfigGroup_ID);

	/**
	 * Get PLU File Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLeichMehl_PluFile_ConfigGroup_ID();

	de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup getLeichMehl_PluFile_ConfigGroup();

	void setLeichMehl_PluFile_ConfigGroup(de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup LeichMehl_PluFile_ConfigGroup);

	ModelColumn<I_LeichMehl_PluFile_Config, de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup> COLUMN_LeichMehl_PluFile_ConfigGroup_ID = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "LeichMehl_PluFile_ConfigGroup_ID", de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup.class);
	String COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID = "LeichMehl_PluFile_ConfigGroup_ID";

	/**
	 * Set PLU file config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLeichMehl_PluFile_Config_ID (int LeichMehl_PluFile_Config_ID);

	/**
	 * Get PLU file config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLeichMehl_PluFile_Config_ID();

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_LeichMehl_PluFile_Config_ID = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "LeichMehl_PluFile_Config_ID", null);
	String COLUMNNAME_LeichMehl_PluFile_Config_ID = "LeichMehl_PluFile_Config_ID";

	/**
	 * Set Replacement.
	 * Specifies the replacement value for the target field identified at the given JsonPath from the source object.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReplacement (java.lang.String Replacement);

	/**
	 * Get Replacement.
	 * Specifies the replacement value for the target field identified at the given JsonPath from the source object.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReplacement();

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_Replacement = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "Replacement", null);
	String COLUMNNAME_Replacement = "Replacement";

	/**
	 * Set Replacement source.
	 * Specifies the source object from where the replacement value for the target field will be taken.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReplacementSource (java.lang.String ReplacementSource);

	/**
	 * Get Replacement source.
	 * Specifies the source object from where the replacement value for the target field will be taken.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReplacementSource();

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_ReplacementSource = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "ReplacementSource", null);
	String COLUMNNAME_ReplacementSource = "ReplacementSource";

	/**
	 * Set Replace Regexp.
	 * Regular expression used when replacing the value from the matched field. With this configuration a full replacement can be done or a partial one. full replacement e.g. given that there is a "value" = "Test sentence.", "ReplaceRegExp" = ":*" and the new value is "TEST", after replacement is performed "value" = "TEST". Partial replacement e.g. given that there is a "value" = "Test sentence.", "ReplaceRegExp" = ".*(Test).*" and the new value is "Dummy test", after replacement is performed "value" = "Dummy test sentence."
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReplaceRegExp (@Nullable java.lang.String ReplaceRegExp);

	/**
	 * Get Replace Regexp.
	 * Regular expression used when replacing the value from the matched field. With this configuration a full replacement can be done or a partial one. full replacement e.g. given that there is a "value" = "Test sentence.", "ReplaceRegExp" = ":*" and the new value is "TEST", after replacement is performed "value" = "TEST". Partial replacement e.g. given that there is a "value" = "Test sentence.", "ReplaceRegExp" = ".*(Test).*" and the new value is "Dummy test", after replacement is performed "value" = "Dummy test sentence."
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReplaceRegExp();

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_ReplaceRegExp = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "ReplaceRegExp", null);
	String COLUMNNAME_ReplaceRegExp = "ReplaceRegExp";

	/**
	 * Set Target field.
	 * Specifies the name of the field from the PLU file which will be updated.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTargetFieldName (java.lang.String TargetFieldName);

	/**
	 * Get Target field.
	 * Specifies the name of the field from the PLU file which will be updated.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTargetFieldName();

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_TargetFieldName = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "TargetFieldName", null);
	String COLUMNNAME_TargetFieldName = "TargetFieldName";

	/**
	 * Set Target field type.
	 * Specifies the type of the field from the PLU file which will be updated.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTargetFieldType (java.lang.String TargetFieldType);

	/**
	 * Get Target field type.
	 * Specifies the type of the field from the PLU file which will be updated.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTargetFieldType();

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_TargetFieldType = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "TargetFieldType", null);
	String COLUMNNAME_TargetFieldType = "TargetFieldType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_LeichMehl_PluFile_Config, Object> COLUMN_Updated = new ModelColumn<>(I_LeichMehl_PluFile_Config.class, "Updated", null);
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
