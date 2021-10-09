package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_ShipmentSchedule_ExportAudit_Item
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ShipmentSchedule_ExportAudit_Item 
{

	String Table_Name = "M_ShipmentSchedule_ExportAudit_Item";

//	/** AD_Table_ID=541504 */
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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
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

	ModelColumn<I_M_ShipmentSchedule_ExportAudit_Item, Object> COLUMN_Created = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit_Item.class, "Created", null);
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
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setExportSequenceNumber (@Nullable BigDecimal ExportSequenceNumber);

	/**
	 * Get Export sequence number.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getExportSequenceNumber();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit_Item, Object> COLUMN_ExportSequenceNumber = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit_Item.class, "ExportSequenceNumber", null);
	String COLUMNNAME_ExportSequenceNumber = "ExportSequenceNumber";

	/**
	 * Set Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExportStatus (@Nullable java.lang.String ExportStatus);

	/**
	 * Get Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExportStatus();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit_Item, Object> COLUMN_ExportStatus = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit_Item.class, "ExportStatus", null);
	String COLUMNNAME_ExportStatus = "ExportStatus";

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

	ModelColumn<I_M_ShipmentSchedule_ExportAudit_Item, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit_Item.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipment disposition export revision.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ExportAudit_ID (int M_ShipmentSchedule_ExportAudit_ID);

	/**
	 * Get Shipment disposition export revision.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ExportAudit_ID();

	@Nullable de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit getM_ShipmentSchedule_ExportAudit();

	void setM_ShipmentSchedule_ExportAudit(@Nullable de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit M_ShipmentSchedule_ExportAudit);

	ModelColumn<I_M_ShipmentSchedule_ExportAudit_Item, de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit> COLUMN_M_ShipmentSchedule_ExportAudit_ID = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit_Item.class, "M_ShipmentSchedule_ExportAudit_ID", de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit.class);
	String COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID = "M_ShipmentSchedule_ExportAudit_ID";

	/**
	 * Set Shipment disposition export revision item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ExportAudit_Item_ID (int M_ShipmentSchedule_ExportAudit_Item_ID);

	/**
	 * Get Shipment disposition export revision item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ExportAudit_Item_ID();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit_Item, Object> COLUMN_M_ShipmentSchedule_ExportAudit_Item_ID = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit_Item.class, "M_ShipmentSchedule_ExportAudit_Item_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ExportAudit_Item_ID = "M_ShipmentSchedule_ExportAudit_Item_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule();

	void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule);

	ModelColumn<I_M_ShipmentSchedule_ExportAudit_Item, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit_Item.class, "M_ShipmentSchedule_ID", de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set API Transaction key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setTransactionIdAPI (@Nullable java.lang.String TransactionIdAPI);

	/**
	 * Get API Transaction key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getTransactionIdAPI();

	ModelColumn<I_M_ShipmentSchedule_ExportAudit_Item, Object> COLUMN_TransactionIdAPI = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit_Item.class, "TransactionIdAPI", null);
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

	ModelColumn<I_M_ShipmentSchedule_ExportAudit_Item, Object> COLUMN_Updated = new ModelColumn<>(I_M_ShipmentSchedule_ExportAudit_Item.class, "Updated", null);
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
