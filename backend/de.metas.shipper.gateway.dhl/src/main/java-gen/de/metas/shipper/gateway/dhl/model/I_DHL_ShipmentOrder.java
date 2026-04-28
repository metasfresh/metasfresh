package de.metas.shipper.gateway.dhl.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for DHL_ShipmentOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DHL_ShipmentOrder 
{

	String Table_Name = "DHL_ShipmentOrder";

//	/** AD_Table_ID=541419 */
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
	 * Set Additional Fee.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAdditionalFee (@Nullable BigDecimal AdditionalFee);

	/**
	 * Get Additional Fee.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAdditionalFee();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_AdditionalFee = new ModelColumn<>(I_DHL_ShipmentOrder.class, "AdditionalFee", null);
	String COLUMNNAME_AdditionalFee = "AdditionalFee";

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
	 * Set Awb (Air Waybill).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setawb (@Nullable java.lang.String awb);

	/**
	 * Get Awb (Air Waybill).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getawb();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_awb = new ModelColumn<>(I_DHL_ShipmentOrder.class, "awb", null);
	String COLUMNNAME_awb = "awb";

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
	 * Set Customs Invoice.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Customs_Invoice_ID (int C_Customs_Invoice_ID);

	/**
	 * Get Customs Invoice.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Customs_Invoice_ID();

	@Nullable org.compiere.model.I_C_Customs_Invoice getC_Customs_Invoice();

	void setC_Customs_Invoice(@Nullable org.compiere.model.I_C_Customs_Invoice C_Customs_Invoice);

	ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_Customs_Invoice> COLUMN_C_Customs_Invoice_ID = new ModelColumn<>(I_DHL_ShipmentOrder.class, "C_Customs_Invoice_ID", org.compiere.model.I_C_Customs_Invoice.class);
	String COLUMNNAME_C_Customs_Invoice_ID = "C_Customs_Invoice_ID";

	/**
	 * Set Customs Invoice Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Customs_Invoice_Line_ID (int C_Customs_Invoice_Line_ID);

	/**
	 * Get Customs Invoice Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Customs_Invoice_Line_ID();

	@Nullable org.compiere.model.I_C_Customs_Invoice_Line getC_Customs_Invoice_Line();

	void setC_Customs_Invoice_Line(@Nullable org.compiere.model.I_C_Customs_Invoice_Line C_Customs_Invoice_Line);

	ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_Customs_Invoice_Line> COLUMN_C_Customs_Invoice_Line_ID = new ModelColumn<>(I_DHL_ShipmentOrder.class, "C_Customs_Invoice_Line_ID", org.compiere.model.I_C_Customs_Invoice_Line.class);
	String COLUMNNAME_C_Customs_Invoice_Line_ID = "C_Customs_Invoice_Line_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_Created = new ModelColumn<>(I_DHL_ShipmentOrder.class, "Created", null);
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

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_CustomerReference = new ModelColumn<>(I_DHL_ShipmentOrder.class, "CustomerReference", null);
	String COLUMNNAME_CustomerReference = "CustomerReference";

	/**
	 * Set Customs Amount.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCustomsAmount (int CustomsAmount);

	/**
	 * Get Customs Amount.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCustomsAmount();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_CustomsAmount = new ModelColumn<>(I_DHL_ShipmentOrder.class, "CustomsAmount", null);
	String COLUMNNAME_CustomsAmount = "CustomsAmount";

	/**
	 * Set Customs Tariff Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomsTariffNumber (@Nullable java.lang.String CustomsTariffNumber);

	/**
	 * Get Customs Tariff Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomsTariffNumber();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_CustomsTariffNumber = new ModelColumn<>(I_DHL_ShipmentOrder.class, "CustomsTariffNumber", null);
	String COLUMNNAME_CustomsTariffNumber = "CustomsTariffNumber";

	/**
	 * Set Customs Value.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomsValue (@Nullable BigDecimal CustomsValue);

	/**
	 * Get Customs Value.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCustomsValue();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_CustomsValue = new ModelColumn<>(I_DHL_ShipmentOrder.class, "CustomsValue", null);
	String COLUMNNAME_CustomsValue = "CustomsValue";

	/**
	 * Set Height in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDHL_HeightInCm (int DHL_HeightInCm);

	/**
	 * Get Height in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDHL_HeightInCm();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_HeightInCm = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_HeightInCm", null);
	String COLUMNNAME_DHL_HeightInCm = "DHL_HeightInCm";

	/**
	 * Set Length in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDHL_LengthInCm (int DHL_LengthInCm);

	/**
	 * Get Length in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDHL_LengthInCm();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_LengthInCm = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_LengthInCm", null);
	String COLUMNNAME_DHL_LengthInCm = "DHL_LengthInCm";

	/**
	 * Set Product.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Product (@Nullable java.lang.String DHL_Product);

	/**
	 * Get Product.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Product();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Product = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Product", null);
	String COLUMNNAME_DHL_Product = "DHL_Product";

	/**
	 * Set Receiver City.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_City (@Nullable java.lang.String DHL_Receiver_City);

	/**
	 * Get Receiver City.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_City();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_City = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_City", null);
	String COLUMNNAME_DHL_Receiver_City = "DHL_Receiver_City";

	/**
	 * Set Receiver Country Code (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_CountryISO2Code (@Nullable java.lang.String DHL_Receiver_CountryISO2Code);

	/**
	 * Get Receiver Country Code (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_CountryISO2Code();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_CountryISO2Code = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_CountryISO2Code", null);
	String COLUMNNAME_DHL_Receiver_CountryISO2Code = "DHL_Receiver_CountryISO2Code";

	/**
	 * Set Receiver Country Code (ISO-3).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_CountryISO3Code (@Nullable java.lang.String DHL_Receiver_CountryISO3Code);

	/**
	 * Get Receiver Country Code (ISO-3).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_CountryISO3Code();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_CountryISO3Code = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_CountryISO3Code", null);
	String COLUMNNAME_DHL_Receiver_CountryISO3Code = "DHL_Receiver_CountryISO3Code";

	/**
	 * Set Receiver Email.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_Email (@Nullable java.lang.String DHL_Receiver_Email);

	/**
	 * Get Receiver Email.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_Email();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_Email = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_Email", null);
	String COLUMNNAME_DHL_Receiver_Email = "DHL_Receiver_Email";

	/**
	 * Set Receiver Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_Name1 (@Nullable java.lang.String DHL_Receiver_Name1);

	/**
	 * Get Receiver Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_Name1();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_Name1 = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_Name1", null);
	String COLUMNNAME_DHL_Receiver_Name1 = "DHL_Receiver_Name1";

	/**
	 * Set Receiver Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_Name2 (@Nullable java.lang.String DHL_Receiver_Name2);

	/**
	 * Get Receiver Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_Name2();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_Name2 = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_Name2", null);
	String COLUMNNAME_DHL_Receiver_Name2 = "DHL_Receiver_Name2";

	/**
	 * Set Receiver Phone.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_Phone (@Nullable java.lang.String DHL_Receiver_Phone);

	/**
	 * Get Receiver Phone.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_Phone();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_Phone = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_Phone", null);
	String COLUMNNAME_DHL_Receiver_Phone = "DHL_Receiver_Phone";

	/**
	 * Set Receiver Street Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_StreetName1 (@Nullable java.lang.String DHL_Receiver_StreetName1);

	/**
	 * Get Receiver Street Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_StreetName1();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_StreetName1 = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_StreetName1", null);
	String COLUMNNAME_DHL_Receiver_StreetName1 = "DHL_Receiver_StreetName1";

	/**
	 * Set Receiver Street Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_StreetName2 (@Nullable java.lang.String DHL_Receiver_StreetName2);

	/**
	 * Get Receiver Street Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_StreetName2();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_StreetName2 = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_StreetName2", null);
	String COLUMNNAME_DHL_Receiver_StreetName2 = "DHL_Receiver_StreetName2";

	/**
	 * Set Receiver Street Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_StreetNumber (@Nullable java.lang.String DHL_Receiver_StreetNumber);

	/**
	 * Get Receiver Street Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_StreetNumber();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_StreetNumber = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_StreetNumber", null);
	String COLUMNNAME_DHL_Receiver_StreetNumber = "DHL_Receiver_StreetNumber";

	/**
	 * Set Receiver Zip Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Receiver_ZipCode (@Nullable java.lang.String DHL_Receiver_ZipCode);

	/**
	 * Get Receiver Zip Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Receiver_ZipCode();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_ZipCode = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Receiver_ZipCode", null);
	String COLUMNNAME_DHL_Receiver_ZipCode = "DHL_Receiver_ZipCode";

	/**
	 * Set DHL_RecipientEmailAddress.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_RecipientEmailAddress (@Nullable java.lang.String DHL_RecipientEmailAddress);

	/**
	 * Get DHL_RecipientEmailAddress.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_RecipientEmailAddress();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_RecipientEmailAddress = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_RecipientEmailAddress", null);
	String COLUMNNAME_DHL_RecipientEmailAddress = "DHL_RecipientEmailAddress";

	/**
	 * Set Shipment Date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_ShipmentDate (@Nullable java.lang.String DHL_ShipmentDate);

	/**
	 * Get Shipment Date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_ShipmentDate();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_ShipmentDate = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_ShipmentDate", null);
	String COLUMNNAME_DHL_ShipmentDate = "DHL_ShipmentDate";

	/**
	 * Set DHL_ShipmetnOrder.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDHL_ShipmentOrder_ID (int DHL_ShipmentOrder_ID);

	/**
	 * Get DHL_ShipmetnOrder.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDHL_ShipmentOrder_ID();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_ShipmentOrder_ID = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_ShipmentOrder_ID", null);
	String COLUMNNAME_DHL_ShipmentOrder_ID = "DHL_ShipmentOrder_ID";

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

	ModelColumn<I_DHL_ShipmentOrder, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest> COLUMN_DHL_ShipmentOrderRequest_ID = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_ShipmentOrderRequest_ID", de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class);
	String COLUMNNAME_DHL_ShipmentOrderRequest_ID = "DHL_ShipmentOrderRequest_ID";

	/**
	 * Set Shipper City.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_City (@Nullable java.lang.String DHL_Shipper_City);

	/**
	 * Get Shipper City.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Shipper_City();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_City = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Shipper_City", null);
	String COLUMNNAME_DHL_Shipper_City = "DHL_Shipper_City";

	/**
	 * Set Shipper Country Code (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_CountryISO2Code (@Nullable java.lang.String DHL_Shipper_CountryISO2Code);

	/**
	 * Get Shipper Country Code (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Shipper_CountryISO2Code();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_CountryISO2Code = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Shipper_CountryISO2Code", null);
	String COLUMNNAME_DHL_Shipper_CountryISO2Code = "DHL_Shipper_CountryISO2Code";

	/**
	 * Set Shipper Country Code (ISO-3).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_CountryISO3Code (@Nullable java.lang.String DHL_Shipper_CountryISO3Code);

	/**
	 * Get Shipper Country Code (ISO-3).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Shipper_CountryISO3Code();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_CountryISO3Code = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Shipper_CountryISO3Code", null);
	String COLUMNNAME_DHL_Shipper_CountryISO3Code = "DHL_Shipper_CountryISO3Code";

	/**
	 * Set Shipper Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_Name1 (@Nullable java.lang.String DHL_Shipper_Name1);

	/**
	 * Get Shipper Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Shipper_Name1();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_Name1 = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Shipper_Name1", null);
	String COLUMNNAME_DHL_Shipper_Name1 = "DHL_Shipper_Name1";

	/**
	 * Set Shipper Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_Name2 (@Nullable java.lang.String DHL_Shipper_Name2);

	/**
	 * Get Shipper Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Shipper_Name2();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_Name2 = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Shipper_Name2", null);
	String COLUMNNAME_DHL_Shipper_Name2 = "DHL_Shipper_Name2";

	/**
	 * Set Shipper Street Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_StreetName1 (@Nullable java.lang.String DHL_Shipper_StreetName1);

	/**
	 * Get Shipper Street Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Shipper_StreetName1();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_StreetName1 = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Shipper_StreetName1", null);
	String COLUMNNAME_DHL_Shipper_StreetName1 = "DHL_Shipper_StreetName1";

	/**
	 * Set Shipper Street Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_StreetName2 (@Nullable java.lang.String DHL_Shipper_StreetName2);

	/**
	 * Get Shipper Street Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Shipper_StreetName2();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_StreetName2 = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Shipper_StreetName2", null);
	String COLUMNNAME_DHL_Shipper_StreetName2 = "DHL_Shipper_StreetName2";

	/**
	 * Set Shipper Street Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_StreetNumber (@Nullable java.lang.String DHL_Shipper_StreetNumber);

	/**
	 * Get Shipper Street Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Shipper_StreetNumber();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_StreetNumber = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Shipper_StreetNumber", null);
	String COLUMNNAME_DHL_Shipper_StreetNumber = "DHL_Shipper_StreetNumber";

	/**
	 * Set Shipper Zip Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_ZipCode (@Nullable java.lang.String DHL_Shipper_ZipCode);

	/**
	 * Get Shipper Zip Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_Shipper_ZipCode();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_ZipCode = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_Shipper_ZipCode", null);
	String COLUMNNAME_DHL_Shipper_ZipCode = "DHL_Shipper_ZipCode";

	/**
	 * Set Weight In kg.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDHL_WeightInKg (BigDecimal DHL_WeightInKg);

	/**
	 * Get Weight In kg.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDHL_WeightInKg();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_WeightInKg = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_WeightInKg", null);
	String COLUMNNAME_DHL_WeightInKg = "DHL_WeightInKg";

	/**
	 * Set Width In cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDHL_WidthInCm (int DHL_WidthInCm);

	/**
	 * Get Width In cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDHL_WidthInCm();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_WidthInCm = new ModelColumn<>(I_DHL_ShipmentOrder.class, "DHL_WidthInCm", null);
	String COLUMNNAME_DHL_WidthInCm = "DHL_WidthInCm";

	/**
	 * Set Electronic Export Notification.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setElectronicExportNotification (@Nullable java.lang.String ElectronicExportNotification);

	/**
	 * Get Electronic Export Notification.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getElectronicExportNotification();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_ElectronicExportNotification = new ModelColumn<>(I_DHL_ShipmentOrder.class, "ElectronicExportNotification", null);
	String COLUMNNAME_ElectronicExportNotification = "ElectronicExportNotification";

	/**
	 * Set Export Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExportType (@Nullable java.lang.String ExportType);

	/**
	 * Get Export Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExportType();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_ExportType = new ModelColumn<>(I_DHL_ShipmentOrder.class, "ExportType", null);
	String COLUMNNAME_ExportType = "ExportType";

	/**
	 * Set Export Type Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExportTypeDescription (@Nullable java.lang.String ExportTypeDescription);

	/**
	 * Get Export Type Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExportTypeDescription();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_ExportTypeDescription = new ModelColumn<>(I_DHL_ShipmentOrder.class, "ExportTypeDescription", null);
	String COLUMNNAME_ExportTypeDescription = "ExportTypeDescription";

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

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_InternationalDelivery = new ModelColumn<>(I_DHL_ShipmentOrder.class, "InternationalDelivery", null);
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

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_IsActive = new ModelColumn<>(I_DHL_ShipmentOrder.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Package.
	 * Shipment Package
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Package_ID (int M_Package_ID);

	/**
	 * Get Package.
	 * Shipment Package
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Package_ID();

	@Nullable org.compiere.model.I_M_Package getM_Package();

	void setM_Package(@Nullable org.compiere.model.I_M_Package M_Package);

	ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_M_Package> COLUMN_M_Package_ID = new ModelColumn<>(I_DHL_ShipmentOrder.class, "M_Package_ID", org.compiere.model.I_M_Package.class);
	String COLUMNNAME_M_Package_ID = "M_Package_ID";

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

	@Nullable org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(@Nullable org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_DHL_ShipmentOrder.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
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

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_M_ShipperTransportation_ID = new ModelColumn<>(I_DHL_ShipmentOrder.class, "M_ShipperTransportation_ID", null);
	String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Net Weight (Kg).
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNetWeightKg (@Nullable BigDecimal NetWeightKg);

	/**
	 * Get Net Weight (Kg).
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getNetWeightKg();

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_NetWeightKg = new ModelColumn<>(I_DHL_ShipmentOrder.class, "NetWeightKg", null);
	String COLUMNNAME_NetWeightKg = "NetWeightKg";

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

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_PackageDescription = new ModelColumn<>(I_DHL_ShipmentOrder.class, "PackageDescription", null);
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

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_PdfLabelData = new ModelColumn<>(I_DHL_ShipmentOrder.class, "PdfLabelData", null);
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

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_TrackingURL = new ModelColumn<>(I_DHL_ShipmentOrder.class, "TrackingURL", null);
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

	ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_Updated = new ModelColumn<>(I_DHL_ShipmentOrder.class, "Updated", null);
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
