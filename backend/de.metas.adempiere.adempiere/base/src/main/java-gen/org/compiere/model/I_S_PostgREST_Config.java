package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for S_PostgREST_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_PostgREST_Config 
{

	String Table_Name = "S_PostgREST_Config";

//	/** AD_Table_ID=541503 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBase_url (String Base_url);

	/**
	 * Get URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getBase_url();

	ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Base_url = new ModelColumn<>(I_S_PostgREST_Config.class, "Base_url", null);
	String COLUMNNAME_Base_url = "Base_url";

	/**
	 * Set Connection timeout.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setConnection_timeout (int Connection_timeout);

	/**
	 * Get Connection timeout.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getConnection_timeout();

	ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Connection_timeout = new ModelColumn<>(I_S_PostgREST_Config.class, "Connection_timeout", null);
	String COLUMNNAME_Connection_timeout = "Connection_timeout";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Created = new ModelColumn<>(I_S_PostgREST_Config.class, "Created", null);
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

	ModelColumn<I_S_PostgREST_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_S_PostgREST_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Output folder.
	 * If a PostgREST result is saved locally, this field specifies the folder from the perspective of the metasfresh server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostgREST_ResultDirectory (String PostgREST_ResultDirectory);

	/**
	 * Get Output folder.
	 * If a PostgREST result is saved locally, this field specifies the folder from the perspective of the metasfresh server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getPostgREST_ResultDirectory();

	ModelColumn<I_S_PostgREST_Config, Object> COLUMN_PostgREST_ResultDirectory = new ModelColumn<>(I_S_PostgREST_Config.class, "PostgREST_ResultDirectory", null);
	String COLUMNNAME_PostgREST_ResultDirectory = "PostgREST_ResultDirectory";

	/**
	 * Set Read timeout.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRead_timeout (int Read_timeout);

	/**
	 * Get Read timeout.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRead_timeout();

	ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Read_timeout = new ModelColumn<>(I_S_PostgREST_Config.class, "Read_timeout", null);
	String COLUMNNAME_Read_timeout = "Read_timeout";

	/**
	 * Set PostgREST Configs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_PostgREST_Config_ID (int S_PostgREST_Config_ID);

	/**
	 * Get PostgREST Configs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_PostgREST_Config_ID();

	ModelColumn<I_S_PostgREST_Config, Object> COLUMN_S_PostgREST_Config_ID = new ModelColumn<>(I_S_PostgREST_Config.class, "S_PostgREST_Config_ID", null);
	String COLUMNNAME_S_PostgREST_Config_ID = "S_PostgREST_Config_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Updated = new ModelColumn<>(I_S_PostgREST_Config.class, "Updated", null);
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
