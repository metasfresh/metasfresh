package de.metas.vertical.creditscore.creditpass.model;


/** Generated Interface for CS_Creditpass_CP_Fallback
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_CS_Creditpass_CP_Fallback 
{

    /** TableName=CS_Creditpass_CP_Fallback */
	String Table_Name = "CS_Creditpass_CP_Fallback";

    /** AD_Table_ID=541336 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */
    /** Column definition for AD_Client_ID */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, org.compiere.model.I_AD_Client>(I_CS_Creditpass_CP_Fallback.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    /** Column definition for AD_Org_ID */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, org.compiere.model.I_AD_Org>(I_CS_Creditpass_CP_Fallback.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    /** Column definition for Created */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object>(I_CS_Creditpass_CP_Fallback.class, "Created", null);
    /** Column name Created */
	String COLUMNNAME_Created = "Created";
    /** Column definition for CreatedBy */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, org.compiere.model.I_AD_User>(I_CS_Creditpass_CP_Fallback.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
	String COLUMNNAME_CreatedBy = "CreatedBy";
    /** Column definition for CS_Creditpass_Config_PaymentRule_ID */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule> COLUMN_CS_Creditpass_Config_PaymentRule_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule>(I_CS_Creditpass_CP_Fallback.class, "CS_Creditpass_Config_PaymentRule_ID", de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule.class);
    /** Column name CS_Creditpass_Config_PaymentRule_ID */
	String COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID = "CS_Creditpass_Config_PaymentRule_ID";
    /** Column definition for CS_Creditpass_CP_Fallback_ID */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object> COLUMN_CS_Creditpass_CP_Fallback_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object>(I_CS_Creditpass_CP_Fallback.class, "CS_Creditpass_CP_Fallback_ID", null);
    /** Column name CS_Creditpass_CP_Fallback_ID */
	String COLUMNNAME_CS_Creditpass_CP_Fallback_ID = "CS_Creditpass_CP_Fallback_ID";
    /** Column definition for FallbackPaymentRule */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object> COLUMN_FallbackPaymentRule = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object>(I_CS_Creditpass_CP_Fallback.class, "FallbackPaymentRule", null);
    /** Column name FallbackPaymentRule */
	String COLUMNNAME_FallbackPaymentRule = "FallbackPaymentRule";
    /** Column definition for IsActive */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object>(I_CS_Creditpass_CP_Fallback.class, "IsActive", null);
    /** Column name IsActive */
	String COLUMNNAME_IsActive = "IsActive";
    /** Column definition for Updated */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, Object>(I_CS_Creditpass_CP_Fallback.class, "Updated", null);
    /** Column name Updated */
	String COLUMNNAME_Updated = "Updated";
    /** Column definition for UpdatedBy */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_CS_Creditpass_CP_Fallback, org.compiere.model.I_AD_User>(I_CS_Creditpass_CP_Fallback.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	org.compiere.model.I_AD_Client getAD_Client();

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID(int AD_Org_ID);

	org.compiere.model.I_AD_Org getAD_Org();

	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	/**
	 * Get Zahlungsart ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCS_Creditpass_Config_PaymentRule_ID();

	/**
	 * Set Zahlungsart ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCS_Creditpass_Config_PaymentRule_ID(int CS_Creditpass_Config_PaymentRule_ID);

	de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule getCS_Creditpass_Config_PaymentRule();

	void setCS_Creditpass_Config_PaymentRule(de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule CS_Creditpass_Config_PaymentRule);

	/**
	 * Get CS Creditpass Configuration payment rule fallback.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCS_Creditpass_CP_Fallback_ID();

	/**
	 * Set CS Creditpass Configuration payment rule fallback.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCS_Creditpass_CP_Fallback_ID(int CS_Creditpass_CP_Fallback_ID);

	/**
	 * Get Zahlart Rückgriff.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getFallbackPaymentRule();

	/**
	 * Set Zahlart Rückgriff.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFallbackPaymentRule(java.lang.String FallbackPaymentRule);

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive(boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();
}
