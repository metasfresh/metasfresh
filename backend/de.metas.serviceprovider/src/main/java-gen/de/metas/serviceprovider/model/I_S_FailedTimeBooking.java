package de.metas.serviceprovider.model;


/** Generated Interface for S_FailedTimeBooking
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_S_FailedTimeBooking 
{

    /** TableName=S_FailedTimeBooking */
    public static final String Table_Name = "S_FailedTimeBooking";

    /** AD_Table_ID=541487 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "Created", null);
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
	 * Set Import-Fehler.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImportErrorMsg (java.lang.String ImportErrorMsg);

	/**
	 * Get Import-Fehler.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImportErrorMsg();

    /** Column definition for ImportErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_ImportErrorMsg = new org.adempiere.model.ModelColumn<I_S_FailedTimeBooking, Object>(I_S_FailedTimeBooking.class, "ImportErrorMsg", null);
    /** Column name ImportErrorMsg */
    public static final String COLUMNNAME_ImportErrorMsg = "ImportErrorMsg";

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
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
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
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
