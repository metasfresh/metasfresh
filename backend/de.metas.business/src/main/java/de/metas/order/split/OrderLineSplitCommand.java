/*
 * #%L
 * de.metas.business
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

package de.metas.order.split;

import com.google.common.annotations.VisibleForTesting;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderLineSplitCommand
{
	public static final AdMessageKey MSG_QTY_TOO_LARGE = AdMessageKey.of("OrderLineSplit_QtyTooLarge");
	public static final AdMessageKey MSG_BELOW_DELIVERED = AdMessageKey.of("OrderLineSplit_QtyBelowDelivered");
	public static final AdMessageKey MSG_BELOW_INVOICED = AdMessageKey.of("OrderLineSplit_QtyBelowInvoiced");
	public static final AdMessageKey MSG_ORDER_NOT_COMPLETED = AdMessageKey.of("OrderLineSplit_OrderNotCompleted");

	private final IOrderLineBL orderLineBL;
	private final IOrderBL orderBL;
	private final IOrderDAO orderDAO;
	private final IDocumentBL documentBL;
	private final IOrderLineSplitListener splitListener;

	/** Spring-managed constructor (production path). */
	public OrderLineSplitCommand()
	{
		this(Services.get(IOrderLineBL.class),
				Services.get(IOrderBL.class),
				Services.get(IOrderDAO.class),
				Services.get(IDocumentBL.class),
				SpringContextHolder.instance.getBean(IOrderLineSplitListener.class));
	}

	@VisibleForTesting
	OrderLineSplitCommand(
			@NonNull final IOrderLineBL orderLineBL,
			@NonNull final IOrderBL orderBL,
			@NonNull final IOrderDAO orderDAO,
			@NonNull final IDocumentBL documentBL,
			@NonNull final IOrderLineSplitListener splitListener)
	{
		this.orderLineBL = orderLineBL;
		this.orderBL = orderBL;
		this.orderDAO = orderDAO;
		this.documentBL = documentBL;
		this.splitListener = splitListener;
	}

	public OrderLineSplitResult split(@NonNull final OrderLineSplitRequest request)
	{
		final I_C_OrderLine original = orderLineBL.getOrderLineById(request.getOrderLineId());
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(original.getC_Order_ID()));

		validate(order, original, request.getQtyToSplitOff());

		final BigDecimal qtyToSplitOff = request.getQtyToSplitOff();
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		// 1) Reactivate the order so we can modify its lines (legacy MOrderLine.beforeSave
		//    rejects new lines on a CO order). The order's DocStatus briefly goes CO -> IP
		//    inside this transaction and is re-completed in step 4.
		documentBL.processEx(order, IDocument.ACTION_ReActivate);

		// 2) Clone original line into a new sibling line on the same order.
		final I_C_OrderLine newLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		InterfaceWrapperHelper.copyValues(original, newLine, /*honorIsCalculated=*/false);
		newLine.setC_OrderLine_ID(0);
		newLine.setQtyDelivered(BigDecimal.ZERO);
		newLine.setQtyInvoiced(BigDecimal.ZERO);
		newLine.setDateDelivered(null);
		newLine.setDateInvoiced(null);
		newLine.setProcessed(false);
		// D4: project cleared on the new line so it can be reserved against a different project
		newLine.setC_Project_ID(0);
		newLine.setLine(computeNextLineNo(orderId));
		newLine.setQtyEntered(qtyToSplitOff);
		// Pricing: clone from original (the order is back in IP so interceptors would recompute,
		// but cloning keeps the new line's price stable to the original's negotiated value).
		newLine.setPriceEntered(original.getPriceEntered());
		newLine.setPriceActual(original.getPriceActual());
		newLine.setPriceList(original.getPriceList());
		newLine.setPriceLimit(original.getPriceLimit());
		newLine.setDiscount(original.getDiscount());
		newLine.setLineNetAmt(original.getPriceActual().multiply(qtyToSplitOff));
		// BEFORE_NEW interceptors recompute QtyOrdered, QtyReserved, tax, etc.
		InterfaceWrapperHelper.save(newLine);

		// 3) Reduce the original line.
		final BigDecimal newQtyForOriginal = original.getQtyOrdered().subtract(qtyToSplitOff);
		original.setQtyEntered(newQtyForOriginal);
		InterfaceWrapperHelper.save(original);

		// 4) Re-complete the order. This re-fires the standard order-completion cascade:
		//    M_ShipmentSchedule + C_Invoice_Candidate are created/refreshed for both lines
		//    via the OrderLineShipmentScheduleHandler and C_Order_Handler SPIs.
		documentBL.processEx(order, IDocument.ACTION_Complete);

		// 5) Shrink M_QtyReservation on the original line to fit the new open qty.
		//    The reservation table is orthogonal to order completion, so this stays explicit.
		splitListener.onOriginalLineReduced(OrderLineId.ofRepoId(original.getC_OrderLine_ID()));

		return OrderLineSplitResult.builder()
				.originalOrderLineId(OrderLineId.ofRepoId(original.getC_OrderLine_ID()))
				.newOrderLineId(OrderLineId.ofRepoId(newLine.getC_OrderLine_ID()))
				.build();
	}

	private void validate(
			@NonNull final I_C_Order order,
			@NonNull final I_C_OrderLine original,
			@NonNull final BigDecimal qtyToSplitOff)
	{
		if (!DocStatus.Completed.getCode().equals(order.getDocStatus()))
		{
			throw new AdempiereException(MSG_ORDER_NOT_COMPLETED);
		}
		if (qtyToSplitOff.signum() <= 0 || qtyToSplitOff.compareTo(original.getQtyOrdered()) >= 0)
		{
			throw new AdempiereException(MSG_QTY_TOO_LARGE, qtyToSplitOff, original.getQtyOrdered());
		}
		final BigDecimal newQtyOrdered = original.getQtyOrdered().subtract(qtyToSplitOff);
		if (newQtyOrdered.compareTo(original.getQtyDelivered()) < 0)
		{
			throw new AdempiereException(MSG_BELOW_DELIVERED, newQtyOrdered, original.getQtyDelivered());
		}
		if (newQtyOrdered.compareTo(original.getQtyInvoiced()) < 0)
		{
			throw new AdempiereException(MSG_BELOW_INVOICED, newQtyOrdered, original.getQtyInvoiced());
		}
	}

	/**
	 * Returns {@code max(Line) + 10} across all existing lines of the given order.
	 * Follows the step-10 convention used across the codebase (cf. OrderGroupRepository:714-718).
	 */
	private int computeNextLineNo(@NonNull final OrderId orderId)
	{
		final int maxLine = orderDAO.retrieveOrderLines(orderId)
				.stream()
				.mapToInt(I_C_OrderLine::getLine)
				.max()
				.orElse(0);
		return maxLine + 10;
	}
}
