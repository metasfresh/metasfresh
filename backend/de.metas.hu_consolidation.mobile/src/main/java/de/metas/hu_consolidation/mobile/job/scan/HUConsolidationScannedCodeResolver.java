package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.picking.slot.PickingSlotQueues;
import de.metas.scannable_code.ScannedCode;
import lombok.NonNull;

import java.util.Optional;

/**
 * Resolves a scanned code of one code type to a crate among the crates of the job.
 * An implementation answers only for its own code type and returns empty for any other code.
 */
public interface HUConsolidationScannedCodeResolver
{
	/**
	 * @return empty if the code is not of this resolver's type ("not my code type"); else the crate.
	 * @throws org.adempiere.exceptions.AdempiereException {@code HU_NOT_FOUND} if no crate of the job matches (also when {@code queues} is empty, without any query),
	 *                                                     {@code HU_AMBIGUOUS} if more than one matches.
	 */
	Optional<HUConsolidationScannedCrate> resolve(@NonNull ScannedCode scannedCode, @NonNull PickingSlotQueues queues);
}
