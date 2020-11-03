package de.metas.serviceprovider.model;


/** Generated Interface for S_FailedTimeBooking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_S_FailedTimeBooking 
{

    /** TableName=S_FailedTimeBooking */
    public static final String Table_Name = "S_FailedTimeBooking";

    /** AD_Table_ID=541487 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "Created", null);
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
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalId();

    /** Column definition for ExternalId */
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_ExternalId = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "ExternalId", null);
    /** Column name ExternalId */
    public static final String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalSystem (java.lang.String ExternalSystem);

	/**
	 * Get External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalSystem();

    /** Column definition for ExternalSystem */
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_ExternalSystem = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "ExternalSystem", null);
    /** Column name ExternalSystem */
    public static final String COLUMNNAME_ExternalSystem = "ExternalSystem";

	/**
	 * Set Error Message.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImportErrorMsg (java.lang.String ImportErrorMsg);

	/**
	 * Get Error Message.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImportErrorMsg();

    /** Column definition for ImportErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_ImportErrorMsg = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "ImportErrorMsg", null);
    /** Column name ImportErrorMsg */
    public static final String COLUMNNAME_ImportErrorMsg = "ImportErrorMsg";

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
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set JSON value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJSONValue (java.lang.String JSONValue);

	/**
	 * Get JSON value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJSONValue();

    /** Column definition for JSONValue */
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_JSONValue = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "JSONValue", null);
    /** Column name JSONValue */
    public static final String COLUMNNAME_JSONValue = "JSONValue";

	/**
	 * Set Failed time booking.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setS_FailedTimeBooking_ID (int S_FailedTimeBooking_ID);

	/**
	 * Get Failed time booking.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getS_FailedTimeBooking_ID();

    /** Column definition for S_FailedTimeBooking_ID */
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_S_FailedTimeBooking_ID = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "S_FailedTimeBooking_ID", null);
    /** Column name S_FailedTimeBooking_ID */
    public static final String COLUMNNAME_S_FailedTimeBooking_ID = "S_FailedTimeBooking_ID";

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
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "Updated", null);
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
