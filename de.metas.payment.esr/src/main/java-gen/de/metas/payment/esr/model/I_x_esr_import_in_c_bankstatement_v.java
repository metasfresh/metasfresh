package de.metas.payment.esr.model;


/** Generated Interface for x_esr_import_in_c_bankstatement_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_x_esr_import_in_c_bankstatement_v 
{

    /** TableName=x_esr_import_in_c_bankstatement_v */
    public static final String Table_Name = "x_esr_import_in_c_bankstatement_v";

    /** AD_Table_ID=540685 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bankauszug.
	 * Bank Statement of account
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bankauszug.
	 * Bank Statement of account
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatement_ID();

    /** Column name C_BankStatement_ID */
    public static final String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_x_esr_import_in_c_bankstatement_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_x_esr_import_in_c_bankstatement_v, Object>(I_x_esr_import_in_c_bankstatement_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDoc();

    /** Column definition for DateDoc */
    public static final org.adempiere.model.ModelColumn<I_x_esr_import_in_c_bankstatement_v, Object> COLUMN_DateDoc = new org.adempiere.model.ModelColumn<I_x_esr_import_in_c_bankstatement_v, Object>(I_x_esr_import_in_c_bankstatement_v.class, "DateDoc", null);
    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set ESR Zahlungsimport.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESR_Import_ID (int ESR_Import_ID);

	/**
	 * Get ESR Zahlungsimport.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getESR_Import_ID();

	public de.metas.payment.esr.model.I_ESR_Import getESR_Import();

	public void setESR_Import(de.metas.payment.esr.model.I_ESR_Import ESR_Import);

    /** Column definition for ESR_Import_ID */
    public static final org.adempiere.model.ModelColumn<I_x_esr_import_in_c_bankstatement_v, de.metas.payment.esr.model.I_ESR_Import> COLUMN_ESR_Import_ID = new org.adempiere.model.ModelColumn<I_x_esr_import_in_c_bankstatement_v, de.metas.payment.esr.model.I_ESR_Import>(I_x_esr_import_in_c_bankstatement_v.class, "ESR_Import_ID", de.metas.payment.esr.model.I_ESR_Import.class);
    /** Column name ESR_Import_ID */
    public static final String COLUMNNAME_ESR_Import_ID = "ESR_Import_ID";
}
