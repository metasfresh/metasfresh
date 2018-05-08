package de.metas.marketing.model;


/** Generated Interface for MKTG_ContactPerson_With_Campaign_ID_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MKTG_ContactPerson_With_Campaign_ID_V 
{

    /** TableName=MKTG_ContactPerson_With_Campaign_ID_V */
    public static final String Table_Name = "MKTG_ContactPerson_With_Campaign_ID_V";

    /** AD_Table_ID=540974 */
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_Client>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_Org>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_User>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_C_BPartner>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_User>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail (java.lang.String EMail);

	/**
	 * Get eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail();

    /** Column definition for EMail */
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "EMail", null);
    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MKTG_Campaign.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMKTG_Campaign_ID (int MKTG_Campaign_ID);

	/**
	 * Get MKTG_Campaign.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMKTG_Campaign_ID();

	public de.metas.marketing.model.I_MKTG_Campaign getMKTG_Campaign();

	public void setMKTG_Campaign(de.metas.marketing.model.I_MKTG_Campaign MKTG_Campaign);

    /** Column definition for MKTG_Campaign_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, de.metas.marketing.model.I_MKTG_Campaign> COLUMN_MKTG_Campaign_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, de.metas.marketing.model.I_MKTG_Campaign>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "MKTG_Campaign_ID", de.metas.marketing.model.I_MKTG_Campaign.class);
    /** Column name MKTG_Campaign_ID */
    public static final String COLUMNNAME_MKTG_Campaign_ID = "MKTG_Campaign_ID";

	/**
	 * Set MKTG_ContactPerson_With_Campaign_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMKTG_ContactPerson_With_Campaign_ID_ID (int MKTG_ContactPerson_With_Campaign_ID_ID);

	/**
	 * Get MKTG_ContactPerson_With_Campaign_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMKTG_ContactPerson_With_Campaign_ID_ID();

    /** Column definition for MKTG_ContactPerson_With_Campaign_ID_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object> COLUMN_MKTG_ContactPerson_With_Campaign_ID_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "MKTG_ContactPerson_With_Campaign_ID_ID", null);
    /** Column name MKTG_ContactPerson_With_Campaign_ID_ID */
    public static final String COLUMNNAME_MKTG_ContactPerson_With_Campaign_ID_ID = "MKTG_ContactPerson_With_Campaign_ID_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, Object>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_AD_User>(I_MKTG_ContactPerson_With_Campaign_ID_V.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
