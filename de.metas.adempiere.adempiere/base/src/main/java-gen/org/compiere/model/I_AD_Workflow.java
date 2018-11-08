package org.compiere.model;


/** Generated Interface for AD_Workflow
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Workflow 
{

    /** TableName=AD_Workflow */
    public static final String Table_Name = "AD_Workflow";

    /** AD_Table_ID=117 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Set Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAccessLevel (java.lang.String AccessLevel);

	/**
	 * Get Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccessLevel();

    /** Column definition for AccessLevel */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_AccessLevel = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "AccessLevel", null);
    /** Column name AccessLevel */
    public static final String COLUMNNAME_AccessLevel = "AccessLevel";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_Client>(I_AD_Workflow.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_Org>(I_AD_Workflow.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_Table>(I_AD_Workflow.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_InCharge_ID();

	public org.compiere.model.I_AD_User getAD_User_InCharge();

	public void setAD_User_InCharge(org.compiere.model.I_AD_User AD_User_InCharge);

    /** Column definition for AD_User_InCharge_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_User> COLUMN_AD_User_InCharge_ID = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_User>(I_AD_Workflow.class, "AD_User_InCharge_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_InCharge_ID */
    public static final String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Node_ID();

	public org.compiere.model.I_AD_WF_Node getAD_WF_Node();

	public void setAD_WF_Node(org.compiere.model.I_AD_WF_Node AD_WF_Node);

    /** Column definition for AD_WF_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_WF_Node> COLUMN_AD_WF_Node_ID = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_WF_Node>(I_AD_Workflow.class, "AD_WF_Node_ID", org.compiere.model.I_AD_WF_Node.class);
    /** Column name AD_WF_Node_ID */
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Set Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/**
	 * Get Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Responsible_ID();

	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible();

	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible);

    /** Column definition for AD_WF_Responsible_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_WF_Responsible> COLUMN_AD_WF_Responsible_ID = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_WF_Responsible>(I_AD_Workflow.class, "AD_WF_Responsible_ID", org.compiere.model.I_AD_WF_Responsible.class);
    /** Column name AD_WF_Responsible_ID */
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Workflow_ID();

    /** Column definition for AD_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_AD_Workflow_ID = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "AD_Workflow_ID", null);
    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Set Workflow - Prozessor.
	 * Workflow Processor Server
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WorkflowProcessor_ID (int AD_WorkflowProcessor_ID);

	/**
	 * Get Workflow - Prozessor.
	 * Workflow Processor Server
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WorkflowProcessor_ID();

	public org.compiere.model.I_AD_WorkflowProcessor getAD_WorkflowProcessor();

	public void setAD_WorkflowProcessor(org.compiere.model.I_AD_WorkflowProcessor AD_WorkflowProcessor);

    /** Column definition for AD_WorkflowProcessor_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_WorkflowProcessor> COLUMN_AD_WorkflowProcessor_ID = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_WorkflowProcessor>(I_AD_Workflow.class, "AD_WorkflowProcessor_ID", org.compiere.model.I_AD_WorkflowProcessor.class);
    /** Column name AD_WorkflowProcessor_ID */
    public static final String COLUMNNAME_AD_WorkflowProcessor_ID = "AD_WorkflowProcessor_ID";

	/**
	 * Set Author.
	 * Author/Creator of the Entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAuthor (java.lang.String Author);

	/**
	 * Get Author.
	 * Author/Creator of the Entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAuthor();

    /** Column definition for Author */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Author = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Author", null);
    /** Column name Author */
    public static final String COLUMNNAME_Author = "Author";

	/**
	 * Set Kosten.
	 * Cost information
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCost (java.math.BigDecimal Cost);

	/**
	 * Get Kosten.
	 * Cost information
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCost();

    /** Column definition for Cost */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Cost = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Cost", null);
    /** Column name Cost */
    public static final String COLUMNNAME_Cost = "Cost";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_User>(I_AD_Workflow.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Document Value Logic.
	 * Logic to determine Workflow Start - If true, a workflow process is started for the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocValueLogic (java.lang.String DocValueLogic);

	/**
	 * Get Document Value Logic.
	 * Logic to determine Workflow Start - If true, a workflow process is started for the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocValueLogic();

    /** Column definition for DocValueLogic */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_DocValueLogic = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "DocValueLogic", null);
    /** Column name DocValueLogic */
    public static final String COLUMNNAME_DocValueLogic = "DocValueLogic";

	/**
	 * Set Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDuration (int Duration);

	/**
	 * Get Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDuration();

    /** Column definition for Duration */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Duration = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Duration", null);
    /** Column name Duration */
    public static final String COLUMNNAME_Duration = "Duration";

	/**
	 * Set Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDurationLimit (int DurationLimit);

	/**
	 * Get Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDurationLimit();

    /** Column definition for DurationLimit */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_DurationLimit = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "DurationLimit", null);
    /** Column name DurationLimit */
    public static final String COLUMNNAME_DurationLimit = "DurationLimit";

	/**
	 * Set Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDurationUnit (java.lang.String DurationUnit);

	/**
	 * Get Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDurationUnit();

    /** Column definition for DurationUnit */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_DurationUnit = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "DurationUnit", null);
    /** Column name DurationUnit */
    public static final String COLUMNNAME_DurationUnit = "DurationUnit";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBetaFunctionality (boolean IsBetaFunctionality);

	/**
	 * Get Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBetaFunctionality();

    /** Column definition for IsBetaFunctionality */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_IsBetaFunctionality = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "IsBetaFunctionality", null);
    /** Column name IsBetaFunctionality */
    public static final String COLUMNNAME_IsBetaFunctionality = "IsBetaFunctionality";

	/**
	 * Set Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefault (boolean IsDefault);

	/**
	 * Get Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Gültig.
	 * Element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsValid (boolean IsValid);

	/**
	 * Get Gültig.
	 * Element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isValid();

    /** Column definition for IsValid */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_IsValid = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "IsValid", null);
    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/**
	 * Set Moving Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMovingTime (int MovingTime);

	/**
	 * Get Moving Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMovingTime();

    /** Column definition for MovingTime */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_MovingTime = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "MovingTime", null);
    /** Column name MovingTime */
    public static final String COLUMNNAME_MovingTime = "MovingTime";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOverlapUnits (java.math.BigDecimal OverlapUnits);

	/**
	 * Get Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOverlapUnits();

    /** Column definition for OverlapUnits */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_OverlapUnits = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "OverlapUnits", null);
    /** Column name OverlapUnits */
    public static final String COLUMNNAME_OverlapUnits = "OverlapUnits";

	/**
	 * Set Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriority (int Priority);

	/**
	 * Get Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPriority();

    /** Column definition for Priority */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Priority = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Priority", null);
    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

	/**
	 * Set Process Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessType (java.lang.String ProcessType);

	/**
	 * Get Process Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProcessType();

    /** Column definition for ProcessType */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_ProcessType = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "ProcessType", null);
    /** Column name ProcessType */
    public static final String COLUMNNAME_ProcessType = "ProcessType";

	/**
	 * Set Publication Status.
	 * Status of Publication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPublishStatus (java.lang.String PublishStatus);

	/**
	 * Get Publication Status.
	 * Status of Publication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPublishStatus();

    /** Column definition for PublishStatus */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_PublishStatus = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "PublishStatus", null);
    /** Column name PublishStatus */
    public static final String COLUMNNAME_PublishStatus = "PublishStatus";

	/**
	 * Set Qty Batch Size.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyBatchSize (java.math.BigDecimal QtyBatchSize);

	/**
	 * Get Qty Batch Size.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyBatchSize();

    /** Column definition for QtyBatchSize */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_QtyBatchSize = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "QtyBatchSize", null);
    /** Column name QtyBatchSize */
    public static final String COLUMNNAME_QtyBatchSize = "QtyBatchSize";

	/**
	 * Set Queuing Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQueuingTime (int QueuingTime);

	/**
	 * Get Queuing Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getQueuingTime();

    /** Column definition for QueuingTime */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_QueuingTime = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "QueuingTime", null);
    /** Column name QueuingTime */
    public static final String COLUMNNAME_QueuingTime = "QueuingTime";

	/**
	 * Set Ressource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_Resource_ID();

	public org.compiere.model.I_S_Resource getS_Resource();

	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

    /** Column definition for S_Resource_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_S_Resource>(I_AD_Workflow.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
    /** Column name S_Resource_ID */
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set Setup Time.
	 * Setup time before starting Production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSetupTime (int SetupTime);

	/**
	 * Get Setup Time.
	 * Setup time before starting Production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSetupTime();

    /** Column definition for SetupTime */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_SetupTime = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "SetupTime", null);
    /** Column name SetupTime */
    public static final String COLUMNNAME_SetupTime = "SetupTime";

	/**
	 * Set Units by Cycles.
	 * The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUnitsCycles (java.math.BigDecimal UnitsCycles);

	/**
	 * Get Units by Cycles.
	 * The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getUnitsCycles();

    /** Column definition for UnitsCycles */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_UnitsCycles = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "UnitsCycles", null);
    /** Column name UnitsCycles */
    public static final String COLUMNNAME_UnitsCycles = "UnitsCycles";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Workflow, org.compiere.model.I_AD_User>(I_AD_Workflow.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Workflow validieren.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidateWorkflow (java.lang.String ValidateWorkflow);

	/**
	 * Get Workflow validieren.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValidateWorkflow();

    /** Column definition for ValidateWorkflow */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_ValidateWorkflow = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "ValidateWorkflow", null);
    /** Column name ValidateWorkflow */
    public static final String COLUMNNAME_ValidateWorkflow = "ValidateWorkflow";

	/**
	 * Set Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Version.
	 * Version of the table definition
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVersion (int Version);

	/**
	 * Get Version.
	 * Version of the table definition
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getVersion();

    /** Column definition for Version */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Version = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Version", null);
    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";

	/**
	 * Set Waiting Time.
	 * Workflow Simulation Waiting time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWaitingTime (int WaitingTime);

	/**
	 * Get Waiting Time.
	 * Workflow Simulation Waiting time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWaitingTime();

    /** Column definition for WaitingTime */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_WaitingTime = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "WaitingTime", null);
    /** Column name WaitingTime */
    public static final String COLUMNNAME_WaitingTime = "WaitingTime";

	/**
	 * Set Workflow Type.
	 * Type of Worflow
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWorkflowType (java.lang.String WorkflowType);

	/**
	 * Get Workflow Type.
	 * Type of Worflow
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWorkflowType();

    /** Column definition for WorkflowType */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_WorkflowType = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "WorkflowType", null);
    /** Column name WorkflowType */
    public static final String COLUMNNAME_WorkflowType = "WorkflowType";

	/**
	 * Set Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWorkingTime (int WorkingTime);

	/**
	 * Get Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWorkingTime();

    /** Column definition for WorkingTime */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_WorkingTime = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "WorkingTime", null);
    /** Column name WorkingTime */
    public static final String COLUMNNAME_WorkingTime = "WorkingTime";

	/**
	 * Set Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setYield (int Yield);

	/**
	 * Get Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getYield();

    /** Column definition for Yield */
    public static final org.adempiere.model.ModelColumn<I_AD_Workflow, Object> COLUMN_Yield = new org.adempiere.model.ModelColumn<I_AD_Workflow, Object>(I_AD_Workflow.class, "Yield", null);
    /** Column name Yield */
    public static final String COLUMNNAME_Yield = "Yield";
}
