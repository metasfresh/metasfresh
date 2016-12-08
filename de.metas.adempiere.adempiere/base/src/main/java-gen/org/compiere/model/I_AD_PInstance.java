package org.compiere.model;


/** Generated Interface for AD_PInstance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_PInstance 
{

    /** TableName=AD_PInstance */
    public static final String Table_Name = "AD_PInstance";

    /** AD_Table_ID=282 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Client>(I_AD_PInstance.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sprache.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Sprache.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Org>(I_AD_PInstance.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess-Instanz.
	 * Instance of the process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Prozess-Instanz.
	 * Instance of the process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PInstance_ID();

    /** Column definition for AD_PInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_AD_PInstance_ID = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "AD_PInstance_ID", null);
    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Prozess.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Process>(I_AD_PInstance.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Role_ID();

	public org.compiere.model.I_AD_Role getAD_Role();

	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

    /** Column definition for AD_Role_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Role>(I_AD_PInstance.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_Table>(I_AD_PInstance.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_User>(I_AD_PInstance.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_User>(I_AD_PInstance.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/**
	 * Get Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorMsg();

    /** Column definition for ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Processing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsProcessing (boolean IsProcessing);

	/**
	 * Get Processing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for IsProcessing */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_IsProcessing = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "IsProcessing", null);
    /** Column name IsProcessing */
    public static final String COLUMNNAME_IsProcessing = "IsProcessing";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Ergebnis.
	 * Result of the action taken
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResult (int Result);

	/**
	 * Get Ergebnis.
	 * Result of the action taken
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getResult();

    /** Column definition for Result */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_Result = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "Result", null);
    /** Column name Result */
    public static final String COLUMNNAME_Result = "Result";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_PInstance, org.compiere.model.I_AD_User>(I_AD_PInstance.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Sql WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWhereClause (java.lang.String WhereClause);

	/**
	 * Get Sql WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWhereClause();

    /** Column definition for WhereClause */
    public static final org.adempiere.model.ModelColumn<I_AD_PInstance, Object> COLUMN_WhereClause = new org.adempiere.model.ModelColumn<I_AD_PInstance, Object>(I_AD_PInstance.class, "WhereClause", null);
    /** Column name WhereClause */
    public static final String COLUMNNAME_WhereClause = "WhereClause";
}
