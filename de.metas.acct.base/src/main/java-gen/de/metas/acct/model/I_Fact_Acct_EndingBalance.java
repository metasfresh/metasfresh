package de.metas.acct.model;


/** Generated Interface for Fact_Acct_EndingBalance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_Fact_Acct_EndingBalance 
{

    /** TableName=Fact_Acct_EndingBalance */
    public static final String Table_Name = "Fact_Acct_EndingBalance";

    /** AD_Table_ID=540696 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Konto.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAccount_ID (int Account_ID);

	/**
	 * Get Konto.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAccount_ID();

	public org.compiere.model.I_C_ElementValue getAccount();

	public void setAccount(org.compiere.model.I_C_ElementValue Account);

    /** Column definition for Account_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_C_ElementValue> COLUMN_Account_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_C_ElementValue>(I_Fact_Acct_EndingBalance.class, "Account_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name Account_ID */
    public static final String COLUMNNAME_Account_ID = "Account_ID";

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_AD_Client>(I_Fact_Acct_EndingBalance.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_AD_Org>(I_Fact_Acct_EndingBalance.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Haben (Day to Date).
	 * Credit from day beginning to date
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctCr_DTD (java.math.BigDecimal AmtAcctCr_DTD);

	/**
	 * Get Haben (Day to Date).
	 * Credit from day beginning to date
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctCr_DTD();

    /** Column definition for AmtAcctCr_DTD */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object> COLUMN_AmtAcctCr_DTD = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object>(I_Fact_Acct_EndingBalance.class, "AmtAcctCr_DTD", null);
    /** Column name AmtAcctCr_DTD */
    public static final String COLUMNNAME_AmtAcctCr_DTD = "AmtAcctCr_DTD";

	/**
	 * Set Soll (Day to Date).
	 * Debit from day beginning to Date
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctDr_DTD (java.math.BigDecimal AmtAcctDr_DTD);

	/**
	 * Get Soll (Day to Date).
	 * Debit from day beginning to Date
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctDr_DTD();

    /** Column definition for AmtAcctDr_DTD */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object> COLUMN_AmtAcctDr_DTD = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object>(I_Fact_Acct_EndingBalance.class, "AmtAcctDr_DTD", null);
    /** Column name AmtAcctDr_DTD */
    public static final String COLUMNNAME_AmtAcctDr_DTD = "AmtAcctDr_DTD";

	/**
	 * Set Buchführungs-Schema.
	 * Stammdaten für Buchhaltung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Stammdaten für Buchhaltung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_C_AcctSchema>(I_Fact_Acct_EndingBalance.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object>(I_Fact_Acct_EndingBalance.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_AD_User>(I_Fact_Acct_EndingBalance.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object>(I_Fact_Acct_EndingBalance.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Accounting Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFact_Acct_ID (int Fact_Acct_ID);

	/**
	 * Get Accounting Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFact_Acct_ID();

    /** Column definition for Fact_Acct_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object> COLUMN_Fact_Acct_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object>(I_Fact_Acct_EndingBalance.class, "Fact_Acct_ID", null);
    /** Column name Fact_Acct_ID */
    public static final String COLUMNNAME_Fact_Acct_ID = "Fact_Acct_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object>(I_Fact_Acct_EndingBalance.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Buchungsart.
	 * Die Art des gebuchten Betrages dieser Transaktion
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostingType (java.lang.String PostingType);

	/**
	 * Get Buchungsart.
	 * Die Art des gebuchten Betrages dieser Transaktion
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostingType();

    /** Column definition for PostingType */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object> COLUMN_PostingType = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object>(I_Fact_Acct_EndingBalance.class, "PostingType", null);
    /** Column name PostingType */
    public static final String COLUMNNAME_PostingType = "PostingType";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, Object>(I_Fact_Acct_EndingBalance.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_Fact_Acct_EndingBalance, org.compiere.model.I_AD_User>(I_Fact_Acct_EndingBalance.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
