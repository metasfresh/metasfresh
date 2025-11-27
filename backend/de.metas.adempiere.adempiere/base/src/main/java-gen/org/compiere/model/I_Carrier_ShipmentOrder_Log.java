package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for Carrier_ShipmentOrder_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Carrier_ShipmentOrder_Log 
{

	String Table_Name = "Carrier_ShipmentOrder_Log";

//	/** AD_Table_ID=542537 */
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
	 * Set Shipment Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCarrier_ShipmentOrder_ID (int Carrier_ShipmentOrder_ID);

	/**
	 * Get Shipment Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCarrier_ShipmentOrder_ID();

	ModelColumn<I_Carrier_ShipmentOrder_Log, org.compiere.model.I_Carrier_ShipmentOrder> COLUMN_Carrier_ShipmentOrder_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "Carrier_ShipmentOrder_ID", org.compiere.model.I_Carrier_ShipmentOrder.class);
	String COLUMNNAME_Carrier_ShipmentOrder_ID = "Carrier_ShipmentOrder_ID";

	/**
	 * Set Carrier ShipmentOrder Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_ShipmentOrder_Log_ID (int Carrier_ShipmentOrder_Log_ID);

	/**
	 * Get Carrier ShipmentOrder Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_ShipmentOrder_Log_ID();

	ModelColumn<I_Carrier_ShipmentOrder_Log, Object> COLUMN_Carrier_ShipmentOrder_Log_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "Carrier_ShipmentOrder_Log_ID", null);
	String COLUMNNAME_Carrier_ShipmentOrder_Log_ID = "Carrier_ShipmentOrder_Log_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Carrier_ShipmentOrder_Log, Object> COLUMN_Created = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "Created", null);
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
	 * Set Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDurationMillis (int DurationMillis);

	/**
	 * Get Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDurationMillis();

	ModelColumn<I_Carrier_ShipmentOrder_Log, Object> COLUMN_DurationMillis = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "DurationMillis", null);
	String COLUMNNAME_DurationMillis = "DurationMillis";

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

	ModelColumn<I_Carrier_ShipmentOrder_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsError (boolean IsError);

	/**
	 * Get Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isError();

	ModelColumn<I_Carrier_ShipmentOrder_Log, Object> COLUMN_IsError = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "IsError", null);
	String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Request ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequestID (java.lang.String RequestID);

	/**
	 * Get Request ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRequestID();

	ModelColumn<I_Carrier_ShipmentOrder_Log, Object> COLUMN_RequestID = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "RequestID", null);
	String COLUMNNAME_RequestID = "RequestID";

	/**
	 * Set Request Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestMessage (@Nullable java.lang.String RequestMessage);

	/**
	 * Get Request Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestMessage();

	ModelColumn<I_Carrier_ShipmentOrder_Log, Object> COLUMN_RequestMessage = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "RequestMessage", null);
	String COLUMNNAME_RequestMessage = "RequestMessage";

	/**
	 * Set Response Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setResponseMessage (@Nullable java.lang.String ResponseMessage);

	/**
	 * Get Response Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getResponseMessage();

	ModelColumn<I_Carrier_ShipmentOrder_Log, Object> COLUMN_ResponseMessage = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "ResponseMessage", null);
	String COLUMNNAME_ResponseMessage = "ResponseMessage";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Carrier_ShipmentOrder_Log, Object> COLUMN_Updated = new ModelColumn<>(I_Carrier_ShipmentOrder_Log.class, "Updated", null);
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
