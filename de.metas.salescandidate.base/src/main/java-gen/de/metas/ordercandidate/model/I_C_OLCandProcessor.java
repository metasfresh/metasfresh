package de.metas.ordercandidate.model;


/** Generated Interface for C_OLCandProcessor
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_OLCandProcessor 
{

    /** TableName=C_OLCandProcessor */
    public static final String Table_Name = "C_OLCandProcessor";

    /** AD_Table_ID=540245 */
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_Client>(I_C_OLCandProcessor.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_Org>(I_C_OLCandProcessor.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ablaufsteuerung.
	 * Schedule Processes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Scheduler_ID (int AD_Scheduler_ID);

	/**
	 * Get Ablaufsteuerung.
	 * Schedule Processes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Scheduler_ID();

	public org.compiere.model.I_AD_Scheduler getAD_Scheduler();

	public void setAD_Scheduler(org.compiere.model.I_AD_Scheduler AD_Scheduler);

    /** Column definition for AD_Scheduler_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_Scheduler> COLUMN_AD_Scheduler_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_Scheduler>(I_C_OLCandProcessor.class, "AD_Scheduler_ID", org.compiere.model.I_AD_Scheduler.class);
    /** Column name AD_Scheduler_ID */
    public static final String COLUMNNAME_AD_Scheduler_ID = "AD_Scheduler_ID";

	/**
	 * Set Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_InCharge_ID();

	public org.compiere.model.I_AD_User getAD_User_InCharge();

	public void setAD_User_InCharge(org.compiere.model.I_AD_User AD_User_InCharge);

    /** Column definition for AD_User_InCharge_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_User> COLUMN_AD_User_InCharge_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_User>(I_C_OLCandProcessor.class, "AD_User_InCharge_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_InCharge_ID */
    public static final String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Zielbelegart.
	 * Zielbelegart für die Umwandlung von Dokumenten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Zielbelegart.
	 * Zielbelegart für die Umwandlung von Dokumenten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocTypeTarget_ID();

	public org.compiere.model.I_C_DocType getC_DocTypeTarget();

	public void setC_DocTypeTarget(org.compiere.model.I_C_DocType C_DocTypeTarget);

    /** Column definition for C_DocTypeTarget_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_C_DocType> COLUMN_C_DocTypeTarget_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_C_DocType>(I_C_OLCandProcessor.class, "C_DocTypeTarget_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocTypeTarget_ID */
    public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set C_OLCandAgg_IncludedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OLCandAgg_IncludedTab (java.lang.String C_OLCandAgg_IncludedTab);

	/**
	 * Get C_OLCandAgg_IncludedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getC_OLCandAgg_IncludedTab();

    /** Column definition for C_OLCandAgg_IncludedTab */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_C_OLCandAgg_IncludedTab = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "C_OLCandAgg_IncludedTab", null);
    /** Column name C_OLCandAgg_IncludedTab */
    public static final String COLUMNNAME_C_OLCandAgg_IncludedTab = "C_OLCandAgg_IncludedTab";

	/**
	 * Set Auftragskand. Verarb..
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OLCandProcessor_ID (int C_OLCandProcessor_ID);

	/**
	 * Get Auftragskand. Verarb..
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OLCandProcessor_ID();

    /** Column definition for C_OLCandProcessor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_C_OLCandProcessor_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "C_OLCandProcessor_ID", null);
    /** Column name C_OLCandProcessor_ID */
    public static final String COLUMNNAME_C_OLCandProcessor_ID = "C_OLCandProcessor_ID";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm();

	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column definition for C_PaymentTerm_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_C_PaymentTerm>(I_C_OLCandProcessor.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_User>(I_C_OLCandProcessor.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule();

    /** Column definition for DeliveryRule */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_DeliveryRule = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "DeliveryRule", null);
    /** Column name DeliveryRule */
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule();

    /** Column definition for DeliveryViaRule */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Frachtkostenberechnung.
	 * Methode zur Berechnung von Frachtkosten
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFreightCostRule (java.lang.String FreightCostRule);

	/**
	 * Get Frachtkostenberechnung.
	 * Methode zur Berechnung von Frachtkosten
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFreightCostRule();

    /** Column definition for FreightCostRule */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_FreightCostRule = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "FreightCostRule", null);
    /** Column name FreightCostRule */
    public static final String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceRule (java.lang.String InvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceRule();

    /** Column definition for InvoiceRule */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_InvoiceRule = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "InvoiceRule", null);
    /** Column name InvoiceRule */
    public static final String COLUMNNAME_InvoiceRule = "InvoiceRule";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmals-Satz.
	 * Merkmals-Satz zum Produkt
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Merkmals-Satz.
	 * Merkmals-Satz zum Produkt
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSet_ID();

	public org.compiere.model.I_M_AttributeSet getM_AttributeSet();

	public void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet);

    /** Column definition for M_AttributeSet_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_AttributeSet> COLUMN_M_AttributeSet_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_AttributeSet>(I_C_OLCandProcessor.class, "M_AttributeSet_ID", org.compiere.model.I_M_AttributeSet.class);
    /** Column name M_AttributeSet_ID */
    public static final String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

	/**
	 * Set Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getM_PricingSystem();

	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem);

    /** Column definition for M_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_PricingSystem>(I_C_OLCandProcessor.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_Shipper>(I_C_OLCandProcessor.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_Warehouse>(I_C_OLCandProcessor.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Zahlungsweise.
	 * Wie die Rechnung bezahlt wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
	 * Wie die Rechnung bezahlt wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "PaymentRule", null);
    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, Object>(I_C_OLCandProcessor.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_User>(I_C_OLCandProcessor.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
