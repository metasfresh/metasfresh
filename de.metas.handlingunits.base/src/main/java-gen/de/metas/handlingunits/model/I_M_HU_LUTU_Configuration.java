package de.metas.handlingunits.model;


/** Generated Interface for M_HU_LUTU_Configuration
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_LUTU_Configuration 
{

    /** TableName=M_HU_LUTU_Configuration */
    public static final String Table_Name = "M_HU_LUTU_Configuration";

    /** AD_Table_ID=540605 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_AD_Client>(I_M_HU_LUTU_Configuration.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_AD_Org>(I_M_HU_LUTU_Configuration.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_C_BPartner>(I_M_HU_LUTU_Configuration.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_C_BPartner_Location>(I_M_HU_LUTU_Configuration.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

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

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_C_UOM>(I_M_HU_LUTU_Configuration.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_AD_User>(I_M_HU_LUTU_Configuration.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_HUStatus = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "HUStatus", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Unendliche CU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInfiniteQtyCU (boolean IsInfiniteQtyCU);

	/**
	 * Get Unendliche CU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInfiniteQtyCU();

    /** Column definition for IsInfiniteQtyCU */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_IsInfiniteQtyCU = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "IsInfiniteQtyCU", null);
    /** Column name IsInfiniteQtyCU */
    public static final String COLUMNNAME_IsInfiniteQtyCU = "IsInfiniteQtyCU";

	/**
	 * Set Unendliche LU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInfiniteQtyLU (boolean IsInfiniteQtyLU);

	/**
	 * Get Unendliche LU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInfiniteQtyLU();

    /** Column definition for IsInfiniteQtyLU */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_IsInfiniteQtyLU = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "IsInfiniteQtyLU", null);
    /** Column name IsInfiniteQtyLU */
    public static final String COLUMNNAME_IsInfiniteQtyLU = "IsInfiniteQtyLU";

	/**
	 * Set Unendliche TU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInfiniteQtyTU (boolean IsInfiniteQtyTU);

	/**
	 * Get Unendliche TU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInfiniteQtyTU();

    /** Column definition for IsInfiniteQtyTU */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_IsInfiniteQtyTU = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "IsInfiniteQtyTU", null);
    /** Column name IsInfiniteQtyTU */
    public static final String COLUMNNAME_IsInfiniteQtyTU = "IsInfiniteQtyTU";

	/**
	 * Set Gebindekonfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_LUTU_Configuration_ID (int M_HU_LUTU_Configuration_ID);

	/**
	 * Get Gebindekonfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_LUTU_Configuration_ID();

    /** Column definition for M_HU_LUTU_Configuration_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_M_HU_LUTU_Configuration_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "M_HU_LUTU_Configuration_ID", null);
    /** Column name M_HU_LUTU_Configuration_ID */
    public static final String COLUMNNAME_M_HU_LUTU_Configuration_ID = "M_HU_LUTU_Configuration_ID";

	/**
	 * Set Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_Product_ID();

	public de.metas.handlingunits.model.I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();

	public void setM_HU_PI_Item_Product(de.metas.handlingunits.model.I_M_HU_PI_Item_Product M_HU_PI_Item_Product);

    /** Column definition for M_HU_PI_Item_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI_Item_Product> COLUMN_M_HU_PI_Item_Product_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI_Item_Product>(I_M_HU_LUTU_Configuration.class, "M_HU_PI_Item_Product_ID", de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class);
    /** Column name M_HU_PI_Item_Product_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_M_Locator>(I_M_HU_LUTU_Configuration.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Packvorschrift (LU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_LU_HU_PI_ID (int M_LU_HU_PI_ID);

	/**
	 * Get Packvorschrift (LU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_LU_HU_PI_ID();

	public de.metas.handlingunits.model.I_M_HU_PI getM_LU_HU_PI();

	public void setM_LU_HU_PI(de.metas.handlingunits.model.I_M_HU_PI M_LU_HU_PI);

    /** Column definition for M_LU_HU_PI_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_M_LU_HU_PI_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI>(I_M_HU_LUTU_Configuration.class, "M_LU_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
    /** Column name M_LU_HU_PI_ID */
    public static final String COLUMNNAME_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";

	/**
	 * Set Packvorschrift Position (LU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_LU_HU_PI_Item_ID (int M_LU_HU_PI_Item_ID);

	/**
	 * Get Packvorschrift Position (LU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_LU_HU_PI_Item_ID();

	public de.metas.handlingunits.model.I_M_HU_PI_Item getM_LU_HU_PI_Item();

	public void setM_LU_HU_PI_Item(de.metas.handlingunits.model.I_M_HU_PI_Item M_LU_HU_PI_Item);

    /** Column definition for M_LU_HU_PI_Item_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI_Item> COLUMN_M_LU_HU_PI_Item_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI_Item>(I_M_HU_LUTU_Configuration.class, "M_LU_HU_PI_Item_ID", de.metas.handlingunits.model.I_M_HU_PI_Item.class);
    /** Column name M_LU_HU_PI_Item_ID */
    public static final String COLUMNNAME_M_LU_HU_PI_Item_ID = "M_LU_HU_PI_Item_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_M_Product>(I_M_HU_LUTU_Configuration.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Packvorschrift (TU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_TU_HU_PI_ID (int M_TU_HU_PI_ID);

	/**
	 * Get Packvorschrift (TU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_TU_HU_PI_ID();

	public de.metas.handlingunits.model.I_M_HU_PI getM_TU_HU_PI();

	public void setM_TU_HU_PI(de.metas.handlingunits.model.I_M_HU_PI M_TU_HU_PI);

    /** Column definition for M_TU_HU_PI_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_M_TU_HU_PI_ID = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI>(I_M_HU_LUTU_Configuration.class, "M_TU_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
    /** Column name M_TU_HU_PI_ID */
    public static final String COLUMNNAME_M_TU_HU_PI_ID = "M_TU_HU_PI_ID";

	/**
	 * Set Menge CU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyCU (java.math.BigDecimal QtyCU);

	/**
	 * Get Menge CU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCU();

    /** Column definition for QtyCU */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_QtyCU = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "QtyCU", null);
    /** Column name QtyCU */
    public static final String COLUMNNAME_QtyCU = "QtyCU";

	/**
	 * Set Menge LU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyLU (java.math.BigDecimal QtyLU);

	/**
	 * Get Menge LU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyLU();

    /** Column definition for QtyLU */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_QtyLU = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "QtyLU", null);
    /** Column name QtyLU */
    public static final String COLUMNNAME_QtyLU = "QtyLU";

	/**
	 * Set Menge TU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyTU (java.math.BigDecimal QtyTU);

	/**
	 * Get Menge TU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyTU();

    /** Column definition for QtyTU */
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_QtyTU = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "QtyTU", null);
    /** Column name QtyTU */
    public static final String COLUMNNAME_QtyTU = "QtyTU";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, Object>(I_M_HU_LUTU_Configuration.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_LUTU_Configuration, org.compiere.model.I_AD_User>(I_M_HU_LUTU_Configuration.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
