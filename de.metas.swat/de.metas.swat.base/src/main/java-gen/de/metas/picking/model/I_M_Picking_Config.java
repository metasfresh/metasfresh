package de.metas.picking.model;


/** Generated Interface for M_Picking_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Picking_Config 
{

    /** TableName=M_Picking_Config */
    public static final String Table_Name = "M_Picking_Config";

    /** AD_Table_ID=540873 */
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

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Picking_Config, org.compiere.model.I_AD_Client>(I_M_Picking_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Picking_Config, org.compiere.model.I_AD_Org>(I_M_Picking_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Picking_Config, Object>(I_M_Picking_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Picking_Config, org.compiere.model.I_AD_User>(I_M_Picking_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Picking_Config, Object>(I_M_Picking_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsAllowOverdelivery.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAllowOverdelivery (boolean IsAllowOverdelivery);

	/**
	 * Get IsAllowOverdelivery.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowOverdelivery();

    /** Column definition for IsAllowOverdelivery */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, Object> COLUMN_IsAllowOverdelivery = new org.adempiere.model.ModelColumn<I_M_Picking_Config, Object>(I_M_Picking_Config.class, "IsAllowOverdelivery", null);
    /** Column name IsAllowOverdelivery */
    public static final String COLUMNNAME_IsAllowOverdelivery = "IsAllowOverdelivery";

	/**
	 * Set IsAutoProcess.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAutoProcess (boolean IsAutoProcess);

	/**
	 * Get IsAutoProcess.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutoProcess();

    /** Column definition for IsAutoProcess */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, Object> COLUMN_IsAutoProcess = new org.adempiere.model.ModelColumn<I_M_Picking_Config, Object>(I_M_Picking_Config.class, "IsAutoProcess", null);
    /** Column name IsAutoProcess */
    public static final String COLUMNNAME_IsAutoProcess = "IsAutoProcess";

	/**
	 * Set Picking configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Picking_Config_ID (int M_Picking_Config_ID);

	/**
	 * Get Picking configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Picking_Config_ID();

    /** Column definition for M_Picking_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, Object> COLUMN_M_Picking_Config_ID = new org.adempiere.model.ModelColumn<I_M_Picking_Config, Object>(I_M_Picking_Config.class, "M_Picking_Config_ID", null);
    /** Column name M_Picking_Config_ID */
    public static final String COLUMNNAME_M_Picking_Config_ID = "M_Picking_Config_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Picking_Config, Object>(I_M_Picking_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Picking_Config, org.compiere.model.I_AD_User>(I_M_Picking_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Picking terminal view profile.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_PickingTerminal_ViewProfile (java.lang.String WEBUI_PickingTerminal_ViewProfile);

	/**
	 * Get Picking terminal view profile.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWEBUI_PickingTerminal_ViewProfile();

    /** Column definition for WEBUI_PickingTerminal_ViewProfile */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config, Object> COLUMN_WEBUI_PickingTerminal_ViewProfile = new org.adempiere.model.ModelColumn<I_M_Picking_Config, Object>(I_M_Picking_Config.class, "WEBUI_PickingTerminal_ViewProfile", null);
    /** Column name WEBUI_PickingTerminal_ViewProfile */
    public static final String COLUMNNAME_WEBUI_PickingTerminal_ViewProfile = "WEBUI_PickingTerminal_ViewProfile";
}
