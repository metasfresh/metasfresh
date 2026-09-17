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

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
 * <b>Stored position: tied with the run's anchor, not the text line's own.</b> A carried "run present" line
 * is stored at the same {@code Line} as the run's first usable shipment line (its "anchor") -- the print-time
 * tie-break (text ranks before article on an exact match, the same rule the order-checkup SQL relies on)
 * then renders it immediately before that member. That is the only choice safe on a shipment that aggregates
 * several orders: another order's own article line can sit anywhere in the numeric range between a text
 * line's original order-side position and its run, so a position taken from the order's own sequence is not
 * safe, even though it always is on a single-order shipment. Tying with an actual {@code Line} value already
 * present on THIS shipment is. When several text lines fold into one block sharing that run, only the LAST
 * one ties with the anchor; earlier members are placed at successive {@link #BLOCK_MEMBER_STEP}-sized steps
 * below it, in their original relative order -- distinct from one another, and still close enough to the
 * anchor that no other (always whole-number) {@code Line} can land between them. See
 * {@link #anchorTiedPosition} for the exact arithmetic and its bound.
 * <p>
 * <b>A shipment-line collision does not corrupt this.</b> A Line-number collision only ever touches an
 * article shipment line's own {@code Line} (never a text line's, which this class assigns); the anchor is
 * chosen only among a run's NON-collided members, so a collided member's {@code Line} is never read as a
 * position signal. What a collision CAN do is make every representative of a run on this shipment unusable
 * as that signal once {@link IInOutDAO#unsetLineNos} has acted on them -- see {@link #copyTextLinesToShipment}
 * for how that is handled: by consulting the exact collision set the producer already computed, not by
 * reading a shipment line's {@code Line} value (which a real database renumbers away from {@code 0}, see that
 * method's javadoc).
 * <p>
 * <b>Multi-order shipments:</b> when one shipment aggregates lines from several orders, this class groups the
 * shipment's lines by their own {@code C_Order_ID} and resolves each order's text lines against that order's
 * own text/article sequence, independently of any other order sharing the shipment. What keeps the result
 * correct once everything is merged onto one shipment is the stored position itself (see above): because it
 * ties with an actual {@code Line} already present on this shipment rather than a value from the order's own,
 * separate numeric space, another order's article line interleaving numerically between the two cannot land
 * between a text line and its run.
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

	/**
	 * Spacing between successive text lines of one block when they are placed below their run's anchor -- see
	 * {@link #anchorTiedPosition}. {@code C_Doc_TextLine.Line} is {@code numeric(10,4)}, so this is the
	 * smallest representable step; every real shipment-line {@code Line} is a whole number, so any position
	 * within one whole unit below an anchor is guaranteed free of them, regardless of what other order's lines
	 * share the shipment.
	 */
	private static final BigDecimal BLOCK_MEMBER_STEP = new BigDecimal("0.0001");

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
	 * <p>
	 * That collision set ({@code ShipmentLineNoInfo}) is fed a Line value {@code ShipmentLineBuilder} captures
	 * BEFORE it saves each shipment line, precisely so the save-time rewrite above can never be mistaken for a
	 * collision -- but the set itself is a field of the producer, not of one shipment: it is never reset
	 * between shipments, so a Line number that collides on one shipment of a multi-shipment producer run is
	 * registered against every shipment of that run, not scoped to the one it actually collided on. This class
	 * trusts the set as given; it cannot, from here, tell a same-run cross-shipment false positive apart from
	 * a genuine one.
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
		// against that order's own article/text sequence.
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

		// this shipment's own carried lines are matched by their ORDER line's own Line (never touched by a
		// collision) -- run membership and block boundaries are computed on the order's own line numbers, not
		// on a shipment line's own Line.
		final ImmutableMap<Integer, Integer> orderLineIdToOrderLine = orderLines.stream()
				.collect(ImmutableMap.toImmutableMap(I_C_OrderLine::getC_OrderLine_ID, I_C_OrderLine::getLine));

		// consecutive text lines with no active order line strictly between them fold into one block, sharing
		// the run of the block's LAST member. Grouped once here (rather than independently per text line)
		// because every member of a block needs to know the block's full size and its own rank within it, to
		// keep several text lines of the same block at distinct, correctly-ordered positions -- see
		// copyOneBlock / anchorTiedPosition.
		final List<ImmutableList<DocTextLine>> blocks = groupIntoBlocks(textLines, allOrderLineLines);

		for (int blockIndex = 0; blockIndex < blocks.size(); blockIndex++)
		{
			final ImmutableList<DocTextLine> block = blocks.get(blockIndex);
			// the next text line strictly after this block -- open end of the run if there is none.
			final BigDecimal nextBlockStartLine = blockIndex + 1 < blocks.size()
					? blocks.get(blockIndex + 1).get(0).getLine()
					: null;
			copyOneBlock(block, nextBlockStartLine, orderLineIdToOrderLine, shipmentLinesOfOrder, shipmentId, collidedShipmentLineIds);
		}
	}

	/**
	 * Groups {@code textLines} (already sorted by {@code Line}) into the maximal runs of consecutive text
	 * lines with no active order line strictly between two consecutive members -- the same "block folding"
	 * rule the order-checkup SQL's {@code text_runs} CTE applies, computed once per order rather than
	 * independently (and redundantly) per text line.
	 */
	private static List<ImmutableList<DocTextLine>> groupIntoBlocks(
			@NonNull final List<DocTextLine> textLines,
			@NonNull final List<BigDecimal> allOrderLineLines)
	{
		final ImmutableList.Builder<ImmutableList<DocTextLine>> blocks = ImmutableList.builder();
		List<DocTextLine> currentBlock = null;
		BigDecimal nextArticleLineAfterCurrentBlock = null;
		for (final DocTextLine textLine : textLines)
		{
			final boolean continuesCurrentBlock = currentBlock != null
					&& (nextArticleLineAfterCurrentBlock == null
							|| textLine.getLine().compareTo(nextArticleLineAfterCurrentBlock) <= 0);
			if (continuesCurrentBlock)
			{
				currentBlock.add(textLine);
			}
			else
			{
				if (currentBlock != null)
				{
					blocks.add(ImmutableList.copyOf(currentBlock));
				}
				currentBlock = new ArrayList<>();
				currentBlock.add(textLine);
			}
			nextArticleLineAfterCurrentBlock = nextArticleLineAtOrAfter(textLine.getLine(), allOrderLineLines);
		}
		if (currentBlock != null)
		{
			blocks.add(ImmutableList.copyOf(currentBlock));
		}
		return blocks.build();
	}

	// the next article line AT OR AFTER fromLine, anywhere in the order. Inclusive (>=): a text line tied
	// with an article at the same position still caps its own block at that article, per the order-side
	// tie-break (text ranks before article on a tie, so the tied article is the boundary, not a later one).
	private static BigDecimal nextArticleLineAtOrAfter(@NonNull final BigDecimal fromLine, @NonNull final List<BigDecimal> allOrderLineLines)
	{
		return allOrderLineLines.stream()
				.filter(line -> line.compareTo(fromLine) >= 0)
				.min(Comparator.naturalOrder())
				.orElse(null);
	}

	private void copyOneBlock(
			@NonNull final ImmutableList<DocTextLine> block,
			@Nullable final BigDecimal nextBlockStartLine,
			@NonNull final ImmutableMap<Integer, Integer> orderLineIdToOrderLine,
			@NonNull final ImmutableList<I_M_InOutLine> shipmentLinesOfOrder,
			@NonNull final InOutId shipmentId,
			@NonNull final ImmutableSet<InOutLineId> collidedShipmentLineIds)
	{
		final BigDecimal blockEndLine = block.get(block.size() - 1).getLine(); // the block's last member, by construction

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

		// membership: counts a collided run member as present -- it IS on the shipment, its Line is merely not
		// a usable position signal. A "belongs with the following lines" line is carried only when its run is
		// non-empty; a "whole document" line is always carried (falls through below).
		final boolean runPresentOnShipment = !runShipmentLines.isEmpty();

		// position reference: the SMALLEST Line among the run's members that did NOT collide. A single
		// collided member does not poison the whole run when another, usable member is present -- only when
		// EVERY run member present collided is this absent, same as an absent run (see anchorTiedPosition).
		final Optional<Integer> usableAnchorLine = runShipmentLines.stream()
				.filter(shipmentLine -> !collidedShipmentLineIds.contains(InOutLineId.ofRepoId(shipmentLine.getM_InOutLine_ID())))
				.map(I_M_InOutLine::getLine)
				.min(Comparator.naturalOrder());

		final int blockSize = block.size();
		for (int rank = 0; rank < blockSize; rank++)
		{
			final DocTextLine textLine = block.get(rank);
			if (textLine.getScope() == TextLineScope.Following && !runPresentOnShipment)
			{
				continue;
			}

			final int rankInBlock = rank;
			final BigDecimal position = usableAnchorLine
					.map(anchorLine -> anchorTiedPosition(anchorLine, blockSize, rankInBlock))
					// run absent, or every member present collided -> print at the head, at this line's own
					// original position (preserves relative order among several such head lines)
					.orElseGet(() -> textLine.getLine().subtract(HEAD_OFFSET));

			docTextLineRepository.copyToDocument(DocTextLineDocumentRef.ofInOutId(shipmentId), textLine, position);
		}
	}

	/**
	 * Position for the {@code rank}-th (0-based, in ascending original order) of {@code blockSize} text lines
	 * that share one run whose usable anchor is {@code anchorLine}: the LAST member of the block (highest
	 * rank) ties exactly with the anchor -- see the class javadoc for why a tie, rather than the text line's
	 * own original position, is what "immediately before the run" requires once several orders can share a
	 * shipment. Earlier members of the same block are placed at successive {@link #BLOCK_MEMBER_STEP} steps
	 * below the anchor, in their original relative order.
	 * <p>
	 * <b>Bound:</b> this assumes a block never holds more than {@code 1 / }{@value #BLOCK_MEMBER_STEP} (10000)
	 * text lines -- an absurd number in practice; beyond it, the earliest members would step more than one
	 * whole unit below the anchor and could collide with whatever real (whole-number) {@code Line} sits just
	 * below it.
	 */
	private static BigDecimal anchorTiedPosition(final int anchorLine, final int blockSize, final int rank)
	{
		final int stepsBelowAnchor = blockSize - 1 - rank;
		return BigDecimal.valueOf(anchorLine).subtract(BLOCK_MEMBER_STEP.multiply(BigDecimal.valueOf(stepsBelowAnchor)));
	}
}
