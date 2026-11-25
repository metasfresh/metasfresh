package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.HuId;
import de.metas.picking.api.PickingSlotId;
import lombok.NonNull;
import lombok.Value;

/**
 * The crate a scanned code was resolved to, together with the picking slot of this job it currently sits in.
 */
@Value(staticConstructor = "of")
public class HUConsolidationScannedCrate
{
	@NonNull HuId huId;
	@NonNull PickingSlotId pickingSlotId;
}
