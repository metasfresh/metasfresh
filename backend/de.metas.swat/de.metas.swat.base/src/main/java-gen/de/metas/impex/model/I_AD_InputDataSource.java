package de.metas.impex.model;


/** Generated Interface for AD_InputDataSource
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_AD_InputDataSource
{

    /** TableName=AD_InputDataSource */
    public static final String Table_Name = "AD_InputDataSource";

    /** AD_Table_ID=540273 */
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
	 * Set Eingabequelle.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_InputDataSource_ID (int AD_InputDataSource_ID);

	/**
	 * Get Eingabequelle.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_InputDataSource_ID();

    /** Column definition for AD_InputDataSource_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_AD_InputDataSource_ID = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "AD_InputDataSource_ID", null);
    /** Column name AD_InputDataSource_ID */
    public static final String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalId();

    /** Column definition for ExternalId */
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_ExternalId = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "ExternalId", null);
    /** Column name ExternalId */
    public static final String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInternalName (java.lang.String InternalName);

	/**
	 * Get Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInternalName();

    /** Column definition for InternalName */
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_InternalName = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "InternalName", null);
    /** Column name InternalName */
    public static final String COLUMNNAME_InternalName = "InternalName";

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
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsDestination.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDestination (boolean IsDestination);

	/**
	 * Get IsDestination.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDestination();

    /** Column definition for IsDestination */
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_IsDestination = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "IsDestination", null);
    /** Column name IsDestination */
    public static final String COLUMNNAME_IsDestination = "IsDestination";

	/**
	 * Set Beleg soll per EDI übermittelt werden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEdiEnabled (boolean IsEdiEnabled);

	/**
	 * Get Beleg soll per EDI übermittelt werden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEdiEnabled();

    /** Column definition for IsEdiEnabled */
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_IsEdiEnabled = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "IsEdiEnabled", null);
    /** Column name IsEdiEnabled */
    public static final String COLUMNNAME_IsEdiEnabled = "IsEdiEnabled";

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
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "Updated", null);
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

	/**
	 * Set URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setURL (java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getURL();

    /** Column definition for URL */
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_URL = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "URL", null);
    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_AD_InputDataSource, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<>(I_AD_InputDataSource.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
