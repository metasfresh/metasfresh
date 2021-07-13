package org.compiere.model;


/** Generated Interface for S_PostgREST_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_S_PostgREST_Config 
{

    /** TableName=S_PostgREST_Config */
    public static final String Table_Name = "S_PostgREST_Config";

    /** AD_Table_ID=541503 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBase_url (java.lang.String Base_url);

	/**
	 * Get URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBase_url();

    /** Column definition for Base_url */
    public static final org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Base_url = new org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object>(I_S_PostgREST_Config.class, "Base_url", null);
    /** Column name Base_url */
    public static final String COLUMNNAME_Base_url = "Base_url";

	/**
	 * Set Connection timeout.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setConnection_timeout (int Connection_timeout);

	/**
	 * Get Connection timeout.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getConnection_timeout();

    /** Column definition for Connection_timeout */
    public static final org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Connection_timeout = new org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object>(I_S_PostgREST_Config.class, "Connection_timeout", null);
    /** Column name Connection_timeout */
    public static final String COLUMNNAME_Connection_timeout = "Connection_timeout";

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
    public static final org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object>(I_S_PostgREST_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object>(I_S_PostgREST_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PostgREST Configs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostgREST_Config_ID (int PostgREST_Config_ID);

	/**
	 * Get PostgREST Configs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPostgREST_Config_ID();

    /** Column definition for PostgREST_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object> COLUMN_PostgREST_Config_ID = new org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object>(I_S_PostgREST_Config.class, "PostgREST_Config_ID", null);
    /** Column name PostgREST_Config_ID */
    public static final String COLUMNNAME_PostgREST_Config_ID = "PostgREST_Config_ID";

	/**
	 * Set Read timeout.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRead_timeout (int Read_timeout);

	/**
	 * Get Read timeout.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRead_timeout();

    /** Column definition for Read_timeout */
    public static final org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Read_timeout = new org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object>(I_S_PostgREST_Config.class, "Read_timeout", null);
    /** Column name Read_timeout */
    public static final String COLUMNNAME_Read_timeout = "Read_timeout";

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
    public static final org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_S_PostgREST_Config, Object>(I_S_PostgREST_Config.class, "Updated", null);
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
