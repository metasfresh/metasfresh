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

package de.metas.servicerepair.sales_order;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class RepairSalesOrderInfo
{
	@NonNull OrderId salesOrderId;
	@NonNull OrderId proposalId;
	@NonNull ProjectId projectId;
	@NonNull @Singular ImmutableList<RepairSalesOrderLineInfo> lines;

	public Set<OrderAndLineId> getSalesOrderLineIds()
	{
		return lines.stream()
				.map(RepairSalesOrderLineInfo::getSalesOrderLineId)
				.collect(ImmutableSet.toImmutableSet());
	}

}
