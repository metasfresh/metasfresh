package de.metas.payment.sepa.model;


/** Generated Interface for SEPA_Export_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_SEPA_Export_Line 
{

    /** TableName=SEPA_Export_Line */
    public static final String Table_Name = "SEPA_Export_Line";

    /** AD_Table_ID=540604 */
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_Client>(I_SEPA_Export_Line.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_Org>(I_SEPA_Export_Line.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_Table>(I_SEPA_Export_Line.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Betrag.
	 * Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmt (java.math.BigDecimal Amt);

	/**
	 * Get Betrag.
	 * Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmt();

    /** Column definition for Amt */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_Amt = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "Amt", null);
    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_C_BPartner>(I_SEPA_Export_Line.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_C_BP_BankAccount>(I_SEPA_Export_Line.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_C_Currency>(I_SEPA_Export_Line.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_User>(I_SEPA_Export_Line.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/**
	 * Get Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorMsg();

    /** Column definition for ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIBAN (java.lang.String IBAN);

	/**
	 * Get IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIBAN();

    /** Column definition for IBAN */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_IBAN = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "IBAN", null);
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set OtherAccountIdentification.
	 * Alternative account ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOtherAccountIdentification (java.lang.String OtherAccountIdentification);

	/**
	 * Get OtherAccountIdentification.
	 * Alternative account ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOtherAccountIdentification();

    /** Column definition for OtherAccountIdentification */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_OtherAccountIdentification = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "OtherAccountIdentification", null);
    /** Column name OtherAccountIdentification */
    public static final String COLUMNNAME_OtherAccountIdentification = "OtherAccountIdentification";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set SEPA Export.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSEPA_Export_ID (int SEPA_Export_ID);

	/**
	 * Get SEPA Export.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSEPA_Export_ID();

	public de.metas.payment.sepa.model.I_SEPA_Export getSEPA_Export();

	public void setSEPA_Export(de.metas.payment.sepa.model.I_SEPA_Export SEPA_Export);

    /** Column definition for SEPA_Export_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, de.metas.payment.sepa.model.I_SEPA_Export> COLUMN_SEPA_Export_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, de.metas.payment.sepa.model.I_SEPA_Export>(I_SEPA_Export_Line.class, "SEPA_Export_ID", de.metas.payment.sepa.model.I_SEPA_Export.class);
    /** Column name SEPA_Export_ID */
    public static final String COLUMNNAME_SEPA_Export_ID = "SEPA_Export_ID";

	/**
	 * Set SEPA_Export_Line_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSEPA_Export_Line_ID (int SEPA_Export_Line_ID);

	/**
	 * Get SEPA_Export_Line_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSEPA_Export_Line_ID();

    /** Column definition for SEPA_Export_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_SEPA_Export_Line_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "SEPA_Export_Line_ID", null);
    /** Column name SEPA_Export_Line_ID */
    public static final String COLUMNNAME_SEPA_Export_Line_ID = "SEPA_Export_Line_ID";

	/**
	 * Set SEPA_Export_Line_Retry_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSEPA_Export_Line_Retry_ID (int SEPA_Export_Line_Retry_ID);

	/**
	 * Get SEPA_Export_Line_Retry_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSEPA_Export_Line_Retry_ID();

	public de.metas.payment.sepa.model.I_SEPA_Export_Line getSEPA_Export_Line_Retry();

	public void setSEPA_Export_Line_Retry(de.metas.payment.sepa.model.I_SEPA_Export_Line SEPA_Export_Line_Retry);

    /** Column definition for SEPA_Export_Line_Retry_ID */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, de.metas.payment.sepa.model.I_SEPA_Export_Line> COLUMN_SEPA_Export_Line_Retry_ID = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, de.metas.payment.sepa.model.I_SEPA_Export_Line>(I_SEPA_Export_Line.class, "SEPA_Export_Line_Retry_ID", de.metas.payment.sepa.model.I_SEPA_Export_Line.class);
    /** Column name SEPA_Export_Line_Retry_ID */
    public static final String COLUMNNAME_SEPA_Export_Line_Retry_ID = "SEPA_Export_Line_Retry_ID";

	/**
	 * Set SEPA_MandateRefNo.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSEPA_MandateRefNo (java.lang.String SEPA_MandateRefNo);

	/**
	 * Get SEPA_MandateRefNo.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSEPA_MandateRefNo();

    /** Column definition for SEPA_MandateRefNo */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_SEPA_MandateRefNo = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "SEPA_MandateRefNo", null);
    /** Column name SEPA_MandateRefNo */
    public static final String COLUMNNAME_SEPA_MandateRefNo = "SEPA_MandateRefNo";

	/**
	 * Set StructuredRemittanceInfo.
	 * Structured Remittance Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStructuredRemittanceInfo (java.lang.String StructuredRemittanceInfo);

	/**
	 * Get StructuredRemittanceInfo.
	 * Structured Remittance Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStructuredRemittanceInfo();

    /** Column definition for StructuredRemittanceInfo */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_StructuredRemittanceInfo = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "StructuredRemittanceInfo", null);
    /** Column name StructuredRemittanceInfo */
    public static final String COLUMNNAME_StructuredRemittanceInfo = "StructuredRemittanceInfo";

	/**
	 * Set Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSwiftCode (java.lang.String SwiftCode);

	/**
	 * Get Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSwiftCode();

    /** Column definition for SwiftCode */
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_SwiftCode = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "SwiftCode", null);
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, Object>(I_SEPA_Export_Line.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_SEPA_Export_Line, org.compiere.model.I_AD_User>(I_SEPA_Export_Line.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
