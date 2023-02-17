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

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaElementsMap;
import de.metas.acct.api.AcctSchemaGeneralLedger;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.currency.CurrencyConversionContext;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.acct.FactTrxLines.FactTrxLinesType;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.MAccount;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

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

	// services
	static final Logger log = LogManager.getLogger(Fact.class);
	@NonNull final AcctDocRequiredServicesFacade services;

	final Doc<?> m_doc;
	private final AcctSchema acctSchema;
	private final PostingType postingType;
	private ArrayList<FactLine> m_lines = new ArrayList<>();
	@Nullable private FactTrxStrategy factTrxLinesStrategy = PerDocumentLineFactTrxStrategy.instance;
	@Nullable private CurrencyConversionContext currencyConversionContext = null;

	public Fact(
			@NonNull final Doc<?> document,
			@NonNull final AcctSchema acctSchema,
			@NonNull final PostingType postingType)
	{
		this.services = document.services;

		this.m_doc = document;
		this.acctSchema = acctSchema;
		this.postingType = postingType;
	}

	public Fact setFactTrxLinesStrategy(@Nullable final FactTrxStrategy factTrxLinesStrategy)
	{
		this.factTrxLinesStrategy = factTrxLinesStrategy;
		return this;
	}

	public Fact setCurrencyConversionContext(@Nullable final CurrencyConversionContext currencyConversionContext)
	{
		this.currencyConversionContext = currencyConversionContext;
		return this;
	}

	@Nullable
	CurrencyConversionContext getCurrencyConversionContext()
	{
		return currencyConversionContext;
	}

	public void dispose()
	{
		m_lines.clear();
		m_lines = null;
	}

	public FactLine createLine(final DocLine<?> docLine,
							   final Account account,
							   @NonNull final CurrencyId currencyId,
							   @Nullable final BigDecimal debitAmt,
							   @Nullable final BigDecimal creditAmt)
	{
		return createLine()
				.setDocLine(docLine)
				.setAccount(account)
				.setAmtSource(currencyId, debitAmt, creditAmt)
				.buildAndAdd();
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
	public FactLine createLine(
			@Nullable final DocLine<?> docLine,
			@NonNull final Account account,
			final CurrencyId currencyId,
			@Nullable final BigDecimal debitAmt,
			@Nullable final BigDecimal creditAmt,
			@Nullable final BigDecimal qty)
	{
		return createLine()
				.setDocLine(docLine)
				.setAccount(account)
				.setAmtSource(currencyId, debitAmt, creditAmt)
				.setQty(qty)
				.buildAndAdd();
	}    // createLine

	public FactLine createLine(final DocLine<?> docLine,
							   final Account account,
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
	@Deprecated
	public FactLine createLine(DocLine<?> docLine, Account account, CurrencyId currencyId, BigDecimal Amt)
	{
		return createLine()
				.setDocLine(docLine)
				.setCurrencyId(currencyId)
				.setAccount(account)
				.setAmtSourceDrOrCr(Amt)
				.buildAndAdd();
	}   // createLine

	public AcctSchema getAcctSchema()
	{
		return acctSchema;
	}

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
		return distinctCurrencyIds.size() < 2;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isSourceBalanced()
	{
		// AZ Goodwill
		// Multi-Currency documents are source balanced by definition
		// No lines -> balanced
		if (m_lines.isEmpty() || m_doc.isMultiCurrency())
		{
			return true;
		}

		Money balance = getSourceBalance();
		if (balance.isZero())
		{
			log.trace("{}", this);
			return true;
		}
		else
		{
			log.warn("NO - Diff={} - {}", balance, this);
			return false;
		}
	}

	private Money getSourceBalance()
	{
		Money result = null;
		for (FactLine line : m_lines)
		{
			final Money lineBalance = line.getSourceBalance();
			result = result == null ? lineBalance : result.add(lineBalance);
		}

		return result != null
				? result
				: Money.zero(acctSchema.getCurrencyId());
	}

	/**
	 * Create Source Line for Suspense Balancing.
	 * Only if Suspense Balancing is enabled and not a multi-currency document (double check as otherwise the rule should not have fired) If not balanced
	 * create balancing entry in currency of the document
	 */
	public void balanceSource()
	{
		final AcctSchema acctSchema = getAcctSchema();
		final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
		if (!acctSchemaGL.isSuspenseBalancing() || m_doc.isMultiCurrency())
		{
			return;
		}

		final Money diff = getSourceBalance();
		log.trace("Diff={}", diff);

		// new line
		final FactLine line = new FactLine(services, m_doc.get_Table_ID(), m_doc.get_ID());
		line.setDocumentInfo(m_doc, null);
		line.setPostingType(getPostingType());

		// Account
		line.setAccount(acctSchema, acctSchemaGL.getSuspenseBalancingAcct());

		// Amount
		if (diff.signum() < 0)
		{
			line.setAmtSource(diff.abs(), null);
		}
		else
		{
			// positive balance => CR
			line.setAmtSource(null, diff);
		}

		line.convert();

		add(line);
	}   // balancingSource

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
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
			final HashMap<OrgId, Money> map = new HashMap<>();
			// Add up values by key
			for (FactLine line : m_lines)
			{
				final Money lineBalance = line.getSourceBalance();
				map.compute(line.getOrgId(), (orgId, prevBalance) -> {
					if (prevBalance == null)
					{
						return lineBalance;
					}
					else
					{
						return prevBalance.add(lineBalance);
					}
				});
			}

			// check if all keys are zero
			for (final Money bal : map.values())
			{
				if (bal.signum() != 0)
				{
					log.warn("({}) NO - {}, Balance={}", segmentType, this, bal);
					return false;
				}
			}

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
			final HashMap<OrgId, Balance> map = new HashMap<>();
			for (FactLine line : m_lines)
			{
				final Balance lineBalance = Balance.ofSourceAmounts(line);

				Balance oldBalance = map.get(line.getOrgId());
				if (oldBalance == null)
				{
					oldBalance = lineBalance;
					map.put(line.getOrgId(), oldBalance);
				}
				else
				{
					oldBalance.add(lineBalance);
				}
			}

			// Create entry for non-zero element
			for (final OrgId orgId : map.keySet())
			{
				final Balance difference = map.get(orgId);

				//
				if (!difference.isZeroBalance())
				{
					// Create Balancing Entry
					final FactLine line = new FactLine(services, m_doc.get_Table_ID(), m_doc.get_ID());
					line.setDocumentInfo(m_doc, null);
					line.setPostingType(getPostingType());
					// Amount & Account
					final AcctSchema acctSchema = getAcctSchema();
					final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
					if (difference.getBalance().signum() < 0)
					{
						if (difference.isReversal())
						{
							line.setAccount(acctSchema, acctSchemaGL.getDueToAcct(elementType));
							line.setAmtSource(null, difference.getPostBalance());
						}
						else
						{
							line.setAccount(acctSchema, acctSchemaGL.getDueFromAcct(elementType));
							line.setAmtSource(difference.getPostBalance(), null);
						}
					}
					else
					{
						if (difference.isReversal())
						{
							line.setAccount(acctSchema, acctSchemaGL.getDueFromAcct(elementType));
							line.setAmtSource(difference.getPostBalance(), null);
						}
						else
						{
							line.setAccount(acctSchema, acctSchemaGL.getDueToAcct(elementType));
							line.setAmtSource(null, difference.getPostBalance());
						}
					}
					line.convert();
					line.setAD_Org_ID(orgId);
					//
					add(line);
					log.debug("({}) - {}", elementType, line);
				}
			}
			map.clear();
		}
	}   // balanceSegment

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isAcctBalanced()
	{
		// no lines -> balanced
		if (m_lines.isEmpty())
		{
			return true;
		}

		final Money balance = getAcctBalance();
		if (balance.isZero())
		{
			return true;
		}
		else
		{
			log.warn("NO - Diff={} - {}", balance, this);
			return false;
		}
	}    // isAcctBalanced

	private Money getAcctBalance()
	{
		return m_lines.stream()
				.map(FactLine::getAcctBalance)
				.reduce(Money::add)
				.orElseGet(() -> Money.zero(acctSchema.getCurrencyId()));
	}

	/**
	 * Balance Accounting Currency.
	 * If the accounting currency is not balanced, if Currency balancing is enabled create a new line using the currency balancing account with zero source balance or
	 * adjust the line with the largest balance sheet account or if no balance sheet account exist, the line with the largest amount
	 */
	public void balanceAccounting()
	{
		final AcctSchema acctSchema = getAcctSchema();
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();
		final Money ZERO = Money.zero(acctCurrencyId);

		Money diff = getAcctBalance();        // DR-CR

		Money BSamount = ZERO;
		FactLine BSline = null;
		Money PLamount = ZERO;
		FactLine PLline = null;

		//
		// Find line biggest BalanceSheet or P&L line
		for (final FactLine l : m_lines)
		{
			// Consider only the lines which are in foreign currency
			if (acctCurrencyId.equals(l.getCurrencyId()))
			{
				continue;
			}

			final Money amt = l.getAcctBalance().abs();
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
		final FactLine line;
		final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
		if (acctSchemaGL.isCurrencyBalancing())
		{
			line = new FactLine(services, m_doc.get_Table_ID(), m_doc.get_ID());
			line.setDocumentInfo(m_doc, null);
			line.setPostingType(getPostingType());
			line.setAccount(acctSchema, acctSchemaGL.getCurrencyBalancingAcct());

			// Amount
			line.setAmtSource(m_doc.getCurrencyId(), BigDecimal.ZERO, BigDecimal.ZERO);
			line.convert();
			// Accounted
			Money drAmt = ZERO;
			Money crAmt = ZERO;
			boolean isDR = diff.signum() < 0;
			Money difference = diff.abs();
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
				drAmt = ZERO;
				crAmt = ZERO;
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
				log.debug("Adjusting Amt={}; Line={}", diff, line);
				line.currencyCorrect(diff);
				log.debug(line.toString());
			}
		}   // correct biggest amount

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

		m_lines = new ArrayList<>(linesAfterDistribution);
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
		return "Fact[" + m_doc
				+ "," + getAcctSchema()
				+ ",PostType=" + getPostingType()
				+ "]";
	}

	public boolean isEmpty()
	{
		return m_lines.isEmpty();
	}

	public FactLine[] getLines()
	{
		final FactLine[] temp = new FactLine[m_lines.size()];
		m_lines.toArray(temp);
		return temp;
	}    // getLines

	@NonNull
	public FactLine getSingleLineByAccountId(final MAccount account)
	{
		FactLine lineFound = null;
		for (FactLine line : m_lines)
		{
			if (line.getAccount_ID() == account.getAccount_ID())
			{
				if (lineFound == null)
				{
					lineFound = line;
				}
				else
				{
					throw new AdempiereException("More than one fact line found for " + account + ": " + lineFound + ", " + line);
				}
			}

		}

		if (lineFound == null)
		{
			throw new AdempiereException("No fact line found for " + account + " in " + m_lines);
		}

		return lineFound;
	}

	public void save()
	{
		if (factTrxLinesStrategy != null)
		{
			factTrxLinesStrategy
					.createFactTrxLines(m_lines)
					.forEach(this::save);
		}
		else
		{
			m_lines.forEach(line -> InterfaceWrapperHelper.save(line, ITrx.TRXNAME_ThreadInherited));
		}
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

	private static final class Balance
	{
		private Money DR;
		private Money CR;

		private Balance(@NonNull CurrencyId currencyId, @NonNull final BigDecimal dr, @NonNull final BigDecimal cr)
		{
			DR = Money.of(dr, currencyId);
			CR = Money.of(cr, currencyId);
		}

		public static Balance ofSourceAmounts(final FactLine line)
		{
			return new Balance(line.getCurrencyId(), line.getAmtSourceDr(), line.getAmtSourceCr());
		}

		public void add(@NonNull Money dr, @NonNull Money cr)
		{
			DR = DR.add(dr);
			CR = CR.add(cr);
		}

		public void add(@NonNull Balance other)
		{
			add(other.DR, other.CR);
		}

		public Money getBalance()
		{
			return DR.subtract(CR);
		}

		/**
		 * @return absolute balance, negated if reversal
		 */
		public Money getPostBalance()
		{
			Money bd = getBalance().abs();
			if (isReversal())
			{
				return bd.negate();
			}
			return bd;
		}    // getPostBalance

		public boolean isZeroBalance()
		{
			return getBalance().signum() == 0;
		}    // isZeroBalance

		/**
		 * @return true if both DR/CR are negative or zero
		 */
		public boolean isReversal()
		{
			return DR.signum() <= 0 && CR.signum() <= 0;
		}    // isReversal

		@Override
		public String toString()
		{
			return "Balance[" + "DR=" + DR + "-CR=" + CR + " = " + getBalance() + "]";
		}
	}    // Balance
}   // Fact
