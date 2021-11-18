package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.function.UnaryOperator;

@Value
@Builder
public class ManufacturingJob
{
	@NonNull PPOrderId ppOrderId;
	@NonNull String documentNo;
	@Nullable BPartnerId customerId;
	@NonNull ZonedDateTime datePromised;
	@Nullable UserId responsibleId;

	@With
	@NonNull ImmutableList<ManufacturingJobActivity> activities;

	public ManufacturingJobActivity getActivityById(@NonNull ManufacturingJobActivityId id)
	{
		return activities.stream()
				.filter(activity -> ManufacturingJobActivityId.equals(activity.getId(), id))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No activity found for " + id));
	}

	public ManufacturingJob withChangedRawMaterialsIssueStep(
			@NonNull final PPOrderIssueScheduleId issueScheduleId,
			@NonNull UnaryOperator<RawMaterialsIssueStep> mapper)
	{
		final ImmutableList<ManufacturingJobActivity> activitiesNew = CollectionUtils.map(activities, activity -> activity.withChangedRawMaterialsIssueStep(issueScheduleId, mapper));
		return withActivities(activitiesNew);
	}
}
