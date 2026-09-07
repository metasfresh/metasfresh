package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for I_DeliveryPlanning
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_I_DeliveryPlanning 
{

	String Table_Name = "I_DeliveryPlanning";

//	/** AD_Table_ID=542292 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Act Delivered Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualDischargeQuantity (@Nullable BigDecimal ActualDischargeQuantity);

	/**
	 * Get Act Delivered Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualDischargeQuantity();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_ActualDischargeQuantity = new ModelColumn<>(I_I_DeliveryPlanning.class, "ActualDischargeQuantity", null);
	String COLUMNNAME_ActualDischargeQuantity = "ActualDischargeQuantity";

	/**
	 * Set Act Load Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualLoadQty (@Nullable BigDecimal ActualLoadQty);

	/**
	 * Get Act Load Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualLoadQty();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_ActualLoadQty = new ModelColumn<>(I_I_DeliveryPlanning.class, "ActualLoadQty", null);
	String COLUMNNAME_ActualLoadQty = "ActualLoadQty";

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
	 * Set ATA.
	 * Actual Arrival Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setATA (@Nullable java.sql.Timestamp ATA);

	/**
	 * Get ATA.
	 * Actual Arrival Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getATA();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_ATA = new ModelColumn<>(I_I_DeliveryPlanning.class, "ATA", null);
	String COLUMNNAME_ATA = "ATA";

	/**
	 * Set ATD.
	 * Actual Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setATD (@Nullable java.sql.Timestamp ATD);

	/**
	 * Get ATD.
	 * Actual Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getATD();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_ATD = new ModelColumn<>(I_I_DeliveryPlanning.class, "ATD", null);
	String COLUMNNAME_ATD = "ATD";

	/**
	 * Set Batch.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBatch (@Nullable java.lang.String Batch);

	/**
	 * Get Batch.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBatch();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_Batch = new ModelColumn<>(I_I_DeliveryPlanning.class, "Batch", null);
	String COLUMNNAME_Batch = "Batch";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_ID();

	ModelColumn<I_I_DeliveryPlanning, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_I_DeliveryPlanning.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
	String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_Run_ID();

	ModelColumn<I_I_DeliveryPlanning, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new ModelColumn<>(I_I_DeliveryPlanning.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
	String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_Created = new ModelColumn<>(I_I_DeliveryPlanning.class, "Created", null);
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
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_DocumentNo = new ModelColumn<>(I_I_DeliveryPlanning.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set ETA.
	 * The ETA is used for shipment tracking, delivery planning, and scheduling of warehouse operations. The ETA is automatically synchronized from the Transport Order to the Purchase Order when the transport information is updated. Manual changes of the ETA in the Purchase Order are not allowed — it is always maintained in the corresponding Transport Order.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setETA (@Nullable java.sql.Timestamp ETA);

	/**
	 * Get ETA.
	 * The ETA is used for shipment tracking, delivery planning, and scheduling of warehouse operations. The ETA is automatically synchronized from the Transport Order to the Purchase Order when the transport information is updated. Manual changes of the ETA in the Purchase Order are not allowed — it is always maintained in the corresponding Transport Order.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getETA();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_ETA = new ModelColumn<>(I_I_DeliveryPlanning.class, "ETA", null);
	String COLUMNNAME_ETA = "ETA";

	/**
	 * Set ETD.
	 * Estimated Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setETD (@Nullable java.sql.Timestamp ETD);

	/**
	 * Get ETD.
	 * Estimated Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getETD();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_ETD = new ModelColumn<>(I_I_DeliveryPlanning.class, "ETD", null);
	String COLUMNNAME_ETD = "ETD";

	/**
	 * Set Import Delivery Planning Data.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_DeliveryPlanning_Data_ID (int I_DeliveryPlanning_Data_ID);

	/**
	 * Get Import Delivery Planning Data.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getI_DeliveryPlanning_Data_ID();

	ModelColumn<I_I_DeliveryPlanning, org.compiere.model.I_I_DeliveryPlanning_Data> COLUMN_I_DeliveryPlanning_Data_ID = new ModelColumn<>(I_I_DeliveryPlanning.class, "I_DeliveryPlanning_Data_ID", org.compiere.model.I_I_DeliveryPlanning_Data.class);
	String COLUMNNAME_I_DeliveryPlanning_Data_ID = "I_DeliveryPlanning_Data_ID";

	/**
	 * Set Imported Delivery Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_DeliveryPlanning_ID (int I_DeliveryPlanning_ID);

	/**
	 * Get Imported Delivery Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_DeliveryPlanning_ID();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_I_DeliveryPlanning_ID = new ModelColumn<>(I_I_DeliveryPlanning.class, "I_DeliveryPlanning_ID", null);
	String COLUMNNAME_I_DeliveryPlanning_ID = "I_DeliveryPlanning_ID";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_ErrorMsg (@Nullable java.lang.String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_ErrorMsg();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_I_ErrorMsg = new ModelColumn<>(I_I_DeliveryPlanning.class, "I_ErrorMsg", null);
	String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_IsImported (java.lang.String I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getI_IsImported();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_I_IsImported = new ModelColumn<>(I_I_DeliveryPlanning.class, "I_IsImported", null);
	String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getI_LineNo();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_I_LineNo = new ModelColumn<>(I_I_DeliveryPlanning.class, "I_LineNo", null);
	String COLUMNNAME_I_LineNo = "I_LineNo";

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

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_IsActive = new ModelColumn<>(I_I_DeliveryPlanning.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Delivery Planning.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Delivery_Planning_ID (int M_Delivery_Planning_ID);

	/**
	 * Get Delivery Planning.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Delivery_Planning_ID();

	ModelColumn<I_I_DeliveryPlanning, org.compiere.model.I_M_Delivery_Planning> COLUMN_M_Delivery_Planning_ID = new ModelColumn<>(I_I_DeliveryPlanning.class, "M_Delivery_Planning_ID", org.compiere.model.I_M_Delivery_Planning.class);
	String COLUMNNAME_M_Delivery_Planning_ID = "M_Delivery_Planning_ID";

	/**
	 * Set Origin Country.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOriginCountry (@Nullable java.lang.String OriginCountry);

	/**
	 * Get Origin Country.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOriginCountry();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_OriginCountry = new ModelColumn<>(I_I_DeliveryPlanning.class, "OriginCountry", null);
	String COLUMNNAME_OriginCountry = "OriginCountry";

	/**
	 * Set Plan Delivered Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlannedDischargeQuantity (@Nullable BigDecimal PlannedDischargeQuantity);

	/**
	 * Get Plan Delivered Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedDischargeQuantity();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_PlannedDischargeQuantity = new ModelColumn<>(I_I_DeliveryPlanning.class, "PlannedDischargeQuantity", null);
	String COLUMNNAME_PlannedDischargeQuantity = "PlannedDischargeQuantity";

	/**
	 * Set Plan Load Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlannedLoadedQuantity (@Nullable BigDecimal PlannedLoadedQuantity);

	/**
	 * Get Plan Load Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedLoadedQuantity();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_PlannedLoadedQuantity = new ModelColumn<>(I_I_DeliveryPlanning.class, "PlannedLoadedQuantity", null);
	String COLUMNNAME_PlannedLoadedQuantity = "PlannedLoadedQuantity";

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

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_Processed = new ModelColumn<>(I_I_DeliveryPlanning.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_Processing = new ModelColumn<>(I_I_DeliveryPlanning.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductName (@Nullable java.lang.String ProductName);

	/**
	 * Get Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductName();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_ProductName = new ModelColumn<>(I_I_DeliveryPlanning.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReleaseNo (@Nullable java.lang.String ReleaseNo);

	/**
	 * Get Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReleaseNo();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_ReleaseNo = new ModelColumn<>(I_I_DeliveryPlanning.class, "ReleaseNo", null);
	String COLUMNNAME_ReleaseNo = "ReleaseNo";

	/**
	 * Set Ship-to location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipToLocation_Name (@Nullable java.lang.String ShipToLocation_Name);

	/**
	 * Get Ship-to location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipToLocation_Name();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_ShipToLocation_Name = new ModelColumn<>(I_I_DeliveryPlanning.class, "ShipToLocation_Name", null);
	String COLUMNNAME_ShipToLocation_Name = "ShipToLocation_Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_Updated = new ModelColumn<>(I_I_DeliveryPlanning.class, "Updated", null);
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
	 * Set Warehouse Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarehouseName (@Nullable java.lang.String WarehouseName);

	/**
	 * Get Warehouse Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWarehouseName();

	ModelColumn<I_I_DeliveryPlanning, Object> COLUMN_WarehouseName = new ModelColumn<>(I_I_DeliveryPlanning.class, "WarehouseName", null);
	String COLUMNNAME_WarehouseName = "WarehouseName";
}
