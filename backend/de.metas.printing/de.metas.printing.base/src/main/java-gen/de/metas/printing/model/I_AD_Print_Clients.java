package de.metas.printing.model;


/** Generated Interface for AD_Print_Clients
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Print_Clients 
{

    /** TableName=AD_Print_Clients */
    public static final String Table_Name = "AD_Print_Clients";

    /** AD_Table_ID=540483 */
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
	 * Set AD_Print_Clients.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Print_Clients_ID (int AD_Print_Clients_ID);

	/**
	 * Get AD_Print_Clients.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Print_Clients_ID();

    /** Column definition for AD_Print_Clients_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object> COLUMN_AD_Print_Clients_ID = new org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object>(I_AD_Print_Clients.class, "AD_Print_Clients_ID", null);
    /** Column name AD_Print_Clients_ID */
    public static final String COLUMNNAME_AD_Print_Clients_ID = "AD_Print_Clients_ID";

	/**
	 * Set Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Session_ID (int AD_Session_ID);

	/**
	 * Get Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Session_ID();

	public org.compiere.model.I_AD_Session getAD_Session();

	public void setAD_Session(org.compiere.model.I_AD_Session AD_Session);

    /** Column definition for AD_Session_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Print_Clients, org.compiere.model.I_AD_Session> COLUMN_AD_Session_ID = new org.adempiere.model.ModelColumn<I_AD_Print_Clients, org.compiere.model.I_AD_Session>(I_AD_Print_Clients.class, "AD_Session_ID", org.compiere.model.I_AD_Session.class);
    /** Column name AD_Session_ID */
    public static final String COLUMNNAME_AD_Session_ID = "AD_Session_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object>(I_AD_Print_Clients.class, "Created", null);
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
	 * Set Letzter Kontakt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateLastPoll (java.sql.Timestamp DateLastPoll);

	/**
	 * Get Letzter Kontakt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateLastPoll();

    /** Column definition for DateLastPoll */
    public static final org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object> COLUMN_DateLastPoll = new org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object>(I_AD_Print_Clients.class, "DateLastPoll", null);
    /** Column name DateLastPoll */
    public static final String COLUMNNAME_DateLastPoll = "DateLastPoll";

	/**
	 * Set Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHostKey (java.lang.String HostKey);

	/**
	 * Get Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHostKey();

    /** Column definition for HostKey */
    public static final org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object> COLUMN_HostKey = new org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object>(I_AD_Print_Clients.class, "HostKey", null);
    /** Column name HostKey */
    public static final String COLUMNNAME_HostKey = "HostKey";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object>(I_AD_Print_Clients.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Print_Clients, Object>(I_AD_Print_Clients.class, "Updated", null);
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
