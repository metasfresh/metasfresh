package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for Carrier_ShipmentOrder_Service
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Carrier_ShipmentOrder_Service 
{

	String Table_Name = "Carrier_ShipmentOrder_Service";

//	/** AD_Table_ID=542547 */
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
	 * Set Carrier Service Catalog.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_Service_ID (int Carrier_Service_ID);

	/**
	 * Get Carrier Service Catalog.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_Service_ID();

	ModelColumn<I_Carrier_ShipmentOrder_Service, org.compiere.model.I_Carrier_Service> COLUMN_Carrier_Service_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Service.class, "Carrier_Service_ID", org.compiere.model.I_Carrier_Service.class);
	String COLUMNNAME_Carrier_Service_ID = "Carrier_Service_ID";

	/**
	 * Set Shipment Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_ShipmentOrder_ID (int Carrier_ShipmentOrder_ID);

	/**
	 * Get Shipment Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_ShipmentOrder_ID();

	ModelColumn<I_Carrier_ShipmentOrder_Service, org.compiere.model.I_Carrier_ShipmentOrder> COLUMN_Carrier_ShipmentOrder_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Service.class, "Carrier_ShipmentOrder_ID", org.compiere.model.I_Carrier_ShipmentOrder.class);
	String COLUMNNAME_Carrier_ShipmentOrder_ID = "Carrier_ShipmentOrder_ID";

	/**
	 * Set Versandauftrag-Servicezuordnung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_ShipmentOrder_Service_ID (int Carrier_ShipmentOrder_Service_ID);

	/**
	 * Get Versandauftrag-Servicezuordnung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_ShipmentOrder_Service_ID();

	ModelColumn<I_Carrier_ShipmentOrder_Service, Object> COLUMN_Carrier_ShipmentOrder_Service_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Service.class, "Carrier_ShipmentOrder_Service_ID", null);
	String COLUMNNAME_Carrier_ShipmentOrder_Service_ID = "Carrier_ShipmentOrder_Service_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Carrier_ShipmentOrder_Service, Object> COLUMN_Created = new ModelColumn<>(I_Carrier_ShipmentOrder_Service.class, "Created", null);
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

	ModelColumn<I_Carrier_ShipmentOrder_Service, Object> COLUMN_IsActive = new ModelColumn<>(I_Carrier_ShipmentOrder_Service.class, "IsActive", null);
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

	ModelColumn<I_Carrier_ShipmentOrder_Service, Object> COLUMN_Updated = new ModelColumn<>(I_Carrier_ShipmentOrder_Service.class, "Updated", null);
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
