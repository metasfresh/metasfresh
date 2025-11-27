package de.metas.handlingunits.picking.job.model.facets;

import com.google.common.collect.ImmutableList;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CollectingParameters
{
	@NonNull RenderedAddressProvider addressProvider;
	@NonNull ImmutableList<PickingJobFacetGroup> groupsInOrder;
	@NonNull PickingJobQuery.Facets activeFacets;
}
