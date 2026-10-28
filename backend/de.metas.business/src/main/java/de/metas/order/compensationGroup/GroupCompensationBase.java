package de.metas.order.compensationGroup;

import de.metas.product.ProductCategoryId;
import lombok.NonNull;
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
 * The base a compensation (discount) line is computed on: a product category and/or a packing-material category.
 * A {@code null} part does not restrict the regular lines; {@link #NONE} means the whole group.
 */
@Value(staticConstructor = "of")
public class GroupCompensationBase
{
	public static final GroupCompensationBase NONE = of(null, null);

	@Nullable ProductCategoryId productCategoryId;
	@Nullable ProductCategoryId packingMaterialProductCategoryId;

	public boolean isNone()
	{
		return productCategoryId == null && packingMaterialProductCategoryId == null;
	}

	public boolean isMatching(@NonNull final GroupRegularLine line)
	{
		return (productCategoryId == null || line.getProductCategoryIds().contains(productCategoryId))
				&& (packingMaterialProductCategoryId == null || line.getPackingMaterialProductCategoryIds().contains(packingMaterialProductCategoryId));
	}
}
