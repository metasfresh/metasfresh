package de.metas.contracts.commission.model;


/** Generated Interface for C_Commission_Overview_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Commission_Overview_V 
{

    /** TableName=C_Commission_Overview_V */
    public static final String Table_Name = "C_Commission_Overview_V";

    /** AD_Table_ID=541463 */
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
	 * Set Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Zugeordneter Vertriebspartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID);

	/**
	 * Get Zugeordneter Vertriebspartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_SalesRep_ID();

    /** Column name C_BPartner_SalesRep_ID */
    public static final String COLUMNNAME_C_BPartner_SalesRep_ID = "C_BPartner_SalesRep_ID";

	/**
	 * Set Provisionsvorgang.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Commission_Instance_ID (int C_Commission_Instance_ID);

	/**
	 * Get Provisionsvorgang.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Commission_Instance_ID();

	public de.metas.contracts.commission.model.I_C_Commission_Instance getC_Commission_Instance();

	public void setC_Commission_Instance(de.metas.contracts.commission.model.I_C_Commission_Instance C_Commission_Instance);

    /** Column definition for C_Commission_Instance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, de.metas.contracts.commission.model.I_C_Commission_Instance> COLUMN_C_Commission_Instance_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, de.metas.contracts.commission.model.I_C_Commission_Instance>(I_C_Commission_Overview_V.class, "C_Commission_Instance_ID", de.metas.contracts.commission.model.I_C_Commission_Instance.class);
    /** Column name C_Commission_Instance_ID */
    public static final String COLUMNNAME_C_Commission_Instance_ID = "C_Commission_Instance_ID";

	/**
	 * Set Provision Übersicht.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Commission_Overview_V_ID (int C_Commission_Overview_V_ID);

	/**
	 * Get Provision Übersicht.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Commission_Overview_V_ID();

    /** Column definition for C_Commission_Overview_V_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_C_Commission_Overview_V_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "C_Commission_Overview_V_ID", null);
    /** Column name C_Commission_Overview_V_ID */
    public static final String COLUMNNAME_C_Commission_Overview_V_ID = "C_Commission_Overview_V_ID";

	/**
	 * Set Einstellungsdetail.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_CommissionSettingsLine_ID (int C_CommissionSettingsLine_ID);

	/**
	 * Get Einstellungsdetail.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_CommissionSettingsLine_ID();

	public de.metas.contracts.commission.model.I_C_CommissionSettingsLine getC_CommissionSettingsLine();

	public void setC_CommissionSettingsLine(de.metas.contracts.commission.model.I_C_CommissionSettingsLine C_CommissionSettingsLine);

    /** Column definition for C_CommissionSettingsLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, de.metas.contracts.commission.model.I_C_CommissionSettingsLine> COLUMN_C_CommissionSettingsLine_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, de.metas.contracts.commission.model.I_C_CommissionSettingsLine>(I_C_Commission_Overview_V.class, "C_CommissionSettingsLine_ID", de.metas.contracts.commission.model.I_C_CommissionSettingsLine.class);
    /** Column name C_CommissionSettingsLine_ID */
    public static final String COLUMNNAME_C_CommissionSettingsLine_ID = "C_CommissionSettingsLine_ID";

	/**
	 * Set Buchauszug.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Commission_Share_ID (int C_Commission_Share_ID);

	/**
	 * Get Buchauszug.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Commission_Share_ID();

	public de.metas.contracts.commission.model.I_C_Commission_Share getC_Commission_Share();

	public void setC_Commission_Share(de.metas.contracts.commission.model.I_C_Commission_Share C_Commission_Share);

    /** Column definition for C_Commission_Share_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, de.metas.contracts.commission.model.I_C_Commission_Share> COLUMN_C_Commission_Share_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, de.metas.contracts.commission.model.I_C_Commission_Share>(I_C_Commission_Overview_V.class, "C_Commission_Share_ID", de.metas.contracts.commission.model.I_C_Commission_Share.class);
    /** Column name C_Commission_Share_ID */
    public static final String COLUMNNAME_C_Commission_Share_ID = "C_Commission_Share_ID";

	/**
	 * Set Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Term_ID();

    /** Column definition for C_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_C_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "C_Flatrate_Term_ID", null);
    /** Column name C_Flatrate_Term_ID */
    public static final String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Provisionsabrechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Commission_ID (int C_Invoice_Candidate_Commission_ID);

	/**
	 * Get Provisionsabrechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Commission_ID();

    /** Column definition for C_Invoice_Candidate_Commission_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_C_Invoice_Candidate_Commission_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "C_Invoice_Candidate_Commission_ID", null);
    /** Column name C_Invoice_Candidate_Commission_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Commission_ID = "C_Invoice_Candidate_Commission_ID";

	/**
	 * Set Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_ID();

    /** Column definition for C_Invoice_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_C_Invoice_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "C_Invoice_Candidate_ID", null);
    /** Column name C_Invoice_Candidate_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Provisionsabrechnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Commission_ID (int C_Invoice_Commission_ID);

	/**
	 * Get Provisionsabrechnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Commission_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice_Commission();

	public void setC_Invoice_Commission(org.compiere.model.I_C_Invoice C_Invoice_Commission);

    /** Column definition for C_Invoice_Commission_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_Commission_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_Invoice>(I_C_Commission_Overview_V.class, "C_Invoice_Commission_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_Commission_ID */
    public static final String COLUMNNAME_C_Invoice_Commission_ID = "C_Invoice_Commission_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_Invoice>(I_C_Commission_Overview_V.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Provisionsabrechnungszeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceLine_Commission_ID (int C_InvoiceLine_Commission_ID);

	/**
	 * Get Provisionsabrechnungszeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceLine_Commission_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine_Commission();

	public void setC_InvoiceLine_Commission(org.compiere.model.I_C_InvoiceLine C_InvoiceLine_Commission);

    /** Column definition for C_InvoiceLine_Commission_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_Commission_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_InvoiceLine>(I_C_Commission_Overview_V.class, "C_InvoiceLine_Commission_ID", org.compiere.model.I_C_InvoiceLine.class);
    /** Column name C_InvoiceLine_Commission_ID */
    public static final String COLUMNNAME_C_InvoiceLine_Commission_ID = "C_InvoiceLine_Commission_ID";

	/**
	 * Set Rechnungsposition.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Rechnungsposition.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	public void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

    /** Column definition for C_InvoiceLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_InvoiceLine>(I_C_Commission_Overview_V.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Datum.
	 * Belegdatum des Provisionsauslösers
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCommissionDate (java.sql.Timestamp CommissionDate);

	/**
	 * Get Datum.
	 * Belegdatum des Provisionsauslösers
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCommissionDate();

    /** Column definition for CommissionDate */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_CommissionDate = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "CommissionDate", null);
    /** Column name CommissionDate */
    public static final String COLUMNNAME_CommissionDate = "CommissionDate";

	/**
	 * Set Provisionsauslöser.
	 * Art des Dokuments, dass den Provisionsvorgang ausgelöst hat
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCommissionTrigger_Type (java.lang.String CommissionTrigger_Type);

	/**
	 * Get Provisionsauslöser.
	 * Art des Dokuments, dass den Provisionsvorgang ausgelöst hat
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCommissionTrigger_Type();

    /** Column definition for CommissionTrigger_Type */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_CommissionTrigger_Type = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "CommissionTrigger_Type", null);
    /** Column name CommissionTrigger_Type */
    public static final String COLUMNNAME_CommissionTrigger_Type = "CommissionTrigger_Type";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_Order>(I_C_Commission_Overview_V.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_OrderLine>(I_C_Commission_Overview_V.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "Created", null);
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
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Hierarchie-Ebene.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLevelHierarchy (int LevelHierarchy);

	/**
	 * Get Hierarchie-Ebene.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLevelHierarchy();

    /** Column definition for LevelHierarchy */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_LevelHierarchy = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "LevelHierarchy", null);
    /** Column name LevelHierarchy */
    public static final String COLUMNNAME_LevelHierarchy = "LevelHierarchy";

	/**
	 * Set Provisionsauslöser Zeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMostRecentTriggerTimestamp (java.sql.Timestamp MostRecentTriggerTimestamp);

	/**
	 * Get Provisionsauslöser Zeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMostRecentTriggerTimestamp();

    /** Column definition for MostRecentTriggerTimestamp */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_MostRecentTriggerTimestamp = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "MostRecentTriggerTimestamp", null);
    /** Column name MostRecentTriggerTimestamp */
    public static final String COLUMNNAME_MostRecentTriggerTimestamp = "MostRecentTriggerTimestamp";

	/**
	 * Set Beauftragtes Produkt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Order_ID (int M_Product_Order_ID);

	/**
	 * Get Beauftragtes Produkt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Order_ID();

    /** Column name M_Product_Order_ID */
    public static final String COLUMNNAME_M_Product_Order_ID = "M_Product_Order_ID";

	/**
	 * Set % der Basispunkte.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPercentOfBasePoints (java.math.BigDecimal PercentOfBasePoints);

	/**
	 * Get % der Basispunkte.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPercentOfBasePoints();

    /** Column definition for PercentOfBasePoints */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PercentOfBasePoints = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "PercentOfBasePoints", null);
    /** Column name PercentOfBasePoints */
    public static final String COLUMNNAME_PercentOfBasePoints = "PercentOfBasePoints";

	/**
	 * Set Beauftragte Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPointsBase_Forecasted (java.math.BigDecimal PointsBase_Forecasted);

	/**
	 * Get Beauftragte Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsBase_Forecasted();

    /** Column definition for PointsBase_Forecasted */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsBase_Forecasted = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "PointsBase_Forecasted", null);
    /** Column name PointsBase_Forecasted */
    public static final String COLUMNNAME_PointsBase_Forecasted = "PointsBase_Forecasted";

	/**
	 * Set Fakturierbare Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPointsBase_Invoiceable (java.math.BigDecimal PointsBase_Invoiceable);

	/**
	 * Get Fakturierbare Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsBase_Invoiceable();

    /** Column definition for PointsBase_Invoiceable */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsBase_Invoiceable = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "PointsBase_Invoiceable", null);
    /** Column name PointsBase_Invoiceable */
    public static final String COLUMNNAME_PointsBase_Invoiceable = "PointsBase_Invoiceable";

	/**
	 * Set Fakturierte Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPointsBase_Invoiced (java.math.BigDecimal PointsBase_Invoiced);

	/**
	 * Get Fakturierte Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsBase_Invoiced();

    /** Column definition for PointsBase_Invoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsBase_Invoiced = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "PointsBase_Invoiced", null);
    /** Column name PointsBase_Invoiced */
    public static final String COLUMNNAME_PointsBase_Invoiced = "PointsBase_Invoiced";

	/**
	 * Set Abgerechnete Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPointsSum_Settled (java.math.BigDecimal PointsSum_Settled);

	/**
	 * Get Abgerechnete Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsSum_Settled();

    /** Column definition for PointsSum_Settled */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsSum_Settled = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "PointsSum_Settled", null);
    /** Column name PointsSum_Settled */
    public static final String COLUMNNAME_PointsSum_Settled = "PointsSum_Settled";

	/**
	 * Set Abzurechnende Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPointsSum_ToSettle (java.math.BigDecimal PointsSum_ToSettle);

	/**
	 * Get Abzurechnende Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsSum_ToSettle();

    /** Column definition for PointsSum_ToSettle */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsSum_ToSettle = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "PointsSum_ToSettle", null);
    /** Column name PointsSum_ToSettle */
    public static final String COLUMNNAME_PointsSum_ToSettle = "PointsSum_ToSettle";

	/**
	 * Set Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPOReference (java.lang.String POReference);

	/**
	 * Get Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Menge.
	 * Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyEntered (java.math.BigDecimal QtyEntered);

	/**
	 * Get Menge.
	 * Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyEntered();

    /** Column definition for QtyEntered */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_QtyEntered = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "QtyEntered", null);
    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Commission_Overview_V, Object>(I_C_Commission_Overview_V.class, "Updated", null);
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
}
