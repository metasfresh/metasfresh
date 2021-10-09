/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.invoicecandidate.ui.spi.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Set;

import de.metas.invoicecandidate.api.impl.InvoiceCandidatesAmtSelectionSummary;
import lombok.NonNull;
import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.compiere.util.DisplayType;

import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
/** Represent a summary information (immutable) about a selection of invoice candidates */
public final class HUInvoiceCandidatesSelectionSummaryInfo implements IGridTabSummaryInfo
{
	private static final long serialVersionUID = 1L;

	/** Cast given {@link IGridTabSummaryInfo} to {@link HUInvoiceCandidatesSelectionSummaryInfo} if possible. If not, null is returned */
	public static HUInvoiceCandidatesSelectionSummaryInfo castOrNull(final IGridTabSummaryInfo gridTabSummaryInfo)
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

	public HUInvoiceCandidatesSelectionSummaryInfo(@NonNull final InvoiceCandidatesAmtSelectionSummary summary)
	{
		super();

		_totalNetAmtApproved = summary.getTotalNetAmtApproved();
		_huNetAmtApproved = summary.getHUNetAmtApproved();
		_cuNetAmtApproved = summary.getCUNetAmtApproved();

		_totalNetAmtNotApproved = summary.getTotalNetAmtNotApproved();
		_huNetAmtNotApproved = summary.getHUNetAmtNotApproved();
		_cuNetAmtNotApproved = summary.getCUNetAmtNotApproved();

		countTotalToRecompute = summary.getCountTotalToRecompute();
		currencySymbols = summary.getCurrencySymbols();
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

	/**
	 * Keep in sync with {@link de.metas.ui.web.view.OrderCandidateViewHeaderPropertiesProvider#toViewHeaderProperties(de.metas.invoicecandidate.api.impl.InvoiceCandidatesAmtSelectionSummary)}
	 */
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
}
