package de.metas.marketing.model;


/** Generated Interface for MKTG_ContactPerson_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MKTG_ContactPerson_Attribute 
{

    /** TableName=MKTG_ContactPerson_Attribute */
    public static final String Table_Name = "MKTG_ContactPerson_Attribute";

    /** AD_Table_ID=540973 */
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_AD_Client>(I_MKTG_ContactPerson_Attribute.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_AD_Org>(I_MKTG_ContactPerson_Attribute.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, Object>(I_MKTG_ContactPerson_Attribute.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_AD_User>(I_MKTG_ContactPerson_Attribute.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, Object>(I_MKTG_ContactPerson_Attribute.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Attribute_ID();

	public org.compiere.model.I_M_Attribute getM_Attribute();

	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute);

    /** Column definition for M_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_M_Attribute> COLUMN_M_Attribute_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_M_Attribute>(I_MKTG_ContactPerson_Attribute.class, "M_Attribute_ID", org.compiere.model.I_M_Attribute.class);
    /** Column name M_Attribute_ID */
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set MKTG_ContactPerson_Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMKTG_ContactPerson_Attribute_ID (int MKTG_ContactPerson_Attribute_ID);

	/**
	 * Get MKTG_ContactPerson_Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMKTG_ContactPerson_Attribute_ID();

    /** Column definition for MKTG_ContactPerson_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, Object> COLUMN_MKTG_ContactPerson_Attribute_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, Object>(I_MKTG_ContactPerson_Attribute.class, "MKTG_ContactPerson_Attribute_ID", null);
    /** Column name MKTG_ContactPerson_Attribute_ID */
    public static final String COLUMNNAME_MKTG_ContactPerson_Attribute_ID = "MKTG_ContactPerson_Attribute_ID";

	/**
	 * Set MKTG_ContactPerson.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMKTG_ContactPerson_ID (int MKTG_ContactPerson_ID);

	/**
	 * Get MKTG_ContactPerson.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMKTG_ContactPerson_ID();

	public de.metas.marketing.model.I_MKTG_ContactPerson getMKTG_ContactPerson();

	public void setMKTG_ContactPerson(de.metas.marketing.model.I_MKTG_ContactPerson MKTG_ContactPerson);

    /** Column definition for MKTG_ContactPerson_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, de.metas.marketing.model.I_MKTG_ContactPerson> COLUMN_MKTG_ContactPerson_ID = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, de.metas.marketing.model.I_MKTG_ContactPerson>(I_MKTG_ContactPerson_Attribute.class, "MKTG_ContactPerson_ID", de.metas.marketing.model.I_MKTG_ContactPerson.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, Object>(I_MKTG_ContactPerson_Attribute.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MKTG_ContactPerson_Attribute, org.compiere.model.I_AD_User>(I_MKTG_ContactPerson_Attribute.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
