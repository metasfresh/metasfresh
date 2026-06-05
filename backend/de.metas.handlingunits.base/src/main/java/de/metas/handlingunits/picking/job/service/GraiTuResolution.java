package de.metas.handlingunits.picking.job.service;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.grai.GRAI;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The parsed GRAI together with its resolved TU packing-instruction and capacity.
 */
@Value
@Builder
public class GraiTuResolution
{
	@NonNull GRAI grai;
	@NonNull HuPackingInstructionsId tuPIId;
	@NonNull HUPIItemProductId huPIItemProductId;
}
