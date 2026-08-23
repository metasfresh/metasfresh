package de.metas.handlingunits.picking.job.model.facets;

import com.google.common.collect.ImmutableList;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CollectingParameters
{
	@NonNull RenderedAddressProvider addressProvider;
	/** Resolves ExternalSystem.Name for the external-system facet captions; fully cached, so this is a map lookup. */
	@NonNull ExternalSystemRepository externalSystemRepository;
	@NonNull ImmutableList<PickingJobFacetGroup> groupsInOrder;
	@NonNull PickingJobQuery.Facets activeFacets;
}
