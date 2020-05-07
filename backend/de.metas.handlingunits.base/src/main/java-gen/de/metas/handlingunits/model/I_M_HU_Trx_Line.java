package de.metas.handlingunits.model;


/** Generated Interface for M_HU_Trx_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_Trx_Line 
{

    /** TableName=M_HU_Trx_Line */
    public static final String Table_Name = "M_HU_Trx_Line";

    /** AD_Table_ID=540515 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_Client>(I_M_HU_Trx_Line.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_Org>(I_M_HU_Trx_Line.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_Table>(I_M_HU_Trx_Line.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_C_UOM>(I_M_HU_Trx_Line.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object>(I_M_HU_Trx_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_User>(I_M_HU_Trx_Line.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Vorgangsdatum.
	 * Vorgangsdatum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Vorgangsdatum.
	 * Vorgangsdatum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateTrx();

    /** Column definition for DateTrx */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_DateTrx = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object>(I_M_HU_Trx_Line.class, "DateTrx", null);
    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/**
	 * Set Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHUStatus (java.lang.String HUStatus);

	/**
	 * Get Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHUStatus();

    /** Column definition for HUStatus */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_HUStatus = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object>(I_M_HU_Trx_Line.class, "HUStatus", null);
    /** Column name HUStatus */
    public static final String COLUMNNAME_HUStatus = "HUStatus";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object>(I_M_HU_Trx_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_HU();

	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Trx_Line.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Item_ID (int M_HU_Item_ID);

	/**
	 * Get Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Item_ID();

	public de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item();

	public void setM_HU_Item(de.metas.handlingunits.model.I_M_HU_Item M_HU_Item);

    /** Column definition for M_HU_Item_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Item> COLUMN_M_HU_Item_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Item>(I_M_HU_Trx_Line.class, "M_HU_Item_ID", de.metas.handlingunits.model.I_M_HU_Item.class);
    /** Column name M_HU_Item_ID */
    public static final String COLUMNNAME_M_HU_Item_ID = "M_HU_Item_ID";

	/**
	 * Set HU-Transaktionskopf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Trx_Hdr_ID (int M_HU_Trx_Hdr_ID);

	/**
	 * Get HU-Transaktionskopf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Trx_Hdr_ID();

	public de.metas.handlingunits.model.I_M_HU_Trx_Hdr getM_HU_Trx_Hdr();

	public void setM_HU_Trx_Hdr(de.metas.handlingunits.model.I_M_HU_Trx_Hdr M_HU_Trx_Hdr);

    /** Column definition for M_HU_Trx_Hdr_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Trx_Hdr> COLUMN_M_HU_Trx_Hdr_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Trx_Hdr>(I_M_HU_Trx_Line.class, "M_HU_Trx_Hdr_ID", de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class);
    /** Column name M_HU_Trx_Hdr_ID */
    public static final String COLUMNNAME_M_HU_Trx_Hdr_ID = "M_HU_Trx_Hdr_ID";

	/**
	 * Set HU-Transaktionszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Trx_Line_ID (int M_HU_Trx_Line_ID);

	/**
	 * Get HU-Transaktionszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Trx_Line_ID();

    /** Column definition for M_HU_Trx_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_M_HU_Trx_Line_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object>(I_M_HU_Trx_Line.class, "M_HU_Trx_Line_ID", null);
    /** Column name M_HU_Trx_Line_ID */
    public static final String COLUMNNAME_M_HU_Trx_Line_ID = "M_HU_Trx_Line_ID";

	/**
	 * Set Lagerort.
	 * Lagerort im Lager
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Lagerort im Lager
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_M_Locator>(I_M_HU_Trx_Line.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_M_Product>(I_M_HU_Trx_Line.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Eltern-Transaktionszeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setParent_HU_Trx_Line_ID (int Parent_HU_Trx_Line_ID);

	/**
	 * Get Eltern-Transaktionszeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getParent_HU_Trx_Line_ID();

	public de.metas.handlingunits.model.I_M_HU_Trx_Line getParent_HU_Trx_Line();

	public void setParent_HU_Trx_Line(de.metas.handlingunits.model.I_M_HU_Trx_Line Parent_HU_Trx_Line);

    /** Column definition for Parent_HU_Trx_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Trx_Line> COLUMN_Parent_HU_Trx_Line_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Trx_Line>(I_M_HU_Trx_Line.class, "Parent_HU_Trx_Line_ID", de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
    /** Column name Parent_HU_Trx_Line_ID */
    public static final String COLUMNNAME_Parent_HU_Trx_Line_ID = "Parent_HU_Trx_Line_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object>(I_M_HU_Trx_Line.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object>(I_M_HU_Trx_Line.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object>(I_M_HU_Trx_Line.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Storno-Zeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReversalLine_ID (int ReversalLine_ID);

	/**
	 * Get Storno-Zeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getReversalLine_ID();

	public de.metas.handlingunits.model.I_M_HU_Trx_Line getReversalLine();

	public void setReversalLine(de.metas.handlingunits.model.I_M_HU_Trx_Line ReversalLine);

    /** Column definition for ReversalLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Trx_Line> COLUMN_ReversalLine_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Trx_Line>(I_M_HU_Trx_Line.class, "ReversalLine_ID", de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
    /** Column name ReversalLine_ID */
    public static final String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, Object>(I_M_HU_Trx_Line.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, org.compiere.model.I_AD_User>(I_M_HU_Trx_Line.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Virtual Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVHU_Item_ID (int VHU_Item_ID);

	/**
	 * Get Virtual Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getVHU_Item_ID();

	public de.metas.handlingunits.model.I_M_HU_Item getVHU_Item();

	public void setVHU_Item(de.metas.handlingunits.model.I_M_HU_Item VHU_Item);

    /** Column definition for VHU_Item_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Item> COLUMN_VHU_Item_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Item>(I_M_HU_Trx_Line.class, "VHU_Item_ID", de.metas.handlingunits.model.I_M_HU_Item.class);
    /** Column name VHU_Item_ID */
    public static final String COLUMNNAME_VHU_Item_ID = "VHU_Item_ID";
}
