package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonManufacturingExpectation
{
	@Nullable List<ReceivedHU> receivedHUs;
	@Nullable List<IssuedHU> issuedHUs;

	//
 	//
 	//

	@Value
	@Builder
	@Jacksonized
	public static class ReceivedHU
	{
		/** The received HU itself — the only handle for a bare VHU/CU, which has neither an LU nor a TU parent. */
		@Nullable Identifier hu;
		@Nullable Identifier lu;
		@Nullable Identifier tu;
		@Nullable QtyAndUOMString qty;
	}

	/**
	 * An HU issued to one of the order's BOM lines. It is created by the issue itself, so it has no
	 * masterdata identifier to address it by - the expectations are positional, ordered by HU id.
	 */
	@Value
	@Builder
	@Jacksonized
	public static class IssuedHU
	{
		@Nullable QtyAndUOMString qty;
		@Nullable Map<String, String> attributes;
	}
}

