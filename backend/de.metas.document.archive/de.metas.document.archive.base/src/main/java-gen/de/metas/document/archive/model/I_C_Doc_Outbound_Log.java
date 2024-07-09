package de.metas.document.archive.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Doc_Outbound_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Doc_Outbound_Log 
{

	String Table_Name = "C_Doc_Outbound_Log";

//	/** AD_Table_ID=540453 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Async Batch.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Async_Batch_ID();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "C_Async_Batch_ID", null);
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
	 * Set Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_BP_Group_ID();

	@Deprecated
	@Nullable org.compiere.model.I_C_BP_Group getC_BP_Group();

	@Deprecated
	void setC_BP_Group(@Nullable org.compiere.model.I_C_BP_Group C_BP_Group);

	ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Outbound Document Log ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Outbound_Log_ID (int C_Doc_Outbound_Log_ID);

	/**
	 * Get Outbound Document Log ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Outbound_Log_ID();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_C_Doc_Outbound_Log_ID = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "C_Doc_Outbound_Log_ID", null);
	String COLUMNNAME_C_Doc_Outbound_Log_ID = "C_Doc_Outbound_Log_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_Created = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrentEMailAddress (@Nullable java.lang.String CurrentEMailAddress);

	/**
	 * Get eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCurrentEMailAddress();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_CurrentEMailAddress = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "CurrentEMailAddress", null);
	String COLUMNNAME_CurrentEMailAddress = "CurrentEMailAddress";

	/**
	 * Set eMail Receipient.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrentEMailRecipient_ID (int CurrentEMailRecipient_ID);

	/**
	 * Get eMail Receipient.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCurrentEMailRecipient_ID();

	String COLUMNNAME_CurrentEMailRecipient_ID = "CurrentEMailRecipient_ID";

	/**
	 * Set Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateDoc (@Nullable java.sql.Timestamp DateDoc);

	/**
	 * Get Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateDoc();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DateDoc = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Last eMailed.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateLastEMail (@Nullable java.sql.Timestamp DateLastEMail);

	/**
	 * Get Last eMailed.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateLastEMail();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DateLastEMail = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "DateLastEMail", null);
	String COLUMNNAME_DateLastEMail = "DateLastEMail";

	/**
	 * Set Last printed.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateLastPrint (@Nullable java.sql.Timestamp DateLastPrint);

	/**
	 * Get Last printed.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateLastPrint();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DateLastPrint = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "DateLastPrint", null);
	String COLUMNNAME_DateLastPrint = "DateLastPrint";

	/**
	 * Set Last store.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateLastStore (@Nullable java.sql.Timestamp DateLastStore);

	/**
	 * Get Last store.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateLastStore();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DateLastStore = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "DateLastStore", null);
	String COLUMNNAME_DateLastStore = "DateLastStore";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocStatus (@Nullable java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocStatus();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set EDI export status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDI_ExportStatus (@Nullable java.lang.String EDI_ExportStatus);

	/**
	 * Get EDI export status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEDI_ExportStatus();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_EDI_ExportStatus = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "EDI_ExportStatus", null);
	String COLUMNNAME_EDI_ExportStatus = "EDI_ExportStatus";

	/**
	 * Set eMail counter.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setEMailCount (int EMailCount);

	/**
	 * Get eMail counter.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getEMailCount();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_EMailCount = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "EMailCount", null);
	String COLUMNNAME_EMailCount = "EMailCount";

	/**
	 * Set File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFileName (@Nullable java.lang.String FileName);

	/**
	 * Get File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFileName();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_FileName = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "FileName", null);
	String COLUMNNAME_FileName = "FileName";

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

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Document to be sent via EDI.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsEdiEnabled (boolean IsEdiEnabled);

	/**
	 * Get Document to be sent via EDI.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isEdiEnabled();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_IsEdiEnabled = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "IsEdiEnabled", null);
	String COLUMNNAME_IsEdiEnabled = "IsEdiEnabled";

	/**
	 * Set Invoice Email Enabled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsInvoiceEmailEnabled (boolean IsInvoiceEmailEnabled);

	/**
	 * Get Invoice Email Enabled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isInvoiceEmailEnabled();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_IsInvoiceEmailEnabled = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "IsInvoiceEmailEnabled", null);
	String COLUMNNAME_IsInvoiceEmailEnabled = "IsInvoiceEmailEnabled";

	/**
	 * Set PDF counter.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPDFCount (int PDFCount);

	/**
	 * Get PDF counter.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getPDFCount();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_PDFCount = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "PDFCount", null);
	String COLUMNNAME_PDFCount = "PDFCount";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_POReference = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set PostFinance Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostFinance_Export_Status (java.lang.String PostFinance_Export_Status);

	/**
	 * Get PostFinance Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPostFinance_Export_Status();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_PostFinance_Export_Status = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "PostFinance_Export_Status", null);
	String COLUMNNAME_PostFinance_Export_Status = "PostFinance_Export_Status";

	/**
	 * Set Print counter.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPrintCount (int PrintCount);

	/**
	 * Get Print counter.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getPrintCount();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_PrintCount = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "PrintCount", null);
	String COLUMNNAME_PrintCount = "PrintCount";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_Record_ID = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Stored count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setStoreCount (int StoreCount);

	/**
	 * Get Stored count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getStoreCount();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_StoreCount = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "StoreCount", null);
	String COLUMNNAME_StoreCount = "StoreCount";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_Updated = new ModelColumn<>(I_C_Doc_Outbound_Log.class, "Updated", null);
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
