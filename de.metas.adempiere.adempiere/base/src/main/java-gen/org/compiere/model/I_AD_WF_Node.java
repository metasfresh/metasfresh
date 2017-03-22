package org.compiere.model;


/** Generated Interface for AD_WF_Node
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_WF_Node 
{

    /** TableName=AD_WF_Node */
    public static final String Table_Name = "AD_WF_Node";

    /** AD_Table_ID=129 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Set Aktion.
	 * Indicates the Action to be performed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAction (java.lang.String Action);

	/**
	 * Get Aktion.
	 * Indicates the Action to be performed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAction();

    /** Column definition for Action */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Action = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Action", null);
    /** Column name Action */
    public static final String COLUMNNAME_Action = "Action";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Client>(I_AD_WF_Node.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Column>(I_AD_WF_Node.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Form_ID (int AD_Form_ID);

	/**
	 * Get Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Form_ID();

	public org.compiere.model.I_AD_Form getAD_Form();

	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form);

    /** Column definition for AD_Form_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Form>(I_AD_WF_Node.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
    /** Column name AD_Form_ID */
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set Bild.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Image_ID (int AD_Image_ID);

	/**
	 * Get Bild.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Image_ID();

	public org.compiere.model.I_AD_Image getAD_Image();

	public void setAD_Image(org.compiere.model.I_AD_Image AD_Image);

    /** Column definition for AD_Image_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Image> COLUMN_AD_Image_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Image>(I_AD_WF_Node.class, "AD_Image_ID", org.compiere.model.I_AD_Image.class);
    /** Column name AD_Image_ID */
    public static final String COLUMNNAME_AD_Image_ID = "AD_Image_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Org>(I_AD_WF_Node.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Process>(I_AD_WF_Node.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Task_ID (int AD_Task_ID);

	/**
	 * Get Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Task_ID();

	public org.compiere.model.I_AD_Task getAD_Task();

	public void setAD_Task(org.compiere.model.I_AD_Task AD_Task);

    /** Column definition for AD_Task_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Task> COLUMN_AD_Task_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Task>(I_AD_WF_Node.class, "AD_Task_ID", org.compiere.model.I_AD_Task.class);
    /** Column name AD_Task_ID */
    public static final String COLUMNNAME_AD_Task_ID = "AD_Task_ID";

	/**
	 * Set Workflow Block.
	 * Workflow Transaction Execution Block
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Block_ID (int AD_WF_Block_ID);

	/**
	 * Get Workflow Block.
	 * Workflow Transaction Execution Block
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Block_ID();

	public org.compiere.model.I_AD_WF_Block getAD_WF_Block();

	public void setAD_WF_Block(org.compiere.model.I_AD_WF_Block AD_WF_Block);

    /** Column definition for AD_WF_Block_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_WF_Block> COLUMN_AD_WF_Block_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_WF_Block>(I_AD_WF_Node.class, "AD_WF_Block_ID", org.compiere.model.I_AD_WF_Block.class);
    /** Column name AD_WF_Block_ID */
    public static final String COLUMNNAME_AD_WF_Block_ID = "AD_WF_Block_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Node_ID();

    /** Column definition for AD_WF_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_AD_WF_Node_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "AD_WF_Node_ID", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_WF_Responsible> COLUMN_AD_WF_Responsible_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_WF_Responsible>(I_AD_WF_Node.class, "AD_WF_Responsible_ID", org.compiere.model.I_AD_WF_Responsible.class);
    /** Column name AD_WF_Responsible_ID */
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/**
	 * Set Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Window_ID();

	public org.compiere.model.I_AD_Window getAD_Window();

	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

    /** Column definition for AD_Window_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Window>(I_AD_WF_Node.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Workflow_ID();

	public org.compiere.model.I_AD_Workflow getAD_Workflow();

	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow);

    /** Column definition for AD_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Workflow> COLUMN_AD_Workflow_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Workflow>(I_AD_WF_Node.class, "AD_Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAttributeName (java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeName();

    /** Column definition for AttributeName */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_AttributeName = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "AttributeName", null);
    /** Column name AttributeName */
    public static final String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Set Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAttributeValue (java.lang.String AttributeValue);

	/**
	 * Get Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeValue();

    /** Column definition for AttributeValue */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_AttributeValue = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "AttributeValue", null);
    /** Column name AttributeValue */
    public static final String COLUMNNAME_AttributeValue = "AttributeValue";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_C_BPartner>(I_AD_WF_Node.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Cost = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Cost", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_User>(I_AD_WF_Node.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Duration = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Duration", null);
    /** Column name Duration */
    public static final String COLUMNNAME_Duration = "Duration";

	/**
	 * Set Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDurationLimit (int DurationLimit);

	/**
	 * Get Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDurationLimit();

    /** Column definition for DurationLimit */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_DurationLimit = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "DurationLimit", null);
    /** Column name DurationLimit */
    public static final String COLUMNNAME_DurationLimit = "DurationLimit";

	/**
	 * Set Dynamic Priority Change.
	 * Change of priority when Activity is suspended waiting for user
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDynPriorityChange (java.math.BigDecimal DynPriorityChange);

	/**
	 * Get Dynamic Priority Change.
	 * Change of priority when Activity is suspended waiting for user
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDynPriorityChange();

    /** Column definition for DynPriorityChange */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_DynPriorityChange = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "DynPriorityChange", null);
    /** Column name DynPriorityChange */
    public static final String COLUMNNAME_DynPriorityChange = "DynPriorityChange";

	/**
	 * Set Dynamic Priority Unit.
	 * Change of priority when Activity is suspended waiting for user
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDynPriorityUnit (java.lang.String DynPriorityUnit);

	/**
	 * Get Dynamic Priority Unit.
	 * Change of priority when Activity is suspended waiting for user
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDynPriorityUnit();

    /** Column definition for DynPriorityUnit */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_DynPriorityUnit = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "DynPriorityUnit", null);
    /** Column name DynPriorityUnit */
    public static final String COLUMNNAME_DynPriorityUnit = "DynPriorityUnit";

	/**
	 * Set EMail.
	 * Electronic Mail Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail (java.lang.String EMail);

	/**
	 * Get EMail.
	 * Electronic Mail Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail();

    /** Column definition for EMail */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "EMail", null);
    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/**
	 * Set EMail Recipient.
	 * Recipient of the EMail
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMailRecipient (java.lang.String EMailRecipient);

	/**
	 * Get EMail Recipient.
	 * Recipient of the EMail
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMailRecipient();

    /** Column definition for EMailRecipient */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_EMailRecipient = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "EMailRecipient", null);
    /** Column name EMailRecipient */
    public static final String COLUMNNAME_EMailRecipient = "EMailRecipient";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Finish Mode.
	 * Workflow Activity Finish Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFinishMode (java.lang.String FinishMode);

	/**
	 * Get Finish Mode.
	 * Workflow Activity Finish Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFinishMode();

    /** Column definition for FinishMode */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_FinishMode = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "FinishMode", null);
    /** Column name FinishMode */
    public static final String COLUMNNAME_FinishMode = "FinishMode";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Help", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zentral verwaltet.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained);

	/**
	 * Get Zentral verwaltet.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCentrallyMaintained();

    /** Column definition for IsCentrallyMaintained */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_IsCentrallyMaintained = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "IsCentrallyMaintained", null);
    /** Column name IsCentrallyMaintained */
    public static final String COLUMNNAME_IsCentrallyMaintained = "IsCentrallyMaintained";

	/**
	 * Set Is Milestone.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMilestone (boolean IsMilestone);

	/**
	 * Get Is Milestone.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMilestone();

    /** Column definition for IsMilestone */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_IsMilestone = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "IsMilestone", null);
    /** Column name IsMilestone */
    public static final String COLUMNNAME_IsMilestone = "IsMilestone";

	/**
	 * Set Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSubcontracting (boolean IsSubcontracting);

	/**
	 * Get Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSubcontracting();

    /** Column definition for IsSubcontracting */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_IsSubcontracting = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "IsSubcontracting", null);
    /** Column name IsSubcontracting */
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

	/**
	 * Set Join Element.
	 * Semantics for multiple incoming Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setJoinElement (java.lang.String JoinElement);

	/**
	 * Get Join Element.
	 * Semantics for multiple incoming Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJoinElement();

    /** Column definition for JoinElement */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_JoinElement = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "JoinElement", null);
    /** Column name JoinElement */
    public static final String COLUMNNAME_JoinElement = "JoinElement";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_MovingTime = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "MovingTime", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOverlapUnits (int OverlapUnits);

	/**
	 * Get Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOverlapUnits();

    /** Column definition for OverlapUnits */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_OverlapUnits = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "OverlapUnits", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Priority = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Priority", null);
    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_QueuingTime = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "QueuingTime", null);
    /** Column name QueuingTime */
    public static final String COLUMNNAME_QueuingTime = "QueuingTime";

	/**
	 * Set EMail-Vorlage.
	 * Text templates for mailings
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_MailText_ID (int R_MailText_ID);

	/**
	 * Get EMail-Vorlage.
	 * Text templates for mailings
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_MailText_ID();

	public org.compiere.model.I_R_MailText getR_MailText();

	public void setR_MailText(org.compiere.model.I_R_MailText R_MailText);

    /** Column definition for R_MailText_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_R_MailText> COLUMN_R_MailText_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_R_MailText>(I_AD_WF_Node.class, "R_MailText_ID", org.compiere.model.I_R_MailText.class);
    /** Column name R_MailText_ID */
    public static final String COLUMNNAME_R_MailText_ID = "R_MailText_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_S_Resource>(I_AD_WF_Node.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_SetupTime = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "SetupTime", null);
    /** Column name SetupTime */
    public static final String COLUMNNAME_SetupTime = "SetupTime";

	/**
	 * Set Split Element.
	 * Semantics for multiple outgoing Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSplitElement (java.lang.String SplitElement);

	/**
	 * Get Split Element.
	 * Semantics for multiple outgoing Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSplitElement();

    /** Column definition for SplitElement */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_SplitElement = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "SplitElement", null);
    /** Column name SplitElement */
    public static final String COLUMNNAME_SplitElement = "SplitElement";

	/**
	 * Set Start Mode.
	 * Workflow Activity Start Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStartMode (java.lang.String StartMode);

	/**
	 * Get Start Mode.
	 * Workflow Activity Start Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStartMode();

    /** Column definition for StartMode */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_StartMode = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "StartMode", null);
    /** Column name StartMode */
    public static final String COLUMNNAME_StartMode = "StartMode";

	/**
	 * Set Subflow Execution.
	 * Mode how the sub-workflow is executed
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSubflowExecution (java.lang.String SubflowExecution);

	/**
	 * Get Subflow Execution.
	 * Mode how the sub-workflow is executed
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSubflowExecution();

    /** Column definition for SubflowExecution */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_SubflowExecution = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "SubflowExecution", null);
    /** Column name SubflowExecution */
    public static final String COLUMNNAME_SubflowExecution = "SubflowExecution";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_UnitsCycles = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "UnitsCycles", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_User>(I_AD_WF_Node.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "ValidTo", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_WaitingTime = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "WaitingTime", null);
    /** Column name WaitingTime */
    public static final String COLUMNNAME_WaitingTime = "WaitingTime";

	/**
	 * Set Wait Time.
	 * Time in minutes to wait (sleep)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWaitTime (int WaitTime);

	/**
	 * Get Wait Time.
	 * Time in minutes to wait (sleep)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getWaitTime();

    /** Column definition for WaitTime */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_WaitTime = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "WaitTime", null);
    /** Column name WaitTime */
    public static final String COLUMNNAME_WaitTime = "WaitTime";

	/**
	 * Set Workflow.
	 * Workflow or tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWorkflow_ID (int Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getWorkflow_ID();

	public org.compiere.model.I_AD_Workflow getWorkflow();

	public void setWorkflow(org.compiere.model.I_AD_Workflow Workflow);

    /** Column definition for Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Workflow> COLUMN_Workflow_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node, org.compiere.model.I_AD_Workflow>(I_AD_WF_Node.class, "Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name Workflow_ID */
    public static final String COLUMNNAME_Workflow_ID = "Workflow_ID";

	/**
	 * Set Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWorkingTime (int WorkingTime);

	/**
	 * Get Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getWorkingTime();

    /** Column definition for WorkingTime */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_WorkingTime = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "WorkingTime", null);
    /** Column name WorkingTime */
    public static final String COLUMNNAME_WorkingTime = "WorkingTime";

	/**
	 * Set X Position.
	 * Absolute X (horizontal) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setXPosition (int XPosition);

	/**
	 * Get X Position.
	 * Absolute X (horizontal) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getXPosition();

    /** Column definition for XPosition */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_XPosition = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "XPosition", null);
    /** Column name XPosition */
    public static final String COLUMNNAME_XPosition = "XPosition";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_Yield = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "Yield", null);
    /** Column name Yield */
    public static final String COLUMNNAME_Yield = "Yield";

	/**
	 * Set Y Position.
	 * Absolute Y (vertical) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setYPosition (int YPosition);

	/**
	 * Get Y Position.
	 * Absolute Y (vertical) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getYPosition();

    /** Column definition for YPosition */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node, Object> COLUMN_YPosition = new org.adempiere.model.ModelColumn<I_AD_WF_Node, Object>(I_AD_WF_Node.class, "YPosition", null);
    /** Column name YPosition */
    public static final String COLUMNNAME_YPosition = "YPosition";
}
