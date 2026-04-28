/*
 * #%L
 * de-metas-salesorder
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.salesorder.candidate;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inoutcandidate.shippertransportation.ShipperDeliveryService;
import de.metas.invoice.InvoiceService;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.salesorder.service.OrderService;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AutoProcessingOLCandService
{
	private static final Logger logger = LogManager.getLogger(AutoProcessingOLCandService.class);

	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	private final OrderService orderService;
	private final ShipmentService shipmentService;
	private final InvoiceService invoiceService;
	private final ShipperDeliveryService shipperDeliveryService;

	public AutoProcessingOLCandService(
			@NonNull final OrderService orderService,
			@NonNull final ShipmentService shipmentService,
			@NonNull final InvoiceService invoiceService,
			@NonNull final ShipperDeliveryService shipperDeliveryService)
	{
		this.orderService = orderService;
		this.shipmentService = shipmentService;
		this.invoiceService = invoiceService;
		this.shipperDeliveryService = shipperDeliveryService;
	}

	public void processOLCands(@NonNull final ProcessOLCandsRequest request)
	{
		final Set<OLCandId> olCandIds = queryBL.createQueryBuilder(I_C_OLCand.class)
				.setOnlySelection(request.getPInstanceId())
				.create()
				.stream()
				.map(I_C_OLCand::getC_OLCand_ID)
				.map(OLCandId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		if (olCandIds.isEmpty())
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Returning! No OlCandIds selection found for PInstanceId: {}. Maybe you created them in another transaction that's not yet committed?", request.getPInstanceId());
			return;
		}

		final Map<AsyncBatchId, List<OLCandId>> asyncBatchId2OLCandIds = trxManager.callInNewTrx(() -> orderService.getAsyncBatchId2OLCandIds(olCandIds));

		// Propagating them means that the WPs which create the shipment-scheds and invoice-candidates also belong to this async-batch.
		// So, when the async-batch is finally done, we will have those scheds&ICs
		final boolean propagateAsyncIdsToShipmentSchduleWPs = request.isShip() || request.isInvoice();

		final Set<OrderId> orderIds = orderService.generateOrderSync(asyncBatchId2OLCandIds, propagateAsyncIdsToShipmentSchduleWPs);

		if (request.isShip())
		{   // now we need a new async-batch! The one we create the Orders with is processed and its observer unregistered
			final Set<InOutId> generatedInOutIds = shipmentService.generateShipmentsForOLCands(asyncBatchId2OLCandIds);
			Loggables.withLogger(logger, Level.INFO).addLog("Created {} M_InOuts", generatedInOutIds.size());
			for (final InOutId inOutId : generatedInOutIds)
			{
				shipperDeliveryService.createTransportationAndPackagesForShipment(inOutId);
			}
		}

		if (request.isInvoice())
		{
			final List<I_M_InOutLine> shipmentLines = inOutDAO.retrieveShipmentLinesForOrderId(orderIds);
			Loggables.withLogger(logger, Level.INFO).addLog("Retrieved {} M_InOutLines for {} C_Order_IDs", shipmentLines.size(), orderIds.size());

			invoiceService.generateInvoicesFromShipmentLines(shipmentLines);
		}

		if (request.isCloseOrder())
		{
			orderIds.forEach(orderBL::closeOrder);
		}
	}
}
