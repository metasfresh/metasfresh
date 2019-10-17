/**
 *
 */
package de.metas.pricing.conditions.service;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.pricing.IPricingContext;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@Immutable
public class CalculatePricingConditionsRequest
{
	private final PricingConditionsId pricingConditionsId;

	/** If set then the system will "calculate" this break. */
	private final PricingConditionsBreak forcePricingConditionsBreak;

	/** If {@link #getForcePricingConditionsBreak()} returns {@code null}, then the system will use this query to find a matching break. */
	private final PricingConditionsBreakQuery pricingConditionsBreakQuery;

	private final Percent bpartnerFlatDiscount;

	private final IPricingContext pricingCtx;

	@Builder
	private CalculatePricingConditionsRequest(
			final PricingConditionsId pricingConditionsId,
			final PricingConditionsBreak forcePricingConditionsBreak,
			final PricingConditionsBreakQuery pricingConditionsBreakQuery,
			final Percent bpartnerFlatDiscount,
			final IPricingContext pricingCtx)
	{
		assertValid(pricingConditionsId, forcePricingConditionsBreak, pricingConditionsBreakQuery);

		this.pricingConditionsId = extractPricingConditionsIdOrNull(pricingConditionsId, forcePricingConditionsBreak);

		this.forcePricingConditionsBreak = forcePricingConditionsBreak;
		this.pricingConditionsBreakQuery = pricingConditionsBreakQuery;

		this.bpartnerFlatDiscount = bpartnerFlatDiscount != null ? bpartnerFlatDiscount : Percent.ZERO;
		this.pricingCtx = pricingCtx;
	}

	private static void assertValid(
			final PricingConditionsId pricingConditionsId,
			final PricingConditionsBreak forcePricingConditionsBreak,
			final PricingConditionsBreakQuery pricingConditionsBreakQuery)
	{
		if (forcePricingConditionsBreak == null && pricingConditionsBreakQuery == null)
		{
			// TODO support PricingConditions that are not backed by discount schema breaks
			throw new AdempiereException("forcePricingConditionsBreak or pricingConditionsBreakQuery shall be specified");
		}
		else if (forcePricingConditionsBreak != null && pricingConditionsBreakQuery != null)
		{
			throw new AdempiereException("Only forcePricingConditionsBreak or pricingConditionsBreakQuery shall be specified but not both");
		}
		if (forcePricingConditionsBreak != null && pricingConditionsId != null)
		{
			PricingConditionsBreakId.assertMatching(pricingConditionsId, forcePricingConditionsBreak.getId());
		}
	}

	private static final PricingConditionsId extractPricingConditionsIdOrNull(
			@Nullable final PricingConditionsId pricingConditionsId,
			@Nullable final PricingConditionsBreak forcePricingConditionsBreak)
	{
		// note that in case both are given, we already asserted that they match
		if (pricingConditionsId != null)
		{
			return pricingConditionsId;
		}
		else if (forcePricingConditionsBreak != null)
		{
			final PricingConditionsBreakId pricingConditionsBreakId = forcePricingConditionsBreak.getId();
			if (pricingConditionsBreakId != null)
			{
				return pricingConditionsBreakId.getPricingConditionsId();
			}
			else
			{
				return null; // pricing conditions ID not available but OK
			}
		}
		else
		{
			throw new AdempiereException("Cannot extract " + PricingConditionsId.class);
		}
	}
}
