package de.metas.document.archive.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Doc_Outbound_Log_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Doc_Outbound_Log_Line 
{

	String Table_Name = "C_Doc_Outbound_Log_Line";

//	/** AD_Table_ID=540454 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAction (@Nullable java.lang.String Action);

	/**
	 * Get Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAction();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Action = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "Action", null);
	String COLUMNNAME_Action = "Action";

	/**
	 * Set Archive.
	 * Archiv für Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Archive_ID (int AD_Archive_ID);

	/**
	 * Get Archive.
	 * Archiv für Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Archive_ID();

	@Nullable org.compiere.model.I_AD_Archive getAD_Archive();

	void setAD_Archive(@Nullable org.compiere.model.I_AD_Archive AD_Archive);

	ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_Archive> COLUMN_AD_Archive_ID = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "AD_Archive_ID", org.compiere.model.I_AD_Archive.class);
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
	 * Set Outbound Document Log ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Outbound_Log_ID (int C_Doc_Outbound_Log_ID);

	/**
	 * Get Outbound Document Log ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Outbound_Log_ID();

	de.metas.document.archive.model.I_C_Doc_Outbound_Log getC_Doc_Outbound_Log();

	void setC_Doc_Outbound_Log(de.metas.document.archive.model.I_C_Doc_Outbound_Log C_Doc_Outbound_Log);

	ModelColumn<I_C_Doc_Outbound_Log_Line, de.metas.document.archive.model.I_C_Doc_Outbound_Log> COLUMN_C_Doc_Outbound_Log_ID = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "C_Doc_Outbound_Log_ID", de.metas.document.archive.model.I_C_Doc_Outbound_Log.class);
	String COLUMNNAME_C_Doc_Outbound_Log_ID = "C_Doc_Outbound_Log_ID";

	/**
	 * Set C_Doc_Outbound_Log_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Outbound_Log_Line_ID (int C_Doc_Outbound_Log_Line_ID);

	/**
	 * Get C_Doc_Outbound_Log_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Outbound_Log_Line_ID();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_C_Doc_Outbound_Log_Line_ID = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "C_Doc_Outbound_Log_Line_ID", null);
	String COLUMNNAME_C_Doc_Outbound_Log_Line_ID = "C_Doc_Outbound_Log_Line_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Copies.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopies (int Copies);

	/**
	 * Get Copies.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCopies();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Copies = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "Copies", null);
	String COLUMNNAME_Copies = "Copies";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Created = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "Created", null);
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

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "DocStatus", null);
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

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set EMail Bcc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail_Bcc (@Nullable java.lang.String EMail_Bcc);

	/**
	 * Get EMail Bcc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail_Bcc();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_EMail_Bcc = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "EMail_Bcc", null);
	String COLUMNNAME_EMail_Bcc = "EMail_Bcc";

	/**
	 * Set EMail Cc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail_Cc (@Nullable java.lang.String EMail_Cc);

	/**
	 * Get EMail Cc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail_Cc();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_EMail_Cc = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "EMail_Cc", null);
	String COLUMNNAME_EMail_Cc = "EMail_Cc";

	/**
	 * Set EMail From.
	 * Full EMail address used to send requests - e.g. edi@organization.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail_From (@Nullable java.lang.String EMail_From);

	/**
	 * Get EMail From.
	 * Full EMail address used to send requests - e.g. edi@organization.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail_From();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_EMail_From = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "EMail_From", null);
	String COLUMNNAME_EMail_From = "EMail_From";

	/**
	 * Set EMail To.
	 * EMail address to send requests to - e.g. edi@manufacturer.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail_To (@Nullable java.lang.String EMail_To);

	/**
	 * Get EMail To.
	 * EMail address to send requests to - e.g. edi@manufacturer.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail_To();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_EMail_To = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "EMail_To", null);
	String COLUMNNAME_EMail_To = "EMail_To";

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

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Printer.
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrinterName (@Nullable java.lang.String PrinterName);

	/**
	 * Get Printer.
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrinterName();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_PrinterName = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "PrinterName", null);
	String COLUMNNAME_PrinterName = "PrinterName";

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

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Record_ID = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStatus (@Nullable java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStatus();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Status = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Set Storage location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStoreURI (@Nullable java.lang.String StoreURI);

	/**
	 * Get Storage location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStoreURI();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_StoreURI = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "StoreURI", null);
	String COLUMNNAME_StoreURI = "StoreURI";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Updated = new ModelColumn<>(I_C_Doc_Outbound_Log_Line.class, "Updated", null);
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
