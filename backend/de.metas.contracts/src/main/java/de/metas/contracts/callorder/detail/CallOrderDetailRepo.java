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
import de.metas.contracts.model.I_C_CallOrderDetail;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class CallOrderDetailRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public CallOrderDetail getById(@NonNull final CallOrderDetailId detailId)
	{
		return ofRecord(InterfaceWrapperHelper.load(detailId, I_C_CallOrderDetail.class));
	}

	@NonNull
	public CallOrderDetail create(@NonNull final CallOrderDetailData detailData)
	{
		final I_C_CallOrderDetail record = InterfaceWrapperHelper.newInstance(I_C_CallOrderDetail.class);

		setDetailDataToRecord(record, detailData);

		save(record);

		return ofRecord(record);
	}

	@NonNull
	public CallOrderDetail update(@NonNull final CallOrderDetail callOrderDetail)
	{
		final I_C_CallOrderDetail record = InterfaceWrapperHelper.load(callOrderDetail.getDetailId(), I_C_CallOrderDetail.class);

		setDetailDataToRecord(record, callOrderDetail.getDetailData());

		save(record);

		return ofRecord(record);
	}

	public void resetDeliveredQtyForShipment(@NonNull final InOutId shipmentId)
	{
		final IQuery<I_C_CallOrderDetail> detailsMatchingShipmentQuery = queryBL.createQueryBuilder(I_C_CallOrderDetail.class)
				.addEqualsFilter(I_C_CallOrderDetail.COLUMNNAME_M_InOut_ID, shipmentId)
				.create();

		final ICompositeQueryUpdater<I_C_CallOrderDetail> updater = queryBL
				.createCompositeQueryUpdater(I_C_CallOrderDetail.class)
				.addSetColumnValue(I_C_CallOrderDetail.COLUMNNAME_QtyDeliveredInUOM, BigDecimal.ZERO);

		detailsMatchingShipmentQuery.update(updater);
	}

	public void resetInvoicedQtyForInvoice(@NonNull final InvoiceId invoiceId)
	{
		final IQuery<I_C_CallOrderDetail> detailsMatchingInvoiceQuery = queryBL.createQueryBuilder(I_C_CallOrderDetail.class)
				.addEqualsFilter(I_C_CallOrderDetail.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create();

		final ICompositeQueryUpdater<I_C_CallOrderDetail> updater = queryBL
				.createCompositeQueryUpdater(I_C_CallOrderDetail.class)
				.addSetColumnValue(I_C_CallOrderDetail.COLUMNNAME_QtyInvoicedInUOM, BigDecimal.ZERO);

		detailsMatchingInvoiceQuery.update(updater);
	}

	public void resetEnteredQtyForOrder(@NonNull final OrderId orderId)
	{
		final IQuery<I_C_CallOrderDetail> detailsMatchingOrderQuery = queryBL.createQueryBuilder(I_C_CallOrderDetail.class)
				.addEqualsFilter(I_C_CallOrderDetail.COLUMNNAME_C_Order_ID, orderId)
				.create();

		final ICompositeQueryUpdater<I_C_CallOrderDetail> resetQtyEntered = queryBL
				.createCompositeQueryUpdater(I_C_CallOrderDetail.class)
				.addSetColumnValue(I_C_CallOrderDetail.COLUMNNAME_QtyEntered, BigDecimal.ZERO);

		detailsMatchingOrderQuery
				.update(resetQtyEntered);
	}

	@NonNull
	public Optional<CallOrderDetail> getDetailByQuery(@NonNull final CallOrderDetailQuery query)
	{
		final IQueryBuilder<I_C_CallOrderDetail> queryBuilder = queryBL.createQueryBuilder(I_C_CallOrderDetail.class)
				.addEqualsFilter(I_C_CallOrderDetail.COLUMNNAME_C_CallOrderSummary_ID, query.getSummaryId());

		if (query.getOrderLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_CallOrderDetail.COLUMNNAME_C_OrderLine_ID, query.getOrderLineId());
		}

		if (query.getInOutLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_CallOrderDetail.COLUMNNAME_M_InOutLine_ID, query.getInOutLineId());
		}

		if (query.getInvoiceAndLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_CallOrderDetail.COLUMN_C_InvoiceLine_ID, query.getInvoiceAndLineId());
		}

		return queryBuilder.create()
				.firstOnlyOptional(I_C_CallOrderDetail.class)
				.map(CallOrderDetailRepo::ofRecord);
	}

	private static void setDetailDataToRecord(@NonNull final I_C_CallOrderDetail record, @NonNull final CallOrderDetailData detailData)
	{
		record.setC_CallOrderSummary_ID(detailData.getSummaryId().getRepoId());

		if (detailData.getOrderDetail() != null)
		{
			final CallOrderDetailData.OrderDetail orderDetail = detailData.getOrderDetail();

			record.setC_OrderLine_ID(orderDetail.getOrderLineId().getRepoId());
			record.setC_Order_ID(orderDetail.getOrderId().getRepoId());
			record.setQtyEntered(orderDetail.getQtyEntered().toBigDecimal());
			record.setC_UOM_ID(orderDetail.getQtyEntered().getUomId().getRepoId());
		}

		if (detailData.getShipmentDetail() != null)
		{
			final CallOrderDetailData.ShipmentDetail shipmentDetail = detailData.getShipmentDetail();

			record.setM_InOut_ID(shipmentDetail.getShipmentId().getRepoId());
			record.setM_InOutLine_ID(shipmentDetail.getShipmentLineId().getRepoId());
			record.setQtyDeliveredInUOM(shipmentDetail.getQtyDelivered().toBigDecimal());
			record.setC_UOM_ID(shipmentDetail.getQtyDelivered().getUomId().getRepoId());
		}

		if (detailData.getInvoiceDetail() != null)
		{
			final CallOrderDetailData.InvoiceDetail invoiceDetail = detailData.getInvoiceDetail();

			record.setC_Invoice_ID(invoiceDetail.getInvoiceId().getRepoId());
			record.setC_InvoiceLine_ID(invoiceDetail.getInvoiceAndLineId().getRepoId());
			record.setQtyInvoicedInUOM(invoiceDetail.getQtyInvoiced().toBigDecimal());
			record.setC_UOM_ID(invoiceDetail.getQtyInvoiced().getUomId().getRepoId());
		}
	}

	@NonNull
	private static CallOrderDetail ofRecord(@NonNull final I_C_CallOrderDetail record)
	{
		return CallOrderDetail.builder()
				.detailId(CallOrderDetailId.ofRepoId(record.getC_CallOrderDetail_ID()))
				.detailData(ofRecordData(record))
				.build();
	}

	@NonNull
	private static CallOrderDetailData ofRecordData(@NonNull final I_C_CallOrderDetail record)
	{
		final CallOrderDetailData.CallOrderDetailDataBuilder builder = CallOrderDetailData.builder()
				.summaryId(CallOrderSummaryId.ofRepoId(record.getC_CallOrderSummary_ID()));

		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		if (record.getC_OrderLine_ID() > 0)
		{
			final Quantity qtyEntered = Quantitys.of(record.getQtyEntered(), uomId);

			return builder
					.orderId(OrderId.ofRepoIdOrNull(record.getC_Order_ID()))
					.orderLineId(OrderLineId.ofRepoIdOrNull(record.getC_OrderLine_ID()))
					.qtyEntered(qtyEntered)
					.build();

		}
		else if (record.getM_InOutLine_ID() > 0)
		{
			final Quantity qtyDeliveredInUOM = Quantitys.of(record.getQtyDeliveredInUOM(), uomId);

			return builder
					.shipmentId(InOutId.ofRepoIdOrNull(record.getM_InOut_ID()))
					.shipmentLineId(InOutLineId.ofRepoIdOrNull(record.getM_InOutLine_ID()))
					.qtyDelivered(qtyDeliveredInUOM)
					.build();
		}
		else if (record.getC_InvoiceLine_ID() > 0)
		{
			final Quantity qtyInvoicedInUOM = Quantitys.of(record.getQtyInvoicedInUOM(), uomId);

			return builder
					.invoiceId(InvoiceId.ofRepoIdOrNull(record.getC_Invoice_ID()))
					.invoiceAndLineId(InvoiceAndLineId.ofRepoIdOrNull(record.getC_Invoice_ID(), record.getC_InvoiceLine_ID()))
					.qtyInvoiced(qtyInvoicedInUOM)
					.build();
		}
		else
		{
			throw new AdempiereException("Detail with no reference document is not supported!")
					.appendParametersToMessage()
					.setParameter("C_CallOrderDetail_ID", record.getC_CallOrderDetail_ID());
		}
	}
}
