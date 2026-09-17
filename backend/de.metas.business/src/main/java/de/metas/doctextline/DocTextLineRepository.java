package de.metas.doctextline;

import com.google.common.collect.ImmutableList;
import de.metas.inout.InOutId;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Doc_TextLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

/**
 * Repository Tables: C_Doc_TextLine
 * Repository Cluster: DocTextLineRepository
 * <p>
 * Owns the position arithmetic of a midpoint insert-above and the scope default derived from it. Both operate
 * on plain values supplied by the caller — this repository never queries article-line tables (C_OrderLine /
 * M_InOutLine), which it does not own (see {@link InsertAboveRequest}).
 */
@Repository
public class DocTextLineRepository
{
	/** Four decimals, and inserts are midpoints: a gap survives ~13 successive inserts at the same spot before {@link #computeInsertAbovePosition} refuses it. */
	/** Four decimals, and inserts are midpoints: a gap survives ~13 successive inserts at the same spot before {@link #computeInsertAbovePosition} refuses it. */
	private static final int LINE_SCALE = 4;

	/** Position given to the first text line ever inserted into an otherwise empty document. */
	private static final BigDecimal FIRST_POSITION_IN_EMPTY_DOCUMENT = BigDecimal.ONE.setScale(LINE_SCALE);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public DocTextLine insertAbove(@NonNull final InsertAboveRequest request)
	{
		final BigDecimal newPosition = computeInsertAbovePosition(request.getReferencePosition(), request.getPreviousPosition());
		final TextLineScope scope = request.isArticleLineExistsBeforeReferencePosition() ? TextLineScope.Following : TextLineScope.Document;

		final I_C_Doc_TextLine record = InterfaceWrapperHelper.newInstance(I_C_Doc_TextLine.class);
		setDocumentRef(record, request.getDocumentRef());
		record.setTextLine(request.getTextLine());
		record.setLine(newPosition);
		record.setTextLineScope(scope.getCode());
		InterfaceWrapperHelper.save(record);

		return ofRecord(record);
	}

	/**
	 * Midpoint between {@code referencePosition} (the row inserted above) and {@code previousPosition} (the
	 * row before it in the merged sequence); halves {@code referencePosition} when there is no previous row;
	 * returns {@link #FIRST_POSITION_IN_EMPTY_DOCUMENT} when there is no reference row either.
	 *
	 * @throws AdempiereException when the gap between the two positions is exhausted at scale {@value #LINE_SCALE}
	 *         — the rounded midpoint would collide with {@code referencePosition} or {@code previousPosition},
	 *         producing a duplicate {@code Line} that nothing in the schema rejects.
	 */
	static BigDecimal computeInsertAbovePosition(@Nullable final BigDecimal referencePosition, @Nullable final BigDecimal previousPosition)
	{
		if (referencePosition == null)
		{
			return FIRST_POSITION_IN_EMPTY_DOCUMENT;
		}

		final BigDecimal newPosition = previousPosition == null
				? referencePosition.divide(BigDecimal.valueOf(2), LINE_SCALE, RoundingMode.HALF_UP)
				: previousPosition.add(referencePosition).divide(BigDecimal.valueOf(2), LINE_SCALE, RoundingMode.HALF_UP);

		final boolean collidesWithReference = newPosition.compareTo(referencePosition) == 0;
		final boolean collidesWithPrevious = previousPosition != null && newPosition.compareTo(previousPosition) == 0;
		if (collidesWithReference || collidesWithPrevious)
		{
			throw new AdempiereException("Cannot insert a text line between " + previousPosition + " and " + referencePosition
					+ ": the position gap is exhausted at scale " + LINE_SCALE + " (the midpoint " + newPosition
					+ " would duplicate an existing position). Move a neighbouring row first to free up space.");
		}

		return newPosition;
	}

	/**
	 * Position for a text row moving past a neighbouring row whose own position must not change -- the case
	 * where a plain two-way swap is not available because the neighbour's position is not this repository's to
	 * write (an article line's position belongs to the order/shipment line table, never touched here).
	 * <p>
	 * {@code lowerPosition} and {@code upperPosition} are the positions the result must land strictly between;
	 * either may be absent when the moved row is landing at the very start or very end of the sequence.
	 *
	 * @throws AdempiereException when both bounds are given and the gap between them is exhausted at scale
	 *         {@value #LINE_SCALE} -- same guard as {@link #computeInsertAbovePosition}, reused here because
	 *         the bounded case is the identical computation with the arguments' roles swapped.
	 */
	public static BigDecimal computePositionBetween(@Nullable final BigDecimal lowerPosition, @Nullable final BigDecimal upperPosition)
	{
		if (upperPosition != null)
		{
			return computeInsertAbovePosition(upperPosition, lowerPosition);
		}
		if (lowerPosition == null)
		{
			return FIRST_POSITION_IN_EMPTY_DOCUMENT;
		}
		return lowerPosition.add(BigDecimal.ONE.setScale(LINE_SCALE));
	}

	/**
	 * Whether any article line sits before {@code referencePosition} — the predicate that decides a new
	 * text line's default scope.
	 *
	 * @param articleLinePositions must contain every article-line position strictly less than
	 *        {@code referencePosition}; further positions are harmless, and order does not matter.
	 */
	public static boolean articleLineExistsBefore(
			@NonNull final Collection<BigDecimal> articleLinePositions,
			@NonNull final BigDecimal referencePosition)
	{
		return articleLinePositions.stream().anyMatch(position -> position.compareTo(referencePosition) < 0);
	}

	/**
	 * Repositions a text line to an already-computed position -- used when a move needs to place the row on the
	 * other side of a neighbour whose own position must stay untouched. Leaves {@code TextLineScope} alone:
	 * moving a text line must never change its stored scope.
	 */
	public void updatePosition(@NonNull final DocTextLineId id, @NonNull final BigDecimal newPosition)
	{
		final I_C_Doc_TextLine record = InterfaceWrapperHelper.load(id.getRepoId(), I_C_Doc_TextLine.class);
		record.setLine(newPosition);
		InterfaceWrapperHelper.save(record);
	}

	/**
	 * Persists an inline edit of a text row's text and/or scope. {@code textLine} may be empty -- an empty
	 * text line is legal and prints as a blank line.
	 */
	public void updateTextAndScope(
			@NonNull final DocTextLineId id,
			@Nullable final String textLine,
			@NonNull final TextLineScope scope)
	{
		final I_C_Doc_TextLine record = InterfaceWrapperHelper.load(id.getRepoId(), I_C_Doc_TextLine.class);
		record.setTextLine(textLine);
		record.setTextLineScope(scope.getCode());
		InterfaceWrapperHelper.save(record);
	}

	public void swapPositions(@NonNull final DocTextLineId id1, @NonNull final DocTextLineId id2)
	{
		final I_C_Doc_TextLine record1 = InterfaceWrapperHelper.load(id1.getRepoId(), I_C_Doc_TextLine.class);
		final I_C_Doc_TextLine record2 = InterfaceWrapperHelper.load(id2.getRepoId(), I_C_Doc_TextLine.class);

		final BigDecimal line1 = record1.getLine();
		record1.setLine(record2.getLine());
		record2.setLine(line1);

		InterfaceWrapperHelper.save(record1);
		InterfaceWrapperHelper.save(record2);
	}

	/**
	 * Persists an independent COPY of {@code source} onto a different document, at an explicit
	 * {@code position} and with the source's own text and scope carried over verbatim (never rederived here --
	 * the caller owns that decision). Used when a document's text lines are carried onto a document derived
	 * from it (e.g. a shipment created from an order). The new row is independent from the moment it is
	 * written: it has no link back to {@code source}, so an edit to {@code source} afterwards never touches it.
	 */
	public DocTextLine copyToDocument(
			@NonNull final DocTextLineDocumentRef targetDocumentRef,
			@NonNull final DocTextLine source,
			@NonNull final BigDecimal position)
	{
		final I_C_Doc_TextLine record = InterfaceWrapperHelper.newInstance(I_C_Doc_TextLine.class);
		setDocumentRef(record, targetDocumentRef);
		record.setTextLine(source.getTextLine());
		record.setLine(position);
		record.setTextLineScope(source.getScope().getCode());
		InterfaceWrapperHelper.save(record);

		return ofRecord(record);
	}

	public ImmutableList<DocTextLine> getByDocument(@NonNull final DocTextLineDocumentRef documentRef)
	{
		final IQueryBuilder<I_C_Doc_TextLine> queryBuilder = queryBL.createQueryBuilder(I_C_Doc_TextLine.class)
				.addOnlyActiveRecordsFilter();
		if (documentRef.getOrderId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Doc_TextLine.COLUMNNAME_C_Order_ID, documentRef.getOrderId());
		}
		else
		{
			queryBuilder.addEqualsFilter(I_C_Doc_TextLine.COLUMNNAME_M_InOut_ID, documentRef.getInOutId());
		}

		return queryBuilder
				.orderBy()
				.addColumn(I_C_Doc_TextLine.COLUMNNAME_Line)
				.addColumn(I_C_Doc_TextLine.COLUMNNAME_C_Doc_TextLine_ID)
				.endOrderBy()
				.create()
				.list()
				.stream()
				.map(DocTextLineRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public void deleteById(@NonNull final DocTextLineId id)
	{
		final I_C_Doc_TextLine record = InterfaceWrapperHelper.load(id.getRepoId(), I_C_Doc_TextLine.class);
		InterfaceWrapperHelper.delete(record);
	}

	private static void setDocumentRef(@NonNull final I_C_Doc_TextLine record, @NonNull final DocTextLineDocumentRef documentRef)
	{
		if (documentRef.getOrderId() != null)
		{
			record.setC_Order_ID(documentRef.getOrderId().getRepoId());
		}
		else
		{
			record.setM_InOut_ID(documentRef.getInOutId().getRepoId());
		}
	}

	private static DocTextLine ofRecord(@NonNull final I_C_Doc_TextLine record)
	{
		final DocTextLineDocumentRef documentRef = record.getC_Order_ID() > 0
				? DocTextLineDocumentRef.ofOrderId(OrderId.ofRepoId(record.getC_Order_ID()))
				: DocTextLineDocumentRef.ofInOutId(InOutId.ofRepoId(record.getM_InOut_ID()));

		return DocTextLine.builder()
				.id(DocTextLineId.ofRepoId(record.getC_Doc_TextLine_ID()))
				.documentRef(documentRef)
				.textLine(record.getTextLine())
				.line(record.getLine())
				.scope(TextLineScope.ofCode(record.getTextLineScope()))
				.build();
	}
}
