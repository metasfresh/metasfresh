package de.metas.frontend_testing.masterdata.shipment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Generates shipments from a sales order's shipment schedules via the REAL
 * {@code ShipmentService.generateShipmentsForScheduleIds} path — the faithful equivalent of the
 * desktop "Generate Shipments" process. With quantity type Picked it runs the
 * {@code GenerateInOutFromShipmentSchedules} workpackage over the schedule's already-picked qty; when
 * no picked qty survives it generates no shipment (the "can't recreate shipment after void" symptom).
 *
 * <p>Newly-created shipments are detected via a before/after snapshot of the order's {@code M_InOut}
 * ids, so the response reports exactly the shipments this call produced (empty == nothing recreatable).
 */
@Builder
public class GenerateShipmentsCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final JsonGenerateShipmentsRequest request;

	public JsonGenerateShipmentsResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonGenerateShipmentsResponse execute0()
	{
		final OrderId orderId = OrderId.ofRepoId(Integer.parseInt(request.getSalesOrderId()));

		final ImmutableSet<ShipmentScheduleId> scheduleIds = retrieveScheduleIds(orderId);

		final M_ShipmentSchedule_QuantityTypeToUse quantityType = request.getQuantityType() != null
				? M_ShipmentSchedule_QuantityTypeToUse.ofCode(request.getQuantityType())
				: M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY;
		final boolean complete = request.getComplete() == null || request.getComplete();

		final Set<Integer> inOutIdsBefore = retrieveInOutIds(orderId);

		ShipmentService.getInstance().generateShipmentsForScheduleIds(
				GenerateShipmentsForSchedulesRequest.builder()
						.shipmentScheduleIds(scheduleIds)
						.quantityTypeToUse(quantityType)
						.isCompleteShipment(complete)
						.waitForShipments(true)
						.build());

		final Set<Integer> inOutIdsAfter = retrieveInOutIds(orderId);

		final ImmutableList<String> newShipmentIds = inOutIdsAfter.stream()
				.filter(id -> !inOutIdsBefore.contains(id))
				.map(String::valueOf)
				.collect(ImmutableList.toImmutableList());

		return JsonGenerateShipmentsResponse.builder()
				.newShipmentIds(newShipmentIds)
				.newShipmentCount(newShipmentIds.size())
				.build();
	}

	private ImmutableSet<ShipmentScheduleId> retrieveScheduleIds(@NonNull final OrderId orderId)
	{
		final Set<OrderLineId> orderLineIds = orderDAO.retrieveOrderLines(orderId).stream()
				.map(I_C_OrderLine::getC_OrderLine_ID)
				.map(OrderLineId::ofRepoId)
				.collect(Collectors.toSet());

		return shipmentSchedulePA.getByOrderLineIds(orderLineIds).stream()
				.map(I_M_ShipmentSchedule::getM_ShipmentSchedule_ID)
				.map(ShipmentScheduleId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private Set<Integer> retrieveInOutIds(@NonNull final OrderId orderId)
	{
		return ImmutableSet.copyOf(queryBL.createQueryBuilder(org.compiere.model.I_M_InOut.class)
				.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.listIds());
	}
}
