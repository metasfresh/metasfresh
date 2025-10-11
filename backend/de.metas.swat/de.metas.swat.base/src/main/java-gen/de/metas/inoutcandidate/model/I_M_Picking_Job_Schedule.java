package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Job_Schedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Job_Schedule 
{

	String Table_Name = "M_Picking_Job_Schedule";

//	/** AD_Table_ID=542513 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Picking_Job_Schedule, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Job_Schedule.class, "Created", null);
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
	 * Set Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Workplace_ID (int C_Workplace_ID);

	/**
	 * Get Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Workplace_ID();

	ModelColumn<I_M_Picking_Job_Schedule, org.compiere.model.I_C_Workplace> COLUMN_C_Workplace_ID = new ModelColumn<>(I_M_Picking_Job_Schedule.class, "C_Workplace_ID", org.compiere.model.I_C_Workplace.class);
	String COLUMNNAME_C_Workplace_ID = "C_Workplace_ID";

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

	ModelColumn<I_M_Picking_Job_Schedule, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Job_Schedule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Picking Job Schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_Schedule_ID (int M_Picking_Job_Schedule_ID);

	/**
	 * Get Picking Job Schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_Schedule_ID();

	ModelColumn<I_M_Picking_Job_Schedule, Object> COLUMN_M_Picking_Job_Schedule_ID = new ModelColumn<>(I_M_Picking_Job_Schedule.class, "M_Picking_Job_Schedule_ID", null);
	String COLUMNNAME_M_Picking_Job_Schedule_ID = "M_Picking_Job_Schedule_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_M_Picking_Job_Schedule, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_M_Picking_Job_Schedule.class, "M_ShipmentSchedule_ID", de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

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

	ModelColumn<I_M_Picking_Job_Schedule, Object> COLUMN_Processed = new ModelColumn<>(I_M_Picking_Job_Schedule.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Qty To Pick.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyToPick (BigDecimal QtyToPick);

	/**
	 * Get Qty To Pick.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToPick();

	ModelColumn<I_M_Picking_Job_Schedule, Object> COLUMN_QtyToPick = new ModelColumn<>(I_M_Picking_Job_Schedule.class, "QtyToPick", null);
	String COLUMNNAME_QtyToPick = "QtyToPick";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Job_Schedule, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Job_Schedule.class, "Updated", null);
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
}
