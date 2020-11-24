package org.compiere.model;


/** Generated Interface for C_BankStatement
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BankStatement 
{

    /** TableName=C_BankStatement */
    public static final String Table_Name = "C_BankStatement";

    /** AD_Table_ID=392 */
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
	 * Set Anfangssaldo.
	 * Balance prior to any transactions
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBeginningBalance (java.math.BigDecimal BeginningBalance);

	/**
	 * Get Anfangssaldo.
	 * Balance prior to any transactions
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBeginningBalance();

    /** Column definition for BeginningBalance */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_BeginningBalance = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "BeginningBalance", null);
    /** Column name BeginningBalance */
    public static final String COLUMNNAME_BeginningBalance = "BeginningBalance";

	/**
	 * Set Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatement_ID();

    /** Column definition for C_BankStatement_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_C_BankStatement_ID = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "C_BankStatement_ID", null);
    /** Column name C_BankStatement_ID */
    public static final String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "Created", null);
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
	 * Set Position(en) kopieren von.
	 * Process which will generate a new document lines based on an existing document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreateFrom (java.lang.String CreateFrom);

	/**
	 * Get Position(en) kopieren von.
	 * Process which will generate a new document lines based on an existing document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreateFrom();

    /** Column definition for CreateFrom */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_CreateFrom = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "CreateFrom", null);
    /** Column name CreateFrom */
    public static final String COLUMNNAME_CreateFrom = "CreateFrom";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Auszugsdatum ELV.
	 * Electronic Funds Transfer Statement Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftStatementDate (java.sql.Timestamp EftStatementDate);

	/**
	 * Get Auszugsdatum ELV.
	 * Electronic Funds Transfer Statement Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEftStatementDate();

    /** Column definition for EftStatementDate */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_EftStatementDate = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "EftStatementDate", null);
    /** Column name EftStatementDate */
    public static final String COLUMNNAME_EftStatementDate = "EftStatementDate";

	/**
	 * Set ELV Auszugs-Referenz.
	 * Electronic Funds Transfer Statement Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftStatementReference (java.lang.String EftStatementReference);

	/**
	 * Get ELV Auszugs-Referenz.
	 * Electronic Funds Transfer Statement Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftStatementReference();

    /** Column definition for EftStatementReference */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_EftStatementReference = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "EftStatementReference", null);
    /** Column name EftStatementReference */
    public static final String COLUMNNAME_EftStatementReference = "EftStatementReference";

	/**
	 * Set Endsaldo.
	 * Ending  or closing balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEndingBalance (java.math.BigDecimal EndingBalance);

	/**
	 * Get Endsaldo.
	 * Ending  or closing balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getEndingBalance();

    /** Column definition for EndingBalance */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_EndingBalance = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "EndingBalance", null);
    /** Column name EndingBalance */
    public static final String COLUMNNAME_EndingBalance = "EndingBalance";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManual();

    /** Column definition for IsManual */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReconciled (boolean IsReconciled);

	/**
	 * Get Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReconciled();

    /** Column definition for IsReconciled */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_IsReconciled = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "IsReconciled", null);
    /** Column name IsReconciled */
    public static final String COLUMNNAME_IsReconciled = "IsReconciled";

	/**
	 * Set Match Statement.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMatchStatement (java.lang.String MatchStatement);

	/**
	 * Get Match Statement.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMatchStatement();

    /** Column definition for MatchStatement */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_MatchStatement = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "MatchStatement", null);
    /** Column name MatchStatement */
    public static final String COLUMNNAME_MatchStatement = "MatchStatement";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPosted (boolean Posted);

	/**
	 * Get Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPosted();

    /** Column definition for Posted */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "Posted", null);
    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Auszugsdatum.
	 * Date of the statement
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStatementDate (java.sql.Timestamp StatementDate);

	/**
	 * Get Auszugsdatum.
	 * Date of the statement
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getStatementDate();

    /** Column definition for StatementDate */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_StatementDate = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "StatementDate", null);
    /** Column name StatementDate */
    public static final String COLUMNNAME_StatementDate = "StatementDate";

	/**
	 * Set Auszugsdifferenz.
	 * Difference between statement ending balance and actual ending balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatementDifference (java.math.BigDecimal StatementDifference);

	/**
	 * Get Auszugsdifferenz.
	 * Difference between statement ending balance and actual ending balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStatementDifference();

    /** Column definition for StatementDifference */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_StatementDifference = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "StatementDifference", null);
    /** Column name StatementDifference */
    public static final String COLUMNNAME_StatementDifference = "StatementDifference";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "Updated", null);
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
