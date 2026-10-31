package de.metas.frontend_testing.masterdata.shipment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleAndJobSchedulesCollection;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import de.metas.inout.model.I_M_InOut;
import de.metas.interfaces.I_C_OrderLine;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;

@Builder
public class ShipmentCreateCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(ShipmentCreateCommand.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

	private static final Duration SHIPMENT_SCHEDULE_POLL_TIMEOUT = Duration.ofSeconds(60);
	private static final Duration SHIPMENT_SCHEDULE_POLL_SLEEP = Duration.ofMillis(1000);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonShipmentCreateRequest request;

	public JsonShipmentCreateResponse execute()
	{
		final OrderId orderId = context.getId(request.getSalesOrder(), OrderId.class);

		// 1. Wait for the shipment schedules (they are created and recomputed asynchronously after order completion).
		// Wait OUTSIDE of a transaction: a transaction keeps the locks of its reads (e.g. on C_OrderLine) while it polls.
		// A DDL statement waiting for such a lock (seen: "ALTER TABLE C_OrderLine ... SET NOT NULL" on a freshly started stack)
		// then blocks every later reader of that table, including the recompute we are waiting for, until we give up.
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = awaitShipmentSchedules(orderId).stream()
				.map(schedule -> ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()))
				.collect(ImmutableSet.toImmutableSet());

		// 2. Create the shipment
		return trxManager.callInThreadInheritedTrx(() -> createShipment(orderId, shipmentScheduleIds));
	}

	private JsonShipmentCreateResponse createShipment(
			@NonNull final OrderId orderId,
			@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds)
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = ImmutableList.copyOf(shipmentSchedulePA.getByIds(shipmentScheduleIds).values());

		// Prepare candidates and generate the shipment
		final ShipmentScheduleWithHUService shipmentScheduleWithHUService = SpringContextHolder.instance.getBean(ShipmentScheduleWithHUService.class);

		final List<ShipmentScheduleWithHU> candidates = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				PrepareForShipmentSchedulesRequest.builder()
						.schedules(ShipmentScheduleAndJobSchedulesCollection.ofShipmentSchedules(shipmentSchedules))
						.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
						.build());

		if (candidates.isEmpty())
		{
			throw new AdempiereException("No candidates found for shipment schedules of order " + orderId);
		}

		final InOutGenerateResult result = huShipmentScheduleBL.createInOutProducerFromShipmentSchedule()
				.setProcessShipments(true)
				.computeShipmentDate(CalculateShippingDateRule.FORCE_SHIPMENT_DATE_TODAY)
				.createShipments(candidates);

		final List<I_M_InOut> shipments = result.getInOuts();
		if (shipments.isEmpty())
		{
			throw new AdempiereException("No shipments were created for order " + orderId);
		}

		if (shipments.size() > 1)
		{
			logger.warn("Multiple shipments created ({}) for order {}; returning first one only", shipments.size(), orderId);
		}

		final I_M_InOut shipment = shipments.get(0);
		return JsonShipmentCreateResponse.builder()
				.id(String.valueOf(shipment.getM_InOut_ID()))
				.documentNo(shipment.getDocumentNo())
				.build();
	}

	private List<I_M_ShipmentSchedule> awaitShipmentSchedules(@NonNull final OrderId orderId)
	{
		// Get order line IDs for this order
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderId);
		final ImmutableSet<OrderLineId> orderLineIds = orderLines.stream()
				.map(ol -> OrderLineId.ofRepoId(ol.getC_OrderLine_ID()))
				.collect(ImmutableSet.toImmutableSet());

		if (orderLineIds.isEmpty())
		{
			throw new AdempiereException("No order lines found for order " + orderId);
		}

		return ShipmentSchedulesAwaiter.builder()
				.orderId(orderId)
				.orderLineIds(orderLineIds)
				// the shipment ships the QtyToDeliver, so it needs the recomputed QtyToDeliver
				.requirePositiveQtyToDeliver(true)
				.timeout(SHIPMENT_SCHEDULE_POLL_TIMEOUT)
				.pollInterval(SHIPMENT_SCHEDULE_POLL_SLEEP)
				.sleeper(ShipmentCreateCommand::sleep)
				.build()
				.await();
	}

	private static void sleep(@NonNull final Duration duration)
	{
		try
		{
			Thread.sleep(duration.toMillis());
		}
		catch (final InterruptedException e)
		{
			throw new AdempiereException(e);
		}
	}
}
