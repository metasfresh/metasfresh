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
	/** exact number of active HUs ({@code M_HU}, any level: LUs and their included TUs/VHUs) holding the product, i.e. having an active {@code M_HU_Storage} of it */
	@Nullable Integer huCount;
	/** exact number of active {@code M_InventoryLine}s of the product, over all inventory documents: catches a refused scan that left an inventory line behind even though no HU exists */
	@Nullable Integer inventoryLineCount;
}
