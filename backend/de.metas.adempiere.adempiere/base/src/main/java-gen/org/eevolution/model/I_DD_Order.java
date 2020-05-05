package org.eevolution.model;


/** Generated Interface for DD_Order
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DD_Order 
{

    /** TableName=DD_Order */
    public static final String Table_Name = "DD_Order";

    /** AD_Table_ID=53037 */
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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_Client>(I_DD_Order.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_Org>(I_DD_Order.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_Org>(I_DD_Order.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_User>(I_DD_Order.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Activity>(I_DD_Order.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_BPartner>(I_DD_Order.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_BPartner_Location>(I_DD_Order.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Campaign>(I_DD_Order.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge();

	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Charge>(I_DD_Order.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_DocType>(I_DD_Order.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Invoice>(I_DD_Order.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Order>(I_DD_Order.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_Project>(I_DD_Order.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setChargeAmt (java.math.BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getChargeAmt();

    /** Column definition for ChargeAmt */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_ChargeAmt = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "ChargeAmt", null);
    /** Column name ChargeAmt */
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Create Confirm.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreateConfirm (java.lang.String CreateConfirm);

	/**
	 * Get Create Confirm.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreateConfirm();

    /** Column definition for CreateConfirm */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_CreateConfirm = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "CreateConfirm", null);
    /** Column name CreateConfirm */
    public static final String COLUMNNAME_CreateConfirm = "CreateConfirm";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_User>(I_DD_Order.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Position(en) kopieren von.
	 * Process which will generate a new document lines based on an existing document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreateFrom (java.lang.String CreateFrom);

	/**
	 * Get Position(en) kopieren von.
	 * Process which will generate a new document lines based on an existing document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreateFrom();

    /** Column definition for CreateFrom */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_CreateFrom = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "CreateFrom", null);
    /** Column name CreateFrom */
    public static final String COLUMNNAME_CreateFrom = "CreateFrom";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Date printed.
	 * Date the document was printed.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatePrinted (java.sql.Timestamp DatePrinted);

	/**
	 * Get Date printed.
	 * Date the document was printed.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePrinted();

    /** Column definition for DatePrinted */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DatePrinted = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DatePrinted", null);
    /** Column name DatePrinted */
    public static final String COLUMNNAME_DatePrinted = "DatePrinted";

	/**
	 * Set Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Eingangsdatum.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateReceived (java.sql.Timestamp DateReceived);

	/**
	 * Get Eingangsdatum.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateReceived();

    /** Column definition for DateReceived */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DateReceived = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DateReceived", null);
    /** Column name DateReceived */
    public static final String COLUMNNAME_DateReceived = "DateReceived";

	/**
	 * Set Distribution Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDD_Order_ID (int DD_Order_ID);

	/**
	 * Get Distribution Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDD_Order_ID();

    /** Column definition for DD_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DD_Order_ID = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DD_Order_ID", null);
    /** Column name DD_Order_ID */
    public static final String COLUMNNAME_DD_Order_ID = "DD_Order_ID";

	/**
	 * Set Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule();

    /** Column definition for DeliveryRule */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DeliveryRule = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DeliveryRule", null);
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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFreightAmt (java.math.BigDecimal FreightAmt);

	/**
	 * Get Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFreightAmt();

    /** Column definition for FreightAmt */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_FreightAmt = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "FreightAmt", null);
    /** Column name FreightAmt */
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";

	/**
	 * Set Frachtkostenberechnung.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFreightCostRule (java.lang.String FreightCostRule);

	/**
	 * Get Frachtkostenberechnung.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFreightCostRule();

    /** Column definition for FreightCostRule */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_FreightCostRule = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "FreightCostRule", null);
    /** Column name FreightCostRule */
    public static final String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Generate To.
	 * Generate To
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGenerateTo (java.lang.String GenerateTo);

	/**
	 * Get Generate To.
	 * Generate To
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGenerateTo();

    /** Column definition for GenerateTo */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_GenerateTo = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "GenerateTo", null);
    /** Column name GenerateTo */
    public static final String COLUMNNAME_GenerateTo = "GenerateTo";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDelivered (boolean IsDelivered);

	/**
	 * Get Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDelivered();

    /** Column definition for IsDelivered */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_IsDelivered = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "IsDelivered", null);
    /** Column name IsDelivered */
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";

	/**
	 * Set Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDropShip();

    /** Column definition for IsDropShip */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_IsDropShip = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "IsDropShip", null);
    /** Column name IsDropShip */
    public static final String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set In Dispute.
	 * Document is in dispute
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInDispute (boolean IsInDispute);

	/**
	 * Get In Dispute.
	 * Document is in dispute
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInDispute();

    /** Column definition for IsInDispute */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_IsInDispute = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "IsInDispute", null);
    /** Column name IsInDispute */
    public static final String COLUMNNAME_IsInDispute = "IsInDispute";

	/**
	 * Set In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInTransit (boolean IsInTransit);

	/**
	 * Get In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInTransit();

    /** Column definition for IsInTransit */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_IsInTransit = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "IsInTransit", null);
    /** Column name IsInTransit */
    public static final String COLUMNNAME_IsInTransit = "IsInTransit";

	/**
	 * Set andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPrinted (boolean IsPrinted);

	/**
	 * Get andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPrinted();

    /** Column definition for IsPrinted */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_IsPrinted = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "IsPrinted", null);
    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSelected (boolean IsSelected);

	/**
	 * Get Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSelected();

    /** Column definition for IsSelected */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_IsSelected = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "IsSelected", null);
    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/**
	 * Set Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_M_Shipper>(I_DD_Order.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_M_Warehouse>(I_DD_Order.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set MRP Allow Cleanup.
	 * MRP is allowed to remove this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMRP_AllowCleanup (boolean MRP_AllowCleanup);

	/**
	 * Get MRP Allow Cleanup.
	 * MRP is allowed to remove this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMRP_AllowCleanup();

    /** Column definition for MRP_AllowCleanup */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_MRP_AllowCleanup = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "MRP_AllowCleanup", null);
    /** Column name MRP_AllowCleanup */
    public static final String COLUMNNAME_MRP_AllowCleanup = "MRP_AllowCleanup";

	/**
	 * Set MRP Generated Document.
	 * This document was generated by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMRP_Generated (boolean MRP_Generated);

	/**
	 * Get MRP Generated Document.
	 * This document was generated by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMRP_Generated();

    /** Column definition for MRP_Generated */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_MRP_Generated = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "MRP_Generated", null);
    /** Column name MRP_Generated */
    public static final String COLUMNNAME_MRP_Generated = "MRP_Generated";

	/**
	 * Set To be deleted (MRP).
	 * Indicates if this document is scheduled to be deleted by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMRP_ToDelete (boolean MRP_ToDelete);

	/**
	 * Get To be deleted (MRP).
	 * Indicates if this document is scheduled to be deleted by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMRP_ToDelete();

    /** Column definition for MRP_ToDelete */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_MRP_ToDelete = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "MRP_ToDelete", null);
    /** Column name MRP_ToDelete */
    public static final String COLUMNNAME_MRP_ToDelete = "MRP_ToDelete";

	/**
	 * Set Kommissionierdatum.
	 * Datum/Zeit der Kommissionierung für die Lieferung
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPickDate (java.sql.Timestamp PickDate);

	/**
	 * Get Kommissionierdatum.
	 * Datum/Zeit der Kommissionierung für die Lieferung
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPickDate();

    /** Column definition for PickDate */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_PickDate = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "PickDate", null);
    /** Column name PickDate */
    public static final String COLUMNNAME_PickDate = "PickDate";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPosted (boolean Posted);

	/**
	 * Get Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPosted();

    /** Column definition for Posted */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "Posted", null);
    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_S_Resource>(I_DD_Order.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
    /** Column name PP_Plant_ID */
    public static final String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

	/**
	 * Set Product Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Product_Planning_ID (int PP_Product_Planning_ID);

	/**
	 * Get Product Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Product_Planning_ID();

	public org.eevolution.model.I_PP_Product_Planning getPP_Product_Planning();

	public void setPP_Product_Planning(org.eevolution.model.I_PP_Product_Planning PP_Product_Planning);

    /** Column definition for PP_Product_Planning_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.eevolution.model.I_PP_Product_Planning> COLUMN_PP_Product_Planning_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.eevolution.model.I_PP_Product_Planning>(I_DD_Order.class, "PP_Product_Planning_ID", org.eevolution.model.I_PP_Product_Planning.class);
    /** Column name PP_Product_Planning_ID */
    public static final String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

	/**
	 * Set Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityRule();

    /** Column definition for PriorityRule */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_PriorityRule = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "PriorityRule", null);
    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Referenced Order.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRef_Order_ID (int Ref_Order_ID);

	/**
	 * Get Referenced Order.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRef_Order_ID();

	public org.eevolution.model.I_DD_Order getRef_Order();

	public void setRef_Order(org.eevolution.model.I_DD_Order Ref_Order);

    /** Column definition for Ref_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.eevolution.model.I_DD_Order> COLUMN_Ref_Order_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.eevolution.model.I_DD_Order>(I_DD_Order.class, "Ref_Order_ID", org.eevolution.model.I_DD_Order.class);
    /** Column name Ref_Order_ID */
    public static final String COLUMNNAME_Ref_Order_ID = "Ref_Order_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_User>(I_DD_Order.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_SendEMail = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "SendEMail", null);
    /** Column name SendEMail */
    public static final String COLUMNNAME_SendEMail = "SendEMail";

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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_AD_User>(I_DD_Order.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_ElementValue>(I_DD_Order.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_DD_Order, org.compiere.model.I_C_ElementValue>(I_DD_Order.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set Volumen.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVolume (java.math.BigDecimal Volume);

	/**
	 * Get Volumen.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getVolume();

    /** Column definition for Volume */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_Volume = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "Volume", null);
    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/**
	 * Set Gewicht.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWeight (java.math.BigDecimal Weight);

	/**
	 * Get Gewicht.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getWeight();

    /** Column definition for Weight */
    public static final org.adempiere.model.ModelColumn<I_DD_Order, Object> COLUMN_Weight = new org.adempiere.model.ModelColumn<I_DD_Order, Object>(I_DD_Order.class, "Weight", null);
    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";
}
