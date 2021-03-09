package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Workflow
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Workflow 
{

	String Table_Name = "AD_Workflow";

//	/** AD_Table_ID=117 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccessLevel (java.lang.String AccessLevel);

	/**
	 * Get Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAccessLevel();

	ModelColumn<I_AD_Workflow, Object> COLUMN_AccessLevel = new ModelColumn<>(I_AD_Workflow.class, "AccessLevel", null);
	String COLUMNNAME_AccessLevel = "AccessLevel";

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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_InCharge_ID();

	String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Node_ID();

	String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

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
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Workflow_ID();

	ModelColumn<I_AD_Workflow, Object> COLUMN_AD_Workflow_ID = new ModelColumn<>(I_AD_Workflow.class, "AD_Workflow_ID", null);
	String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Set Workflow - Prozessor.
	 * Workflow Processor Server
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_WorkflowProcessor_ID (int AD_WorkflowProcessor_ID);

	/**
	 * Get Workflow - Prozessor.
	 * Workflow Processor Server
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_WorkflowProcessor_ID();

	@Nullable org.compiere.model.I_AD_WorkflowProcessor getAD_WorkflowProcessor();

	void setAD_WorkflowProcessor(@Nullable org.compiere.model.I_AD_WorkflowProcessor AD_WorkflowProcessor);

	ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_WorkflowProcessor> COLUMN_AD_WorkflowProcessor_ID = new ModelColumn<>(I_AD_Workflow.class, "AD_WorkflowProcessor_ID", org.compiere.model.I_AD_WorkflowProcessor.class);
	String COLUMNNAME_AD_WorkflowProcessor_ID = "AD_WorkflowProcessor_ID";

	/**
	 * Set Author.
	 * Author/Creator of the Entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAuthor (java.lang.String Author);

	/**
	 * Get Author.
	 * Author/Creator of the Entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAuthor();

	ModelColumn<I_AD_Workflow, Object> COLUMN_Author = new ModelColumn<>(I_AD_Workflow.class, "Author", null);
	String COLUMNNAME_Author = "Author";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Cost = new ModelColumn<>(I_AD_Workflow.class, "Cost", null);
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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Created = new ModelColumn<>(I_AD_Workflow.class, "Created", null);
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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Description = new ModelColumn<>(I_AD_Workflow.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_AD_Workflow, Object> COLUMN_DocumentNo = new ModelColumn<>(I_AD_Workflow.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Document Value Logic.
	 * Logic to determine Workflow Start - If true, a workflow process is started for the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocValueLogic (@Nullable java.lang.String DocValueLogic);

	/**
	 * Get Document Value Logic.
	 * Logic to determine Workflow Start - If true, a workflow process is started for the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocValueLogic();

	ModelColumn<I_AD_Workflow, Object> COLUMN_DocValueLogic = new ModelColumn<>(I_AD_Workflow.class, "DocValueLogic", null);
	String COLUMNNAME_DocValueLogic = "DocValueLogic";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Duration = new ModelColumn<>(I_AD_Workflow.class, "Duration", null);
	String COLUMNNAME_Duration = "Duration";

	/**
	 * Set Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDurationLimit (int DurationLimit);

	/**
	 * Get Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDurationLimit();

	ModelColumn<I_AD_Workflow, Object> COLUMN_DurationLimit = new ModelColumn<>(I_AD_Workflow.class, "DurationLimit", null);
	String COLUMNNAME_DurationLimit = "DurationLimit";

	/**
	 * Set Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDurationUnit (@Nullable java.lang.String DurationUnit);

	/**
	 * Get Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDurationUnit();

	ModelColumn<I_AD_Workflow, Object> COLUMN_DurationUnit = new ModelColumn<>(I_AD_Workflow.class, "DurationUnit", null);
	String COLUMNNAME_DurationUnit = "DurationUnit";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Workflow.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Help = new ModelColumn<>(I_AD_Workflow.class, "Help", null);
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

	ModelColumn<I_AD_Workflow, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Workflow.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBetaFunctionality (boolean IsBetaFunctionality);

	/**
	 * Get Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBetaFunctionality();

	ModelColumn<I_AD_Workflow, Object> COLUMN_IsBetaFunctionality = new ModelColumn<>(I_AD_Workflow.class, "IsBetaFunctionality", null);
	String COLUMNNAME_IsBetaFunctionality = "IsBetaFunctionality";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_AD_Workflow, Object> COLUMN_IsDefault = new ModelColumn<>(I_AD_Workflow.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsValid (boolean IsValid);

	/**
	 * Get Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isValid();

	ModelColumn<I_AD_Workflow, Object> COLUMN_IsValid = new ModelColumn<>(I_AD_Workflow.class, "IsValid", null);
	String COLUMNNAME_IsValid = "IsValid";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_MovingTime = new ModelColumn<>(I_AD_Workflow.class, "MovingTime", null);
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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Name = new ModelColumn<>(I_AD_Workflow.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOverlapUnits (@Nullable BigDecimal OverlapUnits);

	/**
	 * Get Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOverlapUnits();

	ModelColumn<I_AD_Workflow, Object> COLUMN_OverlapUnits = new ModelColumn<>(I_AD_Workflow.class, "OverlapUnits", null);
	String COLUMNNAME_OverlapUnits = "OverlapUnits";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Priority = new ModelColumn<>(I_AD_Workflow.class, "Priority", null);
	String COLUMNNAME_Priority = "Priority";

	/**
	 * Set Process Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessType (@Nullable java.lang.String ProcessType);

	/**
	 * Get Process Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProcessType();

	ModelColumn<I_AD_Workflow, Object> COLUMN_ProcessType = new ModelColumn<>(I_AD_Workflow.class, "ProcessType", null);
	String COLUMNNAME_ProcessType = "ProcessType";

	/**
	 * Set Publication Status.
	 * Status of Publication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPublishStatus (java.lang.String PublishStatus);

	/**
	 * Get Publication Status.
	 * Status of Publication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPublishStatus();

	ModelColumn<I_AD_Workflow, Object> COLUMN_PublishStatus = new ModelColumn<>(I_AD_Workflow.class, "PublishStatus", null);
	String COLUMNNAME_PublishStatus = "PublishStatus";

	/**
	 * Set Qty Batch Size.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyBatchSize (@Nullable BigDecimal QtyBatchSize);

	/**
	 * Get Qty Batch Size.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBatchSize();

	ModelColumn<I_AD_Workflow, Object> COLUMN_QtyBatchSize = new ModelColumn<>(I_AD_Workflow.class, "QtyBatchSize", null);
	String COLUMNNAME_QtyBatchSize = "QtyBatchSize";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_QueuingTime = new ModelColumn<>(I_AD_Workflow.class, "QueuingTime", null);
	String COLUMNNAME_QueuingTime = "QueuingTime";

	/**
	 * Set Ressource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	@Nullable org.compiere.model.I_S_Resource getS_Resource();

	void setS_Resource(@Nullable org.compiere.model.I_S_Resource S_Resource);

	ModelColumn<I_AD_Workflow, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new ModelColumn<>(I_AD_Workflow.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_SetupTime = new ModelColumn<>(I_AD_Workflow.class, "SetupTime", null);
	String COLUMNNAME_SetupTime = "SetupTime";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_UnitsCycles = new ModelColumn<>(I_AD_Workflow.class, "UnitsCycles", null);
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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Workflow.class, "Updated", null);
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
	 * Set Workflow validieren.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidateWorkflow (@Nullable java.lang.String ValidateWorkflow);

	/**
	 * Get Workflow validieren.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValidateWorkflow();

	ModelColumn<I_AD_Workflow, Object> COLUMN_ValidateWorkflow = new ModelColumn<>(I_AD_Workflow.class, "ValidateWorkflow", null);
	String COLUMNNAME_ValidateWorkflow = "ValidateWorkflow";

	/**
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidFrom (@Nullable java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidFrom();

	ModelColumn<I_AD_Workflow, Object> COLUMN_ValidFrom = new ModelColumn<>(I_AD_Workflow.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_AD_Workflow, Object> COLUMN_ValidTo = new ModelColumn<>(I_AD_Workflow.class, "ValidTo", null);
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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Value = new ModelColumn<>(I_AD_Workflow.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Version.
	 * Version of the table definition
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVersion (int Version);

	/**
	 * Get Version.
	 * Version of the table definition
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getVersion();

	ModelColumn<I_AD_Workflow, Object> COLUMN_Version = new ModelColumn<>(I_AD_Workflow.class, "Version", null);
	String COLUMNNAME_Version = "Version";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_WaitingTime = new ModelColumn<>(I_AD_Workflow.class, "WaitingTime", null);
	String COLUMNNAME_WaitingTime = "WaitingTime";

	/**
	 * Set Workflow Type.
	 * Type of Worflow
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWorkflowType (java.lang.String WorkflowType);

	/**
	 * Get Workflow Type.
	 * Type of Worflow
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getWorkflowType();

	ModelColumn<I_AD_Workflow, Object> COLUMN_WorkflowType = new ModelColumn<>(I_AD_Workflow.class, "WorkflowType", null);
	String COLUMNNAME_WorkflowType = "WorkflowType";

	/**
	 * Set Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWorkingTime (int WorkingTime);

	/**
	 * Get Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWorkingTime();

	ModelColumn<I_AD_Workflow, Object> COLUMN_WorkingTime = new ModelColumn<>(I_AD_Workflow.class, "WorkingTime", null);
	String COLUMNNAME_WorkingTime = "WorkingTime";

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

	ModelColumn<I_AD_Workflow, Object> COLUMN_Yield = new ModelColumn<>(I_AD_Workflow.class, "Yield", null);
	String COLUMNNAME_Yield = "Yield";
}
