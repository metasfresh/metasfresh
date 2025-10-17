package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for Carrier_ShipmentOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Carrier_ShipmentOrder 
{

	String Table_Name = "Carrier_ShipmentOrder";

//	/** AD_Table_ID=542532 */
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
	 * Set Carrier Material Assignment.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCarrier_Goods_Type_ID (int Carrier_Goods_Type_ID);

	/**
	 * Get Carrier Material Assignment.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCarrier_Goods_Type_ID();

	ModelColumn<I_Carrier_ShipmentOrder, org.compiere.model.I_Carrier_Goods_Type> COLUMN_Carrier_Goods_Type_ID = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Carrier_Goods_Type_ID", org.compiere.model.I_Carrier_Goods_Type.class);
	String COLUMNNAME_Carrier_Goods_Type_ID = "Carrier_Goods_Type_ID";

	/**
	 * Set Carrier Product.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCarrier_Product_ID (int Carrier_Product_ID);

	/**
	 * Get Carrier Product.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCarrier_Product_ID();

	ModelColumn<I_Carrier_ShipmentOrder, org.compiere.model.I_Carrier_Product> COLUMN_Carrier_Product_ID = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Carrier_Product_ID", org.compiere.model.I_Carrier_Product.class);
	String COLUMNNAME_Carrier_Product_ID = "Carrier_Product_ID";

	/**
	 * Set Shipment Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_ShipmentOrder_ID (int Carrier_ShipmentOrder_ID);

	/**
	 * Get Shipment Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_ShipmentOrder_ID();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Carrier_ShipmentOrder_ID = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Carrier_ShipmentOrder_ID", null);
	String COLUMNNAME_Carrier_ShipmentOrder_ID = "Carrier_ShipmentOrder_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Created = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Created", null);
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
	 * Set Customer Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomerReference (@Nullable java.lang.String CustomerReference);

	/**
	 * Get Customer Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomerReference();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_CustomerReference = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "CustomerReference", null);
	String COLUMNNAME_CustomerReference = "CustomerReference";

	/**
	 * Set International Delivery.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInternationalDelivery (boolean InternationalDelivery);

	/**
	 * Get International Delivery.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInternationalDelivery();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_InternationalDelivery = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "InternationalDelivery", null);
	String COLUMNNAME_InternationalDelivery = "InternationalDelivery";

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

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_IsActive = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	ModelColumn<I_Carrier_ShipmentOrder, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Transportation Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transportation Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipperTransportation_ID();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_M_ShipperTransportation_ID = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "M_ShipperTransportation_ID", null);
	String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Empfängerort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_City (@Nullable java.lang.String Receiver_City);

	/**
	 * Get Empfängerort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_City();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_City = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_City", null);
	String COLUMNNAME_Receiver_City = "Receiver_City";

	/**
	 * Set Empfänger Ländercode (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_CountryISO2Code (@Nullable java.lang.String Receiver_CountryISO2Code);

	/**
	 * Get Empfänger Ländercode (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_CountryISO2Code();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_CountryISO2Code = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_CountryISO2Code", null);
	String COLUMNNAME_Receiver_CountryISO2Code = "Receiver_CountryISO2Code";

	/**
	 * Set E-Mail Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_Email (@Nullable java.lang.String Receiver_Email);

	/**
	 * Get E-Mail Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_Email();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_Email = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_Email", null);
	String COLUMNNAME_Receiver_Email = "Receiver_Email";

	/**
	 * Set Receiver EORI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_EORI (@Nullable java.lang.String Receiver_EORI);

	/**
	 * Get Receiver EORI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_EORI();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_EORI = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_EORI", null);
	String COLUMNNAME_Receiver_EORI = "Receiver_EORI";

	/**
	 * Set Name 1 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_Name1 (@Nullable java.lang.String Receiver_Name1);

	/**
	 * Get Name 1 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_Name1();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_Name1 = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_Name1", null);
	String COLUMNNAME_Receiver_Name1 = "Receiver_Name1";

	/**
	 * Set Name 2 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_Name2 (@Nullable java.lang.String Receiver_Name2);

	/**
	 * Get Name 2 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_Name2();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_Name2 = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_Name2", null);
	String COLUMNNAME_Receiver_Name2 = "Receiver_Name2";

	/**
	 * Set Telefon Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_Phone (@Nullable java.lang.String Receiver_Phone);

	/**
	 * Get Telefon Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_Phone();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_Phone = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_Phone", null);
	String COLUMNNAME_Receiver_Phone = "Receiver_Phone";

	/**
	 * Set Straße 1 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_StreetName1 (@Nullable java.lang.String Receiver_StreetName1);

	/**
	 * Get Straße 1 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_StreetName1();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_StreetName1 = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_StreetName1", null);
	String COLUMNNAME_Receiver_StreetName1 = "Receiver_StreetName1";

	/**
	 * Set Straße 2 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_StreetName2 (@Nullable java.lang.String Receiver_StreetName2);

	/**
	 * Get Straße 2 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_StreetName2();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_StreetName2 = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_StreetName2", null);
	String COLUMNNAME_Receiver_StreetName2 = "Receiver_StreetName2";

	/**
	 * Set Hausnummer Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_StreetNumber (@Nullable java.lang.String Receiver_StreetNumber);

	/**
	 * Get Hausnummer Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_StreetNumber();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_StreetNumber = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_StreetNumber", null);
	String COLUMNNAME_Receiver_StreetNumber = "Receiver_StreetNumber";

	/**
	 * Set PLZ Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_ZipCode (@Nullable java.lang.String Receiver_ZipCode);

	/**
	 * Get PLZ Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiver_ZipCode();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Receiver_ZipCode = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Receiver_ZipCode", null);
	String COLUMNNAME_Receiver_ZipCode = "Receiver_ZipCode";

	/**
	 * Set Lieferdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShipmentDate (java.sql.Timestamp ShipmentDate);

	/**
	 * Get Lieferdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getShipmentDate();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_ShipmentDate = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "ShipmentDate", null);
	String COLUMNNAME_ShipmentDate = "ShipmentDate";

	/**
	 * Set Lieferort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipper_City (@Nullable java.lang.String Shipper_City);

	/**
	 * Get Lieferort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipper_City();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Shipper_City = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Shipper_City", null);
	String COLUMNNAME_Shipper_City = "Shipper_City";

	/**
	 * Set Lieferant Ländercode (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipper_CountryISO2Code (@Nullable java.lang.String Shipper_CountryISO2Code);

	/**
	 * Get Lieferant Ländercode (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipper_CountryISO2Code();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Shipper_CountryISO2Code = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Shipper_CountryISO2Code", null);
	String COLUMNNAME_Shipper_CountryISO2Code = "Shipper_CountryISO2Code";

	/**
	 * Set Shipper EORI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipper_EORI (@Nullable java.lang.String Shipper_EORI);

	/**
	 * Get Shipper EORI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipper_EORI();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Shipper_EORI = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Shipper_EORI", null);
	String COLUMNNAME_Shipper_EORI = "Shipper_EORI";

	/**
	 * Set Lieferant Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipper_Name1 (@Nullable java.lang.String Shipper_Name1);

	/**
	 * Get Lieferant Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipper_Name1();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Shipper_Name1 = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Shipper_Name1", null);
	String COLUMNNAME_Shipper_Name1 = "Shipper_Name1";

	/**
	 * Set  Lieferant Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipper_Name2 (@Nullable java.lang.String Shipper_Name2);

	/**
	 * Get  Lieferant Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipper_Name2();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Shipper_Name2 = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Shipper_Name2", null);
	String COLUMNNAME_Shipper_Name2 = "Shipper_Name2";

	/**
	 * Set Straße 1 Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipper_StreetName1 (@Nullable java.lang.String Shipper_StreetName1);

	/**
	 * Get Straße 1 Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipper_StreetName1();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Shipper_StreetName1 = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Shipper_StreetName1", null);
	String COLUMNNAME_Shipper_StreetName1 = "Shipper_StreetName1";

	/**
	 * Set Straße 2 Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipper_StreetName2 (@Nullable java.lang.String Shipper_StreetName2);

	/**
	 * Get Straße 2 Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipper_StreetName2();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Shipper_StreetName2 = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Shipper_StreetName2", null);
	String COLUMNNAME_Shipper_StreetName2 = "Shipper_StreetName2";

	/**
	 * Set Hausnummer Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipper_StreetNumber (@Nullable java.lang.String Shipper_StreetNumber);

	/**
	 * Get Hausnummer Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipper_StreetNumber();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Shipper_StreetNumber = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Shipper_StreetNumber", null);
	String COLUMNNAME_Shipper_StreetNumber = "Shipper_StreetNumber";

	/**
	 * Set PLZ Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipper_ZipCode (@Nullable java.lang.String Shipper_ZipCode);

	/**
	 * Get PLZ Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipper_ZipCode();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Shipper_ZipCode = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Shipper_ZipCode", null);
	String COLUMNNAME_Shipper_ZipCode = "Shipper_ZipCode";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Carrier_ShipmentOrder, Object> COLUMN_Updated = new ModelColumn<>(I_Carrier_ShipmentOrder.class, "Updated", null);
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
