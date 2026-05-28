package de.metas.order.split;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public OrderSplitResult split(@NonNull final OrderSplitRequest request)
	{
		final I_C_Order oldOrder = orderBL.getById(request.getOrderId());
		final List<I_C_OrderLine> oldLines = orderDAO.retrieveOrderLines(request.getOrderId());

		validate(oldLines);

		final I_C_Order newOrder = createContinuationOrderHeader(oldOrder);

		final int copiedLineCount = createContinuationOrderLines(oldLines, newOrder);

		final List<OrderLineId> oldOrderLineIds = toOrderLineIds(oldLines);
		closeOldShipmentSchedules(oldOrderLineIds);
		closeOldReservations(oldOrderLineIds);

		return OrderSplitResult.builder()
				.oldOrderId(OrderId.ofRepoId(oldOrder.getC_Order_ID()))
				.newOrderId(OrderId.ofRepoId(newOrder.getC_Order_ID()))
				.copiedLineCount(copiedLineCount)
				.build();
	}

	private static List<OrderLineId> toOrderLineIds(@NonNull final List<I_C_OrderLine> oldLines)
	{
		return oldLines.stream()
				.map(ol -> OrderLineId.ofRepoId(ol.getC_OrderLine_ID()))
				.collect(Collectors.toList());
	}

	private void closeOldShipmentSchedules(@NonNull final List<OrderLineId> oldOrderLineIds)
	{
		// Load all M_ShipmentSchedules for the OLD order lines and close (IsClosed=Y) every
		// non-closed schedule — including already-Processed ones (those of fully-delivered
		// lines). This uniformly freezes the OLD SO for further shipping. We deliberately
		// avoid IShipmentScheduleBL.closeShipmentSchedulesFor because that API throws on
		// Processed schedules, which is too strict for our multi-line case where some
		// schedules complete naturally via shipment.
		if (oldOrderLineIds.isEmpty())
		{
			return;
		}
		final List<I_M_ShipmentSchedule> schedules = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID, oldOrderLineIds)
				.create()
				.list();
		for (final I_M_ShipmentSchedule schedule : schedules)
		{
			if (schedule.isClosed())
			{
				continue;  // already closed → idempotent skip
			}
			// The singular closeShipmentSchedule API does NOT check Processed, so it works
			// for both fully-shipped and partial-shipment cases.
			shipmentScheduleBL.closeShipmentSchedule(schedule);
		}
	}

	private void closeOldReservations(@NonNull final List<OrderLineId> oldOrderLineIds)
	{
		qtyReservationService.closeAllActiveForOrderLines(oldOrderLineIds);
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

			// DateOrdered is denormalized from C_Order and has a NOT-NULL constraint on C_OrderLine.
			// setSkipCalculatedColumns(true) skips it during the copy → we must set it explicitly
			// from the new header (which itself was copied from the old SO, so the date is preserved).
			newLine.setDateOrdered(newOrder.getDateOrdered());

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

	private void validate(@NonNull final List<I_C_OrderLine> oldLines)
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
