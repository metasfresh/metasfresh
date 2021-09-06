/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product_Exclude_FlatrateConditions;
import org.springframework.stereotype.Repository;

@Repository
public class FlatrateConditionsExcludedProductsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, FlatrateConditionsExcludedProducts> excludedProductsCache = CCache.<Integer, FlatrateConditionsExcludedProducts>builder()
			.tableName(I_M_Product_Exclude_FlatrateConditions.Table_Name)
			.build();

	public boolean isProductExcludedFromFlatrateConditions(@NonNull final GroupTemplateId groupTemplateId, @NonNull final ProductId productId)
	{
		return getFlatrateConditionsExcludedProducts().isExcluded(groupTemplateId, productId);
	}

	private FlatrateConditionsExcludedProducts getFlatrateConditionsExcludedProducts()
	{
		return excludedProductsCache.getOrLoad(0, this::retrieveFlatrateConditionsExcludedProducts);
	}

	private FlatrateConditionsExcludedProducts retrieveFlatrateConditionsExcludedProducts()
	{
		final ImmutableSet<FlatrateConditionsExcludedProducts.GroupTemplateIdAndProductId> exclusions = queryBL
				.createQueryBuilderOutOfTrx(I_M_Product_Exclude_FlatrateConditions.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(FlatrateConditionsExcludedProductsRepository::fromRecord)
				.collect(ImmutableSet.toImmutableSet());

		return new FlatrateConditionsExcludedProducts(exclusions);
	}

	private static FlatrateConditionsExcludedProducts.GroupTemplateIdAndProductId fromRecord(final I_M_Product_Exclude_FlatrateConditions record)
	{
		return FlatrateConditionsExcludedProducts.GroupTemplateIdAndProductId.of(
				GroupTemplateId.ofRepoId(record.getC_CompensationGroup_Schema_ID()),
				ProductId.ofRepoId(record.getM_Product_ID()));
	}
}
