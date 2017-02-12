package de.metas.procurement.base.model;


/** Generated Interface for PMM_Balance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PMM_Balance 
{

    /** TableName=PMM_Balance */
    public static final String Table_Name = "PMM_Balance";

    /** AD_Table_ID=540755 */
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
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_AD_Client>(I_PMM_Balance.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_AD_Org>(I_PMM_Balance.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_C_BPartner>(I_PMM_Balance.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Abrechnungssatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_DataEntry_ID (int C_Flatrate_DataEntry_ID);

	/**
	 * Get Abrechnungssatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_DataEntry_ID();

	public de.metas.flatrate.model.I_C_Flatrate_DataEntry getC_Flatrate_DataEntry();

	public void setC_Flatrate_DataEntry(de.metas.flatrate.model.I_C_Flatrate_DataEntry C_Flatrate_DataEntry);

    /** Column definition for C_Flatrate_DataEntry_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, de.metas.flatrate.model.I_C_Flatrate_DataEntry> COLUMN_C_Flatrate_DataEntry_ID = new org.adempiere.model.ModelColumn<I_PMM_Balance, de.metas.flatrate.model.I_C_Flatrate_DataEntry>(I_PMM_Balance.class, "C_Flatrate_DataEntry_ID", de.metas.flatrate.model.I_C_Flatrate_DataEntry.class);
    /** Column name C_Flatrate_DataEntry_ID */
    public static final String COLUMNNAME_C_Flatrate_DataEntry_ID = "C_Flatrate_DataEntry_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_AD_User>(I_PMM_Balance.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_M_AttributeSetInstance>(I_PMM_Balance.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_Product_ID();

	public de.metas.handlingunits.model.I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();

	public void setM_HU_PI_Item_Product(de.metas.handlingunits.model.I_M_HU_PI_Item_Product M_HU_PI_Item_Product);

    /** Column definition for M_HU_PI_Item_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, de.metas.handlingunits.model.I_M_HU_PI_Item_Product> COLUMN_M_HU_PI_Item_Product_ID = new org.adempiere.model.ModelColumn<I_PMM_Balance, de.metas.handlingunits.model.I_M_HU_PI_Item_Product>(I_PMM_Balance.class, "M_HU_PI_Item_Product_ID", de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class);
    /** Column name M_HU_PI_Item_Product_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_M_Product>(I_PMM_Balance.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Monatserster.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMonthDate (java.sql.Timestamp MonthDate);

	/**
	 * Get Monatserster.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMonthDate();

    /** Column definition for MonthDate */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_MonthDate = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "MonthDate", null);
    /** Column name MonthDate */
    public static final String COLUMNNAME_MonthDate = "MonthDate";

	/**
	 * Set PMM balance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPMM_Balance_ID (int PMM_Balance_ID);

	/**
	 * Get PMM balance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPMM_Balance_ID();

    /** Column definition for PMM_Balance_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_PMM_Balance_ID = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "PMM_Balance_ID", null);
    /** Column name PMM_Balance_ID */
    public static final String COLUMNNAME_PMM_Balance_ID = "PMM_Balance_ID";

	/**
	 * Set Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Bestellte Menge.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellte Menge.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Bestellte Menge (TU).
	 * Bestellte Menge (TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU);

	/**
	 * Get Bestellte Menge (TU).
	 * Bestellte Menge (TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered_TU();

    /** Column definition for QtyOrdered_TU */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_QtyOrdered_TU = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "QtyOrdered_TU", null);
    /** Column name QtyOrdered_TU */
    public static final String COLUMNNAME_QtyOrdered_TU = "QtyOrdered_TU";

	/**
	 * Set Zusagbar.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyPromised (java.math.BigDecimal QtyPromised);

	/**
	 * Get Zusagbar.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPromised();

    /** Column definition for QtyPromised */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_QtyPromised = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "QtyPromised", null);
    /** Column name QtyPromised */
    public static final String COLUMNNAME_QtyPromised = "QtyPromised";

	/**
	 * Set Zusagbar (TU).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyPromised_TU (java.math.BigDecimal QtyPromised_TU);

	/**
	 * Get Zusagbar (TU).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPromised_TU();

    /** Column definition for QtyPromised_TU */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_QtyPromised_TU = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "QtyPromised_TU", null);
    /** Column name QtyPromised_TU */
    public static final String COLUMNNAME_QtyPromised_TU = "QtyPromised_TU";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PMM_Balance, org.compiere.model.I_AD_User>(I_PMM_Balance.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Wochenerster.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWeekDate (java.sql.Timestamp WeekDate);

	/**
	 * Get Wochenerster.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getWeekDate();

    /** Column definition for WeekDate */
    public static final org.adempiere.model.ModelColumn<I_PMM_Balance, Object> COLUMN_WeekDate = new org.adempiere.model.ModelColumn<I_PMM_Balance, Object>(I_PMM_Balance.class, "WeekDate", null);
    /** Column name WeekDate */
    public static final String COLUMNNAME_WeekDate = "WeekDate";
}
