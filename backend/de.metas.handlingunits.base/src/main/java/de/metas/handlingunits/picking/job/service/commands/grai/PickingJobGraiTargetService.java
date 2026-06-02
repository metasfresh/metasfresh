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
	 * Verifies the TU PI is permitted on the LU picking target; throws keyed {@code GRAITUNotAllowedOnLU} otherwise.
	 *
	 * @throws AdempiereException keyed on {@code de.metas.handlingunits.picking.GRAITUNotAllowedOnLU}
	 *                            when no matching M_HU_PI_Item is found.
	 */
	public void assertTuAllowedOnLu(
			@NonNull final HuPackingInstructionsId tuPIId,
			@NonNull final LUPickingTarget luTarget)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final HuPackingInstructionsId luPIId = resolveLuPackingInstructionsId(luTarget, handlingUnitsDAO);

		// NOTE: bpartnerId=null intentionally — only generic (no-partner) M_HU_PI_Items are matched.
		// Partner-specific inclusions (C_BPartner_ID != null) are not considered here because
		// GRAI mapping is configured at the general M_HU_PI level, and generic inclusions cover the expected case.
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
	private static HuPackingInstructionsId resolveLuPackingInstructionsId(
			@NonNull final LUPickingTarget luTarget,
			@NonNull final IHandlingUnitsDAO handlingUnitsDAO)
	{
		if (luTarget.isNewLU())
		{
			return luTarget.getLuPIIdNotNull();
		}
		else
		{
			final HuId luId = luTarget.getLuIdNotNull();
			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			final I_M_HU lu = handlingUnitsDAO.getById(luId);
			return handlingUnitsBL.getPackingInstructionsId(lu);
		}
	}
}
