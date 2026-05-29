/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.warehouse;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductCategoryId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WarehouseSourceHUConfigList
{
	@NonNull private final ImmutableList<WarehouseSourceHUConfig> warehouseSourceHUConfigs;

	public boolean applies(@NonNull final ProductCategoryId productCategoryId)
	{
		if (warehouseSourceHUConfigs.isEmpty()) {return true;}

		return warehouseSourceHUConfigs.stream()
				.filter(config -> config.getProductCategoryId().equals(productCategoryId))
				.anyMatch(config -> ProductCategoryId.equals(config.getProductCategoryId(), productCategoryId));
	}
}
