/*
 * #%L
 * de.metas.business
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

package de.metas.warehouseassignment;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import java.util.Set;

@Value
@Builder(toBuilder = true)
public class ProductWarehouseAssignments
{
	@NonNull ProductId productId;

	@NonNull ImmutableSet<WarehouseId> warehouseIds;

	public boolean isWarehouseAssigned(@NonNull final WarehouseId warehouseId)
	{
		return warehouseIds.contains(warehouseId);
	}

	@NonNull
	public ProductWarehouseAssignments addAssignments(@NonNull final Set<WarehouseId> warehouseIdsToAdd)
	{
		return toBuilder()
				.warehouseIds(Sets.union(warehouseIds, warehouseIdsToAdd).immutableCopy())
				.build();
	}
}
