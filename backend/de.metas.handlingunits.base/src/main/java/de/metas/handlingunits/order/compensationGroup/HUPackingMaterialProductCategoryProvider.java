package de.metas.handlingunits.order.compensationGroup;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.order.compensationGroup.PackingMaterialProductCategoryProvider;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Component
public class HUPackingMaterialProductCategoryProvider implements PackingMaterialProductCategoryProvider
{
	private final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@Override
	public @NonNull ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> getPackingMaterialProductCategoryIdsAndAncestors(@NonNull final Set<HUPIItemProductId> ids)
	{
		final Set<HUPIItemProductId> regularIds = new HashSet<>();
		for (final HUPIItemProductId id : ids)
		{
			if (HUPIItemProductId.isRegular(id))
			{
				regularIds.add(id);
			}
		}
		if (regularIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSetMultimap<HUPIItemProductId, ProductId> packingMaterialProductIdsByPIP = packingMaterialDAO.retrievePackingMaterialProductIdsByPIItemProductIds(regularIds);
		if (packingMaterialProductIdsByPIP.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<ProductId, ImmutableSet<ProductCategoryId>> categoryIdsByProductId = productDAO.getProductCategoryIdAndAncestorsByProductIds(ImmutableSet.copyOf(packingMaterialProductIdsByPIP.values()));

		final ImmutableMap.Builder<HUPIItemProductId, ImmutableSet<ProductCategoryId>> result = ImmutableMap.builder();
		packingMaterialProductIdsByPIP.asMap().forEach((pipId, productIds) -> {
			final ImmutableSet.Builder<ProductCategoryId> categoryIds = ImmutableSet.builder();
			for (final ProductId productId : productIds)
			{
				final ImmutableSet<ProductCategoryId> productCategoryIds = categoryIdsByProductId.get(productId);
				if (productCategoryIds != null)
				{
					categoryIds.addAll(productCategoryIds);
				}
			}
			final ImmutableSet<ProductCategoryId> union = categoryIds.build();
			if (!union.isEmpty())
			{
				result.put(pipId, union);
			}
		});
		return result.build();
	}
}
