package de.metas.invoicecandidate.model;


/** Generated Interface for C_ILCandHandler
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_ILCandHandler 
{

    /** TableName=C_ILCandHandler */
    public static final String Table_Name = "C_ILCandHandler";

    /** AD_Table_ID=540340 */
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_Client>(I_C_ILCandHandler.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_Org>(I_C_ILCandHandler.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_InCharge_ID();

    /** Column definition for AD_User_InCharge_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_User> COLUMN_AD_User_InCharge_ID = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_User>(I_C_ILCandHandler.class, "AD_User_InCharge_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_InCharge_ID */
    public static final String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Rechnungskandidaten-Controller.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_ILCandHandler_ID (int C_ILCandHandler_ID);

	/**
	 * Get Rechnungskandidaten-Controller.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_ILCandHandler_ID();

    /** Column definition for C_ILCandHandler_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_C_ILCandHandler_ID = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "C_ILCandHandler_ID", null);
    /** Column name C_ILCandHandler_ID */
    public static final String COLUMNNAME_C_ILCandHandler_ID = "C_ILCandHandler_ID";

	/**
	 * Set Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setClassname (java.lang.String Classname);

	/**
	 * Get Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClassname();

    /** Column definition for Classname */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_Classname = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "Classname", null);
    /** Column name Classname */
    public static final String COLUMNNAME_Classname = "Classname";

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
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_User>(I_C_ILCandHandler.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

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
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Betreuer ist Benutzer-Editierbar.
	 * Sagt aus, ob der Berteuer durch den Benutzer eingestelltwerden soll oder nicht
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIs_AD_User_InCharge_UI_Setting (boolean Is_AD_User_InCharge_UI_Setting);

	/**
	 * Get Betreuer ist Benutzer-Editierbar.
	 * Sagt aus, ob der Berteuer durch den Benutzer eingestelltwerden soll oder nicht
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean is_AD_User_InCharge_UI_Setting();

    /** Column definition for Is_AD_User_InCharge_UI_Setting */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_Is_AD_User_InCharge_UI_Setting = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "Is_AD_User_InCharge_UI_Setting", null);
    /** Column name Is_AD_User_InCharge_UI_Setting */
    public static final String COLUMNNAME_Is_AD_User_InCharge_UI_Setting = "Is_AD_User_InCharge_UI_Setting";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Name der DB-Tabelle.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTableName (java.lang.String TableName);

	/**
	 * Get Name der DB-Tabelle.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTableName();

    /** Column definition for TableName */
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_TableName = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "TableName", null);
    /** Column name TableName */
    public static final String COLUMNNAME_TableName = "TableName";

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
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, Object>(I_C_ILCandHandler.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_ILCandHandler, org.compiere.model.I_AD_User>(I_C_ILCandHandler.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
