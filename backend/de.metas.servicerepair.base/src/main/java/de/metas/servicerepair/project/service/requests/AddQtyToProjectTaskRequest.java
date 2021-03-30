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

package de.metas.servicerepair.project.service.requests;

import de.metas.common.util.CoalesceUtil;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class AddQtyToProjectTaskRequest
{
	@NonNull ServiceRepairProjectTaskId taskId;
	@NonNull ProductId productId;
	@NonNull Quantity qtyReserved;
	@NonNull Quantity qtyConsumed;

	@Builder(toBuilder = true)
	private AddQtyToProjectTaskRequest(
			@NonNull final ServiceRepairProjectTaskId taskId,
			@NonNull final ProductId productId,
			@Nullable final Quantity qtyReserved,
			@Nullable final Quantity qtyConsumed)
	{
		final Quantity firstQtyNotNull = CoalesceUtil.coalesce(qtyReserved, qtyConsumed);
		if (firstQtyNotNull == null)
		{
			throw new AdempiereException("At least one qty shall be specified");
		}
		Quantity.getCommonUomIdOfAll(qtyReserved, qtyConsumed); // assume same UOM

		this.taskId = taskId;
		this.productId = productId;
		this.qtyReserved = qtyReserved != null ? qtyReserved : firstQtyNotNull.toZero();
		this.qtyConsumed = qtyConsumed != null ? qtyConsumed : firstQtyNotNull.toZero();
	}

	public UomId getUomId()
	{
		return Quantity.getCommonUomIdOfAll(qtyReserved, qtyConsumed);
	}

	public AddQtyToProjectTaskRequest negate()
	{
		return toBuilder()
				.qtyReserved(qtyReserved.negate())
				.qtyConsumed(qtyConsumed.negate())
				.build();
	}

}
