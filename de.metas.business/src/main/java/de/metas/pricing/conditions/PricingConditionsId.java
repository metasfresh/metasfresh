package de.metas.pricing.conditions;

import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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

	public static PricingConditionsId ofRepoId(final int discountSchemaId)
	{
		return new PricingConditionsId(discountSchemaId);
	}

	@Nullable
	public static PricingConditionsId ofRepoIdOrNull(final int discountSchemaId)
	{
		return discountSchemaId > 0 ? new PricingConditionsId(discountSchemaId) : null;
	}

	public static Set<PricingConditionsId> ofDiscountSchemaIds(@NonNull final Collection<Integer> discountSchemaIds)
	{
		return discountSchemaIds.stream()
				.map(PricingConditionsId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static Set<Integer> toDiscountSchemaIds(@NonNull final Collection<PricingConditionsId> ids)
	{
		return ids.stream()
				.map(PricingConditionsId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private final int repoId;

	private PricingConditionsId(final int discountSchemaId)
	{
		Check.assumeGreaterThanZero(discountSchemaId, "discountSchemaId");
		this.repoId = discountSchemaId;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
