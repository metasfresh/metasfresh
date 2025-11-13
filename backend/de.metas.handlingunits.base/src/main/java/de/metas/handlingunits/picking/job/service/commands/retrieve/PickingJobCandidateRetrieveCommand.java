package de.metas.handlingunits.picking.job.service.commands.retrieve;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.product.ProductId;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Builder
public class PickingJobCandidateRetrieveCommand
{
	//
	// Services
	@NonNull private final IPackagingDAO packagingDAO;
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final IWarehouseBL warehouseBL;
	@NonNull private final MobileUIPickingUserProfileRepository configRepository;
	@NonNull private final PickingJobScheduleService pickingJobScheduleService;
	@NonNull private final WorkplaceService workplaceService;

	//
	// Params
	@NonNull private final PickingJobQuery query;

	//
	// State
	@Nullable ImmutableMap<ShipmentScheduleId, PickingJobSchedule> _onlyPickingJobSchedules; // lazy
	@NonNull private final LinkedHashMap<OrderBasedAggregationKey, OrderBasedAggregation> orderBasedAggregates = new LinkedHashMap<>();
	@NonNull private final LinkedHashMap<ProductBasedAggregationKey, ProductBasedAggregation> productBasedAggregates = new LinkedHashMap<>();
	@NonNull private final LinkedHashMap<DeliveryLocationBasedAggregationKey, DeliveryLocationBasedAggregation> deliveryLocationBasedAggregates = new LinkedHashMap<>();

	public List<PickingJobCandidate> execute()
	{
		streamPackageables()
				.map(this::toScheduledPackageable)
				.filter(Objects::nonNull)
				.forEach(this::add);

		ImmutableList<PickingJobCandidate> jobCandidates = aggregate();
		jobCandidates = allocateQtyAvailableAndFilter(jobCandidates);

		return jobCandidates;
	}

	private ImmutableList<PickingJobCandidate> allocateQtyAvailableAndFilter(final ImmutableList<PickingJobCandidate> jobs)
	{
		if (jobs.isEmpty())
		{
			return jobs;
		}

		final ProductAvailableStocks productAvailableStocks = createProductsAvailableStocksOrNull();
		if (productAvailableStocks == null)
		{
			return jobs;
		}
		productAvailableStocks.warmUpByProductIds(extractProductIds(jobs));

		final ArrayList<PickingJobCandidate> result = new ArrayList<>();
		for (final PickingJobCandidate job : jobs)
		{
			final PickingJobCandidate jobAllocated = productAvailableStocks.allocate(job);
			if (query.isOnlyIfQtyAvailableAtPickingLocator() && jobAllocated.hasQtyAvailableToPick().isFalse())
			{
				continue;
			}

			result.add(jobAllocated);
		}

		return ImmutableList.copyOf(result);
	}

	@NotNull
	private static Set<ProductId> extractProductIds(final ImmutableList<PickingJobCandidate> jobs)
	{
		return jobs.stream()
				.flatMap(job -> job.getProductIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	private Stream<Packageable> streamPackageables()
	{
		final ImmutableMap<ShipmentScheduleId, PickingJobSchedule> jobSchedules = getJobSchedules();
		if (isScheduledForWorkplaceOnly() && jobSchedules.isEmpty())
		{
			return Stream.of();
		}

		return packagingDAO.stream(query.toPackageableQueryBuilder().onlyShipmentScheduleIds(jobSchedules.keySet()).build());
	}

	@Nullable
	private ScheduledPackageable toScheduledPackageable(@NonNull Packageable packageable)
	{
		if (isScheduledForWorkplaceOnly())
		{
			final PickingJobSchedule schedule = getJobScheduleOrNull(packageable.getShipmentScheduleId());
			return schedule != null
					? ScheduledPackageable.of(packageable, schedule)
					: null;
		}
		else
		{
			return ScheduledPackageable.ofPackageable(packageable);
		}
	}

	@Nullable
	private PickingJobSchedule getJobScheduleOrNull(ShipmentScheduleId shipmentScheduleId)
	{
		return getJobSchedules().get(shipmentScheduleId);
	}

	private ImmutableMap<ShipmentScheduleId, PickingJobSchedule> getJobSchedules()
	{
		if (this._onlyPickingJobSchedules == null)
		{
			if (isScheduledForWorkplaceOnly())
			{
				this._onlyPickingJobSchedules = Maps.uniqueIndex(
						pickingJobScheduleService.list(
								PickingJobScheduleQuery.builder()
										.workplaceId(query.getScheduledForWorkplaceId())
										.excludeJobScheduleIds(query.getExcludeScheduleIds().getJobScheduleIds())
										.build()
						),
						PickingJobSchedule::getShipmentScheduleId
				);
			}
			else
			{
				this._onlyPickingJobSchedules = ImmutableMap.of();
			}
		}

		return this._onlyPickingJobSchedules;
	}

	private boolean isScheduledForWorkplaceOnly()
	{
		return query.getScheduledForWorkplaceId() != null;
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

	@Nullable
	private ProductAvailableStocks createProductsAvailableStocksOrNull()
	{
		final Set<LocatorId> pickFromLocatorIds = getPickFromLocatorIds();
		if (pickFromLocatorIds.isEmpty())
		{
			return null;
		}

		return ProductAvailableStocks.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.pickFromLocatorIds(pickFromLocatorIds)
				.build();
	}

	private Set<LocatorId> getPickFromLocatorIds()
	{
		final WorkplaceId scheduledForWorkplaceId = query.getScheduledForWorkplaceId();
		if (query.getScheduledForWorkplaceId() != null)
		{
			return workplaceService.getPickFromLocatorIds(scheduledForWorkplaceId);
		}

		final WarehouseId warehouseId = query.getWarehouseId();
		if (warehouseId != null)
		{
			return warehouseBL.getLocatorIdsByWarehouseId(warehouseId);
		}

		return ImmutableSet.of();
	}
}
