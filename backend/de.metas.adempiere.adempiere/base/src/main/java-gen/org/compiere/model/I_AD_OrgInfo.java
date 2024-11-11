package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_OrgInfo
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_OrgInfo 
{

	String Table_Name = "AD_OrgInfo";

//	/** AD_Table_ID=228 */
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
	 * Set AD_OrgInfo_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_OrgInfo_ID (int AD_OrgInfo_ID);

	/**
	 * Get AD_OrgInfo_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_OrgInfo_ID();

	ModelColumn<I_AD_OrgInfo, Object> COLUMN_AD_OrgInfo_ID = new ModelColumn<>(I_AD_OrgInfo.class, "AD_OrgInfo_ID", null);
	String COLUMNNAME_AD_OrgInfo_ID = "AD_OrgInfo_ID";

	/**
	 * Set Organization Type.
	 * Organization Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgType_ID (int AD_OrgType_ID);

	/**
	 * Get Organization Type.
	 * Organization Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgType_ID();

	@Nullable org.compiere.model.I_AD_OrgType getAD_OrgType();

	void setAD_OrgType(@Nullable org.compiere.model.I_AD_OrgType AD_OrgType);

	ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_OrgType> COLUMN_AD_OrgType_ID = new ModelColumn<>(I_AD_OrgInfo.class, "AD_OrgType_ID", org.compiere.model.I_AD_OrgType.class);
	String COLUMNNAME_AD_OrgType_ID = "AD_OrgType_ID";

	/**
	 * Set Workflow - Verantwortlicher.
	 * Verantwortlicher für die Ausführung des Workflow
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/**
	 * Get Workflow - Verantwortlicher.
	 * Verantwortlicher für die Ausführung des Workflow
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Responsible_ID();

	String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/**
	 * Set Notification group for externally recorded partners.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID (int C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID);

	/**
	 * Get Notification group for externally recorded partners.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID();

	@Nullable org.compiere.model.I_AD_UserGroup getC_BPartner_CreatedFromAnotherOrg_Notify_UserGroup();

	void setC_BPartner_CreatedFromAnotherOrg_Notify_UserGroup(@Nullable org.compiere.model.I_AD_UserGroup C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup);

	ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_UserGroup> COLUMN_C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID = new ModelColumn<>(I_AD_OrgInfo.class, "C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID", org.compiere.model.I_AD_UserGroup.class);
	String COLUMNNAME_C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID = "C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID";

	/**
	 * Set Notification group for expiring supplier approvals.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_SupplierApproval_Expiration_Notify_UserGroup_ID (int C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID);

	/**
	 * Get Notification group for expiring supplier approvals.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_SupplierApproval_Expiration_Notify_UserGroup_ID();

	@Nullable org.compiere.model.I_AD_UserGroup getC_BP_SupplierApproval_Expiration_Notify_UserGroup();

	void setC_BP_SupplierApproval_Expiration_Notify_UserGroup(@Nullable org.compiere.model.I_AD_UserGroup C_BP_SupplierApproval_Expiration_Notify_UserGroup);

	ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_UserGroup> COLUMN_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID = new ModelColumn<>(I_AD_OrgInfo.class, "C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID", org.compiere.model.I_AD_UserGroup.class);
	String COLUMNNAME_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID = "C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID";

	/**
	 * Set Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Calendar_ID();

	@Nullable org.compiere.model.I_C_Calendar getC_Calendar();

	void setC_Calendar(@Nullable org.compiere.model.I_C_Calendar C_Calendar);

	ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new ModelColumn<>(I_AD_OrgInfo.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_OrgInfo, Object> COLUMN_Created = new ModelColumn<>(I_AD_OrgInfo.class, "Created", null);
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
	 * Set Drop Ship Warehouse.
	 * The (logical) warehouse to use for recording drop ship receipts and shipments.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_Warehouse_ID (int DropShip_Warehouse_ID);

	/**
	 * Get Drop Ship Warehouse.
	 * The (logical) warehouse to use for recording drop ship receipts and shipments.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_Warehouse_ID();

	String COLUMNNAME_DropShip_Warehouse_ID = "DropShip_Warehouse_ID";

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

	ModelColumn<I_AD_OrgInfo, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_OrgInfo.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Automatische Abrechnung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoInvoiceFlatrateTerm (boolean IsAutoInvoiceFlatrateTerm);

	/**
	 * Get Automatische Abrechnung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoInvoiceFlatrateTerm();

	ModelColumn<I_AD_OrgInfo, Object> COLUMN_IsAutoInvoiceFlatrateTerm = new ModelColumn<>(I_AD_OrgInfo.class, "IsAutoInvoiceFlatrateTerm", null);
	String COLUMNNAME_IsAutoInvoiceFlatrateTerm = "IsAutoInvoiceFlatrateTerm";

	/**
	 * Set Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLogo_ID (int Logo_ID);

	/**
	 * Get Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLogo_ID();

	@Nullable org.compiere.model.I_AD_Image getLogo();

	void setLogo(@Nullable org.compiere.model.I_AD_Image Logo);

	ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Image> COLUMN_Logo_ID = new ModelColumn<>(I_AD_OrgInfo.class, "Logo_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_Logo_ID = "Logo_ID";

	/**
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Liefer-Lager.
	 * Lager, an das der Lieferant eine Bestellung liefern soll.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_WarehousePO_ID (int M_WarehousePO_ID);

	/**
	 * Get Liefer-Lager.
	 * Lager, an das der Lieferant eine Bestellung liefern soll.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_WarehousePO_ID();

	String COLUMNNAME_M_WarehousePO_ID = "M_WarehousePO_ID";

	/**
	 * Set Org BPartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrg_BPartner_ID (int Org_BPartner_ID);

	/**
	 * Get Org BPartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrg_BPartner_ID();

	String COLUMNNAME_Org_BPartner_ID = "Org_BPartner_ID";

	/**
	 * Set OrgBP_Location_ID.
	 * Default BP Location linked to the org.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrgBP_Location_ID (int OrgBP_Location_ID);

	/**
	 * Get OrgBP_Location_ID.
	 * Default BP Location linked to the org.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrgBP_Location_ID();

	String COLUMNNAME_OrgBP_Location_ID = "OrgBP_Location_ID";

	/**
	 * Set Übergeordnete Organisation.
	 * Parent (superior) Organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParent_Org_ID (int Parent_Org_ID);

	/**
	 * Get Übergeordnete Organisation.
	 * Parent (superior) Organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParent_Org_ID();

	String COLUMNNAME_Parent_Org_ID = "Parent_Org_ID";

	/**
	 * Set Receipt Footer Msg.
	 * This message will be displayed at the bottom of a receipt when doing a sales or purchase
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiptFooterMsg (@Nullable java.lang.String ReceiptFooterMsg);

	/**
	 * Get Receipt Footer Msg.
	 * This message will be displayed at the bottom of a receipt when doing a sales or purchase
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiptFooterMsg();

	ModelColumn<I_AD_OrgInfo, Object> COLUMN_ReceiptFooterMsg = new ModelColumn<>(I_AD_OrgInfo.class, "ReceiptFooterMsg", null);
	String COLUMNNAME_ReceiptFooterMsg = "ReceiptFooterMsg";

	/**
	 * Set Report Bottom Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReportBottom_Logo_ID (int ReportBottom_Logo_ID);

	/**
	 * Get Report Bottom Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReportBottom_Logo_ID();

	@Nullable org.compiere.model.I_AD_Image getReportBottom_Logo();

	void setReportBottom_Logo(@Nullable org.compiere.model.I_AD_Image ReportBottom_Logo);

	ModelColumn<I_AD_OrgInfo, org.compiere.model.I_AD_Image> COLUMN_ReportBottom_Logo_ID = new ModelColumn<>(I_AD_OrgInfo.class, "ReportBottom_Logo_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_ReportBottom_Logo_ID = "ReportBottom_Logo_ID";

	/**
	 * Set Berichts-Präfix.
	 * Präfix, der (Jasper-)Berichten und Belegen vorangestellt wird, wenn diese Abgerufen werden
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReportPrefix (java.lang.String ReportPrefix);

	/**
	 * Get Berichts-Präfix.
	 * Präfix, der (Jasper-)Berichten und Belegen vorangestellt wird, wenn diese Abgerufen werden
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReportPrefix();

	ModelColumn<I_AD_OrgInfo, Object> COLUMN_ReportPrefix = new ModelColumn<>(I_AD_OrgInfo.class, "ReportPrefix", null);
	String COLUMNNAME_ReportPrefix = "ReportPrefix";

	/**
	 * Set Speichung Kreditkartendaten.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStoreCreditCardData (java.lang.String StoreCreditCardData);

	/**
	 * Get Speichung Kreditkartendaten.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStoreCreditCardData();

	ModelColumn<I_AD_OrgInfo, Object> COLUMN_StoreCreditCardData = new ModelColumn<>(I_AD_OrgInfo.class, "StoreCreditCardData", null);
	String COLUMNNAME_StoreCreditCardData = "StoreCreditCardData";

	/**
	 * Set Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupervisor_ID (int Supervisor_ID);

	/**
	 * Get Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSupervisor_ID();

	String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

	/**
	 * Set Time Zone.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTimeZone (@Nullable java.lang.String TimeZone);

	/**
	 * Get Time Zone.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTimeZone();

	ModelColumn<I_AD_OrgInfo, Object> COLUMN_TimeZone = new ModelColumn<>(I_AD_OrgInfo.class, "TimeZone", null);
	String COLUMNNAME_TimeZone = "TimeZone";

	/**
	 * Set Bank for transfers.
	 * Bank account depending on currency will be used from this bank for doing transfers
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTransferBank_ID (int TransferBank_ID);

	/**
	 * Get Bank for transfers.
	 * Bank account depending on currency will be used from this bank for doing transfers
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getTransferBank_ID();

	String COLUMNNAME_TransferBank_ID = "TransferBank_ID";

	/**
	 * Set CashBook for transfers.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTransferCashBook_ID (int TransferCashBook_ID);

	/**
	 * Get CashBook for transfers.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getTransferCashBook_ID();

	@Nullable org.compiere.model.I_C_CashBook getTransferCashBook();

	void setTransferCashBook(@Nullable org.compiere.model.I_C_CashBook TransferCashBook);

	ModelColumn<I_AD_OrgInfo, org.compiere.model.I_C_CashBook> COLUMN_TransferCashBook_ID = new ModelColumn<>(I_AD_OrgInfo.class, "TransferCashBook_ID", org.compiere.model.I_C_CashBook.class);
	String COLUMNNAME_TransferCashBook_ID = "TransferCashBook_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_OrgInfo, Object> COLUMN_Updated = new ModelColumn<>(I_AD_OrgInfo.class, "Updated", null);
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
