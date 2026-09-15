package de.metas.ui.web.doc_textlines;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * The three values {@code InsertAboveRequest} (package {@code de.metas.doctextline}) needs to place a new text
 * line in the merged article-line/text-line sequence relative to a selected reference row (DESIGN.md § D-C):
 * the reference row's own position, the position of the row immediately before it in the merged order (or
 * absent if it is the first row), and whether any article row precedes the reference row's position.
 * <p>
 * Computed by {@link DocTextLinesView#getInsertAbovePositions(de.metas.ui.web.window.datatypes.DocumentId)} --
 * the public surface a future insert-above quick-action process (tasks 8/9, living in a sibling
 * {@code doc_textlines.process} package per the {@code shipment_candidates_editor}/{@code products_proposal}
 * precedents) reaches through {@code getView()}, the same way {@code ProductsProposalViewBasedProcess} calls
 * public methods on {@code ProductsProposalView}.
 */
@Value
@Builder
public class InsertAbovePositions
{
	/** The case DESIGN.md/AC25 calls out: no rows at all, so nothing to reference. */
	public static final InsertAbovePositions EMPTY_DOCUMENT = InsertAbovePositions.builder()
			.referencePosition(null)
			.previousPosition(null)
			.articleLineExistsBeforeReferencePosition(false)
			.build();

	/** Position of the selected reference row; {@code null} only when the document has no rows at all. */
	@Nullable BigDecimal referencePosition;

	/** Position of the row immediately preceding the reference row in the merged sequence; {@code null} when the reference row is the first row. */
	@Nullable BigDecimal previousPosition;

	/** Whether any article row precedes the reference row's position. */
	boolean articleLineExistsBeforeReferencePosition;
}
