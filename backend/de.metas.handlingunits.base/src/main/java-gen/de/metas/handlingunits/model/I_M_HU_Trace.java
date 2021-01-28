package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Trace
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Trace 
{

	String Table_Name = "M_HU_Trace";

//	/** AD_Table_ID=540832 */
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
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_Trace, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Trace.class, "Created", null);
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

	ModelColumn<I_M_HU_Trace, Object> COLUMN_DocStatus = new ModelColumn<>(I_M_HU_Trace.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Event Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEventTime (java.sql.Timestamp EventTime);

	/**
	 * Get Event Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getEventTime();

	ModelColumn<I_M_HU_Trace, Object> COLUMN_EventTime = new ModelColumn<>(I_M_HU_Trace.class, "EventTime", null);
	String COLUMNNAME_EventTime = "EventTime";

	/**
	 * Set HU Trace Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHUTraceType (java.lang.String HUTraceType);

	/**
	 * Get HU Trace Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getHUTraceType();

	ModelColumn<I_M_HU_Trace, Object> COLUMN_HUTraceType = new ModelColumn<>(I_M_HU_Trace.class, "HUTraceType", null);
	String COLUMNNAME_HUTraceType = "HUTraceType";

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

	ModelColumn<I_M_HU_Trace, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Trace.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	de.metas.handlingunits.model.I_M_HU getM_HU();

	void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

	ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_Trace.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Rückverfolgbarkeit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Trace_ID (int M_HU_Trace_ID);

	/**
	 * Get Rückverfolgbarkeit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Trace_ID();

	ModelColumn<I_M_HU_Trace, Object> COLUMN_M_HU_Trace_ID = new ModelColumn<>(I_M_HU_Trace.class, "M_HU_Trace_ID", null);
	String COLUMNNAME_M_HU_Trace_ID = "M_HU_Trace_ID";

	/**
	 * Set HU Transaction Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_Trx_Line_ID (int M_HU_Trx_Line_ID);

	/**
	 * Get HU Transaction Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_Trx_Line_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Trx_Line getM_HU_Trx_Line();

	void setM_HU_Trx_Line(@Nullable de.metas.handlingunits.model.I_M_HU_Trx_Line M_HU_Trx_Line);

	ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU_Trx_Line> COLUMN_M_HU_Trx_Line_ID = new ModelColumn<>(I_M_HU_Trace.class, "M_HU_Trx_Line_ID", de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	String COLUMNNAME_M_HU_Trx_Line_ID = "M_HU_Trx_Line_ID";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_M_HU_Trace, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_M_HU_Trace.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Movement Document.
	 * Bewegung von Warenbestand
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Movement_ID (int M_Movement_ID);

	/**
	 * Get Movement Document.
	 * Bewegung von Warenbestand
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Movement_ID();

	@Nullable org.compiere.model.I_M_Movement getM_Movement();

	void setM_Movement(@Nullable org.compiere.model.I_M_Movement M_Movement);

	ModelColumn<I_M_HU_Trace, org.compiere.model.I_M_Movement> COLUMN_M_Movement_ID = new ModelColumn<>(I_M_HU_Trace.class, "M_Movement_ID", org.compiere.model.I_M_Movement.class);
	String COLUMNNAME_M_Movement_ID = "M_Movement_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_M_HU_Trace, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_M_HU_Trace.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID);

	/**
	 * Get Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Cost_Collector_ID();

	@Nullable org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector();

	void setPP_Cost_Collector(@Nullable org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector);

	ModelColumn<I_M_HU_Trace, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new ModelColumn<>(I_M_HU_Trace.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
	String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	@Nullable org.eevolution.model.I_PP_Order getPP_Order();

	void setPP_Order(@Nullable org.eevolution.model.I_PP_Order PP_Order);

	ModelColumn<I_M_HU_Trace, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_M_HU_Trace.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQty (BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_M_HU_Trace, Object> COLUMN_Qty = new ModelColumn<>(I_M_HU_Trace.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Trace, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Trace.class, "Updated", null);
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
	 * Set CU.
	 * Customer Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVHU_ID (int VHU_ID);

	/**
	 * Get CU.
	 * Customer Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getVHU_ID();

	de.metas.handlingunits.model.I_M_HU getVHU();

	void setVHU(de.metas.handlingunits.model.I_M_HU VHU);

	ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU> COLUMN_VHU_ID = new ModelColumn<>(I_M_HU_Trace.class, "VHU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_VHU_ID = "VHU_ID";

	/**
	 * Set Quell-HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVHU_Source_ID (int VHU_Source_ID);

	/**
	 * Get Quell-HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getVHU_Source_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getVHU_Source();

	void setVHU_Source(@Nullable de.metas.handlingunits.model.I_M_HU VHU_Source);

	ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU> COLUMN_VHU_Source_ID = new ModelColumn<>(I_M_HU_Trace.class, "VHU_Source_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_VHU_Source_ID = "VHU_Source_ID";

	/**
	 * Set CU (VHU) Gebindestatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVHUStatus (java.lang.String VHUStatus);

	/**
	 * Get CU (VHU) Gebindestatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getVHUStatus();

	ModelColumn<I_M_HU_Trace, Object> COLUMN_VHUStatus = new ModelColumn<>(I_M_HU_Trace.class, "VHUStatus", null);
	String COLUMNNAME_VHUStatus = "VHUStatus";
}
