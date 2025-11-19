package de.metas.distribution.config;

import de.metas.distribution.ddorder.DDOrderQuery;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;

@Value(staticConstructor = "of")
public class DistributionJobSortingItem
{
	@NonNull DistributionJobSortingField field;
	@NonNull Direction direction;

	public DDOrderQuery.OrderBy toDDOrderQueryOrderBy()
	{
		return DDOrderQuery.OrderBy.of(field.getDdOrderQueryOrderByField(), direction);
	}
}
