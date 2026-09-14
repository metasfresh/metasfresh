package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonInventoryExpectation
{
	@Nullable Boolean isExists;
	@Nullable String docStatus;
	@Nullable String description;
	/**
	 * Exact number of inventory documents for the HU matching {@link #description} (or, when
	 * {@link #description} is not set, the exact number of inventory documents for the HU at all).
	 * Additive to {@link #isExists} — asserts "exactly N", not merely "at least one".
	 */
	@Nullable Integer count;
	/**
	 * Expected {@code QtyBook} of the asserted inventory document's lines for the HU (summed when it has
	 * more than one). Compared numerically, so scale does not matter ({@code 0.002} equals {@code 0.00200}).
	 */
	@Nullable BigDecimal qtyBook;
	/** Expected {@code QtyCount}, same scoping and comparison as {@link #qtyBook}. */
	@Nullable BigDecimal qtyCount;
}
