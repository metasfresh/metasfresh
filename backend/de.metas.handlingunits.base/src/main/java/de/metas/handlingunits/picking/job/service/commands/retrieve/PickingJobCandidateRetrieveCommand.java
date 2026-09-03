package de.metas.handlingunits.picking.job.service.commands.retrieve;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateList;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.Packageable;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleCollection;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Builder
public class PickingJobCandidateRetrieveCommand
{
	//
	// Services
	@NonNull private final PickingJobShipmentScheduleService shipmentScheduleService;
	@NonNull private final MobileUIPickingUserProfileService configService;
	@NonNull private final PickingJobScheduleService pickingJobScheduleService;

	//
	// Params
	@NonNull private final PickingJobQuery query;

	//
	// State
	@Nullable private PickingJobScheduleCollection _onlyPickingJobSchedules; // lazy
	@NonNull private final LinkedHashMap<OrderBasedAggregationKey, OrderBasedAggregation> orderBasedAggregates = new LinkedHashMap<>();
	@NonNull private final LinkedHashMap<ProductBasedAggregationKey, ProductBasedAggregation> productBasedAggregates = new LinkedHashMap<>();
	@NonNull private final LinkedHashMap<DeliveryLocationBasedAggregationKey, DeliveryLocationBasedAggregation> deliveryLocationBasedAggregates = new LinkedHashMap<>();

	public PickingJobCandidateList execute()
	{
		streamPackageables()
				.map(this::toScheduledPackageable)
				.filter(Objects::nonNull)
				.forEach(this::add);

		return aggregate();
	}

	private Stream<Packageable> streamPackageables()
	{
		final Set<ShipmentScheduleId> onlyShipmentScheduleIds;
		if (query.isScheduledForWorkplaceOnly())
		{
			final PickingJobScheduleCollection jobSchedules = getJobSchedules();
			if (jobSchedules.isEmpty())
			{
				return Stream.of();
			}

			onlyShipmentScheduleIds = jobSchedules.getShipmentScheduleIds();
		}
		else
		{
			onlyShipmentScheduleIds = null;
		}

		return shipmentScheduleService.stream(
				query.toPackageableQueryBuilder()
						.onlyShipmentScheduleIds(onlyShipmentScheduleIds)
						.build()
		);
	}

	@Nullable
	private ScheduledPackageable toScheduledPackageable(@NonNull final Packageable packageable)
	{
		if (query.isScheduledForWorkplaceOnly())
		{
			final PickingJobSchedule schedule = getJobSchedule(packageable.getShipmentScheduleId()).orElse(null);
			return schedule != null
					? ScheduledPackageable.of(packageable, schedule)
					: null;
		}
		else
		{
			return ScheduledPackageable.ofPackageable(packageable);
		}
	}

	private Optional<PickingJobSchedule> getJobSchedule(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return getJobSchedules().getSingleScheduleByShipmentScheduleId(shipmentScheduleId);
	}

	private PickingJobScheduleCollection getJobSchedules()
	{
		if (this._onlyPickingJobSchedules == null)
		{
			this._onlyPickingJobSchedules = retrieveJobSchedules();
		}
		return this._onlyPickingJobSchedules;
	}

	private PickingJobScheduleCollection retrieveJobSchedules()
	{
		return query.isScheduledForWorkplaceOnly()
				? pickingJobScheduleService.list(query.toPickingJobScheduleQuery())
				: PickingJobScheduleCollection.EMPTY;
	}

	private PickingJobCandidateList aggregate()
	{
		final ImmutableList.Builder<PickingJobCandidate> result = ImmutableList.builder();
		orderBasedAggregates.values().forEach(aggregation -> result.add(aggregation.toPickingJobCandidate()));
		productBasedAggregates.values().forEach(aggregation -> result.add(aggregation.toPickingJobCandidate()));
		deliveryLocationBasedAggregates.values().forEach(aggregation -> result.add(aggregation.toPickingJobCandidate()));

		return PickingJobCandidateList.ofList(result.build());
	}

	private void add(@NonNull final ScheduledPackageable item)
	{
		final PickingJobAggregationType aggregationType = configService.getAggregationType(item.getCustomerId());
		switch (aggregationType)
		{
			case SALES_ORDER:
			{
				orderBasedAggregates.computeIfAbsent(OrderBasedAggregationKey.of(item), OrderBasedAggregation::new).add(item);
				break;
			}
			case PRODUCT:
			{
				productBasedAggregates.computeIfAbsent(ProductBasedAggregationKey.of(item), ProductBasedAggregation::new).add(item);
				break;
			}
			case DELIVERY_LOCATION:
			{
				deliveryLocationBasedAggregates.computeIfAbsent(DeliveryLocationBasedAggregationKey.of(item), DeliveryLocationBasedAggregation::new).add(item);
				break;
			}
			default:
			{
				throw new AdempiereException("Unknown aggregation type: " + aggregationType);
			}
		}
	}
}
