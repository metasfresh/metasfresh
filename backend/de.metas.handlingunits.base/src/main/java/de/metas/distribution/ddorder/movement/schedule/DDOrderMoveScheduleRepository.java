package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_DD_OrderLine_HU_Candidate;
import de.metas.handlingunits.model.I_DD_Order_MoveSchedule;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.mmovement.MovementId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public class DDOrderMoveScheduleRepository
{
	private static final IQueryBL queryBL = Services.get(IQueryBL.class);

	public DDOrderMoveSchedule getById(@NonNull final DDOrderMoveScheduleId id)
	{
		final I_DD_Order_MoveSchedule record = InterfaceWrapperHelper.load(id, I_DD_Order_MoveSchedule.class);

		final List<I_DD_OrderLine_HU_Candidate> pickedHUsRecords = queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_Order_MoveSchedule_ID, id)
				.create()
				.list();
		return toDDOrderMoveSchedule(record, pickedHUsRecords);
	}

	public IQueryFilter<I_M_HU> getHUsNotAlreadyScheduledToMoveFilter()
	{
		final ICompositeQueryFilter<I_M_HU> filter = queryBL.createCompositeQueryFilter(I_M_HU.class);

		// HU Filter: only those which were not already assigned to a DD_OrderLine
		{
			final IQuery<I_DD_OrderLine_HU_Candidate> scheduledInProgress = queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED, DDOrderMoveScheduleStatus.IN_PROGRESS)
					.create();
			filter.addNotInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID, I_DD_OrderLine_HU_Candidate.COLUMN_M_HU_ID, scheduledInProgress);
		}

		return filter;
	}

	public ImmutableList<DDOrderMoveSchedule> createScheduleToMoveBulk(@NonNull final List<DDOrderMoveScheduleCreateRequest> requests)
	{
		return requests.stream()
				.map(this::createScheduleToMove)
				.collect(ImmutableList.toImmutableList());
	}

	public DDOrderMoveSchedule createScheduleToMove(@NonNull final DDOrderMoveScheduleCreateRequest request)
	{
		final I_DD_Order_MoveSchedule record = InterfaceWrapperHelper.newInstance(I_DD_Order_MoveSchedule.class);
		//record.setAD_Org_ID(ddOrderline.getAD_Org_ID());
		record.setDD_Order_ID(request.getDdOrderId().getRepoId());
		record.setDD_OrderLine_ID(request.getDdOrderLineId().getRepoId());

		record.setStatus(DDOrderMoveScheduleStatus.NOT_STARTED.getCode());
		record.setM_Product_ID(request.getProductId().getRepoId());

		//
		// Pick From
		record.setPickFrom_Warehouse_ID(request.getPickFromLocatorId().getWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(request.getPickFromLocatorId().getRepoId());
		record.setPickFrom_HU_ID(request.getPickFromHUId().getRepoId());
		record.setC_UOM_ID(request.getQtyToPick().getUomId().getRepoId());
		record.setQtyToPick(request.getQtyToPick().toBigDecimal());
		record.setQtyPicked(BigDecimal.ZERO);
		record.setIsPickWholeHU(request.isPickWholeHU());

		//
		// Drop To
		record.setDropTo_Warehouse_ID(request.getDropToLocatorId().getWarehouseId().getRepoId());
		record.setDropTo_Locator_ID(request.getDropToLocatorId().getRepoId());

		//
		InterfaceWrapperHelper.save(record);
		return toDDOrderMoveSchedule(record, ImmutableList.of());
	}

	/**
	 * @implNote pls keep in sync with {@link #save(DDOrderMoveSchedule)}
	 */
	private static DDOrderMoveSchedule toDDOrderMoveSchedule(
			@NonNull final I_DD_Order_MoveSchedule record,
			@NonNull final List<I_DD_OrderLine_HU_Candidate> pickedHUsRecords)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return DDOrderMoveSchedule.builder()
				.id(DDOrderMoveScheduleId.ofRepoId(record.getDD_Order_MoveSchedule_ID()))
				.ddOrderId(DDOrderId.ofRepoId(record.getDD_Order_ID()))
				.ddOrderLineId(DDOrderLineId.ofRepoId(record.getDD_OrderLine_ID()))
				.status(DDOrderMoveScheduleStatus.ofCode(record.getStatus()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				//
				// Pick From
				.pickFromLocatorId(LocatorId.ofRepoId(record.getPickFrom_Warehouse_ID(), record.getPickFrom_Locator_ID()))
				.pickFromHUId(HuId.ofRepoId(record.getPickFrom_HU_ID()))
				//				.actualHUIdPicked(HuId.ofRepoIdOrNull(record.getM_HU_ID()))
				.qtyToPick(Quantitys.of(record.getQtyToPick(), uomId))
				.isPickWholeHU(record.isPickWholeHU())
				//
				// Drop To
				.dropToLocatorId(LocatorId.ofRepoId(record.getDropTo_Warehouse_ID(), record.getDropTo_Locator_ID()))
				//
				// Status
				.qtyNotPickedReason(QtyRejectedReasonCode.ofNullableCode(record.getRejectReason()).orElse(null))
				.pickedHUs(toDDOrderMoveSchedulePickedHUs(pickedHUsRecords))
				//
				.build();
	}

	private static DDOrderMoveSchedulePickedHUs toDDOrderMoveSchedulePickedHUs(@NonNull final List<I_DD_OrderLine_HU_Candidate> pickedHUsRecords)
	{
		if (pickedHUsRecords.isEmpty())
		{
			return null;
		}

		return pickedHUsRecords.stream()
				.map(DDOrderMoveScheduleRepository::toDDOrderMoveSchedulePickedHU)
				.collect(DDOrderMoveSchedulePickedHUs.collect());

	}

	private static DDOrderMoveSchedulePickedHU toDDOrderMoveSchedulePickedHU(@NonNull final I_DD_OrderLine_HU_Candidate record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return DDOrderMoveSchedulePickedHU.builder()
				//
				// Pick From
				.actualHUIdPicked(HuId.ofRepoIdOrNull(record.getM_HU_ID()))
				.qtyPicked(Quantitys.of(record.getQtyPicked(), uomId))
				.pickFromMovementId(MovementId.ofRepoId(record.getPickFrom_Movement_ID()))
				.inTransitLocatorId(LocatorId.ofRepoIdOrNull(record.getInTransit_Warehouse_ID(), record.getInTransit_Locator_ID()))
				//
				// Drop To
				.dropToMovementId(MovementId.ofRepoIdOrNull(record.getDropTo_Movement_ID()))
				//
				.build();
	}

	/**
	 * @implNote pls keep in sync with {@link #toDDOrderMoveSchedule(I_DD_Order_MoveSchedule, List)}
	 */
	public void save(@NonNull final DDOrderMoveSchedule schedule)
	{
		final I_DD_Order_MoveSchedule record = InterfaceWrapperHelper.load(schedule.getId(), I_DD_Order_MoveSchedule.class);

		final HashMap<HuId, I_DD_OrderLine_HU_Candidate> pickedHURecords = queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_Order_MoveSchedule_ID, schedule.getId())
				.create()
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(pickedHURecord -> HuId.ofRepoId(pickedHURecord.getM_HU_ID())));

		record.setStatus(schedule.getStatus().getCode());
		record.setQtyPicked(schedule.getQtyPicked().toBigDecimal());
		record.setRejectReason(QtyRejectedReasonCode.toCode(schedule.getQtyNotPickedReason()));
		InterfaceWrapperHelper.save(record);

		final DDOrderMoveSchedulePickedHUs pickedHUs = schedule.getPickedHUs();
		if (pickedHUs != null)
		{
			for (final DDOrderMoveSchedulePickedHU pickedHU : pickedHUs.toCollection())
			{
				final I_DD_OrderLine_HU_Candidate existingPickedHURecord = pickedHURecords.remove(pickedHU.getActualHUIdPicked());
				savePickedHU(pickedHU, existingPickedHURecord, schedule);
			}
		}

		InterfaceWrapperHelper.deleteAll(pickedHURecords.values());
	}

	private void savePickedHU(
			final @NonNull DDOrderMoveSchedulePickedHU pickedHU,
			final @Nullable I_DD_OrderLine_HU_Candidate existingRecord,
			final @NonNull DDOrderMoveSchedule schedule)
	{
		final I_DD_OrderLine_HU_Candidate record;
		if (existingRecord == null)
		{
			record = InterfaceWrapperHelper.newInstance(I_DD_OrderLine_HU_Candidate.class);
			record.setDD_Order_MoveSchedule_ID(schedule.getId().getRepoId());
			record.setDD_Order_ID(schedule.getDdOrderId().getRepoId());
			record.setDD_OrderLine_ID(schedule.getDdOrderLineId().getRepoId());
		}
		else
		{
			record = existingRecord;
		}

		record.setStatus(schedule.getStatus().getCode());
		record.setM_Product_ID(schedule.getProductId().getRepoId());

		//
		// Pick From
		record.setPickFrom_Warehouse_ID(schedule.getPickFromLocatorId().getWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(schedule.getPickFromLocatorId().getRepoId());
		record.setPickFrom_HU_ID(schedule.getPickFromHUId().getRepoId());
		//
		record.setM_HU_ID(HuId.toRepoId(pickedHU.getActualHUIdPicked()));
		record.setIsPickWholeHU(schedule.isPickWholeHU());
		record.setC_UOM_ID(pickedHU.getQtyPicked().getUomId().getRepoId());
		record.setQtyPicked(pickedHU.getQtyPicked().toBigDecimal());
		record.setRejectReason(QtyRejectedReasonCode.toCode(schedule.getQtyNotPickedReason()));
		record.setPickFrom_Movement_ID(MovementId.toRepoId(pickedHU.getPickFromMovementId()));
		record.setInTransit_Warehouse_ID(pickedHU.getInTransitLocatorId() != null ? pickedHU.getInTransitLocatorId().getWarehouseId().getRepoId() : -1);
		record.setInTransit_Locator_ID(pickedHU.getInTransitLocatorId() != null ? pickedHU.getInTransitLocatorId().getRepoId() : -1);

		//
		// Drop To Status
		record.setDropTo_Warehouse_ID(schedule.getDropToLocatorId().getWarehouseId().getRepoId());
		record.setDropTo_Locator_ID(schedule.getDropToLocatorId().getRepoId());
		//
		record.setDropTo_Movement_ID(MovementId.toRepoId(pickedHU.getDropToMovementId()));

		InterfaceWrapperHelper.save(record);
	}

	public final List<HuId> retrieveHUIdsScheduledButNotMovedYet(@NonNull final DDOrderLineId ddOrderLineId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED)
				.orderBy(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_HU_Candidate_ID)
				.create()
				.listDistinct(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID, HuId.class);
	}

	public void removeNotStarted(@NonNull final DDOrderLineId ddOrderLineId)
	{
		final ImmutableSet<DDOrderMoveScheduleId> scheduleIds = queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED)
				.create()
				.listIds(DDOrderMoveScheduleId::ofRepoId);

		deleteByScheduleIds(scheduleIds);
	}

	public void removeNotStarted(@NonNull final DDOrderId ddOrderId)
	{
		final ImmutableSet<DDOrderMoveScheduleId> scheduleIds = queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_ID, ddOrderId)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED)
				.create()
				.listIds(DDOrderMoveScheduleId::ofRepoId);

		deleteByScheduleIds(scheduleIds);
	}

	private void deleteByScheduleIds(final Set<DDOrderMoveScheduleId> scheduleIds)
	{
		if (scheduleIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addInArrayFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_Order_MoveSchedule_ID, scheduleIds)
				.create()
				.delete();

		queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addInArrayFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_MoveSchedule_ID, scheduleIds)
				.create()
				.delete();
	}

	public void removeFromHUsScheduledToMoveList(@NonNull final DDOrderLineId ddOrderLineId, @NonNull final Set<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return;
		}

		//
		// Create the query to select the lines we want to remove
		queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addInArrayFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.delete();
	}

	public boolean isScheduledToMove(@NonNull final HuId huId)
	{
		// TODO: only not processed ones

		return queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create()
				.anyMatch();
	}

	public ImmutableList<DDOrderMoveSchedule> getSchedules(final DDOrderId ddOrderId)
	{
		final ImmutableListMultimap<DDOrderMoveScheduleId, I_DD_OrderLine_HU_Candidate> pickedHURecords = queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_Order_ID, ddOrderId)
				.orderBy(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_ID)
				.orderBy(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_HU_Candidate_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> DDOrderMoveScheduleId.ofRepoId(record.getDD_Order_MoveSchedule_ID()),
						record -> record));

		return queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_ID, ddOrderId)
				.orderBy(I_DD_Order_MoveSchedule.COLUMNNAME_DD_OrderLine_ID)
				.orderBy(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_MoveSchedule_ID)
				.create()
				.stream()
				.map(record -> toDDOrderMoveSchedule(record, pickedHURecords.get(DDOrderMoveScheduleId.ofRepoId(record.getDD_Order_MoveSchedule_ID()))))
				.collect(ImmutableList.toImmutableList());
	}

	public boolean hasInProgressSchedules(@NonNull final DDOrderLineId ddOrderLineId)
	{
		return queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_Status, DDOrderMoveScheduleStatus.IN_PROGRESS)
				.create()
				.anyMatch();
	}

	public boolean hasInProgressSchedules(final DDOrderId ddOrderId)
	{
		return queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_ID, ddOrderId)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_Status, DDOrderMoveScheduleStatus.IN_PROGRESS)
				.create()
				.anyMatch();
	}
}
