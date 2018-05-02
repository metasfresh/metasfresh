package de.metas.handlingunits.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.save;

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

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inout.IInOutBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import lombok.NonNull;

@Validator(I_M_InOutLine.class)
public class M_InOutLine
{
	// TODO: delete AD_Message:
	// private static final String MSG_CHANGE_MOVEMENT_QTY_NOT_SUPPORTED = "de.metas.inoutcandidate.modelvalidator.M_InOutLine_Shipment_ChangeMovementQtyNotSupported";

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	})
	public void updateEffectiveValues(final I_M_InOutLine inoutLine)
	{

		final I_M_InOut inOut = inoutLine.getM_InOut();

		final boolean isReturnType = Services.get(IInOutBL.class).isReturnMovementType(inOut.getMovementType());

		if (isReturnType)
		{
			// no nothing in case of returns
			return;
		}

		//
		// Shipment
		if (inoutLine.getM_InOut().isSOTrx())
		{
			final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
			huInOutBL.updateEffectiveValues(inoutLine);
		}
		//
		// Receipt
		else
		{
			// nothing
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void removeHUAssignments(final I_M_InOutLine inoutLine)
	{
		final I_M_InOut inout = inoutLine.getM_InOut();

		//
		// Shipment
		if (inout.isSOTrx())
		{
			// NOTE: instead of deleting the assignments, it's better to fail
			// because if we delete the assignment which is for an HU which is also assigned on other shipment,
			// that patial HU we will not be able to see it again (as a user).
			if (Services.get(IHUAssignmentDAO.class).hasHUAssignmentsForModel(inoutLine))
			{
				throw new HUException("Cannot delete an shipment line which have HUs assigned to it");
			}
			// Services.get(IHUShipmentAssignmentBL.class).removeHUAssignments(inoutLine);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_M_InOutLine.COLUMNNAME_MovementQty)
	public void onMovementQtyChange(final I_M_InOutLine inOutLine)
	{
		// make sure we are dealing with shipments
		if (!inOutLine.getM_InOut().isSOTrx())
		{
			return;
		}

		final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
		final List<I_M_ShipmentSchedule_QtyPicked> allocs = shipmentScheduleAllocDAO.retrieveAllForInOutLine(
				inOutLine,
				I_M_ShipmentSchedule_QtyPicked.class);
		if (!allocs.isEmpty())
		{
			createOrUpdateShipmentScheduleQtyPicked(inOutLine, allocs);
		}

		final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL = Services.get(IShipmentScheduleInvalidateBL.class);
		shipmentScheduleInvalidateBL.invalidateJustForLine(inOutLine); // task 08749: the segment invalidation might not invalidate the sched(s) for this line
		shipmentScheduleInvalidateBL.invalidateSegmentForLine(inOutLine);
	}

	private void createOrUpdateShipmentScheduleQtyPicked(
			@NonNull final I_M_InOutLine inOutLine,
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> allocs)
	{
		final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

		final BigDecimal qtyPickedSum = computeQtyPickedSum(allocs);

		// Adjust "Shipment schedules" to "Shipment Line" allocations
		final BigDecimal shipmentLine_movementQty = inOutLine.getMovementQty();
		final BigDecimal qtyPickedToAdd = shipmentLine_movementQty.subtract(qtyPickedSum);
		if (qtyPickedToAdd.signum() == 0)
		{
			return;
		}

		// * find out an "alloc" where we can add our difference
		I_M_ShipmentSchedule adjustments_shipmentSchedule = null;
		I_M_ShipmentSchedule_QtyPicked adjustments_alloc = null;
		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocs)
		{
			// Select the allocation line on which we can do the adjustments
			adjustments_shipmentSchedule = alloc.getM_ShipmentSchedule();
			if (!huShipmentScheduleBL.isHUAllocation(alloc))
			{
				adjustments_alloc = alloc;
				break;
			}
		}

		if (adjustments_alloc != null)
		{
			// Case: we found a line where to add the difference
			adjustments_alloc.setQtyPicked(adjustments_alloc.getQtyPicked().add(qtyPickedToAdd));
			save(adjustments_alloc);
		}
		else
		{
			// Case: there is no line were we can add the difference, so we are creating one now
			final I_C_UOM uom = Services.get(IProductBL.class).getStockingUOM(inOutLine.getM_Product()); // we assume MovementQty is in product's stocking UOM

			final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked adjustments_allocNew = //
					shipmentScheduleAllocBL.addQtyPicked(adjustments_shipmentSchedule, Quantity.of(qtyPickedToAdd, uom));

			adjustments_allocNew.setM_InOutLine(inOutLine);
			adjustments_allocNew.setProcessed(inOutLine.getM_InOut().isProcessed());
			save(adjustments_allocNew);
		}
	}

	private BigDecimal computeQtyPickedSum(final List<I_M_ShipmentSchedule_QtyPicked> allocs)
	{
		// * calculate how much qty was shipped for all shipment schedules (in total)
		BigDecimal qtyPickedTotal = BigDecimal.ZERO;
		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocs)
		{
			//
			// Calculate how much was allocated in total
			final BigDecimal alloc_qtyPicked = alloc.getQtyPicked();
			qtyPickedTotal = qtyPickedTotal.add(alloc_qtyPicked);
		}
		return qtyPickedTotal;
	}

}
