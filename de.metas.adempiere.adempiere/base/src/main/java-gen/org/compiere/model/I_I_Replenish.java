package org.compiere.model;


/** Generated Interface for I_Replenish
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_Replenish 
{

    /** TableName=I_Replenish */
    public static final String Table_Name = "I_Replenish";

    /** AD_Table_ID=541362 */
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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_Client>(I_I_Replenish.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_Org>(I_I_Replenish.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Kalender.
	 * Bezeichnung des Buchführungs-Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Kalender.
	 * Bezeichnung des Buchführungs-Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Calendar_ID();

	public org.compiere.model.I_C_Calendar getC_Calendar();

	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar);

    /** Column definition for C_Calendar_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_Calendar>(I_I_Replenish.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
    /** Column name C_Calendar_ID */
    public static final String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Set Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_Period>(I_I_Replenish.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_User>(I_I_Replenish.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Datum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateGeneral (java.lang.String DateGeneral);

	/**
	 * Get Datum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDateGeneral();

    /** Column definition for DateGeneral */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_DateGeneral = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "DateGeneral", null);
    /** Column name DateGeneral */
    public static final String COLUMNNAME_DateGeneral = "DateGeneral";

	/**
	 * Set Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (java.lang.String I_IsImported);

	/**
	 * Get Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Replenishment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_Replenish_ID (int I_Replenish_ID);

	/**
	 * Get Import Replenishment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_Replenish_ID();

    /** Column definition for I_Replenish_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_I_Replenish_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "I_Replenish_ID", null);
    /** Column name I_Replenish_ID */
    public static final String COLUMNNAME_I_Replenish_ID = "I_Replenish_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Maximalmenge.
	 * Maximaler Bestand für dieses Produkt
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLevel_Max (java.math.BigDecimal Level_Max);

	/**
	 * Get Maximalmenge.
	 * Maximaler Bestand für dieses Produkt
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLevel_Max();

    /** Column definition for Level_Max */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Level_Max = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Level_Max", null);
    /** Column name Level_Max */
    public static final String COLUMNNAME_Level_Max = "Level_Max";

	/**
	 * Set Mindestmenge.
	 * Minimaler Bestand für dieses Produkt
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLevel_Min (java.math.BigDecimal Level_Min);

	/**
	 * Get Mindestmenge.
	 * Minimaler Bestand für dieses Produkt
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLevel_Min();

    /** Column definition for Level_Min */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Level_Min = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Level_Min", null);
    /** Column name Level_Min */
    public static final String COLUMNNAME_Level_Min = "Level_Min";

	/**
	 * Set Lagerort-Schlüssel.
	 * Suchschlüssel für den Lagerort im Lager
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLocatorValue (java.lang.String LocatorValue);

	/**
	 * Get Lagerort-Schlüssel.
	 * Suchschlüssel für den Lagerort im Lager
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLocatorValue();

    /** Column definition for LocatorValue */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_LocatorValue = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "LocatorValue", null);
    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/**
	 * Set Lagerort.
	 * Lagerort im Lager
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Lagerort im Lager
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Locator>(I_I_Replenish.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Product>(I_I_Replenish.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set M_Replenish.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Replenish_ID (int M_Replenish_ID);

	/**
	 * Get M_Replenish.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Replenish_ID();

	public org.compiere.model.I_M_Replenish getM_Replenish();

	public void setM_Replenish(org.compiere.model.I_M_Replenish M_Replenish);

    /** Column definition for M_Replenish_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Replenish> COLUMN_M_Replenish_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Replenish>(I_I_Replenish.class, "M_Replenish_ID", org.compiere.model.I_M_Replenish.class);
    /** Column name M_Replenish_ID */
    public static final String COLUMNNAME_M_Replenish_ID = "M_Replenish_ID";

	/**
	 * Set Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Warehouse>(I_I_Replenish.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_WarehouseSource_ID (int M_WarehouseSource_ID);

	/**
	 * Get Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_WarehouseSource_ID();

	public org.compiere.model.I_M_Warehouse getM_WarehouseSource();

	public void setM_WarehouseSource(org.compiere.model.I_M_Warehouse M_WarehouseSource);

    /** Column definition for M_WarehouseSource_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Warehouse> COLUMN_M_WarehouseSource_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Warehouse>(I_I_Replenish.class, "M_WarehouseSource_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_WarehouseSource_ID */
    public static final String COLUMNNAME_M_WarehouseSource_ID = "M_WarehouseSource_ID";

	/**
	 * Set Organisations-Schlüssel.
	 * Suchschlüssel der Organisation
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrgValue (java.lang.String OrgValue);

	/**
	 * Get Organisations-Schlüssel.
	 * Suchschlüssel der Organisation
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrgValue();

    /** Column definition for OrgValue */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_OrgValue = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "OrgValue", null);
    /** Column name OrgValue */
    public static final String COLUMNNAME_OrgValue = "OrgValue";

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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Art der Wiederauffüllung.
	 * Methode für das Nachbestellen des Produktes
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReplenishType (java.lang.String ReplenishType);

	/**
	 * Get Art der Wiederauffüllung.
	 * Methode für das Nachbestellen des Produktes
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReplenishType();

    /** Column definition for ReplenishType */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_ReplenishType = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "ReplenishType", null);
    /** Column name ReplenishType */
    public static final String COLUMNNAME_ReplenishType = "ReplenishType";

	/**
	 * Set Markteinführungszeit.
	 * Time to Market (in Wochen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTimeToMarket (int TimeToMarket);

	/**
	 * Get Markteinführungszeit.
	 * Time to Market (in Wochen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getTimeToMarket();

    /** Column definition for TimeToMarket */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_TimeToMarket = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "TimeToMarket", null);
    /** Column name TimeToMarket */
    public static final String COLUMNNAME_TimeToMarket = "TimeToMarket";

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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_User>(I_I_Replenish.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Source Warehouse Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWarehouseSourceValue (java.lang.String WarehouseSourceValue);

	/**
	 * Get Source Warehouse Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWarehouseSourceValue();

    /** Column definition for WarehouseSourceValue */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_WarehouseSourceValue = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "WarehouseSourceValue", null);
    /** Column name WarehouseSourceValue */
    public static final String COLUMNNAME_WarehouseSourceValue = "WarehouseSourceValue";

	/**
	 * Set Lager-Schlüssel.
	 * Suchschlüssel des Lagers
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWarehouseValue (java.lang.String WarehouseValue);

	/**
	 * Get Lager-Schlüssel.
	 * Suchschlüssel des Lagers
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWarehouseValue();

    /** Column definition for WarehouseValue */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_WarehouseValue = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "WarehouseValue", null);
    /** Column name WarehouseValue */
    public static final String COLUMNNAME_WarehouseValue = "WarehouseValue";
}
