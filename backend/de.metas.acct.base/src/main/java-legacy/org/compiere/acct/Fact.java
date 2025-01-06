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
import com.google.common.collect.ImmutableMap;
import de.metas.acct.Account;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaElementsMap;
import de.metas.acct.api.AcctSchemaGeneralLedger;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.currency.CurrencyConversionContext;
import de.metas.elementvalue.ElementValue;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactTrxLines.FactTrxLinesType;
import org.compiere.model.MAccount;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

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
	@Nullable private FactTrxStrategy _factTrxLinesStrategy = PerDocumentLineFactTrxStrategy.instance;
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
		this._factTrxLinesStrategy = factTrxLinesStrategy;
		return this;
	}

	@Nullable
	private FactTrxStrategy getFactTrxLinesStrategyEffective()
	{
		if (acctSchema.isAllowMultiDebitAndCredit())
		{
			return null;
		}

		return _factTrxLinesStrategy;
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
	 * Add Fact Line
	 *
	 * @param line fact line
	 */
	void add(@NonNull final FactLine line)
	{
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

	public CurrencyId getAcctCurrencyId() {return getAcctSchema().getCurrencyId();}

	public boolean isAccountingCurrency(@NonNull final CurrencyId currencyId) {return getAcctSchema().isAccountingCurrency(currencyId);}

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

		Balance balance = getSourceBalance();
		if (balance.isBalanced())
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

	private Balance getSourceBalance()
	{
		return m_lines.stream()
				.map(FactLine::getSourceBalance)
				.collect(Balance.sum())
				.orElseGet(() -> Balance.zero(acctSchema.getCurrencyId())); // NOTE we use the acct schema currency because there is no other currency to use
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

		final Money diff = getSourceBalance().toMoney();
		if (!diff.isZero())
		{
			createLine()
					.setAccount(acctSchemaGL.getSuspenseBalancingAcct())
					.setAmtSourceDrOrCr(diff.negate()) // Negative balance => DR, Positive balance => CR
					.buildAndAdd();
		}
	}   // balancingSource

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isSegmentBalanced()
	{
		// AZ Goodwill
		// Multi-Currency documents are source balanced by definition
		// No lines -> balanced
		if (m_lines.isEmpty() || m_doc.isMultiCurrency())
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
			final ImmutableMap<OrgId, Balance> map = m_lines.stream()
					.collect(ImmutableMap.toImmutableMap(FactLine::getOrgId, FactLine::getSourceBalance, Balance::add));

			// check if all keys are zero
			for (final Balance orgBalance : map.values())
			{
				if (!orgBalance.isBalanced())
				{
					log.warn("({}) NO - {}, Balance={}", segmentType, this, orgBalance);
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
			final ImmutableMap<OrgId, Balance> map = m_lines.stream()
					.collect(ImmutableMap.toImmutableMap(FactLine::getOrgId, FactLine::getSourceBalance, Balance::add));

			// Create entry for non-zero element
			for (final OrgId orgId : map.keySet())
			{
				final Balance orgBalance = map.get(orgId);
				if (!orgBalance.isBalanced())
				{
					// Create Balancing Entry
					final FactLineBuilder line = createLine()
							.orgId(orgId);
					final AcctSchema acctSchema = getAcctSchema();
					final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
					if (orgBalance.signum() < 0)
					{
						if (orgBalance.isReversal())
						{
							line.setAccount(acctSchemaGL.getDueToAcct(elementType));
							line.setAmtSource((Money)null, orgBalance.getPostBalance());
						}
						else
						{
							line.setAccount(acctSchemaGL.getDueFromAcct(elementType));
							line.setAmtSource(orgBalance.getPostBalance(), null);
						}
					}
					else
					{
						if (orgBalance.isReversal())
						{
							line.setAccount(acctSchemaGL.getDueFromAcct(elementType));
							line.setAmtSource(orgBalance.getPostBalance(), null);
						}
						else
						{
							line.setAccount(acctSchemaGL.getDueToAcct(elementType));
							line.setAmtSource((Money)null, orgBalance.getPostBalance());
						}
					}
					//
					add(line.buildAndAddNotNull());
				}
			}
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

		final Money balance = getAcctBalance().toMoney();
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

	public Balance getAcctBalance()
	{
		return m_lines.stream()
				.map(FactLine::getAcctBalance)
				.collect(Balance.sum())
				.orElseGet(() -> Balance.zero(acctSchema.getCurrencyId()));
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

			final Money amt = l.getAcctBalance().toMoney().abs();
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

		//
		// Create Currency Balancing Entry
		final FactLine line;
		final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
		if (acctSchemaGL.isCurrencyBalancing())
		{
			line = createLine()
					.setAccount(acctSchemaGL.getCurrencyBalancingAcct())
					.setAmtSource(m_doc.getCurrencyId(), BigDecimal.ZERO, BigDecimal.ZERO)
					.alsoAddZeroLine()
					.buildAndAddNotNull();
			// Accounted
			Balance diff = getAcctBalance().computeDiffToBalance();

			diff = diff.negateAndInvertIf(BSline != null && BSline.isDrSourceBalance() == diff.isDebit());

			line.setAmtAcct(diff);
			//add(line);
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
				log.error("No BS or PL Line found");
			}
			else
			{
				final Money diff = getAcctBalance().toMoney();        // DR-CR
				log.debug("Adjusting Amt={}; Line={}", diff, line);
				line.currencyCorrect(diff);
			}
		}   // correct biggest amount

	}   // balanceAccounting

	BooleanWithReason checkAccounts()
	{
		for (final FactLine line : m_lines)
		{
			final ElementValue ev = services.getElementValueById(line.getAccountId());
			if (ev.isSummary())
			{
				return BooleanWithReason.falseBecause("Cannot post to Summary Account " + ev.toShortString() + ": " + line);
			}
			if (!ev.isActive())
			{
				return BooleanWithReason.falseBecause("Cannot post to Inactive Account " + ev.toShortString() + ": " + line);
			}
		}

		return BooleanWithReason.TRUE;
	}

	/**
	 * GL Distribution of Fact Lines
	 */
	public void distribute()
	{
		final List<FactLine> linesAfterDistribution = FactGLDistributor.newInstance()
				.distribute(m_lines);

		m_lines = new ArrayList<>(linesAfterDistribution);
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

	public ImmutableList<FactLine> getLines()
	{
		return ImmutableList.copyOf(m_lines);
	}

	public void mapEachLine(final UnaryOperator<FactLine> mapper)
	{
		final ListIterator<FactLine> it = m_lines.listIterator();
		while (it.hasNext())
		{
			FactLine line = it.next();

			final FactLine changedLine = mapper.apply(line);
			if (changedLine == null)
			{
				it.remove();
			}
			else
			{
				it.set(changedLine);
			}
		}
	}

	@NonNull
	public FactLine getSingleLineByAccountId(final AccountId accountId)
	{
		final MAccount account = services.getAccountById(accountId);

		FactLine lineFound = null;
		for (FactLine line : m_lines)
		{
			if (ElementValueId.equals(line.getAccountId(), account.getElementValueId()))
			{
				if (lineFound == null)
				{
					lineFound = line;
				}
				else
				{
					throw new AdempiereException("More than one fact line found for AccountId: " + accountId.getRepoId() + ": " + lineFound + ", " + line);
				}
			}

		}

		if (lineFound == null)
		{
			throw new AdempiereException("No fact line found for AccountId: " + accountId.getRepoId() + " in " + m_lines);
		}

		return lineFound;
	}

	public void save()
	{
		final FactTrxStrategy factTrxLinesStrategy = getFactTrxLinesStrategyEffective();
		if (factTrxLinesStrategy != null)
		{
			factTrxLinesStrategy
					.createFactTrxLines(m_lines)
					.forEach(this::saveNew);
		}
		else
		{
			m_lines.forEach(this::saveNew);
		}
	}

	private void saveNew(FactLine line) {services.saveNew(line);}

	private void saveNew(final FactTrxLines factTrxLines)
	{
		//
		// Case: 1 debit line, one or more credit lines
		if (factTrxLines.getType() == FactTrxLinesType.Debit)
		{
			final FactLine drLine = factTrxLines.getDebitLine();
			saveNew(drLine);

			factTrxLines.forEachCreditLine(crLine -> {
				crLine.setCounterpart_Fact_Acct_ID(drLine.getIdNotNull());
				saveNew(crLine);
			});

		}
		//
		// Case: 1 credit line, one or more debit lines
		else if (factTrxLines.getType() == FactTrxLinesType.Credit)
		{
			final FactLine crLine = factTrxLines.getCreditLine();
			saveNew(crLine);

			factTrxLines.forEachDebitLine(drLine -> {
				drLine.setCounterpart_Fact_Acct_ID(crLine.getIdOrNull());
				saveNew(drLine);
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
		factTrxLines.forEachZeroLine(this::saveNew);
	}

	public void forEach(final Consumer<FactLine> consumer)
	{
		m_lines.forEach(consumer);
	}

}   // Fact
