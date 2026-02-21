package de.metas.handlingunits.picking.job_schedule.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesCommand;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesRequest;
import de.metas.handlingunits.picking.job_schedule.service.commands.PickingJobScheduleAutoAssignCommand;
import de.metas.handlingunits.picking.job_schedule.service.commands.PickingJobScheduleAutoAssignRequest;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleAndJobSchedules;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleAndJobSchedulesCollection;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleCollection;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PickingJobScheduleService
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull private final PickingJobScheduleRepository pickingJobScheduleRepository;
	@NonNull private final WorkplaceRepository workplaceRepository;
	@NonNull private final PickingJobProductService pickingJobProductService;
	@NonNull private final PickingJobShipmentScheduleService pickingJobShipmentScheduleService;

	public static PickingJobScheduleService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				PickingJobScheduleService.class,
				() -> new PickingJobScheduleService(
						new PickingJobScheduleRepository(),
						WorkplaceRepository.newInstanceForUnitTesting(),
						PickingJobProductService.newInstanceForUnitTesting(),
						PickingJobShipmentScheduleService.newInstanceForUnitTesting()
				)
		);
	}

	public PickingJobSchedule getById(@NonNull final PickingJobScheduleId id)
	{
		return pickingJobScheduleRepository.getById(id);
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

		return pickingJobShipmentScheduleService.getByIdsAsRecordMap(shipmentScheduleIdsToLoad)
				.values()
				.stream()
				.map(shipmentSchedule -> {
					final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
					final ImmutableList<PickingJobSchedule> jobSchedules = jobSchedulesByShipmentScheduleId.get(shipmentScheduleId);
					return ShipmentScheduleAndJobSchedules.of(shipmentSchedule, jobSchedules);
				})
				.collect(ShipmentScheduleAndJobSchedulesCollection.collect());

	}

	public void createOrUpdate(@NonNull final CreateOrUpdatePickingJobSchedulesRequest request)
	{
		CreateOrUpdatePickingJobSchedulesCommand.builder()
				.pickingJobScheduleRepository(pickingJobScheduleRepository)
				.pickingJobShipmentScheduleService(pickingJobShipmentScheduleService)
				//
				.request(request)
				//
				.build().execute();
	}

	public void deleteJobSchedulesById(@NonNull final Set<PickingJobScheduleId> jobScheduleIds)
	{
		final PickingJobScheduleCollection deletedSchedules = pickingJobScheduleRepository.deleteByIdsAndReturn(jobScheduleIds);
		pickingJobShipmentScheduleService.flagForRecompute(deletedSchedules.getShipmentScheduleIds());
	}

	public PickingJobScheduleCollection list(@NonNull final PickingJobScheduleQuery query)
	{
		return pickingJobScheduleRepository.list(query);
	}

	public Stream<PickingJobSchedule> stream(@NonNull final PickingJobScheduleQuery query)
	{
		return pickingJobScheduleRepository.stream(query);
	}

	public void markAsProcessed(final Set<PickingJobScheduleId> ids)
	{
		pickingJobScheduleRepository.updateByIds(ids, jobSchedule -> jobSchedule.toBuilder().processed(true).build());
	}

	public Quantity getQtyRemainingToScheduleForPicking(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = pickingJobShipmentScheduleService.getByIdAsRecord(shipmentScheduleId);
		final Quantity qtyToDeliver = pickingJobShipmentScheduleService.getQtyToDeliver(shipmentSchedule);
		final Quantity qtyScheduledForPicking = pickingJobShipmentScheduleService.getQtyScheduledForPicking(shipmentSchedule);
		return qtyToDeliver.subtract(qtyScheduledForPicking);
		
	}

	public void autoAssign(@NonNull final PickingJobScheduleAutoAssignRequest request)
	{
		PickingJobScheduleAutoAssignCommand.builder()
				.workplaceRepository(workplaceRepository)
				.pickingJobScheduleRepository(pickingJobScheduleRepository)
				.pickingJobShipmentScheduleService(pickingJobShipmentScheduleService)
				.sysConfigBL(sysConfigBL)
				.pickingJobProductService(pickingJobProductService)
				.request(request)
				.build()
				.execute();
	}
}
