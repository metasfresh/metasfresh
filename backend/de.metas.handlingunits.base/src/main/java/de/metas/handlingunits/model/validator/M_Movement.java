package de.metas.handlingunits.model.validator;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.event.IEventBusFactory;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.interfaces.I_M_Movement;
import de.metas.movement.event.MovementUserNotificationsProducer;
import de.metas.util.Services;

@Interceptor(I_M_Movement.class)
public class M_Movement
{
	public static final transient M_Movement instance = new M_Movement();

	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	@Init
	public void onInit()
	{
		// Setup event bus topics on which swing client notification listener shall subscribe
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(MovementUserNotificationsProducer.USER_NOTIFICATIONS_TOPIC);
	}

	private M_Movement()
	{
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
		Services.get(IHUAssignmentBL.class).setAssignedHandlingUnits(movementLine, hus);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void moveHandlingUnits(final I_M_Movement movement)
	{
		final boolean doReversal = false;
		huMovementBL.moveHandlingUnits(movement, doReversal);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REVERSECORRECT, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL, ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void unmoveHandlingUnits(final I_M_Movement movement)
	{
		final boolean doReversal = true;
		huMovementBL.moveHandlingUnits(movement, doReversal);
	}

}
