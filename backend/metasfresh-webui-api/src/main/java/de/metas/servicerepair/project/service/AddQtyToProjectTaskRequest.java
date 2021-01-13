/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.project.service;

import de.metas.common.util.CoalesceUtil;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.project.ServiceRepairProjectTaskId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class AddQtyToProjectTaskRequest
{
	@NonNull ServiceRepairProjectTaskId taskId;
	@NonNull Quantity qtyReserved;
	@NonNull Quantity qtyConsumed;

	@Builder
	private AddQtyToProjectTaskRequest(
			@NonNull final ServiceRepairProjectTaskId taskId,
			@Nullable final Quantity qtyReserved,
			@Nullable final Quantity qtyConsumed)
	{
		this.taskId = taskId;

		Quantity.getCommonUomIdOfAll(qtyReserved, qtyConsumed); // assume same UOM
		final Quantity zero = CoalesceUtil.coalesce(qtyReserved, qtyConsumed).toZero();

		this.qtyReserved = qtyReserved != null ? qtyReserved : zero;
		this.qtyConsumed = qtyConsumed != null ? qtyConsumed : zero;
	}

	public UomId getUomId()
	{
		return Quantity.getCommonUomIdOfAll(qtyReserved, qtyConsumed);
	}
}
