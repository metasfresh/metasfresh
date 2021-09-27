package de.metas.elasticsearch.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ES_FTS_Filter
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ES_FTS_Filter 
{

	String Table_Name = "ES_FTS_Filter";

//	/** AD_Table_ID=541759 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ES_FTS_Filter, Object> COLUMN_Created = new ModelColumn<>(I_ES_FTS_Filter.class, "Created", null);
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
	 * Set Full Text Search Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Config_ID (int ES_FTS_Config_ID);

	/**
	 * Get Full Text Search Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Config_ID();

	de.metas.elasticsearch.model.I_ES_FTS_Config getES_FTS_Config();

	void setES_FTS_Config(de.metas.elasticsearch.model.I_ES_FTS_Config ES_FTS_Config);

	ModelColumn<I_ES_FTS_Filter, de.metas.elasticsearch.model.I_ES_FTS_Config> COLUMN_ES_FTS_Config_ID = new ModelColumn<>(I_ES_FTS_Filter.class, "ES_FTS_Config_ID", de.metas.elasticsearch.model.I_ES_FTS_Config.class);
	String COLUMNNAME_ES_FTS_Config_ID = "ES_FTS_Config_ID";

	/**
	 * Set Full Text Search Filter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Filter_ID (int ES_FTS_Filter_ID);

	/**
	 * Get Full Text Search Filter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Filter_ID();

	ModelColumn<I_ES_FTS_Filter, Object> COLUMN_ES_FTS_Filter_ID = new ModelColumn<>(I_ES_FTS_Filter.class, "ES_FTS_Filter_ID", null);
	String COLUMNNAME_ES_FTS_Filter_ID = "ES_FTS_Filter_ID";

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

	ModelColumn<I_ES_FTS_Filter, Object> COLUMN_IsActive = new ModelColumn<>(I_ES_FTS_Filter.class, "IsActive", null);
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

	ModelColumn<I_ES_FTS_Filter, Object> COLUMN_Updated = new ModelColumn<>(I_ES_FTS_Filter.class, "Updated", null);
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
