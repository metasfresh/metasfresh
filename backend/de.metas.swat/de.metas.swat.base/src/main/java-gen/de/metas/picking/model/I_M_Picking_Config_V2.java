package de.metas.picking.model;


/** Generated Interface for M_Picking_Config_V2
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Picking_Config_V2 
{

    /** TableName=M_Picking_Config_V2 */
    public static final String Table_Name = "M_Picking_Config_V2";

    /** AD_Table_ID=541418 */
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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object>(I_M_Picking_Config_V2.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object>(I_M_Picking_Config_V2.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Attribute berücksichtigen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsConsiderAttributes (boolean IsConsiderAttributes);

	/**
	 * Get Attribute berücksichtigen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isConsiderAttributes();

    /** Column definition for IsConsiderAttributes */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_IsConsiderAttributes = new org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object>(I_M_Picking_Config_V2.class, "IsConsiderAttributes", null);
    /** Column name IsConsiderAttributes */
    public static final String COLUMNNAME_IsConsiderAttributes = "IsConsiderAttributes";

	/**
	 * Set Kommissionierprüfung erforderlich.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPickingReviewRequired (boolean IsPickingReviewRequired);

	/**
	 * Get Kommissionierprüfung erforderlich.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPickingReviewRequired();

    /** Column definition for IsPickingReviewRequired */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_IsPickingReviewRequired = new org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object>(I_M_Picking_Config_V2.class, "IsPickingReviewRequired", null);
    /** Column name IsPickingReviewRequired */
    public static final String COLUMNNAME_IsPickingReviewRequired = "IsPickingReviewRequired";

	/**
	 * Set Kommissionierkonfiguration (V2).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Picking_Config_V2_ID (int M_Picking_Config_V2_ID);

	/**
	 * Get Kommissionierkonfiguration (V2).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Picking_Config_V2_ID();

    /** Column definition for M_Picking_Config_V2_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_M_Picking_Config_V2_ID = new org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object>(I_M_Picking_Config_V2.class, "M_Picking_Config_V2_ID", null);
    /** Column name M_Picking_Config_V2_ID */
    public static final String COLUMNNAME_M_Picking_Config_V2_ID = "M_Picking_Config_V2_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Picking_Config_V2, Object>(I_M_Picking_Config_V2.class, "Updated", null);
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
