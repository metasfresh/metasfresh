package de.metas.distribution.ddorder.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.request.service.async.spi.impl.C_Request_CreateFromDDOrder_Async;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.List;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

@Interceptor(I_DD_Order.class)
public class DD_Order
{
	private final IMovementBL movementBL = Services.get(IMovementBL.class);
	private final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final DDOrderService ddOrderService;
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService;

	public DD_Order(
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			@NonNull final DDOrderService ddOrderService)
	{
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.ddOrderService = ddOrderService;
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_CLOSE })
	public void clearSchedules(final I_DD_Order ddOrder)
	{
		final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
		if (ddOrderMoveScheduleService.hasInProgressSchedules(ddOrderId))
		{
			throw new AdempiereException("Closing/Reversing is not allowed when there are schedules in progress");
		}

		ddOrderMoveScheduleService.removeNotStarted(ddOrderId);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createRequestsForQuarantineLines(final I_DD_Order ddOrder)
	{
		final List<DDOrderLineId> ddOrderLineToQuarantineIds = retrieveLineToQuarantineWarehouseIds(ddOrder);
		C_Request_CreateFromDDOrder_Async.createWorkpackage(ddOrderLineToQuarantineIds);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_VOID,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void voidMovements(final I_DD_Order ddOrder)
	{
		// void if creating them automating is activated
		if (ddOrderService.isCreateMovementOnComplete())
		{
			final List<I_M_Movement> movements = movementDAO.retrieveMovementsForDDOrder(ddOrder.getDD_Order_ID());
			for (final I_M_Movement movement : movements)
			{
				movementBL.voidMovement(movement);
			}
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createMovementsIfNeeded(final I_DD_Order ddOrder)
	{
		if (ddOrderService.isCreateMovementOnComplete())
		{
			ddOrderService.generateDirectMovements(ddOrder);
		}
	}

	private List<DDOrderLineId> retrieveLineToQuarantineWarehouseIds(final I_DD_Order ddOrder)
	{
		return ddOrderService.retrieveLines(ddOrder)
				.stream()
				.filter(this::isQuarantineWarehouseLine)
				.map(line -> DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID()))
				.collect(ImmutableList.toImmutableList());
	}

	private boolean isQuarantineWarehouseLine(final I_DD_OrderLine ddOrderLine)
	{
		final I_M_Warehouse warehouse = warehouseDAO.getWarehouseByLocatorRepoId(ddOrderLine.getM_LocatorTo_ID());
		return warehouse != null && warehouse.isQuarantineWarehouse();
	}
}
