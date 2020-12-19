package de.metas.invoicecandidate.ui.spi.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Set;

import lombok.NonNull;
import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableSet;
import de.metas.currency.Currency;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;

/** Represent a summary information (immutable) about a selection of invoice candidates */
public final class InvoiceCandidatesSelectionSummaryInfo implements IGridTabSummaryInfo
{
	private static final long serialVersionUID = 1L;

	/** @return new builder */
	public static Builder builder()
	{
		return new Builder();
	}

	/** Cast given {@link IGridTabSummaryInfo} to {@link InvoiceCandidatesSelectionSummaryInfo} if possible. If not, null is returned */
	public static InvoiceCandidatesSelectionSummaryInfo castOrNull(final IGridTabSummaryInfo gridTabSummaryInfo)
	{
		if (gridTabSummaryInfo instanceof InvoiceCandidatesSelectionSummaryInfo)
		{
			return (InvoiceCandidatesSelectionSummaryInfo)gridTabSummaryInfo;
		}
		return null;
	}

	private final BigDecimal totalNetAmtApproved;
	private final BigDecimal totalNetAmtNotApproved;
	private final int countTotalToRecompute;
	private final Set<String> currencySymbols;

	private InvoiceCandidatesSelectionSummaryInfo(final Builder builder)
	{
		super();
		this.totalNetAmtApproved = builder.totalNetAmtApproved;
		this.totalNetAmtNotApproved = builder.totalNetAmtNotApproved;
		this.countTotalToRecompute = builder.countTotalToRecompute;
		this.currencySymbols = builder.currencySymbols.build();
	}

	public BigDecimal getTotalNetAmtApproved()
	{
		return totalNetAmtApproved;
	}

	public BigDecimal getTotalNetAmtNotApproved()
	{
		return totalNetAmtNotApproved;
	}

	public BigDecimal getTotalNetAmt()
	{
		return totalNetAmtApproved.add(totalNetAmtNotApproved);
	}

	public BigDecimal getTotalNetAmt(final boolean isApprovedForInvoicing)
	{
		if (isApprovedForInvoicing)
		{
			return getTotalNetAmtApproved();
		}
		else
		{
			return getTotalNetAmt();
		}
	}

	public int getCountTotalToRecompute()
	{
		return countTotalToRecompute;
	}

	public Set<String> getCurrencySymbols()
	{
		return currencySymbols;
	}

	@Override
	public String toString()
	{
		return getSummaryMessage();
	}

	public String getSummaryMessage()
	{
		final DecimalFormat amountFormat = DisplayType.getNumberFormat(DisplayType.Amount);
		final StringBuilder message = new StringBuilder();

		message.append("@Netto@ (@ApprovalForInvoicing@): ");
		message.append(amountFormat.format(getTotalNetAmtApproved()));

		final boolean isSameCurrency = currencySymbols.size() == 1;

		String curSymbol = null;
		if (isSameCurrency)
		{
			curSymbol = currencySymbols.iterator().next();
			message.append(curSymbol);
		}

		message.append(" | @Netto@ (@NotApprovedForInvoicing@): ");
		message.append(amountFormat.format(getTotalNetAmtNotApproved()));

		if (isSameCurrency)
		{
			message.append(curSymbol);
		}

		if (countTotalToRecompute > 0)
		{
			message.append(", @IsToRecompute@: ");
			message.append(countTotalToRecompute);
		}

		return message.toString();
	}

	@Override
	public String getSummaryMessageTranslated(final Properties ctx)
	{
		final String message = getSummaryMessage();
		final String messageTrl = Services.get(IMsgBL.class).parseTranslation(ctx, message);
		return messageTrl;

	}

	/**
	 * Builder
	 */
	public static class Builder
	{
		private BigDecimal totalNetAmtApproved = BigDecimal.ZERO;
		private BigDecimal totalNetAmtNotApproved = BigDecimal.ZERO;
		private int countTotalToRecompute = 0;
		private ImmutableSet.Builder<String> currencySymbols = ImmutableSet.<String>builder();

		private Builder()
		{
			super();
		}

		public InvoiceCandidatesSelectionSummaryInfo build()
		{
			return new InvoiceCandidatesSelectionSummaryInfo(this);
		}

		public Builder addTotalNetAmt(BigDecimal amtToAdd, final boolean approved)
		{
			if (approved)
			{
				this.totalNetAmtApproved = totalNetAmtApproved.add(amtToAdd);
			}
			else
			{
				this.totalNetAmtNotApproved = totalNetAmtNotApproved.add(amtToAdd);
			}
			return this;
		}

		public Builder addCurrencySymbol(final String currencySymbol)
		{
			if (Check.isEmpty(currencySymbol, true))
			{
				// NOTE: prevent adding null values because ImmutableSet.Builder will fail in this case
				this.currencySymbols.add("?");
			}
			else
			{
				this.currencySymbols.add(currencySymbol);
			}
			return this;
		}

		public void addCountToRecompute(final int countToRecomputeToAdd)
		{
			Check.assume(countToRecomputeToAdd > 0, "countToRecomputeToAdd > 0");
			this.countTotalToRecompute += countToRecomputeToAdd;
		}

		public void addInvoiceCandidate(@NonNull final I_C_Invoice_Candidate ic)
		{
			final BigDecimal netAmt = ic.getNetAmtToInvoice();
			final boolean isApprovedForInvoicing = ic.isApprovalForInvoicing();
			addTotalNetAmt(netAmt, isApprovedForInvoicing);

			final String currencySymbol = getCurrencySymbolOrNull(ic);
			addCurrencySymbol(currencySymbol);

			if (ic.isToRecompute())
			{
				addCountToRecompute(1);
			}
		}

		private String getCurrencySymbolOrNull(final I_C_Invoice_Candidate ic)
		{
			final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(ic.getC_Currency_ID());
			if(currencyId == null)
			{
				return null;
			}
			
			final Currency currency = Services.get(ICurrencyDAO.class).getById(currencyId);
			return currency.getSymbol().getDefaultValue();
		}
	}
}
