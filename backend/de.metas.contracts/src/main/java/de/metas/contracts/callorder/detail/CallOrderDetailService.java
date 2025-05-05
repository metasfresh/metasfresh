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

package de.metas.contracts.callorder.detail;

import de.metas.contracts.callorder.detail.model.CallOrderDetail;
import de.metas.contracts.callorder.detail.model.CallOrderDetailData;
import de.metas.contracts.callorder.detail.model.CallOrderDetailId;
import de.metas.contracts.callorder.summary.model.CallOrderSummaryId;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CallOrderDetailService
{
	private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);

	@NonNull private final CallOrderDetailRepo detailRepo;

	@NonNull
	public CallOrderDetail createCallOrderDetail(@NonNull final CallOrderDetailData callOrderDetailData)
	{
		return detailRepo.create(callOrderDetailData);
	}

	public void upsertOrderRelatedDetail(@NonNull final CallOrderSummaryId summaryId, @NonNull final I_C_OrderLine ol)
	{
		final CallOrderDetailQuery query = CallOrderDetailQuery.builder()
				.summaryId(summaryId)
				.orderLineId(OrderLineId.ofRepoId(ol.getC_OrderLine_ID()))
				.build();

		final Optional<CallOrderDetailId> existingDetailId = detailRepo.getDetailByQuery(query)
				.map(CallOrderDetail::getDetailId);

		final CallOrderDetailData newDetailData = buildCallOrderData(summaryId, ol);

		if (existingDetailId.isPresent())
		{
			final CallOrderDetail callOrderDetail = CallOrderDetail
					.builder()
					.detailId(existingDetailId.get())
					.detailData(newDetailData)
					.build();

			detailRepo.update(callOrderDetail);
		}
		else
		{
			detailRepo.create(newDetailData);
		}
	}

	public void upsertShipmentRelatedDetail(@NonNull final CallOrderSummaryId summaryId, @NonNull final I_M_InOutLine line)
	{
		final CallOrderDetailQuery query = CallOrderDetailQuery.builder()
				.summaryId(summaryId)
				.inOutLineId(InOutLineId.ofRepoId(line.getM_InOutLine_ID()))
				.build();

		final Optional<CallOrderDetailId> existingDetailId = detailRepo.getDetailByQuery(query)
				.map(CallOrderDetail::getDetailId);

		final CallOrderDetailData newDetailData = buildCallOrderData(summaryId, line);

		if (existingDetailId.isPresent())
		{
			final CallOrderDetail callOrderDetail = CallOrderDetail
					.builder()
					.detailId(existingDetailId.get())
					.detailData(newDetailData)
					.build();

			detailRepo.update(callOrderDetail);
		}
		else
		{
			detailRepo.create(newDetailData);
		}
	}

	public void upsertInvoiceRelatedDetail(@NonNull final CallOrderSummaryId summaryId, @NonNull final I_C_InvoiceLine invoiceLine)
	{
		final CallOrderDetailQuery query = CallOrderDetailQuery.builder()
				.summaryId(summaryId)
				.invoiceAndLineId(InvoiceAndLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID()))
				.build();

		final Optional<CallOrderDetailId> existingDetailId = detailRepo.getDetailByQuery(query)
				.map(CallOrderDetail::getDetailId);

		final CallOrderDetailData newDetailData = buildCallOrderData(summaryId, invoiceLine);

		if (existingDetailId.isPresent())
		{
			final CallOrderDetail callOrderDetail = CallOrderDetail
					.builder()
					.detailId(existingDetailId.get())
					.detailData(newDetailData)
					.build();

			detailRepo.update(callOrderDetail);
		}
		else
		{
			detailRepo.create(newDetailData);
		}
	}

	@NonNull
	private CallOrderDetailData buildCallOrderData(@NonNull final CallOrderSummaryId callOrderSummaryId, @NonNull final I_C_InvoiceLine invoiceLine)
	{
		final Quantity qtyInvoiced = invoiceLineBL.getQtyInvoicedStockUOM(InterfaceWrapperHelper.create(invoiceLine, de.metas.adempiere.model.I_C_InvoiceLine.class));

		return CallOrderDetailData
				.builder()
				.summaryId(callOrderSummaryId)
				.invoiceId(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()))
				.invoiceAndLineId(InvoiceAndLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID()))
				.qtyInvoiced(qtyInvoiced)
				.build();
	}

	@NonNull
	private static CallOrderDetailData buildCallOrderData(@NonNull final CallOrderSummaryId summaryId, @NonNull final I_C_OrderLine ol)
	{
		final UomId uomId = UomId.ofRepoId(ol.getC_UOM_ID());
		final Quantity qtyEntered = Quantitys.of(ol.getQtyEntered(), uomId);

		return CallOrderDetailData
				.builder()
				.summaryId(summaryId)
				.orderId(OrderId.ofRepoId(ol.getC_Order_ID()))
				.orderLineId(OrderLineId.ofRepoId(ol.getC_OrderLine_ID()))
				.qtyEntered(qtyEntered)
				.build();
	}

	@NonNull
	private CallOrderDetailData buildCallOrderData(@NonNull final CallOrderSummaryId summaryId, @NonNull final I_M_InOutLine shipmentLine)
	{
		return CallOrderDetailData
				.builder()
				.summaryId(summaryId)
				.shipmentId(InOutId.ofRepoId(shipmentLine.getM_InOut_ID()))
				.shipmentLineId(InOutLineId.ofRepoId(shipmentLine.getM_InOutLine_ID()))
				.qtyDelivered(inoutBL.getQtyEntered(shipmentLine))
				.build();
	}
}
