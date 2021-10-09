package org.eevolution.costing;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import de.metas.costing.CostElementId;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

@UtilityClass
final class BOMAssertUtils
{

	public static void assertOwnCostPrice(final BOM bom, final CostElementId costElementId, final Object expectedValue)
	{
		final BOMCostElementPrice costElementPrice = bom.getCostPrice().getCostElementPriceOrNull(costElementId);
		final BigDecimal ownCostPrice;
		if (costElementPrice == null)
		{
			ownCostPrice = BigDecimal.ZERO;
		}
		else
		{
			ownCostPrice = costElementPrice.getCostPrice().getOwnCostPrice().getValue();
		}

		assertThat(ownCostPrice)
				.as(ContextDescription.builder()
						.title("ownCostPrice")
						.context("bom", bom)
						.context("costElementId", costElementId)
						.build())
				.isEqualTo(toBigDecimal(expectedValue));
	}

	public static void assertComponentsCostPrice(final BOM bom, final CostElementId costElementId, final Object expectedValue)
	{
		final BOMCostElementPrice costElementPrice = bom.getCostPrice().getCostElementPriceOrNull(costElementId);
		final BigDecimal componentsCostPrice;
		if (costElementPrice == null)
		{
			componentsCostPrice = BigDecimal.ZERO;
		}
		else
		{
			componentsCostPrice = costElementPrice.getCostPrice().getComponentsCostPrice().getValue();
		}

		assertThat(componentsCostPrice)
				.as(ContextDescription.builder()
						.title("componentsCostPrice")
						.context("bom", bom)
						.context("costElementId", costElementId)
						.build())
				.isEqualTo(toBigDecimal(expectedValue));
	}

	private static BigDecimal toBigDecimal(final Object valueObj)
	{
		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof BigDecimal)
		{
			return (BigDecimal)valueObj;
		}
		else if (valueObj instanceof Integer)
		{
			return BigDecimal.valueOf((int)valueObj);
		}
		else if (valueObj instanceof String)
		{
			return new BigDecimal(valueObj.toString());
		}
		else
		{
			throw new AdempiereException("Cannot convert " + valueObj + " (" + valueObj.getClass() + ") to BigDecimal");
		}
	}

}
