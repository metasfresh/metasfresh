package org.eevolution.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for PP_Order_Node
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Order_Node 
{

	String Table_Name = "PP_Order_Node";

//	/** AD_Table_ID=53022 */
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
	 * Set Start Node.
	 * Workflow Node, step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Start Node.
	 * Workflow Node, step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Node_ID();

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
	 * Set Workflow Responsible.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/**
	 * Get Workflow Responsible.
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
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Workflow_ID();

	String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_Created = new ModelColumn<>(I_PP_Order_Node.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFinish (@Nullable java.sql.Timestamp DateFinish);

	/**
	 * Get Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateFinish();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_DateFinish = new ModelColumn<>(I_PP_Order_Node.class, "DateFinish", null);
	String COLUMNNAME_DateFinish = "DateFinish";

	/**
	 * Set Date Finish Schedule.
	 * Scheduled Finish date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFinishSchedule (@Nullable java.sql.Timestamp DateFinishSchedule);

	/**
	 * Get Date Finish Schedule.
	 * Scheduled Finish date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateFinishSchedule();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_DateFinishSchedule = new ModelColumn<>(I_PP_Order_Node.class, "DateFinishSchedule", null);
	String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";

	/**
	 * Set Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateStart (@Nullable java.sql.Timestamp DateStart);

	/**
	 * Get Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateStart();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_DateStart = new ModelColumn<>(I_PP_Order_Node.class, "DateStart", null);
	String COLUMNNAME_DateStart = "DateStart";

	/**
	 * Set Date Start Schedule.
	 * Scheduled start date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateStartSchedule (@Nullable java.sql.Timestamp DateStartSchedule);

	/**
	 * Get Date Start Schedule.
	 * Scheduled start date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateStartSchedule();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_DateStartSchedule = new ModelColumn<>(I_PP_Order_Node.class, "DateStartSchedule", null);
	String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_Description = new ModelColumn<>(I_PP_Order_Node.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocStatus (@Nullable java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocStatus();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_DocStatus = new ModelColumn<>(I_PP_Order_Node.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDuration (int Duration);

	/**
	 * Get Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDuration();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_Duration = new ModelColumn<>(I_PP_Order_Node.class, "Duration", null);
	String COLUMNNAME_Duration = "Duration";

	/**
	 * Set Duration Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDurationReal (int DurationReal);

	/**
	 * Get Duration Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDurationReal();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_DurationReal = new ModelColumn<>(I_PP_Order_Node.class, "DurationReal", null);
	String COLUMNNAME_DurationReal = "DurationReal";

	/**
	 * Set Duration Required.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDurationRequiered (int DurationRequiered);

	/**
	 * Get Duration Required.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDurationRequiered();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_DurationRequiered = new ModelColumn<>(I_PP_Order_Node.class, "DurationRequiered", null);
	String COLUMNNAME_DurationRequiered = "DurationRequiered";

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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Order_Node.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_IsMilestone = new ModelColumn<>(I_PP_Order_Node.class, "IsMilestone", null);
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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_IsSubcontracting = new ModelColumn<>(I_PP_Order_Node.class, "IsSubcontracting", null);
	String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_MovingTime = new ModelColumn<>(I_PP_Order_Node.class, "MovingTime", null);
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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_Name = new ModelColumn<>(I_PP_Order_Node.class, "Name", null);
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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_OverlapUnits = new ModelColumn<>(I_PP_Order_Node.class, "OverlapUnits", null);
	String COLUMNNAME_OverlapUnits = "OverlapUnits";

	/**
	 * Set Manufacturing Activity Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Activity_Type (java.lang.String PP_Activity_Type);

	/**
	 * Get Manufacturing Activity Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPP_Activity_Type();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_PP_Activity_Type = new ModelColumn<>(I_PP_Order_Node.class, "PP_Activity_Type", null);
	String COLUMNNAME_PP_Activity_Type = "PP_Activity_Type";

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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_PP_AlwaysAvailableToUser = new ModelColumn<>(I_PP_Order_Node.class, "PP_AlwaysAvailableToUser", null);
	String COLUMNNAME_PP_AlwaysAvailableToUser = "PP_AlwaysAvailableToUser";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	org.eevolution.model.I_PP_Order getPP_Order();

	void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

	ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_PP_Order_Node.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Node_ID (int PP_Order_Node_ID);

	/**
	 * Get Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Node_ID();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_PP_Order_Node_ID = new ModelColumn<>(I_PP_Order_Node.class, "PP_Order_Node_ID", null);
	String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	/**
	 * Set Manufacturing Order Workflow.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Workflow_ID (int PP_Order_Workflow_ID);

	/**
	 * Get Manufacturing Order Workflow.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Workflow_ID();

	org.eevolution.model.I_PP_Order_Workflow getPP_Order_Workflow();

	void setPP_Order_Workflow(org.eevolution.model.I_PP_Order_Workflow PP_Order_Workflow);

	ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order_Workflow> COLUMN_PP_Order_Workflow_ID = new ModelColumn<>(I_PP_Order_Node.class, "PP_Order_Workflow_ID", org.eevolution.model.I_PP_Order_Workflow.class);
	String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";

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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_PP_UserInstructions = new ModelColumn<>(I_PP_Order_Node.class, "PP_UserInstructions", null);
	String COLUMNNAME_PP_UserInstructions = "PP_UserInstructions";

	/**
	 * Set Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDelivered (@Nullable BigDecimal QtyDelivered);

	/**
	 * Get Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDelivered();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyDelivered = new ModelColumn<>(I_PP_Order_Node.class, "QtyDelivered", null);
	String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReject (@Nullable BigDecimal QtyReject);

	/**
	 * Get Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReject();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyReject = new ModelColumn<>(I_PP_Order_Node.class, "QtyReject", null);
	String COLUMNNAME_QtyReject = "QtyReject";

	/**
	 * Set Qty Required.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyRequiered (@Nullable BigDecimal QtyRequiered);

	/**
	 * Get Qty Required.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyRequiered();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyRequiered = new ModelColumn<>(I_PP_Order_Node.class, "QtyRequiered", null);
	String COLUMNNAME_QtyRequiered = "QtyRequiered";

	/**
	 * Set Quantity Scrap %.
	 * Scrap % Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyScrap (@Nullable BigDecimal QtyScrap);

	/**
	 * Get Quantity Scrap %.
	 * Scrap % Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyScrap();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyScrap = new ModelColumn<>(I_PP_Order_Node.class, "QtyScrap", null);
	String COLUMNNAME_QtyScrap = "QtyScrap";

	/**
	 * Set Queuing Time.
	 * Queue time is the time a job waits at a work center before begin handled.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQueuingTime (int QueuingTime);

	/**
	 * Get Queuing Time.
	 * Queue time is the time a job waits at a work center before begin handled.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getQueuingTime();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_QueuingTime = new ModelColumn<>(I_PP_Order_Node.class, "QueuingTime", null);
	String COLUMNNAME_QueuingTime = "QueuingTime";

	/**
	 * Set Resource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Resource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	@Nullable org.compiere.model.I_S_Resource getS_Resource();

	void setS_Resource(@Nullable org.compiere.model.I_S_Resource S_Resource);

	ModelColumn<I_PP_Order_Node, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new ModelColumn<>(I_PP_Order_Node.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set Scanned QR Code.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScannedQRCode (@Nullable java.lang.String ScannedQRCode);

	/**
	 * Get Scanned QR Code.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getScannedQRCode();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_ScannedQRCode = new ModelColumn<>(I_PP_Order_Node.class, "ScannedQRCode", null);
	String COLUMNNAME_ScannedQRCode = "ScannedQRCode";

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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_SetupTime = new ModelColumn<>(I_PP_Order_Node.class, "SetupTime", null);
	String COLUMNNAME_SetupTime = "SetupTime";

	/**
	 * Set Setup Time Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSetupTimeReal (int SetupTimeReal);

	/**
	 * Get Setup Time Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSetupTimeReal();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_SetupTimeReal = new ModelColumn<>(I_PP_Order_Node.class, "SetupTimeReal", null);
	String COLUMNNAME_SetupTimeReal = "SetupTimeReal";

	/**
	 * Set Setup Time Required.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSetupTimeRequiered (int SetupTimeRequiered);

	/**
	 * Get Setup Time Required.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSetupTimeRequiered();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_SetupTimeRequiered = new ModelColumn<>(I_PP_Order_Node.class, "SetupTimeRequiered", null);
	String COLUMNNAME_SetupTimeRequiered = "SetupTimeRequiered";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Order_Node, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Order_Node.class, "Updated", null);
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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_Value = new ModelColumn<>(I_PP_Order_Node.class, "Value", null);
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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_WaitingTime = new ModelColumn<>(I_PP_Order_Node.class, "WaitingTime", null);
	String COLUMNNAME_WaitingTime = "WaitingTime";

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

	ModelColumn<I_PP_Order_Node, Object> COLUMN_Yield = new ModelColumn<>(I_PP_Order_Node.class, "Yield", null);
	String COLUMNNAME_Yield = "Yield";
}
