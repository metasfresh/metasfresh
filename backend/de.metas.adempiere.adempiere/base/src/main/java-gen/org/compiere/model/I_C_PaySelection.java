package org.compiere.model;


/** Generated Interface for C_PaySelection
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_C_PaySelection 
{

    /** TableName=C_PaySelection */
    public static final String Table_Name = "C_PaySelection";

    /** AD_Table_ID=426 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_C_BP_BankAccount>(I_C_PaySelection.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Zahlung Anweisen.
	 * Zahlung Anweisen
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PaySelection_ID (int C_PaySelection_ID);

	/**
	 * Get Zahlung Anweisen.
	 * Zahlung Anweisen
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PaySelection_ID();

    /** Column definition for C_PaySelection_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_C_PaySelection_ID = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "C_PaySelection_ID", null);
    /** Column name C_PaySelection_ID */
    public static final String COLUMNNAME_C_PaySelection_ID = "C_PaySelection_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_CreateFrom = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "CreateFrom", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegverarbeitung.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "DocAction", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Abgeglichen.
	 * Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReconciled (boolean IsReconciled);

	/**
	 * Get Abgeglichen.
	 * Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReconciled();

	/** Column definition for IsReconciled */
	public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_IsReconciled = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "IsReconciled", null);
	/** Column name IsReconciled */
	public static final String COLUMNNAME_IsReconciled = "IsReconciled";

	public void setIsExportBatchBookings (boolean IsExportBatchBookings);

	boolean isExportBatchBookings();

	public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_IsExportBatchBookings = new org.adempiere.model.ModelColumn<>(I_C_PaySelection.class, "IsExportBatchBookings", null);
	String COLUMNNAME_IsExportBatchBookings = "IsExportBatchBookings";

	/**
	 * Set Last Revolut Export.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastRevolutExport (java.sql.Timestamp LastRevolutExport);

	/**
	 * Get Last Revolut Export.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getLastRevolutExport();

	org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_LastRevolutExport = new org.adempiere.model.ModelColumn<>(I_C_PaySelection.class, "LastRevolutExport", null);
	String COLUMNNAME_LastRevolutExport = "LastRevolutExport";

	/**
	 * Set Last Revolut Export by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastRevolutExportBy_ID (int LastRevolutExportBy_ID);

	/**
	 * Get Last Revolut Export by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLastRevolutExportBy_ID();

	String COLUMNNAME_LastRevolutExportBy_ID = "LastRevolutExportBy_ID";

	/**
	 * Set Last SEPA Export.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastSepaExport (java.sql.Timestamp LastSepaExport);

	/**
	 * Get Last SEPA Export.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getLastSepaExport();

	org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_LastSepaExport = new org.adempiere.model.ModelColumn<>(I_C_PaySelection.class, "LastSepaExport", null);
	String COLUMNNAME_LastSepaExport = "LastSepaExport";

	/**
	 * Set Last SEPA Export by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastSepaExportBy_ID (int LastSepaExportBy_ID);

	/**
	 * Get Last SEPA Export by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLastSepaExportBy_ID();

	String COLUMNNAME_LastSepaExportBy_ID = "LastSepaExportBy_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Zahldatum.
	 * Date Payment made
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayDate (java.sql.Timestamp PayDate);

	/**
	 * Get Zahldatum.
	 * Date Payment made
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPayDate();

    /** Column definition for PayDate */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_PayDate = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "PayDate", null);
    /** Column name PayDate */
    public static final String COLUMNNAME_PayDate = "PayDate";

	/**
	 * Set PaySelection_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaySelection_includedTab (java.lang.String PaySelection_includedTab);

	/**
	 * Get PaySelection_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaySelection_includedTab();

    /** Column definition for PaySelection_includedTab */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_PaySelection_includedTab = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "PaySelection_includedTab", null);
    /** Column name PaySelection_includedTab */
    public static final String COLUMNNAME_PaySelection_includedTab = "PaySelection_includedTab";

	/**
	 * Set Transaktionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaySelectionTrxType (java.lang.String PaySelectionTrxType);

	/**
	 * Get Transaktionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaySelectionTrxType();

    /** Column definition for PaySelectionTrxType */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_PaySelectionTrxType = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "PaySelectionTrxType", null);
    /** Column name PaySelectionTrxType */
    public static final String COLUMNNAME_PaySelectionTrxType = "PaySelectionTrxType";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Gesamtbetrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTotalAmt (java.math.BigDecimal TotalAmt);

	/**
	 * Get Gesamtbetrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTotalAmt();

    /** Column definition for TotalAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_TotalAmt = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "TotalAmt", null);
    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
