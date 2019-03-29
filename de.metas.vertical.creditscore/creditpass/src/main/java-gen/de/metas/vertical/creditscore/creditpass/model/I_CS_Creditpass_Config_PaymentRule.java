package de.metas.vertical.creditscore.creditpass.model;


/** Generated Interface for CS_Creditpass_Config_PaymentRule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_CS_Creditpass_Config_PaymentRule 
{

    /** TableName=CS_Creditpass_Config_PaymentRule */
    public static final String Table_Name = "CS_Creditpass_Config_PaymentRule";

    /** AD_Table_ID=541193 */
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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_AD_Client>(I_CS_Creditpass_Config_PaymentRule.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_AD_Org>(I_CS_Creditpass_Config_PaymentRule.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_C_Currency>(I_CS_Creditpass_Config_PaymentRule.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object>(I_CS_Creditpass_Config_PaymentRule.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_AD_User>(I_CS_Creditpass_Config_PaymentRule.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set CS Creditpass Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCS_Creditpass_Config_ID (int CS_Creditpass_Config_ID);

	/**
	 * Get CS Creditpass Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCS_Creditpass_Config_ID();

	public de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config getCS_Creditpass_Config();

	public void setCS_Creditpass_Config(de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config CS_Creditpass_Config);

    /** Column definition for CS_Creditpass_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config> COLUMN_CS_Creditpass_Config_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config>(I_CS_Creditpass_Config_PaymentRule.class, "CS_Creditpass_Config_ID", de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config.class);
    /** Column name CS_Creditpass_Config_ID */
    public static final String COLUMNNAME_CS_Creditpass_Config_ID = "CS_Creditpass_Config_ID";

	/**
	 * Set CS Creditpass Configuration payment rule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCS_Creditpass_Config_PaymentRule_ID (int CS_Creditpass_Config_PaymentRule_ID);

	/**
	 * Get CS Creditpass Configuration payment rule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCS_Creditpass_Config_PaymentRule_ID();

    /** Column definition for CS_Creditpass_Config_PaymentRule_ID */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object> COLUMN_CS_Creditpass_Config_PaymentRule_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object>(I_CS_Creditpass_Config_PaymentRule.class, "CS_Creditpass_Config_PaymentRule_ID", null);
    /** Column name CS_Creditpass_Config_PaymentRule_ID */
    public static final String COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID = "CS_Creditpass_Config_PaymentRule_ID";

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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object>(I_CS_Creditpass_Config_PaymentRule.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zahlungsweise.
	 * Wie die Rechnung bezahlt wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
	 * Wie die Rechnung bezahlt wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object>(I_CS_Creditpass_Config_PaymentRule.class, "PaymentRule", null);
    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Kaufstyp.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPurchaseType (java.math.BigDecimal PurchaseType);

	/**
	 * Get Kaufstyp.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPurchaseType();

    /** Column definition for PurchaseType */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object> COLUMN_PurchaseType = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object>(I_CS_Creditpass_Config_PaymentRule.class, "PurchaseType", null);
    /** Column name PurchaseType */
    public static final String COLUMNNAME_PurchaseType = "PurchaseType";

	/**
	 * Set Preis der Überprüfung.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestPrice (java.math.BigDecimal RequestPrice);

	/**
	 * Get Preis der Überprüfung.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRequestPrice();

    /** Column definition for RequestPrice */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object> COLUMN_RequestPrice = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object>(I_CS_Creditpass_Config_PaymentRule.class, "RequestPrice", null);
    /** Column name RequestPrice */
    public static final String COLUMNNAME_RequestPrice = "RequestPrice";

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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, Object>(I_CS_Creditpass_Config_PaymentRule.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_AD_User>(I_CS_Creditpass_Config_PaymentRule.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
