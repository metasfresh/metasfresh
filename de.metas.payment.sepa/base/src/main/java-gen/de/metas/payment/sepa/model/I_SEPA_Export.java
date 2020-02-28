package de.metas.payment.sepa.model;


/** Generated Interface for SEPA_Export
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_SEPA_Export 
{

    /** TableName=SEPA_Export */
    public static final String Table_Name = "SEPA_Export";

    /** AD_Table_ID=540603 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_Client>(I_SEPA_Export.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_Org>(I_SEPA_Export.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_Table>(I_SEPA_Export.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_User>(I_SEPA_Export.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIBAN (java.lang.String IBAN);

	/**
	 * Get IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIBAN();

    /** Column definition for IBAN */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_IBAN = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "IBAN", null);
    /** Column name IBAN */
    public static final String COLUMNNAME_IBAN = "IBAN";

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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sammelbuchungen exportieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsExportBatchBookings (boolean IsExportBatchBookings);

	/**
	 * Get Sammelbuchungen exportieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isExportBatchBookings();

    /** Column definition for IsExportBatchBookings */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_IsExportBatchBookings = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "IsExportBatchBookings", null);
    /** Column name IsExportBatchBookings */
    public static final String COLUMNNAME_IsExportBatchBookings = "IsExportBatchBookings";

	/**
	 * Set Zahldatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPaymentDate (java.sql.Timestamp PaymentDate);

	/**
	 * Get Zahldatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPaymentDate();

    /** Column definition for PaymentDate */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_PaymentDate = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "PaymentDate", null);
    /** Column name PaymentDate */
    public static final String COLUMNNAME_PaymentDate = "PaymentDate";

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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set SEPA Creditor Identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSEPA_CreditorIdentifier (java.lang.String SEPA_CreditorIdentifier);

	/**
	 * Get SEPA Creditor Identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSEPA_CreditorIdentifier();

    /** Column definition for SEPA_CreditorIdentifier */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_SEPA_CreditorIdentifier = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "SEPA_CreditorIdentifier", null);
    /** Column name SEPA_CreditorIdentifier */
    public static final String COLUMNNAME_SEPA_CreditorIdentifier = "SEPA_CreditorIdentifier";

	/**
	 * Set SEPA Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSEPA_Export_ID (int SEPA_Export_ID);

	/**
	 * Get SEPA Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSEPA_Export_ID();

    /** Column definition for SEPA_Export_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_SEPA_Export_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "SEPA_Export_ID", null);
    /** Column name SEPA_Export_ID */
    public static final String COLUMNNAME_SEPA_Export_ID = "SEPA_Export_ID";

	/**
	 * Set Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSwiftCode (java.lang.String SwiftCode);

	/**
	 * Get Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSwiftCode();

    /** Column definition for SwiftCode */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_SwiftCode = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "SwiftCode", null);
    /** Column name SwiftCode */
    public static final String COLUMNNAME_SwiftCode = "SwiftCode";

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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_SEPA_Export, Object>(I_SEPA_Export.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_SEPA_Export, org.compiere.model.I_AD_User>(I_SEPA_Export.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
