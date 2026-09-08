package de.metas.frontend_testing.masterdata.warehouse;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonWarehouseRequest
{
	boolean inTransit;
	@Nullable String locatorCode;
	@Nullable Map<String, Locator> locators;

	/**
	 * When set, reference a pre-existing seeded warehouse by a stable name instead of creating a new one.
	 * Supported value: {@code "standard"} — the seeded standard warehouse (the one empties network
	 * 540011's seeded line covers). No new {@code M_Warehouse} row is created, and its default locator is
	 * resolved read-only (never renamed), since it is shared across test runs.
	 */
	@Nullable String existing;

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Locator
	{
		@Nullable String x;
		@Nullable String y;
		@Nullable String z;
		@Nullable String x1;
	}
}
