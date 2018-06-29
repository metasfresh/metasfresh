package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import de.metas.lang.Percent;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PriceOverrideType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.PartialPriceChange;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.PartialPriceChange.PartialPriceChangeBuilder;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.PricingConditionsRowChangeRequestBuilder;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
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

	public static PricingConditionsRowChangeRequest toChangeRequest(final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final PricingConditionsRowChangeRequestBuilder builder = PricingConditionsRowChangeRequest.builder()
				.priceChange(toPartialPriceChange(fieldChangeRequests));

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

	private static PartialPriceChange toPartialPriceChange(final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final PartialPriceChangeBuilder builder = PartialPriceChange.builder();
		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();
			if (PricingConditionsRow.FIELDNAME_PriceType.equals(fieldName))
			{
				final LookupValue priceTypeLookupValue = fieldChangeRequest.getValueAsStringLookupValue();
				final PriceOverrideType priceType = priceTypeLookupValue != null ? PriceOverrideType.ofCode(priceTypeLookupValue.getIdAsString()) : null;
				builder.priceType(priceType);
			}
			else if (PricingConditionsRow.FIELDNAME_BasePricingSystem.equals(fieldName))
			{
				final LookupValue pricingSystem = fieldChangeRequest.getValueAsIntegerLookupValue();
				final PricingSystemId pricingSystemId = pricingSystem != null ? PricingSystemId.ofRepoIdOrNull(pricingSystem.getIdAsInt()) : null;
				builder.basePricingSystemId(Optional.ofNullable(pricingSystemId));
			}
			else if (PricingConditionsRow.FIELDNAME_BasePriceAddAmt.equals(fieldName))
			{
				builder.basePriceAddAmt(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO));
			}
			else if (PricingConditionsRow.FIELDNAME_Price.equals(fieldName))
			{
				builder.fixedPrice(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO));
			}
		}

		return builder.build();
	}
}
