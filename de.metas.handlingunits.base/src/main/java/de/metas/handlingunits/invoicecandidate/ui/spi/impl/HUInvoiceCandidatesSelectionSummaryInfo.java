package de.metas.handlingunits.invoicecandidate.ui.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Currency;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/** Represent a summary information (immutable) about a selection of invoice candidates */
public final class HUInvoiceCandidatesSelectionSummaryInfo implements IGridTabSummaryInfo
{
	private static final long serialVersionUID = 1L;

	/** @return new builder */
	public static final Builder builder()
	{
		return new Builder();
	}

	/** Cast given {@link IGridTabSummaryInfo} to {@link HUInvoiceCandidatesSelectionSummaryInfo} if possible. If not, null is returned */
	public static final HUInvoiceCandidatesSelectionSummaryInfo castOrNull(final IGridTabSummaryInfo gridTabSummaryInfo)
	{
		if (gridTabSummaryInfo instanceof HUInvoiceCandidatesSelectionSummaryInfo)
		{
			return (HUInvoiceCandidatesSelectionSummaryInfo)gridTabSummaryInfo;
		}
		return null;
	}

	private final BigDecimal _totalNetAmtApproved;
	private final BigDecimal _huNetAmtApproved;
	private final BigDecimal _cuNetAmtApproved;
	private final BigDecimal _totalNetAmtNotApproved;
	private final BigDecimal _huNetAmtNotApproved;
	private final BigDecimal _cuNetAmtNotApproved;
	private final int countTotalToRecompute;
	private final Set<String> currencySymbols;

	private HUInvoiceCandidatesSelectionSummaryInfo(final Builder builder)
	{
		super();

		_totalNetAmtApproved = builder.totalNetAmtApproved;
		_huNetAmtApproved = builder.huNetAmtApproved;
		_cuNetAmtApproved = builder.cuNetAmtApproved;

		_totalNetAmtNotApproved = builder.totalNetAmtNotApproved;
		_huNetAmtNotApproved = builder.huNetAmtNotApproved;
		_cuNetAmtNotApproved = builder.cuNetAmtNotApproved;

		countTotalToRecompute = builder.countTotalToRecompute;
		currencySymbols = builder.currencySymbols.build();
	}

	public BigDecimal getTotalNetAmtApproved()
	{
		return _totalNetAmtApproved;
	}

	public BigDecimal getHUNetAmtApproved()
	{
		return _huNetAmtApproved;
	}

	public BigDecimal getCUNetAmtApproved()
	{
		return _cuNetAmtApproved;
	}

	public BigDecimal getTotalNetAmtNotApproved()
	{
		return _totalNetAmtNotApproved;
	}

	public BigDecimal getHUNetAmtNotApproved()
	{
		return _huNetAmtNotApproved;
	}

	public BigDecimal getCUNetAmtNotApproved()
	{
		return _cuNetAmtNotApproved;
	}

	public BigDecimal getTotalNetAmt()
	{
		return _totalNetAmtApproved.add(_totalNetAmtNotApproved);
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
		final StringBuilder message = new StringBuilder();

		message.append("@Netto@ (@ApprovalForInvoicing@): ");
		final BigDecimal totalNetAmtApproved = getTotalNetAmtApproved();
		message.append(getAmountFormatted(totalNetAmtApproved));

		final BigDecimal huNetAmtApproved = getHUNetAmtApproved();
		message.append(" - ").append("Gebinde ").append(getAmountFormatted(huNetAmtApproved));

		final BigDecimal cuNetAmtApproved = getCUNetAmtApproved();
		message.append(" - ").append("Ware ").append(getAmountFormatted(cuNetAmtApproved));

		message.append(" | @Netto@ (@NotApprovedForInvoicing@): ");

		final BigDecimal totalNetAmtNotApproved = getTotalNetAmtNotApproved();
		message.append(getAmountFormatted(totalNetAmtNotApproved));

		final BigDecimal huNetAmtNotApproved = getHUNetAmtNotApproved();
		message.append(" - ").append("Gebinde ").append(getAmountFormatted(huNetAmtNotApproved));

		final BigDecimal cuNetAmtNotApproved = getCUNetAmtNotApproved();
		message.append(" - ").append("Ware ").append(getAmountFormatted(cuNetAmtNotApproved));

		if (countTotalToRecompute > 0)
		{
			message.append(", @IsToRecompute@: ");
			message.append(countTotalToRecompute);
		}

		return message.toString();
	}

	private String getAmountFormatted(final BigDecimal amt)
	{
		final DecimalFormat amountFormat = DisplayType.getNumberFormat(DisplayType.Amount);
		final StringBuilder amountFormatted = new StringBuilder();

		amountFormatted.append(amountFormat.format(amt));

		final boolean isSameCurrency = currencySymbols.size() == 1;

		String curSymbol = null;
		if (isSameCurrency)
		{
			curSymbol = currencySymbols.iterator().next();
			amountFormatted.append(curSymbol);
		}
		return amountFormatted.toString();
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
		private BigDecimal huNetAmtApproved = BigDecimal.ZERO;
		private BigDecimal cuNetAmtApproved = BigDecimal.ZERO;

		private BigDecimal totalNetAmtNotApproved = BigDecimal.ZERO;
		private BigDecimal huNetAmtNotApproved = BigDecimal.ZERO;
		private BigDecimal cuNetAmtNotApproved = BigDecimal.ZERO;

		private int countTotalToRecompute = 0;
		private final ImmutableSet.Builder<String> currencySymbols = ImmutableSet.<String> builder();

		private Builder()
		{
			super();
		}

		public HUInvoiceCandidatesSelectionSummaryInfo build()
		{
			return new HUInvoiceCandidatesSelectionSummaryInfo(this);
		}

		public Builder addTotalNetAmt(final BigDecimal amtToAdd, final boolean approved, final boolean isPackingMaterial)
		{
			if (approved)
			{
				totalNetAmtApproved = totalNetAmtApproved.add(amtToAdd);
				if (isPackingMaterial)
				{
					huNetAmtApproved = huNetAmtApproved.add(amtToAdd);
				}
				else
				{
					cuNetAmtApproved = cuNetAmtApproved.add(amtToAdd);
				}
			}
			else
			{
				totalNetAmtNotApproved = totalNetAmtNotApproved.add(amtToAdd);
				if (isPackingMaterial)
				{
					huNetAmtNotApproved = huNetAmtNotApproved.add(amtToAdd);
				}
				else
				{
					cuNetAmtNotApproved = cuNetAmtNotApproved.add(amtToAdd);
				}
			}
			return this;
		}

		public Builder addCurrencySymbol(final String currencySymbol)
		{
			if (Check.isEmpty(currencySymbol, true))
			{
				// NOTE: prevent adding null values because ImmutableSet.Builder will fail in this case
				currencySymbols.add("?");
			}
			else
			{
				currencySymbols.add(currencySymbol);
			}
			return this;
		}

		public void addCountToRecompute(final int countToRecomputeToAdd)
		{
			Check.assume(countToRecomputeToAdd > 0, "countToRecomputeToAdd > 0");
			countTotalToRecompute += countToRecomputeToAdd;
		}

		public void addInvoiceCandidate(final I_C_Invoice_Candidate ic)
		{
			Check.assumeNotNull(ic, "ic not null");

			final BigDecimal netAmt = ic.getNetAmtToInvoice();
			final boolean isApprovedForInvoicing = ic.isApprovalForInvoicing();
			addTotalNetAmt(netAmt, isApprovedForInvoicing, false); // isPackingMaterial TODO

			final I_C_Currency currency = ic.getC_Currency();
			final String currencySymbol = currency == null ? null : currency.getCurSymbol();
			addCurrencySymbol(currencySymbol);

			if (ic.isToRecompute())
			{
				addCountToRecompute(1);
			}
		}
	}
}
