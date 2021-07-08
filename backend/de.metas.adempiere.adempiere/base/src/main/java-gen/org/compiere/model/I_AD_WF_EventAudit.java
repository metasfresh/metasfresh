package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_WF_EventAudit
 *  @author metasfresh (generated) 
 */
public interface I_AD_WF_EventAudit 
{

	String Table_Name = "AD_WF_EventAudit";

//	/** AD_Table_ID=649 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Workflow Event Audit.
	 * Workflow Process Activity Event Audit Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_EventAudit_ID (int AD_WF_EventAudit_ID);

	/**
	 * Get Workflow Event Audit.
	 * Workflow Process Activity Event Audit Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_EventAudit_ID();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_AD_WF_EventAudit_ID = new ModelColumn<>(I_AD_WF_EventAudit.class, "AD_WF_EventAudit_ID", null);
	String COLUMNNAME_AD_WF_EventAudit_ID = "AD_WF_EventAudit_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Node_ID();

	String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Set Workflow-Prozess.
	 * Actual Workflow Process Instance
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Process_ID (int AD_WF_Process_ID);

	/**
	 * Get Workflow-Prozess.
	 * Actual Workflow Process Instance
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Process_ID();

	String COLUMNNAME_AD_WF_Process_ID = "AD_WF_Process_ID";

	/**
	 * Set Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/**
	 * Get Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Responsible_ID();

	String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeName (@Nullable java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeName();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_AttributeName = new ModelColumn<>(I_AD_WF_EventAudit.class, "AttributeName", null);
	String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_Created = new ModelColumn<>(I_AD_WF_EventAudit.class, "Created", null);
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

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_Description = new ModelColumn<>(I_AD_WF_EventAudit.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Elapsed Time ms.
	 * Elapsed Time in mili seconds
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setElapsedTimeMS (BigDecimal ElapsedTimeMS);

	/**
	 * Get Elapsed Time ms.
	 * Elapsed Time in mili seconds
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getElapsedTimeMS();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_ElapsedTimeMS = new ModelColumn<>(I_AD_WF_EventAudit.class, "ElapsedTimeMS", null);
	String COLUMNNAME_ElapsedTimeMS = "ElapsedTimeMS";

	/**
	 * Set Ereignisart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEventType (java.lang.String EventType);

	/**
	 * Get Ereignisart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEventType();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_EventType = new ModelColumn<>(I_AD_WF_EventAudit.class, "EventType", null);
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

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_WF_EventAudit.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Neuer Wert.
	 * New field value
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNewValue (@Nullable java.lang.String NewValue);

	/**
	 * Get Neuer Wert.
	 * New field value
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNewValue();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_NewValue = new ModelColumn<>(I_AD_WF_EventAudit.class, "NewValue", null);
	String COLUMNNAME_NewValue = "NewValue";

	/**
	 * Set Alter Wert.
	 * The old file data
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOldValue (@Nullable java.lang.String OldValue);

	/**
	 * Get Alter Wert.
	 * The old file data
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOldValue();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_OldValue = new ModelColumn<>(I_AD_WF_EventAudit.class, "OldValue", null);
	String COLUMNNAME_OldValue = "OldValue";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_Record_ID = new ModelColumn<>(I_AD_WF_EventAudit.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Mitteilung.
	 * Text Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTextMsg (@Nullable java.lang.String TextMsg);

	/**
	 * Get Mitteilung.
	 * Text Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTextMsg();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_TextMsg = new ModelColumn<>(I_AD_WF_EventAudit.class, "TextMsg", null);
	String COLUMNNAME_TextMsg = "TextMsg";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_Updated = new ModelColumn<>(I_AD_WF_EventAudit.class, "Updated", null);
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
	 * Set Workflow State.
	 * State of the execution of the workflow
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWFState (java.lang.String WFState);

	/**
	 * Get Workflow State.
	 * State of the execution of the workflow
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getWFState();

	ModelColumn<I_AD_WF_EventAudit, Object> COLUMN_WFState = new ModelColumn<>(I_AD_WF_EventAudit.class, "WFState", null);
	String COLUMNNAME_WFState = "WFState";
}
