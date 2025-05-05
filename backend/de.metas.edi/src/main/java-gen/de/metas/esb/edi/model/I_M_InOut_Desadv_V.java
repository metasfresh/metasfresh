package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_InOut_Desadv_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_InOut_Desadv_V 
{

	String Table_Name = "M_InOut_Desadv_V";

//	/** AD_Table_ID=542414 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_ID();

	String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_Created = new ModelColumn<>(I_M_InOut_Desadv_V.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (@Nullable java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateOrdered();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_DateOrdered = new ModelColumn<>(I_M_InOut_Desadv_V.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (@Nullable String DeliveryViaRule);

	/**
	 * Get Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDeliveryViaRule();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_M_InOut_Desadv_V.class, "DeliveryViaRule", null);
	String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDocumentNo();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_DocumentNo = new ModelColumn<>(I_M_InOut_Desadv_V.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Ship Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/**
	 * Get Ship Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_BPartner_ID();

	String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/**
	 * Set Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_Location_ID (int DropShip_Location_ID);

	/**
	 * Get Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_Location_ID();

	String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/**
	 * Set EDI export status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDI_ExportStatus (@Nullable String EDI_ExportStatus);

	/**
	 * Get EDI export status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getEDI_ExportStatus();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_EDI_ExportStatus = new ModelColumn<>(I_M_InOut_Desadv_V.class, "EDI_ExportStatus", null);
	String COLUMNNAME_EDI_ExportStatus = "EDI_ExportStatus";

	/**
	 * Set EDI Error Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDIErrorMsg (@Nullable String EDIErrorMsg);

	/**
	 * Get EDI Error Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getEDIErrorMsg();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_EDIErrorMsg = new ModelColumn<>(I_M_InOut_Desadv_V.class, "EDIErrorMsg", null);
	String COLUMNNAME_EDIErrorMsg = "EDIErrorMsg";

	/**
	 * Set Delivered %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFulfillmentPercent (@Nullable BigDecimal FulfillmentPercent);

	/**
	 * Get Delivered %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFulfillmentPercent();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_FulfillmentPercent = new ModelColumn<>(I_M_InOut_Desadv_V.class, "FulfillmentPercent", null);
	String COLUMNNAME_FulfillmentPercent = "FulfillmentPercent";

	/**
	 * Set Delivered % min.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFulfillmentPercentMin (@Nullable BigDecimal FulfillmentPercentMin);

	/**
	 * Get Delivered % min.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFulfillmentPercentMin();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_FulfillmentPercentMin = new ModelColumn<>(I_M_InOut_Desadv_V.class, "FulfillmentPercentMin", null);
	String COLUMNNAME_FulfillmentPercentMin = "FulfillmentPercentMin";

	/**
	 * Set unloading address.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOver_Location_ID (int HandOver_Location_ID);

	/**
	 * Get unloading address.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHandOver_Location_ID();

	String COLUMNNAME_HandOver_Location_ID = "HandOver_Location_ID";

	/**
	 * Set Handover partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOver_Partner_ID (int HandOver_Partner_ID);

	/**
	 * Get Handover partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHandOver_Partner_ID();

	String COLUMNNAME_HandOver_Partner_ID = "HandOver_Partner_ID";

	/**
	 * Set Invoicable Quantity per.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoicableQtyBasedOn (@Nullable String InvoicableQtyBasedOn);

	/**
	 * Get Invoicable Quantity per.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getInvoicableQtyBasedOn();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_M_InOut_Desadv_V.class, "InvoicableQtyBasedOn", null);
	String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

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

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_IsActive = new ModelColumn<>(I_M_InOut_Desadv_V.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set M_InOut_Desadv_ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOut_Desadv_ID (String M_InOut_Desadv_ID);

	/**
	 * Get M_InOut_Desadv_ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getM_InOut_Desadv_ID();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_M_InOut_Desadv_ID = new ModelColumn<>(I_M_InOut_Desadv_V.class, "M_InOut_Desadv_ID", null);
	String COLUMNNAME_M_InOut_Desadv_ID = "M_InOut_Desadv_ID";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_M_InOut_Desadv_V, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_M_InOut_Desadv_V.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMovementDate (@Nullable java.sql.Timestamp MovementDate);

	/**
	 * Get Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getMovementDate();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_MovementDate = new ModelColumn<>(I_M_InOut_Desadv_V.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getPOReference();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_POReference = new ModelColumn<>(I_M_InOut_Desadv_V.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

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

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_Processed = new ModelColumn<>(I_M_InOut_Desadv_V.class, "Processed", null);
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

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_Processing = new ModelColumn<>(I_M_InOut_Desadv_V.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set SumDeliveredInStockingUOM.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSumDeliveredInStockingUOM (@Nullable BigDecimal SumDeliveredInStockingUOM);

	/**
	 * Get SumDeliveredInStockingUOM.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSumDeliveredInStockingUOM();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_SumDeliveredInStockingUOM = new ModelColumn<>(I_M_InOut_Desadv_V.class, "SumDeliveredInStockingUOM", null);
	String COLUMNNAME_SumDeliveredInStockingUOM = "SumDeliveredInStockingUOM";

	/**
	 * Set SumOrderedInStockingUOM.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSumOrderedInStockingUOM (@Nullable BigDecimal SumOrderedInStockingUOM);

	/**
	 * Get SumOrderedInStockingUOM.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSumOrderedInStockingUOM();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_SumOrderedInStockingUOM = new ModelColumn<>(I_M_InOut_Desadv_V.class, "SumOrderedInStockingUOM", null);
	String COLUMNNAME_SumOrderedInStockingUOM = "SumOrderedInStockingUOM";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_Updated = new ModelColumn<>(I_M_InOut_Desadv_V.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set User 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserFlag (@Nullable String UserFlag);

	/**
	 * Get User 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserFlag();

	ModelColumn<I_M_InOut_Desadv_V, Object> COLUMN_UserFlag = new ModelColumn<>(I_M_InOut_Desadv_V.class, "UserFlag", null);
	String COLUMNNAME_UserFlag = "UserFlag";
}
