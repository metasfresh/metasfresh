package de.metas.handlingunits.model.validator;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.ddorder.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.picking.DDOrderPickFromService;
import de.metas.request.service.async.spi.impl.C_Request_CreateFromDDOrder_Async;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.eevolution.api.DDOrderId;
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
	private final IHUDDOrderBL huDDOrderBL;
	private final DDOrderPickFromService ddOrderPickFromService;

	public DD_Order(
			@NonNull final DDOrderPickFromService ddOrderPickFromService,
			@NonNull final IHUDDOrderBL huDDOrderBL)
	{
		this.ddOrderPickFromService = ddOrderPickFromService;
		this.huDDOrderBL = huDDOrderBL;
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_CLOSE })
	public void clearHUsScheduledToMoveList(final I_DD_Order ddOrder)
	{
		ddOrderPickFromService.removeDraftSchedules(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void DD_Order_Quarantine_Request(final I_DD_Order ddOrder)
	{

		final List<Integer> ddOrderLineToQuarantineIds = retrieveLineToQuarantineWarehouseIds(ddOrder);

		C_Request_CreateFromDDOrder_Async.createWorkpackage(ddOrderLineToQuarantineIds);

	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE, ModelValidator.TIMING_AFTER_VOID, ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void DD_Order_voidMovements(final I_DD_Order ddOrder)
	{
		// void if creating them automating is activated
		if (huDDOrderBL.isCreateMovementOnComplete())
		{
			final List<I_M_Movement> movements = movementDAO.retrieveMovementsForDDOrder(ddOrder.getDD_Order_ID());
			for (final I_M_Movement movement : movements)
			{
				movementBL.voidMovement(movement);
			}
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void DD_Order_createMovementsIfNeeded(final I_DD_Order ddOrder)
	{
		if (huDDOrderBL.isCreateMovementOnComplete())
		{
			huDDOrderBL.generateMovements(ddOrder);
		}
	}

	private List<Integer> retrieveLineToQuarantineWarehouseIds(final I_DD_Order ddOrder)
	{
		return huDDOrderBL.retrieveLines(ddOrder)
				.stream()
				.filter(this::isQuarantineWarehouseLine)
				.map(I_DD_OrderLine::getDD_OrderLine_ID)
				.collect(ImmutableList.toImmutableList())
				;
	}

	private boolean isQuarantineWarehouseLine(final I_DD_OrderLine ddOrderLine)
	{
		final I_M_Warehouse warehouse = warehouseDAO.getWarehouseByLocatorRepoId(ddOrderLine.getM_LocatorTo_ID());
		return warehouse != null && warehouse.isQuarantineWarehouse();
	}

}
