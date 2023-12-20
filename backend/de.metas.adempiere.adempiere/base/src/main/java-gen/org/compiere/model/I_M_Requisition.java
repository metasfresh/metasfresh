package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Requisition
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Requisition 
{

	String Table_Name = "M_Requisition";

//	/** AD_Table_ID=702 */
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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Author.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAuthor_ID (int Author_ID);

	/**
	 * Get Author.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAuthor_ID();

	String COLUMNNAME_Author_ID = "Author_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Type.
	 * Set Project Type and for Service Projects copy Phases and Tasks of Project Type into Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectType_ID (int C_ProjectType_ID);

	/**
	 * Get Project Type.
	 * Set Project Type and for Service Projects copy Phases and Tasks of Project Type into Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectType_ID();

	@Nullable org.compiere.model.I_C_ProjectType getC_ProjectType();

	void setC_ProjectType(@Nullable org.compiere.model.I_C_ProjectType C_ProjectType);

	ModelColumn<I_M_Requisition, org.compiere.model.I_C_ProjectType> COLUMN_C_ProjectType_ID = new ModelColumn<>(I_M_Requisition.class, "C_ProjectType_ID", org.compiere.model.I_C_ProjectType.class);
	String COLUMNNAME_C_ProjectType_ID = "C_ProjectType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Requisition, Object> COLUMN_Created = new ModelColumn<>(I_M_Requisition.class, "Created", null);
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
	 * Set Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateDoc();

	ModelColumn<I_M_Requisition, Object> COLUMN_DateDoc = new ModelColumn<>(I_M_Requisition.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Date Required.
	 * Date when required
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateRequired (java.sql.Timestamp DateRequired);

	/**
	 * Get Date Required.
	 * Date when required
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateRequired();

	ModelColumn<I_M_Requisition, Object> COLUMN_DateRequired = new ModelColumn<>(I_M_Requisition.class, "DateRequired", null);
	String COLUMNNAME_DateRequired = "DateRequired";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_Requisition, Object> COLUMN_Description = new ModelColumn<>(I_M_Requisition.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_M_Requisition, Object> COLUMN_DocAction = new ModelColumn<>(I_M_Requisition.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_M_Requisition, Object> COLUMN_DocStatus = new ModelColumn<>(I_M_Requisition.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_M_Requisition, Object> COLUMN_DocumentNo = new ModelColumn<>(I_M_Requisition.class, "DocumentNo", null);
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

	ModelColumn<I_M_Requisition, Object> COLUMN_Help = new ModelColumn<>(I_M_Requisition.class, "Help", null);
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

	ModelColumn<I_M_Requisition, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Requisition.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApproved();

	ModelColumn<I_M_Requisition, Object> COLUMN_IsApproved = new ModelColumn<>(I_M_Requisition.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Geplant in Kostenstelle Budget.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBudgetPlanned (boolean IsBudgetPlanned);

	/**
	 * Get Geplant in Kostenstelle Budget.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBudgetPlanned();

	ModelColumn<I_M_Requisition, Object> COLUMN_IsBudgetPlanned = new ModelColumn<>(I_M_Requisition.class, "IsBudgetPlanned", null);
	String COLUMNNAME_IsBudgetPlanned = "IsBudgetPlanned";

	/**
	 * Set Supplier quotes existing?.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsQuotesExist (boolean IsQuotesExist);

	/**
	 * Get Supplier quotes existing?.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isQuotesExist();

	ModelColumn<I_M_Requisition, Object> COLUMN_IsQuotesExist = new ModelColumn<>(I_M_Requisition.class, "IsQuotesExist", null);
	String COLUMNNAME_IsQuotesExist = "IsQuotesExist";

	/**
	 * Set Missing Budget Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMissingBudgetNote (@Nullable java.lang.String MissingBudgetNote);

	/**
	 * Get Missing Budget Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMissingBudgetNote();

	ModelColumn<I_M_Requisition, Object> COLUMN_MissingBudgetNote = new ModelColumn<>(I_M_Requisition.class, "MissingBudgetNote", null);
	String COLUMNNAME_MissingBudgetNote = "MissingBudgetNote";

	/**
	 * Set Missing quote note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMissingQuoteNote (@Nullable java.lang.String MissingQuoteNote);

	/**
	 * Get Missing quote note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMissingQuoteNote();

	ModelColumn<I_M_Requisition, Object> COLUMN_MissingQuoteNote = new ModelColumn<>(I_M_Requisition.class, "MissingQuoteNote", null);
	String COLUMNNAME_MissingQuoteNote = "MissingQuoteNote";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Requisition.
	 * Material Requisition
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Requisition_ID (int M_Requisition_ID);

	/**
	 * Get Requisition.
	 * Material Requisition
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Requisition_ID();

	ModelColumn<I_M_Requisition, Object> COLUMN_M_Requisition_ID = new ModelColumn<>(I_M_Requisition.class, "M_Requisition_ID", null);
	String COLUMNNAME_M_Requisition_ID = "M_Requisition_ID";

	/**
	 * Set M_Requisition_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Requisition_includedTab (@Nullable java.lang.String M_Requisition_includedTab);

	/**
	 * Get M_Requisition_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getM_Requisition_includedTab();

	ModelColumn<I_M_Requisition, Object> COLUMN_M_Requisition_includedTab = new ModelColumn<>(I_M_Requisition.class, "M_Requisition_includedTab", null);
	String COLUMNNAME_M_Requisition_includedTab = "M_Requisition_includedTab";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_M_Requisition, Object> COLUMN_Note = new ModelColumn<>(I_M_Requisition.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Order Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderNote (@Nullable java.lang.String OrderNote);

	/**
	 * Get Order Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderNote();

	ModelColumn<I_M_Requisition, Object> COLUMN_OrderNote = new ModelColumn<>(I_M_Requisition.class, "OrderNote", null);
	String COLUMNNAME_OrderNote = "OrderNote";

	/**
	 * Set Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPosted (boolean Posted);

	/**
	 * Get Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPosted();

	ModelColumn<I_M_Requisition, Object> COLUMN_Posted = new ModelColumn<>(I_M_Requisition.class, "Posted", null);
	String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostingError_Issue_ID (int PostingError_Issue_ID);

	/**
	 * Get Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPostingError_Issue_ID();

	String COLUMNNAME_PostingError_Issue_ID = "PostingError_Issue_ID";

	/**
	 * Set Priority.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priority.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPriorityRule();

	ModelColumn<I_M_Requisition, Object> COLUMN_PriorityRule = new ModelColumn<>(I_M_Requisition.class, "PriorityRule", null);
	String COLUMNNAME_PriorityRule = "PriorityRule";

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

	ModelColumn<I_M_Requisition, Object> COLUMN_Processed = new ModelColumn<>(I_M_Requisition.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_M_Requisition, Object> COLUMN_Processing = new ModelColumn<>(I_M_Requisition.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Purchase Order No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPurchaseOrderNo (@Nullable java.lang.String PurchaseOrderNo);

	/**
	 * Get Purchase Order No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPurchaseOrderNo();

	ModelColumn<I_M_Requisition, Object> COLUMN_PurchaseOrderNo = new ModelColumn<>(I_M_Requisition.class, "PurchaseOrderNo", null);
	String COLUMNNAME_PurchaseOrderNo = "PurchaseOrderNo";

	/**
	 * Set Quote date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQuoteDate (@Nullable java.sql.Timestamp QuoteDate);

	/**
	 * Get Quote date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getQuoteDate();

	ModelColumn<I_M_Requisition, Object> COLUMN_QuoteDate = new ModelColumn<>(I_M_Requisition.class, "QuoteDate", null);
	String COLUMNNAME_QuoteDate = "QuoteDate";

	/**
	 * Set Quote number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQuoteNumber (@Nullable java.lang.String QuoteNumber);

	/**
	 * Get Quote number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQuoteNumber();

	ModelColumn<I_M_Requisition, Object> COLUMN_QuoteNumber = new ModelColumn<>(I_M_Requisition.class, "QuoteNumber", null);
	String COLUMNNAME_QuoteNumber = "QuoteNumber";

	/**
	 * Set Receiver.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiver_ID (int Receiver_ID);

	/**
	 * Get Receiver.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReceiver_ID();

	String COLUMNNAME_Receiver_ID = "Receiver_ID";

	/**
	 * Set Total Lines.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalLines (BigDecimal TotalLines);

	/**
	 * Get Total Lines.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalLines();

	ModelColumn<I_M_Requisition, Object> COLUMN_TotalLines = new ModelColumn<>(I_M_Requisition.class, "TotalLines", null);
	String COLUMNNAME_TotalLines = "TotalLines";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Requisition, Object> COLUMN_Updated = new ModelColumn<>(I_M_Requisition.class, "Updated", null);
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
	 * Set Warehouse Address.
	 * Warehouse Location/Address
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarehouse_Location_ID (int Warehouse_Location_ID);

	/**
	 * Get Warehouse Address.
	 * Warehouse Location/Address
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWarehouse_Location_ID();

	String COLUMNNAME_Warehouse_Location_ID = "Warehouse_Location_ID";
}
