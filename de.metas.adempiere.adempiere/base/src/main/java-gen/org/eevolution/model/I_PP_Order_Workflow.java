package org.eevolution.model;


/** Generated Interface for PP_Order_Workflow
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Order_Workflow 
{

    /** TableName=PP_Order_Workflow */
    public static final String Table_Name = "PP_Order_Workflow";

    /** AD_Table_ID=53029 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_Client>(I_PP_Order_Workflow.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_Org>(I_PP_Order_Workflow.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Workflow_ID();

	public org.compiere.model.I_AD_Workflow getAD_Workflow();

	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow);

    /** Column definition for AD_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_Workflow> COLUMN_AD_Workflow_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_Workflow>(I_PP_Order_Workflow.class, "AD_Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_User>(I_PP_Order_Workflow.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_Duration = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "Duration", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_DurationLimit = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "DurationLimit", null);
    /** Column name DurationLimit */
    public static final String COLUMNNAME_DurationLimit = "DurationLimit";

	/**
	 * Set Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDurationUnit (java.lang.String DurationUnit);

	/**
	 * Get Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDurationUnit();

    /** Column definition for DurationUnit */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_DurationUnit = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "DurationUnit", null);
    /** Column name DurationUnit */
    public static final String COLUMNNAME_DurationUnit = "DurationUnit";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "Help", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_MovingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "MovingTime", null);
    /** Column name MovingTime */
    public static final String COLUMNNAME_MovingTime = "MovingTime";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_OverlapUnits = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "OverlapUnits", null);
    /** Column name OverlapUnits */
    public static final String COLUMNNAME_OverlapUnits = "OverlapUnits";

	/**
	 * Set Produktionsauftrag.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Produktionsauftrag.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order();

	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

    /** Column definition for PP_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.eevolution.model.I_PP_Order>(I_PP_Order_Workflow.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Node_ID (int PP_Order_Node_ID);

	/**
	 * Get Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Node_ID();

	public org.eevolution.model.I_PP_Order_Node getPP_Order_Node();

	public void setPP_Order_Node(org.eevolution.model.I_PP_Order_Node PP_Order_Node);

    /** Column definition for PP_Order_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.eevolution.model.I_PP_Order_Node> COLUMN_PP_Order_Node_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.eevolution.model.I_PP_Order_Node>(I_PP_Order_Workflow.class, "PP_Order_Node_ID", org.eevolution.model.I_PP_Order_Node.class);
    /** Column name PP_Order_Node_ID */
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	/**
	 * Set Manufacturing Order Workflow.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Workflow_ID (int PP_Order_Workflow_ID);

	/**
	 * Get Manufacturing Order Workflow.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Workflow_ID();

    /** Column definition for PP_Order_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_PP_Order_Workflow_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "PP_Order_Workflow_ID", null);
    /** Column name PP_Order_Workflow_ID */
    public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_ProcessType = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "ProcessType", null);
    /** Column name ProcessType */
    public static final String COLUMNNAME_ProcessType = "ProcessType";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_QtyBatchSize = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "QtyBatchSize", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_QueuingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "QueuingTime", null);
    /** Column name QueuingTime */
    public static final String COLUMNNAME_QueuingTime = "QueuingTime";

	/**
	 * Set Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_Resource_ID();

	public org.compiere.model.I_S_Resource getS_Resource();

	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

    /** Column definition for S_Resource_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_S_Resource>(I_PP_Order_Workflow.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_SetupTime = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "SetupTime", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_UnitsCycles = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "UnitsCycles", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, org.compiere.model.I_AD_User>(I_PP_Order_Workflow.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_WaitingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "WaitingTime", null);
    /** Column name WaitingTime */
    public static final String COLUMNNAME_WaitingTime = "WaitingTime";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_WorkingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "WorkingTime", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object> COLUMN_Yield = new org.adempiere.model.ModelColumn<I_PP_Order_Workflow, Object>(I_PP_Order_Workflow.class, "Yield", null);
    /** Column name Yield */
    public static final String COLUMNNAME_Yield = "Yield";
}
