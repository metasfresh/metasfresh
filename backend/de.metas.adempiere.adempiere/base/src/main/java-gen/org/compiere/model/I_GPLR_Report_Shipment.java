package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GPLR_Report_Shipment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GPLR_Report_Shipment 
{

	String Table_Name = "GPLR_Report_Shipment";

//	/** AD_Table_ID=542343 */
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

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_Created = new ModelColumn<>(I_GPLR_Report_Shipment.class, "Created", null);
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

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_DocumentNo = new ModelColumn<>(I_GPLR_Report_Shipment.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set GPLR Report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_ID (int GPLR_Report_ID);

	/**
	 * Get GPLR Report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_ID();

	org.compiere.model.I_GPLR_Report getGPLR_Report();

	void setGPLR_Report(org.compiere.model.I_GPLR_Report GPLR_Report);

	ModelColumn<I_GPLR_Report_Shipment, org.compiere.model.I_GPLR_Report> COLUMN_GPLR_Report_ID = new ModelColumn<>(I_GPLR_Report_Shipment.class, "GPLR_Report_ID", org.compiere.model.I_GPLR_Report.class);
	String COLUMNNAME_GPLR_Report_ID = "GPLR_Report_ID";

	/**
	 * Set GPLR Report - Shipment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_Shipment_ID (int GPLR_Report_Shipment_ID);

	/**
	 * Get GPLR Report - Shipment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_Shipment_ID();

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_GPLR_Report_Shipment_ID = new ModelColumn<>(I_GPLR_Report_Shipment.class, "GPLR_Report_Shipment_ID", null);
	String COLUMNNAME_GPLR_Report_Shipment_ID = "GPLR_Report_Shipment_ID";

	/**
	 * Set Incoterm Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncoterm_Code (@Nullable java.lang.String Incoterm_Code);

	/**
	 * Get Incoterm Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncoterm_Code();

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_Incoterm_Code = new ModelColumn<>(I_GPLR_Report_Shipment.class, "Incoterm_Code", null);
	String COLUMNNAME_Incoterm_Code = "Incoterm_Code";

	/**
	 * Set Incoterm Location.
	 * Location to be specified for commercial clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (@Nullable java.lang.String IncotermLocation);

	/**
	 * Get Incoterm Location.
	 * Location to be specified for commercial clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncotermLocation();

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_GPLR_Report_Shipment.class, "IncotermLocation", null);
	String COLUMNNAME_IncotermLocation = "IncotermLocation";

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

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_IsActive = new ModelColumn<>(I_GPLR_Report_Shipment.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_MovementDate = new ModelColumn<>(I_GPLR_Report_Shipment.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Shipping Info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShippingInfo (@Nullable java.lang.String ShippingInfo);

	/**
	 * Get Shipping Info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShippingInfo();

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_ShippingInfo = new ModelColumn<>(I_GPLR_Report_Shipment.class, "ShippingInfo", null);
	String COLUMNNAME_ShippingInfo = "ShippingInfo";

	/**
	 * Set Ship To BPartner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipTo_BPartnerName (@Nullable java.lang.String ShipTo_BPartnerName);

	/**
	 * Get Ship To BPartner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipTo_BPartnerName();

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_ShipTo_BPartnerName = new ModelColumn<>(I_GPLR_Report_Shipment.class, "ShipTo_BPartnerName", null);
	String COLUMNNAME_ShipTo_BPartnerName = "ShipTo_BPartnerName";

	/**
	 * Set Ship To BPartner Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipTo_BPartnerValue (@Nullable java.lang.String ShipTo_BPartnerValue);

	/**
	 * Get Ship To BPartner Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipTo_BPartnerValue();

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_ShipTo_BPartnerValue = new ModelColumn<>(I_GPLR_Report_Shipment.class, "ShipTo_BPartnerValue", null);
	String COLUMNNAME_ShipTo_BPartnerValue = "ShipTo_BPartnerValue";

	/**
	 * Set ShipTo Country Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipTo_CountryCode (@Nullable java.lang.String ShipTo_CountryCode);

	/**
	 * Get ShipTo Country Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipTo_CountryCode();

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_ShipTo_CountryCode = new ModelColumn<>(I_GPLR_Report_Shipment.class, "ShipTo_CountryCode", null);
	String COLUMNNAME_ShipTo_CountryCode = "ShipTo_CountryCode";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_Updated = new ModelColumn<>(I_GPLR_Report_Shipment.class, "Updated", null);
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

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_WarehouseName = new ModelColumn<>(I_GPLR_Report_Shipment.class, "WarehouseName", null);
	String COLUMNNAME_WarehouseName = "WarehouseName";

	/**
	 * Set Warehouse Key.
	 * Key of the Warehouse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarehouseValue (@Nullable java.lang.String WarehouseValue);

	/**
	 * Get Warehouse Key.
	 * Key of the Warehouse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWarehouseValue();

	ModelColumn<I_GPLR_Report_Shipment, Object> COLUMN_WarehouseValue = new ModelColumn<>(I_GPLR_Report_Shipment.class, "WarehouseValue", null);
	String COLUMNNAME_WarehouseValue = "WarehouseValue";
}
