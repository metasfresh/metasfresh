package de.metas.contracts.commission.model;


/** Generated Interface for C_HierarchyCommissionSettings
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_HierarchyCommissionSettings 
{

    /** TableName=C_HierarchyCommissionSettings */
    public static final String Table_Name = "C_HierarchyCommissionSettings";

    /** AD_Table_ID=541425 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, org.compiere.model.I_AD_Client>(I_C_HierarchyCommissionSettings.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, org.compiere.model.I_AD_Org>(I_C_HierarchyCommissionSettings.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Vertragsbedingungen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Vertragsbedingungen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Conditions_ID();

    /** Column definition for C_Flatrate_Conditions_ID */
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_C_Flatrate_Conditions_ID = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object>(I_C_HierarchyCommissionSettings.class, "C_Flatrate_Conditions_ID", null);
    /** Column name C_Flatrate_Conditions_ID */
    public static final String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set Einstellungen für Hierachie-Provisionsverträge.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_HierarchyCommissionSettings_ID (int C_HierarchyCommissionSettings_ID);

	/**
	 * Get Einstellungen für Hierachie-Provisionsverträge.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_HierarchyCommissionSettings_ID();

    /** Column definition for C_HierarchyCommissionSettings_ID */
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_C_HierarchyCommissionSettings_ID = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object>(I_C_HierarchyCommissionSettings.class, "C_HierarchyCommissionSettings_ID", null);
    /** Column name C_HierarchyCommissionSettings_ID */
    public static final String COLUMNNAME_C_HierarchyCommissionSettings_ID = "C_HierarchyCommissionSettings_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object>(I_C_HierarchyCommissionSettings.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, org.compiere.model.I_AD_User>(I_C_HierarchyCommissionSettings.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object>(I_C_HierarchyCommissionSettings.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Provision von Basis abziehen.
	 * Legt fest, ob die für untere Hierarchie-Ebenen ermittelten Provisionspunkte bei der aktuellen Ebene von der Basispunktzahl abgezogen werden sollen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSubtractLowerLevelCommissionFromBase (boolean IsSubtractLowerLevelCommissionFromBase);

	/**
	 * Get Provision von Basis abziehen.
	 * Legt fest, ob die für untere Hierarchie-Ebenen ermittelten Provisionspunkte bei der aktuellen Ebene von der Basispunktzahl abgezogen werden sollen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSubtractLowerLevelCommissionFromBase();

    /** Column definition for IsSubtractLowerLevelCommissionFromBase */
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_IsSubtractLowerLevelCommissionFromBase = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object>(I_C_HierarchyCommissionSettings.class, "IsSubtractLowerLevelCommissionFromBase", null);
    /** Column name IsSubtractLowerLevelCommissionFromBase */
    public static final String COLUMNNAME_IsSubtractLowerLevelCommissionFromBase = "IsSubtractLowerLevelCommissionFromBase";

	/**
	 * Set % der Basispunkte.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPercentOfBasePoints (java.math.BigDecimal PercentOfBasePoints);

	/**
	 * Get % der Basispunkte.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPercentOfBasePoints();

    /** Column definition for PercentOfBasePoints */
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_PercentOfBasePoints = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object>(I_C_HierarchyCommissionSettings.class, "PercentOfBasePoints", null);
    /** Column name PercentOfBasePoints */
    public static final String COLUMNNAME_PercentOfBasePoints = "PercentOfBasePoints";

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
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, Object>(I_C_HierarchyCommissionSettings.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_HierarchyCommissionSettings, org.compiere.model.I_AD_User>(I_C_HierarchyCommissionSettings.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
