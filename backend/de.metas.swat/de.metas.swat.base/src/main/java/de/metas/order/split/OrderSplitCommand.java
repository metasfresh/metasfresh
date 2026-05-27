package de.metas.order.split;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderSplitCommand
{
	public static final AdMessageKey MSG_NO_SHIPMENTS = AdMessageKey.of("C_Order_Split_NoShipments");
	public static final AdMessageKey MSG_NOTHING_TO_SPLIT = AdMessageKey.of("C_Order_Split_NothingToSplit");

	private final QtyReservationService qtyReservationService;

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	public OrderSplitResult split(@NonNull final OrderSplitRequest request)
	{
		final I_C_Order oldOrder = orderBL.getById(request.getOrderId());
		final List<I_C_OrderLine> oldLines = orderDAO.retrieveOrderLines(request.getOrderId());

		validate(request.getOrderId(), oldLines);

		final I_C_Order newOrder = createContinuationOrderHeader(oldOrder);

		final int copiedLineCount = createContinuationOrderLines(oldLines, newOrder);

		closeOldShipmentSchedules(oldLines);
		closeOldReservations(oldLines);

		return OrderSplitResult.builder()
				.oldOrderId(de.metas.order.OrderId.ofRepoId(oldOrder.getC_Order_ID()))
				.newOrderId(de.metas.order.OrderId.ofRepoId(newOrder.getC_Order_ID()))
				.copiedLineCount(copiedLineCount)
				.build();
	}

	private void closeOldShipmentSchedules(@NonNull final List<I_C_OrderLine> oldLines)
	{
		final com.google.common.collect.ImmutableList<TableRecordReference> orderLineRefs = oldLines.stream()
				.map(ol -> TableRecordReference.of(
						org.compiere.model.I_C_OrderLine.Table_Name, ol.getC_OrderLine_ID()))
				.collect(com.google.common.collect.ImmutableList.toImmutableList());
		shipmentScheduleBL.closeShipmentSchedulesFor(orderLineRefs);
	}

	private void closeOldReservations(@NonNull final List<I_C_OrderLine> oldLines)
	{
		final List<OrderLineId> orderLineIds = oldLines.stream()
				.map(ol -> OrderLineId.ofRepoId(ol.getC_OrderLine_ID()))
				.collect(java.util.stream.Collectors.toList());
		qtyReservationService.closeAllActiveForOrderLines(orderLineIds);
	}

	private int createContinuationOrderLines(
			@NonNull final List<I_C_OrderLine> oldLines,
			@NonNull final I_C_Order newOrder)
	{
		int copiedLineCount = 0;
		int nextLineNo = 10;

		for (final I_C_OrderLine oldLine : oldLines)
		{
			final BigDecimal residue = oldLine.getQtyOrdered().subtract(oldLine.getQtyDelivered());
			if (residue.signum() <= 0)
			{
				continue;  // QtyOrdered <= QtyDelivered → stays on OLD SO (D0/D0')
			}

			final I_C_OrderLine newLine = InterfaceWrapperHelper.copy()
					.setFrom(oldLine)
					.setSkipCalculatedColumns(true)
					.copyToNew(I_C_OrderLine.class);

			newLine.setC_Order_ID(newOrder.getC_Order_ID());
			newLine.setLine(nextLineNo);
			nextLineNo += 10;

			newLine.setQtyDelivered(BigDecimal.ZERO);
			newLine.setQtyInvoiced(BigDecimal.ZERO);
			newLine.setDateDelivered(null);
			newLine.setDateInvoiced(null);
			newLine.setProcessed(false);

			// D1 — defence: clear project on the line
			newLine.setC_Project_ID(0);

			// D6 — let price-lookup interceptor populate pricing on save
			newLine.setIsManualPrice(false);

			// D12 — German audit suffix on Description (preserve existing text)
			final String oldDesc = newLine.getDescription();
			final String descSuffix = " (aus Position " + oldLine.getLine() + ")";
			newLine.setDescription(oldDesc == null ? descSuffix.trim() : oldDesc + descSuffix);

			// Set QtyEntered LAST so the BL price-lookup interceptor sees a fresh,
			// non-manual line with the correct qty.
			newLine.setQtyEntered(residue);

			InterfaceWrapperHelper.save(newLine);
			copiedLineCount++;
		}

		return copiedLineCount;
	}

	private I_C_Order createContinuationOrderHeader(@NonNull final I_C_Order oldOrder)
	{
		final I_C_Order newOrder = InterfaceWrapperHelper.copy()
				.setFrom(oldOrder)
				.setSkipCalculatedColumns(true)
				.copyToNew(I_C_Order.class);

		// Reset doc state
		newOrder.setDocStatus(DocStatus.Drafted.getCode());
		newOrder.setDocAction(IDocument.ACTION_Complete);
		newOrder.setDocumentNo(null);
		newOrder.setProcessed(false);
		newOrder.setIsApproved(false);
		newOrder.setIsDelivered(false);
		newOrder.setIsInvoiced(false);
		newOrder.setIsPrinted(false);
		newOrder.setPosted(false);
		newOrder.setGrandTotal(BigDecimal.ZERO);
		newOrder.setTotalLines(BigDecimal.ZERO);
		newOrder.setDatePrinted(null);
		newOrder.setRef_Order_ID(0);
		newOrder.setLink_Order_ID(0);

		// D1 — clear project
		newOrder.setC_Project_ID(0);
		// D5 — backref to old SO
		newOrder.setPOReference(oldOrder.getDocumentNo());

		// D11 — German audit suffix on Description (preserve existing text)
		final String oldDesc = newOrder.getDescription();
		final String suffix = " (Fortsetzung von " + oldOrder.getDocumentNo() + ")";
		newOrder.setDescription(oldDesc == null ? suffix.trim() : oldDesc + suffix);

		InterfaceWrapperHelper.save(newOrder);
		return newOrder;
	}

	private void validate(
			@NonNull final de.metas.order.OrderId oldOrderId,
			@NonNull final List<I_C_OrderLine> oldLines)
	{
		// Guard 1: ≥1 line has been shipped (QtyDelivered > 0).
		// Using QtyDelivered as the canonical "has shipments" indicator avoids querying
		// M_InOut directly (M_InOut.C_Order_ID is not always populated when shipments
		// are generated from shipment schedules — the relation lives in M_InOutLine.C_OrderLine_ID).
		final boolean hasAnyShipment = oldLines.stream()
				.anyMatch(ol -> ol.getQtyDelivered().signum() > 0);
		if (!hasAnyShipment)
		{
			throw new AdempiereException(MSG_NO_SHIPMENTS);
		}

		// Guard 2: ≥1 line with QtyOrdered > QtyDelivered
		final boolean hasUnshippedResidue = oldLines.stream()
				.anyMatch(ol -> ol.getQtyOrdered().compareTo(ol.getQtyDelivered()) > 0);
		if (!hasUnshippedResidue)
		{
			throw new AdempiereException(MSG_NOTHING_TO_SPLIT);
		}
	}
}
