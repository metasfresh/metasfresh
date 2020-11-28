package de.metas.inoutcandidate.model;


/** Generated Interface for M_IolCandHandler
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_IolCandHandler 
{

    /** TableName=M_IolCandHandler */
    public static final String Table_Name = "M_IolCandHandler";

    /** AD_Table_ID=540385 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

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
	 * Get Created.
	 * Date this record was created
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
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
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
	 * Get Updated.
	 * Date this record was updated
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
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
