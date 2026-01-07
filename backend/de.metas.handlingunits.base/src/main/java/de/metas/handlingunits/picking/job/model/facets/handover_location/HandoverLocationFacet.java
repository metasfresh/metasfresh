package de.metas.handlingunits.picking.job.model.facets.handover_location;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacet;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value(staticConstructor = "of")
public class HandoverLocationFacet implements PickingJobFacet
{
	@NonNull PickingJobFacetGroup group = PickingJobFacetGroup.HANDOVER_LOCATION;
	@With boolean isActive;
	@NonNull BPartnerLocationId bPartnerLocationId;
	@NonNull String renderedAddress;
}
