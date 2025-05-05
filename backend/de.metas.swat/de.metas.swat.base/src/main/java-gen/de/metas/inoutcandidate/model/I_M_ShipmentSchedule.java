package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_ShipmentSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ShipmentSchedule 
{

	String Table_Name = "M_ShipmentSchedule";

//	/** AD_Table_ID=500221 */
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
	 * Set Inputsource.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_InputDataSource_ID (int AD_InputDataSource_ID);

	/**
	 * Get Inputsource.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_InputDataSource_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_AD_InputDataSource_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "AD_InputDataSource_ID", null);
	String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";

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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set User/ Contact override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_Override_ID (int AD_User_Override_ID);

	/**
	 * Get User/ Contact override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_Override_ID();

	String COLUMNNAME_AD_User_Override_ID = "AD_User_Override_ID";

	/**
	 * Set Consolidate Shipments allowed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAllowConsolidateInOut (boolean AllowConsolidateInOut);

	/**
	 * Get Consolidate Shipments allowed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isAllowConsolidateInOut();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_AllowConsolidateInOut = new ModelColumn<>(I_M_ShipmentSchedule.class, "AllowConsolidateInOut", null);
	String COLUMNNAME_AllowConsolidateInOut = "AllowConsolidateInOut";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_ID();

	String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Rechnungsstandort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_Value_ID (int Bill_Location_Value_ID);

	/**
	 * Get Rechnungsstandort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_Value_ID();

	@Nullable org.compiere.model.I_C_Location getBill_Location_Value();

	void setBill_Location_Value(@Nullable org.compiere.model.I_C_Location Bill_Location_Value);

	ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_Location> COLUMN_Bill_Location_Value_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "Bill_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_Bill_Location_Value_ID = "Bill_Location_Value_ID";

	/**
	 * Set Bill Contact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_User_ID (int Bill_User_ID);

	/**
	 * Get Bill Contact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_User_ID();

	String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

	/**
	 * Set Blocked Business Partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setBlockedBPartner (boolean BlockedBPartner);

	/**
	 * Get Blocked Business Partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isBlockedBPartner();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_BlockedBPartner = new ModelColumn<>(I_M_ShipmentSchedule.class, "BlockedBPartner", null);
	String COLUMNNAME_BlockedBPartner = "BlockedBPartner";

	/**
	 * Set Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBPartnerAddress (java.lang.String BPartnerAddress);

	/**
	 * Get Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPartnerAddress();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_BPartnerAddress = new ModelColumn<>(I_M_ShipmentSchedule.class, "BPartnerAddress", null);
	String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/**
	 * Set User address note.
	 * Bezeichnet die letztendlich verwendete Lieferanschrift
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerAddress_Override (@Nullable java.lang.String BPartnerAddress_Override);

	/**
	 * Get User address note.
	 * Bezeichnet die letztendlich verwendete Lieferanschrift
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerAddress_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_BPartnerAddress_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "BPartnerAddress_Override", null);
	String COLUMNNAME_BPartnerAddress_Override = "BPartnerAddress_Override";

	/**
	 * Set Can be exported from.
	 * Timestamp from which onwards the record may be exported
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCanBeExportedFrom (@Nullable java.sql.Timestamp CanBeExportedFrom);

	/**
	 * Get Can be exported from.
	 * Timestamp from which onwards the record may be exported
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCanBeExportedFrom();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_CanBeExportedFrom = new ModelColumn<>(I_M_ShipmentSchedule.class, "CanBeExportedFrom", null);
	String COLUMNNAME_CanBeExportedFrom = "CanBeExportedFrom";

	/**
	 * Set Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Async_Batch_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "C_Async_Batch_ID", null);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCatch_UOM_ID (int Catch_UOM_ID);

	/**
	 * Get Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCatch_UOM_ID();

	String COLUMNNAME_Catch_UOM_ID = "Catch_UOM_ID";

	/**
	 * Set Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner2_ID (int C_BPartner2_ID);

	/**
	 * Get Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner2_ID();

	String COLUMNNAME_C_BPartner2_ID = "C_BPartner2_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Standort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_Value_ID (int C_BPartner_Location_Value_ID);

	/**
	 * Get Standort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_Value_ID();

	@Nullable org.compiere.model.I_C_Location getC_BPartner_Location_Value();

	void setC_BPartner_Location_Value(@Nullable org.compiere.model.I_C_Location C_BPartner_Location_Value);

	ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_Location> COLUMN_C_BPartner_Location_Value_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "C_BPartner_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_BPartner_Location_Value_ID = "C_BPartner_Location_Value_ID";

	/**
	 * Set Alt. Business Partner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Override_ID (int C_BPartner_Override_ID);

	/**
	 * Get Alt. Business Partner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Override_ID();

	String COLUMNNAME_C_BPartner_Override_ID = "C_BPartner_Override_ID";

	/**
	 * Set C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID);

	/**
	 * Get C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Vendor_ID();

	String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

	/**
	 * Set Location override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID);

	/**
	 * Get Location override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_Location_Override_ID();

	String COLUMNNAME_C_BP_Location_Override_ID = "C_BP_Location_Override_ID";

	/**
	 * Set Standort abw..
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_Location_Override_Value_ID (int C_BP_Location_Override_Value_ID);

	/**
	 * Get Standort abw..
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_Location_Override_Value_ID();

	@Nullable org.compiere.model.I_C_Location getC_BP_Location_Override_Value();

	void setC_BP_Location_Override_Value(@Nullable org.compiere.model.I_C_Location C_BP_Location_Override_Value);

	ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_Location> COLUMN_C_BP_Location_Override_Value_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "C_BP_Location_Override_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_BP_Location_Override_Value_ID = "C_BP_Location_Override_Value_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Created = new ModelColumn<>(I_M_ShipmentSchedule.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DateOrdered = new ModelColumn<>(I_M_ShipmentSchedule.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Shipmentdate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryDate (@Nullable java.sql.Timestamp DeliveryDate);

	/**
	 * Get Shipmentdate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDeliveryDate();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryDate = new ModelColumn<>(I_M_ShipmentSchedule.class, "DeliveryDate", null);
	String COLUMNNAME_DeliveryDate = "DeliveryDate";

	/**
	 * Set Shipment Date act..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDeliveryDate_Effective (@Nullable java.sql.Timestamp DeliveryDate_Effective);

	/**
	 * Get Shipment Date act..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.sql.Timestamp getDeliveryDate_Effective();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryDate_Effective = new ModelColumn<>(I_M_ShipmentSchedule.class, "DeliveryDate_Effective", null);
	String COLUMNNAME_DeliveryDate_Effective = "DeliveryDate_Effective";

	/**
	 * Set Alt. Deliverydate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryDate_Override (@Nullable java.sql.Timestamp DeliveryDate_Override);

	/**
	 * Get Alt. Deliverydate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDeliveryDate_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryDate_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "DeliveryDate_Override", null);
	String COLUMNNAME_DeliveryDate_Override = "DeliveryDate_Override";

	/**
	 * Set Delivery Rule.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Delivery Rule.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryRule();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryRule = new ModelColumn<>(I_M_ShipmentSchedule.class, "DeliveryRule", null);
	String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Alt. Deliverymethod.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule_Override (@Nullable java.lang.String DeliveryRule_Override);

	/**
	 * Get Alt. Deliverymethod.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryRule_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryRule_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "DeliveryRule_Override", null);
	String COLUMNNAME_DeliveryRule_Override = "DeliveryRule_Override";

	/**
	 * Set Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryViaRule();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_M_ShipmentSchedule.class, "DeliveryViaRule", null);
	String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Delivery via Rule override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule_Override (@Nullable java.lang.String DeliveryViaRule_Override);

	/**
	 * Get Delivery via Rule override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryViaRule_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryViaRule_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "DeliveryViaRule_Override", null);
	String COLUMNNAME_DeliveryViaRule_Override = "DeliveryViaRule_Override";

	/**
	 * Set Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocSubType (@Nullable java.lang.String DocSubType);

	/**
	 * Get Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocSubType();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DocSubType = new ModelColumn<>(I_M_ShipmentSchedule.class, "DocSubType", null);
	String COLUMNNAME_DocSubType = "DocSubType";

	/**
	 * Set Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportStatus (java.lang.String ExportStatus);

	/**
	 * Get Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExportStatus();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_ExportStatus = new ModelColumn<>(I_M_ShipmentSchedule.class, "ExportStatus", null);
	String COLUMNNAME_ExportStatus = "ExportStatus";

	/**
	 * Set Header  merge characteristic.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHeaderAggregationKey (@Nullable java.lang.String HeaderAggregationKey);

	/**
	 * Get Header  merge characteristic.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHeaderAggregationKey();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_HeaderAggregationKey = new ModelColumn<>(I_M_ShipmentSchedule.class, "HeaderAggregationKey", null);
	String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

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

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ShipmentSchedule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Address override.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBPartnerAddress_Override (boolean IsBPartnerAddress_Override);

	/**
	 * Get Address override.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBPartnerAddress_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsBPartnerAddress_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "IsBPartnerAddress_Override", null);
	String COLUMNNAME_IsBPartnerAddress_Override = "IsBPartnerAddress_Override";

	/**
	 * Set Catch Weight.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCatchWeight (boolean IsCatchWeight);

	/**
	 * Get Catch Weight.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCatchWeight();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsCatchWeight = new ModelColumn<>(I_M_ShipmentSchedule.class, "IsCatchWeight", null);
	String COLUMNNAME_IsCatchWeight = "IsCatchWeight";

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

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsClosed = new ModelColumn<>(I_M_ShipmentSchedule.class, "IsClosed", null);
	String COLUMNNAME_IsClosed = "IsClosed";

	/**
	 * Set Delivery Stopped.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDeliveryStop (boolean IsDeliveryStop);

	/**
	 * Get Delivery Stopped.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDeliveryStop();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsDeliveryStop = new ModelColumn<>(I_M_ShipmentSchedule.class, "IsDeliveryStop", null);
	String COLUMNNAME_IsDeliveryStop = "IsDeliveryStop";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisplayed();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsDisplayed = new ModelColumn<>(I_M_ShipmentSchedule.class, "IsDisplayed", null);
	String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set Different shipping address.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Different shipping address.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDropShip();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsDropShip = new ModelColumn<>(I_M_ShipmentSchedule.class, "IsDropShip", null);
	String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set EDI DESADV Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsEdiDesadvRecipient (boolean IsEdiDesadvRecipient);

	/**
	 * Get EDI DESADV Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isEdiDesadvRecipient();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsEdiDesadvRecipient = new ModelColumn<>(I_M_ShipmentSchedule.class, "IsEdiDesadvRecipient", null);
	String COLUMNNAME_IsEdiDesadvRecipient = "IsEdiDesadvRecipient";

	/**
	 * Set Recompute.
	 * Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsToRecompute (boolean IsToRecompute);

	/**
	 * Get Recompute.
	 * Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isToRecompute();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsToRecompute = new ModelColumn<>(I_M_ShipmentSchedule.class, "IsToRecompute", null);
	String COLUMNNAME_IsToRecompute = "IsToRecompute";

	/**
	 * Set Line Net Amount.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineNetAmt (@Nullable BigDecimal LineNetAmt);

	/**
	 * Get Line Net Amount.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getLineNetAmt();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_LineNetAmt = new ModelColumn<>(I_M_ShipmentSchedule.class, "LineNetAmt", null);
	String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Packvorschrift-Produkt Zuordnung (berechnet).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_Calculated_ID (int M_HU_PI_Item_Product_Calculated_ID);

	/**
	 * Get Packvorschrift-Produkt Zuordnung (berechnet).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_Calculated_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_M_HU_PI_Item_Product_Calculated_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_HU_PI_Item_Product_Calculated_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID = "M_HU_PI_Item_Product_Calculated_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_M_HU_PI_Item_Product_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_HU_PI_Item_Product_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Packing Instruction Override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_Override_ID (int M_HU_PI_Item_Product_Override_ID);

	/**
	 * Get Packing Instruction Override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_Override_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_M_HU_PI_Item_Product_Override_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_HU_PI_Item_Product_Override_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_Override_ID = "M_HU_PI_Item_Product_Override_ID";

	/**
	 * Set Inoutline Handler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_IolCandHandler_ID (int M_IolCandHandler_ID);

	/**
	 * Get Inoutline Handler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_IolCandHandler_ID();

	@Nullable de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler();

	void setM_IolCandHandler(@Nullable de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler);

	ModelColumn<I_M_ShipmentSchedule, de.metas.inoutcandidate.model.I_M_IolCandHandler> COLUMN_M_IolCandHandler_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_IolCandHandler_ID", de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	String COLUMNNAME_M_IolCandHandler_ID = "M_IolCandHandler_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Shipment Constraint.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipment_Constraint_ID (int M_Shipment_Constraint_ID);

	/**
	 * Get Shipment Constraint.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipment_Constraint_ID();

	@Nullable de.metas.inoutcandidate.model.I_M_Shipment_Constraint getM_Shipment_Constraint();

	void setM_Shipment_Constraint(@Nullable de.metas.inoutcandidate.model.I_M_Shipment_Constraint M_Shipment_Constraint);

	ModelColumn<I_M_ShipmentSchedule, de.metas.inoutcandidate.model.I_M_Shipment_Constraint> COLUMN_M_Shipment_Constraint_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_Shipment_Constraint_ID", de.metas.inoutcandidate.model.I_M_Shipment_Constraint.class);
	String COLUMNNAME_M_Shipment_Constraint_ID = "M_Shipment_Constraint_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	@Nullable org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(@Nullable org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Tour.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Tour_ID (int M_Tour_ID);

	/**
	 * Get Tour.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Tour_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_M_Tour_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "M_Tour_ID", null);
	String COLUMNNAME_M_Tour_ID = "M_Tour_ID";

	/**
	 * Set Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID);

	/**
	 * Get Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_Warehouse_Dest_ID();

	String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Alt. Warehouse.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_Override_ID (int M_Warehouse_Override_ID);

	/**
	 * Get Alt. Warehouse.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_Override_ID();

	String COLUMNNAME_M_Warehouse_Override_ID = "M_Warehouse_Override_ID";

	/**
	 * Set No. of purchase order lines w/ same reference.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNrOfOLCandsWithSamePOReference (int NrOfOLCandsWithSamePOReference);

	/**
	 * Get No. of purchase order lines w/ same reference.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getNrOfOLCandsWithSamePOReference();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_NrOfOLCandsWithSamePOReference = new ModelColumn<>(I_M_ShipmentSchedule.class, "NrOfOLCandsWithSamePOReference", null);
	String COLUMNNAME_NrOfOLCandsWithSamePOReference = "NrOfOLCandsWithSamePOReference";

	/**
	 * Set Physical Clearance Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPhysicalClearanceDate (@Nullable java.sql.Timestamp PhysicalClearanceDate);

	/**
	 * Get Physical Clearance Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPhysicalClearanceDate();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PhysicalClearanceDate = new ModelColumn<>(I_M_ShipmentSchedule.class, "PhysicalClearanceDate", null);
	String COLUMNNAME_PhysicalClearanceDate = "PhysicalClearanceDate";

	/**
	 * Set Pick from Manufacturing Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickFrom_Order_ID (int PickFrom_Order_ID);

	/**
	 * Get Pick from Manufacturing Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPickFrom_Order_ID();

	@Nullable org.eevolution.model.I_PP_Order getPickFrom_Order();

	void setPickFrom_Order(@Nullable org.eevolution.model.I_PP_Order PickFrom_Order);

	ModelColumn<I_M_ShipmentSchedule, org.eevolution.model.I_PP_Order> COLUMN_PickFrom_Order_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "PickFrom_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PickFrom_Order_ID = "PickFrom_Order_ID";

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

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_POReference = new ModelColumn<>(I_M_ShipmentSchedule.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Date ready.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationDate (@Nullable java.sql.Timestamp PreparationDate);

	/**
	 * Get Date ready.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationDate();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PreparationDate = new ModelColumn<>(I_M_ShipmentSchedule.class, "PreparationDate", null);
	String COLUMNNAME_PreparationDate = "PreparationDate";

	/**
	 * Set Date ready act..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPreparationDate_Effective (@Nullable java.sql.Timestamp PreparationDate_Effective);

	/**
	 * Get Date ready act..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getPreparationDate_Effective();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PreparationDate_Effective = new ModelColumn<>(I_M_ShipmentSchedule.class, "PreparationDate_Effective", null);
	String COLUMNNAME_PreparationDate_Effective = "PreparationDate_Effective";

	/**
	 * Set Alt Date ready.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationDate_Override (@Nullable java.sql.Timestamp PreparationDate_Override);

	/**
	 * Get Alt Date ready.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationDate_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PreparationDate_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "PreparationDate_Override", null);
	String COLUMNNAME_PreparationDate_Override = "PreparationDate_Override";

	/**
	 * Set Priority.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriorityRule (@Nullable java.lang.String PriorityRule);

	/**
	 * Get Priority.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPriorityRule();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PriorityRule = new ModelColumn<>(I_M_ShipmentSchedule.class, "PriorityRule", null);
	String COLUMNNAME_PriorityRule = "PriorityRule";

	/**
	 * Set Priority override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriorityRule_Override (@Nullable java.lang.String PriorityRule_Override);

	/**
	 * Get Priority override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPriorityRule_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PriorityRule_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "PriorityRule_Override", null);
	String COLUMNNAME_PriorityRule_Override = "PriorityRule_Override";

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

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Processed = new ModelColumn<>(I_M_ShipmentSchedule.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isProcessing();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Processing = new ModelColumn<>(I_M_ShipmentSchedule.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Product Description.
	 * Product Description
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductDescription (@Nullable java.lang.String ProductDescription);

	/**
	 * Get Product Description.
	 * Product Description
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductDescription();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_ProductDescription = new ModelColumn<>(I_M_ShipmentSchedule.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDelivered (@Nullable BigDecimal QtyDelivered);

	/**
	 * Get Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDelivered();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyDelivered = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyDelivered", null);
	String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Stock.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOnHand (@Nullable BigDecimal QtyOnHand);

	/**
	 * Get Stock.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOnHand();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyOnHand = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyOnHand", null);
	String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (@Nullable BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Quantity Ordered.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered_Calculated (@Nullable BigDecimal QtyOrdered_Calculated);

	/**
	 * Get Quantity Ordered.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered_Calculated();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyOrdered_Calculated = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyOrdered_Calculated", null);
	String COLUMNNAME_QtyOrdered_Calculated = "QtyOrdered_Calculated";

	/**
	 * Set Quantity Ordered override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered_Override (@Nullable BigDecimal QtyOrdered_Override);

	/**
	 * Get Quantity Ordered override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyOrdered_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyOrdered_Override", null);
	String COLUMNNAME_QtyOrdered_Override = "QtyOrdered_Override";

	/**
	 * Set Qty Ordered (TU).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered_TU (BigDecimal QtyOrdered_TU);

	/**
	 * Get Qty Ordered (TU).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered_TU();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyOrdered_TU = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyOrdered_TU", null);
	String COLUMNNAME_QtyOrdered_TU = "QtyOrdered_TU";

	/**
	 * Set Picked (stock UOM).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyPickList (@Nullable BigDecimal QtyPickList);

	/**
	 * Get Picked (stock UOM).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyPickList();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyPickList = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyPickList", null);
	String COLUMNNAME_QtyPickList = "QtyPickList";

	/**
	 * Set Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReserved (@Nullable BigDecimal QtyReserved);

	/**
	 * Get Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReserved();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyReserved = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Quantity to Deliver.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToDeliver (@Nullable BigDecimal QtyToDeliver);

	/**
	 * Get Quantity to Deliver.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToDeliver();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyToDeliver = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyToDeliver", null);
	String COLUMNNAME_QtyToDeliver = "QtyToDeliver";

	/**
	 * Set Different Catch Weight Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToDeliverCatch_Override (@Nullable BigDecimal QtyToDeliverCatch_Override);

	/**
	 * Get Different Catch Weight Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToDeliverCatch_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyToDeliverCatch_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyToDeliverCatch_Override", null);
	String COLUMNNAME_QtyToDeliverCatch_Override = "QtyToDeliverCatch_Override";

	/**
	 * Set Deliver Qty Override.
	 * Menge, die abweichend von der ursprünglichen Vorgabe ausgeliefert wird
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToDeliver_Override (@Nullable BigDecimal QtyToDeliver_Override);

	/**
	 * Get Deliver Qty Override.
	 * Menge, die abweichend von der ursprünglichen Vorgabe ausgeliefert wird
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToDeliver_Override();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyToDeliver_Override = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyToDeliver_Override", null);
	String COLUMNNAME_QtyToDeliver_Override = "QtyToDeliver_Override";

	/**
	 * Set Alt deliveryquantity ready.
	 * Teilmenge von "Abw. Liefermenge", die bereits Ausgeliefert wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToDeliver_OverrideFulfilled (@Nullable BigDecimal QtyToDeliver_OverrideFulfilled);

	/**
	 * Get Alt deliveryquantity ready.
	 * Teilmenge von "Abw. Liefermenge", die bereits Ausgeliefert wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToDeliver_OverrideFulfilled();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyToDeliver_OverrideFulfilled = new ModelColumn<>(I_M_ShipmentSchedule.class, "QtyToDeliver_OverrideFulfilled", null);
	String COLUMNNAME_QtyToDeliver_OverrideFulfilled = "QtyToDeliver_OverrideFulfilled";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Record_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Best Before Policy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipmentAllocation_BestBefore_Policy (@Nullable java.lang.String ShipmentAllocation_BestBefore_Policy);

	/**
	 * Get Best Before Policy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipmentAllocation_BestBefore_Policy();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_ShipmentAllocation_BestBefore_Policy = new ModelColumn<>(I_M_ShipmentSchedule.class, "ShipmentAllocation_BestBefore_Policy", null);
	String COLUMNNAME_ShipmentAllocation_BestBefore_Policy = "ShipmentAllocation_BestBefore_Policy";

	/**
	 * Set PS.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSinglePriceTag_ID (@Nullable java.lang.String SinglePriceTag_ID);

	/**
	 * Get PS.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSinglePriceTag_ID();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_SinglePriceTag_ID = new ModelColumn<>(I_M_ShipmentSchedule.class, "SinglePriceTag_ID", null);
	String COLUMNNAME_SinglePriceTag_ID = "SinglePriceTag_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStatus (@Nullable java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStatus();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Status = new ModelColumn<>(I_M_ShipmentSchedule.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Updated = new ModelColumn<>(I_M_ShipmentSchedule.class, "Updated", null);
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
