package org.compiere.model;


/** Generated Interface for AD_AttachmentEntry
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_AttachmentEntry 
{

    /** TableName=AD_AttachmentEntry */
    public static final String Table_Name = "AD_AttachmentEntry";

    /** AD_Table_ID=540833 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Set Anlage.
	 * Anlage zum Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Attachment_ID (int AD_Attachment_ID);

	/**
	 * Get Anlage.
	 * Anlage zum Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Attachment_ID();

	public org.compiere.model.I_AD_Attachment getAD_Attachment();

	public void setAD_Attachment(org.compiere.model.I_AD_Attachment AD_Attachment);

    /** Column definition for AD_Attachment_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_Attachment> COLUMN_AD_Attachment_ID = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_Attachment>(I_AD_AttachmentEntry.class, "AD_Attachment_ID", org.compiere.model.I_AD_Attachment.class);
    /** Column name AD_Attachment_ID */
    public static final String COLUMNNAME_AD_Attachment_ID = "AD_Attachment_ID";

	/**
	 * Set Attachment entry.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID);

	/**
	 * Get Attachment entry.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_AttachmentEntry_ID();

    /** Column definition for AD_AttachmentEntry_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_AD_AttachmentEntry_ID = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "AD_AttachmentEntry_ID", null);
    /** Column name AD_AttachmentEntry_ID */
    public static final String COLUMNNAME_AD_AttachmentEntry_ID = "AD_AttachmentEntry_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_Client>(I_AD_AttachmentEntry.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_Org>(I_AD_AttachmentEntry.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_Table>(I_AD_AttachmentEntry.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Binärwert.
	 * Binärer Wert
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false (lazy loading)
	 */
	public void setBinaryData (byte[] BinaryData);

	/**
	 * Get Binärwert.
	 * Binärer Wert
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false (lazy loading)
	 */
	public byte[] getBinaryData();

    /** Column definition for BinaryData */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_BinaryData = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "BinaryData", null);
    /** Column name BinaryData */
    public static final String COLUMNNAME_BinaryData = "BinaryData";

	/**
	 * Set Content type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setContentType (java.lang.String ContentType);

	/**
	 * Get Content type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getContentType();

    /** Column definition for ContentType */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_ContentType = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "ContentType", null);
    /** Column name ContentType */
    public static final String COLUMNNAME_ContentType = "ContentType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_User>(I_AD_AttachmentEntry.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFileName (java.lang.String FileName);

	/**
	 * Get File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFileName();

    /** Column definition for FileName */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_FileName = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "FileName", null);
    /** Column name FileName */
    public static final String COLUMNNAME_FileName = "FileName";

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
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setType (java.lang.String Type);

	/**
	 * Get Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getType();

    /** Column definition for Type */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "Type", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_User>(I_AD_AttachmentEntry.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setURL (java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getURL();

    /** Column definition for URL */
    public static final org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_URL = new org.adempiere.model.ModelColumn<I_AD_AttachmentEntry, Object>(I_AD_AttachmentEntry.class, "URL", null);
    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";
}
