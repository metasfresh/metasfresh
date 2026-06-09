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

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.OrderId;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregationKeyBuilder;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregator;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_Order;
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
	 * <p>Project handling: each created PO's own {@code BEFORE_COMPLETE} ({@code C_Order_Project})
	 * creates its own project for that PO, and the {@code PurchaseOrderProjectListener} chain
	 * pushes that project back to the corresponding SO line(s) via {@code C_PO_OrderLine_Alloc}.
	 * This per-PO model gives each vendor its own project rather than forcing a single shared
	 * project on a multi-vendor SO.</p>
	 *
	 * <p><b>Maintenance warning — post-commit material-event publishing is load-bearing here.</b>
	 * The PO's auto-completion below fires inside the SO's transaction, and all receipt-schedule
	 * observers in this module (notably
	 * {@code M_ReceiptSchedule_PostMaterialEvent} in {@code de.metas.purchasecandidate.base}) use
	 * {@code PostMaterialEventService.enqueueEventAfterNextCommit} to defer event publication
	 * until after the outer SO transaction commits. If a future observer ever uses
	 * {@code enqueueEventNow} instead, the dropship invariant ("no MD_Candidate rows appear for
	 * these orders") will silently break because dispo could see a half-built PO before the
	 * SO transaction commits. Keep the deferred-event pattern for every receipt-schedule
	 * observer that participates in the dropship flow.</p>
	 *
	 * @param salesOrder the in-memory sales-order instance to create a dropship PO for
	 */
	public void createDropshipPOForSO(@NonNull final I_C_Order salesOrder)
	{
		final OrderId salesOrderId = OrderId.ofRepoId(salesOrder.getC_Order_ID());
		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(salesOrder);

		final boolean isVendorInOrderLinesRequired = true;
		final boolean allowMultiplePOOrders = true;

		final String purchaseQtySource = orderCreatePOFromSOsBL.getConfigPurchaseQtySource();
		final CreatePOFromSOsAggregator aggregator = createAggregator(ctxAware, purchaseQtySource, PurchaseTypeEnum.DROPSHIP);
		aggregator.setItemAggregationKeyBuilder(createKeyBuilder(ctxAware, isVendorInOrderLinesRequired));
		aggregator.setGroupsBufferSize(100);

		// retrieveOrderLines needs the extended de.metas.order.model.I_C_Order view of the same underlying record.
		final de.metas.order.model.I_C_Order salesOrderExt = InterfaceWrapperHelper.create(salesOrder, de.metas.order.model.I_C_Order.class);
		final List<I_C_OrderLine> salesOrderLines = orderCreatePOFromSOsDAO.retrieveOrderLines(salesOrderExt, allowMultiplePOOrders, purchaseQtySource);
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
			throw new AdempiereException("Dropship PO creation for sales order " + salesOrderId + " skipped lines: " + msg);
		});

		// The aggregator leaves each created PO in DocStatus=DR. Look them up so we can
		// auto-complete them in the same transaction as the SO.
		// IsDropShip='Y' narrows the result to POs created by the aggregator above — guards
		// against picking up a stale draft PO with the same Link_Order_ID left over from a
		// prior failed completion (the aggregator stamps IsDropShip='Y' on every created PO).
		//
		// Project handling: each PO's own BEFORE_COMPLETE (via C_Order_Project) will create
		// its own project; the existing PurchaseOrderProjectListener
		// (UpdateSalesOrderFromPurchaseOrderProjectListener) then pushes that project back
		// to the corresponding SO line(s) via C_PO_OrderLine_Alloc. No SO-side stamping is
		// done here — see Change 1 of the dropship-warehouse refactor.
		final List<I_C_Order> createdPOs = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_Link_Order_ID, salesOrderId.getRepoId())
				.addEqualsFilter(I_C_Order.COLUMNNAME_IsSOTrx, false)
				.addEqualsFilter(I_C_Order.COLUMNNAME_DocStatus, IDocument.STATUS_Drafted)
				.addEqualsFilter(I_C_Order.COLUMNNAME_IsDropShip, true)
				.create()
				.list();

		// Auto-complete every created PO so the project chain finalises in the same transaction
		// as the SO. The PO-completion material-event chain is short-circuited for dropship
		// warehouses via the isDropShipWarehouse flag on receipt-schedule events (see
		// M_ReceiptSchedule_PostMaterialEvent and the dispo-service ReceiptsSchedule*Handlers),
		// so no MD_Candidate rows are created.
		final IDocumentBL documentBL = Services.get(IDocumentBL.class);
		for (final I_C_Order po : createdPOs)
		{
			documentBL.processEx(po, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
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
