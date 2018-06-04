package org.compiere.model;


/** Generated Interface for AD_User_NotificationGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_User_NotificationGroup 
{

    /** TableName=AD_User_NotificationGroup */
    public static final String Table_Name = "AD_User_NotificationGroup";

    /** AD_Table_ID=540960 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_Client>(I_AD_User_NotificationGroup.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Notification group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_NotificationGroup_ID (int AD_NotificationGroup_ID);

	/**
	 * Get Notification group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_NotificationGroup_ID();

	public org.compiere.model.I_AD_NotificationGroup getAD_NotificationGroup();

	public void setAD_NotificationGroup(org.compiere.model.I_AD_NotificationGroup AD_NotificationGroup);

    /** Column definition for AD_NotificationGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_NotificationGroup> COLUMN_AD_NotificationGroup_ID = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_NotificationGroup>(I_AD_User_NotificationGroup.class, "AD_NotificationGroup_ID", org.compiere.model.I_AD_NotificationGroup.class);
    /** Column name AD_NotificationGroup_ID */
    public static final String COLUMNNAME_AD_NotificationGroup_ID = "AD_NotificationGroup_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_Org>(I_AD_User_NotificationGroup.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_User>(I_AD_User_NotificationGroup.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set User Notification Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_NotificationGroup_ID (int AD_User_NotificationGroup_ID);

	/**
	 * Get User Notification Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_NotificationGroup_ID();

    /** Column definition for AD_User_NotificationGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object> COLUMN_AD_User_NotificationGroup_ID = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object>(I_AD_User_NotificationGroup.class, "AD_User_NotificationGroup_ID", null);
    /** Column name AD_User_NotificationGroup_ID */
    public static final String COLUMNNAME_AD_User_NotificationGroup_ID = "AD_User_NotificationGroup_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object>(I_AD_User_NotificationGroup.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_User>(I_AD_User_NotificationGroup.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object>(I_AD_User_NotificationGroup.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Benachrichtigungs-Art.
	 * Art der Benachrichtigung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setNotificationType (java.lang.String NotificationType);

	/**
	 * Get Benachrichtigungs-Art.
	 * Art der Benachrichtigung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNotificationType();

    /** Column definition for NotificationType */
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object> COLUMN_NotificationType = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object>(I_AD_User_NotificationGroup.class, "NotificationType", null);
    /** Column name NotificationType */
    public static final String COLUMNNAME_NotificationType = "NotificationType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, Object>(I_AD_User_NotificationGroup.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_User_NotificationGroup, org.compiere.model.I_AD_User>(I_AD_User_NotificationGroup.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
