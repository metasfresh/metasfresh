package de.metas.distribution.workflows_api;

import de.metas.distribution.workflows_api.facets.DistributionFacetIdsCollection;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;

@Value
@Builder
public class DDOrderReferenceQuery
{
	@NonNull UserId responsibleId;
	@NonNull @Default QueryLimit suggestedLimit = QueryLimit.NO_LIMIT;
	@NonNull @Default DistributionFacetIdsCollection activeFacetIds = DistributionFacetIdsCollection.EMPTY;
}
