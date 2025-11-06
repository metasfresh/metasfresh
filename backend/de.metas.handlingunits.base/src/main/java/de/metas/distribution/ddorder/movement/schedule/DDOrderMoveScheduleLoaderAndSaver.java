package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_DD_OrderLine_HU_Candidate;
import de.metas.handlingunits.model.I_DD_Order_MoveSchedule;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mmovement.MovementId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Builder
class DDOrderMoveScheduleLoaderAndSaver
{
	// services
	@NonNull private final IQueryBL queryBL;

	// state
	private final HashMap<DDOrderMoveScheduleId, I_DD_Order_MoveSchedule> recordsById = new HashMap<>();
	private final HashMap<DDOrderMoveScheduleId, List<I_DD_OrderLine_HU_Candidate>> pickedHUsRecords = new HashMap<>();

	public void warmUpById(@NonNull final DDOrderMoveScheduleId id)
	{
		warmUpByIds(ImmutableSet.of(id));
	}

	public void warmUpByIds(@NonNull final Set<DDOrderMoveScheduleId> ids)
	{
		if (ids.isEmpty()) {return;}

		getRecordByIds(ids);
		CollectionUtils.getAllOrLoad(pickedHUsRecords, ids, this::retrievePickedHURecords);
	}

	private void addToCache(@NonNull final I_DD_Order_MoveSchedule record, @Nullable List<I_DD_OrderLine_HU_Candidate> pickedHUsRecords)
	{
		final DDOrderMoveScheduleId id = DDOrderMoveScheduleId.ofRepoId(record.getDD_Order_MoveSchedule_ID());
		this.recordsById.put(id, record);
		this.pickedHUsRecords.put(id, pickedHUsRecords != null && !pickedHUsRecords.isEmpty() ? ImmutableList.copyOf(pickedHUsRecords) : ImmutableList.of());
	}

	private List<I_DD_OrderLine_HU_Candidate> getPickedHURecords(final @NotNull DDOrderMoveScheduleId id)
	{
		return CollectionUtils.getAllOrLoadReturningMap(pickedHUsRecords, ImmutableSet.of(id), this::retrievePickedHURecords)
				.get(id);
	}

	@NonNull
	private I_DD_Order_MoveSchedule getRecordById(final @NotNull DDOrderMoveScheduleId id)
	{
		return CollectionUtils.getOrLoad(recordsById, id, this::retrieveRecords);
	}

	private Collection<I_DD_Order_MoveSchedule> getRecordByIds(final @NotNull Set<DDOrderMoveScheduleId> ids)
	{
		return CollectionUtils.getAllOrLoad(recordsById, ids, this::retrieveRecords);
	}

	private Map<DDOrderMoveScheduleId, I_DD_Order_MoveSchedule> retrieveRecords(@NonNull final Set<DDOrderMoveScheduleId> ids)
	{
		return queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addInArrayFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_MoveSchedule_ID, ids)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> DDOrderMoveScheduleId.ofRepoId(record.getDD_Order_MoveSchedule_ID()),
						record -> record
				));
	}

	private Map<DDOrderMoveScheduleId, List<I_DD_OrderLine_HU_Candidate>> retrievePickedHURecords(@NonNull final Set<DDOrderMoveScheduleId> ids)
	{
		final ImmutableListMultimap<DDOrderMoveScheduleId, I_DD_OrderLine_HU_Candidate> byScheduleId = queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addInArrayFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_Order_MoveSchedule_ID, ids)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> DDOrderMoveScheduleId.ofRepoId(record.getDD_Order_MoveSchedule_ID()),
						record -> record
				));

		final HashMap<DDOrderMoveScheduleId, List<I_DD_OrderLine_HU_Candidate>> result = new HashMap<>();
		ids.forEach(scheduleId -> result.put(scheduleId, byScheduleId.get(scheduleId)));
		return result;
	}

	public DDOrderMoveSchedule loadById(@NonNull final DDOrderMoveScheduleId id)
	{
		warmUpById(id);
		return fromRecord(getRecordById(id));
	}

	public ImmutableList<DDOrderMoveSchedule> loadByIds(@NonNull final Set<DDOrderMoveScheduleId> ids)
	{
		if (ids.isEmpty()) {return ImmutableList.of();}

		warmUpByIds(ids);

		return getRecordByIds(ids)
				.stream()
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableList<DDOrderMoveSchedule> loadByDDOrderId(@NonNull final DDOrderId ddOrderId)
	{
		final ImmutableMap<DDOrderMoveScheduleId, I_DD_Order_MoveSchedule> records = queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_ID, ddOrderId)
				.orderBy(I_DD_Order_MoveSchedule.COLUMNNAME_DD_OrderLine_ID)
				.orderBy(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_MoveSchedule_ID)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> DDOrderMoveScheduleId.ofRepoId(record.getDD_Order_MoveSchedule_ID()),
						record -> record
				));

		this.recordsById.putAll(records);
		CollectionUtils.getAllOrLoad(pickedHUsRecords, records.keySet(), this::retrievePickedHURecords);

		return loadByIds(records.keySet());
	}

	private DDOrderMoveSchedule fromRecord(@NonNull final I_DD_Order_MoveSchedule record)
	{
		return fromRecord(record, getPickedHURecords(DDOrderMoveScheduleId.ofRepoId(record.getDD_Order_MoveSchedule_ID())));
	}

	private static DDOrderMoveSchedule fromRecord(
			@NonNull final I_DD_Order_MoveSchedule record,
			@NonNull final List<I_DD_OrderLine_HU_Candidate> pickedHUsRecordsMap)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		final DDOrderMoveScheduleId id = DDOrderMoveScheduleId.ofRepoId(record.getDD_Order_MoveSchedule_ID());

		return DDOrderMoveSchedule.builder()
				.id(id)
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
				.pickedHUs(fromRecords(pickedHUsRecordsMap))
				//
				.build();
	}

	private static DDOrderMoveSchedulePickedHUs fromRecords(@NonNull final List<I_DD_OrderLine_HU_Candidate> pickedHUsRecords)
	{
		if (pickedHUsRecords.isEmpty())
		{
			return null;
		}

		return pickedHUsRecords.stream()
				.map(DDOrderMoveScheduleLoaderAndSaver::fromRecord)
				.collect(DDOrderMoveSchedulePickedHUs.collect());

	}

	private static DDOrderMoveSchedulePickedHU fromRecord(@NonNull final I_DD_OrderLine_HU_Candidate record)
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
				.dropToLocatorId(LocatorId.ofRepoId(record.getDropTo_Warehouse_ID(), record.getDropTo_Locator_ID()))
				//
				.build();
	}

	public void save(@NonNull final DDOrderMoveSchedule schedule)
	{
		final I_DD_Order_MoveSchedule record = getRecordById(schedule.getId());

		final HashMap<HuId, I_DD_OrderLine_HU_Candidate> pickedHURecords = getPickedHURecords(schedule.getId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(pickedHURecord -> HuId.ofRepoId(pickedHURecord.getM_HU_ID())));

		updateRecord(record, schedule);
		InterfaceWrapperHelper.saveRecord(record);

		final DDOrderMoveSchedulePickedHUs pickedHUs = schedule.getPickedHUs();
		if (pickedHUs != null)
		{
			for (final DDOrderMoveSchedulePickedHU pickedHU : pickedHUs.toCollection())
			{
				final I_DD_OrderLine_HU_Candidate pickedHURecordExisting = pickedHURecords.remove(pickedHU.getActualHUIdPicked());
				final I_DD_OrderLine_HU_Candidate pickedHURecord = pickedHURecordExisting == null
						? InterfaceWrapperHelper.newInstance(I_DD_OrderLine_HU_Candidate.class)
						: pickedHURecordExisting;
				updateRecord(pickedHURecord, pickedHU, schedule);
				InterfaceWrapperHelper.saveRecord(pickedHURecord);
			}
		}

		InterfaceWrapperHelper.deleteAll(pickedHURecords.values());
	}

	private static void updateRecord(@NonNull final I_DD_Order_MoveSchedule record, @NotNull final DDOrderMoveSchedule from)
	{
		record.setStatus(from.getStatus().getCode());
		record.setQtyPicked(from.getQtyPicked().toBigDecimal());
		record.setRejectReason(QtyRejectedReasonCode.toCode(from.getQtyNotPickedReason()));
	}

	private static void updateRecord(
			@NonNull final I_DD_OrderLine_HU_Candidate record,
			@NonNull final DDOrderMoveSchedulePickedHU from,
			@NonNull final DDOrderMoveSchedule schedule)
	{
		record.setDD_Order_MoveSchedule_ID(schedule.getId().getRepoId());
		record.setDD_Order_ID(schedule.getDdOrderId().getRepoId());
		record.setDD_OrderLine_ID(schedule.getDdOrderLineId().getRepoId());

		record.setStatus(schedule.getStatus().getCode());
		record.setM_Product_ID(schedule.getProductId().getRepoId());

		//
		// Pick From
		record.setPickFrom_Warehouse_ID(schedule.getPickFromLocatorId().getWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(schedule.getPickFromLocatorId().getRepoId());
		record.setPickFrom_HU_ID(schedule.getPickFromHUId().getRepoId());
		//
		record.setM_HU_ID(HuId.toRepoId(from.getActualHUIdPicked()));
		record.setIsPickWholeHU(schedule.isPickWholeHU());
		record.setC_UOM_ID(from.getQtyPicked().getUomId().getRepoId());
		record.setQtyPicked(from.getQtyPicked().toBigDecimal());
		record.setRejectReason(QtyRejectedReasonCode.toCode(schedule.getQtyNotPickedReason()));
		record.setPickFrom_Movement_ID(MovementId.toRepoId(from.getPickFromMovementId()));
		record.setInTransit_Warehouse_ID(from.getInTransitLocatorId() != null ? from.getInTransitLocatorId().getWarehouseId().getRepoId() : -1);
		record.setInTransit_Locator_ID(from.getInTransitLocatorId() != null ? from.getInTransitLocatorId().getRepoId() : -1);

		//
		// Drop To Status
		{
			record.setDropTo_Movement_ID(MovementId.toRepoId(from.getDropToMovementId()));

			final LocatorId dropToLocatorId = from.getDropToLocatorId();
			record.setDropTo_Warehouse_ID(WarehouseId.toRepoId(dropToLocatorId.getWarehouseId()));
			record.setDropTo_Locator_ID(LocatorId.toRepoId(dropToLocatorId));
		}
	}

	public ImmutableList<DDOrderMoveSchedule> createSchedulesToMove(@NonNull final List<DDOrderMoveScheduleCreateRequest> requests)
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
		InterfaceWrapperHelper.saveRecord(record);
		addToCache(record, ImmutableList.of());

		return fromRecord(record);
	}

	public DDOrderMoveSchedule updateById(final DDOrderMoveScheduleId id, Consumer<DDOrderMoveSchedule> updater)
	{
		warmUpById(id);
		final I_DD_Order_MoveSchedule record = getRecordById(id);
		final DDOrderMoveSchedule schedule = fromRecord(record);
		updater.accept(schedule);
		save(schedule);

		return schedule;
	}

	public List<DDOrderMoveSchedule> updateByIds(final Set<DDOrderMoveScheduleId> ids, Consumer<DDOrderMoveSchedule> updater)
	{
		warmUpByIds(ids);

		ImmutableList.Builder<DDOrderMoveSchedule> result = ImmutableList.builder();

		ids.forEach(id -> {
			final I_DD_Order_MoveSchedule record = getRecordById(id);
			final DDOrderMoveSchedule schedule = fromRecord(record);
			updater.accept(schedule);
			save(schedule);
			result.add(schedule);
		});

		return result.build();
	}

}
