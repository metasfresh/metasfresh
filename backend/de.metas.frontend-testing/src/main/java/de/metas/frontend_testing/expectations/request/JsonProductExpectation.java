package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/** Expectation on what exists for a product, e.g. "a refused scan booked nothing: no HU and no inventory line for it". */
@Value
@Builder
@Jacksonized
public class JsonProductExpectation
{
	/** exact number of active HUs ({@code M_HU}) holding the product, i.e. having an active {@code M_HU_Storage} of it */
	@Nullable Integer huCount;
	/** exact number of {@code M_InventoryLine} of the product */
	@Nullable Integer inventoryLineCount;
}
