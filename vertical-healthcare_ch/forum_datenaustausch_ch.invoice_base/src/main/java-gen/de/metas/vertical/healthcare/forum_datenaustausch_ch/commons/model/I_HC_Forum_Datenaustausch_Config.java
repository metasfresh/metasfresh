package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model;


/** Generated Interface for HC_Forum_Datenaustausch_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_HC_Forum_Datenaustausch_Config 
{

    /** TableName=HC_Forum_Datenaustausch_Config */
    public static final String Table_Name = "HC_Forum_Datenaustausch_Config";

    /** AD_Table_ID=541145 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_Client>(I_HC_Forum_Datenaustausch_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_Org>(I_HC_Forum_Datenaustausch_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_C_BPartner>(I_HC_Forum_Datenaustausch_Config.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_User>(I_HC_Forum_Datenaustausch_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Export XML Version.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExportXmlVersion (java.lang.String ExportXmlVersion);

	/**
	 * Get Export XML Version.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExportXmlVersion();

    /** Column definition for ExportXmlVersion */
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_ExportXmlVersion = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "ExportXmlVersion", null);
    /** Column name ExportXmlVersion */
    public static final String COLUMNNAME_ExportXmlVersion = "ExportXmlVersion";

	/**
	 * Set HC_Forum_Datenaustausch.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHC_Forum_Datenaustausch_ID (int HC_Forum_Datenaustausch_ID);

	/**
	 * Get HC_Forum_Datenaustausch.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getHC_Forum_Datenaustausch_ID();

    /** Column definition for HC_Forum_Datenaustausch_ID */
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_HC_Forum_Datenaustausch_ID = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "HC_Forum_Datenaustausch_ID", null);
    /** Column name HC_Forum_Datenaustausch_ID */
    public static final String COLUMNNAME_HC_Forum_Datenaustausch_ID = "HC_Forum_Datenaustausch_ID";

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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_User>(I_HC_Forum_Datenaustausch_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
