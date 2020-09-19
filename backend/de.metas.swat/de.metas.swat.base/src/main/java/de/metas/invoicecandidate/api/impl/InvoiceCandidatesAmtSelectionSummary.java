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

import java.math.BigDecimal;
import java.util.Set;

import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Represent a summary information (immutable) about a selection of invoice candidates
 */
public class InvoiceCandidatesAmtSelectionSummary
{
	public static final InvoiceCandidatesAmtSelectionSummary EMPTY = builder().build();

	private final BigDecimal totalNetAmtApproved;
	private final BigDecimal huNetAmtApproved;
	private final BigDecimal cuNetAmtApproved;
	private final BigDecimal totalNetAmtNotApproved;
	private final BigDecimal huNetAmtNotApproved;
	private final BigDecimal cuNetAmtNotApproved;
	private final int countTotalToRecompute;

	private final ImmutableSet<String> currencySymbols;

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

	public ITranslatableString getTotalNetAmtApprovedAsTranslatableString()
	{
		return toTranslatableString(getTotalNetAmtApproved());
	}

	public BigDecimal getHUNetAmtApproved()
	{
		return huNetAmtApproved;
	}

	public ITranslatableString getHUNetAmtApprovedAsTranslatableString()
	{
		return toTranslatableString(getHUNetAmtApproved());
	}

	public BigDecimal getCUNetAmtApproved()
	{
		return cuNetAmtApproved;
	}

	public ITranslatableString getCUNetAmtApprovedAsTranslatableString()
	{
		return toTranslatableString(getCUNetAmtApproved());
	}

	public BigDecimal getTotalNetAmtNotApproved()
	{
		return totalNetAmtNotApproved;
	}

	public ITranslatableString getTotalNetAmtNotApprovedAsTranslatableString()
	{
		return toTranslatableString(getTotalNetAmtNotApproved());
	}

	public BigDecimal getHUNetAmtNotApproved()
	{
		return huNetAmtNotApproved;
	}

	public ITranslatableString getHUNetAmtNotApprovedAsTranslatableString()
	{
		return toTranslatableString(getHUNetAmtNotApproved());
	}

	public BigDecimal getCUNetAmtNotApproved()
	{
		return cuNetAmtNotApproved;
	}

	public ITranslatableString getCUNetAmtNotApprovedAsTranslatableString()
	{
		return toTranslatableString(getCUNetAmtNotApproved());
	}

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

	@Nullable
	private String getSingleCurrencySymbolOrNull()
	{
		final boolean singleCurrencySymbol = currencySymbols.size() == 1;
		return singleCurrencySymbol ? currencySymbols.iterator().next() : null;
	}

	private ITranslatableString toTranslatableString(final BigDecimal amt)
	{
		final TranslatableStringBuilder builder = TranslatableStrings.builder();

		builder.append(amt, DisplayType.Amount);

		final String singleCurrencySymbol = getSingleCurrencySymbolOrNull();
		if (singleCurrencySymbol != null)
		{
			builder.append(" ").append(singleCurrencySymbol);
		}

		return builder.build();
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
