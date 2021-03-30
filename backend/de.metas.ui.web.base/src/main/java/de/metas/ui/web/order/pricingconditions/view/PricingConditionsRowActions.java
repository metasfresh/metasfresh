package de.metas.ui.web.order.pricingconditions.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import de.metas.money.CurrencyId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PriceSpecificationType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest.PartialPriceChange;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest.PartialPriceChange.PartialPriceChangeBuilder;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowChangeRequest.PricingConditionsRowChangeRequestBuilder;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
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
public class PricingConditionsRowActions
{
	public static final PricingConditionsRowChangeRequest saved(@NonNull final PricingConditionsBreak pricingConditionsBreak)
	{
		return PricingConditionsRowChangeRequest.builder()
				.pricingConditionsBreak(pricingConditionsBreak)
				.build();
	}

	public static PricingConditionsRowChangeRequest toChangeRequest(
			final List<JSONDocumentChangedEvent> fieldChangeRequests,
			final CurrencyId defaultCurrencyId)
	{
		final PricingConditionsRowChangeRequestBuilder builder = PricingConditionsRowChangeRequest.builder()
				.priceChange(toPartialPriceChange(fieldChangeRequests, defaultCurrencyId));

		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();
			if (PricingConditionsRow.FIELDNAME_Discount.equals(fieldName))
			{
				builder.discount(Percent.of(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO)));
			}
			else if (PricingConditionsRow.FIELDNAME_PaymentTerm.equals(fieldName))
			{
				final LookupValue paymentTerm = fieldChangeRequest.getValueAsIntegerLookupValue();
				final PaymentTermId paymentTermId = paymentTerm != null ? PaymentTermId.ofRepoIdOrNull(paymentTerm.getIdAsInt()) : null;
				builder.paymentTermId(Optional.ofNullable(paymentTermId));
			}
			else if (PricingConditionsRow.FIELDNAME_PaymentDiscount.equals(fieldName))
			{
				final BigDecimal valueAsBigDecimal = fieldChangeRequest.getValueAsBigDecimal(null);
				if (valueAsBigDecimal != null)
				{
					builder.paymentDiscount(Optional.of(Percent.of(valueAsBigDecimal)));
				}
				else
				{
					builder.paymentDiscount(Optional.empty());
				}
			}
		}

		return builder.build();
	}

	private static PartialPriceChange toPartialPriceChange(
			final List<JSONDocumentChangedEvent> fieldChangeRequests,
			final CurrencyId defaultCurrencyId)
	{
		final PartialPriceChangeBuilder builder = PartialPriceChange.builder()
				.defaultCurrencyId(defaultCurrencyId);
		
		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();

			builder.changedFieldName(fieldName);

			if (PricingConditionsRow.FIELDNAME_BasePriceType.equals(fieldName))
			{
				final LookupValue priceTypeLookupValue = fieldChangeRequest.getValueAsStringLookupValue();
				final PriceSpecificationType priceType = priceTypeLookupValue != null ? PriceSpecificationType.ofCode(priceTypeLookupValue.getIdAsString()) : null;
				builder.priceType(priceType);
			}
			else if (PricingConditionsRow.FIELDNAME_BasePricingSystem.equals(fieldName))
			{
				final LookupValue pricingSystem = fieldChangeRequest.getValueAsIntegerLookupValue();
				final PricingSystemId pricingSystemId = pricingSystem != null ? PricingSystemId.ofRepoIdOrNull(pricingSystem.getIdAsInt()) : null;
				builder.basePricingSystemId(Optional.ofNullable(pricingSystemId));
			}
			else if (PricingConditionsRow.FIELDNAME_PricingSystemSurcharge.equals(fieldName))
			{
				builder.pricingSystemSurchargeAmt(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO));
			}
			else if (PricingConditionsRow.FIELDNAME_BasePrice.equals(fieldName))
			{
				builder.fixedPriceAmt(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO));
			}
			else if (PricingConditionsRow.FIELDNAME_C_Currency_ID.equals(fieldName))
			{
				final LookupValue currency = fieldChangeRequest.getValueAsIntegerLookupValue();
				final CurrencyId currencyId = currency != null ? CurrencyId.ofRepoIdOrNull(currency.getIdAsInt()) : null;
				builder.currencyId(currencyId);
				
				if(currencyId != null)
				{
					builder.defaultCurrencyId(currencyId);
				}
			}
		}

		return builder.build();
	}
}
