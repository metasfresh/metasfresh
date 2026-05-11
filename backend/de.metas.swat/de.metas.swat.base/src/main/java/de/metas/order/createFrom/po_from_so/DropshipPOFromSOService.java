/*
 * #%L
 * de.metas.swat.base
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

package de.metas.order.createFrom.po_from_so;

import de.metas.order.OrderId;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregationKeyBuilder;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregator;
import de.metas.order.model.I_C_Order;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Programmatic entry-point that drives {@link CreatePOFromSOsAggregator} to produce
 * exactly one dropship purchase order for a given sales order.
 *
 * <p>Vendor is required on every sales-order line ({@code IsVendorInOrderLinesRequired=true});
 * BOM explosion and user-notification logic are intentionally absent here — those are
 * concerns of the AD-process driver {@code C_Order_CreatePOFromSOs}.</p>
 *
 * <p>Intended caller: the {@code C_Order_DropshipPO} model interceptor (T5).</p>
 */
@Service
public class DropshipPOFromSOService
{
	private final IC_Order_CreatePOFromSOsDAO orderCreatePOFromSOsDAO = Services.get(IC_Order_CreatePOFromSOsDAO.class);
	private final IC_Order_CreatePOFromSOsBL orderCreatePOFromSOsBL = Services.get(IC_Order_CreatePOFromSOsBL.class);

	/**
	 * Creates exactly one dropship purchase order for the given sales order.
	 *
	 * <p>Under normal flow the upstream interceptor (T5, {@code TIMING_BEFORE_COMPLETE})
	 * will have already validated that every line has a vendor set.
	 * If the aggregator still skips lines at aggregation time (e.g., product master-data gap),
	 * this method throws an {@link AdempiereException} rather than silently losing data.</p>
	 *
	 * @param salesOrderId the ID of the sales order to create a dropship PO for
	 */
	public void createDropshipPOForSO(@NonNull final OrderId salesOrderId)
	{
		final I_C_Order salesOrder = InterfaceWrapperHelper.load(salesOrderId, I_C_Order.class);
		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(salesOrder);

		final boolean isVendorInOrderLinesRequired = true;
		final boolean allowMultiplePOOrders = true;

		final String purchaseQtySource = orderCreatePOFromSOsBL.getConfigPurchaseQtySource();
		final CreatePOFromSOsAggregator aggregator = createAggregator(ctxAware, purchaseQtySource, PurchaseTypeEnum.DROPSHIP);
		aggregator.setItemAggregationKeyBuilder(createKeyBuilder(ctxAware, isVendorInOrderLinesRequired));
		aggregator.setGroupsBufferSize(100);

		final List<I_C_OrderLine> salesOrderLines = orderCreatePOFromSOsDAO.retrieveOrderLines(salesOrder, allowMultiplePOOrders, purchaseQtySource);
		for (final I_C_OrderLine line : salesOrderLines)
		{
			aggregator.add(line);
		}
		aggregator.closeAllGroups();

		// Skipped-lines: under DROPSHIP + IsVendorInOrderLinesRequired=true, the upstream interceptor (T5)
		// already validated all lines have a vendor. Any skipped lines here would indicate a master-data
		// gap discovered at aggregation time — surface as an exception rather than a silent notification.
		aggregator.getSkippedLinesMessage().ifPresent(msg ->
		{
			throw new AdempiereException("Dropship PO creation skipped sales-order lines: " + msg);
		});
	}

	/**
	 * Factory method for the aggregator. Protected to allow overriding in tests.
	 */
	protected CreatePOFromSOsAggregator createAggregator(
			@NonNull final IContextAware ctxAware,
			@NonNull final String purchaseQtySource,
			@NonNull final PurchaseTypeEnum purchaseType)
	{
		return new CreatePOFromSOsAggregator(ctxAware, purchaseQtySource, purchaseType);
	}

	/**
	 * Factory method for the aggregation key builder. Protected to allow overriding in tests.
	 */
	protected CreatePOFromSOsAggregationKeyBuilder createKeyBuilder(
			@NonNull final IContextAware ctxAware,
			final boolean isVendorInOrderLinesRequired)
	{
		final int p_Vendor_ID = 0; // no fixed vendor — derive per line via aggregation key
		return new CreatePOFromSOsAggregationKeyBuilder(p_Vendor_ID, ctxAware, isVendorInOrderLinesRequired);
	}
}
