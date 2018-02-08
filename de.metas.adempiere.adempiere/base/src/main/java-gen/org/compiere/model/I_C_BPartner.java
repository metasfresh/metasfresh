package org.compiere.model;


/** Generated Interface for C_BPartner
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BPartner 
{

    /** TableName=C_BPartner */
    public static final String Table_Name = "C_BPartner";

    /** AD_Table_ID=291 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Akquisitionskosten.
	 * The cost of gaining the prospect as a customer
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAcqusitionCost (java.math.BigDecimal AcqusitionCost);

	/**
	 * Get Akquisitionskosten.
	 * The cost of gaining the prospect as a customer
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAcqusitionCost();

    /** Column definition for AcqusitionCost */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_AcqusitionCost = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "AcqusitionCost", null);
    /** Column name AcqusitionCost */
    public static final String COLUMNNAME_AcqusitionCost = "AcqusitionCost";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Client>(I_C_BPartner.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Org>(I_C_BPartner.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Linked Organization.
	 * The Business Partner is another Organization for explicit Inter-Org transactions
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgBP_ID (int AD_OrgBP_ID);

	/**
	 * Get Linked Organization.
	 * The Business Partner is another Organization for explicit Inter-Org transactions
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgBP_ID();

	public org.compiere.model.I_AD_Org getAD_OrgBP();

	public void setAD_OrgBP(org.compiere.model.I_AD_Org AD_OrgBP);

    /** Column definition for AD_OrgBP_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Org> COLUMN_AD_OrgBP_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Org>(I_C_BPartner.class, "AD_OrgBP_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgBP_ID */
    public static final String COLUMNNAME_AD_OrgBP_ID = "AD_OrgBP_ID";

	/**
	 * Set Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setAddress1 (java.lang.String Address1);

	/**
	 * Get Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getAddress1();

    /** Column definition for Address1 */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Address1 = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Address1", null);
    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Sammel-Lieferscheine erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllowConsolidateInOut (boolean AllowConsolidateInOut);

	/**
	 * Get Sammel-Lieferscheine erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowConsolidateInOut();

    /** Column definition for AllowConsolidateInOut */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_AllowConsolidateInOut = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "AllowConsolidateInOut", null);
    /** Column name AllowConsolidateInOut */
    public static final String COLUMNNAME_AllowConsolidateInOut = "AllowConsolidateInOut";

	/**
	 * Set Partner Parent.
	 * Business Partner Parent
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartner_Parent_ID (int BPartner_Parent_ID);

	/**
	 * Get Partner Parent.
	 * Business Partner Parent
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBPartner_Parent_ID();

    /** Column definition for BPartner_Parent_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_BPartner_Parent_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "BPartner_Parent_ID", null);
    /** Column name BPartner_Parent_ID */
    public static final String COLUMNNAME_BPartner_Parent_ID = "BPartner_Parent_ID";

	/**
	 * Set Geschäftspartnergruppe.
	 * Business Partner Group
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Geschäftspartnergruppe.
	 * Business Partner Group
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group();

	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group);

    /** Column definition for C_BP_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_BP_Group>(I_C_BPartner.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "C_BPartner_ID", null);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Dunning_ID (int C_Dunning_ID);

	/**
	 * Get Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Dunning_ID();

	public org.compiere.model.I_C_Dunning getC_Dunning();

	public void setC_Dunning(org.compiere.model.I_C_Dunning C_Dunning);

    /** Column definition for C_Dunning_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_Dunning> COLUMN_C_Dunning_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_Dunning>(I_C_BPartner.class, "C_Dunning_ID", org.compiere.model.I_C_Dunning.class);
    /** Column name C_Dunning_ID */
    public static final String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

	/**
	 * Set Anrede.
	 * Greeting to print on correspondence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Greeting_ID (int C_Greeting_ID);

	/**
	 * Get Anrede.
	 * Greeting to print on correspondence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Greeting_ID();

	public org.compiere.model.I_C_Greeting getC_Greeting();

	public void setC_Greeting(org.compiere.model.I_C_Greeting C_Greeting);

    /** Column definition for C_Greeting_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_Greeting> COLUMN_C_Greeting_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_Greeting>(I_C_BPartner.class, "C_Greeting_ID", org.compiere.model.I_C_Greeting.class);
    /** Column name C_Greeting_ID */
    public static final String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	/**
	 * Set Terminplan Rechnung.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID);

	/**
	 * Get Terminplan Rechnung.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceSchedule_ID();

	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule();

	public void setC_InvoiceSchedule(org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule);

    /** Column definition for C_InvoiceSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_InvoiceSchedule> COLUMN_C_InvoiceSchedule_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_InvoiceSchedule>(I_C_BPartner.class, "C_InvoiceSchedule_ID", org.compiere.model.I_C_InvoiceSchedule.class);
    /** Column name C_InvoiceSchedule_ID */
    public static final String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm();

	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column definition for C_PaymentTerm_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_PaymentTerm>(I_C_BPartner.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set Tax Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_TaxGroup_ID (int C_TaxGroup_ID);

	/**
	 * Get Tax Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_TaxGroup_ID();

	public org.eevolution.model.I_C_TaxGroup getC_TaxGroup();

	public void setC_TaxGroup(org.eevolution.model.I_C_TaxGroup C_TaxGroup);

    /** Column definition for C_TaxGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.eevolution.model.I_C_TaxGroup> COLUMN_C_TaxGroup_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.eevolution.model.I_C_TaxGroup>(I_C_BPartner.class, "C_TaxGroup_ID", org.eevolution.model.I_C_TaxGroup.class);
    /** Column name C_TaxGroup_ID */
    public static final String COLUMNNAME_C_TaxGroup_ID = "C_TaxGroup_ID";

	/**
	 * Set Ort.
	 * Name des Ortes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setCity (java.lang.String City);

	/**
	 * Get Ort.
	 * Name des Ortes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getCity();

    /** Column definition for City */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_City = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "City", null);
    /** Column name City */
    public static final String COLUMNNAME_City = "City";

	/**
	 * Set Firmenname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCompanyName (java.lang.String CompanyName);

	/**
	 * Get Firmenname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCompanyName();

    /** Column definition for CompanyName */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_CompanyName = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "CompanyName", null);
    /** Column name CompanyName */
    public static final String COLUMNNAME_CompanyName = "CompanyName";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_User>(I_C_BPartner.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Auftrag anlegen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreateSO (java.lang.String CreateSO);

	/**
	 * Get Auftrag anlegen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreateSO();

    /** Column definition for CreateSO */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_CreateSO = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "CreateSO", null);
    /** Column name CreateSO */
    public static final String COLUMNNAME_CreateSO = "CreateSO";

	/**
	 * Set Credit limit indicator %.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditLimitIndicator (java.lang.String CreditLimitIndicator);

	/**
	 * Get Credit limit indicator %.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditLimitIndicator();

    /** Column definition for CreditLimitIndicator */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_CreditLimitIndicator = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "CreditLimitIndicator", null);
    /** Column name CreditLimitIndicator */
    public static final String COLUMNNAME_CreditLimitIndicator = "CreditLimitIndicator";

	/**
	 * Set Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule();

    /** Column definition for DeliveryRule */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_DeliveryRule = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "DeliveryRule", null);
    /** Column name DeliveryRule */
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule();

    /** Column definition for DeliveryViaRule */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "DeliveryViaRule", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Kopien.
	 * Number of copies to be printed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentCopies (int DocumentCopies);

	/**
	 * Get Kopien.
	 * Number of copies to be printed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDocumentCopies();

    /** Column definition for DocumentCopies */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_DocumentCopies = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "DocumentCopies", null);
    /** Column name DocumentCopies */
    public static final String COLUMNNAME_DocumentCopies = "DocumentCopies";

	/**
	 * Set Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDunningGrace (java.sql.Timestamp DunningGrace);

	/**
	 * Get Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDunningGrace();

    /** Column definition for DunningGrace */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_DunningGrace = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "DunningGrace", null);
    /** Column name DunningGrace */
    public static final String COLUMNNAME_DunningGrace = "DunningGrace";

	/**
	 * Set D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDUNS (java.lang.String DUNS);

	/**
	 * Get D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDUNS();

    /** Column definition for DUNS */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_DUNS = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "DUNS", null);
    /** Column name DUNS */
    public static final String COLUMNNAME_DUNS = "DUNS";

	/**
	 * Set eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setEMail (java.lang.String EMail);

	/**
	 * Get eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getEMail();

    /** Column definition for EMail */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "EMail", null);
    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Vorname.
	 * Vorname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFirstname (java.lang.String Firstname);

	/**
	 * Get Vorname.
	 * Vorname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFirstname();

    /** Column definition for Firstname */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Firstname = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Firstname", null);
    /** Column name Firstname */
    public static final String COLUMNNAME_Firstname = "Firstname";

	/**
	 * Set Erster Verkauf.
	 * Date of First Sale
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFirstSale (java.sql.Timestamp FirstSale);

	/**
	 * Get Erster Verkauf.
	 * Date of First Sale
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getFirstSale();

    /** Column definition for FirstSale */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_FirstSale = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "FirstSale", null);
    /** Column name FirstSale */
    public static final String COLUMNNAME_FirstSale = "FirstSale";

	/**
	 * Set Fester Rabatt %.
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFlatDiscount (java.math.BigDecimal FlatDiscount);

	/**
	 * Get Fester Rabatt %.
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFlatDiscount();

    /** Column definition for FlatDiscount */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_FlatDiscount = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "FlatDiscount", null);
    /** Column name FlatDiscount */
    public static final String COLUMNNAME_FlatDiscount = "FlatDiscount";

	/**
	 * Set Frachtkostenberechnung.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFreightCostRule (java.lang.String FreightCostRule);

	/**
	 * Get Frachtkostenberechnung.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFreightCostRule();

    /** Column definition for FreightCostRule */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_FreightCostRule = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "FreightCostRule", null);
    /** Column name FreightCostRule */
    public static final String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Druckformat Rechnung.
	 * Print Format for printing Invoices
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoice_PrintFormat_ID (int Invoice_PrintFormat_ID);

	/**
	 * Get Druckformat Rechnung.
	 * Print Format for printing Invoices
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getInvoice_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getInvoice_PrintFormat();

	public void setInvoice_PrintFormat(org.compiere.model.I_AD_PrintFormat Invoice_PrintFormat);

    /** Column definition for Invoice_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_PrintFormat> COLUMN_Invoice_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_PrintFormat>(I_C_BPartner.class, "Invoice_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name Invoice_PrintFormat_ID */
    public static final String COLUMNNAME_Invoice_PrintFormat_ID = "Invoice_PrintFormat_ID";

	/**
	 * Set Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceRule (java.lang.String InvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceRule();

    /** Column definition for InvoiceRule */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_InvoiceRule = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "InvoiceRule", null);
    /** Column name InvoiceRule */
    public static final String COLUMNNAME_InvoiceRule = "InvoiceRule";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Firma.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsCompany (boolean IsCompany);

	/**
	 * Get Firma.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isCompany();

    /** Column definition for IsCompany */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsCompany = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsCompany", null);
    /** Column name IsCompany */
    public static final String COLUMNNAME_IsCompany = "IsCompany";

	/**
	 * Set Autom. Referenz.
	 * Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCreateDefaultPOReference (boolean IsCreateDefaultPOReference);

	/**
	 * Get Autom. Referenz.
	 * Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCreateDefaultPOReference();

    /** Column definition for IsCreateDefaultPOReference */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsCreateDefaultPOReference = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsCreateDefaultPOReference", null);
    /** Column name IsCreateDefaultPOReference */
    public static final String COLUMNNAME_IsCreateDefaultPOReference = "IsCreateDefaultPOReference";

	/**
	 * Set Kunde.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCustomer (boolean IsCustomer);

	/**
	 * Get Kunde.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCustomer();

    /** Column definition for IsCustomer */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsCustomer = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsCustomer", null);
    /** Column name IsCustomer */
    public static final String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDiscountPrinted (boolean IsDiscountPrinted);

	/**
	 * Get Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDiscountPrinted();

    /** Column definition for IsDiscountPrinted */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsDiscountPrinted = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsDiscountPrinted", null);
    /** Column name IsDiscountPrinted */
    public static final String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/**
	 * Set Mitarbeiter.
	 * Indicates if  this Business Partner is an employee
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEmployee (boolean IsEmployee);

	/**
	 * Get Mitarbeiter.
	 * Indicates if  this Business Partner is an employee
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEmployee();

    /** Column definition for IsEmployee */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsEmployee = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsEmployee", null);
    /** Column name IsEmployee */
    public static final String COLUMNNAME_IsEmployee = "IsEmployee";

	/**
	 * Set One time transaction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOneTime (boolean IsOneTime);

	/**
	 * Get One time transaction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOneTime();

    /** Column definition for IsOneTime */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsOneTime = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsOneTime", null);
    /** Column name IsOneTime */
    public static final String COLUMNNAME_IsOneTime = "IsOneTime";

	/**
	 * Set PO Tax exempt.
	 * Business partner is exempt from tax on purchases
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPOTaxExempt (boolean IsPOTaxExempt);

	/**
	 * Get PO Tax exempt.
	 * Business partner is exempt from tax on purchases
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPOTaxExempt();

    /** Column definition for IsPOTaxExempt */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsPOTaxExempt = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsPOTaxExempt", null);
    /** Column name IsPOTaxExempt */
    public static final String COLUMNNAME_IsPOTaxExempt = "IsPOTaxExempt";

	/**
	 * Set Aktiver Interessent/Kunde.
	 * Indicates this is a Prospect
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsProspect (boolean IsProspect);

	/**
	 * Get Aktiver Interessent/Kunde.
	 * Indicates this is a Prospect
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProspect();

    /** Column definition for IsProspect */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsProspect = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsProspect", null);
    /** Column name IsProspect */
    public static final String COLUMNNAME_IsProspect = "IsProspect";

	/**
	 * Set Vertriebsbeauftragter.
	 * Indicates if  the business partner is a sales representative or company agent
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSalesRep (boolean IsSalesRep);

	/**
	 * Get Vertriebsbeauftragter.
	 * Indicates if  the business partner is a sales representative or company agent
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSalesRep();

    /** Column definition for IsSalesRep */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsSalesRep = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsSalesRep", null);
    /** Column name IsSalesRep */
    public static final String COLUMNNAME_IsSalesRep = "IsSalesRep";

	/**
	 * Set SEPA Signed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSEPASigned (boolean IsSEPASigned);

	/**
	 * Get SEPA Signed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSEPASigned();

    /** Column definition for IsSEPASigned */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsSEPASigned = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsSEPASigned", null);
    /** Column name IsSEPASigned */
    public static final String COLUMNNAME_IsSEPASigned = "IsSEPASigned";

	/**
	 * Set Shipping Notification Email.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsShippingNotificationEmail (boolean IsShippingNotificationEmail);

	/**
	 * Get Shipping Notification Email.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isShippingNotificationEmail();

    /** Column definition for IsShippingNotificationEmail */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsShippingNotificationEmail = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsShippingNotificationEmail", null);
    /** Column name IsShippingNotificationEmail */
    public static final String COLUMNNAME_IsShippingNotificationEmail = "IsShippingNotificationEmail";

	/**
	 * Set Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSummary (boolean IsSummary);

	/**
	 * Get Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSummary();

    /** Column definition for IsSummary */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsSummary = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsSummary", null);
    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Steuerbefreit.
	 * Steuersatz steuerbefreit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsTaxExempt (boolean IsTaxExempt);

	/**
	 * Get Steuerbefreit.
	 * Steuersatz steuerbefreit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isTaxExempt();

    /** Column definition for IsTaxExempt */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsTaxExempt = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsTaxExempt", null);
    /** Column name IsTaxExempt */
    public static final String COLUMNNAME_IsTaxExempt = "IsTaxExempt";

	/**
	 * Set Lieferant.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsVendor (boolean IsVendor);

	/**
	 * Get Lieferant.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isVendor();

    /** Column definition for IsVendor */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_IsVendor = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "IsVendor", null);
    /** Column name IsVendor */
    public static final String COLUMNNAME_IsVendor = "IsVendor";

	/**
	 * Set Kundencockpit_includedTab1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setKundencockpit_includedTab1 (java.lang.String Kundencockpit_includedTab1);

	/**
	 * Get Kundencockpit_includedTab1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getKundencockpit_includedTab1();

    /** Column definition for Kundencockpit_includedTab1 */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Kundencockpit_includedTab1 = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Kundencockpit_includedTab1", null);
    /** Column name Kundencockpit_includedTab1 */
    public static final String COLUMNNAME_Kundencockpit_includedTab1 = "Kundencockpit_includedTab1";

	/**
	 * Set Kundencockpit_includedTab2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setKundencockpit_includedTab2 (java.lang.String Kundencockpit_includedTab2);

	/**
	 * Get Kundencockpit_includedTab2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getKundencockpit_includedTab2();

    /** Column definition for Kundencockpit_includedTab2 */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Kundencockpit_includedTab2 = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Kundencockpit_includedTab2", null);
    /** Column name Kundencockpit_includedTab2 */
    public static final String COLUMNNAME_Kundencockpit_includedTab2 = "Kundencockpit_includedTab2";

	/**
	 * Set Kundencockpit_includedTab3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setKundencockpit_includedTab3 (java.lang.String Kundencockpit_includedTab3);

	/**
	 * Get Kundencockpit_includedTab3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getKundencockpit_includedTab3();

    /** Column definition for Kundencockpit_includedTab3 */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Kundencockpit_includedTab3 = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Kundencockpit_includedTab3", null);
    /** Column name Kundencockpit_includedTab3 */
    public static final String COLUMNNAME_Kundencockpit_includedTab3 = "Kundencockpit_includedTab3";

	/**
	 * Set Nachname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastname (java.lang.String Lastname);

	/**
	 * Get Nachname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLastname();

    /** Column definition for Lastname */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Lastname = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Lastname", null);
    /** Column name Lastname */
    public static final String COLUMNNAME_Lastname = "Lastname";

	/**
	 * Set Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLogo_ID (int Logo_ID);

	/**
	 * Get Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLogo_ID();

	public org.compiere.model.I_AD_Image getLogo();

	public void setLogo(org.compiere.model.I_AD_Image Logo);

    /** Column definition for Logo_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Image> COLUMN_Logo_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Image>(I_C_BPartner.class, "Logo_ID", org.compiere.model.I_AD_Image.class);
    /** Column name Logo_ID */
    public static final String COLUMNNAME_Logo_ID = "Logo_ID";

	/**
	 * Set Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema();

	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

    /** Column definition for M_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_DiscountSchema>(I_C_BPartner.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Frachtkostenpauschale.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_FreightCost_ID (int M_FreightCost_ID);

	/**
	 * Get Frachtkostenpauschale.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_FreightCost_ID();

    /** Column definition for M_FreightCost_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_M_FreightCost_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "M_FreightCost_ID", null);
    /** Column name M_FreightCost_ID */
    public static final String COLUMNNAME_M_FreightCost_ID = "M_FreightCost_ID";

	/**
	 * Set Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList();

	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList);

    /** Column definition for M_PriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_PriceList> COLUMN_M_PriceList_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_PriceList>(I_C_BPartner.class, "M_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_PricingSystem>(I_C_BPartner.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_Warehouse>(I_C_BPartner.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMRP_Exclude (java.lang.String MRP_Exclude);

	/**
	 * Get MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMRP_Exclude();

    /** Column definition for MRP_Exclude */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_MRP_Exclude = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "MRP_Exclude", null);
    /** Column name MRP_Exclude */
    public static final String COLUMNNAME_MRP_Exclude = "MRP_Exclude";

	/**
	 * Set NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNAICS (java.lang.String NAICS);

	/**
	 * Get NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNAICS();

    /** Column definition for NAICS */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_NAICS = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "NAICS", null);
    /** Column name NAICS */
    public static final String COLUMNNAME_NAICS = "NAICS";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Name Zusatz.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName2 (java.lang.String Name2);

	/**
	 * Get Name Zusatz.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName2();

    /** Column definition for Name2 */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Name2 = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Name2", null);
    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Name3.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName3 (java.lang.String Name3);

	/**
	 * Get Name3.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName3();

    /** Column definition for Name3 */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Name3 = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Name3", null);
    /** Column name Name3 */
    public static final String COLUMNNAME_Name3 = "Name3";

	/**
	 * Set Anzahl Beschäftigte.
	 * Anzahl der Mitarbeiter
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNumberEmployees (int NumberEmployees);

	/**
	 * Get Anzahl Beschäftigte.
	 * Anzahl der Mitarbeiter
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getNumberEmployees();

    /** Column definition for NumberEmployees */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_NumberEmployees = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "NumberEmployees", null);
    /** Column name NumberEmployees */
    public static final String COLUMNNAME_NumberEmployees = "NumberEmployees";

	/**
	 * Set Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "PaymentRule", null);
    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Zahlungsweise.
	 * Purchase payment option
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPaymentRulePO (java.lang.String PaymentRulePO);

	/**
	 * Get Zahlungsweise.
	 * Purchase payment option
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRulePO();

    /** Column definition for PaymentRulePO */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_PaymentRulePO = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "PaymentRulePO", null);
    /** Column name PaymentRulePO */
    public static final String COLUMNNAME_PaymentRulePO = "PaymentRulePO";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_DeliveryViaRule (java.lang.String PO_DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPO_DeliveryViaRule();

    /** Column definition for PO_DeliveryViaRule */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_PO_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "PO_DeliveryViaRule", null);
    /** Column name PO_DeliveryViaRule */
    public static final String COLUMNNAME_PO_DeliveryViaRule = "PO_DeliveryViaRule";

	/**
	 * Set Einkauf Rabatt Schema.
	 * Schema to calculate the purchase trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_DiscountSchema_ID (int PO_DiscountSchema_ID);

	/**
	 * Get Einkauf Rabatt Schema.
	 * Schema to calculate the purchase trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema();

	public void setPO_DiscountSchema(org.compiere.model.I_M_DiscountSchema PO_DiscountSchema);

    /** Column definition for PO_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_DiscountSchema> COLUMN_PO_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_DiscountSchema>(I_C_BPartner.class, "PO_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
    /** Column name PO_DiscountSchema_ID */
    public static final String COLUMNNAME_PO_DiscountSchema_ID = "PO_DiscountSchema_ID";

	/**
	 * Set Zahlungskondition.
	 * Payment rules for a purchase order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID);

	/**
	 * Get Zahlungskondition.
	 * Payment rules for a purchase order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getPO_PaymentTerm();

	public void setPO_PaymentTerm(org.compiere.model.I_C_PaymentTerm PO_PaymentTerm);

    /** Column definition for PO_PaymentTerm_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_PaymentTerm> COLUMN_PO_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_PaymentTerm>(I_C_BPartner.class, "PO_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name PO_PaymentTerm_ID */
    public static final String COLUMNNAME_PO_PaymentTerm_ID = "PO_PaymentTerm_ID";

	/**
	 * Set Einkaufspreisliste.
	 * Price List used by this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_PriceList_ID (int PO_PriceList_ID);

	/**
	 * Get Einkaufspreisliste.
	 * Price List used by this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_PriceList_ID();

	public org.compiere.model.I_M_PriceList getPO_PriceList();

	public void setPO_PriceList(org.compiere.model.I_M_PriceList PO_PriceList);

    /** Column definition for PO_PriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_PriceList> COLUMN_PO_PriceList_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_PriceList>(I_C_BPartner.class, "PO_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name PO_PriceList_ID */
    public static final String COLUMNNAME_PO_PriceList_ID = "PO_PriceList_ID";

	/**
	 * Set Einkaufspreissystem.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_PricingSystem_ID (int PO_PricingSystem_ID);

	/**
	 * Get Einkaufspreissystem.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getPO_PricingSystem();

	public void setPO_PricingSystem(org.compiere.model.I_M_PricingSystem PO_PricingSystem);

    /** Column definition for PO_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_PricingSystem> COLUMN_PO_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_M_PricingSystem>(I_C_BPartner.class, "PO_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name PO_PricingSystem_ID */
    public static final String COLUMNNAME_PO_PricingSystem_ID = "PO_PricingSystem_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Referenz Vorgabe.
	 * Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPOReferencePattern (java.lang.String POReferencePattern);

	/**
	 * Get Referenz Vorgabe.
	 * Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPOReferencePattern();

    /** Column definition for POReferencePattern */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_POReferencePattern = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "POReferencePattern", null);
    /** Column name POReferencePattern */
    public static final String COLUMNNAME_POReferencePattern = "POReferencePattern";

	/**
	 * Set PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPostal (java.lang.String Postal);

	/**
	 * Get PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getPostal();

    /** Column definition for Postal */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Postal = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Postal", null);
    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/**
	 * Set Möglicher Gesamtertrag.
	 * Total Revenue expected
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPotentialLifeTimeValue (java.math.BigDecimal PotentialLifeTimeValue);

	/**
	 * Get Möglicher Gesamtertrag.
	 * Total Revenue expected
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPotentialLifeTimeValue();

    /** Column definition for PotentialLifeTimeValue */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_PotentialLifeTimeValue = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "PotentialLifeTimeValue", null);
    /** Column name PotentialLifeTimeValue */
    public static final String COLUMNNAME_PotentialLifeTimeValue = "PotentialLifeTimeValue";

	/**
	 * Set Rating.
	 * Classification or Importance
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRating (java.lang.String Rating);

	/**
	 * Get Rating.
	 * Classification or Importance
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRating();

    /** Column definition for Rating */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Rating = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Rating", null);
    /** Column name Rating */
    public static final String COLUMNNAME_Rating = "Rating";

	/**
	 * Set Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReferenceNo (java.lang.String ReferenceNo);

	/**
	 * Get Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReferenceNo();

    /** Column definition for ReferenceNo */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_ReferenceNo = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "ReferenceNo", null);
    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/**
	 * Set Wiedervorlage Datum Aussen.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReminderDateExtern (java.sql.Timestamp ReminderDateExtern);

	/**
	 * Get Wiedervorlage Datum Aussen.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getReminderDateExtern();

    /** Column definition for ReminderDateExtern */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_ReminderDateExtern = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "ReminderDateExtern", null);
    /** Column name ReminderDateExtern */
    public static final String COLUMNNAME_ReminderDateExtern = "ReminderDateExtern";

	/**
	 * Set Wiedervorlage Datum Innen.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReminderDateIntern (java.sql.Timestamp ReminderDateIntern);

	/**
	 * Get Wiedervorlage Datum Innen.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getReminderDateIntern();

    /** Column definition for ReminderDateIntern */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_ReminderDateIntern = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "ReminderDateIntern", null);
    /** Column name ReminderDateIntern */
    public static final String COLUMNNAME_ReminderDateIntern = "ReminderDateIntern";

	/**
	 * Set Aussendienst.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Aussendienst.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep();

	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column definition for SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_User>(I_C_BPartner.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Sales Responsible.
	 * Sales Responsible Internal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesRepIntern_ID (int SalesRepIntern_ID);

	/**
	 * Get Sales Responsible.
	 * Sales Responsible Internal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSalesRepIntern_ID();

	public org.compiere.model.I_AD_User getSalesRepIntern();

	public void setSalesRepIntern(org.compiere.model.I_AD_User SalesRepIntern);

    /** Column definition for SalesRepIntern_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_User> COLUMN_SalesRepIntern_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_User>(I_C_BPartner.class, "SalesRepIntern_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRepIntern_ID */
    public static final String COLUMNNAME_SalesRepIntern_ID = "SalesRepIntern_ID";

	/**
	 * Set Verkaufsvolumen in 1.000.
	 * Total Volume of Sales in Thousands of Currency
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesVolume (int SalesVolume);

	/**
	 * Get Verkaufsvolumen in 1.000.
	 * Total Volume of Sales in Thousands of Currency
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSalesVolume();

    /** Column definition for SalesVolume */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_SalesVolume = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "SalesVolume", null);
    /** Column name SalesVolume */
    public static final String COLUMNNAME_SalesVolume = "SalesVolume";

	/**
	 * Set E-Mail senden.
	 * Enable sending Document EMail
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSendEMail (boolean SendEMail);

	/**
	 * Get E-Mail senden.
	 * Enable sending Document EMail
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSendEMail();

    /** Column definition for SendEMail */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_SendEMail = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "SendEMail", null);
    /** Column name SendEMail */
    public static final String COLUMNNAME_SendEMail = "SendEMail";

	/**
	 * Set Anteil.
	 * Share of Customer's business as a percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShareOfCustomer (int ShareOfCustomer);

	/**
	 * Get Anteil.
	 * Share of Customer's business as a percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getShareOfCustomer();

    /** Column definition for ShareOfCustomer */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_ShareOfCustomer = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "ShareOfCustomer", null);
    /** Column name ShareOfCustomer */
    public static final String COLUMNNAME_ShareOfCustomer = "ShareOfCustomer";

	/**
	 * Set Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShelfLifeMinPct (int ShelfLifeMinPct);

	/**
	 * Get Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getShelfLifeMinPct();

    /** Column definition for ShelfLifeMinPct */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_ShelfLifeMinPct = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "ShelfLifeMinPct", null);
    /** Column name ShelfLifeMinPct */
    public static final String COLUMNNAME_ShelfLifeMinPct = "ShelfLifeMinPct";

	/**
	 * Set Short Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShortDescription (java.lang.String ShortDescription);

	/**
	 * Get Short Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getShortDescription();

    /** Column definition for ShortDescription */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_ShortDescription = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "ShortDescription", null);
    /** Column name ShortDescription */
    public static final String COLUMNNAME_ShortDescription = "ShortDescription";

	/**
	 * Set Beschreibung Auftrag.
	 * Description to be used on orders
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSO_Description (java.lang.String SO_Description);

	/**
	 * Get Beschreibung Auftrag.
	 * Description to be used on orders
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSO_Description();

    /** Column definition for SO_Description */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_SO_Description = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "SO_Description", null);
    /** Column name SO_Description */
    public static final String COLUMNNAME_SO_Description = "SO_Description";

	/**
	 * Set Zielbelegart.
	 * Zielbelegart für die Umwandlung von Dokumenten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSO_DocTypeTarget_ID (int SO_DocTypeTarget_ID);

	/**
	 * Get Zielbelegart.
	 * Zielbelegart für die Umwandlung von Dokumenten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSO_DocTypeTarget_ID();

	public org.compiere.model.I_C_DocType getSO_DocTypeTarget();

	public void setSO_DocTypeTarget(org.compiere.model.I_C_DocType SO_DocTypeTarget);

    /** Column definition for SO_DocTypeTarget_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_DocType> COLUMN_SO_DocTypeTarget_ID = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_C_DocType>(I_C_BPartner.class, "SO_DocTypeTarget_ID", org.compiere.model.I_C_DocType.class);
    /** Column name SO_DocTypeTarget_ID */
    public static final String COLUMNNAME_SO_DocTypeTarget_ID = "SO_DocTypeTarget_ID";

	/**
	 * Set Notiz Auftragsart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSO_TargetDocTypeReason (java.lang.String SO_TargetDocTypeReason);

	/**
	 * Get Notiz Auftragsart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSO_TargetDocTypeReason();

    /** Column definition for SO_TargetDocTypeReason */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_SO_TargetDocTypeReason = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "SO_TargetDocTypeReason", null);
    /** Column name SO_TargetDocTypeReason */
    public static final String COLUMNNAME_SO_TargetDocTypeReason = "SO_TargetDocTypeReason";

	/**
	 * Set Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTaxID (java.lang.String TaxID);

	/**
	 * Get Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTaxID();

    /** Column definition for TaxID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_TaxID = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "TaxID", null);
    /** Column name TaxID */
    public static final String COLUMNNAME_TaxID = "TaxID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner, org.compiere.model.I_AD_User>(I_C_BPartner.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setURL (java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getURL();

    /** Column definition for URL */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_URL = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "URL", null);
    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Umsatzsteuer-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVATaxID (java.lang.String VATaxID);

	/**
	 * Get Umsatzsteuer-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVATaxID();

    /** Column definition for VATaxID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner, Object> COLUMN_VATaxID = new org.adempiere.model.ModelColumn<I_C_BPartner, Object>(I_C_BPartner.class, "VATaxID", null);
    /** Column name VATaxID */
    public static final String COLUMNNAME_VATaxID = "VATaxID";
}
