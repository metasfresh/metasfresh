package org.compiere.model;


/** Generated Interface for AD_Role
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Role 
{

    /** TableName=AD_Role */
    public static final String Table_Name = "AD_Role";

    /** AD_Table_ID=156 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Client>(I_AD_Role.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Special Form.
	 * Special Form
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Form_ID (int AD_Form_ID);

	/**
	 * Get Special Form.
	 * Special Form
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Form_ID();

	public org.compiere.model.I_AD_Form getAD_Form();

	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form);

    /** Column definition for AD_Form_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Form>(I_AD_Role.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
    /** Column name AD_Form_ID */
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Org>(I_AD_Role.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Role_ID();

    /** Column definition for AD_Role_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_AD_Role_ID = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "AD_Role_ID", null);
    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Primärbaum Menü.
	 * Tree of the menu
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_Menu_ID (int AD_Tree_Menu_ID);

	/**
	 * Get Primärbaum Menü.
	 * Tree of the menu
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_Menu_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_Menu();

	public void setAD_Tree_Menu(org.compiere.model.I_AD_Tree AD_Tree_Menu);

    /** Column definition for AD_Tree_Menu_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Menu_ID = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Tree>(I_AD_Role.class, "AD_Tree_Menu_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_Menu_ID */
    public static final String COLUMNNAME_AD_Tree_Menu_ID = "AD_Tree_Menu_ID";

	/**
	 * Set Primärbaum Organisation.
	 * Tree to determine organizational hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_Org_ID (int AD_Tree_Org_ID);

	/**
	 * Get Primärbaum Organisation.
	 * Tree to determine organizational hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_Org_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_Org();

	public void setAD_Tree_Org(org.compiere.model.I_AD_Tree AD_Tree_Org);

    /** Column definition for AD_Tree_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Tree>(I_AD_Role.class, "AD_Tree_Org_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_Org_ID */
    public static final String COLUMNNAME_AD_Tree_Org_ID = "AD_Tree_Org_ID";

	/**
	 * Set Allow Info Account.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_Account (boolean Allow_Info_Account);

	/**
	 * Get Allow Info Account.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_Account();

    /** Column definition for Allow_Info_Account */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Account = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_Account", null);
    /** Column name Allow_Info_Account */
    public static final String COLUMNNAME_Allow_Info_Account = "Allow_Info_Account";

	/**
	 * Set Allow Info Asset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_Asset (boolean Allow_Info_Asset);

	/**
	 * Get Allow Info Asset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_Asset();

    /** Column definition for Allow_Info_Asset */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Asset = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_Asset", null);
    /** Column name Allow_Info_Asset */
    public static final String COLUMNNAME_Allow_Info_Asset = "Allow_Info_Asset";

	/**
	 * Set Allow Info BPartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_BPartner (boolean Allow_Info_BPartner);

	/**
	 * Get Allow Info BPartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_BPartner();

    /** Column definition for Allow_Info_BPartner */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_BPartner = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_BPartner", null);
    /** Column name Allow_Info_BPartner */
    public static final String COLUMNNAME_Allow_Info_BPartner = "Allow_Info_BPartner";

	/**
	 * Set Allow Info CashJournal.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_CashJournal (boolean Allow_Info_CashJournal);

	/**
	 * Get Allow Info CashJournal.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_CashJournal();

    /** Column definition for Allow_Info_CashJournal */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_CashJournal = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_CashJournal", null);
    /** Column name Allow_Info_CashJournal */
    public static final String COLUMNNAME_Allow_Info_CashJournal = "Allow_Info_CashJournal";

	/**
	 * Set Allow Info CRP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_CRP (boolean Allow_Info_CRP);

	/**
	 * Get Allow Info CRP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_CRP();

    /** Column definition for Allow_Info_CRP */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_CRP = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_CRP", null);
    /** Column name Allow_Info_CRP */
    public static final String COLUMNNAME_Allow_Info_CRP = "Allow_Info_CRP";

	/**
	 * Set Allow Info InOut.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_InOut (boolean Allow_Info_InOut);

	/**
	 * Get Allow Info InOut.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_InOut();

    /** Column definition for Allow_Info_InOut */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_InOut = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_InOut", null);
    /** Column name Allow_Info_InOut */
    public static final String COLUMNNAME_Allow_Info_InOut = "Allow_Info_InOut";

	/**
	 * Set Allow Info Invoice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_Invoice (boolean Allow_Info_Invoice);

	/**
	 * Get Allow Info Invoice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_Invoice();

    /** Column definition for Allow_Info_Invoice */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Invoice = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_Invoice", null);
    /** Column name Allow_Info_Invoice */
    public static final String COLUMNNAME_Allow_Info_Invoice = "Allow_Info_Invoice";

	/**
	 * Set Allow Info MRP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_MRP (boolean Allow_Info_MRP);

	/**
	 * Get Allow Info MRP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_MRP();

    /** Column definition for Allow_Info_MRP */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_MRP = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_MRP", null);
    /** Column name Allow_Info_MRP */
    public static final String COLUMNNAME_Allow_Info_MRP = "Allow_Info_MRP";

	/**
	 * Set Allow Info Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_Order (boolean Allow_Info_Order);

	/**
	 * Get Allow Info Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_Order();

    /** Column definition for Allow_Info_Order */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Order = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_Order", null);
    /** Column name Allow_Info_Order */
    public static final String COLUMNNAME_Allow_Info_Order = "Allow_Info_Order";

	/**
	 * Set Allow Info Payment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_Payment (boolean Allow_Info_Payment);

	/**
	 * Get Allow Info Payment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_Payment();

    /** Column definition for Allow_Info_Payment */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Payment = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_Payment", null);
    /** Column name Allow_Info_Payment */
    public static final String COLUMNNAME_Allow_Info_Payment = "Allow_Info_Payment";

	/**
	 * Set Allow Info Product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_Product (boolean Allow_Info_Product);

	/**
	 * Get Allow Info Product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_Product();

    /** Column definition for Allow_Info_Product */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Product = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_Product", null);
    /** Column name Allow_Info_Product */
    public static final String COLUMNNAME_Allow_Info_Product = "Allow_Info_Product";

	/**
	 * Set Allow Info Resource.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_Resource (boolean Allow_Info_Resource);

	/**
	 * Get Allow Info Resource.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_Resource();

    /** Column definition for Allow_Info_Resource */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Resource = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_Resource", null);
    /** Column name Allow_Info_Resource */
    public static final String COLUMNNAME_Allow_Info_Resource = "Allow_Info_Resource";

	/**
	 * Set Allow Info Schedule.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllow_Info_Schedule (boolean Allow_Info_Schedule);

	/**
	 * Get Allow Info Schedule.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllow_Info_Schedule();

    /** Column definition for Allow_Info_Schedule */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Schedule = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Allow_Info_Schedule", null);
    /** Column name Allow_Info_Schedule */
    public static final String COLUMNNAME_Allow_Info_Schedule = "Allow_Info_Schedule";

	/**
	 * Set Freigabe-Betrag.
	 * The approval amount limit for this role
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmtApproval (java.math.BigDecimal AmtApproval);

	/**
	 * Get Freigabe-Betrag.
	 * The approval amount limit for this role
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtApproval();

    /** Column definition for AmtApproval */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_AmtApproval = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "AmtApproval", null);
    /** Column name AmtApproval */
    public static final String COLUMNNAME_AmtApproval = "AmtApproval";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_C_Currency>(I_AD_Role.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Bestätigung Anzahl Suchergebnisse.
	 * Require Confirmation if more records will be returned by the query (If not defined 500)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setConfirmQueryRecords (int ConfirmQueryRecords);

	/**
	 * Get Bestätigung Anzahl Suchergebnisse.
	 * Require Confirmation if more records will be returned by the query (If not defined 500)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getConfirmQueryRecords();

    /** Column definition for ConfirmQueryRecords */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_ConfirmQueryRecords = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "ConfirmQueryRecords", null);
    /** Column name ConfirmQueryRecords */
    public static final String COLUMNNAME_ConfirmQueryRecords = "ConfirmQueryRecords";

	/**
	 * Set Verbindungsart.
	 * How a Java Client connects to the server(s)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConnectionProfile (java.lang.String ConnectionProfile);

	/**
	 * Get Verbindungsart.
	 * How a Java Client connects to the server(s)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConnectionProfile();

    /** Column definition for ConnectionProfile */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_ConnectionProfile = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "ConnectionProfile", null);
    /** Column name ConnectionProfile */
    public static final String COLUMNNAME_ConnectionProfile = "ConnectionProfile";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_User>(I_AD_Role.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Zugriff auf alle Organisationen.
	 * Access all Organizations (no org access control) of the client
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAccessAllOrgs (boolean IsAccessAllOrgs);

	/**
	 * Get Zugriff auf alle Organisationen.
	 * Access all Organizations (no org access control) of the client
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAccessAllOrgs();

    /** Column definition for IsAccessAllOrgs */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsAccessAllOrgs = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsAccessAllOrgs", null);
    /** Column name IsAccessAllOrgs */
    public static final String COLUMNNAME_IsAccessAllOrgs = "IsAccessAllOrgs";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allow chaning login date.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAllowLoginDateOverride (boolean IsAllowLoginDateOverride);

	/**
	 * Get Allow chaning login date.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowLoginDateOverride();

    /** Column definition for IsAllowLoginDateOverride */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsAllowLoginDateOverride = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsAllowLoginDateOverride", null);
    /** Column name IsAllowLoginDateOverride */
    public static final String COLUMNNAME_IsAllowLoginDateOverride = "IsAllowLoginDateOverride";

	/**
	 * Set IsAttachmentDeletionAllowed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAttachmentDeletionAllowed (boolean IsAttachmentDeletionAllowed);

	/**
	 * Get IsAttachmentDeletionAllowed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAttachmentDeletionAllowed();

    /** Column definition for IsAttachmentDeletionAllowed */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsAttachmentDeletionAllowed = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsAttachmentDeletionAllowed", null);
    /** Column name IsAttachmentDeletionAllowed */
    public static final String COLUMNNAME_IsAttachmentDeletionAllowed = "IsAttachmentDeletionAllowed";

	/**
	 * Set Skip role login page.
	 * Skip role login page and take the defaults
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAutoRoleLogin (boolean IsAutoRoleLogin);

	/**
	 * Get Skip role login page.
	 * Skip role login page and take the defaults
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutoRoleLogin();

    /** Column definition for IsAutoRoleLogin */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsAutoRoleLogin = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsAutoRoleLogin", null);
    /** Column name IsAutoRoleLogin */
    public static final String COLUMNNAME_IsAutoRoleLogin = "IsAutoRoleLogin";

	/**
	 * Set Freigabe eigener Belege.
	 * Users with this role can approve their own documents
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCanApproveOwnDoc (boolean IsCanApproveOwnDoc);

	/**
	 * Get Freigabe eigener Belege.
	 * Users with this role can approve their own documents
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCanApproveOwnDoc();

    /** Column definition for IsCanApproveOwnDoc */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsCanApproveOwnDoc = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsCanApproveOwnDoc", null);
    /** Column name IsCanApproveOwnDoc */
    public static final String COLUMNNAME_IsCanApproveOwnDoc = "IsCanApproveOwnDoc";

	/**
	 * Set Kann exportieren.
	 * Users with this role can export data
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCanExport (boolean IsCanExport);

	/**
	 * Get Kann exportieren.
	 * Users with this role can export data
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCanExport();

    /** Column definition for IsCanExport */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsCanExport = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsCanExport", null);
    /** Column name IsCanExport */
    public static final String COLUMNNAME_IsCanExport = "IsCanExport";

	/**
	 * Set Kann Berichte erstellen.
	 * Users with this role can create reports
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCanReport (boolean IsCanReport);

	/**
	 * Get Kann Berichte erstellen.
	 * Users with this role can create reports
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCanReport();

    /** Column definition for IsCanReport */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsCanReport = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsCanReport", null);
    /** Column name IsCanReport */
    public static final String COLUMNNAME_IsCanReport = "IsCanReport";

	/**
	 * Set Änderungen protokollieren.
	 * Maintain a log of changes
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsChangeLog (boolean IsChangeLog);

	/**
	 * Get Änderungen protokollieren.
	 * Maintain a log of changes
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isChangeLog();

    /** Column definition for IsChangeLog */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsChangeLog = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsChangeLog", null);
    /** Column name IsChangeLog */
    public static final String COLUMNNAME_IsChangeLog = "IsChangeLog";

	/**
	 * Set IsDiscountAllowedOnTotal.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDiscountAllowedOnTotal (boolean IsDiscountAllowedOnTotal);

	/**
	 * Get IsDiscountAllowedOnTotal.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDiscountAllowedOnTotal();

    /** Column definition for IsDiscountAllowedOnTotal */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsDiscountAllowedOnTotal = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsDiscountAllowedOnTotal", null);
    /** Column name IsDiscountAllowedOnTotal */
    public static final String COLUMNNAME_IsDiscountAllowedOnTotal = "IsDiscountAllowedOnTotal";

	/**
	 * Set IsDiscountUptoLimitPrice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDiscountUptoLimitPrice (boolean IsDiscountUptoLimitPrice);

	/**
	 * Get IsDiscountUptoLimitPrice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDiscountUptoLimitPrice();

    /** Column definition for IsDiscountUptoLimitPrice */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsDiscountUptoLimitPrice = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsDiscountUptoLimitPrice", null);
    /** Column name IsDiscountUptoLimitPrice */
    public static final String COLUMNNAME_IsDiscountUptoLimitPrice = "IsDiscountUptoLimitPrice";

	/**
	 * Set Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManual();

    /** Column definition for IsManual */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Menu available.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsMenuAvailable (boolean IsMenuAvailable);

	/**
	 * Get Menu available.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMenuAvailable();

    /** Column definition for IsMenuAvailable */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsMenuAvailable = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsMenuAvailable", null);
    /** Column name IsMenuAvailable */
    public static final String COLUMNNAME_IsMenuAvailable = "IsMenuAvailable";

	/**
	 * Set Zugriff auf gesperrte Einträge.
	 * Allow access to all personal records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPersonalAccess (boolean IsPersonalAccess);

	/**
	 * Get Zugriff auf gesperrte Einträge.
	 * Allow access to all personal records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPersonalAccess();

    /** Column definition for IsPersonalAccess */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsPersonalAccess = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsPersonalAccess", null);
    /** Column name IsPersonalAccess */
    public static final String COLUMNNAME_IsPersonalAccess = "IsPersonalAccess";

	/**
	 * Set Persönliche Sperre.
	 * Allow users with role to lock access to personal records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPersonalLock (boolean IsPersonalLock);

	/**
	 * Get Persönliche Sperre.
	 * Allow users with role to lock access to personal records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPersonalLock();

    /** Column definition for IsPersonalLock */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsPersonalLock = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsPersonalLock", null);
    /** Column name IsPersonalLock */
    public static final String COLUMNNAME_IsPersonalLock = "IsPersonalLock";

	/**
	 * Set Beta-Funktionalität verwenden abw..
	 * Diese Einstellung kann für eine bestimmte Rolle die bei "Mandant" hinterlegte Einstellung überschreiben
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRoleAlwaysUseBetaFunctions (boolean IsRoleAlwaysUseBetaFunctions);

	/**
	 * Get Beta-Funktionalität verwenden abw..
	 * Diese Einstellung kann für eine bestimmte Rolle die bei "Mandant" hinterlegte Einstellung überschreiben
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRoleAlwaysUseBetaFunctions();

    /** Column definition for IsRoleAlwaysUseBetaFunctions */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsRoleAlwaysUseBetaFunctions = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsRoleAlwaysUseBetaFunctions", null);
    /** Column name IsRoleAlwaysUseBetaFunctions */
    public static final String COLUMNNAME_IsRoleAlwaysUseBetaFunctions = "IsRoleAlwaysUseBetaFunctions";

	/**
	 * Set Buchführungsdaten zeigen.
	 * Users with this role can see accounting information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShowAcct (boolean IsShowAcct);

	/**
	 * Get Buchführungsdaten zeigen.
	 * Users with this role can see accounting information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShowAcct();

    /** Column definition for IsShowAcct */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsShowAcct = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsShowAcct", null);
    /** Column name IsShowAcct */
    public static final String COLUMNNAME_IsShowAcct = "IsShowAcct";

	/**
	 * Set Show all entity types.
	 * Show all entity types, even if some of them were marked to not be shown.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShowAllEntityTypes (boolean IsShowAllEntityTypes);

	/**
	 * Get Show all entity types.
	 * Show all entity types, even if some of them were marked to not be shown.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShowAllEntityTypes();

    /** Column definition for IsShowAllEntityTypes */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsShowAllEntityTypes = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsShowAllEntityTypes", null);
    /** Column name IsShowAllEntityTypes */
    public static final String COLUMNNAME_IsShowAllEntityTypes = "IsShowAllEntityTypes";

	/**
	 * Set Nutzerbezogener Organisationszugriff.
	 * Use Org Access defined by user instead of Role Org Access
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseUserOrgAccess (boolean IsUseUserOrgAccess);

	/**
	 * Get Nutzerbezogener Organisationszugriff.
	 * Use Org Access defined by user instead of Role Org Access
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseUserOrgAccess();

    /** Column definition for IsUseUserOrgAccess */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_IsUseUserOrgAccess = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "IsUseUserOrgAccess", null);
    /** Column name IsUseUserOrgAccess */
    public static final String COLUMNNAME_IsUseUserOrgAccess = "IsUseUserOrgAccess";

	/**
	 * Set Max. Suchergebnisse.
	 * If defined, you cannot query more records as defined - the query criteria needs to be changed to query less records
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMaxQueryRecords (int MaxQueryRecords);

	/**
	 * Get Max. Suchergebnisse.
	 * If defined, you cannot query more records as defined - the query criteria needs to be changed to query less records
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMaxQueryRecords();

    /** Column definition for MaxQueryRecords */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_MaxQueryRecords = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "MaxQueryRecords", null);
    /** Column name MaxQueryRecords */
    public static final String COLUMNNAME_MaxQueryRecords = "MaxQueryRecords";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Mindestpreis überschreiben.
	 * Overwrite Price Limit if the Price List  enforces the Price Limit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwritePriceLimit (boolean OverwritePriceLimit);

	/**
	 * Get Mindestpreis überschreiben.
	 * Overwrite Price Limit if the Price List  enforces the Price Limit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwritePriceLimit();

    /** Column definition for OverwritePriceLimit */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_OverwritePriceLimit = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "OverwritePriceLimit", null);
    /** Column name OverwritePriceLimit */
    public static final String COLUMNNAME_OverwritePriceLimit = "OverwritePriceLimit";

	/**
	 * Set Ebene für Einstellungen.
	 * Determines what preferences the user can set
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPreferenceType (java.lang.String PreferenceType);

	/**
	 * Get Ebene für Einstellungen.
	 * Determines what preferences the user can set
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPreferenceType();

    /** Column definition for PreferenceType */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_PreferenceType = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "PreferenceType", null);
    /** Column name PreferenceType */
    public static final String COLUMNNAME_PreferenceType = "PreferenceType";

	/**
	 * Set Root menu node.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRoot_Menu_ID (int Root_Menu_ID);

	/**
	 * Get Root menu node.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRoot_Menu_ID();

	public org.compiere.model.I_AD_Menu getRoot_Menu();

	public void setRoot_Menu(org.compiere.model.I_AD_Menu Root_Menu);

    /** Column definition for Root_Menu_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Menu> COLUMN_Root_Menu_ID = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_Menu>(I_AD_Role.class, "Root_Menu_ID", org.compiere.model.I_AD_Menu.class);
    /** Column name Root_Menu_ID */
    public static final String COLUMNNAME_Root_Menu_ID = "Root_Menu_ID";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der EintrÃ¤ge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der EintrÃ¤ge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSupervisor_ID (int Supervisor_ID);

	/**
	 * Get Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSupervisor_ID();

	public org.compiere.model.I_AD_User getSupervisor();

	public void setSupervisor(org.compiere.model.I_AD_User Supervisor);

    /** Column definition for Supervisor_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_User> COLUMN_Supervisor_ID = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_User>(I_AD_Role.class, "Supervisor_ID", org.compiere.model.I_AD_User.class);
    /** Column name Supervisor_ID */
    public static final String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Role, org.compiere.model.I_AD_User>(I_AD_Role.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set UserDiscount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserDiscount (java.math.BigDecimal UserDiscount);

	/**
	 * Get UserDiscount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getUserDiscount();

    /** Column definition for UserDiscount */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_UserDiscount = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "UserDiscount", null);
    /** Column name UserDiscount */
    public static final String COLUMNNAME_UserDiscount = "UserDiscount";

	/**
	 * Set Nutzer-Ebene.
	 * System Client Organization
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUserLevel (java.lang.String UserLevel);

	/**
	 * Get Nutzer-Ebene.
	 * System Client Organization
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserLevel();

    /** Column definition for UserLevel */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_UserLevel = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "UserLevel", null);
    /** Column name UserLevel */
    public static final String COLUMNNAME_UserLevel = "UserLevel";

	/**
	 * Set Is webui role.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_Role (boolean WEBUI_Role);

	/**
	 * Get Is webui role.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWEBUI_Role();

    /** Column definition for WEBUI_Role */
    public static final org.adempiere.model.ModelColumn<I_AD_Role, Object> COLUMN_WEBUI_Role = new org.adempiere.model.ModelColumn<I_AD_Role, Object>(I_AD_Role.class, "WEBUI_Role", null);
    /** Column name WEBUI_Role */
    public static final String COLUMNNAME_WEBUI_Role = "WEBUI_Role";
}
