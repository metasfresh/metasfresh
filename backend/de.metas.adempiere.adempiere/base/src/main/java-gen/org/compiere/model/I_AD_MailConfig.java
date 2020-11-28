package org.compiere.model;


/** Generated Interface for AD_MailConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_MailConfig 
{

    /** TableName=AD_MailConfig */
    public static final String Table_Name = "AD_MailConfig";

    /** AD_Table_ID=540241 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_Client>(I_AD_MailConfig.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Mail Box.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_MailBox_ID (int AD_MailBox_ID);

	/**
	 * Get Mail Box.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_MailBox_ID();

	public org.compiere.model.I_AD_MailBox getAD_MailBox();

	public void setAD_MailBox(org.compiere.model.I_AD_MailBox AD_MailBox);

    /** Column definition for AD_MailBox_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_MailBox> COLUMN_AD_MailBox_ID = new org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_MailBox>(I_AD_MailConfig.class, "AD_MailBox_ID", org.compiere.model.I_AD_MailBox.class);
    /** Column name AD_MailBox_ID */
    public static final String COLUMNNAME_AD_MailBox_ID = "AD_MailBox_ID";

	/**
	 * Set Mail Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_MailConfig_ID (int AD_MailConfig_ID);

	/**
	 * Get Mail Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_MailConfig_ID();

    /** Column definition for AD_MailConfig_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, Object> COLUMN_AD_MailConfig_ID = new org.adempiere.model.ModelColumn<I_AD_MailConfig, Object>(I_AD_MailConfig.class, "AD_MailConfig_ID", null);
    /** Column name AD_MailConfig_ID */
    public static final String COLUMNNAME_AD_MailConfig_ID = "AD_MailConfig_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_Org>(I_AD_MailConfig.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_Process>(I_AD_MailConfig.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Column User To.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setColumnUserTo (java.lang.String ColumnUserTo);

	/**
	 * Get Column User To.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getColumnUserTo();

    /** Column definition for ColumnUserTo */
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, Object> COLUMN_ColumnUserTo = new org.adempiere.model.ModelColumn<I_AD_MailConfig, Object>(I_AD_MailConfig.class, "ColumnUserTo", null);
    /** Column name ColumnUserTo */
    public static final String COLUMNNAME_ColumnUserTo = "ColumnUserTo";

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
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_MailConfig, Object>(I_AD_MailConfig.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_User>(I_AD_MailConfig.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Custom Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomType (java.lang.String CustomType);

	/**
	 * Get Custom Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCustomType();

    /** Column definition for CustomType */
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, Object> COLUMN_CustomType = new org.adempiere.model.ModelColumn<I_AD_MailConfig, Object>(I_AD_MailConfig.class, "CustomType", null);
    /** Column name CustomType */
    public static final String COLUMNNAME_CustomType = "CustomType";

	/**
	 * Set Document BaseType.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocBaseType (java.lang.String DocBaseType);

	/**
	 * Get Document BaseType.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocBaseType();

    /** Column definition for DocBaseType */
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, Object> COLUMN_DocBaseType = new org.adempiere.model.ModelColumn<I_AD_MailConfig, Object>(I_AD_MailConfig.class, "DocBaseType", null);
    /** Column name DocBaseType */
    public static final String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocSubType (java.lang.String DocSubType);

	/**
	 * Get Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocSubType();

    /** Column definition for DocSubType */
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, Object> COLUMN_DocSubType = new org.adempiere.model.ModelColumn<I_AD_MailConfig, Object>(I_AD_MailConfig.class, "DocSubType", null);
    /** Column name DocSubType */
    public static final String COLUMNNAME_DocSubType = "DocSubType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_MailConfig, Object>(I_AD_MailConfig.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_MailConfig, Object>(I_AD_MailConfig.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_User>(I_AD_MailConfig.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
