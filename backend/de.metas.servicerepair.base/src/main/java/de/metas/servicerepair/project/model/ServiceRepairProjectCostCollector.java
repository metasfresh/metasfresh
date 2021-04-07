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
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.customerreturns.WarrantyCase;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;

@Value
public class ServiceRepairProjectCostCollector
{
	@NonNull ServiceRepairProjectCostCollectorId id;
	@NonNull ServiceRepairProjectTaskId taskId;

	@NonNull ServiceRepairProjectCostCollectorType type;

	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId asiId;
	@NonNull WarrantyCase warrantyCase;
	@NonNull Quantity qtyReserved;
	@NonNull Quantity qtyConsumed;

	@Nullable
	HuId vhuId;

	@Nullable
	OrderAndLineId customerQuotationLineId;

	@Builder
	private ServiceRepairProjectCostCollector(
			@NonNull final ServiceRepairProjectCostCollectorId id,
			@Nullable final ServiceRepairProjectTaskId taskId,
			@NonNull final ServiceRepairProjectCostCollectorType type,
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final WarrantyCase warrantyCase,
			@NonNull final Quantity qtyReserved,
			@NonNull final Quantity qtyConsumed,
			@Nullable final HuId vhuId,
			@Nullable final OrderAndLineId customerQuotationLineId)
	{
		Check.assume(taskId == null || ProjectId.equals(id.getProjectId(), taskId.getProjectId()), "projectId not matching: {}, {}", id, taskId);
		Quantity.getCommonUomIdOfAll(qtyReserved, qtyConsumed);

		this.id = id;
		this.taskId = taskId;
		this.type = type;
		this.productId = productId;
		this.asiId = asiId;
		this.warrantyCase = warrantyCase;
		this.qtyReserved = qtyReserved;
		this.qtyConsumed = qtyConsumed;
		this.vhuId = vhuId;
		this.customerQuotationLineId = customerQuotationLineId;
	}

	public UomId getUomId()
	{
		return Quantity.getCommonUomIdOfAll(qtyReserved, qtyConsumed);
	}

	public Quantity getQtyReservedOrConsumed()
	{
		return getQtyReserved().add(getQtyConsumed());
	}

	public boolean isNotIncludedInCustomerQuotation() { return getCustomerQuotationLineId() == null; }
}
