package de.metas.doctextline;

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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository Tables: C_Doc_TextLine
 * Repository Cluster: DocTextLineRepository
 * <p>
 * Owns the position arithmetic of DESIGN.md § D-C (midpoint insert-above) and the scope default of § D-D.
 * Both operate on plain values supplied by the caller — this repository never queries article-line tables
 * (C_OrderLine / M_InOutLine), which it does not own (see {@link InsertAboveRequest}).
 */
@Repository
public class DocTextLineRepository
{
	private static final int LINE_SCALE = 4;

	/** Position given to the first text line ever inserted into an otherwise empty document (AC25). */
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
	 * Persists an inline edit of a text row's text and/or scope (webui {@code doc_textlines} task 6, DESIGN.md
	 * § D-E). {@code textLine} may be empty (AC24: an empty text line is legal and prints as a blank line).
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

	public List<DocTextLine> getByDocument(@NonNull final DocTextLineDocumentRef documentRef)
	{
		final IQueryBuilder<I_C_Doc_TextLine> queryBuilder = queryBL.createQueryBuilder(I_C_Doc_TextLine.class);
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
				.collect(Collectors.toList());
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
