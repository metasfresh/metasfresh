package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BankStatement
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BankStatement 
{

	String Table_Name = "C_BankStatement";

//	/** AD_Table_ID=392 */
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
	 * Set Beginning Balance.
	 * Balance prior to any transactions
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBeginningBalance (@Nullable BigDecimal BeginningBalance);

	/**
	 * Get Beginning Balance.
	 * Balance prior to any transactions
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getBeginningBalance();

	ModelColumn<I_C_BankStatement, Object> COLUMN_BeginningBalance = new ModelColumn<>(I_C_BankStatement.class, "BeginningBalance", null);
	String COLUMNNAME_BeginningBalance = "BeginningBalance";

	/**
	 * Set Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BankStatement_ID();

	ModelColumn<I_C_BankStatement, Object> COLUMN_C_BankStatement_ID = new ModelColumn<>(I_C_BankStatement.class, "C_BankStatement_ID", null);
	String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
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

	ModelColumn<I_C_BankStatement, Object> COLUMN_Created = new ModelColumn<>(I_C_BankStatement.class, "Created", null);
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
	 * Set Create From ....
	 * Prozess, der die Position(en) aus einem bestehenden Beleg kopiert
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateFrom (@Nullable java.lang.String CreateFrom);

	/**
	 * Get Create From ....
	 * Prozess, der die Position(en) aus einem bestehenden Beleg kopiert
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateFrom();

	ModelColumn<I_C_BankStatement, Object> COLUMN_CreateFrom = new ModelColumn<>(I_C_BankStatement.class, "CreateFrom", null);
	String COLUMNNAME_CreateFrom = "CreateFrom";

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

	ModelColumn<I_C_BankStatement, Object> COLUMN_Description = new ModelColumn<>(I_C_BankStatement.class, "Description", null);
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

	ModelColumn<I_C_BankStatement, Object> COLUMN_DocAction = new ModelColumn<>(I_C_BankStatement.class, "DocAction", null);
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

	ModelColumn<I_C_BankStatement, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_BankStatement.class, "DocStatus", null);
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

	ModelColumn<I_C_BankStatement, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_BankStatement.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set EFT Statement Date.
	 * Electronic Funds Transfer Statement Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftStatementDate (@Nullable java.sql.Timestamp EftStatementDate);

	/**
	 * Get EFT Statement Date.
	 * Electronic Funds Transfer Statement Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEftStatementDate();

	ModelColumn<I_C_BankStatement, Object> COLUMN_EftStatementDate = new ModelColumn<>(I_C_BankStatement.class, "EftStatementDate", null);
	String COLUMNNAME_EftStatementDate = "EftStatementDate";

	/**
	 * Set EFT Statement Reference.
	 * Electronic Funds Transfer Statement Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftStatementReference (@Nullable java.lang.String EftStatementReference);

	/**
	 * Get EFT Statement Reference.
	 * Electronic Funds Transfer Statement Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftStatementReference();

	ModelColumn<I_C_BankStatement, Object> COLUMN_EftStatementReference = new ModelColumn<>(I_C_BankStatement.class, "EftStatementReference", null);
	String COLUMNNAME_EftStatementReference = "EftStatementReference";

	/**
	 * Set Ending Balance.
	 * Ending or closing Balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEndingBalance (BigDecimal EndingBalance);

	/**
	 * Get Ending Balance.
	 * Ending or closing Balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getEndingBalance();

	ModelColumn<I_C_BankStatement, Object> COLUMN_EndingBalance = new ModelColumn<>(I_C_BankStatement.class, "EndingBalance", null);
	String COLUMNNAME_EndingBalance = "EndingBalance";

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

	ModelColumn<I_C_BankStatement, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BankStatement.class, "IsActive", null);
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

	ModelColumn<I_C_BankStatement, Object> COLUMN_IsApproved = new ModelColumn<>(I_C_BankStatement.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Invoice manually allocated.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManual (boolean IsManual);

	/**
	 * Get Invoice manually allocated.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManual();

	ModelColumn<I_C_BankStatement, Object> COLUMN_IsManual = new ModelColumn<>(I_C_BankStatement.class, "IsManual", null);
	String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReconciled (boolean IsReconciled);

	/**
	 * Get Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReconciled();

	ModelColumn<I_C_BankStatement, Object> COLUMN_IsReconciled = new ModelColumn<>(I_C_BankStatement.class, "IsReconciled", null);
	String COLUMNNAME_IsReconciled = "IsReconciled";

	/**
	 * Set Match Bank Statement.
	 * Match Bank Statement Info to Business Partners, Invoices and Payments
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMatchStatement (@Nullable java.lang.String MatchStatement);

	/**
	 * Get Match Bank Statement.
	 * Match Bank Statement Info to Business Partners, Invoices and Payments
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMatchStatement();

	ModelColumn<I_C_BankStatement, Object> COLUMN_MatchStatement = new ModelColumn<>(I_C_BankStatement.class, "MatchStatement", null);
	String COLUMNNAME_MatchStatement = "MatchStatement";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_BankStatement, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_BankStatement.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

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

	ModelColumn<I_C_BankStatement, Object> COLUMN_Name = new ModelColumn<>(I_C_BankStatement.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_C_BankStatement, Object> COLUMN_Posted = new ModelColumn<>(I_C_BankStatement.class, "Posted", null);
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

	ModelColumn<I_C_BankStatement, Object> COLUMN_Processed = new ModelColumn<>(I_C_BankStatement.class, "Processed", null);
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

	ModelColumn<I_C_BankStatement, Object> COLUMN_Processing = new ModelColumn<>(I_C_BankStatement.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Date.
	 * Date of the statement
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatementDate (java.sql.Timestamp StatementDate);

	/**
	 * Get Date.
	 * Date of the statement
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getStatementDate();

	ModelColumn<I_C_BankStatement, Object> COLUMN_StatementDate = new ModelColumn<>(I_C_BankStatement.class, "StatementDate", null);
	String COLUMNNAME_StatementDate = "StatementDate";

	/**
	 * Set Statement difference.
	 * Difference between statement ending balance and actual ending balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStatementDifference (@Nullable BigDecimal StatementDifference);

	/**
	 * Get Statement difference.
	 * Difference between statement ending balance and actual ending balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getStatementDifference();

	ModelColumn<I_C_BankStatement, Object> COLUMN_StatementDifference = new ModelColumn<>(I_C_BankStatement.class, "StatementDifference", null);
	String COLUMNNAME_StatementDifference = "StatementDifference";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BankStatement, Object> COLUMN_Updated = new ModelColumn<>(I_C_BankStatement.class, "Updated", null);
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
