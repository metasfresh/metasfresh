package de.metas.shipper.gateway.dhl.model;


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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_AD_Client>(I_DHL_ShipmentOrder.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_AD_Org>(I_DHL_ShipmentOrder.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_BPartner>(I_DHL_ShipmentOrder.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
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

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_C_BPartner_Location>(I_DHL_ShipmentOrder.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

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

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_AD_User>(I_DHL_ShipmentOrder.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
	 * Set DHL_AccountNumber.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_AccountNumber (java.lang.String DHL_AccountNumber);

	/**
	 * Get DHL_AccountNumber.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDHL_AccountNumber();

    /** Column definition for DHL_AccountNumber */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_DHL_AccountNumber = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "DHL_AccountNumber", null);
    /** Column name DHL_AccountNumber */
    public static final String COLUMNNAME_DHL_AccountNumber = "DHL_AccountNumber";

	/**
	 * Set DHL_HeightInCm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_HeightInCm (int DHL_HeightInCm);

	/**
	 * Get DHL_HeightInCm.
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
	 * Set DHL_LengthInCm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_LengthInCm (int DHL_LengthInCm);

	/**
	 * Get DHL_LengthInCm.
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
	 * Set DHL_Product.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Product (java.lang.String DHL_Product);

	/**
	 * Get DHL_Product.
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
	 * Set DHL_Receiver_City.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_City (java.lang.String DHL_Receiver_City);

	/**
	 * Get DHL_Receiver_City.
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
	 * Set DHL_Receiver_CountryISO2Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_CountryISO2Code (java.lang.String DHL_Receiver_CountryISO2Code);

	/**
	 * Get DHL_Receiver_CountryISO2Code.
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
	 * Set DHL_Receiver_Email.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_Email (java.lang.String DHL_Receiver_Email);

	/**
	 * Get DHL_Receiver_Email.
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
	 * Set DHL_Receiver_Name1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_Name1 (java.lang.String DHL_Receiver_Name1);

	/**
	 * Get DHL_Receiver_Name1.
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
	 * Set DHL_Receiver_Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_Name2 (java.lang.String DHL_Receiver_Name2);

	/**
	 * Get DHL_Receiver_Name2.
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
	 * Set DHL_Receiver_Phone.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_Phone (java.lang.String DHL_Receiver_Phone);

	/**
	 * Get DHL_Receiver_Phone.
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
	 * Set DHL_Receiver_StreetName1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_StreetName1 (java.lang.String DHL_Receiver_StreetName1);

	/**
	 * Get DHL_Receiver_StreetName1.
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
	 * Set DHL_Receiver_StreetName2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_StreetName2 (java.lang.String DHL_Receiver_StreetName2);

	/**
	 * Get DHL_Receiver_StreetName2.
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
	 * Set DHL_Receiver_StreetNumber.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_StreetNumber (java.lang.String DHL_Receiver_StreetNumber);

	/**
	 * Get DHL_Receiver_StreetNumber.
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
	 * Set DHL_Receiver_ZipCode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Receiver_ZipCode (java.lang.String DHL_Receiver_ZipCode);

	/**
	 * Get DHL_Receiver_ZipCode.
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
	 * Set DHL_ShipmentDate.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_ShipmentDate (java.lang.String DHL_ShipmentDate);

	/**
	 * Get DHL_ShipmentDate.
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
	 * Set DHL_ShipmetnOrder.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_ShipmentOrder_ID (int DHL_ShipmentOrder_ID);

	/**
	 * Get DHL_ShipmetnOrder.
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

	public I_DHL_ShipmentOrderRequest getDHL_ShipmentOrderRequest();

	public void setDHL_ShipmentOrderRequest(I_DHL_ShipmentOrderRequest DHL_ShipmentOrderRequest);

    /** Column definition for DHL_ShipmentOrderRequest_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, I_DHL_ShipmentOrderRequest> COLUMN_DHL_ShipmentOrderRequest_ID = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, I_DHL_ShipmentOrderRequest>(I_DHL_ShipmentOrder.class, "DHL_ShipmentOrderRequest_ID", I_DHL_ShipmentOrderRequest.class);
    /** Column name DHL_ShipmentOrderRequest_ID */
    public static final String COLUMNNAME_DHL_ShipmentOrderRequest_ID = "DHL_ShipmentOrderRequest_ID";

	/**
	 * Set DHL_Shipper_City.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_City (java.lang.String DHL_Shipper_City);

	/**
	 * Get DHL_Shipper_City.
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
	 * Set DHL_Shipper_CountryISO2Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_CountryISO2Code (java.lang.String DHL_Shipper_CountryISO2Code);

	/**
	 * Get DHL_Shipper_CountryISO2Code.
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
	 * Set DHL_Shipper_Name1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_Name1 (java.lang.String DHL_Shipper_Name1);

	/**
	 * Get DHL_Shipper_Name1.
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
	 * Set DHL_Shipper_Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_Name2 (java.lang.String DHL_Shipper_Name2);

	/**
	 * Get DHL_Shipper_Name2.
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
	 * Set DHL_Shipper_StreetName1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_StreetName1 (java.lang.String DHL_Shipper_StreetName1);

	/**
	 * Get DHL_Shipper_StreetName1.
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
	 * Set DHL_Shipper_StreetName2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_StreetName2 (java.lang.String DHL_Shipper_StreetName2);

	/**
	 * Get DHL_Shipper_StreetName2.
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
	 * Set DHL_Shipper_StreetNumber.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_StreetNumber (java.lang.String DHL_Shipper_StreetNumber);

	/**
	 * Get DHL_Shipper_StreetNumber.
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
	 * Set DHL_Shipper_ZipCode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_ZipCode (java.lang.String DHL_Shipper_ZipCode);

	/**
	 * Get DHL_Shipper_ZipCode.
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
	 * Set DHL_WeightInKg.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_WeightInKg (java.math.BigDecimal DHL_WeightInKg);

	/**
	 * Get DHL_WeightInKg.
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
	 * Set DHL_WidthInCm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_WidthInCm (int DHL_WidthInCm);

	/**
	 * Get DHL_WidthInCm.
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
	 * Set Package Id.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPackageId (int PackageId);

	/**
	 * Get Package Id.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPackageId();

    /** Column definition for PackageId */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object> COLUMN_PackageId = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, Object>(I_DHL_ShipmentOrder.class, "PackageId", null);
    /** Column name PackageId */
    public static final String COLUMNNAME_PackageId = "PackageId";

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

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DHL_ShipmentOrder, org.compiere.model.I_AD_User>(I_DHL_ShipmentOrder.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
