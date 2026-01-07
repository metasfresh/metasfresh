package de.metas.shipper.gateway.dhl.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for Dhl_ShipmentOrder_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Dhl_ShipmentOrder_Log 
{

	String Table_Name = "Dhl_ShipmentOrder_Log";

//	/** AD_Table_ID=541426 */
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
	 * Set Config Summary.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setConfigSummary (@Nullable java.lang.String ConfigSummary);

	/**
	 * Get Config Summary.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getConfigSummary();

	ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_ConfigSummary = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "ConfigSummary", null);
	String COLUMNNAME_ConfigSummary = "ConfigSummary";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_Created = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "Created", null);
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
	 * Set Dhl ShipmentOrder Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDhl_ShipmentOrder_Log_ID (int Dhl_ShipmentOrder_Log_ID);

	/**
	 * Get Dhl ShipmentOrder Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDhl_ShipmentOrder_Log_ID();

	ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_Dhl_ShipmentOrder_Log_ID = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "Dhl_ShipmentOrder_Log_ID", null);
	String COLUMNNAME_Dhl_ShipmentOrder_Log_ID = "Dhl_ShipmentOrder_Log_ID";

	/**
	 * Set DHL Shipment Order Request.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_ShipmentOrderRequest_ID (int DHL_ShipmentOrderRequest_ID);

	/**
	 * Get DHL Shipment Order Request.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDHL_ShipmentOrderRequest_ID();

	@Nullable de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest getDHL_ShipmentOrderRequest();

	void setDHL_ShipmentOrderRequest(@Nullable de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest DHL_ShipmentOrderRequest);

	ModelColumn<I_Dhl_ShipmentOrder_Log, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest> COLUMN_DHL_ShipmentOrderRequest_ID = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "DHL_ShipmentOrderRequest_ID", de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class);
	String COLUMNNAME_DHL_ShipmentOrderRequest_ID = "DHL_ShipmentOrderRequest_ID";

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

	ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_DurationMillis = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "DurationMillis", null);
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

	ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "IsActive", null);
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

	ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_IsError = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "IsError", null);
	String COLUMNNAME_IsError = "IsError";

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

	ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_RequestMessage = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "RequestMessage", null);
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

	ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_ResponseMessage = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "ResponseMessage", null);
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

	ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_Updated = new ModelColumn<>(I_Dhl_ShipmentOrder_Log.class, "Updated", null);
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
