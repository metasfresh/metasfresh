package de.metas.distribution.workflows_api;

import de.metas.dao.ValueRestriction;
import de.metas.distribution.config.DistributionJobSorting;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.document.engine.DocStatus;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
class DistributionJobQueries
{
	public static DDOrderQuery ddOrdersAssignedToUser(@NonNull final DDOrderReferenceQuery query)
	{
		return ddOrdersAssignedToUser(query.getResponsibleId(), query.getSorting());
	}

	public static DDOrderQuery ddOrdersAssignedToUser(@NonNull final UserId responsibleId, @NonNull DistributionJobSorting sorting)
	{
		return DDOrderQuery.builder()
				.docStatus(DocStatus.Completed)
				.responsibleId(ValueRestriction.equalsTo(responsibleId))
				.orderBys(sorting.toDDOrderQueryOrderBys())
				.build();
	}

}
