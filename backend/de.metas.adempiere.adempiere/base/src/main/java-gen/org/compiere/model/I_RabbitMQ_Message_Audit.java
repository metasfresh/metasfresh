package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for RabbitMQ_Message_Audit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_RabbitMQ_Message_Audit 
{

	String Table_Name = "RabbitMQ_Message_Audit";

//	/** AD_Table_ID=541986 */
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
	 * Set Content.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContent (@Nullable java.lang.String Content);

	/**
	 * Get Content.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContent();

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_Content = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "Content", null);
	String COLUMNNAME_Content = "Content";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_Created = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "Created", null);
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
	 * Set Direction.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDirection (java.lang.String Direction);

	/**
	 * Get Direction.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDirection();

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_Direction = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "Direction", null);
	String COLUMNNAME_Direction = "Direction";

	/**
	 * Set Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEvent_UUID (@Nullable java.lang.String Event_UUID);

	/**
	 * Get Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEvent_UUID();

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_Event_UUID = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "Event_UUID", null);
	String COLUMNNAME_Event_UUID = "Event_UUID";

	/**
	 * Set Host.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHost (@Nullable java.lang.String Host);

	/**
	 * Get Host.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHost();

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_Host = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "Host", null);
	String COLUMNNAME_Host = "Host";

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

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_IsActive = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set RabbitMQ Message Audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRabbitMQ_Message_Audit_ID (int RabbitMQ_Message_Audit_ID);

	/**
	 * Get RabbitMQ Message Audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRabbitMQ_Message_Audit_ID();

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_RabbitMQ_Message_Audit_ID = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "RabbitMQ_Message_Audit_ID", null);
	String COLUMNNAME_RabbitMQ_Message_Audit_ID = "RabbitMQ_Message_Audit_ID";

	/**
	 * Set Queue Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRabbitMQ_QueueName (@Nullable java.lang.String RabbitMQ_QueueName);

	/**
	 * Get Queue Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRabbitMQ_QueueName();

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_RabbitMQ_QueueName = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "RabbitMQ_QueueName", null);
	String COLUMNNAME_RabbitMQ_QueueName = "RabbitMQ_QueueName";

	/**
	 * Set Related Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRelated_Event_UUID (@Nullable java.lang.String Related_Event_UUID);

	/**
	 * Get Related Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRelated_Event_UUID();

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_Related_Event_UUID = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "Related_Event_UUID", null);
	String COLUMNNAME_Related_Event_UUID = "Related_Event_UUID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_RabbitMQ_Message_Audit, Object> COLUMN_Updated = new ModelColumn<>(I_RabbitMQ_Message_Audit.class, "Updated", null);
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
