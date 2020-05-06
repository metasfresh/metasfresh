package de.metas.vertical.pharma.securpharm.model;


/** Generated Interface for M_Securpharm_Action_Result
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Securpharm_Action_Result 
{

    /** TableName=M_Securpharm_Action_Result */
    public static final String Table_Name = "M_Securpharm_Action_Result";

    /** AD_Table_ID=541350 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Aktion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAction (java.lang.String Action);

	/**
	 * Get Aktion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAction();

    /** Column definition for Action */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_Action = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "Action", null);
    /** Column name Action */
    public static final String COLUMNNAME_Action = "Action";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_Client>(I_M_Securpharm_Action_Result.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_Org>(I_M_Securpharm_Action_Result.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_User>(I_M_Securpharm_Action_Result.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Inventur.
	 * Parameter für eine physische Inventur
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Inventur.
	 * Parameter für eine physische Inventur
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Inventory_ID();

	public org.compiere.model.I_M_Inventory getM_Inventory();

	public void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory);

    /** Column definition for M_Inventory_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_M_Inventory> COLUMN_M_Inventory_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_M_Inventory>(I_M_Securpharm_Action_Result.class, "M_Inventory_ID", org.compiere.model.I_M_Inventory.class);
    /** Column name M_Inventory_ID */
    public static final String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/**
	 * Set Securpharm Aktion Ergebnise.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Securpharm_Action_Result_ID (int M_Securpharm_Action_Result_ID);

	/**
	 * Get Securpharm Aktion Ergebnise.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Securpharm_Action_Result_ID();

    /** Column definition for M_Securpharm_Action_Result_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_M_Securpharm_Action_Result_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "M_Securpharm_Action_Result_ID", null);
    /** Column name M_Securpharm_Action_Result_ID */
    public static final String COLUMNNAME_M_Securpharm_Action_Result_ID = "M_Securpharm_Action_Result_ID";

	/**
	 * Set Securpharm Produktdaten Ergebnise.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Securpharm_Productdata_Result_ID (int M_Securpharm_Productdata_Result_ID);

	/**
	 * Get Securpharm Produktdaten Ergebnise.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Securpharm_Productdata_Result_ID();

	public de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result getM_Securpharm_Productdata_Result();

	public void setM_Securpharm_Productdata_Result(de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result M_Securpharm_Productdata_Result);

    /** Column definition for M_Securpharm_Productdata_Result_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result> COLUMN_M_Securpharm_Productdata_Result_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result>(I_M_Securpharm_Action_Result.class, "M_Securpharm_Productdata_Result_ID", de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result.class);
    /** Column name M_Securpharm_Productdata_Result_ID */
    public static final String COLUMNNAME_M_Securpharm_Productdata_Result_ID = "M_Securpharm_Productdata_Result_ID";

	/**
	 * Set TransaktionsID Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTransactionIDServer (java.lang.String TransactionIDServer);

	/**
	 * Get TransaktionsID Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTransactionIDServer();

    /** Column definition for TransactionIDServer */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_TransactionIDServer = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "TransactionIDServer", null);
    /** Column name TransactionIDServer */
    public static final String COLUMNNAME_TransactionIDServer = "TransactionIDServer";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_User>(I_M_Securpharm_Action_Result.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
