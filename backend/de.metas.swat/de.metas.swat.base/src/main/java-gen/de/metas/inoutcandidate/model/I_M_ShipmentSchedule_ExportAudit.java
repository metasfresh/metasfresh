package de.metas.inoutcandidate.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_ShipmentSchedule_ExportAudit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ShipmentSchedule_ExportAudit 
{

	String Table_Name = "M_ShipmentSchedule_ExportAudit";

//	/** AD_Table_ID=541543 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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

	ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_Created = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit.class, "Created", null);
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
	 * Set Export sequence number.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExportSequenceNumber (int ExportSequenceNumber);

	/**
	 * Get Export sequence number.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExportSequenceNumber();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_ExportSequenceNumber = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit.class, "ExportSequenceNumber", null);
	String COLUMNNAME_ExportSequenceNumber = "ExportSequenceNumber";

	/**
	 * Set Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportStatus (java.lang.String ExportStatus);

	/**
	 * Get Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExportStatus();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_ExportStatus = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit.class, "ExportStatus", null);
	String COLUMNNAME_ExportStatus = "ExportStatus";

	/**
	 * Set Forwarded data.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setForwardedData (@Nullable java.lang.String ForwardedData);

	/**
	 * Get Forwarded data.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getForwardedData();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_ForwardedData = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit.class, "ForwardedData", null);
	String COLUMNNAME_ForwardedData = "ForwardedData";

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

	ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipment disposition export revision.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ExportAudit_ID (int M_ShipmentSchedule_ExportAudit_ID);

	/**
	 * Get Shipment disposition export revision.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ExportAudit_ID();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_M_ShipmentSchedule_ExportAudit_ID = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit.class, "M_ShipmentSchedule_ExportAudit_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID = "M_ShipmentSchedule_ExportAudit_ID";

	/**
	 * Set API Transaction key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTransactionIdAPI (@Nullable java.lang.String TransactionIdAPI);

	/**
	 * Get API Transaction key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTransactionIdAPI();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_TransactionIdAPI = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit.class, "TransactionIdAPI", null);
	String COLUMNNAME_TransactionIdAPI = "TransactionIdAPI";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_Updated = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit.class, "Updated", null);
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
