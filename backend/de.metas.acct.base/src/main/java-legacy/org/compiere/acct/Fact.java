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
import java.util.function.Consumer;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.acct.FactTrxLines.FactTrxLinesType;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.MAccount;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaElementsMap;
import de.metas.acct.api.AcctSchemaGeneralLedger;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Accounting Fact
 *
 * @author Jorg Janke
 * @version $Id: Fact.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 * <p>
 * BF [ 2789949 ] Multicurrency in matching posting
 */
public final class Fact
{
	/**
	 * Constructor
	 *
	 * @param document    pointer to document
	 * @param acctSchema  Account Schema to create accounts
	 * @param postingType the default Posting type (actual,..) for this posting
	 */
	public Fact(
			@NonNull final Doc<?> document,
			@NonNull final AcctSchema acctSchema,
			@NonNull final PostingType postingType)
	{
		this.m_doc = document;
		this.acctSchema = acctSchema;
		this.postingType = postingType;
	}

	// services
	static final transient Logger log = LogManager.getLogger(Fact.class);

	/**
	 * Document
	 */
	final Doc<?> m_doc;
	/**
	 * Accounting Schema
	 */
	private final AcctSchema acctSchema;

	/**
	 * Posting Type
	 */
	private final PostingType postingType;

	/**
	 * Is Converted
	 */
	private boolean m_converted = false;

	/**
	 * Lines
	 */
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
	public void dispose()
	{
		m_lines.clear();
		m_lines = null;
	}   // dispose

	/**
	 * Create and convert Fact Line. Used to create a DR and/or CR entry
	 *
	 * @param docLine    the document line or null
	 * @param account    if null, line is not created
	 * @param currencyId the currency
	 * @param debitAmt   debit amount, can be null
	 * @param creditAmt  credit amount, can be null
	 * @return Fact Line
	 */
	public FactLine createLine(final DocLine<?> docLine,
			final MAccount account,
			final CurrencyId currencyId,
			@Nullable final BigDecimal debitAmt, @Nullable final BigDecimal creditAmt)
	{
		return createLine()
				.setDocLine(docLine)
				.setAccount(account)
				.setAmtSource(currencyId, debitAmt, creditAmt)
				.buildAndAdd();
	}

	public FactLine createLine(final DocLine<?> docLine,
			final MAccount account,
			final int C_Currency_ID,
			final BigDecimal debitAmt, final BigDecimal creditAmt)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(C_Currency_ID);
		return createLine(docLine, account, currencyId, debitAmt, creditAmt);
	}

	public FactLineBuilder createLine()
	{
		return new FactLineBuilder(this);
	}

	/**
	 * Create and convert Fact Line. Used to create a DR and/or CR entry.
	 *
	 * @param docLine    the document line or null
	 * @param account    if null, line is not created
	 * @param currencyId the currency
	 * @param debitAmt   debit amount, can be null
	 * @param creditAmt  credit amount, can be null
	 * @param qty        quantity, can be null and in that case the standard qty from DocLine/Doc will be used.
	 * @return Fact Line or null
	 */
	public FactLine createLine(final DocLine<?> docLine,
			final MAccount account,
			final CurrencyId currencyId,
			final BigDecimal debitAmt, final BigDecimal creditAmt,
			final BigDecimal qty)
	{
		return createLine()
				.setDocLine(docLine)
				.setAccount(account)
				.setAmtSource(currencyId, debitAmt, creditAmt)
				.setQty(qty)
				.buildAndAdd();
	}    // createLine

	public FactLine createLine(final DocLine<?> docLine,
			final MAccount account,
			final int C_Currency_ID,
			final BigDecimal debitAmt, final BigDecimal creditAmt,
			final BigDecimal qty)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(C_Currency_ID);
		return createLine(docLine, account, currencyId, debitAmt, creditAmt, qty);
	}

	/**
	 * Add Fact Line
	 *
	 * @param line fact line
	 */
	void add(final FactLine line)
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
	 * @param docLine    Document line or null
	 * @param account    Account to be used
	 * @param currencyId Currency
	 * @param Amt        if negative Cr else Dr
	 * @return FactLine
	 */
	public FactLine createLine(DocLine<?> docLine, MAccount account, CurrencyId currencyId, BigDecimal Amt)
	{
		return createLine()
				.setDocLine(docLine)
				.setCurrencyId(currencyId)
				.setAccount(account)
				.setAmtSourceDrOrCr(Amt)
				.buildAndAdd();
	}   // createLine

	/**
	 * Is converted
	 *
	 * @return true if converted
	 */
	public boolean isConverted()
	{
		return m_converted;
	}    // isConverted

	/**
	 * Get AcctSchema
	 *
	 * @return AcctSchema; never returns null
	 */
	public AcctSchema getAcctSchema()
	{
		return acctSchema;
	}    // getAcctSchema

	public AcctSchemaId getAcctSchemaId()
	{
		return getAcctSchema().getId();
	}

	private AcctSchemaElementsMap getAcctSchemaElements()
	{
		return getAcctSchema().getSchemaElements();
	}

	public PostingType getPostingType()
	{
		return postingType;
	}

	public boolean isSingleCurrency()
	{
		final ImmutableList<CurrencyId> distinctCurrencyIds = CollectionUtils.extractDistinctElements(m_lines, FactLine::getCurrencyId);

		final boolean lessThanTwoCurrencies = distinctCurrencyIds.size() < 2;
		return lessThanTwoCurrencies;
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
		{
			return true;
		}
		BigDecimal balance = getSourceBalance();
		boolean retValue = balance.signum() == 0;
		if (retValue)
		{
			log.trace("{}", this);
		}
		else
		{
			log.warn("NO - Diff=" + balance + " - " + toString());
		}
		return retValue;
	}    // isSourceBalanced

	/**
	 * Return Source Balance
	 *
	 * @return source balance
	 */
	protected BigDecimal getSourceBalance()
	{
		BigDecimal result = BigDecimal.ZERO;
		for (FactLine line : m_lines)
		{
			result = result.add(line.getSourceBalance());
		}
		// log.debug("getSourceBalance - " + result.toString());
		return result;
	}    // getSourceBalance

	/**
	 * Create Source Line for Suspense Balancing. Only if Suspense Balancing is enabled and not a multi-currency document (double check as otherwise the rule should not have fired) If not balanced
	 * create balancing entry in currency of the document
	 *
	 * @return FactLine
	 */
	public FactLine balanceSource()
	{
		final AcctSchema acctSchema = getAcctSchema();
		final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
		if (!acctSchemaGL.isSuspenseBalancing() || m_doc.isMultiCurrency())
		{
			return null;
		}
		BigDecimal diff = getSourceBalance();
		log.trace("Diff=" + diff);

		// new line
		FactLine line = new FactLine(m_doc.get_Table_ID(), m_doc.get_ID());
		line.setDocumentInfo(m_doc, null);
		line.setPostingType(getPostingType());

		// Account
		line.setAccount(acctSchema, acctSchemaGL.getSuspenseBalancingAcctId());

		// Amount
		if (diff.signum() < 0)
		{
			line.setAmtSource(m_doc.getCurrencyId(), diff.abs(), BigDecimal.ZERO);
		}
		else
		{
			// positive balance => CR
			line.setAmtSource(m_doc.getCurrencyId(), BigDecimal.ZERO, diff);
		}

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
		{
			return true;
		}

		// check all balancing segments
		for (AcctSchemaElement ase : getAcctSchemaElements())
		{
			final AcctSchemaElementType elementType = ase.getElementType();
			if (ase.isBalanced() && !isSegmentBalanced(elementType))
			{
				return false;
			}
		}

		return true;
	}   // isSegmentBalanced

	/**
	 * Is Source Segment balanced.
	 *
	 * @param segmentType - see AcctSchemaElement.SEGMENT_* Implemented only for Org Other sensible candidates are Project, User1/2
	 * @return true if segments are balanced
	 */
	private boolean isSegmentBalanced(final AcctSchemaElementType segmentType)
	{
		if (segmentType.equals(AcctSchemaElementType.Organization))
		{
			HashMap<Integer, BigDecimal> map = new HashMap<>();
			// Add up values by key
			for (FactLine line : m_lines)
			{
				Integer key = new Integer(line.getAD_Org_ID());
				BigDecimal bal = line.getSourceBalance();
				BigDecimal oldBal = map.get(key);
				if (oldBal != null)
				{
					bal = bal.add(oldBal);
				}
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
			return true;
		}
		else
		{
			return true;
		}
	}   // isSegmentBalanced

	/**
	 * Balance all segments. - For all balancing segments - For all segment values - If balance <> 0 create dueTo/dueFrom line overwriting the segment value
	 */
	public void balanceSegments()
	{
		// check all balancing segments
		for (final AcctSchemaElement ase : getAcctSchemaElements())
		{
			if (ase.isBalanced())
			{
				balanceSegment(ase.getElementType());
			}
		}
	}   // balanceSegments

	/**
	 * Balance Source Segment
	 *
	 * @param elementType segment element type
	 */
	private void balanceSegment(final AcctSchemaElementType elementType)
	{
		// no lines -> balanced
		if (m_lines.isEmpty())
		{
			return;
		}

		// Org
		if (elementType.equals(AcctSchemaElementType.Organization))
		{
			HashMap<Integer, Balance> map = new HashMap<>();
			// Add up values by key
			for (FactLine line : m_lines)
			{
				Integer key = new Integer(line.getAD_Org_ID());
				// BigDecimal balance = line.getSourceBalance();
				Balance oldBalance = map.get(key);
				if (oldBalance == null)
				{
					oldBalance = new Balance(line.getAmtSourceDr(), line.getAmtSourceCr());
					map.put(key, oldBalance);
				}
				else
				{
					oldBalance.add(line.getAmtSourceDr(), line.getAmtSourceCr());
					// log.info("Key=" + key + ", Balance=" + balance + " - " + line);
				}
			}

			// Create entry for non-zero element
			Iterator<Integer> keys = map.keySet().iterator();
			while (keys.hasNext())
			{
				Integer key = keys.next();
				Balance difference = map.get(key);

				//
				if (!difference.isZeroBalance())
				{
					// Create Balancing Entry
					final FactLine line = new FactLine(m_doc.get_Table_ID(), m_doc.get_ID());
					line.setDocumentInfo(m_doc, null);
					line.setPostingType(getPostingType());
					// Amount & Account
					final AcctSchema acctSchema = getAcctSchema();
					final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
					if (difference.getBalance().signum() < 0)
					{
						if (difference.isReversal())
						{
							line.setAccount(acctSchema, acctSchemaGL.getDueToAcctId(elementType));
							line.setAmtSource(m_doc.getCurrencyId(), BigDecimal.ZERO, difference.getPostBalance());
						}
						else
						{
							line.setAccount(acctSchema, acctSchemaGL.getDueFromAcct(elementType));
							line.setAmtSource(m_doc.getCurrencyId(), difference.getPostBalance(), BigDecimal.ZERO);
						}
					}
					else
					{
						if (difference.isReversal())
						{
							line.setAccount(acctSchema, acctSchemaGL.getDueFromAcct(elementType));
							line.setAmtSource(m_doc.getCurrencyId(), difference.getPostBalance(), BigDecimal.ZERO);
						}
						else
						{
							line.setAccount(acctSchema, acctSchemaGL.getDueToAcctId(elementType));
							line.setAmtSource(m_doc.getCurrencyId(), BigDecimal.ZERO, difference.getPostBalance());
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
		{
			return true;
		}
		BigDecimal balance = getAcctBalance();
		boolean retValue = balance.signum() == 0;
		if (retValue)
		{
			log.trace(toString());
		}
		else
		{
			log.warn("NO - Diff=" + balance + " - " + toString());
		}
		return retValue;
	}    // isAcctBalanced

	/**
	 * Return Accounting Balance
	 *
	 * @return true if accounting lines are balanced
	 */
	protected BigDecimal getAcctBalance()
	{
		BigDecimal result = BigDecimal.ZERO;
		for (FactLine line : m_lines)
		{
			result = result.add(line.getAcctBalance());
		}
		// log.debug(result.toString());
		return result;
	}    // getAcctBalance

	/**
	 * Balance Accounting Currency. If the accounting currency is not balanced, if Currency balancing is enabled create a new line using the currency balancing account with zero source balance or
	 * adjust the line with the largest balance sheet account or if no balance sheet account exist, the line with the largest amount
	 *
	 * @return FactLine
	 */
	public FactLine balanceAccounting()
	{
		BigDecimal diff = getAcctBalance();        // DR-CR
		FactLine line = null;

		BigDecimal BSamount = BigDecimal.ZERO;
		FactLine BSline = null;
		BigDecimal PLamount = BigDecimal.ZERO;
		FactLine PLline = null;

		//
		// Find line biggest BalanceSheet or P&L line
		final CurrencyId acctCurrencyId = getAcctSchema().getCurrencyId();
		for (final FactLine l : m_lines)
		{
			// Consider only the lines which are in foreign currency
			if (acctCurrencyId.equals(l.getCurrencyId()))
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
		final AcctSchema acctSchema = getAcctSchema();
		final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
		if (acctSchemaGL.isCurrencyBalancing())
		{
			line = new FactLine(m_doc.get_Table_ID(), m_doc.get_ID());
			line.setDocumentInfo(m_doc, null);
			line.setPostingType(getPostingType());
			line.setAccount(acctSchema, acctSchemaGL.getCurrencyBalancingAcctId());

			// Amount
			line.setAmtSource(m_doc.getCurrencyId(), BigDecimal.ZERO, BigDecimal.ZERO);
			line.convert();
			// Accounted
			BigDecimal drAmt = BigDecimal.ZERO;
			BigDecimal crAmt = BigDecimal.ZERO;
			boolean isDR = diff.signum() < 0;
			BigDecimal difference = diff.abs();
			if (isDR)
			{
				drAmt = difference;
			}
			else
			{
				crAmt = difference;
			}
			// Switch sides
			boolean switchIt = BSline != null
					&& ((BSline.isDrSourceBalance() && isDR)
					|| (!BSline.isDrSourceBalance() && !isDR));
			if (switchIt)
			{
				drAmt = BigDecimal.ZERO;
				crAmt = BigDecimal.ZERO;
				if (isDR)
				{
					crAmt = difference.negate();
				}
				else
				{
					drAmt = difference.negate();
				}
			}
			line.setAmtAcct(drAmt, crAmt);
			add(line);
		}
		else
		// Adjust biggest (Balance Sheet) line amount
		{
			if (BSline != null)
			{
				line = BSline;
			}
			else
			{
				line = PLline;
			}
			if (line == null)
			{
				log.error("No Line found");
			}
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
	 */
	BooleanWithReason checkAccounts()
	{
		// no lines -> nothing to distribute
		if (m_lines.isEmpty())
		{
			return BooleanWithReason.TRUE;
		}

		// For all fact lines
		for (final FactLine line : m_lines)
		{
			final MAccount account = line.getAccount();
			if (account == null)
			{
				return BooleanWithReason.falseBecause("No Account for " + line);
			}

			final I_C_ElementValue ev = account.getAccount();
			if (ev == null)
			{
				return BooleanWithReason.falseBecause("No Element Value for " + account + ": " + line);
			}
			if (ev.isSummary())
			{
				return BooleanWithReason.falseBecause("Cannot post to Summary Account " + ev + ": " + line);
			}
			if (!ev.isActive())
			{
				return BooleanWithReason.falseBecause("Cannot post to Inactive Account " + ev + ": " + line);
			}

		}    // for all lines

		return BooleanWithReason.TRUE;
	}    // checkAccounts

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
		sb.append(m_doc);
		sb.append(",").append(getAcctSchema());
		sb.append(",PostType=").append(getPostingType());
		sb.append("]");
		return sb.toString();
	}    // toString

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
	}    // getLines

	public void save()
	{
		factTrxLinesStrategy
				.createFactTrxLines(m_lines)
				.forEach(this::save);
	}

	private void save(final FactTrxLines factTrxLines)
	{
		//
		// Case: 1 debit line, one or more credit lines
		if (factTrxLines.getType() == FactTrxLinesType.Debit)
		{
			final FactLine drLine = factTrxLines.getDebitLine();
			InterfaceWrapperHelper.save(drLine, ITrx.TRXNAME_ThreadInherited);

			factTrxLines.forEachCreditLine(crLine -> {
				crLine.setCounterpart_Fact_Acct_ID(drLine.getFact_Acct_ID());
				InterfaceWrapperHelper.save(crLine, ITrx.TRXNAME_ThreadInherited);
			});

		}
		//
		// Case: 1 credit line, one or more debit lines
		else if (factTrxLines.getType() == FactTrxLinesType.Credit)
		{
			final FactLine crLine = factTrxLines.getCreditLine();
			InterfaceWrapperHelper.save(crLine, ITrx.TRXNAME_ThreadInherited);

			factTrxLines.forEachDebitLine(drLine -> {
				drLine.setCounterpart_Fact_Acct_ID(crLine.getFact_Acct_ID());
				InterfaceWrapperHelper.save(drLine, ITrx.TRXNAME_ThreadInherited);
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
		factTrxLines.forEachZeroLine(zeroLine -> InterfaceWrapperHelper.save(zeroLine, ITrx.TRXNAME_ThreadInherited));
	}

	public void forEach(final Consumer<FactLine> consumer)
	{
		m_lines.forEach(consumer);
	}

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

		/**
		 * DR Amount
		 */
		private BigDecimal DR = BigDecimal.ZERO;
		/**
		 * CR Amount
		 */
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
		}    // getBalance

		/**
		 * Get Post Balance
		 *
		 * @return absolute balance - negative if reversal
		 */
		public BigDecimal getPostBalance()
		{
			BigDecimal bd = getBalance().abs();
			if (isReversal())
			{
				return bd.negate();
			}
			return bd;
		}    // getPostBalance

		/**
		 * Zero Balance
		 *
		 * @return true if 0
		 */
		public boolean isZeroBalance()
		{
			return getBalance().signum() == 0;
		}    // isZeroBalance

		/**
		 * Reversal
		 *
		 * @return true if both DR/CR are negative or zero
		 */
		public boolean isReversal()
		{
			return DR.signum() <= 0 && CR.signum() <= 0;
		}    // isReversal

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
	}    // Balance
}   // Fact
