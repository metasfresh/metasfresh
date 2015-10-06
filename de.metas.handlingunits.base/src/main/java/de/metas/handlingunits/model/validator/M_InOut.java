package de.metas.handlingunits.model.validator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.IHUPickingSlotBL;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.inout.IHUShipmentAssignmentBL;
import de.metas.handlingunits.inout.impl.MInOutHUDocumentFactory;
import de.metas.handlingunits.inout.impl.ReceiptInOutLineHUAssignmentListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.util.HUByIdComparator;

@Validator(I_M_InOut.class)
public class M_InOut
{
	@Init
	public void init()
	{
		Services.get(IHUDocumentFactoryService.class)
				.registerHUDocumentFactory(I_M_InOut.Table_Name, new MInOutHUDocumentFactory());

		Services.get(IHUAssignmentBL.class)
				.registerHUAssignmentListener(ReceiptInOutLineHUAssignmentListener.instance);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL })
	public void destroyHandlingUnitsForReceipt(final I_M_InOut inout)
	{
		if (inout.isSOTrx())
		{
			// TODO: change HUStatus from Shipped back to Picked
		}
		else
		{
			final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
			huInOutBL.destroyHUs(inout);
		}
	}

	/**
	 * Generates additional shipment lines for the packaging materials of currently assigned HUs.
	 *
	 * @param shipment
	 * @see IHUInOutBL#createPackingMaterialLines(I_M_InOut)
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE })
	public void addPackingMaterialLinesForShipment(final I_M_InOut shipment)
	{
		if (shipment.isSOTrx())
		{
			final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
			huInOutBL.createPackingMaterialLines(shipment);
		}
		else
		{
			// nothing to do in case of receipts because packing materials lines are already created
			// see de.metas.inoutcandidate.spi.impl.InOutProducerFromReceiptScheduleHU.createPackingMaterialReceiptLine(HUPackingMaterialDocumentLineCandidate)
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void setHUStatusShippedForShipment(final I_M_InOut shipment)
	{
		// Make sure we deal with a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}

		Services.get(IHUShipmentAssignmentBL.class).updateHUsOnShipmentComplete(shipment);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void emptyPickingSlots(final I_M_InOut shipment)
	{
		// Make sure we deal with a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}

		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

		//
		// Retrieve all HUs which we need to remove from their picking slots
		final Set<I_M_HU> husToRemove = new TreeSet<>(HUByIdComparator.instance);

		final List<I_M_HU> handlingUnits = Services.get(IHUInOutDAO.class).retrieveHandlingUnits(shipment);

		husToRemove.addAll(handlingUnits);

		//
		// Remove collected HUs from their picking slots
		for (final I_M_HU hu : husToRemove)
		{
			huPickingSlotBL.removeFromPickingSlotQueueRecursivelly(hu);
		}

	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_VOID, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void removeHUAssignmentsForShipment(final I_M_InOut shipment)
	{
		// Make sure we deal with a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}

		//
		// Remove all HU Assignments
		Services.get(IHUShipmentAssignmentBL.class).removeHUAssignments(shipment);

		//
		// Unassign shipment from M_Packages (if any)
		Services.get(IHUPackageBL.class).unassignShipmentFromPackages(shipment);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void assertReActivationAllowed(final I_M_InOut inout)
	{
		//
		// Services
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		final boolean hasHUAssignments = huAssignmentDAO.hasHUAssignmentsForModel(inout);
		if (!hasHUAssignments) // reactivation is allowed if there are no HU assignments
		{
			return;
		}

		//
		// Shipment
		if (inout.isSOTrx())
		{
			// TODO: change HUStatus from Shipped back to Picked
			// check the calls of de.metas.handlingunits.inout.impl.HUShipmentAssignmentBL.setHUStatus(IHUContext, I_M_HU, boolean)
		}
		//
		// Receipt
		else
		{
			// TODO: destroy the HUs
			throw new UnsupportedOperationException();
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void unassignShipmentFromMPackages(final I_M_InOut shipment)
	{
		// Make sure we are dealing with a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}

		Services.get(IHUPackageBL.class).unassignShipmentFromPackages(shipment);
	}

}
