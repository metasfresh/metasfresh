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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.order.OrderAndLineId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value(staticConstructor = "of")
public class QuotationLineIdsByCostCollectorIdIndex
{
	@Getter(AccessLevel.NONE)
	@NonNull ImmutableMap<ServiceRepairProjectCostCollectorId, OrderAndLineId> map;

	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	public ImmutableSet<ServiceRepairProjectCostCollectorId> getCostCollectorIds()
	{
		return map.keySet();
	}

	public OrderAndLineId getOrderAndLineId(@NonNull final ServiceRepairProjectCostCollectorId costCollectorId)
	{
		final OrderAndLineId orderAndLineId = map.get(costCollectorId);
		if (orderAndLineId == null)
		{
			throw new AdempiereException("No order and line ID found for " + costCollectorId + " in " + this);
		}
		return orderAndLineId;
	}
}
