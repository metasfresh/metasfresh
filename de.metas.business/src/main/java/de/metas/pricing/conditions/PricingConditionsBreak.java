package de.metas.pricing.conditions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.annotation.Nullable;

import de.metas.lang.Percent;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.Check;
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
	int seqNo;

	PriceOverride priceOverride;

	//
	// Discount%
	boolean bpartnerFlatDiscount;
	Percent discount;

	//
	// PaymentTerm
	PaymentTermId paymentTermIdOrNull;
	Percent paymentDiscountOverrideOrNull;

	/** created from {@link #getPaymentTermId()} plus {@link #paymentTermDiscountOverrideOrNull}. */
	PaymentTermId derivedPaymentTermIdOrNull;

	//
	// Quality
	BigDecimal qualityDiscountPercentage;

	LocalDateTime dateCreated;
	boolean hasChanges;

	@Builder(toBuilder = true)
	public PricingConditionsBreak(
			final PricingConditionsBreakId id,
			@NonNull final PricingConditionsBreakMatchCriteria matchCriteria,
			final int seqNo,
			@NonNull final PriceOverride priceOverride,
			final boolean bpartnerFlatDiscount,
			final Percent discount,
			@Nullable final PaymentTermId paymentTermIdOrNull,
			@Nullable final Percent paymentDiscountOverrideOrNull,
			@Nullable final PaymentTermId derivedPaymentTermIdOrNull,
			final BigDecimal qualityDiscountPercentage,
			final LocalDateTime dateCreated,
			final boolean hasChanges)
	{
		this.id = id;
		this.matchCriteria = matchCriteria;
		this.seqNo = seqNo;
		this.priceOverride = priceOverride;
		this.bpartnerFlatDiscount = bpartnerFlatDiscount;
		this.discount = discount != null ? discount : Percent.ZERO;
		this.qualityDiscountPercentage = qualityDiscountPercentage;

		this.paymentTermIdOrNull = paymentTermIdOrNull;
		this.paymentDiscountOverrideOrNull = paymentDiscountOverrideOrNull;
		this.derivedPaymentTermIdOrNull = derivedPaymentTermIdOrNull;

		this.dateCreated = dateCreated;
		this.hasChanges = hasChanges;
	}

	public PricingConditionsId getPricingConditionsId()
	{
		Check.assumeNotNull(id, "id is not null for {}", this);
		return id.getPricingConditionsId();
	}

	public PricingConditionsId getPricingConditionsIdOrNull()
	{
		return id != null ? id.getPricingConditionsId() : null;
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
				&& Objects.equals(paymentTermIdOrNull, reference.paymentTermIdOrNull)
				&& Objects.equals(paymentDiscountOverrideOrNull, reference.paymentDiscountOverrideOrNull)
				&& Objects.equals(derivedPaymentTermIdOrNull, reference.derivedPaymentTermIdOrNull);
	}

	public boolean isTemporaryPricingConditionsBreak()
	{
		return hasChanges || id == null;
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
