package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_Package
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Package 
{

	String Table_Name = "M_Package";

//	/** AD_Table_ID=664 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Package, Object> COLUMN_Created = new ModelColumn<>(I_M_Package.class, "Created", null);
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
	 * Set Date received.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateReceived (@Nullable java.sql.Timestamp DateReceived);

	/**
	 * Get Date received.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateReceived();

	ModelColumn<I_M_Package, Object> COLUMN_DateReceived = new ModelColumn<>(I_M_Package.class, "DateReceived", null);
	String COLUMNNAME_DateReceived = "DateReceived";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_Package, Object> COLUMN_Description = new ModelColumn<>(I_M_Package.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_M_Package, Object> COLUMN_DocumentNo = new ModelColumn<>(I_M_Package.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

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

	ModelColumn<I_M_Package, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Package.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosed (boolean IsClosed);

	/**
	 * Get Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosed();

	ModelColumn<I_M_Package, Object> COLUMN_IsClosed = new ModelColumn<>(I_M_Package.class, "IsClosed", null);
	String COLUMNNAME_IsClosed = "IsClosed";

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

	ModelColumn<I_M_Package, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_M_Package.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Package.
	 * Shipment Package
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Package_ID (int M_Package_ID);

	/**
	 * Get Package.
	 * Shipment Package
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Package_ID();

	ModelColumn<I_M_Package, Object> COLUMN_M_Package_ID = new ModelColumn<>(I_M_Package.class, "M_Package_ID", null);
	String COLUMNNAME_M_Package_ID = "M_Package_ID";

	/**
	 * Set Package.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PackagingContainer_ID (int M_PackagingContainer_ID);

	/**
	 * Get Package.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PackagingContainer_ID();

	String COLUMNNAME_M_PackagingContainer_ID = "M_PackagingContainer_ID";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_M_Package, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_M_Package.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID);

	/**
	 * Get Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_Dest_ID();

	String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";

	/**
	 * Set Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackageNetTotal (@Nullable BigDecimal PackageNetTotal);

	/**
	 * Get Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPackageNetTotal();

	ModelColumn<I_M_Package, Object> COLUMN_PackageNetTotal = new ModelColumn<>(I_M_Package.class, "PackageNetTotal", null);
	String COLUMNNAME_PackageNetTotal = "PackageNetTotal";

	/**
	 * Set Weight.
	 * Weight of a package
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackageWeight (@Nullable BigDecimal PackageWeight);

	/**
	 * Get Weight.
	 * Weight of a package
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPackageWeight();

	ModelColumn<I_M_Package, Object> COLUMN_PackageWeight = new ModelColumn<>(I_M_Package.class, "PackageWeight", null);
	String COLUMNNAME_PackageWeight = "PackageWeight";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_M_Package, Object> COLUMN_POReference = new ModelColumn<>(I_M_Package.class, "POReference", null);
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

	ModelColumn<I_M_Package, Object> COLUMN_Processed = new ModelColumn<>(I_M_Package.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Info Received.
	 * Information of the receipt of the package (acknowledgement)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceivedInfo (@Nullable java.lang.String ReceivedInfo);

	/**
	 * Get Info Received.
	 * Information of the receipt of the package (acknowledgement)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceivedInfo();

	ModelColumn<I_M_Package, Object> COLUMN_ReceivedInfo = new ModelColumn<>(I_M_Package.class, "ReceivedInfo", null);
	String COLUMNNAME_ReceivedInfo = "ReceivedInfo";

	/**
	 * Set Date.
	 * Shipment Date/Time
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipDate (@Nullable java.sql.Timestamp ShipDate);

	/**
	 * Get Date.
	 * Shipment Date/Time
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getShipDate();

	ModelColumn<I_M_Package, Object> COLUMN_ShipDate = new ModelColumn<>(I_M_Package.class, "ShipDate", null);
	String COLUMNNAME_ShipDate = "ShipDate";

	/**
	 * Set Tracking Info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTrackingInfo (@Nullable java.lang.String TrackingInfo);

	/**
	 * Get Tracking Info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTrackingInfo();

	ModelColumn<I_M_Package, Object> COLUMN_TrackingInfo = new ModelColumn<>(I_M_Package.class, "TrackingInfo", null);
	String COLUMNNAME_TrackingInfo = "TrackingInfo";

	/**
	 * Set Tracking URL.
	 * URL of the shipper to track shipments
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTrackingURL (@Nullable java.lang.String TrackingURL);

	/**
	 * Get Tracking URL.
	 * URL of the shipper to track shipments
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTrackingURL();

	ModelColumn<I_M_Package, Object> COLUMN_TrackingURL = new ModelColumn<>(I_M_Package.class, "TrackingURL", null);
	String COLUMNNAME_TrackingURL = "TrackingURL";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Package, Object> COLUMN_Updated = new ModelColumn<>(I_M_Package.class, "Updated", null);
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
