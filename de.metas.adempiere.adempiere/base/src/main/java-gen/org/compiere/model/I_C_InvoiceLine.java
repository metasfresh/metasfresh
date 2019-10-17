package org.compiere.model;


/** Generated Interface for C_InvoiceLine
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_C_InvoiceLine
{

    /** TableName=C_InvoiceLine */
    String Table_Name = "C_InvoiceLine";

    /** AD_Table_ID=333 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Set Asset-Gruppe.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/**
	 * Get Asset-Gruppe.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Asset_Group_ID();

	org.compiere.model.I_A_Asset_Group getA_Asset_Group();

	void setA_Asset_Group(org.compiere.model.I_A_Asset_Group A_Asset_Group);

    /** Column definition for A_Asset_Group_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_A_Asset_Group> COLUMN_A_Asset_Group_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "A_Asset_Group_ID", org.compiere.model.I_A_Asset_Group.class);
    /** Column name A_Asset_Group_ID */
    String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_ID (int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Asset_ID();

	org.compiere.model.I_A_Asset getA_Asset();

	void setA_Asset(org.compiere.model.I_A_Asset A_Asset);

    /** Column definition for A_Asset_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_A_Asset> COLUMN_A_Asset_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "A_Asset_ID", org.compiere.model.I_A_Asset.class);
    /** Column name A_Asset_ID */
    String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/**
	 * Set Capital vs Expense.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_CapvsExp (java.lang.String A_CapvsExp);

	/**
	 * Get Capital vs Expense.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_CapvsExp();

    /** Column definition for A_CapvsExp */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_A_CapvsExp = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "A_CapvsExp", null);
    /** Column name A_CapvsExp */
    String COLUMNNAME_A_CapvsExp = "A_CapvsExp";

	/**
	 * Set Asset Related?.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_CreateAsset (boolean A_CreateAsset);

	/**
	 * Get Asset Related?.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isA_CreateAsset();

    /** Column definition for A_CreateAsset */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_A_CreateAsset = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "A_CreateAsset", null);
    /** Column name A_CreateAsset */
    String COLUMNNAME_A_CreateAsset = "A_CreateAsset";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	org.compiere.model.I_AD_Org getAD_Org();

	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTrx_ID();

	org.compiere.model.I_AD_Org getAD_OrgTrx();

	void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx);

    /** Column definition for AD_OrgTrx_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set A_Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Processed (boolean A_Processed);

	/**
	 * Get A_Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isA_Processed();

    /** Column definition for A_Processed */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_A_Processed = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "A_Processed", null);
    /** Column name A_Processed */
    String COLUMNNAME_A_Processed = "A_Processed";

	/**
	 * Set Base_PricingSystem_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBase_PricingSystem_ID (int Base_PricingSystem_ID);

	/**
	 * Get Base_PricingSystem_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBase_PricingSystem_ID();

	org.compiere.model.I_M_PricingSystem getBase_PricingSystem();

	void setBase_PricingSystem(org.compiere.model.I_M_PricingSystem Base_PricingSystem);

    /** Column definition for Base_PricingSystem_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_PricingSystem> COLUMN_Base_PricingSystem_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "Base_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name Base_PricingSystem_ID */
    String COLUMNNAME_Base_PricingSystem_ID = "Base_PricingSystem_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	org.compiere.model.I_C_Activity getC_Activity();

	void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

    /** Column definition for C_Campaign_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	org.compiere.model.I_C_Charge getC_Charge();

	void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Rechnungsposition.
	 * Invoice Detail Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Rechnungsposition.
	 * Invoice Detail Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_ID();

    /** Column definition for C_InvoiceLine_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_C_InvoiceLine_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_InvoiceLine_ID", null);
    /** Column name C_InvoiceLine_ID */
    String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	org.compiere.model.I_C_OrderLine getC_OrderLine();

//	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	org.compiere.model.I_C_Project getC_Project();

	void setC_Project(org.compiere.model.I_C_Project C_Project);

    /** Column definition for C_Project_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
    /** Column name C_Project_ID */
    String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Projekt-Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/**
	 * Get Projekt-Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectPhase_ID();

	org.compiere.model.I_C_ProjectPhase getC_ProjectPhase();

	void setC_ProjectPhase(org.compiere.model.I_C_ProjectPhase C_ProjectPhase);

    /** Column definition for C_ProjectPhase_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_ProjectPhase_ID", org.compiere.model.I_C_ProjectPhase.class);
    /** Column name C_ProjectPhase_ID */
    String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/**
	 * Set Projekt-Aufgabe.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectTask_ID (int C_ProjectTask_ID);

	/**
	 * Get Projekt-Aufgabe.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectTask_ID();

	org.compiere.model.I_C_ProjectTask getC_ProjectTask();

	void setC_ProjectTask(org.compiere.model.I_C_ProjectTask C_ProjectTask);

    /** Column definition for C_ProjectTask_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_ProjectTask> COLUMN_C_ProjectTask_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_ProjectTask_ID", org.compiere.model.I_C_ProjectTask.class);
    /** Column name C_ProjectTask_ID */
    String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

    /** Column definition for Created */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "Created", null);
    /** Column name Created */
    String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

    /** Column definition for CreatedBy */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	org.compiere.model.I_C_TaxCategory getC_TaxCategory();

	void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory);

    /** Column definition for C_TaxCategory_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_TaxCategory> COLUMN_C_TaxCategory_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_TaxCategory_ID", org.compiere.model.I_C_TaxCategory.class);
    /** Column name C_TaxCategory_ID */
    String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Set Steuer.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Steuer.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	org.compiere.model.I_C_Tax getC_Tax();

	void setC_Tax(org.compiere.model.I_C_Tax C_Tax);

    /** Column definition for C_Tax_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Tax> COLUMN_C_Tax_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name C_Tax_ID */
    String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	org.compiere.model.I_C_UOM getC_UOM();

	void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getDescription();

    /** Column definition for Description */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "Description", null);
    /** Column name Description */
    String COLUMNNAME_Description = "Description";

	/**
	 * Set External IDs.
	 * List of external IDs from C_Invoice_Candidates;
 delimited with ';
,;
'
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalIds (java.lang.String ExternalIds);

	/**
	 * Get External IDs.
	 * List of external IDs from C_Invoice_Candidates;
 delimited with ';
,;
'
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalIds();

    /** Column definition for ExternalIds */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_ExternalIds = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "ExternalIds", null);
    /** Column name ExternalIds */
    String COLUMNNAME_ExternalIds = "ExternalIds";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

    /** Column definition for IsActive */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "IsActive", null);
    /** Column name IsActive */
    String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDescription (boolean IsDescription);

	/**
	 * Get Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDescription();

    /** Column definition for IsDescription */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsDescription = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "IsDescription", null);
    /** Column name IsDescription */
    String COLUMNNAME_IsDescription = "IsDescription";

	/**
	 * Set IsOrderLineReadOnly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOrderLineReadOnly (boolean IsOrderLineReadOnly);

	/**
	 * Get IsOrderLineReadOnly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOrderLineReadOnly();

    /** Column definition for IsOrderLineReadOnly */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsOrderLineReadOnly = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "IsOrderLineReadOnly", null);
    /** Column name IsOrderLineReadOnly */
    String COLUMNNAME_IsOrderLineReadOnly = "IsOrderLineReadOnly";

	/**
	 * Set andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrinted (boolean IsPrinted);

	/**
	 * Get andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrinted();

    /** Column definition for IsPrinted */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsPrinted = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "IsPrinted", null);
    /** Column name IsPrinted */
    String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

    /** Column definition for Line */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "Line", null);
    /** Column name Line */
    String COLUMNNAME_Line = "Line";

	/**
	 * Set Gutschrift Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLine_CreditMemoReason (java.lang.String Line_CreditMemoReason);

	/**
	 * Get Gutschrift Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getLine_CreditMemoReason();

    /** Column definition for Line_CreditMemoReason */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_Line_CreditMemoReason = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "Line_CreditMemoReason", null);
    /** Column name Line_CreditMemoReason */
    String COLUMNNAME_Line_CreditMemoReason = "Line_CreditMemoReason";

	/**
	 * Set Zeilennetto.
	 * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLineNetAmt (java.math.BigDecimal LineNetAmt);

	/**
	 * Get Zeilennetto.
	 * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Line Total.
	 * Total line amount incl. Tax
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineTotalAmt (java.math.BigDecimal LineTotalAmt);

	/**
	 * Get Line Total.
	 * Total line amount incl. Tax
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getLineTotalAmt();

    /** Column definition for LineTotalAmt */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_LineTotalAmt = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "LineTotalAmt", null);
    /** Column name LineTotalAmt */
    String COLUMNNAME_LineTotalAmt = "LineTotalAmt";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Line on Shipment or Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Line on Shipment or Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	org.compiere.model.I_M_Product getM_Product();

	void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set RMA-Position.
	 * Return Material Authorization Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_RMALine_ID (int M_RMALine_ID);

	/**
	 * Get RMA-Position.
	 * Return Material Authorization Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_RMALine_ID();

	org.compiere.model.I_M_RMALine getM_RMALine();

	void setM_RMALine(org.compiere.model.I_M_RMALine M_RMALine);

    /** Column definition for M_RMALine_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_RMALine> COLUMN_M_RMALine_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "M_RMALine_ID", org.compiere.model.I_M_RMALine.class);
    /** Column name M_RMALine_ID */
    String COLUMNNAME_M_RMALine_ID = "M_RMALine_ID";

	/**
	 * Set Einzelpreis.
	 * Actual Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceActual (java.math.BigDecimal PriceActual);

	/**
	 * Get Einzelpreis.
	 * Actual Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getPriceActual();

    /** Column definition for PriceActual */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "PriceActual", null);
    /** Column name PriceActual */
    String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Preis.
	 * Price Entered - the price based on the selected/base UoM
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceEntered (java.math.BigDecimal PriceEntered);

	/**
	 * Get Preis.
	 * Price Entered - the price based on the selected/base UoM
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getPriceEntered();

    /** Column definition for PriceEntered */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_PriceEntered = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "PriceEntered", null);
    /** Column name PriceEntered */
    String COLUMNNAME_PriceEntered = "PriceEntered";

	/**
	 * Set Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceLimit (java.math.BigDecimal PriceLimit);

	/**
	 * Get Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getPriceLimit();

    /** Column definition for PriceLimit */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_PriceLimit = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "PriceLimit", null);
    /** Column name PriceLimit */
    String COLUMNNAME_PriceLimit = "PriceLimit";

	/**
	 * Set Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceList (java.math.BigDecimal PriceList);

	/**
	 * Get Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getPriceList();

    /** Column definition for PriceList */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_PriceList = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "PriceList", null);
    /** Column name PriceList */
    String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

    /** Column definition for Processed */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "Processed", null);
    /** Column name Processed */
    String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductDescription (java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getProductDescription();

    /** Column definition for ProductDescription */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_ProductDescription = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "ProductDescription", null);
    /** Column name ProductDescription */
    String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Menge.
	 * The Quantity Entered is based on the selected UoM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (java.math.BigDecimal QtyEntered);

	/**
	 * Get Menge.
	 * The Quantity Entered is based on the selected UoM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getQtyEntered();

    /** Column definition for QtyEntered */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_QtyEntered = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "QtyEntered", null);
    /** Column name QtyEntered */
    String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Berechn. Menge.
	 * Menge, die bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyInvoiced (java.math.BigDecimal QtyInvoiced);

	/**
	 * Get Berechn. Menge.
	 * Menge, die bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getQtyInvoiced();

    /** Column definition for QtyInvoiced */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_QtyInvoiced = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "QtyInvoiced", null);
    /** Column name QtyInvoiced */
    String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Referenced Invoice Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_InvoiceLine_ID (int Ref_InvoiceLine_ID);

	/**
	 * Get Referenced Invoice Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_InvoiceLine_ID();

    /** Column definition for Ref_InvoiceLine_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_Ref_InvoiceLine_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "Ref_InvoiceLine_ID", null);
    /** Column name Ref_InvoiceLine_ID */
    String COLUMNNAME_Ref_InvoiceLine_ID = "Ref_InvoiceLine_ID";

	/**
	 * Set Revenue Recognition Amt.
	 * Revenue Recognition Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRRAmt (java.math.BigDecimal RRAmt);

	/**
	 * Get Revenue Recognition Amt.
	 * Revenue Recognition Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getRRAmt();

    /** Column definition for RRAmt */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_RRAmt = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "RRAmt", null);
    /** Column name RRAmt */
    String COLUMNNAME_RRAmt = "RRAmt";

	/**
	 * Set Revenue Recognition Start.
	 * Revenue Recognition Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRRStartDate (java.sql.Timestamp RRStartDate);

	/**
	 * Get Revenue Recognition Start.
	 * Revenue Recognition Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getRRStartDate();

    /** Column definition for RRStartDate */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_RRStartDate = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "RRStartDate", null);
    /** Column name RRStartDate */
    String COLUMNNAME_RRStartDate = "RRStartDate";

	/**
	 * Set Ressourcenzuordnung.
	 * Resource Assignment
	 *
	 * <br>Type: Assignment
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_ResourceAssignment_ID (int S_ResourceAssignment_ID);

	/**
	 * Get Ressourcenzuordnung.
	 * Resource Assignment
	 *
	 * <br>Type: Assignment
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_ResourceAssignment_ID();

    /** Column definition for S_ResourceAssignment_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_S_ResourceAssignment_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "S_ResourceAssignment_ID", null);
    /** Column name S_ResourceAssignment_ID */
    String COLUMNNAME_S_ResourceAssignment_ID = "S_ResourceAssignment_ID";

	/**
	 * Set Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxAmt (java.math.BigDecimal TaxAmt);

	/**
	 * Get Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getTaxAmt();

    /** Column definition for TaxAmt */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_TaxAmt = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "TaxAmt", null);
    /** Column name TaxAmt */
    String COLUMNNAME_TaxAmt = "TaxAmt";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "Updated", null);
    /** Column name Updated */
    String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

    /** Column definition for UpdatedBy */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	org.compiere.model.I_C_ElementValue getUser1();

	void setUser1(org.compiere.model.I_C_ElementValue User1);

    /** Column definition for User1_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User1_ID */
    String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	org.compiere.model.I_C_ElementValue getUser2();

	void setUser2(org.compiere.model.I_C_ElementValue User2);

    /** Column definition for User2_ID */
    org.adempiere.model.ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<>(I_C_InvoiceLine.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    String COLUMNNAME_User2_ID = "User2_ID";
}
