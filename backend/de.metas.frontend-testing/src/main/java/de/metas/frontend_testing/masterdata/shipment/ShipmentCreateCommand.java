package de.metas.frontend_testing.masterdata.shipment;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleAndJobSchedulesCollection;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
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
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

	private static final Duration SHIPMENT_SCHEDULE_POLL_TIMEOUT = Duration.ofSeconds(60);
	private static final Duration SHIPMENT_SCHEDULE_POLL_SLEEP = Duration.ofMillis(1000);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonShipmentCreateRequest request;

	public JsonShipmentCreateResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonShipmentCreateResponse execute0()
	{
		final OrderId orderId = context.getId(request.getSalesOrder(), OrderId.class);

		final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse = request.getQuantityType() != null
				? M_ShipmentSchedule_QuantityTypeToUse.ofCode(request.getQuantityType())
				: M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER;

		// 1. Wait for the shipment schedules (they are created and recomputed asynchronously after order completion)
		final List<I_M_ShipmentSchedule> shipmentSchedules = awaitShipmentSchedules(orderId, quantityTypeToUse);

		// 2. Prepare candidates and generate the shipment
		final ShipmentScheduleWithHUService shipmentScheduleWithHUService = SpringContextHolder.instance.getBean(ShipmentScheduleWithHUService.class);

		final List<ShipmentScheduleWithHU> candidates = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				PrepareForShipmentSchedulesRequest.builder()
						.schedules(ShipmentScheduleAndJobSchedulesCollection.ofShipmentSchedules(shipmentSchedules))
						.quantityTypeToUse(quantityTypeToUse)
						.build());

		if (candidates.isEmpty())
		{
			throw new AdempiereException("No candidates found for shipment schedules of order " + orderId);
		}

		// complete defaults to true (preserving the pre-existing behaviour: completed shipment).
		// complete=false leaves the M_InOut as a DRAFT (DocStatus='DR', Processed='N').
		final boolean complete = CoalesceUtil.coalesce(request.getComplete(), Boolean.TRUE);

		final InOutGenerateResult result = huShipmentScheduleBL.createInOutProducerFromShipmentSchedule()
				.setProcessShipments(complete)
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

	private List<I_M_ShipmentSchedule> awaitShipmentSchedules(
			@NonNull final OrderId orderId,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse)
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
				// shipping the QtyToDeliver needs the recomputed QtyToDeliver
				.requirePositiveQtyToDeliver(quantityTypeToUse == M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
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
