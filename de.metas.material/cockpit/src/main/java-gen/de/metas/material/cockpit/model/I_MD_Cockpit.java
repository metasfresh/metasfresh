package de.metas.material.cockpit.model;


/** Generated Interface for MD_Cockpit
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Cockpit 
{

    /** TableName=MD_Cockpit */
    public static final String Table_Name = "MD_Cockpit";

    /** AD_Table_ID=540863 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_AD_Client>(I_MD_Cockpit.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_AD_Org>(I_MD_Cockpit.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set AttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAttributesKey (java.lang.String AttributesKey);

	/**
	 * Get AttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributesKey();

    /** Column definition for AttributesKey */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_AttributesKey = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "AttributesKey", null);
    /** Column name AttributesKey */
    public static final String COLUMNNAME_AttributesKey = "AttributesKey";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_AD_User>(I_MD_Cockpit.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateGeneral (java.sql.Timestamp DateGeneral);

	/**
	 * Get Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateGeneral();

    /** Column definition for DateGeneral */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_DateGeneral = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "DateGeneral", null);
    /** Column name DateGeneral */
    public static final String COLUMNNAME_DateGeneral = "DateGeneral";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_M_Product>(I_MD_Cockpit.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Materialcockpit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Cockpit_ID (int MD_Cockpit_ID);

	/**
	 * Get Materialcockpit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Cockpit_ID();

    /** Column definition for MD_Cockpit_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_MD_Cockpit_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "MD_Cockpit_ID", null);
    /** Column name MD_Cockpit_ID */
    public static final String COLUMNNAME_MD_Cockpit_ID = "MD_Cockpit_ID";

	/**
	 * Set Zusage Lieferant.
	 * Vom Lieferanten per Webapplikation zugesagte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPMM_QtyPromised_OnDate (java.math.BigDecimal PMM_QtyPromised_OnDate);

	/**
	 * Get Zusage Lieferant.
	 * Vom Lieferanten per Webapplikation zugesagte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPMM_QtyPromised_OnDate();

    /** Column definition for PMM_QtyPromised_OnDate */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_PMM_QtyPromised_OnDate = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "PMM_QtyPromised_OnDate", null);
    /** Column name PMM_QtyPromised_OnDate */
    public static final String COLUMNNAME_PMM_QtyPromised_OnDate = "PMM_QtyPromised_OnDate";

	/**
	 * Set Produktionsstätte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Plant_ID (int PP_Plant_ID);

	/**
	 * Get Produktionsstätte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Plant_ID();

	public org.compiere.model.I_S_Resource getPP_Plant();

	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant);

    /** Column definition for PP_Plant_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_S_Resource>(I_MD_Cockpit.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
    /** Column name PP_Plant_ID */
    public static final String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_ProductName = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "ProductName", null);
    /** Column name ProductName */
    public static final String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Produktschlüssel.
	 * Schlüssel des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductValue (java.lang.String ProductValue);

	/**
	 * Get Produktschlüssel.
	 * Schlüssel des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductValue();

    /** Column definition for ProductValue */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Zusagbare Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyAvailableToPromise (java.math.BigDecimal QtyAvailableToPromise);

	/**
	 * Get Zusagbare Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyAvailableToPromise();

    /** Column definition for QtyAvailableToPromise */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyAvailableToPromise = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "QtyAvailableToPromise", null);
    /** Column name QtyAvailableToPromise */
    public static final String COLUMNNAME_QtyAvailableToPromise = "QtyAvailableToPromise";

	/**
	 * Set Materialentnahme.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyMaterialentnahme (java.math.BigDecimal QtyMaterialentnahme);

	/**
	 * Get Materialentnahme.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyMaterialentnahme();

    /** Column definition for QtyMaterialentnahme */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyMaterialentnahme = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "QtyMaterialentnahme", null);
    /** Column name QtyMaterialentnahme */
    public static final String COLUMNNAME_QtyMaterialentnahme = "QtyMaterialentnahme";

	/**
	 * Set Zählmenge.
	 * Für den jeweiligen Tag gezählte/geschätze Bestandsmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOnHandCount (java.math.BigDecimal QtyOnHandCount);

	/**
	 * Get Zählmenge.
	 * Für den jeweiligen Tag gezählte/geschätze Bestandsmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOnHandCount();

    /** Column definition for QtyOnHandCount */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyOnHandCount = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "QtyOnHandCount", null);
    /** Column name QtyOnHandCount */
    public static final String COLUMNNAME_QtyOnHandCount = "QtyOnHandCount";

	/**
	 * Set Schätzbestand.
	 * Kombination aus der Zählmenge des jeweiligen Tages mit aktuellen Warenein- und Ausgängen sowie Materialentnahmen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOnHandEstimate (java.math.BigDecimal QtyOnHandEstimate);

	/**
	 * Get Schätzbestand.
	 * Kombination aus der Zählmenge des jeweiligen Tages mit aktuellen Warenein- und Ausgängen sowie Materialentnahmen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOnHandEstimate();

    /** Column definition for QtyOnHandEstimate */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyOnHandEstimate = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "QtyOnHandEstimate", null);
    /** Column name QtyOnHandEstimate */
    public static final String COLUMNNAME_QtyOnHandEstimate = "QtyOnHandEstimate";

	/**
	 * Set Menge für Produktion.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyRequiredForProduction (java.math.BigDecimal QtyRequiredForProduction);

	/**
	 * Get Menge für Produktion.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyRequiredForProduction();

    /** Column definition for QtyRequiredForProduction */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyRequiredForProduction = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "QtyRequiredForProduction", null);
    /** Column name QtyRequiredForProduction */
    public static final String COLUMNNAME_QtyRequiredForProduction = "QtyRequiredForProduction";

	/**
	 * Set Bestellt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved_Purchase (java.math.BigDecimal QtyReserved_Purchase);

	/**
	 * Get Bestellt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved_Purchase();

    /** Column definition for QtyReserved_Purchase */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyReserved_Purchase = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "QtyReserved_Purchase", null);
    /** Column name QtyReserved_Purchase */
    public static final String COLUMNNAME_QtyReserved_Purchase = "QtyReserved_Purchase";

	/**
	 * Set Beauftragt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved_Sale (java.math.BigDecimal QtyReserved_Sale);

	/**
	 * Get Beauftragt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved_Sale();

    /** Column definition for QtyReserved_Sale */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyReserved_Sale = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "QtyReserved_Sale", null);
    /** Column name QtyReserved_Sale */
    public static final String COLUMNNAME_QtyReserved_Sale = "QtyReserved_Sale";

	/**
	 * Set Bestandsänderung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyStockChange (java.math.BigDecimal QtyStockChange);

	/**
	 * Get Bestandsänderung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyStockChange();

    /** Column definition for QtyStockChange */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockChange = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "QtyStockChange", null);
    /** Column name QtyStockChange */
    public static final String COLUMNNAME_QtyStockChange = "QtyStockChange";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_Cockpit, Object>(I_MD_Cockpit.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MD_Cockpit, org.compiere.model.I_AD_User>(I_MD_Cockpit.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
