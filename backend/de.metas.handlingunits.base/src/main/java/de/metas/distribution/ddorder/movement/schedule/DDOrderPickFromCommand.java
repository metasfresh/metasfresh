package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.generate.DDOrderMovementHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.movement.generate.HUMovementGenerator;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.MovementId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.model.I_DD_Order;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

class DDOrderPickFromCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	@NonNull private final DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository;

	// Params
	@NonNull private final Instant movementDate = SystemTime.asInstant();
	@NonNull private final DDOrderMoveScheduleId scheduleId;
	@NonNull private final Quantity qtyPicked;
	@Nullable private final QtyRejectedReasonCode qtyNotPickedReason;

	// State
	private DDOrderMoveSchedule schedule;
	private I_DD_Order ddOrder;
	private LocatorId inTransitLocatorId;

	@Builder
	private DDOrderPickFromCommand(
			final @NonNull DDOrderLowLevelDAO ddOrderLowLevelDAO,
			final @NonNull DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository,
			final @NonNull IHandlingUnitsBL handlingUnitsBL,
			//
			final @NonNull DDOrderPickFromRequest request)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
		this.ddOrderMoveScheduleRepository = ddOrderMoveScheduleRepository;
		this.handlingUnitsBL = handlingUnitsBL;

		this.scheduleId = request.getScheduleId();
		this.qtyPicked = request.getQtyPicked();
		this.qtyNotPickedReason = request.getQtyNotPickedReason();
	}

	public DDOrderMoveSchedule execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private DDOrderMoveSchedule executeInTrx()
	{
		trxManager.assertThreadInheritedTrxExists();

		//
		// Load
		schedule = ddOrderMoveScheduleRepository.getById(scheduleId);
		ddOrder = ddOrderLowLevelDAO.getById(schedule.getDdOrderId());
		final WarehouseId warehouseInTransitId = WarehouseId.ofRepoId(ddOrder.getM_Warehouse_ID());
		inTransitLocatorId = warehouseBL.getDefaultLocatorId(warehouseInTransitId);

		//
		if (schedule.isPickedFrom())
		{
			throw new AdempiereException("Already picked");
		}

		//
		// Extract the HU if needed
		final List<I_M_HU> actualPickedHURecords = splitOutOfPickFromHU(schedule);

		//
		// generate movement Pick From Locator -> InTransit
		final MovementId pickFromMovementId = createPickFromMovement(extractHUIds(actualPickedHURecords));

		final DDOrderMoveSchedulePickedHUs pickedHUs = actualPickedHURecords.stream()
				.map(actualPickedHU -> DDOrderMoveSchedulePickedHU.builder()
						.actualHUIdPicked(HuId.ofRepoId(actualPickedHU.getM_HU_ID()))
						.qtyPicked(handlingUnitsBL.getStorageFactory()
								.getStorage(actualPickedHU)
								.getQuantity(schedule.getProductId(), qtyPicked.getUOM()))
						.pickFromMovementId(pickFromMovementId)
						.inTransitLocatorId(inTransitLocatorId)
						.build())
				.collect(DDOrderMoveSchedulePickedHUs.collect());

		//
		// update the schedule
		schedule.markAsPickedFrom(qtyNotPickedReason, pickedHUs);
		ddOrderMoveScheduleRepository.save(schedule);

		return schedule;
	}

	private static ImmutableSet<HuId> extractHUIds(final List<I_M_HU> hus)
	{
		return hus.stream().map(hu -> HuId.ofRepoId(hu.getM_HU_ID())).collect(ImmutableSet.toImmutableSet());
	}

	private List<I_M_HU> splitOutOfPickFromHU(final DDOrderMoveSchedule schedule)
	{
		final HuId pickFromHUId = schedule.getPickFromHUId();
		final ProductId productId = schedule.getProductId();
		final I_M_HU pickFromHU = handlingUnitsBL.getById(pickFromHUId);

		final Quantity pickFromHU_TotalQty = handlingUnitsBL.getStorageFactory()
				.getStorage(pickFromHU)
				.getQuantity(productId, qtyPicked.getUOM());

		if (pickFromHU_TotalQty.qtyAndUomCompareToEquals(qtyPicked))
		{
			return ImmutableList.of(pickFromHU);
		}
		else
		{
			return HUTransformService.newInstance()
					.husToNewCUs(HUTransformService.HUsToNewCUsRequest.builder()
							.sourceHU(pickFromHU)
							.productId(productId)
							.qtyCU(qtyPicked)
							.keepNewCUsUnderSameParent(false)
							.reservedVHUsPolicy(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED)
							.build());
		}
	}

	private MovementId createPickFromMovement(@NonNull final Set<HuId> huIdsToMove)
	{
		final HUMovementGenerateRequest request = DDOrderMovementHelper.prepareMovementGenerateRequest(ddOrder, schedule.getDdOrderLineId())
				.movementDate(movementDate)
				.fromLocatorId(schedule.getPickFromLocatorId())
				.toLocatorId(inTransitLocatorId)
				.huIdsToMove(huIdsToMove)
				.build();

		return new HUMovementGenerator(request)
				.createMovement()
				.getSingleMovementLineId()
				.getMovementId();
	}
}
