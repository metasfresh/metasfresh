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
import com.google.common.collect.ImmutableSet;
import de.metas.doctextline.DocTextLine;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.TextLineScope;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.model.I_M_InOut;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
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
 * <b>Stored position, and why it is the text line's own -- not the anchor's:</b> a carried line is stored at
 * its OWN original {@code Line} (matching the order-checkup SQL, which emits {@code tl_line} rather than an
 * anchor's position). That is provably still "immediately before" the run: the run's own lower bound is
 * always strictly greater than the text line's {@code Line} (that gap is exactly what the run-boundary
 * computation below carves out), so the text line's own value sorts ahead of every run member without having
 * to know which one is numerically first. It also keeps two text lines of one block at distinct stored
 * positions, rather than collapsing them onto one shared anchor value and leaning on an undocumented
 * {@code C_Doc_TextLine_ID} tie-break.
 * <p>
 * <b>A shipment-line collision does not corrupt this.</b> A Line-number collision only ever touches an
 * article shipment line's own {@code Line} (never a text line's, which this class assigns); since the stored
 * position never reads that value, a collision cannot corrupt it. What a collision CAN do is make the run's
 * only representative(s) on this shipment unusable as an "is a real member actually here" signal once
 * {@link IInOutDAO#unsetLineNos} has acted on them -- see {@link #copyTextLinesToShipment} for how that is
 * handled: by consulting the exact collision set the producer already computed, not by reading a shipment
 * line's {@code Line} value (which a real database renumbers away from {@code 0}, see that method's javadoc).
 * <p>
 * <b>Multi-order shipments (a decision, not an oversight):</b> when one shipment aggregates lines from several
 * orders, this class groups the shipment's lines by their own {@code C_Order_ID} and processes each order's
 * text lines independently, against that order's own text/article sequence. The result on the shipment is
 * that both orders' copied text lines are interleaved by their stored {@code Line} values with the shipment's
 * article lines, exactly as the order side already interleaves text and article rows sharing one numeric
 * position space. This is deliberate: a text line belongs to exactly one order and is positioned relative to
 * that order's own article lines wherever they land on the shipment; nothing about a second order sharing the
 * same shipment changes what "immediately before this run" means for the first order's text. The
 * Line-number-collision guard remains the loud-failure path for the case the numeric spaces actually clash.
 */
class TextLineShipmentCopier
{
	/**
	 * Offset used to place a text line "at the head" of the shipment: no run member is usable as a position
	 * reference, either because none of the run is on this shipment (a whole-document line whose run is
	 * absent) or because every run member present is part of a Line-number collision. Subtracting a constant
	 * well above any real Line value guarantees a result below every real shipment-line position, while
	 * preserving relative order among several such head lines (their own original Line still differs).
	 * <p>
	 * <b>Bound:</b> the result is stored into {@code C_Doc_TextLine.Line numeric(10,4)}, whose representable
	 * minimum is {@code -999999.9999}. Every writer of a {@code C_Doc_TextLine.Line} on the order side keeps
	 * positions at or above {@code 0.0001} (the insert-above arithmetic throws before rounding a midpoint down
	 * to {@code 0}), so the smallest input this offset ever subtracts from is {@code 0.0001}, landing exactly
	 * on the representable minimum with zero headroom. This value MUST NOT be applied a second time to an
	 * already-head-placed line (e.g. by a future copier reusing this pattern on this class's own output) --
	 * doing so overflows the column and aborts the write. The order-checkup report's SQL function
	 * (the sibling, first implementation of this same carry rule) projects the identical literal for the same
	 * reason and at the same value; it never stores it, so it carries no such bound.
	 */
	private static final BigDecimal HEAD_OFFSET = BigDecimal.valueOf(1_000_000);

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	// DocTextLineRepository needs no Spring wiring of its own (its only dependency is Services.get(IQueryBL.class)
	// in a field initializer), but this class is created from InOutProducerFromShipmentScheduleWithHU, which is
	// itself plain-constructed (not a Spring bean) and runs in contexts (incl. plain JUnit tests) that never set
	// up a Spring ApplicationContext -- getBeanOrSupply resolves the managed bean when one exists and falls back
	// to a plain instance otherwise, which is the standing idiom for exactly this situation.
	private final DocTextLineRepository docTextLineRepository =
			SpringContextHolder.getBeanOrSupply(DocTextLineRepository.class, DocTextLineRepository::new);

	/**
	 * Copies every carried text line of every order represented on {@code shipment} onto new
	 * {@code C_Doc_TextLine} rows against {@code shipment} itself. A no-op for a shipment with no order-linked
	 * lines, or whose order(s) carry no text lines.
	 * <p>
	 * Must run AFTER {@link IInOutDAO#unsetLineNos} has been called for {@code collidedShipmentLineIds} (the
	 * caller's own collision set, computed by {@code ShipmentLineNoInfo} before that call). {@code
	 * collidedShipmentLineIds} -- not a shipment line's own {@code Line} value -- is what this class consults
	 * to decide whether a run's representative is usable as a position signal: on a real database,
	 * {@code unsetLineNos} does not leave a colliding line at {@code Line = 0} (legacy {@code MInOutLine}'s
	 * {@code beforeSave} rewrites a zero Line to {@code MAX(Line)+10} before the row ever reaches disk, so the
	 * collided line ends up renumbered to the end of the document -- an ordinary-looking, non-zero value that
	 * is indistinguishable from a genuinely-last line once written). Reading {@code Line} after that rewrite
	 * can therefore never detect the collision; consulting the caller's own pre-computed collision set can.
	 */
	void copyTextLinesToShipment(
			@NonNull final I_M_InOut shipment,
			@NonNull final ImmutableSet<InOutLineId> collidedShipmentLineIds)
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

		// see the class javadoc "Multi-order shipments" -- each order's text lines are resolved independently,
		// against that order's own article/text sequence; the interleaving that results on the shipment is the
		// deliberate outcome, not an accident of iteration order.
		for (final OrderId orderId : shipmentLinesByOrder.keySet())
		{
			copyTextLinesForOrder(orderId, shipmentLinesByOrder.get(orderId), shipmentId, collidedShipmentLineIds);
		}
	}

	private void copyTextLinesForOrder(
			@NonNull final OrderId orderId,
			@NonNull final ImmutableList<I_M_InOutLine> shipmentLinesOfOrder,
			@NonNull final InOutId shipmentId,
			@NonNull final ImmutableSet<InOutLineId> collidedShipmentLineIds)
	{
		final List<DocTextLine> textLines = docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId));
		if (textLines.isEmpty())
		{
			return;
		}

		// active only -- matches the order-checkup SQL, which filters IsActive='Y' in every one of its four
		// lookups. An inactive order line must not shift a run boundary on either side.
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderId)
				.stream()
				.filter(I_C_OrderLine::isActive)
				.collect(ImmutableList.toImmutableList());

		// the order's own line sequence sets the run boundaries -- every active order line, not only the ones
		// that made it onto this shipment.
		final List<BigDecimal> allOrderLineLines = orderLines.stream()
				.map(orderLine -> BigDecimal.valueOf(orderLine.getLine()))
				.collect(ImmutableList.toImmutableList());
		final List<BigDecimal> allTextLineLines = textLines.stream()
				.map(DocTextLine::getLine)
				.collect(ImmutableList.toImmutableList());

		// this shipment's own carried lines are matched by their ORDER line's own Line (never touched by a
		// collision) -- run membership and block boundaries are computed on the order's own line numbers, not
		// on a shipment line's own Line.
		final ImmutableMap<Integer, Integer> orderLineIdToOrderLine = orderLines.stream()
				.collect(ImmutableMap.toImmutableMap(I_C_OrderLine::getC_OrderLine_ID, I_C_OrderLine::getLine));

		for (final DocTextLine textLine : textLines)
		{
			copyOneTextLine(textLine, allOrderLineLines, allTextLineLines, orderLineIdToOrderLine,
					shipmentLinesOfOrder, shipmentId, collidedShipmentLineIds);
		}
	}

	private void copyOneTextLine(
			@NonNull final DocTextLine textLine,
			@NonNull final List<BigDecimal> allOrderLineLines,
			@NonNull final List<BigDecimal> allTextLineLines,
			@NonNull final ImmutableMap<Integer, Integer> orderLineIdToOrderLine,
			@NonNull final ImmutableList<I_M_InOutLine> shipmentLinesOfOrder,
			@NonNull final InOutId shipmentId,
			@NonNull final ImmutableSet<InOutLineId> collidedShipmentLineIds)
	{
		final BigDecimal tlLine = textLine.getLine();

		// the next article line AT OR AFTER tlLine, anywhere in the order -- bounds the maximal run of
		// consecutive text lines starting at (or containing) tlLine. Inclusive (>=): a text line tied with an
		// article at the same position still caps its own block at that article, per the order-side tie-break
		// (text ranks before article on a tie, so the tied article is the boundary, not a later one).
		final BigDecimal nextArticleLine = allOrderLineLines.stream()
				.filter(line -> line.compareTo(tlLine) >= 0)
				.min(Comparator.naturalOrder())
				.orElse(null);

		// the last line of that block of consecutive text lines (tlLine itself always qualifies, so this
		// stream is never actually empty). Inclusive upper bound (<=): a text line tied with nextArticleLine
		// still belongs to this block.
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
		// Inclusive lower bound (>=): an article line tied with blockEndLine belongs to this run (it is
		// "beneath" the text per the same tie-break that caps the block above).
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

		// membership (AC: "prints if at least one article line of its run is on that shipment"): counts a
		// collided run member as present -- it IS on the shipment, its Line is merely not a usable position
		// signal. A "belongs with the following lines" line is carried only when its run is non-empty; a
		// "whole document" line is always carried (falls through below).
		final boolean runPresentOnShipment = !runShipmentLines.isEmpty();
		if (textLine.getScope() == TextLineScope.Following && !runPresentOnShipment)
		{
			return;
		}

		// position: a run member usable as a signal exists only if at least one of them was NOT part of a
		// Line-number collision. A single collided member does not poison the whole run when other, usable
		// members are present -- only when EVERY run member collided does this fall back to the head offset.
		final boolean usableRunMemberExists = runShipmentLines.stream()
				.anyMatch(shipmentLine -> !collidedShipmentLineIds.contains(InOutLineId.ofRepoId(shipmentLine.getM_InOutLine_ID())));

		final BigDecimal position = usableRunMemberExists
				? tlLine // the text line's own position -- see the class javadoc for why this, not an anchor's Line
				: tlLine.subtract(HEAD_OFFSET); // run absent, or every member present collided -> print at the head

		docTextLineRepository.copyToDocument(DocTextLineDocumentRef.ofInOutId(shipmentId), textLine, position);
	}
}
