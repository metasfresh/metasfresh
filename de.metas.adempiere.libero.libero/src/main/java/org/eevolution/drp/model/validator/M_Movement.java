package org.eevolution.drp.model.validator;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;

import de.metas.quantity.Quantity;
import de.metas.util.Services;

@Validator(I_M_Movement.class)
public class M_Movement
{
	/**
	 * After Movement Complete, update DD_OrderLine
	 * <ul>
	 * <li>updates QtyInTransit
	 * <li>updated QtyDelivered
	 * </ul>
	 *
	 * @param movement
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(final I_M_Movement movement)
	{
		// services
		final IMovementBL movementBL = Services.get(IMovementBL.class);
		final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
		final IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
		final IDDOrderDAO ddOrdersRepo = Services.get(IDDOrderDAO.class);

		if (movement.getDD_Order_ID() > 0)
		{
			final I_DD_Order order = movement.getDD_Order();
			order.setIsInTransit(true);
			ddOrdersRepo.save(order);
		}

		final List<I_M_MovementLine> movementLines = movementDAO.retrieveLines(movement);
		for (final I_M_MovementLine movementLine : movementLines)
		{
			//
			// Update line OR alternative if it's set
			final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt = retrieveDD_OrderLine_Or_Alternative(movementLine);
			if (ddOrderLineOrAlt == null)
			{
				continue;
			}

			final I_C_UOM uom = ddOrderLineOrAlt.getC_UOM();
			final Quantity movementQty = movementBL.getMovementQty(movementLine, uom);

			final Quantity qtyInTransit = new Quantity(ddOrderLineOrAlt.getQtyInTransit(), uom);
			final boolean isMovementReceipt = ddOrderBL.isMovementReceipt(movementLine);

			//
			// Movement-Shipment: Source Warehouse -> InTransit Warehouse
			if (!isMovementReceipt)
			{
				final Quantity qtyInTransitNew = qtyInTransit.add(movementQty);
				ddOrderLineOrAlt.setQtyInTransit(qtyInTransitNew.toBigDecimal());
			}
			//
			// Movement-Receipt: InTransit Warehouse -> Destination Warehouse
			else
			{
				final Quantity qtyInTransitNew = qtyInTransit.subtract(movementQty);
				ddOrderLineOrAlt.setQtyInTransit(qtyInTransitNew.toBigDecimal());

				final Quantity qtyDelivered = new Quantity(ddOrderLineOrAlt.getQtyDelivered(), uom);
				final Quantity qtyDeliveredNew = qtyDelivered.add(movementQty);
				ddOrderLineOrAlt.setQtyDelivered(qtyDeliveredNew.toBigDecimal());
			}

			ddOrdersRepo.save(ddOrderLineOrAlt);
		}
	}

	private I_DD_OrderLine_Or_Alternative retrieveDD_OrderLine_Or_Alternative(final I_M_MovementLine movementLine)
	{
		final I_DD_OrderLine_Alternative ddOrderLineAlt = movementLine.getDD_OrderLine_Alternative();
		if (ddOrderLineAlt != null && ddOrderLineAlt.getDD_OrderLine_Alternative_ID() > 0)
		{
			return InterfaceWrapperHelper.create(ddOrderLineAlt, I_DD_OrderLine_Or_Alternative.class);
		}

		final I_DD_OrderLine ddOrderLine = movementLine.getDD_OrderLine();
		if (ddOrderLine != null && ddOrderLine.getDD_OrderLine_ID() > 0)
		{
			return InterfaceWrapperHelper.create(ddOrderLine, I_DD_OrderLine_Or_Alternative.class);
		}

		return null;
	}
}
