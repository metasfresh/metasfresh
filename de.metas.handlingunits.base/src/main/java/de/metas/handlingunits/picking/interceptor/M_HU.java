package de.metas.handlingunits.picking.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import lombok.NonNull;

@Interceptor(I_M_HU.class)
public class M_HU
{
	public static final M_HU INSTANCE = new M_HU();

	private M_HU()
	{
	}

	/**
	 * When a picked HU is destroyed, it has to be removed from the picking slot.
	 * <p>
	 * NOTE: We don't know if the old status of the HU is picked, but {@link IHUPickingSlotBL#removeFromPickingSlotQueue(I_M_HU)} does.
	 *
	 * @param hu
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {
			I_M_HU.COLUMNNAME_HUStatus
	})
	public void removeDestroyedTopLevelHuFromPickingSlotQueue(@NonNull final I_M_HU hu)
	{
		if (!X_M_HU.HUSTATUS_Destroyed.equals(hu.getHUStatus()))
		{
			return; // Do nothing in case the HU was not destroyed
		}

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		if (!handlingUnitsBL.isTopLevel(hu))
		{
			return;	// Only do this for the top level HUs
		}

		// At this point, it means we have a top-level, destroyed HU. We need to take it out from the picking slots
		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
		huPickingSlotBL.removeFromPickingSlotQueue(hu);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_M_HU.COLUMNNAME_M_Locator_ID)
	public void preventMovingSourceHu(@NonNull final I_M_HU hu)
	{
		final IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);
		final boolean sourceHU = huPickingSlotDAO.isSourceHU(hu.getM_HU_ID());
		if (sourceHU)
		{
			throw new SourceHuMayNotBeRemovedException(hu);
		}
	}

	public static final class SourceHuMayNotBeRemovedException extends AdempiereException
	{
		private static final long serialVersionUID = -7002396154928421269L;
		private static final String MSG_CANNOT_MOVE_SOURCE_HU_1P = "CANNOT_MOVE_SOURCE_HU";

		private SourceHuMayNotBeRemovedException(final I_M_HU hu)
		{
			super(MSG_CANNOT_MOVE_SOURCE_HU_1P, new Object[] { hu.getValue() });
		}
	}

}
