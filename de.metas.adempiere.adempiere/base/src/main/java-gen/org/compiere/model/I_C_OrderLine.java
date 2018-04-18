package org.compiere.model;


/** Generated Interface for C_OrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_OrderLine 
{

    /** TableName=C_OrderLine */
    public static final String Table_Name = "C_OrderLine";

    /** AD_Table_ID=260 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_Client>(I_C_OrderLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_Org>(I_C_OrderLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgTrx_ID();

	public org.compiere.model.I_AD_Org getAD_OrgTrx();

	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx);

    /** Column definition for AD_OrgTrx_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_Org>(I_C_OrderLine.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity();

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Activity>(I_C_OrderLine.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_BPartner>(I_C_OrderLine.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_BPartner_Location>(I_C_OrderLine.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign();

	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

    /** Column definition for C_Campaign_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Campaign>(I_C_OrderLine.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge();

	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Charge>(I_C_OrderLine.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Compensation Group Schema Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_CompensationGroup_SchemaLine_ID (int C_CompensationGroup_SchemaLine_ID);

	/**
	 * Get Compensation Group Schema Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_CompensationGroup_SchemaLine_ID();

	public de.metas.order.model.I_C_CompensationGroup_SchemaLine getC_CompensationGroup_SchemaLine();

	public void setC_CompensationGroup_SchemaLine(de.metas.order.model.I_C_CompensationGroup_SchemaLine C_CompensationGroup_SchemaLine);

    /** Column definition for C_CompensationGroup_SchemaLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, de.metas.order.model.I_C_CompensationGroup_SchemaLine> COLUMN_C_CompensationGroup_SchemaLine_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, de.metas.order.model.I_C_CompensationGroup_SchemaLine>(I_C_OrderLine.class, "C_CompensationGroup_SchemaLine_ID", de.metas.order.model.I_C_CompensationGroup_SchemaLine.class);
    /** Column name C_CompensationGroup_SchemaLine_ID */
    public static final String COLUMNNAME_C_CompensationGroup_SchemaLine_ID = "C_CompensationGroup_SchemaLine_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Currency>(I_C_OrderLine.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Order Compensation Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_CompensationGroup_ID (int C_Order_CompensationGroup_ID);

	/**
	 * Get Order Compensation Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_CompensationGroup_ID();

	public org.compiere.model.I_C_Order_CompensationGroup getC_Order_CompensationGroup();

	public void setC_Order_CompensationGroup(org.compiere.model.I_C_Order_CompensationGroup C_Order_CompensationGroup);

    /** Column definition for C_Order_CompensationGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Order_CompensationGroup> COLUMN_C_Order_CompensationGroup_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Order_CompensationGroup>(I_C_OrderLine.class, "C_Order_CompensationGroup_ID", org.compiere.model.I_C_Order_CompensationGroup.class);
    /** Column name C_Order_CompensationGroup_ID */
    public static final String COLUMNNAME_C_Order_CompensationGroup_ID = "C_Order_CompensationGroup_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Order>(I_C_OrderLine.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "C_OrderLine_ID", null);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Zahlungsbedingung abw..
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_Override_ID (int C_PaymentTerm_Override_ID);

	/**
	 * Get Zahlungsbedingung abw..
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_Override_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm_Override();

	public void setC_PaymentTerm_Override(org.compiere.model.I_C_PaymentTerm C_PaymentTerm_Override);

    /** Column definition for C_PaymentTerm_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_Override_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_PaymentTerm>(I_C_OrderLine.class, "C_PaymentTerm_Override_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_Override_ID */
    public static final String COLUMNNAME_C_PaymentTerm_Override_ID = "C_PaymentTerm_Override_ID";

	/**
	 * Set Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project();

	public void setC_Project(org.compiere.model.I_C_Project C_Project);

    /** Column definition for C_Project_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Project>(I_C_OrderLine.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Projekt-Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/**
	 * Get Projekt-Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ProjectPhase_ID();

	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase();

	public void setC_ProjectPhase(org.compiere.model.I_C_ProjectPhase C_ProjectPhase);

    /** Column definition for C_ProjectPhase_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ProjectPhase>(I_C_OrderLine.class, "C_ProjectPhase_ID", org.compiere.model.I_C_ProjectPhase.class);
    /** Column name C_ProjectPhase_ID */
    public static final String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/**
	 * Set Projekt-Aufgabe.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ProjectTask_ID (int C_ProjectTask_ID);

	/**
	 * Get Projekt-Aufgabe.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ProjectTask_ID();

	public org.compiere.model.I_C_ProjectTask getC_ProjectTask();

	public void setC_ProjectTask(org.compiere.model.I_C_ProjectTask C_ProjectTask);

    /** Column definition for C_ProjectTask_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ProjectTask> COLUMN_C_ProjectTask_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ProjectTask>(I_C_OrderLine.class, "C_ProjectTask_ID", org.compiere.model.I_C_ProjectTask.class);
    /** Column name C_ProjectTask_ID */
    public static final String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

	/**
	 * Set Steuer.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Steuer.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax();

	public void setC_Tax(org.compiere.model.I_C_Tax C_Tax);

    /** Column definition for C_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Tax> COLUMN_C_Tax_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Tax>(I_C_OrderLine.class, "C_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_TaxCategory_ID();

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory();

	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory);

    /** Column definition for C_TaxCategory_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_TaxCategory> COLUMN_C_TaxCategory_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_TaxCategory>(I_C_OrderLine.class, "C_TaxCategory_ID", org.compiere.model.I_C_TaxCategory.class);
    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_UOM>(I_C_OrderLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_User>(I_C_OrderLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Lieferdatum.
	 * Date when the product was delivered
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateDelivered (java.sql.Timestamp DateDelivered);

	/**
	 * Get Lieferdatum.
	 * Date when the product was delivered
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDelivered();

    /** Column definition for DateDelivered */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_DateDelivered = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "DateDelivered", null);
    /** Column name DateDelivered */
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";

	/**
	 * Set Rechnungsdatum.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced);

	/**
	 * Get Rechnungsdatum.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateInvoiced();

    /** Column definition for DateInvoiced */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_DateInvoiced = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "DateInvoiced", null);
    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/**
	 * Set Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

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
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Rabatt %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscount (java.math.BigDecimal Discount);

	/**
	 * Get Rabatt %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Preislimit erzwingen.
	 * Do not allow prices below the limit price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEnforcePriceLimit (boolean EnforcePriceLimit);

	/**
	 * Get Preislimit erzwingen.
	 * Do not allow prices below the limit price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEnforcePriceLimit();

    /** Column definition for EnforcePriceLimit */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_EnforcePriceLimit = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "EnforcePriceLimit", null);
    /** Column name EnforcePriceLimit */
    public static final String COLUMNNAME_EnforcePriceLimit = "EnforcePriceLimit";

	/**
	 * Set Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFreightAmt (java.math.BigDecimal FreightAmt);

	/**
	 * Get Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFreightAmt();

    /** Column definition for FreightAmt */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_FreightAmt = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "FreightAmt", null);
    /** Column name FreightAmt */
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";

	/**
	 * Set Häufigkeitsart.
	 * Häufigkeitsart für Ereignisse
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFrequencyType (java.lang.String FrequencyType);

	/**
	 * Get Häufigkeitsart.
	 * Häufigkeitsart für Ereignisse
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFrequencyType();

    /** Column definition for FrequencyType */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_FrequencyType = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "FrequencyType", null);
    /** Column name FrequencyType */
    public static final String COLUMNNAME_FrequencyType = "FrequencyType";

	/**
	 * Set Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationAmtType (java.lang.String GroupCompensationAmtType);

	/**
	 * Get Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroupCompensationAmtType();

    /** Column definition for GroupCompensationAmtType */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_GroupCompensationAmtType = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "GroupCompensationAmtType", null);
    /** Column name GroupCompensationAmtType */
    public static final String COLUMNNAME_GroupCompensationAmtType = "GroupCompensationAmtType";

	/**
	 * Set Compensation base amount.
	 * Base amount for calculating percentage group compensation
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationBaseAmt (java.math.BigDecimal GroupCompensationBaseAmt);

	/**
	 * Get Compensation base amount.
	 * Base amount for calculating percentage group compensation
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getGroupCompensationBaseAmt();

    /** Column definition for GroupCompensationBaseAmt */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_GroupCompensationBaseAmt = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "GroupCompensationBaseAmt", null);
    /** Column name GroupCompensationBaseAmt */
    public static final String COLUMNNAME_GroupCompensationBaseAmt = "GroupCompensationBaseAmt";

	/**
	 * Set Compensation percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationPercentage (java.math.BigDecimal GroupCompensationPercentage);

	/**
	 * Get Compensation percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getGroupCompensationPercentage();

    /** Column definition for GroupCompensationPercentage */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_GroupCompensationPercentage = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "GroupCompensationPercentage", null);
    /** Column name GroupCompensationPercentage */
    public static final String COLUMNNAME_GroupCompensationPercentage = "GroupCompensationPercentage";

	/**
	 * Set Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationType (java.lang.String GroupCompensationType);

	/**
	 * Get Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroupCompensationType();

    /** Column definition for GroupCompensationType */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_GroupCompensationType = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "GroupCompensationType", null);
    /** Column name GroupCompensationType */
    public static final String COLUMNNAME_GroupCompensationType = "GroupCompensationType";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDescription (boolean IsDescription);

	/**
	 * Get Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDescription();

    /** Column definition for IsDescription */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_IsDescription = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "IsDescription", null);
    /** Column name IsDescription */
    public static final String COLUMNNAME_IsDescription = "IsDescription";

	/**
	 * Set Discount Editable.
	 * Allow user to change the discount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDiscountEditable (boolean IsDiscountEditable);

	/**
	 * Get Discount Editable.
	 * Allow user to change the discount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDiscountEditable();

    /** Column definition for IsDiscountEditable */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_IsDiscountEditable = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "IsDiscountEditable", null);
    /** Column name IsDiscountEditable */
    public static final String COLUMNNAME_IsDiscountEditable = "IsDiscountEditable";

	/**
	 * Set Group Compensation Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsGroupCompensationLine (boolean IsGroupCompensationLine);

	/**
	 * Get Group Compensation Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isGroupCompensationLine();

    /** Column definition for IsGroupCompensationLine */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_IsGroupCompensationLine = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "IsGroupCompensationLine", null);
    /** Column name IsGroupCompensationLine */
    public static final String COLUMNNAME_IsGroupCompensationLine = "IsGroupCompensationLine";

	/**
	 * Set Prod.-Beschr. ändern.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsIndividualDescription (boolean IsIndividualDescription);

	/**
	 * Get Prod.-Beschr. ändern.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isIndividualDescription();

    /** Column definition for IsIndividualDescription */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_IsIndividualDescription = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "IsIndividualDescription", null);
    /** Column name IsIndividualDescription */
    public static final String COLUMNNAME_IsIndividualDescription = "IsIndividualDescription";

	/**
	 * Set Manueller Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManualPrice (boolean IsManualPrice);

	/**
	 * Get Manueller Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManualPrice();

    /** Column definition for IsManualPrice */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_IsManualPrice = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "IsManualPrice", null);
    /** Column name IsManualPrice */
    public static final String COLUMNNAME_IsManualPrice = "IsManualPrice";

	/**
	 * Set Price Editable.
	 * Allow user to change the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPriceEditable (boolean IsPriceEditable);

	/**
	 * Get Price Editable.
	 * Allow user to change the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPriceEditable();

    /** Column definition for IsPriceEditable */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_IsPriceEditable = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "IsPriceEditable", null);
    /** Column name IsPriceEditable */
    public static final String COLUMNNAME_IsPriceEditable = "IsPriceEditable";

	/**
	 * Set Abo.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSubscription (boolean IsSubscription);

	/**
	 * Get Abo.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSubscription();

    /** Column definition for IsSubscription */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_IsSubscription = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "IsSubscription", null);
    /** Column name IsSubscription */
    public static final String COLUMNNAME_IsSubscription = "IsSubscription";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Zeilennetto.
	 * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt);

	/**
	 * Get Zeilennetto.
	 * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Zugehörige Bestellposition.
	 * Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Bestellposition referenzieren.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLink_OrderLine_ID (int Link_OrderLine_ID);

	/**
	 * Get Zugehörige Bestellposition.
	 * Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Bestellposition referenzieren.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLink_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getLink_OrderLine();

	public void setLink_OrderLine(org.compiere.model.I_C_OrderLine Link_OrderLine);

    /** Column definition for Link_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_OrderLine> COLUMN_Link_OrderLine_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_OrderLine>(I_C_OrderLine.class, "Link_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name Link_OrderLine_ID */
    public static final String COLUMNNAME_Link_OrderLine_ID = "Link_OrderLine_ID";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_AttributeSetInstance>(I_C_OrderLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Discount Schema Break.
	 * Trade Discount Break
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID);

	/**
	 * Get Discount Schema Break.
	 * Trade Discount Break
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchemaBreak_ID();

	public org.compiere.model.I_M_DiscountSchemaBreak getM_DiscountSchemaBreak();

	public void setM_DiscountSchemaBreak(org.compiere.model.I_M_DiscountSchemaBreak M_DiscountSchemaBreak);

    /** Column definition for M_DiscountSchemaBreak_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_DiscountSchemaBreak> COLUMN_M_DiscountSchemaBreak_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_DiscountSchemaBreak>(I_C_OrderLine.class, "M_DiscountSchemaBreak_ID", org.compiere.model.I_M_DiscountSchemaBreak.class);
    /** Column name M_DiscountSchemaBreak_ID */
    public static final String COLUMNNAME_M_DiscountSchemaBreak_ID = "M_DiscountSchemaBreak_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Product>(I_C_OrderLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Promotion.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Promotion_ID (int M_Promotion_ID);

	/**
	 * Get Promotion.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Promotion_ID();

	public org.compiere.model.I_M_Promotion getM_Promotion();

	public void setM_Promotion(org.compiere.model.I_M_Promotion M_Promotion);

    /** Column definition for M_Promotion_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Promotion> COLUMN_M_Promotion_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Promotion>(I_C_OrderLine.class, "M_Promotion_ID", org.compiere.model.I_M_Promotion.class);
    /** Column name M_Promotion_ID */
    public static final String COLUMNNAME_M_Promotion_ID = "M_Promotion_ID";

	/**
	 * Set Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Shipper>(I_C_OrderLine.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Warehouse>(I_C_OrderLine.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set No Price Conditions Indicator.
	 *
	 * <br>Type: Color
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNoPriceConditionsColor_ID (int NoPriceConditionsColor_ID);

	/**
	 * Get No Price Conditions Indicator.
	 *
	 * <br>Type: Color
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getNoPriceConditionsColor_ID();

    /** Column definition for NoPriceConditionsColor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_NoPriceConditionsColor_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "NoPriceConditionsColor_ID", null);
    /** Column name NoPriceConditionsColor_ID */
    public static final String COLUMNNAME_NoPriceConditionsColor_ID = "NoPriceConditionsColor_ID";

	/**
	 * Set Gesamtauftragsrabbat.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderDiscount (java.math.BigDecimal OrderDiscount);

	/**
	 * Get Gesamtauftragsrabbat.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOrderDiscount();

    /** Column definition for OrderDiscount */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_OrderDiscount = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "OrderDiscount", null);
    /** Column name OrderDiscount */
    public static final String COLUMNNAME_OrderDiscount = "OrderDiscount";

	/**
	 * Set Abrufauftragsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPOCallOrderDate (java.sql.Timestamp POCallOrderDate);

	/**
	 * Get Abrufauftragsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPOCallOrderDate();

    /** Column definition for POCallOrderDate */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_POCallOrderDate = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "POCallOrderDate", null);
    /** Column name POCallOrderDate */
    public static final String COLUMNNAME_POCallOrderDate = "POCallOrderDate";

	/**
	 * Set Manufacturing Cost Collector.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID);

	/**
	 * Get Manufacturing Cost Collector.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Cost_Collector_ID();

	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector();

	public void setPP_Cost_Collector(org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector);

    /** Column definition for PP_Cost_Collector_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.eevolution.model.I_PP_Cost_Collector>(I_C_OrderLine.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
    /** Column name PP_Cost_Collector_ID */
    public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Einzelpreis.
	 * Actual Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriceActual (java.math.BigDecimal PriceActual);

	/**
	 * Get Einzelpreis.
	 * Actual Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceActual();

    /** Column definition for PriceActual */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "PriceActual", null);
    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Cost Price.
	 * Price per Unit of Measure including all indirect costs (Freight, etc.)
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceCost (java.math.BigDecimal PriceCost);

	/**
	 * Get Cost Price.
	 * Price per Unit of Measure including all indirect costs (Freight, etc.)
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceCost();

    /** Column definition for PriceCost */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_PriceCost = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "PriceCost", null);
    /** Column name PriceCost */
    public static final String COLUMNNAME_PriceCost = "PriceCost";

	/**
	 * Set Preis.
	 * Price Entered - the price based on the selected/base UoM
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriceEntered (java.math.BigDecimal PriceEntered);

	/**
	 * Get Preis.
	 * Price Entered - the price based on the selected/base UoM
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceEntered();

    /** Column definition for PriceEntered */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_PriceEntered = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "PriceEntered", null);
    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/**
	 * Set Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriceLimit (java.math.BigDecimal PriceLimit);

	/**
	 * Get Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceLimit();

    /** Column definition for PriceLimit */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_PriceLimit = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "PriceLimit", null);
    /** Column name PriceLimit */
    public static final String COLUMNNAME_PriceLimit = "PriceLimit";

	/**
	 * Set Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriceList (java.math.BigDecimal PriceList);

	/**
	 * Get Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceList();

    /** Column definition for PriceList */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_PriceList = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "PriceList", null);
    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Auszeichnungspreis (standard).
	 * Auszeichnungspreis (standard)
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceList_Std (java.math.BigDecimal PriceList_Std);

	/**
	 * Get Auszeichnungspreis (standard).
	 * Auszeichnungspreis (standard)
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceList_Std();

    /** Column definition for PriceList_Std */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_PriceList_Std = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "PriceList_Std", null);
    /** Column name PriceList_Std */
    public static final String COLUMNNAME_PriceList_Std = "PriceList_Std";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductDescription (java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductDescription();

    /** Column definition for ProductDescription */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_ProductDescription = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "ProductDescription", null);
    /** Column name ProductDescription */
    public static final String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Menge.
	 * The Quantity Entered is based on the selected UoM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyEntered (java.math.BigDecimal QtyEntered);

	/**
	 * Get Menge.
	 * The Quantity Entered is based on the selected UoM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyEntered();

    /** Column definition for QtyEntered */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_QtyEntered = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "QtyEntered", null);
    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Berechn. Menge.
	 * Menge, die bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced);

	/**
	 * Get Berechn. Menge.
	 * Menge, die bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInvoiced();

    /** Column definition for QtyInvoiced */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_QtyInvoiced = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "QtyInvoiced", null);
    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Lost Sales Qty.
	 * Quantity of potential sales
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyLostSales (java.math.BigDecimal QtyLostSales);

	/**
	 * Get Lost Sales Qty.
	 * Quantity of potential sales
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyLostSales();

    /** Column definition for QtyLostSales */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_QtyLostSales = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "QtyLostSales", null);
    /** Column name QtyLostSales */
    public static final String COLUMNNAME_QtyLostSales = "QtyLostSales";

	/**
	 * Set Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set QtyOrderedOverUnder.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrderedOverUnder (java.math.BigDecimal QtyOrderedOverUnder);

	/**
	 * Get QtyOrderedOverUnder.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrderedOverUnder();

    /** Column definition for QtyOrderedOverUnder */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_QtyOrderedOverUnder = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "QtyOrderedOverUnder", null);
    /** Column name QtyOrderedOverUnder */
    public static final String COLUMNNAME_QtyOrderedOverUnder = "QtyOrderedOverUnder";

	/**
	 * Set Offen.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved (java.math.BigDecimal QtyReserved);

	/**
	 * Get Offen.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved();

    /** Column definition for QtyReserved */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_QtyReserved = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "QtyReserved", null);
    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Gegenbelegzeile-Fremdorganisation.
	 * Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Gegenbeleg-Position aus einer anderen Organisation referenzieren
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRef_OrderLine_ID (int Ref_OrderLine_ID);

	/**
	 * Get Gegenbelegzeile-Fremdorganisation.
	 * Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Gegenbeleg-Position aus einer anderen Organisation referenzieren
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRef_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getRef_OrderLine();

	public void setRef_OrderLine(org.compiere.model.I_C_OrderLine Ref_OrderLine);

    /** Column definition for Ref_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_OrderLine> COLUMN_Ref_OrderLine_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_OrderLine>(I_C_OrderLine.class, "Ref_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name Ref_OrderLine_ID */
    public static final String COLUMNNAME_Ref_OrderLine_ID = "Ref_OrderLine_ID";

	/**
	 * Set Revenue Recognition Amt.
	 * Revenue Recognition Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRRAmt (java.math.BigDecimal RRAmt);

	/**
	 * Get Revenue Recognition Amt.
	 * Revenue Recognition Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRRAmt();

    /** Column definition for RRAmt */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_RRAmt = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "RRAmt", null);
    /** Column name RRAmt */
    public static final String COLUMNNAME_RRAmt = "RRAmt";

	/**
	 * Set Revenue Recognition Start.
	 * Revenue Recognition Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRRStartDate (java.sql.Timestamp RRStartDate);

	/**
	 * Get Revenue Recognition Start.
	 * Revenue Recognition Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getRRStartDate();

    /** Column definition for RRStartDate */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_RRStartDate = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "RRStartDate", null);
    /** Column name RRStartDate */
    public static final String COLUMNNAME_RRStartDate = "RRStartDate";

	/**
	 * Set Laufzeit.
	 * Number of recurring runs
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRunsMax (int RunsMax);

	/**
	 * Get Laufzeit.
	 * Number of recurring runs
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRunsMax();

    /** Column definition for RunsMax */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_RunsMax = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "RunsMax", null);
    /** Column name RunsMax */
    public static final String COLUMNNAME_RunsMax = "RunsMax";

	/**
	 * Set Ressourcenzuordnung.
	 * Resource Assignment
	 *
	 * <br>Type: Assignment
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_ResourceAssignment_ID (int S_ResourceAssignment_ID);

	/**
	 * Get Ressourcenzuordnung.
	 * Resource Assignment
	 *
	 * <br>Type: Assignment
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_ResourceAssignment_ID();

    /** Column definition for S_ResourceAssignment_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_S_ResourceAssignment_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "S_ResourceAssignment_ID", null);
    /** Column name S_ResourceAssignment_ID */
    public static final String COLUMNNAME_S_ResourceAssignment_ID = "S_ResourceAssignment_ID";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_OrderLine, Object>(I_C_OrderLine.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_AD_User>(I_C_OrderLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser1_ID (int User1_ID);

	/**
	 * Get Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser1_ID();

	public org.compiere.model.I_C_ElementValue getUser1();

	public void setUser1(org.compiere.model.I_C_ElementValue User1);

    /** Column definition for User1_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ElementValue>(I_C_OrderLine.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User1_ID */
    public static final String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser2_ID (int User2_ID);

	/**
	 * Get Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser2_ID();

	public org.compiere.model.I_C_ElementValue getUser2();

	public void setUser2(org.compiere.model.I_C_ElementValue User2);

    /** Column definition for User2_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ElementValue>(I_C_OrderLine.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";
}
