package de.metas.hu_consolidation.mobile.job.commands.consolidate;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

/**
 * Fails with an operator message naming the pallet type and the crate type if a pallet (LU) type does not take a crate (TU) type.
 */
@RequiredArgsConstructor
class NewLUTakesTUsAssertion
{
	private static final AdMessageKey MSG_PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE = AdMessageKey.of("de.metas.hu_consolidation.mobile.job.PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE");

	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final HUTransformService huTransformService;

	/**
	 * Checks a crate for a new pallet of the given packing instructions, judged by {@link HUTransformService#isTUAttachableToNewLU}, i.e. for the partner the move resolves (the crate's own, else the one of the pallet it sits on).
	 * A crate without real packing instructions is not checked.
	 *
	 * @throws AdempiereException with the operator message if the pallet type does not take the crate type
	 */
	public void assertNewLUTakesTU(@NonNull final HuPackingInstructionsId luPIId, @NonNull final I_M_HU tu)
	{
		final HuPackingInstructionsId tuPIId = handlingUnitsBL.getEffectivePackingInstructionsId(tu);
		if (!tuPIId.isRealPackingInstructions())
		{
			return;
		}

		if (!huTransformService.isTUAttachableToNewLU(luPIId, tu))
		{
			throw palletTypeDoesNotTakeCrateType(luPIId, tuPIId);
		}
	}

	/**
	 * Checks crates for the given existing pallet, judged by {@link HUTransformService#isTUAttachableToExistingLU}; one check per crate, because the answer
	 * depends on the partner of the pallet each crate sits on, not only on its type.
	 *
	 * @throws AdempiereException with the operator message if the pallet does not take one of the crates
	 */
	public void assertLUTakesTUs(@NonNull final I_M_HU lu, @NonNull final List<I_M_HU> tus)
	{
		for (final I_M_HU tu : tus)
		{
			if (!huTransformService.isTUAttachableToExistingLU(tu, lu))
			{
				throw palletTypeDoesNotTakeCrateType(handlingUnitsBL.getPIId(lu), handlingUnitsBL.getEffectivePackingInstructionsId(tu));
			}
		}
	}

	private AdempiereException palletTypeDoesNotTakeCrateType(@NonNull final HuPackingInstructionsId luPIId, @NonNull final HuPackingInstructionsId tuPIId)
	{
		return new AdempiereException(MSG_PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE, handlingUnitsBL.getPIName(luPIId), handlingUnitsBL.getPIName(tuPIId));
	}
}
