package de.metas.handlingunits.material.interceptor;

import java.math.BigDecimal;
import java.util.Date;

import org.compiere.model.I_M_Transaction;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.event.commons.EventDescriptor;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * This pojo contains properties needed to create events for {@link I_M_Transaction}s.
 * We need it because we need to construct the events *after* commit, in order to also catch assigned HUs.
 */
@Value
public class TransactionDescriptor
{
	@VisibleForTesting
	public static TransactionDescriptor ofRecord(@NonNull final I_M_Transaction record)
	{
		return new TransactionDescriptor(
				EventDescriptor.createNew(record),
				record.getM_Product_ID(),
				record.getM_Transaction_ID(),
				record.getM_Locator().getM_Warehouse_ID(),
				record.getMovementDate(),
				record.getMovementQty(),
				record.getPP_Cost_Collector_ID(),
				record.getM_InOutLine_ID(),
				record.getM_MovementLine_ID(),
				record.getM_InventoryLine_ID(),
				record.getMovementType());
	}

	EventDescriptor eventDescriptor;

	int productId;
	int transactionId;
	int warehouseId;
	int costCollectorId;
	int inoutLineId;
	int movementLineId;
	int inventoryLineId;
	Date movementDate;
	BigDecimal movementQty;
	String movementType;

	private TransactionDescriptor(
			EventDescriptor eventDescriptor,
			int productId,
			int transactionId,
			int warehouseId,
			Date movementDate,
			BigDecimal movementQty,
			int costCollectorId,
			int inoutLineId,
			int movementLineId,
			int inventoryLineId,
			String movementType)
	{
		this.eventDescriptor = eventDescriptor;
		this.productId = productId;
		this.transactionId = transactionId;
		this.warehouseId = warehouseId;
		this.movementDate = movementDate;
		this.movementQty = movementQty;
		this.costCollectorId = costCollectorId;
		this.inoutLineId = inoutLineId;
		this.movementLineId = movementLineId;
		this.inventoryLineId = inventoryLineId;
		this.movementType = movementType;
	}
}
