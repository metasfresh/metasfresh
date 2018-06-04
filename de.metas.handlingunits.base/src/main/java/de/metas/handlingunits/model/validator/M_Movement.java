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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Locator;
import org.compiere.model.ModelValidator;

import de.metas.event.IEventBusFactory;
import de.metas.handlingunits.HUContextDateTrxProvider.ITemporaryDateTrx;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.interfaces.I_M_Movement;
import de.metas.movement.event.MovementUserNotificationsProducer;

@Validator(I_M_Movement.class)
public class M_Movement
{
	public static final transient M_Movement instance = new M_Movement();

	@Init
	public void onInit()
	{
		// Setup event bus topics on which swing client notification listener shall subscribe
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(MovementUserNotificationsProducer.USER_NOTIFICATIONS_TOPIC);
	}

	private M_Movement()
	{
		super();
	}

	/**
	 * Iterate all movement lines. In case a movement line is generated from a receipt, retrieve the HUs from that receipt and assign them to movement line.
	 *
	 * @param movement
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_PREPARE)
	public void assignHUsFromReceipt(final I_M_Movement movement)
	{
		final IMovementBL movementBL = Services.get(IMovementBL.class);
		final IMovementDAO movementDAO = Services.get(IMovementDAO.class);

		final boolean reversal = movementBL.isReversal(movement); // true if this is an actual reversal

		//
		// Copy M_InOut_ID from original movement, if this is a reversal
		if (reversal)
		{
			final I_M_Movement movementOriginal = InterfaceWrapperHelper.create(movement.getReversal(), I_M_Movement.class);
			movement.setM_InOut_ID(movementOriginal.getM_InOut_ID());
		}

		//
		// Assign HUs from receipt lines, only if this is not a reversal
		if (!reversal)
		{
			for (final I_M_MovementLine movementLine : movementDAO.retrieveLines(movement, I_M_MovementLine.class))
			{
				assignHUsFromReceiptLine(movementLine);
			}
		}
	}

	/**
	 * Iterate each movement line, collect the packing materials and generate movement lines which also move those packing materials.
	 *
	 * @param movement
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_PREPARE)
	public void createPackingMaterialMovementLines(final I_M_Movement movement)
	{
		final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
		huMovementBL.createPackingMaterialMovementLines(movement);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void deletePackingMaterialMovementLines(final I_M_Movement movement)
	{
		final IMovementDAO movementDAO = Services.get(IMovementDAO.class);

		for (final I_M_MovementLine movementLine : movementDAO.retrieveLines(movement, I_M_MovementLine.class))
		{
			if (!movementLine.isPackagingMaterial())
			{
				continue;
			}

			InterfaceWrapperHelper.delete(movementLine);
		}
	}

	/**
	 * Fetch all HUs which are assigned to linked Receipt Line and assign them to given movement line.
	 *
	 * @param movementLine
	 * @param packingMaterialsCollector
	 * @return list of HUs which are now currently assigned given <code>movementLine</code>
	 */
	private void assignHUsFromReceiptLine(final I_M_MovementLine movementLine)
	{
		//
		// Particular case: movement generated from Receipt Line (to move Qty to destination warehouse)
		final I_M_InOutLine receiptLine = InterfaceWrapperHelper.create(movementLine.getM_InOutLine(), I_M_InOutLine.class);
		if (receiptLine == null || receiptLine.getM_InOutLine_ID() <= 0)
		{
			return;
		}

		// Don't move HUs for InDispute receipt lines
		if (receiptLine.isInDispute())
		{
			return;
		}

		//
		// Fetch HUs which are currently assigned to Receipt Line
		final List<I_M_HU> hus = Services.get(IHUAssignmentDAO.class).retrieveTopLevelHUsForModel(receiptLine);
		if (hus.isEmpty())
		{
			return;
		}

		//
		// Assign them to movement line
		final String trxName = InterfaceWrapperHelper.getTrxName(movementLine);
		Services.get(IHUAssignmentBL.class).setAssignedHandlingUnits(movementLine, hus, trxName);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void moveHandlingUnits(final I_M_Movement movement)
	{
		final boolean doReversal = false;
		moveHandlingUnits(movement, doReversal);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REVERSECORRECT, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL, ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void unmoveHandlingUnits(final I_M_Movement movement)
	{
		final boolean doReversal = true;
		moveHandlingUnits(movement, doReversal);
	}

	private void moveHandlingUnits(final I_M_Movement movement, final boolean doReversal)
	{
		final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
		final Date movementDate = movement.getMovementDate();

		try (ITemporaryDateTrx dateTrx = IHUContext.DateTrxProvider.temporarySet(movementDate))
		{
			for (final I_M_MovementLine movementLine : movementDAO.retrieveLines(movement, I_M_MovementLine.class))
			{
				moveHandlingUnits(movementLine, doReversal);
			}
		}
	}

	private void moveHandlingUnits(final I_M_MovementLine movementLine, final boolean doReversal)
	{
		final I_M_Locator locatorFrom;
		final I_M_Locator locatorTo;
		if (!doReversal)
		{
			locatorFrom = movementLine.getM_Locator();
			locatorTo = movementLine.getM_LocatorTo();
		}
		else
		{
			locatorFrom = movementLine.getM_LocatorTo();
			locatorTo = movementLine.getM_Locator();
		}

		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final List<I_M_HU> hus = huAssignmentDAO.retrieveTopLevelHUsForModel(movementLine);
		for (final I_M_HU hu : hus)
		{
			moveHandlingUnit(hu, locatorFrom, locatorTo, doReversal);
		}
	}

	private void moveHandlingUnit(
			final I_M_HU hu,
			final I_M_Locator locatorFrom, final I_M_Locator locatorTo,
			final boolean doReversal)
	{
		//
		// Make sure hu's current locator is the locator from which we need to move
		final int huLocatorIdOld = hu.getM_Locator_ID();
		final int locatorFromId = locatorFrom.getM_Locator_ID();
		final int locatorToId = locatorTo.getM_Locator_ID();
		Check.assume(huLocatorIdOld > 0
				&& (huLocatorIdOld == locatorFromId || huLocatorIdOld == locatorToId),
				"HU Locator was supposed to be {} or {}, but was {}", locatorFrom, locatorTo, hu.getM_Locator());

		// If already moved, then do nothing.
		if (huLocatorIdOld == locatorToId)
		{
			return;
		}

		//
		// Activate HU (not needed, but we want to be sure)
		// (even if we do reversals)

		// NOTE: as far as we know, HUContext won't be used by setHUStatus, because the status active doesn't
		// trigger a movement to/from gebindelager. In this case a movement is already created from a lager to another.
		// So no HU leftovers.
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();
		Services.get(IHandlingUnitsBL.class).setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);

		//
		// Update HU's Locator
		// FIXME: refactor this and have a common way of setting HU's locator
		hu.setM_Locator_ID(locatorToId);

		// Save changed HU
		Services.get(IHandlingUnitsDAO.class).saveHU(hu);
	}

}
