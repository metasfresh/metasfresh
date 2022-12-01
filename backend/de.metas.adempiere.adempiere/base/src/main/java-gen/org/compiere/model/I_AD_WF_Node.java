package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for AD_WF_Node
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_WF_Node 
{

	String Table_Name = "AD_WF_Node";

//	/** AD_Table_ID=129 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAction (java.lang.String Action);

	/**
	 * Get Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAction();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Action = new ModelColumn<>(I_AD_WF_Node.class, "Action", null);
	String COLUMNNAME_Action = "Action";

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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	@Nullable org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(@Nullable org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_AD_WF_Node.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Form_ID (int AD_Form_ID);

	/**
	 * Get Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Form_ID();

	@Nullable org.compiere.model.I_AD_Form getAD_Form();

	void setAD_Form(@Nullable org.compiere.model.I_AD_Form AD_Form);

	ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new ModelColumn<>(I_AD_WF_Node.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
	String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set Image.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Image_ID (int AD_Image_ID);

	/**
	 * Get Image.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Image_ID();

	@Nullable org.compiere.model.I_AD_Image getAD_Image();

	void setAD_Image(@Nullable org.compiere.model.I_AD_Image AD_Image);

	ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Image> COLUMN_AD_Image_ID = new ModelColumn<>(I_AD_WF_Node.class, "AD_Image_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_AD_Image_ID = "AD_Image_ID";

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

	ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_AD_WF_Node.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Task_ID (int AD_Task_ID);

	/**
	 * Get Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Task_ID();

	@Nullable org.compiere.model.I_AD_Task getAD_Task();

	void setAD_Task(@Nullable org.compiere.model.I_AD_Task AD_Task);

	ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Task> COLUMN_AD_Task_ID = new ModelColumn<>(I_AD_WF_Node.class, "AD_Task_ID", org.compiere.model.I_AD_Task.class);
	String COLUMNNAME_AD_Task_ID = "AD_Task_ID";

	/**
	 * Set Workflow Block.
	 * Workflow Transaction Execution Block
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Block_ID (int AD_WF_Block_ID);

	/**
	 * Get Workflow Block.
	 * Workflow Transaction Execution Block
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Block_ID();

	String COLUMNNAME_AD_WF_Block_ID = "AD_WF_Block_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Node_ID();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_AD_WF_Node_ID = new ModelColumn<>(I_AD_WF_Node.class, "AD_WF_Node_ID", null);
	String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Set Workflow Steps Template (ID).
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Node_Template_ID (int AD_WF_Node_Template_ID);

	/**
	 * Get Workflow Steps Template (ID).
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Node_Template_ID();

	String COLUMNNAME_AD_WF_Node_Template_ID = "AD_WF_Node_Template_ID";

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
	 * Set Window.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Window.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Window_ID();

	@Nullable org.compiere.model.I_AD_Window getAD_Window();

	void setAD_Window(@Nullable org.compiere.model.I_AD_Window AD_Window);

	ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new ModelColumn<>(I_AD_WF_Node.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

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

	ModelColumn<I_AD_WF_Node, Object> COLUMN_AttributeName = new ModelColumn<>(I_AD_WF_Node.class, "AttributeName", null);
	String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Set Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeValue (@Nullable java.lang.String AttributeValue);

	/**
	 * Get Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeValue();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_AttributeValue = new ModelColumn<>(I_AD_WF_Node.class, "AttributeValue", null);
	String COLUMNNAME_AttributeValue = "AttributeValue";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Kosten.
	 * Cost information
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCost (BigDecimal Cost);

	/**
	 * Get Kosten.
	 * Cost information
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCost();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Cost = new ModelColumn<>(I_AD_WF_Node.class, "Cost", null);
	String COLUMNNAME_Cost = "Cost";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Created = new ModelColumn<>(I_AD_WF_Node.class, "Created", null);
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

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Description = new ModelColumn<>(I_AD_WF_Node.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocAction (@Nullable java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocAction();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_DocAction = new ModelColumn<>(I_AD_WF_Node.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDuration (int Duration);

	/**
	 * Get Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDuration();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Duration = new ModelColumn<>(I_AD_WF_Node.class, "Duration", null);
	String COLUMNNAME_Duration = "Duration";

	/**
	 * Set Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDurationLimit (int DurationLimit);

	/**
	 * Get Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDurationLimit();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_DurationLimit = new ModelColumn<>(I_AD_WF_Node.class, "DurationLimit", null);
	String COLUMNNAME_DurationLimit = "DurationLimit";

	/**
	 * Set Dynamic Priority Change.
	 * Change of priority when Activity is suspended waiting for user
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDynPriorityChange (@Nullable BigDecimal DynPriorityChange);

	/**
	 * Get Dynamic Priority Change.
	 * Change of priority when Activity is suspended waiting for user
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDynPriorityChange();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_DynPriorityChange = new ModelColumn<>(I_AD_WF_Node.class, "DynPriorityChange", null);
	String COLUMNNAME_DynPriorityChange = "DynPriorityChange";

	/**
	 * Set Dynamic Priority Unit.
	 * Change of priority when Activity is suspended waiting for user
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDynPriorityUnit (@Nullable java.lang.String DynPriorityUnit);

	/**
	 * Get Dynamic Priority Unit.
	 * Change of priority when Activity is suspended waiting for user
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDynPriorityUnit();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_DynPriorityUnit = new ModelColumn<>(I_AD_WF_Node.class, "DynPriorityUnit", null);
	String COLUMNNAME_DynPriorityUnit = "DynPriorityUnit";

	/**
	 * Set eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail (@Nullable java.lang.String EMail);

	/**
	 * Get eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_EMail = new ModelColumn<>(I_AD_WF_Node.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set EMail Recipient.
	 * Recipient of the EMail
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMailRecipient (@Nullable java.lang.String EMailRecipient);

	/**
	 * Get EMail Recipient.
	 * Recipient of the EMail
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMailRecipient();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_EMailRecipient = new ModelColumn<>(I_AD_WF_Node.class, "EMailRecipient", null);
	String COLUMNNAME_EMailRecipient = "EMailRecipient";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_WF_Node.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Finish Mode.
	 * Workflow Activity Finish Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFinishMode (@Nullable java.lang.String FinishMode);

	/**
	 * Get Finish Mode.
	 * Workflow Activity Finish Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFinishMode();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_FinishMode = new ModelColumn<>(I_AD_WF_Node.class, "FinishMode", null);
	String COLUMNNAME_FinishMode = "FinishMode";

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

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Help = new ModelColumn<>(I_AD_WF_Node.class, "Help", null);
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

	ModelColumn<I_AD_WF_Node, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_WF_Node.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zentral verwaltet.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCentrallyMaintained (boolean IsCentrallyMaintained);

	/**
	 * Get Zentral verwaltet.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCentrallyMaintained();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_IsCentrallyMaintained = new ModelColumn<>(I_AD_WF_Node.class, "IsCentrallyMaintained", null);
	String COLUMNNAME_IsCentrallyMaintained = "IsCentrallyMaintained";

	/**
	 * Set Is Milestone.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsMilestone (boolean IsMilestone);

	/**
	 * Get Is Milestone.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isMilestone();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_IsMilestone = new ModelColumn<>(I_AD_WF_Node.class, "IsMilestone", null);
	String COLUMNNAME_IsMilestone = "IsMilestone";

	/**
	 * Set Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSubcontracting (boolean IsSubcontracting);

	/**
	 * Get Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSubcontracting();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_IsSubcontracting = new ModelColumn<>(I_AD_WF_Node.class, "IsSubcontracting", null);
	String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

	/**
	 * Set Join Element.
	 * Semantics for multiple incoming Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setJoinElement (java.lang.String JoinElement);

	/**
	 * Get Join Element.
	 * Semantics for multiple incoming Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getJoinElement();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_JoinElement = new ModelColumn<>(I_AD_WF_Node.class, "JoinElement", null);
	String COLUMNNAME_JoinElement = "JoinElement";

	/**
	 * Set Moving Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMovingTime (int MovingTime);

	/**
	 * Get Moving Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMovingTime();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_MovingTime = new ModelColumn<>(I_AD_WF_Node.class, "MovingTime", null);
	String COLUMNNAME_MovingTime = "MovingTime";

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

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Name = new ModelColumn<>(I_AD_WF_Node.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOverlapUnits (int OverlapUnits);

	/**
	 * Get Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOverlapUnits();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_OverlapUnits = new ModelColumn<>(I_AD_WF_Node.class, "OverlapUnits", null);
	String COLUMNNAME_OverlapUnits = "OverlapUnits";

	/**
	 * Set Manufacturing Activity Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Activity_Type (@Nullable java.lang.String PP_Activity_Type);

	/**
	 * Get Manufacturing Activity Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPP_Activity_Type();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_PP_Activity_Type = new ModelColumn<>(I_AD_WF_Node.class, "PP_Activity_Type", null);
	String COLUMNNAME_PP_Activity_Type = "PP_Activity_Type";

	/**
	 * Set User Instructions.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_UserInstructions (@Nullable java.lang.String PP_UserInstructions);

	/**
	 * Get User Instructions.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPP_UserInstructions();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_PP_UserInstructions = new ModelColumn<>(I_AD_WF_Node.class, "PP_UserInstructions", null);
	String COLUMNNAME_PP_UserInstructions = "PP_UserInstructions";

	/**
	 * Set Always available to user.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_AlwaysAvailableToUser (@Nullable java.lang.String PP_AlwaysAvailableToUser);

	/**
	 * Get Always available to user.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPP_AlwaysAvailableToUser();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_PP_AlwaysAvailableToUser = new ModelColumn<>(I_AD_WF_Node.class, "PP_AlwaysAvailableToUser", null);
	String COLUMNNAME_PP_AlwaysAvailableToUser = "PP_AlwaysAvailableToUser";

	/**
	 * Set Priority.
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

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Priority = new ModelColumn<>(I_AD_WF_Node.class, "Priority", null);
	String COLUMNNAME_Priority = "Priority";

	/**
	 * Set Queuing Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQueuingTime (int QueuingTime);

	/**
	 * Get Queuing Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getQueuingTime();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_QueuingTime = new ModelColumn<>(I_AD_WF_Node.class, "QueuingTime", null);
	String COLUMNNAME_QueuingTime = "QueuingTime";

	/**
	 * Set EMail-Vorlage.
	 * Text templates for mailings
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_MailText_ID (int R_MailText_ID);

	/**
	 * Get EMail-Vorlage.
	 * Text templates for mailings
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_MailText_ID();

	@Nullable org.compiere.model.I_R_MailText getR_MailText();

	void setR_MailText(@Nullable org.compiere.model.I_R_MailText R_MailText);

	ModelColumn<I_AD_WF_Node, org.compiere.model.I_R_MailText> COLUMN_R_MailText_ID = new ModelColumn<>(I_AD_WF_Node.class, "R_MailText_ID", org.compiere.model.I_R_MailText.class);
	String COLUMNNAME_R_MailText_ID = "R_MailText_ID";

	/**
	 * Set Setup Time.
	 * Setup time before starting Production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSetupTime (int SetupTime);

	/**
	 * Get Setup Time.
	 * Setup time before starting Production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSetupTime();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_SetupTime = new ModelColumn<>(I_AD_WF_Node.class, "SetupTime", null);
	String COLUMNNAME_SetupTime = "SetupTime";

	/**
	 * Set Split Element.
	 * Semantics for multiple outgoing Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSplitElement (java.lang.String SplitElement);

	/**
	 * Get Split Element.
	 * Semantics for multiple outgoing Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSplitElement();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_SplitElement = new ModelColumn<>(I_AD_WF_Node.class, "SplitElement", null);
	String COLUMNNAME_SplitElement = "SplitElement";

	/**
	 * Set Resource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Resource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	@Nullable org.compiere.model.I_S_Resource getS_Resource();

	void setS_Resource(@Nullable org.compiere.model.I_S_Resource S_Resource);

	ModelColumn<I_AD_WF_Node, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new ModelColumn<>(I_AD_WF_Node.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set Start Mode.
	 * Workflow Activity Start Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStartMode (@Nullable java.lang.String StartMode);

	/**
	 * Get Start Mode.
	 * Workflow Activity Start Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStartMode();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_StartMode = new ModelColumn<>(I_AD_WF_Node.class, "StartMode", null);
	String COLUMNNAME_StartMode = "StartMode";

	/**
	 * Set Subflow Execution.
	 * Mode how the sub-workflow is executed
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSubflowExecution (@Nullable java.lang.String SubflowExecution);

	/**
	 * Get Subflow Execution.
	 * Mode how the sub-workflow is executed
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSubflowExecution();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_SubflowExecution = new ModelColumn<>(I_AD_WF_Node.class, "SubflowExecution", null);
	String COLUMNNAME_SubflowExecution = "SubflowExecution";

	/**
	 * Set Units by Cycles.
	 * The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUnitsCycles (@Nullable BigDecimal UnitsCycles);

	/**
	 * Get Units by Cycles.
	 * The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getUnitsCycles();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_UnitsCycles = new ModelColumn<>(I_AD_WF_Node.class, "UnitsCycles", null);
	String COLUMNNAME_UnitsCycles = "UnitsCycles";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Updated = new ModelColumn<>(I_AD_WF_Node.class, "Updated", null);
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
	 * Set Valid From.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidFrom (@Nullable java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidFrom();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_ValidFrom = new ModelColumn<>(I_AD_WF_Node.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_ValidTo = new ModelColumn<>(I_AD_WF_Node.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Value = new ModelColumn<>(I_AD_WF_Node.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Waiting Time.
	 * Workflow Simulation Waiting time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWaitingTime (int WaitingTime);

	/**
	 * Get Waiting Time.
	 * Workflow Simulation Waiting time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWaitingTime();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_WaitingTime = new ModelColumn<>(I_AD_WF_Node.class, "WaitingTime", null);
	String COLUMNNAME_WaitingTime = "WaitingTime";

	/**
	 * Set Wait Time.
	 * Time in minutes to wait (sleep)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWaitTime (int WaitTime);

	/**
	 * Get Wait Time.
	 * Time in minutes to wait (sleep)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWaitTime();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_WaitTime = new ModelColumn<>(I_AD_WF_Node.class, "WaitTime", null);
	String COLUMNNAME_WaitTime = "WaitTime";

	/**
	 * Set Workflow.
	 * Workflow or tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWorkflow_ID (int Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWorkflow_ID();

	String COLUMNNAME_Workflow_ID = "Workflow_ID";

	/**
	 * Set Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWorkingTime (int WorkingTime);

	/**
	 * Get Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWorkingTime();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_WorkingTime = new ModelColumn<>(I_AD_WF_Node.class, "WorkingTime", null);
	String COLUMNNAME_WorkingTime = "WorkingTime";

	/**
	 * Set X Position.
	 * Absolute X (horizontal) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setXPosition (int XPosition);

	/**
	 * Get X Position.
	 * Absolute X (horizontal) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getXPosition();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_XPosition = new ModelColumn<>(I_AD_WF_Node.class, "XPosition", null);
	String COLUMNNAME_XPosition = "XPosition";

	/**
	 * Set Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setYield (int Yield);

	/**
	 * Get Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getYield();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_Yield = new ModelColumn<>(I_AD_WF_Node.class, "Yield", null);
	String COLUMNNAME_Yield = "Yield";

	/**
	 * Set Y Position.
	 * Absolute Y (vertical) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setYPosition (int YPosition);

	/**
	 * Get Y Position.
	 * Absolute Y (vertical) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getYPosition();

	ModelColumn<I_AD_WF_Node, Object> COLUMN_YPosition = new ModelColumn<>(I_AD_WF_Node.class, "YPosition", null);
	String COLUMNNAME_YPosition = "YPosition";
}
