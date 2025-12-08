package de.metas.pricing.conditions;

import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
public class PricingConditions
{
	private static final Comparator<PricingConditionsBreak> SORT_BY_BREAK_VALUE = Comparator.<PricingConditionsBreak, BigDecimal>comparing(b -> b.getMatchCriteria().getBreakValue())
			.thenComparing(PricingConditionsBreak::getSeqNo);
	private static final Comparator<PricingConditionsBreak> SORT_BY_BREAK_VALUE_DESC = SORT_BY_BREAK_VALUE.reversed();

	PricingConditionsId id;

	boolean active;
	@NonNull Instant validFrom;

	PricingConditionsDiscountType discountType;

	boolean bpartnerFlatDiscount;
	Percent flatDiscount;

	BreakValueType breakValueType;
	AttributeId breakAttributeId;

	List<PricingConditionsBreak> breaks;

	@Builder
	private PricingConditions(
			PricingConditionsId id,
			@Nullable Boolean active,
			@NonNull Instant validFrom,
			PricingConditionsDiscountType discountType,
			boolean bpartnerFlatDiscount,
			Percent flatDiscount,
			BreakValueType breakValueType,
			AttributeId breakAttributeId,
			List<PricingConditionsBreak> breaks)
	{
		this.id = id;
		this.active = active != null && active;
		this.validFrom = validFrom;
		this.discountType = discountType;
		this.bpartnerFlatDiscount = bpartnerFlatDiscount;
		this.flatDiscount = flatDiscount;
		this.breakValueType = breakValueType;
		this.breakAttributeId = breakAttributeId;
		this.breaks = breaks;
	}

	public boolean isBreaksDiscountType()
	{
		return PricingConditionsDiscountType.BREAKS.equals(discountType);
	}

	/**
	 * Pick the first break that applies based on product, category and attribute instance
	 *
	 * @return schema break or null
	 */
	public PricingConditionsBreak pickApplyingBreak(final @NonNull PricingConditionsBreakQuery query)
	{
		final BigDecimal breakValue = extractBreakValue(query);
		if (breakValue == null)
		{
			return null;
		}

		return breaks.stream()
				.sorted(SORT_BY_BREAK_VALUE_DESC)
				.filter(schemaBreak -> schemaBreakMatches(schemaBreak, breakValue, query))
				.findFirst()
				.orElse(null);
	}

	private boolean schemaBreakMatches(
			final PricingConditionsBreak schemaBreak,
			final BigDecimal breakValue,
			final PricingConditionsBreakQuery query)
	{
		final PricingConditionsBreakMatchCriteria matchCriteria = schemaBreak.getMatchCriteria();
		return matchCriteria.breakValueMatches(breakValue)
				&& matchCriteria.productMatches(query.getProduct())
				&& matchCriteria.attributeMatches(query.getAttributes());
	}

	private BigDecimal extractBreakValue(final PricingConditionsBreakQuery query)
	{
		if (!isBreaksDiscountType())
		{
			throw new AdempiereException("DiscountType shall be Breaks: " + this);
		}
		else if (breakValueType == BreakValueType.QUANTITY)
		{
			return query.getQty();
		}
		else if (breakValueType == BreakValueType.AMOUNT)
		{
			return query.getAmt();
		}
		else if (breakValueType == BreakValueType.ATTRIBUTE)
		{
			final ImmutableAttributeSet attributes = query.getAttributes();
			if (attributes.hasAttribute(breakAttributeId))
			{
				return attributes.getValueAsBigDecimal(breakAttributeId);
			}
			else
			{
				return null;
			}
		}
		else
		{
			throw new AdempiereException("Unknown break value type: " + breakValueType);
		}
	}

	public Stream<PricingConditionsBreak> streamBreaksMatchingAnyOfProducts(final Set<ProductAndCategoryAndManufacturerId> products)
	{
		Check.assumeNotEmpty(products, "products is not empty");

		return getBreaks()
				.stream()
				.filter(schemaBreak -> schemaBreak.getMatchCriteria().productMatchesAnyOf(products));
	}

	public PricingConditionsBreak getBreakById(@NonNull final PricingConditionsBreakId breakId)
	{
		PricingConditionsBreakId.assertMatching(getId(), breakId);

		return getBreaks()
				.stream()
				.filter(schemaBreak -> breakId.equals(schemaBreak.getId()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No break found for " + breakId + " in " + this));
	}
}
