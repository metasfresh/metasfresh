package de.metas.handlingunits.model;


/** Generated Interface for M_HU_Item_Storage_Snapshot
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_Item_Storage_Snapshot 
{

    /** TableName=M_HU_Item_Storage_Snapshot */
    public static final String Table_Name = "M_HU_Item_Storage_Snapshot";

    /** AD_Table_ID=540673 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_AD_Client>(I_M_HU_Item_Storage_Snapshot.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_AD_Org>(I_M_HU_Item_Storage_Snapshot.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_C_UOM>(I_M_HU_Item_Storage_Snapshot.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object>(I_M_HU_Item_Storage_Snapshot.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_AD_User>(I_M_HU_Item_Storage_Snapshot.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object>(I_M_HU_Item_Storage_Snapshot.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Item_ID (int M_HU_Item_ID);

	/**
	 * Get Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Item_ID();

	public de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item();

	public void setM_HU_Item(de.metas.handlingunits.model.I_M_HU_Item M_HU_Item);

    /** Column definition for M_HU_Item_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, de.metas.handlingunits.model.I_M_HU_Item> COLUMN_M_HU_Item_ID = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, de.metas.handlingunits.model.I_M_HU_Item>(I_M_HU_Item_Storage_Snapshot.class, "M_HU_Item_ID", de.metas.handlingunits.model.I_M_HU_Item.class);
    /** Column name M_HU_Item_ID */
    public static final String COLUMNNAME_M_HU_Item_ID = "M_HU_Item_ID";

	/**
	 * Set Handling Units Item Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Item_Storage_ID (int M_HU_Item_Storage_ID);

	/**
	 * Get Handling Units Item Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Item_Storage_ID();

	public de.metas.handlingunits.model.I_M_HU_Item_Storage getM_HU_Item_Storage();

	public void setM_HU_Item_Storage(de.metas.handlingunits.model.I_M_HU_Item_Storage M_HU_Item_Storage);

    /** Column definition for M_HU_Item_Storage_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, de.metas.handlingunits.model.I_M_HU_Item_Storage> COLUMN_M_HU_Item_Storage_ID = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, de.metas.handlingunits.model.I_M_HU_Item_Storage>(I_M_HU_Item_Storage_Snapshot.class, "M_HU_Item_Storage_ID", de.metas.handlingunits.model.I_M_HU_Item_Storage.class);
    /** Column name M_HU_Item_Storage_ID */
    public static final String COLUMNNAME_M_HU_Item_Storage_ID = "M_HU_Item_Storage_ID";

	/**
	 * Set Handling Units Item Storage Snapshot.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Item_Storage_Snapshot_ID (int M_HU_Item_Storage_Snapshot_ID);

	/**
	 * Get Handling Units Item Storage Snapshot.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Item_Storage_Snapshot_ID();

    /** Column definition for M_HU_Item_Storage_Snapshot_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_M_HU_Item_Storage_Snapshot_ID = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object>(I_M_HU_Item_Storage_Snapshot.class, "M_HU_Item_Storage_Snapshot_ID", null);
    /** Column name M_HU_Item_Storage_Snapshot_ID */
    public static final String COLUMNNAME_M_HU_Item_Storage_Snapshot_ID = "M_HU_Item_Storage_Snapshot_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_M_Product>(I_M_HU_Item_Storage_Snapshot.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object>(I_M_HU_Item_Storage_Snapshot.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Snapshot UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSnapshot_UUID (java.lang.String Snapshot_UUID);

	/**
	 * Get Snapshot UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSnapshot_UUID();

    /** Column definition for Snapshot_UUID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_Snapshot_UUID = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object>(I_M_HU_Item_Storage_Snapshot.class, "Snapshot_UUID", null);
    /** Column name Snapshot_UUID */
    public static final String COLUMNNAME_Snapshot_UUID = "Snapshot_UUID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, Object>(I_M_HU_Item_Storage_Snapshot.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_AD_User>(I_M_HU_Item_Storage_Snapshot.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
