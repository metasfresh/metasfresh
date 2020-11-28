package de.metas.pricing.conditions;

import static de.metas.common.util.CoalesceUtil.coalesce;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import javax.annotation.Nullable;

import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
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

	PriceSpecification priceSpecification;

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

	Instant dateCreated;

	UserId createdById;

	boolean hasChanges;

	@Builder(toBuilder = true)
	public PricingConditionsBreak(
			final PricingConditionsBreakId id,
			@NonNull final PricingConditionsBreakMatchCriteria matchCriteria,
			final int seqNo,
			@NonNull final PriceSpecification priceSpecification,
			final boolean bpartnerFlatDiscount,
			final Percent discount,
			@Nullable final PaymentTermId paymentTermIdOrNull,
			@Nullable final Percent paymentDiscountOverrideOrNull,
			@Nullable final PaymentTermId derivedPaymentTermIdOrNull,
			final BigDecimal qualityDiscountPercentage,
			final Instant dateCreated,
			final UserId createdById,
			final boolean hasChanges)
	{
		this.id = id;
		this.matchCriteria = matchCriteria;
		this.seqNo = seqNo;
		this.priceSpecification = priceSpecification;
		this.bpartnerFlatDiscount = bpartnerFlatDiscount;
		this.discount = discount != null ? discount : Percent.ZERO;
		this.qualityDiscountPercentage = qualityDiscountPercentage;

		this.paymentTermIdOrNull = paymentTermIdOrNull;
		this.paymentDiscountOverrideOrNull = paymentDiscountOverrideOrNull;
		this.derivedPaymentTermIdOrNull = derivedPaymentTermIdOrNull;

		this.dateCreated = dateCreated;
		this.createdById = createdById;

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

		return Objects.equals(priceSpecification, reference.priceSpecification)
				&& Objects.equals(coalesce(discount, Percent.ZERO), coalesce(reference.discount, Percent.ZERO))
				&& Objects.equals(coalesce(bpartnerFlatDiscount, Percent.ZERO), coalesce(reference.bpartnerFlatDiscount, Percent.ZERO))
				&& Objects.equals(paymentTermIdOrNull, reference.paymentTermIdOrNull)
				&& Objects.equals(coalesce(paymentDiscountOverrideOrNull, Percent.ZERO), coalesce(reference.paymentDiscountOverrideOrNull, Percent.ZERO))
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

	public PricingConditionsBreak toTemporaryPricingConditionsBreakIfPriceRelevantFieldsChanged(@NonNull final PricingConditionsBreak reference)
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
