package de.metas.distribution.mobileui.job.service;

import de.metas.distribution.mobileui.config.DistributionJobSorting;
import de.metas.distribution.mobileui.launchers.facets.DistributionFacetIdsCollection;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
public class DDOrderReferenceQuery
{
	@NonNull UserId responsibleId;
	@NonNull @Default QueryLimit suggestedLimit = QueryLimit.NO_LIMIT;
	@NonNull @Default DistributionFacetIdsCollection activeFacetIds = DistributionFacetIdsCollection.EMPTY;
	boolean excludeAlreadyStarted;
	@NonNull @Default DistributionJobSorting sorting = DistributionJobSorting.DEFAULT;
	@Nullable WarehouseId warehouseToId;
	@Nullable LocatorId locatorToId;
}
