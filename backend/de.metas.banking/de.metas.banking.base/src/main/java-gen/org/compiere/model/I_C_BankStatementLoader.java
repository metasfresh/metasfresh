/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;


/** Generated Interface for C_BankStatementLoader
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BankStatementLoader 
{

    /** TableName=C_BankStatementLoader */
    public static final String Table_Name = "C_BankStatementLoader";

    /** AD_Table_ID=640 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountNo (java.lang.String AccountNo);

	/**
	 * Get Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountNo();

    /** Column definition for AccountNo */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_AccountNo = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "AccountNo", null);
    /** Column name AccountNo */
    public static final String COLUMNNAME_AccountNo = "AccountNo";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_AD_Client>(I_C_BankStatementLoader.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_AD_Org>(I_C_BankStatementLoader.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Branch ID.
	 * Bank Branch ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBranchID (java.lang.String BranchID);

	/**
	 * Get Branch ID.
	 * Bank Branch ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBranchID();

    /** Column definition for BranchID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_BranchID = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "BranchID", null);
    /** Column name BranchID */
    public static final String COLUMNNAME_BranchID = "BranchID";

	/**
	 * Set Ladeprogramm Bankauszug.
	 * Definition of Bank Statement Loader (SWIFT, OFX)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLoader_ID (int C_BankStatementLoader_ID);

	/**
	 * Get Ladeprogramm Bankauszug.
	 * Definition of Bank Statement Loader (SWIFT, OFX)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLoader_ID();

    /** Column definition for C_BankStatementLoader_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_C_BankStatementLoader_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "C_BankStatementLoader_ID", null);
    /** Column name C_BankStatementLoader_ID */
    public static final String COLUMNNAME_C_BankStatementLoader_ID = "C_BankStatementLoader_ID";

	/**
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_C_BP_BankAccount>(I_C_BankStatementLoader.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_AD_User>(I_C_BankStatementLoader.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Datums-Format.
	 * Date format used in the imput format
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateFormat (java.lang.String DateFormat);

	/**
	 * Get Datums-Format.
	 * Date format used in the imput format
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDateFormat();

    /** Column definition for DateFormat */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_DateFormat = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "DateFormat", null);
    /** Column name DateFormat */
    public static final String COLUMNNAME_DateFormat = "DateFormat";

	/**
	 * Set Tag letzter Lauf.
	 * Date the process was last run.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateLastRun (java.sql.Timestamp DateLastRun);

	/**
	 * Get Tag letzter Lauf.
	 * Date the process was last run.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateLastRun();

    /** Column definition for DateLastRun */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_DateLastRun = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "DateLastRun", null);
    /** Column name DateLastRun */
    public static final String COLUMNNAME_DateLastRun = "DateLastRun";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFileName (java.lang.String FileName);

	/**
	 * Get File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFileName();

    /** Column definition for FileName */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_FileName = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "FileName", null);
    /** Column name FileName */
    public static final String COLUMNNAME_FileName = "FileName";

	/**
	 * Set Financial Institution ID.
	 * The ID of the Financial Institution / Bank
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFinancialInstitutionID (java.lang.String FinancialInstitutionID);

	/**
	 * Get Financial Institution ID.
	 * The ID of the Financial Institution / Bank
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFinancialInstitutionID();

    /** Column definition for FinancialInstitutionID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_FinancialInstitutionID = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "FinancialInstitutionID", null);
    /** Column name FinancialInstitutionID */
    public static final String COLUMNNAME_FinancialInstitutionID = "FinancialInstitutionID";

	/**
	 * Set Host Address.
	 * Host Address URL or DNS
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHostAddress (java.lang.String HostAddress);

	/**
	 * Get Host Address.
	 * Host Address URL or DNS
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHostAddress();

    /** Column definition for HostAddress */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_HostAddress = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "HostAddress", null);
    /** Column name HostAddress */
    public static final String COLUMNNAME_HostAddress = "HostAddress";

	/**
	 * Set Host port.
	 * Host Communication Port
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHostPort (int HostPort);

	/**
	 * Get Host port.
	 * Host Communication Port
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHostPort();

    /** Column definition for HostPort */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_HostPort = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "HostPort", null);
    /** Column name HostPort */
    public static final String COLUMNNAME_HostPort = "HostPort";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPassword();

    /** Column definition for Password */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "Password", null);
    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/**
	 * Set PIN.
	 * Personal Identification Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPIN (java.lang.String PIN);

	/**
	 * Get PIN.
	 * Personal Identification Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPIN();

    /** Column definition for PIN */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_PIN = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "PIN", null);
    /** Column name PIN */
    public static final String COLUMNNAME_PIN = "PIN";

	/**
	 * Set Proxy address.
	 * Address of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProxyAddress (java.lang.String ProxyAddress);

	/**
	 * Get Proxy address.
	 * Address of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProxyAddress();

    /** Column definition for ProxyAddress */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_ProxyAddress = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "ProxyAddress", null);
    /** Column name ProxyAddress */
    public static final String COLUMNNAME_ProxyAddress = "ProxyAddress";

	/**
	 * Set Proxy logon.
	 * Logon of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProxyLogon (java.lang.String ProxyLogon);

	/**
	 * Get Proxy logon.
	 * Logon of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProxyLogon();

    /** Column definition for ProxyLogon */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_ProxyLogon = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "ProxyLogon", null);
    /** Column name ProxyLogon */
    public static final String COLUMNNAME_ProxyLogon = "ProxyLogon";

	/**
	 * Set Proxy-Passwort.
	 * Password of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProxyPassword (java.lang.String ProxyPassword);

	/**
	 * Get Proxy-Passwort.
	 * Password of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProxyPassword();

    /** Column definition for ProxyPassword */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_ProxyPassword = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "ProxyPassword", null);
    /** Column name ProxyPassword */
    public static final String COLUMNNAME_ProxyPassword = "ProxyPassword";

	/**
	 * Set Proxy port.
	 * Port of your proxy server
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProxyPort (int ProxyPort);

	/**
	 * Get Proxy port.
	 * Port of your proxy server
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getProxyPort();

    /** Column definition for ProxyPort */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_ProxyPort = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "ProxyPort", null);
    /** Column name ProxyPort */
    public static final String COLUMNNAME_ProxyPort = "ProxyPort";

	/**
	 * Set Statement Loader Class.
	 * Class name of the bank statement loader
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStmtLoaderClass (java.lang.String StmtLoaderClass);

	/**
	 * Get Statement Loader Class.
	 * Class name of the bank statement loader
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStmtLoaderClass();

    /** Column definition for StmtLoaderClass */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_StmtLoaderClass = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "StmtLoaderClass", null);
    /** Column name StmtLoaderClass */
    public static final String COLUMNNAME_StmtLoaderClass = "StmtLoaderClass";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, org.compiere.model.I_AD_User>(I_C_BankStatementLoader.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set User ID.
	 * User ID or account number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserID (java.lang.String UserID);

	/**
	 * Get User ID.
	 * User ID or account number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserID();

    /** Column definition for UserID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object> COLUMN_UserID = new org.adempiere.model.ModelColumn<I_C_BankStatementLoader, Object>(I_C_BankStatementLoader.class, "UserID", null);
    /** Column name UserID */
    public static final String COLUMNNAME_UserID = "UserID";
}
