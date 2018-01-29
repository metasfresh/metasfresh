/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_C_Cash;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCash;
import org.compiere.model.MCashBook;

/**
 * Post Invoice Documents.
 * 
 * <pre>
 *  Table:              C_Cash (407)
 *  Document Types:     CMC
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: Doc_Cash.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Cash extends Doc<DocLine_Cash>
{
	/**
	 * Constructor
	 * 
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_Cash(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_CashJournal);
	}	// Doc_Cash

	@Override
	protected void loadDocumentDetails()
	{
		I_C_Cash cash = getModel(I_C_Cash.class);
		setDateDoc(cash.getStatementDate());

		// Amounts
		setAmount(Doc.AMTTYPE_Gross, cash.getStatementDifference());

		// Set CashBook Org & Currency
		MCashBook cb = MCashBook.get(getCtx(), cash.getC_CashBook_ID());
		setC_CashBook_ID(cb.getC_CashBook_ID());
		setC_Currency_ID(cb.getC_Currency_ID());

		// Contained Objects
		setDocLines(loadLines(cash, cb));
	}

	private List<DocLine_Cash> loadLines(I_C_Cash cash, MCashBook cb)
	{
		final List<DocLine_Cash> list = new ArrayList<>();
		final MCash cashPO = LegacyAdapters.convertToPO(cash);
		for (final I_C_CashLine line : cashPO.getLines(true))
		{
			DocLine_Cash docLine = new DocLine_Cash(line, this);
			//
			list.add(docLine);
		}

		return list;
	}	// loadLines

	/**************************************************************************
	 * Get Source Currency Balance - subtracts line amounts from total - no rounding
	 * 
	 * @return positive amount, if total invoice is bigger than lines
	 */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;    // Lines are balanced
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for
	 * CMC.
	 * 
	 * <pre>
	 *  Expense
	 *          CashExpense     DR
	 *          CashAsset               CR
	 *  Receipt
	 *          CashAsset       DR
	 *          CashReceipt             CR
	 *  Charge
	 *          Charge          DR
	 *          CashAsset               CR
	 *  Difference
	 *          CashDifference  DR
	 *          CashAsset               CR
	 *  Invoice
	 *          CashAsset       DR
	 *          CashTransfer            CR
	 *  Transfer
	 *          BankInTransit   DR
	 *          CashAsset               CR
	 * </pre>
	 * 
	 * @param as account schema
	 * @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as)
	{
		// Need to have CashBook
		if (getC_CashBook_ID() == 0)
		{
			// p_Error = "C_CashBook_ID not set";
			// log.error(p_Error);
			// return null;
			throw newPostingException()
					.setC_AcctSchema(as)
					.setDetailMessage("C_CashBook_ID not set");
		}

		// create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		// Header posting amt as Invoices and Transfer could be differenet currency
		// CashAsset Total
		BigDecimal assetAmt = BigDecimal.ZERO;

		// Lines
		for (DocLine_Cash line : getDocLines())
		{
			String CashType = line.getCashType();

			if (CashType.equals(DocLine_Cash.CASHTYPE_EXPENSE))
			{   // amount is negative
				   // CashExpense DR
				   // CashAsset CR
				fact.createLine(line, getAccount(Doc.ACCTTYPE_CashExpense, as),
						getC_Currency_ID(), line.getAmount().negate(), null);
				// fact.createLine(line, getAccount(Doc.ACCTTYPE_CashAsset, as),
				// p_vo.C_Currency_ID, null, line.getAmount().negate());
				assetAmt = assetAmt.subtract(line.getAmount().negate());
			}
			else if (CashType.equals(DocLine_Cash.CASHTYPE_RECEIPT))
			{   // amount is positive
				   // CashAsset DR
				   // CashReceipt CR
				// fact.createLine(line, getAccount(Doc.ACCTTYPE_CashAsset, as),
				// p_vo.C_Currency_ID, line.getAmount(), null);
				assetAmt = assetAmt.add(line.getAmount());
				fact.createLine(line, getAccount(Doc.ACCTTYPE_CashReceipt, as),
						getC_Currency_ID(), null, line.getAmount());
			}
			else if (CashType.equals(DocLine_Cash.CASHTYPE_CHARGE))
			{   // amount is negative
				   // Charge DR
				   // CashAsset CR
				fact.createLine(line, line.getChargeAccount(as, line.getAmount().negate()),
						getC_Currency_ID(), line.getAmount().negate());
				// fact.createLine(line, getAccount(Doc.ACCTTYPE_CashAsset, as),
				// p_vo.C_Currency_ID, null, line.getAmount().negate());
				assetAmt = assetAmt.subtract(line.getAmount().negate());
			}
			else if (CashType.equals(DocLine_Cash.CASHTYPE_DIFFERENCE))
			{   // amount is pos/neg
				   // CashDifference DR
				   // CashAsset CR
				fact.createLine(line, getAccount(Doc.ACCTTYPE_CashDifference, as),
						getC_Currency_ID(), line.getAmount().negate());
				// fact.createLine(line, getAccount(Doc.ACCTTYPE_CashAsset, as),
				// p_vo.C_Currency_ID, line.getAmount());
				assetAmt = assetAmt.add(line.getAmount());
			}
			else if (CashType.equals(DocLine_Cash.CASHTYPE_INVOICE))
			{   // amount is pos/neg
				   // CashAsset DR dr -- Invoice is in Invoice Currency !
				   // CashTransfer cr CR
				if (line.getC_Currency_ID() == getC_Currency_ID())
					assetAmt = assetAmt.add(line.getAmount());
				else
					fact.createLine(line,
							getAccount(Doc.ACCTTYPE_CashAsset, as),
							line.getC_Currency_ID(), line.getAmount());
				fact.createLine(line,
						getAccount(Doc.ACCTTYPE_CashTransfer, as),
						line.getC_Currency_ID(), line.getAmount().negate());
			}
			else if (CashType.equals(DocLine_Cash.CASHTYPE_TRANSFER))
			{   // amount is pos/neg
				   // BankInTransit DR dr -- Transfer is in Bank Account Currency
				   // CashAsset dr CR
				int temp = getC_BP_BankAccount_ID();
				setC_BP_BankAccount_ID(line.getC_BP_BankAccount_ID());
				fact.createLine(line,
						getAccount(Doc.ACCTTYPE_BankInTransit, as),
						line.getC_Currency_ID(), line.getAmount().negate());
				setC_BP_BankAccount_ID(temp);
				if (line.getC_Currency_ID() == getC_Currency_ID())
					assetAmt = assetAmt.add(line.getAmount());
				else
					fact.createLine(line,
							getAccount(Doc.ACCTTYPE_CashAsset, as),
							line.getC_Currency_ID(), line.getAmount());
			}
		}	// lines

		// Cash Asset
		fact.createLine(null, getAccount(Doc.ACCTTYPE_CashAsset, as),
				getC_Currency_ID(), assetAmt);

		//
		ArrayList<Fact> facts = new ArrayList<>();
		facts.add(fact);
		return facts;
	}   // createFact

}   // Doc_Cash
