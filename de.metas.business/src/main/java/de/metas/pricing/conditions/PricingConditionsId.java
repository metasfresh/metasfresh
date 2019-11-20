package de.metas.pricing.conditions;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
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
// todo maybe this shouldn't be made repo id aware, but i wanna see with my eyes why it doesn't work!
public class PricingConditionsId implements RepoIdAware
{
	public static final PricingConditionsId ofDiscountSchemaId(final int discountSchemaId)
	{
		return new PricingConditionsId(discountSchemaId);
	}

	public static final PricingConditionsId ofDiscountSchemaIdOrNull(final int discountSchemaId)
	{
		return discountSchemaId > 0 ? new PricingConditionsId(discountSchemaId) : null;
	}

	public static final Set<PricingConditionsId> ofDiscountSchemaIds(final Collection<Integer> discountSchemaIds)
	{
		return discountSchemaIds.stream()
				.map(PricingConditionsId::ofDiscountSchemaId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static final Set<Integer> toDiscountSchemaIds(final Collection<PricingConditionsId> ids)
	{
		return ids.stream()
				.map(PricingConditionsId::getDiscountSchemaId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private final int discountSchemaId;

	private PricingConditionsId(final int discountSchemaId)
	{
		Check.assumeGreaterThanZero(discountSchemaId, "discountSchemaId");
		this.discountSchemaId = discountSchemaId;
	}

	@Override
	public int getRepoId()
	{
		return discountSchemaId;
	}
}
