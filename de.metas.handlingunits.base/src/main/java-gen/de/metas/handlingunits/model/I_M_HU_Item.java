package de.metas.handlingunits.model;


/** Generated Interface for M_HU_Item
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_Item 
{

    /** TableName=M_HU_Item */
    public static final String Table_Name = "M_HU_Item";

    /** AD_Table_ID=540506 */
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

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Positionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setItemType (java.lang.String ItemType);

	/**
	 * Get Positionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getItemType();

    /** Column definition for ItemType */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, Object> COLUMN_ItemType = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "ItemType", null);
    /** Column name ItemType */
    public static final String COLUMNNAME_ItemType = "ItemType";

	/**
	 * Set Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_HU();

	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Item_ID (int M_HU_Item_ID);

	/**
	 * Get Handling Units Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Item_ID();

    /** Column definition for M_HU_Item_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, Object> COLUMN_M_HU_Item_ID = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "M_HU_Item_ID", null);
    /** Column name M_HU_Item_ID */
    public static final String COLUMNNAME_M_HU_Item_ID = "M_HU_Item_ID";

	/**
	 * Set Packmittel.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackingMaterial_ID (int M_HU_PackingMaterial_ID);

	/**
	 * Get Packmittel.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackingMaterial_ID();

	public de.metas.handlingunits.model.I_M_HU_PackingMaterial getM_HU_PackingMaterial();

	public void setM_HU_PackingMaterial(de.metas.handlingunits.model.I_M_HU_PackingMaterial M_HU_PackingMaterial);

    /** Column definition for M_HU_PackingMaterial_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, de.metas.handlingunits.model.I_M_HU_PackingMaterial> COLUMN_M_HU_PackingMaterial_ID = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "M_HU_PackingMaterial_ID", de.metas.handlingunits.model.I_M_HU_PackingMaterial.class);
    /** Column name M_HU_PackingMaterial_ID */
    public static final String COLUMNNAME_M_HU_PackingMaterial_ID = "M_HU_PackingMaterial_ID";

	/**
	 * Set Packvorschrift Position.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_ID (int M_HU_PI_Item_ID);

	/**
	 * Get Packvorschrift Position.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_ID();

	//public de.metas.handlingunits.model.I_M_HU_PI_Item getM_HU_PI_Item();

	public void setM_HU_PI_Item(de.metas.handlingunits.model.I_M_HU_PI_Item M_HU_PI_Item);

    /** Column definition for M_HU_PI_Item_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, de.metas.handlingunits.model.I_M_HU_PI_Item> COLUMN_M_HU_PI_Item_ID = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "M_HU_PI_Item_ID", de.metas.handlingunits.model.I_M_HU_PI_Item.class);
    /** Column name M_HU_PI_Item_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_ID = "M_HU_PI_Item_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "Qty", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_M_HU_Item.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
