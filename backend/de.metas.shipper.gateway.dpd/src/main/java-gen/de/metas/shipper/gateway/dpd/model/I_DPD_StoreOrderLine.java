package de.metas.shipper.gateway.dpd.model;


/** Generated Interface for DPD_StoreOrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DPD_StoreOrderLine 
{

    /** TableName=DPD_StoreOrderLine */
    public static final String Table_Name = "DPD_StoreOrderLine";

    /** AD_Table_ID=541437 */
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
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object>(I_DPD_StoreOrderLine.class, "Created", null);
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
	 * Set DPD StoreOrder.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDPD_StoreOrder_ID (int DPD_StoreOrder_ID);

	/**
	 * Get DPD StoreOrder.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDPD_StoreOrder_ID();

	public de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder getDPD_StoreOrder();

	public void setDPD_StoreOrder(de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder DPD_StoreOrder);

    /** Column definition for DPD_StoreOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder> COLUMN_DPD_StoreOrder_ID = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder>(I_DPD_StoreOrderLine.class, "DPD_StoreOrder_ID", de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder.class);
    /** Column name DPD_StoreOrder_ID */
    public static final String COLUMNNAME_DPD_StoreOrder_ID = "DPD_StoreOrder_ID";

	/**
	 * Set DPD Store Order Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDPD_StoreOrderLine_ID (int DPD_StoreOrderLine_ID);

	/**
	 * Get DPD Store Order Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDPD_StoreOrderLine_ID();

    /** Column definition for DPD_StoreOrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object> COLUMN_DPD_StoreOrderLine_ID = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object>(I_DPD_StoreOrderLine.class, "DPD_StoreOrderLine_ID", null);
    /** Column name DPD_StoreOrderLine_ID */
    public static final String COLUMNNAME_DPD_StoreOrderLine_ID = "DPD_StoreOrderLine_ID";

	/**
	 * Set Height In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHeightInCm (int HeightInCm);

	/**
	 * Get Height In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getHeightInCm();

    /** Column definition for HeightInCm */
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object> COLUMN_HeightInCm = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object>(I_DPD_StoreOrderLine.class, "HeightInCm", null);
    /** Column name HeightInCm */
    public static final String COLUMNNAME_HeightInCm = "HeightInCm";

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
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object>(I_DPD_StoreOrderLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Length In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLengthInCm (int LengthInCm);

	/**
	 * Get Length In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLengthInCm();

    /** Column definition for LengthInCm */
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object> COLUMN_LengthInCm = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object>(I_DPD_StoreOrderLine.class, "LengthInCm", null);
    /** Column name LengthInCm */
    public static final String COLUMNNAME_LengthInCm = "LengthInCm";

	/**
	 * Set Packstück.
	 * Shipment Package
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Package_ID (int M_Package_ID);

	/**
	 * Get Packstück.
	 * Shipment Package
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Package_ID();

	public org.compiere.model.I_M_Package getM_Package();

	public void setM_Package(org.compiere.model.I_M_Package M_Package);

    /** Column definition for M_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, org.compiere.model.I_M_Package> COLUMN_M_Package_ID = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, org.compiere.model.I_M_Package>(I_DPD_StoreOrderLine.class, "M_Package_ID", org.compiere.model.I_M_Package.class);
    /** Column name M_Package_ID */
    public static final String COLUMNNAME_M_Package_ID = "M_Package_ID";

	/**
	 * Set Package Content.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackageContent (java.lang.String PackageContent);

	/**
	 * Get Package Content.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPackageContent();

    /** Column definition for PackageContent */
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object> COLUMN_PackageContent = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object>(I_DPD_StoreOrderLine.class, "PackageContent", null);
    /** Column name PackageContent */
    public static final String COLUMNNAME_PackageContent = "PackageContent";

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
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object>(I_DPD_StoreOrderLine.class, "Updated", null);
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

	/**
	 * Set Weight In Kg.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWeightInKg (int WeightInKg);

	/**
	 * Get Weight In Kg.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWeightInKg();

    /** Column definition for WeightInKg */
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object> COLUMN_WeightInKg = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object>(I_DPD_StoreOrderLine.class, "WeightInKg", null);
    /** Column name WeightInKg */
    public static final String COLUMNNAME_WeightInKg = "WeightInKg";

	/**
	 * Set Width In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWidthInCm (int WidthInCm);

	/**
	 * Get Width In Cm.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWidthInCm();

    /** Column definition for WidthInCm */
    public static final org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object> COLUMN_WidthInCm = new org.adempiere.model.ModelColumn<I_DPD_StoreOrderLine, Object>(I_DPD_StoreOrderLine.class, "WidthInCm", null);
    /** Column name WidthInCm */
    public static final String COLUMNNAME_WidthInCm = "WidthInCm";
}
