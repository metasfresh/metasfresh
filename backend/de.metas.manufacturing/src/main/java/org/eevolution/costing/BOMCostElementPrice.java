package org.eevolution.costing;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.money.CurrencyId;
import de.metas.uom.UomId;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;

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
	public static BOMCostElementPrice zero(
			@NonNull final CostElementId costElementId,
			@NonNull final CurrencyId currencyId,
			@NonNull final UomId uomId)
	{
		return builder()
				.costElementId(costElementId)
				.costPrice(CostPrice.zero(currencyId, uomId))
				.build();
	}

	private RepoIdAware id;

	@NonNull
	private final CostElementId costElementId;

	@NonNull
	private CostPrice costPrice;

	@Nullable
	public <ID extends RepoIdAware> ID getId(@NonNull final Class<ID> idType)
	{
		final RepoIdAware id = getId();
		return id != null ? idType.cast(id) : null;
	}

	public UomId getUomId()
	{
		return getCostPrice().getUomId();
	}

	@NonNull
	public static UomId extractUniqueUomId(@NonNull final Collection<BOMCostElementPrice> list)
	{
		return CollectionUtils.extractSingleElement(list, BOMCostElementPrice::getUomId);
	}

	public void clearOwnCostPrice()
	{
		setCostPrice(getCostPrice().withZeroOwnCostPrice());
	}

	public void clearComponentsCostPrice()
	{
		setCostPrice(getCostPrice().withZeroComponentsCostPrice());
	}

	public void setComponentsCostPrice(CostAmount componentsCostPrice)
	{
		setCostPrice(getCostPrice().withComponentsCostPrice(componentsCostPrice));
	}
}
