/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.material.cockpit;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Value(staticConstructor = "of")
public class ProductWithDemandSupplyCollection
{
	@NonNull
	Map<ProductId, List<ProductWithDemandSupply>> productId2QtyList;

	@NonNull
	public List<ProductWithDemandSupply> getByProductId(@NonNull final ProductId productId)
	{
		return CoalesceUtil.coalesceNotNull(productId2QtyList.get(productId), ImmutableList.of());
	}

	@NonNull
	public List<ProductWithDemandSupply> getAll()
	{
		return productId2QtyList.values()
				.stream()
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
	}

	public static Collector<ProductWithDemandSupply, ?, ProductWithDemandSupplyCollection> toProductsWithDemandSupply()
	{
		return Collectors.collectingAndThen(Collectors.groupingBy(ProductWithDemandSupply::getProductId),
											ProductWithDemandSupplyCollection::of);
	}
}
