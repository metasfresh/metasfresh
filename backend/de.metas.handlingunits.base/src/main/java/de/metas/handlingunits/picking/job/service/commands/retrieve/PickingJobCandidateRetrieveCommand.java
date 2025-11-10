package de.metas.handlingunits.picking.job.service.commands.retrieve;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.IPackagingDAO;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Builder
public class PickingJobCandidateRetrieveCommand
{
	//
	// Services
	@NonNull private final IPackagingDAO packagingDAO;
	@NonNull private final MobileUIPickingUserProfileRepository configRepository;
	@NonNull private final PickingJobScheduleService pickingJobScheduleService;

	//
	// Params
	@NonNull private final PickingJobQuery query;

	//
	// State
	@NonNull private final LinkedHashMap<OrderBasedAggregationKey, OrderBasedAggregation> orderBasedAggregates = new LinkedHashMap<>();
	@NonNull private final LinkedHashMap<ProductBasedAggregationKey, ProductBasedAggregation> productBasedAggregates = new LinkedHashMap<>();
	@NonNull private final LinkedHashMap<DeliveryLocationBasedAggregationKey, DeliveryLocationBasedAggregation> deliveryLocationBasedAggregates = new LinkedHashMap<>();

	public List<PickingJobCandidate> execute()
	{
		if (query.getScheduledForWorkplaceId() != null)
		{
			final Map<ShipmentScheduleId, PickingJobSchedule> onlyPickingJobSchedules = Maps.uniqueIndex(
					pickingJobScheduleService.list(
							PickingJobScheduleQuery.builder()
									.workplaceId(query.getScheduledForWorkplaceId())
									.excludeJobScheduleIds(query.getExcludeScheduleIds().getJobScheduleIds())
									.build()
					),
					PickingJobSchedule::getShipmentScheduleId
			);
			if (onlyPickingJobSchedules.isEmpty())
			{
				return ImmutableList.of();
			}

			packagingDAO.stream(query.toPackageableQueryBuilder().onlyShipmentScheduleIds(onlyPickingJobSchedules.keySet()).build())
					.map(packagable -> {
						final PickingJobSchedule schedule = onlyPickingJobSchedules.get(packagable.getShipmentScheduleId());
						return schedule != null
								? ScheduledPackageable.of(packagable, schedule)
								: null;
					})
					.filter(Objects::nonNull)
					.forEach(this::add);
		}
		else
		{
			packagingDAO.stream(query.toPackageableQuery())
					.map(ScheduledPackageable::ofPackageable)
					.forEach(this::add);
		}

		return aggregate();
	}

	private ImmutableList<PickingJobCandidate> aggregate()
	{
		final ImmutableList.Builder<PickingJobCandidate> result = ImmutableList.builder();
		orderBasedAggregates.values().forEach(aggregation -> result.add(aggregation.toPickingJobCandidate()));
		productBasedAggregates.values().forEach(aggregation -> result.add(aggregation.toPickingJobCandidate()));
		deliveryLocationBasedAggregates.values().forEach(aggregation -> result.add(aggregation.toPickingJobCandidate()));

		return result.build();
	}

	private void add(@NonNull final ScheduledPackageable item)
	{
		final PickingJobAggregationType aggregationType = configRepository.getAggregationType(item.getCustomerId());
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
