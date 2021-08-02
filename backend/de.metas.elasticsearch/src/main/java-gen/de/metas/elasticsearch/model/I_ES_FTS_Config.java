package de.metas.elasticsearch.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ES_FTS_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ES_FTS_Config 
{

	String Table_Name = "ES_FTS_Config";

//	/** AD_Table_ID=541755 */
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

	ModelColumn<I_ES_FTS_Config, Object> COLUMN_Created = new ModelColumn<>(I_ES_FTS_Config.class, "Created", null);
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
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_ES_FTS_Config, Object> COLUMN_Description = new ModelColumn<>(I_ES_FTS_Config.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Create Index Command.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_CreateIndexCommand (java.lang.String ES_CreateIndexCommand);

	/**
	 * Get Create Index Command.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getES_CreateIndexCommand();

	ModelColumn<I_ES_FTS_Config, Object> COLUMN_ES_CreateIndexCommand = new ModelColumn<>(I_ES_FTS_Config.class, "ES_CreateIndexCommand", null);
	String COLUMNNAME_ES_CreateIndexCommand = "ES_CreateIndexCommand";

	/**
	 * Set Document To Index Template.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_DocumentToIndexTemplate (java.lang.String ES_DocumentToIndexTemplate);

	/**
	 * Get Document To Index Template.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getES_DocumentToIndexTemplate();

	ModelColumn<I_ES_FTS_Config, Object> COLUMN_ES_DocumentToIndexTemplate = new ModelColumn<>(I_ES_FTS_Config.class, "ES_DocumentToIndexTemplate", null);
	String COLUMNNAME_ES_DocumentToIndexTemplate = "ES_DocumentToIndexTemplate";

	/**
	 * Set Full Text Search Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Config_ID (int ES_FTS_Config_ID);

	/**
	 * Get Full Text Search Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Config_ID();

	ModelColumn<I_ES_FTS_Config, Object> COLUMN_ES_FTS_Config_ID = new ModelColumn<>(I_ES_FTS_Config.class, "ES_FTS_Config_ID", null);
	String COLUMNNAME_ES_FTS_Config_ID = "ES_FTS_Config_ID";

	/**
	 * Set Elasticsearch Index.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_Index (java.lang.String ES_Index);

	/**
	 * Get Elasticsearch Index.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getES_Index();

	ModelColumn<I_ES_FTS_Config, Object> COLUMN_ES_Index = new ModelColumn<>(I_ES_FTS_Config.class, "ES_Index", null);
	String COLUMNNAME_ES_Index = "ES_Index";

	/**
	 * Set Query.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_QueryCommand (java.lang.String ES_QueryCommand);

	/**
	 * Get Query.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getES_QueryCommand();

	ModelColumn<I_ES_FTS_Config, Object> COLUMN_ES_QueryCommand = new ModelColumn<>(I_ES_FTS_Config.class, "ES_QueryCommand", null);
	String COLUMNNAME_ES_QueryCommand = "ES_QueryCommand";

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

	ModelColumn<I_ES_FTS_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_ES_FTS_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ES_FTS_Config, Object> COLUMN_Updated = new ModelColumn<>(I_ES_FTS_Config.class, "Updated", null);
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
