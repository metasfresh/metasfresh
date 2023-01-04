package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.device.accessor.DeviceId;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WFActivityId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivityId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Value
public class ManufacturingJob
{
	@NonNull PPOrderId ppOrderId;
	@NonNull String documentNo;
	@Nullable BPartnerId customerId;
	@NonNull ZonedDateTime datePromised;
	@Nullable UserId responsibleId;
	boolean allowUserReporting;

	@NonNull WarehouseId warehouseId;
	@Nullable DeviceId currentScaleDeviceId;

	@NonNull ImmutableList<ManufacturingJobActivity> activities;

	@Builder(toBuilder = true)
	private ManufacturingJob(
			@NonNull final PPOrderId ppOrderId,
			@NonNull final String documentNo,
			@Nullable final BPartnerId customerId,
			@NonNull final ZonedDateTime datePromised,
			@Nullable final UserId responsibleId,
			final boolean allowUserReporting,
			//
			final @NonNull WarehouseId warehouseId,
			@Nullable final DeviceId currentScaleDeviceId,
			//
			@NonNull final ImmutableList<ManufacturingJobActivity> activities)
	{
		this.warehouseId = warehouseId;
		this.currentScaleDeviceId = currentScaleDeviceId;
		Check.assumeNotEmpty(activities, "activities is not empty");

		this.ppOrderId = ppOrderId;
		this.documentNo = documentNo;
		this.customerId = customerId;
		this.datePromised = datePromised;
		this.responsibleId = responsibleId;
		this.allowUserReporting = allowUserReporting;
		this.activities = activities;
	}

	public static boolean equals(@Nullable ManufacturingJob job1, @Nullable ManufacturingJob job2)
	{
		return Objects.equals(job1, job2);
	}

	public void assertUserReporting()
	{
		if (!allowUserReporting)
		{
			throw new AdempiereException("Cannot report to this manufacturing job. It was already closed, voided or revered.");
		}
	}

	public ManufacturingJobActivity getActivityById(@NonNull WFActivityId wfActivityId)
	{
		return getActivityById(wfActivityId.getAsId(ManufacturingJobActivityId.class));
	}

	public ManufacturingJobActivity getActivityById(@NonNull ManufacturingJobActivityId id)
	{
		return activities.stream()
				.filter(activity -> ManufacturingJobActivityId.equals(activity.getId(), id))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No activity found for " + id));
	}

	public boolean isLastActivity(@NonNull final ManufacturingJobActivityId jobActivityId)
	{
		final ManufacturingJobActivity lastActivity = activities.get(activities.size() - 1);
		return ManufacturingJobActivityId.equals(lastActivity.getId(), jobActivityId);
	}

	public ManufacturingJob withChangedRawMaterialsIssueStep(
			@NonNull final PPOrderRoutingActivityId activityId,
			@NonNull final PPOrderIssueScheduleId issueScheduleId,
			@NonNull UnaryOperator<RawMaterialsIssueStep> mapper)
	{
		if (!containsRawMaterialsIssueStep(issueScheduleId))
		{
			throw new AdempiereException("Cannot find issue step");
		}

		final ImmutableList<ManufacturingJobActivity> activitiesNew = CollectionUtils.map(
				activities,
				activity -> PPOrderRoutingActivityId.equals(activity.getOrderRoutingActivityId(), activityId)
						? activity.withChangedRawMaterialsIssueStep(issueScheduleId, mapper)
						: activity);
		return withActivities(activitiesNew);
	}

	private ManufacturingJob withActivities(final ImmutableList<ManufacturingJobActivity> activitiesNew)
	{
		return !Objects.equals(this.activities, activitiesNew)
				? toBuilder().activities(activitiesNew).build()
				: this;
	}

	private boolean containsRawMaterialsIssueStep(final PPOrderIssueScheduleId issueScheduleId)
	{
		return activities.stream().anyMatch(activity -> activity.containsRawMaterialsIssueStep(issueScheduleId));
	}

	public ManufacturingJob withChangedReceiveLine(
			@NonNull final FinishedGoodsReceiveLineId id,
			@NonNull UnaryOperator<FinishedGoodsReceiveLine> mapper)
	{
		final ImmutableList<ManufacturingJobActivity> activitiesNew = CollectionUtils.map(activities, activity -> activity.withChangedReceiveLine(id, mapper));
		return withActivities(activitiesNew);
	}

	public FinishedGoodsReceiveLine getFinishedGoodsReceiveLineById(@NonNull final FinishedGoodsReceiveLineId finishedGoodsReceiveLineId)
	{
		return activities.stream()
				.map(ManufacturingJobActivity::getFinishedGoodsReceive)
				.filter(Objects::nonNull)
				.map(finishedGoodsReceive -> finishedGoodsReceive.getLineByIdOrNull(finishedGoodsReceiveLineId))
				.filter(Objects::nonNull)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No finished goods receive line found for " + finishedGoodsReceiveLineId));
	}

	public ManufacturingJob withCurrentScaleDevice(@Nullable final DeviceId currentScaleDeviceId)
	{
		return !DeviceId.equals(this.currentScaleDeviceId, currentScaleDeviceId)
				? toBuilder().currentScaleDeviceId(currentScaleDeviceId).build()
				: this;
	}
}
