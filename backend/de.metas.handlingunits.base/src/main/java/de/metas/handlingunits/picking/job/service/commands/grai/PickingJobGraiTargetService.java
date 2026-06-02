package de.metas.handlingunits.picking.job.service.commands.grai;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

/**
 * Service for GRAI picking target checks — specifically, verifying that a resolved TU packing-instruction
 * is includable in the effective picking-target LU's packing instruction.
 */
@Service
public class PickingJobGraiTargetService
{
	private static final AdMessageKey MSG_TU_NOT_ALLOWED_ON_LU = AdMessageKey.of("de.metas.handlingunits.picking.GRAITUNotAllowedOnLU");

	/**
	 * Asserts that the given TU packing-instruction ({@code tuPIId}) is allowed on the given LU picking target.
	 *
	 * <p>"Allowed" means: an {@code M_HU_PI_Item} with {@code ItemType='HU'} and
	 * {@code Included_HU_PI_ID = tuPIId} exists on the LU PI's current version.
	 *
	 * @throws AdempiereException keyed on {@code de.metas.handlingunits.picking.GRAITUNotAllowedOnLU}
	 *                            when no such item is found.
	 */
	public void assertTuAllowedOnLu(
			@NonNull final HuPackingInstructionsId tuPIId,
			@NonNull final LUPickingTarget luTarget)
	{
		final HuPackingInstructionsId luPIId = resolveLuPackingInstructionsId(luTarget);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final boolean allowed = handlingUnitsDAO.retrieveFirstPIItem(luPIId, tuPIId, /* bpartnerId= */ null).isPresent();

		if (!allowed)
		{
			throw new AdempiereException(MSG_TU_NOT_ALLOWED_ON_LU);
		}
	}

	/**
	 * Resolves the LU's M_HU_PI_ID from the given {@link LUPickingTarget}.
	 * <ul>
	 *   <li>New LU: the PI id is carried directly on the target.</li>
	 *   <li>Existing LU: derive the PI from the existing HU record via {@link IHandlingUnitsBL}.</li>
	 * </ul>
	 */
	@NonNull
	private static HuPackingInstructionsId resolveLuPackingInstructionsId(@NonNull final LUPickingTarget luTarget)
	{
		if (luTarget.isNewLU())
		{
			return luTarget.getLuPIIdNotNull();
		}
		else
		{
			// Existing LU: read M_HU_PI_ID from the HU record
			final HuId luId = luTarget.getLuIdNotNull();
			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			final I_M_HU lu = handlingUnitsDAO.getById(luId);
			return handlingUnitsBL.getPackingInstructionsId(lu);
		}
	}
}
