package de.metas.handlingunits.model;


/** Generated Interface for M_HU_Assignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_Assignment 
{

    /** TableName=M_HU_Assignment */
    public static final String Table_Name = "M_HU_Assignment";

    /** AD_Table_ID=540569 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_Client>(I_M_HU_Assignment.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_Org>(I_M_HU_Assignment.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_Table>(I_M_HU_Assignment.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object>(I_M_HU_Assignment.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_User>(I_M_HU_Assignment.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object>(I_M_HU_Assignment.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Transfer Packing Materials.
	 * Shall we transfer packing materials along with the HU
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTransferPackingMaterials (boolean IsTransferPackingMaterials);

	/**
	 * Get Transfer Packing Materials.
	 * Shall we transfer packing materials along with the HU
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTransferPackingMaterials();

    /** Column definition for IsTransferPackingMaterials */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object> COLUMN_IsTransferPackingMaterials = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object>(I_M_HU_Assignment.class, "IsTransferPackingMaterials", null);
    /** Column name IsTransferPackingMaterials */
    public static final String COLUMNNAME_IsTransferPackingMaterials = "IsTransferPackingMaterials";

	/**
	 * Set M_HU_Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Assignment_ID (int M_HU_Assignment_ID);

	/**
	 * Get M_HU_Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Assignment_ID();

    /** Column definition for M_HU_Assignment_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object> COLUMN_M_HU_Assignment_ID = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object>(I_M_HU_Assignment.class, "M_HU_Assignment_ID", null);
    /** Column name M_HU_Assignment_ID */
    public static final String COLUMNNAME_M_HU_Assignment_ID = "M_HU_Assignment_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Assignment.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Unit (LU).
	 * Handling Unit (Loading Unit)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_LU_HU_ID (int M_LU_HU_ID);

	/**
	 * Get Handling Unit (LU).
	 * Handling Unit (Loading Unit)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_LU_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_LU_HU();

	public void setM_LU_HU(de.metas.handlingunits.model.I_M_HU M_LU_HU);

    /** Column definition for M_LU_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU> COLUMN_M_LU_HU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Assignment.class, "M_LU_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_LU_HU_ID */
    public static final String COLUMNNAME_M_LU_HU_ID = "M_LU_HU_ID";

	/**
	 * Set Handling Unit (TU).
	 * Handling Unit of type Tranding Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_TU_HU_ID (int M_TU_HU_ID);

	/**
	 * Get Handling Unit (TU).
	 * Handling Unit of type Tranding Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_TU_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_TU_HU();

	public void setM_TU_HU(de.metas.handlingunits.model.I_M_HU M_TU_HU);

    /** Column definition for M_TU_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU> COLUMN_M_TU_HU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Assignment.class, "M_TU_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_TU_HU_ID */
    public static final String COLUMNNAME_M_TU_HU_ID = "M_TU_HU_ID";

	/**
	 * Set Produkte.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setProducts (java.lang.String Products);

	/**
	 * Get Produkte.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getProducts();

    /** Column definition for Products */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object> COLUMN_Products = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object>(I_M_HU_Assignment.class, "Products", null);
    /** Column name Products */
    public static final String COLUMNNAME_Products = "Products";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object>(I_M_HU_Assignment.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object>(I_M_HU_Assignment.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, Object>(I_M_HU_Assignment.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, org.compiere.model.I_AD_User>(I_M_HU_Assignment.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Virtual Handling Unit (VHU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVHU_ID (int VHU_ID);

	/**
	 * Get Virtual Handling Unit (VHU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getVHU_ID();

	public de.metas.handlingunits.model.I_M_HU getVHU();

	public void setVHU(de.metas.handlingunits.model.I_M_HU VHU);

    /** Column definition for VHU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU> COLUMN_VHU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Assignment.class, "VHU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name VHU_ID */
    public static final String COLUMNNAME_VHU_ID = "VHU_ID";
}
