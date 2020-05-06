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

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for U_POSTerminal
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_U_POSTerminal 
{

    /** TableName=U_POSTerminal */
    public static final String Table_Name = "U_POSTerminal";

    /** AD_Table_ID=52004 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AutoLock */
    public static final String COLUMNNAME_AutoLock = "AutoLock";

	/** Set Auto Lock.
	  * Whether to automatically lock the terminal when till is closed
	  */
	public void setAutoLock (boolean AutoLock);

	/** Get Auto Lock.
	  * Whether to automatically lock the terminal when till is closed
	  */
	public boolean isAutoLock();

    /** Column name Card_BankAccount_ID */
    public static final String COLUMNNAME_Card_BankAccount_ID = "Card_BankAccount_ID";

	/** Set Card Bank Account.
	  * Bank Account on which card transactions will be processed
	  */
	public void setCard_BankAccount_ID (int Card_BankAccount_ID);

	/** Get Card Bank Account.
	  * Bank Account on which card transactions will be processed
	  */
	public int getCard_BankAccount_ID();

	public I_C_BankAccount getCard_BankAccount() throws RuntimeException;

    /** Column name CardTransferBankAccount_ID */
    public static final String COLUMNNAME_CardTransferBankAccount_ID = "CardTransferBankAccount_ID";

	/** Set Transfer Card trx to.
	  * Bank account on which to transfer Card transactions
	  */
	public void setCardTransferBankAccount_ID (int CardTransferBankAccount_ID);

	/** Get Transfer Card trx to.
	  * Bank account on which to transfer Card transactions
	  */
	public int getCardTransferBankAccount_ID();

	public I_C_BankAccount getCardTransferBankAccount() throws RuntimeException;

    /** Column name CardTransferCashBook_ID */
    public static final String COLUMNNAME_CardTransferCashBook_ID = "CardTransferCashBook_ID";

	/** Set Transfer Card trx to.
	  * Cash Book on which to transfer all Card transactions
	  */
	public void setCardTransferCashBook_ID (int CardTransferCashBook_ID);

	/** Get Transfer Card trx to.
	  * Cash Book on which to transfer all Card transactions
	  */
	public int getCardTransferCashBook_ID();

	public I_C_CashBook getCardTransferCashBook() throws RuntimeException;

    /** Column name CardTransferType */
    public static final String COLUMNNAME_CardTransferType = "CardTransferType";

	/** Set Card Transfer Type	  */
	public void setCardTransferType (String CardTransferType);

	/** Get Card Transfer Type	  */
	public String getCardTransferType();

    /** Column name CashBookTransferType */
    public static final String COLUMNNAME_CashBookTransferType = "CashBookTransferType";

	/** Set Cash Book Transfer Type.
	  * Where the money in the cash book should be transfered to. Either a Bank Account or another Cash Book
	  */
	public void setCashBookTransferType (String CashBookTransferType);

	/** Get Cash Book Transfer Type.
	  * Where the money in the cash book should be transfered to. Either a Bank Account or another Cash Book
	  */
	public String getCashBookTransferType();

    /** Column name CashTransferBankAccount_ID */
    public static final String COLUMNNAME_CashTransferBankAccount_ID = "CashTransferBankAccount_ID";

	/** Set Transfer Cash trx to.
	  * Bank Account on which to transfer all Cash transactions
	  */
	public void setCashTransferBankAccount_ID (int CashTransferBankAccount_ID);

	/** Get Transfer Cash trx to.
	  * Bank Account on which to transfer all Cash transactions
	  */
	public int getCashTransferBankAccount_ID();

	public I_C_BankAccount getCashTransferBankAccount() throws RuntimeException;

    /** Column name CashTransferCashBook_ID */
    public static final String COLUMNNAME_CashTransferCashBook_ID = "CashTransferCashBook_ID";

	/** Set Transfer Cash trx to.
	  * Cash Book on which to transfer all Cash transactions
	  */
	public void setCashTransferCashBook_ID (int CashTransferCashBook_ID);

	/** Get Transfer Cash trx to.
	  * Cash Book on which to transfer all Cash transactions
	  */
	public int getCashTransferCashBook_ID();

	public I_C_CashBook getCashTransferCashBook() throws RuntimeException;

    /** Column name C_CashBook_ID */
    public static final String COLUMNNAME_C_CashBook_ID = "C_CashBook_ID";

	/** Set Cash Book.
	  * Cash Book for recording petty cash transactions
	  */
	public void setC_CashBook_ID (int C_CashBook_ID);

	/** Get Cash Book.
	  * Cash Book for recording petty cash transactions
	  */
	public int getC_CashBook_ID();

	public I_C_CashBook getC_CashBook() throws RuntimeException;

    /** Column name C_CashBPartner_ID */
    public static final String COLUMNNAME_C_CashBPartner_ID = "C_CashBPartner_ID";

	/** Set Cash BPartner.
	  * BPartner to be used for Cash transactions
	  */
	public void setC_CashBPartner_ID (int C_CashBPartner_ID);

	/** Get Cash BPartner.
	  * BPartner to be used for Cash transactions
	  */
	public int getC_CashBPartner_ID();

	public I_C_BPartner getC_CashBPartner() throws RuntimeException;

    /** Column name Check_BankAccount_ID */
    public static final String COLUMNNAME_Check_BankAccount_ID = "Check_BankAccount_ID";

	/** Set Check Bank Account.
	  * Bank Account to be used for processing Check transactions
	  */
	public void setCheck_BankAccount_ID (int Check_BankAccount_ID);

	/** Get Check Bank Account.
	  * Bank Account to be used for processing Check transactions
	  */
	public int getCheck_BankAccount_ID();

	public I_C_BankAccount getCheck_BankAccount() throws RuntimeException;

    /** Column name CheckTransferBankAccount_ID */
    public static final String COLUMNNAME_CheckTransferBankAccount_ID = "CheckTransferBankAccount_ID";

	/** Set Tranfer Check trx to.
	  * Bank account on which to transfer Check transactions
	  */
	public void setCheckTransferBankAccount_ID (int CheckTransferBankAccount_ID);

	/** Get Tranfer Check trx to.
	  * Bank account on which to transfer Check transactions
	  */
	public int getCheckTransferBankAccount_ID();

	public I_C_BankAccount getCheckTransferBankAccount() throws RuntimeException;

    /** Column name CheckTransferCashBook_ID */
    public static final String COLUMNNAME_CheckTransferCashBook_ID = "CheckTransferCashBook_ID";

	/** Set Transfer Check trx to.
	  * Cash Book on which to transfer all Check transactions
	  */
	public void setCheckTransferCashBook_ID (int CheckTransferCashBook_ID);

	/** Get Transfer Check trx to.
	  * Cash Book on which to transfer all Check transactions
	  */
	public int getCheckTransferCashBook_ID();

	public I_C_CashBook getCheckTransferCashBook() throws RuntimeException;

    /** Column name CheckTransferType */
    public static final String COLUMNNAME_CheckTransferType = "CheckTransferType";

	/** Set Check Transfer Type	  */
	public void setCheckTransferType (String CheckTransferType);

	/** Get Check Transfer Type	  */
	public String getCheckTransferType();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name C_TemplateBPartner_ID */
    public static final String COLUMNNAME_C_TemplateBPartner_ID = "C_TemplateBPartner_ID";

	/** Set Template BPartner.
	  * BPartner that is to be used as template when new customers are created
	  */
	public void setC_TemplateBPartner_ID (int C_TemplateBPartner_ID);

	/** Get Template BPartner.
	  * BPartner that is to be used as template when new customers are created
	  */
	public int getC_TemplateBPartner_ID();

	public I_C_BPartner getC_TemplateBPartner() throws RuntimeException;

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name LastLockTime */
    public static final String COLUMNNAME_LastLockTime = "LastLockTime";

	/** Set Last Lock Time.
	  * Last time at which the terminal was locked
	  */
	public void setLastLockTime (Timestamp LastLockTime);

	/** Get Last Lock Time.
	  * Last time at which the terminal was locked
	  */
	public Timestamp getLastLockTime();

    /** Column name Locked */
    public static final String COLUMNNAME_Locked = "Locked";

	/** Set Locked.
	  * Whether the terminal is locked
	  */
	public void setLocked (boolean Locked);

	/** Get Locked.
	  * Whether the terminal is locked
	  */
	public boolean isLocked();

    /** Column name LockTime */
    public static final String COLUMNNAME_LockTime = "LockTime";

	/** Set Lock Time.
	  * Time in minutes the terminal should be kept in a locked state.
	  */
	public void setLockTime (int LockTime);

	/** Get Lock Time.
	  * Time in minutes the terminal should be kept in a locked state.
	  */
	public int getLockTime();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name PO_PriceList_ID */
    public static final String COLUMNNAME_PO_PriceList_ID = "PO_PriceList_ID";

	/** Set Purchase Pricelist.
	  * Price List used by this Business Partner
	  */
	public void setPO_PriceList_ID (int PO_PriceList_ID);

	/** Get Purchase Pricelist.
	  * Price List used by this Business Partner
	  */
	public int getPO_PriceList_ID();

	public I_M_PriceList getPO_PriceList() throws RuntimeException;

    /** Column name PrinterName */
    public static final String COLUMNNAME_PrinterName = "PrinterName";

	/** Set Printer Name.
	  * Name of the Printer
	  */
	public void setPrinterName (String PrinterName);

	/** Get Printer Name.
	  * Name of the Printer
	  */
	public String getPrinterName();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public I_AD_User getSalesRep() throws RuntimeException;

    /** Column name SO_PriceList_ID */
    public static final String COLUMNNAME_SO_PriceList_ID = "SO_PriceList_ID";

	/** Set Sales Pricelist	  */
	public void setSO_PriceList_ID (int SO_PriceList_ID);

	/** Get Sales Pricelist	  */
	public int getSO_PriceList_ID();

	public I_M_PriceList getSO_PriceList() throws RuntimeException;

    /** Column name UnlockingTime */
    public static final String COLUMNNAME_UnlockingTime = "UnlockingTime";

	/** Set UnlockingTime.
	  * Time at which the terminal should be unlocked
	  */
	public void setUnlockingTime (Timestamp UnlockingTime);

	/** Get UnlockingTime.
	  * Time at which the terminal should be unlocked
	  */
	public Timestamp getUnlockingTime();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name U_POSTerminal_ID */
    public static final String COLUMNNAME_U_POSTerminal_ID = "U_POSTerminal_ID";

	/** Set POS Terminal	  */
	public void setU_POSTerminal_ID (int U_POSTerminal_ID);

	/** Get POS Terminal	  */
	public int getU_POSTerminal_ID();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
