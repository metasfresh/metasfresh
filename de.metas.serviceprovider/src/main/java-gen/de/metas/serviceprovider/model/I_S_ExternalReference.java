package de.metas.serviceprovider.model;


/** Generated Interface for S_ExternalReference
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_S_ExternalReference 
{

    /** TableName=S_ExternalReference */
    public static final String Table_Name = "S_ExternalReference";

    /** AD_Table_ID=541486 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_S_ExternalReference, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_S_ExternalReference, Object>(I_S_ExternalReference.class, "Created", null);
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
	 * Set External reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExternalReference (java.lang.String ExternalReference);

	/**
	 * Get External reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalReference();

    /** Column definition for ExternalReference */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalReference, Object> COLUMN_ExternalReference = new org.adempiere.model.ModelColumn<I_S_ExternalReference, Object>(I_S_ExternalReference.class, "ExternalReference", null);
    /** Column name ExternalReference */
    public static final String COLUMNNAME_ExternalReference = "ExternalReference";

	/**
	 * Set External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExternalSystem (java.lang.String ExternalSystem);

	/**
	 * Get External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalSystem();

    /** Column definition for ExternalSystem */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalReference, Object> COLUMN_ExternalSystem = new org.adempiere.model.ModelColumn<I_S_ExternalReference, Object>(I_S_ExternalReference.class, "ExternalSystem", null);
    /** Column name ExternalSystem */
    public static final String COLUMNNAME_ExternalSystem = "ExternalSystem";

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
    public static final org.adempiere.model.ModelColumn<I_S_ExternalReference, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_S_ExternalReference, Object>(I_S_ExternalReference.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalReference, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_S_ExternalReference, Object>(I_S_ExternalReference.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Referenced table ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReferenced_AD_Table_ID (int Referenced_AD_Table_ID);

	/**
	 * Get Referenced table ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getReferenced_AD_Table_ID();

    /** Column name Referenced_AD_Table_ID */
    public static final String COLUMNNAME_Referenced_AD_Table_ID = "Referenced_AD_Table_ID";

	/**
	 * Set Referenced record ID.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReferenced_Record_ID (int Referenced_Record_ID);

	/**
	 * Get Referenced record ID.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getReferenced_Record_ID();

    /** Column definition for Referenced_Record_ID */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalReference, Object> COLUMN_Referenced_Record_ID = new org.adempiere.model.ModelColumn<I_S_ExternalReference, Object>(I_S_ExternalReference.class, "Referenced_Record_ID", null);
    /** Column name Referenced_Record_ID */
    public static final String COLUMNNAME_Referenced_Record_ID = "Referenced_Record_ID";

	/**
	 * Set External reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_ExternalReference_ID (int S_ExternalReference_ID);

	/**
	 * Get External reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_ExternalReference_ID();

    /** Column definition for S_ExternalReference_ID */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalReference, Object> COLUMN_S_ExternalReference_ID = new org.adempiere.model.ModelColumn<I_S_ExternalReference, Object>(I_S_ExternalReference.class, "S_ExternalReference_ID", null);
    /** Column name S_ExternalReference_ID */
    public static final String COLUMNNAME_S_ExternalReference_ID = "S_ExternalReference_ID";

	/**
	 * Set Art.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setType (java.lang.String Type);

	/**
	 * Get Art.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getType();

    /** Column definition for Type */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalReference, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_S_ExternalReference, Object>(I_S_ExternalReference.class, "Type", null);
    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

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
    public static final org.adempiere.model.ModelColumn<I_S_ExternalReference, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_S_ExternalReference, Object>(I_S_ExternalReference.class, "Updated", null);
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
