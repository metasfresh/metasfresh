package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_WF_Activity
 *  @author metasfresh (generated) 
 */
public interface I_AD_WF_Activity 
{

	String Table_Name = "AD_WF_Activity";

//	/** AD_Table_ID=644 */
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
	 * Set Message.
	 * System Message
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Message_ID (int AD_Message_ID);

	/**
	 * Get Message.
	 * System Message
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Message_ID();

	String COLUMNNAME_AD_Message_ID = "AD_Message_ID";

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
	 * Set Workflow-Aktivität.
	 * Workflow Activity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Activity_ID (int AD_WF_Activity_ID);

	/**
	 * Get Workflow-Aktivität.
	 * Workflow Activity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Activity_ID();

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_AD_WF_Activity_ID = new ModelColumn<>(I_AD_WF_Activity.class, "AD_WF_Activity_ID", null);
	String COLUMNNAME_AD_WF_Activity_ID = "AD_WF_Activity_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Node_ID();

	String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Set Workflow-Prozess.
	 * Actual Workflow Process Instance
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Process_ID (int AD_WF_Process_ID);

	/**
	 * Get Workflow-Prozess.
	 * Actual Workflow Process Instance
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Process_ID();

	String COLUMNNAME_AD_WF_Process_ID = "AD_WF_Process_ID";

	/**
	 * Set Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/**
	 * Get Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Responsible_ID();

	String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Workflow_ID();

	String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_Created = new ModelColumn<>(I_AD_WF_Activity.class, "Created", null);
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
	 * Set Letzte Benachrichtigung.
	 * Date when last alert were sent
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateLastAlert (@Nullable java.sql.Timestamp DateLastAlert);

	/**
	 * Get Letzte Benachrichtigung.
	 * Date when last alert were sent
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateLastAlert();

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_DateLastAlert = new ModelColumn<>(I_AD_WF_Activity.class, "DateLastAlert", null);
	String COLUMNNAME_DateLastAlert = "DateLastAlert";

	/**
	 * Set Dyn Priority Start.
	 * Starting priority before changed dynamically
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDynPriorityStart (int DynPriorityStart);

	/**
	 * Get Dyn Priority Start.
	 * Starting priority before changed dynamically
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDynPriorityStart();

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_DynPriorityStart = new ModelColumn<>(I_AD_WF_Activity.class, "DynPriorityStart", null);
	String COLUMNNAME_DynPriorityStart = "DynPriorityStart";

	/**
	 * Set End Wait.
	 * End of sleep time
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEndWaitTime (@Nullable java.sql.Timestamp EndWaitTime);

	/**
	 * Get End Wait.
	 * End of sleep time
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEndWaitTime();

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_EndWaitTime = new ModelColumn<>(I_AD_WF_Activity.class, "EndWaitTime", null);
	String COLUMNNAME_EndWaitTime = "EndWaitTime";

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

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_WF_Activity.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriority (int Priority);

	/**
	 * Get Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPriority();

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_Priority = new ModelColumn<>(I_AD_WF_Activity.class, "Priority", null);
	String COLUMNNAME_Priority = "Priority";

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

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_Processed = new ModelColumn<>(I_AD_WF_Activity.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_Processing = new ModelColumn<>(I_AD_WF_Activity.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

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

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_Record_ID = new ModelColumn<>(I_AD_WF_Activity.class, "Record_ID", null);
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

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_TextMsg = new ModelColumn<>(I_AD_WF_Activity.class, "TextMsg", null);
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

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_Updated = new ModelColumn<>(I_AD_WF_Activity.class, "Updated", null);
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

	ModelColumn<I_AD_WF_Activity, Object> COLUMN_WFState = new ModelColumn<>(I_AD_WF_Activity.class, "WFState", null);
	String COLUMNNAME_WFState = "WFState";
}
