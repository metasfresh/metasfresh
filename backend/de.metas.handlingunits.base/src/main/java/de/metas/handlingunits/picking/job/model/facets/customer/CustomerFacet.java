package de.metas.handlingunits.picking.job.model.facets.customer;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacet;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value(staticConstructor = "of")
public class CustomerFacet implements PickingJobFacet
{
	@NonNull PickingJobFacetGroup group = PickingJobFacetGroup.CUSTOMER;
	@With boolean isActive;
	@NonNull BPartnerId bpartnerId;
	@NonNull String customerBPValue;
	@NonNull String customerName;
}
