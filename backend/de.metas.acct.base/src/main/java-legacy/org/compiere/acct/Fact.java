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
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaElementsMap;
import de.metas.acct.api.AcctSchemaGeneralLedger;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.PostingType;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.ToString;

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
	private static final transient Logger log = LogManager.getLogger(Fact.class);

	/**
	 * Document
	 */
	private final Doc<?> m_doc;
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
			final BigDecimal debitAmt, final BigDecimal creditAmt)
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
	private void add(final FactLine line)
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

	/**
	 * Save Fact
	 *
	 * @param trxName transaction
	 * @return true if all lines were saved
	 */
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

	@ToString(exclude = "fact")
	public static final class FactLineBuilder
	{
		private boolean built = false;

		private final Fact fact;
		private DocLine<?> docLine = null;
		private Integer subLineId = null;

		private MAccount account = null;

		private CurrencyId currencyId;
		private CurrencyConversionContext currencyConversionCtx;
		private BigDecimal amtSourceDr;
		private BigDecimal amtSourceCr;

		private BigDecimal qty = null;
		private int uomId;

		private boolean alsoAddZeroLine = false;

		// Other dimensions
		private OrgId orgId;
		private BPartnerId bpartnerId;
		private Integer C_Tax_ID;
		private Integer locatorId;
		private ActivityId activityId;

		private FactLineBuilder(final Fact fact)
		{
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

		private FactLine build()
		{
			markAsBuilt();

			// Data Check
			final MAccount account = getAccount();
			if (account == null)
			{
				throw new AdempiereException("No account for " + this);
			}

			//
			final Doc<?> doc = getDoc();
			final DocLine<?> docLine = getDocLine();
			final FactLine line = new FactLine(
					doc.get_Table_ID(), // AD_Table_ID
					doc.get_ID(), // Record_ID
					docLine == null ? 0 : docLine.get_ID()); // Line_ID

			// Set Document, Line, Sub Line
			line.setDocumentInfo(doc, docLine);
			final Integer subLine_ID = getSubLine_ID();
			if (subLine_ID != null)
			{
				line.setSubLine_ID(subLine_ID);
			}

			// Account
			line.setPostingType(getPostingType());
			line.setAccount(getAcctSchema(), account);

			//
			// Qty
			final BigDecimal qty = getQty();
			if (qty != null)
			{
				line.setQty(qty);
			}
			final int uomId = getUomId();
			if (uomId > 0)
			{
				line.setC_UOM_ID(uomId);
			}

			//
			// Amounts - one needs to not zero
			final CurrencyId currencyId = getCurrencyId();
			final BigDecimal amtSourceDr = getAmtSourceDr();
			final BigDecimal amtSourceCr = getAmtSourceCr();
			line.setAmtSource(currencyId, amtSourceDr, amtSourceCr);
			if (line.isZeroAmtSource())
			{
				if (line.getQty().signum() == 0)
				{
					log.debug("Both amounts & qty = 0/Null - {}", this);
					// https://github.com/metasfresh/metasfresh/issues/4147 we might need the zero-line later
					if (!alsoAddZeroLine)
					{
						return null;
					}
				}

				if (log.isDebugEnabled())
				{
					log.debug("Both amounts = 0/Null, Qty=" + (docLine == null ? "<NULL>" : docLine.getQty()) + " - docLine=" + (docLine == null ? "<NULL>" : docLine) + " - " + toString());
				}
			}

			//
			// Currency convert
			final CurrencyConversionContext currencyConversionCtx = getCurrencyConversionCtx();
			if (currencyConversionCtx != null)
			{
				line.setCurrencyConversionCtx(currencyConversionCtx);
				line.addDescription(currencyConversionCtx.getSummary());
			}

			//
			// Optionally overwrite Acct Amount
			if (docLine != null && (docLine.getAmtAcctDr() != null || docLine.getAmtAcctCr() != null))
			{
				line.setAmtAcct(docLine.getAmtAcctDr(), docLine.getAmtAcctCr());
			}
			else
			{
				line.convert();
			}

			//
			// Set the other dimensions
			final Integer locatorId = getLocatorId();
			if (locatorId != null)
			{
				// NOTE: set locator before org because when locator is set, the org is reset.
				line.setM_Locator_ID(locatorId);
			}
			//
			final OrgId orgId = getOrgId();
			if (orgId != null)
			{
				line.setAD_Org_ID(orgId.getRepoId());
			}
			//
			final BPartnerId bpartnerId = getBpartnerId();
			if (bpartnerId != null)
			{
				line.setC_BPartner_ID(bpartnerId.getRepoId());
			}
			//
			final Integer taxId = getC_Tax_ID();
			if (taxId != null)
			{
				line.setC_Tax_ID(taxId);
			}
			//
			final ActivityId activityId = getActivityId();
			if (activityId != null)
			{
				line.setC_Activity_ID(activityId.getRepoId());
			}

			//
			log.debug("Built: {}", line);
			return line;
		}

		private void assertNotBuild()
		{
			Check.assume(!built, "not already built");
		}

		private void markAsBuilt()
		{
			assertNotBuild();
			built = true;
		}

		public FactLineBuilder setAccount(@NonNull final AccountId accountId)
		{
			final IAccountDAO accountsRepo = Services.get(IAccountDAO.class);
			return setAccount(accountsRepo.getById(Env.getCtx(), accountId));
		}

		public FactLineBuilder setAccount(final MAccount account)
		{
			assertNotBuild();
			this.account = account;
			return this;
		}

		private MAccount getAccount()
		{
			// TODO: check if we can enforce it all the time
			// Check.assumeNotNull(account, "account not null for {}", this);
			return account;
		}

		private Doc<?> getDoc()
		{
			return fact.m_doc;
		}

		public FactLineBuilder setDocLine(DocLine<?> docLine)
		{
			assertNotBuild();
			this.docLine = docLine;
			return this;
		}

		private DocLine<?> getDocLine()
		{
			return docLine;
		}

		public FactLineBuilder setSubLine_ID(final int subLineId)
		{
			this.subLineId = subLineId;
			return this;
		}

		private Integer getSubLine_ID()
		{
			return subLineId;
		}

		private AcctSchema getAcctSchema()
		{
			return fact.getAcctSchema();
		}

		private PostingType getPostingType()
		{
			return fact.getPostingType();
		}

		public FactLineBuilder setQty(BigDecimal qty)
		{
			assertNotBuild();
			this.qty = qty;
			return this;
		}

		public FactLineBuilder setQty(final Quantity qty)
		{
			assertNotBuild();
			this.qty = qty.toBigDecimal();
			this.uomId = qty.getUOMId();
			return this;
		}

		private BigDecimal getQty()
		{
			return qty;
		}

		private int getUomId()
		{
			return uomId;
		}

		public FactLineBuilder setAmtSource(final CurrencyId currencyId, final BigDecimal amtSourceDr, final BigDecimal amtSourceCr)
		{
			setCurrencyId(currencyId);
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

		/**
		 * Usually the {@link #buildAndAdd()} method ignores fact lines that have zero/null source amount and zero/null qty.
		 * Invoke this builder method still have the builder add them.
		 */
		public FactLineBuilder alsoAddZeroLine()
		{
			alsoAddZeroLine = true;
			return this;
		}

		public FactLineBuilder setCurrencyId(final CurrencyId currencyId)
		{
			assertNotBuild();
			this.currencyId = currencyId;
			return this;
		}

		private CurrencyId getCurrencyId()
		{
			return currencyId;
		}

		public FactLineBuilder setCurrencyConversionCtx(CurrencyConversionContext currencyConversionCtx)
		{
			assertNotBuild();
			this.currencyConversionCtx = currencyConversionCtx;
			return this;
		}

		private CurrencyConversionContext getCurrencyConversionCtx()
		{
			return currencyConversionCtx;
		}

		private BigDecimal getAmtSourceDr()
		{
			return amtSourceDr;
		}

		private BigDecimal getAmtSourceCr()
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

		@Deprecated
		public FactLineBuilder setAD_Org_ID(Integer adOrgId)
		{
			final OrgId orgId = adOrgId != null ? OrgId.ofRepoIdOrNull(adOrgId) : null;
			return orgId(orgId);
		}

		public FactLineBuilder orgId(final OrgId orgId)
		{
			assertNotBuild();
			this.orgId = orgId;
			return this;
		}

		@Deprecated
		public FactLineBuilder setAD_Org_ID_IfValid(final int adOrgId)
		{
			return orgIdIfValid(OrgId.ofRepoIdOrNull(adOrgId));
		}

		public FactLineBuilder orgIdIfValid(final OrgId orgId)
		{
			if (orgId != null && orgId.isRegular())
			{
				orgId(orgId);
			}
			return this;
		}

		private OrgId getOrgId()
		{
			return orgId;
		}

		@Deprecated
		public FactLineBuilder setC_BPartner_ID(Integer bpartnerRepoId)
		{
			final BPartnerId bpartnerId = bpartnerRepoId != null ? BPartnerId.ofRepoIdOrNull(bpartnerRepoId) : null;
			return bpartnerId(bpartnerId);
		}

		public FactLineBuilder bpartnerId(final BPartnerId bpartnerId)
		{
			assertNotBuild();
			this.bpartnerId = bpartnerId;
			return this;
		}

		public FactLineBuilder bpartnerIdIfNotNull(final BPartnerId bpartnerId)
		{
			if (bpartnerId != null)
			{
				return bpartnerId(bpartnerId);
			}
			else
			{
				return this;
			}
		}

		public FactLineBuilder setC_BPartner_ID_IfValid(final int bpartnerRepoId)
		{
			return bpartnerIdIfNotNull(BPartnerId.ofRepoIdOrNull(bpartnerRepoId));
		}

		private BPartnerId getBpartnerId()
		{
			return bpartnerId;
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

		public FactLineBuilder locatorId(final int locatorId)
		{
			assertNotBuild();
			this.locatorId = locatorId;
			return this;
		}

		private Integer getLocatorId()
		{
			return locatorId;
		}

		public FactLineBuilder activityId(final ActivityId activityId)
		{
			assertNotBuild();
			this.activityId = activityId;
			return this;
		}

		private ActivityId getActivityId()
		{
			return activityId;
		}
	}
}   // Fact
