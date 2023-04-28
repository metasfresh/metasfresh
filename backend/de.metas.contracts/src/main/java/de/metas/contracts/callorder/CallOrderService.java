/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.callorder.detail.CallOrderDetailService;
import de.metas.contracts.callorder.detail.UpsertCallOrderDetailRequest;
import de.metas.contracts.callorder.summary.CallOrderSummaryRepo;
import de.metas.contracts.callorder.summary.CallOrderSummaryService;
import de.metas.contracts.callorder.summary.model.CallOrderSummary;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CallOrderService
{
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

	private final CallOrderSummaryService summaryService;
	private final CallOrderDetailService detailService;
	private final CallOrderContractService callContractService;

	private final CallOrderSummaryRepo summaryRepo;

	public CallOrderService(
			@NonNull final CallOrderSummaryService summaryService,
			@NonNull final CallOrderDetailService detailService,
			@NonNull final CallOrderContractService callContractService,
			@NonNull final CallOrderSummaryRepo summaryRepo)
	{
		this.summaryService = summaryService;
		this.detailService = detailService;
		this.callContractService = callContractService;
		this.summaryRepo = summaryRepo;
	}

	public void handleCallOrderDetailUpsert(@NonNull final UpsertCallOrderDetailRequest request)
	{
		final FlatrateTermId flatrateTermId = request.getCallOrderContractId();

		final Optional<CallOrderSummary> summaryOptional = summaryRepo.getByFlatrateTermId(flatrateTermId);

		if (!summaryOptional.isPresent())
		{
			return;
		}

		final CallOrderSummary summary = summaryOptional.get();

		if (request.getOrderLine() != null)
		{
			detailService.upsertOrderRelatedDetail(summary.getSummaryId(), request.getOrderLine());
		}
		else if (request.getShipmentLine() != null)
		{
			detailService.upsertShipmentRelatedDetail(summary.getSummaryId(), request.getShipmentLine());
		}
		else if (request.getInvoiceLine() != null)
		{
			detailService.upsertInvoiceRelatedDetail(summary.getSummaryId(), request.getInvoiceLine());
		}
	}

	public void createCallOrderContractIfRequired(@NonNull final I_C_OrderLine ol)
	{
		if (!callContractService.isCallOrderContractLine(ol))
		{
			return;
		}

		if (flatrateBL.existsTermForOrderLine(ol))
		{
			return;
		}

		final I_C_Flatrate_Term newCallOrderTerm = callContractService.createCallOrderContract(ol);

		summaryService.createSummaryForOrderLine(ol, newCallOrderTerm);
	}

	public boolean isCallOrder(@NonNull final OrderId orderId)
	{
		final I_C_Order order = orderBL.getById(orderId);

		return isCallOrder(order);
	}

	public boolean isCallOrder(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeTargetId = DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID());

		if (docTypeTargetId == null)
		{
			return false;
		}

		return docTypeBL.isCallOrder(docTypeTargetId);
	}
}
