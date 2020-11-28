package de.metas.handlingunits.model;


/** Generated Interface for M_HU_PI_Item
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_PI_Item 
{

    /** TableName=M_HU_PI_Item */
    public static final String Table_Name = "M_HU_PI_Item";

    /** AD_Table_ID=540509 */
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
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object>(I_M_HU_PI_Item.class, "Created", null);
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
	 * Set Unter-Packvorschrift.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIncluded_HU_PI_ID (int Included_HU_PI_ID);

	/**
	 * Get Unter-Packvorschrift.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getIncluded_HU_PI_ID();

	public de.metas.handlingunits.model.I_M_HU_PI getIncluded_HU_PI();

	public void setIncluded_HU_PI(de.metas.handlingunits.model.I_M_HU_PI Included_HU_PI);

    /** Column definition for Included_HU_PI_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_Included_HU_PI_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, de.metas.handlingunits.model.I_M_HU_PI>(I_M_HU_PI_Item.class, "Included_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
    /** Column name Included_HU_PI_ID */
    public static final String COLUMNNAME_Included_HU_PI_ID = "Included_HU_PI_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object>(I_M_HU_PI_Item.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set AllowDirectlyOnPM.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsAllowDirectlyOnPM (boolean IsAllowDirectlyOnPM);

	/**
	 * Get AllowDirectlyOnPM.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isAllowDirectlyOnPM();

    /** Column definition for IsAllowDirectlyOnPM */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object> COLUMN_IsAllowDirectlyOnPM = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object>(I_M_HU_PI_Item.class, "IsAllowDirectlyOnPM", null);
    /** Column name IsAllowDirectlyOnPM */
    public static final String COLUMNNAME_IsAllowDirectlyOnPM = "IsAllowDirectlyOnPM";

	/**
	 * Set Positionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setItemType (java.lang.String ItemType);

	/**
	 * Get Positionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getItemType();

    /** Column definition for ItemType */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object> COLUMN_ItemType = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object>(I_M_HU_PI_Item.class, "ItemType", null);
    /** Column name ItemType */
    public static final String COLUMNNAME_ItemType = "ItemType";

	/**
	 * Set Packmittel.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackingMaterial_ID (int M_HU_PackingMaterial_ID);

	/**
	 * Get Packmittel.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackingMaterial_ID();

	public de.metas.handlingunits.model.I_M_HU_PackingMaterial getM_HU_PackingMaterial();

	public void setM_HU_PackingMaterial(de.metas.handlingunits.model.I_M_HU_PackingMaterial M_HU_PackingMaterial);

    /** Column definition for M_HU_PackingMaterial_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, de.metas.handlingunits.model.I_M_HU_PackingMaterial> COLUMN_M_HU_PackingMaterial_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, de.metas.handlingunits.model.I_M_HU_PackingMaterial>(I_M_HU_PI_Item.class, "M_HU_PackingMaterial_ID", de.metas.handlingunits.model.I_M_HU_PackingMaterial.class);
    /** Column name M_HU_PackingMaterial_ID */
    public static final String COLUMNNAME_M_HU_PackingMaterial_ID = "M_HU_PackingMaterial_ID";

	/**
	 * Set Packvorschrift Position.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_ID (int M_HU_PI_Item_ID);

	/**
	 * Get Packvorschrift Position.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_ID();

    /** Column definition for M_HU_PI_Item_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object> COLUMN_M_HU_PI_Item_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object>(I_M_HU_PI_Item.class, "M_HU_PI_Item_ID", null);
    /** Column name M_HU_PI_Item_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_ID = "M_HU_PI_Item_ID";

	/**
	 * Set Packvorschrift Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID);

	/**
	 * Get Packvorschrift Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Version_ID();

	public de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version();

	public void setM_HU_PI_Version(de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version);

    /** Column definition for M_HU_PI_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, de.metas.handlingunits.model.I_M_HU_PI_Version> COLUMN_M_HU_PI_Version_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, de.metas.handlingunits.model.I_M_HU_PI_Version>(I_M_HU_PI_Item.class, "M_HU_PI_Version_ID", de.metas.handlingunits.model.I_M_HU_PI_Version.class);
    /** Column name M_HU_PI_Version_ID */
    public static final String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object>(I_M_HU_PI_Item.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item, Object>(I_M_HU_PI_Item.class, "Updated", null);
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
