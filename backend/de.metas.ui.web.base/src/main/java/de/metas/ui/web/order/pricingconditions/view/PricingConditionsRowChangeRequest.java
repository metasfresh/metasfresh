package de.metas.ui.web.order.pricingconditions.view;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import de.metas.money.CurrencyId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PriceSpecification;
import de.metas.pricing.conditions.PriceSpecificationType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

@Value
@Builder
public class PricingConditionsRowChangeRequest
{
	PricingConditionsBreak pricingConditionsBreak;

	PricingConditionsBreakId sourcePricingConditionsBreakId;

	Percent discount;

	/** {@code null} means that no change is requested. Empty means "change ID to null". */
	@Nullable
	Optional<PaymentTermId> paymentTermId;

	/** {@code null} means that no change is requested. Empty means "change value to null". */
	@Nullable
	Optional<Percent> paymentDiscount;

	PriceChange priceChange;

	public static interface PriceChange
	{
	}

	@lombok.Value
	@lombok.Builder
	public static final class PartialPriceChange implements PriceChange
	{
		/** Currently this field is just for debugging. But might also be used to distinguish between fields that were changed to null and fields that were not changed. */
		@Singular
		Set<String> changedFieldNames;

		PriceSpecificationType priceType;

		Optional<PricingSystemId> basePricingSystemId;
		BigDecimal pricingSystemSurchargeAmt;

		BigDecimal fixedPriceAmt;

		CurrencyId currencyId;
		
		CurrencyId defaultCurrencyId;
	}

	@lombok.Value(staticConstructor = "of")
	public static final class CompletePriceChange implements PriceChange
	{
		@NonNull
		PriceSpecification price;
	}
}
