package de.metas.frontend_testing.masterdata.shipment;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reverses a completed shipment via the document engine's Reverse-Correct action — the faithful
 * equivalent of the desktop/WebUI "void shipment". The reverse restores the HU snapshot taken at
 * completion and replays one HU-trx line per aggregated transport unit through the single aggregate
 * VHU; {@code ShipmentScheduleHUTrxListener#trxLineProcessed} then emits one listener-shaped
 * {@code M_ShipmentSchedule_QtyPicked} row per replayed line. On an aggregate-HU shipment those rows
 * are identical on the partial unique-index tuple and would collide when the next shipment is generated
 * — unless the merge consolidates them into a single row.
 *
 * <p>The response reports the largest group of identical, not-yet-shipped active rows produced by the
 * reverse (see {@link JsonReverseShipmentResponse#getMaxIdenticalUnshippedQtyPickedRowsPerVhuTuple()}),
 * which is exactly the count that would collide on {@code M_ShipmentSchedule_QtyPicked_UI}.
 */
@Builder
public class ReverseShipmentCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final JsonReverseShipmentRequest request;

	public JsonReverseShipmentResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonReverseShipmentResponse execute0()
	{
		final InOutId inOutId = InOutId.ofRepoId(Integer.parseInt(request.getShipmentId()));
		final I_M_InOut shipment = InterfaceWrapperHelper.load(inOutId, I_M_InOut.class);
		final OrderId orderId = OrderId.ofRepoIdOrNull(shipment.getC_Order_ID());

		documentBL.processEx(shipment, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);

		InterfaceWrapperHelper.refresh(shipment);

		return JsonReverseShipmentResponse.builder()
				.id(String.valueOf(shipment.getM_InOut_ID()))
				.documentNo(shipment.getDocumentNo())
				.docStatus(shipment.getDocStatus())
				.maxIdenticalUnshippedQtyPickedRowsPerVhuTuple(computeMaxIdenticalUnshippedRows(orderId))
				.build();
	}

	/**
	 * Across all shipment schedules of the given order, count the active, not-yet-shipped
	 * {@code M_ShipmentSchedule_QtyPicked} rows grouped by the partial-unique-index tuple, and return
	 * the size of the largest group (the count that would collide once an {@code M_InOutLine_ID} is set).
	 */
	private int computeMaxIdenticalUnshippedRows(final OrderId orderId)
	{
		if (orderId == null)
		{
			return 0;
		}

		final ImmutableSet<OrderLineId> orderLineIds = orderDAO.retrieveOrderLines(orderId).stream()
				.map(I_C_OrderLine::getC_OrderLine_ID)
				.map(OrderLineId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		if (orderLineIds.isEmpty())
		{
			return 0;
		}

		final Set<Integer> scheduleIds = shipmentSchedulePA.getByOrderLineIds(orderLineIds).stream()
				.map(I_M_ShipmentSchedule::getM_ShipmentSchedule_ID)
				.collect(Collectors.toSet());
		if (scheduleIds.isEmpty())
		{
			return 0;
		}

		final List<I_M_ShipmentSchedule_QtyPicked> rows = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addInArrayFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, scheduleIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_ShipmentSchedule_QtyPicked.class);

		final Map<String, Integer> countByTuple = new HashMap<>();
		int max = 0;
		for (final I_M_ShipmentSchedule_QtyPicked row : rows)
		{
			// only rows that are not yet linked to a shipment line participate in the partial index
			if (row.getM_InOutLine_ID() > 0)
			{
				continue;
			}

			final String tuple = row.getM_ShipmentSchedule_ID()
					+ "|" + row.getVHU_ID()
					+ "|" + row.getM_TU_HU_ID()
					+ "|" + row.getM_LU_HU_ID()
					+ "|" + plain(row.getQtyLU())
					+ "|" + plain(row.getQtyTU())
					+ "|" + plain(row.getQtyPicked());
			final int count = countByTuple.merge(tuple, 1, Integer::sum);
			if (count > max)
			{
				max = count;
			}
		}
		return max;
	}

	/** Null-safe plain-string of a qty column (a row written by a path that skipped updateQtyTUAndQtyLU may have null). */
	private static String plain(final BigDecimal value)
	{
		return value != null ? value.stripTrailingZeros().toPlainString() : "0";
	}
}
