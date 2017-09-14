package de.metas.payment.esr.model;


/** Generated Interface for ESR_Import
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_ESR_Import 
{

    /** TableName=ESR_Import */
    public static final String Table_Name = "ESR_Import";

    /** AD_Table_ID=540409 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Attachment entry.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID);

	/**
	 * Get Attachment entry.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_AttachmentEntry_ID();

    /** Column definition for AD_AttachmentEntry_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_AD_AttachmentEntry_ID = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "AD_AttachmentEntry_ID", null);
    /** Column name AD_AttachmentEntry_ID */
    public static final String COLUMNNAME_AD_AttachmentEntry_ID = "AD_AttachmentEntry_ID";

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_AD_Client>(I_ESR_Import.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_AD_Org>(I_ESR_Import.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_C_BP_BankAccount>(I_ESR_Import.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_AD_User>(I_ESR_Import.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Daten-Typ.
	 * Art der Daten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataType (java.lang.String DataType);

	/**
	 * Get Daten-Typ.
	 * Art der Daten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDataType();

    /** Column definition for DataType */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_DataType = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "DataType", null);
    /** Column name DataType */
    public static final String COLUMNNAME_DataType = "DataType";

	/**
	 * Set Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDoc();

    /** Column definition for DateDoc */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_DateDoc = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "DateDoc", null);
    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

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
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Gesamtbetrag.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESR_Control_Amount (java.math.BigDecimal ESR_Control_Amount);

	/**
	 * Get Gesamtbetrag.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getESR_Control_Amount();

    /** Column definition for ESR_Control_Amount */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_ESR_Control_Amount = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "ESR_Control_Amount", null);
    /** Column name ESR_Control_Amount */
    public static final String COLUMNNAME_ESR_Control_Amount = "ESR_Control_Amount";

	/**
	 * Set Anzahl Transaktionen (kontr.).
	 * Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESR_Control_Trx_Qty (java.math.BigDecimal ESR_Control_Trx_Qty);

	/**
	 * Get Anzahl Transaktionen (kontr.).
	 * Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getESR_Control_Trx_Qty();

    /** Column definition for ESR_Control_Trx_Qty */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_ESR_Control_Trx_Qty = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "ESR_Control_Trx_Qty", null);
    /** Column name ESR_Control_Trx_Qty */
    public static final String COLUMNNAME_ESR_Control_Trx_Qty = "ESR_Control_Trx_Qty";

	/**
	 * Set ESR Zahlungsimport.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setESR_Import_ID (int ESR_Import_ID);

	/**
	 * Get ESR Zahlungsimport.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getESR_Import_ID();

    /** Column definition for ESR_Import_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_ESR_Import_ID = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "ESR_Import_ID", null);
    /** Column name ESR_Import_ID */
    public static final String COLUMNNAME_ESR_Import_ID = "ESR_Import_ID";

	/**
	 * Set Anzahl Transaktionen.
	 * Anzahl der importierten Zeilen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setESR_Trx_Qty (java.math.BigDecimal ESR_Trx_Qty);

	/**
	 * Get Anzahl Transaktionen.
	 * Anzahl der importierten Zeilen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getESR_Trx_Qty();

    /** Column definition for ESR_Trx_Qty */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_ESR_Trx_Qty = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "ESR_Trx_Qty", null);
    /** Column name ESR_Trx_Qty */
    public static final String COLUMNNAME_ESR_Trx_Qty = "ESR_Trx_Qty";

	/**
	 * Set Hash.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHash (java.lang.String Hash);

	/**
	 * Get Hash.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHash();

    /** Column definition for Hash */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_Hash = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "Hash", null);
    /** Column name Hash */
    public static final String COLUMNNAME_Hash = "Hash";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zahlungseingang.
	 * Dies ist eine Verkaufs-Transaktion (Zahlungseingang)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReceipt (boolean IsReceipt);

	/**
	 * Get Zahlungseingang.
	 * Dies ist eine Verkaufs-Transaktion (Zahlungseingang)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReceipt();

    /** Column definition for IsReceipt */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_IsReceipt = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "IsReceipt", null);
    /** Column name IsReceipt */
    public static final String COLUMNNAME_IsReceipt = "IsReceipt";

	/**
	 * Set GĂĽltig.
	 * Element ist gĂĽltig
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsValid (boolean IsValid);

	/**
	 * Get GĂĽltig.
	 * Element ist gĂĽltig
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isValid();

    /** Column definition for IsValid */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_IsValid = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "IsValid", null);
    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

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
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_ESR_Import, Object>(I_ESR_Import.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_ESR_Import, org.compiere.model.I_AD_User>(I_ESR_Import.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
