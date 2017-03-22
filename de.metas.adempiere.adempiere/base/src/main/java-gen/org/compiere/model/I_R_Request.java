package org.compiere.model;

/**
 * Generated Interface for R_Request
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_R_Request
{

	/** TableName=R_Request */
	public static final String Table_Name = "R_Request";

	/** AD_Table_ID=417 */
	// public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

	// org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

	/**
	 * AccessLevel = 7 - System - Client - Org
	 */
	// java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

	/** Load Meta Data */

	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setA_Asset_ID(int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getA_Asset_ID();

	public org.compiere.model.I_A_Asset getA_Asset();

	public void setA_Asset(org.compiere.model.I_A_Asset A_Asset);

	/** Column definition for A_Asset_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_A_Asset> COLUMN_A_Asset_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_A_Asset>(I_R_Request.class, "A_Asset_ID", org.compiere.model.I_A_Asset.class);
	/** Column name A_Asset_ID */
	public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

	/** Column definition for AD_Client_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_Client>(I_R_Request.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
	/** Column name AD_Client_ID */
	public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/** Column definition for AD_Org_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_Org>(I_R_Request.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
	/** Column name AD_Org_ID */
	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rolle.
	 * Responsibility Role
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setAD_Role_ID(int AD_Role_ID);

	/**
	 * Get Rolle.
	 * Responsibility Role
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Role_ID();

	public org.compiere.model.I_AD_Role getAD_Role();

	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

	/** Column definition for AD_Role_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_Role>(I_R_Request.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
	/** Column name AD_Role_ID */
	public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setAD_Table_ID(int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

	/** Column definition for AD_Table_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_Table>(I_R_Request.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
	/** Column name AD_Table_ID */
	public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setAD_User_ID(int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

	/** Column definition for AD_User_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_User>(I_R_Request.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
	/** Column name AD_User_ID */
	public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_Activity_ID(int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity();

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

	/** Column definition for C_Activity_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Activity>(I_R_Request.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
	/** Column name C_Activity_ID */
	public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_BPartner_ID(int C_BPartner_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

	/** Column definition for C_BPartner_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_BPartner>(I_R_Request.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
	/** Column name C_BPartner_ID */
	public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_Campaign_ID(int C_Campaign_ID);

	/**
	 * Get Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign();

	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

	/** Column definition for C_Campaign_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Campaign>(I_R_Request.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	/** Column name C_Campaign_ID */
	public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_Invoice_ID(int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

	/** Column definition for C_Invoice_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Invoice>(I_R_Request.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	/** Column name C_Invoice_ID */
	public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Request Invoice.
	 * The generated invoice for this request
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_InvoiceRequest_ID(int C_InvoiceRequest_ID);

	/**
	 * Get Request Invoice.
	 * The generated invoice for this request
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_InvoiceRequest_ID();

	public org.compiere.model.I_C_Invoice getC_InvoiceRequest();

	public void setC_InvoiceRequest(org.compiere.model.I_C_Invoice C_InvoiceRequest);

	/** Column definition for C_InvoiceRequest_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Invoice> COLUMN_C_InvoiceRequest_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Invoice>(I_R_Request.class, "C_InvoiceRequest_ID", org.compiere.model.I_C_Invoice.class);
	/** Column name C_InvoiceRequest_ID */
	public static final String COLUMNNAME_C_InvoiceRequest_ID = "C_InvoiceRequest_ID";

	/**
	 * Set Close Date.
	 * Close Date
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setCloseDate(java.sql.Timestamp CloseDate);

	/**
	 * Get Close Date.
	 * Close Date
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getCloseDate();

	/** Column definition for CloseDate */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_CloseDate = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "CloseDate", null);
	/** Column name CloseDate */
	public static final String COLUMNNAME_CloseDate = "CloseDate";

	/**
	 * Set Confidentiality.
	 * Type of Confidentiality
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setConfidentialType(java.lang.String ConfidentialType);

	/**
	 * Get Confidentiality.
	 * Type of Confidentiality
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getConfidentialType();

	/** Column definition for ConfidentialType */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_ConfidentialType = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "ConfidentialType", null);
	/** Column name ConfidentialType */
	public static final String COLUMNNAME_ConfidentialType = "ConfidentialType";

	/**
	 * Set Entry Confidentiality.
	 * Confidentiality of the individual entry
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setConfidentialTypeEntry(java.lang.String ConfidentialTypeEntry);

	/**
	 * Get Entry Confidentiality.
	 * Confidentiality of the individual entry
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getConfidentialTypeEntry();

	/** Column definition for ConfidentialTypeEntry */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_ConfidentialTypeEntry = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "ConfidentialTypeEntry", null);
	/** Column name ConfidentialTypeEntry */
	public static final String COLUMNNAME_ConfidentialTypeEntry = "ConfidentialTypeEntry";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_Order_ID(int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

	/** Column definition for C_Order_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Order>(I_R_Request.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	/** Column name C_Order_ID */
	public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Zahlung.
	 * Payment identifier
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_Payment_ID(int C_Payment_ID);

	/**
	 * Get Zahlung.
	 * Payment identifier
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment();

	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment);

	/** Column definition for C_Payment_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Payment> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Payment>(I_R_Request.class, "C_Payment_ID", org.compiere.model.I_C_Payment.class);
	/** Column name C_Payment_ID */
	public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_Project_ID(int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project();

	public void setC_Project(org.compiere.model.I_C_Project C_Project);

	/** Column definition for C_Project_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_Project>(I_R_Request.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
	/** Column name C_Project_ID */
	public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

	/** Column definition for Created */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "Created", null);
	/** Column name Created */
	public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getCreatedBy();

	/** Column definition for CreatedBy */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_User>(I_R_Request.class, "CreatedBy", org.compiere.model.I_AD_User.class);
	/** Column name CreatedBy */
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Complete Plan.
	 * Planned Completion Date
	 *
	 * <br>
	 * Type: Date
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setDateCompletePlan(java.sql.Timestamp DateCompletePlan);

	/**
	 * Get Complete Plan.
	 * Planned Completion Date
	 *
	 * <br>
	 * Type: Date
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getDateCompletePlan();

	/** Column definition for DateCompletePlan */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_DateCompletePlan = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "DateCompletePlan", null);
	/** Column name DateCompletePlan */
	public static final String COLUMNNAME_DateCompletePlan = "DateCompletePlan";

	/**
	 * Set Date last action.
	 * Date this request was last acted on
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setDateLastAction(java.sql.Timestamp DateLastAction);

	/**
	 * Get Date last action.
	 * Date this request was last acted on
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getDateLastAction();

	/** Column definition for DateLastAction */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_DateLastAction = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "DateLastAction", null);
	/** Column name DateLastAction */
	public static final String COLUMNNAME_DateLastAction = "DateLastAction";

	/**
	 * Set Last Alert.
	 * Date when last alert were sent
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setDateLastAlert(java.sql.Timestamp DateLastAlert);

	/**
	 * Get Last Alert.
	 * Date when last alert were sent
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getDateLastAlert();

	/** Column definition for DateLastAlert */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_DateLastAlert = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "DateLastAlert", null);
	/** Column name DateLastAlert */
	public static final String COLUMNNAME_DateLastAlert = "DateLastAlert";

	/**
	 * Set Date next action.
	 * Date that this request should be acted on
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setDateNextAction(java.sql.Timestamp DateNextAction);

	/**
	 * Get Date next action.
	 * Date that this request should be acted on
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getDateNextAction();

	/** Column definition for DateNextAction */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_DateNextAction = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "DateNextAction", null);
	/** Column name DateNextAction */
	public static final String COLUMNNAME_DateNextAction = "DateNextAction";

	/**
	 * Set Start Plan.
	 * Planned Start Date
	 *
	 * <br>
	 * Type: Date
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setDateStartPlan(java.sql.Timestamp DateStartPlan);

	/**
	 * Get Start Plan.
	 * Planned Start Date
	 *
	 * <br>
	 * Type: Date
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getDateStartPlan();

	/** Column definition for DateStartPlan */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_DateStartPlan = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "DateStartPlan", null);
	/** Column name DateStartPlan */
	public static final String COLUMNNAME_DateStartPlan = "DateStartPlan";

	/**
	 * Set Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setDocumentNo(java.lang.String DocumentNo);

	/**
	 * Get Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

	/** Column definition for DocumentNo */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "DocumentNo", null);
	/** Column name DocumentNo */
	public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Due type.
	 * Status of the next action for this Request
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setDueType(java.lang.String DueType);

	/**
	 * Get Due type.
	 * Status of the next action for this Request
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getDueType();

	/** Column definition for DueType */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_DueType = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "DueType", null);
	/** Column name DueType */
	public static final String COLUMNNAME_DueType = "DueType";

	/**
	 * Set End Time.
	 * End of the time span
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setEndTime(java.sql.Timestamp EndTime);

	/**
	 * Get End Time.
	 * End of the time span
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getEndTime();

	/** Column definition for EndTime */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_EndTime = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "EndTime", null);
	/** Column name EndTime */
	public static final String COLUMNNAME_EndTime = "EndTime";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsActive(boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isActive();

	/** Column definition for IsActive */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "IsActive", null);
	/** Column name IsActive */
	public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Escalated.
	 * This request has been escalated
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsEscalated(boolean IsEscalated);

	/**
	 * Get Escalated.
	 * This request has been escalated
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isEscalated();

	/** Column definition for IsEscalated */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_IsEscalated = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "IsEscalated", null);
	/** Column name IsEscalated */
	public static final String COLUMNNAME_IsEscalated = "IsEscalated";

	/**
	 * Set Invoiced.
	 * Is this invoiced?
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsInvoiced(boolean IsInvoiced);

	/**
	 * Get Invoiced.
	 * Is this invoiced?
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isInvoiced();

	/** Column definition for IsInvoiced */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_IsInvoiced = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "IsInvoiced", null);
	/** Column name IsInvoiced */
	public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/**
	 * Set Self-Service.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsSelfService(boolean IsSelfService);

	/**
	 * Get Self-Service.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isSelfService();

	/** Column definition for IsSelfService */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "IsSelfService", null);
	/** Column name IsSelfService */
	public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Last Result.
	 * Result of last contact
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setLastResult(java.lang.String LastResult);

	/**
	 * Get Last Result.
	 * Result of last contact
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getLastResult();

	/** Column definition for LastResult */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_LastResult = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "LastResult", null);
	/** Column name LastResult */
	public static final String COLUMNNAME_LastResult = "LastResult";

	/**
	 * Set Change Request.
	 * BOM (Engineering) Change Request
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setM_ChangeRequest_ID(int M_ChangeRequest_ID);

	/**
	 * Get Change Request.
	 * BOM (Engineering) Change Request
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getM_ChangeRequest_ID();

	public org.compiere.model.I_M_ChangeRequest getM_ChangeRequest();

	public void setM_ChangeRequest(org.compiere.model.I_M_ChangeRequest M_ChangeRequest);

	/** Column definition for M_ChangeRequest_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_ChangeRequest> COLUMN_M_ChangeRequest_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_ChangeRequest>(I_R_Request.class, "M_ChangeRequest_ID", org.compiere.model.I_M_ChangeRequest.class);
	/** Column name M_ChangeRequest_ID */
	public static final String COLUMNNAME_M_ChangeRequest_ID = "M_ChangeRequest_ID";

	/**
	 * Set Fixed in.
	 * Fixed in Change Notice
	 *
	 * <br>
	 * Type: Table
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setM_FixChangeNotice_ID(int M_FixChangeNotice_ID);

	/**
	 * Get Fixed in.
	 * Fixed in Change Notice
	 *
	 * <br>
	 * Type: Table
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getM_FixChangeNotice_ID();

	public org.compiere.model.I_M_ChangeNotice getM_FixChangeNotice();

	public void setM_FixChangeNotice(org.compiere.model.I_M_ChangeNotice M_FixChangeNotice);

	/** Column definition for M_FixChangeNotice_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_ChangeNotice> COLUMN_M_FixChangeNotice_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_ChangeNotice>(I_R_Request.class, "M_FixChangeNotice_ID", org.compiere.model.I_M_ChangeNotice.class);
	/** Column name M_FixChangeNotice_ID */
	public static final String COLUMNNAME_M_FixChangeNotice_ID = "M_FixChangeNotice_ID";

	/**
	 * Set Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setM_InOut_ID(int M_InOut_ID);

	/**
	 * Get Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

	/** Column definition for M_InOut_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_InOut>(I_R_Request.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	/** Column name M_InOut_ID */
	public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setM_Product_ID(int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

	/** Column definition for M_Product_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_Product>(I_R_Request.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
	/** Column name M_Product_ID */
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Product Used.
	 * Product/Resource/Service used in Request
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setM_ProductSpent_ID(int M_ProductSpent_ID);

	/**
	 * Get Product Used.
	 * Product/Resource/Service used in Request
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getM_ProductSpent_ID();

	public org.compiere.model.I_M_Product getM_ProductSpent();

	public void setM_ProductSpent(org.compiere.model.I_M_Product M_ProductSpent);

	/** Column definition for M_ProductSpent_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_Product> COLUMN_M_ProductSpent_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_Product>(I_R_Request.class, "M_ProductSpent_ID", org.compiere.model.I_M_Product.class);
	/** Column name M_ProductSpent_ID */
	public static final String COLUMNNAME_M_ProductSpent_ID = "M_ProductSpent_ID";

	/**
	 * Set RMA.
	 * Return Material Authorization
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setM_RMA_ID(int M_RMA_ID);

	/**
	 * Get RMA.
	 * Return Material Authorization
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getM_RMA_ID();

	public org.compiere.model.I_M_RMA getM_RMA();

	public void setM_RMA(org.compiere.model.I_M_RMA M_RMA);

	/** Column definition for M_RMA_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_RMA> COLUMN_M_RMA_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_M_RMA>(I_R_Request.class, "M_RMA_ID", org.compiere.model.I_M_RMA.class);
	/** Column name M_RMA_ID */
	public static final String COLUMNNAME_M_RMA_ID = "M_RMA_ID";

	/**
	 * Set Next action.
	 * Next Action to be taken
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setNextAction(java.lang.String NextAction);

	/**
	 * Get Next action.
	 * Next Action to be taken
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getNextAction();

	/** Column definition for NextAction */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_NextAction = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "NextAction", null);
	/** Column name NextAction */
	public static final String COLUMNNAME_NextAction = "NextAction";

	/**
	 * Set PerformanceType.
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setPerformanceType(java.lang.String PerformanceType);

	/**
	 * Get PerformanceType.
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getPerformanceType();

	/** Column definition for PerformanceType */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_PerformanceType = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "PerformanceType", null);
	/** Column name PerformanceType */
	public static final String COLUMNNAME_PerformanceType = "PerformanceType";

	/**
	 * Set Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setPriority(java.lang.String Priority);

	/**
	 * Get Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getPriority();

	/** Column definition for Priority */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_Priority = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "Priority", null);
	/** Column name Priority */
	public static final String COLUMNNAME_Priority = "Priority";

	/**
	 * Set User Importance.
	 * Priority of the issue for the User
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setPriorityUser(java.lang.String PriorityUser);

	/**
	 * Get User Importance.
	 * Priority of the issue for the User
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getPriorityUser();

	/** Column definition for PriorityUser */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_PriorityUser = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "PriorityUser", null);
	/** Column name PriorityUser */
	public static final String COLUMNNAME_PriorityUser = "PriorityUser";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setProcessed(boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isProcessed();

	/** Column definition for Processed */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "Processed", null);
	/** Column name Processed */
	public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Berechn. Menge.
	 * Menge, die bereits in Rechnung gestellt wurde
	 *
	 * <br>
	 * Type: Quantity
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setQtyInvoiced(java.math.BigDecimal QtyInvoiced);

	/**
	 * Get Berechn. Menge.
	 * Menge, die bereits in Rechnung gestellt wurde
	 *
	 * <br>
	 * Type: Quantity
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInvoiced();

	/** Column definition for QtyInvoiced */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_QtyInvoiced = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "QtyInvoiced", null);
	/** Column name QtyInvoiced */
	public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Quantity Plan.
	 * Planned Quantity
	 *
	 * <br>
	 * Type: Quantity
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setQtyPlan(java.math.BigDecimal QtyPlan);

	/**
	 * Get Quantity Plan.
	 * Planned Quantity
	 *
	 * <br>
	 * Type: Quantity
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPlan();

	/** Column definition for QtyPlan */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_QtyPlan = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "QtyPlan", null);
	/** Column name QtyPlan */
	public static final String COLUMNNAME_QtyPlan = "QtyPlan";

	/**
	 * Set Quantity Used.
	 * Quantity used for this event
	 *
	 * <br>
	 * Type: Quantity
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setQtySpent(java.math.BigDecimal QtySpent);

	/**
	 * Get Quantity Used.
	 * Quantity used for this event
	 *
	 * <br>
	 * Type: Quantity
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.math.BigDecimal getQtySpent();

	/** Column definition for QtySpent */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_QtySpent = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "QtySpent", null);
	/** Column name QtySpent */
	public static final String COLUMNNAME_QtySpent = "QtySpent";

	/**
	 * Set Category.
	 * Request Category
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_Category_ID(int R_Category_ID);

	/**
	 * Get Category.
	 * Request Category
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_Category_ID();

	public org.compiere.model.I_R_Category getR_Category();

	public void setR_Category(org.compiere.model.I_R_Category R_Category);

	/** Column definition for R_Category_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Category> COLUMN_R_Category_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Category>(I_R_Request.class, "R_Category_ID", org.compiere.model.I_R_Category.class);
	/** Column name R_Category_ID */
	public static final String COLUMNNAME_R_Category_ID = "R_Category_ID";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>
	 * Type: Button
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setRecord_ID(int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>
	 * Type: Button
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getRecord_ID();

	/** Column definition for Record_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "Record_ID", null);
	/** Column name Record_ID */
	public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Request Amount.
	 * Amount associated with this request
	 *
	 * <br>
	 * Type: Amount
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setRequestAmt(java.math.BigDecimal RequestAmt);

	/**
	 * Get Request Amount.
	 * Amount associated with this request
	 *
	 * <br>
	 * Type: Amount
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.math.BigDecimal getRequestAmt();

	/** Column definition for RequestAmt */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_RequestAmt = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "RequestAmt", null);
	/** Column name RequestAmt */
	public static final String COLUMNNAME_RequestAmt = "RequestAmt";

	/**
	 * Set Request_includedTab.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setRequest_includedTab(java.lang.String Request_includedTab);

	/**
	 * Get Request_includedTab.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getRequest_includedTab();

	/** Column definition for Request_includedTab */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_Request_includedTab = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "Request_includedTab", null);
	/** Column name Request_includedTab */
	public static final String COLUMNNAME_Request_includedTab = "Request_includedTab";

	/**
	 * Set Ergebnis.
	 * Result of the action taken
	 *
	 * <br>
	 * Type: Text
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setResult(java.lang.String Result);

	/**
	 * Get Ergebnis.
	 * Result of the action taken
	 *
	 * <br>
	 * Type: Text
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getResult();

	/** Column definition for Result */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_Result = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "Result", null);
	/** Column name Result */
	public static final String COLUMNNAME_Result = "Result";

	/**
	 * Set Group.
	 * Request Group
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_Group_ID(int R_Group_ID);

	/**
	 * Get Group.
	 * Request Group
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_Group_ID();

	public org.compiere.model.I_R_Group getR_Group();

	public void setR_Group(org.compiere.model.I_R_Group R_Group);

	/** Column definition for R_Group_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Group> COLUMN_R_Group_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Group>(I_R_Request.class, "R_Group_ID", org.compiere.model.I_R_Group.class);
	/** Column name R_Group_ID */
	public static final String COLUMNNAME_R_Group_ID = "R_Group_ID";

	/**
	 * Set Mail Template.
	 * Text templates for mailings
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_MailText_ID(int R_MailText_ID);

	/**
	 * Get Mail Template.
	 * Text templates for mailings
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_MailText_ID();

	public org.compiere.model.I_R_MailText getR_MailText();

	public void setR_MailText(org.compiere.model.I_R_MailText R_MailText);

	/** Column definition for R_MailText_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_MailText> COLUMN_R_MailText_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_MailText>(I_R_Request.class, "R_MailText_ID", org.compiere.model.I_R_MailText.class);
	/** Column name R_MailText_ID */
	public static final String COLUMNNAME_R_MailText_ID = "R_MailText_ID";

	/**
	 * Set Request.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>
	 * Type: ID
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_Request_ID(int R_Request_ID);

	/**
	 * Get Request.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>
	 * Type: ID
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_Request_ID();

	/** Column definition for R_Request_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_R_Request_ID = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "R_Request_ID", null);
	/** Column name R_Request_ID */
	public static final String COLUMNNAME_R_Request_ID = "R_Request_ID";

	/**
	 * Set Related Request.
	 * Related Request (Master Issue, ..)
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_RequestRelated_ID(int R_RequestRelated_ID);

	/**
	 * Get Related Request.
	 * Related Request (Master Issue, ..)
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_RequestRelated_ID();

	public org.compiere.model.I_R_Request getR_RequestRelated();

	public void setR_RequestRelated(org.compiere.model.I_R_Request R_RequestRelated);

	/** Column definition for R_RequestRelated_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Request> COLUMN_R_RequestRelated_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Request>(I_R_Request.class, "R_RequestRelated_ID", org.compiere.model.I_R_Request.class);
	/** Column name R_RequestRelated_ID */
	public static final String COLUMNNAME_R_RequestRelated_ID = "R_RequestRelated_ID";

	/**
	 * Set Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_RequestType_ID(int R_RequestType_ID);

	/**
	 * Get Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_RequestType_ID();

	public org.compiere.model.I_R_RequestType getR_RequestType();

	public void setR_RequestType(org.compiere.model.I_R_RequestType R_RequestType);

	/** Column definition for R_RequestType_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_RequestType> COLUMN_R_RequestType_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_RequestType>(I_R_Request.class, "R_RequestType_ID", org.compiere.model.I_R_RequestType.class);
	/** Column name R_RequestType_ID */
	public static final String COLUMNNAME_R_RequestType_ID = "R_RequestType_ID";

	/**
	 * Set Request Type Interner Name.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_RequestType_InternalName(java.lang.String R_RequestType_InternalName);

	/**
	 * Get Request Type Interner Name.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getR_RequestType_InternalName();

	/** Column definition for R_RequestType_InternalName */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_R_RequestType_InternalName = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "R_RequestType_InternalName", null);
	/** Column name R_RequestType_InternalName */
	public static final String COLUMNNAME_R_RequestType_InternalName = "R_RequestType_InternalName";

	/**
	 * Set Resolution.
	 * Request Resolution
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_Resolution_ID(int R_Resolution_ID);

	/**
	 * Get Resolution.
	 * Request Resolution
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_Resolution_ID();

	public org.compiere.model.I_R_Resolution getR_Resolution();

	public void setR_Resolution(org.compiere.model.I_R_Resolution R_Resolution);

	/** Column definition for R_Resolution_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Resolution> COLUMN_R_Resolution_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Resolution>(I_R_Request.class, "R_Resolution_ID", org.compiere.model.I_R_Resolution.class);
	/** Column name R_Resolution_ID */
	public static final String COLUMNNAME_R_Resolution_ID = "R_Resolution_ID";

	/**
	 * Set Standard Response.
	 * Request Standard Response
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_StandardResponse_ID(int R_StandardResponse_ID);

	/**
	 * Get Standard Response.
	 * Request Standard Response
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_StandardResponse_ID();

	public org.compiere.model.I_R_StandardResponse getR_StandardResponse();

	public void setR_StandardResponse(org.compiere.model.I_R_StandardResponse R_StandardResponse);

	/** Column definition for R_StandardResponse_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_StandardResponse> COLUMN_R_StandardResponse_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_StandardResponse>(I_R_Request.class, "R_StandardResponse_ID", org.compiere.model.I_R_StandardResponse.class);
	/** Column name R_StandardResponse_ID */
	public static final String COLUMNNAME_R_StandardResponse_ID = "R_StandardResponse_ID";

	/**
	 * Set Status.
	 * Request Status
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_Status_ID(int R_Status_ID);

	/**
	 * Get Status.
	 * Request Status
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_Status_ID();

	public org.compiere.model.I_R_Status getR_Status();

	public void setR_Status(org.compiere.model.I_R_Status R_Status);

	/** Column definition for R_Status_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Status> COLUMN_R_Status_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_R_Status>(I_R_Request.class, "R_Status_ID", org.compiere.model.I_R_Status.class);
	/** Column name R_Status_ID */
	public static final String COLUMNNAME_R_Status_ID = "R_Status_ID";

	/**
	 * Set Sales Representative.
	 * Sales Representative or Company Agent
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setSalesRep_ID(int SalesRep_ID);

	/**
	 * Get Sales Representative.
	 * Sales Representative or Company Agent
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep();

	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

	/** Column definition for SalesRep_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_User>(I_R_Request.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
	/** Column name SalesRep_ID */
	public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Anfangsdatum.
	 * First effective day (inclusive)
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setStartDate(java.sql.Timestamp StartDate);

	/**
	 * Get Anfangsdatum.
	 * First effective day (inclusive)
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getStartDate();

	/** Column definition for StartDate */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_StartDate = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "StartDate", null);
	/** Column name StartDate */
	public static final String COLUMNNAME_StartDate = "StartDate";

	/**
	 * Set Start Time.
	 * Time started
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setStartTime(java.sql.Timestamp StartTime);

	/**
	 * Get Start Time.
	 * Time started
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getStartTime();

	/** Column definition for StartTime */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_StartTime = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "StartTime", null);
	/** Column name StartTime */
	public static final String COLUMNNAME_StartTime = "StartTime";

	/**
	 * Set Summary.
	 * Textual summary of this request
	 *
	 * <br>
	 * Type: Text
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setSummary(java.lang.String Summary);

	/**
	 * Get Summary.
	 * Textual summary of this request
	 *
	 * <br>
	 * Type: Text
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getSummary();

	/** Column definition for Summary */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_Summary = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "Summary", null);
	/** Column name Summary */
	public static final String COLUMNNAME_Summary = "Summary";

	/**
	 * Set Task Status.
	 * Status of the Task
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setTaskStatus(java.lang.String TaskStatus);

	/**
	 * Get Task Status.
	 * Status of the Task
	 *
	 * <br>
	 * Type: List
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getTaskStatus();

	/** Column definition for TaskStatus */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_TaskStatus = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "TaskStatus", null);
	/** Column name TaskStatus */
	public static final String COLUMNNAME_TaskStatus = "TaskStatus";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

	/** Column definition for Updated */
	public static final org.adempiere.model.ModelColumn<I_R_Request, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_R_Request, Object>(I_R_Request.class, "Updated", null);
	/** Column name Updated */
	public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>
	 * Type: Table
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getUpdatedBy();

	/** Column definition for UpdatedBy */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_AD_User>(I_R_Request.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
	/** Column name UpdatedBy */
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Zulieferant.
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_BP_Vendor_ID(int C_BP_Vendor_ID);

	/**
	 * Get Zulieferant.
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_BP_Vendor_ID();

	public org.compiere.model.I_C_BPartner getC_BP_Vendor();

	public void setC_BP_Vendor(org.compiere.model.I_C_BPartner C_BP_Vendor);

	/** Column definition for C_BP_Vendor_ID */
	public static final org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_BPartner> COLUMN_C_BP_Vendor_ID = new org.adempiere.model.ModelColumn<I_R_Request, org.compiere.model.I_C_BPartner>(I_R_Request.class, "C_BP_Vendor_ID", org.compiere.model.I_C_BPartner.class);
	/** Column name C_BP_Vendor_ID */
	public static final String COLUMNNAME_C_BP_Vendor_ID = "C_BP_Vendor_ID";

}
