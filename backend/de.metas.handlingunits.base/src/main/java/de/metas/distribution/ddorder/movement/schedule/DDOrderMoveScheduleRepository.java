package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_DD_OrderLine_HU_Candidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.mmovement.MovementAndLineId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public class DDOrderMoveScheduleRepository
{
	private static final IQueryBL queryBL = Services.get(IQueryBL.class);

	public DDOrderMoveSchedule getById(@NonNull final DDOrderMoveScheduleId id)
	{
		final I_DD_OrderLine_HU_Candidate record = InterfaceWrapperHelper.load(id, I_DD_OrderLine_HU_Candidate.class);
		return toDDOrderMoveSchedule(record);
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
		final I_DD_OrderLine_HU_Candidate record = InterfaceWrapperHelper.newInstance(I_DD_OrderLine_HU_Candidate.class);
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
		return toDDOrderMoveSchedule(record);
	}

	/**
	 * @implNote pls keep in sync with {@link #save(DDOrderMoveSchedule)}
	 */
	private static DDOrderMoveSchedule toDDOrderMoveSchedule(@NonNull final I_DD_OrderLine_HU_Candidate record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return DDOrderMoveSchedule.builder()
				.id(DDOrderMoveScheduleId.ofRepoId(record.getDD_OrderLine_HU_Candidate_ID()))
				.ddOrderId(DDOrderId.ofRepoId(record.getDD_Order_ID()))
				.ddOrderLineId(DDOrderLineId.ofRepoId(record.getDD_OrderLine_ID()))
				.status(DDOrderMoveScheduleStatus.ofCode(record.getStatus()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				//
				// Pick From
				.pickFromLocatorId(LocatorId.ofRepoId(record.getPickFrom_Warehouse_ID(), record.getPickFrom_Locator_ID()))
				.pickFromHUId(HuId.ofRepoId(record.getPickFrom_HU_ID()))
				.actualHUIdPicked(HuId.ofRepoIdOrNull(record.getM_HU_ID()))
				.qtyToPick(Quantitys.create(record.getQtyToPick(), uomId))
				.isPickWholeHU(record.isPickWholeHU())
				.qtyPicked(Quantitys.create(record.getQtyPicked(), uomId))
				.qtyNotPickedReason(QtyRejectedReasonCode.ofNullableCode(record.getRejectReason()).orElse(null))
				.pickFromMovementLineId(MovementAndLineId.ofRepoIdsOrNull(record.getPickFrom_Movement_ID(), record.getPickFrom_MovementLine_ID()))
				.inTransitLocatorId(LocatorId.ofRepoIdOrNull(record.getInTransit_Warehouse_ID(), record.getInTransit_Locator_ID()))
				//
				// Drop To
				.dropToLocatorId(LocatorId.ofRepoId(record.getDropTo_Warehouse_ID(), record.getDropTo_Locator_ID()))
				.dropToMovementLineId(MovementAndLineId.ofRepoIdsOrNull(record.getDropTo_Movement_ID(), record.getDropTo_MovementLine_ID()))
				//
				.build();
	}

	/**
	 * @implNote pls keep in sync with {@link #toDDOrderMoveSchedule(I_DD_OrderLine_HU_Candidate)}
	 */
	public void save(@NonNull final DDOrderMoveSchedule schedule)
	{
		final I_DD_OrderLine_HU_Candidate record = InterfaceWrapperHelper.load(schedule.getId(), I_DD_OrderLine_HU_Candidate.class);
		record.setStatus(schedule.getStatus().getCode());

		//
		// Pick From Status
		record.setM_HU_ID(HuId.toRepoId(schedule.getActualHUIdPicked()));
		record.setIsPickWholeHU(schedule.isPickWholeHU());
		record.setQtyPicked(schedule.getQtyPicked().toBigDecimal());
		record.setRejectReason(QtyRejectedReasonCode.toCode(schedule.getQtyNotPickedReason()));
		record.setPickFrom_Movement_ID(schedule.getPickFromMovementLineId() != null ? schedule.getPickFromMovementLineId().getMovementId().getRepoId() : -1);
		record.setPickFrom_MovementLine_ID(schedule.getPickFromMovementLineId() != null ? schedule.getPickFromMovementLineId().getMovementLineId().getRepoId() : -1);
		record.setInTransit_Warehouse_ID(schedule.getInTransitLocatorId() != null ? schedule.getInTransitLocatorId().getWarehouseId().getRepoId() : -1);
		record.setInTransit_Locator_ID(schedule.getInTransitLocatorId() != null ? schedule.getInTransitLocatorId().getRepoId() : -1);

		//
		// Drop To Status
		record.setDropTo_Movement_ID(schedule.getDropToMovementLineId() != null ? schedule.getDropToMovementLineId().getMovementId().getRepoId() : -1);
		record.setDropTo_MovementLine_ID(schedule.getDropToMovementLineId() != null ? schedule.getDropToMovementLineId().getMovementLineId().getRepoId() : -1);

		InterfaceWrapperHelper.save(record);
	}

	public final List<HuId> retrieveHUIdsScheduledButNotMovedYet(@NonNull final DDOrderLineId ddOrderLineId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED)
				.orderBy(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_HU_Candidate_ID)
				.create()
				.listDistinct(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID, HuId.class);
	}

	public void removeNotStarted(@NonNull final DDOrderLineId ddOrderLineId)
	{
		queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED)
				.create()
				.delete();
	}

	public void removeNotStarted(@NonNull final DDOrderId ddOrderId)
	{
		queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_Order_ID, ddOrderId)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED)
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
		return queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_Order_ID, ddOrderId)
				.orderBy(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_ID)
				.orderBy(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_HU_Candidate_ID)
				.create()
				.stream()
				.map(DDOrderMoveScheduleRepository::toDDOrderMoveSchedule)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean hasInProgressSchedules(@NonNull final DDOrderLineId ddOrderLineId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_Status, DDOrderMoveScheduleStatus.IN_PROGRESS)
				.create()
				.anyMatch();
	}

	public boolean hasInProgressSchedules(final DDOrderId ddOrderId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_Order_ID, ddOrderId)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_Status, DDOrderMoveScheduleStatus.IN_PROGRESS)
				.create()
				.anyMatch();
	}
}
