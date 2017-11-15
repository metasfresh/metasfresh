package de.metas.payment.esr.model;


/** Generated Interface for ESR_PostFinanceUserNumber
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_ESR_PostFinanceUserNumber 
{

    /** TableName=ESR_PostFinanceUserNumber */
    public static final String Table_Name = "ESR_PostFinanceUserNumber";

    /** AD_Table_ID=540860 */
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
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_AD_Client>(I_ESR_PostFinanceUserNumber.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_AD_Org>(I_ESR_PostFinanceUserNumber.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_C_BP_BankAccount>(I_ESR_PostFinanceUserNumber.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
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
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object>(I_ESR_PostFinanceUserNumber.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_AD_User>(I_ESR_PostFinanceUserNumber.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set ESR_PostFinanceUserNumber.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setESR_PostFinanceUserNumber_ID (int ESR_PostFinanceUserNumber_ID);

	/**
	 * Get ESR_PostFinanceUserNumber.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getESR_PostFinanceUserNumber_ID();

    /** Column definition for ESR_PostFinanceUserNumber_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_ESR_PostFinanceUserNumber_ID = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object>(I_ESR_PostFinanceUserNumber.class, "ESR_PostFinanceUserNumber_ID", null);
    /** Column name ESR_PostFinanceUserNumber_ID */
    public static final String COLUMNNAME_ESR_PostFinanceUserNumber_ID = "ESR_PostFinanceUserNumber_ID";

	/**
	 * Set ESR Teilnehmernummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setESR_RenderedAccountNo (java.lang.String ESR_RenderedAccountNo);

	/**
	 * Get ESR Teilnehmernummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getESR_RenderedAccountNo();

    /** Column definition for ESR_RenderedAccountNo */
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_ESR_RenderedAccountNo = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object>(I_ESR_PostFinanceUserNumber.class, "ESR_RenderedAccountNo", null);
    /** Column name ESR_RenderedAccountNo */
    public static final String COLUMNNAME_ESR_RenderedAccountNo = "ESR_RenderedAccountNo";

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
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object>(I_ESR_PostFinanceUserNumber.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, Object>(I_ESR_PostFinanceUserNumber.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_ESR_PostFinanceUserNumber, org.compiere.model.I_AD_User>(I_ESR_PostFinanceUserNumber.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
