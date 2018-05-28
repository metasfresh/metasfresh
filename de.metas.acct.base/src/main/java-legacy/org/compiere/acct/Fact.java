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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.acct.FactTrxLines.FactTrxLinesType;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MFactAcct;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.currency.ICurrencyConversionContext;
import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Accounting Fact
 *
 * @author Jorg Janke
 * @version $Id: Fact.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 * 
 *          BF [ 2789949 ] Multicurrency in matching posting
 */
public final class Fact
{
	/**
	 * Constructor
	 * 
	 * @param document pointer to document
	 * @param acctSchema Account Schema to create accounts
	 * @param defaultPostingType the default Posting type (actual,..) for this posting
	 */
	public Fact(final Doc document, final MAcctSchema acctSchema, final String defaultPostingType)
	{
		Check.assumeNotNull(document, "document not null");
		m_doc = document;

		Check.assumeNotNull(acctSchema, "acctSchema not null");
		m_acctSchema = acctSchema;

		Check.assumeNotEmpty(defaultPostingType, "defaultPostingType not empty");
		m_postingType = defaultPostingType;

		// Fix [ 1884676 ] Fact not setting transaction
		m_trxName = document.getTrxName();

		log.info("Fact: {}", this);
	}	// Fact

	// services
	private static final transient Logger log = LogManager.getLogger(Fact.class);

	/** Document */
	private final Doc m_doc;
	/** Accounting Schema */
	private final MAcctSchema m_acctSchema;
	/** Transaction */
	private String m_trxName = ITrx.TRXNAME_None;

	/** Posting Type */
	private final String m_postingType;

	/** Actual Balance Type */
	public static final String POST_Actual = MFactAcct.POSTINGTYPE_Actual;
	/** Budget Balance Type */
	public static final String POST_Budget = MFactAcct.POSTINGTYPE_Budget;
	/** Encumbrance Posting */
	public static final String POST_Commitment = MFactAcct.POSTINGTYPE_Commitment;
	/** Encumbrance Posting */
	public static final String POST_Reservation = MFactAcct.POSTINGTYPE_Reservation;

	/** Is Converted */
	private boolean m_converted = false;

	/** Lines */
	private List<FactLine> m_lines = new ArrayList<>();

	private FactTrxStrategy factTrxLinesStrategy = PerDocumentLineFactTrxStrategy.instance;

	public Fact setFactTrxLinesStrategy(@NonNull final FactTrxStrategy factTrxLinesStrategy)
	{
		this.factTrxLinesStrategy = factTrxLinesStrategy;
		return this;
	}

	/**
	 * Dispose
	 */
	public final void dispose()
	{
		m_lines.clear();
		m_lines = null;
	}   // dispose

	/**
	 * Create and convert Fact Line. Used to create a DR and/or CR entry
	 *
	 * @param docLine the document line or null
	 * @param account if null, line is not created
	 * @param C_Currency_ID the currency
	 * @param debitAmt debit amount, can be null
	 * @param creditAmt credit amount, can be null
	 * @return Fact Line
	 */
	public FactLine createLine(final DocLine docLine,
			final MAccount account,
			final int C_Currency_ID,
			final BigDecimal debitAmt, final BigDecimal creditAmt)
	{
		return createLine()
				.setDocLine(docLine)
				.setAccount(account)
				.setAmtSource(C_Currency_ID, debitAmt, creditAmt)
				.setQty(null) // N/A
				.buildAndAdd();
	}

	public final FactLineBuilder createLine()
	{
		return new FactLineBuilder(this);
	}

	/**
	 * Create and convert Fact Line. Used to create a DR and/or CR entry.
	 *
	 * @param docLine the document line or null
	 * @param account if null, line is not created
	 * @param C_Currency_ID the currency
	 * @param debitAmt debit amount, can be null
	 * @param creditAmt credit amount, can be null
	 * @param qty quantity, can be null and in that case the standard qty from DocLine/Doc will be used.
	 * @return Fact Line or null
	 */
	public FactLine createLine(final DocLine docLine,
			final MAccount account,
			final int C_Currency_ID,
			final BigDecimal debitAmt, final BigDecimal creditAmt,
			final BigDecimal qty)
	{
		return createLine()
				.setDocLine(docLine)
				.setAccount(account)
				.setAmtSource(C_Currency_ID, debitAmt, creditAmt)
				.setQty(qty)
				.buildAndAdd();
	}	// createLine

	/**
	 * Add Fact Line
	 * 
	 * @param line fact line
	 */
	private final void add(final FactLine line)
	{
		Check.assumeNotNull(line, "line not null");
		m_lines.add(line);
	}   // add

	/**
	 * Remove Fact Line
	 * 
	 * @param line fact line
	 */
	public void remove(FactLine line)
	{
		m_lines.remove(line);
	}   // remove

	/**
	 * Create and convert Fact Line. Used to create either a DR or CR entry
	 *
	 * @param docLine Document Line or null
	 * @param accountDr Account to be used if Amt is DR balance
	 * @param accountCr Account to be used if Amt is CR balance
	 * @param C_Currency_ID Currency
	 * @param Amt if negative Cr else Dr
	 * @return FactLine
	 */
	public FactLine createLine(DocLine docLine, MAccount accountDr, MAccount accountCr, int C_Currency_ID, BigDecimal Amt)
	{
		return createLine()
				.setDocLine(docLine)
				.setC_Currency_ID(C_Currency_ID)
				.setAccountDrOrCrAndAmount(accountDr, accountCr, Amt)
				.buildAndAdd();
	}   // createLine

	/**
	 * Create and convert Fact Line. Used to create either a DR or CR entry
	 *
	 * @param docLine Document line or null
	 * @param account Account to be used
	 * @param C_Currency_ID Currency
	 * @param Amt if negative Cr else Dr
	 * @return FactLine
	 */
	public FactLine createLine(DocLine docLine, MAccount account, int C_Currency_ID, BigDecimal Amt)
	{
		return createLine()
				.setDocLine(docLine)
				.setC_Currency_ID(C_Currency_ID)
				.setAccount(account)
				.setAmtSourceDrOrCr(Amt)
				.buildAndAdd();
	}   // createLine

	/**
	 * Is Posting Type
	 * 
	 * @param PostingType - see POST_*
	 * @return true if document is posting type
	 */
	public boolean isPostingType(String PostingType)
	{
		return m_postingType.equals(PostingType);
	}   // isPostingType

	/**
	 * Is converted
	 * 
	 * @return true if converted
	 */
	public boolean isConverted()
	{
		return m_converted;
	}	// isConverted

	/**
	 * Get AcctSchema
	 * 
	 * @return AcctSchema; never returns null
	 */
	public final MAcctSchema getAcctSchema()
	{
		return m_acctSchema;
	}	// getAcctSchema

	public final String getPostingType()
	{
		return m_postingType;
	}

	/**************************************************************************
	 * Are the lines Source Balanced
	 * 
	 * @return true if source lines balanced
	 */
	public boolean isSourceBalanced()
	{
		// AZ Goodwill
		// Multi-Currency documents are source balanced by definition
		// No lines -> balanced
		if (m_lines.size() == 0 || m_doc.isMultiCurrency())
			return true;
		BigDecimal balance = getSourceBalance();
		boolean retValue = balance.signum() == 0;
		if (retValue)
			log.trace(toString());
		else
			log.warn("NO - Diff=" + balance + " - " + toString());
		return retValue;
	}	// isSourceBalanced

	/**
	 * Return Source Balance
	 * 
	 * @return source balance
	 */
	protected BigDecimal getSourceBalance()
	{
		BigDecimal result = BigDecimal.ZERO;
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine line = m_lines.get(i);
			result = result.add(line.getSourceBalance());
		}
		// log.debug("getSourceBalance - " + result.toString());
		return result;
	}	// getSourceBalance

	/**
	 * Create Source Line for Suspense Balancing. Only if Suspense Balancing is enabled and not a multi-currency document (double check as otherwise the rule should not have fired) If not balanced
	 * create balancing entry in currency of the document
	 * 
	 * @return FactLine
	 */
	public FactLine balanceSource()
	{
		if (!m_acctSchema.isSuspenseBalancing() || m_doc.isMultiCurrency())
			return null;
		BigDecimal diff = getSourceBalance();
		log.trace("Diff=" + diff);

		// new line
		FactLine line = new FactLine(m_doc.getCtx(), m_doc.get_Table_ID(), m_doc.get_ID(), 0, get_TrxName());
		line.setDocumentInfo(m_doc, null);
		line.setPostingType(m_postingType);

		// Account
		line.setAccount(m_acctSchema, m_acctSchema.getSuspenseBalancing_Acct());

		// Amount
		if (diff.signum() < 0)   // negative balance => DR
			line.setAmtSource(m_doc.getC_Currency_ID(), diff.abs(), BigDecimal.ZERO);
		else
			// positive balance => CR
			line.setAmtSource(m_doc.getC_Currency_ID(), BigDecimal.ZERO, diff);

		// Convert
		line.convert();
		//
		add(line);
		return line;
	}   // balancingSource

	/**************************************************************************
	 * Are all segments balanced
	 * 
	 * @return true if segments are balanced
	 */
	public boolean isSegmentBalanced()
	{
		// AZ Goodwill
		// Multi-Currency documents are source balanced by definition
		// No lines -> balanced
		if (m_lines.size() == 0 || m_doc.isMultiCurrency())
			return true;

		MAcctSchemaElement[] elements = m_acctSchema.getAcctSchemaElements();
		// check all balancing segments
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			if (ase.isBalanced() && !isSegmentBalanced(ase.getElementType()))
				return false;
		}
		return true;
	}   // isSegmentBalanced

	/**
	 * Is Source Segment balanced.
	 * 
	 * @param segmentType - see AcctSchemaElement.SEGMENT_* Implemented only for Org Other sensible candidates are Project, User1/2
	 * @return true if segments are balanced
	 */
	public boolean isSegmentBalanced(String segmentType)
	{
		if (segmentType.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
		{
			HashMap<Integer, BigDecimal> map = new HashMap<>();
			// Add up values by key
			for (int i = 0; i < m_lines.size(); i++)
			{
				FactLine line = m_lines.get(i);
				Integer key = new Integer(line.getAD_Org_ID());
				BigDecimal bal = line.getSourceBalance();
				BigDecimal oldBal = map.get(key);
				if (oldBal != null)
					bal = bal.add(oldBal);
				map.put(key, bal);
				// System.out.println("Add Key=" + key + ", Bal=" + bal + " <- " + line);
			}
			// check if all keys are zero
			Iterator<BigDecimal> values = map.values().iterator();
			while (values.hasNext())
			{
				BigDecimal bal = values.next();
				if (bal.signum() != 0)
				{
					map.clear();
					log.warn("(" + segmentType + ") NO - " + toString() + ", Balance=" + bal);
					return false;
				}
			}
			map.clear();
			log.trace("(" + segmentType + ") - " + toString());
			return true;
		}
		log.trace("(" + segmentType + ") (not checked) - " + toString());
		return true;
	}   // isSegmentBalanced

	/**
	 * Balance all segments. - For all balancing segments - For all segment values - If balance <> 0 create dueTo/dueFrom line overwriting the segment value
	 */
	public void balanceSegments()
	{
		MAcctSchemaElement[] elements = m_acctSchema.getAcctSchemaElements();
		// check all balancing segments
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			if (ase.isBalanced())
				balanceSegment(ase.getElementType());
		}
	}   // balanceSegments

	/**
	 * Balance Source Segment
	 * 
	 * @param elementType segment element type
	 */
	private void balanceSegment(String elementType)
	{
		// no lines -> balanced
		if (m_lines.size() == 0)
			return;

		log.debug("(" + elementType + ") - " + toString());

		// Org
		if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
		{
			HashMap<Integer, Balance> map = new HashMap<>();
			// Add up values by key
			for (int i = 0; i < m_lines.size(); i++)
			{
				FactLine line = m_lines.get(i);
				Integer key = new Integer(line.getAD_Org_ID());
				// BigDecimal balance = line.getSourceBalance();
				Balance oldBalance = map.get(key);
				if (oldBalance == null)
				{
					oldBalance = new Balance(line.getAmtSourceDr(), line.getAmtSourceCr());
					map.put(key, oldBalance);
				}
				else
					oldBalance.add(line.getAmtSourceDr(), line.getAmtSourceCr());
				// log.info("Key=" + key + ", Balance=" + balance + " - " + line);
			}

			// Create entry for non-zero element
			Iterator<Integer> keys = map.keySet().iterator();
			while (keys.hasNext())
			{
				Integer key = keys.next();
				Balance difference = map.get(key);
				log.info(elementType + "=" + key + ", " + difference);
				//
				if (!difference.isZeroBalance())
				{
					// Create Balancing Entry
					final FactLine line = new FactLine(m_doc.getCtx(), m_doc.get_Table_ID(), m_doc.get_ID(), 0, get_TrxName());
					line.setDocumentInfo(m_doc, null);
					line.setPostingType(m_postingType);
					// Amount & Account
					if (difference.getBalance().signum() < 0)
					{
						if (difference.isReversal())
						{
							line.setAccount(m_acctSchema, m_acctSchema.getDueTo_Acct(elementType));
							line.setAmtSource(m_doc.getC_Currency_ID(), BigDecimal.ZERO, difference.getPostBalance());
						}
						else
						{
							line.setAccount(m_acctSchema, m_acctSchema.getDueFrom_Acct(elementType));
							line.setAmtSource(m_doc.getC_Currency_ID(), difference.getPostBalance(), BigDecimal.ZERO);
						}
					}
					else
					{
						if (difference.isReversal())
						{
							line.setAccount(m_acctSchema, m_acctSchema.getDueFrom_Acct(elementType));
							line.setAmtSource(m_doc.getC_Currency_ID(), difference.getPostBalance(), BigDecimal.ZERO);
						}
						else
						{
							line.setAccount(m_acctSchema, m_acctSchema.getDueTo_Acct(elementType));
							line.setAmtSource(m_doc.getC_Currency_ID(), BigDecimal.ZERO, difference.getPostBalance());
						}
					}
					line.convert();
					line.setAD_Org_ID(key.intValue());
					//
					add(line);
					log.debug("({}) - {}", elementType, line);
				}
			}
			map.clear();
		}
	}   // balanceSegment

	/**************************************************************************
	 * Are the lines Accounting Balanced
	 * 
	 * @return true if accounting lines are balanced
	 */
	public boolean isAcctBalanced()
	{
		// no lines -> balanced
		if (m_lines.size() == 0)
			return true;
		BigDecimal balance = getAcctBalance();
		boolean retValue = balance.signum() == 0;
		if (retValue)
			log.trace(toString());
		else
			log.warn("NO - Diff=" + balance + " - " + toString());
		return retValue;
	}	// isAcctBalanced

	/**
	 * Return Accounting Balance
	 * 
	 * @return true if accounting lines are balanced
	 */
	protected BigDecimal getAcctBalance()
	{
		BigDecimal result = BigDecimal.ZERO;
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine line = m_lines.get(i);
			result = result.add(line.getAcctBalance());
		}
		// log.debug(result.toString());
		return result;
	}	// getAcctBalance

	/**
	 * Balance Accounting Currency. If the accounting currency is not balanced, if Currency balancing is enabled create a new line using the currency balancing account with zero source balance or
	 * adjust the line with the largest balance sheet account or if no balance sheet account exist, the line with the largest amount
	 * 
	 * @return FactLine
	 */
	public FactLine balanceAccounting()
	{
		BigDecimal diff = getAcctBalance();		// DR-CR
		log.debug("Balance=" + diff
				+ ", CurrBal=" + m_acctSchema.isCurrencyBalancing()
				+ " - " + toString());
		FactLine line = null;

		BigDecimal BSamount = BigDecimal.ZERO;
		FactLine BSline = null;
		BigDecimal PLamount = BigDecimal.ZERO;
		FactLine PLline = null;

		//
		// Find line biggest BalanceSheet or P&L line
		final int acctCurrencyId = getAcctSchema().getC_Currency_ID();
		for (final FactLine l : m_lines)
		{
			// Consider only the lines which are in foreign currency
			if (l.getC_Currency_ID() == acctCurrencyId)
			{
				continue;
			}

			final BigDecimal amt = l.getAcctBalance().abs();
			if (l.isBalanceSheet() && amt.compareTo(BSamount) > 0)
			{
				BSamount = amt;
				BSline = l;
			}
			else if (!l.isBalanceSheet() && amt.compareTo(PLamount) > 0)
			{
				PLamount = amt;
				PLline = l;
			}
		}

		// Create Currency Balancing Entry
		if (m_acctSchema.isCurrencyBalancing())
		{
			line = new FactLine(m_doc.getCtx(), m_doc.get_Table_ID(), m_doc.get_ID(), 0, get_TrxName());
			line.setDocumentInfo(m_doc, null);
			line.setPostingType(m_postingType);
			line.setAccount(m_acctSchema, m_acctSchema.getCurrencyBalancing_Acct());

			// Amount
			line.setAmtSource(m_doc.getC_Currency_ID(), BigDecimal.ZERO, BigDecimal.ZERO);
			line.convert();
			// Accounted
			BigDecimal drAmt = BigDecimal.ZERO;
			BigDecimal crAmt = BigDecimal.ZERO;
			boolean isDR = diff.signum() < 0;
			BigDecimal difference = diff.abs();
			if (isDR)
				drAmt = difference;
			else
				crAmt = difference;
			// Switch sides
			boolean switchIt = BSline != null
					&& ((BSline.isDrSourceBalance() && isDR)
							|| (!BSline.isDrSourceBalance() && !isDR));
			if (switchIt)
			{
				drAmt = BigDecimal.ZERO;
				crAmt = BigDecimal.ZERO;
				if (isDR)
					crAmt = difference.negate();
				else
					drAmt = difference.negate();
			}
			line.setAmtAcct(drAmt, crAmt);
			add(line);
		}
		else
		// Adjust biggest (Balance Sheet) line amount
		{
			if (BSline != null)
				line = BSline;
			else
				line = PLline;
			if (line == null)
				log.error("No Line found");
			else
			{
				log.debug("Adjusting Amt=" + diff + "; Line=" + line);
				line.currencyCorrect(diff);
				log.debug(line.toString());
			}
		}   // correct biggest amount

		return line;
	}   // balanceAccounting

	/**
	 * Check Accounts of Fact Lines
	 *
	 * @return true if success
	 */
	public boolean checkAccounts()
	{
		// no lines -> nothing to distribute
		if (m_lines.isEmpty())
		{
			return true;
		}

		// For all fact lines
		for (int i = 0; i < m_lines.size(); i++)
		{
			final FactLine line = m_lines.get(i);

			final MAccount account = line.getAccount();
			if (account == null)
			{
				log.warn("No Account for " + line);
				return false;
			}

			final I_C_ElementValue ev = account.getAccount();
			if (ev == null)
			{
				log.warn("No Element Value for " + account + ": " + line);
				return false;
			}
			if (ev.isSummary())
			{
				log.warn("Cannot post to Summary Account " + ev
						+ ": " + line);
				return false;
			}
			if (!ev.isActive())
			{
				log.warn("Cannot post to Inactive Account " + ev
						+ ": " + line);
				return false;
			}

		}	// for all lines

		return true;
	}	// checkAccounts

	/**
	 * GL Distribution of Fact Lines
	 */
	public void distribute()
	{
		final List<FactLine> linesAfterDistribution = FactGLDistributor.newInstance()
				.distribute(m_lines);

		m_lines = linesAfterDistribution;
		// TODO
	}

	/**************************************************************************
	 * String representation
	 * 
	 * @return String
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Fact[");
		sb.append(m_doc.toString());
		sb.append(",").append(m_acctSchema.toString());
		sb.append(",PostType=").append(m_postingType);
		sb.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Get Lines
	 * 
	 * @return FactLine Array
	 */
	public FactLine[] getLines()
	{
		final FactLine[] temp = new FactLine[m_lines.size()];
		m_lines.toArray(temp);
		return temp;
	}	// getLines

	/**
	 * Save Fact
	 * 
	 * @param trxName transaction
	 * @return true if all lines were saved
	 */
	public final void save(final String trxName)
	{
		m_trxName = trxName;

		factTrxLinesStrategy
				.createFactTrxLines(m_lines)
				.forEach(this::save);
	}

	private void save(final FactTrxLines factTrxLines)
	{
		final String trxName = m_trxName;

		//
		// Case: 1 debit line, one or more credit lines
		if (factTrxLines.getType() == FactTrxLinesType.Debit)
		{
			final FactLine drLine = factTrxLines.getDebitLine();
			InterfaceWrapperHelper.save(drLine, trxName);

			factTrxLines.forEachCreditLine(crLine -> {
				crLine.setCounterpart_Fact_Acct_ID(drLine.getFact_Acct_ID());
				InterfaceWrapperHelper.save(crLine, trxName);
			});

		}
		//
		// Case: 1 credit line, one or more debit lines
		else if (factTrxLines.getType() == FactTrxLinesType.Credit)
		{
			final FactLine crLine = factTrxLines.getCreditLine();
			InterfaceWrapperHelper.save(crLine, trxName);

			factTrxLines.forEachDebitLine(drLine -> {
				drLine.setCounterpart_Fact_Acct_ID(crLine.getFact_Acct_ID());
				InterfaceWrapperHelper.save(drLine, trxName);
			});
		}
		//
		// Case: no debit lines, no credit lines
		else if (factTrxLines.getType() == FactTrxLinesType.EmptyOrZero)
		{
			// nothing to do
		}
		else
		{
			throw new AdempiereException("Unknown type: " + factTrxLines.getType());
		}

		//
		// also save the zero lines, if they are here
		factTrxLines.forEachZeroLine(zeroLine -> InterfaceWrapperHelper.save(zeroLine, trxName));
	}

	/**
	 * Get Transaction
	 *
	 * @return trx
	 */
	public final String get_TrxName()
	{
		return m_trxName;
	}	// getTrxName

	/**
	 * Fact Balance Utility
	 *
	 * @author Jorg Janke
	 * @version $Id: Fact.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
	 */
	private static final class Balance
	{
		/**
		 * New Balance
		 *
		 * @param dr DR
		 * @param cr CR
		 */
		public Balance(final BigDecimal dr, final BigDecimal cr)
		{
			super();
			DR = dr;
			CR = cr;
		}

		/** DR Amount */
		private BigDecimal DR = BigDecimal.ZERO;
		/** CR Amount */
		private BigDecimal CR = BigDecimal.ZERO;

		/**
		 * Add
		 *
		 * @param dr DR
		 * @param cr CR
		 */
		public void add(BigDecimal dr, BigDecimal cr)
		{
			DR = DR.add(dr);
			CR = CR.add(cr);
		}

		/**
		 * Get Balance
		 *
		 * @return balance
		 */
		public BigDecimal getBalance()
		{
			return DR.subtract(CR);
		}	// getBalance

		/**
		 * Get Post Balance
		 *
		 * @return absolute balance - negative if reversal
		 */
		public BigDecimal getPostBalance()
		{
			BigDecimal bd = getBalance().abs();
			if (isReversal())
				return bd.negate();
			return bd;
		}	// getPostBalance

		/**
		 * Zero Balance
		 *
		 * @return true if 0
		 */
		public boolean isZeroBalance()
		{
			return getBalance().signum() == 0;
		}	// isZeroBalance

		/**
		 * Reversal
		 *
		 * @return true if both DR/CR are negative or zero
		 */
		public boolean isReversal()
		{
			return DR.signum() <= 0 && CR.signum() <= 0;
		}	// isReversal

		/**
		 * String Representation
		 *
		 * @return info
		 */
		@Override
		public String toString()
		{
			final StringBuilder sb = new StringBuilder("Balance[");
			sb.append("DR=").append(DR)
					.append("-CR=").append(CR)
					.append(" = ").append(getBalance())
					.append("]");
			return sb.toString();
		} // toString
	}	// Balance

	public static final class FactLineBuilder
	{
		private boolean built = false;

		@ToStringBuilder(skip = true)
		private final Fact fact;
		private DocLine docLine = null;
		private Integer subLineId = null;

		private MAccount account = null;

		private int currencyId;
		private ICurrencyConversionContext currencyConversionCtx;
		private BigDecimal amtSourceDr;
		private BigDecimal amtSourceCr;

		private BigDecimal qty = null;

		// Other dimensions
		private Integer AD_Org_ID;
		private Integer C_BPartner_ID;
		private Integer C_Tax_ID;

		private FactLineBuilder(final Fact fact)
		{
			super();
			this.fact = fact;
		}

		/**
		 * Creates the {@link FactLine} and adds it to {@link Fact}.
		 * 
		 * @return created {@link FactLine}
		 */
		public FactLine buildAndAdd()
		{
			final FactLine fl = build();

			if (fl != null)
			{
				fact.add(fl);
			}

			return fl;
		}

		private final FactLine build()
		{
			markAsBuilt();

			// Data Check
			final MAccount account = getAccount();
			if (account == null)
			{
				log.info("No account for {}", this);
				return null;
			}

			//
			final Doc doc = getDoc();
			final DocLine docLine = getDocLine();
			final FactLine line = new FactLine(doc.getCtx(),
					doc.get_Table_ID(), // AD_Table_ID
					doc.get_ID(), // Record_ID
					docLine == null ? 0 : docLine.get_ID(), // Line_ID
					getTrxName());

			// Set Document, Line, Sub Line
			line.setDocumentInfo(doc, docLine);
			final Integer subLine_ID = getSubLine_ID();
			if (subLine_ID != null)
			{
				line.setSubLine_ID(subLine_ID);
			}

			// Account
			line.setPostingType(getPostingType());
			line.setAccount(getC_AcctSchema(), account);

			//
			// Qty
			final BigDecimal qty = getQty();
			if (qty != null)
			{
				line.setQty(qty);
			}

			//
			// Amounts - one needs to not zero
			final int currencyId = getC_Currency_ID();
			final BigDecimal amtSourceDr = getAmtSourceDr();
			final BigDecimal amtSourceCr = getAmtSourceCr();
			line.setAmtSource(currencyId, amtSourceDr, amtSourceCr);
			if (line.isZeroAmtSource())
			{
				if (line.getQty().signum() == 0)
				{
					log.debug("Both amounts & qty = 0/Null - {}", this);
					return null;
				}

				if (log.isDebugEnabled())
					log.debug("Both amounts = 0/Null, Qty=" + (docLine == null ? "<NULL>" : docLine.getQty()) + " - docLine=" + (docLine == null ? "<NULL>" : docLine) + " - " + toString());
			}

			//
			// Currency convert
			final ICurrencyConversionContext currencyConversionCtx = getCurrencyConversionCtx();
			if (currencyConversionCtx != null)
			{
				line.setCurrencyConversionCtx(currencyConversionCtx);
				line.addDescription(currencyConversionCtx.getSummary());
			}
			line.convert();

			//
			// Optionally overwrite Acct Amount
			if (docLine != null && (docLine.getAmtAcctDr() != null || docLine.getAmtAcctCr() != null))
			{
				line.setAmtAcct(docLine.getAmtAcctDr(), docLine.getAmtAcctCr());
			}

			//
			// Set the other dimensions
			final Integer adOrgId = getAD_Org_ID();
			if (adOrgId != null)
			{
				line.setAD_Org_ID(adOrgId);
			}
			//
			final Integer bpartnerId = getC_BPartner_ID();
			if (bpartnerId != null)
			{
				line.setC_BPartner_ID(bpartnerId);
			}
			//
			final Integer taxId = getC_Tax_ID();
			if (taxId != null)
			{
				line.setC_Tax_ID(taxId);
			}

			//
			log.debug("Built: {}", line);
			return line;
		}

		private final void assertNotBuild()
		{
			Check.assume(!built, "not already built");
		}

		private final void markAsBuilt()
		{
			assertNotBuild();
			built = true;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public FactLineBuilder setAccount(MAccount account)
		{
			assertNotBuild();
			this.account = account;
			return this;
		}

		private final MAccount getAccount()
		{
			// TODO: check if we can enforce it all the time
			// Check.assumeNotNull(account, "account not null for {}", this);
			return account;
		}

		private final Doc getDoc()
		{
			return fact.m_doc;
		}

		public final FactLineBuilder setDocLine(DocLine docLine)
		{
			assertNotBuild();
			this.docLine = docLine;
			return this;
		}

		private final DocLine getDocLine()
		{
			return docLine;
		}

		public final FactLineBuilder setSubLine_ID(final int subLineId)
		{
			this.subLineId = subLineId;
			return this;
		}

		private final Integer getSubLine_ID()
		{
			return subLineId;
		}

		private final MAcctSchema getC_AcctSchema()
		{
			return fact.getAcctSchema();
		}

		private final String getPostingType()
		{
			return fact.getPostingType();
		}

		private final String getTrxName()
		{
			return fact.get_TrxName();
		}

		public FactLineBuilder setQty(BigDecimal qty)
		{
			assertNotBuild();
			this.qty = qty;
			return this;
		}

		private final BigDecimal getQty()
		{
			return qty;
		}

		public FactLineBuilder setAmtSource(final int currencyId, final BigDecimal amtSourceDr, final BigDecimal amtSourceCr)
		{
			setC_Currency_ID(currencyId);
			setAmtSource(amtSourceDr, amtSourceCr);
			return this;
		}

		public FactLineBuilder setAmtSource(final BigDecimal amtSourceDr, final BigDecimal amtSourceCr)
		{
			assertNotBuild();
			this.amtSourceDr = amtSourceDr;
			this.amtSourceCr = amtSourceCr;
			return this;
		}

		public FactLineBuilder setC_Currency_ID(final int currencyId)
		{
			assertNotBuild();
			this.currencyId = currencyId;
			return this;
		}

		private final int getC_Currency_ID()
		{
			return currencyId;
		}

		public FactLineBuilder setCurrencyConversionCtx(ICurrencyConversionContext currencyConversionCtx)
		{
			assertNotBuild();
			this.currencyConversionCtx = currencyConversionCtx;
			return this;
		}

		private final ICurrencyConversionContext getCurrencyConversionCtx()
		{
			return currencyConversionCtx;
		}

		private final BigDecimal getAmtSourceDr()
		{
			return amtSourceDr;
		}

		private final BigDecimal getAmtSourceCr()
		{
			return amtSourceCr;
		}

		public FactLineBuilder setAccountDrOrCrAndAmount(final MAccount accountDr, final MAccount accountCr, final BigDecimal amt)
		{
			if (amt.signum() < 0)
			{
				setAccount(accountCr);
				setAmtSource(null, amt.abs());
			}
			else
			{
				setAccount(accountDr);
				setAmtSource(amt, null);
			}
			return this;
		}   // createLine

		/**
		 * Sets the AmtSourceDr (if amtSource is positive) or AmtSourceCr (if amtSource is negative).
		 * 
		 * @param amtSource
		 */
		public FactLineBuilder setAmtSourceDrOrCr(final BigDecimal amtSource)
		{
			if (amtSource.signum() < 0)
			{
				setAmtSource(null, amtSource.abs());
			}
			else
			{
				setAmtSource(amtSource, null);
			}
			return this;
		}

		public FactLineBuilder setAD_Org_ID(Integer adOrgId)
		{
			assertNotBuild();
			this.AD_Org_ID = adOrgId;
			return this;
		}

		public FactLineBuilder setAD_Org_ID_IfValid(final int adOrgId)
		{
			assertNotBuild();
			if (adOrgId > 0 && adOrgId != Env.CTXVALUE_AD_Org_ID_System)
			{
				setAD_Org_ID(adOrgId);
			}
			return this;
		}

		private Integer getAD_Org_ID()
		{
			return AD_Org_ID;
		}

		public FactLineBuilder setC_BPartner_ID(Integer bpartnerId)
		{
			assertNotBuild();
			this.C_BPartner_ID = bpartnerId;
			return this;
		}

		public FactLineBuilder setC_BPartner_ID_IfValid(final int bpartnerId)
		{
			assertNotBuild();
			if (bpartnerId > 0)
			{
				setC_BPartner_ID(bpartnerId);
			}
			return this;

		}

		private Integer getC_BPartner_ID()
		{
			return C_BPartner_ID;
		}

		public FactLineBuilder setC_Tax_ID(Integer taxId)
		{
			assertNotBuild();
			this.C_Tax_ID = taxId;
			return this;
		}

		private Integer getC_Tax_ID()
		{
			return C_Tax_ID;
		}
	}
}   // Fact
