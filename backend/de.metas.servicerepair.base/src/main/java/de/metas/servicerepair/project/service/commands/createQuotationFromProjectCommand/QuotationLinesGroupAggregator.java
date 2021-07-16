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

package de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand;

import de.metas.order.OrderAndLineId;
import de.metas.order.OrderFactory;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Stream;

interface QuotationLinesGroupAggregator
{
	static QuotationLinesGroupKey extractKey(final ServiceRepairProjectCostCollector costCollector)
	{
		final ServiceRepairProjectCostCollectorType costCollectorType = costCollector.getType();
		if (costCollectorType == ServiceRepairProjectCostCollectorType.RepairedProductToReturn
				|| costCollectorType == ServiceRepairProjectCostCollectorType.RepairingConsumption)
		{
			return QuotationLinesGroupKey.builder()
					.type(QuotationLinesGroupKey.Type.REPAIRED_PRODUCT)
					.warrantyCase(costCollector.getWarrantyCase())
					.taskId(costCollector.getTaskId())
					.build();
		}
		else
		{
			return QuotationLinesGroupKey.OTHERS;
		}
	}

	void add(@NonNull final ServiceRepairProjectCostCollector costCollector);

	void createOrderLines(OrderFactory orderFactory);

	Stream<Map.Entry<ServiceRepairProjectCostCollectorId, OrderAndLineId>> streamQuotationLineIdsIndexedByCostCollectorId();

	@Nullable
	GroupTemplate getGroupTemplate();
}
