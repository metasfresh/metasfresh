package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for I_ModCntr_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_I_ModCntr_Log 
{

	String Table_Name = "I_ModCntr_Log";

//	/** AD_Table_ID=542372 */
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
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmount (@Nullable BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_Amount = new ModelColumn<>(I_I_ModCntr_Log.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Bill partner search key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_Value (@Nullable java.lang.String Bill_BPartner_Value);

	/**
	 * Get Bill partner search key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBill_BPartner_Value();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_Bill_BPartner_Value = new ModelColumn<>(I_I_ModCntr_Log.class, "Bill_BPartner_Value", null);
	String COLUMNNAME_Bill_BPartner_Value = "Bill_BPartner_Value";

	/**
	 * Set Partner No..
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerValue (@Nullable java.lang.String BPartnerValue);

	/**
	 * Get Partner No..
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerValue();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_BPartnerValue = new ModelColumn<>(I_I_ModCntr_Log.class, "BPartnerValue", null);
	String COLUMNNAME_BPartnerValue = "BPartnerValue";

	/**
	 * Set Calendar name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCalendarName (java.lang.String CalendarName);

	/**
	 * Get Calendar name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCalendarName();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_CalendarName = new ModelColumn<>(I_I_ModCntr_Log.class, "CalendarName", null);
	String COLUMNNAME_CalendarName = "CalendarName";

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

	ModelColumn<I_I_ModCntr_Log, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_ID();

	@Nullable org.compiere.model.I_C_DataImport getC_DataImport();

	void setC_DataImport(@Nullable org.compiere.model.I_C_DataImport C_DataImport);

	ModelColumn<I_I_ModCntr_Log, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
	String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_Run_ID();

	@Nullable org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	void setC_DataImport_Run(@Nullable org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

	ModelColumn<I_I_ModCntr_Log, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
	String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	@Nullable de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term();

	void setC_Flatrate_Term(@Nullable de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term);

	ModelColumn<I_I_ModCntr_Log, de.metas.contracts.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "C_Flatrate_Term_ID", de.metas.contracts.model.I_C_Flatrate_Term.class);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Collection Point.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCollectionPoint_BPartner_ID (int CollectionPoint_BPartner_ID);

	/**
	 * Get Collection Point.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCollectionPoint_BPartner_ID();

	String COLUMNNAME_CollectionPoint_BPartner_ID = "CollectionPoint_BPartner_ID";

	/**
	 * Set Collection Point Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCollectionPointValue (@Nullable java.lang.String CollectionPointValue);

	/**
	 * Get Collection Point Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCollectionPointValue();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_CollectionPointValue = new ModelColumn<>(I_I_ModCntr_Log.class, "CollectionPointValue", null);
	String COLUMNNAME_CollectionPointValue = "CollectionPointValue";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_Created = new ModelColumn<>(I_I_ModCntr_Log.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Date.
	 * Transaction Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Date.
	 * Transaction Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateTrx();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_DateTrx = new ModelColumn<>(I_I_ModCntr_Log.class, "DateTrx", null);
	String COLUMNNAME_DateTrx = "DateTrx";

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

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_Description = new ModelColumn<>(I_I_ModCntr_Log.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_DocumentNo = new ModelColumn<>(I_I_ModCntr_Log.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Year.
	 * The Fiscal Year
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFiscalYear (java.lang.String FiscalYear);

	/**
	 * Get Year.
	 * The Fiscal Year
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFiscalYear();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_FiscalYear = new ModelColumn<>(I_I_ModCntr_Log.class, "FiscalYear", null);
	String COLUMNNAME_FiscalYear = "FiscalYear";

	/**
	 * Set Harvesting Year.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHarvesting_Year_ID (int Harvesting_Year_ID);

	/**
	 * Get Harvesting Year.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHarvesting_Year_ID();

	@Nullable org.compiere.model.I_C_Year getHarvesting_Year();

	void setHarvesting_Year(@Nullable org.compiere.model.I_C_Year Harvesting_Year);

	ModelColumn<I_I_ModCntr_Log, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_Harvesting_Year_ID = "Harvesting_Year_ID";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_ErrorMsg (@Nullable java.lang.String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_ErrorMsg();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_I_ErrorMsg = new ModelColumn<>(I_I_ModCntr_Log.class, "I_ErrorMsg", null);
	String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_IsImported (java.lang.String I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getI_IsImported();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_I_IsImported = new ModelColumn<>(I_I_ModCntr_Log.class, "I_IsImported", null);
	String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineContent (@Nullable java.lang.String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_LineContent();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_I_LineContent = new ModelColumn<>(I_I_ModCntr_Log.class, "I_LineContent", null);
	String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getI_LineNo();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_I_LineNo = new ModelColumn<>(I_I_ModCntr_Log.class, "I_LineNo", null);
	String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Import Contract Module Logs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_ModCntr_Log_ID (int I_ModCntr_Log_ID);

	/**
	 * Get Import Contract Module Logs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_ModCntr_Log_ID();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_I_ModCntr_Log_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "I_ModCntr_Log_ID", null);
	String COLUMNNAME_I_ModCntr_Log_ID = "I_ModCntr_Log_ID";

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

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_I_ModCntr_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setISO_Code (@Nullable java.lang.String ISO_Code);

	/**
	 * Get ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getISO_Code();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_ISO_Code = new ModelColumn<>(I_I_ModCntr_Log.class, "ISO_Code", null);
	String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_I_ModCntr_Log.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Invoice Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_InvoicingGroup_ID (int ModCntr_InvoicingGroup_ID);

	/**
	 * Get Invoice Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_InvoicingGroup_ID();

	@Nullable de.metas.contracts.model.I_ModCntr_InvoicingGroup getModCntr_InvoicingGroup();

	void setModCntr_InvoicingGroup(@Nullable de.metas.contracts.model.I_ModCntr_InvoicingGroup ModCntr_InvoicingGroup);

	ModelColumn<I_I_ModCntr_Log, de.metas.contracts.model.I_ModCntr_InvoicingGroup> COLUMN_ModCntr_InvoicingGroup_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "ModCntr_InvoicingGroup_ID", de.metas.contracts.model.I_ModCntr_InvoicingGroup.class);
	String COLUMNNAME_ModCntr_InvoicingGroup_ID = "ModCntr_InvoicingGroup_ID";

	/**
	 * Set Invoicing Group Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_InvoicingGroupName (@Nullable java.lang.String ModCntr_InvoicingGroupName);

	/**
	 * Get Invoicing Group Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getModCntr_InvoicingGroupName();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_ModCntr_InvoicingGroupName = new ModelColumn<>(I_I_ModCntr_Log.class, "ModCntr_InvoicingGroupName", null);
	String COLUMNNAME_ModCntr_InvoicingGroupName = "ModCntr_InvoicingGroupName";

	/**
	 * Set Document Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_Log_DocumentType (@Nullable java.lang.String ModCntr_Log_DocumentType);

	/**
	 * Get Document Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getModCntr_Log_DocumentType();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_ModCntr_Log_DocumentType = new ModelColumn<>(I_I_ModCntr_Log.class, "ModCntr_Log_DocumentType", null);
	String COLUMNNAME_ModCntr_Log_DocumentType = "ModCntr_Log_DocumentType";

	/**
	 * Set Contract Module Log.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_Log_ID (int ModCntr_Log_ID);

	/**
	 * Get Contract Module Log.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_Log_ID();

	@Nullable de.metas.contracts.model.I_ModCntr_Log getModCntr_Log();

	void setModCntr_Log(@Nullable de.metas.contracts.model.I_ModCntr_Log ModCntr_Log);

	ModelColumn<I_I_ModCntr_Log, de.metas.contracts.model.I_ModCntr_Log> COLUMN_ModCntr_Log_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "ModCntr_Log_ID", de.metas.contracts.model.I_ModCntr_Log.class);
	String COLUMNNAME_ModCntr_Log_ID = "ModCntr_Log_ID";

	/**
	 * Set Modules.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_Module_ID (int ModCntr_Module_ID);

	/**
	 * Get Modules.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_Module_ID();

	@Nullable de.metas.contracts.model.I_ModCntr_Module getModCntr_Module();

	void setModCntr_Module(@Nullable de.metas.contracts.model.I_ModCntr_Module ModCntr_Module);

	ModelColumn<I_I_ModCntr_Log, de.metas.contracts.model.I_ModCntr_Module> COLUMN_ModCntr_Module_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "ModCntr_Module_ID", de.metas.contracts.model.I_ModCntr_Module.class);
	String COLUMNNAME_ModCntr_Module_ID = "ModCntr_Module_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
	 * Set Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceActual (@Nullable BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_PriceActual = new ModelColumn<>(I_I_ModCntr_Log.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Price UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceUOM (@Nullable java.lang.String PriceUOM);

	/**
	 * Get Price UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPriceUOM();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_PriceUOM = new ModelColumn<>(I_I_ModCntr_Log.class, "PriceUOM", null);
	String COLUMNNAME_PriceUOM = "PriceUOM";

	/**
	 * Set Price Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrice_UOM_ID (int Price_UOM_ID);

	/**
	 * Get Price Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPrice_UOM_ID();

	String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";

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

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_Processed = new ModelColumn<>(I_I_ModCntr_Log.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_Processing = new ModelColumn<>(I_I_ModCntr_Log.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Producer.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProducer_BPartner_ID (int Producer_BPartner_ID);

	/**
	 * Get Producer.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getProducer_BPartner_ID();

	String COLUMNNAME_Producer_BPartner_ID = "Producer_BPartner_ID";

	/**
	 * Set Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductValue (@Nullable java.lang.String ProductValue);

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductValue();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_ProductValue = new ModelColumn<>(I_I_ModCntr_Log.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_Qty = new ModelColumn<>(I_I_ModCntr_Log.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

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

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_Record_ID = new ModelColumn<>(I_I_ModCntr_Log.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set UOM Symbol.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUOMSymbol (@Nullable java.lang.String UOMSymbol);

	/**
	 * Get UOM Symbol.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUOMSymbol();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_UOMSymbol = new ModelColumn<>(I_I_ModCntr_Log.class, "UOMSymbol", null);
	String COLUMNNAME_UOMSymbol = "UOMSymbol";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_Updated = new ModelColumn<>(I_I_ModCntr_Log.class, "Updated", null);
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

	/**
	 * Set Warehouse Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarehouseName (@Nullable java.lang.String WarehouseName);

	/**
	 * Get Warehouse Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWarehouseName();

	ModelColumn<I_I_ModCntr_Log, Object> COLUMN_WarehouseName = new ModelColumn<>(I_I_ModCntr_Log.class, "WarehouseName", null);
	String COLUMNNAME_WarehouseName = "WarehouseName";
}
