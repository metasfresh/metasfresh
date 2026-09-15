package de.metas.doctextline;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Everything {@link DocTextLineRepository#insertAbove(InsertAboveRequest)} needs to place a new
 * {@link DocTextLine} in the merged order/article-line sequence (DESIGN.md § D-C) and derive its
 * {@link TextLineScope} default (§ D-D). The merged sequence spans a table this repository does not own
 * (article lines), so the caller — which already loaded that merged view — supplies the two positions and
 * the scope-driving fact as plain values; this repository never queries article lines itself.
 */
@Value
@Builder
public class InsertAboveRequest
{
	@NonNull DocTextLineDocumentRef documentRef;
	@Nullable String textLine;

	/** Position of the row selected to insert above; {@code null} when the document has no rows at all (AC25). */
	@Nullable BigDecimal referencePosition;

	/** Position of the row immediately preceding {@link #referencePosition} in the merged sequence; {@code null} when the reference row is the first row. */
	@Nullable BigDecimal previousPosition;

	/** Whether any article line of the document has a smaller position than the row being inserted above (DESIGN.md § D-D). */
	boolean articleLineExistsBeforeNewPosition;
}
