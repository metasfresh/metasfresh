package de.metas.handlingunits.model;


/** Generated Interface for M_InventoryLine_HU
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_InventoryLine_HU 
{

    /** TableName=M_InventoryLine_HU */
    public static final String Table_Name = "M_InventoryLine_HU";

    /** AD_Table_ID=541345 */
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
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_AD_Client>(I_M_InventoryLine_HU.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_AD_Org>(I_M_InventoryLine_HU.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object>(I_M_InventoryLine_HU.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_AD_User>(I_M_InventoryLine_HU.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_C_UOM>(I_M_InventoryLine_HU.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object>(I_M_InventoryLine_HU.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_HU();

	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, de.metas.handlingunits.model.I_M_HU>(I_M_InventoryLine_HU.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set M_InventoryLine_HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_InventoryLine_HU_ID (int M_InventoryLine_HU_ID);

	/**
	 * Get M_InventoryLine_HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_InventoryLine_HU_ID();

    /** Column definition for M_InventoryLine_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_M_InventoryLine_HU_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object>(I_M_InventoryLine_HU.class, "M_InventoryLine_HU_ID", null);
    /** Column name M_InventoryLine_HU_ID */
    public static final String COLUMNNAME_M_InventoryLine_HU_ID = "M_InventoryLine_HU_ID";

	/**
	 * Set Inventur-Position.
	 * Eindeutige Position in einem Inventurdokument
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Inventur-Position.
	 * Eindeutige Position in einem Inventurdokument
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_InventoryLine_ID();

	public org.compiere.model.I_M_InventoryLine getM_InventoryLine();

	public void setM_InventoryLine(org.compiere.model.I_M_InventoryLine M_InventoryLine);

    /** Column definition for M_InventoryLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_M_InventoryLine> COLUMN_M_InventoryLine_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_M_InventoryLine>(I_M_InventoryLine_HU.class, "M_InventoryLine_ID", org.compiere.model.I_M_InventoryLine.class);
    /** Column name M_InventoryLine_ID */
    public static final String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	/**
	 * Set Buchmenge.
	 * Buchmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyBook (java.math.BigDecimal QtyBook);

	/**
	 * Get Buchmenge.
	 * Buchmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyBook();

    /** Column definition for QtyBook */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_QtyBook = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object>(I_M_InventoryLine_HU.class, "QtyBook", null);
    /** Column name QtyBook */
    public static final String COLUMNNAME_QtyBook = "QtyBook";

	/**
	 * Set Zählmenge.
	 * Gezählte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyCount (java.math.BigDecimal QtyCount);

	/**
	 * Get Zählmenge.
	 * Gezählte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCount();

    /** Column definition for QtyCount */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_QtyCount = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object>(I_M_InventoryLine_HU.class, "QtyCount", null);
    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

	/**
	 * Set Internal Use Qty.
	 * Internal Use Quantity removed from Inventory
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyInternalUse (java.math.BigDecimal QtyInternalUse);

	/**
	 * Get Internal Use Qty.
	 * Internal Use Quantity removed from Inventory
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInternalUse();

    /** Column definition for QtyInternalUse */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_QtyInternalUse = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object>(I_M_InventoryLine_HU.class, "QtyInternalUse", null);
    /** Column name QtyInternalUse */
    public static final String COLUMNNAME_QtyInternalUse = "QtyInternalUse";

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
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, Object>(I_M_InventoryLine_HU.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_AD_User>(I_M_InventoryLine_HU.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
