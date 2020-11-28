package de.metas.pricing.conditions.service;

import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.conditions.PriceSpecification;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakMatchCriteria;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.util.lang.Percent;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class PricingConditionsBreakChangeRequest
{
	PricingConditionsId pricingConditionsId;
	PricingConditionsBreakId pricingConditionsBreakId;

	PricingConditionsBreakId updateFromPricingConditionsBreakId;

	PricingConditionsBreakMatchCriteria matchCriteria;

	//
	// Price
	PriceSpecification price;

	// Discount
	Percent discount;

	/** {@code null} means that no change is requested. Empty means "change ID to null". */
	@Nullable
	Optional<PaymentTermId> paymentTermId;
	/** {@code null} means that no change is requested. Empty means "change value to null" */
	@Nullable
	Optional<Percent> paymentDiscount;
}
