package de.metas.frontend_testing.masterdata.shipment;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleAndJobSchedulesCollection;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
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
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonShipmentCreateResponse execute0()
	{
		final OrderId orderId = context.getId(request.getSalesOrder(), OrderId.class);

		// 1. Poll for shipment schedules (they are created asynchronously after order completion)
		final List<I_M_ShipmentSchedule> shipmentSchedules = pollForShipmentSchedules(orderId);
		if (shipmentSchedules.isEmpty())
		{
			throw new AdempiereException("No shipment schedules found for order " + orderId);
		}

		// 2. Prepare candidates and generate the shipment
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

	private List<I_M_ShipmentSchedule> pollForShipmentSchedules(final OrderId orderId)
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

		final Stopwatch stopwatch = Stopwatch.createStarted();

		while (true)
		{
			final List<I_M_ShipmentSchedule> schedules = shipmentSchedulePA.getByOrderLineIds(orderLineIds);
			if (!schedules.isEmpty())
			{
				logger.info("Found {} shipment schedules for order {} after {}", schedules.size(), orderId, stopwatch);
				return schedules;
			}

			if (stopwatch.elapsed().compareTo(SHIPMENT_SCHEDULE_POLL_TIMEOUT) > 0)
			{
				throw new AdempiereException("Timeout waiting for shipment schedules for order " + orderId + ". Waited " + stopwatch.elapsed());
			}

			logger.info("No shipment schedules yet for order {}. Sleeping... (elapsed: {})", orderId, stopwatch);
			sleep(SHIPMENT_SCHEDULE_POLL_SLEEP);
		}
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
