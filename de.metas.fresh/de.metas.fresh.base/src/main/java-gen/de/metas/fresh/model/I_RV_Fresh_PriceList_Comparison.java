package de.metas.fresh.model;


/** Generated Interface for RV_Fresh_PriceList_Comparison
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_RV_Fresh_PriceList_Comparison 
{

    /** TableName=RV_Fresh_PriceList_Comparison */
    public static final String Table_Name = "RV_Fresh_PriceList_Comparison";

    /** AD_Table_ID=540563 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_AD_Client>(I_RV_Fresh_PriceList_Comparison.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_AD_Org>(I_RV_Fresh_PriceList_Comparison.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Vergleichs Preislistenversion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAlt_Pricelist_Version_ID (int Alt_Pricelist_Version_ID);

	/**
	 * Get Vergleichs Preislistenversion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAlt_Pricelist_Version_ID();

	public org.compiere.model.I_M_PriceList_Version getAlt_Pricelist_Version();

	public void setAlt_Pricelist_Version(org.compiere.model.I_M_PriceList_Version Alt_Pricelist_Version);

    /** Column definition for Alt_Pricelist_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_M_PriceList_Version> COLUMN_Alt_Pricelist_Version_ID = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_M_PriceList_Version>(I_RV_Fresh_PriceList_Comparison.class, "Alt_Pricelist_Version_ID", org.compiere.model.I_M_PriceList_Version.class);
    /** Column name Alt_Pricelist_Version_ID */
    public static final String COLUMNNAME_Alt_Pricelist_Version_ID = "Alt_Pricelist_Version_ID";

	/**
	 * Set Vgl. Standardpreis.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAltPriceStd (java.math.BigDecimal AltPriceStd);

	/**
	 * Get Vgl. Standardpreis.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAltPriceStd();

    /** Column definition for AltPriceStd */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_AltPriceStd = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "AltPriceStd", null);
    /** Column name AltPriceStd */
    public static final String COLUMNNAME_AltPriceStd = "AltPriceStd";

	/**
	 * Set attributes.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setattributes (java.lang.String attributes);

	/**
	 * Get attributes.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getattributes();

    /** Column definition for attributes */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_attributes = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "attributes", null);
    /** Column name attributes */
    public static final String COLUMNNAME_attributes = "attributes";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_C_BPartner>(I_RV_Fresh_PriceList_Comparison.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_AD_User>(I_RV_Fresh_PriceList_Comparison.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Saison Fix Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSeasonFixedPrice (boolean IsSeasonFixedPrice);

	/**
	 * Get Saison Fix Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSeasonFixedPrice();

    /** Column definition for IsSeasonFixedPrice */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_IsSeasonFixedPrice = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "IsSeasonFixedPrice", null);
    /** Column name IsSeasonFixedPrice */
    public static final String COLUMNNAME_IsSeasonFixedPrice = "IsSeasonFixedPrice";

	/**
	 * Set CU:TU Zuordnung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setItemProductName (java.lang.String ItemProductName);

	/**
	 * Get CU:TU Zuordnung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getItemProductName();

    /** Column definition for ItemProductName */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_ItemProductName = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "ItemProductName", null);
    /** Column name ItemProductName */
    public static final String COLUMNNAME_ItemProductName = "ItemProductName";

	/**
	 * Set Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_Version_ID();

	public org.compiere.model.I_M_PriceList_Version getM_PriceList_Version();

	public void setM_PriceList_Version(org.compiere.model.I_M_PriceList_Version M_PriceList_Version);

    /** Column definition for M_PriceList_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_M_PriceList_Version> COLUMN_M_PriceList_Version_ID = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_M_PriceList_Version>(I_RV_Fresh_PriceList_Comparison.class, "M_PriceList_Version_ID", org.compiere.model.I_M_PriceList_Version.class);
    /** Column name M_PriceList_Version_ID */
    public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/**
	 * Set packingmaterialname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setpackingmaterialname (java.lang.String packingmaterialname);

	/**
	 * Get packingmaterialname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getpackingmaterialname();

    /** Column definition for packingmaterialname */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_packingmaterialname = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "packingmaterialname", null);
    /** Column name packingmaterialname */
    public static final String COLUMNNAME_packingmaterialname = "packingmaterialname";

	/**
	 * Set Standardpreis.
	 * Standardpreis
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceStd (java.math.BigDecimal PriceStd);

	/**
	 * Get Standardpreis.
	 * Standardpreis
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceStd();

    /** Column definition for PriceStd */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_PriceStd = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "PriceStd", null);
    /** Column name PriceStd */
    public static final String COLUMNNAME_PriceStd = "PriceStd";

	/**
	 * Set Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductName (java.lang.String ProductName);

	/**
	 * Get Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductName();

    /** Column definition for ProductName */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_ProductName = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "ProductName", null);
    /** Column name ProductName */
    public static final String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, org.compiere.model.I_AD_User>(I_RV_Fresh_PriceList_Comparison.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_RV_Fresh_PriceList_Comparison, Object>(I_RV_Fresh_PriceList_Comparison.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
