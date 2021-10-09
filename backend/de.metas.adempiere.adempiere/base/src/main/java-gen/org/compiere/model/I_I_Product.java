package org.compiere.model;


/** Generated Interface for I_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_Product 
{

    /** TableName=I_Product */
    public static final String Table_Name = "I_Product";

    /** AD_Table_ID=532 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_AD_Issue>(I_I_Product.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * The Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartner_Value (java.lang.String BPartner_Value);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * The Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartner_Value();

    /** Column definition for BPartner_Value */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_BPartner_Value = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "BPartner_Value", null);
    /** Column name BPartner_Value */
    public static final String COLUMNNAME_BPartner_Value = "BPartner_Value";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_ID();

	public org.compiere.model.I_C_DataImport getC_DataImport();

	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport);

    /** Column definition for C_DataImport_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_C_DataImport>(I_I_Product.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
    /** Column name C_DataImport_ID */
    public static final String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_Run_ID();

	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	public void setC_DataImport_Run(org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

    /** Column definition for C_DataImport_Run_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_C_DataImport_Run>(I_I_Product.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
    /** Column name C_DataImport_Run_ID */
    public static final String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Set Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_TaxCategory_ID();

    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Set MwSt-Kategorie.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_TaxCategory_Name (java.lang.String C_TaxCategory_Name);

	/**
	 * Get MwSt-Kategorie.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getC_TaxCategory_Name();

    /** Column definition for C_TaxCategory_Name */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_C_TaxCategory_Name = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "C_TaxCategory_Name", null);
    /** Column name C_TaxCategory_Name */
    public static final String COLUMNNAME_C_TaxCategory_Name = "C_TaxCategory_Name";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Klassifizierung.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setClassification (java.lang.String Classification);

	/**
	 * Get Klassifizierung.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClassification();

    /** Column definition for Classification */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Classification = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Classification", null);
    /** Column name Classification */
    public static final String COLUMNNAME_Classification = "Classification";

	/**
	 * Set Bestellkosten.
	 * Fixed Cost Per Order
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCostPerOrder (java.math.BigDecimal CostPerOrder);

	/**
	 * Get Bestellkosten.
	 * Fixed Cost Per Order
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCostPerOrder();

    /** Column definition for CostPerOrder */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_CostPerOrder = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "CostPerOrder", null);
    /** Column name CostPerOrder */
    public static final String COLUMNNAME_CostPerOrder = "CostPerOrder";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Customs Tariff.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomsTariff (java.lang.String CustomsTariff);

	/**
	 * Get Customs Tariff.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCustomsTariff();

    /** Column definition for CustomsTariff */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_CustomsTariff = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "CustomsTariff", null);
    /** Column name CustomsTariff */
    public static final String COLUMNNAME_CustomsTariff = "CustomsTariff";

	/**
	 * Set Zugesicherte Lieferzeit.
	 * Promised days between order and delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryTime_Promised (int DeliveryTime_Promised);

	/**
	 * Get Zugesicherte Lieferzeit.
	 * Promised days between order and delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDeliveryTime_Promised();

    /** Column definition for DeliveryTime_Promised */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_DeliveryTime_Promised = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "DeliveryTime_Promised", null);
    /** Column name DeliveryTime_Promised */
    public static final String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Beschreibungs-URL.
	 * URL for the description
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescriptionURL (java.lang.String DescriptionURL);

	/**
	 * Get Beschreibungs-URL.
	 * URL for the description
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescriptionURL();

    /** Column definition for DescriptionURL */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_DescriptionURL = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "DescriptionURL", null);
    /** Column name DescriptionURL */
    public static final String COLUMNNAME_DescriptionURL = "DescriptionURL";

	/**
	 * Set Eingestellt.
	 * This product is no longer available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscontinued (boolean Discontinued);

	/**
	 * Get Eingestellt.
	 * This product is no longer available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDiscontinued();

    /** Column definition for Discontinued */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Discontinued = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Discontinued", null);
    /** Column name Discontinued */
    public static final String COLUMNNAME_Discontinued = "Discontinued";

	/**
	 * Set Eingestellt durch.
	 * Discontinued By
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscontinuedBy (java.sql.Timestamp DiscontinuedBy);

	/**
	 * Get Eingestellt durch.
	 * Discontinued By
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDiscontinuedBy();

    /** Column definition for DiscontinuedBy */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_DiscontinuedBy = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "DiscontinuedBy", null);
    /** Column name DiscontinuedBy */
    public static final String COLUMNNAME_DiscontinuedBy = "DiscontinuedBy";

	/**
	 * Set Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNote (java.lang.String DocumentNote);

	/**
	 * Get Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNote();

    /** Column definition for DocumentNote */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_DocumentNote = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "DocumentNote", null);
    /** Column name DocumentNote */
    public static final String COLUMNNAME_DocumentNote = "DocumentNote";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalId();

    /** Column definition for ExternalId */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_ExternalId = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "ExternalId", null);
    /** Column name ExternalId */
    public static final String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_LineContent (java.lang.String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_LineContent();

    /** Column definition for I_LineContent */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_I_LineContent = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "I_LineContent", null);
    /** Column name I_LineContent */
    public static final String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getI_LineNo();

    /** Column definition for I_LineNo */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_I_LineNo = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "I_LineNo", null);
    /** Column name I_LineNo */
    public static final String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Import - Produkt.
	 * Import Item or Service
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_Product_ID (int I_Product_ID);

	/**
	 * Get Import - Produkt.
	 * Import Item or Service
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_Product_ID();

    /** Column definition for I_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_I_Product_ID = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "I_Product_ID", null);
    /** Column name I_Product_ID */
    public static final String COLUMNNAME_I_Product_ID = "I_Product_ID";

	/**
	 * Set Bild-URL.
	 * URL of  image
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImageURL (java.lang.String ImageURL);

	/**
	 * Get Bild-URL.
	 * URL of  image
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImageURL();

    /** Column definition for ImageURL */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_ImageURL = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "ImageURL", null);
    /** Column name ImageURL */
    public static final String COLUMNNAME_ImageURL = "ImageURL";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Verkauft.
	 * Die Organisation verkauft dieses Produkt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSold (boolean IsSold);

	/**
	 * Get Verkauft.
	 * Die Organisation verkauft dieses Produkt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSold();

    /** Column definition for IsSold */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_IsSold = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "IsSold", null);
    /** Column name IsSold */
    public static final String COLUMNNAME_IsSold = "IsSold";

	/**
	 * Set Lagerhaltig.
	 * Die Organisation hat dieses Produkt auf Lager
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsStocked (boolean IsStocked);

	/**
	 * Get Lagerhaltig.
	 * Die Organisation hat dieses Produkt auf Lager
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isStocked();

    /** Column definition for IsStocked */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_IsStocked = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "IsStocked", null);
    /** Column name IsStocked */
    public static final String COLUMNNAME_IsStocked = "IsStocked";

	/**
	 * Set Customs Tariff.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_CustomsTariff_ID (int M_CustomsTariff_ID);

	/**
	 * Get Customs Tariff.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_CustomsTariff_ID();

	public org.compiere.model.I_M_CustomsTariff getM_CustomsTariff();

	public void setM_CustomsTariff(org.compiere.model.I_M_CustomsTariff M_CustomsTariff);

    /** Column definition for M_CustomsTariff_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_M_CustomsTariff> COLUMN_M_CustomsTariff_ID = new org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_M_CustomsTariff>(I_I_Product.class, "M_CustomsTariff_ID", org.compiere.model.I_M_CustomsTariff.class);
    /** Column name M_CustomsTariff_ID */
    public static final String COLUMNNAME_M_CustomsTariff_ID = "M_CustomsTariff_ID";

	/**
	 * Set Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_Version_ID();

    /** Column name M_PriceList_Version_ID */
    public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/**
	 * Set Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_Version_Name (java.lang.String M_PriceList_Version_Name);

	/**
	 * Get Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getM_PriceList_Version_Name();

    /** Column definition for M_PriceList_Version_Name */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_M_PriceList_Version_Name = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "M_PriceList_Version_Name", null);
    /** Column name M_PriceList_Version_Name */
    public static final String COLUMNNAME_M_PriceList_Version_Name = "M_PriceList_Version_Name";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set M_ProductPlanningSchema_Selector.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ProductPlanningSchema_Selector (java.lang.String M_ProductPlanningSchema_Selector);

	/**
	 * Get M_ProductPlanningSchema_Selector.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getM_ProductPlanningSchema_Selector();

    /** Column definition for M_ProductPlanningSchema_Selector */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_M_ProductPlanningSchema_Selector = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "M_ProductPlanningSchema_Selector", null);
    /** Column name M_ProductPlanningSchema_Selector */
    public static final String COLUMNNAME_M_ProductPlanningSchema_Selector = "M_ProductPlanningSchema_Selector";

	/**
	 * Set Manufacturer.
	 * Manufacturer of the Product
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setManufacturer_ID (int Manufacturer_ID);

	/**
	 * Get Manufacturer.
	 * Manufacturer of the Product
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getManufacturer_ID();

    /** Column name Manufacturer_ID */
    public static final String COLUMNNAME_Manufacturer_ID = "Manufacturer_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Nettogewicht.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNetWeight (java.math.BigDecimal NetWeight);

	/**
	 * Get Nettogewicht.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getNetWeight();

    /** Column definition for NetWeight */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_NetWeight = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "NetWeight", null);
    /** Column name NetWeight */
    public static final String COLUMNNAME_NetWeight = "NetWeight";

	/**
	 * Set Mindestbestellmenge.
	 * Minimum order quantity in UOM
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrder_Min (int Order_Min);

	/**
	 * Get Mindestbestellmenge.
	 * Minimum order quantity in UOM
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOrder_Min();

    /** Column definition for Order_Min */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Order_Min = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Order_Min", null);
    /** Column name Order_Min */
    public static final String COLUMNNAME_Order_Min = "Order_Min";

	/**
	 * Set Packungsgröße.
	 * Package order size in UOM (e.g. order set of 5 units)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrder_Pack (int Order_Pack);

	/**
	 * Get Packungsgröße.
	 * Package order size in UOM (e.g. order set of 5 units)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOrder_Pack();

    /** Column definition for Order_Pack */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Order_Pack = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Order_Pack", null);
    /** Column name Order_Pack */
    public static final String COLUMNNAME_Order_Pack = "Order_Pack";

	/**
	 * Set Package UOM.
	 * UOM of the package
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackage_UOM_ID (int Package_UOM_ID);

	/**
	 * Get Package UOM.
	 * UOM of the package
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPackage_UOM_ID();

    /** Column name Package_UOM_ID */
    public static final String COLUMNNAME_Package_UOM_ID = "Package_UOM_ID";

	/**
	 * Set Package Size.
	 * Size of a package
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackageSize (java.lang.String PackageSize);

	/**
	 * Get Package Size.
	 * Size of a package
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPackageSize();

    /** Column definition for PackageSize */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_PackageSize = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "PackageSize", null);
    /** Column name PackageSize */
    public static final String COLUMNNAME_PackageSize = "PackageSize";

	/**
	 * Set Preis gültig.
	 * Effective Date of Price
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceEffective (java.sql.Timestamp PriceEffective);

	/**
	 * Get Preis gültig.
	 * Effective Date of Price
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPriceEffective();

    /** Column definition for PriceEffective */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_PriceEffective = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "PriceEffective", null);
    /** Column name PriceEffective */
    public static final String COLUMNNAME_PriceEffective = "PriceEffective";

	/**
	 * Set Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceLimit (java.math.BigDecimal PriceLimit);

	/**
	 * Get Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceLimit();

    /** Column definition for PriceLimit */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_PriceLimit = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "PriceLimit", null);
    /** Column name PriceLimit */
    public static final String COLUMNNAME_PriceLimit = "PriceLimit";

	/**
	 * Set Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceList (java.math.BigDecimal PriceList);

	/**
	 * Get Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceList();

    /** Column definition for PriceList */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_PriceList = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "PriceList", null);
    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Einkaufspreis.
	 * Price based on a purchase order
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPricePO (java.math.BigDecimal PricePO);

	/**
	 * Get Einkaufspreis.
	 * Price based on a purchase order
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPricePO();

    /** Column definition for PricePO */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_PricePO = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "PricePO", null);
    /** Column name PricePO */
    public static final String COLUMNNAME_PricePO = "PricePO";

	/**
	 * Set Standard Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceStd (java.math.BigDecimal PriceStd);

	/**
	 * Get Standard Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceStd();

    /** Column definition for PriceStd */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_PriceStd = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "PriceStd", null);
    /** Column name PriceStd */
    public static final String COLUMNNAME_PriceStd = "PriceStd";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Produktkategorie-Schlüssel.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductCategory_Value (java.lang.String ProductCategory_Value);

	/**
	 * Get Produktkategorie-Schlüssel.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductCategory_Value();

    /** Column definition for ProductCategory_Value */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_ProductCategory_Value = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "ProductCategory_Value", null);
    /** Column name ProductCategory_Value */
    public static final String COLUMNNAME_ProductCategory_Value = "ProductCategory_Value";

	/**
	 * Set Product Manufacturer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductManufacturer (java.lang.String ProductManufacturer);

	/**
	 * Get Product Manufacturer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductManufacturer();

    /** Column definition for ProductManufacturer */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_ProductManufacturer = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "ProductManufacturer", null);
    /** Column name ProductManufacturer */
    public static final String COLUMNNAME_ProductManufacturer = "ProductManufacturer";

	/**
	 * Set Produktart.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductType (java.lang.String ProductType);

	/**
	 * Get Produktart.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductType();

    /** Column definition for ProductType */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_ProductType = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "ProductType", null);
    /** Column name ProductType */
    public static final String COLUMNNAME_ProductType = "ProductType";

	/**
	 * Set Ursprungsland.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRawMaterialOrigin_ID (int RawMaterialOrigin_ID);

	/**
	 * Get Ursprungsland.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRawMaterialOrigin_ID();

	public org.compiere.model.I_C_Country getRawMaterialOrigin();

	public void setRawMaterialOrigin(org.compiere.model.I_C_Country RawMaterialOrigin);

    /** Column definition for RawMaterialOrigin_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_C_Country> COLUMN_RawMaterialOrigin_ID = new org.adempiere.model.ModelColumn<I_I_Product, org.compiere.model.I_C_Country>(I_I_Product.class, "RawMaterialOrigin_ID", org.compiere.model.I_C_Country.class);
    /** Column name RawMaterialOrigin_ID */
    public static final String COLUMNNAME_RawMaterialOrigin_ID = "RawMaterialOrigin_ID";

	/**
	 * Set Raw Material Origin Country Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRawMaterialOriginCountryCode (java.lang.String RawMaterialOriginCountryCode);

	/**
	 * Get Raw Material Origin Country Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRawMaterialOriginCountryCode();

    /** Column definition for RawMaterialOriginCountryCode */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_RawMaterialOriginCountryCode = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "RawMaterialOriginCountryCode", null);
    /** Column name RawMaterialOriginCountryCode */
    public static final String COLUMNNAME_RawMaterialOriginCountryCode = "RawMaterialOriginCountryCode";

	/**
	 * Set Lizenzbetrag.
	 * (Included) Amount for copyright, etc.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRoyaltyAmt (java.math.BigDecimal RoyaltyAmt);

	/**
	 * Get Lizenzbetrag.
	 * (Included) Amount for copyright, etc.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRoyaltyAmt();

    /** Column definition for RoyaltyAmt */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_RoyaltyAmt = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "RoyaltyAmt", null);
    /** Column name RoyaltyAmt */
    public static final String COLUMNNAME_RoyaltyAmt = "RoyaltyAmt";

	/**
	 * Set Regaltiefe.
	 * Shelf depth required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShelfDepth (int ShelfDepth);

	/**
	 * Get Regaltiefe.
	 * Shelf depth required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getShelfDepth();

    /** Column definition for ShelfDepth */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_ShelfDepth = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "ShelfDepth", null);
    /** Column name ShelfDepth */
    public static final String COLUMNNAME_ShelfDepth = "ShelfDepth";

	/**
	 * Set Regalhöhe.
	 * Shelf height required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShelfHeight (int ShelfHeight);

	/**
	 * Get Regalhöhe.
	 * Shelf height required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getShelfHeight();

    /** Column definition for ShelfHeight */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_ShelfHeight = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "ShelfHeight", null);
    /** Column name ShelfHeight */
    public static final String COLUMNNAME_ShelfHeight = "ShelfHeight";

	/**
	 * Set Regalbreite.
	 * Shelf width required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShelfWidth (int ShelfWidth);

	/**
	 * Get Regalbreite.
	 * Shelf width required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getShelfWidth();

    /** Column definition for ShelfWidth */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_ShelfWidth = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "ShelfWidth", null);
    /** Column name ShelfWidth */
    public static final String COLUMNNAME_ShelfWidth = "ShelfWidth";

	/**
	 * Set SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSKU (java.lang.String SKU);

	/**
	 * Get SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSKU();

    /** Column definition for SKU */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_SKU = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "SKU", null);
    /** Column name SKU */
    public static final String COLUMNNAME_SKU = "SKU";

	/**
	 * Set Einheiten pro Palette.
	 * Units Per Pallet
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUnitsPerPallet (int UnitsPerPallet);

	/**
	 * Get Einheiten pro Palette.
	 * Units Per Pallet
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUnitsPerPallet();

    /** Column definition for UnitsPerPallet */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_UnitsPerPallet = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "UnitsPerPallet", null);
    /** Column name UnitsPerPallet */
    public static final String COLUMNNAME_UnitsPerPallet = "UnitsPerPallet";

	/**
	 * Set UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC (java.lang.String UPC);

	/**
	 * Get UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC();

    /** Column definition for UPC */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Vendor Category.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVendorCategory (java.lang.String VendorCategory);

	/**
	 * Get Vendor Category.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVendorCategory();

    /** Column definition for VendorCategory */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_VendorCategory = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "VendorCategory", null);
    /** Column name VendorCategory */
    public static final String COLUMNNAME_VendorCategory = "VendorCategory";

	/**
	 * Set Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVendorProductNo (java.lang.String VendorProductNo);

	/**
	 * Get Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVendorProductNo();

    /** Column definition for VendorProductNo */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_VendorProductNo = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "VendorProductNo", null);
    /** Column name VendorProductNo */
    public static final String COLUMNNAME_VendorProductNo = "VendorProductNo";

	/**
	 * Set Volume.
	 * Volume of a product
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVolume (int Volume);

	/**
	 * Get Volume.
	 * Volume of a product
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getVolume();

    /** Column definition for Volume */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Volume = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Volume", null);
    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/**
	 * Set Weight.
	 * Weight of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWeight (java.math.BigDecimal Weight);

	/**
	 * Get Weight.
	 * Weight of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getWeight();

    /** Column definition for Weight */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_Weight = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "Weight", null);
    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/**
	 * Set Kodierung der Mengeneinheit.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setX12DE355 (java.lang.String X12DE355);

	/**
	 * Get Kodierung der Mengeneinheit.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getX12DE355();

    /** Column definition for X12DE355 */
    public static final org.adempiere.model.ModelColumn<I_I_Product, Object> COLUMN_X12DE355 = new org.adempiere.model.ModelColumn<I_I_Product, Object>(I_I_Product.class, "X12DE355", null);
    /** Column name X12DE355 */
    public static final String COLUMNNAME_X12DE355 = "X12DE355";
}
