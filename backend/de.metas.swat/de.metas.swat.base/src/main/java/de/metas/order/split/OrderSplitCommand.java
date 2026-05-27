package de.metas.order.split;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutQuery;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
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

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	public OrderSplitResult split(@NonNull final OrderSplitRequest request)
	{
		final I_C_Order oldOrder = orderBL.getById(request.getOrderId());
		final List<I_C_OrderLine> oldLines = orderDAO.retrieveOrderLines(request.getOrderId());

		validate(request.getOrderId(), oldLines);

		final I_C_Order newOrder = createContinuationOrderHeader(oldOrder);

		// Line cloning lands in Task 8b
		throw new UnsupportedOperationException("Line cloning not yet wired (Task 8b)");
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
		// Guard 1: ≥1 completed shipment (DocStatus IN ('CO', 'CL'))
		final boolean hasCompletedShipment = inOutDAO.retrieveByQuery(
						InOutQuery.builder().orderId(oldOrderId).build())
				.anyMatch(io -> "CO".equals(io.getDocStatus()) || "CL".equals(io.getDocStatus()));
		if (!hasCompletedShipment)
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
