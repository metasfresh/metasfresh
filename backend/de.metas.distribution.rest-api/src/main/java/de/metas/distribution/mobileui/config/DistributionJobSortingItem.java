package de.metas.distribution.mobileui.config;

import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;

@Value(staticConstructor = "of")
public class DistributionJobSortingItem
{
	@NonNull DistributionJobSortingField field;
	@NonNull Direction direction;

	DDOrderQuery.OrderBy toDDOrderQueryOrderBy()
	{
		return DDOrderQuery.OrderBy.of(field.getDdOrderQueryOrderByField(), direction);
	}

	WorkflowLauncherCaption.OrderBy toWorkflowLauncherCaptionOrderBy()
	{
		return WorkflowLauncherCaption.OrderBy.builder()
				.field(field.getCode())
				.ascending(direction.isAscending())
				.build();
	}

}
