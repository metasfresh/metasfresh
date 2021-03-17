/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.material.interceptor.transactionevent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.adempiere.mmovement.MovementLineId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Transaction;
import org.eevolution.api.PPCostCollectorId;

import de.metas.inout.InOutLineId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * This pojo contains properties needed to create events for {@link I_M_Transaction}s.
 * We need it because we need to construct the events *after* commit, in order to also catch assigned HUs.
 */
@Value
public final class TransactionDescriptor
{
	EventDescriptor eventDescriptor;

	int transactionId;

	ProductId productId;
	WarehouseId warehouseId;

	PPCostCollectorId costCollectorId;
	InOutLineId inoutLineId;
	MovementLineId movementLineId;
	InventoryLineId inventoryLineId;

	Instant transactionDate;
	BigDecimal movementQty;
	String movementType;

	List<HUDescriptor> huDescriptors;

	@Builder
	private TransactionDescriptor(
			final EventDescriptor eventDescriptor,
			final int transactionId,
			final ProductId productId,
			final WarehouseId warehouseId,
			final Instant transactionDate,
			final BigDecimal movementQty,
			//
			final PPCostCollectorId costCollectorId,
			final InOutLineId inoutLineId,
			final MovementLineId movementLineId,
			final InventoryLineId inventoryLineId,
			//
			final String movementType,
			@Singular final List<HUDescriptor> huDescriptors)
	{
		this.eventDescriptor = eventDescriptor;
		this.transactionId = transactionId;
		this.productId = productId;
		this.warehouseId = warehouseId;
		this.transactionDate = transactionDate;
		this.movementQty = movementQty;
		this.costCollectorId = costCollectorId;
		this.inoutLineId = inoutLineId;
		this.movementLineId = movementLineId;
		this.inventoryLineId = inventoryLineId;
		this.movementType = movementType;
		this.huDescriptors = huDescriptors;
	}
}
