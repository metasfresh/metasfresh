package de.metas.contracts.commission.model;


/** Generated Interface for C_CommissionSettingsLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_CommissionSettingsLine 
{

    /** TableName=C_CommissionSettingsLine */
    public static final String Table_Name = "C_CommissionSettingsLine";

    /** AD_Table_ID=541429 */
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
	 * Set Geschäftspartnergruppe.
	 * Geschäftspartnergruppe
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Geschäftspartnergruppe.
	 * Geschäftspartnergruppe
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group();

	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group);

    /** Column definition for C_BP_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, org.compiere.model.I_C_BP_Group>(I_C_CommissionSettingsLine.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Einstellungsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_CommissionSettingsLine_ID (int C_CommissionSettingsLine_ID);

	/**
	 * Get Einstellungsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_CommissionSettingsLine_ID();

    /** Column definition for C_CommissionSettingsLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_C_CommissionSettingsLine_ID = new org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object>(I_C_CommissionSettingsLine.class, "C_CommissionSettingsLine_ID", null);
    /** Column name C_CommissionSettingsLine_ID */
    public static final String COLUMNNAME_C_CommissionSettingsLine_ID = "C_CommissionSettingsLine_ID";

	/**
	 * Set Einstellungen für Hierachie-Provisionsverträge.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_HierarchyCommissionSettings_ID (int C_HierarchyCommissionSettings_ID);

	/**
	 * Get Einstellungen für Hierachie-Provisionsverträge.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_HierarchyCommissionSettings_ID();

	public de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings getC_HierarchyCommissionSettings();

	public void setC_HierarchyCommissionSettings(de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings C_HierarchyCommissionSettings);

    /** Column definition for C_HierarchyCommissionSettings_ID */
    public static final org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings> COLUMN_C_HierarchyCommissionSettings_ID = new org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings>(I_C_CommissionSettingsLine.class, "C_HierarchyCommissionSettings_ID", de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object>(I_C_CommissionSettingsLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object>(I_C_CommissionSettingsLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set % der Basispunkte.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPercentOfBasePoints (java.math.BigDecimal PercentOfBasePoints);

	/**
	 * Get % der Basispunkte.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPercentOfBasePoints();

    /** Column definition for PercentOfBasePoints */
    public static final org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_PercentOfBasePoints = new org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object>(I_C_CommissionSettingsLine.class, "PercentOfBasePoints", null);
    /** Column name PercentOfBasePoints */
    public static final String COLUMNNAME_PercentOfBasePoints = "PercentOfBasePoints";

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
    public static final org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object>(I_C_CommissionSettingsLine.class, "SeqNo", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_CommissionSettingsLine, Object>(I_C_CommissionSettingsLine.class, "Updated", null);
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
