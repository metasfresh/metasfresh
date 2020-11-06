package de.metas.esb.edi.model;


/** Generated Interface for EDI_Desadv
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_Desadv 
{

    /** TableName=EDI_Desadv */
    public static final String Table_Name = "EDI_Desadv";

    /** AD_Table_ID=540644 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_Location_ID();

    /** Column name Bill_Location_ID */
    public static final String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

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
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Ship Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/**
	 * Get Ship Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_BPartner_ID();

    /** Column name DropShip_BPartner_ID */
    public static final String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/**
	 * Set Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_Location_ID (int DropShip_Location_ID);

	/**
	 * Get Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_Location_ID();

    /** Column name DropShip_Location_ID */
    public static final String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/**
	 * Set DESADV.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_Desadv_ID (int EDI_Desadv_ID);

	/**
	 * Get DESADV.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_Desadv_ID();

    /** Column definition for EDI_Desadv_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_EDI_Desadv_ID = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "EDI_Desadv_ID", null);
    /** Column name EDI_Desadv_ID */
    public static final String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";

	/**
	 * Set EDI Fehlermeldung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDIErrorMsg (java.lang.String EDIErrorMsg);

	/**
	 * Get EDI Fehlermeldung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEDIErrorMsg();

    /** Column definition for EDIErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_EDIErrorMsg = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "EDIErrorMsg", null);
    /** Column name EDIErrorMsg */
    public static final String COLUMNNAME_EDIErrorMsg = "EDIErrorMsg";

	/**
	 * Set EDI Status Exportieren.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_ExportStatus (java.lang.String EDI_ExportStatus);

	/**
	 * Get EDI Status Exportieren.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEDI_ExportStatus();

    /** Column definition for EDI_ExportStatus */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_EDI_ExportStatus = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "EDI_ExportStatus", null);
    /** Column name EDI_ExportStatus */
    public static final String COLUMNNAME_EDI_ExportStatus = "EDI_ExportStatus";

	/**
	 * Set Geliefert %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFulfillmentPercent (java.math.BigDecimal FulfillmentPercent);

	/**
	 * Get Geliefert %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFulfillmentPercent();

    /** Column definition for FulfillmentPercent */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_FulfillmentPercent = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "FulfillmentPercent", null);
    /** Column name FulfillmentPercent */
    public static final String COLUMNNAME_FulfillmentPercent = "FulfillmentPercent";

	/**
	 * Set Geliefert % Minimum.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFulfillmentPercentMin (java.math.BigDecimal FulfillmentPercentMin);

	/**
	 * Get Geliefert % Minimum.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFulfillmentPercentMin();

    /** Column definition for FulfillmentPercentMin */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_FulfillmentPercentMin = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "FulfillmentPercentMin", null);
    /** Column name FulfillmentPercentMin */
    public static final String COLUMNNAME_FulfillmentPercentMin = "FulfillmentPercentMin";

	/**
	 * Set Übergabeadresse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHandOver_Location_ID (int HandOver_Location_ID);

	/**
	 * Get Übergabeadresse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHandOver_Location_ID();

    /** Column name HandOver_Location_ID */
    public static final String COLUMNNAME_HandOver_Location_ID = "HandOver_Location_ID";

	/**
	 * Set Handover partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHandOver_Partner_ID (int HandOver_Partner_ID);

	/**
	 * Get Handover partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHandOver_Partner_ID();

    /** Column name HandOver_Partner_ID */
    public static final String COLUMNNAME_HandOver_Partner_ID = "HandOver_Partner_ID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMovementDate();

    /** Column definition for MovementDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_MovementDate = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "MovementDate", null);
    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPOReference (java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set SumDeliveredInStockingUOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSumDeliveredInStockingUOM (java.math.BigDecimal SumDeliveredInStockingUOM);

	/**
	 * Get SumDeliveredInStockingUOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getSumDeliveredInStockingUOM();

    /** Column definition for SumDeliveredInStockingUOM */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_SumDeliveredInStockingUOM = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "SumDeliveredInStockingUOM", null);
    /** Column name SumDeliveredInStockingUOM */
    public static final String COLUMNNAME_SumDeliveredInStockingUOM = "SumDeliveredInStockingUOM";

	/**
	 * Set SumOrderedInStockingUOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSumOrderedInStockingUOM (java.math.BigDecimal SumOrderedInStockingUOM);

	/**
	 * Get SumOrderedInStockingUOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getSumOrderedInStockingUOM();

    /** Column definition for SumOrderedInStockingUOM */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_SumOrderedInStockingUOM = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "SumOrderedInStockingUOM", null);
    /** Column name SumOrderedInStockingUOM */
    public static final String COLUMNNAME_SumOrderedInStockingUOM = "SumOrderedInStockingUOM";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Nutzer 1.
	 * Can be used to flag records and thus make them selectable from the UI via advanced search.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserFlag (java.lang.String UserFlag);

	/**
	 * Get Nutzer 1.
	 * Can be used to flag records and thus make them selectable from the UI via advanced search.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserFlag();

    /** Column definition for UserFlag */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv, Object> COLUMN_UserFlag = new org.adempiere.model.ModelColumn<I_EDI_Desadv, Object>(I_EDI_Desadv.class, "UserFlag", null);
    /** Column name UserFlag */
    public static final String COLUMNNAME_UserFlag = "UserFlag";
}
