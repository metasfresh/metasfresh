package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Expectation on the {@code M_Inventory_Candidate} rows of an HU (what the HU Manager "Dispose" action writes). */
@Value
@Builder
@Jacksonized
public class JsonInventoryCandidateExpectation
{
	/** exact number of candidate rows of the HU */
	@Nullable Integer count;
	/** {@code M_Inventory_Candidate.QtyToDispose}, summed over the HU's rows, compared numerically */
	@Nullable BigDecimal qtyToDispose;
	/** {@code M_Inventory_Candidate.IsWholeHU}: every row of the HU has this value */
	@Nullable Boolean isWholeHU;
}
