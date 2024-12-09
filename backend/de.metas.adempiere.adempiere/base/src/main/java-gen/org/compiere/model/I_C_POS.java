<<<<<<< HEAD
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


/** Generated Interface for C_POS
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_POS 
{

    /** TableName=C_POS */
    public static final String Table_Name = "C_POS";

    /** AD_Table_ID=748 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Get Mandant.
=======
package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS 
{

	String Table_Name = "C_POS";

//	/** AD_Table_ID=748 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_Client>(I_C_POS.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_Org>(I_C_POS.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Auto Logout Delay.
	 * Automatically logout if terminal inactive for this period
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAutoLogoutDelay (int AutoLogoutDelay);
=======
	void setAutoLogoutDelay (int AutoLogoutDelay);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Auto Logout Delay.
	 * Automatically logout if terminal inactive for this period
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAutoLogoutDelay();

    /** Column definition for AutoLogoutDelay */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_AutoLogoutDelay = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "AutoLogoutDelay", null);
    /** Column name AutoLogoutDelay */
    public static final String COLUMNNAME_AutoLogoutDelay = "AutoLogoutDelay";

	/**
	 * Set CashDrawer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCashDrawer (java.lang.String CashDrawer);

	/**
	 * Get CashDrawer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCashDrawer();

    /** Column definition for CashDrawer */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_CashDrawer = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "CashDrawer", null);
    /** Column name CashDrawer */
    public static final String COLUMNNAME_CashDrawer = "CashDrawer";

	/**
	 * Set Vorlage Geschäftspartner.
=======
	int getAutoLogoutDelay();

	ModelColumn<I_C_POS, Object> COLUMN_AutoLogoutDelay = new ModelColumn<>(I_C_POS.class, "AutoLogoutDelay", null);
	String COLUMNNAME_AutoLogoutDelay = "AutoLogoutDelay";

	/**
	 * Set Cash Last Balance.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCashLastBalance (BigDecimal CashLastBalance);

	/**
	 * Get Cash Last Balance.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCashLastBalance();

	ModelColumn<I_C_POS, Object> COLUMN_CashLastBalance = new ModelColumn<>(I_C_POS.class, "CashLastBalance", null);
	String COLUMNNAME_CashLastBalance = "CashLastBalance";

	/**
	 * Set Template B.Partner.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Business Partner used for creating new Business Partners on the fly
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BPartnerCashTrx_ID (int C_BPartnerCashTrx_ID);

	/**
	 * Get Vorlage Geschäftspartner.
=======
	void setC_BPartnerCashTrx_ID (int C_BPartnerCashTrx_ID);

	/**
	 * Get Template B.Partner.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Business Partner used for creating new Business Partners on the fly
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BPartnerCashTrx_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerCashTrx();

	public void setC_BPartnerCashTrx(org.compiere.model.I_C_BPartner C_BPartnerCashTrx);

    /** Column definition for C_BPartnerCashTrx_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_BPartner> COLUMN_C_BPartnerCashTrx_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_BPartner>(I_C_POS.class, "C_BPartnerCashTrx_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartnerCashTrx_ID */
    public static final String COLUMNNAME_C_BPartnerCashTrx_ID = "C_BPartnerCashTrx_ID";

	/**
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
=======
	int getC_BPartnerCashTrx_ID();

	String COLUMNNAME_C_BPartnerCashTrx_ID = "C_BPartnerCashTrx_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
=======
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_BP_BankAccount>(I_C_POS.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Kassenbuch.
	 * Cash Book for recording petty cash transactions
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_CashBook_ID (int C_CashBook_ID);

	/**
	 * Get Kassenbuch.
	 * Cash Book for recording petty cash transactions
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_CashBook_ID();

	public org.compiere.model.I_C_CashBook getC_CashBook();

	public void setC_CashBook(org.compiere.model.I_C_CashBook C_CashBook);

    /** Column definition for C_CashBook_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_CashBook> COLUMN_C_CashBook_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_CashBook>(I_C_POS.class, "C_CashBook_ID", org.compiere.model.I_C_CashBook.class);
    /** Column name C_CashBook_ID */
    public static final String COLUMNNAME_C_CashBook_ID = "C_CashBook_ID";

	/**
	 * Set Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_DocType>(I_C_POS.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set POS-Terminal.
=======
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeOrder_ID (int C_DocTypeOrder_ID);

	/**
	 * Get Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeOrder_ID();

	String COLUMNNAME_C_DocTypeOrder_ID = "C_DocTypeOrder_ID";

	/**
	 * Set POS Terminal.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Point of Sales Terminal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_POS_ID (int C_POS_ID);

	/**
	 * Get POS-Terminal.
=======
	void setC_POS_ID (int C_POS_ID);

	/**
	 * Get POS Terminal.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Point of Sales Terminal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_POS_ID();

    /** Column definition for C_POS_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_C_POS_ID = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "C_POS_ID", null);
    /** Column name C_POS_ID */
    public static final String COLUMNNAME_C_POS_ID = "C_POS_ID";

	/**
	 * Set POS - Tastenanordnung.
	 * POS Function Key Layout
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_POSKeyLayout_ID (int C_POSKeyLayout_ID);

	/**
	 * Get POS - Tastenanordnung.
	 * POS Function Key Layout
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_POSKeyLayout_ID();

	public org.compiere.model.I_C_POSKeyLayout getC_POSKeyLayout();

	public void setC_POSKeyLayout(org.compiere.model.I_C_POSKeyLayout C_POSKeyLayout);

    /** Column definition for C_POSKeyLayout_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_POSKeyLayout> COLUMN_C_POSKeyLayout_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_POSKeyLayout>(I_C_POS.class, "C_POSKeyLayout_ID", org.compiere.model.I_C_POSKeyLayout.class);
    /** Column name C_POSKeyLayout_ID */
    public static final String COLUMNNAME_C_POSKeyLayout_ID = "C_POSKeyLayout_ID";

	/**
	 * Get Erstellt.
=======
	int getC_POS_ID();

	ModelColumn<I_C_POS, Object> COLUMN_C_POS_ID = new ModelColumn<>(I_C_POS.class, "C_POS_ID", null);
	String COLUMNNAME_C_POS_ID = "C_POS_ID";

	/**
	 * Set POS Cash Journal.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_POS_Journal_ID (int C_POS_Journal_ID);

	/**
	 * Get POS Cash Journal.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_POS_Journal_ID();

	ModelColumn<I_C_POS, Object> COLUMN_C_POS_Journal_ID = new ModelColumn<>(I_C_POS.class, "C_POS_Journal_ID", null);
	String COLUMNNAME_C_POS_Journal_ID = "C_POS_Journal_ID";

	/**
	 * Get Created.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_POS, Object> COLUMN_Created = new ModelColumn<>(I_C_POS.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_User>(I_C_POS.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Workplace_ID (int C_Workplace_ID);

	/**
	 * Get Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Workplace_ID();

	@Nullable org.compiere.model.I_C_Workplace getC_Workplace();

	void setC_Workplace(@Nullable org.compiere.model.I_C_Workplace C_Workplace);

	ModelColumn<I_C_POS, org.compiere.model.I_C_Workplace> COLUMN_C_Workplace_ID = new ModelColumn<>(I_C_POS.class, "C_Workplace_ID", org.compiere.model.I_C_Workplace.class);
	String COLUMNNAME_C_Workplace_ID = "C_Workplace_ID";

	/**
	 * Set Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
=======
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Aktiv.
=======
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_POS, Object> COLUMN_Description = new ModelColumn<>(I_C_POS.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
=======
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
=======
	boolean isActive();

	ModelColumn<I_C_POS, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Modify Price.
	 * Allow modifying the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsModifyPrice (boolean IsModifyPrice);
=======
	void setIsModifyPrice (boolean IsModifyPrice);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Modify Price.
	 * Allow modifying the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isModifyPrice();

    /** Column definition for IsModifyPrice */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_IsModifyPrice = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "IsModifyPrice", null);
    /** Column name IsModifyPrice */
    public static final String COLUMNNAME_IsModifyPrice = "IsModifyPrice";

	/**
	 * Set Preisliste.
=======
	boolean isModifyPrice();

	ModelColumn<I_C_POS, Object> COLUMN_IsModifyPrice = new ModelColumn<>(I_C_POS.class, "IsModifyPrice", null);
	String COLUMNNAME_IsModifyPrice = "IsModifyPrice";

	/**
	 * Set Price List.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
=======
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList();

	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList);

    /** Column definition for M_PriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_M_PriceList> COLUMN_M_PriceList_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_M_PriceList>(I_C_POS.class, "M_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Lager.
=======
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Warehouse.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
=======
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_M_Warehouse>(I_C_POS.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
=======
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Name.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
=======
	void setName (java.lang.String Name);

	/**
	 * Get Name.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set On Screen Keyboard layout.
	 * The key layout to use for on screen keyboard for text fields.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOSK_KeyLayout_ID (int OSK_KeyLayout_ID);

	/**
	 * Get On Screen Keyboard layout.
	 * The key layout to use for on screen keyboard for text fields.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOSK_KeyLayout_ID();

	public org.compiere.model.I_C_POSKeyLayout getOSK_KeyLayout();

	public void setOSK_KeyLayout(org.compiere.model.I_C_POSKeyLayout OSK_KeyLayout);

    /** Column definition for OSK_KeyLayout_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_POSKeyLayout> COLUMN_OSK_KeyLayout_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_POSKeyLayout>(I_C_POS.class, "OSK_KeyLayout_ID", org.compiere.model.I_C_POSKeyLayout.class);
    /** Column name OSK_KeyLayout_ID */
    public static final String COLUMNNAME_OSK_KeyLayout_ID = "OSK_KeyLayout_ID";

	/**
	 * Set On Screen Number Pad layout.
	 * The key layout to use for on screen number pad for numeric fields.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOSNP_KeyLayout_ID (int OSNP_KeyLayout_ID);

	/**
	 * Get On Screen Number Pad layout.
	 * The key layout to use for on screen number pad for numeric fields.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOSNP_KeyLayout_ID();

	public org.compiere.model.I_C_POSKeyLayout getOSNP_KeyLayout();

	public void setOSNP_KeyLayout(org.compiere.model.I_C_POSKeyLayout OSNP_KeyLayout);

    /** Column definition for OSNP_KeyLayout_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_POSKeyLayout> COLUMN_OSNP_KeyLayout_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_C_POSKeyLayout>(I_C_POS.class, "OSNP_KeyLayout_ID", org.compiere.model.I_C_POSKeyLayout.class);
    /** Column name OSNP_KeyLayout_ID */
    public static final String COLUMNNAME_OSNP_KeyLayout_ID = "OSNP_KeyLayout_ID";

	/**
	 * Set Drucker.
=======
	java.lang.String getName();

	ModelColumn<I_C_POS, Object> COLUMN_Name = new ModelColumn<>(I_C_POS.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Payment Processor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOSPaymentProcessor (@Nullable java.lang.String POSPaymentProcessor);

	/**
	 * Get Payment Processor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOSPaymentProcessor();

	ModelColumn<I_C_POS, Object> COLUMN_POSPaymentProcessor = new ModelColumn<>(I_C_POS.class, "POSPaymentProcessor", null);
	String COLUMNNAME_POSPaymentProcessor = "POSPaymentProcessor";

	/**
	 * Set Printer.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPrinterName (java.lang.String PrinterName);

	/**
	 * Get Drucker.
=======
	void setPrinterName (@Nullable java.lang.String PrinterName);

	/**
	 * Get Printer.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getPrinterName();

    /** Column definition for PrinterName */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_PrinterName = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "PrinterName", null);
    /** Column name PrinterName */
    public static final String COLUMNNAME_PrinterName = "PrinterName";

	/**
	 * Set Vertriebsbeauftragter.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Vertriebsbeauftragter.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep();

	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column definition for SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_User>(I_C_POS.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Get Aktualisiert.
=======
	@Nullable java.lang.String getPrinterName();

	ModelColumn<I_C_POS, Object> COLUMN_PrinterName = new ModelColumn<>(I_C_POS.class, "PrinterName", null);
	String COLUMNNAME_PrinterName = "PrinterName";

	/**
	 * Set SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSUMUP_Config_ID (int SUMUP_Config_ID);

	/**
	 * Get SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSUMUP_Config_ID();

	ModelColumn<I_C_POS, Object> COLUMN_SUMUP_Config_ID = new ModelColumn<>(I_C_POS.class, "SUMUP_Config_ID", null);
	String COLUMNNAME_SUMUP_Config_ID = "SUMUP_Config_ID";

	/**
	 * Get Updated.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_POS, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_POS, Object>(I_C_POS.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_POS, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_POS, org.compiere.model.I_AD_User>(I_C_POS.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
