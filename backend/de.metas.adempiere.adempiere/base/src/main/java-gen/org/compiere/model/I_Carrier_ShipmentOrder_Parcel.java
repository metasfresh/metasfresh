package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for Carrier_ShipmentOrder_Parcel
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Carrier_ShipmentOrder_Parcel 
{

	String Table_Name = "Carrier_ShipmentOrder_Parcel";

//	/** AD_Table_ID=542535 */
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
	 * Set AWB.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setawb (@Nullable java.lang.String awb);

	/**
	 * Get AWB.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getawb();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_awb = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "awb", null);
	String COLUMNNAME_awb = "awb";

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

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, org.compiere.model.I_Carrier_ShipmentOrder> COLUMN_Carrier_ShipmentOrder_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "Carrier_ShipmentOrder_ID", org.compiere.model.I_Carrier_ShipmentOrder.class);
	String COLUMNNAME_Carrier_ShipmentOrder_ID = "Carrier_ShipmentOrder_ID";

	/**
	 * Set Shipment Order Parcel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_ShipmentOrder_Parcel_ID (int Carrier_ShipmentOrder_Parcel_ID);

	/**
	 * Get Shipment Order Parcel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_ShipmentOrder_Parcel_ID();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_Carrier_ShipmentOrder_Parcel_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "Carrier_ShipmentOrder_Parcel_ID", null);
	String COLUMNNAME_Carrier_ShipmentOrder_Parcel_ID = "Carrier_ShipmentOrder_Parcel_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_Created = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "Created", null);
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
	 * Set Height In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHeightInCm (int HeightInCm);

	/**
	 * Get Height In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getHeightInCm();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_HeightInCm = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "HeightInCm", null);
	String COLUMNNAME_HeightInCm = "HeightInCm";

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

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_IsActive = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Length In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLengthInCm (int LengthInCm);

	/**
	 * Get Length In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLengthInCm();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_LengthInCm = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "LengthInCm", null);
	String COLUMNNAME_LengthInCm = "LengthInCm";

	/**
	 * Set Package.
	 * Shipment Package
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Package_ID (int M_Package_ID);

	/**
	 * Get Package.
	 * Shipment Package
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Package_ID();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, org.compiere.model.I_M_Package> COLUMN_M_Package_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "M_Package_ID", org.compiere.model.I_M_Package.class);
	String COLUMNNAME_M_Package_ID = "M_Package_ID";

	/**
	 * Set Package Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackageDescription (@Nullable java.lang.String PackageDescription);

	/**
	 * Get Package Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPackageDescription();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_PackageDescription = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "PackageDescription", null);
	String COLUMNNAME_PackageDescription = "PackageDescription";

	/**
	 * Set PdfLabelData.
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPdfLabelData (@Nullable byte[] PdfLabelData);

	/**
	 * Get PdfLabelData.
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable byte[] getPdfLabelData();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_PdfLabelData = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "PdfLabelData", null);
	String COLUMNNAME_PdfLabelData = "PdfLabelData";

	/**
	 * Set Tracking URL.
	 * URL of the shipper to track shipments
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTrackingURL (@Nullable java.lang.String TrackingURL);

	/**
	 * Get Tracking URL.
	 * URL of the shipper to track shipments
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTrackingURL();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_TrackingURL = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "TrackingURL", null);
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

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_Updated = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "Updated", null);
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
	 * Set Weight In Kg.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWeightInKg (BigDecimal WeightInKg);

	/**
	 * Get Weight In Kg.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getWeightInKg();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_WeightInKg = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "WeightInKg", null);
	String COLUMNNAME_WeightInKg = "WeightInKg";

	/**
	 * Set Width In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWidthInCm (int WidthInCm);

	/**
	 * Get Width In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWidthInCm();

	ModelColumn<I_Carrier_ShipmentOrder_Parcel, Object> COLUMN_WidthInCm = new ModelColumn<>(I_Carrier_ShipmentOrder_Parcel.class, "WidthInCm", null);
	String COLUMNNAME_WidthInCm = "WidthInCm";
}
