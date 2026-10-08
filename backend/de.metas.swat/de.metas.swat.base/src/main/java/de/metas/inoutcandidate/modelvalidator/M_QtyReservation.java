package de.metas.inoutcandidate.modelvalidator;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_QtyReservation;
import org.compiere.model.ModelValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Invalidates shipment schedules when qty reservations are created or deleted,
 * so that the shipment schedule recomputation picks up the changed reserved quantities.
 */
@Validator(I_M_QtyReservation.class)
@Component
@RequiredArgsConstructor
public class M_QtyReservation
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_BEFORE_DELETE })
	public void invalidateShipmentSchedules(@NonNull final I_M_QtyReservation record)
	{
		final IShipmentScheduleSegment segment = extractInvalidationSegment(record);

		trxManager.accumulateAndProcessAfterCommit(
				"M_QtyReservation.invalidateShipmentSchedules",
				ImmutableList.of(segment),
				shipmentScheduleInvalidateBL::notifySegmentsChanged
		);
	}

	private static IShipmentScheduleSegment extractInvalidationSegment(final @NotNull I_M_QtyReservation record)
	{
		return ShipmentScheduleSegments.builder()
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.anyBPartnerId()
				.build();
	}
}
