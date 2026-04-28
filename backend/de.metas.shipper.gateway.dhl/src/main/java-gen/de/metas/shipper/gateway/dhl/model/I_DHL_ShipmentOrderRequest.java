package de.metas.shipper.gateway.dhl.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for DHL_ShipmentOrderRequest
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DHL_ShipmentOrderRequest 
{

	String Table_Name = "DHL_ShipmentOrderRequest";

//	/** AD_Table_ID=541420 */
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

	ModelColumn<I_DHL_ShipmentOrderRequest, Object> COLUMN_Created = new ModelColumn<>(I_DHL_ShipmentOrderRequest.class, "Created", null);
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
	 * Set DHL Shipment Order Request.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDHL_ShipmentOrderRequest_ID (int DHL_ShipmentOrderRequest_ID);

	/**
	 * Get DHL Shipment Order Request.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDHL_ShipmentOrderRequest_ID();

	ModelColumn<I_DHL_ShipmentOrderRequest, Object> COLUMN_DHL_ShipmentOrderRequest_ID = new ModelColumn<>(I_DHL_ShipmentOrderRequest.class, "DHL_ShipmentOrderRequest_ID", null);
	String COLUMNNAME_DHL_ShipmentOrderRequest_ID = "DHL_ShipmentOrderRequest_ID";

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

	ModelColumn<I_DHL_ShipmentOrderRequest, Object> COLUMN_IsActive = new ModelColumn<>(I_DHL_ShipmentOrderRequest.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DHL_ShipmentOrderRequest, Object> COLUMN_Updated = new ModelColumn<>(I_DHL_ShipmentOrderRequest.class, "Updated", null);
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
