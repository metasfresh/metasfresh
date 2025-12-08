package de.metas.manufacturing.job.service;

import de.metas.manufacturing.job.model.ManufacturingJobFacets;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class ManufacturingJobReferenceQuery
{
	@NonNull UserId responsibleId;
	@Nullable ResourceId plantOrWorkstationId;
	@Nullable ResourceId workstationId;
	@NonNull Instant now;
	@NonNull @With @Builder.Default QueryLimit suggestedLimit = QueryLimit.NO_LIMIT;
	@NonNull @Builder.Default ManufacturingJobFacets.FacetIdsCollection activeFacetIds = ManufacturingJobFacets.FacetIdsCollection.EMPTY;

	public ManufacturingJobReferenceQuery withNoLimit()
	{
		return withSuggestedLimit(QueryLimit.NO_LIMIT);
	}
}
