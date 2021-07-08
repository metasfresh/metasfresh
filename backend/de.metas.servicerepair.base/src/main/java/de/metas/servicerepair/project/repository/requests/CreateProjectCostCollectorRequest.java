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

package de.metas.servicerepair.project.repository.requests;

import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.customerreturns.WarrantyCase;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.eevolution.api.PPOrderAndCostCollectorId;

import javax.annotation.Nullable;

@Value
public class CreateProjectCostCollectorRequest
{
	@NonNull ServiceRepairProjectTaskId taskId;

	@NonNull ServiceRepairProjectCostCollectorType type;

	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId asiId;
	@NonNull WarrantyCase warrantyCase;
	@NonNull Quantity qtyReserved;
	@NonNull Quantity qtyConsumed;

	@Nullable HuId reservedVhuId;

	@Nullable PPOrderAndCostCollectorId repairOrderCostCollectorId;

	@Builder
	private CreateProjectCostCollectorRequest(
			@NonNull final ServiceRepairProjectTaskId taskId,
			@NonNull final ServiceRepairProjectCostCollectorType type,
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId,
			@Nullable final WarrantyCase warrantyCase,
			@Nullable final Quantity qtyReserved,
			@Nullable final Quantity qtyConsumed,
			@Nullable final HuId reservedVhuId,
			@Nullable final PPOrderAndCostCollectorId repairOrderCostCollectorId)
	{
		final Quantity qtyFirstNotNull = CoalesceUtil.coalesce(qtyReserved, qtyConsumed);
		if (qtyFirstNotNull == null)
		{
			throw new AdempiereException("At least one qty shall be set");
		}

		this.taskId = taskId;
		this.type = type;
		this.productId = productId;
		this.asiId = asiId != null ? asiId : AttributeSetInstanceId.NONE;
		this.warrantyCase = warrantyCase != null ? warrantyCase : WarrantyCase.NO;
		this.qtyReserved = qtyReserved != null ? qtyReserved : qtyFirstNotNull.toZero();
		this.qtyConsumed = qtyConsumed != null ? qtyConsumed : qtyFirstNotNull.toZero();
		this.reservedVhuId = reservedVhuId;
		this.repairOrderCostCollectorId = repairOrderCostCollectorId;
	}

	public UomId getUomId()
	{
		return Quantity.getCommonUomIdOfAll(qtyReserved, qtyConsumed);
	}
}
