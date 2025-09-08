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

package de.metas.contracts.callorder.summary;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.callorder.summary.model.CallOrderSummary;
import de.metas.contracts.callorder.summary.model.CallOrderSummaryData;
import de.metas.contracts.callorder.summary.model.CallOrderSummaryId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CallOrderSummaryService
{
	private final IUOMConversionBL conversionBL = Services.get(IUOMConversionBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final CallOrderSummaryRepo summaryRepo;

	public CallOrderSummaryService(@NonNull final CallOrderSummaryRepo summaryRepo)
	{
		this.summaryRepo = summaryRepo;
	}

	@NonNull
	public CallOrderSummary getById(@NonNull final CallOrderSummaryId callOrderSummaryId)
	{
		return summaryRepo.getById(callOrderSummaryId);
	}

	public void createSummaryForOrderLine(@NonNull final I_C_OrderLine ol, @NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final OrderId orderId = OrderId.ofRepoId(ol.getC_Order_ID());
		final I_C_Order order = orderBL.getById(orderId);

		final CallOrderSummaryData callOrderSummaryData = CallOrderSummaryData.builder()
				.orderId(orderId)
				.orderLineId(OrderLineId.ofRepoId(ol.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(ol.getM_Product_ID()))
				.uomId(UomId.ofRepoId(ol.getC_UOM_ID()))
				.qtyEntered(ol.getQtyEntered())
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNull(ol.getM_AttributeSetInstance_ID()))
				.build();

		summaryRepo.create(callOrderSummaryData);
	}

	public void addQtyDelivered(@NonNull final CallOrderSummaryId callOrderSummaryId, @NonNull final Quantity deltaQtyDelivered)
	{
		final CallOrderSummary callOrderSummary = summaryRepo.getById(callOrderSummaryId);
		final CallOrderSummaryData summaryData = callOrderSummary.getSummaryData();

		final Quantity currentQtyDelivered = Quantitys.of(summaryData.getQtyDelivered(), summaryData.getUomId());

		final Quantity sum = conversionBL.computeSum(
				UOMConversionContext.of(callOrderSummary.getSummaryData().getProductId()),
				ImmutableList.of(currentQtyDelivered, deltaQtyDelivered),
				currentQtyDelivered.getUomId());

		final BigDecimal newQtyDelivered = sum.toBigDecimal();

		summaryRepo.update(callOrderSummary.withSummaryData(summaryData.withQtyDelivered(newQtyDelivered)));
	}

	public void addQtyInvoiced(@NonNull final CallOrderSummaryId callOrderSummaryId, @NonNull final Quantity deltaQtyInvoiced)
	{
		final CallOrderSummary callOrderSummary = summaryRepo.getById(callOrderSummaryId);
		final CallOrderSummaryData summaryData = callOrderSummary.getSummaryData();

		final Quantity currentQtyInvoiced = Quantitys.of(summaryData.getQtyInvoiced(), summaryData.getUomId());

		final Quantity sum = conversionBL.computeSum(
				UOMConversionContext.of(callOrderSummary.getSummaryData().getProductId()),
				ImmutableList.of(currentQtyInvoiced, deltaQtyInvoiced),
				currentQtyInvoiced.getUomId());

		final BigDecimal newQtyInvoiced = sum.toBigDecimal();

		summaryRepo.update(callOrderSummary.withSummaryData(summaryData.withQtyInvoiced(newQtyInvoiced)));
	}
}
