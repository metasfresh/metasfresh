package org.eevolution.costing;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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

@Data
@Builder
public class BOMCostElementPrice
{
	public static BOMCostElementPrice zero(final CostElementId costElementId, final CurrencyId currencyId)
	{
		final CostAmount zero = CostAmount.zero(currencyId);
		return builder()
				.costElementId(costElementId)
				.ownCostPrice(zero)
				.componentsCostPrice(zero)
				.build();
	}

	private int repoId;

	@NonNull
	private final CostElementId costElementId;

	@NonNull
	private final CostAmount ownCostPrice;

	@NonNull
	private CostAmount componentsCostPrice;

	public CostAmount getTotalCostPrice()
	{
		return getOwnCostPrice()
				.add(getComponentsCostPrice());
	}

	public void clearComponentsCostPrice()
	{
		setComponentsCostPrice(getComponentsCostPrice().toZero());
	}
}
