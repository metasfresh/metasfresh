package de.metas.pricing.service;

import com.google.common.collect.ImmutableList;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Outcome of {@link IPriceListDAO#mutateCustomerPrices}: one {@link Outcome} per derivative price list that qualified.
 */
@Value
@Builder
public class MutateCustomerPricesResult
{
	@Singular
	ImmutableList<Outcome> outcomes;

	public int getNewVersionsCount()
	{
		return (int)outcomes.stream().filter(Outcome::isNewVersionCreated).count();
	}

	public boolean isEmpty()
	{
		return outcomes.isEmpty();
	}

	public enum SkipReason
	{
		/** The derivative already has an active version valid from the same day as the base version. */
		SAME_DAY_VERSION_EXISTS,
		/** The derivative has no active version that could be copied. */
		NO_ACTIVE_VERSION,
	}

	/**
	 * Either {@link #newPriceListVersionId} (new version created) or {@link #skipReason} (skipped) is set.
	 */
	@Value
	@Builder
	public static class Outcome
	{
		@NonNull PriceListId priceListId;
		@Nullable PriceListVersionId newPriceListVersionId;
		@Nullable SkipReason skipReason;

		public boolean isNewVersionCreated()
		{
			return newPriceListVersionId != null;
		}
	}
}
