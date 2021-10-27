package de.metas.distribution.ddorder.movement.schedule;

import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.generate.DDOrderMovementHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
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
import org.adempiere.mmovement.MovementAndLineId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.model.I_DD_Order;

import javax.annotation.Nullable;
import java.time.Instant;

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
		if (schedule.getPickFromMovementLineId() != null
				|| schedule.getActualHUIdPicked() != null)
		{
			throw new AdempiereException("Already picked");
		}

		//
		// Extract the HU if needed
		final HuId actualPickedHUId = splitOutOfPickFromHU(schedule);

		//
		// generate movement Pick From Locator -> InTransit
		final MovementAndLineId pickFromMovementLineId = createPickFromMovement(actualPickedHUId);

		//
		// update the schedule
		schedule.markAsPickedFrom(qtyPicked, qtyNotPickedReason, actualPickedHUId, pickFromMovementLineId, inTransitLocatorId);
		ddOrderMoveScheduleRepository.save(schedule);

		return schedule;
	}

	private HuId splitOutOfPickFromHU(final DDOrderMoveSchedule schedule)
	{
		final HuId pickFromHUId = schedule.getPickFromHUId();
		final ProductId productId = schedule.getProductId();
		final I_M_HU pickFromHU = handlingUnitsBL.getById(pickFromHUId);

		final Quantity pickFromHU_TotalQty = handlingUnitsBL.getStorageFactory()
				.getStorage(pickFromHU)
				.getQuantity(productId, qtyPicked.getUOM());

		if (pickFromHU_TotalQty.qtyAndUomCompareToEquals(qtyPicked))
		{
			return pickFromHUId;
		}
		else
		{
			final I_M_HU newCU = HUTransformService.newInstance()
					.huToNewSingleCU(HUTransformService.HUsToNewCUsRequest.builder()
							.sourceHU(pickFromHU)
							.productId(productId)
							.qtyCU(qtyPicked)
							.keepNewCUsUnderSameParent(false)
							.onlyFromUnreservedHUs(true)
							.build());

			return HuId.ofRepoId(newCU.getM_HU_ID());
		}
	}

	private MovementAndLineId createPickFromMovement(@NonNull final HuId huIdToMove)
	{
		final HUMovementGenerateRequest request = DDOrderMovementHelper.prepareMovementGenerateRequest(ddOrder, schedule.getDdOrderLineId())
				.movementDate(movementDate)
				.fromLocatorId(schedule.getPickFromLocatorId())
				.toLocatorId(inTransitLocatorId)
				.huIdToMove(huIdToMove)
				.build();

		return new HUMovementGenerator(request)
				.createMovement()
				.getSingleMovementLineId();
	}
}
