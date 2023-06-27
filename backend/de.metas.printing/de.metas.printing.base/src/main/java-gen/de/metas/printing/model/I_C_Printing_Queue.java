package de.metas.printing.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Printing_Queue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Printing_Queue 
{

	String Table_Name = "C_Printing_Queue";

//	/** AD_Table_ID=540435 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Archiv.
	 * Archiv fĂĽr Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Archive_ID (int AD_Archive_ID);

	/**
	 * Get Archiv.
	 * Archiv fĂĽr Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Archive_ID();

	org.compiere.model.I_AD_Archive getAD_Archive();

	void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive);

	ModelColumn<I_C_Printing_Queue, org.compiere.model.I_AD_Archive> COLUMN_AD_Archive_ID = new ModelColumn<>(I_C_Printing_Queue.class, "AD_Archive_ID", org.compiere.model.I_AD_Archive.class);
	String COLUMNNAME_AD_Archive_ID = "AD_Archive_ID";

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
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_AD_Language = new ModelColumn<>(I_C_Printing_Queue.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

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
	 * Set Hardware-Drucker.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PrinterHW_ID (int AD_PrinterHW_ID);

	/**
	 * Get Hardware-Drucker.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PrinterHW_ID();

	@Nullable de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW();

	void setAD_PrinterHW(@Nullable de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW);

	ModelColumn<I_C_Printing_Queue, de.metas.printing.model.I_AD_PrinterHW> COLUMN_AD_PrinterHW_ID = new ModelColumn<>(I_C_Printing_Queue.class, "AD_PrinterHW_ID", de.metas.printing.model.I_AD_PrinterHW.class);
	String COLUMNNAME_AD_PrinterHW_ID = "AD_PrinterHW_ID";

	/**
	 * Set Media Tray.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PrinterHW_MediaTray_ID (int AD_PrinterHW_MediaTray_ID);

	/**
	 * Get Media Tray.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PrinterHW_MediaTray_ID();

	@Nullable de.metas.printing.model.I_AD_PrinterHW_MediaTray getAD_PrinterHW_MediaTray();

	void setAD_PrinterHW_MediaTray(@Nullable de.metas.printing.model.I_AD_PrinterHW_MediaTray AD_PrinterHW_MediaTray);

	ModelColumn<I_C_Printing_Queue, de.metas.printing.model.I_AD_PrinterHW_MediaTray> COLUMN_AD_PrinterHW_MediaTray_ID = new ModelColumn<>(I_C_Printing_Queue.class, "AD_PrinterHW_MediaTray_ID", de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
	String COLUMNNAME_AD_PrinterHW_MediaTray_ID = "AD_PrinterHW_MediaTray_ID";

	/**
	 * Set Process.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Process.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	@Nullable org.compiere.model.I_AD_Process getAD_Process();

	void setAD_Process(@Nullable org.compiere.model.I_AD_Process AD_Process);

	ModelColumn<I_C_Printing_Queue, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_C_Printing_Queue.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Role_ID();

	@Nullable org.compiere.model.I_AD_Role getAD_Role();

	void setAD_Role(@Nullable org.compiere.model.I_AD_Role AD_Role);

	ModelColumn<I_C_Printing_Queue, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new ModelColumn<>(I_C_Printing_Queue.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_ID();

	String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Bill Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_User_ID (int Bill_User_ID);

	/**
	 * Get Bill Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_User_ID();

	String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

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

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_C_Printing_Queue.class, "C_Async_Batch_ID", null);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCopies (int Copies);

	/**
	 * Get Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCopies();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_Copies = new ModelColumn<>(I_C_Printing_Queue.class, "Copies", null);
	String COLUMNNAME_Copies = "Copies";

	/**
	 * Set Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Printing_Queue_ID (int C_Printing_Queue_ID);

	/**
	 * Get Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Printing_Queue_ID();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_C_Printing_Queue_ID = new ModelColumn<>(I_C_Printing_Queue.class, "C_Printing_Queue_ID", null);
	String COLUMNNAME_C_Printing_Queue_ID = "C_Printing_Queue_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_Created = new ModelColumn<>(I_C_Printing_Queue.class, "Created", null);
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

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_DeliveryDate = new ModelColumn<>(I_C_Printing_Queue.class, "DeliveryDate", null);
	String COLUMNNAME_DeliveryDate = "DeliveryDate";

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

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Printing_Queue.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Abweichender Rechnungspartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsDifferentInvoicingPartner (boolean IsDifferentInvoicingPartner);

	/**
	 * Get Abweichender Rechnungspartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isDifferentInvoicingPartner();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_IsDifferentInvoicingPartner = new ModelColumn<>(I_C_Printing_Queue.class, "IsDifferentInvoicingPartner", null);
	String COLUMNNAME_IsDifferentInvoicingPartner = "IsDifferentInvoicingPartner";

	/**
	 * Set Foreign Customer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsForeignCustomer (boolean IsForeignCustomer);

	/**
	 * Get Foreign Customer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isForeignCustomer();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_IsForeignCustomer = new ModelColumn<>(I_C_Printing_Queue.class, "IsForeignCustomer", null);
	String COLUMNNAME_IsForeignCustomer = "IsForeignCustomer";

	/**
	 * Set Abw. Druck-Empfänger.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrintoutForOtherUser (boolean IsPrintoutForOtherUser);

	/**
	 * Get Abw. Druck-Empfänger.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrintoutForOtherUser();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_IsPrintoutForOtherUser = new ModelColumn<>(I_C_Printing_Queue.class, "IsPrintoutForOtherUser", null);
	String COLUMNNAME_IsPrintoutForOtherUser = "IsPrintoutForOtherUser";

	/**
	 * Set Print Item Name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setItemName (@Nullable java.lang.String ItemName);

	/**
	 * Get Print Item Name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getItemName();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_ItemName = new ModelColumn<>(I_C_Printing_Queue.class, "ItemName", null);
	String COLUMNNAME_ItemName = "ItemName";

	/**
	 * Set Warteschlangen-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrintingQueueAggregationKey (@Nullable java.lang.String PrintingQueueAggregationKey);

	/**
	 * Get Warteschlangen-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrintingQueueAggregationKey();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_PrintingQueueAggregationKey = new ModelColumn<>(I_C_Printing_Queue.class, "PrintingQueueAggregationKey", null);
	String COLUMNNAME_PrintingQueueAggregationKey = "PrintingQueueAggregationKey";

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

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_Processed = new ModelColumn<>(I_C_Printing_Queue.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Printing_Queue, Object> COLUMN_Updated = new ModelColumn<>(I_C_Printing_Queue.class, "Updated", null);
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
