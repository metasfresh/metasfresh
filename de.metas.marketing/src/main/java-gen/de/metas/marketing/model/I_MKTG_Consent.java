package de.metas.marketing.model;


/** Generated Interface for MKTG_Consent
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MKTG_Consent 
{

    /** TableName=MKTG_Consent */
    public static final String Table_Name = "MKTG_Consent";

    /** AD_Table_ID=540976 */
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_Client>(I_MKTG_Consent.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_Org>(I_MKTG_Consent.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_User>(I_MKTG_Consent.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_C_BPartner>(I_MKTG_Consent.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Einverständnis erklärt am.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConsentDeclaredOn (java.sql.Timestamp ConsentDeclaredOn);

	/**
	 * Get Einverständnis erklärt am.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getConsentDeclaredOn();

    /** Column definition for ConsentDeclaredOn */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, Object> COLUMN_ConsentDeclaredOn = new org.adempiere.model.ModelColumn<I_MKTG_Consent, Object>(I_MKTG_Consent.class, "ConsentDeclaredOn", null);
    /** Column name ConsentDeclaredOn */
    public static final String COLUMNNAME_ConsentDeclaredOn = "ConsentDeclaredOn";

	/**
	 * Set Einverständnis widerrufen am.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConsentRevokedOn (java.sql.Timestamp ConsentRevokedOn);

	/**
	 * Get Einverständnis widerrufen am.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getConsentRevokedOn();

    /** Column definition for ConsentRevokedOn */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, Object> COLUMN_ConsentRevokedOn = new org.adempiere.model.ModelColumn<I_MKTG_Consent, Object>(I_MKTG_Consent.class, "ConsentRevokedOn", null);
    /** Column name ConsentRevokedOn */
    public static final String COLUMNNAME_ConsentRevokedOn = "ConsentRevokedOn";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MKTG_Consent, Object>(I_MKTG_Consent.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_User>(I_MKTG_Consent.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_MKTG_Consent, Object>(I_MKTG_Consent.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MKTG_Consent, Object>(I_MKTG_Consent.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Marketing Platform GatewayId.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMarketingPlatformGatewayId (java.lang.String MarketingPlatformGatewayId);

	/**
	 * Get Marketing Platform GatewayId.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMarketingPlatformGatewayId();

    /** Column definition for MarketingPlatformGatewayId */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, Object> COLUMN_MarketingPlatformGatewayId = new org.adempiere.model.ModelColumn<I_MKTG_Consent, Object>(I_MKTG_Consent.class, "MarketingPlatformGatewayId", null);
    /** Column name MarketingPlatformGatewayId */
    public static final String COLUMNNAME_MarketingPlatformGatewayId = "MarketingPlatformGatewayId";

	/**
	 * Set MKTG_Consent.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMKTG_Consent_ID (int MKTG_Consent_ID);

	/**
	 * Get MKTG_Consent.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMKTG_Consent_ID();

    /** Column definition for MKTG_Consent_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, Object> COLUMN_MKTG_Consent_ID = new org.adempiere.model.ModelColumn<I_MKTG_Consent, Object>(I_MKTG_Consent.class, "MKTG_Consent_ID", null);
    /** Column name MKTG_Consent_ID */
    public static final String COLUMNNAME_MKTG_Consent_ID = "MKTG_Consent_ID";

	/**
	 * Set MKTG_ContactPerson.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMKTG_ContactPerson_ID (int MKTG_ContactPerson_ID);

	/**
	 * Get MKTG_ContactPerson.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMKTG_ContactPerson_ID();

	public de.metas.marketing.model.I_MKTG_ContactPerson getMKTG_ContactPerson();

	public void setMKTG_ContactPerson(de.metas.marketing.model.I_MKTG_ContactPerson MKTG_ContactPerson);

    /** Column definition for MKTG_ContactPerson_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, de.metas.marketing.model.I_MKTG_ContactPerson> COLUMN_MKTG_ContactPerson_ID = new org.adempiere.model.ModelColumn<I_MKTG_Consent, de.metas.marketing.model.I_MKTG_ContactPerson>(I_MKTG_Consent.class, "MKTG_ContactPerson_ID", de.metas.marketing.model.I_MKTG_ContactPerson.class);
    /** Column name MKTG_ContactPerson_ID */
    public static final String COLUMNNAME_MKTG_ContactPerson_ID = "MKTG_ContactPerson_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MKTG_Consent, Object>(I_MKTG_Consent.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MKTG_Consent, org.compiere.model.I_AD_User>(I_MKTG_Consent.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
