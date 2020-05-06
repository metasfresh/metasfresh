package org.compiere.model;


/** Generated Interface for C_BankStatement
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BankStatement 
{

    /** TableName=C_BankStatement */
    public static final String Table_Name = "C_BankStatement";

    /** AD_Table_ID=392 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_AD_Client>(I_C_BankStatement.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_AD_Org>(I_C_BankStatement.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
	 * Set Bankauszug.
	 * Bank Statement of account
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bankauszug.
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
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_C_BP_BankAccount>(I_C_BankStatement.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_C_DocType>(I_C_BankStatement.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Get Erstellt.
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
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_AD_User>(I_C_BankStatement.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
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
	 * Set Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
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
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
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
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
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
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
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
	 * Set Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Freigegeben.
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
	 * Set Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPosted (boolean Posted);

	/**
	 * Get Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPosted();

    /** Column definition for Posted */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<I_C_BankStatement, Object>(I_C_BankStatement.class, "Posted", null);
    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
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
	 * Set Verarbeiten.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
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
	 * Get Aktualisiert.
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
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BankStatement, org.compiere.model.I_AD_User>(I_C_BankStatement.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
