package org.eevolution.model;


/** Generated Interface for PP_Order_Node
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Order_Node 
{

    /** TableName=PP_Order_Node */
    public static final String Table_Name = "PP_Order_Node";

    /** AD_Table_ID=53022 */
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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Node_ID();

	public org.compiere.model.I_AD_WF_Node getAD_WF_Node();

	public void setAD_WF_Node(org.compiere.model.I_AD_WF_Node AD_WF_Node);

    /** Column definition for AD_WF_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Node> COLUMN_AD_WF_Node_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Node>(I_PP_Order_Node.class, "AD_WF_Node_ID", org.compiere.model.I_AD_WF_Node.class);
    /** Column name AD_WF_Node_ID */
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Set Workflow Steps Template.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Node_Template_ID (int AD_WF_Node_Template_ID);

	/**
	 * Get Workflow Steps Template.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Node_Template_ID();

	public org.compiere.model.I_AD_WF_Node_Template getAD_WF_Node_Template();

	public void setAD_WF_Node_Template(org.compiere.model.I_AD_WF_Node_Template AD_WF_Node_Template);

    /** Column definition for AD_WF_Node_Template_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Node_Template> COLUMN_AD_WF_Node_Template_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Node_Template>(I_PP_Order_Node.class, "AD_WF_Node_Template_ID", org.compiere.model.I_AD_WF_Node_Template.class);
    /** Column name AD_WF_Node_Template_ID */
    public static final String COLUMNNAME_AD_WF_Node_Template_ID = "AD_WF_Node_Template_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Responsible> COLUMN_AD_WF_Responsible_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Responsible>(I_PP_Order_Node.class, "AD_WF_Responsible_ID", org.compiere.model.I_AD_WF_Responsible.class);
    /** Column name AD_WF_Responsible_ID */
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Workflow> COLUMN_AD_Workflow_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Workflow>(I_PP_Order_Node.class, "AD_Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Projektabschluss.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateFinish (java.sql.Timestamp DateFinish);

	/**
	 * Get Projektabschluss.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateFinish();

    /** Column definition for DateFinish */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DateFinish = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DateFinish", null);
    /** Column name DateFinish */
    public static final String COLUMNNAME_DateFinish = "DateFinish";

	/**
	 * Set DateFinishSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateFinishSchedule (java.sql.Timestamp DateFinishSchedule);

	/**
	 * Get DateFinishSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateFinishSchedule();

    /** Column definition for DateFinishSchedule */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DateFinishSchedule = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DateFinishSchedule", null);
    /** Column name DateFinishSchedule */
    public static final String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";

	/**
	 * Set DateStart.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateStart (java.sql.Timestamp DateStart);

	/**
	 * Get DateStart.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateStart();

    /** Column definition for DateStart */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DateStart = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DateStart", null);
    /** Column name DateStart */
    public static final String COLUMNNAME_DateStart = "DateStart";

	/**
	 * Set DateStartSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateStartSchedule (java.sql.Timestamp DateStartSchedule);

	/**
	 * Get DateStartSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateStartSchedule();

    /** Column definition for DateStartSchedule */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DateStartSchedule = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DateStartSchedule", null);
    /** Column name DateStartSchedule */
    public static final String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDuration (int Duration);

	/**
	 * Get Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDuration();

    /** Column definition for Duration */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Duration = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Duration", null);
    /** Column name Duration */
    public static final String COLUMNNAME_Duration = "Duration";

	/**
	 * Set Duration Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDurationReal (int DurationReal);

	/**
	 * Get Duration Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDurationReal();

    /** Column definition for DurationReal */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DurationReal = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DurationReal", null);
    /** Column name DurationReal */
    public static final String COLUMNNAME_DurationReal = "DurationReal";

	/**
	 * Set Duration Requiered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDurationRequiered (int DurationRequiered);

	/**
	 * Get Duration Requiered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDurationRequiered();

    /** Column definition for DurationRequiered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DurationRequiered = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DurationRequiered", null);
    /** Column name DurationRequiered */
    public static final String COLUMNNAME_DurationRequiered = "DurationRequiered";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_IsMilestone = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "IsMilestone", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_IsSubcontracting = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "IsSubcontracting", null);
    /** Column name IsSubcontracting */
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_MovingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "MovingTime", null);
    /** Column name MovingTime */
    public static final String COLUMNNAME_MovingTime = "MovingTime";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_OverlapUnits = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "OverlapUnits", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order>(I_PP_Order_Node.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Node_ID (int PP_Order_Node_ID);

	/**
	 * Get Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Node_ID();

    /** Column definition for PP_Order_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_PP_Order_Node_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "PP_Order_Node_ID", null);
    /** Column name PP_Order_Node_ID */
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	/**
	 * Set Manufacturing Order Workflow.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Workflow_ID (int PP_Order_Workflow_ID);

	/**
	 * Get Manufacturing Order Workflow.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Workflow_ID();

	public org.eevolution.model.I_PP_Order_Workflow getPP_Order_Workflow();

	public void setPP_Order_Workflow(org.eevolution.model.I_PP_Order_Workflow PP_Order_Workflow);

    /** Column definition for PP_Order_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order_Workflow> COLUMN_PP_Order_Workflow_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order_Workflow>(I_PP_Order_Node.class, "PP_Order_Workflow_ID", org.eevolution.model.I_PP_Order_Workflow.class);
    /** Column name PP_Order_Workflow_ID */
    public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";

	/**
	 * Set Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReject (java.math.BigDecimal QtyReject);

	/**
	 * Get Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReject();

    /** Column definition for QtyReject */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyReject = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QtyReject", null);
    /** Column name QtyReject */
    public static final String COLUMNNAME_QtyReject = "QtyReject";

	/**
	 * Set Menge angefragt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyRequiered (java.math.BigDecimal QtyRequiered);

	/**
	 * Get Menge angefragt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyRequiered();

    /** Column definition for QtyRequiered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyRequiered = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QtyRequiered", null);
    /** Column name QtyRequiered */
    public static final String COLUMNNAME_QtyRequiered = "QtyRequiered";

	/**
	 * Set QtyScrap.
	 * Scrap Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyScrap (java.math.BigDecimal QtyScrap);

	/**
	 * Get QtyScrap.
	 * Scrap Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyScrap();

    /** Column definition for QtyScrap */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyScrap = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QtyScrap", null);
    /** Column name QtyScrap */
    public static final String COLUMNNAME_QtyScrap = "QtyScrap";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QueuingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QueuingTime", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_S_Resource>(I_PP_Order_Node.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_SetupTime = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "SetupTime", null);
    /** Column name SetupTime */
    public static final String COLUMNNAME_SetupTime = "SetupTime";

	/**
	 * Set Setup Time Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSetupTimeReal (int SetupTimeReal);

	/**
	 * Get Setup Time Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSetupTimeReal();

    /** Column definition for SetupTimeReal */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_SetupTimeReal = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "SetupTimeReal", null);
    /** Column name SetupTimeReal */
    public static final String COLUMNNAME_SetupTimeReal = "SetupTimeReal";

	/**
	 * Set Setup Time Requiered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSetupTimeRequiered (int SetupTimeRequiered);

	/**
	 * Get Setup Time Requiered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSetupTimeRequiered();

    /** Column definition for SetupTimeRequiered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_SetupTimeRequiered = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "SetupTimeRequiered", null);
    /** Column name SetupTimeRequiered */
    public static final String COLUMNNAME_SetupTimeRequiered = "SetupTimeRequiered";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Value", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_WaitingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "WaitingTime", null);
    /** Column name WaitingTime */
    public static final String COLUMNNAME_WaitingTime = "WaitingTime";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Yield = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Yield", null);
    /** Column name Yield */
    public static final String COLUMNNAME_Yield = "Yield";
}
