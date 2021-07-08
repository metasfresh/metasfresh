/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.repair_order;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderAndCostCollectorId;
import org.eevolution.api.PPOrderId;

@Value
@Builder
public class RepairManufacturingOrderInfo
{
	@NonNull PPOrderId id;
	@NonNull ProjectId projectId;

	@NonNull ProductId repairedProductId;
	/**
	 * shall be ONE
	 */
	@NonNull Quantity repairedQty;

	@NonNull ImmutableList<RepairManufacturingCostCollector> costCollectors;

	public ImmutableSet<PPOrderAndCostCollectorId> getCostCollectorIds()
	{
		return getCostCollectors().stream()
				.map(RepairManufacturingCostCollector::getId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
