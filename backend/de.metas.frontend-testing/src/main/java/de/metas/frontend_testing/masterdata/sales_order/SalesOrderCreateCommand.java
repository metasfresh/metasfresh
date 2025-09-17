package de.metas.frontend_testing.masterdata.sales_order;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.order.OrderFactory;
import de.metas.order.OrderLineBuilder;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Builder
public class SalesOrderCreateCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(SalesOrderCreateCommand.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	@NonNull private final PickingJobScheduleService pickingJobScheduleService;

	@NonNull private final JsonSalesOrderCreateRequest request;
	@NonNull private final MasterdataContext context;

	//
	// State
	private transient OrderFactory salesOrderFactory;
	private final ArrayList<LineCreateRequestAndBuilder> lineCreateRequestAndBuilders = new ArrayList<>();

	private static Duration JOB_SCHEDULE_CREATE_TIMEOUT = Duration.ofSeconds(30);
	private static Duration JOB_SCHEDULE_CREATE_SLEEP_BETWEEN = Duration.ofMillis(1000);

	public JsonSalesOrderCreateResponse execute()
	{
		final JsonSalesOrderCreateResponse response = trxManager.callInThreadInheritedTrx(this::execute0);
		trxManager.runInThreadInheritedTrx(this::createSchedules);
		return response;
	}

	private JsonSalesOrderCreateResponse execute0()
	{
		final BPartnerLocationId shipBPartnerLocationId = request.getLocation() != null ? context.getId(request.getLocation(), BPartnerLocationId.class) : null;
		final BPartnerId shipBPartnerId = CoalesceUtil.coalesceSuppliers(
				() -> request.getBpartner() != null ? context.getId(request.getBpartner(), BPartnerId.class) : null,
				() -> shipBPartnerLocationId != null ? shipBPartnerLocationId.getBpartnerId() : null
		);
		if (shipBPartnerId == null)
		{
			throw new AdempiereException("At least one of bpartner or location shall be set");
		}
		if (shipBPartnerLocationId != null && !BPartnerId.equals(shipBPartnerId, shipBPartnerLocationId.getBpartnerId()))
		{
			throw new AdempiereException("Partner and location shall match")
					.setParameter("shipBPartnerId", shipBPartnerId)
					.setParameter("shipBPartnerLocationId", shipBPartnerLocationId)
					.setParameter("request", request);
		}

		this.salesOrderFactory = OrderFactory.newSalesOrder()
				.shipBPartner(shipBPartnerId, shipBPartnerLocationId, null)
				.warehouseId(context.getId(request.getWarehouse(), WarehouseId.class))
				.datePromised(request.getDatePromised());
		request.getLines().forEach(this::createOrderLine);

		final I_C_Order salesOrderRecord = salesOrderFactory.createAndComplete();

		return JsonSalesOrderCreateResponse.builder()
				.id(String.valueOf(salesOrderRecord.getC_Order_ID()))
				.documentNo(salesOrderRecord.getDocumentNo())
				.build();
	}

	private void createOrderLine(final JsonSalesOrderCreateRequest.Line salesOrderLine)
	{
		final ProductId productId = context.getId(salesOrderLine.getProduct(), ProductId.class);
		final I_C_UOM uom = productBL.getStockUOM(productId);
		final Quantity qty = Quantity.of(salesOrderLine.getQty(), uom);
		final HUPIItemProductId piItemProductId = salesOrderLine.getPiItemProduct() != null
				? context.getId(salesOrderLine.getPiItemProduct(), HUPIItemProductId.class)
				: null;

		final OrderLineBuilder lineBuilder = salesOrderFactory.newOrderLine()
				.productId(productId)
				.addQty(qty)
				.piItemProductId(piItemProductId);

		this.lineCreateRequestAndBuilders.add(LineCreateRequestAndBuilder.of(salesOrderLine, lineBuilder));
	}

	private void createSchedules()
	{
		final HashMap<OrderLineId, ImmutableList<JsonSalesOrderCreateRequest.Schedule>> jobSchedulesToCreateByOrderLineId = new HashMap<>();
		this.lineCreateRequestAndBuilders.forEach(lineRequestAndBuilder -> {
			final List<JsonSalesOrderCreateRequest.Schedule> jobScheduleRequests = lineRequestAndBuilder.getScheduleRequests();
			if (jobScheduleRequests.isEmpty()) {return;}

			jobSchedulesToCreateByOrderLineId.put(lineRequestAndBuilder.getOrderLineId(), ImmutableList.copyOf(jobScheduleRequests));
		});

		if (jobSchedulesToCreateByOrderLineId.isEmpty()) {return;}

		final ImmutableSet<OrderLineId> orderLineIds = ImmutableSet.copyOf(jobSchedulesToCreateByOrderLineId.keySet());

		Stopwatch stopwatch = Stopwatch.createStarted();
		while (!jobSchedulesToCreateByOrderLineId.isEmpty())
		{
			final List<I_M_ShipmentSchedule> shipmentSchedules = shipmentSchedulePA.getByOrderLineIds(orderLineIds);
			for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
			{
				final OrderLineId orderLineId = OrderLineId.ofRepoId(shipmentSchedule.getC_OrderLine_ID());
				final ImmutableList<JsonSalesOrderCreateRequest.Schedule> jobSchedulesToCreate = jobSchedulesToCreateByOrderLineId.remove(orderLineId);

				createSchedules(shipmentSchedule, jobSchedulesToCreate);
			}

			if (stopwatch.elapsed().compareTo(JOB_SCHEDULE_CREATE_TIMEOUT) > 0)
			{
				throw new AdempiereException("Timeout creating the job schedules for shipment schedules. Took " + stopwatch.elapsed());
			}

			if (!jobSchedulesToCreateByOrderLineId.isEmpty())
			{
				logger.info("Still have {} shipment schedules to create job schedules for. Sleeping...", jobSchedulesToCreateByOrderLineId.size());
				sleep(JOB_SCHEDULE_CREATE_SLEEP_BETWEEN);
			}
		}
		stopwatch.stop();

		logger.info("Created job schedules for all {} order lines. Took {}", orderLineIds.size(), stopwatch);
	}

	private static void sleep(@NonNull final Duration duration)
	{
		logger.info("Sleeping {}...", duration);
		try
		{
			Thread.sleep(duration.toMillis());
		}
		catch (InterruptedException e)
		{
			throw new AdempiereException(e);
		}
	}

	private void createSchedules(final I_M_ShipmentSchedule shipmentSchedule, final ImmutableList<JsonSalesOrderCreateRequest.Schedule> jobSchedulesToCreate)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		final ShipmentScheduleAndJobScheduleIdSet shipmentScheduleAndJobScheduleIds = ShipmentScheduleAndJobScheduleIdSet.of(shipmentScheduleId);

		jobSchedulesToCreate.forEach(jobScheduleRequest -> {
			final WorkplaceId workplaceId = context.getId(jobScheduleRequest.getWorkplace(), WorkplaceId.class);
			pickingJobScheduleService.createOrUpdate(CreateOrUpdatePickingJobSchedulesRequest.builder()
					.shipmentScheduleAndJobScheduleIds(shipmentScheduleAndJobScheduleIds)
					.workplaceId(workplaceId)
					.qtyToPickBD(jobScheduleRequest.getQty())
					.build());
		});
	}

	//
	//
	//
	//
	//
	@Value(staticConstructor = "of")
	private static class LineCreateRequestAndBuilder
	{
		@NonNull JsonSalesOrderCreateRequest.Line lineCreateRequest;
		@NonNull OrderLineBuilder lineBuilder;

		public OrderLineId getOrderLineId() {return lineBuilder.getCreatedOrderAndLineId().getOrderLineId();}

		public List<JsonSalesOrderCreateRequest.Schedule> getScheduleRequests() {return lineCreateRequest.getSchedules() != null ? lineCreateRequest.getSchedules() : ImmutableList.of();}
	}
}
