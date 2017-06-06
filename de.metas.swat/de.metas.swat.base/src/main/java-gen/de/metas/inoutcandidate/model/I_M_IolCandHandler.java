package de.metas.inoutcandidate.model;


/** Generated Interface for M_IolCandHandler
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_IolCandHandler 
{

    /** TableName=M_IolCandHandler */
    public static final String Table_Name = "M_IolCandHandler";

    /** AD_Table_ID=540385 */
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

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, org.compiere.model.I_AD_Client>(I_M_IolCandHandler.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, org.compiere.model.I_AD_Org>(I_M_IolCandHandler.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object> COLUMN_Classname = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object>(I_M_IolCandHandler.class, "Classname", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object>(I_M_IolCandHandler.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, org.compiere.model.I_AD_User>(I_M_IolCandHandler.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object>(I_M_IolCandHandler.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set M_IolCandHandler.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID);

	/**
	 * Get M_IolCandHandler.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_IolCandHandler_ID();

    /** Column definition for M_IolCandHandler_ID */
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object> COLUMN_M_IolCandHandler_ID = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object>(I_M_IolCandHandler.class, "M_IolCandHandler_ID", null);
    /** Column name M_IolCandHandler_ID */
    public static final String COLUMNNAME_M_IolCandHandler_ID = "M_IolCandHandler_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object> COLUMN_TableName = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object>(I_M_IolCandHandler.class, "TableName", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, Object>(I_M_IolCandHandler.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_IolCandHandler, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_IolCandHandler, org.compiere.model.I_AD_User>(I_M_IolCandHandler.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
