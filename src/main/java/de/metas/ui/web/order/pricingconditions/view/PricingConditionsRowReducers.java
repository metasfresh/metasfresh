package de.metas.ui.web.order.pricingconditions.view;

import java.math.BigDecimal;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.util.Util;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PriceOverride;
import de.metas.pricing.conditions.PriceOverrideType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreak.PricingConditionsBreakBuilder;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest.CompletePriceChange;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest.PartialPriceChange;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest.PriceChange;
import de.metas.util.lang.Percent;
import de.metas.pricing.conditions.PricingConditionsBreakId;
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
		final PricingConditionsBreak pricingConditionsBreak = applyTo(request, pricingConditionsBreakOld)
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

	private PricingConditionsBreak applyTo(
			@NonNull final PricingConditionsRowChangeRequest request,
			@NonNull final PricingConditionsBreak rowPricingConditionsBreak)
	{
		final PricingConditionsBreak pricingConditionsBreakEffective = Util.coalesce(request.getPricingConditionsBreak(), rowPricingConditionsBreak);
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

			final PaymentTermService paymentTermService = Adempiere.getBean(PaymentTermService.class);
			final PaymentTermId derivedPaymentTermIdOrNull = paymentTermService.getOrCreateDerivedPaymentTerm(paymentTermIdOrNull, paymentDiscountOrNull);
			builder.derivedPaymentTermIdOrNull(derivedPaymentTermIdOrNull);
		}

		//
		// Price
		if (request.getPriceChange() != null)
		{
			final PriceOverride price = applyPriceChangeTo(request.getPriceChange(), rowPricingConditionsBreak.getPriceOverride());
			builder.priceOverride(price);
		}

		PricingConditionsBreak newPricingConditionsBreak = builder.build();
		if (!Objects.equals(newPricingConditionsBreak, pricingConditionsBreakEffective))
		{
			newPricingConditionsBreak = newPricingConditionsBreak.toBuilder().hasChanges(true).build();
		}

		return newPricingConditionsBreak;
	}

	private PriceOverride applyPriceChangeTo(final PriceChange priceChange, final PriceOverride price)
	{
		if (priceChange == null)
		{
			// no change
			return price;
		}
		else if (priceChange instanceof CompletePriceChange)
		{
			return ((CompletePriceChange)priceChange).getPrice();
		}
		else if (priceChange instanceof PartialPriceChange)
		{
			return applyPartialPriceChangeTo((PartialPriceChange)priceChange, price);
		}
		else
		{
			throw new AdempiereException("Unknow price change request: " + priceChange);
		}

	}

	private PriceOverride applyPartialPriceChangeTo(
			@NonNull final PartialPriceChange changes,
			final PriceOverride price)
	{
		final PriceOverrideType priceType = changes.getPriceType() != null ? changes.getPriceType() : price.getType();
		if (priceType == PriceOverrideType.NONE)
		{
			return PriceOverride.none();
		}
		else if (priceType == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			final PricingSystemId requestBasePricingSystemId = changes.getBasePricingSystemId() != null ? changes.getBasePricingSystemId().orElse(null) : null;
			final PricingSystemId basePricingSystemId = Util.coalesce(requestBasePricingSystemId, price.getBasePricingSystemId(), PricingSystemId.NONE);
			final BigDecimal basePriceAddAmt = changes.getBasePriceAddAmt() != null ? changes.getBasePriceAddAmt() : price.getBasePriceAddAmt();

			return PriceOverride.basePricingSystem(
					basePricingSystemId,
					basePriceAddAmt != null ? basePriceAddAmt : BigDecimal.ZERO);
		}
		else if (priceType == PriceOverrideType.FIXED_PRICE)
		{
			final BigDecimal fixedPriceValue = Util.coalesce(changes.getFixedPrice(), price.getFixedPrice().getValue());
			final CurrencyId fixedPriceCurrencyId = Util.coalesce(changes.getFixedPriceCurrencyId(), price.getFixedPrice().getCurrencyId());

			final Money fixedPrice = Money.of(fixedPriceValue, fixedPriceCurrencyId);
			return PriceOverride.fixedPriceOrNone(fixedPrice);
		}
		else
		{
			throw new AdempiereException("Unknow price type: " + priceType);
		}
	}

}
