package de.metas.elasticsearch.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for ES_FTS_Index_Queue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ES_FTS_Index_Queue 
{

	String Table_Name = "ES_FTS_Index_Queue";

//	/** AD_Table_ID=541757 */
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
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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

	ModelColumn<I_ES_FTS_Index_Queue, Object> COLUMN_Created = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "Created", null);
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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Config_ID (int ES_FTS_Config_ID);

	/**
	 * Get Full Text Search Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Config_ID();

	de.metas.elasticsearch.model.I_ES_FTS_Config getES_FTS_Config();

	void setES_FTS_Config(de.metas.elasticsearch.model.I_ES_FTS_Config ES_FTS_Config);

	ModelColumn<I_ES_FTS_Index_Queue, de.metas.elasticsearch.model.I_ES_FTS_Config> COLUMN_ES_FTS_Config_ID = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "ES_FTS_Config_ID", de.metas.elasticsearch.model.I_ES_FTS_Config.class);
	String COLUMNNAME_ES_FTS_Config_ID = "ES_FTS_Config_ID";

	/**
	 * Set FTS Models To Index Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Index_Queue_ID (int ES_FTS_Index_Queue_ID);

	/**
	 * Get FTS Models To Index Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Index_Queue_ID();

	ModelColumn<I_ES_FTS_Index_Queue, Object> COLUMN_ES_FTS_Index_Queue_ID = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "ES_FTS_Index_Queue_ID", null);
	String COLUMNNAME_ES_FTS_Index_Queue_ID = "ES_FTS_Index_Queue_ID";

	/**
	 * Set Event Type.
	 * Type of Event
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEventType (java.lang.String EventType);

	/**
	 * Get Event Type.
	 * Type of Event
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEventType();

	ModelColumn<I_ES_FTS_Index_Queue, Object> COLUMN_EventType = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "EventType", null);
	String COLUMNNAME_EventType = "EventType";

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

	ModelColumn<I_ES_FTS_Index_Queue, Object> COLUMN_IsActive = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsError (boolean IsError);

	/**
	 * Get Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isError();

	ModelColumn<I_ES_FTS_Index_Queue, Object> COLUMN_IsError = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "IsError", null);
	String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_ES_FTS_Index_Queue, Object> COLUMN_Processed = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Processing Tag.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessingTag (@Nullable java.lang.String ProcessingTag);

	/**
	 * Get Processing Tag.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProcessingTag();

	ModelColumn<I_ES_FTS_Index_Queue, Object> COLUMN_ProcessingTag = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "ProcessingTag", null);
	String COLUMNNAME_ProcessingTag = "ProcessingTag";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_ES_FTS_Index_Queue, Object> COLUMN_Record_ID = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ES_FTS_Index_Queue, Object> COLUMN_Updated = new ModelColumn<>(I_ES_FTS_Index_Queue.class, "Updated", null);
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
