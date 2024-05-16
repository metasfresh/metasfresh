/*
 * #%L
 * de-metas-salesorder
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.salesorder.service;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.inoutcandidate.shippertransportation.ShipperDeliveryService;
import de.metas.invoice.InvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AutoProcessingOrderService
{
	private static final Logger logger = LogManager.getLogger(AutoProcessingOrderService.class);

	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	private final OrderService orderService;
	private final ShipmentService shipmentService;
	private final InvoiceService invoiceService;
	private final ShipperDeliveryService shipperDeliveryService;

	public AutoProcessingOrderService(
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

	public void completeShipAndInvoice(@NonNull final OrderId orderId)
	{
		final ImmutableSet<ShipmentScheduleId> scheduleIds = orderService.generateSchedules(orderId);
		if (scheduleIds.isEmpty())
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Returning! No shipmentScheduleIds generated for C_Order_ID={}", orderId.getRepoId());
			return;
		}

		final boolean canSchedulesBeFulfilled = shipmentService.canSchedulesBeFulfilled(scheduleIds);
		if (!canSchedulesBeFulfilled)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Returning! Shipment schedules cannot be fulfilled, shipmentScheduleIds: {}", scheduleIds);
			return;
		}

		final GenerateShipmentsForSchedulesRequest request = GenerateShipmentsForSchedulesRequest.builder()
				.scheduleIds(scheduleIds)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				
				// Usually we want only *CUs* to be picked on-the-fly, because we don't know and care which HUs are shipped and don't want to make assumptions regarding their TU-Packaging.
				// But here it is different: we want that whatever HUs are picked, exactly those HUs shall be boxed and send to the customer.
				.onTheFlyPickToPackingInstructions(true)
				.isCompleteShipment(true)
				.build();

		final Set<InOutId> generatedInOutIds = shipmentService.generateShipmentsForScheduleIds(request);
		if (generatedInOutIds.isEmpty())
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Returning! No shipments generated for shipmentScheduleIds: {}", scheduleIds);
			return;
		}

		for (final InOutId inOutId : generatedInOutIds)
		{
			shipperDeliveryService.createTransportationAndPackagesForShipment(inOutId);
		}

		final List<I_M_InOutLine> inOutLines = shipmentService.retrieveInOutLineByShipScheduleId(scheduleIds);

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceService.retrieveInvoiceCandsByInOutLines(inOutLines);

		final boolean areShipToAndBillToSame = sameShippingAndBillingAddress(invoiceCandidates, generatedInOutIds);

		if (!areShipToAndBillToSame)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Returning! Shipment Partner/Location is not equal to BillTo Partner/Location");
			return;
		}

		final Set<InvoiceCandidateId> invoiceCandidateIds = invoiceCandidates
				.stream()
				.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.map(InvoiceCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		invoiceService.generateInvoicesFromInvoiceCandidateIds(invoiceCandidateIds);
	}

	private boolean sameShippingAndBillingAddress(
			@NonNull final List<I_C_Invoice_Candidate> invoiceCandidates,
			@NonNull final Set<InOutId> shipmentIds)
	{
		final Map<InOutId, I_M_InOut> shipmentsByIds = inOutDAO.getShipmentsByIds(shipmentIds, I_M_InOut.class);

		for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
		{
			final InOutId invoiceCandidateInOutId = InOutId.ofRepoIdOrNull(invoiceCandidate.getM_InOut_ID());
			if (invoiceCandidateInOutId == null)
			{
				Loggables.withLogger(logger, Level.DEBUG).addLog("Missing inOutId from invoice candidate! icId: " + invoiceCandidate.getC_Invoice_Candidate_ID());

				return false;
			}

			final I_M_InOut inOut = shipmentsByIds.get(invoiceCandidateInOutId);

			if (inOut == null)
			{
				throw new AdempiereException("Something went wrong! invoice candidate belongs to different shipment!")
						.appendParametersToMessage()
						.setParameter("invoiceCandidateId:", invoiceCandidate.getC_Invoice_Candidate_ID())
						.setParameter("invoiceCandidate.inOutId:", invoiceCandidate.getM_InOut_ID())
						.setParameter("inOutId", invoiceCandidateInOutId);
			}

			final BPartnerLocationAndCaptureId shipToBPartner = InOutDocumentLocationAdapterFactory.locationAdapter(inOut).getBPartnerLocationAndCaptureId();
			final BPartnerLocationAndCaptureId billToBPartner = invoiceCandBL.getBillLocationId(invoiceCandidate, false);

			if (!shipToBPartner.equals(billToBPartner))
			{
				return false;
			}
		}

		return true;
	}
}
