package de.metas.pricing.conditions;

import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;
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
public class PricingConditionsBreakId
{
	public static final PricingConditionsBreakId of(final int discountSchemaId, final int discountSchemaBreakId)
	{
		return new PricingConditionsBreakId(PricingConditionsId.ofRepoId(discountSchemaId), discountSchemaBreakId);
	}

	public static final PricingConditionsBreakId ofOrNull(final int discountSchemaId, final int discountSchemaBreakId)
	{
		if (discountSchemaId > 0 && discountSchemaBreakId > 0)
		{
			return of(discountSchemaId, discountSchemaBreakId);
		}
		else
		{
			return null;
		}
	}

	public static final boolean matching(final PricingConditionsId id, PricingConditionsBreakId breakId)
	{
		if (id == null)
		{
			return breakId == null;
		}
		else if (breakId == null)
		{
			return true;
		}
		else
		{
			return Objects.equals(id, breakId.getPricingConditionsId());
		}
	}

	public static final void assertMatching(final PricingConditionsId id, PricingConditionsBreakId breakId)
	{
		if (!matching(id, breakId))
		{
			throw new AdempiereException("" + id + " and " + breakId + " are not matching")
					.setParameter("pricingConditionsId", id)
					.setParameter("pricingConditionsBreakId", breakId);
		}
	}

	private final PricingConditionsId pricingConditionsId;
	private final int discountSchemaBreakId;

	private PricingConditionsBreakId(@NonNull final PricingConditionsId pricingConditionsId, final int discountSchemaBreakId)
	{
		Check.assumeGreaterThanZero(discountSchemaBreakId, "discountSchemaBreakId");

		this.pricingConditionsId = pricingConditionsId;
		this.discountSchemaBreakId = discountSchemaBreakId;
	}

	public boolean matchingDiscountSchemaId(final int discountSchemaId)
	{
		return pricingConditionsId.getRepoId() == discountSchemaId;
	}

	public int getDiscountSchemaId()
	{
		return pricingConditionsId.getRepoId();
	}
}
