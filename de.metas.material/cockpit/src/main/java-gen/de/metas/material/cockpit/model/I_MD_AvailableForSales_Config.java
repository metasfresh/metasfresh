package de.metas.material.cockpit.model;


/** Generated Interface for MD_AvailableForSales_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_AvailableForSales_Config 
{

    /** TableName=MD_AvailableForSales_Config */
    public static final String Table_Name = "MD_AvailableForSales_Config";

    /** AD_Table_ID=541343 */
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
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_Client>(I_MD_AvailableForSales_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_Org>(I_MD_AvailableForSales_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Max. Wartezeit auf asynchrone Antwort (ms).
	 * Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAsyncTimeoutMillis (int AsyncTimeoutMillis);

	/**
	 * Get Max. Wartezeit auf asynchrone Antwort (ms).
	 * Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAsyncTimeoutMillis();

    /** Column definition for AsyncTimeoutMillis */
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_AsyncTimeoutMillis = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "AsyncTimeoutMillis", null);
    /** Column name AsyncTimeoutMillis */
    public static final String COLUMNNAME_AsyncTimeoutMillis = "AsyncTimeoutMillis";

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
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_User>(I_MD_AvailableForSales_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Farbe für kurzfr. Verfügbarkeitsproblem.
	 * Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInsufficientQtyAvailableForSalesColor_ID (int InsufficientQtyAvailableForSalesColor_ID);

	/**
	 * Get Farbe für kurzfr. Verfügbarkeitsproblem.
	 * Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getInsufficientQtyAvailableForSalesColor_ID();

	public org.compiere.model.I_AD_Color getInsufficientQtyAvailableForSalesColor();

	public void setInsufficientQtyAvailableForSalesColor(org.compiere.model.I_AD_Color InsufficientQtyAvailableForSalesColor);

    /** Column definition for InsufficientQtyAvailableForSalesColor_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_Color> COLUMN_InsufficientQtyAvailableForSalesColor_ID = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_Color>(I_MD_AvailableForSales_Config.class, "InsufficientQtyAvailableForSalesColor_ID", org.compiere.model.I_AD_Color.class);
    /** Column name InsufficientQtyAvailableForSalesColor_ID */
    public static final String COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID = "InsufficientQtyAvailableForSalesColor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Async.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAsync (boolean IsAsync);

	/**
	 * Get Async.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAsync();

    /** Column definition for IsAsync */
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_IsAsync = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "IsAsync", null);
    /** Column name IsAsync */
    public static final String COLUMNNAME_IsAsync = "IsAsync";

	/**
	 * Set Feature aktivtiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsFeatureActivated (boolean IsFeatureActivated);

	/**
	 * Get Feature aktivtiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isFeatureActivated();

    /** Column definition for IsFeatureActivated */
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_IsFeatureActivated = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "IsFeatureActivated", null);
    /** Column name IsFeatureActivated */
    public static final String COLUMNNAME_IsFeatureActivated = "IsFeatureActivated";

	/**
	 * Set MD_AvailableForSales_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_AvailableForSales_Config_ID (int MD_AvailableForSales_Config_ID);

	/**
	 * Get MD_AvailableForSales_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_AvailableForSales_Config_ID();

    /** Column definition for MD_AvailableForSales_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_MD_AvailableForSales_Config_ID = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "MD_AvailableForSales_Config_ID", null);
    /** Column name MD_AvailableForSales_Config_ID */
    public static final String COLUMNNAME_MD_AvailableForSales_Config_ID = "MD_AvailableForSales_Config_ID";

	/**
	 * Set Rückschauinterval Auftragspositionen in Bearb. (Std).
	 * Interval in Stunden bis zum aktuellen Zeitpunkt, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSalesOrderLookBehindHours (int SalesOrderLookBehindHours);

	/**
	 * Get Rückschauinterval Auftragspositionen in Bearb. (Std).
	 * Interval in Stunden bis zum aktuellen Zeitpunkt, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSalesOrderLookBehindHours();

    /** Column definition for SalesOrderLookBehindHours */
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_SalesOrderLookBehindHours = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "SalesOrderLookBehindHours", null);
    /** Column name SalesOrderLookBehindHours */
    public static final String COLUMNNAME_SalesOrderLookBehindHours = "SalesOrderLookBehindHours";

	/**
	 * Set Vorausschauinterval zu gepl. Lieferungen (Std).
	 * Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setShipmentDateLookAheadHours (int ShipmentDateLookAheadHours);

	/**
	 * Get Vorausschauinterval zu gepl. Lieferungen (Std).
	 * Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getShipmentDateLookAheadHours();

    /** Column definition for ShipmentDateLookAheadHours */
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_ShipmentDateLookAheadHours = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "ShipmentDateLookAheadHours", null);
    /** Column name ShipmentDateLookAheadHours */
    public static final String COLUMNNAME_ShipmentDateLookAheadHours = "ShipmentDateLookAheadHours";

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
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, Object>(I_MD_AvailableForSales_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_User>(I_MD_AvailableForSales_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
