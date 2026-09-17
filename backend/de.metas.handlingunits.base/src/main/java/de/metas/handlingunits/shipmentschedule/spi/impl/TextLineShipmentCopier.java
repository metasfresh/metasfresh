/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.handlingunits.shipmentschedule.spi.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.doctextline.DocTextLine;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.TextLineScope;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOut;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOutLine;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * Copies an order's text lines ({@code C_Doc_TextLine}) onto a shipment's own new text-line rows once the
 * shipment's article lines are built.
 * <p>
 * A text line's "run" is the article lines beneath it (in the order's own merged Line sequence) up to the
 * next text line or the end of the order; consecutive text lines share the run of the last line of their own
 * block. A "belongs with the following lines" text line is carried onto the shipment only when at least one
 * article line of its run is on that shipment; a "whole document" text line is always carried, and prints at
 * the head of the shipment's own lines when none of its run made it onto this particular shipment. This
 * mirrors the carry rule already shipped for the order-checkup report's SQL function -- reimplemented here in
 * Java because this copy step runs before the shipment's own text lines are ever persisted anywhere, and works
 * from the shipment's just-built lines rather than a stored report record.
 * <p>
 * <b>Caller contract:</b> {@link #copyTextLinesToShipment(I_M_InOut)} must run AFTER
 * {@link IInOutDAO#unsetLineNos} has resolved every Line-number collision on {@code shipment}'s own lines.
 * The position this class derives for a carried text line is the anchor shipment line's own final
 * {@code Line} -- reading it before the collision is resolved would derive positions from numbers about to
 * become {@code 0}.
 */
class TextLineShipmentCopier
{
	/**
	 * Offset used to place a whole-document-scoped text line "at the head" of the shipment when none of its
	 * run made it onto this particular shipment: subtracting a constant well above any real Line value
	 * guarantees a result below every real shipment-line position, while preserving relative order among
	 * several such head lines (their own original order-side Line still differs).
	 */
	private static final BigDecimal HEAD_OFFSET = BigDecimal.valueOf(1_000_000);

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	// DocTextLineRepository needs no Spring wiring of its own (its only dependency is Services.get(IQueryBL.class)
	// in a field initializer), so it is constructed directly rather than resolved via SpringContextHolder -- this
	// class is created from InOutProducerFromShipmentScheduleWithHU, which is itself plain-constructed (not a
	// Spring bean) and runs in contexts (incl. plain JUnit tests) that never set up a Spring ApplicationContext.
	private final DocTextLineRepository docTextLineRepository = new DocTextLineRepository();

	/**
	 * Copies every carried text line of every order represented on {@code shipment} onto new
	 * {@code C_Doc_TextLine} rows against {@code shipment} itself. A no-op for a shipment with no order-linked
	 * lines, or whose order(s) carry no text lines.
	 */
	void copyTextLinesToShipment(@NonNull final I_M_InOut shipment)
	{
		final List<I_M_InOutLine> shipmentLinesWithOrderLine = inOutDAO.retrieveLines(shipment)
				.stream()
				.filter(line -> line.getC_OrderLine_ID() > 0)
				.collect(ImmutableList.toImmutableList());

		if (shipmentLinesWithOrderLine.isEmpty())
		{
			return;
		}

		final InOutId shipmentId = InOutId.ofRepoId(shipment.getM_InOut_ID());

		final ImmutableListMultimap<OrderId, I_M_InOutLine> shipmentLinesByOrder = shipmentLinesWithOrderLine
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						line -> OrderId.ofRepoId(line.getC_Order_ID()),
						line -> line));

		for (final OrderId orderId : shipmentLinesByOrder.keySet())
		{
			copyTextLinesForOrder(orderId, shipmentLinesByOrder.get(orderId), shipmentId);
		}
	}

	private void copyTextLinesForOrder(
			@NonNull final OrderId orderId,
			@NonNull final ImmutableList<I_M_InOutLine> shipmentLinesOfOrder,
			@NonNull final InOutId shipmentId)
	{
		final List<DocTextLine> textLines = docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId));
		if (textLines.isEmpty())
		{
			return;
		}

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderId);

		// the order's own line sequence sets the run boundaries -- every active order line, not only the ones
		// that made it onto this shipment.
		final List<BigDecimal> allOrderLineLines = orderLines.stream()
				.map(orderLine -> BigDecimal.valueOf(orderLine.getLine()))
				.collect(ImmutableList.toImmutableList());
		final List<BigDecimal> allTextLineLines = textLines.stream()
				.map(DocTextLine::getLine)
				.collect(ImmutableList.toImmutableList());

		// this shipment's own carried lines are matched by their ORDER line's own Line (never zeroed by a
		// collision) -- run membership and block boundaries are computed on the order's own line numbers, not
		// on the shipment line's own (possibly-just-zeroed) Line.
		final ImmutableMap<Integer, Integer> orderLineIdToOrderLine = orderLines.stream()
				.collect(ImmutableMap.toImmutableMap(I_C_OrderLine::getC_OrderLine_ID, I_C_OrderLine::getLine));

		for (final DocTextLine textLine : textLines)
		{
			copyOneTextLine(textLine, allOrderLineLines, allTextLineLines, orderLineIdToOrderLine, shipmentLinesOfOrder, shipmentId);
		}
	}

	private void copyOneTextLine(
			@NonNull final DocTextLine textLine,
			@NonNull final List<BigDecimal> allOrderLineLines,
			@NonNull final List<BigDecimal> allTextLineLines,
			@NonNull final ImmutableMap<Integer, Integer> orderLineIdToOrderLine,
			@NonNull final ImmutableList<I_M_InOutLine> shipmentLinesOfOrder,
			@NonNull final InOutId shipmentId)
	{
		final BigDecimal tlLine = textLine.getLine();

		// the next article line AT OR AFTER tlLine, anywhere in the order -- bounds the maximal run of
		// consecutive text lines starting at (or containing) tlLine.
		final BigDecimal nextArticleLine = allOrderLineLines.stream()
				.filter(line -> line.compareTo(tlLine) >= 0)
				.min(Comparator.naturalOrder())
				.orElse(null);

		// the last line of that block of consecutive text lines (tlLine itself always qualifies, so this
		// stream is never actually empty).
		final BigDecimal blockEndLine = allTextLineLines.stream()
				.filter(line -> line.compareTo(tlLine) >= 0)
				.filter(line -> nextArticleLine == null || line.compareTo(nextArticleLine) <= 0)
				.max(Comparator.naturalOrder())
				.orElse(tlLine);

		// the next text line strictly after the block -- open end of the run if there is none.
		final BigDecimal nextBlockStartLine = allTextLineLines.stream()
				.filter(line -> line.compareTo(blockEndLine) > 0)
				.min(Comparator.naturalOrder())
				.orElse(null);

		// this shipment's own lines whose ORDER line falls inside the run [blockEndLine, nextBlockStartLine).
		final ImmutableList<I_M_InOutLine> runShipmentLines = shipmentLinesOfOrder.stream()
				.filter(shipmentLine -> {
					final Integer orderLineLine = orderLineIdToOrderLine.get(shipmentLine.getC_OrderLine_ID());
					if (orderLineLine == null)
					{
						return false;
					}
					final BigDecimal line = BigDecimal.valueOf(orderLineLine);
					return line.compareTo(blockEndLine) >= 0
							&& (nextBlockStartLine == null || line.compareTo(nextBlockStartLine) < 0);
				})
				.collect(ImmutableList.toImmutableList());

		final boolean runPresentOnShipment = !runShipmentLines.isEmpty();

		// a "belongs with the following lines" text line is carried only when at least one article line of
		// its run is on this shipment; a "whole document" line is always carried (falls through below).
		if (textLine.getScope() == TextLineScope.Following && !runPresentOnShipment)
		{
			return;
		}

		final BigDecimal position = runPresentOnShipment
				? anchorPosition(textLine, runShipmentLines)
				: tlLine.subtract(HEAD_OFFSET); // whole-document scope, run absent from this shipment -> head

		docTextLineRepository.copyToDocument(DocTextLineDocumentRef.ofInOutId(shipmentId), textLine, position);
	}

	/**
	 * The position of the FIRST shipment line of {@code textLine}'s run -- the same {@code Line} value as that
	 * anchor line, relying on the merged article/text ordering convention established on the order side (a
	 * text line ranks before an article line tied at the same position) to place the copy immediately before
	 * it once a report reads both in that order.
	 *
	 * @throws AdempiereException when the anchor's own {@code Line} was reset to {@code 0} by
	 *         {@link IInOutDAO#unsetLineNos} (a Line-number collision -- this shipment aggregates order lines
	 *         whose numbers overlap). Deriving a position from {@code 0} would silently collapse the text line
	 *         to the top of the document regardless of where the user placed it, which this refuses instead.
	 */
	private static BigDecimal anchorPosition(
			@NonNull final DocTextLine textLine,
			@NonNull final ImmutableList<I_M_InOutLine> runShipmentLines)
	{
		final I_M_InOutLine anchor = runShipmentLines.stream()
				.min(Comparator.comparingInt(I_M_InOutLine::getLine))
				.orElseThrow(() -> new AdempiereException("unreachable: runShipmentLines is non-empty"));

		if (anchor.getLine() <= 0)
		{
			throw new AdempiereException("Cannot derive a shipment position for text line "
					+ textLine.getId() + " ('" + textLine.getTextLine() + "'): its anchor shipment line "
					+ "M_InOutLine_ID=" + anchor.getM_InOutLine_ID() + " had its own Line reset to 0 by a "
					+ "Line-number collision (this shipment aggregates order lines whose numbers overlap). "
					+ "No reliable position can be derived in that case.");
		}

		return BigDecimal.valueOf(anchor.getLine());
	}
}
