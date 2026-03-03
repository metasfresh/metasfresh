package de.metas.inoutcandidate.modelvalidator;

import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_QtyReservation;
import org.compiere.model.ModelValidator;

import java.util.Collections;

/**
 * Invalidates shipment schedules when qty reservations are created or deleted,
 * so that the shipment schedule recomputation picks up the changed reserved quantities.
 */
@Validator(I_M_QtyReservation.class)
public class M_QtyReservation
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL = Services.get(IShipmentScheduleInvalidateBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_DELETE })
	public void invalidateShipmentSchedules(@NonNull final I_M_QtyReservation record)
	{
		final IShipmentScheduleSegment segment = ShipmentScheduleSegments.builder()
				.productId(record.getM_Product_ID())
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.bpartnerId(0) // ANY — reservation affects all partners for this product/warehouse
				.build();

		trxManager.runAfterCommit(() -> shipmentScheduleInvalidateBL.notifySegmentsChanged(Collections.singletonList(segment)));
	}
}
