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

import static org.compiere.util.TimeUtil.asInstant;
import static org.compiere.util.TimeUtil.getDay;

import java.sql.Timestamp;
import java.time.Instant;

import org.adempiere.mmovement.MovementLineId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Transaction;
import org.eevolution.api.PPCostCollectorId;

import com.google.common.annotations.VisibleForTesting;

import de.metas.inout.InOutLineId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

public final class TransactionDescriptorFactory
{
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);

	public TransactionDescriptor ofRecord(@NonNull final I_M_Transaction record)
	{
		final WarehouseId warehouseId = warehousesRepo.getWarehouseIdByLocatorRepoId(record.getM_Locator_ID());

		return TransactionDescriptor.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.transactionId(record.getM_Transaction_ID())
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.warehouseId(warehouseId)
				.transactionDate(extractTransactionDate(record))
				.movementQty(record.getMovementQty())
				.costCollectorId(PPCostCollectorId.ofRepoIdOrNull(record.getPP_Cost_Collector_ID()))
				.inoutLineId(InOutLineId.ofRepoIdOrNull(record.getM_InOutLine_ID()))
				.movementLineId(MovementLineId.ofRepoIdOrNull(record.getM_MovementLine_ID()))
				.inventoryLineId(InventoryLineId.ofRepoIdOrNull(record.getM_InventoryLine_ID()))
				.movementType(record.getMovementType())
				.build();
	}

	private static Instant extractTransactionDate(@NonNull final I_M_Transaction record)
	{
		final Timestamp movementDate = record.getMovementDate();
		final Timestamp movementDateDay = getDay(movementDate);

		final boolean movementDateContainsTime = !movementDate.equals(movementDateDay);
		if (movementDateContainsTime)
		{
			return asInstant(movementDate);
		}

		// try to fall back to the M_Transaction's created or update date, to get the actual movement date *and time*.
		final Timestamp created = record.getCreated();
		if (movementDateDay.equals(getDay(created)))
		{
			return asInstant(created);
		}

		final Timestamp updated = record.getUpdated();
		if (movementDateDay.equals(getDay(updated)))
		{
			return asInstant(updated);
		}

		return asInstant(movementDate);
	}
}
