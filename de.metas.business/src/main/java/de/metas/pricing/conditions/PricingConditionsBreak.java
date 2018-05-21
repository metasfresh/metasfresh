package de.metas.pricing.conditions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
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
public class PricingConditionsBreak
{
	PricingConditionsBreakId id;
	PricingConditionsBreakMatchCriteria matchCriteria;

	PriceOverride priceOverride;

	//
	// Discount%
	boolean bpartnerFlatDiscount;
	BigDecimal discount;
	int paymentTermId;

	//
	// Quality
	BigDecimal qualityDiscountPercentage;

	LocalDateTime dateCreated;

	@Builder(toBuilder = true)
	public PricingConditionsBreak(
			final PricingConditionsBreakId id,
			@NonNull final PricingConditionsBreakMatchCriteria matchCriteria,
			@NonNull final PriceOverride priceOverride,
			final boolean bpartnerFlatDiscount,
			final BigDecimal discount,
			final int paymentTermId,
			final BigDecimal qualityDiscountPercentage,
			final LocalDateTime dateCreated)
	{
		this.id = id;
		this.matchCriteria = matchCriteria;
		this.priceOverride = priceOverride;
		this.bpartnerFlatDiscount = bpartnerFlatDiscount;
		this.discount = discount;
		this.paymentTermId = paymentTermId > 0 ? paymentTermId : -1;
		this.qualityDiscountPercentage = qualityDiscountPercentage;
		this.dateCreated = dateCreated;
	}

	public PricingConditionsId getPricingConditionsId()
	{
		Check.assumeNotNull(id, "id is not null for {}", this);
		return id.getPricingConditionsId();
	}

	public boolean equalsByPriceRelevantFields(@NonNull final PricingConditionsBreak reference)
	{
		if (this == reference)
		{
			return true;
		}

		return Objects.equals(priceOverride, reference.priceOverride)
				&& Objects.equals(discount, reference.discount)
				&& Objects.equals(bpartnerFlatDiscount, reference.bpartnerFlatDiscount)
				&& Objects.equals(paymentTermId, reference.paymentTermId);
	}

	public boolean isTemporaryPricingConditionsBreak()
	{
		return id == null;
	}

	public PricingConditionsBreak toTemporaryPricingConditionsBreak()
	{
		if (isTemporaryPricingConditionsBreak())
		{
			return this;
		}

		return toBuilder().id(null).build();
	}

	public PricingConditionsBreak toTemporaryPricingConditionsBreakIfPriceRelevantFieldsChanged(final PricingConditionsBreak reference)
	{
		if (isTemporaryPricingConditionsBreak())
		{
			return this;
		}

		if (equalsByPriceRelevantFields(reference))
		{
			return this;
		}

		return toTemporaryPricingConditionsBreak();
	}

}
