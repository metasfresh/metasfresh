package de.metas.handlingunits.picking.interceptor;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.slot.IHUPickingSlotBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

@Interceptor(I_M_HU.class)
public class M_HU
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

	/**
	 * When a picked HU is destroyed, it has to be removed from the picking slot.
	 * <p>
	 * NOTE: We don't know if the old status of the HU is picked, but {@link IHUPickingSlotBL#removeFromPickingSlotQueue(HuId)} does.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = { I_M_HU.COLUMNNAME_HUStatus })
	public void removeDestroyedTopLevelHuFromPickingSlotQueue(@NonNull final I_M_HU hu)
	{
		if (!X_M_HU.HUSTATUS_Destroyed.equals(hu.getHUStatus()))
		{
			return; // Do nothing in case the HU was not destroyed
		}

		if (!handlingUnitsBL.isTopLevel(hu))
		{
			return;    // Only do this for the top level HUs
		}

		// At this point, it means we have a top-level, destroyed HU. We need to take it out from the picking slots
		huPickingSlotBL.removeFromPickingSlotQueue(HuId.ofRepoId(hu.getM_HU_ID()));
	}
}
