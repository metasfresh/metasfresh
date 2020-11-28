package org.compiere.model;


/** Generated Interface for AD_OrgInfo
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_OrgInfo 
{

    /** TableName=AD_OrgInfo */
    public static final String Table_Name = "AD_OrgInfo";

    /** AD_Table_ID=228 */
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
	 * Set AD_OrgInfo_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgInfo_ID (int AD_OrgInfo_ID);

	/**
	 * Get AD_OrgInfo_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgInfo_ID();

    /** Column definition for AD_OrgInfo_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_AD_OrgInfo_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "AD_OrgInfo_ID", null);
    /** Column name AD_OrgInfo_ID */
    public static final String COLUMNNAME_AD_OrgInfo_ID = "AD_OrgInfo_ID";

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
	 * Set Workflow - Verantwortlicher.
	 * Verantwortlicher für die Ausführung des Workflow
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/**
	 * Get Workflow - Verantwortlicher.
	 * Verantwortlicher für die Ausführung des Workflow
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Responsible_ID();

	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible();

	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible);

    /** Column definition for AD_WF_Responsible_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_WF_Responsible> COLUMN_AD_WF_Responsible_ID = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_WF_Responsible>(I_AD_OrgInfo.class, "AD_WF_Responsible_ID", org.compiere.model.I_AD_WF_Responsible.class);
    /** Column name AD_WF_Responsible_ID */
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/**
	 * Set Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Calendar.
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
	 * Get Created.
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

    /** Column name DropShip_Warehouse_ID */
    public static final String COLUMNNAME_DropShip_Warehouse_ID = "DropShip_Warehouse_ID";

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
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PricingSystem_ID();

    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

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

    /** Column name M_WarehousePO_ID */
    public static final String COLUMNNAME_M_WarehousePO_ID = "M_WarehousePO_ID";

	/**
	 * Set Org BPartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrg_BPartner_ID (int Org_BPartner_ID);

	/**
	 * Get Org BPartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOrg_BPartner_ID();

    /** Column name Org_BPartner_ID */
    public static final String COLUMNNAME_Org_BPartner_ID = "Org_BPartner_ID";

	/**
	 * Set OrgBP_Location_ID.
	 * Default BP Location linked to the org.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrgBP_Location_ID (int OrgBP_Location_ID);

	/**
	 * Get OrgBP_Location_ID.
	 * Default BP Location linked to the org.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOrgBP_Location_ID();

    /** Column name OrgBP_Location_ID */
    public static final String COLUMNNAME_OrgBP_Location_ID = "OrgBP_Location_ID";

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

    /** Column name Parent_Org_ID */
    public static final String COLUMNNAME_Parent_Org_ID = "Parent_Org_ID";

	/**
	 * Set Receipt Footer Msg.
	 * This message will be displayed at the bottom of a receipt when doing a sales or purchase
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReceiptFooterMsg (java.lang.String ReceiptFooterMsg);

	/**
	 * Get Receipt Footer Msg.
	 * This message will be displayed at the bottom of a receipt when doing a sales or purchase
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReportPrefix (java.lang.String ReportPrefix);

	/**
	 * Get Berichts-Präfix.
	 * Präfix, der (Jasper-)Berichten und Belegen vorangestellt wird, wenn diese Abgerufen werden
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReportPrefix();

    /** Column definition for ReportPrefix */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_ReportPrefix = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "ReportPrefix", null);
    /** Column name ReportPrefix */
    public static final String COLUMNNAME_ReportPrefix = "ReportPrefix";

	/**
	 * Set Speichung Kreditkartendaten.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStoreCreditCardData (java.lang.String StoreCreditCardData);

	/**
	 * Get Speichung Kreditkartendaten.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStoreCreditCardData();

    /** Column definition for StoreCreditCardData */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_StoreCreditCardData = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "StoreCreditCardData", null);
    /** Column name StoreCreditCardData */
    public static final String COLUMNNAME_StoreCreditCardData = "StoreCreditCardData";

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

    /** Column name Supervisor_ID */
    public static final String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

	/**
	 * Set Time Zone.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTimeZone (java.lang.String TimeZone);

	/**
	 * Get Time Zone.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTimeZone();

    /** Column definition for TimeZone */
    public static final org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object> COLUMN_TimeZone = new org.adempiere.model.ModelColumn<I_AD_OrgInfo, Object>(I_AD_OrgInfo.class, "TimeZone", null);
    /** Column name TimeZone */
    public static final String COLUMNNAME_TimeZone = "TimeZone";

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
	 * Get Updated.
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
}
