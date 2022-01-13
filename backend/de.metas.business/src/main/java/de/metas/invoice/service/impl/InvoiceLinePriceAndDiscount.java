/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.invoice.service.impl;

import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyPrecision;
import de.metas.logging.LogManager;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import de.metas.adempiere.model.I_C_InvoiceLine;

import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ZERO;

/**
 * Invoice line price & discount calculations
 */
@Value
public class InvoiceLinePriceAndDiscount
{
	public static InvoiceLinePriceAndDiscount of(@NonNull final I_C_InvoiceLine invoiceLine, @NonNull final CurrencyPrecision precision)
	{
		return builder()
				.precision(precision)
				.priceEntered(invoiceLine.getPriceEntered())
				.priceActual(invoiceLine.getPriceActual())
				.discount(Percent.of(invoiceLine.getDiscount()))
				.build();
	}

	private static final Logger logger = LogManager.getLogger(InvoiceLinePriceAndDiscount.class);

	BigDecimal priceEntered;
	BigDecimal priceActual;
	Percent discount;
	CurrencyPrecision precision;

	@Builder(toBuilder = true)
	private InvoiceLinePriceAndDiscount(
			@Nullable final BigDecimal priceEntered,
			@Nullable final BigDecimal priceActual,
			@Nullable final Percent discount,
			@Nullable final CurrencyPrecision precision)
	{
		this.precision = precision != null ? precision : CurrencyPrecision.ofInt(2);
		this.priceEntered = this.precision.round(CoalesceUtil.coalesce(priceEntered, ZERO));
		this.priceActual = this.precision.round(CoalesceUtil.coalesce(priceActual, ZERO));

		this.discount = CoalesceUtil.coalesce(discount, Percent.ZERO);
	}

	public InvoiceLinePriceAndDiscount withUpdatedPriceActual()
	{
		if (priceEntered.signum() == 0)
		{
			return toBuilder().priceActual(ZERO).build();
		}
		final BigDecimal priceActual = discount.subtractFromBase(priceEntered, precision.toInt(), precision.getRoundingMode());
		return toBuilder().priceActual(priceActual).build();
	}

	public InvoiceLinePriceAndDiscount withUpdatedPriceEntered()
	{
		if (priceActual.signum() == 0)
		{
			return toBuilder().priceEntered(ZERO).build();
		}
		final BigDecimal priceEntered = discount.addToBase(priceActual, precision.toInt(), precision.getRoundingMode());
		return toBuilder().priceEntered(priceEntered).build();
	}

	public InvoiceLinePriceAndDiscount withUpdatedDiscount()
	{
		if (priceEntered.signum() == 0)
		{
			return toBuilder().discount(Percent.ZERO).build();
		}

		final BigDecimal delta = priceEntered.subtract(priceActual);
		final Percent discount = Percent.of(delta, priceEntered, precision.toInt());

		return toBuilder().discount(discount).build();
	}

	public void applyTo(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		logger.debug("Applying {} to {}", this, invoiceLine);

		invoiceLine.setPriceEntered(priceEntered);
		invoiceLine.setDiscount(discount.toBigDecimal());
		invoiceLine.setPriceActual(priceActual);
	}
}
