/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.impexp;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MBankStatementLoader;


/**
 *	Interface to be implemented by bank statement loader classes
 *
 *  Bank statement loader classes that extend this interface can be loaded
 *  by the MBankStatementLoader controller class. 
 *  The usage patter looks like this:
 *
 *  -init() is called in order to initialize the loader
 *
 *  -validate() is called, allowing the loader to perform data validation if
 *  it provides this.
 *
 *  -loadLines() is called, request the loader to start loading statement lines
 *
 *  -for everu statement line that the loader encounteres, it calls the
 *  saveLine() method of the MBankStatement controller object it obtained
 *  as part of the call to init()
 *
 *  -The MBankStatementLoader controller object can now obtain the data for the current bank
 *  statement line by using the corresponding get methods of the loader class.
 *
 *	@author Maarten Klinker, Eldir Tomassen
 *	@version $Id: BankStatementLoaderInterface.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */

public interface BankStatementLoaderInterface
{
	/**
	 * Initialize the loader
	 * @param controller Reference to the MBankStatementLoader controller object
	 * @return Initialized succesfully
	 */
	public boolean init(MBankStatementLoader controller);

	/**
	 * Verify whether the data to be imported is valid
	 * @return Data is valid
	 * If the actual loaders does not do any validity checks
	 * it will just return true.
	 */	
	public boolean isValid();

	/**
	 * Start importing statement lines
	 * @return Statement lines imported succesfully
	 */	
	public boolean loadLines();

	/**
	 * Return the most recent error
	 * @return Error message
	 * This error message will be handled as a Adempiere message,
	 * (e.g. it can be translated)
	 */
	public String getLastErrorMessage();

	/**
	 * Return the most recent error description
	 * @return Error discription
	 * This is an additional error description, it can be used to provided
	 * descriptive iformation, such as a file name or SQL error, that can not
	 * be translated by the Adempiere message system.
	 */
	public String getLastErrorDescription();
	
	/**
	 * The last time this loader aquired bank statement data.
	 * For OFX this is the <DTEND> value. This is generally only available\
	 * after loadLines() has been called. If a specific loader class 
	 * does not provided this information it is allowed to return null
	 * @return Date last run
	 */
	public Timestamp getDateLastRun();
	
	/**
	 * The routing number of the bank account for the statement line.
	 * @return Routing number
	 */
	public String getRoutingNo();
	
	/**
	 * The account number of the bank account for the statement line.
	 * @return Bank account number
	 */
	public String getBankAccountNo();
	
	/**
	 * Additional reference information
	 * Statement level reference information. If a specific loader class
	 * does not provided this, it is allowed to return null.
	 * @return Error discription
	 */
	public String getStatementReference();
	
	/**
	 * Statement Date
	 * Date of the bank statement. If a specific loader does not provide this, 
	 * it is allowed to return null.
	 * @return Statement Date
	 */
	public Timestamp getStatementDate();
	
	/**
	 * Transaction ID assigned by the bank.
	 * For OFX this is the <FITID>
	 * If a specific loader does not provide this, it is allowed to return
	 * null.
	 * @return Transaction ID
	 */
	public String getTrxID();
	
	/**
	 * Additional reference information
	 * Statement line level reference information.
	 * For OFX this is the <REFNUM> field.
	 * If a specific loader does not provided this, it is allowed to return null.
	 * @return Error discription
	 */
	public String getReference();
	
	/**
	 * Check number
	 * Check number, in case the transaction was initiated by a check.
	 * For OFX this is the <CHECKNUM> field, for MS-Money (OFC) this is the
	 * <CHKNUM> field.
	 * If a specific loader does not provide this, it is allowed to return null.
	 * @return Transaction reference
	 */
	public String getCheckNo();
	
	/**
	 * Payee name
	 * Name information, for OFX this is the <NAME> or
	 * <PAYEE><NAME> field	
	 * If a specific loader class does not provide this, it is allowed
	 * to return null.
	 * @return Payee name
	 */
	public String getPayeeName();
	
	/**
	 * Payee account
	 * Account information of "the other party"
	 * If a specific loader class does not provide this, it is allowed
	 * to return null.
	 * @return Payee bank account number
	 */
	public String getPayeeAccountNo();
	
	/**
	 * Statement line date
	 * This has to be provided by all loader classes.
	 * @return Statement line date
	 */
	public Timestamp getStatementLineDate();
	
	/**
	 * Effective date
	 * Date theat the funds became available.
	 * If a specific loader does not provide this, it is allowed to return null.
	 * @return Effective date
	 */
	public Timestamp getValutaDate();
	
	/**
	 * Transaction type
	 * @return Transaction type
	 * This returns the transaction type as used by the bank
	 * Whether a transaction is credit or debit depends on the amount (i.e. negative),
	 * this field is for reference only.
	 * If a specific loader class does not provide this, it is allowed
	 * to return null.
	 */
	public String getTrxType();

	/**
	 * Indicates whether this transaction is a reversal
	 * @return true if this is a reversal
	 */
	public boolean getIsReversal();
	
	/**
	 * Currency
	 * @return Currency
	 * Return the currency, if included in the statement data.
	 * It is returned as it appears in the import data, it should
	 * not be processed by the loader in any way.
	 * If a specific loader class does not provide this, it is allowed
	 * to return null.
	 */
	public String getCurrency();
	
	/**
	 * Statement line amount
	 * @return Statement Line Amount
	 * This has to be provided by all loader classes.
	 */
	public BigDecimal getStmtAmt();
	
	/**
	 * Transaction Amount
	 * @return Transaction Amount
	 */
	public BigDecimal getTrxAmt();
	
	/**
	 * Interest Amount
	 * @return Interest Amount
	 */
	public BigDecimal getInterestAmt();
	
	/**
	 * Transaction memo
	 * @return Memo
	 * Additional descriptive information.
	 * For OFX this is the <MEMO> filed, for SWIFT MT940
	 * this is the "86" line.
	 * If a specific loader does not provide this, it is allowed to return null.
	 */
	public String getMemo();
	
	/**
	 * Charge name
	 * @return Charge name
	 * Name of the charge, in case this transaction is a bank charge.
	 * If a specific loader class does not provide this, it is allowed
	 * to return null.
	 */
	public String getChargeName();
	
	/**
	 * Charge amount
	 * @return Charge amount
	 * Name of the charge, in case this transaction is a bank charge.
	 * If a specific loader class does not provide this, it is allowed
	 * to return null.
	 */
	public BigDecimal getChargeAmt();

}	//BankStatementLoaderInterface	

