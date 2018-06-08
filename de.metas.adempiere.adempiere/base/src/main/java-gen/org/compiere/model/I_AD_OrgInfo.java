package org.compiere.model;


/** Generated Interface for AD_OrgInfo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_OrgInfo 
{

    /** TableName=AD_OrgInfo */
    public static final String Table_Name = "AD_OrgInfo";

    /** AD_Table_ID=228 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Client>(I_AD_OrgInfo.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Org>(I_AD_OrgInfo.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Organization Type.
	 * Organization Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgType_ID (int AD_OrgType_ID);

	/**
	 * Get Organization Type.
	 * Organization Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgType_ID();

	public org.compiere.model.I_AD_OrgType getAD_OrgType();

	public void setAD_OrgType(org.compiere.model.I_AD_OrgType AD_OrgType);

    /** Column definition for AD_OrgType_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_OrgType> COLUMN_AD_OrgType_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_OrgType>(I_AD_OrgInfo.class, "AD_OrgType_ID", org.compiere.model.I_AD_OrgType.class);
    /** Column name AD_OrgType_ID */
    public static final String COLUMNNAME_AD_OrgType_ID = "AD_OrgType_ID";

	/**
	 * Set Kalender.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Kalender.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Calendar_ID();

	public org.compiere.model.I_C_Calendar getC_Calendar();

	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar);

    /** Column definition for C_Calendar_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_Calendar>(I_AD_OrgInfo.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
    /** Column name C_Calendar_ID */
    public static final String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Set Anschrift.
	 * Adresse oder Anschrift
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Anschrift.
	 * Adresse oder Anschrift
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Location_ID();

	public org.compiere.model.I_C_Location getC_Location();

	public void setC_Location(org.compiere.model.I_C_Location C_Location);

    /** Column definition for C_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_Location>(I_AD_OrgInfo.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_User>(I_AD_OrgInfo.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Drop Ship Warehouse.
	 * The (logical) warehouse to use for recording drop ship receipts and shipments.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_Warehouse_ID (int DropShip_Warehouse_ID);

	/**
	 * Get Drop Ship Warehouse.
	 * The (logical) warehouse to use for recording drop ship receipts and shipments.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getDropShip_Warehouse();

	public void setDropShip_Warehouse(org.compiere.model.I_M_Warehouse DropShip_Warehouse);

    /** Column definition for DropShip_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_M_Warehouse> COLUMN_DropShip_Warehouse_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_M_Warehouse>(I_AD_OrgInfo.class, "DropShip_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name DropShip_Warehouse_ID */
    public static final String COLUMNNAME_DropShip_Warehouse_ID = "DropShip_Warehouse_ID";

	/**
	 * Set D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDUNS (java.lang.String DUNS);

	/**
	 * Get D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDUNS();

    /** Column definition for DUNS */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_DUNS = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "DUNS", null);
    /** Column name DUNS */
    public static final String COLUMNNAME_DUNS = "DUNS";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLogo_ID (int Logo_ID);

	/**
	 * Get Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLogo_ID();

	public org.compiere.model.I_AD_Image getLogo();

	public void setLogo(org.compiere.model.I_AD_Image Logo);

    /** Column definition for Logo_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Image> COLUMN_Logo_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Image>(I_AD_OrgInfo.class, "Logo_ID", org.compiere.model.I_AD_Image.class);
    /** Column name Logo_ID */
    public static final String COLUMNNAME_Logo_ID = "Logo_ID";

	/**
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_M_Warehouse>(I_AD_OrgInfo.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Liefer-Lager.
	 * Lager, an das der Lieferant eine Bestellung liefern soll.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_WarehousePO_ID (int M_WarehousePO_ID);

	/**
	 * Get Liefer-Lager.
	 * Lager, an das der Lieferant eine Bestellung liefern soll.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_WarehousePO_ID();

	public org.compiere.model.I_M_Warehouse getM_WarehousePO();

	public void setM_WarehousePO(org.compiere.model.I_M_Warehouse M_WarehousePO);

    /** Column definition for M_WarehousePO_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_M_Warehouse> COLUMN_M_WarehousePO_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_M_Warehouse>(I_AD_OrgInfo.class, "M_WarehousePO_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_WarehousePO_ID */
    public static final String COLUMNNAME_M_WarehousePO_ID = "M_WarehousePO_ID";

	/**
	 * Set Übergeordnete Organisation.
	 * Parent (superior) Organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setParent_Org_ID (int Parent_Org_ID);

	/**
	 * Get Übergeordnete Organisation.
	 * Parent (superior) Organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getParent_Org_ID();

	public org.compiere.model.I_AD_Org getParent_Org();

	public void setParent_Org(org.compiere.model.I_AD_Org Parent_Org);

    /** Column definition for Parent_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Org> COLUMN_Parent_Org_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Org>(I_AD_OrgInfo.class, "Parent_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name Parent_Org_ID */
    public static final String COLUMNNAME_Parent_Org_ID = "Parent_Org_ID";

	/**
	 * Set Receipt Footer Msg.
	 * This message will be displayed at the bottom of a receipt when doing a sales or purchase
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReceiptFooterMsg (java.lang.String ReceiptFooterMsg);

	/**
	 * Get Receipt Footer Msg.
	 * This message will be displayed at the bottom of a receipt when doing a sales or purchase
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReceiptFooterMsg();

    /** Column definition for ReceiptFooterMsg */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_ReceiptFooterMsg = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "ReceiptFooterMsg", null);
    /** Column name ReceiptFooterMsg */
    public static final String COLUMNNAME_ReceiptFooterMsg = "ReceiptFooterMsg";

	/**
	 * Set Berichts-Präfix.
	 * Präfix, der (Jasper-)Berichten und Belegen vorangestellt wird, wenn diese Abgerufen werden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReportPrefix (java.lang.String ReportPrefix);

	/**
	 * Get Berichts-Präfix.
	 * Präfix, der (Jasper-)Berichten und Belegen vorangestellt wird, wenn diese Abgerufen werden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReportPrefix();

    /** Column definition for ReportPrefix */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_ReportPrefix = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "ReportPrefix", null);
    /** Column name ReportPrefix */
    public static final String COLUMNNAME_ReportPrefix = "ReportPrefix";

	/**
	 * Set Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSupervisor_ID (int Supervisor_ID);

	/**
	 * Get Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSupervisor_ID();

	public org.compiere.model.I_AD_User getSupervisor();

	public void setSupervisor(org.compiere.model.I_AD_User Supervisor);

    /** Column definition for Supervisor_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_User> COLUMN_Supervisor_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_User>(I_AD_OrgInfo.class, "Supervisor_ID", org.compiere.model.I_AD_User.class);
    /** Column name Supervisor_ID */
    public static final String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

	/**
	 * Set Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTaxID (java.lang.String TaxID);

	/**
	 * Get Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTaxID();

    /** Column definition for TaxID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_TaxID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "TaxID", null);
    /** Column name TaxID */
    public static final String COLUMNNAME_TaxID = "TaxID";

	/**
	 * Set Bank for transfers.
	 * Bank account depending on currency will be used from this bank for doing transfers
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTransferBank_ID (int TransferBank_ID);

	/**
	 * Get Bank for transfers.
	 * Bank account depending on currency will be used from this bank for doing transfers
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getTransferBank_ID();

	public org.compiere.model.I_C_Bank getTransferBank();

	public void setTransferBank(org.compiere.model.I_C_Bank TransferBank);

    /** Column definition for TransferBank_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_Bank> COLUMN_TransferBank_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_Bank>(I_AD_OrgInfo.class, "TransferBank_ID", org.compiere.model.I_C_Bank.class);
    /** Column name TransferBank_ID */
    public static final String COLUMNNAME_TransferBank_ID = "TransferBank_ID";

	/**
	 * Set CashBook for transfers.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTransferCashBook_ID (int TransferCashBook_ID);

	/**
	 * Get CashBook for transfers.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getTransferCashBook_ID();

	public org.compiere.model.I_C_CashBook getTransferCashBook();

	public void setTransferCashBook(org.compiere.model.I_C_CashBook TransferCashBook);

    /** Column definition for TransferCashBook_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_CashBook> COLUMN_TransferCashBook_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_CashBook>(I_AD_OrgInfo.class, "TransferCashBook_ID", org.compiere.model.I_C_CashBook.class);
    /** Column name TransferCashBook_ID */
    public static final String COLUMNNAME_TransferCashBook_ID = "TransferCashBook_ID";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_User>(I_AD_OrgInfo.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
