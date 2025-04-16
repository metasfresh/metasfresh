package de.metas.shipper.gateway.dhl.model;

import org.adempiere.model.ModelColumn;
import org.compiere.model.I_M_Package;

import javax.annotation.Nullable;

/** Generated Interface for DHL_ShipmentOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DHL_ShipmentOrder 
{

    /** TableName=DHL_ShipmentOrder */
    public static final String Table_Name = "DHL_ShipmentOrder";

    /** AD_Table_ID=541419 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Additional Fee.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAdditionalFee (java.math.BigDecimal AdditionalFee);

	/**
	 * Get Additional Fee.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAdditionalFee();

    /** Column definition for AdditionalFee */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_AdditionalFee = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "AdditionalFee", null);
    /** Column name AdditionalFee */
    public static final String COLUMNNAME_AdditionalFee = "AdditionalFee";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Luftfrachtbrief.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setawb (java.lang.String awb);

	/**
	 * Get Luftfrachtbrief.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getawb();

    /** Column definition for awb */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_awb = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "awb", null);
    /** Column name awb */
    public static final String COLUMNNAME_awb = "awb";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Zollrechnung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Customs_Invoice_ID (int C_Customs_Invoice_ID);

	/**
	 * Get Zollrechnung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Customs_Invoice_ID();

	public org.compiere.model.I_C_Customs_Invoice getC_Customs_Invoice();

	public void setC_Customs_Invoice(org.compiere.model.I_C_Customs_Invoice C_Customs_Invoice);

    /** Column definition for C_Customs_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_Customs_Invoice> COLUMN_C_Customs_Invoice_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_Customs_Invoice>(I_DHL_ShipmentOrder.class, "C_Customs_Invoice_ID", org.compiere.model.I_C_Customs_Invoice.class);
    /** Column name C_Customs_Invoice_ID */
    public static final String COLUMNNAME_C_Customs_Invoice_ID = "C_Customs_Invoice_ID";

	/**
	 * Set Customs Invoice Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Customs_Invoice_Line_ID (int C_Customs_Invoice_Line_ID);

	/**
	 * Get Customs Invoice Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Customs_Invoice_Line_ID();

	public org.compiere.model.I_C_Customs_Invoice_Line getC_Customs_Invoice_Line();

	public void setC_Customs_Invoice_Line(org.compiere.model.I_C_Customs_Invoice_Line C_Customs_Invoice_Line);

    /** Column definition for C_Customs_Invoice_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_Customs_Invoice_Line> COLUMN_C_Customs_Invoice_Line_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_Customs_Invoice_Line>(I_DHL_ShipmentOrder.class, "C_Customs_Invoice_Line_ID", org.compiere.model.I_C_Customs_Invoice_Line.class);
    /** Column name C_Customs_Invoice_Line_ID */
    public static final String COLUMNNAME_C_Customs_Invoice_Line_ID = "C_Customs_Invoice_Line_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Kundenreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomerReference (java.lang.String CustomerReference);

	/**
	 * Get Kundenreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCustomerReference();

    /** Column definition for CustomerReference */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_CustomerReference = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "CustomerReference", null);
    /** Column name CustomerReference */
    public static final String COLUMNNAME_CustomerReference = "CustomerReference";

	/**
	 * Set Customs Amount.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCustomsAmount (int CustomsAmount);

	/**
	 * Get Customs Amount.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCustomsAmount();

    /** Column definition for CustomsAmount */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_CustomsAmount = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "CustomsAmount", null);
    /** Column name CustomsAmount */
    public static final String COLUMNNAME_CustomsAmount = "CustomsAmount";

	/**
	 * Set Customs Tariff Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomsTariffNumber (java.lang.String CustomsTariffNumber);

	/**
	 * Get Customs Tariff Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCustomsTariffNumber();

    /** Column definition for CustomsTariffNumber */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_CustomsTariffNumber = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "CustomsTariffNumber", null);
    /** Column name CustomsTariffNumber */
    public static final String COLUMNNAME_CustomsTariffNumber = "CustomsTariffNumber";

	/**
	 * Set Customs Value.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomsValue (java.math.BigDecimal CustomsValue);

	/**
	 * Get Customs Value.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCustomsValue();

    /** Column definition for CustomsValue */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_CustomsValue = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "CustomsValue", null);
    /** Column name CustomsValue */
    public static final String COLUMNNAME_CustomsValue = "CustomsValue";

	/**
	 * Set Höhe in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_HeightInCm (int DHL_HeightInCm);

	/**
	 * Get Höhe in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDHL_HeightInCm();

    /** Column definition for DHL_HeightInCm */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_HeightInCm = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_HeightInCm", null);
    /** Column name DHL_HeightInCm */
    public static final String COLUMNNAME_DHL_HeightInCm = "DHL_HeightInCm";

	/**
	 * Set Länge in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_LengthInCm (int DHL_LengthInCm);

	/**
	 * Get Länge in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDHL_LengthInCm();

    /** Column definition for DHL_LengthInCm */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_LengthInCm = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_LengthInCm", null);
    /** Column name DHL_LengthInCm */
    public static final String COLUMNNAME_DHL_LengthInCm = "DHL_LengthInCm";

	/**
	 * Set Produkt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Product (java.lang.String DHL_Product);

	/**
	 * Get Produkt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Product();

    /** Column definition for DHL_Product */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Product = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Product", null);
    /** Column name DHL_Product */
    public static final String COLUMNNAME_DHL_Product = "DHL_Product";

	/**
	 * Set Empfängerort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_City (java.lang.String DHL_Receiver_City);

	/**
	 * Get Empfängerort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_City();

    /** Column definition for DHL_Receiver_City */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_City = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_City", null);
    /** Column name DHL_Receiver_City */
    public static final String COLUMNNAME_DHL_Receiver_City = "DHL_Receiver_City";

	/**
	 * Set Empfänger Ländercode (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_CountryISO2Code (java.lang.String DHL_Receiver_CountryISO2Code);

	/**
	 * Get Empfänger Ländercode (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_CountryISO2Code();

    /** Column definition for DHL_Receiver_CountryISO2Code */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_CountryISO2Code = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_CountryISO2Code", null);
    /** Column name DHL_Receiver_CountryISO2Code */
    public static final String COLUMNNAME_DHL_Receiver_CountryISO2Code = "DHL_Receiver_CountryISO2Code";

	/**
	 * Set Empfänger Ländercode (ISO-3).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_CountryISO3Code (java.lang.String DHL_Receiver_CountryISO3Code);

	/**
	 * Get Empfänger Ländercode (ISO-3).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_CountryISO3Code();

    /** Column definition for DHL_Receiver_CountryISO3Code */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_CountryISO3Code = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_CountryISO3Code", null);
    /** Column name DHL_Receiver_CountryISO3Code */
    public static final String COLUMNNAME_DHL_Receiver_CountryISO3Code = "DHL_Receiver_CountryISO3Code";

	/**
	 * Set E-Mail Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_Email (java.lang.String DHL_Receiver_Email);

	/**
	 * Get E-Mail Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_Email();

    /** Column definition for DHL_Receiver_Email */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_Email = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_Email", null);
    /** Column name DHL_Receiver_Email */
    public static final String COLUMNNAME_DHL_Receiver_Email = "DHL_Receiver_Email";

	/**
	 * Set Name 1 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_Name1 (java.lang.String DHL_Receiver_Name1);

	/**
	 * Get Name 1 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_Name1();

    /** Column definition for DHL_Receiver_Name1 */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_Name1 = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_Name1", null);
    /** Column name DHL_Receiver_Name1 */
    public static final String COLUMNNAME_DHL_Receiver_Name1 = "DHL_Receiver_Name1";

	/**
	 * Set Name 2 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_Name2 (java.lang.String DHL_Receiver_Name2);

	/**
	 * Get Name 2 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_Name2();

    /** Column definition for DHL_Receiver_Name2 */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_Name2 = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_Name2", null);
    /** Column name DHL_Receiver_Name2 */
    public static final String COLUMNNAME_DHL_Receiver_Name2 = "DHL_Receiver_Name2";

	/**
	 * Set Telefon Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_Phone (java.lang.String DHL_Receiver_Phone);

	/**
	 * Get Telefon Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_Phone();

    /** Column definition for DHL_Receiver_Phone */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_Phone = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_Phone", null);
    /** Column name DHL_Receiver_Phone */
    public static final String COLUMNNAME_DHL_Receiver_Phone = "DHL_Receiver_Phone";

	/**
	 * Set Straße 1 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_StreetName1 (java.lang.String DHL_Receiver_StreetName1);

	/**
	 * Get Straße 1 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_StreetName1();

    /** Column definition for DHL_Receiver_StreetName1 */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_StreetName1 = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_StreetName1", null);
    /** Column name DHL_Receiver_StreetName1 */
    public static final String COLUMNNAME_DHL_Receiver_StreetName1 = "DHL_Receiver_StreetName1";

	/**
	 * Set Straße 2 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_StreetName2 (java.lang.String DHL_Receiver_StreetName2);

	/**
	 * Get Straße 2 Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_StreetName2();

    /** Column definition for DHL_Receiver_StreetName2 */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_StreetName2 = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_StreetName2", null);
    /** Column name DHL_Receiver_StreetName2 */
    public static final String COLUMNNAME_DHL_Receiver_StreetName2 = "DHL_Receiver_StreetName2";

	/**
	 * Set Hausnummer Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_StreetNumber (java.lang.String DHL_Receiver_StreetNumber);

	/**
	 * Get Hausnummer Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_StreetNumber();

    /** Column definition for DHL_Receiver_StreetNumber */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_StreetNumber = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_StreetNumber", null);
    /** Column name DHL_Receiver_StreetNumber */
    public static final String COLUMNNAME_DHL_Receiver_StreetNumber = "DHL_Receiver_StreetNumber";

	/**
	 * Set PLZ Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_ZipCode (java.lang.String DHL_Receiver_ZipCode);

	/**
	 * Get PLZ Empfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Receiver_ZipCode();

    /** Column definition for DHL_Receiver_ZipCode */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Receiver_ZipCode = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Receiver_ZipCode", null);
    /** Column name DHL_Receiver_ZipCode */
    public static final String COLUMNNAME_DHL_Receiver_ZipCode = "DHL_Receiver_ZipCode";

	/**
	 * Set DHL_RecipientEmailAddress.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_RecipientEmailAddress (java.lang.String DHL_RecipientEmailAddress);

	/**
	 * Get DHL_RecipientEmailAddress.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_RecipientEmailAddress();

    /** Column definition for DHL_RecipientEmailAddress */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_RecipientEmailAddress = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_RecipientEmailAddress", null);
    /** Column name DHL_RecipientEmailAddress */
    public static final String COLUMNNAME_DHL_RecipientEmailAddress = "DHL_RecipientEmailAddress";

	/**
	 * Set Lieferdatum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_ShipmentDate (java.lang.String DHL_ShipmentDate);

	/**
	 * Get Lieferdatum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_ShipmentDate();

    /** Column definition for DHL_ShipmentDate */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_ShipmentDate = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_ShipmentDate", null);
    /** Column name DHL_ShipmentDate */
    public static final String COLUMNNAME_DHL_ShipmentDate = "DHL_ShipmentDate";

	/**
	 * Set DHL_ShipmentOrder.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_ShipmentOrder_ID (int DHL_ShipmentOrder_ID);

	/**
	 * Get DHL_ShipmentOrder.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDHL_ShipmentOrder_ID();

    /** Column definition for DHL_ShipmentOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_ShipmentOrder_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_ShipmentOrder_ID", null);
    /** Column name DHL_ShipmentOrder_ID */
    public static final String COLUMNNAME_DHL_ShipmentOrder_ID = "DHL_ShipmentOrder_ID";

	/**
	 * Set DHL Shipment Order Request.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_ShipmentOrderRequest_ID (int DHL_ShipmentOrderRequest_ID);

	/**
	 * Get DHL Shipment Order Request.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDHL_ShipmentOrderRequest_ID();

	public de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest getDHL_ShipmentOrderRequest();

	public void setDHL_ShipmentOrderRequest(de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest DHL_ShipmentOrderRequest);

    /** Column definition for DHL_ShipmentOrderRequest_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest> COLUMN_DHL_ShipmentOrderRequest_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest>(I_DHL_ShipmentOrder.class, "DHL_ShipmentOrderRequest_ID", de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class);
    /** Column name DHL_ShipmentOrderRequest_ID */
    public static final String COLUMNNAME_DHL_ShipmentOrderRequest_ID = "DHL_ShipmentOrderRequest_ID";

	/**
	 * Set Lieferort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_City (java.lang.String DHL_Shipper_City);

	/**
	 * Get Lieferort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Shipper_City();

    /** Column definition for DHL_Shipper_City */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_City = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Shipper_City", null);
    /** Column name DHL_Shipper_City */
    public static final String COLUMNNAME_DHL_Shipper_City = "DHL_Shipper_City";

	/**
	 * Set Lieferant Ländercode (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_CountryISO2Code (java.lang.String DHL_Shipper_CountryISO2Code);

	/**
	 * Get Lieferant Ländercode (ISO-2).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Shipper_CountryISO2Code();

    /** Column definition for DHL_Shipper_CountryISO2Code */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_CountryISO2Code = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Shipper_CountryISO2Code", null);
    /** Column name DHL_Shipper_CountryISO2Code */
    public static final String COLUMNNAME_DHL_Shipper_CountryISO2Code = "DHL_Shipper_CountryISO2Code";

	/**
	 * Set Lieferant Ländercode (ISO-3).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_CountryISO3Code (java.lang.String DHL_Shipper_CountryISO3Code);

	/**
	 * Get Lieferant Ländercode (ISO-3).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Shipper_CountryISO3Code();

    /** Column definition for DHL_Shipper_CountryISO3Code */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_CountryISO3Code = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Shipper_CountryISO3Code", null);
    /** Column name DHL_Shipper_CountryISO3Code */
    public static final String COLUMNNAME_DHL_Shipper_CountryISO3Code = "DHL_Shipper_CountryISO3Code";

	/**
	 * Set Lieferant Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_Name1 (java.lang.String DHL_Shipper_Name1);

	/**
	 * Get Lieferant Name 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Shipper_Name1();

    /** Column definition for DHL_Shipper_Name1 */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_Name1 = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Shipper_Name1", null);
    /** Column name DHL_Shipper_Name1 */
    public static final String COLUMNNAME_DHL_Shipper_Name1 = "DHL_Shipper_Name1";

	/**
	 * Set  Lieferant Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_Name2 (java.lang.String DHL_Shipper_Name2);

	/**
	 * Get  Lieferant Name 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Shipper_Name2();

    /** Column definition for DHL_Shipper_Name2 */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_Name2 = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Shipper_Name2", null);
    /** Column name DHL_Shipper_Name2 */
    public static final String COLUMNNAME_DHL_Shipper_Name2 = "DHL_Shipper_Name2";

	/**
	 * Set Straße 1 Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_StreetName1 (java.lang.String DHL_Shipper_StreetName1);

	/**
	 * Get Straße 1 Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Shipper_StreetName1();

    /** Column definition for DHL_Shipper_StreetName1 */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_StreetName1 = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Shipper_StreetName1", null);
    /** Column name DHL_Shipper_StreetName1 */
    public static final String COLUMNNAME_DHL_Shipper_StreetName1 = "DHL_Shipper_StreetName1";

	/**
	 * Set Straße 2 Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_StreetName2 (java.lang.String DHL_Shipper_StreetName2);

	/**
	 * Get Straße 2 Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Shipper_StreetName2();

    /** Column definition for DHL_Shipper_StreetName2 */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_StreetName2 = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Shipper_StreetName2", null);
    /** Column name DHL_Shipper_StreetName2 */
    public static final String COLUMNNAME_DHL_Shipper_StreetName2 = "DHL_Shipper_StreetName2";

	/**
	 * Set Hausnummer Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_StreetNumber (java.lang.String DHL_Shipper_StreetNumber);

	/**
	 * Get Hausnummer Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Shipper_StreetNumber();

    /** Column definition for DHL_Shipper_StreetNumber */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_StreetNumber = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Shipper_StreetNumber", null);
    /** Column name DHL_Shipper_StreetNumber */
    public static final String COLUMNNAME_DHL_Shipper_StreetNumber = "DHL_Shipper_StreetNumber";

	/**
	 * Set PLZ Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_ZipCode (java.lang.String DHL_Shipper_ZipCode);

	/**
	 * Get PLZ Lieferant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_Shipper_ZipCode();

    /** Column definition for DHL_Shipper_ZipCode */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_Shipper_ZipCode = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_Shipper_ZipCode", null);
    /** Column name DHL_Shipper_ZipCode */
    public static final String COLUMNNAME_DHL_Shipper_ZipCode = "DHL_Shipper_ZipCode";

	/**
	 * Set Gewicht in kg.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_WeightInKg (java.math.BigDecimal DHL_WeightInKg);

	/**
	 * Get Gewicht in kg.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDHL_WeightInKg();

    /** Column definition for DHL_WeightInKg */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_WeightInKg = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_WeightInKg", null);
    /** Column name DHL_WeightInKg */
    public static final String COLUMNNAME_DHL_WeightInKg = "DHL_WeightInKg";

	/**
	 * Set Breite in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_WidthInCm (int DHL_WidthInCm);

	/**
	 * Get Breite in cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDHL_WidthInCm();

    /** Column definition for DHL_WidthInCm */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_WidthInCm = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_WidthInCm", null);
    /** Column name DHL_WidthInCm */
    public static final String COLUMNNAME_DHL_WidthInCm = "DHL_WidthInCm";

	/**
	 * Set Electronic Export Notification.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setElectronicExportNotification (java.lang.String ElectronicExportNotification);

	/**
	 * Get Electronic Export Notification.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getElectronicExportNotification();

    /** Column definition for ElectronicExportNotification */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_ElectronicExportNotification = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "ElectronicExportNotification", null);
    /** Column name ElectronicExportNotification */
    public static final String COLUMNNAME_ElectronicExportNotification = "ElectronicExportNotification";

	/**
	 * Set Export Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExportType (java.lang.String ExportType);

	/**
	 * Get Export Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExportType();

    /** Column definition for ExportType */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_ExportType = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "ExportType", null);
    /** Column name ExportType */
    public static final String COLUMNNAME_ExportType = "ExportType";

	/**
	 * Set Export Type Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExportTypeDescription (java.lang.String ExportTypeDescription);

	/**
	 * Get Export Type Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExportTypeDescription();

    /** Column definition for ExportTypeDescription */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_ExportTypeDescription = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "ExportTypeDescription", null);
    /** Column name ExportTypeDescription */
    public static final String COLUMNNAME_ExportTypeDescription = "ExportTypeDescription";

	/**
	 * Set International Delivery.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInternationalDelivery (boolean InternationalDelivery);

	/**
	 * Get International Delivery.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInternationalDelivery();

    /** Column definition for InternationalDelivery */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_InternationalDelivery = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "InternationalDelivery", null);
    /** Column name InternationalDelivery */
    public static final String COLUMNNAME_InternationalDelivery = "InternationalDelivery";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_M_Shipper>(I_DHL_ShipmentOrder.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipperTransportation_ID();

    /** Column definition for M_ShipperTransportation_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_M_ShipperTransportation_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "M_ShipperTransportation_ID", null);
    /** Column name M_ShipperTransportation_ID */
    public static final String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Net Weight (Kg).
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNetWeightKg (java.math.BigDecimal NetWeightKg);

	/**
	 * Get Net Weight (Kg).
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getNetWeightKg();

    /** Column definition for NetWeightKg */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_NetWeightKg = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "NetWeightKg", null);
    /** Column name NetWeightKg */
    public static final String COLUMNNAME_NetWeightKg = "NetWeightKg";

	/**
	 * Set Package Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackageDescription (java.lang.String PackageDescription);

	/**
	 * Get Package Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPackageDescription();

    /** Column definition for PackageDescription */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_PackageDescription = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "PackageDescription", null);
    /** Column name PackageDescription */
    public static final String COLUMNNAME_PackageDescription = "PackageDescription";

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

	@Nullable
	org.compiere.model.I_M_Package getM_Package();

	void setM_Package(@Nullable org.compiere.model.I_M_Package M_Package);

	ModelColumn<I_DHL_ShipmentOrder, I_M_Package> COLUMN_M_Package_ID = new ModelColumn<>(I_DHL_ShipmentOrder.class, "M_Package_ID", org.compiere.model.I_M_Package.class);
	String COLUMNNAME_M_Package_ID = "M_Package_ID";

	/**
	 * Set PdfLabelData.
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPdfLabelData (byte[] PdfLabelData);

	/**
	 * Get PdfLabelData.
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public byte[] getPdfLabelData();

    /** Column definition for PdfLabelData */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_PdfLabelData = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "PdfLabelData", null);
    /** Column name PdfLabelData */
    public static final String COLUMNNAME_PdfLabelData = "PdfLabelData";

	/**
	 * Set Nachverfolgungs-URL.
	 * URL des Spediteurs um Sendungen zu verfolgen
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTrackingURL (java.lang.String TrackingURL);

	/**
	 * Get Nachverfolgungs-URL.
	 * URL des Spediteurs um Sendungen zu verfolgen
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTrackingURL();

    /** Column definition for TrackingURL */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_TrackingURL = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "TrackingURL", null);
    /** Column name TrackingURL */
    public static final String COLUMNNAME_TrackingURL = "TrackingURL";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
