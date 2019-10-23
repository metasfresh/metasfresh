package de.metas.letters.model;


/** Generated Interface for AD_BoilerPlate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_BoilerPlate 
{

    /** TableName=AD_BoilerPlate */
    public static final String Table_Name = "AD_BoilerPlate";

    /** AD_Table_ID=504410 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Set Textbaustein.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID);

	/**
	 * Get Textbaustein.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_BoilerPlate_ID();

    /** Column definition for AD_BoilerPlate_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object> COLUMN_AD_BoilerPlate_ID = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object>(I_AD_BoilerPlate.class, "AD_BoilerPlate_ID", null);
    /** Column name AD_BoilerPlate_ID */
    public static final String COLUMNNAME_AD_BoilerPlate_ID = "AD_BoilerPlate_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_Client>(I_AD_BoilerPlate.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_Org>(I_AD_BoilerPlate.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object>(I_AD_BoilerPlate.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_User>(I_AD_BoilerPlate.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object>(I_AD_BoilerPlate.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Jasper Process.
	 * The Jasper Process used by the printengine if any process defined
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJasperProcess_ID (int JasperProcess_ID);

	/**
	 * Get Jasper Process.
	 * The Jasper Process used by the printengine if any process defined
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getJasperProcess_ID();

	public org.compiere.model.I_AD_Process getJasperProcess();

	public void setJasperProcess(org.compiere.model.I_AD_Process JasperProcess);

    /** Column definition for JasperProcess_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_Process> COLUMN_JasperProcess_ID = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_Process>(I_AD_BoilerPlate.class, "JasperProcess_ID", org.compiere.model.I_AD_Process.class);
    /** Column name JasperProcess_ID */
    public static final String COLUMNNAME_JasperProcess_ID = "JasperProcess_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object>(I_AD_BoilerPlate.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Betreff.
	 * Mail Betreff
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSubject (java.lang.String Subject);

	/**
	 * Get Betreff.
	 * Mail Betreff
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSubject();

    /** Column definition for Subject */
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object> COLUMN_Subject = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object>(I_AD_BoilerPlate.class, "Subject", null);
    /** Column name Subject */
    public static final String COLUMNNAME_Subject = "Subject";

	/**
	 * Set Text.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTextSnippet (java.lang.String TextSnippet);

	/**
	 * Get Text.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTextSnippet();

    /** Column definition for TextSnippet */
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object> COLUMN_TextSnippet = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object>(I_AD_BoilerPlate.class, "TextSnippet", null);
    /** Column name TextSnippet */
    public static final String COLUMNNAME_TextSnippet = "TextSnippet";

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
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, Object>(I_AD_BoilerPlate.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_BoilerPlate, org.compiere.model.I_AD_User>(I_AD_BoilerPlate.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
