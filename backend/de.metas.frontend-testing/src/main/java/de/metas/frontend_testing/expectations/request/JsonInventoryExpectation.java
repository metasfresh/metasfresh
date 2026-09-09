package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

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
}
