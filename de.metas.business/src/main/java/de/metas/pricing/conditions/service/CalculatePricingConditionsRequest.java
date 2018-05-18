/**
 *
 */
package de.metas.pricing.conditions.service;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.pricing.IPricingContext;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.pricing.conditions.PricingConditionsId;
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

	private final PricingConditionsBreak forceSchemaBreak;
	private final PricingConditionsBreakQuery schemaBreakQuery;

	private final BigDecimal bpartnerFlatDiscount;

	private final IPricingContext pricingCtx;

	@Builder
	private CalculatePricingConditionsRequest(
			final PricingConditionsId pricingConditionsId,
			final PricingConditionsBreak forceSchemaBreak,
			final PricingConditionsBreakQuery schemaBreakQuery,
			final BigDecimal bpartnerFlatDiscount,
			final IPricingContext pricingCtx)
	{
		assertValid(pricingConditionsId, forceSchemaBreak, schemaBreakQuery);

		this.pricingConditionsId = extractPricingConditionsId(pricingConditionsId, forceSchemaBreak);

		this.forceSchemaBreak = forceSchemaBreak;
		this.schemaBreakQuery = schemaBreakQuery;

		this.bpartnerFlatDiscount = bpartnerFlatDiscount != null ? bpartnerFlatDiscount : BigDecimal.ZERO;
		this.pricingCtx = pricingCtx;
	}

	private static void assertValid(final PricingConditionsId pricingConditionsId, final PricingConditionsBreak forceSchemaBreak, final PricingConditionsBreakQuery schemaBreakQuery)
	{
		if (forceSchemaBreak == null && schemaBreakQuery == null)
		{
			throw new AdempiereException("forceSchemaBreak or schemaBreakQuery shall be specified");
		}
		else if (forceSchemaBreak != null && schemaBreakQuery != null)
		{
			throw new AdempiereException("Only forceSchemaBreak or schemaBreakQuery shall be specified but not both");
		}
		if (forceSchemaBreak != null && pricingConditionsId != null)
		{
			PricingConditionsBreakId.assertMatching(pricingConditionsId, forceSchemaBreak.getId());
		}
	}

	private static final PricingConditionsId extractPricingConditionsId(final PricingConditionsId pricingConditionsId, final PricingConditionsBreak forceSchemaBreak)
	{
		if (pricingConditionsId != null)
		{
			return pricingConditionsId;
		}
		else if (forceSchemaBreak != null)
		{
			return forceSchemaBreak.getPricingConditionsId();
		}
		else
		{
			throw new AdempiereException("Cannot extract " + PricingConditionsId.class);
		}
	}
}
