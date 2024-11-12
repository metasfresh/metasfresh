package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Archive
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Archive 
{

	String Table_Name = "AD_Archive";

//	/** AD_Table_ID=754 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Archive.
	 * Archiv für Belege und Berichte
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Archive_ID (int AD_Archive_ID);

	/**
	 * Get Archive.
	 * Archiv für Belege und Berichte
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Archive_ID();

	ModelColumn<I_AD_Archive, Object> COLUMN_AD_Archive_ID = new ModelColumn<>(I_AD_Archive.class, "AD_Archive_ID", null);
	String COLUMNNAME_AD_Archive_ID = "AD_Archive_ID";

	/**
	 * Set Archive Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Archive_Storage_ID (int AD_Archive_Storage_ID);

	/**
	 * Get Archive Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Archive_Storage_ID();

	@Nullable org.compiere.model.I_AD_Archive_Storage getAD_Archive_Storage();

	void setAD_Archive_Storage(@Nullable org.compiere.model.I_AD_Archive_Storage AD_Archive_Storage);

	ModelColumn<I_AD_Archive, org.compiere.model.I_AD_Archive_Storage> COLUMN_AD_Archive_Storage_ID = new ModelColumn<>(I_AD_Archive.class, "AD_Archive_Storage_ID", org.compiere.model.I_AD_Archive_Storage.class);
	String COLUMNNAME_AD_Archive_Storage_ID = "AD_Archive_Storage_ID";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_AD_Archive, Object> COLUMN_AD_Language = new ModelColumn<>(I_AD_Archive.class, "AD_Language", null);
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
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance();

	void setAD_PInstance(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance);

	ModelColumn<I_AD_Archive, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_AD_Archive.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Process.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Process.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	@Nullable org.compiere.model.I_AD_Process getAD_Process();

	void setAD_Process(@Nullable org.compiere.model.I_AD_Process AD_Process);

	ModelColumn<I_AD_Archive, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_AD_Archive.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

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
	 * Set Binary Data.
	 * Binary Data
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBinaryData (byte[] BinaryData);

	/**
	 * Get Binary Data.
	 * Binary Data
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	byte[] getBinaryData();

	ModelColumn<I_AD_Archive, Object> COLUMN_BinaryData = new ModelColumn<>(I_AD_Archive.class, "BinaryData", null);
	String COLUMNNAME_BinaryData = "BinaryData";

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

	ModelColumn<I_AD_Archive, Object> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_AD_Archive.class, "C_Async_Batch_ID", null);
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
	 * Set Ausgehende Belege Konfig.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Outbound_Config_ID (int C_Doc_Outbound_Config_ID);

	/**
	 * Get Ausgehende Belege Konfig.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Outbound_Config_ID();

	@Nullable org.compiere.model.I_C_Doc_Outbound_Config getC_Doc_Outbound_Config();

	void setC_Doc_Outbound_Config(@Nullable org.compiere.model.I_C_Doc_Outbound_Config C_Doc_Outbound_Config);

	ModelColumn<I_AD_Archive, org.compiere.model.I_C_Doc_Outbound_Config> COLUMN_C_Doc_Outbound_Config_ID = new ModelColumn<>(I_AD_Archive.class, "C_Doc_Outbound_Config_ID", org.compiere.model.I_C_Doc_Outbound_Config.class);
	String COLUMNNAME_C_Doc_Outbound_Config_ID = "C_Doc_Outbound_Config_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Archive, Object> COLUMN_Created = new ModelColumn<>(I_AD_Archive.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Archive, Object> COLUMN_Description = new ModelColumn<>(I_AD_Archive.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Flavor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentFlavor (@Nullable java.lang.String DocumentFlavor);

	/**
	 * Get Flavor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentFlavor();

	ModelColumn<I_AD_Archive, Object> COLUMN_DocumentFlavor = new ModelColumn<>(I_AD_Archive.class, "DocumentFlavor", null);
	String COLUMNNAME_DocumentFlavor = "DocumentFlavor";

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

	ModelColumn<I_AD_Archive, Object> COLUMN_DocumentNo = new ModelColumn<>(I_AD_Archive.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_AD_Archive, Object> COLUMN_Help = new ModelColumn<>(I_AD_Archive.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_AD_Archive, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Archive.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Is Direct Enqueue.
	 * Decides whether a print queue record should be created automatically when the print item (archive) is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDirectEnqueue (boolean IsDirectEnqueue);

	/**
	 * Get Is Direct Enqueue.
	 * Decides whether a print queue record should be created automatically when the print item (archive) is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDirectEnqueue();

	ModelColumn<I_AD_Archive, Object> COLUMN_IsDirectEnqueue = new ModelColumn<>(I_AD_Archive.class, "IsDirectEnqueue", null);
	String COLUMNNAME_IsDirectEnqueue = "IsDirectEnqueue";

	/**
	 * Set Directly proces queue item.
	 * Decides whether according to config a print-job is created or the PDF is stored locally right away.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDirectProcessQueueItem (boolean IsDirectProcessQueueItem);

	/**
	 * Get Directly proces queue item.
	 * Decides whether according to config a print-job is created or the PDF is stored locally right away.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDirectProcessQueueItem();

	ModelColumn<I_AD_Archive, Object> COLUMN_IsDirectProcessQueueItem = new ModelColumn<>(I_AD_Archive.class, "IsDirectProcessQueueItem", null);
	String COLUMNNAME_IsDirectProcessQueueItem = "IsDirectProcessQueueItem";

	/**
	 * Set Main Archive.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMainArchive (boolean IsMainArchive);

	/**
	 * Get Main Archive.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMainArchive();

	ModelColumn<I_AD_Archive, Object> COLUMN_IsMainArchive = new ModelColumn<>(I_AD_Archive.class, "IsMainArchive", null);
	String COLUMNNAME_IsMainArchive = "IsMainArchive";

	/**
	 * Set Report.
	 * Indicates a Report record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReport (boolean IsReport);

	/**
	 * Get Report.
	 * Indicates a Report record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReport();

	ModelColumn<I_AD_Archive, Object> COLUMN_IsReport = new ModelColumn<>(I_AD_Archive.class, "IsReport", null);
	String COLUMNNAME_IsReport = "IsReport";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_AD_Archive, Object> COLUMN_Name = new ModelColumn<>(I_AD_Archive.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Override Doc Type Name.
	 * Override Doc Type Name
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOverride_DocType_ID (int Override_DocType_ID);

	/**
	 * Get Override Doc Type Name.
	 * Override Doc Type Name
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOverride_DocType_ID();

	String COLUMNNAME_Override_DocType_ID = "Override_DocType_ID";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_AD_Archive, Object> COLUMN_POReference = new ModelColumn<>(I_AD_Archive.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_AD_Archive, Object> COLUMN_Record_ID = new ModelColumn<>(I_AD_Archive.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Archive, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Archive.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
