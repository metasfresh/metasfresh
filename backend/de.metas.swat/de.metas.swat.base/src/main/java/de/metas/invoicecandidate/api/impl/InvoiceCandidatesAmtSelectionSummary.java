/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.invoicecandidate.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.DisplayType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Set;

/**
 * Represent a summary information (immutable) about a selection of invoice candidates
 */
public class InvoiceCandidatesAmtSelectionSummary
{
	private final BigDecimal totalNetAmtApproved;
	private final BigDecimal huNetAmtApproved;
	private final BigDecimal cuNetAmtApproved;
	private final BigDecimal totalNetAmtNotApproved;
	private final BigDecimal huNetAmtNotApproved;
	private final BigDecimal cuNetAmtNotApproved;
	private final int countTotalToRecompute;

	private final Set<String> currencySymbols;

	public static Builder builder()
	{
		return new Builder();
	}

	private InvoiceCandidatesAmtSelectionSummary(@NonNull final Builder builder)
	{
		totalNetAmtApproved = builder.totalNetAmtApproved;
		huNetAmtApproved = builder.huNetAmtApproved;
		cuNetAmtApproved = builder.cuNetAmtApproved;

		totalNetAmtNotApproved = builder.totalNetAmtNotApproved;
		huNetAmtNotApproved = builder.huNetAmtNotApproved;
		cuNetAmtNotApproved = builder.cuNetAmtNotApproved;

		countTotalToRecompute = builder.countTotalToRecompute;
		currencySymbols = builder.currencySymbols.build();
	}

	public BigDecimal getTotalNetAmtApproved()
	{
		return totalNetAmtApproved;
	}

	public BigDecimal getHUNetAmtApproved()
	{
		return huNetAmtApproved;
	}

	public BigDecimal getCUNetAmtApproved()
	{
		return cuNetAmtApproved;
	}

	public BigDecimal getTotalNetAmtNotApproved()
	{
		return totalNetAmtNotApproved;
	}

	public BigDecimal getHUNetAmtNotApproved()
	{
		return huNetAmtNotApproved;
	}

	public BigDecimal getCUNetAmtNotApproved()
	{
		return cuNetAmtNotApproved;
	}

	@SuppressWarnings("unused")
	public BigDecimal getTotalNetAmt()
	{
		return totalNetAmtApproved.add(totalNetAmtNotApproved);
	}

	public int getCountTotalToRecompute()
	{
		return countTotalToRecompute;
	}

	public Set<String> getCurrencySymbols()
	{
		return currencySymbols;
	}

	public String getSummaryMessageTranslated(final Properties ctx)
	{
		final String message = getSummaryMessage();
		final String messageTrl = Services.get(IMsgBL.class).parseTranslation(ctx, message);
		return messageTrl;
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

		String curSymbol;
		if (isSameCurrency)
		{
			curSymbol = currencySymbols.iterator().next();
			amountFormatted.append(curSymbol);
		}
		return amountFormatted.toString();
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
		private final ImmutableSet.Builder<String> currencySymbols = ImmutableSet.builder();

		private Builder()
		{
			super();
		}

		public InvoiceCandidatesAmtSelectionSummary build()
		{
			return new InvoiceCandidatesAmtSelectionSummary(this);
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

		@SuppressWarnings("UnusedReturnValue")
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

	}
}

