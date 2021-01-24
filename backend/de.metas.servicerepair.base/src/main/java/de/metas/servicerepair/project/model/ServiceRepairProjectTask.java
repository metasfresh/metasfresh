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

package de.metas.servicerepair.project.model;

import de.metas.handlingunits.HuId;
import de.metas.inout.InOutAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.project.service.requests.AddQtyToProjectTaskRequest;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class ServiceRepairProjectTask
{
	@NonNull ServiceRepairProjectTaskId id;
	@NonNull ClientAndOrgId clientAndOrgId;
	@NonNull ServiceRepairProjectTaskType type;
	@NonNull ServiceRepairProjectTaskStatus status;

	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId asiId;
	@NonNull Quantity qtyRequired;
	@NonNull Quantity qtyReserved;
	@NonNull Quantity qtyConsumed;

	@Nullable
	InOutAndLineId customerReturnLineId;

	@Nullable PPOrderId repairOrderId;
	boolean isRepairOrderDone;
	@Nullable HuId repairVhuId;

	public ProjectId getProjectId() { return getId().getProjectId(); }

	public UomId getUomId()
	{
		return Quantity.getCommonUomIdOfAll(qtyRequired, qtyReserved, qtyConsumed);
	}

	public Quantity getQtyToReserve()
	{
		return getQtyRequired().subtract(getQtyReservedOrConsumed()).toZeroIfNegative();
	}

	public Quantity getQtyReservedOrConsumed()
	{
		return getQtyReserved().add(getQtyConsumed());
	}

	public ServiceRepairProjectTask reduce(@NonNull final AddQtyToProjectTaskRequest request)
	{
		if (!ProductId.equals(getProductId(), request.getProductId()))
		{
			return this;
		}

		return toBuilder()
				.qtyReserved(getQtyReserved().add(request.getQtyReserved()))
				.qtyConsumed(getQtyConsumed().add(request.getQtyConsumed()))
				.build()
				.withUpdatedStatus();
	}

	public ServiceRepairProjectTask withUpdatedStatus()
	{
		final ServiceRepairProjectTaskStatus newStatus = computeStatus();
		return !newStatus.equals(getStatus())
				? toBuilder().status(newStatus).build()
				: this;
	}

	private ServiceRepairProjectTaskStatus computeStatus()
	{
		final @NonNull ServiceRepairProjectTaskType type = getType();
		switch (type)
		{
			case REPAIR_ORDER:
				return computeStatusForRepairOrderType();
			case SPARE_PARTS:
				return computeStatusForSparePartsType();
			default:
				throw new AdempiereException("Unknown type for " + this);
		}
	}

	private ServiceRepairProjectTaskStatus computeStatusForRepairOrderType()
	{
		if (getRepairOrderId() == null)
		{
			return ServiceRepairProjectTaskStatus.NOT_STARTED;
		}
		else
		{
			return isRepairOrderDone()
					? ServiceRepairProjectTaskStatus.COMPLETED
					: ServiceRepairProjectTaskStatus.IN_PROGRESS;
		}
	}

	private ServiceRepairProjectTaskStatus computeStatusForSparePartsType()
	{
		if (getQtyToReserve().signum() <= 0)
		{
			return ServiceRepairProjectTaskStatus.COMPLETED;
		}
		else if (getQtyReservedOrConsumed().signum() == 0)
		{
			return ServiceRepairProjectTaskStatus.NOT_STARTED;
		}
		else
		{
			return ServiceRepairProjectTaskStatus.IN_PROGRESS;
		}
	}

	public ServiceRepairProjectTask withRepairOrderId(@NonNull final PPOrderId repairOrderId)
	{
		return toBuilder()
				.repairOrderId(repairOrderId)
				.build()
				.withUpdatedStatus();
	}

	public ServiceRepairProjectTask withRepairOrderDone(final boolean isRepairOrderDone)
	{
		return toBuilder()
				.isRepairOrderDone(isRepairOrderDone)
				.build()
				.withUpdatedStatus();
	}
}
