package de.metas.handlingunits.picking.job_schedule.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesCommand;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleAndJobSchedules;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleAndJobSchedulesCollection;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleCollection;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PickingJobScheduleService
{
	@NonNull private final IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	@NonNull private final PickingJobScheduleRepository pickingJobScheduleRepository;
	@NonNull private final IShipmentScheduleInvalidateBL invalidSchedulesService;

	public static PickingJobScheduleService newInstanceForUnitTesting()
	{
		return SpringContextHolder.getBeanOrSupply(
				PickingJobScheduleService.class,
				() -> new PickingJobScheduleService(
						new PickingJobScheduleRepository(),
						Services.get(IShipmentScheduleInvalidateBL.class)
				)
		);
	}

	public List<PickingJobSchedule> getByIds(@NonNull final Set<PickingJobScheduleId> ids)
	{
		return pickingJobScheduleRepository.getByIds(ids);
	}

	public ShipmentScheduleAndJobSchedulesCollection getShipmentScheduleAndJobSchedulesCollection(@NonNull final ShipmentScheduleAndJobScheduleIdSet scheduleIds)
	{
		if (scheduleIds.isEmpty()) {return ShipmentScheduleAndJobSchedulesCollection.EMPTY;}
		return getShipmentScheduleAndJobSchedulesCollection(scheduleIds.getShipmentScheduleIds(), scheduleIds.getJobScheduleIds());
	}

	public ShipmentScheduleAndJobSchedulesCollection getShipmentScheduleAndJobSchedulesCollection(
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds,
			@NonNull final Set<PickingJobScheduleId> jobScheduleIds)
	{
		final HashSet<ShipmentScheduleId> shipmentScheduleIdsToLoad = new HashSet<>(shipmentScheduleIds);

		final ImmutableListMultimap<ShipmentScheduleId, PickingJobSchedule> jobSchedulesByShipmentScheduleId = Multimaps.index(
				getByIds(jobScheduleIds),
				PickingJobSchedule::getShipmentScheduleId
		);
		shipmentScheduleIdsToLoad.addAll(jobSchedulesByShipmentScheduleId.keySet());

		return shipmentScheduleBL.getByIds(shipmentScheduleIdsToLoad)
				.values()
				.stream()
				.map(shipmentSchedule -> {
					ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
					final ImmutableList<PickingJobSchedule> jobSchedules = jobSchedulesByShipmentScheduleId.get(shipmentScheduleId);
					return ShipmentScheduleAndJobSchedules.of(shipmentSchedule, jobSchedules);
				})
				.collect(ShipmentScheduleAndJobSchedulesCollection.collect());

	}

	public void createOrUpdate(@NonNull final CreateOrUpdatePickingJobSchedulesRequest request)
	{
		CreateOrUpdatePickingJobSchedulesCommand.builder()
				.pickingJobScheduleRepository(pickingJobScheduleRepository)
				.shipmentScheduleBL(shipmentScheduleBL)
				//
				.request(request)
				//
				.build().execute();
	}

	public void deleteJobSchedulesById(@NonNull final Set<PickingJobScheduleId> jobScheduleIds)
	{
		pickingJobScheduleRepository.deleteJobSchedulesById(jobScheduleIds);
	}

	public List<PickingJobSchedule> list(@NonNull PickingJobScheduleQuery query)
	{
		return pickingJobScheduleRepository.list(query);
	}

	public Stream<PickingJobSchedule> stream(@NonNull PickingJobScheduleQuery query)
	{
		return pickingJobScheduleRepository.stream(query);
	}

	public void markAsProcessed(final Set<PickingJobScheduleId> ids)
	{
		pickingJobScheduleRepository.updateByIds(ids, jobSchedule -> jobSchedule.toBuilder().processed(true).build());
	}

	public Set<ShipmentScheduleId> getShipmentScheduleIdsWithAllJobSchedulesProcessedOrMissing(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty()) {return ImmutableSet.of();}

		final Map<ShipmentScheduleId, PickingJobScheduleCollection> jobSchedulesByShipmentScheduleId = stream(PickingJobScheduleQuery.builder().onlyShipmentScheduleIds(shipmentScheduleIds).build())
				.collect(Collectors.groupingBy(PickingJobSchedule::getShipmentScheduleId, PickingJobScheduleCollection.collect()));

		final HashSet<ShipmentScheduleId> result = new HashSet<>();
		for (final ShipmentScheduleId shipmentScheduleId : shipmentScheduleIds)
		{
			final PickingJobScheduleCollection jobSchedules = jobSchedulesByShipmentScheduleId.get(shipmentScheduleId);
			if (jobSchedules == null || jobSchedules.isAllProcessed())
			{
				result.add(shipmentScheduleId);
			}
		}

		return result;
	}
}
