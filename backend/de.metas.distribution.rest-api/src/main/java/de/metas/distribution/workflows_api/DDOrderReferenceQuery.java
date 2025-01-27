package de.metas.distribution.workflows_api;

import de.metas.distribution.workflows_api.facets.DistributionFacetIdsCollection;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.ad.dao.QueryLimit;

@Value
@Builder
public class DDOrderReferenceQuery
{
	@NonNull UserId responsibleId;
	@NonNull @With @Builder.Default QueryLimit suggestedLimit = QueryLimit.NO_LIMIT;
	@NonNull @Builder.Default DistributionFacetIdsCollection activeFacetIds = DistributionFacetIdsCollection.EMPTY;
}
