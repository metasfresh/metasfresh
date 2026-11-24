package de.metas.hu_consolidation.mobile.job.commands.consolidate;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Checks, before crates (TUs) are put on a new pallet (LU), that the pallet's packing instructions have an item for each crate's packing instructions.
 * Same lookup as {@link HUTransformService#tuToNewLU} ({@link IHandlingUnitsBL#isTUTypeTakenByLUType}); one lookup per crate type and partner, not per crate.
 */
@RequiredArgsConstructor
class NewLUTakesTUsAssertion
{
	private static final AdMessageKey MSG_PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE = AdMessageKey.of("de.metas.hu_consolidation.mobile.job.PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE");

	@NonNull private final IHandlingUnitsBL handlingUnitsBL;

	/**
	 * @throws AdempiereException with an operator message naming both types, if the pallet type does not take one of the crate types
	 */
	public void assertNewLUTakesTUs(@NonNull final HuPackingInstructionsId luPIId, @NonNull final List<I_M_HU> tus)
	{
		tus.stream()
				.map(tu -> new CrateType(handlingUnitsBL.getEffectivePackingInstructionsId(tu), IHandlingUnitsBL.extractBPartnerIdOrNull(tu)))
				.filter(crateType -> crateType.getTuPIId().isRealPackingInstructions())
				.distinct()
				.forEach(crateType -> assertNewLUTakesTUType(luPIId, crateType));
	}

	private void assertNewLUTakesTUType(@NonNull final HuPackingInstructionsId luPIId, @NonNull final CrateType crateType)
	{
		if (!handlingUnitsBL.isTUTypeTakenByLUType(luPIId, crateType.getTuPIId(), crateType.getBpartnerId()))
		{
			throw new AdempiereException(MSG_PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE, handlingUnitsBL.getPIName(luPIId), handlingUnitsBL.getPIName(crateType.getTuPIId()));
		}
	}

	@Value
	private static class CrateType
	{
		@NonNull HuPackingInstructionsId tuPIId;
		@Nullable BPartnerId bpartnerId;
	}
}
