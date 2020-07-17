package de.metas.order;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.Nullable;

import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/** Order line price & discount calculations */
@Value
public class PriceAndDiscount
{
	public static PriceAndDiscount of(final I_C_OrderLine orderLine, final CurrencyPrecision precision)
	{
		return builder()
				.precision(precision)
				.priceEntered(orderLine.getPriceEntered())
				.priceActual(orderLine.getPriceActual())
				.discount(Percent.of(orderLine.getDiscount()))
				.priceLimit(orderLine.getPriceLimit())
				.build();
	}

	private static final Logger logger = LogManager.getLogger(PriceAndDiscount.class);

	BigDecimal priceEntered;
	BigDecimal priceActual;
	Percent discount;
	CurrencyPrecision precision;
	BigDecimal priceLimit;

	boolean priceLimitEnforced;
	ITranslatableString priceLimitEnforcedExplanation;
	ITranslatableString priceLimitNotEnforcedExplanation;

	@Builder(toBuilder = true)
	private PriceAndDiscount(
			@Nullable BigDecimal priceEntered,
			@Nullable BigDecimal priceActual,
			@Nullable Percent discount,
			@Nullable CurrencyPrecision precision,
			@Nullable BigDecimal priceLimit,
			boolean priceLimitEnforced,
			@Nullable ITranslatableString priceLimitEnforcedExplanation,
			@Nullable ITranslatableString priceLimitNotEnforcedExplanation)
	{
		this.precision = precision != null ? precision : CurrencyPrecision.ofInt(2);
		this.priceEntered = this.precision.round(CoalesceUtil.coalesce(priceEntered, ZERO));
		this.priceActual = this.precision.round(CoalesceUtil.coalesce(priceActual, ZERO));
		this.priceLimit = this.precision.round(CoalesceUtil.coalesce(priceLimit, ZERO));

		this.discount = CoalesceUtil.coalesce(discount, Percent.ZERO);

		this.priceLimitEnforced = priceLimitEnforced;
		if (priceLimitEnforced)
		{
			this.priceLimitEnforcedExplanation = TranslatableStrings.nullToEmpty(priceLimitEnforcedExplanation);
			this.priceLimitNotEnforcedExplanation = null;
		}
		else
		{
			this.priceLimitEnforcedExplanation = null;
			this.priceLimitNotEnforcedExplanation = TranslatableStrings.nullToEmpty(priceLimitNotEnforcedExplanation);
		}
	}

	public PriceAndDiscount enforcePriceLimit(final PriceLimitRuleResult priceLimitResult)
	{
		if (!priceLimitResult.isApplicable())
		{
			return toBuilder()
					.priceLimitNotEnforcedExplanation(priceLimitResult.getNotApplicableReason())
					.build();
		}

		final BigDecimal priceLimit = priceLimitResult.getPriceLimit();

		//
		boolean priceLimitEnforced = false;
		ITranslatableString priceLimitEnforcedExplanation = null;
		ITranslatableString priceLimitNotEnforcedExplanation = null;
		boolean updateDiscount = false;

		//
		// PriceEntered
		final BigDecimal priceEntered;
		final BooleanWithReason enforceOnPriceEntered = priceLimitResult.checkApplicableAndBelowPriceLimit(this.priceEntered);
		if (enforceOnPriceEntered.isTrue())
		{
			priceEntered = priceLimit;

			priceLimitEnforced = true;
			priceLimitEnforcedExplanation = enforceOnPriceEntered.getReason();
			updateDiscount = true;
		}
		else
		{
			priceEntered = this.priceEntered;
			priceLimitNotEnforcedExplanation = enforceOnPriceEntered.getReason();
		}

		//
		// PriceActual
		final BigDecimal priceActual;
		final BooleanWithReason enforceOnPriceActual = priceLimitResult.checkApplicableAndBelowPriceLimit(this.priceActual);
		if (enforceOnPriceActual.isTrue())
		{
			priceActual = priceLimit;

			priceLimitEnforced = true;
			priceLimitEnforcedExplanation = enforceOnPriceActual.getReason();
			updateDiscount = true;
		}
		else
		{
			priceActual = this.priceActual;
			priceLimitNotEnforcedExplanation = enforceOnPriceActual.getReason();
		}

		//
		// Discount
		final Percent discount;
		if (priceEntered.signum() != 0 && updateDiscount)
		{
			discount = calculateDiscountFromPrices(priceEntered, priceActual, precision);
		}
		else
		{
			discount = this.discount;
		}

		return toBuilder()
				.priceEntered(priceEntered)
				.priceActual(priceActual)
				.discount(discount)
				.priceLimit(priceLimit)
				.priceLimitEnforced(priceLimitEnforced)
				.priceLimitEnforcedExplanation(priceLimitEnforcedExplanation)
				.priceLimitNotEnforcedExplanation(priceLimitNotEnforcedExplanation)
				.build();
	}

	public PriceAndDiscount updatePriceActual()
	{
		final BigDecimal priceActual = discount.subtractFromBase(priceEntered, precision.toInt());
		return toBuilder().priceActual(priceActual).build();
	}

	public PriceAndDiscount updatePriceActualIfPriceEnteredIsNotZero()
	{
		if (priceEntered.signum() == 0)
		{
			return this;
		}

		return updatePriceActual();
	}

	public static Percent calculateDiscountFromPrices(
			final BigDecimal priceEntered,
			final BigDecimal priceActual,
			final CurrencyPrecision precision)
	{
		if (priceEntered.signum() == 0)
		{
			return Percent.ZERO;
		}

		final BigDecimal delta = priceEntered.subtract(priceActual);
		return Percent.of(delta, priceEntered, precision.toInt());
	}

	public static BigDecimal calculatePriceEnteredFromPriceActualAndDiscount(
			@NonNull final BigDecimal priceActual,
			@NonNull final BigDecimal discount,
			final int precision)
	{
		final BigDecimal multiplier = Env.ONEHUNDRED
				.add(discount)
				.divide(Env.ONEHUNDRED, 12, RoundingMode.HALF_UP);
		return priceActual
				.multiply(multiplier)
				.setScale(precision, RoundingMode.HALF_UP);
	}

	public void applyTo(final I_C_OrderLine orderLine)
	{
		logger.debug("Applying {} to {}", this, orderLine);

		orderLine.setPriceEntered(priceEntered);
		orderLine.setDiscount(discount.toBigDecimal());
		orderLine.setPriceActual(priceActual);
		orderLine.setPriceLimit(priceLimit);
		orderLine.setPriceLimitNote(buildPriceLimitNote());
	}

	private String buildPriceLimitNote()
	{
		final ITranslatableString msg;
		if (priceLimitEnforced)
		{
			msg = TranslatableStrings.builder()
					.appendADMessage("Enforced")
					.append(": ")
					.append(priceLimitEnforcedExplanation)
					.build();
		}
		else
		{
			msg = TranslatableStrings.builder()
					.appendADMessage("NotEnforced")
					.append(": ")
					.append(priceLimitNotEnforcedExplanation)
					.build();
		}

		final String adLanguage = Language.getBaseAD_Language();
		return msg.translate(adLanguage);
	}
}
