package org.compiere.model;


/** Generated Interface for AD_Table_AttachmentListener
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Table_AttachmentListener 
{

    /** TableName=AD_Table_AttachmentListener */
    public static final String Table_Name = "AD_Table_AttachmentListener";

    /** AD_Table_ID=541465 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

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
	 * Set AD_JavaClass.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_JavaClass_ID (int AD_JavaClass_ID);

	/**
	 * Get AD_JavaClass.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_JavaClass_ID();

    /** Column definition for AD_JavaClass_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object> COLUMN_AD_JavaClass_ID = new org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object>(I_AD_Table_AttachmentListener.class, "AD_JavaClass_ID", null);
    /** Column name AD_JavaClass_ID */
    public static final String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

	/**
	 * Set Meldung.
	 * System Message
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Message_ID (int AD_Message_ID);

	/**
	 * Get Meldung.
	 * System Message
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Message_ID();

	public org.compiere.model.I_AD_Message getAD_Message();

	public void setAD_Message(org.compiere.model.I_AD_Message AD_Message);

    /** Column definition for AD_Message_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, org.compiere.model.I_AD_Message> COLUMN_AD_Message_ID = new org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, org.compiere.model.I_AD_Message>(I_AD_Table_AttachmentListener.class, "AD_Message_ID", org.compiere.model.I_AD_Message.class);
    /** Column name AD_Message_ID */
    public static final String COLUMNNAME_AD_Message_ID = "AD_Message_ID";

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
	 * Set AD_Table_AttachmentListener.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_AttachmentListener_ID (int AD_Table_AttachmentListener_ID);

	/**
	 * Get AD_Table_AttachmentListener.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_AttachmentListener_ID();

    /** Column definition for AD_Table_AttachmentListener_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object> COLUMN_AD_Table_AttachmentListener_ID = new org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object>(I_AD_Table_AttachmentListener.class, "AD_Table_AttachmentListener_ID", null);
    /** Column name AD_Table_AttachmentListener_ID */
    public static final String COLUMNNAME_AD_Table_AttachmentListener_ID = "AD_Table_AttachmentListener_ID";

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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object>(I_AD_Table_AttachmentListener.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object>(I_AD_Table_AttachmentListener.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Send Notification.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSendNotification (boolean IsSendNotification);

	/**
	 * Get Send Notification.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSendNotification();

    /** Column definition for IsSendNotification */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object> COLUMN_IsSendNotification = new org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object>(I_AD_Table_AttachmentListener.class, "IsSendNotification", null);
    /** Column name IsSendNotification */
    public static final String COLUMNNAME_IsSendNotification = "IsSendNotification";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object>(I_AD_Table_AttachmentListener.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Table_AttachmentListener, Object>(I_AD_Table_AttachmentListener.class, "Updated", null);
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
