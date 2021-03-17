package de.metas.ui.web.order.pricingconditions.view;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PriceSpecification;
import de.metas.pricing.conditions.PriceSpecificationType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreak.PricingConditionsBreakBuilder;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest.CompletePriceChange;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest.PartialPriceChange;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest.PriceChange;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
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

@UtilityClass
class PricingConditionsRowReducers
{
	public PricingConditionsRow copyAndChange(
			@NonNull final PricingConditionsRowChangeRequest request,
			@NonNull final PricingConditionsRow row)
	{
		row.assertEditable();

		boolean changed = false;

		final PricingConditionsBreak pricingConditionsBreakOld = row.getPricingConditionsBreak();
		final PricingConditionsBreak pricingConditionsBreak = applyRequestTo(request, pricingConditionsBreakOld)
				.toTemporaryPricingConditionsBreakIfPriceRelevantFieldsChanged(pricingConditionsBreakOld);

		if (!Objects.equals(pricingConditionsBreak, pricingConditionsBreakOld))
		{
			changed = true;
		}

		//
		// Copied from ID
		PricingConditionsBreakId copiedFromPricingConditionsBreakId = row.getCopiedFromPricingConditionsBreakId();
		if (!Objects.equals(request.getSourcePricingConditionsBreakId(), copiedFromPricingConditionsBreakId))
		{
			copiedFromPricingConditionsBreakId = request.getSourcePricingConditionsBreakId();
			changed = true;
		}

		//
		if (!changed)
		{
			return row;
		}

		return row.toBuilder()
				.pricingConditionsBreak(pricingConditionsBreak)
				.copiedFromPricingConditionsBreakId(copiedFromPricingConditionsBreakId)
				.build();
	}

	private PricingConditionsBreak applyRequestTo(
			@NonNull final PricingConditionsRowChangeRequest request,
			@NonNull final PricingConditionsBreak rowPricingConditionsBreak)
	{
		final PricingConditionsBreak pricingConditionsBreakEffective = coalesce(request.getPricingConditionsBreak(), rowPricingConditionsBreak);
		final PricingConditionsBreakBuilder builder = pricingConditionsBreakEffective.toBuilder();

		//
		// Discount%
		if (request.getDiscount() != null)
		{
			builder.discount(request.getDiscount());
			builder.hasChanges(true);
		}

		//
		// Payment Term
		final boolean paymentTermChangeRequested = request.getPaymentTermId() != null;
		final boolean paymentDiscountChangeRequested = request.getPaymentDiscount() != null;
		if (paymentTermChangeRequested || paymentDiscountChangeRequested)
		{
			final PaymentTermId paymentTermIdOrNull;
			if (paymentTermChangeRequested)
			{
				paymentTermIdOrNull = request.getPaymentTermId().orElse(null);
				builder.paymentTermIdOrNull(paymentTermIdOrNull);
			}
			else
			{
				// if no payment term change was requested, we use the old paymentTermId to get derivedPaymentTermIdOrNull.
				paymentTermIdOrNull = pricingConditionsBreakEffective.getPaymentTermIdOrNull();
			}

			final Percent paymentDiscountOrNull = paymentDiscountChangeRequested ? request.getPaymentDiscount().orElse(null) : null;
			builder.paymentDiscountOverrideOrNull(paymentDiscountOrNull);

			final PaymentTermService paymentTermService = SpringContextHolder.instance.getBean(PaymentTermService.class);
			final PaymentTermId derivedPaymentTermIdOrNull = paymentTermService.getOrCreateDerivedPaymentTerm(paymentTermIdOrNull, paymentDiscountOrNull);
			builder.derivedPaymentTermIdOrNull(derivedPaymentTermIdOrNull);
		}

		//
		// Price
		if (request.getPriceChange() != null)
		{
			final PriceSpecification price = applyPriceChangeTo(request.getPriceChange(), rowPricingConditionsBreak.getPriceSpecification());
			builder.priceSpecification(price);
		}

		PricingConditionsBreak newPricingConditionsBreak = builder.build();
		if (!Objects.equals(newPricingConditionsBreak, pricingConditionsBreakEffective))
		{
			newPricingConditionsBreak = newPricingConditionsBreak.toBuilder().hasChanges(true).build();
		}

		return newPricingConditionsBreak;
	}

	private PriceSpecification applyPriceChangeTo(
			@Nullable final PriceChange priceChange,
			@NonNull final PriceSpecification price)
	{
		if (priceChange == null)
		{
			// no change
			return price;
		}
		else if (priceChange instanceof CompletePriceChange)
		{
			final CompletePriceChange completePriceChange = (CompletePriceChange)priceChange;
			return completePriceChange.getPrice();
		}
		else if (priceChange instanceof PartialPriceChange)
		{
			final PartialPriceChange partialPriceChange = (PartialPriceChange)priceChange;
			return applyPartialPriceChangeTo(partialPriceChange, price);
		}
		else
		{
			throw new AdempiereException("Unknow price change request: " + priceChange);
		}

	}

	private static PriceSpecification applyPartialPriceChangeTo(
			@NonNull final PartialPriceChange changes,
			@NonNull final PriceSpecification price)
	{
		final PriceSpecificationType priceType = coalesce(changes.getPriceType(), price.getType());
		if (priceType == PriceSpecificationType.NONE)
		{
			return PriceSpecification.none();
		}
		else if (priceType == PriceSpecificationType.BASE_PRICING_SYSTEM)
		{
			final PricingSystemId requestBasePricingSystemId = changes.getBasePricingSystemId() != null ? changes.getBasePricingSystemId().orElse(null) : null;
			final PricingSystemId basePricingSystemId = coalesce(requestBasePricingSystemId, price.getBasePricingSystemId(), PricingSystemId.NONE);

			final Money surcharge = extractMoney(
					changes.getPricingSystemSurchargeAmt(),
					changes.getCurrencyId(),
					price.getPricingSystemSurcharge(),
					extractDefaultCurrencyIdSupplier(changes));
			return PriceSpecification.basePricingSystem(basePricingSystemId, surcharge);
		}
		else if (priceType == PriceSpecificationType.FIXED_PRICE)
		{
			final Money fixedPrice = extractMoney(
					changes.getFixedPriceAmt(),
					changes.getCurrencyId(),
					price.getFixedPrice(),
					extractDefaultCurrencyIdSupplier(changes));

			return PriceSpecification.fixedPrice(fixedPrice);
		}
		else
		{
			throw new AdempiereException("Unknow price type: " + priceType);
		}
	}

	private static Supplier<CurrencyId> extractDefaultCurrencyIdSupplier(final PartialPriceChange changes)
	{
		if (changes.getDefaultCurrencyId() != null)
		{
			return changes::getDefaultCurrencyId;
		}
		else
		{
			final ICurrencyBL currenciesService = Services.get(ICurrencyBL.class);
			return () -> currenciesService.getBaseCurrencyId(Env.getClientId(), Env.getOrgId());
		}
	}

	@NonNull
	private static Money extractMoney(
			@Nullable final BigDecimal amount,
			@Nullable final CurrencyId currencyId,
			@Nullable final Money fallback,
			@NonNull final Supplier<CurrencyId> defaultCurrencyIdSupplier)
	{
		if (amount == null && currencyId == null && fallback != null)
		{
			return fallback;
		}

		final BigDecimal amountEffective = coalesce(
				amount,
				fallback != null ? fallback.toBigDecimal() : BigDecimal.ZERO);

		final CurrencyId currencyIdEffective = coalesceSuppliers(
				() -> currencyId,
				() -> fallback != null ? fallback.getCurrencyId() : null,
				defaultCurrencyIdSupplier);
		if (currencyIdEffective == null)
		{
			// shall not happen
			throw new AdempiereException("No currency set");
		}

		return Money.of(amountEffective, currencyIdEffective);
	}
}
